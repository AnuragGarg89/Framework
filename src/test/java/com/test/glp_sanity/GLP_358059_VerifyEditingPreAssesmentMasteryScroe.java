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
package com.test.glp_sanity;

import java.io.IOException;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.util.GLP_Utilities;

import de.sstoehr.harreader.HarReaderException;

/**
 * @author Abhishek Sharda
 * @date Feb 12, 2018
 * @description: Test to Verify that new course creation and subscription
 *               working for newly created user
 */
public class GLP_358059_VerifyEditingPreAssesmentMasteryScroe
        extends BaseClass {
    private String learnerUserName = null;

    public GLP_358059_VerifyEditingPreAssesmentMasteryScroe() {
    }

    @Test(groups = { Groups.SANITY, Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.HEARTBEAT }, enabled = true,
            description = "Verify new course subscription and navigation to GLP wprking for newly created user")
    public void verifyCompleteDiagnosticTestAttempted()
            throws IOException, HarReaderException {
        startReport(getTestCaseId(),
                "Verify new course subscription and navigation to GLP wprking for newly created user");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        try {
            learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                    + objCommonUtil.generateRandomStringOfAlphabets(4);

            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            // Login to the application as an Instructor
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            // Navigate to the performance dashboard
            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            objGLPInstructorInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardExportButton",
                    "Verify instructor has navigated to Performance Dashboard page.");

            // Switch to the Management tab
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();
            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            // Verify instructor is navigated to management dashboard.
            objGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor is navigated to management dashboard.");
            // Click on edit button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid value between 80 to 100% in textbox");
            // Click on save button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementSaveButton", "Click on save button.");
            // Verify pre-assessment mastery threshold value is updated by
            // clicking
            // on save button.
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementMasteryPercentage",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue") + "%",
                    "Verify pre-assessment mastery threshold value is updated by clicking on save button.");
            // Add logout and login
            objGLPInstructorCourseViewPage.verifyLogout();
        }

        finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }

}
