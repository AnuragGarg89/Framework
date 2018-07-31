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
 * @date Dec 13, 2017
 * @description :Verify that no inline message displays on the top of the
 *              management tab when the user clicks the 'Cancel' button
 */
public class GLP_283529_VerifyInlineMessageNotDisplayAfterClickingCancelButton
        extends BaseClass {
    GLP_283529_VerifyInlineMessageNotDisplayAfterClickingCancelButton() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify that no inline message displays on the top of the management tab when the user clicks the 'Cancel' button.")

    public void verifyInlineMessageNotDisplayAfterClickingCancelButton() {
        startReport(getTestCaseId(),
                "Verify that no inline message displays on the top of the management tab when the user clicks the 'Cancel' button.");
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
            // Get mastery value before edit
            String masteryLevelBeforeEdit = objProductApplicationManagementDashboardPage
                    .getElementAttribute("InstructorManagementTextBox", "value",
                            "Get the attribute value from the slider input box.");
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid value in textbox");
            // Click on cancel button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementCancelButton",
                    "Click on 'Cancel' button.");
            // verify warning message is not displayed
            objProductApplicationManagementDashboardPage
                    .verifyElementNotPresent(
                            "InstructorManagementWarningMessage",
                            "Verify inline message is not displayed.");

            // verify threshold value not updated
            objProductApplicationManagementDashboardPage.verifyTextContains(
                    "InstructorManagementSliderValue", masteryLevelBeforeEdit,
                    "Verify that threshold value is not updated.");
            // Click on edit button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            // Enter valid value in text box
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue2"),
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
            // Click on cancel button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementCancelButton",
                    "Click on 'Cancel' button.");
            // verify warning message is not displayed
            objProductApplicationManagementDashboardPage
                    .verifyElementNotPresent(
                            "InstructorManagementWarningMessage",
                            "Verify inline message is not displayed.");
            // verify threshold value not updated
            objProductApplicationManagementDashboardPage.verifyTextContains(
                    "InstructorManagementSliderValue", masteryLevelBeforeEdit,
                    "Verify that threshold value is not updated.");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
