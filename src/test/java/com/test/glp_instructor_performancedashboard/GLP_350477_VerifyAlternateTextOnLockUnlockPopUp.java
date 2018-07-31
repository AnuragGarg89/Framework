
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
import com.autofusion.constants.Constants;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_StudentPerformanceDetailsPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date June 12, 2018
 * @description: Verify alternate text - select all' on lock/Unlock pop up This
 *               test case also cover other test cases - 350479,350482,350484
 */
public class GLP_350477_VerifyAlternateTextOnLockUnlockPopUp extends BaseClass {
    public GLP_350477_VerifyAlternateTextOnLockUnlockPopUp() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify alternate text - select all' on lock/Unlock pop up")
    public void verifyAlternateTextOnLockUnlockPopUp() {
        startReport(getTestCaseId(),
                "Verify alternate text - select all' on lock/Unlock pop up");
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

            // Unlock 'Module 16' for learner
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");

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
            objGLPInstructorDashboardPage.clickByJS("StudentDropdownContainer",
                    "Open dropdown container to select 'All Students'.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickByJS("AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Open drop down container to select 'All Modules'
            objGLPInstructorDashboardPage.clickByJS("ModuleDropdownContainer",
                    "Open dropdown container to select 'All Modules'.");

            // Select 'All Modules' from drop down value
            objGLPInstructorDashboardPage.clickByJS("AllModuleDropDownValue",
                    "Select 'All Modules' from dropdown value");

            // Verify tool tip for 'Select All' check box on student list page
            if (objGLPInstructorDashboardPage
                    .getAttributeOfHiddenElement("SelectFirstStudent", "title")
                    .equals(ResourceConfigurations
                            .getProperty("alternateTextSelectAll"))) {
                logResultInReport(Constants.PASS
                        + ": Alternate text 'Select All' on select all checkbox verified successfully. ",
                        "Verify alternate text 'Select All' select all checkbox.",
                        reportTestObj);
            } else {
                logResultInReport(Constants.FAIL
                        + ": Alternate text 'Select All' on select all checkbox is not verified .",
                        "Verify alternate text 'Select All' select all checkbox.",
                        reportTestObj);
            }

            // Click on 'Select All' student check box to select all student
            objGLPInstructorDashboardPage.clickByJS(
                    "PerforManceDashBoardStudentListCheckBox",
                    "Click on 'Select All' student check box to select all student");

            // "Click on 'Lock Selected' button");
            objGLPInstructorDashboardPage.clickByJS(
                    "InstructorDashBoardLockSelectedButton",
                    "Click on 'Lock Selected' button");

            // Verify 'Lock Module' test pop up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("lockModulePopUp"),
                    "Verify 'Lock Module' test pop up is present on UI");

            // Verify tool tip for 'Select All' check box on student list page
            if (objGLPInstructorDashboardPage
                    .getAttributeOfHiddenElement(
                            "UnlockModulePopUpSelectAllCheckbox", "title")
                    .equals(ResourceConfigurations
                            .getProperty("alternateTextSelectAll"))) {
                logResultInReport(Constants.PASS
                        + ": Alternate text 'Select All' on 'lock module tests' pop-up verified successfully. ",
                        "Verify alternate text 'Select All' on 'lock module tests' pop-up.",
                        reportTestObj);
            } else {
                logResultInReport(Constants.FAIL
                        + ": Alternate text 'Select All' on 'lock module tests' pop-up is not verified .",
                        "Verify alternate text 'Select All' on 'lock module tests' pop-up.",
                        reportTestObj);
            }

            // Click on 'Cancel' button and close 'lock module test' pop-up
            objGLPInstructorDashboardPage.clickByJS("UnlockPreAssessmentCancel",
                    "Click on 'Cancel' button and close 'lock module test' pop-up.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Click on student name link text to open student detail page
            objGLPInstructorDashboardPage.clickByJS(
                    "InstructorDashboardStudentLinkText",
                    "Click on student name link text to open student detail page.");

            GLPInstructor_StudentPerformanceDetailsPage objGLPInstructortudentPerformanceDetailsPage = new GLPInstructor_StudentPerformanceDetailsPage(
                    reportTestObj, APP_LOG);

            // Click on 'Lock Selected' button on student detail page
            objGLPInstructortudentPerformanceDetailsPage.clickByJS(
                    "StudentDetailLockButton",
                    "Click on 'Lock Selected' button on student detail page.");

            // Verify 'Lock module test' pop up is present on UI on student
            // detail page
            objGLPInstructortudentPerformanceDetailsPage.verifyTextContains(
                    "ModuleTestHeading",
                    ResourceConfigurations.getProperty("lockModulePopUp"),
                    "Verify 'Lock Module' test pop up is present on UI on student detail page.");

            // Verify tool tip for 'Select All' check box on student detail page
            if (objGLPInstructortudentPerformanceDetailsPage
                    .getAttributeOfHiddenElement("ModulePopUpSelectAllCheckbox",
                            "title")
                    .equals(ResourceConfigurations
                            .getProperty("alternateTextSelectAll"))) {
                logResultInReport(Constants.PASS
                        + ": Alternate text 'Select All' on 'lock module tests' pop-up on student detail page verified successfully. ",
                        "Verify alternate text 'Select All' on 'lock module tests' pop-up on student detail page.",
                        reportTestObj);
            } else {
                logResultInReport(Constants.FAIL
                        + ": Alternate text 'Select All' on 'lock module tests' pop-up  student detail page is not verified .",
                        "Verify alternate text 'Select All' on 'lock module tests' pop-up on student detail page .",
                        reportTestObj);
            }

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
