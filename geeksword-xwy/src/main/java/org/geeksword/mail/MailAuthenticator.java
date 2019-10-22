/*
 * Copyright 2011 Yonghong Technology Corp, Inc. All rights reserved.
 * Yonghong Technology Corp PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package org.geeksword.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
   String name;
   String password;

   public MailAuthenticator(String name, String password) {
      this.name = name;
      this.password = password;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(name, password);
   }
}
