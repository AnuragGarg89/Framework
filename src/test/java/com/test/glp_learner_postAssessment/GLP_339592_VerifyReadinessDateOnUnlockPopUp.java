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
 * @date May 16, 2018
 * @description: Verify readiness date on 'Unlock' pop-up when learner is in
 *               locked state
 * 
 */
public class GLP_339592_VerifyReadinessDateOnUnlockPopUp extends BaseClass {
    public GLP_339592_VerifyReadinessDateOnUnlockPopUp() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify readiness date on 'Unlock' pop-up when learner is in locked state")
    public void verifyReadinesDateOnUnlockPopUp() {
        startReport(getTestCaseId(),
                "Verify readiness date on 'Unlock' pop-up when learner is in locked state");
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
                    "verify learner is redirected to welcome test screen on clicking the 'Start test' button on module screen");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on Start button on module test welcome test screen");

            // Click on 'cross' icon to close module 16 test
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestCloseButton",
                    "Click on 'cross' icon to close module 16 test");

            // Click on 'Leave' button to close module 16 test
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestPopUpLeaveButton",
                    "Click on 'Leave' button to close module 16 test");

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

            // Unlock module 16 test for learner
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusLocked"), "16");

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement("ContentDropDown",
                    "Expand the menu.");
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Select check box against learner name
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardStudentCheckBox",
                    "Select checkbox against learner '" + learnerUserName
                            + "'");

            // Click on 'Unlock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedButton",
                    "Click on 'Unlock Selected' button");

            // Verify 'Unlock Module' test pop-up appears
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("unlockModulePopUp"),
                    "Verify 'Unlock Module' test pop-up is present on UI.");

            // Verify 'Readiness' is showing for learner
            objGLPInstructorDashboardPage.isValidDate("ReadinessDate");

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
