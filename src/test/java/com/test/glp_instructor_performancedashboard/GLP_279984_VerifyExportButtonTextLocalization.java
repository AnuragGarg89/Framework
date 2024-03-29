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
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;

/**
 * @author lekh.bahl
 * @date Dec 05, 2017
 * @description : Verify Export button text localization
 */
public class GLP_279984_VerifyExportButtonTextLocalization extends BaseClass {
    public GLP_279984_VerifyExportButtonTextLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LOCALIZATION,
            Groups.INSTRUCTOR }, enabled = true,
            description = "Test to verify that export button should be displayed in language set in the browser")

    public void verifyFooterLinks() {
        startReport(getTestCaseId(),
                "Test to verify that export button should be displayed in language set in the browser");
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

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Export Button should be displayed on Performance Dashboard page");

            // Verify that Export button text is displayed in language set in
            // the
            // browser
            objProductApplicationInstructorDashboardPage.verifyText(
                    "InstructorDashBoardExportButton",
                    ResourceConfigurations.getProperty(
                            "instructorPerformanceExportButtonText"),
                    "Verify Export button text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
