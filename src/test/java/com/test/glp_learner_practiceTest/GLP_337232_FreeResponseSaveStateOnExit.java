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
package com.test.glp_learner_practiceTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date May 07, 2018
 * @description: Free Response question should be retained on exiting the
 *               practice test submitting the answer to a question ( 1st
 *               incorrect attempt)
 * 
 */
public class GLP_337232_FreeResponseSaveStateOnExit extends BaseClass {
    public GLP_337232_FreeResponseSaveStateOnExit() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Free Response question should be retained on exiting the practice test submitting the answer to a question(1st incorrect attempt)")
    public void VerifyFreeResponseStateManagement() {
        startReport(getTestCaseId(),
                "Free Response question should be retained on exiting the practice test after submitting the answer to a question (1st incorrect attempt)");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome LearnerScreen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on first Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialToggleModulesArrow",
                    "Verify the first Module collapsed arrow is clicked.");

            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on start button of first module");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of first LO");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify number of tries should be displayed as 3
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestNumberOfTries",
                    ResourceConfigurations.getProperty("numberOfTries"),
                    "Verify number of tries shoule be displayed as 3");

            // Navigate to free response question type
            objGLPLearnerPracticeTestPage.navigateToQuestionTypeOnPracticeTest(
                    ResourceConfigurations.getProperty("fibFreeResponse"),
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Attempt practice test questions
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on cross icon on practice test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on cross icon on practice test");

            // Click on leave button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpLeaveButton",
                    "Click on leave button on the pop up");

            // Click on continue button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestContinueBtn", "Click on continue button");

            // Verify save state for free response question
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestFreeResponseTextBox",
                    ResourceConfigurations
                            .getProperty("freeResponseInputValue"),
                    "Free Response question should be retained on exiting the practice test submitting the answer to a question(1st incorrect attempt)");

            // Verify feedback present after coming back on the same question
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be retained after coming back to the practice test");

            // Attempt practice test question in 2nd attempt
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on cross icon on practice test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on cross icon on practice test");

            // Click on leave button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpLeaveButton",
                    "Click on leave button on the pop up");

            // Click on continue button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestContinueBtn", "Click on continue button");

            // Verify save state for free response question in 2nd attempt
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestFreeResponseTextBox",
                    ResourceConfigurations
                            .getProperty("freeResponseInputValue"),
                    "Free Response question should be retained on exiting the practice test submitting the answer to a question(2nd incorrect attempt)");

            // Verify feedback present after coming back on the same question
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be retained after coming back to the practice test");

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }
}
