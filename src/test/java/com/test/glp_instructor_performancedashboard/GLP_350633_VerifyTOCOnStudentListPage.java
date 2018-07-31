
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
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date June 03, 2018
 * @description: Verify display of Chapter 11-16 and first non-mastered module
 *               in placement column on Student List page when filter selected
 *               are - 'All Students' and 'Pre-Assessment' This testcase also
 *               cover another testcase - 350634
 */
public class GLP_350633_VerifyTOCOnStudentListPage extends BaseClass {
    public GLP_350633_VerifyTOCOnStudentListPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify display of Chapter 11-16 and first non-mastered module in placement column on Student List page when filter selected are - 'All Students' and 'Pre-Assessment'")
    public void verifyTOCOnStudentListPage() {
        startReport(getTestCaseId(),
                "Verify display of Chapter 11-16 and first non-mastered module in placement column on Student List page when filter selected are - 'All Students' and 'Pre-Assessment'");
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
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);

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

            // GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage =
            // new GLPLearner_CourseMaterialPage(
            // reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            // objGLPLearnerCourseMaterialPage.clickOnElement(
            // "DiagnosticGoToCourseHomeLink",
            // "Click on Go To Course Home Link to navigate to course material
            // page");

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
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

            // Open drop down container to select 'All Modules'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'All Modules'.");

            // Verify drop down content have value - 'Pre-Assessment'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=2",
                    ResourceConfigurations
                            .getProperty("contentDropdownPreassessment"),
                    "Verify drop down content have value - 'Pre-Assessment'");

            // Verify drop down content have value - 'Module 11'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=3",
                    ResourceConfigurations.getProperty("module11StudentDetail"),
                    "Verify drop down content have value - 'Module 11'");

            // Verify drop down content have value - 'Module 12'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=4",
                    ResourceConfigurations.getProperty("module12StudentDetail"),
                    "Verify drop down content have value - 'Module 12'");

            // Verify drop down content have value - 'Module 13'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=5",
                    ResourceConfigurations.getProperty("module13StudentDetail"),
                    "Verify drop down content have value - 'Module 13'");

            // Verify drop down content have value - 'Module 14'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=6",
                    ResourceConfigurations.getProperty("module14StudentDetail"),
                    "Verify drop down content have value - 'Module 14'");

            // Verify drop down content have value - 'Module 15'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=7",
                    ResourceConfigurations.getProperty("module15StudentDetail"),
                    "Verify drop down content have value - 'Module 15'");

            // Verify drop down content have value - 'Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "ContentDropdownVaules:dynamicReplace=8",
                    ResourceConfigurations.getProperty("module16StudentDetail"),
                    "Verify drop down content have value - 'Module 16'");

            // Select on 'Pre-Assessment' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Select 'Pre-Assessment' from dropdown value.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Verify 'Module 1' is displaying in front of searched learner in
            // 'Placement' column
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentListPlacementModule",
                    ResourceConfigurations.getProperty("module1StudentDetail"),
                    "'Module 1' is displaying in front of searched learner in 'Placement' column.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
