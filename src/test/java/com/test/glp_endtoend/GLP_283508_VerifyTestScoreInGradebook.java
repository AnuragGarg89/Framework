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
package com.test.glp_endtoend;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nitish.jaiswal
 * @date Sept 18, 2017
 * @description: Verify personalized study plan[PSP] should display after
 *               completing the Diagnostic test
 */
public class GLP_283508_VerifyTestScoreInGradebook extends BaseClass {
    public GLP_283508_VerifyTestScoreInGradebook() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.LEARNER },
            enabled = true,
            description = "To Verify user diagnostic score is matching with the record in Gradebook.")
    public void VerifyTestScoreInGradebook() throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify user diagnostic score is matching with the record in Gradebook.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);
            // Login in the application
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
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            String overallDiagnosticScore = Integer
                    .toString(objGLPLearnerDiagnosticTestPage
                            .getDiagnosticQuestionsCount());
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = objGLPLearnerDiagnosticTestPage
                    .attemptAdaptiveDiagnosticTest(0, 0, ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
            objGLPLearnerCourseMaterialPage.navigateToCourseViewPage();

            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify User is navigated back to course view page after clicking Pearson Logo.");
            objGLPLearnerCourseViewPage.verifyLogout();

            // Get attempted score for diagnostic
            String diagnosticTestscore = objRestUtil
                    .getDiagnosticAttemptedScore(learnerUserName,
                            ResourceConfigurations
                                    .getProperty("consolePassword"));

            // Get overall score for diagnostic
            // String diagnosticOverallscore =
            // objRestUtil.getDiagnosticOverallScore(learnerUserName);

            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Mastery Settings page
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            // Click on Export button
            // objGLPInstructorInstructorDashboardPage.handleUnlockPopupIfExists(
            // "InstructorDashBoardUnlockNowButton");
            objGLPInstructorInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardExportButton",
                    "Click on the Export button to download the gradebook");
            // Check whether the error page is not displayed
            objGLPInstructorInstructorDashboardPage.verifyElementNotPresent(
                    "GradebookExportErrorAlert",
                    "Verify instructor is not displayed the error screen on clicking the export button");
            objGLPInstructorInstructorDashboardPage.verifyScoreInGradeBook(
                    instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    diagnosticTestscore, overallDiagnosticScore, "Diagnostic");
        }
        // Delete User via API
        finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}
