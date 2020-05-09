```cpp
#include <sys/types.h>
#include <sys/socket.h>

ssize_t send(int sockfd, const void *buf, size_t len, int flags);

ssize_t sendto(int sockfd, const void *buf, size_t len, int flags,
               const struct sockaddr *dest_addr, socklen_t addrlen);

ssize_t sendmsg(int sockfd, const struct msghdr *msg, int flags);


ssize_t recv(int sockfd, void *buf, size_t len, int flags);

ssize_t recvfrom(int sockfd, void *buf, size_t len, int flags,
                 struct sockaddr *src_addr, socklen_t *addrlen);

ssize_t recvmsg(int sockfd, struct msghdr *msg, int flags);
```

```cpp
#include <unistd.h>
ssize_t write(int fd, const void *buf, size_t count);
ssize_t read(int fd, void *buf, size_t count);

```
- write与flags = 0的send等价
- read
    - 若无数据，则等待
    - 若有数据，则有多少读多少，不保证读完
- recv flags
    - MSG_DONTWAIT: nonblocking mode
    - MSG_WAITALL: block until the full request is satisfied


### send/recv的阻塞/非阻塞模式
- 阻塞模式，send
    - send将应用请求发送的数据拷贝到发送缓冲区便返回
    - 若发送缓冲区比请求发送的数据大，send函数拷贝后立即返回，网络开始发送数据
    - 若发送缓冲区比请求发送的数据小
        - send等待接收端对已发送数据的确认，在输出缓冲区中为待发送数据腾出空间，直到所有数据拷贝进入发送缓冲区
        - 接收端协议栈将数据写入接收缓冲区即确认，不需要等待应用调用recv
- 非阻塞模式，send
    - send将数据拷贝至发送缓冲区
    - 若缓冲区可用空间不足，send失败
- send将数据拷贝至TCP/IP协议栈的输出缓冲区，send执行成功不代表数据成功发送
- 发送缓冲区空间充足时，阻塞与非阻塞的send没有区别
- send默认阻塞模式
- recv
    - 若socket上没有消息
        - 若socket阻塞，等待消息到达后返回
        - 若socket非阻塞，直接返回-1
    - 若socket上有消息
        - 返回可用的数据，至多请求的大小，不等待所有数据到达

### 网络
- 端口映射：内网ip:port与外网ip:port映射
- 网关：连接两个不同网络的设备
- 路由：实现路由寻找和包转发的设备
    - 路由能够实现网关的功能
    - PC不具备寻址能力，需要将IP包发送到默认中转地址转发，即默认网关
    - 网关与物理设备无关，可以在路由器、交换机、防火墙、服务器上


### socket收发是否加锁
- TCP
  - 创建一个单独的发送进程，由发送进程统一发送数据
  - 多线程读写同一个socket是错误的设计（可能short write）
    - short write: write/send时返回的数字可能比发送的字节小
  - 全双工，可以1条线程读，1条线程写
- UDP：原子操作，线程安全，可以多线程读写，不需要加锁


### 网络服务器socket IO方式
- 常见网络服务器设计
  - 一个线程服务一个客户端，使用阻塞IO：并发连接数过高时有性能问题
  - 一个线程服务多个客户端，使用非阻塞IO：IO多路复用模型，大部分网络服务器的解决方案
  - 一个线程服务多个客户端，使用异步IO：Unix上未普遍应用
- Linux IO多路复用技术
  - select模型
    - 最大并发数限制1024（可修改系统参数增加最大并发数）
    - 每次调用时线性扫描所有fd，fd数目增多时效率线性下降
  - poll模型：与select模型效率相同
  - epoll模型
    - 没有最大并发数限制，上限是系统可打开的socket fd数目限制
    - 只关心活跃的连接，与连接总数无关，效率高
- IO多路复用机制：事件多路分离器 (Event Demultiplexer)
  - 将事件源的IO事件分离，并分发到对应的read/write事件处理器(Event Handler)
  - 开发人员预先注册事件和事件处理器（回调函数）
  - 事件分离器负责将事件传递给事件处理器
- IO设计模式（IO复用的事件驱动模型）
  - Reactor模式
    - 同步IO
    - 事件就绪时通知用户，分离器关注IO就绪事件
    - 用户线程负责socket缓冲区和应用进程内存之间的数据交换
    - 事件分离器负责等待IO操作，将就绪事件传递给对应的处理器
    - 处理器负责完成实际的IO操作
  - Proactor模式
    - 异步IO
    - 事件完成时通知用户，分离器关注IO完成事件
    - 操作系统负责socket缓冲区和应用进程内存之间的数据交换
    - 分离器兼任处理器，负责发起异步IO操作（无视IO就绪事件）
    - IO操作由操作系统完成
    - 分离器捕获IO完成事件，将事件传递给对应处理器
    - 系统级异步/真异步/需要操作系统支持异步IO
  - 操作步骤
    - Reactor
      - 注册就绪事件和对应的事件处理器
      - 事件分离器等待IO就绪事件
      - 事件到达，激活分离器，分离器调用事件对应的处理器
      - 处理器执行IO操作，处理数据，注册新事件，完成操作后返还控制权
    - Proactor
      - 处理器发起异步IO操作
      - 事件分离器等待IO完成事件
      - 操作系统利用并行内核线程执行操作，将结果存入用户自定义缓冲区，操作完成后通知事件分离器
      - 事件分离器激活事件处理器
      - 事件处理器处理用户自定义缓冲区中的数据，启动新的异步操作，控制权返还事件分离器