# Generic-Algorithms
遗传算法

用遗传算法解决旅行商问题，提供基本的实现源码并附带数据集att48.
本框架优点是“绿色”,这是就其空间利用效率较高。程序运行需要三的层次的空间：
一，初始种群空间和子代种群空间、主要开销用于存放父代个体和子代个体的引用变量，规模为O(scale)
在演化过程中，这个空间始终是复用的。
二，所有个体的空间，主要开销用于存放每个个体的染色体——这里用顺序表实现。这个空间也是事先就开辟而后复用的，
规模为O(scale * numberOfCities),也是由于实现开辟好每条染色体空间的缘故，在构造新个体时并不涉及顺序表的
复制。与此同时，这里也不涉及顺序表内元素的移动。总的来说，其操作效率将不逊于使用二维数组建模。
三，存放基因——这里用整数的包装类表示——的开销。这些是在初始化种群生成的。而在杂交、变异的过程中，只是调整
染色体上每个基因引用的对象，并不用产生新的基因对象。也因此不会有临时的数组垃圾，这也正是说其“绿色”的
由来。最后，这层空间规模为O(scale * numberOfCities)

TSP类里提供基本框架，通过继承该类，用户可以很方便地构造、测试自己的遗传算法。
在此框架基础上，你可以提供自己设计的改进的遗传算法方案以及测试结果。

欢迎讨论！
