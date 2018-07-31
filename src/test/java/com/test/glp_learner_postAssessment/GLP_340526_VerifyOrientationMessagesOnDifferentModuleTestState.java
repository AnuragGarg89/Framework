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
package com.test.glp_learner_postAssessment;

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
import com.glp.page.GLPLearner_PostAssessmentPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date May 25, 2018
 * @description: Verify orientation messages for learner for different state of
 *               module test
 */
public class GLP_340526_VerifyOrientationMessagesOnDifferentModuleTestState
        extends BaseClass {
    public GLP_340526_VerifyOrientationMessagesOnDifferentModuleTestState() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify orientation messages for learner for different state of module test")
    public void verifyOrientationMessagesOnDifferentModuleTestState() {
        startReport(getTestCaseId(),
                "Verify orientation messages for learner for different state of module test");
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
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

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

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Verify text ' You are on your way ...'
            objGLPLearnerPostAssessmentPage.verifyText("YouAreOnWayHeading",
                    ResourceConfigurations.getProperty("CourseHomeHeading"),
                    "Verify text 'You're on your way!'");

            objGLPLearnerPostAssessmentPage.verifyText("YouAreReadyParagraph",
                    ResourceConfigurations.getProperty("CourseHomeParagraph"),
                    "Verify text 'You're ready for the Module 16 test.'");

            // Lock 'Module 16' test again by instructor
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusLocked"), "16");

            // Click on 'Start' button in front of 'Module 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    "Click on 'Start' button in front of 'Module 16'.");

            // Verify orientation message : You're ready for the Module 16 test!
            objGLPLearnerCourseMaterialPage.verifyText(
                    "ModuleTestResultHeading",
                    ResourceConfigurations.getProperty("lockOverlayHeading"),
                    "Verify orientation message 'You're ready for the Module 16 test!'");

            // Verify orientation message : We've notified your instructor to
            // unlock it.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "ModuleTestResultParagraph",
                    ResourceConfigurations.getProperty("lockOverlayParagraph"),
                    "Verify orientation message 'We've notified your instructor to unlock it.'");

            // Unlock 'Module 16' test for learner
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");

            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            // Click on 'Start test' button on 'lock overlay' screen
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    "Click on 'Start test' button on 'lock overlay' screen.");

            // Verifying redirection on clicking the start button of module test
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "ModuleTestWelcomeScreenGoalText",
                    "verify learner is redirected to welcome screen on clicking the start test on module screen");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on start button on module test welcome screen");

            // Click on cross icon to close module 16 test
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestCloseButton",
                    "Click on cross icon on module test");

            // Click on 'Leave' button to close module 16 test
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestPopUpLeaveButton",
                    "Click on leave button on module test exit pop up");

            // Verify orientation message : We've saved your place.
            objGLPLearnerCourseMaterialPage.verifyText("LockOverlayHeading",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText1"),
                    "Verify orientation message 'We've saved your place.'");

            // Verify orientation message : When you return, you can continue
            // working from
            // where you left off.
            objGLPLearnerCourseMaterialPage.verifyText("LockOverlayParagraph",
                    ResourceConfigurations
                            .getProperty("diagnosticResumeMessageText2"),
                    "Verify orientation message 'When you return, you can continue working from where you left off.'");

            // Click on 'continue' button in front of 'Module 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    "Click on 'continue' button in front of 'Module 16'.");

            // Start giving module 16 test once again and complete it
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Verify orientation message : You can do this
            objGLPLearnerCourseMaterialPage.verifyText(
                    "ModuleTestResultHeading",
                    ResourceConfigurations
                            .getProperty("moduleTestResultHeading"),
                    "Verify orientation message 'You can do this!'");

            // Verify orientation message : Start practicing so you can
            // test again.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "ModuleTestResultParagraph",
                    ResourceConfigurations
                            .getProperty("moduleTestResultParagraph"),
                    "Verify orientation message 'Start practicing so you can test again.'");

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
