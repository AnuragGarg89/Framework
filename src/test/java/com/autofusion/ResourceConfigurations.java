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
 * Method to read the property value. 
 */

package com.autofusion;

import java.util.Locale;
import java.util.ResourceBundle;

import com.autofusion.constants.Constants;

public final class ResourceConfigurations extends BaseClass {
    public ResourceConfigurations() {
        // TODO Auto-generated constructor stub
    }

    protected static String language = PropertyManager.getInstance()
            .getValueForKey("language").trim();
    /**
     * Resource bundle.
     */

    protected static Locale locale = new Locale(language);
    protected static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("configFiles.propertiesFile.constant", locale);

    /**
     * @author abhishek.sharda
     * @param key
     *            Field name
     * @return key value Description Method to read the property value.
     */
    public static String getProperty(final String key) {
        String str = null;
        try {

            if (resourceBundle != null) {
                str = resourceBundle.getString(key);
            } else {
                APP_LOG.info("Properties file was not loaded correctly!!");
            }
            return str.trim();
        } catch (Exception e) {
            APP_LOG.error("Property file throwing exception  : ", e);
            return Constants.FAIL + ": Error while getting value of: " + key
                    + " from ResourceConfigurations";

        }
    }

}
