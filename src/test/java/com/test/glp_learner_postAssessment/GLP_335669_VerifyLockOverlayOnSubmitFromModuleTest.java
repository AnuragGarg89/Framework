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
 * @date May 11, 2018
 * @description: Verify lock overlay page display on selecting submit button
 *               from module test
 * 
 */
public class GLP_335669_VerifyLockOverlayOnSubmitFromModuleTest
        extends BaseClass {
    public GLP_335669_VerifyLockOverlayOnSubmitFromModuleTest() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify lock overlay page display on selecting submit button from module test")
    public void verifyLockOverlayOnSubmitFromModuleTest() {
        startReport(getTestCaseId(),
                "Verify lock overlay page display on selecting submit button from module test");
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
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course materialpage");
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Verify 'Start' button is displaying in front of 'Module 16'.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "ModuleTestChapter11StartButton",
                    ResourceConfigurations.getProperty("module11StartButton"),
                    "Verify 'Start' button is displaying in front of 'Module 16'");

            // Click on 'Start' button for 'Module 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "ModuleTestChapter11StartButton",
                    "Click on 'Start' button for 'Module 16'");

            // Verifying redirection on clicking the start button of module test
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "ModuleTestWelcomeScreenGoalText",
                    "verify learner is redirected to welcome test screen on clicking the 'Start test' button on module screen");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on Start button on module test welcome test screen");

            // Lock 'Module 16' test again by instructor
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusLocked"), "16");

            // Click on 'Submit' button to save response
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "PostAssessmentTestSubmitButton",
                    "Click on 'Submit' button to save response");

            // Verify 'Lock Overlay' screen appears after clicking on 'Submit'
            // button
            objGLPLearnerPostAssessmentPage.verifyText("LockOverlayHeading",
                    ResourceConfigurations.getProperty("lockOverlayHeading"),
                    "Verify text 'You're ready for the Module 16 test!'");

            objGLPLearnerPostAssessmentPage.verifyText("LockOverlayParagraph",
                    ResourceConfigurations.getProperty("lockOverlayParagraph"),
                    "Verify text 'We've notified your instructor to unlock it.'");

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
