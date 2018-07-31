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
 * @author lekh.bahl
 * @date Dec 06, 2017
 * @description: To Verify that the Static 'UI Text - welcome screen etc.', and
 *               'Labels - Button , navigation Links etc.' should be displayed
 *               in Spanish language for Diagnostic Test
 */
public class GLP_282717_VerifyDiagnosticLabelAndTextLocalization
        extends BaseClass {
    public GLP_282717_VerifyDiagnosticLabelAndTextLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER },
            enabled = true,
            description = "To Verify that the Static 'UI Text - welcome screen etc.', and 'Labels - Button , navigation Links etc.' should be displayed in Spanish language for Diagnostic Test")
    public void verifyDiagnosticLabelAndTextLocalization() {
        startReport(getTestCaseId(),
                "To Verify that the Static 'UI Text - welcome screen etc.', and 'Labels - Button , navigation Links etc.' should be displayed in Spanish language for Diagnostic Test");
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
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Navigate to Multipart Question
            objProductApplication_DiagnosticTestPage
                    .navigateToSpecificQuestionType(
                            ResourceConfigurations.getProperty("multipart"));
            // Verify CoachMark Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticGotItDialog",
                    ResourceConfigurations.getProperty("coachMarkText"),
                    "Verify Coach-mark text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify CoachMark got it button localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticGotItButton",
                    ResourceConfigurations.getProperty("coachMarkGotItText"),
                    "Verify Coach-mark got it button is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on Got it button
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticGotItButton", "Click on I Don't Know button");
            // Verify Submit button localization
            objProductApplication_DiagnosticTestPage.verifyTextInList(
                    "DiagnosticSubmitButton",
                    ResourceConfigurations.getProperty("submitButtonText"),
                    "Verify Submit button is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Verify Leave Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpLeaveButton",
                    ResourceConfigurations.getProperty("diagnosticLeaveText"),
                    "Verify 'Leave' text on pop up is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Cancel Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpCancelButton",
                    ResourceConfigurations.getProperty("diagnosticCancelText"),
                    "Verify 'Cancel' text on pop up is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // Verify continue button text is displayed in respective language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeStartYourPathBtn",
                    ResourceConfigurations
                            .getProperty("diagnosticContinubuttonText"),
                    "Verify continue button text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

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
