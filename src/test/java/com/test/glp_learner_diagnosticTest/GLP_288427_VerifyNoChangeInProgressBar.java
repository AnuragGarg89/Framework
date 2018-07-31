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
 * @description: Verify that the Progress bar should be saved in the system when
 *               a learner exits from pre-assessment test before completing it
 *               and the same progress is reflected on the "Resume" screen when
 *               learner logs in again after logging out
 */
public class GLP_288427_VerifyNoChangeInProgressBar extends BaseClass {
    public GLP_288427_VerifyNoChangeInProgressBar() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify Progress bar should be saved in the system when a learner exits from pre-assessment test before completing it and the same progress is reflected on the 'Resume Your Path' screen when learner logs in again.")
    public void verifyNoChangeInProgressBar() {
        startReport(getTestCaseId(),
                "To verify Progress bar should be saved in the system when a learner exits from pre-assessment test before completing it and the same progress is reflected on the 'Resume Your Path' screen when learner logs in again.");
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
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            String progressBarWidthBeforeAttemtingQuestion = objGLPLearnerDiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            // Attempt few question
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

            String progressBarWidthCourseHomeAfterAttemptingfewQues = objGLPLearnerCourseHomePage
                    .getCurrentWidthOfProgressBar();

            objGLPLearnerCourseHomePage.compareProgressBarLength(
                    progressBarWidthBeforeAttemtingQuestion,
                    progressBarWidthCourseHomeAfterAttemptingfewQues);

            // Logout from application
            objGLPLearnerCourseViewPage.verifyLogout();
            // Login with same learner
            objProductApplicationLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Navigate to resume screen
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();

            String progressBarWidthCourseHomeAfterReLogin = objGLPLearnerCourseHomePage
                    .getCurrentWidthOfProgressBar();
            objGLPLearnerCourseHomePage.compareProgressBarLengthEquality(
                    progressBarWidthCourseHomeAfterAttemptingfewQues,
                    progressBarWidthCourseHomeAfterReLogin);

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
