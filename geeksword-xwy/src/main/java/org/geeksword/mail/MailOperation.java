/*
 * Copyright 2011 Yonghong Technology Corp, Inc. All rights reserved.
 * Yonghong Technology Corp PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package org.geeksword.mail;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送操作类
 */
public class MailOperation {
   /**
    * 发送邮件
    *
    * @param user     发件人邮箱
    * @param password 授权码（注意不是邮箱登录密码）
    * @param host     服务器
    * @param from     发件人
    * @param to       接收者邮箱
    * @param subject  邮件主题
    * @return success 发送成功 failure 发送失败
    * @throws Exception
    */
   public String sendMail(String user, String password, String host,
      String from, String to, String subject) throws Exception
   {
      if(to != null) {
         Properties props = System.getProperties();
         props.put("mail.smtp.host", host);
         props.put("mail.smtp.auth", "true");
         MailAuthenticator auth = new MailAuthenticator(user, password);
         Session session = Session.getInstance(props, auth);
         //是否显示debug信息
         //session.setDebug(true);

         try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            if(!to.trim().equals(""))
               message.addRecipient(Message.RecipientType.TO,
                  new InternetAddress(to.trim()));
            message.setSubject(subject);
            //图片 注意设置contentId 再往img标签添加
            MimeBodyPart img = new MimeBodyPart();
            String imgPath = "D://CloudMusic//abc.png";
            DataHandler dh = new DataHandler(new FileDataSource(imgPath));
            img.setDataHandler(dh);
            img.setContentID("a");
            img.setFileName(imgPath);

            // 正文
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent("<!DOCTYPE>" +
               "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#000000;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>诚邀：</span>"
               + "<div style='width:950px;font-family:arial;'>  2019年10月25日永洪科技将在京举办第一届数据分析技术与应用高峰论坛。数据分析领域数千专业人士汇聚一堂，一场AI+BI数据应用领域的盛宴值得期待。邮件回复“确认参加”领取邀请码，扫描图片下方二维码即可免费报名参加。数据分析领域的干货知识分享，以及精美小礼品等你来哦。<br/> <br/> <br/>发件人：Yonghong </div>"
               + "<br/> <img src='cid:a'>"
               + "</div>", "text/html;charset=utf-8");

            MimeMultipart mp = new MimeMultipart(); // 整个邮件：正文+图片/附件
            mp.addBodyPart(mbp1);
            mp.addBodyPart(img);
            mp.setSubType("related");//正文与图片关系
            message.setContent(mp);
            message.setSentDate(new Date());
            message.saveChanges();
            Transport trans = session.getTransport("smtp");
            Transport.send(message);
            // System.out.println(message.toString());
         }
         catch(Exception e) {
            e.printStackTrace();
            return "failure";
         }
         return "success";
      }
      else {
         return "failure";
      }
   }
}
