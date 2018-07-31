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

/**
 * @author anurag.garg1
 *
 */
public class GLP_328531_VerifyTOCFooterNavigation extends BaseClass {

	public GLP_328531_VerifyTOCFooterNavigation() {

	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = false, description = "Verify that Instructor should be able to navigate to next or previous content within an EO through footer.")
	public void verifyTOCFooterNavigation() {

		startReport(getTestCaseId(),
				"Verify that Instructor should be able to navigate to next or previous content within an EO through footer.");

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

			// Expand Module 12
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementModule12Name",
					"Module 12 Expanded");

			// Click on Preview button of LO 12.4
			objProductInstructorManagementDashboardPage.clickOnElement("InstructorManagementPreviewButtonOfLO12.4",
					"Preview Button of LO 12.4 clicked");

			// Collapse the 1st EO
			objProductCoreInstructionPage.clickOnElement("CoreInstruction1stEO", "Collapse first EO of LO 12.4");

			// Click on Second EO to expand on Core Instruction Page
			objProductCoreInstructionPage.clickOnElement("CoreInstruction2ndEO", "Expand second EO of LO 12.4");

			// Click on 1st Core Instruction of 2nd EO
			objProductCoreInstructionPage.clickOnElement("CoreInstruction2ndEO1stCoreInstruction",
					"Clicking on 1st Core Instruction of 12.4");

			// Verify that Previous navigation is not present for 1st Core
			// Instruction
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionFooterPreviousPageArrow",
					"Verify Previous navigation is not displayed on 1st core instruction");

			// Get the text of the next core Instruction
			String nextCoreInstruction = objProductCoreInstructionPage.getText("CoreInstructionFooterNextCIText",
					"Text of Next Core instruction");
			if (nextCoreInstruction.contains(": ")) {
				nextCoreInstruction = nextCoreInstruction.replace(": ", "");
			}

			// Verify that the text of second core instruction
			objProductCoreInstructionPage.verifyElementAttributeValue("CoreInstruction2ndEO2ndCoreInstruction",
					"innerText", nextCoreInstruction, "Get the text of the second Core instruction from TOC");

			// Click on next navigation to navigate to 2nd core instruction
			objProductCoreInstructionPage.verifyElementPresent("CoreInstructionFooterNextPageArrow",
					"Verify next navigation is displayed on 1st core instruction");
			objProductCoreInstructionPage.clickOnElement("CoreInstructionFooterNextPageArrow",
					"Click on next navigation ");

			// Verify that the content displayed is of next core instruction
			objProductCoreInstructionPage.verifyElementAttributeValue("CoreInstructionContentOnRightHandSide",
					"innerText", nextCoreInstruction, "Validating the text displayed on right hand side");

			// Verify the next verb displayed is correct in footer
			objProductCoreInstructionPage.verifyNextVerbOnFooter("CoreInstructionFooterNextVerb",
					"CoreInstruction2ndEO3rdCoreInstructionImg", "Verifying the next verb on the footer");

			// Verify the previous verb displayed is correct in footer
			objProductCoreInstructionPage.verifyPreviousVerbOnFooter("CoreInstructionFooterPreviousVerb",
					"CoreInstruction2ndEO1stCoreInstructionImg", "Verifying the previous verb on the footer");

			// Verify that Previous navigation is present on 2nd Core
			// Instruction
			objProductCoreInstructionPage.verifyElementPresent("CoreInstructionFooterPreviousPageArrow",
					"Verify Previous navigation is displayed on 2nd core instruction");

			// Click on Previous navigation from 2nd core Instruction
			objProductCoreInstructionPage.clickOnElement("CoreInstructionFooterPreviousPageArrow",
					"Verify Previous navigation is displayed on 2nd core instruction");

			// Verify that Previous navigation takes us back to 1st core
			// instruction
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionFooterPreviousPageArrow",
					"Verify Previous navigation is not displayed on 1st core instruction");

			// Navigate to the last core instruction of 2nd EO
			objProductCoreInstructionPage.clickOnElements("CoreInstructionAllCoreInstructionsUnder2ndEO",
					"Navigating to the last core Instruction of 2nd EO under Module 12.4");

			// Verify that Next navigation is not present for last Core
			// Instruction
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionFooterNextPageArrow",
					"Verify Next navigation is not displayed on Last core instruction");

			// click on 6th core instruction
			objProductCoreInstructionPage.clickOnElement("CoreInstruction2ndEO6thCoreInstruction",
					"Clicking on 6th Core Instruction of 12.4");

			// Verify the next verb displayed is correct in footer
			objProductCoreInstructionPage.verifyNextVerbOnFooter("CoreInstructionFooterNextVerb",
					"CoreInstruction2ndEO7thCoreInstructionImg", "Verifying the next verb on the footer");

			// Verify the previous verb displayed is correct in footer
			objProductCoreInstructionPage.verifyPreviousVerbOnFooter("CoreInstructionFooterPreviousVerb",
					"CoreInstruction2ndEO5thCoreInstructionImg", "Verifying the previous verb on the footer");

			// click on 8th core instruction
			objProductCoreInstructionPage.clickOnElement("CoreInstruction2ndEO8thCoreInstruction",
					"Clicking on 8th Core Instruction of 12.4");

			// Verify the next verb displayed is correct in footer
			objProductCoreInstructionPage.verifyNextVerbOnFooter("CoreInstructionFooterNextVerbForPracticeQuiz",
					"CoreInstruction2ndEO9thCoreInstructionImg", "Verifying the next verb on the footer");

			// Verify the previous verb displayed is correct in footer
			objProductCoreInstructionPage.verifyPreviousVerbOnFooter("CoreInstructionFooterPreviousVerb",
					"CoreInstruction2ndEO7thCoreInstructionImg", "Verifying the previous verb on the footer");

		} finally {
			webDriver.quit();
			webDriver = null;
		}
	}
}
