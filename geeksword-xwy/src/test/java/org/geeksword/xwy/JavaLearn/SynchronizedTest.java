package org.geeksword.xwy.JavaLearn;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

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
            System.out.println("Method 1 execute" + ":" + sdf.format(new Date()));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end" + ":" + sdf.format(new Date()));
    }

    private synchronized void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute" + ":" + sdf.format(new Date()));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end" + ":" + sdf.format(new Date()));
    }

    private void method3() {
        System.out.println("Method 3 start");
        try {
            System.out.println("Method 3 execute:" + sdf.format(new Date()));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 3 end" + ":" + sdf.format(new Date()));
    }

    private void method4() {
        System.out.println("Method 4 start");
        try {
            System.out.println("Method 4 execute:" + sdf.format(new Date()));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 4 end" + ":" + sdf.format(new Date()));
    }

    private static synchronized void method5() {
        System.out.println("Method 5 start");
        try {
            System.out.println("Method 5 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 5 end");
    }

    private static synchronized void method6() {
        System.out.println("Method 6 start");
        try {
            System.out.println("Method 6 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 6 end");
    }

    private void method7() {
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

    private void method8() {
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

       /*
                    运行结果解释
        1、代码段2结果：
　　          虽然method1和method2是不同的方法，但是这两个方法都进行了同步，并且是通过同一个对象去调用的，
            所以调用之前都需要先去竞争同一个对象上的锁（monitor），也就只能互斥的获取到锁，因此，method1和method2只能顺序的执行。

        2、代码段3结果：
　　          虽然test和test2属于不同对象，但是test和test2属于同一个类的不同实例，由于method5和method6都属于静态同步方法，
            所以调用的时候需要获取同一个类上monitor（每个类只对应一个class对象），所以也只能顺序的执行。

        3、代码段4结果：
              对于代码块的同步实质上需要获取Synchronized关键字后面括号中对象的monitor，由于这段代码中括号的内容都是this，
              而method7和method8又是通过同一的对象去调用的，所以进入同步块之前需要去竞争同一个对象上的锁，因此只能顺序执行同步块。*/
    }
}
