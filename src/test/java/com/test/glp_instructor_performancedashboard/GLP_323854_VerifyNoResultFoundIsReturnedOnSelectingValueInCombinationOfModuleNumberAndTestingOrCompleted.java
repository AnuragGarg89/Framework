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
 * @date April 9, 2018
 * @description :To verify that No result found should return if we set dropdown
 *              value in combination of 'Module number' and ('Testing' or
 *              'Completed')
 * 
 * 
 */
public class GLP_323854_VerifyNoResultFoundIsReturnedOnSelectingValueInCombinationOfModuleNumberAndTestingOrCompleted
        extends BaseClass {
    public GLP_323854_VerifyNoResultFoundIsReturnedOnSelectingValueInCombinationOfModuleNumberAndTestingOrCompleted() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "To verify that No result found should return if we set dropdown value in combination of 'Module number' and ('Testing' or 'Completed')")
    public void
           verifyNoResultFoundIsReturnedOnSelectingValueInCombinationOfModuleNumberAndTestingOrCompleted()
                   throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify that No result found should return if we set dropdown value in combination of 'Module number' and ('Testing' or 'Completed')");

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
                    "Testing",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            // Select Pre-Assessment and Status as Not Started in filter
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceNoRecordFound",
                    "No record is found.");

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Verify Student List is clicked.");

            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    "Completed",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            // Select Pre-Assessment and Status as Not Started in filter
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceNoRecordFound",
                    "No record is found.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
