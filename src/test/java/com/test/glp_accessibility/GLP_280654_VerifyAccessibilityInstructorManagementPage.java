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

package com.test.glp_accessibility;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mohit.gupta5
 * @date Nov 14, 2017
 * @description : To Verify Instructor Management Page is implemented with
 *              (WCAG) 2.0 with AA standards
 * 
 */
public class GLP_280654_VerifyAccessibilityInstructorManagementPage
        extends BaseClass {
    public GLP_280654_VerifyAccessibilityInstructorManagementPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = false,
            description = "To Verify Instructor Management Page is implemented with (WCAG) 2.0 with AA standards")

    public void verifyAccessibilityInstructorManagementPage() {
        startReport(getTestCaseId(),
                "To Verify Instructor Management Page is implemented with (WCAG) 2.0 with AA standards");

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

            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify course tile
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");
            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Welcome page for Instructor.
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            // Verify that the Get Started button is displayed on the welcome
            // page
            objProductApplicationWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify that instructor is navigated to Welcome screen.");

            GLPInstructor_MasterySettingPage objProductApplicationMasterSettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Pre Assessment mastery level page.
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            objProductApplicationMasterSettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify that user is navigated to the Pre-assessment mastery lavel page");

            // Change Mastery percentage
            objProductApplicationMasterSettingPage.setMasteryPercentage();

            // Navigate to the Management dashboard page
            objProductApplicationMasterSettingPage
                    .navigateToInstructorDashboard();
            objRestUtil.testAccessibility();
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
