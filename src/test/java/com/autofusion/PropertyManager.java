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
 * Returns value from config file or from variable jenkins
 */
package com.autofusion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager extends BaseClass {
    private static PropertyManager _instance = null;
    public Properties testData;
    public InputStream fileConfig = null;

    public PropertyManager() {

        try {
            this.testData = new Properties();
            this.fileConfig = getClass().getClassLoader()
                    .getResourceAsStream("configFiles/propertiesFile/config.properties");
            if (this.fileConfig != null) {
                this.testData.load(this.fileConfig);
                this.fileConfig.close();
            } else {
                APP_LOG.info("Error on reading config file");
            }

        }

        catch (IOException e)

        {
            APP_LOG.error("Error" + e);
        }

        finally {
            if (this.fileConfig != null) {
                try {
                    this.fileConfig.close();
                } catch (IOException e) {
                    APP_LOG.error(
                            "Func:  Error occured while closing config file"
                                    + e);
                }
            }

        }

    }

    public static synchronized PropertyManager getInstance() {

        if (_instance == null) {
            _instance = new PropertyManager();
        }
        return _instance;
    }

    public String getValueForKey(String key) {
        return this.testData.getProperty(key);

    }

    /**
     * valueFromConfig return value from CONFIG properties if value from Jenkins
     * environment is null.
     * 
     * @return--> The value from config file or from variable jenkins
     */
    public String valueFromConfig(String key) {

        // Checking if value from Jenkins variable is null, if it is null then
        // value from config.properties will return
        try {
            if (System.getenv(key) == null) {
                System.out.println("Value of Jenkins parameter " + key
                        + " is null, loaded value: " + this.getValueForKey(key)
                        + " from Config properties");
                return this.getValueForKey(key);

            }
            System.out.println("Value of Jenkins parameter " + key
                    + " is not null, loaded value: " + System.getenv(key)
                    + " from jenkins");
            return System.getenv(key);

        } catch (NullPointerException | SecurityException e) {
            System.out.println(
                    "Error while getting value from config/jenkins because of :-"
                            + e);

        }

        return null;
    }

}
