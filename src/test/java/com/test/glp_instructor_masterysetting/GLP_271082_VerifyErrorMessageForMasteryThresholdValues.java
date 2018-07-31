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

package com.test.glp_instructor_masterysetting;

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
 * @author tarun.gupta1
 * @date Nov 21, 2017
 * @description : Verify that an error message is displayed when the user
 *              attempts to set the threshold value below/above the allowed
 *              threshold
 * 
 */
public class GLP_271082_VerifyErrorMessageForMasteryThresholdValues
        extends BaseClass {
    public GLP_271082_VerifyErrorMessageForMasteryThresholdValues() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify that an error message is displayed when the user attempts to set the threshold value below/above the allowed threshold")

    public void verifyErrorMessageForMasteryThresholdValues() {
        startReport(getTestCaseId(),
                "Verify that an error message is displayed when the user attempts to set the threshold value below/above the allowed threshold");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);

            // Login to the application as an Instructor
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            GLPInstructor_MasterySettingPage objProductApplicationMasterSettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            // Navigate to the welcome page
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenInstructor();
            // Navigate to pre assessment mastery
            objProductApplicationWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            objProductApplicationMasterSettingPage.verifyElementPresent(
                    "MasterySettingTextBox",
                    "Mastery setting text box is present");
            // Set mastery level in input field less than 80
            objProductApplicationMasterSettingPage.enterInputData(
                    "MasterySettingTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMinValue"),
                    "Enter less then 80 value in text box");
            // Verify error message
            objProductApplicationMasterSettingPage.verifyText(
                    "MasteryThresholdErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify error message appears if the value entered is less than 80");
            // Verify error message in red
            objProductApplicationMasterSettingPage.verifyElementCSSValue(
                    "MasteryThresholdErrorMessage", "color", "219, 0, 32",
                    "Verify that error message is displayed in RED color");
            // Set mastery level in input field more than 100
            objProductApplicationMasterSettingPage.enterInputData(
                    "MasterySettingTextBox",
                    ResourceConfigurations.getProperty(
                            "managementDashboardTextboxInValidMaxValue"),
                    "Enters a value more than 80 in test box");
            // Verify error message
            objProductApplicationMasterSettingPage.verifyText(
                    "MasteryThresholdErrorMessage",
                    ResourceConfigurations
                            .getProperty("masteryThresholdErrorMessage"),
                    "Verify error message appears if the value entered is more than 80");
            // Verify error message in red
            objProductApplicationMasterSettingPage.verifyElementCSSValue(
                    "MasteryThresholdErrorMessage", "color", "219, 0, 32",
                    "Verify that error message is displayed in RED color");
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
