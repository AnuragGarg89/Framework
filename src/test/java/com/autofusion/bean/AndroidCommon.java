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
/*
 * Methods to get the Mobile parameters value during the runtime.
 */

package com.autofusion.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AndroidCommon {

    protected static Properties prop = new Properties();
    protected static String adbBasePath = "";
    protected static ArrayList<String> strState = new ArrayList<>();
    protected static int i = 0, j = 0;
    protected static Logger APP_LOGS;

    public AndroidCommon(String adbBasePath, Logger APP_LOGS) {
        this.adbBasePath = adbBasePath;
        this.APP_LOGS = APP_LOGS;
    }

    public static String deviceSerielNo() {
        String command = executeCommand("instruments -s devices");
        return command.trim();
    }

    public static String executeCommand(String cmd) {

        StringBuilder output = new StringBuilder();
        Process p;
        InputStreamReader in;
        try {
            p = Runtime.getRuntime().exec("instruments -s devices");

            p.waitFor();
            in = new InputStreamReader(p.getInputStream());
            BufferedReader reader = new BufferedReader(in);

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException | NullPointerException | IllegalArgumentException
                | InterruptedException e) {
            APP_LOGS.debug(
                    "Eexception occured while try to get details of device "
                            + e);
        }
        return output.toString();
    }

    public static void wait(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < n);
    }

    public static boolean verifyKeyboard() throws InterruptedException {
        boolean val;

        String command = executeCommand(
                adbBasePath + " adb shell dumpsys input_method");
        if (command.contains("mInputShown=true")) {
            val = true;
        } else {
            val = false;
        }
        return val;
    }

}
