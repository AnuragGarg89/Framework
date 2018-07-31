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
import com.autofusion.constants.Constants;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.autofusion.util.DiagnosticAttemptThroughAPI;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mukul.sehra
 * @date Apr 05, 2018
 * @description: Verify Unlock button state is Disabled when student is either
 *               attempting the test or the test has been completed
 */
public class GLP_321283_VerifyUnlockButtonDisabled extends BaseClass {
    public GLP_321283_VerifyUnlockButtonDisabled() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.LEARNER,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify Unlock button state is Disabled when student is either attempting the test or the test has been completed")
    public void verifyUnlockButtonDisabledForPreAssessment()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify Unlock button state is Disabled when student is either attempting the test or the test has been completed");
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
                    instructorName, true);

            // Login as the learner
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Click on 'Start Pre-assessment' button
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();

            // Attempt few questions on Diagnostic test
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
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
            objGLPLearnerCourseHomePage.verifyText("CourseHomeStartYourPathBtn",
                    ResourceConfigurations.getProperty("continue"),
                    "Verify User is redirected to continue diagnostic screen to continue test.");

            // Logout of Learner
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login to the application as a Instructor
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to Instructor's performance dashboard page
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on course tile and verfify Instructor navigated to Wecome Screen");
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Click on Pre-assessment button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Click on 'Pre-Assessment option");

            // Assert that diagnostic test status is displayed as testing
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusTesting",
                    "Verify that the diagnostic test status is 'Testing'");

            // Assert unlock button is disabled
            if (objGLPInstructorDashboardPage.isButtonDisabled(
                    "InstructorDashBoardUnlockStatusButton")) {
                logResultInReport(
                        Constants.PASS + ": The 'Unlock' button is Disabled",
                        "Verify that the 'Unlock' button is Disabled",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": The 'Unlock' button is Not Disabled",
                        "Verify that the 'Unlock' button is Disabled",
                        reportTestObj);
            }

            // Attempt Diagnostic test via API
            DiagnosticAttemptThroughAPI attemptDiagnostic = new DiagnosticAttemptThroughAPI(
                    reportTestObj, APP_LOG);
            attemptDiagnostic.attemptDiagnosticTestThroughAPI(learnerUserName,
                    instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Refresh the page
            objGLPLearnerDiagnosticTestPage.refreshPage();

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Click on Pre-assessment button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Click on 'Pre-Assessment option");

            // Assert that diagnostic test status is displayed as completed
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusCompleted",
                    "Verify that the diagnostic test status is 'Completed'");

            // Assert unlock button is disabled
            if (objGLPInstructorDashboardPage.isButtonDisabled(
                    "InstructorDashBoardUnlockStatusButton")) {
                logResultInReport(
                        Constants.PASS + ": The 'Unlock' button is Disabled",
                        "Verify that the 'Unlock' button is Disabled",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": The 'Unlock' button is Not Disabled",
                        "Verify that the 'Unlock' button is Disabled",
                        reportTestObj);
            }

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}