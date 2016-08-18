# NOTES

标签（空格分隔）： AlgorithmsPractice

---

# LeetCode Notes

### P6 Zigzag
一层循环：每个Unit。注：一长一短为一个单元，包含numRows + (numRows-2)个字符
二层循环：A. 该unit的长列；B. 该unit的短列

WA原因：
1. 当numRows为1时，计算unitLen = numRows + (numRows-2)得0，除数为零运行错误。
2. 题目理解，对“之”字形没理解对
3. 处理末尾自负个数不足unitLen得情形，考虑不周，可能出现数组访问越界。
4. 整数运算结果取浮点数：3/5得0.0，1.0*3/5才得0.6

### P7 Reverse Integer
1. 建议获取逆序字符串时，for循环的判断条件设为`i <= j`。原因：考虑参数x = 0的情形，这时若判断条件为`i < j`，则逆序字符序列`rseq`会为空值运行报异常。
2. 数据溢出问题

### P8 String to Integer (atoi)
Lessons
1. 用整型表示符号位，便于后期相乘相加
2. 用预先定义的ans变量存结果，避免调用Long.parseLong()抛异常导致没有结果可返回
3. 处理溢出：可以限制接受的字符位数（int类型最长占10位，加符号位1位，不会超过11位）
4. 读取字符过程中自己计算结果，比调用Long.parseLong()快很多（为啥）

Bugs

根据str得到validStr的过程，涉及几个if...else...屡屡出错。

WAcases

Input: "+", "+-2", "-2147483648"

### P26 Remove Duplicates from Sorted Array
1. 两根指针。一个指针(下标)遍历数组，另一个指针(下标)负责将不重复的数置于原数组的正确位置。

2. 很简单，但出现编译错误——
```
int ans;
for (int i = 1, ans = i; ...) {...}
```

### P28 strStr
在源字符串source中查找子串target。
1. 双重for循环
2. KMP

### P38 Count and Say （找第n个数的字符串表示）
表示规则：对于连续字符串，表示为重复次数+数本身。

#### 方法一：直接遍历
模拟手工操作，扫描字符串，为`每个单元`计数、输出即可（每个单元：如"111221"包括"111"、"22"、"1"共三个单元）。

#### 方法二：递归 
`// TODO`

### P41 First Missing Positive
方法：
1. 使用**类似桶排序**的方法：将值放在它应该在的位置
2. 再扫描一次得出哪个位置有缺少值。

### P49 Group Anagrams （变位词）
寻找`字符串数组`中的变位词，基础版是判断`两个字符串`是否变位词。

#### 方法一：双重for循环（判断两两之间是否互为变位字符串，TLE）
1. strs长度小于等于1时，直接返回。
2. 使用与 strs 等长的布尔数组，表示某字符串是否已被添加到最终的返回结果中。
3. **双重遍历strs，注意避免重复添加。**
4. 私有方法`isAnagrams`用于判断两个字符串是否互为变位词（hashmap 统计字频）。

时间复杂度：`isAnagrams`最坏为O(2L)，双重for循环为O(n2)，整体为O(Ln2)。


#### 方法二：排序＋hashmap
（这也是一开始的思路）
1. 遍历字符串数组strs，建立key为字符串、value为相应变位词集的hashmap。
1. Java 中对 String 排序可先将其转换为 char[], 排序后再转换为新的 String
```
	char[] strChar = str.toCharArray();
	Arrays.sort(strChar);
	String strSorted = String.valueOf(strChar);
```

时间复杂度：遍历字符串数组，需O(n)，对单个字符串排序O(LlogL)，整体为O(nLlogL)。


### P58 Length of Last Word
1. 不仅需要考虑`s == null || s.length() == 0`的情况，还要考虑s只包含空格的情况，所以首先要`trim()`去除头尾的空格。

### P125 Valid Palindrome
两步走：
1. 找到最左边和最右边的下一个合法字符（字母或数字）
2. 一致转换为小写进行比较

字符的判断尽量使用语言提供的API。

### P217 Contains Duplicate 数组元素查重
利用HashTable查找的O(1)特性。遍历数组元素，每次插入集合，若不成功，则return true；全部插入成功，返回false。

### P378 Kth Smallest Element in a Sorted Matrix
在行列均排好序的矩阵中找第k小的数字，难度Medium。

