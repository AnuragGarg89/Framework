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
 * @description: Test to verify that UI of modal pop-up window
 * 
 * 
 */
public class GLP_317495_VerifyPracticeTestLeavePopUpUI extends BaseClass {
    public GLP_317495_VerifyPracticeTestLeavePopUpUI() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "verify UI of leave practice test modal pop-up window")
    public void VerifyLeavePopUpOnPracticeTest() {
        startReport(getTestCaseId(),
                "Verify UI of leave practice test modal pop-up window");
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

            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of first LO");

            // Click on start button on practice test welcome screen
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");

            // Attempt 2 practice test questions
            objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 2,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on browser back button
            objGLPLearnerPracticeTestPage.clickBrowserBackButton(
                    "Click on browser back button from preactice test");

            // Verify on clicking cross icon pop up window is displayed
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestPopUpHeader",
                    "pop up window is displayed on clicking the cross icon on practice test");

            // Verify practice test leave pop up UI
            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestPopUpHeadingText",
                    ResourceConfigurations
                            .getProperty("practiceTestPopUpHeading"),
                    "Verify practice test leave pop up heading text");

            objGLPLearnerPracticeTestPage.verifyText("PracticeTestPopUpText",
                    ResourceConfigurations.getProperty("practiceTestPopUpText"),
                    "Verify practice test leave pop up heading body text");

            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestPopUpCancelButton",
                    ResourceConfigurations
                            .getProperty("practiceTestCancelText"),
                    "Verify practice test pop up cancel button text");

            objGLPLearnerPracticeTestPage.verifyText(
                    "PracticeTestPopUpLeaveButton",
                    ResourceConfigurations.getProperty("practiceTestLeaveText"),
                    "Verify practice test pop up leave button text");

            // Click on cancel button on the pop up
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpCancelButton",
                    "Click on cancel button on the pop up");
            // On clicking the cancel button on leave pop up learner pop up
            // window should be dismissed
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestPopUpHeader",
                    "pop up window should be dismissed on clicking the cancel button on pop up window");

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
