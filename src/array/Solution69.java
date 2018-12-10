package array;

import org.junit.Test;

/**
 * 实现 int sqrt(int x) 函数。
 *
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 *
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 *
 * 示例 1:
 *
 * 输入: 4
 * 输出: 2
 * 示例 2:
 *
 * 输入: 8
 * 输出: 2
 * 说明: 8 的平方根是 2.82842...,
 *      由于返回类型是整数，小数部分将被舍去。
 */
public class Solution69 {
    //使用二分查找的方法
    public int mySqrt(int x) {
        //为了避免整型的溢出，所以中间采用long类型来存储
        long l=0,h=x;
        while (l<=h){
            long mid=l+(h-l)/2;
            if (mid*mid<x){
                l=mid+1;
            }else if (mid*mid==x){
                return (int) mid;
            }else {
                h=mid-1;
            }
        }
        return (int) h;
    }
    @Test
    public void test(){
        int x=2147395599;
        int r = mySqrt(x);
        System.out.println(r);
    }

}
