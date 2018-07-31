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
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author anuj.tiwari1
 * @date Nov 28, 2017
 * @description : Verify that the horizontal slider is moveable only after
 *              clicking on the 'Edit' button.
 */
public class GLP_270920_VerifyDraggerMoveabilityAfterClickingEdit
        extends BaseClass {
    public GLP_270920_VerifyDraggerMoveabilityAfterClickingEdit() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify that the horizontal slider is moveable only after clicking on the 'Edit' button.")

    public void verifyDraggerMoveability() {
        startReport(getTestCaseId(),
                "Verify that the horizontal slider is moveable only after clicking on the 'Edit' button.");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Click on the Pearson Logo
            objProductApplicationCourseViewPage.clickOnElement("PearsonLogo",
                    "Click on the Pearson Logo");

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

            // Verify that the SLider is not movable before clicking the Edit
            // button.

            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "ManagementTabSliderNonMovable",
                    "Verify that the slider is not movable before clicking the edit button");

            // Verify Edit button on management dashboard.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor navigated to management dashboard.");

            // Click on edit button
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");

            // Verify that the Slider can be moved after clicking the Edit
            // button.
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "ManagementTabSliderAfterCLickingEditButton",
                    "Verify that the slider is movable after clicking the edit button");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
