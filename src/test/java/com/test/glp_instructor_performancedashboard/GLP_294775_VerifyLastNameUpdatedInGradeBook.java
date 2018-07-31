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
 * @author lekh.bahl
 * @date Dec 05, 2017
 * @description : Verify Export button text localization
 */
public class GLP_294775_VerifyLastNameUpdatedInGradeBook extends BaseClass {
    public GLP_294775_VerifyLastNameUpdatedInGradeBook() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LOCALIZATION,
            Groups.INSTRUCTOR }, enabled = true,
            description = "Test to verify that export button should be displayed in language set in the browser")

    public void verifyFooterLinks() {
        startReport(getTestCaseId(),
                "Test to verify that export button should be displayed in language set in the browser");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Create Learner subscribing the new course created by the
            // Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objProductApplicationCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify Learner is logged in successfully.");
            objProductApplicationCourseViewPage.clickOnSettingsOption();
            objProductApplicationCourseViewPage.clickOnElement(
                    "CourseViewSettingsEditButton",
                    "Click on edit button to edit last name.");
            String newLastName = "Test"
                    + objCommonUtil.generateRandomStringOfAlphabets(5);
            objProductApplicationCourseViewPage.typeText(
                    "CourseViewSettingsLastName",
                    "Enter new last name to update.", newLastName);
            objProductApplicationCourseViewPage.clickOnElement(
                    "CourseViewSettingsSaveButton",
                    "Click on save button to save and update last name.");
            objProductApplicationCourseViewPage.clickOnElement("PearsonLogo",
                    "Click on 'Pearson logo' to Navigate to 'Course View'.");
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objProductApplicationCourseViewPage.verifyCourseTilePresent();
            // Automate the remaining steps of test case
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));
            objProductApplicationCourseViewPage.verifyLogout();

            // Login now with instructor
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Export Button should be displayed on Performance Dashboard page");
            // Click on Export button
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardExportButton",
                    "Click on the Export button to download the gradebook");
            // Check whether the error page is not displayed
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent("GradebookExportErrorAlert",
                            "Verify instructor is not displayed the error screen on clicking the export button");
            String lastNameInGradebook = objProductApplicationInstructorDashboardPage
                    .getGradeBookDesiredColumnValue(instructorName,
                            ResourceConfigurations.getProperty(
                                    "consolePassword"),
                            "LastName");
            objProductApplicationInstructorDashboardPage.verifyTextEquals(
                    newLastName, lastNameInGradebook,
                    "Verify Last Name is updated in Gradebook.");

        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
