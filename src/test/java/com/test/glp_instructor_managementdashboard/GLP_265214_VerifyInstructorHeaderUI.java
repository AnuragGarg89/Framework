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

package com.test.glp_instructor_managementdashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mayank.mittal
 * @date Nov 24, 2017
 * @description : Verify instructor header UI.
 */
public class GLP_265214_VerifyInstructorHeaderUI extends BaseClass {
    public GLP_265214_VerifyInstructorHeaderUI() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify instructor header UI.")

    public void verifyInstructorHeaderUI() {
        startReport(getTestCaseId(), "Verify instructor header UI.");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // Generate unique instructor userName
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Verify pearson logo
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PearsonLogo", "Verify pearson logo is displayed.");
            // Verify User name
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "LoginUserNameTextBox", "Verify user name is displayed.");
            // Navigate to welcome page
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            // Verify that the Get Started button is displayed on the welcome
            // page
            objProductApplicationWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify that instructor is navigated to Welcome screen.");
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PearsonLogo",
                    "Verify pearson logo is displayed on Welcome screen.");
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "LoginUserNameTextBox",
                    "Verify user name is displayed on Welcome screen.");
            // Navigate to pre-assessment page
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            // verify next button is present
            objProductApplicationMasterySettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify that user is navigated to the Pre-assessment mastery lavel page");
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PearsonLogo",
                    "Verify pearson logo is displayed on Pre-Assessment screen.");
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "LoginUserNameTextBox",
                    "Verify user name is displayed on Pre-Assessment screen.");

            // Navigate to the Management dashboard page
            objProductApplicationMasterySettingPage
                    .navigateToInstructorDashboard();
            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboard = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            // Verify Edit button on management dashboard.
            objProductApplicationManagementDashboard.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor navigated to management dashboard.");

            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PearsonLogo", "Verify 'Pearson Logo' is displayed.");
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "LoginUserNameTextBox", "Verify 'User Name' is displayed.");

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