@[TOC]

# UNIX 网络编程
## 卷1：套接字联网API


---

# 第8章 基本UDP套接字编程

| UDP server | UDP client |
|-|-|
| socket() <br> bind() <br> recvfrom() | socket() |
| - | sendto() <br> recvfrom() |
| sendto() | - |

```c
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

// generic struct for any kind of socket
struct sockaddr {
    sa_family_t sa_family;
    char        sa_data[14];
}

// struct specific to IP-based communication (InterNet)
struct sockaddr_in {
    sa_family_t    sin_family; /* address family: AF_INET */
    in_port_t      sin_port;   /* port in network byte order */
    struct in_addr sin_addr;   /* internet address */
};

/* Internet address. */
struct in_addr {
    uint32_t       s_addr;     /* address in network byte order */
};

```

- 首次调用sendto时，udp socket若没有绑定本地端口，则内核分配临时端口
- recvfrom的入参sockaddr\*和socklen_t*可以均为nullptr
- 防止数据报丢失是recvfrom永久阻塞：为recvfrom设置超时


# 20 广播
