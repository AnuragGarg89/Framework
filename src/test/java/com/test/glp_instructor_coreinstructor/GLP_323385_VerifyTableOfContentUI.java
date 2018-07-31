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
package com.test.glp_instructor_coreinstructor;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CoreInstructionsPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

public class GLP_323385_VerifyTableOfContentUI extends BaseClass {

	public GLP_323385_VerifyTableOfContentUI() {

	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = true, description = "To verify the UI of Table of Content in Instruction flow")
	public void verifyTableOfContentUI() {

		startReport(getTestCaseId(), "To verify the UI of Table of Content in Instruction flow");

		GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj, APP_LOG);

		GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
				reportTestObj, APP_LOG);

		GLPInstructor_InstructorDashboardPage objProductInstructorPerformanceDashboardPage = new GLPInstructor_InstructorDashboardPage(
				reportTestObj, APP_LOG);

		GLPInstructor_ManagementDashboardPage objProductInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
				reportTestObj, APP_LOG);

		GLPInstructor_CoreInstructionsPage objProductCoreInstructionPage = new GLPInstructor_CoreInstructionsPage(
				reportTestObj, APP_LOG);

		try {
			// Login with an Existing user
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
			// Click on course tile
			objProductApplicationCourseViewPage.clickOnCourseTile();

			// Switch to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();

			// Expand Module 11
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementModule11Name",
					"Module 11 Expanded");

			// Get the name of LO 11.1
			String loNameOnManagementTab = objProductInstructorManagementDashboardPage
					.getText("InstructorManagementLO11.1Name");
			loNameOnManagementTab = loNameOnManagementTab.replace(":", ": ");

			// Expand LO 11.1
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementLO11.1RadioButton",
					"Clicking on LO 11.1 to expand");

			// Get Text of All EO's under LO 11
			String[] allEONamesOnManagementTab = objProductInstructorManagementDashboardPage
					.getText("InstructorManagementAllEOOFLO11.1", "Getting Name of All EO's on Management tab");

			// Click on Preview button of LO11.1
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementPreviewButtonOfLO11.1",
					"Preview Button of LO 11.1 clicked");

			// Verify that core instruction page opened is for LO 11.1
			objProductCoreInstructionPage.verifyText("CoreInstructionLOName", loNameOnManagementTab,
					"Verifying Core instruction page is opened for LO 11.1");

			// Verify that 1st EO is Expanded by default
			objProductCoreInstructionPage.verifyFirstEOIsExpanded();

			// Verify that 1st Core instruction is selected by default under 1st
			// EO
			objProductCoreInstructionPage.verifyFirstCoreInstructionIsSelectedByDefault();

			// Verify Content displayed on the right hand is of the core
			// instruction selected

			String contentText = objProductCoreInstructionPage.getElementAttribute(
					"CoreInstructionContentOnRightHandSide", "innerText", "Get the text of the content displayed");
			objProductCoreInstructionPage.verifyElementAttributeValue("CoreInstruction1stDefaultCoreInstruction",
					"innerText", contentText, "Validating the text displayed on right hand side");

			// Click on all Core instructions
			objProductCoreInstructionPage.clickOnElements("CoreInstructionAllCoreInstructionsUnderNthEO:EONumber=1",
					"All Core instructions under Default expanded EO clicked");

			// Verify the List of EO on core instruction page is correct
			String[] allEONamesOnCoreInstructionPage = objProductCoreInstructionPage
					.getText("CoreInstructionALLEOName");
			objProductCoreInstructionPage.verifyEoOnCoreInstructionPage(allEONamesOnManagementTab,
					allEONamesOnCoreInstructionPage, "Verify EO's on Core Instruction page are correct");

			// Click on second EO
			objProductCoreInstructionPage.clickOnElement("CoreInstructionNthEO:EONumber=2",
					"Clicking on 2nd EO to expand");

			// Click on All core instructions of 2nd EO
			objProductCoreInstructionPage.clickOnElements("CoreInstructionAllCoreInstructionsUnderNthEO:EONumber=2",
					"All Core instructions under 2nd EO clicked");

			// Click on Hamburger icon to verify full screen is displayed
			objProductCoreInstructionPage.clickOnElement("CoreInstructionHamburgerIconWhenDrawerIsExpanded",
					"Clicking on Hamburger icon");
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionLeftHandDrawerContainer",
					"Drawer container is Collapsed and Full screen is Displayed");

			// Verify on full screen content is displayed
			objProductCoreInstructionPage.verifyElementPresent("CoreInstructionContentOnRightHandSide",
					"Content displayed on Full screen");

			// Click on Hamburger icon to verify full screen is closed
			objProductCoreInstructionPage.clickOnElement("CoreInstructionHamburgerIconWhenDrawerIsCollapsed",
					"Clicking on Hamburger icon");
			objProductCoreInstructionPage.verifyElementPresent("CoreInstructionLeftHandDrawerContainer",
					"Drawer container is Expanded and Full screen is Collapsed");

		} finally {
			webDriver.quit();
			webDriver = null;
		}

	}
}
