package com.test.glp_learner_practiceTest;

import java.util.Map;
import java.util.Set;

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
 * @author Ratnesh.Singh
 * @date May 6, 2018
 * @description: Test to verify that in-case of incorrect answer feedback should
 *               get displayed under Help Me Solve This learning Aid as an
 *               Assessment. Also verify values of 'isCorrectAnswer', 'score' &
 *               'status' in CouchBase DB for corresponding incorrect attempt.
 * 
 */

public class GLP_334239_VerifyUIAndDBStateForIncorrectAnswerInLearningAidAsAssessment
        extends BaseClass

{

    public GLP_334239_VerifyUIAndDBStateForIncorrectAnswerInLearningAidAsAssessment() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify that in-case of incorrect answer feedback should get displayed under Help Me Solve This learning Aids as an Assessment.")

    public void VerifyUIAndDBForIncorrectAnswerInLearningAidAsAssessment() {
        startReport(getTestCaseId(),
                "Test to verify that in-case of incorrect answer feedback should get displayed under Help Me Solve This learning Aids as an Assessment.");
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

            // learnerUserName = "GLP_Learner_334239_lZnn";

            // Login to the GLP application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

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
                    ResourceConfigurations.getProperty("module11Text"));

            // Click on 1st LO Start button under module 11
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedLOStartButtons",
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"));

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
                    "PracticeTestCloseButton",
                    "Verify Practice Assessment player is displayed to the learner");

            // Click on Practice Test Got It button if displayed
            objGLPLearnerPracticeTestPage.clickOnPracticeGotItButtonIfPresent();

            // Navigate Learning Aids As an Assessment in Practice Test
            objGLPLearnerPracticeTestPage
                    .navigateToLAidsAsAssessmentinPracticeTest();

            // Remove all removes all performance entries with an entryType of
            // "resource" from the browser's performance data buffer
            objRestUtil.clearPerformanceEntries();

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Verify that Learning Aids as Assessment is displayed again after
            // clearing browser performance entries and refreshing page.
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "PracticeTestLearningAidAccessmentHeader",
                    "Verify Learning Aids as Assessment is displayed again after clearing browser performance entries and refreshing page.");

            // Attempt Help Me Solve This Learning Aid as an Assessment under
            // Practice Test and
            // Verify that negative feed is getting displayed for
            // interactive part
            int partWithNegativeFeedBack = objGLPLearnerPracticeTestPage
                    .verifyNegativeFeedbackForInteractivePartinLA(false);

            // Fetching LA IDs of attempted parts of in Learning Aids as an
            // Assessment
            Set<String> lAIdsForHMST = objRestUtil.getMatchingNetworkEntries(
                    ResourceConfigurations.getProperty("hmstLAIDPattern"),
                    "HMSTLAIds");

            // Get CouchBase Query
            String query = objGLPLearnerPracticeTestPage.getDBQuery(
                    learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Fetch DB State saved in CouchBase for Learning Aids as Assessment
            // part with negative Feedback.

            Map<String, String> dataMapLAAsAssessmentPartWithNegativeFeedback = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            partWithNegativeFeedBack, query,
                            ResourceConfigurations.getProperty(
                                    "dbStateRequiredAttributes"),
                            lAIdsForHMST);

            // Validate the DB State saved in CouchBase for Learning Aids as
            // Assessment part with negative Feedback.
            objGLPLearnerPracticeTestPage
                    .validateCouchBaseDBStateForHMSTOrHMSTAssessmentNegativeFeedbackPart(
                            partWithNegativeFeedBack,
                            dataMapLAAsAssessmentPartWithNegativeFeedback,
                            true);

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }

}
