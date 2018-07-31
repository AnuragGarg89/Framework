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
 * @date Nov 9, 2017
 * @description : Verify that the threshold value saved by the instructor on
 *              Management page is retained on navigating away from the page.
 */
public class GLP_270929_VerifyThresholdValueIsSavedOnManagementPageOnNavigatingAwayFromPage
        extends BaseClass {
    public GLP_270929_VerifyThresholdValueIsSavedOnManagementPageOnNavigatingAwayFromPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify that the threshold value saved by the instructor on Management page is retained on navigating away from the page.")

    public void
           verifyThresholdValueIsSavedOnManagementPageOnNavigatingAwayFromPage() {
        startReport(getTestCaseId(),
                "Verify that the threshold value saved by the instructor on Management page is retained on navigating away from the page.");
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
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Navigate to performance dashboard page
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Switch to the Management tab
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();

            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "PreAssessmentMasteryLevelHeading",
                    "Verify Pre-Assessment Mastery Level Heading is present.");
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on Edit Button.");

            // Enter numeric value in text box
            objProductApplicationManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid numeric value in textbox");

            objProductApplicationManagementDashboardPage.clickOnSaveButton();

            returnDriver().navigate().back();

            // Switch to the Management tab
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();

            objProductApplicationManagementDashboardPage.verifyText(
                    "InstructorManagementMasteryPercentage",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue") + "%",
                    "Verify that the threshold value saved by the instructor on Management page is retained on navigating away from the page.");
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
