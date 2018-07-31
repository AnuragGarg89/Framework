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

package com.test.glp_endtoend;

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
 * @date Nov 14, 2017
 * @description : Verify that instructor is navigated to instructor Welcome
 *              Screen on clicking "Squires title card" displayed on Course Home
 *              page.
 * 
 */
public class GLP_283510_VerifyMasteryEditingForExistingUser extends BaseClass {
    public GLP_283510_VerifyMasteryEditingForExistingUser() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "Verify existing Instructor coming from Console and editing pre-assesment mastery scroe from GLP.")

    public void verifyMaterialHeader() {
        try {
            startReport(getTestCaseId(),
                    "Verify existing Instructor coming from Console and editing pre-assesment mastery scroe from GLP.");
            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            // Login to the application as an Instructor
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            // Navigate to the performance dashboard
            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            objGLPInstructorInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Verify instructor has navigated to Performance Dashboard page.");

            // Switch to the Management tab
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();
            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            // Verify instructor is navigated to management dashboard.
            objGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor is navigated to management dashboard.");
            // Click on edit button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMinValue"),
                    "Enter invalid value less than 80% in textbox");
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify Error message for invalid value less than 80.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMaxValue"),
                    "Enter invalid value more than 100% in textbox");
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify Error message for invalid value more than 100.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid value between 80 to 100% in textbox");
            // Click on save button
            objGLPInstructorManagementDashboardPage.clickOnSaveButton();
            // Verify pre-assessment mastery threshold value is updated by
            // clicking
            // on save button.
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementMasteryPercentage",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue") + "%",
                    "Verify pre-assessment mastery threshold value is updated by clicking on save button.");
            // Add logout and login
            objGLPInstructorCourseViewPage.verifyLogout();
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to the performance dashboard
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementMasteryPercentage",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue") + "%",
                    "Verify pre-assessment mastery threshold value which was updated is reflected back after login again.");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
