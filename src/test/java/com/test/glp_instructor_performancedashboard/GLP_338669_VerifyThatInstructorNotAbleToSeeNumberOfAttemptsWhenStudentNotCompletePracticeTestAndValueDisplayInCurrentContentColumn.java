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
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mohit.gupta5
 * @date May 10, 2018
 * @description :To verify that instructor should be able to see number of
 *              attempts when a student has made for practice test and value
 *              display in current content column
 * 
 */
public class GLP_338669_VerifyThatInstructorNotAbleToSeeNumberOfAttemptsWhenStudentNotCompletePracticeTestAndValueDisplayInCurrentContentColumn
        extends BaseClass {
    public GLP_338669_VerifyThatInstructorNotAbleToSeeNumberOfAttemptsWhenStudentNotCompletePracticeTestAndValueDisplayInCurrentContentColumn() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "To verify that If a student has not attempted Practice even once, then No of attempts will be displayed beneath Practice/Quiz label in current content column")
    public void
           verifyThatInstructorNotAbleToSeeNumberOfAttemptsWhenStudentNotCompletePracticeTestAndValueDisplayInCurrentContentColumn() {
        startReport(getTestCaseId(),
                "To verify that If a student has not attempted Practice even once, then No of attempts will be displayed beneath Practice/Quiz label in current content column");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Create Learner subscribing the new course created by the
            // Instructor and unlock it via api

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // // Login to the application as a learner
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Navigate to diagnostic test
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Skip all Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on start button of first module");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of first LO");

            // Click on Course Drawer Back Arrow
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on Course Drawer Back Arrow");

            objGLPLearnerPracticeTestPage.verifySingout();

            objGLPConsoleLoginPage.login(instructorName,
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            // Login to the application as an Instructor
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            // Navigate to the performance dashboard
            objGLPInstructorCourseViewPage.navigateToPerformanceDashboard();

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Verify Student List is clicked.");

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceModuleDropDown",
                    "Verify Module List is clicked.");

            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    "Module 11",
                    "InstructorDashBoardPerformanceModuleDropDownValues");

            objProductApplicationInstructorDashboardPage.verifyText(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations.getProperty("practiceText"),
                    "Content coming in 'Current Content' column matches with the filter selection.");
            // Verify "Attempt 1" Text
            objProductApplicationInstructorDashboardPage
                    .verifyElementNotPresent("PerforManceDashBoardAttempt1Text",
                            "Verify 'Attempt 1' Text is Not displayed in 'Current Content' column");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
