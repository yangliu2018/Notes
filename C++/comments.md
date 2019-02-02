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
