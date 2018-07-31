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
import com.glp.page.GLPLearner_PostAssessmentPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author anuj.tiwari1
 * @date May 16, 2018
 * @description: Verify functionality of CoachMark and Submit button for
 *               Practice Test
 * 
 */
public class GLP_341924_VerifySubmitBtn_CoachMark_PracticeTest
        extends BaseClass {
    public GLP_341924_VerifySubmitBtn_CoachMark_PracticeTest() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify Submit button and coachmark for Practice Test.")
    public void verifySubmitBtnAndCoachMark() {
        startReport(getTestCaseId(),
                "Verify Submit button and coachmark for Practice Test.");
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

            // objProductApplicationConsoleLoginPage
            // .login("GLP_Learner_341923_ftDq", "Password11");

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Navigate to the Diagnostic Test Page.
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Attempt diagnostic and Navigate to the practice test.
            APP_LOG.info("Attempt the diagnostic test");
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

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

            // Click on Start button of First LO for 16th module
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "PostAssessmentLO16.1StartButton",
                    "Click on start button of module test of 16th Module");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);
            // Click on the Practice Test of first EO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestFirstEOQuiz",
                    "Click on the Practice test of first EO.");

            // Click on the Start button to start the practice test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestStartButton",
                    "Click on the Start button to start the practice test.");

            // Verify that coachmark is displayed to the user
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestCoachMark",
                    "Verify that the CoachMark is displayed on the page");

            // Refresh the page
            objGLPLearnerPracticeTestPage.refreshPage();

            // Verify that CoachMark is displayed even after refreshing the
            // page.
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestCoachMark",
                    "Verify that the CoachMark is displayed on the page after refreshing the page.");

            // Click on cross icon on Practice Test page
            objGLPLearnerPracticeTestPage.clickOnCrossButton(
                    "PracticeTestCrossIconButton",
                    "Click on cross icon to exit Practice Test.");
            // Click on Leave button on Post Assessment Exit Pop up and exit the
            // Practice Test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpLeaveButton",
                    "Click on Leave button on pop up to exit Practice Test.");

            // Click on the Practice Test of first EO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestFirstEOQuiz",
                    "Click on the Practice test of first EO.");

            // Click on the Start button to start the practice test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestStartButton",
                    "Click on the Start button to start the practice test.");

            // Verify that CoachMark is displayed even after refreshing the
            // page.
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestCoachMark",
                    "Verify that the CoachMark is displayed on the page after coming back to Pratice Test.");

            // Verify that Submit button is enabled even without entering any
            // value.
            objGLPLearnerPracticeTestPage.verifyButtonEnabledOrDisabled(
                    "SubmitButton", "Yes",
                    "Verify that the Submit button in enabled without entering any value or selecting any option.");

            // Click on the Submit button and navigate to the next question
            objGLPLearnerPracticeTestPage.clickOnElement("SubmitButton",
                    "Click on the submit button and navigate to the next question.");

            // Verify that CoachMark is not displayed after Clicking the submit
            // button.
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestCoachMark",
                    "Verify that the CoachMark is not displayed on the page");

            // Click on cross icon on Practice Test page
            objGLPLearnerPracticeTestPage.clickOnCrossButton(
                    "PracticeTestCrossIconButton",
                    "Click on cross icon to exit Practice Test.");
            // Click on Leave button on Post Assessment Exit Pop up and exit the
            // Practice Test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpLeaveButton",
                    "Click on Leave button on pop up to exit Practice Test.");

            // Click on the Second EO to expand it.
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeTestSecondEO",
                    "Click on the Second EO to expand it.");

            // Click on the Practice Test of second EO
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestFirstEOQuiz",
                    "Click on the Practice test of second EO.");

            // Click on the Start button to start the practice test
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PracticeTestStartButton",
                    "Click on the Start button to start the practice test.");

            // Verify that CoachMark is not displayed on the practice test of
            // second EO
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "PracticeTestCoachMark",
                    "Verify that the CoachMark is not displayed on the page");

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
