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
package com.glp.page;

import org.apache.log4j.Logger;

import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_CoreInstructionsPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";

    public GLPLearner_CoreInstructionsPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;

    }

}
