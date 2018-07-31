
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
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date May 24, 2018
 * @description: Verify 'Unlocked Selected' is disable when selected - 'All
 *               students' and 'All modules'
 */
public class GLP_339594_VerifyUnlockSelectedIsDisableForAllStudentAndAllModules
        extends BaseClass {
    public GLP_339594_VerifyUnlockSelectedIsDisableForAllStudentAndAllModules() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify 'Unlocked Selected' is disable when selected - 'All students' and 'All modules'")
    public void verifyUnlockSelectedIsDisableForAllStudentAndAllModules() {
        startReport(getTestCaseId(),
                "Verify 'Unlocked Selected' is disable when selected - 'All students' and 'All modules'");
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
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on 'Start' button in front of first LO of module 16
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=1",
                    "Click on 'Start' button in front of first LO of module 16");

            GLPLearner_PracticeTestPage objProductApplicationPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on 'back arrow' icon to go back to practice test page
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on 'back arrow' icon to go back to practice test page");

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

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Select 'Pre-Assessment' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "PreassessmentDropdownValue",
                    "Select 'Pre-Assessment' from drop-down value.");

            // Select 'All Modules' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllModuleDropDownValue",
                    "Select 'All Modules' from dropdown value.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Select check box against student name
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardStudentCheckBox",
                    "Select check box against student name.");

            // Verify 'Unlock Selected' button is disable for selected learner
            if (objGLPInstructorDashboardPage.isButtonDisabled(
                    "InstructorDashBoardUnLockSelectedButton")) {
                logResultInReport(
                        Constants.PASS
                                + ": 'Unlock Selected' button is disable.",
                        "Verify 'Unlock Selcted' button' is disable for '"
                                + learnerUserName + "' .",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": 'Unlock Selected' button is not disable.",
                        "Verify 'Unlock Selcted' button' is disable for '"
                                + learnerUserName + "' .",
                        reportTestObj);
            }

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
