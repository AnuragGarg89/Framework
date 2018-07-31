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
import com.glp.page.GLPInstructor_StudentPerformanceDetailsPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author anuj.tiwari
 * @date May 31, 2018
 * @description : Verify Time on Task details for any student on the Performance
 *              Dashboard.
 * 
 */
public class GLP_349329_VerifyHyphenInTimeOnTaskForStudent extends BaseClass {
    public GLP_349329_VerifyHyphenInTimeOnTaskForStudent() {
    }

    @Test(groups = { Groups.REGRESSION }, enabled = true,
            description = "Verify hyphen in Time on Task for module, LO and pre Assessment for any student on the Performance Dashboard.")

    public void verifyTimeOnTask() {
        startReport(getTestCaseId(),
                "Verify hyphen in Time on Task for module, LO and pre Assessment for any student on the Performance Dashboard.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {

            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_StudentPerformanceDetailsPage objInstructorStudentPerformanceDetailsPage = new GLPInstructor_StudentPerformanceDetailsPage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), false);

            // Login to the application as an Instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            // Verify course tile on course view page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

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

            // Verify that the instructor is navigated to Student Details page.
            objInstructorStudentPerformanceDetailsPage.verifyText(
                    "CloseStudentDetailsHeading",
                    ResourceConfigurations
                            .getProperty("closeStudentDetailsHeading"),
                    "Verify that the learner landed on the Student details page by verifying hte heading 'Close Student Details.'");

            // Verify that the list of all modules is displayed.
            objInstructorStudentPerformanceDetailsPage
                    .verifyListOfModuleIsDisplayed(
                            "StudentDetailsListOfModules");

            // Verify TOT for preAssessment
            APP_LOG.info(
                    "Verifying that the hyphen should be displayed for the student since the pre asessment is locked.");
            objInstructorStudentPerformanceDetailsPage
                    .verifyTOT("preAssessment", "-");

            // Verify TOT for one Module
            APP_LOG.info(
                    "Verifying that the hyphen is displayed for Module 11 TOT");
            objInstructorStudentPerformanceDetailsPage.verifyTOT("module", "-",
                    "11");

            // Verify TOT for one Module
            APP_LOG.info(
                    "Verifying that the hyphen is displayed for Module 12 TOT");
            objInstructorStudentPerformanceDetailsPage.verifyTOT("module", "-",
                    "12");

            // Verify TOT for one Module
            APP_LOG.info(
                    "Verifying that the hyphen is displayed for Module 12 TOT");
            objInstructorStudentPerformanceDetailsPage.verifyTOT("module", "-",
                    "13");

            // Verify TOT for any LO
            APP_LOG.info(
                    "Verifying that the hyphen is displayed for Module 11 LO 11.1 TOT");
            objInstructorStudentPerformanceDetailsPage.verifyTOT("LO", "-",
                    "11.1");

            // Verify TOT for any LO
            APP_LOG.info(
                    "Verifying that the hyphen is displayed for Module 11 LO 12.2 TOT");
            objInstructorStudentPerformanceDetailsPage.verifyTOT("LO", "-",
                    "12.2");

            // Click on the cross icon on the student details page to close the
            // details and navigate back to Student list page.
            objInstructorStudentPerformanceDetailsPage.clickOnElement(
                    "CloseStudentDetailsCrossButton",
                    "Click on the Cross button on Student Details page and navigate back to the student list page.");

            // Unlock the diagnostic test for learner
            objRestUtil.lockUnlockDiagnosticForLearner(learnerUserName,
                    "unlocked");

            // Navigate to course home by clicking the pearson logo.
            objInstructorStudentPerformanceDetailsPage.clickOnElement(
                    "ConsolePearsonLogo", "Click on the pearson logo");

            // Verify course tile on course view page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Click on the Student List Tab
            objProductApplicationInstructorDashboardPage
                    .switchToStudentListTab();

            // Search for the student on the student list page.
            APP_LOG.info("Searching the learner on the student list page.");
            objProductApplicationInstructorDashboardPage.enterInputData(
                    "SearchStudentFilter", learnerUserName,
                    "Enter the username in the search student input box and search for the learner.");

            // Hit Enter Key
            act.sendKeys(Keys.ENTER).perform();

            // Click on the student name appeared in the search result
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardListOfStudents",
                    "Click on the student name appeared in the search result.");

            // Verify TOT for preAssessment
            APP_LOG.info(
                    "Verifying that the hyphen should be displayed for the student since the pre asessment is not started as of now.");
            objInstructorStudentPerformanceDetailsPage
                    .verifyTOT("preAssessment", "-");

            // Click on the cross icon on the student details page to close the
            // details and navigate back to Student list page.
            objInstructorStudentPerformanceDetailsPage.clickOnElement(
                    "CloseStudentDetailsCrossButton",
                    "Click on the Cross button on Student Details page and navigate back to the student list page.");

            // Logout of the application
            objProductApplicationCourseViewPage.verifyLogout();

            // Login to the application as a Learner
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-Assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            // Attempt 6 questions in Diagnostic
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 6,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // Logout of the application
            objProductApplicationCourseViewPage.verifyLogout();

            // Login to the application again as an Instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            // Verify course tile on course view page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Click on the Student List Tab
            objProductApplicationInstructorDashboardPage
                    .switchToStudentListTab();

            // Search for the student on the student list page.
            APP_LOG.info("Searching the learner on the student list page.");
            objProductApplicationInstructorDashboardPage.enterInputData(
                    "SearchStudentFilter", learnerUserName,
                    "Enter the username in the search student input box and search for the learner.");

            act.sendKeys(Keys.ENTER).perform();

            // Click on the student name appeared in the search result
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardListOfStudents",
                    "Click on the student name appeared in the search result.");

            // Verify TOT for preAssessment when the Diagnostic is In progress
            APP_LOG.info(
                    "Verifying that the hyphen should be displayed for the student even after attmpting 6 questions in diagnostic");
            objInstructorStudentPerformanceDetailsPage
                    .verifyTOT("preAssessment", "-");

        }

        finally {

            webDriver.quit();
            webDriver = null;

        }
    }
}