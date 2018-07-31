
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
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date June 03, 2018
 * @description: Verify Module 11-16 displaying on TOC on course content page
 *               from instructor dashboard
 */
public class GLP_350625_VerifyTOCOnCourseContentPage extends BaseClass {
    public GLP_350625_VerifyTOCOnCourseContentPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify Module 11-16 displaying on TOC on course content page from instructor dashboard")
    public void verifyTOCOnStudentDetailPage() {
        startReport(getTestCaseId(),
                "Verify Module 11-16 displaying on TOC on course content page from instructor dashboard");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
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

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Complete Diagnostic Test Question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Verify 'Module 11' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=1",
                    ResourceConfigurations.getProperty("module11StudentDetail"),
                    "Verify 'Module 11' is displaying in TOC on student detail page.");

            // Verify 'Module 12' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=2",
                    ResourceConfigurations.getProperty("module12StudentDetail"),
                    "Verify 'Module 12' is displaying in TOC on student detail page.");

            // Verify 'Module 13' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=3",
                    ResourceConfigurations.getProperty("module13StudentDetail"),
                    "Verify 'Module 13' is displaying in TOC on student detail page.");

            // Verify 'Module 14' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=4",
                    ResourceConfigurations.getProperty("module14StudentDetail"),
                    "Verify 'Module 14' is displaying in TOC on student detail page.");

            // Verify 'Module 15' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=5",
                    ResourceConfigurations.getProperty("module15StudentDetail"),
                    "Verify 'Module 15' is displaying in TOC on student detail page.");

            // Verify 'Module 16' is displaying in TOC on student detail page
            objGLPInstructorDashboardPage.verifyTextContains(
                    "Module11to16OnCourseContent:dynamicReplace=6",
                    ResourceConfigurations.getProperty("module16StudentDetail"),
                    "Verify 'Module 16' is displaying in TOC on student detail page.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
