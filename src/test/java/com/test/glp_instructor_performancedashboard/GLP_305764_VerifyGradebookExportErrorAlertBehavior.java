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
 */package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.util.GLP_Utilities;

/**
 * @author Saurabh.Sharma
 * @date Feb 16, 2018
 * @description : To verify error message should be displayed until the user
 *              closes the alert while remaining on the same page.
 */

public class GLP_305764_VerifyGradebookExportErrorAlertBehavior
        extends BaseClass {
    public GLP_305764_VerifyGradebookExportErrorAlertBehavior() {
    }

    @Test(groups = { Groups.HEARTBEAT, Groups.INSTRUCTOR }, enabled = true,
            description = "Test to verify error message is displayed until the user closes the alert while remaining on the same page.")

    public void verifyErrorMessageIsNotDisplayed() {
        startReport(getTestCaseId(),
                "Test to verify error message is displayed until the user closes the alert while remaining on the same page.");

        GLP_Utilities objGLP_Utilities = new GLP_Utilities(reportTestObj,
                APP_LOG);
        try {
            // Delete the template for the course for which grade book is being
            // down loaded
            objGLP_Utilities.deleteGradebookTemplate(
                    "en_" + configurationsXlsMap.get("LearnerModelId"));
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify course tile on course view page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instructor are displayed on Instructor homepage");
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Verify that Export Button is displayed on Performance Dashboard page");
            // Click on Export button
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardExportButton",
                    "Click on the Export button to download the gradebook");
            // Check whether the error page is displayed
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "GradebookExportErrorAlert",
                    "Verify error screen is displayed to the instructor on clicking the export button");
            // Verify user is able to perform some action(click on any footer
            // link) inspite of the error being displayed
            objProductApplicationInstructorDashboardPage.verifyLinkOpenInNewTab(
                    "DashBoardFooterAccessibilityLink",
                    ResourceConfigurations.getProperty(
                            "accessibilityInformationRevelPearsonText"),
                    "Verify user is able to click on Accessibility link and a new window opens.");
            // Click on close button of the error message
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardGBErrorAlertCloseButton",
                    "Click on the 'X' button to close the error alert");
            // Verify the error alert closes on clicking the 'X' button
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent("GradebookExportErrorAlert",
                            "Verify the error alert closes on clicking the 'X' button");
        }
        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
            objGLP_Utilities.createGradebookTemplate();
        }
    }
}
