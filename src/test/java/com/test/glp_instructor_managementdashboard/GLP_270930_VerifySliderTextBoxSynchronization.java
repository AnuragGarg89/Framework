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
package com.test.glp_instructor_managementdashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : Verify that horizontal slider and input box value remain in
 *              synchronization on editing the threshold value.
 */
public class GLP_270930_VerifySliderTextBoxSynchronization extends BaseClass {
    public GLP_270930_VerifySliderTextBoxSynchronization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify that horizontal slider and input box value remain in synchronization on editing the threshold value.")
    public void verifySliderTextBoxSynchronization() {
        startReport(getTestCaseId(),
                "Verify that horizontal slider and input box value remain in synchronization on editing the threshold value.");
        GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);
        try {
            // Login with already existing user
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_ManagementDashboardPage objProductManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();
            objProductManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify edit button is appearing.");
            objProductManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton",
                    "Click on the Edit button");
            objProductManagementDashboardPage
                    .verifySliderInputBoxSyncronization();
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
