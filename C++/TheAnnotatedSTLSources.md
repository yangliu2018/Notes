# STL原码剖析
# The Annotated STL Sources(using SGI STL)
### 侯捷
---
### 源码之前 了无秘密
---
## 第1章 STL概论与版本简介
- abstract concepts
    - assignable
    - default constructible
    - equality comparable
    - less-than comparable
    - regular
    - input iterator
    - ouput iterator
    - foward iterator
    - bidrectional iterator
    - random access iterator
    - unary function
    - binary function
    - predicate
    - binary predicate
    - sequence container
    - associative container
    - ...
- 软件组件分类学
- STL六大组件
    1. 容器(containers): 存放数据的数据结构，类模板
    2. 算法(algorithms): 函数模板
    3. 迭代器(iterators): 泛型指针，连接容器和算法，重载了指针操作的类模板
    4. 仿函数(functors): 重载operator()的类模板
    5. 配接器(adapters): 修饰容器、仿函或迭代器的接口的适配器
    6. 配置器(allocators): 负责动态空间分配、管理和释放的类模板
- STL以source code而非binary code形式出现在C++标准库中


## 第2章 空间配置器
- 空间：内存、磁盘或其他存储介质
- allocator的必要接口
    - allocator::value_type
    - allocator::pointer
    - allocator::const_pointer
    - allocator::reference
    - allocator::const_reference
    - allocator::size_type
    - allocator::difference_type
    - allocator::rebind
    - allocator::allocator()
    - allocator::allocator(const allocator&)
    - ...
- SGI标准空间分配器std::allocator    ::operator new/delete的封装
- SGI特殊空间分配器std::alloc
    - 内存配置 alloc::allocate()
    - 内存释放 alloc::deallocate()
    - 对象构造 ::construct()
    - 对象析构 ::destruct()
- 对象构造泛化为new(pointer) T(value)
- 对象析构根据对象类型特化，trivial destruct no-op
- 双层级配置器
    - 第一级配置器直接使用malloc/free/realloc
    - 第二级配置器视情况采用不同策略：大内存调用第一级配置器，小内存采用内存池
## 第3章 迭代器(iterators)概念与traits编程技法
- 迭代器是智能指针
- 迭代器的实现需要知道容器的实现细节，因此迭代器一般由容器设计者开发
- 迭代器相应类型的(associate types)推导
    - 函数模板参数作为函数形参可以用于推导类型
    - 迭代器指向数据类型的推导：迭代器解引用作为函数模板的实参
    - 迭代器相应类型不止所指对象类型，常用的相应类型有5种
- 5种associated types
    - value_type
    - difference_type
    - pointer
    - reference
    - iterator_category
```cpp
template <class I, class T>
void func_impl(I i, T t) {
    // type T is value_type
}

template <class I>
void func(I i) {
    func_impl(I, *I);
}
```

- traits编程技法
    - template参数推导机制只能推导参数，不能推导返回值
    - 内嵌类型声明：typedef T value_type;
    - 并非所有的迭代器都是类类型，不是类类型的迭代器无法定义内嵌类型
    - 类类型迭代器的value_type类型推导：迭代器类的内嵌类型
    - 原生指针迭代器的value_type推导：模板的偏特化
- 迭代器分为5类
    - input iterator
    - output iterator
    - forward iterator
    - bidirectional iterator
    - random access iterator
