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