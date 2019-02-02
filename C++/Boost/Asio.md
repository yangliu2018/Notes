TCP: read_some/wirte_some
UDP/ICMP: receive_from/send_to


socket read/write functions

read_some/write_some(buffer)
receive/send(buffer[, flags])
receive_from/send_to(buffer, endpoint[, flags]])


free stream read/write functions

connect/async_connect ==> condition
read/write/asycn_read/async_write ==> completion
read_until/async_read_until ==> delimeter/completion
read_at/write_at/async_read_at/async_write/at ==> offset/completion  ==> not sockets, but random access stream

async handler: void handler(const boost::system::error_code&, size_t)  
connect condition: iterator connect_condition(const boost::system::error_code&, iterator next)  
read/write/read_at/write_at completion: size_t completion(const boost::system::error_code&, size_t)  
read_until delimeter: character, std::string or boost::regex  
read_until completion: pair<iterator, bool> completion(iterator begin, iterator end)  

run: 是否存在异步操作，若存在则阻塞  
poll: 是否需要CPU执行异步操作或其处理程序，若当前不需要则立即返回
run_one: 
poll_one: 

post: 立即返回
dispatch: 如果当前线程run，则执行handler
wrap: 包装为factor，之后调用时相当于io_context.dispatch()

serialize asynchronous actions: 
1. io_context::strand::post/dispatch
2. io_context::strand::wrap ==> io_context::post/dispatch  


identify the start and end of message
* start: first byte passed at the end of previous message
* fixed-size messages
* specific character that ends message, such as '\n' or '\0'
* specify message length as the prefix of the message

make-request-read-answer strategy
* pull-like(pull requests, client to server)(client request, server answer)
* push-like(push requests, server to client)(server notify, client acknowledge)
* simulate push-like using pull-like ping process

co-routine: allow multiple entry points for suspending and sesuming execution at certain locations with a function
yield: 建议实现重调度线程的执行；执行完当前时间片后，放弃CPU，线程插入调度队列，不挂起  
sleep: 线程休眠一段时间；线程挂起，离开调度队列，阻塞一定的时间后，重新插入调度队列  

* coroutine: the class to derive or use in connection class in order to impletement co-routines
* reenter(entry): the body of co-routine. entry is a pointer to a coroutine instance to be used as a block with the whole function
* yield code: execute a statement as part of the co-routine. next time the function is entered, execution will start after this code
