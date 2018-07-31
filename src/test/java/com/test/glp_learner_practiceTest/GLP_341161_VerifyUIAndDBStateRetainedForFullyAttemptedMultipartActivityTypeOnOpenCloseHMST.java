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

/**
 * @author Ratnesh.Singh
 * @date May 31, 2018
 * @description: Test to verify that UI & DB state is retained for Fully
 *               attempted Multi-part Activity in-case user opens Student
 *               Initiated Help Me Solve This Learning Aid and closes it with or
 *               without attempting HMST parts.
 * 
 */
public class GLP_341161_VerifyUIAndDBStateRetainedForFullyAttemptedMultipartActivityTypeOnOpenCloseHMST
        extends BaseClass {

    public GLP_341161_VerifyUIAndDBStateRetainedForFullyAttemptedMultipartActivityTypeOnOpenCloseHMST() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.MULTIPART },
            enabled = true,
            description = "Test to verify that UI & DB state of fully attempted multipart activity type is retained when user opens and closes HMST LA.")

    public void
           VerifyUIAndDBStateRetainedForFullyAttemptedMultipartActivityTypeOnOpenCloseHMST() {
        startReport(getTestCaseId(),
                "Test to verify that UI & DB state of fully attempted multipart activity type is retained when user opens and closes HMST LA.");
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

            // learnerUserName = "GLP_Learner_341161_jgul";

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
                    .getText("PracticeTestMultipartQuestionText");

            // Remove all removes all performance entries with an entryType of
            // "resource" from the browser's performance data buffer
            objRestUtil.clearPerformanceEntries();

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that multipart question is displayed again after refresh
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after page refresh.");

            // Fetching the multipart question text after clearing browser
            // performance entries and refreshing page.
            String questionAfterRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after clearing browser
            // performance entries and refreshing page.
            objGLPLearnerPracticeTestPage.compareText(multipartQuestionText,
                    questionAfterRefresh,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after refreshing page" });

            // Get total number of parts in multipart question in Practice Test
            int totalNumberOfPartsInMultipart = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsInMultipartQuestion();
            // int totalNumberOfPartsInMultipart = 2;

            // Attempt all parts of multipart question in Practice Test
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(1,
                            totalNumberOfPartsInMultipart);

            // Fetching LA IDs of attempted part/s of Multipart Question
            Set<String> lAIdsForMultipart = objRestUtil
                    .getMatchingNetworkEntries(ResourceConfigurations
                            .getProperty("multipartLAIDPattern"),
                            "MultipartLAIds");

            // Fetch attempted UI state for all attempted parts of Multipart
            // question type.
            Map<String, Map<String, String>> attemptedUIStateForAllPartsBefore = objGLPLearnerPracticeTestPage
                    .getAttemptedUIStateOfAllPartsInMultipart(
                            totalNumberOfPartsInMultipart);

            // Fetch attempted DB state for all attempted parts of Multipart
            // question type.
            Map<String, Map<String, String>> attemptedDBStateForAllPartsBefore = objGLPLearnerPracticeTestPage
                    .getAttemptedDBStateOfAllPartsInMultipart(
                            totalNumberOfPartsInMultipart, lAIdsForMultipart,
                            learnerUserName, ResourceConfigurations
                                    .getProperty("consolePassword"));

            // Open Student initiated Help Me Solve This Learning Aid
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));

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

            // Verify that multipart question is displayed again after opening
            // and closing
            // Student Initiated Learning Aids
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after opening and closing Student Initiated Learning Aids.");

            // Fetching the multipart question text after opening and closing
            // HMST
            String questionAfterOpenCloseHMST = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after opening and closing HMST
            objGLPLearnerPracticeTestPage.compareText(multipartQuestionText,
                    questionAfterOpenCloseHMST,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after opening closing HMST" });

            // Fetch attempted UI state for all attempted parts of Multipart
            // question type after opening and closing HMST.
            Map<String, Map<String, String>> attemptedUIStateForAllPartsAfterOpenCloseHMST = objGLPLearnerPracticeTestPage
                    .getAttemptedUIStateOfAllPartsInMultipart(
                            totalNumberOfPartsInMultipart);

            // Fetch attempted DB state for all attempted parts of Multipart
            // question type after opening and closing HMST.
            Map<String, Map<String, String>> attemptedDBStateForAllPartsAfterOpenCloseHMST = objGLPLearnerPracticeTestPage
                    .getAttemptedDBStateOfAllPartsInMultipart(
                            totalNumberOfPartsInMultipart, lAIdsForMultipart,
                            learnerUserName, ResourceConfigurations
                                    .getProperty("consolePassword"));

            // Verify that UI state of fully attempted Multipart Question is
            // sustained after opening and closing student initiated Hep Me
            // Solve This LA
            objGLPLearnerPracticeTestPage
                    .compareSavedDBOrAttemptedUIStatesForAllPartsInMultipart(
                            totalNumberOfPartsInMultipart, "UI",
                            "Close/Reopen Help Me Sole This LA",
                            attemptedUIStateForAllPartsBefore,
                            attemptedUIStateForAllPartsAfterOpenCloseHMST);

            // Verify that DB state of fully attempted Multipart Question is
            // sustained after opening and closing student initiated Hep Me
            // Solve This LA
            objGLPLearnerPracticeTestPage
                    .compareSavedDBOrAttemptedUIStatesForAllPartsInMultipart(
                            totalNumberOfPartsInMultipart, "DB",
                            "Close/Reopen Help Me Sole This LA",
                            attemptedDBStateForAllPartsBefore,
                            attemptedDBStateForAllPartsAfterOpenCloseHMST);

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
