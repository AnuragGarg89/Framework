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

import org.openqa.selenium.WebDriver;
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
 * @description : To verify the Inline Error Message by navigating away from the
 *              page.
 */
public class GLP_283526_VerifyInlineMessageByNavigatingAwayFromPage
        extends BaseClass {
    GLP_283526_VerifyInlineMessageByNavigatingAwayFromPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verfiy that the inline error message is displayed in the management page when user navigates without saving value.")

    public void verifyInlineMessageByNavigatingAwayFromPage() {
        startReport(getTestCaseId(),
                "Verfiy that the inline error message is displayed in the management page when user navigates without saving value.");
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

            // Click on browser back
            WebDriver driver = returnDriver();
            driver.navigate().back();
            // Verify the warning message displayed on management dashboard
            // page.
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementWarningMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardWarningMessage"),
                    "Verify the warning message is displayed on the management dashboard page when the user clicks browser back btn without saving the new mastery value.");

            // Enter the mastery value in the input box. The value should be a
            // valid
            // value
            // between 80-100
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementMasteryPercentageTextBox",
                    ResourceConfigurations
                            .getProperty("preAssessmentThresholdValidValue2"),
                    "Enter the valid mastery value in the input box");
            // Click on performance dashboard
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementPerformanceTab",
                    "Click on performance tab");

            // Verify the warning message displayed on management dashboard
            // page.
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementWarningMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardWarningMessage"),
                    "Verify the warning message is displayed on the management dashboard page when the user navigates to Performance Dashboard without saving the new mastery value.");

            // Enter the mastery value in the input box. The value should be a
            // valid
            // value
            // between 80-100
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementMasteryPercentageTextBox",
                    ResourceConfigurations
                            .getProperty("preAssessmentThresholdValidValue2"),
                    "Enter the valid mastery value in the input box");

            // Click on the Sign Out link and try to sign out of the
            // application.
            objProductApplicationCourseViewPage.verifyLogout();

            // Verify the warning message displayed on management dashboard
            // page.
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementWarningMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardWarningMessage"),
                    "Verify the warning message is displayed on the management dashboard page when the user tries to Sign Out without saving the new mastery value.");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
