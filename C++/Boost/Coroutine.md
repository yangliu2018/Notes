|Library|Comparison|
|-|-|
|Boost.Coroutine   |deprecated, non-C++11   |
|Boost.Coroutine2   |C++11, asymmetric, stackful   |
|Boost.Fiber   |C++11, cooperative tasks   |
|Boost.Asio.Coroutine   |   |

Boost.Coroutine2
* provides templates for generalized subroutines which allow suspending and resuming execution at certain locations
* preserves the local state of execution and allows re-entering subroutines more than once

coroutine: a language-level construct providing a special kind of control flow

routine: 
* a sequence of operations
* parent-child relationship
* child terminates always before parent

coroutine:  
* a generalization of routines 
* preserve execution state 
* explicit suspend and resume
* enhanced control flow(maintaining execution context)
* each coroutine has its own stack and control-block(fcontext_t from Boost.Context)
* before suspend: store non-volatile registers in control-block(including stack and instruction/program pointer)
* before resume: resotore registers
* context switch requires no system privileges
* context switch provides cooperative multitasking 


first-class continuation
* can be passed as an argument,
* returned as a function,
* and stored in a data struction


协程相比线程的优势
1. 执行效率高：程序控制子程序的切换，没有线程切换的开销
2. 不需锁机制：单线程，不存在同时写变量冲突，共享资源不加锁，只需判断状态


RAII(Resource Acquisition is Initialization)
ABI(application binary interface)
API(application program interface)


coroutine<>::pull_type
* transfer data from another executin context(pulled-from)
* template parameter: transfered parameter type
* constructor parameter: a coroutine-function accepting a reference to a coroutine<>::push_type
* instantiation: pass the control of execution to coroutine-function
* a complementary coroutine<>::push_type is synthesized by the library and passed as reference to coroutine-function
* coroutine<>::pull_type::operator(): only switch context, tranfer no data
* coroutine<>::pull_type::iterator/std::begin()/std::end(): input iterator

coroutine<>::push_type
* transfer data to another execution context(pushed-to)
* template parameter: transfered parameter type
* constructor parameter: a coroutine-function accepting a reference to a coroutine<>::pull_type
* instantiation: not pass the control of exection to coroutine-function
* the first call of coroutine<>::push_type::operator(): symthesize a complementary coroutine<>::pull_type and pass it as reference to coroutine-function
* coroutine<>::push_type::iterator/std::begin()/std::end(): ouput iterator

coroutine-function
* return: void
* parameter: counterpart-coroutine
* same template argument for both coroutine types
* category
    - pull_type(coroutine(push_type)): output-coroutine, pull-coroutine, source 
    - push_type(coroutine(pull_type)): input-coroutine, push-coroutine, sink
* enter
    - pull_type(coroutine(push_type)): construction
    - push_type(coroutine(pull_type)): first invocation of operator()(T)
* switch context
    - pull_type: operator()()
    - push_type: operator()(T)
* send data:
    - push_type: operator()(T)
* check state(of coroutine and transfered data)
    - operator bool() & bool operator !()
* access data
    - pull_type: get()


stackful coroutine: 每个协程有独立的运行栈  
stackless coroutine: generator, continuation, async/await没有独立的栈空间，状态保存在闭包中  
并发不需多线程/多进程/多核，并行必须多线程/多进程/多核  
需要IO并发的原因：IO设备通常远慢于CPU  
事件驱动的两种中断响应机制：
1. 设备就绪时发起中断，通知CPU就绪，CPU执行操作(等待 => 就绪 => 中断 => (CPU)执行)
2. 设备完成操作后发起中断，通知CPU操作完成(等待 => 就绪 => (设备)执行 => 中断)

核心态中没有线程的概念，一切都是异步的事件驱动，线程是核心态给用户态提供的高层概念


生成器/协程/流对象
stack unwinding: objects allocated on stack are unwound when exiting scope
exception handling: throw exception after stack unwinding
