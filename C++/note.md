# 第一章 开始

## 1.1 编写一个简单的C++程序
1个函数包含4个部分：
* 返回类型 return type
* 函数名 function name
* 形参列表 parameter list
* 函数体 function body

访问main函数的返回值
* UNIX: $echo $?
* Windows: $echo %ERRORLEVEL%

## 1.2 初识输入输出
C++语言本身没有IO语句，由标准库(standard library)提供IO机制(iostream库)
* 一个流(stream)是一个字符序列
* istream: 输入流
* ostream: 输出流
标准输入输出对象（4个）
* istream对象
 * cin 标准输入(standard input)
* ostream对象
 * cout 标准输出(standard output)
 * cerr 标准错误(standard error) 输出警告和错误信息
 * clog 输出程序运行时的一般性信息

字符串字面值常量(string literal)
操纵符(manipulator)
* endl: 结束当前行，并将与设备关联的缓冲区(buffer)中内容刷到设备中
* 缓冲区刷新操作可以保证程序产生的输出写入输出流而不是停留在内存中
* 调试时添加的打印语句应当保证一直刷新流，否则程序崩溃时输出可能仍留在缓冲区中，错误判断崩溃位置

命名空间(namespace)
作用域运算符(::)

从键盘输入文件结束符
* Windows: Ctrl + Z, Enter
* Unix: Ctrl + D

常见的编译器可以检查的错误（编译器只能检查形式上的错误）
* 语法错误(syntax error)
* 类型错误(type error)
* 声明错误(declaration error)
单个错误具有传递效应，导致编译器在其后报告比实际数量多得多的错误信息
好习惯：修正一个错误后立即重新编译代码，“编辑-编译-调试”(edit-compile-debug)

## 1.5 类简介
类类型(class type)
成员函数(member function)==方法(method)
点运算符 . 访问成员函数
调用运算符 () 调用函数

文件重定向
$ filename &lt;infile >outfile
