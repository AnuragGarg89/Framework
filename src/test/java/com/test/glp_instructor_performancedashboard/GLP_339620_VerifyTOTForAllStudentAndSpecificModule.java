
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
package com.test.glp_instructor_performancedashboard;

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
 * @date June 03, 2018
 * @description: Verify TOT for learner in 'Not Started' and 'Placed Out' and
 *               'Ready' state when selected - 'All Students' and 'Module N'
 */
public class GLP_339620_VerifyTOTForAllStudentAndSpecificModule
        extends BaseClass {
    public GLP_339620_VerifyTOTForAllStudentAndSpecificModule() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify TOT for learner in 'Not Started' and 'Placed Out' and 'Ready' state when selected - 'All Students' and 'Module N'")
    public void verifyTOTForNotStartedAndPlacedOutStateInModule() {
        startReport(getTestCaseId(),
                "Verify TOT for learner in 'Not Started' and 'Placed Out' and 'Ready' state when selected - 'All Students' and 'Module N'");
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

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Complete Diagnostic Test Question
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

            // Click on 'Module 16' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "Module16DropDownValue",
                    "Click on 'Module 16' from dropdown value.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Verify status is 'Not Started'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusNotStarted",
                    "Module 16 status is 'Not Started' verified successfully.");

            // Verify TOT should be display against searched learner as zero
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashboardTimeOnTask",
                    ResourceConfigurations.getProperty("zeroTimeOnTask"),
                    "TOT displayed against searched learner '" + learnerUserName
                            + "' as Zero for module 16.");

            // Open drop down container to select 'Module 15'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Module 15'.");

            // Click on 'Module 15' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "Module15DropdownValue",
                    "Click on 'Module 15' from dropdown value.");

            // Verify status is 'Placed out'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusPlacedOut",
                    "Module 15 status is 'Placed out' verified successfully.");

            // Verify TOT should be display against searched learner
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashboardTimeOnTaskDashed", "class", "dashed",
                    "TOT should be display against searched learner '"
                            + learnerUserName + "' as '--' for module 15.");

            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");

            // logout of the application
            objGLPInstructorDashboardPage.verifyLogout();

            // login as learner again to start module test
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // click on course tile
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // click on 'Start test' button in front of module 16 test
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    "Click on 'Start test' button in front of 'Module 16'.");

            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Verifying redirection on clicking the start button of module test
            objGLPLearnerPostAssessmentPage.verifyElementPresent(
                    "ModuleTestWelcomeScreenGoalText",
                    "verify learner is redirected to welcome screen on clicking the start test on module screen");

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on 'Start test' button on module test welcome screen");

            // Start module 16 test and attempt two questions
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

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

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

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

            // Click on 'Module 16' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "Module16DropDownValue",
                    "Click on 'Module 16' from dropdown value.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Verify status is 'Ready'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashBoardPerformanceStatusReady",
                    ResourceConfigurations.getProperty("statusReady"),
                    "Verify 'Ready'status on 'Status' column for learner on student list page.");

            // Verify TOT should be display against searched learner
            objGLPInstructorDashboardPage
                    .isValidTimeOnTask("InstructorDashboardTimeOnTask");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
