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
 * @date June 18, 2018
 * @description: Verify that number of attempts will be counted after Learner
 *               completed the Practice Quiz and this count gets incremented on
 *               every complete re-attempt of Practice Quiz.
 * 
 */
public class GLP_329402_VerifyNumberOfAttemptsCountOnPracticeTestGetsIncrementedOnEveryCompleteReAttempt
        extends BaseClass {
    public GLP_329402_VerifyNumberOfAttemptsCountOnPracticeTestGetsIncrementedOnEveryCompleteReAttempt() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that number of attempts gets incremented on every complete re-attempt of Practice Quiz.")
    public void
           VerifyNumberOfAttemptsCountOnPracticeTestGetsIncrementedOnEveryCompleteReAttempt() {
        startReport(getTestCaseId(),
                "Verify that number of attempts gets incremented on every complete re-attempt of Practice Quiz.");
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
                    ResourceConfigurations.getProperty("lo13_1"));

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

            // Attempt practice test
            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Verify practice test summary screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestSummaryScreenQuizResultText",
                    "Verify learner is successfully navigated to practice test Summery page.");

            // Verify "1attempts" text
            objGLPLearnerPracticeTestPage.verifyText(
                    "PraciceTestCountAttemptNumber",
                    ResourceConfigurations.getProperty("attemptNumber1"),
                    "Verify practice test '1attempts' text");

            // Click on practice again button on practice test summary page
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSummarypagePracticeAgainButton",
                    "Click on practice again button on practice test summary page");

            // Verify whether the first question of practice test is
            // displayed
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestNumberOfTries",
                    "Verify that the first question of practice test is displayed to the learner");

            // Re-Attempt practice test
            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Verify practice test summary screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestSummaryScreenQuizResultText",
                    "Verify learner is successfully navigated to practice test Summery page after second complete attempt.");

            // Verify "2attempts" text
            objGLPLearnerPracticeTestPage.verifyText(
                    "PraciceTestCountAttemptNumber",
                    ResourceConfigurations.getProperty("attemptNumber2"),
                    "Verify practice test '2attempts' text");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
