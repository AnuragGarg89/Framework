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
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date Nov 16, 2017
 * @description : Verify UI of instructor welcome screen .
 */
public class GLP_251083_VerifyContentofWelcomePage extends BaseClass {
	public GLP_251083_VerifyContentofWelcomePage() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify ui of instructor welcome screen. ")
	public void verifyContents() {
		startReport(getTestCaseId(), "Verify ui of instructor welcome screen.");
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

			// Navigate to the welcome page
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();
			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);

			// Verify that the "course" link is displayed on the page
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorCourseLink",
					ResourceConfigurations.getProperty("welcomeCourseLink"),
					"Verify course link with arrow Sign");

			// Verify the step numbers displayed on the page below get started
			// button
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorStepText",
					ResourceConfigurations.getProperty("welcomeStepNumber"),
					"Verify step number is displayed.");

			// Verify the "Get Started" button displayed on the welcome page.
			objProductApplicationWelcomeInstructorPage
					.verifyText("WelcomeInstructorGetStartedButton",
							ResourceConfigurations
									.getProperty("welcomeGetStarted"),
							"Verify Get Started button is displayed on welcome screen.");

			// Verify the Welcome text with instructor name displayed on the
			// welcome page.
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorWelcomeText",
					ResourceConfigurations.getProperty("welcomeMessage")
							+ instructorName + "!",
					"Verify welcome text with instructor name is displayed.");

			// Verify the thanks message displayed below the welcome text.
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorThanksMessageText",
					ResourceConfigurations
							.getProperty("welcomeScreenThanksMessage"),
					"Verify Thanks message is displayed on welcome screen.");

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