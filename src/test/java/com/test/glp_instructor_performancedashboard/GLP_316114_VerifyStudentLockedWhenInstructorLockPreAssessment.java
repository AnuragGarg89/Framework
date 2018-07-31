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
import com.autofusion.CouchBaseDB;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
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
 * @description: Verify student get locked when instructor lock assessment
 *
 */
public class GLP_316114_VerifyStudentLockedWhenInstructorLockPreAssessment
        extends BaseClass {
    public GLP_316114_VerifyStudentLockedWhenInstructorLockPreAssessment() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify student get locked when instructor lock assessment.")
    public void verifyStudentLockedWhenInstructorLockPreAssessment()
            throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify student get locked when instructor lock assessment.");
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
                    instructorName,

                    false);

            String courseID = objRestUtil.getGlpCourseId(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

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

            // Click on 'Unlock' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockStatusButton",
                    "Click on 'Unlock' button");

            // Click on 'Lock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardLockSelectedButton",
                    "Click on 'Lock Selected' button");

            // Verify 'Lock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("LockPreAssessmentTest"),
                    "Verify 'Lock PreAssessment' pop-up is present on UI.");

            // Scroll to view 'PreAssessment' confirmation pop-up
            objGLPInstructorDashboardPage
                    .scrollToElement("UnlockPreAssessmentTest");

            // Verify no in 'Lock <no> now' button is 1
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashBoardUnlockNowButton",
                    ResourceConfigurations
                            .getProperty("LockPreAssessmentUnlock1now"),
                    "Verify number in 'Lock <no> now' button is 1.");

            // De-select 'Select All' checkbox
            objGLPInstructorDashboardPage.clickOnElement(
                    "UnlockPreAssessmentSelectAllPopUpCheckbox",
                    "De-select 'Select All' checkbox.");

            // Click on 'Lock 0 now' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Lock 0 now' button.");

            // Verify 'Lock Pre-Assessment' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockPreAssessmentTest",
                    ResourceConfigurations.getProperty("LockPreAssessmentTest"),
                    "Verify 'Lock PreAssessment' pop-up is present on UI.");

            // Select 'Select All' checkbox
            objGLPInstructorDashboardPage.clickOnElement(
                    "UnlockPreAssessmentSelectAllPopUpCheckbox",
                    "Select 'Select All' checkbox.");

            // Click on 'Lock 1 now' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnlockNowButton",
                    "Click on 'Lock 1 now' button.");

            // Verify 'Lock' button is disable
            if (objGLPInstructorDashboardPage
                    .isButtonDisabled("InstructorDashBoardlockStatusButton")) {
                logResultInReport(
                        Constants.PASS + ": The 'Lock' button is Disabled",
                        "Verify that the 'Lock' button is Disabled",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": The 'Lock' button is Not Disabled",
                        "Verify that the 'Lock' button is Disabled",
                        reportTestObj);
            }

            // Verify all the selected checkbox should get re-set
            objGLPInstructorDashboardPage.verifyElementNotPresent(
                    "InstructorDashBoardLockUnlockCount",
                    "Verify all the selected checkbox should get re-set");

            // Verify that selected student get locked in couchbase db
            String subscriptionID = objRestUtil.getSubscriptionId(
                    "GLP_Learner_311938_xJKt",
                    ResourceConfigurations.getProperty("consolePassword"));

            String queryString = "select preAssessmentStatus from led where id='"
                    + subscriptionID + "';";

            CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);

            cb.verifyCouchbaseQueryData(queryString, "results",
                    "preAssessmentStatus", "locked", 0);

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