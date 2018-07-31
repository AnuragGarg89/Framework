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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_WelcomeLearnerPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOG;
    protected ExtentTest reportTestObj = null;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();

    public GLPLearner_WelcomeLearnerPage(ExtentTest reportTestObj,
            Logger APP_LOG) {
        this.APP_LOG = APP_LOG;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click an element
     * @return The object of GLPLearner_WelcomeLearnerPage
     */
    public GLPLearner_WelcomeLearnerPage clickOnElement(String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_CLICK, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPLearner_WelcomeLearnerPage(reportTestObj, APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify element is present
     * @return String PASS/FAIL
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOG.debug("Element is present :" + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                locator);
        logResultInReport(this.result, message, this.reportTestObj);
        if (this.result.contains(Constants.PASS)) {
            return Constants.PASS;
        } else {
            return Constants.FAIL;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 07 Nov,2017
     * @description Verify element Attribute like class, id
     * @return The object of GLPLearner_WelcomeLearnerPage
     */
    public GLPLearner_WelcomeLearnerPage verifyElementAttributeValue(
            String element, String attributeName, String verifyText,
            String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPLearner_WelcomeLearnerPage(reportTestObj, APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify text
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        this.APP_LOG.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element,
                text) + " i.e. " + text;
        if (result.contains("PASS")) {
            result = "PASS: " + element + " contains the correct text i.e. "
                    + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: " + element
                    + " does not contains the correct text i.e. " + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;
    }

    /**
     * @author pallavi.tyagi
     * @date 15 Nov,2017
     * @description Verify Element Not Present/display
     */
    public void verifyElementNotPresent(String element, String stepDesc) {

        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String Locator, String Text,
            String stepDesc) {
        APP_LOG.debug("Element is present: " + Locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator,
                Text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author pallavi.tyagi
     * @date 30 Nov ,17
     * @description To Get title attribute value
     * 
     */
    public String getElementAttribute(String element, String attributeName,
            String stepDesc) {

        this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element,
                attributeName);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author Lekh.bahl
     * @date 8 Dec,2017
     * @description Verify text in the list
     */
    public String verifyTextInList(String element, String text,
            String stepDesc) {
        this.APP_LOG.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction
                .execute(ACTION_VERIFY_EQUALS_TEXT_IN_LIST, element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

}
