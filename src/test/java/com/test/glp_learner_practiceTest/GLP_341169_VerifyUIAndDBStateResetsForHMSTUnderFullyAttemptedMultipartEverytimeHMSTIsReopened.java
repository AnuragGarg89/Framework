package com.test.glp_learner_practiceTest;
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
 * @author Ratnesh.Singh
 * @date May 2, 2018
 * @description: Test to verify that UI & DB state resets for HMST under Fully
 *               attempted Multi-part activity every time user closes and
 *               reopens student initiated learning aids.
 * 
 */

public class GLP_341169_VerifyUIAndDBStateResetsForHMSTUnderFullyAttemptedMultipartEverytimeHMSTIsReopened
        extends BaseClass {

    public GLP_341169_VerifyUIAndDBStateResetsForHMSTUnderFullyAttemptedMultipartEverytimeHMSTIsReopened() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.MULTIPART },
            enabled = true,
            description = "Test to verify that UI & DB state of student initiated Help Me Solve This Learning Aids under fully attempted multipart gets reset every time user reopens HMST LA.")

    public void
           VerifyUIAndDBStateResetsForHMSTUnderFullyAttemptedMultipartEverytimeHMSTIsReopened() {
        startReport(getTestCaseId(),
                "Test to verify that UI & DB state of student initiated Help Me Solve This Learning Aids under fully attempted multipart gets reset every time user reopens HMST LA.");
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

            // learnerUserName = "GLP_Learner_341169_bJcH";

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
            // Screen
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

            // Click on Module 13 collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElementContainsInnerText(
                    "CourseMaterialModuleTitleButton",
                    ResourceConfigurations.getProperty("module13Text"));

            // Click on 5th LO Start button under module 13
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedLOStartButtons",
                    ResourceConfigurations
                            .getProperty("subModule13_5AreaLabel"));

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Verify that Practice and apply as you go pop up gets displayed
            // and click on Got it
            objGLPLearnerPracticeTestPage.closePracticeAndApplyPopup();

            // Click on Second EO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEO:EONumber=2", "Click on Second EO");

            // Click on Second EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=2,PracticeTestNumber=1",
                    "Click on Second EO Practice Quiz");

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

            // Navigate to Multipart Question under Pratice Test
            objGLPLearnerPracticeTestPage
                    .navigateToMultipartinPracticeTestWithHMST();

            // Fetching the question text for Multipart Question
            String multipartQuestionText = objGLPLearnerPracticeTestPage
                    .getText(
                            "PracticeTestMultipartDynamicAssessmentText:partToReplace=1,assessmentToReplace=1");

            // Get total number of parts in multipart question in Practice Test
            int totalNumberOfPartsInMultipart = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsInMultipartQuestion();
            // int totalNumberOfPartsInMultipart = 2;

            // Attempt all parts of multipart question in Practice Test
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(1,
                            totalNumberOfPartsInMultipart);

            // Remove all removes all performance entries with an entryType of
            // "resource" from the browser's performance data buffer
            objRestUtil.clearPerformanceEntries();

            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));

            // Get the text of first part assessment of Help Me Solve This
            // Learning Aids
            String hmstFirstPartAssessmentText = objGLPLearnerPracticeTestPage
                    .getText(
                            "PracticeTestHMSTLearningAidsDynamicAssessmentText:partToReplace=1,assessmentToReplace=1");

            // Verify that HMST loaded with same question as fully attempted
            // multi-part question
            objGLPLearnerPracticeTestPage.compareText(multipartQuestionText,
                    hmstFirstPartAssessmentText,
                    new String[] { "yes",
                            "Multipart Question Text for first assessment before opening HMST",
                            "HMST Question Text for first assessment when first time opening HMST" });

            // Fetch total number of in HMST Learning Aids
            int totalNumberOfPartsToAttempt = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();

            // Navigate to interactive part of HMST Learning Aids
            int interactivePartIndex = objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalNumberOfPartsToAttempt);

            // Fetch question Text/UI state before attempting Interactive Part
            // of Learning Aids
            String uiStateInteractivePartBeforeAttempt = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(interactivePartIndex);

            // Attempt Interactive part completely.
            objGLPLearnerPracticeTestPage.attemptInteractivePartinHMSTLA(
                    interactivePartIndex, true, true);

            // Fetch question Text/UI state after attempting Interactive Part
            // of Learning Aids
            String uiStateInteractivePartAfterAttemptingHMST = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(interactivePartIndex);

            // Fetching LA IDs of attempted parts of in Help Me Solve This
            // Learning Aids
            Set<String> lAIdsForHMST = objRestUtil.getMatchingNetworkEntries(
                    ResourceConfigurations.getProperty("hmstLAIDPattern"),
                    "HMSTLAIds");

            // Close Student initiated HMST Learning Aids through Close Icon on
            // HMST Window
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "LearningAidsHeaderCloseIcon",
                    "Click on Close Icon under Student initiated 'Help Me Solve This' Learning Aids.");

            // Verify that Student initiated Learning Aids gets closed and no
            // more visible after clicking close icon
            objGLPLearnerPracticeTestPage.verifyElementIsNotVisible(
                    "LearningAidsBody",
                    "Verify that Student initiated Learning Aids gets closed and no more visible after clicking Close Icon.");

            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));

            // Get the text of first part assessment of Help Me Solve This
            // Learning Aids
            String hmstFirstPartAssessmentTextAfterCloseReopenHMST = objGLPLearnerPracticeTestPage
                    .getText(
                            "PracticeTestHMSTLearningAidsDynamicAssessmentText:partToReplace=1,assessmentToReplace=1");

            // Verify that HMST loaded with same question as fully attempted
            // multi-part question every time user closes and reopens HMST
            objGLPLearnerPracticeTestPage.compareText(multipartQuestionText,
                    hmstFirstPartAssessmentTextAfterCloseReopenHMST,
                    new String[] { "yes",
                            "Multipart Question Text for first assessment before opening HMST",
                            "HMST Question Text for first assessment after close-reopen HMST" });

            // Verify that second part of HMST Learning Aids is not visible
            objGLPLearnerPracticeTestPage.verifyElementIsNotVisible(
                    "PracticeTestHMSTLearningAidsPartDynamicLocator:partToReplace=2",
                    "Verify that second part of Student initiated Learning Aids is not visible after user Reopens Learning Aids.");

            // Navigate to interactive part of HMST Learning Aids
            objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalNumberOfPartsToAttempt);

            // Fetch question Text/UI state after attempting closing and
            // reopening attempted HMST Learning Aids
            String uiStateInteractivePartAfterCloseReOpenHMST = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(interactivePartIndex);

            // Verify that UI state of HMST interactive part is not same as
            // attempted state of that part after close-reopen HMST.
            objGLPLearnerPracticeTestPage.compareText(
                    uiStateInteractivePartAfterAttemptingHMST,
                    uiStateInteractivePartAfterCloseReOpenHMST,
                    new String[] { "yes",
                            "UI State of Interactive part after attempt when first time opening HMST",
                            "UI State of Interactive part after close-reopen HMST",
                            "ReverseCompare" });

            // Verify that UI state of HMST interactive part resets/same as
            // before attempting that part after close-reopen HMST.
            objGLPLearnerPracticeTestPage.compareText(
                    uiStateInteractivePartBeforeAttempt,
                    uiStateInteractivePartAfterCloseReOpenHMST,
                    new String[] { "yes",
                            "UI State of Interactive part before attempt when first time opening HMST",
                            "UI State of Interactive part after close-reopen HMST" });

            // Get CouchBase Query
            String query = objGLPLearnerPracticeTestPage.getDBQuery(
                    learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Fetch DB State saved in CouchBase for attempted interactive part
            // of HMST Learning Aids.
            Map<String, String> dataMapInteractivePartHMST = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            interactivePartIndex, query,
                            "noOfTriesAttempted|status", lAIdsForHMST);

            // Verify that DB state gets reset for interactive part of HMST
            // Learning Aids.
            objGLPLearnerPracticeTestPage
                    .verifyDBStateResetOfAttemptedPartInHMST(
                            interactivePartIndex, dataMapInteractivePartHMST);

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }

}
