# 现代操作系统

---


# 第1章 引论

- 资源管理：多路复用共享资源
    - 时间复用：客户排队，各个客户轮流使用资源
    - 空间复用：每个客户得到资源的一部分


# 第2章 进程与线程

## 2.3 进程间通信
- IPC: Inter Process Communication
- 竞争条件(race condition): 多个进程同时读写共享资源，结果取决于进程运行的精确顺序
- 临界区域(critical region)/临界区(critical region): 访问共享内存的程序片段
- 忙等待(busy waiting): 连续测试某个变量直到某个值出现，浪费CPU
    - 自旋锁(spin lock)是使用忙等待的锁
- 


# 第3章 内存管理

# 第4章 文件系统

# 第5章 输入/输出

# 第6章 死锁

# 第7章 虚拟化和云

# 第8章 多处理机系统

# 第9章 安全