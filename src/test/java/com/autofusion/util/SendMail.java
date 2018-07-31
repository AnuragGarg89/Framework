/*
 * PEARSON PROPRIETARY AND CONFIDENTIAL INFORMATION SUBJECT TO NDA 
 * Copyright (c) 2017 Pearson Education, Inc.
 * All Rights Reserved. 
 * 
 * NOTICE: All information contained herein is, and remains the property of 
 * Pearson Education, Inc. The intellectual and technical concepts contained 
 * herein are proprietary to Pearson Education, Inc. and may be covered by U.S. 
 * and Foreign Patents, patent applications, and are protected by trade secret 
 * or copyright law. Dissemination of this information, reproduction of this  
 * material, and copying or distribution of this software is strictly forbidden   
 * unless prior written permission is obtained from Pearson Education, Inc.
 */
package com.autofusion.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class SendMail {
    public SendMail() {
        // TODO Auto-generated constructor stub
    }

    protected static Logger APP_LOG = null;

    public static boolean sendMail(String userName, String password,
            String[] to, String[] cc, String[] bcc, String subject, String text,
            String attachmentPath, String attachmentName, String smtpHost,
            String smtpPort) {

        String host = smtpHost;
        String port = smtpPort;

        String auth = "true";

        Properties props = new Properties();
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.host", host);
        if (!"".equals(port)) {
            props.put("mail.smtp.port", port);
        }
        String starttls = "true";
        if (!"".equals(starttls)) {
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.auth", auth);
        }
        boolean debug = false;
        if (debug) {
            props.put("mail.smtp.debug", "true");
        } else {
            props.put("mail.smtp.debug", "false");
        }

        if (!"".equals(port)) {
            props.put("mail.smtp.socketFactory.port", port);
        }
        String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
        if (!"".equals(socketFactoryClass)) {
            props.put("mail.smtp.socketFactory.class", socketFactoryClass);
        }
        String fallback = "false";
        if (!"".equals(fallback)) {
            props.put("mail.smtp.socketFactory.fallback", fallback);
        }
        try {

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(debug);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setSubject(subject);
            // attachment start
            // create the message part

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);

            // attachment ends
            // Put parts in message
            msg.setContent(multipart);
            msg.setFrom(new InternetAddress(userName));

            for (int i = 0; i < to.length; i++) {
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to[i]));
            }

            for (int i = 0; i < cc.length; i++) {
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(cc[i]));
            }

            for (int i = 0; i < bcc.length; i++) {
                msg.addRecipient(Message.RecipientType.BCC,
                        new InternetAddress(bcc[i]));
            }

            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            return true;
        } catch (Exception e) {
            APP_LOG.error(
                    "Func: sendMail - Exception occured while sending mail"
                            + e);

            return false;
        }
    }

    public static boolean sendMail(String userName, String password,
            String[] to, String[] cc, String[] bcc, String subject,
            String text) {

        if (userName == null || "".equals(userName.trim())) {
            return false;
        }
        String host = "smtp.gmail.com";
        String port = "465";
        Properties props = new Properties();
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.host", host);
        if (!"".equals(port)) {
            props.put("mail.smtp.port", port);
        }
        String starttls = "true";
        String auth = "true";
        if (!"".equals(starttls)) {
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.auth", auth);
        }
        boolean debug = false;
        if (debug) {
            props.put("mail.smtp.debug", "true");
        } else {
            props.put("mail.smtp.debug", "false");
        }

        if (!"".equals(port)) {
            props.put("mail.smtp.socketFactory.port", port);
        }
        String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
        if (!"".equals(socketFactoryClass)) {
            props.put("mail.smtp.socketFactory.class", socketFactoryClass);
        }
        String fallback = "false";
        if (!"".equals(fallback)) {
            props.put("mail.smtp.socketFactory.fallback", fallback);
        }
        try {

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(debug);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setSubject(subject);
            // attachment start
            // create the message part

            msg.setFrom(new InternetAddress(userName));

            for (int i = 0; i < to.length; i++) {
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to[i]));
            }

            for (int i = 0; i < cc.length; i++) {
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(cc[i]));
            }

            for (int i = 0; i < bcc.length; i++) {
                msg.addRecipient(Message.RecipientType.BCC,
                        new InternetAddress(bcc[i]));
            }

            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            return true;
        } catch (Exception e) {
            APP_LOG.error(
                    "Func: sendMail -Exception occured while sending mail" + e);
            return false;
        }
    }
}