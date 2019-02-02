std::ref, std::cref  generate an object of type std::reference_wrapper  
std::move  
std::forward




reference declaration: named variable, alias, already-existing object or function  
no references to void  
no references to references  
not be cv-qualified at the top level  
not objects, not necessary occupy storage  
no arrays of references  
no pointers to references  

reference collapsing(template/typedef):  r + r => r, others left  
reference collapsing + template argument deduction(T&&) => std::forward  

lvalue reference:  
    1. alias to existing object
    2. pass-by-reference in function call(argument)
    3. lvaue expression for function call(return type)  
    4. extend lifetimes of temporary objects(lvalue reference to const)

rvalue reference:  
    1. extend lifetimes of temporary objects, modifiable  
    2. lvalue/rvalue reference function overloads  
    3. rvalue reference values are lvalues when used in expression  
    4. move constructor, move assignment operator, move-aware function  
    5. move out of an object in scope that is no longer needed

forwarding reference:  
    1. preserve the value category of a function argument => std::forward  
    2. 

dangling reference:  access reference after lifetime of the referred-to objects ends; undefined behavior


scope versus lifetime  
lifetime of reference versus lifetime of object


value category  
C++ expression has type and value category
* glvalue(generalized lvalue)
* prvalue(pure rvalue)
* xvalue(expiring value)
* lvalue = glvalue - xvalue
* rvalue = prvalue + xvalue
* primary categories: lvalue, prvalue, xvalue
* mixed categories: glvalue, rvalue
* special categories: pending member function call, void expression, bit fields
* value = lvalue + xvalue + prvalue = lvalue + rvalue = glvalue + pvalue

C++11 expression properties: identity, movable
|expressions|have identity| can be moved from|
|-|-|-|
|xvalue expressions   |yes   |yes   |
|lvalue expressions   |yes   |no   |
|prvalue expressions   |no   |yes   |
|not used   |no   |no   |
|glvalue expressions   |yes   |unknown   |
|rvalue expressions   |unknown   |yes   |

转发：传递右值引用时，需要将右值引用从左值转换为右值  
T&&模板：引用折叠，同时定义左值引用和右值引用作为参数的函数  
std::forward<T>(x) == static_cast<T&&>(x)  
std::forward<type>(x) == std::forward<type&&>(x) == std::move(x) == static_cast<type&&>(x), type is not a reference, convert to right value  



函数引用参数匹配规则
|实参|形参|
|--|--|
|左值   |左值引用 > const左值引用   |
|const左值   |const左值引用   |
|右值   |右值引用 > const右值引用 > const左值引用|
|const 右值|const右值引用 > const左值引用|

左值右值中的左右指的是可以位于赋值表达式的左右  
不同的引用传递可以重载，值传递与引用传递不能重载  
左值 = 普通左值 + 左值引用 + 右值引用  
变量类型为右值引用的单变量表达式是左值表达式

引用折叠推导规则
|T|T&|T&&|
|-|-|-|
|type   |type&|type&&   |
|type&   |type&|type&   |
|const type&   |const type&|const type&   |
|type&&   |type&|type&&   |
|const type&&   |const type&|const type&&   |
