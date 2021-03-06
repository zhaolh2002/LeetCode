目录：

- [二分查找](#二分查找)
- [简单的面试题](#简单的面试题)
- [三路快排思想的应用](#三路快排思想的应用)
- [双指针](#双指针)
  - [对撞指针](#对撞指针)
  - [滑动窗口](#滑动窗口)   
- [二维数组](#二维数组)
- [更多数组中的问题](#更多数组中的问题)

# 二分查找

二分搜索（英语：binary search），也称折半搜索（英语：half-interval search）、对数搜索（英语：logarithmic search），是一种在**有序数组**中查找某一特定元素的搜索算法。搜索过程从数组的中间元素开始，如果中间元素正好是要查找的元素，则搜索过程结束；如果某一特定元素大于或者小于中间元素，则在数组大于或小于中间元素的那一半中查找，而且跟开始一样从中间元素开始比较。如果在某一步骤数组为空，则代表找不到。这种搜索算法每一次比较都使**搜索范围缩小一半**。

<div align="center">
    <img src="../pict/array_01.png"/>
</div>

代码实现：

```java
public  int binarySearch(Comparable[] arr, int n, Comparable target){

        int l = 0, r = n - 1; // 在[l...r]的范围里寻找target
        while(l <= r){    // 当 l == r时,区间[l...r]依然是有效的
            int mid = l + (r - l) / 2;	//可以防止整型越界
            if(arr[mid].compareTo(target) == 0) return mid;
            if(target.compareTo(arr[mid]) > 0)
                l = mid + 1;  // target在[mid+1...r]中; [l...mid]一定没有target，则继续到左边搜索
            else    // target < arr[mid]
                r = mid - 1;  // target在[l...mid-1]中; [mid...r]一定没有target，则继续到右边搜索
        }

        return -1;
    }
```
相关题目：

## 704

二分查找

### 描述

给定一个 n 个元素**有序的（升序）**整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。


示例 1:

输入: nums = [-1,0,3,5,9,12], target = 9
输出: 4
解释: 9 出现在 nums 中并且下标为 4

示例 2:

输入: nums = [-1,0,3,5,9,12], target = 2
输出: -1
解释: 2 不存在 nums 中因此返回 -1


提示：

你可以假设 nums 中的所有元素是不重复的。
n 将在 [1, 10000]之间。
nums 的每个元素都将在 [-9999, 9999]之间。

### 分析

二分查找的实现
### 实现

```java
public int search(int[] nums, int target) {
        int l=0,h=nums.length-1;
        while (l<=h){
            int mid=(l+h)>>1;
            if (nums[mid]==target){
                return mid;
            }
            else if (nums[mid]<target){
                l=mid+1;
            }else {
                h=mid-1;
            }
        }
        return -1;
    }
```

## 34

在排序数组中查找元素的第一个和最后一个位置

### 描述

给定一个按照**升序排列**的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

你的算法时间复杂度必须是 O(log n) 级别。

如果数组中不存在目标值，返回 [-1, -1]。

示例 1:

输入: nums = [5,7,7,8,8,10], target = 8
输出: [3,4]

示例 2:

输入: nums = [5,7,7,8,8,10], target = 6
输出: [-1,-1]

### 分析

由于数组是升序排序，看到有序数组，就可以想到是否可以使用二分查找？

这里需要注意边界条件，处理target==nums[mid]的细节需要注意，下面举例说明：

nums={5,7,7,8,8,10}, target=8

firstOccurance: 尽量往左搜索，所以将target==nums[mid]的部分放在左移的部分

当l==h的时候，下一次循环必然是l>h，而此时确保了nums[l]=target, 即l为值等于target的**最靠左的下标**

l=0, h=5, mid=2 ,此时target（8）>nums[mid]（7）

l=2+1, h=5, mid=4,此时target（8）=nums[mid]（8）

l=3, h=4-1, mid=3,此时target（8）=nums[mid]（8）

l=3, h=3-1, 跳出循环，返回值为3

lastOccurance:尽量往右搜索，所以将target==nums[mid]的部分放在了右移搜索的部分

l=0, h=5, mid=2 ,此时target（8）>nums[mid]（7）

l=3, h=5, mid=4,此时target（8）=nums[mid]（8）

l=5, h=5, mid=5,此时target（8）<nums[mid]（10）

l=5, h=4, 此时跳出循环，返回值为4

### 实现

```java
public int[] searchRange(int[] nums, int target) {
        int[] res={-1,-1};
        if (nums.length==0||nums==null){
            return res;
        }
        int last = lastOccurance(nums, target);
        //当target比数组中的最小值还要小的时候
        if (last<0||nums[last]!=target){
            return res;
        }
        int first = firstOccurance(nums, target);
        res[0]=first;
        res[1]=last;
        return res;
    }
    //寻找target出现的第一个位置，
    private int firstOccurance(int[] nums,int target){
        int l=0,h=nums.length-1;
        while (l<=h){
            int mid=l+(h-l)/2;
            if (target>nums[mid]){
                l=mid+1;
            }else//target<=nums[mid]
            {
                h=mid-1;
            }
        }
        //此时h<l
        return l;
    }
    //寻找target出现的最后一个位置
    //注意一点，如果target比数组最小值还小，那么返回-1
    private int lastOccurance(int[] nums,int target){
        int l=0,h=nums.length-1;
        while (l<=h){
            int mid=l+(h-l)/2;
            if (target<nums[mid]){
                h=mid-1;
            }else //target>=nums[mid]
            {
                l=mid+1;
            }
        }
        //注意返回的是h，此时h<l
        return h;
    }
```
## 33*

搜索旋转排序数组

### 描述

假设按照升序排序的数组在预先未知的某个点上进行了旋转。

( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。

搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。

你可以假设数组中不存在重复的元素。

你的算法时间复杂度必须是 O(log n) 级别。

示例 1:

输入: nums = [4,5,6,7,0,1,2], target = 0
输出: 4

示例 2:

输入: nums = [4,5,6,7,0,1,2], target = 3
输出: -1

### 分析

涉及搜索排序数组中某个元素，就应该想到使用**二分搜索**。

由题干所知该数组为旋转排序数组，那么有一个特点：二分搜索的时候，必然存在一边是有序的，那么在有序的部分继续使用二分搜索，若不在此范围内则跳出该部分，继续循环，直到搜索到目标值或跳出循环为止。

### 实现

```java
public int search(int[] nums, int target) {
        if (nums==null||nums.length==0){
            return -1;
        }
        int l=0,h=nums.length-1;
        while (l<=h){
            int mid=l+(h-l)/2;
            if (nums[mid]==target){
                return mid;
            }
            //左半部分有序
            if (nums[l]<=nums[mid]){
                //如果目标值在这个范围内，注意要使用等于，因为边界值可能是目标值
                if (nums[l]<=target&&target<=nums[mid]){
                    h=mid-1;
                }else {
                    //目标值不在[l,mid]的区间内，跳出该搜索范围
                    l=mid+1;
                }
            }else //右半部分有序 
            {
                if (nums[mid]<=target&&target<=nums[h]){
                    l=mid+1;
                }else {
                    //目标值不在[mid,h]的区间内，跳出该搜索范围
                    h=mid-1;
                }
            }

        }

        return -1;
    }
```
## 81

搜索旋转排序数组(2)

### 描述

假设按照升序排序的数组在预先未知的某个点上进行了旋转。

( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。

编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。

示例 1:

输入: nums = [2,5,6,0,0,1,2], target = 0
输出: true

示例 2:

输入: nums = [2,5,6,0,0,1,2], target = 3
输出: false
进阶:

这是 搜索旋转排序数组 的延伸题目，本题中的 nums  **可能包含重复元素**。

这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？

### 分析

这里需要关注**重复元素的处理**，当nums[l]==nums[mid]的时候，证明nums[l,mid]之间都是相等的元素。此时需要将当前值继续放在搜索范围内，那么l=mid+1,会使得搜索忽略了边界值，而l=mid，会因为若当前的l和mid值相等的时候，使得程序进入死循环。此时需要继续向右搜索，则考虑使用 l=l+1。

### 实现

```java
public boolean search(int[] nums, int target) {
        if (nums==null||nums.length==0){
            return false;
        }
        int l=0,h=nums.length-1;
        while (l<=h){
            int mid = l+(h-l)/2;
            if (nums[mid]==target){
                return true;
            }
            //左半部分有序
            if (nums[l]<nums[mid]){
                //如果目标值在这个范围内，注意要使用等于，因为边界值可能是目标值
                if (nums[l]<=target&&target<=nums[mid]){
                    h=mid-1;
                }else {
                    l=mid+1;
                }
            }else if (nums[l]>nums[mid]){
                //右半部分有序
                if (nums[mid]<=target&&target<=nums[h]){
                    l=mid+1;
                }else {
                    h=mid-1;
                }
            }else{
                //nums[l]==nums[mid]，证明nums[l,mid]之间都是相等的元素
                //继续往右搜索,为了避免边界值无法被搜索到的情况
                l++;
            }
        }
        return false;
    }
```
# 简单的面试题

 ## 283

移动零

### 描述

 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

 示例:

 输入: [0,1,0,3,12]
 输出: [1,3,12,0,0]
 说明:

 必须在原数组上操作，不能拷贝额外的数组。
 尽量减少操作次数。
### 分析

 1. 解法1：

  利用额外的空间，首先遍历一遍数组将数组中的非零元素放入list中，再次遍历数组将list（集合）中的数字依次放入数组中，最后将数组的剩余部分补充0即可。

 2. 解法2：

 利用一个k来记录非零元素的下标，数组nums[0,...,k)表示非零元素的范围，**注意区间为左闭右开。**
 先遍历一遍数组，将非零元素依次放入前k个位置，再从k开始遍历数组，此时只需要填充0即可。

 3. 解法3：

 与解法2类似，在遍历的时候遇到非零元素则将其与k位置的元素交换，遇到零元素则直接跳过。
 这样操作以后，将nums[0,k)保证为非零元素，保证[0,k)中所有的非零元素都按照顺序排列在[0,k)中。
 这种解法只需要一次遍历数组，即可。

### 实现

解法1：

 ```java
    //时间复杂度：O(n)
    //空间复杂度：O(n)
    public void moveZeroes(int[] nums) {
        if (nums==null)
            return;
        List<Integer> temp=new ArrayList<>();
        for (int i=0;i<nums.length;i++){
            if (nums[i]!=0)
                temp.add(nums[i]);
        }
        for (int i=0;i<temp.size();i++){
            nums[i]=temp.get(i);
        }
        for (int i=temp.size();i<nums.length;i++){
            nums[i]=0;
        }

    }
 ```
解法2：
```java
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public void moveZeroes(int[] nums) {
        if (nums==null)
            return;
        int k=0;//k:表示[0,k)区间内均为非零元素
        //遍历数组，当遇到非零元素时，将其放入[0,k)期间
        //保证[0,k)中所有的非零元素都按照顺序排列在[0,k)中
        for (int i=0;i<nums.length;i++){
            if (nums[i]!=0)
                nums[k++]=nums[i];
        }
        for (int i=k;i<nums.length;i++){
            nums[i]=0;
        }



    }

```
解法3：
```java
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public void moveZeroes2(int[] nums) {
        if (nums==null)
            return;
        int k=0;//k:表示[0,k)区间内均为非零元素
        //遍历数组，当遇到非零元素时，将其放入[0,k)期间
        //保证[0,k)中所有的非零元素都按照顺序排列在[0,k)中
        //同时保证[k，i）为0
        for (int i=0;i<nums.length;i++){
            if (nums[i]!=0){
                if (k!=i){
                    int t=nums[i];
                    nums[i]=nums[k];
                    nums[k]=t;
                }
                k++;
            }
        }
    }

```
## 27

移除元素

### 描述

给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。

示例 1:

给定 nums = [3,2,2,3], val = 3,

函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。

你不需要考虑数组中超出新长度后面的元素。

### 分析

1. 解法1：

首先遍历一遍数组统计出不等于val元素的个数记为count;

然后通过一个双重循环，当搜索到待删除的元素的时候，从这个元素后面开始寻找**第一个不为val的元素**，将这两个元素交换位置，即可：

例如：nums=[3,2,2,3],val =3
当搜索到nums[0],此时为3，则交换位置得到[2,3,2,3]

当搜素到nums[1],此时为3，则交换位置得到[2,2,3,3]

当搜索到nums[2],此时为3，则交换位置得到[2,2,3,3]

当搜索到nums[3],此时为3，后面没有元素可以搜索了，则遍历结束。得到结果为[2,2,3,3]

这种解法其实是伪删除，只是将待删除的元素移动到数组的最后。

2. 解法2：

设置一个计数器count来记录不等于val的元素，遍历数组，只有当搜索的元素不等于val的时候，记录该元素。
这样确保了nums[0,count]均为不等于val的元素，并且保持相对顺序。

### 实现

解法1：

```java
    //时间复杂度：O(n^2)
    //空间复杂度：O(1)
    public int removeElement(int[] nums, int val) {
        if (nums==null||nums.length==0)
            return 0;
        int count=0;
        for (int i=0;i<nums.length;i++){
            if (nums[i]!=val)
                count++;
        }

        for (int i=0;i<nums.length;i++){
            if (nums[i]==val) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j]!=val){
                        int t=nums[i];
                        nums[i]=nums[j];
                        nums[j]=t;
                    }

                }
            }
        }
        return count;

    }
```
解法2：
```java
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public int removeElement(int[] nums, int val) {
        if (nums==null||nums.length==0)
            return 0;
        int count=0;
        //遍历数组，只记录不等于val的元素
        for (int i=0;i<nums.length;i++){
            if (nums[i]!=val)
               nums[count++]=nums[i];
        }

        return count;

    }
```

## 26

删除排序数组中的重复项

### 描述

给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

示例 1:

给定数组 nums = [1,1,2], 

函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 

你不需要考虑数组中超出新长度后面的元素。

示例 2:

给定 nums = [0,0,1,1,1,2,2,3,3,4],

函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。

你不需要考虑数组中超出新长度后面的元素。

说明:

为什么返回数值是整数，但输出的答案是数组呢?

请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。

你可以想象内部操作如下:

```java
// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
```

### 实现

```java
public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int count = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i])
                nums[++count] = nums[i];


        }
        return count+1;

    }
```

## 80

删除排序数组中的重复项（2）

### 描述

给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

示例 1:

给定 nums = [1,1,1,2,2,3],

函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。

你不需要考虑数组中超出新长度后面的元素。
示例 2:

给定 nums = [0,0,1,1,1,1,2,3,3],

函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。

你不需要考虑数组中超出新长度后面的元素。

### 实现

```java
    //利用增强for循环
    public int removeDuplicates(int[] nums) {
        if (nums!=null&&nums.length!=0){
            int count=0;
            for (int num:nums){
                if (count<2||num>nums[count-2])
                    nums[count++]=num;

            }
            return count;

        }
        else
            return 0;
    }
```

# 三路快排思想的应用

当数组中有大量元素重复出现的时候，普通的快排算法会退化为O(n^2)，这样我们可以考虑使用三路快排算法。
三路快排要做的事情，其实就是将数组分成三部分：小于v，等于v和大于v，之后递归的对小于v和大于v部分进行排序就好了。

<div align=center>

![](../pict/array_02.png)

```java
/**
 * 三向切分快排
 *
 * 在有大量重复元素的排序过程中效果很好
 *
 */
public class QuickSort3Way<T extends Comparable<T>> extends Sort<T> {

    public void sort(T[] a,int l,int h){
        if (h<=l)
            return;
        int lt=l,i=l+1,gt=h;//分别标注小于中位数的，等于中位数的，大于中位数的下标
        //partition
        T v = a[l];
        while (i<=gt){
            int cmp = a[i].compareTo(v);
            if (cmp<0){
               swap(a,lt++,i++);
            }else if (cmp>0){
                //需要注意这里的i是未遍历的元素，所以不需要加1操作
                swap(a,i,gt--);
            }else i++;
        }
        //现在a[l,lt-1]<v=a[lt,gt]<a[gt+1,h]
        sort(a,l,lt-1);
        sort(a,gt+1,h);

    }
    @Override
    public void sort(T[] nums) {
        sort(nums,0,nums.length-1);
    }


}
```



相关题目：
 * [75.颜色分类](#75)
 * [88.合并两个有序数组](#88)
 * [215.数组中的第K个最大元素](#215)

 ## 75

颜色分类（荷兰国旗问题）

### 描述

 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。

 注意:
 不能使用代码库中的排序函数来解决这道题。

 示例:

 输入: [2,0,2,1,1,0]
 输出: [0,0,1,1,2,2]

### 分析

 由于这道题目中需要排序的数字只有0、1和2三个数字，这说明待排序的数字中**存在着大量重复的数字**，这时可以考虑使用计数排序和三向快排。
 1. 计数排序
 首先，第一次遍历数组分别记录下三个数字的出现次数n0，n1和n2；接下来，就是用0 1 2这三个数字重新填充数组；
 [0,n0)填充0，[n0,n1+n0)填充1，[n1+n0，n0+n1+n2)填充2，即可。
  >注意：n0+n1+n2=n,n为数组长度
 2. 三向快排

- 首先，设置三个指针分别来指向0 1 2这三个数字。注意其初始值的设置，zero设置为-1，one设置为0，two设置为n, 因为nums[0,zero]=0，是左闭右闭的区间，则不能将初始值nums[0]设为0，nums[two,n-1]=2,与上同理；
- 接着，开始遍历数组，当遇到0时，将指向0的指针向前移动一位，并将这个0交换到该位置；当遇到1时，不做交换操作，继续遍历；当遇到2时，与0的时候同理，将指向2的指针后移一位，并将这个2交换到该位置，注意此时num[two-1]为未知元素，则交换以后指向1的指针不必向前移动。

### 实现

 1. 计数排序
 ```java
    //时间复杂度：O(n)
    //空间复杂度：O(1)
    public void sortColors(int[] nums) {
        int[] colors=new int[3];
        for (int i=0;i<nums.length;i++){
            if (nums[i]==0)
                colors[0]++;
            else if (nums[i]==1)
                colors[1]++;
            else if (nums[i]==2)
                colors[2]++;

        }
        for (int i=0;i<nums.length;i++){
            if (i<colors[0])
                nums[i]=0;
            else if (i>=colors[0]&&i<colors[0]+colors[1])
                nums[i]=1;
            else nums[i]=2;
        }

    }
 ```
 2. 三向快排
 ```java
    //使用三向快排的思想来实现
    public void sortColors(int[] nums) {
        int zero=-1,n=nums.length;            //nums[0,zero]=0,则不能将初始值nums[0]设为0
        int two=n;                            //nums[two,n-1]=2,与上同理
        int one=0;
        while (one<two){
            if (nums[one]==0&&zero<n-1){
                zero++;
                swap(nums,one++,zero);//num[zero+1]=0,one从下一个位置开始
            }
            else if (nums[one]==1){
                one++;
            }else {
                two--;
                swap(nums,two,one);//由于num[two-1]元素未知
            }
        }
    }

    private void swap(int[] a,int i,int j){
        int t=a[i];
        a[i]=a[j];
        a[j]=t;

    }
 ```

 ## 88

合并两个有序数组

### 描述

 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。

 说明:

 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 示例:

 输入:
 nums1 = [1,2,3,0,0,0], m = 3
 nums2 = [2,5,6],       n = 3

 输出: [1,2,2,3,5,6]

### 分析

 因为nums1有足够的空间来保存nums1和nums2中的所有元素，那么可以从nums1的n1+n2-1的**位置开始**填充元素；从后开始遍历两个数组，设置两个指针index1和index2分别指向nums1和nums2的元素尾部,num2取出较大的元素放入nums1的尾部，循环结束后， 如果index1和index2同时为0，则已经将全部元素排序好；

 如果index1为0，说明nums1中的元素已经排序完成，那么需要继续将num2的剩余元素填充到num1的相应位置中；

 如果index2为0，说明num2中的元素已经排序完成，那么剩余的nums1的元素保存初始位置即可。

### 实现

 ```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2.length==0||nums1.length==0)
            return;
        if (nums1.length==1)
            nums1[0]=nums2[0];

        int index1=m-1,index2=n-1;
        int last=m+n-1;


        while (index1>=0&&index2>=0){
            if (nums1[index1]<nums2[index2])
                nums1[last--]=nums2[index2--];
            else
                nums1[last--]=nums1[index1--];
        }
        //当index2<0时，说明nums2数组已经排序结束，剩余的nums1数组元素保持初始位置

        //当index1<0,则说明num1的元素都取完了，那剩下的num2的元素可一次全部写进nums1。
        while (index2>=0){
            nums1[last--]=nums2[index2--];
        }
    }
 ```
 ## 215

数组中的第K个最大元素

###  描述

在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

 示例 1:

 输入: [3,2,1,5,6,4] 和 k = 2
 输出: 5
 示例 2:

 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 输出: 4
 说明:

 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
### 分析

使用快排中的中的partition思想解决，因为partition返回值为有序数组的下标。首先将寻找第k大元素转化为寻找第（n-k）小元素，那么利用partition二分原数组，若当前的pos（partition的返回值）小于k的话，说明目标在右半部分，若大于，则说明目标在左边。若相等则说明已经搜索到目标。

### 实现

 ```java
public int findKthLargest(int[] nums, int k) {
        k=nums.length-k;
        int l=0,h=nums.length-1;
        while (l<h){
            int pos = partition(nums, l, h);
            if (pos>k)
                h=pos-1;	//右边
            else if (pos<k)
                l=pos+1;	//左边
            else break;		//搜索到目标

        }
        return nums[k];

    }

    private int partition(int[] nums,int l,int h){
        int i=l+1,j=h;
        int pivot=nums[l];
        while (true){
            while (nums[i]<pivot&&i!=h)
                i++;
            while (nums[j]>=pivot&&j!=l)
                j--;
            if (i>=j)
                break;
            swap(nums,i,j);

        }
        swap(nums,l,j);
        return j;

    }

    private void swap(int[] nums,int i,int j){
        int t=nums[i];
        nums[i]=nums[j];
        nums[j]=t;
    }
 ```
# 双指针
双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。
## 对撞指针
使用对撞指针的前提是**数组的有序的**，分别设置一个头指针和一个尾指针来遍历数组，当满足一定条件来分别移动两个指针的位置，最终完成任务。

相关题目：
 * [167.两数之和 II - 输入有序数组](#167)
 * [125.验证回文串](#125)
 * [344.反转字符串](#344)
 * [345.反转字符串中的元音字母](#345)
 * [11.盛最多水的容器](#11)

 ## 167

两数之和 II - 输入有序数组

### 描述

 给定一个已按照**升序排列 的有序**数组，找到两个数使得它们相加之和等于目标数。

 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。

 说明:

 返回的下标值（index1 和 index2）不是从零开始的。
 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
 示例:

 输入: numbers = [2, 7, 11, 15], target = 9
 输出: [1,2]
 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。

### 分析

 本题是经典的对撞指针的思想，由于数组**有序**，所以设置头尾指针。
 遍历数组，如果头尾指针的和等于target则记录下标；如果大于，这时需要减小，则将**尾指针向前移动**；如果小于，这时需要增加，则将**头指针向后移动**。 

### 实现

 ```java
public int[] twoSum(int[] numbers, int target) {
        int l=0,h=numbers.length-1;
        int[] res = new int[2];
        while (l<h){
            if (numbers[l]+numbers[h]==target)
            {
                res[0]=l+1;
                res[1]=h+1;
            }
            if (numbers[l]+numbers[h]<target)
                l++;
            else h--;
        }
        return res;

    }

 ```

 ## 125

验证回文串

### 描述

 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，**可以忽略字母的大小写**。

 说明：本题中，我们将空字符串定义为有效的回文串。

 示例 1:

 输入: "A man, a plan, a canal: Panama"
 输出: true

### 分析

 由于题目中规定“忽略字母的大小写”，那么首先将原字符串全部转化为大写或小写。

本可以使用对撞指针的思想，设置两个指针，一个头指针和一个尾指针。遍历字符串，若头尾指针相等的时候，则将头指针向后移动一位，将尾指针向前移动一位。若不相等则返回false, 当头尾指针指向同一个位置的时候，则为true。

 **注意：**

 > 由于只考虑字母和数字字符，所以指针的值需要做判断

 > 大小写字符的转化差值为32，如果用差值来判断会有bug  

### 实现

 ```java
public boolean isPalindrome(String s) {
        if (s==null||s.length()==0){
            return true;
        }
    	//将所有的字符都转化为小写
        char[] chars = s.toLowerCase().toCharArray();
        int l=0,h=chars.length-1;
        while (l<h){
            while (l<h&&!isNumApl(chars[l])) l++;
            while (l<h&&!isNumApl(chars[h])) h--;

            if (chars[l]==chars[h]){
                l++;
                h--;
            }else {
                return false;
            }
        }
        return true;
    }
    private boolean isNumApl(char c){
        if (c>='a'&&c<='z'){
            return true;
        }
        if (c>='A'&&c<='Z'){
            return true;
        }
        if (c>='0'&&c<='9'){
            return true;
        }
        return false;
    }

 ```
## 344

反转字符串

### 描述

编写一个函数，其作用是将输入的字符串反转过来。

### 分析

设置头尾指针，遍历的时候，将头尾指针两两交换，即可。
### 实现

```java
public String reverseString(String s) {
        if (s==null){
            return null;
        }
        char[] chars = s.toCharArray();
        int l=0,h=chars.length-1;
        while (l<h){
            char t=chars[l];
            chars[l]=chars[h];
            chars[h]=t;
            l++;
            h--;
        }
        return new String(chars);
    }
```
## 345

反转字符串中的元音字母

### 描述

编写一个函数，以字符串作为输入，反转该字符串中的**元音字母**。

### 分析

思路和[125](#125)题有些类似，同样设置对撞指针，一个头指针和一个尾指针，关键在于搜索的时候，**判断待搜索的字符是否为元音字母，**
如果为元音字母则交换头尾指针的值，与[344](#344)题中同理；如果不为元音字母，则继续遍历——头指针后移和尾指针前移。

>判断待搜索字符是否为元音，可以使用额外的空间，用一个频率数组存放元音字母的频率。搜索的时候，查询字符的下标对应的频率数组的数值，如果为1则表示为元音，反之则不是。

### 实现

```java
//时间复杂度O(n)
//空间复杂度O(1)
public String reverseVowels(String s) {
        char[] vowels={'a','e','i','o','u','A','E','I','O','U'};
        int[] freq=new int[256];
        for (char c:vowels) {
            freq[c]=1;
        }
        char[] chars = s.toCharArray();
        int l=0,h=chars.length-1;
        while (l<h){
            while (l<h&&freq[chars[l]]!=1)l++;
            while (l<h&&freq[chars[h]]!=1)h--;
            if (l<h){
                char t=chars[l];
                chars[l]=chars[h];
                chars[h]=t;
                l++;
                h--;
            }
        }
        return new String(chars);
    }
```
## 11

盛最多水的容器

### 描述

给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

说明：你不能倾斜容器，且 n 的值至少为 2。

<div align=center>

![](../pict/question_11.jpg)

图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。

### 分析

同样使用对撞指针，设置头尾指针和一个存放最大值的max，遍历数组，当前搜索范围的面积为头尾指针值的较小值乘以头尾指针下标的差值记作s。

每次遍历的时候，将数组较小的指针继续遍历，因为指针的值相当于长方形的高，**因为宽度在不断减小，那么尽量要让高尽量大才能取到最大值**。
每一次遍历记录当前搜索到的最大值，遍历结束便得到最大值。

### 实现

```java
//时间复杂度O（n）
//空间复杂度O（1）
public int maxArea(int[] height) {
        int l=0,h=height.length-1;
        int max=0;
        while (l<h){
            int s=Math.min(height[l],height[h])*(h-l);
            max=Math.max(max,s);
            if (height[l]<height[h])
            {
                l++;
            }else {
                h--;
            }
        }
        return max;
    }
```
## 滑动窗口
当求解的时候，需要获得数组或者字符串的**连续子部分**的时候，就可以考虑使用滑动窗口的思想。
num[l,h]为滑动窗口，根据具体的要求，通过遍历的时候，来改变l和h的大小，从而完成任务。
相关题目：

 * [209.长度最小的子数组](#209)
 * [3.无重复字符的最长子串](#3)
 * [438.找到字符串中所有字母异位词](#438)
 * [76.最小覆盖子串](#76)
 * [713.乘积小于K的子数组](#713)
 * [剑指Offer-和为 S 的连续正数序列](https://cyc2018.github.io/CS-Notes/#/notes/%E5%89%91%E6%8C%87%20Offer%20%E9%A2%98%E8%A7%A3%20-%2050~59?id=_572-%e5%92%8c%e4%b8%ba-s-%e7%9a%84%e8%bf%9e%e7%bb%ad%e6%ad%a3%e6%95%b0%e5%ba%8f%e5%88%97)

## 209*

长度最小的子数组

### 描述

给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。

示例: 

输入: s = 7, nums = [2,3,1,2,4,3]
输出: 2
解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
进阶:

如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。

### 分析

本题需要求解满足一定条件的**连续子数组**，那么可以使用滑动窗口的思想；

首先，定义一个滑动窗口num[l,h],由于可能存在不符合条件的结果，那么初始化的时候该滑动窗口的**大小为0**。
因为该数组的元素都是正整数，所以当小于s的时候，则需要扩大滑动窗口的大小；当大于或等于s的时候，则尽量减小滑动窗口中和的大小。直到遍历完该数组，则可以获得最小长度的滑动窗口。

**注意在遍历过程中记录滑动窗口的最小值**

### 实现

```java
public int minSubArrayLen(int s, int[] nums) {
        int i=0,j=-1;//滑动窗口num[i,j]，由于初始化的时候没有数值，所以j=-1
        int sum=0;
        int minCount=nums.length+1;
        while (i<nums.length){
            if (sum<s&&j<nums.length-1){
                sum+=nums[++j];
            }else {
                sum-=nums[i++];
            }
            if (sum>=s){
                //由于是nums[i,j]，前闭后闭区间
                minCount=Math.min(minCount,j-i+1);
            }
        }
    	//没有满足条件的子数组就是j-i+1,此时j=nums.length,i=0
        if (minCount==nums.length+1)
            return 0;
        return minCount;

    }
```
时间复杂度：O(n)

空间复杂度：O(1)

## 3*

无重复字符的最长子串

### 描述

给定一个字符串，找出不含有重复字符的最长子串的长度。

示例 1:

输入: "abcabcbb"
输出: 3 
解释: 无重复字符的最长子串是 "abc"，其长度为 3。
### 分析

因为要确保子串无重复的字符，那么需要一个额外的空间来记录每个字符出现的频率。这道题同样可以使用滑动窗口的方法来求解，当待搜索的字符未出现的时候，将其加入滑动窗口。
当该字符已经出现的时候，则将滑动窗口的第一个字符从中移除，移除以后将频率数组中该字符的频率减1。每次遍历的时候，记录滑动窗口大小的最大值。

判断一个字符是否出现在之前的滑动窗口中，利用一个频率数组记录每个字符出现的次数，当其值为0则表示没有出现，加入滑动窗口后需要将其频率加1。
>补充：ASCII码占用一个字节，可以有0～255共256个取值。
### 实现

```java
public int lengthOfLongestSubstring(String s) {
        int l=0,h=-1;//s[l,h]为滑动窗口
        int[] freq=new int[256];//用于记录字符出现的频率
        int count=0;//子字符串的长度
        while (l<s.length()){
            //注意h的范围
            if (h<s.length()-1&&freq[s.charAt(h+1)]==0){
                h++;
                freq[s.charAt(h)]++;
            }else {
                freq[s.charAt(l++)]--;
            }
            count=Math.max(count,h-l+1);
        }
        return count;

    }
```
时间复杂度：O(n)

空间复杂度：O(1)

## 438

找到字符串中所有字母异位词

### 描述

给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。

字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。

说明：

字母异位词指字母相同，但排列不同的字符串。
不考虑答案输出的顺序。
示例 1:

输入:
s: "cbaebabacd" p: "abc"

输出:
[0, 6]

解释:
起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。

### 分析

字母异位词表示该字符串中出现的**字符的次数相同。** 由于字符串中只包含小写英文字母，那么同样需要额外的空间来记录字符出现的频率。

首先，遍历字符串p来统计其中的字符频率。再开始遍历字符串s,此时的滑动窗口大小是**固定**的为p的长度，由于字母异位词的长度必定相等。
当滑动窗口中的频率数组与p的频率数组相同的时候则寻找到字母异位词，记录其起始下标即可。

>注意：当比较两个数组中的值是否对应相等的时候，可以通过 Arrays.equals() 方法比较数组中元素值是否相等。
### 实现

```java
public List<Integer> findAnagrams(String s, String p) {
        int[] pFreq=new int[26];
        int[] sFreq=new int[26];
        List<Integer> res=new ArrayList<Integer>();
        //首先统计p中出现的字符频率
        int pLen = p.length();
        for (int i=0;i<pLen;i++){
            pFreq[p.charAt(i)-'a']++;
        }
        for (int i=0;i<s.length();i++){
            sFreq[s.charAt(i)-'a']++;
            if (i>=pLen){
                sFreq[s.charAt(i-pLen)-'a']--;
            }
            if (Arrays.equals(sFreq,pFreq)){
                res.add(i-pLen+1);
            }

        }
        return res;

    }
```
时间复杂度：O(n)

空间复杂度：O(1)

## 76***

最小覆盖子串

### 描述

给定一个字符串 S 和一个字符串 T，请在 S 中找出包含 T 所有字母的**最小子串**。

示例：

输入: S = "ADOBECODEBANC", T = "ABC"
输出: "BANC"
说明：

如果 S 中不存这样的子串，则返回空字符串 ""。
如果 S 中存在这样的子串，我们保证它是唯一的答案。
### 分析

本题求解的是最小子串，子串是连续的，所以可以使用滑动窗口的思想来求解。此时的滑动窗口大小**至少为T的长度**，因为需要覆盖T。

设置一个total来记录搜索的窗口中包含T中字符的数量，当total等于T的长度的时候，则确保了此时的子串一定包含了T的所有字符。

设置一个l为记录子串的起始位置，每次尽量减小滑动窗口的大小，从滑动窗口的头开始移除字符，当移除的字符是T中所包含的字符的时候，则将total大小减小，并更新l的值。

由于此时的频率数组中，T中未包含的字符对应的值为非正数，当数组的值大于0的时候，则表示T中包含的字符。
### 实现

```java
public String minWindow(String s, String t) {
        int[] freq=new int[128];
        int sLen = s.length();
        int tLen = t.length();
        if (sLen==0||tLen==0)
            return "";
        int minLen=Integer.MAX_VALUE;
        //首先统计t中出现的字符的频率
        for (int i=0;i<t.length();i++){
            freq[t.charAt(i)]++;
        }
        int l=0;//起始位置
        int total=0;//记录当前包含的字符数量
        for (int i=0,j=0;i<sLen;i++){
            //当t中的字符出现的时候
            if (freq[s.charAt(i)]-->0)
                total++;
            //此时说明已经包含了t
            while (total==tLen){
                if (i-j+1<minLen){
                    minLen=i-j+1;
                    l=j;
                }
                //此时移除的是T包含中的字符
                if (++freq[s.charAt(j++)]>0)
                    total--;
            }

        }
        if (minLen==Integer.MAX_VALUE)
            return "";
        else
            return s.substring(l,l+minLen);

    }
```
时间复杂度：O(n)

空间复杂度：O(1)
## 713*

乘积小于K的子数组

### 描述

给定一个正整数数组 nums。

找出该数组内乘积小于 k 的**连续**的子数组的个数。

示例 1:

输入: nums = [10,5,2,6], k = 100
输出: 8
解释: 8个乘积小于100的子数组分别为: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]。
需要注意的是 [10,5,2] 并不是乘积小于100的子数组。
说明:

0 < nums.length <= 50000
0 < nums[i] < 1000
0 <= k < 10^6

### 分析

由于题目中是连续的子数组，那么可以考虑使用滑动窗口来解决。保证滑动窗口nums[l,r]的乘积小于k，若当前元素能够保证滑动窗口的乘积依然小于k，那么就将窗口右移。否则开始统计子数组的个数，另外注意到当长度为n的子数组满足乘积小于k时，显然该子数组的子数组也满足，故**包含nums[l]的长度为 r-l+1 的子数组个数即为 r-l +1**。

### 实现

```java
public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums==null||nums.length==0){
            return 0;
        }
        //滑动窗口是nums[l,r),初始情况为0
        int l=0,r=-1;//nums[l,r]
        int count=0;
        int cur=1;
        while(l<n){
            if(r<n-1&&cur*nums[r+1]<k){
                cur*=nums[++r];
            }else if(r>=l){
                count+=r-l+1;	//统计子数组的个数
                cur/=nums[l++];
            }else{
                //r>l
                r++;
                l++;             
            } 
        }
        return count;
    }
```
# 二维数组
相关题目：
* [54.螺旋矩阵](#54)
* [59.螺旋矩阵（2）](#59)
* [48.旋转图像](#48)
* [74.搜索二维矩阵（1）](#74)
* [240.搜索二维矩阵（2）](#240)
## 54.螺旋矩阵
### 描述

给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。

示例 1:

输入:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
输出: [1,2,3,6,9,8,7,4,5]
示例 2:

输入:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
输出: [1,2,3,4,8,12,11,10,9,5,6,7]
### 分析

按照顺时针的顺序，构造一个方向数组来创建新的坐标（newX，newY），当这个新的坐标没有被遍历并且
是有效的就将该坐标下的数值记录到填放结果的集合中，**每次遍历都沿一个方向遍历直到无法遍历才转换方向，转换方向沿顺时针转换（右，下，左，上）**，当结果的集合的大小为m*n的时候跳出循环，此时表明矩阵已经遍历结束了。

### 实现

```java
	//右，下，左，上这样的顺时针方向
	int[][] d={{0,1},{1,0},{0,-1},{-1,0}};
    private int N,M;
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res=new ArrayList<>();
        int m=matrix.length;
        if (m==0){
            return res;
        }
        int n=matrix[0].length;
        M=m;
        N=n;
        boolean[][] visited=new boolean[m][n];
        int curX=0,curY=0,curD=0;
        while (res.size()<m*n){
            if (!visited[curX][curY]){
                res.add(matrix[curX][curY]);
                visited[curX][curY]=true;
            }
            int newX = curX + d[curD][0];
            int newY = curY + d[curD][1];
      		//每次遍历都沿一个方向搜索直到无法遍历为止
            if (inArea(newX,newY)&&!visited[newX][newY]){
                curX=newX;
                curY=newY;
            }else {
                //无法遍历就转换方向
                curD=(curD+1)%4;
            }
        }
        return res;

    }

    private boolean inArea(int x,int y){
        return 0<=x&&x<M&&0<=y&&y<N;
    }
```
## 59.螺旋矩阵（2）
### 描述

给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。

示例:

输入: 3
输出:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]

### 分析

和[54题](#54)，是相同的思路，不再赘述。

### 实现

```java
	int[][] d={{0,1},{1,0},{0,-1},{-1,0}};
    private int M,N;
    public int[][] generateMatrix(int n) {
        int[][] matrix=new int[n][n];
        int curX=0,curY=0,curD=0;
        int i=1;
        M=n;
        N=n;
        while (i<=n*n){
            if (matrix[curX][curY]==0){
                matrix[curX][curY]=i++;
            }
            int newX = curX + d[curD][0];
            int newY = curY + d[curD][1];
            if (inArea(newX,newY)&&matrix[newX][newY]==0){
                curX=newX;
                curY=newY;
            }else {
                curD=(curD+1)%4;
            }
        }
        return matrix;
    }

    private boolean inArea(int x,int y){
        return 0<=x&&x<M&&0<=y&&y<N;
    }
```

## 48.旋转图像

### 描述

给定一个 n × n 的二维矩阵表示一个图像。

将图像顺时针旋转 90 度。

说明：

你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。

示例 1:

给定 matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
示例 2:

给定 matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

原地旋转输入矩阵，使其变为:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
### 分析

首先上下旋转；然后在按左对角线交换

转置+翻转

例如：

```java
  1 2 3     7 8 9     7 4 1
  4 5 6  => 4 5 6  => 8 5 2
  7 8 9     1 2 3     9 6 3
```
### 实现

```java
public void rotate(Integer[][] matrix) {
        int n=matrix.length;
        //先上下交换（翻转）
        for (int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int t=matrix[i][j];
                matrix[i][j]=matrix[n-1-i][j];
                matrix[n-1-i][j]=t;
            }
        }

        //按对角线对称交换（转置）
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j< n; j++) {
                int t=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=t;
            }
        }
    }
```
## 74.搜索二维矩阵（1）

### 描述

编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：

每行中的整数从左到右按升序排列。
每行的第一个整数大于前一行的最后一个整数。

示例 1:

输入:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 3
输出: true

示例 2:

输入:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 13
输出: false

### 分析

这一题关键在于设置初始位置，使得在搜索的过程中**能够根据当前值来确定下一个需要的搜索位置。**

本题初始值可以设在左下角或右下角

### 实现

```java
public boolean searchMatrix(int[][] matrix, int target) {
        int m=matrix.length;
        if(matrix==null||m==0){
            return false;
        }
        int n=matrix[0].length;
        int i=m-1,j=0;	//初始值设在左下角
    	//注意循环的条件
        while(i>=0&&j<n){
                if(matrix[i][j]==target){
                    return true;
                }else if(matrix[i][j]>target){
                    i--;
                }else{
                    j++;
                }
            }
        return false;
    }
```

## 240.搜索二维矩阵（2）

### 描述

编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：

每行的元素从左到右升序排列。
每列的元素从上到下升序排列。
示例:

现有矩阵 matrix 如下：
```java

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
```
给定 target = 5，返回 true。

给定 target = 20，返回 false。

### 分析

这一题关键在于设置初始位置，使得在搜索的过程中**能够根据当前值来确定下一个需要的搜索位置。**

本题将初始值设在左下角或右上角

### 实现

```java
//时间复杂度O(n)
public boolean searchMatrix(int[][] matrix, int target){
        int m = matrix.length;
        if (m==0){
            return false;
        }
        int n = matrix[0].length;
        //关键在于初始值的设置，
        int i=m-1,j=0;
        while (i>=0&&j<n){
            //搜索的当前值大于目标值的时候继续向上搜索
            if (matrix[i][j]>target){
                i--;
            }
            //搜素的当前值小于目标值的时候继续向右搜索
            else if (matrix[i][j]<target){
                j++;
            }
            //相等的时候则直接返回true
            else {
                return true;
            }
        }
        return false;
    }
```




# 更多数组中的问题

相关题目：
* [717.1比特与2比特字符](#717)
* [674.最长连续递增序列](#674)
* [268.缺失数字](#268)
* [56.合并区间](#56)
* [485.最大连续1的个数](#485)
* [4.寻找两个有序数组的中位数](#4)
* [169.众数](#169)
* [229.众数(2)](#229)
* [238.除自身以外数组的乘积](#238)
* [217.存在重复元素](#217)
* [42.接雨水](#42)
* [128.最长连续序列](#128)
* [69.X的平方根](#69)
* [581. 最短无序连续子数组](#581)

## 717

1比特与2比特字符

### 描述

有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。

现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。

示例 1:

输入: 
bits = [1, 0, 0]
输出: True
解释: 
唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
示例 2:

输入: 
bits = [1, 1, 1, 0]
输出: False
解释: 
唯一的编码方式是两比特字符和两比特字符。所以最后一个字符不是一比特字符。
注意:

1 <= len(bits) <= 1000.
bits[i] 总是0 或 1.
### 分析

- 思路1（使用贪心策略）：如果遇到1就将其当做2比特，若最后一位也当做2比特的话，i 等于 n-1；
- 思路2：只和最后一个元素前面连续1的个数有关，若是偶数则表明前面能够组成若干个2比特，若是奇数，则最后一个元素需要和倒数第二个元素组成2比特（10）。

### 实现

思路1：

```java
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = 0;
        for (; i < n-1; ) {
            if (bits[i]==1){
                i+=2;
            }else {
                i++;
            }
        }
        return i==n-1;
    }
```

思路2：

```java
   //从倒数第二个位置开始看连续1的数量，如果为奇数个表明最后一位0要和倒数第二位组成2比特（10）
    //如果是偶数个，则前面的这些1可以组成若干个2比特，最后一位组成1比特
    public boolean isOneBitCharacter1(int[] bits){
        int n = bits.length;
        int count=0;
        for (int i = n-2; i >=0; i--) {
            if (bits[i]==1){
                count++;
            }else {
                break;
            }
        }
        return count%2==0;
    }
```
## 674

最长连续递增序列

### 描述

给定一个未经排序的整数数组，找到最长且**连续**的的递增序列。

示例 1:

输入: [1,3,5,4,7]
输出: 3

解释: 最长连续递增序列是 [1,3,5], 长度为3。
尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。 

示例 2:

输入: [2,2,2,2,2]
输出: 1

解释: 最长连续递增序列是 [2], 长度为1。
注意：数组长度不会超过10000。
### 实现

```java
public int findLengthOfLCIS(int[] nums){
        if (nums==null||nums.length==0||nums.length==1){
            return nums.length;
        }
        int count=1;
        int max=0;
        for (int i = 1; i < nums.length; i++) {
            //确保是连续并且递增
            if (nums[i]>nums[i-1]){
                count++;
            }else {
                //重新计算
                count=1;
            }
            max=Math.max(max,count);
        }
        return max;

    }
```
## 268

缺失数字

### 描述

给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。

示例 1:

输入: [3,0,1]
输出: 2

示例 2:

输入: [9,6,4,2,3,5,7,0,1]
输出: 8

### 分析

- 使用额外空间：利用一个辅助数组记录元素是否出现

- 利用等差数列公式求解：**缺失的数字就是和预期结果之间的差**

### 实现

使用额外空间：

时间复杂度：O(n)

空间复杂度：O(0)

```java
public int missingNumber(int[] nums) {
        int n = nums.length;
        boolean[] flag=new boolean[n+1];
        for (int i = 0; i < n; i++) {
            flag[nums[i]]=true;
        }
        for (int i = 0; i <= n; i++) {
            if (!flag[i]){
                return i;
            }
        }
        return 0;
    }
```
使用等差数列公式：

时间复杂度：O(n)

空间复杂度：O(1)

```java
public int missingNumber(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        
        int n=nums.length;
    	//预期结果为等差数列：{1+2+...+n}的结果
        int res=n*(n+1)/2;
    	//缺失的数字就是和预期结果之间的差
        for(int i=0;i<n;i++){
            res-=nums[i];
        }
        return res;
    }
```

## 56*

合并区间

### 描述

给出一个区间的集合，请合并所有重叠的区间。

示例 1:

输入: [[1,3],[2,6],[8,10],[15,18]]
输出: [[1,6],[8,10],[15,18]]
解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2:

输入: [[1,4],[4,5]]
输出: [[1,5]]
解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。

### 实现

```java
public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        if (n<=1){
            return intervals;
        }
        //将区间集合按照起始点升序排列
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });
        LinkedList<int[]> list=new LinkedList<>();
        list.add(intervals[0]);
        for (int i = 1; i < n; i++) {
            if (isCross(intervals[i],list.getLast())){
                //先删除原区间
                int[] last = list.removeLast();
                int[] merge = merge(intervals[i], last);
                //再插入合并以后的区间
                list.add(merge);
            }else {
                list.add(intervals[i]);
            }
        }
        int[][] res = list.toArray(new int[list.size()][2]);
        return res;
    }

    //合并两个区间，起始点取最小值，终点取最大值
    private int[] merge(int[] interval1,int[] interval2){
        return new int[]{Math.min(interval1[0],interval2[0]),Math.max(interval1[1],interval2[1])};
    }
    //判断两个区间是否重叠
    private boolean isCross(int[] interval1,int[] interval2){
        return interval1[0]<=interval2[1];
    }
```
## 485

最大连续1的个数

### 描述

给定一个二进制数组， 计算其中最大连续1的个数。

示例 1:

输入: [1,1,0,1,1,1]
输出: 3
解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
注意：

输入的数组只包含 0 和1。
输入数组的长度是正整数，且不超过 10,000。

### 实现

```java
public int findMaxConsecutiveOnes(int[] nums){
        int n =nums.length;
        if (n==0){
            return 0;
        }
        int count=0;
        int max=0;
        for (int i = 0; i < n; i++) {
            if (nums[i]==1){
                count++;
            }else {
                max=Math.max(max,count);
                count=0;
            }
        }
        return max>count?max:count;
    }
```
## 4***

寻找两个有序数组的中位数

### 描述

给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。

请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。

你可以假设 nums1 和 nums2 不会同时为空。

示例 1:

nums1 = [1, 3]

nums2 = [2]

则中位数是 2.0

示例 2:

nums1 = [1, 2]

nums2 = [3, 4]

则中位数是 (2 + 3)/2 = 2.5

### 分析

解法1：

时间复杂度O(m+n)

因为操作的是**两个有序数组**，这里可以考虑使用归并排序。

归并排序得到的一个新的数组nums[]，再从nums[]中取中位数。

解法2：

时间复杂度O(log(m+n))

[参考](https://segmentfault.com/a/1190000013027222)

假设第k个数是我们要找的中位数，那么前k-1个数应该都比这个第k个数要小。后面的数都比这个第k个数大。（像变形的用二分法找第K个数）。

如果我们每次在nums1数组中找前(k/2) = m个数，在nums2数组中找剩下的(k-k/2) = n个数。

然后对a[p + k/2 - 1]和b[q + k - k/2 -1]进行比较，记为a[i]和b[j]。

a[i] < b[j]： 说明我们可以扔掉0-i之间的（i+ 1）个数。为什么？
**因为a数组中的前m个数的最大值都比b数组中的前n个数要小**，那么这前m个数一定是在我们想要的中位数之前的，并且对找到中位数没有说明影响。所以为了缩小搜索范围，我们可以扔掉这些数，在a的剩下来的数中和b的数组中接着找。

i>=a.length: 说明a中没有m个数可以寻找。

那么第K个数要么在b剩下的数组[n ~ b.length]中，要么就在a的前m个数中。

一直搜索到什么时候为止呢？

k=1代表的是，当前的这个是就是我们想要的值，我们应该在如何选择? Math.min(a[p], b[q]).

### 实现

解法1：

时间复杂度O(m+n)

```java
private int[] nums;
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double m;
        int n1 = nums1.length;
        int n2 = nums2.length;
        nums=new int[n1+n2];
        nums=merge(nums1,nums2);
        if ((n1+n2)%2!=0){
            //奇数的情况
            m=nums[(n1+n2)/2];
        }else {
            //偶数的情况
            m=((double) nums[(n1+n2)/2]+(double) nums[(n1+n2)/2-1])/2;
        }
        return m;
    }
    //归并排序--将两个已经排序好的数组合并为一个新的排序数组
    private int[] merge(int[] nums1,int[] nums2){
        if (nums1.length==0){
            return nums2;
        }
        if (nums2.length==0){
            return nums1;
        }
        int index1=0,index2=0,index=0;
        while (index1<nums1.length&&index2<nums2.length){
            if (nums1[index1]<nums2[index2]){
                nums[index++]=nums1[index1++];
            }else {
                nums[index++]=nums2[index2++];
            }
        }
        //若nums1排序结束，将nums2中其余元素放入nums中
        if (index1>=nums1.length){
            while (index2<nums2.length){
                nums[index++]=nums2[index2++];
            }
        }
        //同理将nums1中其余元素放入nums中
        if (index2>=nums2.length){
            while (index1<nums1.length){
                nums[index++]=nums1[index1++];
            }
        }
        return nums;
    }
```
解法2：

时间复杂度O(log(m+n))

```java
public double findMedianSortedArrays1(int[] nums1, int[] nums2){
        int n1 = nums1.length;
        int n2 = nums2.length;
        if ((n1+n2)%2==1){
            return findKnum(nums1,0,nums2,0,(n1+n2)/2+1);
        }else {
            return (findKnum(nums1,0,nums2,0,(n1+n2)/2)+findKnum(nums1,0,nums2,0,(n1+n2)/2+1))/2.0;
        }
    }
    private int findKnum(int[] nums1,int l1, int[] nums2,int l2,int k){
        //递归终止条件
        if (l1>=nums1.length){
            return nums2[l2+k-1];
        }
        if (l2>=nums2.length){
            return nums1[l1+k-1];
        }
        if (k==1){
            return Math.min(nums1[l1],nums2[l2]);
        }

        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;
            if (l1+k/2-1<nums1.length)
            {
            mid1 = nums1[l1+k/2-1];
            }
            if (l2+k/2-1<nums2.length)
            {
                    mid2 = nums2[l2+k/2-1];
                }
                if (mid1<mid2){
                    //舍弃nums1中前(k/2-1)个元素，因为中位数不可能在这部分
                    return findKnum(nums1,l1+k/2,nums2,l2,k-k/2);
                }else {
                    //舍弃nums2中前(k/2-1)个元素，因为中位数不可能在这部分
                    return findKnum(nums1,l1,nums2,l2+k/2,k-k/2);
                }
    }
```
## 摩尔投票算法

下面两题都可以使用[摩尔投票算法以及其拓展](https://www.zhihu.com/question/284969980)来解决

## 169*

求众数

### 描述

给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组**总是存在众数**。

示例 1:

输入: [3,2,3]
输出: 3
示例 2:

输入: [2,2,1,1,1,2,2]
输出: 2

### 实现

```java
///// Boyer-Moore Voting Algorithm(摩尔投票算法)
    public int majorityElement(int[] nums){
        if (nums.length==0||nums==null)
            return 0;
        int majortiy=nums[0];
        int count=1;
        //遍历数组找到出现次数最多的元素
        for (int i=1;i<nums.length;i++){
            if (nums[i]==majortiy)
                count++;
            else
                count--;
            //此时说明，数量最多的元素不存在或者不超过半数
            if (count==0){
                majortiy=nums[i];
                count=1;
            }

        }
        //将计数器清零
        count=0;
        //再次遍历数组，统计出现最多元素的数量
        for (int e:nums){
            if (e==majortiy){
                count++;
            }
        }
        if (count>nums.length/2)
            return majortiy;
        else return 0;

    }
```

## 229

众数(2)

### 描述

给定一个大小为 n 的数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。

说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1)。

示例 1:

输入: [3,2,3]
输出: [3]

示例 2:

输入: [1,1,1,3,3,2,2,2]
输出: [1,2]

### 实现

```java
public List<Integer> majorityElement(int[] nums){
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        if (n==0){
            return res;
        }
        //因为出现次数超过[n/2]的元素，最多只有2个
        int num1,num2,count1=0,count2=0;
        num1=num2=nums[0];
        //首先寻找这两个众数的候选数num1和num2
        for (int i:nums){
            if (i==num1){
                count1++;
            }else if (i==num2){
                count2++;
            }
            else if (count1==0){
                num1=i;
                count1++;
            }
            else if (count2==0){
                num2=i;
                count2++;
            }else {
                count1--;
                count2--;
            }
        }
        //再根据其次数确定是否为众数
        count1=count2=0;
        for (int i:nums){
            if (i==num1){
                count1++;
            }else if (i==num2){
                count2++;
            }
        }
        //将结果加入结果集合中
        if (count1>n/3){
            res.add(num1);
        }
        if (count2>n/3){
            res.add(num2);
        }
        return res;
    }
```
## 238

除自身以外数组的乘积

### 描述

给定长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。

示例:

输入: [1,2,3,4]

输出: [24,12,8,6]

**说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。**

进阶：
你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）

### 分析

使用两次循环，左右分别累乘。

### 实现

```java
	//时间复杂O（n）
    public int[] productExceptSelf(int[] nums){
        int n = nums.length;
        int[] res = new int[n];
        if (n==0){
            return res;
        }
        //从左向右直到i-1的位置
        res[0]=1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i-1] * nums[i-1];
        }
        //从右向左直到i+1的位置
        int right=1;    //从右开始的累乘积
        for (int i = n-1; i >=0; i--) {
            res[i] *=right;
            right *=nums[i];
        }
        return res;
    }
```
## 217

存在重复元素

### 描述

给定一个整数数组，判断是否存在重复元素。

如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。

示例 1:

输入: [1,2,3,1]
输出: true

示例 2:

输入: [1,2,3,4]
输出: false

示例 3:

输入: [1,1,1,3,3,4,3,2,4,2]
输出: true

### 分析

1. 使用set：时间复杂度O(N),空间复杂度O(N)；
2. 使用排序：时间复杂度O(NlgN),空间复杂度O(1)

### 实现

```java
public boolean containsDuplicate(int[] nums) {
        Set<Integer> record=new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (record.contains(nums[i])){
                return true;
            }else {
                record.add(nums[i]);
            }
        }
        return false;
    }
    
    //通过排序的方法
        public boolean containsDuplicate2(int[] nums){
            Arrays.sort(nums);
            for (int i = 1; i < nums.length; i++) {
                if (nums[i]==nums[i-1]){
                    return true;
                }
            }
            return false;
        }
```
## 42**

接雨水

### 描述

给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。


![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)

**上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。**

示例:

输入: [0,1,0,2,1,0,1,3,2,1,2,1]

输出: 6

### 分析

计算出每一列中左边最高， 右边最高的高度，每一列中包含水滴的个数即为两边最低的高度减去当前高度，最后将每一列包含的水滴累加起来即可。

### 实现

```java
public int trap(int[] height) {
        int n = height.length;
        if (n==0){
            return 0;
        }
        int[] left,right;
        left=new int[n];
        right=new int[n];
        //先初始化左边界
        for (int i = 1; i < n; i++) {
            left[i]=Math.max(height[i-1],left[i-1]);
        }
        //初始化右边界
        for (int i = n-2; i >=0 ; i--) {
            right[i]=Math.max(height[i+1],right[i+1]);
        }
        int water=0;
        //计算雨水量，计算height[1,n-2]的范围，因为两边不能累积雨水
        for (int i = 1; i < n-1; i++) {
            int h=Math.min(left[i],right[i]);
            //累积雨水，当存在高度差的时候就可以累积雨水
            water+=Math.max(0,h-height[i]);
        }
        return water;
    }
```
## 128**

最长连续序列

### 描述

给定一个未排序的整数数组，找出最长连续序列的长度。

要求算法的时间复杂度为 O(n)。

示例:

输入: [100, 4, 200, 1, 3, 2]
输出: 4

**解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。**

### 分析

由于要求时间复杂度是O(n)，所以无法使用排序，使用Map来空间换时间。

Map中K为数值，V为连续序列的长度；

遍历数组，若当前值不存在于Map中，则寻找其左右相邻的数字能构成连续序列的长度，若不存在则为0，当前数值的连续序列的长度为左右长度加上一，最后更新左右边界的连续序列的长度。

### 实现

```java
public int longestConsecutive(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        //K:num,V:连续序列的长度
        Map<Integer,Integer> map=new HashMap<>();
        int maxLen=0;
        for(int num:nums){
            //当前是新加入的数字的时候
            if (!map.containsKey(num)){
                //左右的相邻数字
                int left=map.getOrDefault(num-1,0);
                int right=map.getOrDefault(num+1,0);
                //计算当前连续序列的长度
                int len=left+right+1;
                maxLen=Math.max(len,maxLen);
                map.put(num,len);
                //更新左右边界
                map.put(num-left,len);
                map.put(num+right,len);
            }
        }
        return maxLen;
    }
```
## 581*

最短无序连续子数组

### 描述

给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。

你找到的子数组应是最短的，请输出它的长度。

示例 1:

输入: [2, 6, 4, 8, 10, 9, 15]
输出: 5
解释: 你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
说明 :

输入的数组长度范围在 [1, 10,000]。
输入的数组可能包含重复元素 ，所以升序的意思是<=。

### 实现

```java
public int findUnsortedSubarray(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        int n=nums.length;
        int reIndex1=0;
        int max=nums[0];
        //从左向右寻找最后一个逆序的下标
        for(int i=1;i<n;i++){
            if(nums[i]<max){
                reIndex1=i;
            }
            max=Math.max(max,nums[i]);
        }
        
        int reIndex2=1;
        int min=nums[n-1];
        //从右向左寻找最后一个逆序的下标
        for(int i=n-2;i>=0;i--){
            if(nums[i]>min){
                reIndex2=i;
            }
            min=Math.min(min,nums[i]);
        }
        int count=reIndex1-reIndex2+1;
        return count;
        
    }
```



# 参考资料

[玩儿转算法面试 - 课程官方代码仓](https://github.com/liuyubobobo/Play-with-Algorithm-Interview)


