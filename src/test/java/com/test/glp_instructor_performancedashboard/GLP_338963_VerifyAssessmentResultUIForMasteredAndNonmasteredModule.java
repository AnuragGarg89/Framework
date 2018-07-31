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
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;

/**
 * @author lekh.bahl
 * @date May 30, 2018
 * @description
 * 
 */
public class GLP_338963_VerifyAssessmentResultUIForMasteredAndNonmasteredModule
        extends BaseClass {
    public GLP_338963_VerifyAssessmentResultUIForMasteredAndNonmasteredModule() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify that Pre-assessment results Card is displayed as per the VD design showing the details of Mastered/Non Mastered Modules of any student.")
    public void verifyStudentNameAndEmailIDIsDisplayed() {
        startReport(getTestCaseId(),
                "Verify that Pre-assessment results Card is displayed as per the VD design showing the details of Mastered/Non Mastered Modules of any student.");

        try {

            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
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

            // Click on Student list tab
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Click on student list tab");

            // Click on 2nd student in the student list
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentListSecondStudent",
                    "Click on 2nd student in the student list");
            // Verify pre-assessment result card is displayed
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PreAssessmentResultCard",
                    "Verify Pre-assessment result card is displayed");
            // Verify Legend/Options shown below Pre-assessment results card
            // Header.
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PreAssessmentResultCardMastered",
                    "Verify Legend/Option mastered, shown below Pre-assessment results card Header.");
            // Verify Legend/Options shown below Pre-assessment results card
            // Header.
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PreAssessmentResultCardRequired",
                    "Verify Legend/Option required, shown below Pre-assessment results card Header.");
            // verify that all the modules are listed as 11, 12, 13 and so on in
            // circles just below the Legends/Options.

            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PreAssessmentResultCardRequired",
                    "Verify every module represent as circel in preassessment result.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
