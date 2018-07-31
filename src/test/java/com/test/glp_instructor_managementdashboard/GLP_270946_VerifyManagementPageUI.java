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

package com.test.glp_instructor_managementdashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : To verify the UI of management page.
 */
public class GLP_270946_VerifyManagementPageUI extends BaseClass {
    public GLP_270946_VerifyManagementPageUI() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "To verify the UI of management page.")

    public void verifyManagementPageUI() {
        startReport(getTestCaseId(), "To verify the UI of management page.");

        GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // Generate unique instructor userName
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_ManagementDashboardPage objProductManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to the welcome instructor page
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            // Navigate to the Mastery Settings page
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            // Change Mastery percentage
            objProductApplicationMasterySettingPage.setMasteryPercentage();

            // Navigate to the Management dashboard page
            objProductApplicationMasterySettingPage
                    .navigateToInstructorDashboard();

            objProductManagementDashboardPage.verifyElementPresent(
                    "InstructorManagementEditButton",
                    "Verify edit button is appearing.");

            // Verify Management tab is active
            objProductManagementDashboardPage.verifyElementAttributeValue(
                    "ManagementTab", "class", "active",
                    "Verify management tab is selected.");

            // Verify UI of Management Page
            objProductManagementDashboardPage
                    .verifyPreAssessmentMasteryLevelSection();

        }

        // Delete User via API
        finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }
}