# Qt事件驱动机制原码解析

- Qt 5.9.0

---


# 事件消息循环


```cpp
/*****************************************************************************
  Main event loop wrappers
 *****************************************************************************/

int QCoreApplication::exec()
{
    if (!QCoreApplicationPrivate::checkInstance("exec"))
        return -1;

    QThreadData *threadData = self->d_func()->threadData;
    if (threadData != QThreadData::current()) {
        qWarning("%s::exec: Must be called from the main thread", self->metaObject()->className());
        return -1;
    }
    if (!threadData->eventLoops.isEmpty()) {
        qWarning("QCoreApplication::exec: The event loop is already running");
        return -1;
    }

    threadData->quitNow = false;
    QEventLoop eventLoop;
    self->d_func()->in_exec = true;
    self->d_func()->aboutToQuitEmitted = false;
    // 执行事件循环
    int returnCode = eventLoop.exec();
    threadData->quitNow = false;

    if (self)
        self->d_func()->execCleanup();

    return returnCode;
}
```


```cpp
int QEventLoop::exec(ProcessEventsFlags flags)
{
    Q_D(QEventLoop);
    //we need to protect from race condition with QThread::exit
    QMutexLocker locker(&static_cast<QThreadPrivate *>(QObjectPrivate::get(d->threadData->thread.loadAcquire()))->mutex);
    if (d->threadData->quitNow)
        return -1;
    if (d->inExec) {
        qWarning("QEventLoop::exec: instance %p has already called exec()", this);
        return -1;
    }
    struct LoopReference {
        QEventLoopPrivate *d;
        QMutexLocker &locker;
        bool exceptionCaught;
        LoopReference(QEventLoopPrivate *d, QMutexLocker &locker) : d(d), locker(locker), exceptionCaught(true)
        {
            d->inExec = true;
            d->exit.storeRelease(false);
            ++d->threadData->loopLevel;
            d->threadData->eventLoops.push(d->q_func());
            locker.unlock();
        }
        ~LoopReference()
        {
            if (exceptionCaught) {
                qWarning("Qt has caught an exception thrown from an event handler. Throwing\n"
                         "exceptions from an event handler is not supported in Qt.\n"
                         "You must not let any exception whatsoever propagate through Qt code.\n"
                         "If that is not possible, in Qt 5 you must at least reimplement\n"
                         "QCoreApplication::notify() and catch all exceptions there.\n");
            }
            locker.relock();
            QEventLoop *eventLoop = d->threadData->eventLoops.pop();
            Q_ASSERT_X(eventLoop == d->q_func(), "QEventLoop::exec()", "internal error");
            Q_UNUSED(eventLoop); // --release warning
            d->inExec = false;
            --d->threadData->loopLevel;
        }
    };
    LoopReference ref(d, locker);
    // remove posted quit events when entering a new event loop
    QCoreApplication *app = QCoreApplication::instance();
    if (app && app->thread() == thread())
        QCoreApplication::removePostedEvents(app, QEvent::Quit);
#ifdef Q_OS_WASM
    // Partial support for nested event loops: Make the runtime throw a JavaSrcript
    // exception, which returns control to the browser while preserving the C++ stack.
    // Event processing then continues as normal. The sleep call below never returns.
    // QTBUG-70185
    if (d->threadData->loopLevel > 1)
        emscripten_sleep(1);
#endif

    // 循环处理事件
    while (!d->exit.loadAcquire())
        processEvents(flags | WaitForMoreEvents | EventLoopExec);
    ref.exceptionCaught = false;
    return d->returnCode.loadRelaxed();
}
```

```cpp
bool QEventLoop::processEvents(ProcessEventsFlags flags)
{
    Q_D(QEventLoop);
    if (!d->threadData->hasEventDispatcher())
        return false;
    return d->threadData->eventDispatcher.loadRelaxed()->processEvents(flags);
}
```

