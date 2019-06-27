constexpr关键字
内建函数




IO多路复用 = 事件驱动模型 = Reactor设计模式
异步IO模型 = Proactor设计模式
IO多路复用使用同步非阻塞IO

* 5种I/O模型
* 阻塞I/O模型
* 非阻塞I/O模型
* I/O复用模型
* 信号驱动I/O模型
* 异步I/O模型

挂起 != 阻塞

推送：接收方  
轮询：调用方

初始化和赋值不修改虚表指针
类型转换构造函数和类型转换操作符同时存在时，优先调用转换构造函数，但仍可以显式调用转换操作符


model/view  数据层/表示层

MVC 由三种对象组成。Model是应用程序对象，View是它的屏幕表示，Controller定义了用户界面如何对用户输入进行响应。在MVC之前，用户界面设计倾向于三者揉合在一起，MVC对它们进行了解耦，提高了灵活性与重用性

operator bool(): type conversion function, no specified return type  
bool operator !(): operator overloading


thread stack
* non-supporting thread systems: thread libraries allocate stack space from heap and implement thread switching using timers
* build-in supporting thread systems: ususlly create stack by making new pages into address space; guard page at ench end of the stack to detect overruns and underruns

mstsc(Microsoft Terminal Services Client)

- 多态
    - 运行时绑定
    - 运算符重载
    - 函数重载
    - 模板


- 类的构造函数的隐含入参this指针，用于指明构造对象的地址
- 类的构造函数开始自动调用基类的构造函数
- 类的析构函数结尾自动调用基类的析构函数
- 基类的析构函数定义为虚函数是为了多态情况下父类指针指向的子类对象的析构
- 基类中的"delete this"语句用于多态下的子类对象的析构
- "delete this"将调用析构函数并释放内存空间，栈上的对象不可使用

- static的2种语义
    - 存储方式：静态存储方式及生命周期
    - 访问控制：作用域是当前源文件而不是所有源文件
- static/extern
    - extern: 带外部链接的静态存储期指定符
    - extern: 全局声明，当作全局变量使用，作用域当前源文件
        - 全局变量定义
        - 局部变量定义：相当于静态局部变量
- 全局变量与静态全局变量
    - 相同点：静态存储方式，在main函数之前初始化，随程序构造和析构
    - 不同点：作用域不同，静态全局变量的作用域限定为该源文件，普通全局变量的作用域是全部源文件
    - 使用静态全局变量可以避免污染其他源文件
- 局部变量与静态局部变量
    - 相同点：作用域相同，为当前函数
    - 不同点：存储方式和生命周期不同，静态局部变量在第一次遇到时初始化，在程序结束时析构
- 成员变量与静态成员变量
    - 相同点：限制在类中
    - 不同点
        - 成员变量属于单个对象，静态成员变量属于类及其所有对象
        - 成员变量随对象构造和析构，静态成员变量随程序构造和析构
    - 静态成员变量是限制使用类或对象访问的普通全局变量，在main函数之前初始化，构造和析构与类和对象无关
- 全局函数与静态全局函数
    - 相同点：全局函数
    - 不同点：作用域不同，内部函数只能被本文件调用，外部函数能够被所有源文件调用
- 成员函数与静态成员函数
    - 不同点：是否存在this指针
    - 静态成员函数式限制使用类或对象访问的普通全局函数


- 进程间通信的方式
    - 管道 (pipe)
    - 高级管道 (popen)
    - 有名管道 (named pipe)
    - 信号量 (semophore)
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



- 两个类互相依赖
- A拥有B的对象，B拥有A的指针或引用
- A.h: #include "B.h"
- B.h: class A;
- A.cpp: nothing
- B.cpp: #include "A.h"


- nan: not a number
- inf: infinity
- snan: signaling nan
- qnan: quiet nan
- ind: indefinite nan, a special type of quiet NaN generated under specific conditions.



### istream
- get: reads until a delimiter and leaves delimiter in stream
- getline: reads until a delimiter, read the delimiter and drop it
- read
- readsome: read characters immediately avaiable


### static loacal variable initialization is thread-safe
- 一个线程初始化静态局部变量时，其他执行到这里的线程会被挂起
```cpp
// initialize static local variable once and thread-safe
class Bar {};

// c++11
Bar& foo() {
    static Bar bar;
    return bar;
}

// two check and one lock: check -> lock -> check
// access without lock parallelly
// create with lock and only once
std::mutex mtx;
Bar& foo() {
    static Bar* p = nullptr;
    if (!p) {
        std::lock_guard<std::mutex> lock(mtx);
        if (!p) p = new Bar();
    }
    return *p;
}

// one lock and one check, but loss efficiency when accessing: lock -> check
Bar& foo() {
    static Bar* p = nullptr;
    {
        std::lock_gurad<std::mutex> lock(mtx);
        if (!p) p = new Bar();
    }
    return *p;
}

// initialize when process begining
Bar bar;
Bar& foo() {
    return bar;
}
```


### constexpr
- 修饰对象
    - const: 未区分编译期常量和运行期常量
    - constexpr: 编译期常量，编译期求值，可在常量表达式中出现
