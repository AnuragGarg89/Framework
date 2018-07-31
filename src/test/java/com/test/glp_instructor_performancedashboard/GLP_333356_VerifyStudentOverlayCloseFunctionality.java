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
 * @description Verify that the student detail is open up in a overlay and
 *              browser 'Back', 'X' icon and 'Refresh' the web page closes the
 *              overlay window. Also, removing 'UserName' and adding 'Footer'
 *              links from this screen.
 * 
 */
public class GLP_333356_VerifyStudentOverlayCloseFunctionality
        extends BaseClass {
    public GLP_333356_VerifyStudentOverlayCloseFunctionality() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify that the student detail is open up in a overlay and browser 'Back', 'X' icon and 'Refresh' the web page closes the overlay window. Also, removing 'UserName' and adding 'Footer' links from this screen.")
    public void verifyStudentNameAndEmailIDIsDisplayed() {
        startReport(getTestCaseId(),
                "Verify that the student detail is open up in a overlay and browser 'Back', 'X' icon and 'Refresh' the web page closes the overlay window. Also, removing 'UserName' and adding 'Footer' links from this screen.");

        try {

            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
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

            // Click on Student list tab
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Click on student list tab");

            // Click on 2nd student in the student list
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentListSecondStudent",
                    "Click on 2nd student in the student list");

            // Click on cross icon
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardCloseIcon",
                    "Click on cross icon on student detail screen");

            // Verify on clicking the cross icon instructor is redirected to the
            // student list screen
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "StudentListSecondStudent",
                    "Verify on clicking the cross icon instructor is redirected to the student list screen");

            // Click on 2nd student in the student list
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentListSecondStudent",
                    "Click on 2nd student in the student list");

            // Click on browser back button
            objProductApplicationInstructorDashboardPage.clickBrowserBackButton(
                    "Click on browser back button student detail screen");

            // Verify on clicking the browser back button instructor is
            // redirected to the
            // student list screen
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "CourseContentInstructorProgress",
                    "Verify on clicking the browser back button instructor is redirected to the student list screen");

            // Click on Student list tab
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Click on student list tab");

            // Click on 2nd student in the student list
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentListSecondStudent",
                    "Click on 2nd student in the student list");

            // Refresh the browser
            objCommonUtil.refreshCurrentPage();

            // Verify on refreshing the browser instructor is redirected to the
            // student list screen
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "StudentListSecondStudent",
                    "Verify on refreshing the browser instructor is redirected to the student list screen");

            // Click on 2nd student in the student list
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "StudentListSecondStudent",
                    "Click on 2nd student in the student list");

            // Verify footer should be displayed on the overlay screen
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "DashBoardPrivacyPolicyLink",
                    "Verify privacy policy footer displayed on the overlay screen");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "DashBoardFooterAccessibilityLink",
                    "Verify accessibility footer displayed on the overlay screen");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "DashBoardFooterTermsAndConditionsLink",
                    "Verify terms and conditions footer be displayed on the overlay screen");

            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "DashBoardFooterCopyRightText",
                    "Verify copyright text displayed on the overlay screen");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
