# 高性能计算之并行编程技术——MPI并行程序设计

---

# 第一部分 并行程序设计基础

## 第1章 并行计算机

- 并行计算机的分类
  - 执行单元与数据
    - 指令与数据: SIMD, MIMD
    - 程序与数据: SPMD, MPMD
  - 存储方式：共享内存，分布式内存，分布式共享内存


## 第二章 并行编程模型与并行语言
- 2种并行编程模型
  - 数据并行
  - 消息传递
- 产生并行语言的3种方式
  - 设计全新的编程语言
  - 扩展串行语言的语法成分，支持并行特征
  - 不改变串行语言，提供并行库（如MPI）
- 其他并行编程模型
  - 共享变量模型
  - 函数式模型

## 第三章 并行算法
- 并行算法分类
  - 运算的基本对象
    - 数值并行算法（数值计算）
    - 非数值并行算法（符号计算）
  - 进程间依赖关系
    - 同步并行算法（全局时钟控制步调一致）
    - 异步并行算法（步调、进展不同）
    - 纯并行算法（各部分没有任何关系）
  - 并行计算任务大小：平衡并行度和通信开销
    - 粗粒度并行算法
    - 细粒度并行算法
    - 中粒度并行算法
- 并行算法设计
  - SIMD适合同步并行算法
  - MIMD适合异步并行算法
  - 集群（尽量减少通信，计算代替通信）（并行粒度大，重叠计算和通信）
    - 适合集群系统的SPMD并行算法（同步）
    - 适合集群系统的MPMD并行算法（异步）


# 第二部分 基本MPI并行程序设计

## 第4章 MPI简介
- MPI定义
  - MPI是一个库，而不是语言（FORTRAN + MPI, C + MPI)
  - MPI是一种标准或规范的代表，而不是具体实现（MPI程序可以不加修改运行在所有并行机上）
  - MPI是一种消息传递编程模型，且是其事实标准（MPI的目标是进程间通信）
- MPI的目的
  - 较高的通信性能
  - 较好的程序可移植性
  - 强大的功能
- MPI和语言的绑定
  - MPI-1: Fortran 77, C
  - MPI-2: Fortran 90, C++


## 第5章 第一个MPI程序

- MPI程序框架结构
  - 头文件
  - 相关变量声明
  - 程序开始
  - 程序体（计算与通信）
  - 程序结束
- MPI程序惯例
  - MPI的常量、变量、函数的名称均有前缀"MPI_"，不准自定义前缀"MPI_"的名称
  - FORTRAN的MPI调用一般全为大写（FORTRAN不区分大小写）
  - C的MPI调用为MPI_Aaaa_aaa的形式

## 第6章 六个接口构成的MPI子集
- MPI-1有128个调用接口，MPI-2有287个调用接口
- MPI的所有通信功能可以用6个基本MPI调用实现
```cpp
int MPI_Init(int* argc, cahr*** argv); // MPI初始化

int MPI_Finalize(void); // MPI结束

int MPI_Comm_rank(MPI_Comm comm, int* rank);    //当前进程标识（通信域和域中标识号）

int MPI_Comm_size(MPI_Comm comm, int* size);    // 通信域和域中进程数

/**
 * @param buf 发送缓冲区起始地址
 * @param count 发送数据数目
 * @param datatype 发送数据类型（句柄）
 * @param dest 目的进程标识号
 * @param tag 消息类型
 * @param comm 通信域（句柄）
 */
int MPI_Send(void* buf, int count, MPI_Datatype datatype, int dest, int tag, MPI_Comm comm);

/**
 * 返回状态status
 *  MPI_SOURCE 发送数据进程标识
 *  MPI_TAG 发送数据TAG标识
 *  MPI_ERROR 接受操作错误代码
 */
int MPI_Recv(void* buf, int count, MPI_Datatype datatype, int source, int tag, MPI_Comm comm, MPI_Status* status)
```
- MPI消息传递过程
  - 消息装配
  - 消息传递
  - 消息拆卸
- MPI类型匹配
  - 宿主语言类型与通信操作类型匹配
  - 发送和接收的类型匹配
    - 有类型数据的通信，发送方和接收方使用相同数据类型
    - 无类型数据的通信，发送方和接收方使用MPI_BYTE
    - 打包数据的通信，发送方和接收方使用MPI_PACKED
- 数据转换
  - 数据类型的转换（MPI不支持）
  - 数据表示的转换（MPI对异构环境的支持）
- MPI消息 = 信封 + 数据
  - 信封：源/目 + 标识 + 通信域
  - 数据：起始地址 + 数据个数 + 数据类型
- 任意源与任意标识
  - 任意源：MPI_ANY_SOURCE, 接收任意进程发送的消息
  - 任意标识：MPI_ANY_TAG，接收任意tag的消息
  - 接收操作可以接收任何发送者的消息，发送操作必须指明一个单独的接收者
  - 允许发送者=接收者，进程给自身发消息，但要注意死锁
- MPI通信域 = 进程组 + 通信上下文
  - MPI_COMM_WORLD：预定义通信域，MPI初始化时产生，包括初始化时的全部进程

## 第7章 简单MPI程序
```cpp
double MPI_Wtime(); // 当前时刻
double MPI_Wtick(); // 时间精度

// 获取当前进程所运行机器的名字
int MPI_Get_processor_name(char* name, int* resultlen); 

// 获取MPI的主版本号和次版本号
int MPI_Get_version(int* version, int* subversion);
```
- 安全的MPI程序：环状通信依赖引发死锁

## 第8章 MPI并行程序的两种基本模式
- 对等模式
  - Jacobi迭代：新值是旧值相邻数值点的平均
  - 虚拟进程 (MPI_PROC_NULL)
    - 充当真实进程通信的目的或源
    - 方便编写通信语句，简化处理边界的代码
    - 发送/接收虚拟进程的数据时，真实进程中立即返回，相当于执行空操作
- 主从模式
  - 矩阵向量乘法
  - 主进程打印各从进程的信息

```cpp
// 捆绑发送接收
// 避免单独书写发送和接收操作，由于次序错误造成的死锁（通信系统自动优化通信次序）
// 非对称，可以对应一个普通发送操作和一个普通接收操作

int MPI_Sendrecv(
  void* sendbuf, int sendcount, MPI_Datatype sendtype, int dest, int sendtag,
  void* recvbuf, int recvcount, MPI_Datatype recvtype, int source, int recvtag,
  MPI_Comm comm, MPI_Status* status);
```

## 第9章 不同通信模式MPI并行程序的设计


# 第三部分 高级MPI并行程序设计

## 第12章 非阻塞通信MPI程序设计
- 非阻塞通信：实现计算与通信的重叠






# 第四部分 MPI的最新发展MPI-2