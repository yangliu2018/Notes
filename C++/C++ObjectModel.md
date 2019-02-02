# C++对象模型

### 章节目录
* 第1章 关于对象(Object Lessons)
* 第2章 构造函数语意学(The Semantics of Constructors)
* 第3章 Data语意学(The Semantics of Data)
* 第4章 Function语意学(The Semantics of Functions)
* 第5章 构造、解构、拷贝语意学(Semantics of Construction, Destruction, and Copy)
* 第6章 执行期语意学(Runtime Semantics)
* 第7章 站在对象模型的类端(Ont the Cusp of the Object Model)





* C++对象模型的概念
	* 语言中直接支持面向对象程序设计的部分
	* 对于各种支持的底层实现机制


## 第1章 关于对象

* C++在布局及存取时间上主要的额外负担是由virtual引起
	* virtual function机制：支持有效率的执行期绑定(runtime binding)
	* virtual base class：实现多次出现在继承体系中的base class，有单一而共享的实体
* 多重继承下的额外负担：一个derived class和第二或后继的base class的转换
* 没有什么天生的理由说C++程序一定比C庞大或迟缓
* 2种class data members: static, nonstatic
* 3种class member functions: static, nonstatic, virtual
* C++对象模型
	* nonstatic data members存放在class object内
	* static data members存放在class object外
	* static and nonstatic function members存放在class object外
	* virtnal functions
		* 每个类产生一堆只想virtuan functions的指针，存放在虚函数表(virtual table, vtbl)中
		* 每个class object被添加一个指针(vptr)，指向相关的virtual table
		* vptr的setting和resetting由每个class的constructor, destructor和copy assignment运算符自动完成
		* 每个class关联的type_info object（用于支持runtime type identification, RTTI）一般放在vtbl的第一个slot



## Runtime Library