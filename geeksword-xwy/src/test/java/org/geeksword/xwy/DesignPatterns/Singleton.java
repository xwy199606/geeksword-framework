package org.geeksword.xwy.DesignPatterns;

import org.geeksword.xwy.model.Customer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/8  3:59 PM
 * @Description:单例模式的几种写法
 */
public class Singleton {
    //使用一个私有构造函数、一个私有静态变量以及一个公有静态函数来实现。
    //私有构造函数保证了不能通过构造函数来创建对象实例，只能通过公有静态函数返回唯一的私有静态变量。
    private static volatile Singleton single = null;

    private Singleton() {
    }

    /* ----------------双重校验锁 -------------- */

    /**
     * 双重检查锁的优势就在于会优先验证一次single对象是否存在值，而不是像懒汉式一样优先加锁，这样导致了性能的浪费。
     * 虽说synchronized在1.6之后得到了很好的优化，但是在多线程竞争下依然不排除性能的浪费。
     * 双重检索可以降低锁的浪费，也同时保证了线程的安全。
     * 隐患补充：这里隐藏着一个问题，当两个线程进来后，先获取锁的线程开始创建线程的同时，
     * 第二个线程进入判断，因为构造对象的过程可能会比较长，这时第一个线程还未完成对象的完整创建，
     * 但第二个线程会拿到一个不是null的值，从而认为已经构造完成，导致返回的类并非是一个完整的对象。
     * 解决方案可以通过volatile来解决这个问题，完美的解决了这个问题的还是推荐使用内部类的创建方式。
     */
    public static Singleton getSingle() {
        if (single == null)
            synchronized (Singleton.class) {
                if (single == null) {
                    single = new Singleton();
                }
            }
        return single;
    }

    /* --------------- 注册登记式 -------------- */

    /*
     * 更适合多实例场景的管理，当下最火的Spring框架中IOC容器就采用的这种方式来管理我们系统中的Bean对象（例子相对spring要简单许多）
     * 我们创建的单例对象全部保存在Map容器中，由Map容器统一管理我们的单例对象。
     */
    //Map容器

    private final static Map<String, Object> objectsMap = new ConcurrentHashMap<>();

    //获取实例 @return
    public static synchronized Object getInstance() {
        String key = "&Customer";
        if (!objectsMap.containsKey(key)) {
            objectsMap.put(key, new Customer());
        }
        return objectsMap.get(key);
    }

    /* --------------- 内部类形式 -------------- */

    /*
     * 内部类可以说是比较有创意的一种方式了，避免资源的直接浪费，也同时保证了单例。
     * 内部类不同于饿汉式和懒汉式，也算是集成了这两种方式的优点，程序启动后并不会初始化内部类，而当外部类调用内部类的方法时，
     * 才会初始化内部类的Customer实例，保证在不浪费资源的情况下达到的单例模式的应用。
     */
    static {
        System.out.println("父类加载了");
    }

    //获取实例 @return
    public static Customer getInstance1() {
        return CustomerSingleton.CUSTOMER;
    }

    //内部类
    static class CustomerSingleton {
        static final Customer CUSTOMER = new Customer();

        static {
            System.out.println("子类加载了");
        }
    }
}
