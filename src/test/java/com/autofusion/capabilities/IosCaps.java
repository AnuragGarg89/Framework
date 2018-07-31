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
public class IosCaps extends Caps {
    public IosCaps() {
        // TODO Auto-generated constructor stub
    }

    protected static Logger APP_LOG;

    @Override
    public DesiredCapabilities getCapabilities(Logger APP_LOGS) {
        iOSCaps.setCapability("locationServicesEnabled", true);
        iOSCaps.setCapability("locationServicesAuthorized", true);
        iOSCaps.setCapability("autoAcceptAlerts", true);

        try {
            iOSCaps.setCapability("automationName", "Appium");
            iOSCaps.setCapability("platformName", "iOS");
            iOSCaps.setCapability("platformVersion", InitClass
                    .loadExternalConfig().getProperty("IOS_PLATFORM_VER"));
            iOSCaps.setCapability("newCommandTimeout", "2000");
            iOSCaps.setCapability("Browser", "Chrome");
            iOSCaps.setCapability("DEVICE_NAME",
                    InitClass.loadExternalConfig().getProperty("DEVICE_NAME"));
            iOSCaps.setCapability("PLATFORM_VERSION", InitClass
                    .loadExternalConfig().getProperty("PLATFORM_VERSION"));
            iOSCaps.setCapability("APPIUM_VERSION", InitClass
                    .loadExternalConfig().getProperty("APPIUM_VERSION"));
            iOSCaps.setCapability("PLATFORM_NAME", InitClass
                    .loadExternalConfig().getProperty("PLATFORM_NAME"));

        } catch (Exception e) {
            APP_LOG.info("Set Capability Issue with device connectivity: " + e);

        }
        return iOSCaps;
    }
}