- 形参只声明类型，不指定形参名，不在函数体中使用，用于函数重载
- 用模板的类型推导实现的编译期间接层
- 迭代器类型定义规则：迭代器符合的最高迭代器类型
- 算法的迭代器参数命名规则：算法能接受的最低迭代器类型
- 设计相关型别是迭代器的责任，设计迭代器是容器的责任
- 算法使用迭代器的对外接口，算法独立于容器和迭代器
```cpp
// 5 tag types for iterator category, no members
struct input_iterator_tag {};
struct output_iterator_tag {};
struct forward_iterator_tag: public input_iterator_tag {};
struct bidirectional_iterator_tag: public forward_iterator_tag {};
struct random_access_iterator_tag: public bidirectional_iterator_tag {};


template <class I>
struct iterator_traits {
    typedef typename I::value_type          value_type;
    typedef typename I::difference_type     difference_type;
    typedef typename I::pointer             pointer;
    typedef typename I::reference           reference;
    typedef typename I::iterator_categoty   iterator_categoty;
};

template <class T> 
struct iterator_traits<T*> {    // template patrial specialization
    typedef T                           value_type;
    typedef ptrdiff_t                   difference_type;
    typedef T*                          pointer;
    typedef T&                          reference;
    typedef random_access_iterator_tag  iterator_category;
};

template <class T>
struct iterator_traits<const T*> {  // template patrial specialization
    typedef T                           value_type;
    typedef ptrdiff_t                   difference_type;
    typedef const T*                    pointer;
    typedef const T&                    reference;
    typedef random_access_iterator_tag  iterator_category;

}
```
- iterator_traits: 迭代器特性 
- type_traits: 类型特性
- 如果class中有指针成员并对齐动态分配内存，则需要实现no-trivial default ctor/copy ctor/assignment op=/destructor
- 设计泛型算法时需要根据类类型采用最有效的构造/拷贝/析构操作


## 第4章 序列式容器
- 任何数据结构(data structures)都是为了实现某种特性的算法(algorithms)
- 常用数据结构: array, list, tree, stack, queue, hash table, set, map...
- 数据结构的分类（根据数据在容器中的排列方式）
    - 序列式(sequence)
    - 关联式(associate)
- 序列式容器(sequencial containers)中的元素可排序(ordered)，但未必有序(sorted)
- stack和queue是deque的适配器
- set/map/mulitset/multimap内部含有RB-tree，RB-tree未公开
- 迭代器解引用和成员访问运算符的重载
    - reference operator*() const;
    - pointer operator->() const { return &(operator*());};
- deque逻辑上是一段连续空间，内存上是分段连续空间
- deque的中控器中存储多个缓冲区的指针，缓冲区是连续线性空间
- deque的iterator拥有指向deque中控器和当前元素及其所在缓冲区的指针
- stack, queue, heap, priority_queue没有迭代器，不允许遍历行为
#### heap
- heap不属于STL容器组件，binary max heap是priority queue的底层机制
- heap property: if P is a parent node of C, then the key of P is either greater/less than or equal to the key of C in a max/min heap
- 优先队列不是队列而是树
- 二叉堆(binary heap)是一种完全二叉树(complete binary tree)
- complete binary tree可以用array存储所有节点
    - 某节点位于i处时，左子结点2i，右子节点2i-1，父节点int(i/2)
- binary heap的push和pop: precolate up/down
    - push: 新增的元素放在array尾部，不断上升
    - pop: 交换array首尾元素，array尾的元素放在array首，不断下降
- sort_heap算法：不断对heap执行pop操作
- make_heap算法：不断对heap执行push操作
- priority_queue的底层是max_heap, max_heap的底层是vector

## 第5章 关联式容器
- RB-tree(red-black tree, 红黑树)
- 关联式容器没有头尾，每个元素都有key和value
- balanced binary tree(平衡二叉树)
    - AVL-tree
    - RB-tree
    - AA-tree
- 树(tree)由节点(node)和边(edge)构成
- binary search tree(二叉搜索树)
    - 任何节点的键值大于其左子树每个节点的键值，小于其右子数每个节点的键值
    - 对数时间的元素访问和插入
    - 最小key节点: 从root开始，持续向左
    - 最大key节点: 从root开始，持续向右
    - 插入node: 从root开始，遇大key向左，遇小key向右，直至leaf
    - 删除node: 
        - 若被删node没有子节点，直接删除
        - 若被删node有1个子节点，子节点连接至被删node的父节点
        - 若被删node有2个子节点，子节点右子树的最小key节点取代子节点
- balanced binary search tree(平衡二叉搜索树)
    - 平衡：没有任何一个节点过深
    - 不同的平衡条件造成不同的效率表现
    - AVL-tree, RB-tree, AA-tree均可实现平衡二叉搜索树
- AVL tree(Adelson-Velskii-Landis tree)
    - 平衡条件：任何节点的左右子树高度相差不超过1
    - 外侧插入：单旋转，插入点位于左子结点的左子树或右子节点的右子树
    - 内侧插入：双旋转，插入点位于左子结点的右子树或右子节点的左子树
