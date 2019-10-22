package org.geeksword.xwy.swordoffer;

import org.apache.commons.codec.digest.DigestUtils;



import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Day04 {
    //
    public int minNumberInRotateArray(int [] array) {
        if (array.length == 0)
        return 0;
        //sort默认正序
        Arrays.sort(array);
        return array[0];
    }

    private ExecutorService t = Executors.newCachedThreadPool();
    public static void main(String[] args) {
//        int[] array = {5,4,2,3,1};
//        Arrays.sort(array);
//        System.out.println(array[1]);
//        double a  = 1000;
//
//        for (int i = 0; i < 10; i++) {
//            a = a * 1.0225;
//            a = a + 1000.0;
//        }
//        System.out.println(a);
        String s = "abcd";
        String s1 = "192.168.137.1";
        String s2 = DigestUtils.md5Hex(String.valueOf(s1.hashCode())).toUpperCase();
        System.out.println(s2);
        System.out.println(hash(s));
    }
    static final int hash(Object key){
        int h;
        return (key == null)? 0 : (h = key.hashCode())^(h>>>16);
    }
}
