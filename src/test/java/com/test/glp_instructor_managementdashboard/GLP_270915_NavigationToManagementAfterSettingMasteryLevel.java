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
 * @author mukul.sehra
 * @date Dec 01 , 2017
 * @description : Verify that the Management tab is the landing page for an
 *              instructor after threshold setup from pre-assessment mastery
 *              level page for the first time login.
 */
public class GLP_270915_NavigationToManagementAfterSettingMasteryLevel extends BaseClass {
	public GLP_270915_NavigationToManagementAfterSettingMasteryLevel() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that the Management tab is the landing page for an instructor after threshold setup from pre-assessment mastery level page for the first time login")

	public void verifyNavigationToManagementAfterSettingMasteryLevel() {
		startReport(getTestCaseId(),
				"Verify that the Management tab is the landing page for an instructor after threshold setup from pre-assessment mastery level page for the first time login");

		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

		// Generate unique instructor userName
		String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);

		// Create user with role Instructor, subscribe RIO-Squires course
		try {
			objRestUtil.createInstructorWithNewCourse(instructorName,
					ResourceConfigurations.getProperty("consolePassword"), false);
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj,
					APP_LOG);
			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);
			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);
			GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);

			// Login to the application
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));
			objProductApplicationCourseViewPage.waitForCourseToAppearInstructor();

			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instructor displayed on Instructor homepage");
			// Navigate to welcome screen
			objProductApplicationCourseViewPage.navigateToWelcomeScreenInstructor();

			// Navigate to the Mastery Settings page
			objProductApplicationWelcomeInstructorPage.navigateToPreAssessmentMastryLevel();
			objProductApplicationMasterySettingPage.verifyElementPresent("PreAssessmentMasteryNextBtn",
					"Verify that user is navigated to the Pre-assessment mastery lavel page");
			// Set the mastery level to 90% and verify that value in the input
			// box has been changed to 90
			objProductApplicationMasterySettingPage.enterInputData("PreAssessmentMasteryPercentageTextBox",
					ResourceConfigurations.getProperty("changeMasteryPercentage90"),
					"set the master percent to 90 on Assessment Mastery Setting page");

			objProductApplicationMasterySettingPage.verifyElementAttributeValue("PreAssessmentMasterySliderValue",
					"value", ResourceConfigurations.getProperty("changeMasteryPercentage90"),
					"Verify that Mastery Level Percentage set to 90%");

			// Change Mastery percentage
			objProductApplicationMasterySettingPage.setMasteryPercentage();
			
			// Navigate to the Instructor dashboard page
			objProductApplicationMasterySettingPage.navigateToInstructorDashboard();

			// Verify that the Instructor has been landed to the management tab
			GLPInstructor_ManagementDashboardPage objProductApplication_ManagementDashboard = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);
			objProductApplication_ManagementDashboard.verifyElementAttributeValue("ManagementTab", "class", "active",
					"Verify management tab is selected.");
			String masteryLevelOnInstructorDashBoard = objProductApplication_ManagementDashboard
					.getText("InstructorManagementMasteryPercentage");

			// Verify the mastery level on the management dashboard page
			objProductApplication_ManagementDashboard.verifyMasteringScore(
					ResourceConfigurations.getProperty("changeMasteryPercentageValue") + "%",
					masteryLevelOnInstructorDashBoard);
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