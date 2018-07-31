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
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : To verify new instructor lands on Management view after
 *              setting the mastery and existing, user lands on performance
 *              view..
 */
public class GLP_270945_VerifyUserLandsOnManagementView extends BaseClass {
	public GLP_270945_VerifyUserLandsOnManagementView() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT,
			Groups.INSTRUCTOR, }, enabled = true, description = "To verify new instructor lands on Management view after setting the mastery and existing, user lands on performance view.")
	public void verifyUserLandsOnManagementView() {
		startReport(getTestCaseId(),
				"To verify new instructor lands on Management view after setting the mastery and existing, user lands on performance view.");

		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

		// Generate unique instructor userName
		String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);

		// Create Instructor with role Instructor and subscribe and login

		try {
			objRestUtil.createInstructorWithNewCourse(instructorName,
					ResourceConfigurations.getProperty("consolePassword"), false);
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj,
					APP_LOG);
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));

			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);

			// Verify user is redirected to Course View Page
			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instruction displayed on Instructor homepage");

			GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
					reportTestObj, APP_LOG);
			GLPInstructor_MasterySettingPage objProductApplicationMasterySettingPage = new GLPInstructor_MasterySettingPage(
					reportTestObj, APP_LOG);
			GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
					reportTestObj, APP_LOG);
			GLPInstructor_ManagementDashboardPage objProductManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);

			// Navigate to the Welcome Instructor Page
			objProductApplicationCourseViewPage.navigateToWelcomeScreenInstructor();

			objProductApplicationWelcomeInstructorPage.verifyElementPresent("WelcomeInstructorGetStartedButton",
					"Verify Get started button is appearing.");

			// Navigate to the Mastery Settings page
			objProductApplicationWelcomeInstructorPage.navigateToPreAssessmentMastryLevel();

			objProductApplicationMasterySettingPage.verifyElementPresent("PreAssessmentMasteryNextBtn",
					"Verify Next button is appearing.");

			// Change Mastery percentage
			objProductApplicationMasterySettingPage.setMasteryPercentage();

			// Navigate to the Management Dashboard Page
			objProductApplicationMasterySettingPage.navigateToInstructorDashboard();

			objProductManagementDashboardPage.verifyElementPresent("InstructorManagementEditButton",
					"Verify edit button is appearing.");

			// Verify changed percentage is displayed on Management Dashboard
			// page
			objProductManagementDashboardPage.verifyText("InstructorManagementMasteryPercentage",
					ResourceConfigurations.getProperty("changeMasteryPercentage"),
					"Verify changed percentage is displayed i.e. 85%.");

			// Click on person Logo
			objProductManagementDashboardPage.clickOnElement("PearsonLogo", "Click on Pearson Logo.");
			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instruction displayed on Instructor homepage");

			// Again click on course tile and verify user is redirected to
			// performance tab
			objProductApplicationCourseViewPage.navigateToPerformanceDashboard();

			objProductApplicationInstructorDashboardPage.verifyElementPresent("InstructorDashBoardCourseContentText",
					"Verify user is on performance dashboard page.");

			// Logout of flow
			objProductApplicationCourseViewPage.verifyLogout();
			/*
			 * objProductApplicationConsoleLoginPage.verifyElementPresent(
			 * "LoginUserNameTextBox",
			 * "Verify user has logged out successfully and has come to login screen." );
			 * 
			 * if (terminateOnFailure()) { proceedOnFail = false; return; }
			 */

			// Relogin
			objProductApplicationConsoleLoginPage.login(instructorName,
					ResourceConfigurations.getProperty("consolePassword"));
			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instruction displayed on Instructor homepage");

			// Again click on course tile and verify user is redirected to
			// performance tab
			objProductApplicationCourseViewPage.navigateToPerformanceDashboard();

			objProductApplicationInstructorDashboardPage.verifyElementPresent("InstructorDashBoardCourseContentText",
					"Verify user is on performance dashboard page.");

			// Click on Pearson Logo
			objProductApplicationInstructorDashboardPage.clickOnElement("PearsonLogo", "Click on Pearson Logo.");

			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instruction displayed on Instructor homepage");

			// Again click on course tile and verify user is redirected to
			// performance tab
			objProductApplicationCourseViewPage.navigateToPerformanceDashboard();
			objProductApplicationInstructorDashboardPage.verifyElementPresent("InstructorDashBoardCourseContentText",
					"Verify user is on performance dashboard page.");
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
