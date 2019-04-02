package org.geeksword.xwy.JavaLearn;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。
 * @Date :Created in 2019/3/12  6:50 PM
 * @Description:
 */
public class Sleeper extends Thread {
    public static int[] output;
    public static int idx;

    private int sleep_time;

    public Sleeper() {
        this.sleep_time = 0;
    }

    public Sleeper(int sleep_time) {
        this.sleep_time = sleep_time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        output[idx++] = this.sleep_time;
    }
}
