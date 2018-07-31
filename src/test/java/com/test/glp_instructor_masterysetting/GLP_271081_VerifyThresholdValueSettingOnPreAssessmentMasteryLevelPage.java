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
 * @author anuj.tiwari1
 * @date Nov 29, 2017
 * @description : Verify that the user is able to set the threshold value on the
 *              'Pre-Assessment Mastery Level' page
 */
public class GLP_271081_VerifyThresholdValueSettingOnPreAssessmentMasteryLevelPage
		extends BaseClass {
	public GLP_271081_VerifyThresholdValueSettingOnPreAssessmentMasteryLevelPage() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that the user is able to set the threshold value on the 'Pre-Assessment Mastery Level' page.")
	public void verifyThresholdValueSettingOnPreAssessmentMasteryLevelPage() {
		startReport(
				getTestCaseId(),
				"Verify that the user is able to set the threshold value on the 'Pre-Assessment Mastery Level' page.");

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

			// Click on the Pearson Logo
			objProductApplicationCourseViewPage.clickOnElement("PearsonLogo",
					"Click on the Pearson Logo");

			// Verify course tile on courseview page
			objProductApplicationCourseViewPage
					.verifyElementPresent("CourseTileInstructor",
							"Courses associated with instruction displayed on Instructor homepage");

			// Navigate to the Mastery Settings page
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();
			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);

			objProductApplicationWelcomeInstructorPage
					.navigateToPreAssessmentMastryLevel();
			GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);

			GLPInstructor_MasterySettingPage objProductApplicationMasterySetting = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);

			// Click on slider
			objProductApplicationMasterySetting
					.clickByJS("PreAssessmentMasteryPageSlider",
							"Click on the slider that is displayed on the Pre Assessment mastery page.");

			// Get mastery value before edit
			String masteryLevelBeforeEdit = objProductApplicationMasterySetting
					.getElementAttribute("PreAssessmentMasterySliderValue",
							"value",
							"Get the attribute value from the slider input box.");

			// Click on slider
			objProductApplicationMasterySetting
					.clickByJS("PreAssessmentMasteryPageSlider",
							"Click on the slider that is displayed on the Pre Assessment mastery page.");

			// Move slider
			objProductApplicationManagementDashboardPage.moveSlider();

			// Get mastery value after edit
			String masteryLevelAfterEdit = objProductApplicationMasterySetting
					.getElementAttribute("InstructorManagementTextBox",
							"value",
							"Get the attribute value from the slider input box.");

			// Verify value updated
			objProductApplicationManagementDashboardPage.verifySliderMovement(
					masteryLevelBeforeEdit, masteryLevelAfterEdit + "%");

			// Click on the next button
			objProductApplicationMasterySetting
					.clickOnElement("PreAssessmentMasteryNextBtn",
							"Click on the Next button displayed on the Pre Assessment mastery page.");

			// Click on Edit button
			objProductApplicationManagementDashboardPage.clickOnEditButton();

			objProductApplicationManagementDashboardPage
					.verifyElementAttributeValue(
							"InstructorManagementMasteryPercentageTextBox",
							"value",
							masteryLevelAfterEdit,
							"Verify that the mastery value set on Pre Assessment page is equal to the value displayed on the management page.");
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