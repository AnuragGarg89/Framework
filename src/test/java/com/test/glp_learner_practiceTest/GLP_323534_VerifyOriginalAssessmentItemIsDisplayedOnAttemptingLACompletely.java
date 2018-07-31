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
import com.autofusion.constants.Constants;
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
 * @date March 29, 2018
 * @description: Test to verify that original assessment item is displayed once
 *               learner finishes all parts of Help Me Solve this Learning Aid
 * 
 */

public class GLP_323534_VerifyOriginalAssessmentItemIsDisplayedOnAttemptingLACompletely
        extends BaseClass {
    public GLP_323534_VerifyOriginalAssessmentItemIsDisplayedOnAttemptingLACompletely() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify that learner is able to access the Help Me Solve This Learning Aid on practice test.")

    public void verifyLearnerCanAccessHMSTLA() {
        startReport(getTestCaseId(),
                "Test to verify that original assessment item is displayed once learner finishes all parts of Help Me Solve this Learning Aid.");
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
            // Attempt the first part of the Learning Aids
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1, 1);

            // Verify the remaining parts label move at the top of second part
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "LearningAidsRemainingPartsSecondLabel",
                    "Verify the remaining parts label move at the top of second part.");

            // Verify the Continue/Submit button is displayed on the next part
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "LearningAidsSecondPartSubmitButton",
                    "Verify the Continue/Submit button is displayed on the next part");
            // Verify the Continue/Submit button is not displayed for the first
            // part
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "LearningAidsFirstPartSubmitButton",
                    "Verify the Continue/Submit button is not displayed for the first part");
            // Attempt the remaining parts of the Learning Aids
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(2,
                    totalParts);
            // Verify the Continue/Submit button is not displayed after last
            // part is attempted
            String sButtonText = objGLPLearnerPracticeTestPage
                    .getText("LearningAidsSubmitPartButton");
            if (!sButtonText.equals(ResourceConfigurations
                    .getProperty("courseMaterialContinueButton"))
                    && !sButtonText.equals(ResourceConfigurations
                            .getProperty("submitButtonText"))) {
                logResultInReport(
                        Constants.PASS
                                + ": Continue/Submit button is not displayed once the last part is attempted",
                        "Verify the Continue/Submit button is not displayed after last part is attempted",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": Continue/Submit button is still displayed after attempting the last part",
                        "Verify the Continue/Submit button is not displayed after last part is attempted",
                        reportTestObj);
            }
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
