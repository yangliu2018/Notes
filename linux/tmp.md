## 直接IO技术


## glibc malloc



## top
- VIRT: virtual image
- RES: resident size
- SHR: shared memory



## shared memory
1. 创建共享内存
```cpp
int shmget(key_t key, size_t size, int shmflg);
```
- key: 共享内存命名
- size: 共享内存容量
- shmflg: 共享内存权限标志
- return: 共享内存标识符

2. 连接共享内存至当前进程地址空间
```cpp
void *shmat(int shm_id, const void *shm_addr, int shmflg);
```
- shm_id: shmget返回的共享内存标志
- shm_addr: 连接至当前进程中的虚拟内存空间地址，通常为空，系统自动选择
- shm_flg: 标志位，通常为0

3. 将共享内存从当前进程分离
- 不删除共享内存，只是当前进程不可用该共享内存
```cpp
int shmdt(const void *shmaddr);
```
- shmaddr: shmat得到的共享内存在该进程地址空间中的地址

4. 控制共享内存
```cpp
int shmctl(int shm_id, int command, struct shmid_ds *buf);
struct shmid_ds
{
    uid_t shm_perm.uid;
    uid_t shm_perm.gid;
    mode_t shm_perm.mode;
}
```
- shm_id: shmget返回的共享内存标识符
- commnad: 操作
    - IPC_STAT: 将共享内存当前关联值写入shmid_ds
    - IPC_SET: 如果有权限，则用shmid_ds设置共享内存的关联值
    - IPC_RMID: 删除共享内存段

## Pipe
- 管道是单向无格式字节流，半双工
- 管道对于两侧的进程而言是存在于内存中的文件
- 写端在管道缓冲区末尾写入，读端在管道缓冲区头部读取
- 管道只能用于有亲缘关系的进程间通信，有名管道能用于无亲缘关系的进程间通信
```c
// create a pipe and two file descriptor
int pipe(int fd[2]);
// fd[0]: read
// fd[1]: write
// pipe + fork
```
```c
// run a subprocess by shell, create a pipe and link it to the stdio of subprocess
FILE *popen( const char* command, const char* mode);
// command: shell command string
// mode: 'r' or 'w', link FILE* to the stdout or stdin of command

// close a subprocess created by pipen
int pclose (FILE* stream);
```
- named pipe/FIFO: 与一个路径名关联，以FIFO的文件形式存在于文件系统中
- 命名管道的应用：shell命令行使用命名管道将数据从一条命令传送到另一条命令，不需要创建临时文件
```c
int mkfifo(const char* pathname, mode_t mode);
int mknod(const char* pathname, mode_t mode, dev_t dev);
```

## Socket
```c
int socket(int domain, int type, int protocal);
```
- domain
    - AF_INET ipv4因特网域
    - AF_INET6 ipv6因特网域
    - AF_UNIX unix域
    - AF_SPEC 未指定
- protobal: 通常为0，自动匹配域和套接字默认的协议
- type
    - SOCKET_STREAM: 流式套接字，使用TCP协议
    - SOCKET_DGRAM: 数据报套接字，使用UDP协议
    - SOCK_RAW: 原始套接字，允许对底层协议如IP或ICMP直接访问，用于新网络协议的实现与测试
    - SOCK_SEQPACKAGE: 长度固定，有序，可靠的面向链接的报文传递
```c
// 字节序
uint32_t htonl(uint32_t hostlong);
uint16_t htons(uint16_t hostshort);
uint32_t ntohl(uint32_t netlong);
uint16_t ntohs(uint16_t netshort);

// 通用地址结构
struct sockaddr
{
    unsigned short sa_famliy;   // AF_XXX, internet domain 
    char sa_data[14];   // 14 bytes address
}

// internet地址结构
struct in_addr {
    in_addr_t s_addr;   // ipv4 address
}

struct socketaddr_in {
    short int sim_family;           // internet address family (AF_INET)
    unsigned short int sin_port;    // port, 16 bits
    struct in_addr sin_addr;        // internet address, 32 bits, ipv4
    unsigned char sin_zero[8];      // alignment
}

// 字符串地址和点分整型地址的转换
const char *inet_ntop(int domain,const void* restrict addr,char* restrict str,socklen_t size);
int inet_pton(int domain,const char* restrict str,void* restrict addr);


```


### FILE*与fd的关系
- FILE是结构体，fd是整型
- 文件描述符是文件指针表的下标
- stdin, stdout, stderr是FILE*类型，在表中的下标分别为0, 1, 2


# child process
- system() = fork() + execl() on ubuntu
- fork: 子进程是父进程的副本，拷贝父进程的资源，父子进程执行顺序不确定
- vfork: 子进程与父进程共享数据段，共享虚拟地址空间，子进程先执行
- clone: 带参数，有选择性的继承父进程的资源
- vfork + exec：推荐使用，没有冗余拷贝
    - 父进程运行 -> vfork -> 子进程执行 -> exec -> 父子进程均执行
- exec: 在同一个进程中前后执行两个不同的程序

```c 
int clone(int (fn)(void ), void *child_stack, int flags, void *arg); 
```

标志	含义
CLONE_PARENT	创建的子进程的父进程是调用者的父进程，新进程与创建它的进程成了“兄弟”而不是“父子”
CLONE_FS	子进程与父进程共享相同的文件系统，包括root、当前目录、umask
CLONE_FILES	子进程与父进程共享相同的文件描述符（file descriptor）表
CLONE_NEWNS	在新的namespace启动子进程，namespace描述了进程的文件hierarchy
CLONE_SIGHAND	子进程与父进程共享相同的信号处理（signal handler）表
CLONE_PTRACE	若父进程被trace，子进程也被trace
CLONE_VFORK	父进程被挂起，直至子进程释放虚拟内存资源
CLONE_VM	子进程与父进程运行于相同的内存空间
CLONE_PID	子进程在创建时PID与父进程一致
CLONE_THREAD	Linux 2.4中增加以支持POSIX线程标准，子进程与父进程共享相同的线程群



### read with timeout
- select(): monitor multiple file descriptors, waiting until one or more of the file descriptors ready for I/O operation
- poll(): wait for some event on a file descriptor
- epoll: I/O event notification facility
```c
int select(int nfds, fd_set *readfds, fd_set *writefds,
            fd_set *exceptfds, struct timeval *timeout);
struct timeval {
    long    tv_sec;         /* seconds */
    long    tv_usec;        /* microseconds */
};


int poll(struct pollfd *fds, nfds_t nfds, int timeout);
struct pollfd {
    int   fd;         /* file descriptor */
    short events;     /* requested events */
    short revents;    /* returned events */
};

```


### nonblocking I/O
```c
int open(const char *pathname, int flags, mode_t mode);
```
- O_NONBLOCK/O_NDELAY: the file is opened in nonblocking mode, neither the open() nor any subsquent I/O operations on the file descriptor will cause the calling process to wait


