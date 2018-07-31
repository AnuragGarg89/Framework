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
 * @date May 10, 2018
 * @description: Test to verify the state of Student Initiated Learning Aids on
 *               browser refresh
 */

public class GLP_323521_VerifyStateOfStudentInitiatedHMSTOnBrowserRefresh
        extends BaseClass {
    public GLP_323521_VerifyStateOfStudentInitiatedHMSTOnBrowserRefresh() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify the state of Student Initiated Learning Aids on browser refresh")
    public void verifyLearnerCanAccessHMSTLA() {
        startReport(getTestCaseId(),
                "Test to verify the state of Student Initiated Learning Aids on browser refresh");
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
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);

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
                    ResourceConfigurations.getProperty("module11Text"),
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Navigate to the question of the practice test having LA
            objGLPLearnerPracticeTestPage.navigateToQuestionWithLearningAids();

            // Get the text of the question displayed
            String strOriginalText = objGLPLearnerPracticeTestPage
                    .getText("PracticeQuestionDisplayedText");
            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Get the total number of parts of the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Attempt Help Me Solve This till second last part
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1,
                    totalParts - 1);
            // Page Refresh
            objGLPLearnerPracticeTestPage.refreshPage();
            // Verify the HMST gets closed
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "LearningAidsSubmitPartButton",
                    "Verify the Help Me Solve This window gets closed");
            // Verify the original Practice item gets refreshed
            String strRefreshedText = objGLPLearnerPracticeTestPage
                    .getText("PracticeQuestionDisplayedText");
            objGLPLearnerPracticeTestPage
                    .verifyPracticeQuestionContentIsUpdated(strOriginalText,
                            strRefreshedText, false);
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
