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
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author lekh.bahl
 * @date Nov 22, 2017
 * @description : Verify that the user is able to see the correct step number
 *              upon landing to the Mastery threshold page.
 */
public class GLP_271079_VerifyStepNumber extends BaseClass {
	public GLP_271079_VerifyStepNumber() {
		// TODO Auto-generated constructor stub
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that the user is able to see the correct step number upon landing to the Mastery threshold page.")
	public void verifyStepNumber() {
		startReport(
				getTestCaseId(),
				"Verify that the user is able to see the correct step number upon landing to the Mastery threshold page.");

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

			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);
			objProductApplicationCourseViewPage.verifyElementPresent(
					"CourseTileInstructor",
					"Verify Instructor is logged in successfully.");

			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);

			// Navigate to the welcome page
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();

			// Verify that the Get Started button is displayed on the welcome
			// page
			objProductApplicationWelcomeInstructorPage
					.verifyElementPresent("WelcomeInstructorGetStartedButton",
							"Verify that get started button is present on Welcome screen.");

			// Navigate to the Pre Assessment Mastery Page
			objProductApplicationWelcomeInstructorPage
					.navigateToPreAssessmentMastryLevel();

			// 271083 steps covered below
			GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);
			objProductApplicationMasterySettingPage.clickOnElement(
					"MasterySettingTextBox",
					"Verify that user click on mastery level textbox");
			objProductApplicationMasterySettingPage.enterInputData(
					"MasterySettingTextBox", "85",
					"Verify that user enters a value between 80 and 100");

			// Click on back button
			objProductApplicationMasterySettingPage
					.navigateToWelcomeScreenFromMasterySetting();

			// Click on get started button
			objProductApplicationWelcomeInstructorPage
					.navigateToPreAssessmentMastryLevel();

			// Click on course link
			objProductApplicationMasterySettingPage
					.navigateToCourseViewFromCourseLink();

			// Click on course tile to navigate to mastery settings screen
			objProductApplicationCourseViewPage.navigateToMasterySetting();

			String masteryValue = objProductApplicationMasterySettingPage
					.getElementAttribute("PreAssessmentMasterySliderValue",
							"value", "Fetch already set mastery value from UI.");

			// Refresh the current browser page
			objCommonUtil.refreshCurrentPage();
			objProductApplicationCourseViewPage.verifyLogout();
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));
			// Click on course tile to navigate to mastery settings screen
			objProductApplicationCourseViewPage.navigateToMasterySetting();
			String sliderValue = objProductApplicationMasterySettingPage
					.getElementAttribute("PreAssessmentMasterySliderValue",
							"value",
							"Fetch currently set mastery value from UI.");

			objProductApplicationMasterySettingPage.verifyMasteringScore(
					masteryValue, sliderValue);

			// Verify Step Number at the bottom of the page.
			objProductApplicationMasterySettingPage
					.verifyText("PreAssessmentMasteryStepText",
							ResourceConfigurations
									.getProperty("preAssessmentStepText"),
							"Verify step number on the bottom of the page should be displayed");

			// 271080 steps covered below
			objProductApplicationMasterySettingPage
					.verifyElementAttributeValue(
							"PreAssessmentMasterySliderValue",
							"value",
							ResourceConfigurations
									.getProperty("defaultPreAssessmentSliderValue"),
							"Verfiy that the default value on pre assessement mastery level is 90%");

			// 271082 covered below
			objProductApplicationMasterySettingPage.enterInputData(
					"MasterySettingTextBox", "75",
					"Verify that user enters a value less than 80");

			// Verify error message
			objProductApplicationMasterySettingPage
					.verifyText(
							"MasteryThresholdErrorMessage",
							ResourceConfigurations
									.getProperty("masteryThresholdErrorMessage"),
							"Verify error message appears if the value entered is less than 80");

			// Verify error message in red
			objProductApplicationMasterySettingPage
					.verifyElementCSSValue("MasteryThresholdErrorMessage",
							"color", "rgba(219, 0, 32, 1)",
							"Verify that error message displayed on Screen should be RED in color");

			// Set mastery level in input field more than 100
			objProductApplicationMasterySettingPage.enterInputData(
					"MasterySettingTextBox", "175",
					"Verify that user enters a value less than 80");

			// Verify error message
			objProductApplicationMasterySettingPage
					.verifyText(
							"MasteryThresholdErrorMessage",
							ResourceConfigurations
									.getProperty("masteryThresholdErrorMessage"),
							"Verify error message appears if the value entered is less than 80");

			// Verify error message in red
			objProductApplicationMasterySettingPage
					.verifyElementCSSValue("MasteryThresholdErrorMessage",
							"color", "rgba(219, 0, 32, 1)",
							"Verify that error message displayed on Screen should be RED in color");

			// 271081 covered below objProductApplicationMasterySettingPage

			objProductApplicationMasterySettingPage
					.clickByJS("PreAssessmentMasteryPageSlider",
							"Click on the slider that is displayed on the Pre Assessment mastery page.");
			// Get mastery value before edit
			String masteryLevelBeforeEdit = objProductApplicationMasterySettingPage
					.getElementAttribute("PreAssessmentMasterySliderValue",
							"value",
							"Get the attribute value from the slider input box.");

			// Move slider
			GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);
			objCommonUtil.refreshCurrentPage();
			// Click on slider
			objProductApplicationMasterySettingPage
					.clickByJS("PreAssessmentMasteryPageSlider",
							"Click on the slider that is displayed on the Pre Assessment mastery page.");

			// Move Slider
			objProductApplicationManagementDashboardPage.moveSlider();

			// Get mastery value after edit
			String masteryLevelAfterEdit = objProductApplicationMasterySettingPage
					.getElementAttribute(
							"PreAssessmentMasteryPercentageTextBox", "value",
							"Get the attribute value from the slider input box.");

			// Verify value updated
			objProductApplicationManagementDashboardPage.verifySliderMovement(
					masteryLevelBeforeEdit, masteryLevelAfterEdit + "%");

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