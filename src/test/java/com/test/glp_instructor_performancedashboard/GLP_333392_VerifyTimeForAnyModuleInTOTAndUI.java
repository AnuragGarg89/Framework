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

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

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
 * @date June 6, 2018
 * @description : Verify the Time for any module and TOT related UI on Student
 *              Details page.
 * 
 */
public class GLP_333392_VerifyTimeForAnyModuleInTOTAndUI extends BaseClass {

    public GLP_333392_VerifyTimeForAnyModuleInTOTAndUI() {
    }

    @Test(groups = { Groups.REGRESSION }, enabled = true,
            description = "Verify the Time for any module and TOT related UI on Student Details page.")

    public void verifyTimeOnTaskUIAndTime() {
        startReport(getTestCaseId(),
                "Verify the Time for any module and TOT related UI on Student Details page.");
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

            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);

            // Login to the application as a Learner
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Click on 'Start Pre-Assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            // Attempt complete the diagnostic test
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // verify diagnostic test completed
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();

            // Click on Go TO Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "ModuleTwelveSecondChapter",
                    "Click on start button of first module");

            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("EOSecondGuide",
                    "Click on practice quiz of first LO");

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            // // Click on practice test of first LO
            // objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
            // "Click on practice quiz of first LO");
            //
            // // Click on start button on practice test welcome screen
            // objGLPLearnerPracticeTestPage.clickOnElement(
            // "PracticeTestWelcomeScreenStartButton",
            // "Click on start button on practice test welcome screen");
            // // Attempt practice test
            // objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
            // ResourceConfigurations
            // .getProperty("diagnosticSubmitButton"));

            // Navigate back to the Course home page.
            GLPLearner_CourseMaterialPage objLearnerCourseMaterial = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            objLearnerCourseMaterial.clickOnElement(
                    "CourseMaterialModuleBackButton",
                    "Click on the Material Back button to navigate to course home.");

            // Logout of the application
            objProductApplicationCourseViewPage.verifyLogout();

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

            // Search for the student on the student list page.
            APP_LOG.info("Searching the learner on the student list page.");
            objProductApplicationInstructorDashboardPage.enterInputData(
                    "SearchStudentFilter", learnerUserName,
                    "Enter the username in the search student input box and search for the learner.");

            Actions act = new Actions(returnDriver());
            act.sendKeys(Keys.ENTER).perform();

            // // Click on the student name appeared in the search result
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashboardListOfStudents",
                    "Click on the student name appeared in the search result.");

            String tot = objRestUtil.getTotAggregation(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"), "16",
                    "1", "max");

            StringTokenizer str = new StringTokenizer(tot, ":");

            String hour = str.nextToken();
            String min = str.nextToken();
            String sec = str.nextToken();
            String totNew = null;

            if (Integer.parseInt(hour) != 00) {
                hour = hour + " hr";
                totNew = hour;
            }
            if (Integer.parseInt(min) != 00) {
                min = min + " min";
                if (totNew == null) {
                    totNew = min;
                } else {
                    totNew = totNew + " " + min;
                }

            }
            if (Integer.parseInt(sec) != 00) {
                sec = sec + " sec";
                if (totNew == null) {
                    totNew = sec;
                } else {
                    totNew = totNew + " " + sec;
                }
            }

            totNew = totNew.trim();
            objInstructorStudentPerformanceDetailsPage.verifyTOT("lo", totNew,
                    "16.1");

        }

        finally {

            webDriver.quit();
            webDriver = null;

        }
    }
}