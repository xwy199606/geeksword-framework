package org.geeksword.xwy.JavaLearn;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。
 * @Date :Created in 2019/3/12  6:51 PM
 * @Description:
 */
public class SleepSort {
    public static void main(String[] args) {
        int[] nubs = {3, 5, 4, 8, 9, 1};
        SleepSort.sort(nubs);
        for (int n : nubs)
            System.out.printf("%d   ", n);
    }

    public static void sort(int[] nubs) {
        Sleeper.idx = 0;
        Sleeper.output = new int[nubs.length];
        for (int i = 0; i < nubs.length; i++)        //[1]
            new Sleeper(nubs[i]).start();

        try {
            Thread.sleep(100);                //[2]
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < nubs.length; i++)
            nubs[i] = Sleeper.output[i];
    }
}
