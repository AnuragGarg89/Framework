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
 * @author anuj.tiwari1
 * @date Dec 01, 2017
 * @description : Verify UI of instructor pre assessment mastery page in
 *              Spanish.
 */

public class GLP_271857_VerifyPreAssessmentMasteryPageInDifferentLanguages
		extends BaseClass {
	public GLP_271857_VerifyPreAssessmentMasteryPageInDifferentLanguages() {

	}

	@Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify ui of instructor pre assessment mastery page in respective language.")
	public void verifyPreAssessmentMasteryPageInDifferentLanguages() {
		startReport(getTestCaseId(),
				"Verify ui of instructor pre assessment mastery page in respective language.");

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

			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);
			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);
			objProductApplicationCourseViewPage.verifyElementPresent(
					"CourseTileInstructor",
					"Verify Instructor is logged in successfully.");

			// Navigate to the Welcome page for Instructor.
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();

			// Navigate to the Pre Assessment mastery level page.
			objProductApplicationWelcomeInstructorPage
					.navigateToPreAssessmentMastryLevel();

			GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);

			// Verify the title heading on the Pre Assessment Mastery page
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreAssessmentMasteryHeading",
							ResourceConfigurations
									.getProperty("preAssessmentMasteryPageHeading"),
							"Verify that the heading on Pre Assessment page is displayed in respective language");

			// Verify the text displayed below heading on the Pre Assessment
			// Mastery page
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreMasteryPageText",
							ResourceConfigurations
									.getProperty("preAssessmentMasteyrPageText"),
							"Verify the text displayed below heading on the Pre Assessment Mastery page in respecting language");

			// Verify that the next button is displayed in respective language
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreAssessmentMasteryNextBtn",
							ResourceConfigurations
									.getProperty("preAssessmentMasteryNextBtn"),
							"Verify that the next button is displayed in respective language");

			// Verify that the tool tip of Next Button is displayed in
			// respective language.
			objProductApplicationMasterySettingPage
					.verifyElementAttributeValue(
							"PreAssessmentMasteryNextBtn",
							"title",
							ResourceConfigurations
									.getProperty("preAssessmentMasteryNextBtn"),
							"Verify that the tool tip of next button is displayed in respective language.");

			// Verify that the back button is displayed in respective language.
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreAssessmentMasteryBackBtn",
							ResourceConfigurations
									.getProperty("preAssessmentMasteryBackBtn"),
							"Verify that the back button is displayed in respective language.");

			// Verify that the tool tip of Back button is displayed in
			// respective language.
			objProductApplicationMasterySettingPage
					.verifyElementAttributeValue(
							"PreAssessmentMasteryBackBtn",
							"title",
							ResourceConfigurations
									.getProperty("preAssessmentMasteryBackBtn"),
							"Verify that the tool tip of back button is displayed in respective language.");

			// Verify that the step number dispalyed on the pre assessment
			// mastery page, is
			// displayed in respective language.
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreAssessmentMasteryPageStep",
							ResourceConfigurations
									.getProperty("preAssessmentStepText"),
							"Verify that the step number dispalyed on the pre assessment mastery page, is displayed in respective language.");

			// Enter any invalid value inside the slider input box.
			objProductApplicationMasterySettingPage.enterInputData(
					"PreAssessmentMasterySliderValue", ResourceConfigurations
							.getProperty("preAssessmentThresholdInvalidValue"),
					"Enter any invalid value in the slider input box.");

			// Verify that the error message is displayed in respective
			// language.
			objProductApplicationMasterySettingPage
					.verifyText(
							"PreAssessmentMasteryPageErrorMessage",
							ResourceConfigurations
									.getProperty("masteryThresholdErrorMessage"),
							"Verify that the error message is displayed in respective language.");

			// Verify that the courses link is displayed in respective language.
			objProductApplicationMasterySettingPage
					.verifyText("PreAssessmentMasteryPageCourseLink",
							ResourceConfigurations
									.getProperty("welcomeCourseLink"),
							"Verify that the Course link is displayed in the respective language.");

			// Verify that the tool tip of courses link is displayed in
			// respective language.
			objProductApplicationMasterySettingPage
					.verifyElementAttributeValue(
							"PreAssessmentMasteryPageCourseLink", "title",
							ResourceConfigurations
									.getProperty("welcomeCourseLink"),
							"Verify the tool tip of courses link is displayed in respective language.");
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