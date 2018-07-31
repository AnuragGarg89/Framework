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
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author pankaj.sarjal
 * @date March 27, 2018
 * @description: Verify 'PreAssessment' status when clicked on 'Unlock' button
 *
 */
public class GLP_316563_VerifyPreAssesmentStatusWhenClickUnlock
        extends BaseClass {
    public GLP_316563_VerifyPreAssesmentStatusWhenClickUnlock() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.LEARNER, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify 'PreAssessment' status when clicked on 'Unlock' button.")
    public void verifyPreAssesmentStatusWhenClickUnlock()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify 'PreAssessment' status when clicked on 'Unlock' button.");
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

            // Click on 'Unlock Later' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLaterButton",
                    "Click on 'Unlock Later' button");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify 'Test' column is present on UI
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardTestColumn",
                    "'Test' column is present on UI.");

            // Verify color of 'Unlock' button before clicking
            objGLPInstructorDashboardPage.verifyBackgourndColor(
                    "InstructorDashBoardUnlockStatusButton",
                    ResourceConfigurations
                            .getProperty("unlockButtonColorBeforeClick"),
                    "Verify color code of 'Unlock button' before clicking on it. ");

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Click on Pre-assessment button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Click on 'Pre-Assessment option");

            // Click on 'Unlock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockStatusButton",
                    "Click on 'Unlock' button");

            // Click on 'Lock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardlockEnableButton",
                    "Click on 'Lock' button");

            // Verify 'Unlock' button is enable
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardUnlockStatusButton", "class",
                    "lock-icon unlock",
                    "Verify 'Unlock button' is enable for student.");

            // Verify color of 'Unlock' button before clicking
            objGLPInstructorDashboardPage.verifyBackgourndColor(
                    "InstructorDashBoardUnlockStatusButton",
                    ResourceConfigurations
                            .getProperty("unlockButtonColorBeforeClick"),
                    "Verify color code of 'Unlock button' before clicking on it. ");

            // Click on 'Unlock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockStatusButton",
                    "Click on 'Unlock' button");

            // Verify color of 'Unlock' button after clicking
            objGLPInstructorDashboardPage.verifyBackgourndColor(
                    "InstructorDashBoardUnlockDisableButton",
                    ResourceConfigurations
                            .getProperty("unlockButtonColorAfterClick"),
                    "Verify color code of 'Unlock button' after clicking on it. ");

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login as 'Learner'
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile is present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Verify 'Start Pre-Assessment' button is present on UI
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify 'Pre-Assessment' is unlock for student.");

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