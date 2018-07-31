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
 * @author mayank.mittal
 * @date Dec 24, 2017
 * @description : To verify instructor is able to click on reset button on
 *              management tab.
 */
public class GLP_283103_VerifyInstructorClickOnResetButton extends BaseClass {
    public GLP_283103_VerifyInstructorClickOnResetButton() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "To verify instructor is able to click on reset button on management tab.")

    public void verifyInstructorClickOnResetButton() {
        startReport(getTestCaseId(),
                "To verify instructor is able to click on reset button on management tab.");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
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

            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementResetcoursedataText",
                    ResourceConfigurations
                            .getProperty("previewResetCourseDataText"),
                    "Verify Reset Course link text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementResetcoursedataText",
                    "Reset Course Data is present on Management Dashboard is clicked.");
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementResetDataPopUpMessageHeading",
                    ResourceConfigurations.getProperty("resetcoursedataText"),
                    "Verify Reset Data popup heading is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementResetDataPopUpMessage",
                    ResourceConfigurations.getProperty("resetDataPopUpMessage"),
                    "Verify Reset Data Pop Up Message is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementResetCourseDataCancelButton",
                    ResourceConfigurations
                            .getProperty("resetCourseDataCancelButtonText"),
                    "Verify Cancel Button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementResetCourseDataResetButton",
                    ResourceConfigurations
                            .getProperty("resetCourseDataResetButtonText"),
                    "Verify Reset Button text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
