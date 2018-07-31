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
 * @date Nov 30, 2017
 * @description : Verify content of instructor welcome screen is displayed in
 *              respective language.
 * 
 */
public class GLP_271860_VerifyContentofWelcomePageInRespectiveLanguage extends
		BaseClass {
	public GLP_271860_VerifyContentofWelcomePageInRespectiveLanguage() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify content of instructor welcome screen is displayed in respective language. ")
	public void verifyContents() {
		startReport(
				getTestCaseId(),
				"Verify content of instructor welcome screen is displayed in respective language.");

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

			// Verify Welcome text is displayed in
			// respective language
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorWelcomeMessageText",
					ResourceConfigurations
							.getProperty("welcomePageWelcomeText"),
					"Verify 'Welcome' text is displayed in "
							+ ResourceConfigurations.getProperty("language"));
			// Verify Message 1st line is displayed in
			// respective language
			objProductApplicationWelcomeInstructorPage.verifyTextContains(
					"WelcomeInstructorThanksMessageText",
					ResourceConfigurations
							.getProperty("welcomePageTextMessageLine1"),
					"Verify thanks message is displayed in "
							+ ResourceConfigurations.getProperty("language"));
			// language
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorCourseLink",
					ResourceConfigurations.getProperty("welcomeCourseLink"),
					"Verify course link text is displayed in "
							+ ResourceConfigurations.getProperty("language"));

			// Verify the step numbers displayed in respective language

			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorStepText",
					ResourceConfigurations.getProperty("welcomeStepNumber"),
					"Verify step number is displayed in "
							+ ResourceConfigurations.getProperty("language"));

			// Verify the "Get Started" button text is displayed in respective
			// language
			objProductApplicationWelcomeInstructorPage.verifyText(
					"WelcomeInstructorGetStartedButton",
					ResourceConfigurations.getProperty("welcomeGetStarted"),
					"Verify Get Started button text is displayed in "
							+ ResourceConfigurations.getProperty("language"));
			// verify get started button tooltip text is displayed in respective
			// language
			objProductApplicationWelcomeInstructorPage
					.verifyElementAttributeValue(
							"WelcomeInstructorGetStartedButton",
							"title",
							ResourceConfigurations
									.getProperty("welcomeGetStarted"),
							"Verify Getstarted button Tooltip text is displayed in "
									+ ResourceConfigurations
											.getProperty("language"));
			// verify courses link tooltip text is displayed in respective
			// language
			objProductApplicationWelcomeInstructorPage
					.verifyElementAttributeValue(
							"WelcomeInstructorCourseLink",
							"title",
							ResourceConfigurations
									.getProperty("welcomeCourseLink"),
							"Verify courses link Tooltip text is displayed in "
									+ ResourceConfigurations
											.getProperty("language"));

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