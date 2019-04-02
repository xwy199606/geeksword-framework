package org.geeksword.xwy.swordoffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/8  10:58 AM
 * @Description:格式匹配
 *  输入
 *      pattern="aabb" str="北京 北京 杭州 杭州" 返回true
 *      pattern="abab" str="北京 杭州 杭州 北京" 返回false
 */
public class day02 {
    public static void main(String[] args) {
        String str = "97,98,99";
        boolean abc = str.contains("abc");
        System.out.println(abc);
        System.out.println(asciiToString(str));

        System.out.println(wordPattern("abc","北京 上海 武汉"));
        System.out.println(wordPattern("cdbdd","北京 武汉 上海 武汉 武汉"));
    }
    //cdbdd abcbb


    //数字转字符
    private static String asciiToString(String value)
    {
        StringBuilder sb = new StringBuilder();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sb.append((char) Integer.parseInt(chars[i]));
        }
        return sb.toString();
    }


    private static boolean wordPattern(String pattern, String str) {
        Map<Character, String> map = new HashMap<Character, String>();
        Set<String> set = new HashSet<String>();
        String[] pieces = str.split(" ");
        if(pieces.length != pattern.length()) return false;
        int i = 0;
        for(String s : pieces){
            char p = pattern.charAt(i);
            System.out.println(p);
            // 如果该字符产生过映射
            if(map.containsKey(p)){
                // 且映射的字符串和当前字符串不一样
                if(!s.equals(map.get(p))) return false;
            } else {
                // 如果该字符没有产生过映射
                // 如果当前字符串已经被映射过了
                if(set.contains(s)) return false;
                // 否则新加一组映射
                map.put(p, s);
                set.add(s);
            }
            i++;
        }
        return true;
    }

}