- RB tree(红黑树)的规则
    - 节点非红即黑
    - 根节点为黑色
    - 红节点的子节点为黑 ==> 新增节点的父节点必定为黑
    - 任一节点至树尾端的任意路径所含黑节点数相等 ==> 新增节点必定为红
- 当新节点根据二叉搜索树的规则到达插入点，却不符合红黑树的规则，则调整颜色并转换树形
- 叶结点不一定为红：调整后的叶结点可以为黑
- map以RB-tree为底层机制
- hast_set, hash_map, hast_multiset, hash_multimap以hash_table为底层机制
- multimap允许key重复
- hash table
    - 字典结构(directionary)
    - O(1)的增删改查
    - 散列函数(hash function)
    - 碰撞(collision): 不同元素的hash值相等
    - 负载系数(loading factor): 元素个数除以表格大小
- 解决hash table碰撞问题的方法
    - 线性探测(linear probing): 向下寻找，直到找到可用空间
    - 二次探测(quadratic probing): F(i) = i^2
    - 开链(separate chaining): 每个表格元素中维护一个list
- 散列：将任意长的输入通过散列算法变换成固定长度的输出
    - 散列变换是一种压缩映射
    - 输出的结果为散列值
    - 不同的输入可能有相同的散列值输出
    - 将任意长度的消息压缩为某一固定长度的消息摘要
    
## 第6章 算法
- 算法：以有限的步骤解决数学或逻辑上的问题
- 算法分析：算法消耗的资源，包括空间和时间
- STL算法作用于iterator表示的[first, last)曲线
- mutating alogrithms: 运算过程改变区间内元素
- nonmutating algorithms: 不改变操作对象的值
- 有些算法接收factor作为参数，改变其策略: _if
- 有些mutating algorithms提供in-place版和copy版: _copy
- 数值算法(numeric algorithms)
- copy为了效率有多个泛化、特化、强化(generalization, specialization, refinement)版本
    - raw pointers, trivial assignment operator
    - memcpy, memmove, n循环，迭代器循环
- set算法
    - 并集(union)
    - 交集(intersection)
    - 差集(difference)
    - 对称差集(symmetric difference)
- set算法要求有序区间(sorted range)，允许元素重复出现
- heap算法
    - make_heap
    - pop_heap
    - push_heap
    - sort_heap
## 第7章 仿函数(factors)
- 函数对象(function objects)
- function objects能被function adapter修饰，多个串联
- function objects的分类
    - 算术(arithmetic)
    - 关系运算(relational)
    - 逻辑运算(logical)


## 第8章 配接器
- adapter: 改变class的接口，使接口不兼容的classes能相互协作
    - function adapter: 改变函数对象的接口
    - container adapter: 改变容器的接口 
    - iterator adapter: 改变迭代器的接口
- insert iterators: 将赋值(assign)操作转变为插入(insert)操作
    - front_insert_iterator ==> front_inserter()
    - back_insert_iterator ==> back_inserter()
    - insert_iterator ==> inserter()
- reverse iterators: 逆向迭代器
- iostream iterators: 将迭代器绑定到iostream对象上
- function adapters能无限制的创造任何表达式
- insert iterator内部维护一个容器，insert interator的operator=调用转换为内部容器的push_front, push_back或insert操作函数，表面上是赋值实际上是插入
- insert iterator的operator ++, --, *, ->无意义
- insert iterator的categoty为ouput iterator
- 逆向迭代器内部维护一个正向迭代器，同样的地址指向相邻的对象、
    - 正向迭代器指向对象的低地址边缘
    - 逆向迭代器指向的对象高地址边缘
    - *reverse_iterator ==> *--iterator
    - ++reverse_iterator ==> --iterator
    - --reverse_iterator ==> ++iterator
- iostream iterator内部维护一个iostream对象
    - iterator operator++ ==> iostream operator>>, read
    - iterator operator-- ==> iostream operator<<, write
- std::mem_fn比std::function轻量，前者是指针的简单包装，后者使用类型擦除
- type erasure: 类型擦除，泛型编程执行期不包含类型信息
- type inference: 类型推断，程序自动推断数据类型
- virtual member function object与泛型算法可以实现多态
- stl containters只支持value semantic, 不支持reference semantic, 元素类型不可为引用

## End
- C++支持的编程思维
    - 面向对象编程
    - 泛型编程
