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

import java.util.HashMap;

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
 * @date Jun 06, 2018
 * @description: Verify multi-part question's static text is displayed in
 *               browser selected language
 */

public class GLP_334464_VerifyMultipartQuestionTextIsDisplayedInBrowserSelectedLanguage
        extends BaseClass {
    public GLP_334464_VerifyMultipartQuestionTextIsDisplayedInBrowserSelectedLanguage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify multi-part question's static text is displayed in browser selected language")
    public void verifyStateOfRecommendedLA() {
        startReport(getTestCaseId(),
                "Verify multi-part question's static text is displayed in browser selected language");
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

            // Login to the GLP application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile is Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic first question
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Navigate to diagnostic page
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            // Attempt the diagnostic for the created user
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module13Text"),
                    ResourceConfigurations
                            .getProperty("subModule13_5AreaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Navigate to Multi-part question
            objGLPLearnerPracticeTestPage.navigateToMultipartinPracticeTest();

            // Verify only 1 part is displayed for multipart
            objGLPLearnerPracticeTestPage.verifyFormativeMultiPartUI();
            // Get the text of remaining tries label for the first part
            String strTriesLeftText = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartRemainingTriesLabelPartOne")
                    .substring(2);
            // Verify the number of tries are displayed in browser
            // selected language
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations.getProperty("triesLeftText"),
                    strTriesLeftText, "Tries Left");
            // Verify the submit button text is displayed in browser selected
            // language
            String strSubmitButtonText = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestMultipartSubmitTryAgainPartOne");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations.getProperty("submitButtonText"),
                    strSubmitButtonText, "Submit button");
            // Attempt the first part of the multi part question
            objGLPLearnerPracticeTestPage
                    .attemptActivePartOfMultipartInPracticeTest(1);
            // Verify the feedback is displayed
            HashMap<String, String> feedback = objGLPLearnerPracticeTestPage
                    .verifyFeedbackIsDisplayedForSinglePart(1, true);
            // Verify the automated feedback text is displayed in browser
            // selected language
            if (feedback.containsKey("positive")) {
                objGLPLearnerPracticeTestPage.verifyLocalizedText(
                        ResourceConfigurations
                                .getProperty("correctFeedbackText"),
                        feedback.get("positive"), "Feedback Text");
            } else {
                objGLPLearnerPracticeTestPage.verifyLocalizedText(
                        ResourceConfigurations
                                .getProperty("incorrectFeedbacktext"),
                        feedback.get("negative"), "Feedback Text");
            }
        } finally {
            webDriver.close();
            webDriver = null;
        }
    }
}
