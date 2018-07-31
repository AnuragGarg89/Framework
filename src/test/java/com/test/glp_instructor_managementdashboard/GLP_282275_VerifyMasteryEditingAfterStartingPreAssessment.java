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
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

public class GLP_282275_VerifyMasteryEditingAfterStartingPreAssessment
        extends BaseClass {

    public GLP_282275_VerifyMasteryEditingAfterStartingPreAssessment() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify that the Edit mastery button onmanagement page is disabled if one of the learner starts Diagnostic Test")

    public void editDisabledAfterStartingPreAssessment() {

        startReport(getTestCaseId(),
                "Verify that the Edit mastery button onmanagement page is disabled if one of the learner starts Diagnostic Test");

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
                    instUserName,
                    // configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),

                    true);

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
            objGLPInstructorMasterySettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify that instructor is navigated to Pre-Assessment Mastery setting screen.");

            // Navigate to the Management dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();

            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify that Edit button is present on the mangement page.");

            // Verify that the Edit button is enabled
            objGLPInstructorManagementDashboardPage
                    .verifyButtonEnabledOrDisabled(
                            "InstructorManagementEditButton", "yes",
                            "Verify that the Edit button is enabled on the Management Dashboard page.");

            // logout of the application
            objProductApplicationCourseViewPage.verifyLogout();

            // Login to the application as Learner
            GLPLearner_CourseViewPage objGLPearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Verify Welcome Screen Page title 'Course Home' is present.
            objGLPLearnerCourseHomePage.verifyWelcomeScreenPageTitle(
                    ResourceConfigurations
                            .getProperty("welcomeScreenPageTitle"),
                    "Verify Welcome Screen Page title 'Course Home' is present.");

            // Click on 'Start Pre-assessment' button
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            // logout of the application
            objGLPearnerCourseViewPage.verifyLogout();
            // Login to the application with instructor
            objProductApplicationConsoleLoginPage.login(instUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Navigate to the Performance Dashboard page.
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            GLPInstructor_InstructorDashboardPage objGLPInstructorInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorInstructorDashboardPage.switchToManagementTab();

            // Verify that the Edit button is disabled
            objGLPInstructorManagementDashboardPage
                    .verifyButtonEnabledOrDisabled(
                            "InstructorManagementEditButton", "no",
                            "Verify that the Edit button is disabled on the Management Dashboard page.");

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