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

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
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
 * @author pallavi.tyagi
 * @date Mar 13, 2018
 * @description : To verify reset pop up UI and functionality
 */

public class GLP_321233_VerifyResetPopUpFunctionality extends BaseClass {

    public GLP_321233_VerifyResetPopUpFunctionality() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "To verify reset pop up UI and functionality ")

    public void VerifyResetPopUpUI() {

        startReport(getTestCaseId(),
                "To verify reset pop up UI and functionality ");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // Generate unique instructor userName
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instUserName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            objRestUtil.createInstructorWithNewCourse(instUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instUserName, true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(instUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Welcome screen
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            objGLPInstructorWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify that instructor is navigated to Welcome screen.");

            // Navigate to the Mastery Settings page
            objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Management dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();

            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify that Edit button is present on the mangement page.");
            // Navigate to Performance dashboard
            objGLPInstructorManagementDashboardPage.switchToPerformaceTab();

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Click on the Student List Tab
            objProductApplicationInstructorDashboardPage
                    .switchToStudentListTab();

            // Verify that the student is displayed on the Performance Dashboard
            // page.
            objProductApplicationInstructorDashboardPage
                    .verifyStudentListIsDisplayed();

            // Search for the student on the student list page.
            APP_LOG.info("Searching the learner on the student list page.");
            objProductApplicationInstructorDashboardPage.enterInputData(
                    "SearchStudentFilter", learnerUserName,
                    "Enter the username in the search student input box and search for the learner.");

            Actions act = new Actions(returnDriver());
            act.sendKeys(Keys.ENTER).perform();

            // Click on the student name appeared in the search result
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardListOfStudents",
                    "Click on the student name appeared in the search result.");

            // Click on 'Reset' button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementResetcoursedataText",
                    "Click on reset button on student detail page");

            // Verify the UI of reset pop up
            objProductApplicationInstructorDashboardPage.verifyText(
                    "PerforManceDashBoardLockPopUpMessage",
                    ResourceConfigurations.getProperty("resetPopUpHeaderText"),
                    "Verify reset pop up header text displayed in "
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "ResetPopupBody",
                    ResourceConfigurations.getProperty("resetPopUpBodyText"),
                    "Verify reset pop up body text displayed in "
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "UnlockPreAssessmentCancel",
                    ResourceConfigurations
                            .getProperty("resetCourseDataCancelButtonText"),
                    "Verify reset pop up cancel button text displayed in "
                            + ResourceConfigurations.getProperty("language"));

            objProductApplicationInstructorDashboardPage.verifyText(
                    "InstructorDashBoardUnlockNowButton",
                    ResourceConfigurations
                            .getProperty("resetCourseDataResetButtonText"),
                    "Verify reset pop up reset now button text displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on cancel button on reset pop up
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "UnlockPreAssessmentCancel",
                    "Click on cancel button on reset pop up window");

            // Verify on clicking the cancel button pop up window get dismissed
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent("InstructorDashBoardUnlockPopup",
                            "Verify on clicking the cancel button pop up window get dismissed");

            // Click on 'Reset' button
            objGLPInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementResetcoursedataText",
                    "Click on reset button on student detail page");

            // Click on reset now button
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on reset now button");

            // Click on cross button
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardCloseIcon",
                    "Click on cross icon on student detail page");
            // Verify on clicking the reset now resets the learner status to not
            // started
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "StudentStatus",
                    "Verify on reset now, resets the learner state state to not started");
        }

        // Delete User via API
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
