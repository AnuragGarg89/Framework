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

import java.util.Map;
import java.util.Set;

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
 * @description: Verify that the score is updated as "0" in couchbase on
 *               incorrect attempt of single part FIB free response question in
 *               Practice Test.
 * 
 */
public class GLP_329366_VerifyScoreInDBOnIncorrectAttemptSinglePartFIBFreeResponseInPracticeTest
        extends BaseClass {
    public GLP_329366_VerifyScoreInDBOnIncorrectAttemptSinglePartFIBFreeResponseInPracticeTest() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that the score is updated as '0' in couchbase on incorrect attempt of single part FIB free response question in Practice Test.")
    public void
           VerifyScoreInDBOnIncorrectAttemptSinglePartFIBFreeResponseInPracticeTest() {
        startReport(getTestCaseId(),
                "Verify that the score is updated as '0' in couchbase on incorrect attempt of single part FIB free response question in Practice Test.");
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

            // Navigate to Free Response Question
            objGLPLearnerPracticeTestPage.navigateToQuestionTypeOnPracticeTest(
                    ResourceConfigurations.getProperty("fibFreeResponse"),
                    ResourceConfigurations
                            .getProperty("practiceTestSubmitButton"));

            // Remove all removes all performance entries with an entryType of
            // "resource" from the browser's performance data buffer
            objRestUtil.clearPerformanceEntries();

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Fetching question ID for single part free response question
            Set<String> questionIds = objRestUtil.getMatchingNetworkEntries(
                    ResourceConfigurations.getProperty("questionIDPattern"),
                    "QuestionIDs");

            // Get CouchBase Query
            String query = objGLPLearnerPracticeTestPage.getDBQuery(
                    learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Fetch initial total number of tries for free response question
            int totalNumberOfIntialTriesBeforeAttempt = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(1,
                            "PracticeTestRemainingTriesLabelforSinglePart");

            // Attempt FIB free response question with incorrect answer
            objGLPLearnerPracticeTestPage
                    .attemptSinglePartQuestionInPracticeTest();

            // Fetch number of tries left after attempting free response
            // question
            int totalNumberOfTriesLeftAfterAttempt = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(1,
                            "PracticeTestRemainingTriesLabelforSinglePart");

            // Fetch DB State saved in CouchBase for incorrectly attempted
            // single part FIB free response question in practice test
            Map<String, String> dbStateForAttemptedFIBFreeReponse = objGLPLearnerPracticeTestPage
                    .getDBStateForSinglePartQuestionInPracticeTest(query,
                            ResourceConfigurations
                                    .getProperty("dbStateRequiredAttributes"),
                            questionIds, ResourceConfigurations
                                    .getProperty("fibFreeResponse"));

            // Validate the DB state of attempted single part free response
            // question in practice test
            objGLPLearnerPracticeTestPage
                    .validateCouchBaseDBStateForCompletedSinglePartQuestionInPractice(
                            totalNumberOfIntialTriesBeforeAttempt,
                            totalNumberOfTriesLeftAfterAttempt,
                            dbStateForAttemptedFIBFreeReponse,
                            ResourceConfigurations
                                    .getProperty("fibFreeResponse"));

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
