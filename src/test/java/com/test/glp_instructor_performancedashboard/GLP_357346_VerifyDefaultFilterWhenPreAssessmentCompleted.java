
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
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date June 07, 2018
 * @description: Verify change in default filter to 'All Students' in 'All
 *               Modules' once Pre-Assessment is completed .This test case also
 *               cover another test case - 357347
 */
public class GLP_357346_VerifyDefaultFilterWhenPreAssessmentCompleted
        extends BaseClass {
    public GLP_357346_VerifyDefaultFilterWhenPreAssessmentCompleted() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify change in default filter to 'All Students' in 'All Modules' once Pre-Assessment is completed")
    public void verifyDefaultFilterWhenPreAssessmentCompleted() {
        startReport(getTestCaseId(),
                "Verify change in default filter to 'All Students' in 'All Modules' once Pre-Assessment is completed");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);

        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        String learnerUserName2 = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create new instructor
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(instructorName,
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

            // Navigate to the Instructor dash board page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

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

            // logout instructor
            objGLPInstructorDashboardPage.verifyLogout();

            // login with first learner
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Complete Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login with instructor again
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify default filter for Students is 'All Students'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultStudentsDropdownValue",
                    ResourceConfigurations
                            .getProperty("studentDropdownAllStudents"),
                    "Verify default filter for Students is 'All Students'");

            // Verify default filter for Content is 'All modules'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultContentDropdownValue",
                    ResourceConfigurations
                            .getProperty("contentDropdownAllModules"),
                    "Verify default filter for Content is 'All modules'");

            // Create one more user and subscribe course using corresponding
            // APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName2,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            objGLPInstructorDashboardPage.refreshPage();

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Verify default filter for Students is 'All Students'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultStudentsDropdownValue",
                    ResourceConfigurations
                            .getProperty("studentDropdownAllStudents"),
                    "Verify default filter for 'Students' changes to 'All Students' as soon as new learner joins the course while other have already completed 'Pre-Assessment.");

            // Verify default filter for Content is 'Pre-assessment'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "DefaultContentDropdownValue",
                    ResourceConfigurations
                            .getProperty("contentDropdownPreassessment"),
                    "Verify default filter for 'Content' changes to 'Pre-assessment' as soon as new learner joins the course while other have already completed 'Pre-Assessment.");

        } finally {

            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
            }
            webDriver.quit();
            webDriver = null;
        }
    }
}
