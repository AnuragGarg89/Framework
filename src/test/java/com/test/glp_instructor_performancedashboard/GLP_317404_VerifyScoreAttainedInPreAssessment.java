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
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author anuj.tiwari
 * @date June 21, 2018
 * @description : Verify the Time for any module and TOT related UI on Student
 *              Details page.
 * 
 */
public class GLP_317404_VerifyScoreAttainedInPreAssessment extends BaseClass {

    public GLP_317404_VerifyScoreAttainedInPreAssessment() {
    }

    @Test(groups = { Groups.REGRESSION }, enabled = true,
            description = "Verify the score attained by any student in Pre Assessment on Student Details page.")

    public void verifyScoreOfPreAssessment() {
        startReport(getTestCaseId(),
                "Verify the score attained by any student in Pre Assessment on Student Details page.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String learnerUserName = "GLP_Learner_317404_EyBk";
        // "GLP_Learner_" + getTestCaseId() + "_"
        // + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {

            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_StudentPerformanceDetailsPage objInstructorStudentPerformanceDetailsPage = new GLPInstructor_StudentPerformanceDetailsPage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            /*
             * // Create user and subscribe course using corresponding APIs.
             * objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
             * ResourceConfigurations.getProperty("consolePassword"),
             * ResourceConfigurations .getProperty("consoleUserTypeLearner"),
             * configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);
             * 
             * // Login to the application as a Learner
             * objProductApplicationConsoleLoginPage.login(learnerUserName,
             * ResourceConfigurations.getProperty("consolePassword"));
             * 
             * // Verify CourseTile Present and navigate to Welcome Learner
             * Screen objGLPLearnerCourseViewPage.verifyCourseTilePresent();
             * 
             * // Click on 'Start Pre-Assessment' button
             * objProductApplicationCourseHomePage.navigateToDiagnosticPage();
             * 
             * // Attempt complete the diagnostic test
             * objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0,
             * 0, ResourceConfigurations.getProperty("submitWithoutAttempt"));
             * 
             * // verify diagnostic test completed
             * objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
             * 
             * // Click on Go TO Course Home Link
             * objGLPLearnerDiagnosticTestPage.clickOnElement(
             * "DiagnosticGoToCourseHomeLink",
             * "Click on Go To Course Home Link to navigate to course material page"
             * );
             * 
             * // Logout of the application
             * objProductApplicationCourseViewPage.verifyLogout();
             */
            // Login to the application again as an Instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"),
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

            // Click on the sort icon for student score for sorting the score in
            // ascending order.

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "SortScoreButton",
                    "Click on the sorting button, to sort the score in ascending order.");

            // Verifying that the score is sorted in ascending order.
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "StudentDiagnosticScore", "asc",
                    "Verify that the student score is sorted in ascending order.");

            // Click on the sort icon for TOT for sorting the TOT in
            // ascending order.

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "SortTOTButton",
                    "Click on the sorting button, to sort the TOT in ascending order.");

            // Verifying that the TOT is sorted in ascending order.
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "InstructorDashboardTimeOnTask", "asc",
                    "Verify that the TOT is sorted in ascending order.");

            // Search for the student on the student list page.
            APP_LOG.info("Searching the learner on the student list page.");
            objProductApplicationInstructorDashboardPage.enterInputData(
                    "SearchStudentFilter", learnerUserName,
                    "Enter the username in the search student input box and search for the learner.");

            Actions act = new Actions(returnDriver());
            act.sendKeys(Keys.ENTER).perform();

            objProductApplicationInstructorDashboardPage.verifyText(
                    "StudentDiagnosticCompletedStatus",
                    ResourceConfigurations.getProperty("statusCompleted"),
                    "Verify that the status of the diagnostic test is completed.");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "StudentDiagnosticScore",
                    "Verify that the score is present when the student has completed the diagnostic.");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "StatusColumnBeforeResultColumn",
                    "Verify that the Status column is present before Results column.");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "PlacementColumnAfterResultColumn",
                    "Verify that the Placement column is present after Results column.");

        }

        finally {

            webDriver.quit();
            webDriver = null;

        }
    }
}