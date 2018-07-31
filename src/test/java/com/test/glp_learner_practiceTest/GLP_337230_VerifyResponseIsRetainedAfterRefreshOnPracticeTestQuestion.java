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
 * @author anuj.tiwari1
 * @date May 05, 2018
 * @description: To verify that Free Response question should be retained on
 *               clicking Browser Refresh after submitting the answer to a
 *               question ( 1st incorrect attempt)
 * 
 */
public class GLP_337230_VerifyResponseIsRetainedAfterRefreshOnPracticeTestQuestion
        extends BaseClass {
    public GLP_337230_VerifyResponseIsRetainedAfterRefreshOnPracticeTestQuestion() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER,
            Groups.FIBFREERESPONSE }, enabled = true,
            description = "To verify that the response is reatined in the free response after refreshing the question in Practice Test. ")

    public void verifyResponseIsRetainedInPractice() {
        startReport(getTestCaseId(),
                "To verify that the response is reatined in the free response after refreshing the question in Practice Test. ");
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

            // Navigate to Free Response Question
            objGLPLearnerPracticeTestPage.navigateToQuestionTypeOnPracticeTest(
                    ResourceConfigurations.getProperty("fibFreeResponse"),
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Fetching the question before refresh
            String questionOriginal = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            APP_LOG.info(
                    "Attempt one question in Practice Test. -----First Incorrect try----");

            // Attempt the Practice Test
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            // Refresh the page
            APP_LOG.info("Refresh the page");
            objGLPLearnerDiagnosticTestPage.refreshPage();

            // Fetching the question after refresh
            String questionAfterRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterRefresh);

            // Verifying that the value is retained after refresh
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestFreeResponseTextBox",
                    ResourceConfigurations
                            .getProperty("freeResponseInputValue"),
                    "Verify that the value entered by the user is retained after the refresh");

            APP_LOG.info(
                    "Attempt the same question again in Practice Test. -----Second Incorrect try----");

            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 1,
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Refresh the page
            APP_LOG.info("Refresh the page");
            objGLPLearnerDiagnosticTestPage.refreshPage();

            // Fetching the question after refresh
            String questionAfterSecondRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterSecondRefresh);

            // Verifying that the value is retained after refresh
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestFreeResponseTextBox",
                    ResourceConfigurations
                            .getProperty("freeResponseInputValue"),
                    "Verify that the value entered by the user is retained after the refresh");

            APP_LOG.info(
                    "Attempt the same question again in Practice Test. -----Third Incorrect try----");

            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 1,
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Refresh the page
            APP_LOG.info("Refresh the page");
            objGLPLearnerDiagnosticTestPage.refreshPage();

            // Fetching the question after refresh
            String questionAfterThirdRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTest1stQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterThirdRefresh);

            // Verifying that the value is retained after refresh
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestFreeResponseTextBox",
                    ResourceConfigurations
                            .getProperty("freeResponseInputValue"),
                    "Verify that the value entered by the user is retained after the refresh");

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
