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
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author Saurabh.Sharma
 * @date March 21, 2018
 * @description: Test to verify that learner is able to access the View an
 *               Example Learning Aid on practice test.
 * 
 */

public class GLP_317216_VerifyLearnerIsAbleToAccessViewAnExampleLearningAid
        extends BaseClass {
    public GLP_317216_VerifyLearnerIsAbleToAccessViewAnExampleLearningAid() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.SANITY },
            enabled = true,
            description = "Test to verify that learner is able to access the View An Example Learning Aid on practice test.")

    public void verifyLearnerCanAccessViewAnExampleLA() {
        startReport(getTestCaseId(),
                "Test to verify that learner is able to access the View An Example Learning Aid on practice test.");
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
                            .getProperty("learningAidsViewAnExample"));
            // Get the number of parts for the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Verify continue button is displayed for first part
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "LearningAidsSubmitPartButton",
                    "Verify Continue button is displayed on the first displayed part.");
            // Attempt the first part of the Learning Aids
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1, 1);
            // Verify the Continue is displayed for second part
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "LearningAidsSecondPartSubmitButton",
                    "Verify Continue button is gets displayed for second part.");
            // Attempt the rest of the parts
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(2,
                    totalParts);
            // Verify the Close button is displayed after the last part
            objGLPLearnerPracticeTestPage.verifyElementIsVisible(
                    "LearningAidsCloseButton",
                    "Verify the Close button is displayed after the last part");
            // Click on Close button
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "LearningAidsCloseButton",
                    "Click on Close button to close the Learning Aids window");
            // Verify the Learning Aids window gets closed
            objGLPLearnerPracticeTestPage.verifyElementIsNotVisible(
                    "LearningAidsBody",
                    "Verify Learning Aids window gets closed");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }

}