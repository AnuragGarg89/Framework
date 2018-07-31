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

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.keywords.ReadObjectRepoXml;
import com.relevantcodes.extentreports.ExtentTest;

public class GmailUtility extends ReadObjectRepoXml {
    public Properties props = new Properties();
    public Folder mailFolder;
    public Store mailStore;
    public Logger APP_LOGS;
    public ExtentTest reportTestObj;

    public GmailUtility(ExtentTest reportTestObj, Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    public String getURL() {
        String str = this.gmailConfig();
        if (str.contains(Constants.FAIL)) {
            return Constants.FAIL;
        } else {

            str = str.split("https://")[1];
            str = str.split(">")[0];
            str = str.replaceAll("If", "");
            str = str.trim();
            str = "https://" + str;
            return str;
        }

    }

    public void setProperty() {
        this.props.setProperty("mail.imap.host", "imap.gmail.com");
        this.props.setProperty("mail.imap.port", "993");
        this.props.setProperty("mail.imap.connectiontimeout", "5000");
        this.props.setProperty("mail.imap.timeout", "5000");

    }

    public Message[] getGmailMessages() throws MessagingException {
        try {
            Session session = Session.getDefaultInstance(this.props, null);
            this.mailStore = session.getStore("imaps");
            this.mailStore.connect("imap.gmail.com",
                    ResourceConfigurations.getProperty("gmailUserName"),
                    ResourceConfigurations.getProperty("gmailPassword"));
            this.mailFolder = mailStore.getFolder("inbox");
            this.mailFolder.open(Folder.READ_WRITE);

            WebDriverWait wait = new WebDriverWait(returnDriver(), 30);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webDriver) {
                    try {
                        APP_LOG.info("Mail box sucessfully read");
                        boolean bool = mailFolder.getMessageCount() >= 0;
                        if (bool) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (Exception t) {
                        APP_LOG.error(
                                "Mail box unread due to some exception, e" + t);
                        return false;
                    }

                }
            });

            int messageCount = this.mailFolder.getMessageCount();
            this.APP_LOGS.info("Total Messages:- " + messageCount);
            return this.mailFolder.getMessages();
        } catch (Exception e) {
            APP_LOG.error("" + e);
            return null;
        }
    }

    public void closeGmailSession() {
        try {
            this.mailFolder.close(true);
            this.mailStore.close();
        } catch (MessagingException e) {
            APP_LOG.error(
                    "Exception occured while trying to close Gmail session", e);
        }
    }

    public void deleteMail(Message message) {
        try {
            message.setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException e) {
            APP_LOG.error(
                    "Exception occured while trying to set flag for email deletion",
                    e);
        }

    }

    public String gmailConfig() {
        this.setProperty();
        try {
            Message[] messages = this.getGmailMessages();
            if (messages.length > 0) {
                for (int i = messages.length - 1; i >= 0; i--) {
                    Object content = messages[i].getContent();
                    if (content instanceof String) {
                        if (messages[i].getSubject().contains(
                                "QA-INT-Squires Activation Conformation")) {
                            String body = (String) content;
                            this.deleteMail(messages[i]);
                            return body;
                        }
                    }
                }

                this.closeGmailSession();
            } else if (messages.length == 0) {
                this.closeGmailSession();
                return Constants.FAIL;

            }

        } catch (Exception e) {
            APP_LOG.error("Exception occured while trying to delete email", e);
        }

        return Constants.FAIL;
    }

}
