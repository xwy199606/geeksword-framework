package org.geeksword.xwy.DesignPatterns;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/8  3:59 PM
 * @Description:
 */
public class Singleton {
    //使用一个私有构造函数、一个私有静态变量以及一个公有静态函数来实现。
    //私有构造函数保证了不能通过构造函数来创建对象实例，只能通过公有静态函数返回唯一的私有静态变量。
    private static volatile Singleton single = null;

    private Singleton() {
    }

    public static Singleton getSingle() {
        if (single == null)
            synchronized (Singleton.class) {
                if (single == null) {
                    single = new Singleton();
                }
            }
        return single;
    }
}
