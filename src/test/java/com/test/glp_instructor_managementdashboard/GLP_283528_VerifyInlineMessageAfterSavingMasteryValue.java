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
 * @date Dec 13, 2017
 * @description : To verify the Inline Error Message after editing the mastery
 *              value and clicking the save button.
 */
public class GLP_283528_VerifyInlineMessageAfterSavingMasteryValue
        extends BaseClass {
    GLP_283528_VerifyInlineMessageAfterSavingMasteryValue() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify the Inline Error Message after editing the mastery value and clicking the save button.")

    public void verifyInlineMessageAfterSavingMasteryValue() {
        startReport(getTestCaseId(),
                "Verify the Inline Error Message after editing the mastery value and clicking the save button.");
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

            // Click on Edit Button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton",
                    "Click on the Edit button");

            // Enter the mastery value in the input box. The value should be a
            // valid
            // value
            // between 80-100
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementMasteryPercentageTextBox",
                    ResourceConfigurations
                            .getProperty("preAssessmentThresholdValidValue1"),
                    "Enter the valid mastery value in the input box");

            // Click on Save Button
            objProductApplicationManagementDashboardPage.clickOnSaveButton();      

            // Verify the success message displayed on management dashboard
            // page.
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementWarningMessage",
                    ResourceConfigurations
                            .getProperty("saveMasteryValueConfirmationMessage"),
                    "Verify the warning message is displayed on the management dashboard page when the user clicks browser back btn without saving the new mastery value.");

            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementTabClickCloseButton",
                    "Verify that the X icon is displayed on the success message.");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
