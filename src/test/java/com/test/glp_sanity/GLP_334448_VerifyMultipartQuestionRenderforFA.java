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
 * @author mehak.verma
 * @date May 10, 2018
 * @description: To verify that multipart questions render properly along with
 *               number of tries for formative assessment
 */

public class GLP_334448_VerifyMultipartQuestionRenderforFA extends BaseClass {

    public GLP_334448_VerifyMultipartQuestionRenderforFA() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.MULTIPART,
            Groups.SANITY }, enabled = true,
            description = "To verify that multipart questions render properly along with number of tries for formative assessment")
    public void
           verifyStudentAbleToRetryTheCurrentQuestionAfterSubmitingResponseIncorrect() {
        startReport(getTestCaseId(),
                "To verify that multipart questions render properly along with number of tries for formative assessment");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        int totalnumberOfParts = 0;
        int numberOfTriesLeft = 0;
        int attemptCnt = 0;
        FindElement findElement = new FindElement();

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

            // Click on Module 13 collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElementContainsInnerText(
                    "CourseMaterialModuleTitleButton",
                    ResourceConfigurations.getProperty("module13Text"));

            // Click on 5th Submodule Start button under module 13
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedSubModuleStartButtons",
                    ResourceConfigurations
                            .getProperty("subModule13_5AreaLabel"));

            // Verify that page is loaded
            findElement.checkPageIsReady();
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);
            // Verify that Practice and apply as you go pop up gets displayed
            // and click on Got it
            objGLPLearnerPracticeTestPage.closePracticeAndApplyPopup();

            // Click on practice test of Second LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeTestSecondLO",
                    "Click on Second LO");

            // Click on Second LO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSecondLOPracticeQuiz",
                    "Click on practice quiz of Second LO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify whether the first question of practice test is
            // displayed
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestCloseButton",
                    "Verify Practice Assessment player is displayed to the learner");

            // Click on Diagnostic Got It button if displayed
            objGLPLearnerPracticeTestPage.clickOnPracticeGotItButtonIfPresent();

            // Navigate to Multipart Question
            objGLPLearnerPracticeTestPage.navigateToMultipartinPracticeTest();

            // Get total number of parts in multipart question type.
            totalnumberOfParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsInMultipartQuestion();

            // Fetching the question text for Multipart Question
            String questionOriginal = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Attempt first part of multipart question in Practice test
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(1, 1);

            // Verify that user is not able to attempt/submit first part
            // after finishing all the attempts of first part.

            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=1",
                    "Verify that Submit/Try Again button is not visible to user after user consumes all the tries for part: "
                            + 1);

            // Verify that second part is visible after finishing all
            // the attempts of first part.

            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipartQuestionDynamicPart:dynamicReplace=2",
                    "Verify that second part of Multipart Question is visible to user only after finishing all tries for part: "
                            + 1);

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Fetching the question text after refreshing Multipart
            // Question
            String questionAfterRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterRefresh);

            // Verify that even after refreshing page user is not able
            // to attempt/submit first part after finishing all the
            // attempts of first part.
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=1",
                    "Verify that after refreshing page also Submit/Try Again button is not visible to user after user consumes all the tries for part: "
                            + 1);

            // Click on Close Practice Test Button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on parctice test close button.");

            // Click on Leave Practice test button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLeaveButton",
                    "Click on Leave practice test button.");

            // Click on practice test of Second LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeTestSecondLO",
                    "Click on Second LO");

            // Click on Second LO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSecondLOPracticeQuiz",
                    "Click on practice quiz of Second LO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Fetching the question text after closing and reopening
            // practice test
            String questionAfterCloseReOpen = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after closing and
            // reopening
            // practice test
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterCloseReOpen);

            // Verify that even after closing and reopening Practice
            // test user is not able to attempt/submit first part after
            // finishing all the attempts of first part.
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=1",
                    "Verify that after closing and reopening the practice test also Submit/Try Again button is not visible to user after user consumes all the tries for part: "
                            + 1);

            // Verify that third part is not visible until user
            // finishes
            // second part
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartQuestionDynamicPart:dynamicReplace=3",
                    "Verify that third part of Multipart Question is not visible to user.");

            // Attempt second part of multipart question in Practice
            // test
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(2, 2);

            // Verify that user is not able to attempt/submit second part
            // after finishing all the attempts of second part.

            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=2",
                    "Verify that Submit/Try Again button is not visible to user after user consumes all the tries for part: "
                            + 2);

            // Verify that third part is visible after finishing all the
            // attempts of second part if second part is not the last
            // part.

            if (totalnumberOfParts != 2) {
                objGLPLearnerPracticeTestPage.verifyElementPresent(
                        "PracticeTestMultipartQuestionDynamicPart:dynamicReplace=3",
                        "Verify that third part of Multipart Question is visible to user only after finishing all tries for part: "
                                + 2);
            }

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Fetching the question text after refreshing Multipart
            // Question
            String questionAfterSecondRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterSecondRefresh);

            // Verify that even after refreshing page user is not able
            // to attempt/submit first part after finishing all the
            // attempts of first part.

            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=1",
                    "Verify that after refreshing page also Submit/Try Again button is not visible to user after user consumes all the tries for part: 1");

            // Verify that even after refreshing page user is not able
            // to attempt/submit second part after finishing all the
            // attempts of second part.

            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=2",
                    "Verify that after refreshing page also Submit/Try Again button is not visible to user after user consumes all the tries for part: "
                            + 2);

        }

        finally {
            /*
             * if (unpublishData.equalsIgnoreCase("TRUE")) {
             * objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
             * ResourceConfigurations.getProperty("consolePassword"));
             * System.out.println("Unpublish data from couchbase DB"); }
             */
            webDriver.quit();
            webDriver = null;
        }
    }

}
