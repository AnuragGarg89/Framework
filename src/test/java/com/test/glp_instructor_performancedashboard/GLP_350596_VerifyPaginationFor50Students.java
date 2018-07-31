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
 * @author pankaj.sarjal
 * @date June 14, 2018
 * @description :To verify the pagination for 50 students on student list page.
 *              This test case also cover another test case - 350595
 * 
 */
public class GLP_350596_VerifyPaginationFor50Students extends BaseClass {
    public GLP_350596_VerifyPaginationFor50Students() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "To verify the pagination for 50 students on student list page.")
    public void verifyPaginationFor50Students() throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify the pagination for 50 students on student list page.");

        try {
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            // Login with instructor who has 50 or more than 50 learners
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_50Learner"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify course tile on course view page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to Performance dash board
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Switch to student list tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Open drop down container to select 'All Students'
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'All Students'.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Open drop down container to select 'All Modules'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'All Modules'.");

            // Select on 'Pre-Assessment' from drop down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Select 'Pre-Assessment' from dropdown value.");

            // verify pagination is present.
            objGLPInstructorDashboardPage.verifyPaginationPresent();

            // Verify bottom text - 'Showing 50 students (<N> total)'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PaginationBottomText",
                    ResourceConfigurations.getProperty("paginationBottomText"),
                    "Verify bottom text - 'Showing 50 students (<N> total)'");

            // Verify 'Previous' arrow button is disable by default
            objGLPInstructorDashboardPage.verifyButtonEnabledOrDisabled(
                    "PaginationBackArrowIcon", "no",
                    "Verify 'Previous' arrow button is disable by default.");

            objGLPInstructorDashboardPage.clickOnElement(
                    "PerforManceDashBoardStudentListPaginationPage2",
                    "Click on Page 2 to navigate next page student list");

            objGLPInstructorDashboardPage.verifyElementCSSValue(
                    "PagniationPage2", "border-bottom-style", "solid",
                    "Selected page is present with bottom border.");

            // Verify 'Previous' arrow button is enable after clicking on first
            // page link
            objGLPInstructorDashboardPage.verifyButtonEnabledOrDisabled(
                    "PaginationBackArrowIcon", "yes",
                    "Verify 'Previous' arrow button is enable after clicking on first page link.");

            // Verify 'Next' arrow button is enable after clicking on first page
            // link
            objGLPInstructorDashboardPage.verifyButtonEnabledOrDisabled(
                    "PaginationNextArrowIcon", "yes",
                    "Verify 'Next' arrow button is enable after clicking on first page link.");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
