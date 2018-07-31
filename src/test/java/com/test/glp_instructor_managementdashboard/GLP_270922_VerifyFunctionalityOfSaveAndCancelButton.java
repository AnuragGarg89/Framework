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
 * @author anuj.tiwari1
 * @date Nov 22, 2017
 * @description : Verify the functionality of 'Save' and 'Cancel' buttons that
 *              should appear on clicking the Edit button
 */
public class GLP_270922_VerifyFunctionalityOfSaveAndCancelButton
        extends BaseClass {
    public GLP_270922_VerifyFunctionalityOfSaveAndCancelButton() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify the functionality of 'Save' and 'Cancel' buttons that should appear on clicking the Edit button")
    public void verifyFunctionalityOfSaveAndCancelButton() {
        startReport(getTestCaseId(),
                "Verify the functionality of 'Save' and 'Cancel' buttons that should appear on clicking the Edit button");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to performance dashboard page
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Switch to the Management tab
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();

            // Verify that the Edit button is displayed on the Management
            // Dashboard
            // and click the Edit button.
            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify that the Edit button is displayed on the Management dashboard page");

            objProductApplicationManagementDashboardPage.clickOnEditButton();

            // Verify Save and Cancel buttons.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementSaveButton",
                    "Verify that the Save button is displayed on the Management dashboard page after clicking the Edit button");

            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementCancelButton",
                    "Verify that the Cancel button is displayed on the Management dashboard page after clicking the Edit button");

            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementTextBox",
                    "Verify that the Input box is displayed on the Management dashboard page after clicking the Edit button");

            // Get the input box value before editing the slider value.
            String updatedSliderValue = ResourceConfigurations
                    .getProperty("validSliderInputValueForSave");

            // Enter the valid value in the input box.
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox", updatedSliderValue,
                    "Enter an valid value in the slider input box");

            // Click on save button.
            objProductApplicationManagementDashboardPage.clickOnSaveButton();

            // Get mastery value after edit
            String valueAfterSaving = objProductApplicationManagementDashboardPage
                    .getText("InstructorManagementMasteryPercentage");

            // Verify that the updated value is saved
            objProductApplicationManagementDashboardPage
                    .verifySavedValueAfterEdit(updatedSliderValue,
                            valueAfterSaving, "save");

            // Click on the Edit Button again
            objProductApplicationManagementDashboardPage.clickOnEditButton();

            updatedSliderValue = ResourceConfigurations
                    .getProperty("validSliderInputValueForCancelVerification");

            // Enter the valid value in the input box.
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox", updatedSliderValue,
                    "Enter an valid value in the slider input box");

            // Click on cancel button.
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementCancelButton",
                    "Click on the cancel button.");

            // Get mastery value after cancelling
            String valueAfterCancelling = objProductApplicationManagementDashboardPage
                    .getText("InstructorManagementMasteryPercentage");

            // Verify that the updated value is not saved when the cancel button
            // is
            // clicked
            objProductApplicationManagementDashboardPage
                    .verifySavedValueAfterEdit(updatedSliderValue,
                            valueAfterCancelling, "cancel");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
