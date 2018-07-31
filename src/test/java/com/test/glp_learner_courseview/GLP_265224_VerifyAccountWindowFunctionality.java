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
package com.test.glp_learner_courseview;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseViewPage;

/**
 * @author mohit.gupta5
 * @date Nov 22, 2017
 * @description: To verify that My Account window UI functionality
 */
public class GLP_265224_VerifyAccountWindowFunctionality extends BaseClass {
    public GLP_265224_VerifyAccountWindowFunctionality() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify that My Account window UI functionality")
    public void verifyAccountWindowFunctionality() {
        startReport(getTestCaseId(),
                "To verify that My Account window UI functionality");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(
                    configurationsXlsMap.get("LEARNER_USER_NAME"),
                    configurationsXlsMap.get("LEARNER_PASSWORD"));

            GLPLearner_CourseViewPage objProductApplicationCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Click on User name to open 'Account Window' DropDown.
            objProductApplicationCourseViewPage.clickOnElement(
                    "CourseViewUserName",
                    "Click on User name to open 'Account Window' DropDown.");
            // Verify 'My Account' Text is present
            objProductApplicationCourseViewPage.verifyText(
                    "CourseViewMyAccountText",
                    ResourceConfigurations.getProperty("myAccountText"),
                    "Verify 'My Account' Text is present");
            // Verify 'Account settings' Text is present
            objProductApplicationCourseViewPage.verifyText(
                    "CourseViewAccountSettingsText",
                    ResourceConfigurations.getProperty("accountSettingText"),
                    "Verify 'Account settings' Text is present");
            // Verify 'Sing out' Text is present
            objProductApplicationCourseViewPage.verifyText("LogoutButton",
                    ResourceConfigurations.getProperty("signOutText"),
                    "Verify 'Sing out' Text is present");
            // Verify 'Cross' button is present
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseViewCrossButton",
                    "Verify 'Cross' button is present");
        } finally {

            webDriver.quit();
            webDriver = null;
        }

    }
}
