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
 * @author ratnesh.singh
 * @date April 3, 2018
 * @description: Verify PreAssessment confirmation pop-up hide when clicked on
 *               Pearson Logo.
 *
 * 
 */
public class GLP_316695_VerifyPreAssesmentPopUpHideOnPearsonLogoClick
        extends BaseClass {
    public void GLP_316695_VerifyPreAssesmentPopUpHideOnPearsonLogoClick() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify PreAssessment confirmation pop-up hide when clicked on Pearson Logo.")
    public void VerifyPreAssesmentPopUpHideOnPearsonLogoClick()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify PreAssessment confirmation pop-up hide when clicked on Pearson Logo.");
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
                    "InstructorDashBoardUnlockLater",
                    "Click on 'Unlock Later' button");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

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

            // Click on 'Unlock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedIcon",
                    "Click on 'Unlock Selected' button");

            // Verify 'UnLock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentTest"),
                    "Verify 'UnLock PreAssessment' pop-up is present on UI.");

            // Click on Pearson logo
            objGLPInstructorDashboardPage.clickOnElement("PearsonLogo",
                    "Click on Pearson logo button at top of the page");

            // Verify 'UnLock Pre-Assessment' pop-up is hidden now
            objGLPInstructorCourseViewPage.verifyElementNotPresent(
                    "UnlockPreAssessmentTest",
                    "UnLock Pre-assessment Test confirmation popup should get hide");

            // Click on Course Tile
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on Course card displayed on console Homepage.");

            // Click on "Unlock Later" Pre-Assessment Button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockLater",
                    "Click on 'Unlock Later' Pre-Assessment Button.");

            // Verify 'UnLock Pre-Assessment' pop-up is hidden now
            objGLPInstructorCourseViewPage.verifyElementNotPresent(
                    "UnlockPreAssessmentTest",
                    "UnLock Pre-assessment Test confirmation popup should get hide");

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }

}
