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
 * @date Nov 23, 2017
 * @description : Verify content of management dashboard is displayed in
 *              respective language.
 */
public class GLP_271859_VerifyContentofManagementDashboardInRespectiveLanguage
        extends BaseClass {
    public GLP_271859_VerifyContentofManagementDashboardInRespectiveLanguage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify content of management dashboard is displayed in respective language.")

    public void verifyContentofManagementDashboardInRespectiveLanguage() {
        startReport(getTestCaseId(),
                "Verify content of management dashboard is displayed in respective language.");
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
            // verify text message heading in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "PreAssessmentMasteryLevelHeading",
                    ResourceConfigurations
                            .getProperty("managementDashboardTextMessageLine1"),
                    "Verify text message heading is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // verify text message in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementTabTextMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardTextMessageLine2"),
                    "Verify text message is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify that Edit button text is displayed in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementEditButton",
                    ResourceConfigurations
                            .getProperty("managementDashboardEditButton"),
                    "Verify Edit button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // verify EDit button tooltip text is displayed in respective
            // language
            objProductApplicationManagementDashboardPage
                    .verifyElementAttributeValue(
                            "InstructorManagementEditButton", "title",
                            ResourceConfigurations.getProperty(
                                    "managementDashboardEditButton"),
                            "Verify Edit button Tooltip text is displayed in "
                                    + ResourceConfigurations
                                            .getProperty("language"));
            // Click on edit button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            // Verify Save button text is displayed in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementSaveButton",
                    ResourceConfigurations
                            .getProperty("managementDashboardSaveButton"),
                    "Verify Save button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // Verify Cancel button text is displayed in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementCancelButton",
                    ResourceConfigurations
                            .getProperty("managementDashboardCancelButton"),
                    "Verify cancel button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Enter numeric invalid value in text box
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxNumericInValidValue"),
                    "Enter numeric invalid value in textbox");
            // Verify that the error message is displayed in respective language
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementErrorMessage",
                    ResourceConfigurations
                            .getProperty("managementDashboardErrorMessage"),
                    "Verify that the error message is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // verify Save button tooltip text is displayed in respective
            // language
            objProductApplicationManagementDashboardPage
                    .verifyElementAttributeValue(
                            "InstructorManagementSaveButton", "title",
                            ResourceConfigurations.getProperty(
                                    "managementDashboardSaveButton"),
                            "Verify Save button Tooltip text is displayed in "
                                    + ResourceConfigurations
                                            .getProperty("language"));
            // verify Cancel button tooltip text is displayed in respective
            // language
            objProductApplicationManagementDashboardPage
                    .verifyElementAttributeValue(
                            "InstructorManagementCancelButton", "title",
                            ResourceConfigurations.getProperty(
                                    "managementDashboardCancelButton"),
                            "Verify Cancel button Tooltip text is displayed in "
                                    + ResourceConfigurations
                                            .getProperty("language"));
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
