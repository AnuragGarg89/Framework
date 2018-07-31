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
package com.test.glp_endtoend;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author tarun.gupta1
 * @date Oct 27, 2017
 * @description : Verify Intstructor is able to login and can view the
 *              performance dashboard
 */
public class GLP_283509_VerifyInstructorFlow extends BaseClass {
    public GLP_283509_VerifyInstructorFlow() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify newly created Intstructor is able to login, can view the performance dashboard and can edit mastery level.")

    public void verifyMasteringScoreSetting() {
        startReport(getTestCaseId(),
                "Verify newly created Intstructor is able to login, can view the performance dashboard and can edit mastery level.");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // Generate unique instructor userName
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // objProductApplicationCourseViewPage.waitForCourseToAppearInstructor();
            // Navigate to the Mastery Settings page
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            objGLPInstructorCourseViewPage.navigateToWelcomeScreenInstructor();
            objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            // Set the mastery level to 90%
            objGLPInstructorMasterySettingPage.clickOnMasterySlider(
                    "PreAssessmentMasteryPageSlider",
                    "Click on the slider that is displayed on the Pre Assessment Mastery Setting page");
            // String sliderValueOnMastery =
            // objProductApplicationMasterySettingPage
            // .getText("PreAssessmentMasterySliderValue");
            String sliderValueOnMastery = objGLPInstructorMasterySettingPage
                    .getElementAttribute("PreAssessmentMasterySliderValue",
                            "value", "Retrieve slider value on mastery screen ")
                    + ResourceConfigurations.getProperty("percentageSymbol");
            // Navigate to the Performance dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            // Switch to the Management tab and verify the Mastering Score is
            // equal to 90%
            // objProductApplicationInstructorDashboardPage
            // .switchToManagementTab();
            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            String masteryLevelOnInstructorDashBoard = objGLPInstructorManagementDashboardPage
                    .getText("InstructorManagementMasteryPercentage");
            objGLPInstructorManagementDashboardPage.verifyMasteringScore(
                    sliderValueOnMastery, masteryLevelOnInstructorDashBoard);
            objGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify instructor is navigated to management dashboard.");
            // Click on edit button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementEditButton", "Click on edit button.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMinValue"),
                    "Enter invalid value less than 80% in textbox");
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify Error message for invalid value less than 80.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMaxValue"),
                    "Enter invalid value more than 100% in textbox");
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify Error message for invalid value more than 100.");
            objGLPInstructorManagementDashboardPage.enterInputData(
                    "InstructorManagementTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue"),
                    "Enter valid value between 80 to 100% in textbox");
            // Click on save button
            objGLPInstructorManagementDashboardPage.clickOnSaveButton();
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
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to the performance dashboard
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();
            objGLPInstructorManagementDashboardPage.verifyText(
                    "InstructorManagementMasteryPercentage",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxValidValue") + "%",
                    "Verify pre-assessment mastery threshold value which was updated is reflected back after login again.");
        }

        // Delete User via API
        finally {

            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }
}
