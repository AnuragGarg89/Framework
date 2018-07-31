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
 * @author anuj.tiwari1
 * @date May 16, 2018
 * @description: Verify functionality of CoachMark and Submit button for Pre And
 *               Post Assessment.
 * 
 */
public class GLP_341923_VerifySubmitBtn_CoachMark_Pre_PostAssessment
        extends BaseClass {
    public GLP_341923_VerifySubmitBtn_CoachMark_Pre_PostAssessment() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify Submit button and coachmark for Pre and Post Assessment.")
    public void verifySubmitBtnAndCoachMark() {
        startReport(getTestCaseId(),
                "Verify Submit button and coachmark for Pre and Post Assessment.");
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

            APP_LOG.info("User navigated to the Diagnostic page successfully");
            // Verify that coachmark is displayed to the user
            objGLPLearnerDiagnosticTestPage.verifyElementPresent(
                    "DiagnosticCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Refresh the page
            objGLPLearnerDiagnosticTestPage.refreshPage();

            // Verify that the page is ready for the execution
            objGLPLearnerDiagnosticTestPage.returnSetOfQuestion();

            // Verify that CoachMark is displayed even after refreshing the
            // page.
            objGLPLearnerDiagnosticTestPage.verifyElementPresent(
                    "DiagnosticCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page and exit the
            // pre-assessment
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // Navigate back to the diagnostic test
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            // Verify that the page is ready for the execution
            objGLPLearnerDiagnosticTestPage.returnSetOfQuestion();

            // Verify that math pallet is displayed when the user comes back to
            // diagnostic page after exiting the diagnostic test in between
            objGLPLearnerDiagnosticTestPage.verifyElementPresent(
                    "DiagnosticCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Verify the text of the coach mark.
            objGLPLearnerDiagnosticTestPage.verifyText("DiagnosticGotItDialog",
                    ResourceConfigurations.getProperty("coachMarkText"),
                    "Verify the text of the CoachMark.");

            // Verify that the Got It link is present
            objGLPLearnerDiagnosticTestPage.verifyElementPresent(
                    "DiagnosticGotItButton",
                    "Verify that the Got it link is present on the coachmark.");

            // Click on the Got It link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGotItButton", "Click on the got it link.");

            // Verify that Submit button is enabled even without entering any
            // value.
            objGLPLearnerDiagnosticTestPage.verifyButtonEnabledOrDisabled(
                    "SubmitButton", "Yes",
                    "Verify that the Submit button in enabled without entering any value or selecting any option.");

            // Click on the Submit button and navigate to the next question
            objGLPLearnerDiagnosticTestPage.clickOnElement("SubmitButton",
                    "Click on the submit button and navigate to the next question.");
            // Verify that the page is ready for the execution
            objGLPLearnerDiagnosticTestPage.returnSetOfQuestion();

            // Verify that on the new question coachmark is not displayed again.
            objGLPLearnerDiagnosticTestPage.verifyElementNotPresent(
                    "DiagnosticCoachMark",
                    "Verify that the CoachMark is not displayed on the page");

            // Submitting the questions without selecting any answers.
            APP_LOG.info(
                    "Verifying the submission of questions without selecting or entering any answer.");

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Unlock the Post Assessment
            APP_LOG.info("Unlocking the Post Assessment");
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");

            // // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Click on Module test Start button of 16th module
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestChapter11StartButton",
                    "Click on start button of module test of 16th Module");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on start button on module test welcome screen");

            // Verify that coachmark is displayed to the user
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "PostAssessmentCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Refresh the page
            objGLPLearnerPostAssessmentPage.refreshPage();

            // Verify that the page is ready for the execution
            objGLPLearnerPostAssessmentPage.returnSetOfQuestion();

            // Verify that CoachMark is displayed even after refreshing the
            // page.
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "PostAssessmentCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Click on cross icon on Post Assessment page
            objGLPLearnerPostAssessmentPage.clickOnCrossButton(
                    "PostAssessmentCrossIconButton",
                    "Click on cross icon to exit Post Assessment.");
            // Click on Leave button on Post Assessment Exit Pop up and exit the
            // Post Assessment
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "PostAssessmentPopUpLeaveButton",
                    "Click on Leave button on pop up to exit Post Assessment.");

            // Click on Module test Continue button of 16th module
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestChapter11StartButton",
                    "Click on continue button of module test of 16th Module");

            // Verify that CoachMark is displayed after coming back to the Post
            // Assessment after exit.
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "PostAssessmentCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Click on the Got It link
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "PostAssessmentCoachMarkGotItButton",
                    "Click on the got it link.");

            // Refresh the page
            objGLPLearnerPostAssessmentPage.refreshPage();

            // Verify that the page is ready for the execution
            objGLPLearnerPostAssessmentPage.returnSetOfQuestion();

            // Verify that CoachMark is not displayed after refreshing the
            // page.
            objGLPLearnerPostAssessmentPage.verifyElementNotPresent(
                    "PostAssessmentCoachMark",
                    "Verify that the CoachMark is not displayed on the page");
            // Verify that Submit button is enabled even without entering any
            // value.
            objGLPLearnerPostAssessmentPage.verifyButtonEnabledOrDisabled(
                    "SubmitButton", "Yes",
                    "Verify that the Submit button in enabled without entering any value or selecting any option.");

            // Click on the Submit button and navigate to the next question
            objGLPLearnerPostAssessmentPage.clickOnElement("SubmitButton",
                    "Click on the submit button and navigate to the next question.");

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
