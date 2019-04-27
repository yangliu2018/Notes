# 机器学习
### 周志华

# 第一章 绪论

## 基本术语
- 数据集 data set
- 示例/样本 instance/sample
- 属性/特征 attribute/feature
- 属性值 attribute value
- 属性空间/样本空间 attribute space/sample space
- 特征向量 feature vector
- 维数 dimensionality
- 学习/训练 learning/training
- 训练数据 training data
- 训练样本 training sample
- 训练集 training set
- 假设 hypothesis
- 预测 prediction
- 标签 label
- 样例 example
- 标记空间 label space
- 分类 classification
- 回归 regression
- 聚类 clustering
- 簇 cluster
- 监督学习 supervised learning
- 无监督学习 unsupervised learning
- 泛化 generalization
- 分布 distribution
- 独立同分布 independent and identically distrubuted
- 学习算法：数据 -> 模型

## 假设空间
- 归纳 induction: 从特殊到一般的泛化 generalization
- 演绎 deduction: 从一般到特殊的特化 specialization
- 归纳学习 induction learning
- 概念学习 concept learning
- 机械学习：死记硬背的记住训练样本
- 学习：在假设空间(hypothesis space)中搜索与训练集匹配(fit)的假设
- 版本空间 version space: 与训练集一致的假设集合

## 归纳偏好
- inductive bias
- 特征选择 feature selection
- 奥卡姆剃刀 Occam's razor: 若有多个假设与观察一致，则选最简单的那个
- 没有免费午餐定理 No Free Lunch Theorem

## 应用现状
- 科学研究的基本手段：理论+实验+计算
- 数据科学
- 大数据时代的三大关键技术：机器学习，云计算，众包(crowdsourcing)
- 数据挖掘 data mining
- 数据挖掘：数据的管理和分析
    - 数据库：数据的管理
    - 机器学习：数据的分析

# 第二章 模型评估与选择

# 第三章 线性模型

## 基本形式
- line model
- $f(\vec{x}) = \vec{w}^T \vec{x} + b$
- 许多功能强大的非线性模型可在线性模型的基础上引入层级结构或高维映射而得
- comprehensibility/understandability

## 线性回归
- line regression
- data set: $D = \{(\vec{x_1}, y_1), (\vec{x_2}, y_2), ..., (\vec{x_m}, y_m)\}, \vec{x_i} = (x_{i1}, ..., x_{id}), y_i \in R$
- 线性回归：学习一个尽可能准确地预测实值输出标记的线性模型
- 有序离散属性的连续化：标量
- 无序离散属性的连续化将引入序关系，可能误导后续处理
- 无序离散属性的向量化：两两垂直的单位向量
- 最小化均方误差/平方损失
- 欧式距离
- 最小二乘法：基于均方误差最小化的进行模型求解的方法
- least square method的使用不限于linear regression
```math
f(x) = w x + b \\

E(w, b) = \sum_{i = 1}^m (y_i - w x_i - b)^2 \\

\begin{cases}
\frac {\partial E} {\partial b} = 0  \implies w \bar x + b = \bar y \\
\frac {\partial E} {\partial w} = 0 \implies w {\bar x}^2 + b \bar x = \bar {xy} \\
\end{cases}

\implies

\begin{cases}
w = \frac {\bar{xy} - \bar x \bar y} {\bar {x^2} - \bar x ^2 } \\
b = \bar y - w \bar x
\end{cases}

```
- log linear regression: $lny = \vec w ^ T \vec x + b$
- $ y = e ^ {\vec w ^ T \vec x + b}$
- generalized linear regression: $y = g^{-1}(\vec w ^ T \vec x + b)$, link function g

## 对数几率回归
- uint-step function
```math
y = 
\begin{cases}
0, z < 0 \\
0.5, z = 0 \\
1, z > 0
\end{cases}
```
- logistic function 对数几率函数 $y = \frac 1 {1+ e ^ {-z}}$
- sogmoid function: S形的函数，将实数值转化为接近0和1的值
- 对数几率回归是一种分类学习方法
- 极大似然法 maximun likelihood method

## 线性判别分析
- LDA, linear discriminant analysis

## 多分类学习
- 拆解法：将多分类任务拆解为若干二分类任务求解，然后对多个分类器集成
- 拆分策略
    - OvO: one vs. one, n(n - 1)/2 classifiers
    - OvR: one vs. rest, n classifiers
    - MvM: many vs. many, ECCO

## 类别不平衡问题
- class imbalance