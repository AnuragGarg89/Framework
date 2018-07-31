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

import java.io.IOException;

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

import de.sstoehr.harreader.HarReaderException;

/**
 * @author Abhishek Sharda
 * @date Feb 12, 2018
 * @description: Test to Verify Course Home & Diagnostic result screen - All
 *               module mastered
 * 
 */
public class GLP_358022_VerifyDiagnosticResultScreenAllModuleMastered
        extends BaseClass {
    private String learnerUserName = null;

    public GLP_358022_VerifyDiagnosticResultScreenAllModuleMastered() {
    }

    @Test(groups = { Groups.SANITY, Groups.HEARTBEAT, Groups.REGRESSION },
            enabled = true,
            description = "Verify on Diagnostic result screen, all the modules are displayed as mastered and a button is displayed to navigate to course home")
    public void verifyCompleteDiagnosticTestAttempted()
            throws IOException, HarReaderException {
        startReport(getTestCaseId(),
                "Verify on Diagnostic result screen, all the modules are displayed as mastered and a button is displayed to navigate to course home");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        try {
            learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                    + objCommonUtil.generateRandomStringOfAlphabets(4);

            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);
            // Login in the application
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            // Automate the remaining steps of test case
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));
            // Verify 'All done!' Text
            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "ModuleTestResultHeading",
                    ResourceConfigurations.getProperty(
                            "nonMasteredResultDiagnosticHeadingText"),
                    "Verify text is matched for Heading.");
            // Verify 'You've placed into' Text
            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "ModuleTestResultParagraph",
                    ResourceConfigurations
                            .getProperty("nonMasteredResultDiagnosticText"),
                    "Verify text is matched for Sub-Heading.");
            // Verify 'Module 16: Solving More Linear Equations and
            // Inequalities' Text
            objGLPLearnerDiagnosticTestPage.verifyTextContains("Module16Test",
                    ResourceConfigurations.getProperty("rioToolTips6"),
                    "Verify text 'Module 16: Solving More Linear Equations and Inequalities'is matched");
            // Verify 'Start' Button
            objGLPLearnerDiagnosticTestPage.verifyText(
                    "ResultDiagnosticStartButton",
                    ResourceConfigurations
                            .getProperty("resultDiagnosticStartButton"),
                    "Verify text is matched for Start Button.");

        }

        finally {
            webDriver.quit();
            webDriver = null;
        }
    }

}
