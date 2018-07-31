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
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : Verify that header for Instructor is displayed on following
 *              pages: Login page,Course View page,Welcome screen,Mastery
 *              Setting Level Screen, Instructor Management board and Instructor
 *              dashboard.
 */
public class GLP_265213_VerifyInstructorDispalyedOnAllPages extends BaseClass {
    public GLP_265213_VerifyInstructorDispalyedOnAllPages() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify Link content for footer links.")

    public void verifyInstructorDispalyedOnAllPages() {
        startReport(getTestCaseId(),
                "Verify that the user is able navigate through the pages and to the instructor home page.");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {

            // Login to the application as an Instructor
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify instructor is navigated to instructor home
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify user is navigated to instructor home page");
            // Navigate to the Performance Dashboard of Instructor
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            // Verify the pearson logo and username
            objProductApplicationCourseViewPage.verifyPearsonLogoAndUserName();

            // Navigate to the Management Dashboard of Instructor
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();

            // Verify the pearson logo and username
            objProductApplicationCourseViewPage.verifyPearsonLogoAndUserName();

        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}