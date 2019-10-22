package org.geeksword.xwy.JavaLearn;

import jdk.nashorn.internal.ir.Node;

//泛型类+方法
public class Fx<T extends Comparable> {
    public T Max(T a , T b, T c){
        T d = a.compareTo(b)>0?a:b;
        return d.compareTo(c)>0?d:c;
    }

    public static void main(String[] args) {
        Fx<Integer> fx = new Fx<Integer>();
        System.out.println(fx.Max(8,9,2));

        Fx<Character> maxChar = new Fx<>();
        System.out.println(maxChar.Max('A','s','d'));
    }
}
