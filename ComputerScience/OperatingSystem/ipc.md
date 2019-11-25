
# IPC: inter process communication
- 进程间通信的方式
    - 管道 (pipe)
    - 高级管道 (popen)
    - 有名管道 (named pipe)
    - 信号量 (semaphore)
    - 消息队列 (message queue)
    - 信号 (signal)
    - 共享内存 (shared memory)
    - 套接字 (socket)
- 线程间通信的方式
    - 全局变量
    - 参数传递
    - 消息传递
- 线程间的同步和互斥
    - 临界区
    - 互斥量
    - 信号量
    - 事件
- linux进程间通信原语
    - sleep/wakeup
    - send/receive: message passing
- mutex is binary semaphore
- mutex

## semaphore的实现原理

### linux semaphore
```cpp
struct semaphore {
	raw_spinlock_t		lock;
	unsigned int		count;
	struct list_head	wait_list;
};

extern int __must_check down_interruptible(struct semaphore *sem);
extern int __must_check down_killable(struct semaphore *sem);
extern int __must_check down_trylock(struct semaphore *sem);
extern int __must_check down_timeout(struct semaphore *sem, long jiffies);
extern void up(struct semaphore *sem);
```

### POSIX semphore
```cpp
#include <semaphore.h>

// sem_t是长整型
typedef union
{
  char __size[__SIZEOF_SEM_T];
  long int __align;
} sem_t;

// unnamed semaphore
int sem_init(sem_t *sem, int pshared, unsigned int value);
int sem_wait(sem_t * sem);
int sem_timedwait(sem_t * sem, const struct timespec *abstime);
int sem_trywait(sem_t * sem);
int sem_post(sem_t * sem);
int sem_post_multiple(sem_t * sem, int number);
int sem_getvalue(sem_t * sem, int * sval);
int sem_destroy(sem_t * sem);

// named semaphore
sem_t *sem_open(const char *name, int oflag);
sem_t *sem_open(const char *name, int oflag, mode_t mode, unsigned int value);
int sem_close(sem_t *sem);
```
- 在共享内存中创建信号量