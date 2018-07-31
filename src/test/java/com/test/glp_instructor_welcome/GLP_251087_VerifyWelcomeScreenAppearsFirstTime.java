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

package com.test.glp_instructor_welcome;

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
 * @author pallavi.tyagi
 * @date Nov 14, 2017
 * @description : Verify that after clicking on "Get started" button, welcome
 *              screen will never appear after clicking on course tile.
 * 
 * 
 */
public class GLP_251087_VerifyWelcomeScreenAppearsFirstTime extends BaseClass {
	public GLP_251087_VerifyWelcomeScreenAppearsFirstTime() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that after clicking on Get started button, welcome screen will never appear after clicking on course tile.")
	public void verifyWelcomeScreenAppearance() {
		startReport(
				getTestCaseId(),
				"Verify that after clicking on Get started button, welcome screen will never appear after clicking on course tile.");

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
			objProductApplicationCourseViewPage
					.verifyElementPresent("CourseTileInstructor",
							"Courses associated with instruction displayed on Instructor homepage");

			// Navigate to the welcome page
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();
			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);

			objProductApplicationWelcomeInstructorPage.verifyElementPresent(
					"WelcomeInstructorGetStartedButton",
					"Verify that instructor is navigated to Welcome screen.");

			// Navigate to the Mastery Settings page
			objProductApplicationWelcomeInstructorPage
					.navigateToPreAssessmentMastryLevel();
			GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);
			objProductApplicationMasterySettingPage
					.verifyElementPresent("PreAssessmentMasteryNextBtn",
							"Verify that instructor is navigated to Pre-Assessment Mastery setting screen.");
			GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
					reportTestObj, APP_LOG);

			// Navigate to the Performance dashboard page
			objProductApplicationMasterySettingPage
					.navigateToInstructorDashboard();

			// Verify the logout button and logout of the application.
			objProductApplicationCourseViewPage.verifyLogout();

			// Login again with same user credentials
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));

			// Navigate to course tile page and click on course tile.
			objProductApplicationCourseViewPage.clickOnCourseTile();

			// Verify that the welcome page is not displayed.
			objProductApplicationWelcomeInstructorPage
					.verifywelcomeScreenNotDisplayed();

			// Verify that the Performance Dashboard page is displayed
			objProductApplicationInstructorDashboardPage
					.verifyText(
							"InstructorDashBoardPerformanceTab",
							ResourceConfigurations
									.getProperty("courseMaterialViewPageMenuOption2"),
							"Verify user has navigated to the performance dashboard page.");
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