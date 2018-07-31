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
 * @date May 07, 2018
 * @description: Test to verify that the content of HMST gets refreshed on
 *               shuffling between LA options
 * 
 */

public class GLP_323520_VerifyContentGetsRefreshedOnShufflingBetweenLAOptions
        extends BaseClass {
    public GLP_323520_VerifyContentGetsRefreshedOnShufflingBetweenLAOptions() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify that the content of HMST gets refreshed on shuffling between LA options.")
    public void VerifyContentGetsRefreshed() {
        startReport(getTestCaseId(),
                "Test to verify that the content of HMST gets refreshed on shuffling between LA options.");

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
            // Get the index of the first interactive part of the HMST
            int interactivePartIndex = objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalParts);
            // Get the number of tries for the interactive HMST part
            int triesLeftHMST = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInHMSTLA(
                            interactivePartIndex);
            // Attempt Help Me Solve This till second last part
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(
                    interactivePartIndex, totalParts - 1);
            // Select View An Example from the LA drop down
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsViewAnExample"));
            // Select Help Me Solve This from the LA drop down
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Verify the first part content does not match with the last
            // instance
            String strUpdatedFirstPartContent = objGLPLearnerPracticeTestPage
                    .getTextOfSinglePartInMultipart(1);
            objGLPLearnerPracticeTestPage.verifyLearningAidPartContentIsUpdated(
                    strFirstPartContent, strUpdatedFirstPartContent, false);
            // Get the index of the first interactive part of the HMST
            interactivePartIndex = objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalParts);
            // Verify the submitted part is cleared out by checking the number
            // of tries
            objGLPLearnerPracticeTestPage.verifyInteractiveHMSTPartIsRefreshed(
                    interactivePartIndex, triesLeftHMST);

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }

}
