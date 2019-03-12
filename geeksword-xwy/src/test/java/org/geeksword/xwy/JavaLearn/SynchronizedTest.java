package org.geeksword.xwy.JavaLearn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/11  11:20 AM
 * @Description:通过synchornized的几种用法 研究其实现原理
 */
public class SynchronizedTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private synchronized void method1() {
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute"+ ":" + sdf.format(new Date()));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end"+ ":" + sdf.format(new Date()));
    }

    private synchronized void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute"+ ":" + sdf.format(new Date()));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end"+ ":" + sdf.format(new Date()));
    }

    private void method3() {
        System.out.println("Method 3 start");
        try {
            System.out.println("Method 3 execute:" + sdf.format(new Date()));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 3 end"+ ":" + sdf.format(new Date()));
    }

    private void method4() {
        System.out.println("Method 4 start");
        try {
            System.out.println("Method 4 execute:" + sdf.format(new Date()));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 4 end"+ ":" + sdf.format(new Date()));
    }

    private static synchronized void method5(){
        System.out.println("Method 5 start");
        try {
            System.out.println("Method 5 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 5 end");
    }

    private static synchronized void method6(){
        System.out.println("Method 6 start");
        try {
            System.out.println("Method 6 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 6 end");
    }

    public void method7(){
        System.out.println("Method 7 start");
        try {
            synchronized (this) {
                System.out.println("Method 7 execute");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 7 end");
    }

    public void method8(){
        System.out.println("Method 8 start");
        try {
            synchronized (this) {
                System.out.println("Method 8 execute");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 8 end");
    }
    public static void main(String[] args) {
        final SynchronizedTest test = new SynchronizedTest();
        final SynchronizedTest test2 = new SynchronizedTest();

        //method3 method4 没有同步情况 执行结果表明：
        // 线程1和线程2同时进入执行状态，
        // 线程2执行速度比线程1快，所以线程2先执行完成，这个过程中线程1和线程2是同时执行的。
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method3();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method4();
            }
        }).start();

        // method1 method2 对普通方法同步 执行结果表明：
        // 线程2需要等待线程1的method1执行完成才能开始执行method2方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method2();
            }
        }).start();

        //method5 method6 对静态方法（类）同步 执行结果表明：
        // 对静态方法的同步本质上是对类的同步（静态方法本质上是属于类的方法，而不是对象上的方法）所以即使
        // test和test2属于不同的对象，但是它们都属于SynchronizedTest类的实例，所以也只能顺序的执行method5和method6，不能并发执行。
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method5();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test2.method6();
            }
        }).start();

        //method7 method8 对代码块进行同步 执行结果表明：
        // 虽然线程1和线程2都进入了对应的方法开始执行，但是线程2在进入同步块之前，需要等待线程1中同步块执行完成。
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method7();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method8();
            }
        }).start();
    }
}
