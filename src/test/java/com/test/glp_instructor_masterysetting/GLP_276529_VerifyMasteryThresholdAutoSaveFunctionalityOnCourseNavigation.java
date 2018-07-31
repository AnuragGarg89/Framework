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
 * @description : Verify that the threshold value added by the instructor on
 *              Pre- Assessment mastery setup page is auto-saved on navigating
 *              away from the page (On Courses Click).
 */
public class GLP_276529_VerifyMasteryThresholdAutoSaveFunctionalityOnCourseNavigation
		extends BaseClass {
	public GLP_276529_VerifyMasteryThresholdAutoSaveFunctionalityOnCourseNavigation() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that the threshold value added by the instructor on Pre- Assessment mastery setup page is auto-saved on navigating away from the page(On Courses Click).")
	public void verifyMasteryThresholdAutoSave() {
		startReport(
				getTestCaseId(),
				"Verify that the threshold value added by the instructor on Pre- Assessment mastery setup page is auto-saved on navigating away from the page (On Courses Click).");

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

			// Set mastery level in input field
			objProductApplicationMasterSettingPage
					.enterInputData(
							"MasterySettingTextBox",
							ResourceConfigurations
									.getProperty("preAssessmentThresholdValidValue1"),
							"Verify that instrucor updated mastery threshold value in text box");

			// Click on course link button
			objProductApplicationMasterSettingPage.clickOnElement(
					"PreAssessmentMasteryPageCourseLink",
					"Click on 'Courses' button.");
			// Verify course tile on courseview page
			objProductApplicationCourseViewPage
					.verifyElementPresent(
							"CourseTileInstructor",
							"Verify after clicking 'Courses' link instructor is navigated to courseview page");
			// click on course tile
			objProductApplicationCourseViewPage.clickOnElement(
					"CourseTileInstructor", "Click on course tile");

			// navigate to pre-assessment page
			objProductApplicationMasterSettingPage
					.verifyElementPresent(
							"InstructorManagementTab",
							"Verify that after clicking course tile, instructor is navigated to Management Dashboard page");

			objProductApplicationMasterSettingPage.clickOnElement(
					"InstructorManagementTab", "Click on management tab");

			objProductApplicationMasterSettingPage.verifyElementPresent(
					"InstructorManagementDashboardEdit", "Verify Edit button");

			objProductApplicationMasterSettingPage
					.clickOnElement("InstructorManagementDashboardEdit",
							"Click on edit button");

			// verify mastery threshold value is auto-saves
			objProductApplicationMasterSettingPage.verifyElementAttributeValue(
					"InstructorManagementMasteryPercentageTextBox", "value",
					ResourceConfigurations
							.getProperty("preAssessmentThresholdValidValue1"),
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