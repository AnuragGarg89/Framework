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
package com.test.glp_learner_postAssessment;

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
import com.glp.page.GLPLearner_PostAssessmentPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date MAy 14, 2018
 * @description: Verify Number of questions and progress bar in post assessment
 * 
 */
public class GLP_341158_VerifyPostAssessmenQuestionCountAndProgressBar
        extends BaseClass {
    public GLP_341158_VerifyPostAssessmenQuestionCountAndProgressBar() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify Number of questions and progress bar in post assessment")
    public void VerifyProgressBarAndQuestionCount() {
        startReport(getTestCaseId(),
                "Verify Number of questions and progress bar in post assessment");
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
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            int numberOfLo = objGLPLearnerPostAssessmentPage.getLoCount();

            // Click on Module test Start button of first module
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestChapter11StartButton",
                    "Click on start button of module test of first module");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on start button on module test welcome screen");

            // Verify number of question in module test
            int numberOfQuestions = objGLPLearnerPostAssessmentPage
                    .getQuestionCount();

            String lOCount = Integer.toString(numberOfLo * 8);
            String postassessmentNumberOfQuestions = Integer
                    .toString(numberOfQuestions);

            objGLPLearnerPostAssessmentPage.compareText(lOCount,
                    postassessmentNumberOfQuestions,
                    "Verify number of questions in post assessment is equal to the 8* number of LO");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            String progressBarWidthValueBefore = objGLPLearnerPracticeTestPage
                    .getCurrentWidthOfProgressBar();

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            String progressBarWidthValueAfter = objGLPLearnerPracticeTestPage
                    .getCurrentWidthOfProgressBar();

            objGLPLearnerPracticeTestPage.verifyProgressBarForwardDirection(
                    progressBarWidthValueBefore, progressBarWidthValueAfter);

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
