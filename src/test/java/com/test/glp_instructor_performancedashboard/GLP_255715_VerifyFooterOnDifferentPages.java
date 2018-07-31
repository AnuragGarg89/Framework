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
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : Verify footer for instructor on Login page,Course View
 *              page,Welcome screen,Mastery Setting Level Screen and Instructor
 *              dashboard.
 */
public class GLP_255715_VerifyFooterOnDifferentPages extends BaseClass {
    public GLP_255715_VerifyFooterOnDifferentPages() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.NEWCOURSEREQUIRED },
            enabled = false,
            description = "Verify footer for instructor on Login page,Course View page,Welcome screen,Mastery Setting Level Screen and Instructor dashboard.")

    public void verifyFooterLinks() {
        startReport(getTestCaseId(),
                "Verify footer for instructor on Login page,Course View page,Welcome screen,Mastery Setting Level Screen and Instructor dashboard.");

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

            // Login into the application
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instructor displayed on Instructor homepage");

            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to the Welcome Screen Instructor
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();

            objProductApplicationWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify 'Get Started' button is present");
            objProductApplicationWelcomeInstructorPage.verifyFooterHyperLinks();

            // Navigate to Mastery Screen Instructor
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();

            objProductApplicationMasterySettingPage.verifyElementPresent(
                    "PreAssessmentMasteryNextBtn",
                    "Verify Next button is present");
            objProductApplicationMasterySettingPage.verifyFooterHyperLinks();

            // Navigate to Instructor Dashboard
            objProductApplicationMasterySettingPage
                    .navigateToInstructorDashboard();

            objProductApplicationInstructorDashboardPage.scrollIntoView(
                    "InstructorDashBoardFooterAccessibilityLink");
            objProductApplicationInstructorDashboardPage
                    .verifyFooterHyperLinks();
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