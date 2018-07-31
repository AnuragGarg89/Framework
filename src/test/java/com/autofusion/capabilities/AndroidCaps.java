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
package com.autofusion.capabilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autofusion.util.InitClass;

/**
 * @author nitin.singh
 */
public class AndroidCaps extends Caps {

    public AndroidCaps() {
        // TODO Auto-generated constructor stub
    }

    protected String adbPath = "";
    protected String apkPath = "";
    protected String apkName = "";
    protected String apkPackage = "";
    protected String launchActivity = "";
    protected String reInstallApp = "";
    protected String runOnDevice = "";
    public String browserName = "";
    protected String platformName = "";
    private Logger APP_LOG;

    public DesiredCapabilities getCapabilities() {

        this.adbPath = InitClass.loadExternalConfig().getProperty("ADB_PATH");
        this.apkPath = InitClass.loadExternalConfig().getProperty("APK_PATH");
        this.apkName = InitClass.loadExternalConfig().getProperty("APK_NAME");
        this.apkPackage = InitClass.loadExternalConfig()
                .getProperty("APK_PACKAGE");
        this.launchActivity = InitClass.loadExternalConfig()
                .getProperty("LAUNCH_ACTIVITY");
        this.reInstallApp = InitClass.loadExternalConfig()
                .getProperty("ReInstallApp");
        this.runOnDevice = InitClass.loadExternalConfig()
                .getProperty("runOnDevice");
        this.browserName = InitClass.loadExternalConfig()
                .getProperty("browserName");
        this.platformName = InitClass.loadExternalConfig()
                .getProperty("PLATFORM_NAME");

        try {
            int apilevel = Integer.parseInt("23");
            if (apilevel < 17) {
                androidCaps.setCapability("automationName", "Selendroid");
            } else {
                androidCaps.setCapability("automationName", "Appium");
            }
            androidCaps.setCapability("platformName", "Android");
            androidCaps.setCapability("platformVersion", "6.0");
            androidCaps.setCapability("deviceName", "lenovo");
            androidCaps.setCapability("newCommandTimeout", "2000");

            if ("on".equals(this.reInstallApp)) {
                androidCaps.setCapability("app",
                        this.apkPath + "/" + this.apkName);
            }

            if ("yes".equals(this.runOnDevice)) {
                androidCaps.setCapability("appActivity", this.launchActivity);
                androidCaps.setCapability("appPackage", this.apkPackage);
            } else {
                androidCaps.setCapability("browserName", this.browserName);
            }

        } catch (Exception e) {
            this.APP_LOG.debug(
                    "Set Capability Issue with device connectivity: " + e);
            this.APP_LOG.info(
                    "Set Capability Issue with device connectivity: " + e);

        }
        return androidCaps;
    }
}