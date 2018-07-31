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
import com.glp.page.GLPInstructor_StudentPerformanceDetailsPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author Abhishek Sharda
 * @date March 30, 2018
 * @description: Verify Instructor should be able to reset the course for the
 *               student when the student's pre-assessment is "In Progress"
 *
 * 
 */
public class GLP_358027_VerifyInstructorResetStudentAssessmentInProgress
        extends BaseClass {
    public GLP_358027_VerifyInstructorResetStudentAssessmentInProgress() {
    }

    @Test(groups = { Groups.SANITY, Groups.HEARTBEAT, Groups.REGRESSION },
            enabled = true,
            description = "Verify Instructor reset the course for a single student when his pre-assessment is 'In Progress'")
    public void verifyResetStudentAssessmentInProgress()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify Instructor reset course for a single student when his pre-assessment is 'In Progress'");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_RESET"),
                    true);
            // Login to the application as a Instructor
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            // Automate the remaining steps of test case
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");
            objGLPLearnerCourseViewPage.verifyLogout();
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_RESET"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Mastery Settings page
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            // Navigate to the Performance dashboard page
            objGLPInstructorCourseViewPage
                    .navigateToInstructorDashboardFromCourseView();
            objGLPInstructorDashboardPage.switchToStudentListTab();
            objGLPInstructorDashboardPage
                    .navigateToLearnerProfile(learnerUserName);
            GLPInstructor_StudentPerformanceDetailsPage objGLPInstructor_StudentPerformanceDetailsPage = new GLPInstructor_StudentPerformanceDetailsPage(
                    reportTestObj, APP_LOG);
            objGLPInstructor_StudentPerformanceDetailsPage.clickOnElement(
                    "StudentDetailsResetSubscribedLearnerButton",
                    "Click on 'Reset' button on reset course.");
            objGLPInstructor_StudentPerformanceDetailsPage.clickOnElement(
                    "StudentDetailsResetPopUpWindow",
                    "Click on 'Reset' button on reset course pop up.");
            objGLPInstructor_StudentPerformanceDetailsPage.clickOnElement(
                    "CloseStudentDetailsCrossButton",
                    "Click on 'cross' button on Close student details option.");
            objGLPLearnerCourseViewPage.verifyLogout();
            objRestUtil.lockUnlockDiagnosticForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"));
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify Learner is logged in successfully.");
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();
            objGLPLearnerCourseHomePage.verifyText("CourseHomeStartYourPathBtn",
                    ResourceConfigurations
                            .getProperty("startPreAssessmentTestButton"),
                    "Verify 'Start Pre-assessment' button display again to verify reset course success.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
