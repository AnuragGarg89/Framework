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
 * @description : Verify that Save button is disabled if instructor entered non
 *              numeric value in threshold text box.
 */
public class GLP_270918_VerifyNonNumericValueIsNotAccepted extends BaseClass {
    public GLP_270918_VerifyNonNumericValueIsNotAccepted() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify that Save button is disabled if instructor entered non numeric value in threshold text box.")

    public void verifyNonNumericValueIsNotAccepted() {
        startReport(getTestCaseId(),
                "Verify that the Save button is disabled if instructor entered non numeric value in threshold text box.");
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
            // Verify save button on management dashboard
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementSaveButton",
                    "Verify save button is displayed.");
            // Verify cancel button on management dashboard
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementCancelButton",
                    "Verify cancel button is displayed.");
            // Enter non numeric value in text box
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxNonNumericValue"),
                    "Enter non numeric value in textbox");
            // Verify save button is disable
            objProductApplicationManagementDashboardPage
                    .verifyElementIsNotEnabled(
                            "InstructorManagementSaveButton");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
