
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
 * @date June 03, 2018
 * @description: Verify default filter and various drop down values on student
 *               list page when 'Pre-Assessment' or 'All Modules' is selected.
 *               This test case also cover test case ID - 335322
 */
public class GLP_335324_VerifyDefaultFilterAndDropDownValues extends BaseClass {
    public GLP_335324_VerifyDefaultFilterAndDropDownValues() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify default filter and various dropdown values on student list page when 'Pre-Assessment' or 'All Modules' is selected.")
    public void verifyDefaultFilterAndDropDownValues() {
        startReport(getTestCaseId(),
                "Verify default filter and various dropdown values on student list page when 'Pre-Assessment' or 'All Modules' is selected.");

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

            // Verify default filter for Students is 'All Students'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultStudentsDropdownValue",
                    ResourceConfigurations
                            .getProperty("studentDropdownAllStudents"),
                    "Verify default filter for Students is 'All Students'");

            // Verify default filter for Content is 'Pre-assessment'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultContentDropdownValue",
                    ResourceConfigurations
                            .getProperty("contentDropdownPreassessment"),
                    "Verify default filter for Content is 'Pre-assessment'");

            // Open drop down container to select 'All Students'
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'All Students'.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Open drop down container to select 'Pre-Assessment'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Pre-Assessment'.");

            // Select 'Pre-Assessment' in content drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "ContentDropdownVaules:dynamicReplace=2",
                    "Select 'Pre-Assessment' in content drop down.");

            // Verify status on status column when 'Pre-Assessment' is selected
            // in content drop down
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify status on status column when 'Pre-Assessment' is selected in content dropdown.");

            // Open drop down container to select 'All Modules'
            objGLPInstructorDashboardPage.clickByJS("ModuleDropdownContainer",
                    "Open dropdown container to select 'All Modules'.");

            // Select 'All Modules' in content drop down
            objGLPInstructorDashboardPage.clickByJS(
                    "ContentDropdownVaules:dynamicReplace=1",
                    "Select 'All Modules' in content drop down.");

            // Verify status on status column when 'All Modules' is selected
            // in content drop down
            objGLPInstructorDashboardPage.verifyStatusIsPresent(
                    "Verify status on status column when 'All Modules' is selected in content dropdown.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
