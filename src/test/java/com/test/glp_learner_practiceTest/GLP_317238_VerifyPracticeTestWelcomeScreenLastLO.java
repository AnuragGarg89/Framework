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
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date March 19, 2018
 * @description: Verify the UI of practice test welcome screen
 * 
 * 
 */
public class GLP_317238_VerifyPracticeTestWelcomeScreenLastLO
        extends BaseClass {
    public GLP_317238_VerifyPracticeTestWelcomeScreenLastLO() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify the UI of practice test welcome screen except last LO")
    public void VerifyPracticeTestLastEOWelcomeScreen() {
        startReport(getTestCaseId(),
                "Verify the UI of practice test welcome screen except last LO");
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
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);

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

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElementIfVisible(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on desired Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.navigateCourseModuleByName(
                    ResourceConfigurations.getProperty("module11Text"));

            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on start button of first module");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on Last LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeTestLastLO",
                    "Click on Last LO");

            // Click on Last LO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestLastEOQuiz", "Click on practice quiz");

            // Verify practice test Welcome screen UI
            objGLPLearnerPracticeTestPage.verifyText("PracticeTestWelcomeText",
                    ResourceConfigurations
                            .getProperty("practiceTestLastLOHeaderText"),
                    "Verify practice test welcome screen heading text in"
                            + ResourceConfigurations.getProperty("language"));

            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestWelcomePageBody",
                    ResourceConfigurations
                            .getProperty("practiceTestBodyTextLastLO"),
                    "Verify practice test welcome screen body text in"
                            + ResourceConfigurations.getProperty("language"));

            objGLPLearnerPracticeTestPage.verifyText("PracticeTestStartButton",
                    ResourceConfigurations.getProperty(
                            "practiceTestWelcomePageStartButtonText"),
                    "Verify practice test welcome screen start button text in"
                            + ResourceConfigurations.getProperty("language"));

            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestWelcomePageInteract",
                    "Verify interact is displyed on the practice test welocme screen");

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
