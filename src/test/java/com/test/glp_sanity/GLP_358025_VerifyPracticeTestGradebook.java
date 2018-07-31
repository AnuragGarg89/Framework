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
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date March 29, 2018
 * @description: Validate that progress bar should remain same if learner exit
 *               the practice test and again go to same question
 * 
 */
public class GLP_358025_VerifyPracticeTestGradebook extends BaseClass {
    public GLP_358025_VerifyPracticeTestGradebook() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.HEARTBEAT },
            enabled = true,
            description = "Attempt practice test coming from attempting diagnostic test.")
    public void VerifyProgressBarNoChangePracticeTest() {
        startReport(getTestCaseId(),
                "Validate Attempt practice test coming from attempting diagnostic test.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {

            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify 'Rio' course tile is present
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            objGLPLearnerCourseMaterialPage
                    .scrollToElement("CourseMaterialLOStartButton");
            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on start button of first module");
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);
            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of first LO");
            // get Module name
            // String attemptedDiagnosticScore = objRestUtil
            // .getDiagnosticAndPracticeAttemptedScoreAndMaxScore(
            // learnerUserName, ResourceConfigurations.getProperty(
            // "consolePassword"),
            // "Practice", "Score");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");
            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            String attemptedPracticeScore = objRestUtil
                    .getDiagnosticAndPracticeAttemptedScoreAndMaxScore(
                            learnerUserName, ResourceConfigurations.getProperty(
                                    "consolePassword"),
                            "Practice", "Score");
            String maxPracticeScore = objRestUtil
                    .getDiagnosticAndPracticeAttemptedScoreAndMaxScore(
                            learnerUserName, ResourceConfigurations.getProperty(
                                    "consolePassword"),
                            "Practice", "MaxScore");
            // Login with Instructor
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
            // objGLPInstructorInstructorDashboardPage.verifyScoreInGradeBook(
            // instructorName,
            // ResourceConfigurations.getProperty("consolePassword"),
            // attemptedDiagnosticScore, "20", "Diagnostic");
            objGLPInstructorInstructorDashboardPage.verifyScoreInGradeBook(
                    instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    attemptedPracticeScore, maxPracticeScore, "Practice");

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}
