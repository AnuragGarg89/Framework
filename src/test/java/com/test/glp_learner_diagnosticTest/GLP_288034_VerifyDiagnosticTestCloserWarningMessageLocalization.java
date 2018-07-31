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
 * @author Pallavi.tyagi
 * @date Feb 23, 2018
 * @description: To show a warning message to student, when the student attempts
 *               to close or accidently navigates away from the diagnostic test
 *               window without completing the test.
 */
public class GLP_288034_VerifyDiagnosticTestCloserWarningMessageLocalization
        extends BaseClass {
    public GLP_288034_VerifyDiagnosticTestCloserWarningMessageLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER },
            enabled = true,
            description = "To show a warning message to student, when the student attempts to close or accidently navigates away from the diagnostic test window without completing the test.")
    public void verifyDiagnosticLabelAndTextLocalization() {
        startReport(getTestCaseId(),
                "To show a warning message to student, when the student attempts to close or accidently navigates away from the diagnostic test window without completing the test.");
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

            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Verify Warning message Heading Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpHeadingText",
                    ResourceConfigurations
                            .getProperty("diagnosticPopUpHeadingText"),
                    "Verify diagnostic popup message heading text is displayed in ."
                            + ResourceConfigurations.getProperty("language"));
            // Verify Warning message Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpText",
                    ResourceConfigurations.getProperty("diagnosticPopUpText"),
                    "Verify diagnostic popup message text is displayed in . "
                            + ResourceConfigurations.getProperty("language"));
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
            // Click on cancel button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpCancelButton",
                    "Click on Cancel button on pop up.");
            objProductApplication_DiagnosticTestPage.verifyElementNotPresent(
                    "diagnosticPopUpHeadingText",
                    "Verify learner stay on the diagnostic test window.");
            WebDriver driver = returnDriver();
            driver.navigate().back();
            // Verify Warning message Heading Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpHeadingText",
                    ResourceConfigurations
                            .getProperty("diagnosticPopUpHeadingText"),
                    "Verify diagnostic popup message heading text is displayed in ."
                            + ResourceConfigurations.getProperty("language"));
            // Verify Warning message Text localization
            objProductApplication_DiagnosticTestPage.verifyText(
                    "DiagnosticPopUpText",
                    ResourceConfigurations.getProperty("diagnosticPopUpText"),
                    "Verify diagnostic popup message text is displayed in . "
                            + ResourceConfigurations.getProperty("language"));
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
            // verify continue button text is displayed in respective language
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeStartYourPathBtn",
                    ResourceConfigurations
                            .getProperty("diagnosticContinubuttonText"),
                    "Verify learner navigated to 'Resume' page.");
            // Click on continue button on resume screen
            objProductApplicationCourseHomePage.clickOnElement(
                    "CourseHomeStartYourPathBtn",
                    "Click on Continue button on resume screen.");
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