- 修饰函数
    - 如果可以编译期求值，则求值并返回编译期常量
    - 如果不可以编译期求值，则忽略constexpr，视作普通函数


### macro预编译过程
- token: 标记，源代码中的唯一标记，理解为单词
- 预编译过程没有类型和变量的概念，仅仅只是标记的替换
- 什么都不加：将标记按原样放入预编译的代码
- #: 字符串操作符，位于标记前，将标记转换为字符串，即加上""后放入预编译的代码
- ##: 连接符，位于标记前或标记后，将标记直接与前或后连接成为新的标记；分隔符，表明##前后是两个标记，而不是前后整体是一个标记，并去除##前后的空格，将标记连接
- @#: 字符化操作符，位于宏函数的参数前，将传入的单字符参数名转换为字符，并加上''
- +, -, *, /等常见操作符也是分隔符


### new/delete
- operator new: a function that allocates raw memory, same as malloc
- new operator: an operator that calls function operator new
```cpp
class Foo {
public:
    void* operator new(size_t);
    void operator delete();
}

Foo* foo = new Foo();   // call Foo::operator new(size_t)
delete foo;             // call Foo::operator delete()
```
- operator new, operator new[]: allocation functions as the name of operator-like functions
- new expression: creates and initializes objects with dynamic storage duration
```cpp
void* buffer = ::operator new(100); // allocate memory
Foo* foo = new (buffer) Foo();      // construct object
foo->~Foo();                        // deconstruct object
::operator delete(buffer);          // deallocate memory
```
- 栈上局部对象显式调用析构函数会与离开作用域析构冲突，造成二次析构
- 堆上申请内存的对象显式调用析构会与delete析构冲突，造成内存泄漏或二次析
- 栈上或堆上的普通对象都有内存的自动申请和自动释放
- 栈上普通对象由线程分配和释放内存
- 堆上普通对象由进程分配和释放内存


### const T *, T const *, T * const
- T const* / const T*: nonconstant pointer to constant T
- T* const: constant pointer to nonconstant T
- const T* const / T const* const: constant pointer to constant T
- reference ==> T* const
- const reference ==> const T* const


### 不要直接从网络收发结构体的原因
1. 结构体可能含有指针，存在结构体外的资源
2. 直接收发依赖于两端的结构体内存布局完全一致，而芯片结构、操作系统、编译器均会影响结构体的内存布局
3. 建议使用序列化框架收发结构体

### 红黑树
- 左旋：旋转节点、右子节点、右子节点的左子节点
    - 右子节点成为父节点，旋转节点成为左子结点，右子节点的左子结点成为左子结点的右子节点
    - 左子树高度加一，右子树高度减一
    - 右子树的左子树被挪到左边，成为左子树的右子树
    - 整棵树看起来像是逆时针旋转
- 右旋与左旋互为逆运算
- 红黑树是自平衡二叉搜索树
- 红黑树的规则
    - 每个节点有颜色，非红即黑
    - 根节点黑色
    - 没有相邻的红色节点（红色节点的父子节点为黑节点）
    - 某个节点到它的后代NIL节点的所有路径有相同的黑节点数目（黑平衡）
- 插入红色节点后
    - 如果父节点为黑，结束
    - 如果父节点为红，则祖父节点为黑
        - 问题转变为有一对相邻红色节点的红黑树的调整问题，黑平衡未破坏
        - 如果叔节点为红，则父节点和叔节点变黑，祖父节点变红，当前节点变为祖父节点，循环
        - 如果叔节点为黑，则需要旋转，将红色的当前节点和父节点中靠近另一个子树的节点变为黑色的祖父节点，原来黑色的的祖父节点变为红色的子节点，即当前子树减少一个红色节点，高度减一，另一个子树增加一个红色节点，高度加一，结束
- 删除节点前的交换（将要删除的节点变为树末节点）
    - 若当前节点无子节点，不需要交换
    - 若当前节点有且只有一个子节点，交换当前节点和子节点的键值
    - 若当前节点有两个子节点，交换当前节点和后继节点（大于删除节点的最小节点，右子树的最左节点）的键值
