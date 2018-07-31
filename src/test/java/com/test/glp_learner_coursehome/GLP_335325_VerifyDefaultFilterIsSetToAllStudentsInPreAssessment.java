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
package com.test.glp_learner_coursehome;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mohit Gupta5
 * @date May 25, 2018
 * @description :Verify Default filter is set to All Students in Pre Assessment
 */
public class GLP_335325_VerifyDefaultFilterIsSetToAllStudentsInPreAssessment
        extends BaseClass {
    public GLP_335325_VerifyDefaultFilterIsSetToAllStudentsInPreAssessment() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify Default filter is set to All Students in Pre Assessment")
    public void verifyDefaultfilterissettoAllStudentsinPreAssessment() {
        startReport(getTestCaseId(),
                "Verify Default filter is set to All Students in Pre Assessment");
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
                    true);
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),

                    true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Navigate to diagnostic test
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            objGLPLearnerCourseViewPage.verifyLogout();

            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Verify Student List is clicked.");

            objProductApplicationInstructorDashboardPage.verifyText(
                    "InstructorDashBoardPerformanceStatusTesting",
                    ResourceConfigurations.getProperty("testingText"),
                    "Verify 'Testing'  text content coming in the status column");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
