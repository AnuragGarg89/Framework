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
 * @author mayank.mittal
 * @date May 15, 2018
 * @description : To verify the text on student list page when any value is
 *              selected in "student" drop down and in "content" drop down in
 *              Spanish and Arabic language
 * 
 */
public class GLP_319994_VerifyStudentListLocalisation extends BaseClass {
    public GLP_319994_VerifyStudentListLocalisation() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LOCALIZATION,
            Groups.INSTRUCTOR }, enabled = true,
            description = "To verify the text on student list page when any value is selected in student drop down and in content drop down in Spanish and Arabic language")

    public void verifyStudentListLocalisation() {
        startReport(getTestCaseId(),
                "To verify the text on student list page when any value is selected in student drop down and in content drop down in Spanish and Arabic language");
        try {
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

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Verify Student List is clicked.");

            objProductApplicationInstructorDashboardPage.verifyText(
                    "PerforManceDashBoardLockText",
                    ResourceConfigurations
                            .getProperty("perforManceDashBoardLockText"),
                    "Verify Performance Dashboard Lock text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "PerforManceDashBoardUnLockText",
                    ResourceConfigurations
                            .getProperty("perforManceDashBoardUnLockText"),
                    "Verify Performance Dashboard Lock text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "PerforManceDashBoardInText",
                    ResourceConfigurations
                            .getProperty("perforManceDashBoardInText"),
                    "Verify Performance Dashboard Lock text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "InstructorDashBoardExportButton",
                    ResourceConfigurations.getProperty(
                            "instructorPerformanceExportButtonText"),
                    "Verify Performance Dashboard Lock text is displayed in"
                            + ResourceConfigurations.getProperty("language"));

        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