- 标准BST删除：删除的节点为叶结点或只有一个子节点
- 删除节点
    - 交换待删节点和其中序后继
        - 中序后继是大于当前节点的最小节点
        - 中序后继只可能有0个子节点或1个右子节点，无左子结点
        - 将删除有2个子节点的节点的问题转变为删除有0或1个子节点的节点的问题
    - 若待删节点没有子节点，则将其视为有一个黑色NIL子节点的节点
        - 将删除有0或1个子节点的节点的问题转变为删除有且只有1个子节点的节点的问题
    - 若待删节点为红色
        - 父节点和子节点必定为黑色
        - 删除待删节点，将黑色的子节点上移，结束
        - 此情形下的黑色子节点必定为NIL
    - 若待删节点为黑色
        - 若待删节点的子节点为红色
            - 删除待删节点，红色子节点上移并变为黑色，结束
        - 若待删节点的子节点为黑色
            - 此情形下的黑色子节点必定为NIL
            - 删除待删节点，黑色子节点上移并变为双黑色
            - 问题转变为存在一个节点为双黑节点的红黑树的调整
                - 当前红黑树仍然黑平衡，仅需要将双黑调整为单黑
                - 由于NIL的存在，可以只考虑双黑节点的子节点和兄弟节点的子节点均存在的情形，将问题泛化
                - 递归解决该问题时双黑节点可能存在非NIL的子节点
            - case 1: 双黑节点为根
                - 双黑变单黑，结束
            - case 2: 双黑节点的兄弟节点为黑色
                - case 2.1: 黑色兄弟节点有红色的子节点
                    - 不需要考虑父节点颜色
                    - case 2.1.1: 黑色兄弟节点的右子节点为红色
                        - 绕父节点左旋，修改颜色
                            - 黑色兄弟节点变为父节点，颜色变为原来父节点的颜色
                            - 父节点变为左子树的根结点，变黑
                            - 兄弟节点的红色右子节点变为右子树的根节点，变黑
                        - 双黑变单黑
                    - case 2.1.2: 黑色兄弟节点的左子结点为红色，右子节点为黑色
                        - 绕兄弟节点顺右旋，修改颜色
                            - 黑色兄弟节点向右下降并变红
                            - 兄弟节点的红色左子结点上升并变黑
                        - 转变为黑色兄弟节点有红色右子节点的情形，即case 2.1.1
                - case 2.2: 黑色兄弟节点的子节点均为黑色
                    - case 2.2.1: 父节点为红色
                        - 双黑变单黑，兄弟节点黑变红，父节点红变黑，结束
                    - case 2.2.2: 父节点为黑色
                        - 双黑变单黑，兄弟节点黑变红，父节点黑变双黑
                        - 存在一个节点为双黑的红黑树的调整问题，递归解决
            - case 3: 双黑节点的兄弟节点为红色
                - 父节点必定为黑色，兄弟节点的子节点必定为黑色
                - 黑色父节点和红色兄弟节点交换颜色，绕父节点左旋
                - 转变为兄弟节点为黑色的情形，即case 2
- 旋转红黑树，调整颜色，不影响黑平衡的情形
    - 父节点为黑色，子节点为红色，绕父节点旋转并交换父子节点的颜色
    - 父节点为黑色，子节点为红色，祖父节点为红色或黑色，绕祖父节点旋转
        - 父节点变为祖父节点并变为原祖父节点的颜色
        - 祖父节点变为
### C++ ABI
- C++头文件决定ABI，是给编译器看的
- C++ ABI的主要内容
    - 函数参数传递的方式，如x86-64用寄存器传递函数的前4个整数参数
    - 虚函数的调用方式，通常是vptr/vtbl然后用vtbl[offset]调用
    - struct和class的内存布局，通过偏移量访问数据成员
    - name mangling
    - RTTI和异常处理的实现


### 多维数组
- C/C++没有真正的多维数组，只有数组的数组
- 矩形二维数组：T[M][N] <==> std::array<std::array<T, N>, M>
- 锯齿形二维数组：std::vector<std::vector\<T>>
- 用一维数组模拟二维数组
```cpp
template <class T>
class Matrix {
public:
    Matrix(size_t rows = 0; size_t cols = 0)
        : m_rows(rows), m_cols(cols), m_data(rows * cols) {}

    T& at(size_t row, size_t col) {
        return m_data.at(row * m_cols + col);
    }

    void resize(size_t rows, size_t cols) {
        m_rows = rows;
        m_cols = cols;
        m_data.resize(rows * cols);
    }
private:
    size_t m_rows;
    size_t m_cols;
    std::vector<T> m_data; 
}
```


### Constraint Satisfaction
- techniques used to solve constraint satisfaction problems
    - constraints on a finite domain: search (backtracking, local search, ...)
    - constraint propagation
    - constraints on real or rational numbers: variable elimination or simplex algorithm
- constraint satisfaction problems
    - eight queens puzzle
    - Sudoku
    - many logic puzzles
    - boolean satisfiability problem
    - scheduling problem
    - bounded-error estimation problem
    - various problems on graphs such as the graph coloring problem
    - optimization problem in which the optimized function is the number of violated constraints
- solving
    - nonlinear constraints: variants of backtracking, constraint propagation, and local search
    - linear and polynomial equations and inequalities, and problems containing variables with infinite domian
        - variable elimination, simplex algorithm
- constraint propagation: when one element is identified, update the constraints of other elements

### x & (x - 1)
- x & (x - 1)的效果是将x的为1的最低二进制位变为0
- 判断正整数是否为2的次方：x & (x - 1) ?= 0
- 判断正整数的二进制表示中1的个数
    - 循环 x = x & (x - 1)，直到x == 0，循环次数即为1的个数
```math
\begin{aligned}
value &= x_1 x_2 ... x_n \\
assume \ x_i &= 1, x_{i+1}, x_{i+2}, ..., x_n = 0 \\
value &= x_1 x_2 ... x_{i-1} 1 00...0 \\
value - 1 &= x_1 x_2 ... x_{i-1}011...1 \\
\implies value \ \& \ (value - 1) &= x_1 x_2 ... x_{i - 1}000...0
\end{aligned}
```