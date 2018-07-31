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
 * @date Nov 14, 2017
 * @description : Verify that on clicking 'Course' link, instructor should land
 *              back to course home page.
 */
public class GLP_266611_VerifyCourseLinkfunctionality extends BaseClass {
	public GLP_266611_VerifyCourseLinkfunctionality() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR,
			Groups.NEWCOURSEREQUIRED }, enabled = true, description = "Verify that on clicking 'Course' link, instructor should land back to course home page.")
	public void verifyCourselinkfunctionality() {
		startReport(
				getTestCaseId(),
				"Verify that on clicking 'Course' link, instructor should land back to course home page.");

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

			// Navigate to the Welcome page.
			objProductApplicationCourseViewPage
					.navigateToWelcomeScreenInstructor();

			// Verify that the welcome page is displayed to the user.
			objProductApplicationWelcomeInstructorPage.verifyElementPresent(
					"WelcomeInstructorGetStartedButton",
					"Verify that instructor is navigated to Welcome screen.");

			// Verify that the "course" link is displayed on the welcome page.
			objProductApplicationWelcomeInstructorPage
					.verifyElementPresent("WelcomeInstructorCourseLink",
							"Verify course link with back arrow sign is displayed on welcome screen.");

			// Verify that clicking on "course" link, user is navigated back to
			// the course view page
			objProductApplicationWelcomeInstructorPage
					.navigateToCourseViewPageFromWelcomescreen();
			objProductApplicationCourseViewPage
					.verifyElementPresent(
							"CourseTileInstructor",
							"Verify that on clicking course link instructor is land back to courseview page from welcome screen.");
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