#### 方法一：大顶堆解决第K小问题
用大根堆来存储当前最小的k个数字——遍历矩阵，如果遍历位置数字大于等于堆顶元素，跳过该行继续遍历，否则将数字存入堆中并删除堆顶数字（保证堆中有k个数字）。最终堆顶数字即是第k小的数字。
参考：[基于堆实现的优先级队列：PriorityQueue 解决 Top K 问题](http://my.oschina.net/leejun2005/blog/135085)

#### 方法二：小根堆
逐次取出最小的数字来找出最终结果。
1. 将第一行元素存入小根堆，最小元素必定在堆顶（matrix[0][0]）
2. 然后删除堆顶数字并用它同列的下一数字代替，这样当前最小的数字依然在堆顶
3. 遍历k次后即可得到第k小的数字
4. 由于需要记录数字的坐标，这里用内部类包含了数字值和两个坐标。内部类要实现Comparable接口
1. 注意：`Comparable`接口实现，编译提示参数类型错误。正确写法应在类生命中`使用范型`
```
    private class Node implements Comparable<Node> {
		……
    	
		@Override
		public int compareTo(Node o) {
			return this.val - o.val;
		}
    }
```

#### 方法三：
二分查找。在二分查找循环中，统计矩阵中小于等于中间值的数字个数，拿它和k比较来确定第k小的数字在左半部分还是右半部分。


--------

# LintCode notes

### P31 Array Partitioning
方法一：自左向右
用下标`i`遍历数组`nums`，同时用下标`right`保存分界点（>=k的索引）。遍历结束，`right`为所求结果。

方法二：两根指针
快排partition经典代码

```
	int left = 0, right = nums.length - 1;
	while (left <= right) {
		// 从左向右，直到找到 >=k 的索引为止
		while (left <= right && nums[left] < k) 
			++left;
		// 从右向左，直到找到 <k 的索引为止
		while (left <= right && nums[right] >= k)
			--right;
		// 注意进行越界检查！
		if (left <= right) {
			int tmp = nums[left];
			nums[left] = nums[right];
			nums[right] = tmp;
			++left;
			--right;
		}
	}
	return left;
```

### P55 Compare Strings
问字符串A是否包含了字符串B中的所有字符，P158判断变位词的变形题。

先遍历 A 和 B 统计各字符出现的频次，然后比较频次大小即可。万能哈希表。

### P138 Subarray Sum
求元素和为零的子序列

方法一：两重循环

内层循环应从`int j = i`开始。

方法二：比较子串和(TLE)

使用`int curSum`保存到索引`i`处的累加和，`ArrayList sums`保存不同索引处的和。执行`sums.add(curSum)`之前先检查`curSum`是否为0，再检查`curSum`是否已经存在于`sums`中。**时间复杂度与方法一相同的！**根本原因在于`sums.indexOf(curSum)`操作的时间复杂度为线性。

与这种方法类似的有哈希表实现，哈希表的查找在理想情况下可认为是 O(1)。

方法三：哈希表

与方法二几乎一样，只是吧存储`sums`的数据结构从`ArrayList`换成了`HashMap`，查找更快。

### P158 Two Strings Are Anagrams
判断两字符串是否变位词。

方法一：hashmap 统计字频

对于`比较字符数量`的问题，常用方法为遍历两个字符串，统计各字符出现频次，看是否相等。有很多简单字符串类面试题都是此题的变形题。Python标准库直接支持`为hashable对象计数`。
1. 初始化含有256个字符的计数器数组。
1. 对字符串 s 中的字符，计数增1，字符串 t 中的字符减1，再遍历letterCount数组看有无非0值（若有则不是变位词）。

判断互为变位词
```
	final int alphabetNum = 256;
	int[] letterCnt = new int[alphabetNum];
	for (int i = 0; i < s.length(); i++) {
		++letterCnt[s.charAt(i)];
		--letterCnt[t.charAt(i)];
	}
	for (int i = 0; i < alphabetNum; i++) {
		if (letterCnt[i] != 0)  return false;
	}
	return true;
```

判断字符串A是否字符串B的父集
```
	final int alphabetNum = 26;
	int[] letterCnt = new int[alphabetNum];
	for (int i = 0; i &lt; A.length(); i++) {
		++letterCnt[A.charAt(i) - 'A'];
	}
	for (int i = 0; i &lt; B.length(); i++) {
		--letterCnt[B.charAt(i) - 'A'];
		if (letterCnt[B.charAt(i) - 'A'] < 0)   return false;
	}
	return true;
```

方法二：排序字符串
对字符串先排序，若排序后的字符串内容相同，则其互为变位词。

Bug
1. `++letterCnt[s.charAt(i)];`错写成了`++letterCnt[s.charAt(i) - 'a'];`，导致Runtime Error. （因为字符串中可能包含比‘a’小的字符，从而出现负数下标）。