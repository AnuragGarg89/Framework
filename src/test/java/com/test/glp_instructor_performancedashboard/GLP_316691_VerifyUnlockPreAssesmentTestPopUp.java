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
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author pankaj.sarjal
 * @date April 1, 2018
 * @description: Verify 'Unlock PreAssessment Test' pop-up
 *
 */
public class GLP_316691_VerifyUnlockPreAssesmentTestPopUp extends BaseClass {
    public GLP_316691_VerifyUnlockPreAssesmentTestPopUp() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify 'Unlock PreAssessment Test' pop-up.")
    public void verifyUnlockPreAssesmentTestPopUp()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify 'Unlock PreAssessment Test' pop-up.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // // Create Learner subscribing the new course created by the
            // // Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, false);

            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            // Login to the application as a Instructor
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Navigate to 'Welcome Screen Instructor' page
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = objGLPInstructorCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            // Navigate to 'Mastery Level' screen page
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Instructor dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Click on 'Unlock Later' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLaterButton",
                    "Click on 'Unlock Later' button");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify 'Test' column is present on UI
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardTestColumn",
                    "'Test' column is present on UI.");

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Click on Pre-assessment button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPreAssessment",
                    "Click on 'Pre-Assessment option");

            // Click on 'Check box' against student list
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardStudentCheckBox",
                    "Select checkbox against student list");

            // Verify class 'badge-count' is present on UI
            objGLPInstructorDashboardPage.verifyElementAttributeValue(
                    "InstructorDashBoardLockUnlockCount", "class",
                    "badge-count",
                    "Verify class 'badge-count' is present on UI");

            // Click on 'Unlock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedButton",
                    "Click on 'Unlock Selected' button");

            // Verify 'Unlock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentTest"),
                    "Verify 'Unlock PreAssessment' pop-up is present on UI.");

            // Scroll to 'Cancel' button of PreAssessment pop-up
            objGLPInstructorDashboardPage
                    .scrollToElement("UnlockPreAssessmentTest");

            // Verify 'Select All' checkbox is present on 'Unlock
            // Pre-Assessment' pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "UnlockPreAssessmentSelectAllPopUpCheckbox",
                    "Verify 'Select All pop-up' checkbox is present on 'Unlock Pre-Assessment' pop-up.");

            // Verify 'Name' column is present on 'Unlock Pre-Assessment' pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "UnlockPreAssessmentName",
                    "Verify 'Name' column is present on 'Unlock Pre-Assessment' pop-up.");

            // Verify 'Test' column is present on 'Unlock Pre-Assessment' pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "UnlockPreAssessmentTestColumn",
                    "Verify 'Test' column is present on 'Unlock Pre-Assessment' pop-up");

            // Verify 'Last Login' column is present on 'Unlock Pre-Assessment'
            // pop-up
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "UnlockPreAssessmentLastlogin",
                    "Verify 'Last Login' column is present on 'Unlock Pre-Assessment' pop-up");

            // Click on 'Unlock Now' button on 'Unlock Pre-Assessment' pop-up
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Unlock Now' button on 'Unlock Pre-Assessment' pop-up");

            // Verify 'Unlock' button is disable for student
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockDisableButton",
                    "Verify 'Unlock' button is disable for student");

            // Verify all the selected checkbox should get re-set
            objGLPInstructorDashboardPage.verifyElementNotPresent(
                    "InstructorDashBoardLockUnlockCount",
                    "Verify all the selected checkbox should get re-set");

        } finally {
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