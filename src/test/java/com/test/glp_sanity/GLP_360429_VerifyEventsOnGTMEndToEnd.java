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
 * @author yogesh.choudhary
 * @date Dec 18, 2017
 * @description: Verify that the GA/GTM events are getting generated when FIB
 *               Dropdown questions appear for Diagnostic test
 * 
 */
public class GLP_360429_VerifyEventsOnGTMEndToEnd extends BaseClass {
    public GLP_360429_VerifyEventsOnGTMEndToEnd() {
    }

    @Test(groups = { Groups.SANITY, Groups.LEARNER, Groups.HEARTBEAT },
            enabled = false,
            description = "Verify that GA/GTM events are getting generated when FIB Dropdown questions appear for Diagnostic test")
    public void verifyEventsOnGTMEndToEnd() {
        startReport(getTestCaseId(),
                "Verify that the GA/GTM events are getting generated when FIB Dropdown questions appear for Diagnostic test.");
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
            // Close console
            objProductApplicationCourseHomePage.openCloseGTMConsole("DOWN");

            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            // Close console
            objProductApplicationCourseHomePage.openCloseGTMConsole("UP");
            // Verify GTM Event Triggered for Question Navigation
            objProductApplicationCourseHomePage.verifyGTMEventForAction(
                    ResourceConfigurations.getProperty("startLearner"));

            // Verify GTM Event Triggered for Question Navigation
            objProductApplicationCourseHomePage
                    .verifyGTMEventForAction(ResourceConfigurations
                            .getProperty("assessmentItemStarted"));

            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            // Close console
            objProductApplicationCourseHomePage.openCloseGTMConsole("DOWN");
            // Attempt all Diagnostic Test Question
            objProductApplication_DiagnosticTestPage
                    .attemptAdaptiveDiagnosticTest(0, 1, ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            // Open Console
            objProductApplicationCourseHomePage.openCloseGTMConsole("UP");

            // Verify GTM Event Triggered for Question Submission/Completed
            objProductApplicationCourseHomePage.verifyGTMEventForAction(
                    ResourceConfigurations.getProperty("assessmentCompleted"));
            // Verify GTM Event Triggered for New Question display
            objProductApplicationCourseHomePage
                    .verifyGTMEventForAction(ResourceConfigurations
                            .getProperty("assessmentItemStarted"));

            // Close console
            objProductApplicationCourseHomePage.openCloseGTMConsole("DOWN");

            // Navigate to Multipart Question
            objProductApplication_DiagnosticTestPage
                    .navigateToSpecificQuestionType(ResourceConfigurations
                            .getProperty("fibFreeResponse"));
            // Open Console
            objProductApplicationCourseHomePage.openCloseGTMConsole("UP");
            objProductApplicationCourseHomePage
                    .verifyGTMEventForAction(ResourceConfigurations
                            .getProperty("assessmentItemStarted"));

            // Check Exit Learner
            objProductApplicationCourseHomePage.openCloseGTMConsole("DOWN");
            // Click on Close Diagonastic Icon
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // Open Console
            objProductApplicationCourseHomePage.openCloseGTMConsole("UP");
            objProductApplicationCourseHomePage.verifyGTMEventForAction(
                    ResourceConfigurations.getProperty("exitLearner"));

            // Check Resume Learner
            objProductApplicationCourseHomePage.openCloseGTMConsole("DOWN");
            // Click on Continue button
            objProductApplicationCourseHomePage.clickOnElement(
                    "CourseHomeContinueDiagnosticButton",
                    "Click on Continue button");
            // Open Console
            objProductApplicationCourseHomePage.openCloseGTMConsole("UP");
            objProductApplicationCourseHomePage.verifyGTMEventForAction(
                    ResourceConfigurations.getProperty("resumeLearner"));

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}
