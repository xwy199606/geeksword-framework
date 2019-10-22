package org.geeksword.xwy.swordoffer;

import java.io.File;
import java.util.*;

public class Day03 {

    //替换空格
    public String replaceSpace(StringBuffer str) {
        int p1 = str.length() - 1;
        for (int i = 0; i < p1; i++) {
            if (str.charAt(i) == ' ') {
                str.append("   ");
            }
        }
        int p2 = str.length() - 1;
        while (p1 >= 0 && p2 > p1) {
            char c = str.charAt(p1--);
            if (c == ' ') {
                str.setCharAt(p2--, '0');
                str.setCharAt(p2--, '2');
                str.setCharAt(p2--, '%');
            } else {
                str.setCharAt(p2--, c);
            }
        }
        return str.toString();
    }

    //字符串中找到第一个只出现一次的字符的位置
    public static int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0)
            return -1;
        Map<Character, Integer> map = new LinkedHashMap();
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (map.containsKey(ch[i])) {
                int value = map.get(ch[i]);
                value++;
                map.put(ch[i], value);
            } else {
                map.put(ch[i], 1);
            }
        }
        Iterator it = map.keySet().iterator();
        char key = ' ';
        while (it.hasNext()) {
            char temp = (char) it.next();
            if (map.get(temp) == 1) {
                key = temp;
                break;
            }
        }
        //sout(key);
        int index = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == key)
                index = i;
        }
        return index;
    }

    //二维数组中查找
    public boolean Find(int target, int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int rows = matrix.length, cols = matrix[0].length;
        int r = 0, c = cols - 1; // 从右上角开始
        while (r <= rows - 1 && c >= 0) {
            if (target == matrix[r][c])
                return true;
            else if (target > matrix[r][c])
                r++;
            else
                c--;
        }
        return false;
    }

    //单链表测试数据
    public static void main(String[] args) {
        ListNode<Integer> listNode = new ListNode<>();
        listNode.addNode(1);
        listNode.addNode(2);
        listNode.addNode(3);
        listNode.addNode(4);
        listNode.addNode(5);
        listNode.printLink();
        System.out.println(listNode.val);
//        listNode.reserveLink();
        listNode.printLink();
        System.out.println(listNode.printListFromTailToHead(listNode.head));

        ArrayList<Integer> arrayList = listNode.printListFromTailToHead(listNode.head);

    }
}
