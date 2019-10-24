/*
 * Copyright 2011 Yonghong Technology Corp, Inc. All rights reserved.
 * Yonghong Technology Corp PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package org.geeksword.mail;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawTest {
   private static int successCount;
   private static Set<String> mailSet = new HashSet<>();
   private static Set<String> urlSet = new HashSet<>();

   /**
    * 获取URL
    */
   public static Set<String> crawlURL(String crawlURL) {
      try {
         URL url = new URL(crawlURL);
         URLConnection conn = url.openConnection();//和网页建立连接
         BufferedReader bufin = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = null;
         String mailRegx = "href=\\\"(?<href>[^\\\"]*)\\\"";//<a href=>的正则表达式
         Pattern p = Pattern.compile(mailRegx);//将正则封装成一个对象
         Set<String> set = new HashSet<>();
         //使用set进行去重存储
         while((line = bufin.readLine()) != null)//逐行读取网页上的字符串
         {
            Matcher m = p.matcher(line);//字符串和规则对象关联，生成一个匹配器
            while(m.find())//循环查找匹配规则的子串
            {
               set.add(m.group().substring(6, m.group().length() - 1));//添加截取后可用的URL
            }
         }

      /*set.add(crawlURL);
      for(String string : set) {
          System.out.println(string);//获取匹配后的结果
      }*/
         System.out.println("主链接：" + crawlURL + " 子链接：" + set.size());
         return set;
      }
      catch(Exception e) {
         return new HashSet<>();
      }
   }

   /**
    * 读取URL内容中所含的邮箱
    *
    * @param spec URL
    */
   public static Set<String> crawlMail(String spec) {
      try {
         URL url = new URL(spec);
         URLConnection conn = url.openConnection();//和网页建立连接
         BufferedReader bufin = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = null;
         String mailRegx = "\\w+@\\w+(\\.\\w+)+";//邮箱的正则表达式，\w表示字母+数字
         Pattern p = Pattern.compile(mailRegx);//将正则封装成一个对象
         Set<String> set = new HashSet<String>();
         //一开始用的arrayList来装这些邮箱，但是会出现重复数据，由于set集合里面本身自带唯一性属性，故使用set
         while((line = bufin.readLine()) != null)//逐行读取网页上的字符串
         {
            Matcher m = p.matcher(line);//字符串和规则对象关联，生成一个匹配器
            while(m.find())//循环查找匹配规则的子串
            {
               set.add(m.group());
            }
         }
         // for(String string : set) {
         //    System.out.println(string);//获取匹配后的结果
         // }
         return set;
      }
      catch(Exception e) {
         //e.printStackTrace();
         return new HashSet<>();
      }
   }

   public static void sendMail(int mailSize, Object[] array) {
      //发送邮件
      int count = 0;
      MailOperation operation = new MailOperation();
      String user = "xiongwuyang@yonghongtech.com";
      String password = "kBkZXXrEnGWsGrFh";
      String host = "smtp.exmail.qq.com";
      String from = "xiongwuyang@yonghongtech.com";
      String subject = "永洪科技《第一届数据分析与应用高峰论坛》邀请函";
/*      String to = "1348773661@qq.com";// 收件人
      try {
         String a = operation.sendMail(user,password,host,from,to,subject);
      }
      catch(Exception e) {
         e.printStackTrace();
      }*/

      for(int i = 1200; i < mailSize; i++) {
         try {
            String to = (String) array[i];
            String res = operation.sendMail(user, password, host, from, to, subject);
            Thread.sleep(3000);
            if("success".equals(res)) {
               count++;
               System.out.println("账号：" + to + " 位置：" + i);
            }
         }
         catch(Exception e) {
            e.printStackTrace();
         }
      }
      successCount = successCount + count;
      System.out.println("历史发送成功人数：" + successCount);
      System.out.println("当前总人数：" + mailSet.size());
      System.out.println("当前发送成功人数：" + count);
   }

   public static void main(String[] args) throws Exception {
      String url = "https://www.douban.com/group/search?start=0&cat=1013&sort=relevance&q=%E9%82%AE%E7%AE%B1";

      //第一层的URL
      Set<String> set1 = crawlURL(url);

      //第二层的URL
      for(String s : set1) {
         Set<String> set2 = crawlURL(s);

         if(set2.size() > 0) {
            urlSet.addAll(set2);
         }
      }

      //第二层url中的邮箱账号
      for(String spec : urlSet) {
         Set<String> set = crawlMail(spec);

         if(set.size() > 0) {
            mailSet.addAll(set);
         }
      }

      for(String string : mailSet) {
         System.out.println(string);
      }
      System.out.println("链接数：" + urlSet.size());
      System.out.println("邮箱数：" + mailSet.size());
      Object[] array = mailSet.toArray();
      int mailSize = array.length;
      sendMail(mailSize, array);
   }
}
