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
package com.autofusion.keywords;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.relevantcodes.extentreports.ExtentTest;

public class ApplicationSpecificKeywords extends Keyword
        implements KeywordConstant {
    public Logger APP_LOGS;
    public ExtentTest reportTestObj;
    protected String result = "";

    private PerformAction PerformAction = new PerformAction();

    public ApplicationSpecificKeywords(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    public void setReportTestObj(ExtentTest reportTestObj) {
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author mehak.verma
     * @date 08 December,16
     * @param time
     *            (in milliseconds)
     * @description to provide given time of wait within a function
     * @return null
     */
    public void doWait(long time) {
        try {
            time = 0;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            APP_LOG.error("Exception occured in doWait method", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @author mehak.verma
     * @date 13 january,16
     * @description to check if the app is already login
     * @return null
     */
    public void checkIfAlreadyLoginThenLogout() {
        String checkLogoutSign = this.PerformAction.execute(ACTION_CLICK,
                "LearnerConsoleLogOutButtonsign");
        if ("PASS".equalsIgnoreCase(checkLogoutSign)) {
            this.PerformAction.execute(ACTION_CLICK,
                    "LearnerConsoleLogOutButton");
        }
    }

    /**
     * @author mehak.verma
     * @date 08 December,16
     * @description It navigates to Homework page.
     * @return null
     */
    public void navigateHomeworkPage() {
        this.PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE,
                "CourseHomeNavigation");
        this.PerformAction.execute(ACTION_CLICK, "CourseHomeNavigation");
        this.PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE,
                "HomeWorkButton");
        this.PerformAction.execute(ACTION_CLICK, "HomeWorkButton");
    }

    /**
     * @author lekh.bahl
     * @date 06 Jan,17
     * @description It navigates to the Page where element Id2 is present.
     * @return null
     */
    // elementId1: Element Id is being clicked till elementId2 is found
    // elementId2: Element Id whose presence is checked

    public void navigatesToPage(String elementId1, String elementId2) {
        int maxPageCount = 0;
        while (!(this.PerformAction
                .execute(ACTION_VERIFY_ISDISPLAYED, elementId2)
                .equalsIgnoreCase(Constants.PASS)) && (maxPageCount <= 10)) {
            this.PerformAction.execute(ACTION_CLICK, elementId1);
            this.result = this.PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,
                    elementId2);
            maxPageCount = maxPageCount + 1;
            if (this.result.equalsIgnoreCase(Constants.PASS)
                    || (maxPageCount == 10)) {
                break;
            }
        }
    }

    /**
     * @author mayank.mittal
     * @date 11 Jan,17
     * @description To find the existence of an element on DOM.
     * @return null
     */
    public void verifyIsDisplayed(String elementId) {
        this.result = this.PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,
                elementId);
        logResultInReport(this.result, elementId + " is displayed.",
                this.reportTestObj);
    }

    /**
     * @author mayank.mittal
     * @date 18 Jan,17
     * @description To click on an element on DOM.
     * @return null
     */
    public void clickOnElement(String elementId) {
        this.result = this.PerformAction.execute(ACTION_CLICK, elementId);
        logResultInReport(this.result, "Cross is clicked", this.reportTestObj);
    }

    /**
     * @author mayank.mittal
     * @date 18 Jan,17
     * @description To verify page title.
     * @return null
     */
    public void verifyPageTitle(String title) {
        this.PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "LearnerConsoleSettingsXpath");
        this.result = this.PerformAction.execute(ACTION_VERIFY_TITLE, "",
                title);
        logResultInReport(this.result, "Verify " + title + " title.",
                this.reportTestObj);
    }

}