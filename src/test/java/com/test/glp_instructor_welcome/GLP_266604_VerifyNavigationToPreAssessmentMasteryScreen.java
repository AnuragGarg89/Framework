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
package com.test.glp_instructor_welcome;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date Nov 10, 2017
 * @description : Verify that click on 'Get Started' button user should be
 *              navigated to course mastery settings.
 */
public class GLP_266604_VerifyNavigationToPreAssessmentMasteryScreen
        extends BaseClass {
    public GLP_266604_VerifyNavigationToPreAssessmentMasteryScreen() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify that click on 'Get Started' button user should be navigated to course mastery settings. ")
    public void verifyNavigationToPreAssessmentMasteryScreen() {
        startReport(getTestCaseId(),
                "Verify that click on 'Get Started' button user should be navigated to course mastery settings.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create Instructor and subscribe course using corresponding APIs.
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            // Login to the application as an Instructor
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Welcome page Settings page
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            objProductApplicationWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify that instructor is navigated to Welcome screen.");

            // Navigate to the Pre Assessment Mastery Level page Settings page
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            objProductApplicationMasterySettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify that instructor is navigated to Pre-Assessment Mastery setting screen.");
            // Click on the Back button available on Mastery Screen
            objProductApplicationMasterySettingPage.clickOnElement(
                    "PreAssessmentMasteryBackBtn",
                    "Click on the Back button on Mastery Screen.");

            // Instructor should be redirected to welcome page.
            // Verify the "Get Started" button displayed on the welcome page.
            objProductApplicationWelcomeInstructorPage.verifyText(
                    "WelcomeInstructorGetStartedButton",
                    ResourceConfigurations.getProperty("welcomeGetStarted"),
                    "Verify Get Started button is displayed on welcome screen.");
        }

        // Delete User via API
        finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}