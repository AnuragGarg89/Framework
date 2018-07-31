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
import com.autofusion.keywords.FindElement;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author ratnesh.singh
 * @date Jun 16, 2018
 * @description: To verify that MCQSA question UI state and Feedback should be
 *               retained after submitting the incorrect answer to a question on
 *               Browser Refresh or Exit Practice Test or Browser back
 *               operations (for single attempt & all attempts).
 * 
 */
public class GLP_337220_VerifyMCQSAStateSaveOnRefreshOrExitOrBrowserBack
        extends BaseClass {
    public GLP_337220_VerifyMCQSAStateSaveOnRefreshOrExitOrBrowserBack() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.MCQSA },
            enabled = true,
            description = "To verify that the response is reatined in the free response after refreshing the question in Practice Test. ")

    public void VerifyMCQSAStateSaveOnRefreshOrExitOrBrowserBack() {
        startReport(getTestCaseId(),
                "To verify that the UI state and feedback is retained in the MCQSA dropdown after refreshing the question in Practice Test.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        FindElement findElement = new FindElement();

        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

            // learnerUserName = "GLP_Learner_350594_hdTI";

            // Login to the GLP application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // objGLPLearnerCourseViewPage.clickOnElement(
            // "CourseViewCourseCardImage",
            // "Verify that Course Card is Clicked.");

            // Verify CourseTile is Present and navigate to Welcome Learner
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic first question
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Attempt the diagnostic for the created user
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on Module 11 collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElementContainsInnerText(
                    "CourseMaterialModuleTitleButton",
                    ResourceConfigurations.getProperty("module13Text"));

            // Click on 1st LO Start button under module 11
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedLOStartButtons",
                    ResourceConfigurations
                            .getProperty("subModule13_5AreaLabel"));

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Verify that Practice and apply as you go pop up gets displayed
            // and click on Got it
            objGLPLearnerPracticeTestPage.closePracticeAndApplyPopup();

            // Click on First EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=1,PracticeTestNumber=1",
                    "Click on First EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify whether the first question of practice test is
            // displayed
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestNumberOfTries",
                    "Verify that the first question of practice test is displayed to the learner");

            // Navigate to MCQSA Question
            objGLPLearnerPracticeTestPage.navigateToQuestionTypeOnPracticeTest(
                    ResourceConfigurations.getProperty("mcqSa"),
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Fetching the question before refresh
            String questionOriginal = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            APP_LOG.info(
                    "Attempt one question in Practice Test. -----First Incorrect try----");

            // Attempt the MCQSA question for first attempt
            objGLPLearnerPracticeTestPage.attemptUIRenderedQuestion(
                    objGLPLearnerPracticeTestPage.returnSetOfQuestion1(),
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Verify feedback present after first attempt
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be present after first attempt");

            // Fetch UI state of attempted MCQSA question after
            // first attempt and before first refresh
            String attemptedMCQSAUIState = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Refresh the page
            APP_LOG.info("Refresh the page");
            objGLPLearnerPracticeTestPage.refreshPage();

            // Fetching the question after refresh
            String questionAfterRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterRefresh,
                    new String[] { "yes", "MCQSA Question before any operation",
                            "MCQSA Question after refreshing page" });

            // Fetch UI state of attempted MCQSA question after
            // first refresh
            String attemptedMCQSAUIStateAfterFirstRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Verifying that the attempted values is retained after first
            // refresh
            objGLPLearnerPracticeTestPage.compareText(attemptedMCQSAUIState,
                    attemptedMCQSAUIStateAfterFirstRefresh,
                    new String[] { "yes",
                            "MCQSA Question UI State after attempt",
                            "MCQSA Question after UI State after refresh" });

            // Verify feedback present after first refresh
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be retained after refresh");

            // Click on Close Practice Test Button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on parctice test close button.");

            // Click on Leave Practice test button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLeaveButton",
                    "Click on Leave practice test button.");

            // Click on First EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=1,PracticeTestNumber=1",
                    "Click on First EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify whether the Practice Assessment player is displayed to the
            // learner
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestCloseButton",
                    "Verify Practice Assessment player is displayed to the learner");

            // Fetching the question after refresh
            String questionAfterExitPracticeTest = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after exit practice test
            // operation
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterExitPracticeTest,
                    new String[] { "yes", "MCQSA Question before any operation",
                            "MCQSA Question after exit practice test operation" });

            // Fetch UI state of attempted MCQSA question after
            // exit practice test operation
            String attemptedMCQSAUIStateAfterExitPracticeTest = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Verifying that the attempted values is retained after exit
            // practice test operation
            objGLPLearnerPracticeTestPage.compareText(attemptedMCQSAUIState,
                    attemptedMCQSAUIStateAfterExitPracticeTest,
                    new String[] { "yes",
                            "MCQSA Question UI State after attempt",
                            "MCQSA Question after UI State after exit practice test operation" });

            // Verify feedback present after exit practice test operation
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be retained after exit practice test operation");

            // Click on browser back button
            objGLPLearnerPracticeTestPage.clickBrowserBackButton(
                    "Click on browser back button from practice test");

            // Click on leave button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLeaveButton",
                    "Click on leave button on the pop up");

            // Click on First EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=1,PracticeTestNumber=1",
                    "Click on First EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify whether the Practice Assessment player is displayed to the
            // learner
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestCloseButton",
                    "Verify Practice Assessment player is displayed to the learner");

            // Fetching the question after browser back operation
            String questionAfterBrowserBack = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after browser back operation
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterBrowserBack,
                    new String[] { "yes", "MCQSA Question before any operation",
                            "MCQSA Question after browser back operation" });

            // Fetch UI state of attempted MCQSA question after
            // browser back operation
            String attemptedMCQSAUIStateAfterBrowserBack = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Verifying that the attempted values is retained after browser
            // back operation
            objGLPLearnerPracticeTestPage.compareText(attemptedMCQSAUIState,
                    attemptedMCQSAUIStateAfterExitPracticeTest,
                    new String[] { "yes",
                            "MCQSA Question UI State after attempt",
                            "MCQSA Question after UI State after browser back operation" });

            // Verify feedback present after browser back operation
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestFeedbackMessage",
                    "Feedback message should be retained after browser back operation");

            // Attempt question for all the remaining tries
            objGLPLearnerPracticeTestPage
                    .attemptSinglePartQuestionInPracticeTest();

            // Fetch selected choice of attempted MCQSA question after
            // all the attempts
            String selectedChoiceMCQSAUIStateAfterAllAttempts = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Fetch correct choice of attempted MCQSA question after
            // all the attempts
            String correctChoiceMCQSAUIStateAfterAllAttempts = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQACorrectChoiceText");

            // Refresh the page
            APP_LOG.info("Refresh the page");
            objGLPLearnerPracticeTestPage.refreshPage();

            // Fetch selected choice of attempted MCQSA question after all
            // attempts and refresh
            String selectedChoiceMCQSAUIStateAfterAllAttemptsAndRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQASelectedChoiceText");

            // Fetch correct choice of attempted MCQSA question after all
            // attempts and refresh
            String correctChoiceMCQSAUIStateAfterAllAttemptsAndRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMCSQACorrectChoiceText");

            // Verifying that the attempted values is retained after all
            // attempts and refresh
            objGLPLearnerPracticeTestPage.compareText(
                    selectedChoiceMCQSAUIStateAfterAllAttempts,
                    selectedChoiceMCQSAUIStateAfterAllAttemptsAndRefresh,
                    new String[] { "yes",
                            "MCQSA selected choice after all attempts",
                            "MCQSA selected choice after all attempts and refresh operation" });

            // Verifying that the attempted values is retained after all
            // attempts and refresh
            objGLPLearnerPracticeTestPage.compareText(
                    correctChoiceMCQSAUIStateAfterAllAttempts,
                    correctChoiceMCQSAUIStateAfterAllAttemptsAndRefresh,
                    new String[] { "yes",
                            "MCQSA selected choice after all attempts",
                            "MCQSA selected choice after all attempts and refresh operation" });

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
