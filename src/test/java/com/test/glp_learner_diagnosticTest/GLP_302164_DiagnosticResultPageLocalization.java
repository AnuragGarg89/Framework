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
 * @author mayank.mittal
 * @date Feb 02, 2018
 * @description: Verify the course diagnostic result page content in Arabic
 *               language.
 */

public class GLP_302164_DiagnosticResultPageLocalization extends BaseClass {
    public GLP_302164_DiagnosticResultPageLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify the course diagnostic result page content in Arabic language.")
    public void verifyDiagnosticResultPageLocalization()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "To Verify the course diagnostic result page content in "
                        + ResourceConfigurations.getProperty("language")
                        + "language.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);
            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            // Automate the remaining steps of test case
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            // Attempt all Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "ResultDiagnosticHeadingText",
                    ResourceConfigurations.getProperty(
                            "nonMasteredResultDiagnosticHeadingText"),
                    "Verify text is matched for Heading.");
            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "ResultDiagnosticText",
                    ResourceConfigurations
                            .getProperty("nonMasteredResultDiagnosticText"),
                    "Verify text is matched for Sub-Heading.");
            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "ResultDiagnosticStartButton",
                    ResourceConfigurations
                            .getProperty("resultDiagnosticStartButton"),
                    "Verify text is matched for Start Button.");
            objGLPLearnerDiagnosticTestPage.verifyTextContains(
                    "DiagnosticGoToCourseHomeLink",
                    ResourceConfigurations
                            .getProperty("resultDiagnosticGoToCourseHomeLink"),
                    "Verify learner is successfully navigated to Result Diagnostic screen.");

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