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
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PostAssessmentPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date June 06, 2018
 * @description: Verify End to End flow for learner complete pre-assessment and
 *               instructor unlock module from UI and then learner complete
 *               module test
 */
public class GLP_358040_EndToEndModuleTestCompletion extends BaseClass {
    public GLP_358040_EndToEndModuleTestCompletion() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR,
            Groups.SANITY }, enabled = true,
            description = "Verify End to End flow for learner complete pre-assessment and instructor unlock module from UI and then learner complete module test")
    public void vndToEndModuleTestCompletion() {
        startReport(getTestCaseId(),
                "Verify End to End flow for learner complete pre-assessment and instructor unlock module from UI and then learner complete module test");
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

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Open drop down container to select 'All Students'
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'All Students'.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Open drop down container to select 'Module 16'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Module 16'.");

            // Select on 'Module 16' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "Module16DropDownValue",
                    "Select 'Module 16' from dropdown value.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Verify status 'Not Started' when module test is in 'locked' state
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusNotStarted",
                    "Verify status 'Not Started' when module test is in 'locked' state");

            // Click on check box against searched learner :
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardStudentCheckBox",
                    "Select 'check box' against searched learner.");

            // Click on 'unlock selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedButton",
                    "Click on 'Unlock Selected' button.");

            // Verify unlock module test pop up appears on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("unlockModulePopUp"),
                    "Verify 'Unlock Module' test pop-up is present on UI.");

            // Click on unlock now button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Unlock now' button.");

            // Verify status 'Ready' when module test is in 'Unlocked' state
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusReady",
                    "Verify status 'Ready' when module test is in 'Unlocked' state");

            // logout of the application
            objGLPInstructorDashboardPage.verifyLogout();

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Verify 'Start' button is displaying in front of 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    ResourceConfigurations.getProperty("module11StartButton"),
                    "Verify 'Start' button is displaying infornt of 'Module 16'.");

            // Click on 'Start' button for 'Module 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    "Click on 'Start' button for 'Module 16'");

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Verifying redirection on clicking the start button of module test
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "ModuleTestWelcomeScreenGoalText",
                    "verify learner is redirected to welcome screen on clicking the start test on module screen");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on start button on module test welcome screen");

            // Attempt module 16 test and complete it
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Verify module test result heading is present on UI
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "ModuleTestResultHeading",
                    ResourceConfigurations
                            .getProperty("moduleTestResultHeading"),
                    "Verify module test result heading text - 'You can do this!'.");

            // Verify module test result paragraph is present on UI
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "ModuleTestResultParagraph",
                    ResourceConfigurations
                            .getProperty("moduleTestResultParagraph"),
                    "Verify module test result paragraph text - 'Start practicing so you can test again.'.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
