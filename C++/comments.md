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