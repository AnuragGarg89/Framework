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
 * @author Ratnesh.Singh
 * @date April 14, 2018
 * @description: Verify that user is able input value in the Free Response
 *               Textbox and it reflects correctly in Free Response Textbox.
 * 
 */

public class GLP_332604_VerifyFreeResponseUserInput extends BaseClass {

    public GLP_332604_VerifyFreeResponseUserInput() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.FIBFREERESPONSE,
            Groups.LEARNER }, enabled = true,
            description = "Verify that user is able input value in the Free Response Textbox and it refects correctly in Textbox.")
    public void verifyMultiPartPageLocalization() {
        startReport(getTestCaseId(),
                "Verify that user is able input value in the Free Response Textbox and it refects correctly in Textbox.");
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

            // learnerUserName = "GLP_Learner_332604_ZORd";

            // Login in the application GLPConsole_LoginPage
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
            // Navigate to Free Response Question
            objProductApplication_DiagnosticTestPage
                    .navigateToSpecificQuestionType(ResourceConfigurations
                            .getProperty("fibFreeResponse"));

            // Click on Diagnostic Got It button if displayed
            objProductApplication_DiagnosticTestPage
                    .clickOnDiagnosticGotItButtonIfPresent();

            // Provide first user input in Free Response Text Box and verify
            // that
            // first input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput1"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeTextOrNumber"));

            // Provide second user input in Free Response Text Box and verify
            // that
            // second input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput2"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeTextOrNumber"));

            // Provide third user input in Free Response Text Box and verify
            // that
            // third input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput3"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeTextOrNumber"));

            // Provide fourth user input in Free Response Text Box and verify
            // that
            // fourth input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput4"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeTextOrNumber"));

            // Provide fifth user input in Free Response Text Box and verify
            // that
            // fifth input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput5"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeSquareRoot"));

            // Provide sixth user input in Free Response Text Box and verify
            // that
            // sixth input appears correctly in Free Response Box

            objProductApplication_DiagnosticTestPage
                    .verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponseUserInput6"),
                            ResourceConfigurations.getProperty(
                                    "freeResponseInputTypeModulus"));

            // Verify Submit button is Enabled once user provides input in Free
            // Response Box
            objProductApplication_DiagnosticTestPage
                    .verifyButtonEnabledOrDisabled("DiagnosticSubmitButton",
                            "yes", "Verify Submit button is Enabled");

            // Fetch Progress Bar Width before Submitting user response
            String progressBarWidthValueBefore = objProductApplication_DiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            // Click on 'Start Pre-assessment' button
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticSubmitButton",
                    "Verify submit button is clicked");

            // Fetch Progress Bar Width after Submitting user response
            String progressBarWidthValueAfter = objProductApplication_DiagnosticTestPage
                    .getCurrentWidthOfProgressBar();

            // Verify that on clicking Submit button user moves to next question
            // on Diagnostic and progress bar increases
            objProductApplication_DiagnosticTestPage
                    .verifyProgressBarForwardDirection(
                            progressBarWidthValueBefore,
                            progressBarWidthValueAfter);

        }

        finally {

            webDriver.quit();
            webDriver = null;
        }
    }

}
