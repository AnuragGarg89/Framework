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
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author Saurabh.Sharma
 * @date April 30, 2018
 * @description: Test to verify that the content of original assessment item is
 *               refreshed once learner finishes the Learner Aids
 * 
 */

public class GLP_323535_VerifyOriginalAssessmentItemIsRefreshedOnClosingLA
        extends BaseClass {
    public GLP_323535_VerifyOriginalAssessmentItemIsRefreshedOnClosingLA() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify that the content of original assessment item is refreshed once learner finishes the Learner Aids.")
    public void verifyLearnerCanAccessHMSTLA() {
        startReport(getTestCaseId(),
                "Test to verify that the content of original assessment item is refreshed once learner finishes the Learner Aids");
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
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module11Text"),
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Navigate to the question of the practice test having LA
            objGLPLearnerPracticeTestPage.navigateToQuestionWithLearningAids();
            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Get the total number of parts of the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Get the content of the first part
            String strFirstPartContent = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(1);

            // Attempt Help Me Solve This till second last part
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1,
                    totalParts - 1);
            // Again select Help Me Solve This from the drop down
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Verify that Help Me Solve This is refreshed and opens from the
            // start
            objGLPLearnerPracticeTestPage
                    .verifyLearningAidsPartsAreRefreshed(totalParts);
            // Attempt all parts of Help Me Solve This and Click on
            // Close button
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1,
                    totalParts);
            // Click on Close button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "LearningAidsCloseButton",
                    "Click on close button to close the Learning Aids window");
            // Again open the Help Me Solve This Learning Aid LA
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Verify the first part content does not match with the last
            // instance
            String strUpdatedFirstPartContent = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(1);
            objGLPLearnerPracticeTestPage.verifyLearningAidPartContentIsUpdated(
                    strFirstPartContent, strUpdatedFirstPartContent, false);
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}