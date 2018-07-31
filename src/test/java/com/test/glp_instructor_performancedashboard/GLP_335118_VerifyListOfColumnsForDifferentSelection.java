
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
 * @date June 22, 2018
 * @description: Verify list of column's for selection of different values like
 *               - All Modules / Module <N> This test case also cover test case
 *               ID : 335117
 * 
 */
public class GLP_335118_VerifyListOfColumnsForDifferentSelection
        extends BaseClass {
    public GLP_335118_VerifyListOfColumnsForDifferentSelection() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify list of column's for selection of different values like - All Modules / Module <N>.")
    public void verifyListOfColumnsForDifferentSelection() {
        startReport(getTestCaseId(),
                "Verify list of column's for selection of different values like - All Modules / Module <N>.");

        try {

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnCourseTile();

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Open drop down container to select 'Testing' status on Student
            // drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'Testing' status on Student drop down.");

            // Select 'Testing' status from drop-down value on Student drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "DynamicStudentDropdownValue:DropdownNumber=4",
                    "Select 'Testing' status from dropdown value on Student drop down.");

            // Open drop down container to select 'All Modules'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'All Modules'.");

            // Select 'All Modules' in content drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "ContentDropdownVaules:dynamicReplace=1",
                    "Select 'All Modules' in content drop down.");

            // Verify column 'Status' is present on UI when drop down 'Student =
            // Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=4",
                    ResourceConfigurations
                            .getProperty("studentTableColumnStatus"),
                    "Verify column 'Status' is present on UI when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify status on status column when when Student drop down is
            // 'Testing' and Content is 'All Modules'
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify status on status column when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify column 'Current Content' is present on UI when drop down
            // 'Student = Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=5",
                    ResourceConfigurations
                            .getProperty("studentTableColumnCurrentContent"),
                    "Verify column 'Current Content' is present on UI when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify 'Module 16' is displaying in 'Current Content' column when
            // drop down 'Student = Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations
                            .getProperty("currentContentModule16"),
                    "Verify 'Module 16' is displaying in 'Current Content' column when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify column 'Overall Completion' is present on UI when drop
            // down 'Student = Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=6",
                    ResourceConfigurations
                            .getProperty("studentTableColumnOverallCompletion"),
                    "Verify column 'Overall Completion' is present on UI when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify 'Overall Completion' field contains valid value
            objGLPInstructorDashboardPage.verifyValidOverallCompletionValue();

            // Verify column 'Total Time on Task' is present on UI when drop
            // down 'Student = Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=7",
                    ResourceConfigurations
                            .getProperty("studentTableColumnTotalTOT"),
                    "Verify column 'Total Time on Task' is present on UI when drop down 'Student = Testing' and 'Current Content = All Modules'.");

            // Verify valid time on 'Time on Task' column is displaying when
            // drop down 'Student = Testing' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage
                    .isValidTimeOnTask("InstructorDashboardTimeOnTask");

            // Open drop down container to select 'Ready' status on Student
            // drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'Ready' status on Student drop down.");

            // Select 'Ready' status from drop-down value on Student drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "DynamicStudentDropdownValue:DropdownNumber=2",
                    "Select 'Ready' status from dropdown value on Student drop down.");

            // Verify column 'Status' is present on UI when drop down 'Student =
            // Ready' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=4",
                    ResourceConfigurations
                            .getProperty("studentTableColumnStatus"),
                    "Verify column 'Status' is present on UI when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify status on status column when when when drop down 'Student
            // = Ready' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify status on status column when when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify column 'Current Content' is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=5",
                    ResourceConfigurations
                            .getProperty("studentTableColumnCurrentContent"),
                    "Verify column 'Current Content' is present on UI when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify 'Module 16' is displaying in 'Current Content' column when
            // drop down 'Student = Ready' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations
                            .getProperty("currentContentModule16"),
                    "Verify 'Module 16' is displaying in 'Current Content' column when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify column 'Overall Completion' is present on UI when drop
            // down 'Student = Ready' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=6",
                    ResourceConfigurations
                            .getProperty("studentTableColumnOverallCompletion"),
                    "Verify column 'Overall Completion' is present on UI when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify 'Overall Completion' field contains valid value
            objGLPInstructorDashboardPage.verifyValidOverallCompletionValue();

            // Verify column 'Total Time on Task' is present on UI when drop
            // down 'Student = Ready' and 'Current Content = All Modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=7",
                    ResourceConfigurations
                            .getProperty("studentTableColumnTotalTOT"),
                    "Verify column 'Total Time on Task' is present on UI when drop down 'Student = Ready' and 'Current Content = All Modules'.");

            // Verify valid time on 'Time on Task' column is displaying
            objGLPInstructorDashboardPage
                    .isValidTimeOnTask("InstructorDashboardTimeOnTask");

            // Here starts second test case's steps
            // Open drop down container to select 'Module 16'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Module 16'.");

            // Select 'Module 16' in content drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "ContentDropdownVaules:dynamicReplace=8",
                    "Select 'Module 16' in content drop down.");

            // Verify column 'Status' is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=4",
                    ResourceConfigurations
                            .getProperty("studentTableColumnStatus"),
                    "Verify column 'Status' is present on UI when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify 'Ready' status on status column when drop down 'Student =
            // Ready' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify 'Ready' status on status column when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify column 'Current Content' is present on UI when drop down
            // 'Student = Ready' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=5",
                    ResourceConfigurations
                            .getProperty("studentTableColumnCurrentcontent"),
                    "Verify column 'Current content' is present on UI when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify 'Module 16' is displaying in 'Current Content' column when
            // drop down 'Student = Ready' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations
                            .getProperty("currentContentModule16"),
                    "Verify 'Module 16' is displaying in 'Current Content' column when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify 'Mastery' column is present on UI when drop down 'Student
            // = Ready' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=6",
                    ResourceConfigurations
                            .getProperty("studentTableColumnMastery"),
                    "Verify column 'Mastery' is present on UI when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify 'Mastery' field contains valid value
            objGLPInstructorDashboardPage.verifyValidOverallCompletionValue();

            // Verify column 'Total Time on Task' is present on UI when drop
            // down 'Student = Ready' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=7",
                    ResourceConfigurations
                            .getProperty("studentTableColumnTotalTOT"),
                    "Verify column 'Total Time on Task' is present on UI when drop down 'Student = Ready' and 'Current Content = Module 16'.");

            // Verify value in column 'Total time on task' is dash (--)
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "PerforManceDashBoardStudentListTotDash",
                    "Verify value in column 'Total time on task' is dash (--).");

            // Open drop down container to select 'Testing' status on Student
            // drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'Testing' status on Student drop down.");

            // Select 'Testing' status from drop-down value on Student drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "DynamicStudentDropdownValue:DropdownNumber=4",
                    "Select 'Testing' status from dropdown value on Student drop down.");

            // Verify column 'Status' is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=4",
                    ResourceConfigurations
                            .getProperty("studentTableColumnStatus"),
                    "Verify column 'Status' is present on UI when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify 'Testing' status on status column when drop down 'Student
            // =
            // Testing' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify 'Testing' status on status column when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify column 'Current Content' is present on UI when drop down
            // 'Student = Testing' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=5",
                    ResourceConfigurations
                            .getProperty("studentTableColumnCurrentcontent"),
                    "Verify column 'Current Content' is present on UI when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify 'Module 16' is displaying in 'Current Content' column when
            // drop down 'Student = Testing' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations
                            .getProperty("currentContentModule16"),
                    "Verify 'Module 16' is displaying in 'Current Content' column when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify 'Mastery' column is present on UI when drop down 'Student
            // = Testing' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=6",
                    ResourceConfigurations
                            .getProperty("studentTableColumnMastery"),
                    "Verify column 'Mastery' is present on UI when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify 'Mastery' field contains valid value
            objGLPInstructorDashboardPage.verifyValidOverallCompletionValue();

            // Verify column 'Total Time on Task' is present on UI when drop
            // down 'Student = Testing' and 'Current Content = Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicStudentTableHeader:ColumnNumber=7",
                    ResourceConfigurations
                            .getProperty("studentTableColumnTotalTOT"),
                    "Verify column 'Total Time on Task' is present on UI when drop down 'Student = Testing' and 'Current Content = Module 16'.");

            // Verify value in column 'Total time on task'
            objGLPInstructorDashboardPage
                    .isValidTimeOnTask("InstructorDashboardTimeOnTask");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
