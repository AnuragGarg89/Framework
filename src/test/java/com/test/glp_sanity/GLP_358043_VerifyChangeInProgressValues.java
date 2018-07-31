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
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nitish.jaiswal
 * @date Dec 23, 2017
 * @description: Verify that "Resume" screen shown when the learner's diagnostic
 *               test is not yet complete but user exits test and then logs in
 *               after Logging Out, then following UI contents should display on
 *               the page
 */
public class GLP_358043_VerifyChangeInProgressValues extends BaseClass {
    public GLP_358043_VerifyChangeInProgressValues() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that Resume screen shown when the learner's diagnostic test is not yet complete but user exits test and then logs in after Logging Out, then following UI contents should display on the page")
    public void verifyChangeInProgressValues() {
        startReport(getTestCaseId(),
                "Verify that Resume screen shown when the learner's diagnostic test is not yet complete but user exits test and then logs in after Logging Out, then following UI contents should display on the page");
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
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Navigate to diagnostic test
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Attempt few questions
            /*
             * objProductApplication_DiagnosticTestPage
             * .getQuestionTypeAndAttempt();
             */
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
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
                    "Verify resume screen 'When you return, you can continue working from where you left off' text message is displayed ");
            // verify continue button text is displayed
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeStartYourPathBtn",
                    ResourceConfigurations
                            .getProperty("diagnosticContinubuttonText"),
                    "Verify continue button text is displayed");

            // verify progress bar
            objProductApplicationCourseHomePage.verifyElementPresent(
                    "CourseHomeProgressBar", "Verify Progess Bar is visible.");
            // Logout from application
            objGLPLearnerCourseViewPage.verifyLogout();
            // Login with same learner
            objProductApplicationLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Navigate to resume screen
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();

            // verify continue button text is displayed
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeStartYourPathBtn",
                    ResourceConfigurations
                            .getProperty("diagnosticContinubuttonText"),
                    "Verify continue button text is displayed");

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
