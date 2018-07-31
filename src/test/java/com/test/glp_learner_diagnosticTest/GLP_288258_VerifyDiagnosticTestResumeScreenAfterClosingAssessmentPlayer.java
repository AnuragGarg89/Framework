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
package com.test.glp_learner_diagnosticTest;

import org.openqa.selenium.WebDriver;
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

/**
 * 
 * @author pallavi.tyagi
 * @date Feb 22, 2018
 * @description :Verify that Resume screen should be shown if learner's
 *              diagnostic test is not yet complete and the user exits the test.
 * 
 */

public class GLP_288258_VerifyDiagnosticTestResumeScreenAfterClosingAssessmentPlayer
        extends BaseClass {
    public GLP_288258_VerifyDiagnosticTestResumeScreenAfterClosingAssessmentPlayer() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER },
            enabled = true,
            description = "Verify that Resume screen should be shown if learner's diagnostic test is not yet complete and the user exits the test.")
    public void verifyDiagnosticTestResumeScreen() {
        startReport(getTestCaseId(),
                "Verify that Resume screen should be shown if learner's diagnostic test is not yet complete and the user exits the test.");
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
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Navigate to diagnostic test
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Attempt first question
            objProductApplication_DiagnosticTestPage
                    .attemptAdaptiveDiagnosticTest(0, 2, ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            objProductApplication_DiagnosticTestPage.verifyElementIsVisible(
                    "DiagnosticProgressBar", "Verify Progess Bar is visible.");
            String progressBarWidthValueBefore = objProductApplication_DiagnosticTestPage
                    .getCurrentWidthOfProgressBar();
            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            // verify resume screen message text in respective
            // language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeBackText",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText1"),
                    "Verify resume screen 'We’ve saved your place' text message is displayed ");
            // verify resume screen message text in respective
            // language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeResumeMessageText",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText2"),
                    "Verify resume screen 'When you return, you can continue working from where you left off' text message is displayed");
            // Navigate to diagnostic test
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            String progressBarWidthValueAfter = objProductApplication_DiagnosticTestPage
                    .getCurrentWidthOfProgressBar();
            objProductApplication_DiagnosticTestPage.compareBarLength(
                    progressBarWidthValueBefore, progressBarWidthValueAfter);
            // Click on browser back
            WebDriver driver = returnDriver();
            driver.navigate().back();
            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            // verify resume screen message text in respective
            // language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeBackText",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText1"),
                    "Verify resume screen 'We’ve saved your place' text message is displayed ");
            // verify resume screen message text in respective
            // language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeResumeMessageText",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText2"),
                    "Verify resume screen 'When you return, you can continue working from where you left off' text message is displayed");
            // Navigate to diagnostic test
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            String progressBarWidthValueAfter1 = objProductApplication_DiagnosticTestPage
                    .getCurrentWidthOfProgressBar();
            objProductApplication_DiagnosticTestPage.compareBarLength(
                    progressBarWidthValueBefore, progressBarWidthValueAfter1);
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
