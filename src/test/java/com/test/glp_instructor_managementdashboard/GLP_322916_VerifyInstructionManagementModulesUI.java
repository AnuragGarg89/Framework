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
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

public class GLP_322916_VerifyInstructionManagementModulesUI extends BaseClass {

	public GLP_322916_VerifyInstructionManagementModulesUI() {

	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = true, description = "To verify the UI of Moudle section on Instructor Management page")
	public void verifyInstructorManagementUI() {

		startReport(getTestCaseId(), "To verify the UI of Moudle section on Instructor Managemnet page");

		GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj, APP_LOG);

		GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
				reportTestObj, APP_LOG);

		GLPInstructor_InstructorDashboardPage objProductInstructorPerformanceDashboardPage = new GLPInstructor_InstructorDashboardPage(
				reportTestObj, APP_LOG);

		GLPInstructor_ManagementDashboardPage objProductInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
				reportTestObj, APP_LOG);

		try {

			// Login with an Existing user
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

			// Click on course tile
			objProductApplicationCourseViewPage.clickOnCourseTile();

			// Switch to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();

			// Verify in collapsed form "PREVIEW" button is not displayed
			objProductInstructorManagementDashboardPage.verifyElementNotPresent(
					"InstructorManagemenPreviewButtonOnModulesInCollapedForm",
					"Preview Button is not Displayed in collapsed form");

			// Expand Module 11
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementModule11Name",
					"Module 11 Expanded");

			// Expand LO 11.1
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementLO11.1RadioButton",
					"Clicking on LO 11.1 to expand");
			String attrOfLO11AfterExpanding = objProductInstructorManagementDashboardPage.getElementAttribute(
					"InstructorManagementLO11.1Attribute", "class",
					"Verify Circle for LO gets highlighted when LO is expanded");
			objProductInstructorManagementDashboardPage.verifyElementAttributeValue(
					"InstructorManagementLO11.1Attribute", "class", attrOfLO11AfterExpanding,
					"Verify 11.1 LO Radio Circle gets highlighted");
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementLO11.1RadioButton",
					"Clicking on LO 11.1 to collapse");

			// Collapse Module 11
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementModule11Name",
					"Module 11 collapsed");

			// Expand All Modules via Accordion
			objProductInstructorManagementDashboardPage
					.expandAllModulesViaAccordians("InstructorManagementModuleAccordians", "Modules 11-16 Expanded");

			// Verify switching tab will display All modules in collapsed form

			// Switch to performance
			objProductInstructorManagementDashboardPage.switchToPerformaceTab();
			// Switching back to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();
			// Verify in collapsed form "PREVIEW" button is not displayed
			objProductInstructorManagementDashboardPage.verifyElementNotPresent(
					"InstructorManagemenPreviewButtonOnModulesInCollapedForm",
					"Preview Button is not Displayed in collapsed form");

			// Expand All Modules via Accordion
			objProductInstructorManagementDashboardPage
					.expandAllModulesViaAccordians("InstructorManagementModuleAccordians", "Modules 11-16 Expanded");

			// Expand All LO's
			objProductInstructorManagementDashboardPage.expandAllLOs("InstructorManagementModuleLORadioButton",
					"LO's of Module 11-16 Expanded");

			// Collapse All LO's
			objProductInstructorManagementDashboardPage.collapseAllLOs("InstructorManagementModuleLORadioButton",
					"LO's of Module 11-16 Collpased");

			// Collapse All Modules via ModuleName
			objProductInstructorManagementDashboardPage
					.collapseAllModulesViaModuleName("InstructorManagementModuleAccordians", "Modules 11-16 Collapsed");

			// SignOut and Sign Back in to verify the default state of this Tab

			// Click on Instructor Name
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementUserNameLink",
					"InstructorName Clicked");
			// Click on SignOut
			objProductApplicationCourseViewPage.verifyLogout();
			
			// Login
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
			// Click on course tile
			objProductApplicationCourseViewPage.clickOnCourseTile();
			// Switch to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();
			// Verify in collapsed form "PREVIEW" button is not displayed
			objProductInstructorManagementDashboardPage.verifyElementNotPresent(
					"InstructorManagemenPreviewButtonOnModulesInCollapedForm",
					"Preview Button is not Displayed in collapsed form");

		} finally {
			webDriver.quit();
			webDriver = null;
		}

	}

}
