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
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author pallavi.tyagi
 * @date Nov 23, 2017
 * @description : Verify that on clicking the edit button, input box appear
 *              which allows text to be entered to set threshold value.
 */
public class GLP_270916_VerifyEditMasteryValueFromTextBox extends BaseClass {
	public GLP_270916_VerifyEditMasteryValueFromTextBox() {
	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = true, description = "Verify that on clicking the edit button, input box appear which allows text to be entered to set threshold value.")

	public void verifyEditMasteryValueFromTextBox() {
		startReport(getTestCaseId(),
				"Verify that on clicking the edit button, input box appear which allows text to be entered to set threshold value.");
		try {
			// Login in the application
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj,
					APP_LOG);
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME_EDITSCORE"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);
			// Verify course tile on courseview page
			objProductApplicationCourseViewPage.verifyElementPresent("CourseTileInstructor",
					"Courses associated with instruction displayed on Instructor homepage");

			GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
					reportTestObj, APP_LOG);
			// Navigate to Performance dashboard
			objProductApplicationCourseViewPage.navigateToPerformanceDashboard();

			// Switch to the Management tab
			objProductApplicationInstructorDashboardPage.switchToManagementTab();
			GLPInstructor_ManagementDashboardPage objProductApplicationManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);
			// Verify Edit button on management dashboard.
			objProductApplicationManagementDashboardPage.verifyElementPresent("InstructorManagementEditButton",
					"Verify instructor navigated to management dashboard.");
			// Click on edit button
			objProductApplicationManagementDashboardPage.clickOnElement("InstructorManagementEditButton",
					"Click on edit button.");
			// Verify save button on management dashboard
			objProductApplicationManagementDashboardPage.verifyElementPresent("InstructorManagementSaveButton",
					"Verify save button is displayed.");
			// Verify cancel button on management dashboard
			objProductApplicationManagementDashboardPage.verifyElementPresent("InstructorManagementCancelButton",
					"Verify cancel button is displayed.");
			// Verify textbox on management dashboard
			objProductApplicationManagementDashboardPage.verifyElementPresent("InstructorManagementTextBox",
					"Verify text box is displayed.");
			// Enter valid value in text box

			objProductApplicationManagementDashboardPage.enterInputData("InstructorManagementTextBox",
					ResourceConfigurations.getProperty("managementDashboardTextboxValidValue"),
					"Enter valid value in textbox");

			// Click on save button
			objProductApplicationManagementDashboardPage.clickOnSaveButton();
			
			// Verify pre-assessment mastery threshold value is updated by
			// clicking
			// on save button.
			objProductApplicationManagementDashboardPage.verifyText("InstructorManagementMasteryPercentage",
					ResourceConfigurations.getProperty("managementDashboardTextboxValidValue") + "%",
					"Verify pre-assessment mastery threshold value is updated by clicking on save button.");

		}

		// Delete User via API
		finally {
			webDriver.quit();
			webDriver = null;
		}
	}
}
