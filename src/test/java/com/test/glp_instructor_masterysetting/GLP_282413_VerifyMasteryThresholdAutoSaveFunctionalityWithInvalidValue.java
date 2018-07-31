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
 * @author pallavi.tyagi
 * @date Dec 7, 2017
 * @description : Verify that when the instructor inputs the mastery threshold
 *              value below or above the specified range then it should not be
 *              auto-saved on navigating away from the page
 */
public class GLP_282413_VerifyMasteryThresholdAutoSaveFunctionalityWithInvalidValue
		extends BaseClass {
	public GLP_282413_VerifyMasteryThresholdAutoSaveFunctionalityWithInvalidValue() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that when the instructor inputs the mastery threshold value below or above the specified range then it should not be auto-saved on navigating away from the page.")
	public void verifyMasteryThresholdAutoSaveFunctionalityWithInvalidValue() {
		startReport(
				getTestCaseId(),
				"Verify that when the instructor inputs the mastery threshold value below or above the specified range then it should not be auto-saved on navigating away from the page.");

		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
		String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);
		try {

			// Create Instructor and subscribe course using corresponding APIs.
			objRestUtil.createInstructorWithNewCourse(instructorName,
					ResourceConfigurations.getProperty("consolePassword"),
					false);

			// Login to the application as an Instructor
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
					reportTestObj, APP_LOG);
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));

			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);
			objProductApplicationCourseViewPage.verifyElementPresent(
					"CourseTileInstructor",
					"Verify Instructor is logged in successfully.");

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
			objProductApplicationMasterSettingPage
					.verifyElementPresent("PreAssessmentMasteryNextBtn",
							"Verify that user is navigated to the Pre-assessment mastery lavel page");

			// Enter invalid numeric value in text box
			objProductApplicationMasterSettingPage.enterInputData(
					"MasterySettingTextBox", ResourceConfigurations
							.getProperty("preAssessmentThresholdInvalidValue"),
					"Enter invalid numeric value in text box.");

			objProductApplicationCourseViewPage.verifyLogout();
			// Login to the application again with same Instructor
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));

			// Verify course tile on courseview page
			objProductApplicationCourseViewPage
					.verifyElementPresent(
							"CourseTileInstructor",
							"Verify after clicking 'Courses' link instructor is navigated to courseview page");
			// click on course tile
			objProductApplicationCourseViewPage.clickOnElement(
					"CourseTileInstructor", "Click on course tile");

			objProductApplicationMasterSettingPage
					.verifyElementPresent("PreAssessmentMasteryNextBtn",
							"Verify that user is navigated to the Pre-assessment mastery lavel page");

			// verify mastery threshold value is auto-saves
			objProductApplicationMasterSettingPage.verifyElementAttributeValue(
					"MasterySettingTextBox", "value", ResourceConfigurations
							.getProperty("defaultPreAssessmentSliderValue"),
					"Verify that threshold value is autosaved.");

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