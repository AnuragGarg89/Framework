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
import com.glp.page.GLPLearner_LearningObjectivePage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date June 19, 2018
 * @description: Verify UI of practice test passed summary screen
 */
public class GLP_322130_VerifyPracticeTestPassSummaryScreen extends BaseClass {
    public GLP_322130_VerifyPracticeTestPassSummaryScreen() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify UI of practice test passed summary screen")
    public void verifyPracticeTestPassSummaryScreenUI() {
        startReport(getTestCaseId(),
                "Verify UI of practice test passed summary screen");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);
            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            // Attempt diagnostic test
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Click on Go TO Course Home Link
            objGLPLearnerCourseMaterialPage.clickOnElementIfVisible(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on first Module collapsed arrow.
            objGLPLearnerCourseMaterialPage
                    .navigateToCoreInstructionsInLo("16.1");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on Last LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeTestLastLO",
                    "Click on last LO");

            // Click on practice test of last LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of last LO");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Attempt practice test
            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on summary button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSummaryButton", "Click on summary button");

            // Verify pass summary screen ui
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestSummaryScreenCongrats",
                    ResourceConfigurations.getProperty(
                            "practiceTestLastLoPassedSummaryScreenCongratsText"),
                    "Verify congrats message is displyed on last LO passed summary screen");

            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestSummaryScreenCompletedModule",
                    ResourceConfigurations.getProperty(
                            "practiceTestLastLoPassedSummaryScreenCompletedModuleText"),
                    "Verify completed module sub heading should be displayed");

            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestSummaryScreenMainGoalGrapic",
                    "Verify main goal grapic is present");

            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestSummarypagePracticeAgainButton",
                    ResourceConfigurations.getProperty("continue"),
                    "Verify continue button is displyed on the summary screen");

            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestSummaryScreenPracticeAgain",
                    "Verify practice again link is displyed on the summary screen");

            objGLPLearnerPracticeTestPage.verifyText(
                    "PraciceTestSummarypageCourseHomeLink",
                    ResourceConfigurations
                            .getProperty("resultDiagnosticGoToCourseHomeLink"),
                    "Verify go to course home link is displyed on the summary screen");

            // Verify on clicking the continue button learner is redirected to
            // the next EO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSummaryScreenPracticeAgain",
                    "Click on continue button button");
            objGLPLearnerPracticeTestPage.verifyTextContains(
                    "CoreInstructionNAvigationDrawerTitle", ".2",
                    "Verify on clicking the continue button learner is redirected to the next LO");
            GLPLearner_LearningObjectivePage objGLPLearnerLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);

            // Click on back arrow
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");
            objGLPLearnerCourseMaterialPage
                    .navigateToCoreInstructionsInLo("16.1");

            // Click on practice again
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestSummaryScreenPracticeAgain",
                    "Click on practice again");

            // verify redirection of practice again button
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "AssessmentPlayer",
                    "Verify learner is redirected to assessment player on clicking the practice again button");

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
