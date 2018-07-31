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
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_StudentPerformanceDetailsPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author pankaj.sarjal
 * @date June 07, 2018
 * @description: Verify reset functionality at instructor end This test case
 *               also cover another test case - 321232
 */
public class GLP_321234_VerifyResetFunctionalityAtInstructorEnd
        extends BaseClass {
    public GLP_321234_VerifyResetFunctionalityAtInstructorEnd() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify reset functionality at instructor end")
    public void verifyResetFunctionalityAtInstructorEnd()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify reset functionality at instructor end");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, false);

            // Login to the application as a Instructor
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to 'Welcome Screen Instructor' page
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = objGLPInstructorCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            // Navigate to 'Mastery Level' screen page
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Instructor dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Click on 'Unlock Now' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Unlock Now' button");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Open drop down container to select 'Pre-Assessment'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Pre-Assessment'.");

            // Click on Pre-assessment button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Click on 'Pre-Assessment option");

            // Verify status is 'Not Started'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusNotStarted",
                    "Verify student pre-assessment status is 'Not Started'.");

            // Click on student link text from student list page
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardStudentLinkText",
                    "Click on student link text from student list page.");

            GLPInstructor_StudentPerformanceDetailsPage ObjGLPInstructorStudentPerformanceDetailsPage = new GLPInstructor_StudentPerformanceDetailsPage(
                    reportTestObj, APP_LOG);

            // Verify 'Student Name' is displaying at student detail page
            if (ObjGLPInstructorStudentPerformanceDetailsPage
                    .getText("StudentNameText").contains(learnerUserName)) {
                logResultInReport(
                        Constants.PASS + ": Student Name '" + learnerUserName
                                + "' is displaying at student detail page",
                        "Verify Student Name '" + learnerUserName
                                + "' is displaying at student detail page.",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": Student Name '" + learnerUserName
                                + "' not displaying at student detail page",
                        "Verify Student Name '" + learnerUserName
                                + "' is displaying at student detail page.",
                        reportTestObj);
            }

            // Verify 'Student Email' is displaying at 'student detail' page
            if (ObjGLPInstructorStudentPerformanceDetailsPage
                    .getText("StudentEmailText").contains(ResourceConfigurations
                            .getProperty("studentEmailText"))) {
                logResultInReport(
                        Constants.PASS + ": Student Email '"
                                + ResourceConfigurations
                                        .getProperty("studentEmailText")
                                + "' is displaying at student detail page",
                        "Verify Student Email '"
                                + ResourceConfigurations
                                        .getProperty("studentEmailText")
                                + "' is displaying at student detail page.",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": Student Email '"
                                + ResourceConfigurations
                                        .getProperty("studentEmailText")
                                + "' is not displaying at student detail page",
                        "Verify Student Email '"
                                + ResourceConfigurations
                                        .getProperty("studentEmailText")
                                + "' is displaying at student detail page.",
                        reportTestObj);
            }

            GLPInstructor_ManagementDashboardPage ObjGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);

            // Click on 'cross' icon to close student detail list page
            ObjGLPInstructorManagementDashboardPage.clickByJS(
                    "StudentDetailCrossIcon",
                    "Click on 'cross' icon to close student detail list page");

            // Click on 'Management' tab
            objGLPInstructorDashboardPage.switchToManagementTab();

            // Click on 'Edit' button to reset mastery level
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton",
                    "Click on 'Edit' button to reset mastery level");

            // Click on slider
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementTabSliderValue",
                    "Click on the slider that is displayed on the Management dashboard.");

            // Move slider
            ObjGLPInstructorManagementDashboardPage.moveSlider();

            // Click on save button
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementSaveButton", "Click on save button.");

            // Logout from application
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login as 'Learner'
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile is present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            // Click on 'Cross' icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");

            // Click on 'Leave' button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // Logout from application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login to the application as a Instructor
            objGLPConsoleLoginPage.login(instructorName,
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

            // Open drop down container to select 'Pre-Assessment'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Pre-Assessment'.");

            // Select on 'Pre-Assessment' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Select 'Pre-Assessment' from dropdown value.");

            // Verify status is 'Testing'
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStatusTesting",
                    "Verify student pre-assessment' status is 'Testing'.");

            // Switch to 'Management' tab
            objGLPInstructorDashboardPage.switchToManagementTab();

            // Verify 'Edit' button is disable
            ObjGLPInstructorManagementDashboardPage.verifyElementIsNotEnabled(
                    "InstructorManagementEditButton");

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Click on 'Student' Name link
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardStudentLinkText",
                    "Click on student link text from student list page.");

            // verify reset button text message heading in respective language
            ObjGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementResetcoursedataText",
                    ResourceConfigurations.getProperty("resetButtonText"),
                    "Verify reset button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on 'Reset' button
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementResetcoursedataText",
                    "Click on 'reset course data' button to reset course.");

            // Click on 'Reset Now' button
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementResetCourseDataResetButton",
                    "Click on 'reset now' button on reset course popup.");

            // Click on 'cross' icon to close student detail list page
            ObjGLPInstructorManagementDashboardPage.clickByJS(
                    "StudentDetailCrossIcon",
                    "Click on 'cross' icon to close student detail list page");

            // Verify 'Management tab' is present on UI
            ObjGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorDashBoardManagementTab",
                    "Verify 'Management tab' is present on UI");

            // Switch to 'Management' tab
            ObjGLPInstructorManagementDashboardPage.switchToManagementTab();

            // Click on 'Edit' button to reset mastery level
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton",
                    "Click on 'Edit' button to reset mastery level");

            // Click on slider
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementTabSliderValue",
                    "Click on the slider that is displayed on the Management dashboard.");

            // Move slider
            ObjGLPInstructorManagementDashboardPage.moveSlider();

            // Click on save button
            ObjGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementSaveButton", "Click on save button.");

            // Verify 'Mastery Level' has been saved after rest of the course
            ObjGLPInstructorManagementDashboardPage.verifyTextContains(
                    "MasteryLevelSavedText",
                    ResourceConfigurations
                            .getProperty("saveMasteryValueConfirmationMessage"),
                    "Verify 'Mastery Level' has been saved after reset of the course");

            // Logout from application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login to the application as a learner
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            objGLPLearnerCourseViewPage.verifyText("CourseHomeStartYourPathBtn",
                    ResourceConfigurations.getProperty(
                            "welcomeLearnerPageStartPreAssessmentButton"),
                    "Verify learner is redirected to start pre assessment screen once instructor reset the course");

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}
