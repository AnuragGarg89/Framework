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
 * @author mayank.mittal
 * @date April 16, 2018
 * @description :To verify the pagination on instructor management page.
 * 
 */
public class GLP_316537_Pagination extends BaseClass {
    public GLP_316537_Pagination() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "To verify the pagination on instructor management page.")
    public void verifyPaginationOnInstructorManagementPage()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify the pagination on instructor management page.");

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

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceModuleDropDown",
                    "Verify Module List is clicked.");

            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    "Pre-assessment",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            // verify pagination is present.
            objProductApplicationInstructorDashboardPage
                    .verifyPaginationPresent();

            objProductApplicationInstructorDashboardPage.isButtonDisabled(
                    "PerforManceDashBoardStudentListPaginationPrevious");

            objProductApplicationInstructorDashboardPage
                    .verifyPaginationNextButtonIsDisabled();

            objProductApplicationInstructorDashboardPage.verifyElementCSSValue(
                    "PerforManceDashBoardStudentListPaginationPage1",
                    "border-bottom-style", "solid",
                    "Selected page is present with bottom border.");

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "PerforManceDashBoardStudentListPaginationPage2",
                    "Click on Page 2 to navigate next page student list");
            objProductApplicationInstructorDashboardPage.verifyElementCSSValue(
                    "PerforManceDashBoardStudentListPaginationPage2",
                    "border-bottom-style", "solid",
                    "Selected page is present with bottom border.");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
