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
package com.test.glp_sanity;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mukul.sehra
 * @date Feb 08, 2018
 * @description: Verify Lock/Unlock feature implementation for the Diagnostic
 *               Test via UI
 */
public class GLP_358039_VerifyLockUnlockCourseViaUI extends BaseClass {
    public GLP_358039_VerifyLockUnlockCourseViaUI() {
    }

    @Test(groups = { Groups.SANITY, Groups.HEARTBEAT, Groups.REGRESSION },
            enabled = true,
            description = "Verify Lock/Unlock feature implementation for the Diagnostic Test via UI")
    public void verifyLockUnlockDiagnosticViaUI() throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify Lock/Unlock feature implementation for the Diagnostic Test via UI");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create Learner subscribing the course
            // and unlock it via api
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_LOCKUNLOCK"),
                    true);

            // Login to the application as a learner
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Verify that the 'Start your path' button is available on the
            // welcome screen
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify that the 'Start Assessment' button is available on the welcome screen");

            // Logout learner from application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login the application as Instructor
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_LOCKUNLOCK"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to Instructor dashboard page
            objGLPInstructorCourseViewPage
                    .navigateToInstructorDashboardFromCourseView();
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

           /* // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();*/

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify 'Pre-assessment' is pre-selected in all modules drop down
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashBoardAllModules",
                    ResourceConfigurations
                            .getProperty("preAssessmentPreviewHeading"),
                    "Verify filter selected is Pre-Assessment");

            // Click the checkbox corresponding to learner you want to lock
            objGLPInstructorDashboardPage
                    .clickCheckboxCorrespondingToLearner(learnerUserName);

            // Click on 'Lock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardLockSelectedButton",
                    "Select the learner and Click on 'Lock Selected' button");

            // Verify 'Lock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("LockPreAssessmentTest"),
                    "Verify 'Lock PreAssessment' pop-up is present on UI.");

            // Click on 'Lock 1 now' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Lock 1 now' button.");

            // Logout instructor
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login to the application again as a learner
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();

            // Verify that after Diagnostic test being Locked by the Instructor,
            // the "Start your path" button is not available on the welcome
            // screen
            objGLPLearnerCourseHomePage.verifyText("CourseHomeLetTryText",
                    ResourceConfigurations
                            .getProperty("lockedDiagnosticWelcomeText"),
                    "Verify that the message to unlock the Diagnostic test is being displayed for the Locked course");
            objGLPLearnerCourseHomePage.verifyElementNotPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify that the 'Start Assessment' button is not available on the welcome screen");

            // Logout learner from the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login the application as Instructor and click the course tile to
            // navigate to the performance dashboard
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_LOCKUNLOCK"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            // Navigate to Instructor dashboard page
            objGLPInstructorCourseViewPage
                    .navigateToInstructorDashboardFromCourseView();

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify 'Pre-assessment' is pre-selected in all modules dropdown
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashBoardAllModules",
                    ResourceConfigurations
                            .getProperty("preAssessmentPreviewHeading"),
                    "Verify filter selected is Pre-Assessment");

            // Click the checkbox corresponding to learner you want to unlock
            objGLPInstructorDashboardPage
                    .clickCheckboxCorrespondingToLearner(learnerUserName);

            // Click on 'unlock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedButton",
                    "Select the learner and Click on 'Unlock Selected' button");

            // Verify 'Unlock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentTest"),
                    "Verify 'Unlock PreAssessment' pop-up is present on UI.");

            // Click on 'Unlock 1 now' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Unlock 1 now' button.");

            // Logout Instructor from application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login to the application as a learner
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();

            // Verify that after Diagnostic test being Unlocked by the
            // Instructor, the "Start your path" button is again available on
            // the welcome screen
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify that the Diagnostic test is unlocked and Leaner can see the Start Assessment button");

            // Logout of application
            objGLPLearnerCourseViewPage.verifyLogout();

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}