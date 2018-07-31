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
package com.test.glp_instructor_masterysetting;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date Dec 7, 2017
 * @description : Verify that the threshold value added by the instructor on
 *              Pre- Assessment mastery setup page is auto-saved on navigating
 *              away from the page.
 */
public class GLP_351629_VerifyMasteryThresholdAutoSaveFunctionalityOnLogout
        extends BaseClass {
    public GLP_351629_VerifyMasteryThresholdAutoSaveFunctionalityOnLogout() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED, Groups.SANITY, Groups.HEARTBEAT },
            enabled = false,
            description = "Verify that the threshold value added by the instructor on Pre- Assessment mastery setup page is auto-saved on navigating away from the page")
    public void verifyMasteryThresholdAutoSave() {
        startReport(getTestCaseId(),
                "Verify that the threshold value added by the instructor on Pre- Assessment mastery setup page is auto-saved on navigating away from the page");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Login to the application as an Instructor
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Welcome page for Instructor.
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            // Verify that the Get Started button is displayed on the welcome
            // page
            objProductApplicationWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify that instructor is navigated to Welcome screen.");

            GLPInstructor_MasterySettingPage objProductApplicationMasterSettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Pre Assessment mastery level page.
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            objProductApplicationMasterSettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify that user is navigated to the Pre-assessment mastery lavel page");

            // Set mastery level in input field
            objProductApplicationMasterSettingPage.enterInputData(
                    "MasterySettingTextBox",
                    ResourceConfigurations
                            .getProperty("preAssessmentThresholdValidValue1"),
                    "Verify that instrucor updated mastery threshold value in text box");
            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationMasterSettingPage.clickOnElement(
                    "PreAssessmentMasteryNextBtn",
                    "Click on the Next button displayed on the Pre Assessment mastery page.");

            objProductApplicationCourseViewPage.verifyLogout();
            // Login to the application again with same Instructor
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Verify instructor has navigated to Performance Dashboard page.");

            // Switch to the Management tab
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();
            objProductApplicationMasterSettingPage.verifyElementPresent(
                    "InstructorManagementDashboardEdit",
                    "Verify Edit button on 'Pre-assessment mastery level'");

            objProductApplicationMasterSettingPage.clickOnElement(
                    "InstructorManagementDashboardEdit",
                    "Click on edit button at 'Pre-assessment mastery level'");
            // verify mastery threshold value is auto-saves
            objProductApplicationMasterSettingPage.verifyElementAttributeValue(
                    "MasterySettingTextBox", "value",
                    ResourceConfigurations
                            .getProperty("preAssessmentThresholdValidValue1"),
                    "Verify that threshold value is autosaved.");
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementSaveButton",
                    "Click on save button at 'Pre-assessment mastery level'.");
            objProductApplicationCourseViewPage.verifyLogout();

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