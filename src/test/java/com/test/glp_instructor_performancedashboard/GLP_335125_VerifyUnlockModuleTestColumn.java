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
 * @date June 23, 2018
 * @description: Verify different columns of 'Unlock module test' pop up
 */
public class GLP_335125_VerifyUnlockModuleTestColumn extends BaseClass {
    public GLP_335125_VerifyUnlockModuleTestColumn() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, }, enabled = true,
            description = "Verify different columns of 'Unlock module test' pop up")
    public void verifyUnlockModuleTestColumn() {
        startReport(getTestCaseId(),
                "Verify different columns of 'Unlock module test' pop up");

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
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Open drop down container to select 'All Students'
            objGLPInstructorDashboardPage.clickOnElement(
                    "StudentDropdownContainer",
                    "Open dropdown container to select 'All Students'.");

            // Select 'All Students' from drop-down value
            objGLPInstructorDashboardPage.clickOnElement(
                    "AllStudentDropdownValue",
                    "Select 'All Students' from dropdown value.");

            // Open drop down container to select 'Module 16'
            objGLPInstructorDashboardPage.clickOnElement(
                    "ModuleDropdownContainer",
                    "Open dropdown container to select 'Module 16'.");

            // Select 'Module 16' in content drop down
            objGLPInstructorDashboardPage.clickOnElement(
                    "ContentDropdownVaules:dynamicReplace=8",
                    "Select 'Module 16' in content drop down.");

            // Verify 'Unlock Selected' button is enable on student list page
            objGLPInstructorDashboardPage.verifyButtonEnabledOrDisabled(
                    "InstructorDashBoardUnLockSelectedButton", "yes",
                    "Verify 'Unlock Selected' button is enable on student list page");

            // Click on 'unlock selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedButton",
                    "Click on 'Unlock Selected' button.");

            // Verify unlock module test pop up appears on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("unlockModulePopUp"),
                    "Verify 'Unlock Module' test pop-up is present on UI.");

            // Verify 'Name' column is present on 'Unlock module test' pop up
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicUnlockModuleTestPopUpHeader:ColumnNumber=2",
                    ResourceConfigurations
                            .getProperty("unlockModuleTestHeaderName"),
                    "Verify 'Name' column is present on 'Unlock module test' pop up.");

            // Verify 'Achieved readiness' column is present on 'Unlock module
            // test' pop up
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicUnlockModuleTestPopUpHeader:ColumnNumber=3",
                    ResourceConfigurations.getProperty(
                            "unlockModuleTestHeaderAchievedReadines"),
                    "Verify 'Achieved readiness' column is present on 'Unlock module test' pop up.");

            // Verify 'Last Login' column is present on 'Unlock module test' pop
            // up
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DynamicUnlockModuleTestPopUpHeader:ColumnNumber=4",
                    ResourceConfigurations
                            .getProperty("unlockModuleTestHeaderLastLogin"),
                    "Verify 'Last Login' column is present on 'Unlock module test' pop up.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
