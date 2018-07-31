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
 * @date March 20, 2018
 * @description: Verify 'Welcome Back' message behaviour when user click on
 *               different places on page
 *
 */
public class GLP_316552_VerifyWelcomeBackMessageBehaviour extends BaseClass {
    public GLP_316552_VerifyWelcomeBackMessageBehaviour() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify 'Welcome Back' message behaviour when user click on different places on page")
    public void verifyWelcomeBackMessageBehaviour()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify 'Welcome Back' message behaviour when user click on different places on page");
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

            // Login to the application as a Instructor
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to 'Welcome Screen Instructor' page
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = objGLPInstructorCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            // Navigate to 'Mastery Level' screen page
            objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Instructor dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Verify 'Welcome message' is present on UI
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message successfully.");

            // Click on 'Unlock Later' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLaterButton",
                    "Click on 'Unlock Later' button");

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login again as Instructor
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            // Verify 'Welcome Back' message pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message successfully.");

            // Click on 'Course Heading'
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardCourseHeading",
                    "Click on 'Course Heading'.");

            // Verify 'Welcome Back' message pop-up after clicking on 'Course
            // Heading'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message is present on UI after clicking on 'Course Heading'.");

            // Verify 'Welcome Back' message pop-up is present
            objGLPInstructorDashboardPage
                    .pressEscapeAndVerifyWelcomeBackPopUp();

            // Click on 'Unlock Later' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLaterButton",
                    "Click on 'Unlock Later' button");

            // Verify 'Welcome' message is not present on UI
            objGLPInstructorDashboardPage.verifyElementNotPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify 'Welcome back' message pop-up hides after clicking on 'Unlock Later' button.");

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login again as Instructor
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            // Verify 'Welcome Back' message pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message is present on UI after re-login and clicking on 'Course Tile' successfully.");

            // Click on Pearson logo
            objGLPLearnerCourseViewPage.clickOnElement("PearsonLogo",
                    "Click on Pearson Logo.");

            // Verify 'Welcome' message is not present on UI
            objGLPInstructorDashboardPage.verifyElementNotPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify 'Welcome back' message pop-up hides after clicking on 'Pearson Logo' successfully.");

            // Verify 'Course Tile' is present on UI
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify 'Course Tile' is present on UI.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            // Verify 'Welcome Back' message pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message is present on UI after clicking on 'Course Tile' successfully.");

            // Switch to 'Management tab'
            objGLPInstructorDashboardPage.switchToManagementTab();

            // Verify 'Welcome' message is not present on UI
            objGLPInstructorDashboardPage.verifyElementNotPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify 'Welcome back' message pop-up hides after switching to 'Management' tab successfully.");

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