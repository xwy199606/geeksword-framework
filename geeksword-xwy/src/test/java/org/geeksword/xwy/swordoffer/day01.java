package org.geeksword.xwy.swordoffer;

import java.util.Arrays;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/7  5:00 PM
 * @Description:斐波那契数列、矩形覆盖、跳台阶、变态跳台阶
 */
public class day01 {
    /*
     * Description of the topic:
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * n<=39
     * answer：f(n) = f(n-1) + f(n-2)
     */
    public static void main(String[] args) {
//        System.out.println(Fibonacci(7));
//        System.out.println(FibonacciII(8));
//        System.out.println(JumpFloorI(4));
//        System.out.println(JumpFloorII(7));
        int a= 0;
        while(true){
            a = a+1;
        }
    }

    //递归
    private static int Fibonacci(int n) {
        if (n <= 1)
            return n;
        else
            return Fibonacci(n - 1) + Fibonacci(n - 2);
    }

    //循环
    private static int FibonacciII(int n) {
        if (n <= 1)
            return n;
        int[] fib = new int[n + 1];
        fib[1] = 1;
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n];
    }

    /*
     * Description of the topic:
     * 我们可以用 2*1 的小矩形横着或者竖着去覆盖更大的矩形。请问用 n 个 2*1 的小矩形无重叠地覆盖一个 2*n 的大矩形，总共有多少种方法？
     */
    private static int RectCover() {
        return 0;
    }

    /*
     * Description:
     * 一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级... 它也可以跳上 n 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
     * answer：假定台阶四级，跳到4级的方式就是前三级总和加上直接跳到第四级 A1+A2+A3+1=A4 以此类推
     */
    //动态规划
    private static int JumpFloorI(int target) {
        int[] dp = new int[target];
        Arrays.fill(dp, 1);
        for (int i = 0; i < target; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = dp[i] + dp[j];
            }
        }
        return dp[target - 1];
    }
    //数学推导
    private static int JumpFloorII(int target){
        return (int) Math.pow(2,target-1);
    }
}
