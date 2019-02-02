Boost.Context 
* provide a sort of cooperative multitasking on a single thread
* provide an abstraction of current execution state in current thread
* including stack(with local variables) and stack pointer, all registers and CPU flags, and instruction pointer 
* a execution context presents a special point in the application's execution path
* callcc()/continuation: suspend current execution path and transfer execution control, permmit another context to run on current thread

fiber
* the state of the control flow of a program at a given point in time(the content of processor registers)
* Operating systems simulate parallel execution of programs on a single processor by switching between programs (context switch) by preserving and restoring the fiber
* contains the content of processor registers and manages the associated stack(allocation/deallocation)

call/cc operator(call with current continuation)
* capture current continuation as a first-class object
* pass current continuation as an argument to another continuation

continuation
* the state of control flow of a program at a given point in time
* be suspended and resumed later to change control flow
* the content of processor registers

callcc()
* a function equivalent to call/cc operator
* callcc(continuation (continuation && c)), c: current continuation to be resumed
* context-function: continuation (continuation && c){}



Boost.Fiber
* a framework for micro-/userland-threads(fibers) scheduled cooperatively
* API contains classes and functions to manage and synchronize fibers similiarly to standard thread support library
* each fiber has its own stack
* A fiber can save the current execution state, including all registers and CPU flags, the instruction pointer, and the stack pointer and later restore this state
* fibers can be migrates between threads, using a custom scheduler
* allows multiplex fibers across multiple cores
* fiber block == fiber suspend
* fiber block != thread block
* scheduler is a customization point in fiber manager
* each thread has its own scheduler

scheduler
* round_robin
* work_stealing
* numa::work_stealing
* shared_work

异步IO与非阻塞IO的区别
* asynchronous I/O
    - initiate operation and return immediately
    - notify caller on completion, usually via callback 
* nonblocking I/O: 
    - refuse to start and return error code EWOULDBLOCK if it would block
    - perform only when it can be complete immediately
    - caller usually repeatedly retry the operation until stoping returning EWOULDBLOCK 

线程是抢占式调度的共享数据的轻量级进程，由内核调度  
协程是合作式调度的用户态轻量级线程，不由内核调度  
纤程是微软的一种协程的实现  
管程：管理共享资源的操作系统资源管理模块  
- 同步操作：阻塞当前线程，不阻塞当前进程
- 异步操作：阻塞当前纤程，不阻塞当前线程

* boost.context = processor registers
* boost.coroutine = boost.context + data-transfer
* boost.fiber = boost.coroutine + scheduler
* boost.coroutine: function with void return type, input_type, output_type, control passed between coroutines
* boost.fiber: object of callable type with no parameters, control passed between manager and fiber


MPMC Queue(multi-procducer multi-consumer queue)
