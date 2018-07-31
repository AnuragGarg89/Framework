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
 * @date May 15, 2018
 * @description: Verify that application should display all buttons/navigation
 *               links of View An Example in Browser selected language
 */

public class GLP_328224_VerifyVAETextIsDisplayedInBrowserSelectedLanguage
        extends BaseClass {
    public GLP_328224_VerifyVAETextIsDisplayedInBrowserSelectedLanguage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that application should display all buttons/ navigation links of View An Example in Browser selected language")
    public void verifyLearnerCanAccessHMSTLA() {
        startReport(getTestCaseId(),
                "Verify that application should display all buttons/ navigation links of View An Example in Browser selected language");
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

            String sLocalizedText = "";
            // Verify the text of Help Me Answer This drop down is in browser
            // selected language
            sLocalizedText = objGLPLearnerPracticeTestPage
                    .getText("HelpMeAnswerThisDropdown");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations
                            .getProperty("learningAidsDropDownText"),
                    sLocalizedText, "Help Me Answer This dropdown");

            // Click on Help Me Answer This drop down
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "HelpMeAnswerThisDropdown",
                    "Click on Help Me Answer This dropdown");

            // Verify the text of View An Example option is in browser
            // selected language
            sLocalizedText = objGLPLearnerPracticeTestPage
                    .getText("ViewAnExampleLAOption");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations
                            .getProperty("learningAidsViewAnExample"),
                    sLocalizedText, "View An Example option");
            // Click on Help Me Answer This drop down
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "HelpMeAnswerThisDropdown",
                    "Click on Help Me Answer This dropdown");

            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsViewAnExample"));
            // Get the total number of parts of the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();

            // Verify the text of Continue button is in browser
            // selected language
            sLocalizedText = objGLPLearnerPracticeTestPage
                    .getText("LearningAidsSubmitPartButton");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    sLocalizedText, "Continue button");
            // Attempt all parts of View An Example
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1,
                    totalParts);

            // Verify the text of Close button is in browser
            // selected language
            sLocalizedText = objGLPLearnerPracticeTestPage
                    .getText("PracticeTestHMSTLearningAidsCloseButton");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations.getProperty("closeText"),
                    sLocalizedText, "Close button");

            // Verify the text of tool tip of Close icon is in browser
            // selected language
            sLocalizedText = objGLPLearnerPracticeTestPage
                    .getAttribute("LearningAidsHeaderCloseIcon", "title");
            objGLPLearnerPracticeTestPage.verifyLocalizedText(
                    ResourceConfigurations
                            .getProperty("learninAidsCloseIconHeaderTitle"),
                    sLocalizedText, "Heading Close Icon");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
