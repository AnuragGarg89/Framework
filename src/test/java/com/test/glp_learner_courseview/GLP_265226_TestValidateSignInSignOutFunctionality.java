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
 * 
 * @author mohit.gupta5
 * @date 22 Nov, 2017
 * @description To Verify that on clicking sign out button learner logout from
 *              application.
 */
public class GLP_265226_TestValidateSignInSignOutFunctionality
        extends BaseClass {
    public GLP_265226_TestValidateSignInSignOutFunctionality() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that on clicking sign out button learner logout from application.")
    public void testSignInSignOutFunctionality() {
        startReport(getTestCaseId(),
                "Verify that on clicking sign out button learner logout from application.");
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
            // Click on 'Sign out' button
            objProductApplicationCourseViewPage.clickOnElement("LogoutButton",
                    "Click on 'Sign out' button");
            // Verify that user is on 'SignIn' page.
            objProductApplicationLoginPage.verifyElementPresent(
                    "ConsoleSignInButton",
                    "Verify that user is on 'SignIn' page.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
