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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.autofusion.BaseClass;

public class InitClass extends BaseClass {

    private static Logger APP_LOGS = null;
    protected static ArrayList<String> strState = new ArrayList<>();
    protected static Properties CONFIG;
    protected static Properties USER_CONFIG;
    protected static String fileSeprator = System.getProperty("file.separator");

    public InitClass(String path) {
    }

    public InitClass() {
        // TODO Auto-generated constructor stub
    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    public static synchronized Logger initializeLogger(String basePath,
            String device) {
        String logDate = now("dd.MMMMM.yyyyhh.mm.ss");
        try {
            System.setProperty("log.dir",
                    basePath + File.separator
                            + device.toLowerCase(Locale.ENGLISH)
                            + File.separator + "testLogs" + File.separator
                            + "ApplicationLog_" + logDate + ".log");
            APP_LOGS = Logger.getLogger("automation");
        } catch (Exception e) {
            APP_LOGS.debug("Exception during Logger Creation " + e);
        }
        APP_LOGS.debug(" initializeLogger " + basePath + " :: " + device + " - "
                + logDate);
        APP_LOGS.debug("Logger Initiated");

        return APP_LOGS;
    }

    public static synchronized String
           initializeExternalConfigFile(String folderPath) {
        APP_LOGS.debug("config Initiated :" + folderPath);
        InputStreamReader fileConfig2 = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(
                    (folderPath + fileSeprator + "mobileconfig.properties"));

            fileConfig2 = new InputStreamReader(fis, "UTF-8");
            USER_CONFIG = new Properties();
            USER_CONFIG.load(fileConfig2);
            APP_LOG.info("User config loaded");
            return "Success";
        } catch (Exception e) {
            APP_LOGS.debug("config file not Initiated" + e);
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    APP_LOGS.error("config file not Initiated" + e);
                }
            }
            if (fileConfig2 != null) {
                try {
                    fileConfig2.close();
                } catch (IOException e) {
                    APP_LOGS.error("config file not Initiated" + e);
                }
            }

        }

    }

    public static Properties loadExternalConfig() {
        try {

            if (USER_CONFIG == null) {
                initializeExternalConfigFile(projectPath);

            }

            return USER_CONFIG;
        } catch (Exception e) {
            APP_LOG.error("" + e);
            return USER_CONFIG;
        }
    }

}
