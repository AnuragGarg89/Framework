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
package com.test.glp_learner_diagnosticTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date March 29, 2018
 * @description: To verify if the progress bar is displayed for a diagnostic
 *               test as well as resume diagnostic test screen based upon the
 *               total number of questions configured for a test.
 * 
 * 
 */
public class GLP_340143_VerifyProressBarRemainSameOnResumeScreen
        extends BaseClass {
    public GLP_340143_VerifyProressBarRemainSameOnResumeScreen() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify if the progress bar is displayed for a diagnostic test as well as resume diagnostic test screen based upon the total number of questions configured for a test")
    public void VerifyProgressBarNoChangePracticeTest() {
        startReport(getTestCaseId(),
                "Verify progress bar remain same on resume screen if learner exit the diagnostic test");
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
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Navigate to diagnostic test
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Skip all Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            String progressBarWidthValueBeforeExit = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            // Click on cross icon on practice test
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon on diagnostic test");

            // Click on leave button
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on leave button on the pop up");

            String progressBarWidthValueAfterExit = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            objGLPLearnerDiagnosticTestPage.verifyProgressBarNoIncrease(
                    progressBarWidthValueBeforeExit,
                    progressBarWidthValueAfterExit,
                    "On exiting the diagnostic test same progress is displayed on resume screen as displyed on diagnostic test");

            objGLPLearnerDiagnosticTestPage.refreshPage();

            String progressBarWidthValueAfterRefresh = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            // Verify progress bar remains same on refreshing the resume
            // diagnostic screen
            objGLPLearnerDiagnosticTestPage.verifyProgressBarNoIncrease(
                    progressBarWidthValueAfterExit,
                    progressBarWidthValueAfterRefresh,
                    "Progress bar remains same after refreshing the diagnostic test resume screen");

            // click on continue button
            objGLPLearnerCourseHomePage.clickOnElement(
                    "CourseHomeStartPreAssessmentButton",
                    "Click on continue button on resume dignostic screen ");

            // Verify progress bar remains same if user again go to diagnostic
            // test
            String progressBarWidthValueAfterGoingBack = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();
            objGLPLearnerDiagnosticTestPage.verifyProgressBarNoIncrease(
                    progressBarWidthValueBeforeExit,
                    progressBarWidthValueAfterGoingBack,
                    "Progress bar remains same after laerner going back to the diagnostic test");

            objGLPLearnerDiagnosticTestPage.refreshPage();

            String progressBarWidthValueTestScreenAfterRefresh = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();
            objGLPLearnerDiagnosticTestPage.verifyProgressBarNoIncrease(
                    progressBarWidthValueBeforeExit,
                    progressBarWidthValueTestScreenAfterRefresh,
                    "Progress bar remains same after refreshing the diagnostic test screen");

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
