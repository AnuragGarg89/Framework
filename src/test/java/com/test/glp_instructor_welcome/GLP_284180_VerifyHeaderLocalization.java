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
 * @author rashmi.z
 * @date Dec 23, 2017
 * @description: To verify header text on CourseHome page should be in the
 *               language selected Localization Support with "Arabic - Saudi
 *               Arabia" languages for Header in Instructor flow Localization
 *               Support with"Spanish (Latin America) - español (Latinoamérica)"
 *               languages for Header in Instructor flow
 *               GLP_287525_VerifyHeaderLocalization and
 *               GLP_284180_VerifyHeaderLocalization
 * 
 */
public class GLP_284180_VerifyHeaderLocalization extends BaseClass {
	public GLP_284180_VerifyHeaderLocalization() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "To verify header on coursehome page localization")
	public void verifyScreenGreyedOutWhenCoachmarkIsPresent() {
		startReport(getTestCaseId(),
				"To verify header on coursehome page localization");
		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
		String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);
		try {
			// Create Instructor and subscribe course using corresponding APIs.
			objRestUtil.createInstructorWithNewCourse(instructorName,
					ResourceConfigurations.getProperty("consolePassword"),
					false);

			// Login in the application
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
					reportTestObj, APP_LOG);
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));

			// Login to the application as an Instructor
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
			// Verify instructor is navigated to Welcome screen

			objProductApplicationWelcomeInstructorPage.verifyElementPresent(
					"WelcomeInstructorGetStartedButton",
					"Verify that instructor is navigated to Welcome screen.");

			objProductApplicationWelcomeInstructorPage.clickOnElement(
					"CourseHomeHeaderAccountName",
					"Click on Learner Account Name.");
			// Verify "My Account text localization
			objProductApplicationWelcomeInstructorPage.verifyText("MyAccount",
					ResourceConfigurations.getProperty("myAccount"),
					"Verify My Account header text is displayed in "
							+ ResourceConfigurations.getProperty("language"));

			// Verify Account Settings text localization
			objProductApplicationWelcomeInstructorPage.verifyText(
					"AccountSettings",
					ResourceConfigurations.getProperty("accountSettings"),
					"Verify Account Settings header text is displayed in "
							+ ResourceConfigurations.getProperty("language"));

			// Verify Sign Out text localization
			objProductApplicationWelcomeInstructorPage.verifyText("SignOut",
					ResourceConfigurations.getProperty("signOut"),
					"Verify Sign Out header text is displayed in "
							+ ResourceConfigurations.getProperty("language"));
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