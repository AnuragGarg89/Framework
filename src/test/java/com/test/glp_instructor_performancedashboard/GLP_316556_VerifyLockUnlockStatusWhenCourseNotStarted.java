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
package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author pankaj.sarjal
 * @date March 24, 2018
 * @description: Verify 'Lock-Unlock' status when course 'Not Started'.
 *
 */
public class GLP_316556_VerifyLockUnlockStatusWhenCourseNotStarted
        extends BaseClass {
    public GLP_316556_VerifyLockUnlockStatusWhenCourseNotStarted() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify 'Lock-Unlock' status when course 'Not Started'.")
    public void verifyLockUnlockStatusWhenCourseNotStarted()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify 'Lock-Unlock' status when course 'Not Started'.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Create Learner subscribing the new course created by the
            // Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName,

                    false);

            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            // Login to the application as a Instructor
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to 'Welcome Screen Instructor' page
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = objGLPInstructorCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            // Navigate to 'Mastery Level' screen page
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Instructor dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Verify 'Unlock Later' button is present on UI
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockLaterButton",
                    "Verify 'Unlock Later' button is present successfully.");

            // Click on 'Unlock Later' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLaterButton",
                    "Click on 'Unlock Later' button");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify Course status is 'Not Started'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusNotStarted",
                    "Course status is 'Not Started' verified successfully.");

            // Verify 'Test' column is present on UI
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardTestColumn",
                    "'Test' column is present on UI.");

            // Verify 'Lock' button is disable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardlockStatusButton", "class",
                    "lock-icon lock font-none disabled",
                    "Verify 'Lock button' is disable for student.");

            // Verify 'Unlock' button is enable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardUnlockStatusButton", "class",
                    "lock-icon unlock",
                    "Verify 'Unlock button' is enable for student.");

            // Click on 'Unlock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockStatusButton",
                    "Click on 'Unlock' button");

            // Verify 'Unlock' button is disable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardUnlockDisableButton", "class",
                    "lock-icon unlock font-none disabled",
                    "Verify 'Unlock button' is disable for student.");

            // Verify 'Lock' button is enable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardlockEnableButton", "class",
                    "lock-icon lock",
                    "Verify 'Lock button' is enable for student.");

            // Click on 'Lock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardlockEnableButton",
                    "Click on 'Lock' button");

            // Verify 'Lock' button is disable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardlockStatusButton", "class",
                    "lock-icon lock font-none disabled",
                    "Verify 'Lock button' is disable for student.");

            // Verify 'Unlock' button is enable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardUnlockStatusButton", "class",
                    "lock-icon unlock",
                    "Verify 'Unlock button' is enable for student.");
        } finally {
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