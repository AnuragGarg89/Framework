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
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date May 31, 2018
 * @descriptionVerify the Student overlay screen for Pre-assessment card bar
 *                    chart.
 * 
 */
public class GLP_347241_VerifyStudentOverlayPreAssessmentCard
        extends BaseClass {
    public GLP_347241_VerifyStudentOverlayPreAssessmentCard() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify the Student overlay screen for Pre-assessment card bar chart")
    public void verifyStudentNameAndEmailIDIsDisplayed() {
        startReport(getTestCaseId(),
                "Verify the Student overlay screen for Pre-assessment card bar chart");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // Generate unique instructor userName
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Welcome page for Instructor.
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);

            GLPInstructor_MasterySettingPage objProductApplicationMasterSettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Pre Assessment mastery level page.
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Navigate to the Management dashboard page
            objProductApplicationMasterSettingPage
                    .navigateToInstructorDashboard();

            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);

            // Click on performance dashboard
            objProductApplicationManagementDashboardPage.clickOnElement(
                    "InstructorManagementPerformanceTab",
                    "Click on performance tab");

            // Click on the Student List Tab
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Click on the Student List tab on the performance dashboard.");

            // Click on student name
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentTabFirstStudentName",
                    "Click on the name of the student");

            // Verify drop down is disabled in pre assessmnet card
            objProductApplicationInstructorDashboardPage
                    .verifyButtonEnabledOrDisabled("PreAssessmentCardDropDown",
                            "no",
                            "Verify drop down is disabled in pre assessmnet card");

            // Click on cross icon
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardCloseIcon",
                    "Click on cross icon on student detail screen");

            objProductApplicationInstructorDashboardPage.verifyLogout();

            objProductApplicationLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Skip all Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            objProductApplicationInstructorDashboardPage.verifyLogout();

            objProductApplicationLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Click on the Student List Tab
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Click on the Student List tab on the performance dashboard.");

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

            // Verify drop down is enabled in pre assessmnet card
            objProductApplicationInstructorDashboardPage
                    .verifyButtonEnabledOrDisabled("PreAssessmentCardDropDown",
                            "yes",
                            "Verify drop down is enabled in pre assessmnet card when learner conplete the diagnostic test");

            // Click on drop down in pre assessment card
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "PreAssessmentCardDropDown",
                    "Click on pre assessment card drop down");

            // Verify Student heading displayed on the bar graph
            objProductApplicationInstructorDashboardPage.verifyText(
                    "StudentHeadingBarGraph",
                    ResourceConfigurations
                            .getProperty("barGraphStudentHeadingText"),
                    "Verify 'Student' heading text displayed on the bar graph");

            // Verify Class Avg heading displayed on the bar graph
            objProductApplicationInstructorDashboardPage.verifyText(
                    "ClassAvgHeadingBarGraph",
                    ResourceConfigurations.getProperty("barGraphClassAvgText"),
                    "Verify 'class avg' heading text displayed on the bar graph");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