- processEvents是QAbstractEventDispatcher的纯虚方法，不同平台有不同的实现
- Unix平台采用epoll机制
```cpp
bool QEventDispatcherUNIX::processEvents(QEventLoop::ProcessEventsFlags flags)
{
    Q_D(QEventDispatcherUNIX);
    d->interrupt.storeRelaxed(0);
    // we are awake, broadcast it
    emit awake();
    QCoreApplicationPrivate::sendPostedEvents(0, 0, d->threadData);
    const bool include_timers = (flags & QEventLoop::X11ExcludeTimers) == 0;
    const bool include_notifiers = (flags & QEventLoop::ExcludeSocketNotifiers) == 0;
    const bool wait_for_events = flags & QEventLoop::WaitForMoreEvents;
    const bool canWait = (d->threadData->canWaitLocked()
                          && !d->interrupt.loadRelaxed()
                          && wait_for_events);
    if (canWait)
        emit aboutToBlock();
    if (d->interrupt.loadRelaxed())
        return false;
    timespec *tm = nullptr;
    timespec wait_tm = { 0, 0 };
    if (!canWait || (include_timers && d->timerList.timerWait(wait_tm)))
        tm = &wait_tm;
    d->pollfds.clear();
    d->pollfds.reserve(1 + (include_notifiers ? d->socketNotifiers.size() : 0));
    if (include_notifiers)
        for (auto it = d->socketNotifiers.cbegin(); it != d->socketNotifiers.cend(); ++it)
            d->pollfds.append(qt_make_pollfd(it.key(), it.value().events()));
    // This must be last, as it's popped off the end below
    d->pollfds.append(d->threadPipe.prepare());
    int nevents = 0;
    switch (qt_safe_poll(d->pollfds.data(), d->pollfds.size(), tm)) {
    case -1:
        perror("qt_safe_poll");
        break;
    case 0:
        break;
    default:
        nevents += d->threadPipe.check(d->pollfds.takeLast());
        if (include_notifiers)
            nevents += d->activateSocketNotifiers();
        break;
    }
    if (include_timers)
        nevents += d->activateTimers();
    // return true if we handled events, false otherwise
    return (nevents > 0);
}


int QEventDispatcherUNIXPrivate::activateSocketNotifiers()
{
    markPendingSocketNotifiers();
    if (pendingNotifiers.isEmpty())
        return 0;
    int n_activated = 0;
    QEvent event(QEvent::SockAct);
    while (!pendingNotifiers.isEmpty()) {
        QSocketNotifier *notifier = pendingNotifiers.takeFirst();
        QCoreApplication::sendEvent(notifier, &event);
        ++n_activated;
    }
    return n_activated;
}


bool QCoreApplication::sendEvent(QObject *receiver, QEvent *event)
{
    Q_TRACE(QCoreApplication_sendEvent, receiver, event, event->type());
    if (event)
        event->spont = false;
    return notifyInternal2(receiver, event);
}

/*!
  \internal
  \since 5.6
  This function is here to make it possible for Qt extensions to
  hook into event notification without subclassing QApplication.
*/
bool QCoreApplication::notifyInternal2(QObject *receiver, QEvent *event)
{
    bool selfRequired = QCoreApplicationPrivate::threadRequiresCoreApplication();
    if (!self && selfRequired)
        return false;
    // Make it possible for Qt Script to hook into events even
    // though QApplication is subclassed...
    bool result = false;
    void *cbdata[] = { receiver, event, &result };
    if (QInternal::activateCallbacks(QInternal::EventNotifyCallback, cbdata)) {
        return result;
    }
    // Qt enforces the rule that events can only be sent to objects in
    // the current thread, so receiver->d_func()->threadData is
    // equivalent to QThreadData::current(), just without the function
    // call overhead.
    QObjectPrivate *d = receiver->d_func();
    QThreadData *threadData = d->threadData;
    QScopedScopeLevelCounter scopeLevelCounter(threadData);
    if (!selfRequired)
        return doNotify(receiver, event);
    return self->notify(receiver, event);
}


bool QCoreApplication::notify(QObject *receiver, QEvent *event)
{
    // no events are delivered after ~QCoreApplication() has started
    if (QCoreApplicationPrivate::is_app_closing)
        return true;
    return doNotify(receiver, event);
}

static bool doNotify(QObject *receiver, QEvent *event)
{
    if (receiver == 0) {                        // serious error
        qWarning("QCoreApplication::notify: Unexpected null receiver");
        return true;
    }
#ifndef QT_NO_DEBUG
    QCoreApplicationPrivate::checkReceiverThread(receiver);
#endif
    return receiver->isWidgetType() ? false : QCoreApplicationPrivate::notify_helper(receiver, event);
}


bool QCoreApplicationPrivate::notify_helper(QObject *receiver, QEvent * event)
{
    // Note: when adjusting the tracepoints in here
    // consider adjusting QApplicationPrivate::notify_helper too.
    Q_TRACE(QCoreApplication_notify_entry, receiver, event, event->type());
    bool consumed = false;
    bool filtered = false;
    Q_TRACE_EXIT(QCoreApplication_notify_exit, consumed, filtered);
    // send to all application event filters (only does anything in the main thread)
    if (QCoreApplication::self
            && receiver->d_func()->threadData->thread.loadAcquire() == mainThread()
            && QCoreApplication::self->d_func()->sendThroughApplicationEventFilters(receiver, event)) {
        filtered = true;
        return filtered;
    }
    // send to all receiver event filters
    if (sendThroughObjectEventFilters(receiver, event)) {
        filtered = true;
        return filtered;
    }
    // deliver the event
    consumed = receiver->event(event);
    return consumed;
}

bool QObject::event(QEvent *e)
{
    switch (e->type()) {
    case QEvent::Timer:
        timerEvent((QTimerEvent*)e);
        break;
    case QEvent::ChildAdded:
    case QEvent::ChildPolished:
    case QEvent::ChildRemoved:
        childEvent((QChildEvent*)e);
        break;
    case QEvent::DeferredDelete:
        qDeleteInEventHandler(this);
        break;
    case QEvent::MetaCall:
        {
            QAbstractMetaCallEvent *mce = static_cast<QAbstractMetaCallEvent*>(e);
            if (!d_func()->connections.loadRelaxed()) {
                QBasicMutexLocker locker(signalSlotLock(this));
                d_func()->ensureConnectionData();
            }
            QObjectPrivate::Sender sender(this, const_cast<QObject*>(mce->sender()), mce->signalId());
            mce->placeMetaCall(this);
            break;
        }
    case QEvent::ThreadChange: {
        Q_D(QObject);
        QThreadData *threadData = d->threadData;
        QAbstractEventDispatcher *eventDispatcher = threadData->eventDispatcher.loadRelaxed();
        if (eventDispatcher) {
            QList<QAbstractEventDispatcher::TimerInfo> timers = eventDispatcher->registeredTimers(this);
            if (!timers.isEmpty()) {
                // do not to release our timer ids back to the pool (since the timer ids are moving to a new thread).
                eventDispatcher->unregisterTimers(this);
                QMetaObject::invokeMethod(this, "_q_reregisterTimers", Qt::QueuedConnection,
                                          Q_ARG(void*, (new QList<QAbstractEventDispatcher::TimerInfo>(timers))));
            }
        }
        break;
    }
    default:
        if (e->type() >= QEvent::User) {
            customEvent(e);
            break;
        }
        return false;
    }
    return true;
}
```