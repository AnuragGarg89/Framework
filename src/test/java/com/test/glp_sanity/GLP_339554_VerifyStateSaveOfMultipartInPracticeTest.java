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
 * @date May 14, 2018
 * @description: Verify that the UI and DB State for multipart question is saved
 *               and sustained after refreshing page, after closing and
 *               reopening practice test and logging out/in again. (ALM
 *               TC#339554 (339554 + 339558 + 339560 + 339562 + 339556 clubbed
 *               together))
 */

public class GLP_339554_VerifyStateSaveOfMultipartInPracticeTest
        extends BaseClass {

    public GLP_339554_VerifyStateSaveOfMultipartInPracticeTest() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.MULTIPART,
            Groups.SANITY }, enabled = true,
            description = "Verify that the UI and DB State for multipart question is sustained.")

    public void verifyStateSaveOfMultipartInPracticeTest() {
        startReport(getTestCaseId(),
                "Verify that the UI and DB State for multipart question is sustained.");
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

            // learnerUserName = "GLP_Learner_339554_ZLrH";

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
            APP_LOG.info("Navigating to the diagnostic page");
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
            objGLPLearnerPracticeTestPage.navigateToMultipartinPracticeTest();

            // Remove all removes all performance entries with an entryType of
            // "resource" from the browser's performance data buffer
            objRestUtil.clearPerformanceEntries();

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that multipart question is displayed after refresh
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after page refresh.");

            // Fetching the question text for Multipart Question
            String questionOriginal = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Fetching initial total number of tries left for first part of
            // Multipart Question
            int totalNumberOfTriesPartOne = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(1,
                            "PracticeTestMultipartRemainingTriesDynamicLabel:dynamicReplace=1");

            // int totalNumberOfTriesPartOne = 3;

            // Attempt first part of Multipart Question
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(1, 1);

            // Fetching number of tries left after attempting first part
            // of
            // Multipart Question
            int totalNumberOfTriesLeftPartOne = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(1,
                            "PracticeTestMultipartRemainingTriesDynamicLabel:dynamicReplace=1");

            // Verify that first part of Multipart Question is in attempted
            // state and return attempted question elements state.

            Map<String, String> attemptedQuestionStatePartOne = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            1);

            // Verify that Submit/Try Again button under first part gets
            // disappeared after completing first part of Multipart Question
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=1",
                    "Verify that Submit/Try Again button is not visible to user after completing first part");

            // Fetching initial total number of tries left for second part of
            // Multipart Question
            int totalNumberOfTriesPartTwo = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(2,
                            "PracticeTestMultipartRemainingTriesDynamicLabel:dynamicReplace=2");

            // int totalNumberOfTriesPartTwo = 3;

            // Attempt first part of multipart Question
            objGLPLearnerPracticeTestPage
                    .attemptMultipartQuestionInPracticeTest(2, 2);

            // Fetching number of tries left after attempting second part
            // of Multipart Question
            int totalNumberOfTriesLeftPartTwo = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInPracticeTest(2,
                            "PracticeTestMultipartRemainingTriesDynamicLabel:dynamicReplace=2");

            // Verify that second part of Multipart Question is in attempted
            // state and return attempted question elements state.
            Map<String, String> attemptedQuestionStatePartTwo = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            2);

            // Verify that Submit/Try Again button under second part gets
            // disappeared after completing second part of Multipart Question
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestMultipartSubmitTryAgainDynamicButton:dynamicReplace=2",
                    "Verify that Submit/Try Again button is not visible to user after completing second part");

            // Fetching LA IDs of attempted parts of Multipart Question
            Set<String> lAIdsForMultipart = objRestUtil
                    .getMatchingNetworkEntries(ResourceConfigurations
                            .getProperty("multipartLAIDPattern"),
                            "MultipartLAIds");

            // Get CouchBase Query
            String query = objGLPLearnerPracticeTestPage.getDBQuery(
                    learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Fetch DB State saved in CouchBase for first part of
            // Multipart Question after completing it.

            Map<String, String> dataMapafterCompletingPartOne = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            1, query,
                            ResourceConfigurations
                                    .getProperty("dbStateRequiredAttributes"),
                            lAIdsForMultipart);

            // Validate the DB State saved in CouchBase for first part of
            // Multipart Question.
            objGLPLearnerPracticeTestPage
                    .validateCouchBaseDBStateForCompletedPartInMultipart(1,
                            totalNumberOfTriesPartOne,
                            totalNumberOfTriesLeftPartOne,
                            dataMapafterCompletingPartOne);

            // Fetch DB State saved in CouchBase for second part of
            // Multipart Question.

            Map<String, String> dataMapafterCompletingPartTwo = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            2, query,
                            ResourceConfigurations
                                    .getProperty("dbStateRequiredAttributes"),
                            lAIdsForMultipart);

            // Validate the DB State saved in CouchBase for second part of
            // Multipart Question.
            objGLPLearnerPracticeTestPage
                    .validateCouchBaseDBStateForCompletedPartInMultipart(1,
                            totalNumberOfTriesPartTwo,
                            totalNumberOfTriesLeftPartTwo,
                            dataMapafterCompletingPartTwo);

            // Refresh the page.
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that multipart question is displayed after refresh
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after page refresh.");

            // Fetching the multipart question text after refreshing practice
            // test
            String questionAfterRefresh = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after refresh
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterRefresh,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after refreshing page" });

            // Fetching attempted question elements state for first part of
            // Multipart Question after refresh
            Map<String, String> attemptedQuestionStateAfterRefreshPartOne = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            1);

            // Fetching attempted question elements state for second part of
            // Multipart Question after refresh
            Map<String, String> attemptedQuestionStateAfterRefreshPartTwo = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            2);

            // Verify that UI state of first part of Multipart Question is
            // sustained after refresh
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "UI State", "Refresh", attemptedQuestionStatePartOne,
                    attemptedQuestionStateAfterRefreshPartOne);

            // Verify that UI state of second part of Multipart Question is
            // sustained after refresh
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(2,
                    "UI State", "Refresh", attemptedQuestionStatePartTwo,
                    attemptedQuestionStateAfterRefreshPartTwo);

            // Click on Close Practice Test Button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on parctice test close button.");

            // Click on Leave Practice test button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLeaveButton",
                    "Click on Leave practice test button.");

            // Click on Second EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=2,PracticeTestNumber=1",
                    "Click on Second EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify that multipart question is displayed after closing and
            // reopening practice test
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after page refresh.");

            // Fetching the multipart question text after closing and
            // reopening practice test
            String questionAfterCloseReOpen = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after closing and reopening
            // practice test
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterCloseReOpen,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after closing and reopening practice test" });

            // Fetching attempted question elements state for first part of
            // Multipart Question after closing and reopening the practice test
            Map<String, String> attemptedQuestionStateAfterCloseReopenPartOne = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            1);

            // Fetching attempted question elements state for second part of
            // Multipart Question after closing and reopening the practice test
            Map<String, String> attemptedQuestionStateAfterCloseReopenPartTwo = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            2);

            // Verify that UI state of first part of Multipart Question is
            // sustained after closing and reopening practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "UI State", "Close/Re-Open Practice Test",
                    attemptedQuestionStatePartOne,
                    attemptedQuestionStateAfterCloseReopenPartOne);

            // Verify that UI state of second part of Multipart Question is
            // sustained after closing and reopening practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(2,
                    "UI State", "Close/Re-Open Practice Test",
                    attemptedQuestionStatePartTwo,
                    attemptedQuestionStateAfterCloseReopenPartTwo);

            // Click on Close Practice Test Button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on parctice test close button.");

            // Click on Leave Practice test button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLeaveButton",
                    "Click on Leave practice test button.");

            // Click on Practice test Course Drawer Back Arrow
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on Practice test Course Drawer Back Arrow.");

            // Click on Pearson logo on course material page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialPearsonLogo",
                    "Click on Pearson Logo on Course Material Page.");

            // Click on username dropdown menu on course view page

            objGLPLearnerCourseViewPage.clickOnElement("CourseViewUserName",
                    "Click on username dropdown menu on course view page.");

            // Click on logout button displayed in dropdown menu

            objGLPLearnerCourseViewPage.clickOnElement("LogoutButton",
                    "Click on Logout button dispalyed in dropdown menu.");

            // Login again to the GLP application
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on course view card image on course view home page
            objGLPLearnerCourseViewPage.clickOnElement(
                    "CourseViewCourseCardImage",
                    "Click on Course view Card image.");

            // Click on 5th Submodule Start button under module 13
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialModule13_5LOStartButton",
                    "Click on start button of module - 13_5");

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Click on Second EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=2,PracticeTestNumber=1",
                    "Click on Second EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify that multipart question is displayed after logout/login
            // and navigating to same practice test
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after logout/login and navigating to same practice test.");

            // Fetching the multipart question text after logout/login and
            // navigating to same practice test
            String questionAfterLogoutLogin = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after logout/login and
            // navigating to same practice test
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterLogoutLogin,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after logout/login and navigating to same practice test" });

            // Fetching attempted question elements state for first part of
            // Multipart Question after logout/login and navigating to same
            // practice test
            Map<String, String> attemptedQuestionStateAfterLogoutLoginPartOne = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            1);

            // Fetching attempted question elements state for second part of
            // Multipart Question after logout/login and navigating to same
            // practice test
            Map<String, String> attemptedQuestionStateAfterLogoutLoginPartTwo = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            2);

            // Verify that UI state of first part of Multipart Question is
            // sustained after logout/login and navigating to same
            // practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "UI State", "Logout/Login", attemptedQuestionStatePartOne,
                    attemptedQuestionStateAfterLogoutLoginPartOne);

            // Verify that UI state of second part of Multipart Question is
            // sustained after logout/login and navigating to same practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(2,
                    "UI State", "Logout/Login", attemptedQuestionStatePartTwo,
                    attemptedQuestionStateAfterLogoutLoginPartTwo);

            // Close/Re-open browser and navigate to GLP console URL and set
            // WebDriver instance to new WebDriver
            this.webDriver = objGLPLearnerPracticeTestPage
                    .closeReopenBrowserAndNavigateGLPConsole();

            // Login again to the GLP application
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on course view card image on course view home page
            objGLPLearnerCourseViewPage.clickOnElement(
                    "CourseViewCourseCardImage",
                    "Click on Course view Card image.");

            // Click on 5th Submodule Start button under module 13
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialModule13_5LOStartButton",
                    "Click on start button of module - 13_5");

            // Verify that page is loaded
            findElement.checkPageIsReady();

            // Click on Second EO Practice Quiz
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestDynamicEOPracticeQuiz:EONumber=2,PracticeTestNumber=1",
                    "Click on Second EO Practice Quiz");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Verify that multipart question is displayed after
            // closing/reopening browser and navigating to same practice test
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestMultipart",
                    "Verify that Multipart Question is displayed after close/reopen browser and navigating to same practice test.");

            // Fetching the multipart question text after closing/reopening
            // browser and navigating to same practice test
            String questionAfterCloseReopenBrowser = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartQuestionText");

            // Comparing the question before and after closing/reopening browser
            // and navigating to same practice test
            objGLPLearnerPracticeTestPage.compareText(questionOriginal,
                    questionAfterCloseReopenBrowser,
                    new String[] { "yes",
                            "Multipart Question before any operation",
                            "Multipart Question after closing/reopening browser and navigating to same practice test" });

            // Fetching attempted question elements state for first part of
            // Multipart Question after closing/reopening browser and navigating
            // to same practice test
            Map<String, String> attemptedQuestionStateAfterCloseReopenBrowserPartOne = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            1);

            // Fetching attempted question elements state for second part of
            // Multipart Question after closing/reopening browser and navigating
            // to same practice test
            Map<String, String> attemptedQuestionStateAfterCloseReopenBrowserPartTwo = objGLPLearnerPracticeTestPage
                    .checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                            2);

            // Verify that UI state of first part of Multipart Question is
            // sustained after closing/reopening browser and navigating to same
            // practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "UI State", "Close/Re-Open Browser",
                    attemptedQuestionStatePartOne,
                    attemptedQuestionStateAfterCloseReopenBrowserPartOne);

            // Verify that UI state of second part of Multipart Question is
            // sustained after closing/reopening browser and navigating to same
            // practice test
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(2,
                    "UI State", "Close/Re-Open Browser",
                    attemptedQuestionStatePartTwo,
                    attemptedQuestionStateAfterCloseReopenBrowserPartTwo);

            // Fetch DB State saved in CouchBase for first part of
            // Multipart Question after completing all checks
            // like refresh/close-reopen practice test/logout-login/close-reopen
            // browser.
            Map<String, String> dataMapafterAllChecksPartOne = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            1, query,
                            ResourceConfigurations
                                    .getProperty("dbStateRequiredAttributes"),
                            lAIdsForMultipart);

            // Fetch DB State saved in CouchBase for second part of
            // Multipart Question after completing all checks
            // like refresh/close-reopen practice test/logout-login/close-reopen
            // browser.
            Map<String, String> dataMapafterAllChecksPartTwo = objGLPLearnerPracticeTestPage
                    .getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                            2, query,
                            ResourceConfigurations
                                    .getProperty("dbStateRequiredAttributes"),
                            lAIdsForMultipart);

            // Verify that DB state of first part of Multipart Question is
            // sustained after completing all checks
            // like refresh/closing-reopening practice
            // test/logout-login/closing-reopening
            // browser
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "CouchBase DB State",
                    "Refresh/Close-Reopen Practice/Logout-Login/Close-Reopen Browser",
                    dataMapafterCompletingPartOne,
                    dataMapafterAllChecksPartOne);

            // Verify that DB state of second part of Multipart Question is
            // sustained after completing all checks
            // like refresh/closing-reopening practice
            // test/logout-login/closing-reopening
            // browser
            objGLPLearnerPracticeTestPage.compareSavedDBOrAttemptedUIStates(1,
                    "CouchBase DB State",
                    "Refresh/Close-Reopen Practice/Logout-Login/Close-Reopen Browser",
                    dataMapafterCompletingPartTwo,
                    dataMapafterAllChecksPartTwo);

        }

        finally

        {

            this.webDriver.quit();
            this.webDriver = null;
        }
    }

}
