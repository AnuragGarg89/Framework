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

package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;

/**
 * @author Saurabh.Sharma
 * @date Jan 28, 2018
 * @description : Verify the Instructor is able to download the gradebook csv
 */
public class GLP_294772_VerifyInstructorIsAbleToDownloadGradebook
        extends BaseClass {
    public GLP_294772_VerifyInstructorIsAbleToDownloadGradebook() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Test to verify that Instructor is able to download the gradebook csv on performance dashboard")

    public void verifyGradebookDownload() {
        startReport(getTestCaseId(),
                "Test to verify that Instructor is able to download the gradebook csv on performance dashboard");
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
                    "Courses associated with instructor displayed on Instructor homepage");
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
            // Check whether the error page is not displayed
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent("GradebookExportErrorAlert",
                            "Verify instructor is not displayed the error screen on clicking the export button");
            objProductApplicationInstructorDashboardPage
                    .verifyGradeBookDownloaded(
                            configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                            configurationsXlsMap.get("INSTRUCTOR_PASSWORD"),
                            "Verify gradebook download");
        }
        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
