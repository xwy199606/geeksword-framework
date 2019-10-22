package org.geeksword.xwy.JavaLearn;

//要利用clone实现Java中深拷贝，需要继承Cloneable接口，并重写其中的clone方法。
public class clone {
    // 创建内部类Person,主要是为了在同一个类里面实现相互调用
    public static class Person implements Cloneable{
        private int age;
        private String name;
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Person(int age,String name){
            this.age=age;
            this.name=name;
        }
        public void printInfo(){
            System.out.println("姓名是"+this.name+",年龄是"+this.age);
        }
        // 实现深层拷贝的函数
        @Override
        public Object clone() throws CloneNotSupportedException{
            Person p1=(Person)super.clone();
            return p1;
        }
    }
    public static void main(String[] args){
        Person per1=new Person(23,"张三");
        Person per2;
        try {
            per2 = (Person) per1.clone(); // 成功深层拷贝per1，改变per2的值不会影响per1
            per2.setName("李四");
            per2.setAge(34);
            per1.printInfo();
            per2.printInfo();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
