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
 * @date Mar 12, 2018
 * @description :To verify that Mastery column should be displayed on selecting
 *              All Students and Module number in dropdown.
 */
public class GLP_322953_VerifyMasteryColumnShouldBeDisplayedSelectingAllStudentsAndModuleNumberDropdown
        extends BaseClass {
    public GLP_322953_VerifyMasteryColumnShouldBeDisplayedSelectingAllStudentsAndModuleNumberDropdown() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "To verify that Mastery column should be displayed on selecting All Students and Module number in dropdown.")
    public void
           verifyMasteryColumnShouldBeDisplayedSelectingAllStudentsAndModuleNumberDropdown()
                   throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify that Mastery column should be displayed on selecting All Students and Module number in dropdown.");

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
                    "Module 11",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Verify Student List is clicked.");

            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    "All Students",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            objProductApplicationInstructorDashboardPage
                    .verifyAllElementsValueInList(
                            "PerforManceDashBoardStudentTableHeaders",
                            ResourceConfigurations.getProperty(
                                    "perforManceDashBoardStudentTableHeadersWithModulesNo"));

        } finally {
            // finally delete the created user
            // objRestUtil.deleteUserViaApi(learnerUserName1);
            webDriver.quit();
            webDriver = null;
        }
    }
}
