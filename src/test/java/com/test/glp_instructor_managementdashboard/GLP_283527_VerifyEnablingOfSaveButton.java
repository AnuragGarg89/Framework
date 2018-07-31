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
package com.test.glp_instructor_managementdashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author pallavi.tyagi
 * @date Dec 12, 2017
 * @description : When the inline alert message is displayed on the management
 *              tab and the user hasn't clicked on the 'X' button then the user
 *              should be able to click on SAVE or CANCEL button and/or should
 *              be able to edit the mastery value from slider or text box.
 */
public class GLP_283527_VerifyEnablingOfSaveButton extends BaseClass {
    GLP_283527_VerifyEnablingOfSaveButton() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "When the inline alert message is displayed on the management tab and the user hasn't clicked on the 'X' button then the user should be able to click on SAVE or CANCEL button and/or should be able to edit the mastery value from slider or text box.")

    public void verifyEnablingOfSaveButton() {
        startReport(getTestCaseId(),
                "When the inline alert message is displayed on the management tab and the user hasn't clicked on the 'X' button then the user should be able to click on SAVE or CANCEL button and/or should be able to edit the mastery value from slider or text box.");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Export Button should be displayed on Performance Dashboard page");

            // Switch to the Management tab
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();
            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            // Verify Edit button on management dashboard.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor navigated to management dashboard.");
            // Click on edit button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid value in textbox");
            // click on performance dashboard
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementPerformanceTab",
                    "Click on performance dashboard.");
            // verify inline message is displayed
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementWarningMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardWarningMessage"),
                    "Verify inline message is displayed ");
            // Verify x button in inline message.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementWarningMessageCrossButton",
                    "Verify x button in warning inline message.");
            // Verify save button on management dashboard
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementSaveButton",
                    "Verify save button is displayed.");
            // Verify cancel button on management dashboard
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementCancelButton",
                    "Verify cancel button is displayed.");
            // Click on Cross button of inline message
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementWarningMessageCrossButton",
                    "Click on x button of inline message.");
            // Verify Edit button on management dashboard.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify Edit button is displayed.");
            // verify save button is not displayed
            objProductApplicationManagementDashboardPage
                    .verifyElementNotPresent("InstructorManagementSaveButton",
                            "Verify save button is not displayed.");
            // Verify Cancel button is not displayed
            objProductApplicationManagementDashboardPage
                    .verifyElementNotPresent("InstructorManagementCancelButton",
                            "Verify Cancel button is not displayed.");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
