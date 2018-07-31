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

package com.test.console_coursecreation;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : To verify that for new instructor clicking on the "Courses"
 *              link available on Welcome page and Pre-assessment mastery will
 *              redirect to console home page.
 */
public class GLP_283536_VerifyCoursesLinkRedirectsToConsoleHome
        extends BaseClass {
    public GLP_283536_VerifyCoursesLinkRedirectsToConsoleHome() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.CONSOLE,
            Groups.NEWCOURSEREQUIRED }, enabled = false,
            description = "To verify that for new instructor clicking on the Courses link available on Welcome page and Pre-assessment mastery will redirect to console home page.")

    public void verifyCoursesLinkRedirectsToConsoleHome() {
        startReport(getTestCaseId(),
                "To verify that for new instructor clicking on the Courses link available on Welcome page and Pre-assessment mastery will redirect to console home page.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            GLPConsole_LoginPage objInstructorConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_WelcomeInstructorPage objInstructorWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objInstructorMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            objInstructorConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            objInstructorWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify user is redirected to welcome Page.");
            objInstructorWelcomeInstructorPage.clickOnElement(
                    "WelcomeInstructorCourseLink",
                    "Click on Courses Link on welcome page.");
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            objInstructorWelcomeInstructorPage.verifyElementPresent(
                    "WelcomeInstructorGetStartedButton",
                    "Verify user is redirected to welcome Page.");
            objInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            objInstructorMasterySettingPage.verifyElementPresent(
                    "preAssessmentMasteryPageHeading",
                    "Verify user is redirected to Pre Assessment Mastery Page.");
            objInstructorMasterySettingPage.clickOnElement(
                    "PreAssessmentMasteryPageCourseLink",
                    "Click on Courses Link.");
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardCourseContentText",
                    "Verify user is redirected to Instructor Dashboard Page.");
            //// objInstructorMasterySettingPage.verifyElementPresent(
            // "preAssessmentMasteryPageHeading",
            // "Verify user is redirected to Pre Assessment Mastery Page.");
            // objInstructorMasterySettingPage.navigateToInstructorDashboard();
            objInstructorMasterySettingPage.clickOnElement("PearsonLogo",
                    "Click on Pearson Logo.");
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            objProductApplicationInstructorDashboardPage
                    .switchToManagementTab();
            GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                    reportTestObj, APP_LOG);
            objProductApplicationManagementDashboardPage.verifyElementPresent(
                    "PreAssessmentMasteryLevelHeading",
                    "Verify user is redirected to Management tab,");
            objInstructorMasterySettingPage.clickOnElement("PearsonLogo",
                    "Click on Pearson Logo.");
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
        }

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