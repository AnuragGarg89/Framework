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
 * @description :To verify student filter should be sorted as per option
 *              selected in dropdown
 */
public class GLP_313821_VerifyStudentFilterSortedAsPerSelectionInDropdown
        extends BaseClass {
    public GLP_313821_VerifyStudentFilterSortedAsPerSelectionInDropdown() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "To verify student filter should be sorted as per option selected in dropdown")
    public void verifyStudentFilterSortedAsPerSelectionInDropdown()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify student filter should be sorted as per option selected in dropdown");

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
                    "Verify All Module View is clicked.");

            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("preAssessmentPreviewHeading"),
                    "InstructorDashBoardPerformanceModuleDropDownValues");
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Click on 'All students' drop down table");
            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("testingValueInStudentDropDown"),
                    "InstructorDashBoardPerformanceStudentDropDownValues");
            // Select Pre-Assessment and Status as Not Started in filter
            objProductApplicationInstructorDashboardPage
                    .verifyWebtableValuesOnFilterSelection(
                            "InstructorDashBoardPerformanceNoRecordFound",
                            "InstructorDashBoardPerformanceStatusTesting");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusNotStarted",
                            "Not Started student is not present as per filter applied.");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusCompleted",
                            "Completed student is not present as per filter applied.");
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Click on 'All students' drop down table");
            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("notStartedValueInStudentDropDown"),
                    "InstructorDashBoardPerformanceStudentDropDownValues");
            // Select Pre-Assessment and Status as Not Started in filter
            objProductApplicationInstructorDashboardPage
                    .verifyWebtableValuesOnFilterSelection(
                            "InstructorDashBoardPerformanceNoRecordFound",
                            "InstructorDashBoardPerformanceStatusNotStarted");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusTesting",
                            "Testing student is not present as per filter applied.");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusCompleted",
                            "Completed student is not present as per filter applied.");

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Click on 'All students' drop down table");
            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("completedValueInStudentDropDown"),
                    "InstructorDashBoardPerformanceStudentDropDownValues");
            // Select Pre-Assessment and Status as Completed in filter
            objProductApplicationInstructorDashboardPage
                    .verifyWebtableValuesOnFilterSelection(
                            "InstructorDashBoardPerformanceNoRecordFound",
                            "InstructorDashBoardPerformanceStatusCompleted");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusTesting",
                            "Testing student is not present as per filter applied.");
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent(
                            "InstructorDashBoardPerformanceStatusNotStarted",
                            "Not Started student is not present as per filter applied.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}
