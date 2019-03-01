
### 1. 依赖关系(Dependence)
### 2. 泛化关系(Generalization)
### 3. 关联关系(Association)
### 4. 聚合关系(Aggregation)
### 5. 组合关系(Composition)
### 6. 实现关系(Implementation)

### 1. 依赖关系(Dependence)
A类的变化引起B类的变化
虚线+箭头
A类的对象是B类方法的局部变量
A类的对象是B类方法的参数
A类向B类发送消息，影响B类发生变化

### 2. 泛化关系(Generalization)
继承关系，父类是子类的泛化
实线+空心三角


### 3. 关联关系(Association)
类之间的联系
实线
通常将一个类作为另一个类的属性
双向关联
单向关联
自关联
多重关联


### 4. 聚合关系(Aggregation)
整体和部分的关系，整体和部分可以分开
成员对象是整体对象的一部分，但成员对象可以脱离整体对象独立存在
直线+空心菱形


### 5. 组合关系(Composition)
整体与部分的关系，整体和部分不可以分开
成员类是整体类的一部分，整体类可以控制成员类的生命周期

### 6. 实现关系(Implementation)

|relationship|line|arrow|remark|
|-|-|-|-|
|泛化(generalization)   |实线   |空心三角   |继承非抽象类 |
|实现(relization)   |虚线   |空心三角   |继承抽象类   |
|聚合(aggregation)   |实线   |空心菱形   |整体由部分组成，弱依赖   |
|组合(composition)   |实线   |实心菱形   |整体由部分组成，强依赖   |
|关联(association)   |实线   |箭头/无   |非运行期，静态，成员变量   |
|依赖(dependency)   |虚线   |箭头   |运行期，临时性，方法入参   |
