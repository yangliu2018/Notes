# 第一章 开始

## 1.1 编写一个简单的C++程序

### 练习 1.1
查阅你使用的编译器的文档，确定它所使用的文件命名约定。编译并运行第2页的main程序。


### 练习 1.2
观察系统如何处理main返回-1的错误标志
```cpp
int main()
{
  return -1;
}
```

## 1.2 初识输入输出

### 练习 1.3
在标准输出上打印Hello, World。
```cpp
#include <iostream>
int main(void)
{
  std::cout << "Hello, World." << std::endl;
  return 0;
}
```

### 练习 1.4
使用乘法运算符*打印两个数的积。
```cpp
#include <iostream>
int main(void)
{
  std::cout << "Enter two numbers:" << std::endl;
  int var1 = 0, var2 = 0;
  std::cin >> var1 >> var2;
  std::cout << "The product of " << var1 << " and " << var2
       << " is "  << var1 * var2 << std::endl;
  return 0;
}
```

### 练习 1.5
重写程序，将每个运算对象的打印操作放在一条独立的语句中。
```cpp
#include <iostream>
int main(void)
{
  std::cout << "Enter two numbers:";
  std::cout << std::endl;
  int var1 = 0, var2 = 0;
  std::cin >> var1;
  std::cin >> var2;
  std::cout << "The product of ";
  std::cout << var1;
  std::cout << " and ";
  std::cout << var2;
  std::cout << " is ";
  std::cout << var1 * var2 << std::endl;
  return 0;
}
```

### 练习 1.6
解释下面程序片段是否合法
```cpp
std::cout << "The sum of" << v1;
          << " and " << v2;
          << " is " << v1 + v2 << std::endl;
```
不合法，分号标志语句结束，下一条语句以"<<"开头，应删除相应分号或加上"std::cout"

### 练习 1.7
编译一个包含不正确嵌套注释的程序，观察编译器返回的错误信息

### 练习 1.8
指出哪些语句合法
```cpp
std::cout << "/*";
std::cout << "*/";
//std::cout << /* "*/" */;
std::cout << /* "*/" /* "/*" */;
```

### 练习 1.9
编写程序，使用while循环将50到100的整数相加
```cpp
#inlcude <iostream>
int main(void)
{
  int sum = 0;
  int val = 50;
  while (val <= 100)
  {
    sum += val;
    ++val;
  }
  std::cout << "Sum of 50 to 100 inclusive is  "sum << std::endl;
  return 0;
}
```

### 练习 1.10
使用递减运算符在循环中按递减顺序打印10到0之间的整数
```cpp
#include <iostream>
int main(void)
{
  int val = 10;
  while (val >=0)
  {
    std::cout << val << std::endl;
    --val;
  }
  return 0;
}
```

### 练习 1.11
提示用户输入两个整数，打印两个整数范围内的所有整数
```cpp
#include <iostream>
int main(void)
{
  std::cout << "Enter two integers: "
  int val1 = 0, val2 = 0;
  std::cin >> val2 >> val2;
  std::cout << "Integers of " << val1 << " to " << val2 " are";
  int valMin = val1 < val2 ? val1 : val2;
  int valMax = val1 > val2 ? val1 : val2;
  int valTemp = valMin;
  while (valTemp <= valMax)
  {
    std::cout << valTemp;
    if (valTemp == valMax)
      std::cout << std::endl;
    else
      std::cout << " ";
    ++valTemp；
  }
  std::cout << std::endl;
  return 0;
}
```

### 练习 1.12
下面的for循环实现了什么功能？sum的终值是什么？
```cpp
int sum = 0;
for (int i = -100; i <= 100; ++i)
{
  sum += i;
}
```
求-100到100之间所有整数的和，结果sum的值是0

### 练习 1.13
使用for循环重做1.4.1节中所有练习

#### 1.9
```cpp
#inlcude <iostream>
int main(void)
{
  int sum = 0;
  for (int val = 50; val <= 100 ; ++val)
  {
    sum += val;
  }
  std::cout << "Sum of 50 to 100 inclusive is  "sum << std::endl;
  return 0;
}
```

#### 1.10
```cpp
#include <iostream>
int main(void)
{
  for (int val = 10; val >=0; --val)
  {
    std::cout << val << std::endl;
  }
  return 0;
}
```

#### 1.11
```cpp
#include <iostream>
int main(void)
{
  std::cout << "Enter two integers: "
  int val1 = 0, val2 = 0;
  cin >> val2 >> val2;
  std::cout << "Integers of " << val1 << " to " << val2 " are";
  int valMin = val1 < val2 ? val1 : val2;
  int valMax = val1 > val2 ? val1 : val2;
  for (int valTemp = valMin; valTemp <= valMax; ++valTemp)
  {
    std::cout << " " << temp;
  }
  std::cout << std::endl;
  return 0;
}
```

### 练习 1.14
对比for循环和while循环，两种形式的优缺点是什么？
for循环能缩小计数变量的作用域


### 练习 1.15
编写程序，包含第14页“再探编译”中讨论的常见错误，熟悉编译器生成的错误信息

### 练习 1.16
编写程序，从cin读取一组数，输出其和
```cpp
#include <iostream>
int main()
{
  int sum = 0;
  while (std::cin >> val)
  {
    sum += val;
  }
  std::cout << "The sum is " << sum << std::endl;
}
```

### 练习 1.17
如果输入的所有值都是相等的，本节的程序会输出什么？如果没有重复值，输出又会怎么样？
假设总共输入n个数
如果输入的所有值都是相等的，输出只有一条语句，该值被输入n次
如果输入的所有值都互不相等，输出将有n条语句，每个数都被输入一次

### 练习 1.18
编译并运行本节程序，分别输入全部相等的值和没有重复的值并运行
```cpp
#include <iostream>
int main()
{
  int currVal = 0, val = 0;
  if (std::cin >> currVal)
  {
    int cnt = 1;
    while (std::cin >> val)
    {
      if (val == currVal)
        ++cnt;
      else
      {
        std::cout << currVal << " occurs "
                  << cnt << " times" << std::endl;
        currval = val;
        cnt = 1;
      }
    }
    std::cout << currVal << " occurs "
              << cnt << " times" << std::endl;
  }
  return 0;
}
```

### 练习 1.19
修改1.4.1节练习1.10所编写的程序，使其能处理第一个数比第二个数小的情况


## 1.5 类简介
