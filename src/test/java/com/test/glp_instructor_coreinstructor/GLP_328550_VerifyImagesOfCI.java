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
 * 
 * @author anurag.garg1 2018-04-19
 *
 */
public class GLP_328550_VerifyImagesOfCI extends BaseClass {
	public GLP_328550_VerifyImagesOfCI() {

	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = true, description = "Verify the image displayed on Core Instruction page.")
	public void verifyImagesOfCI() throws InterruptedException {

		startReport(getTestCaseId(), "Verify the image displayed on Core Instruction page.");

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

			// Click on Preview button of LO11.1
			objProductInstructorManagementDashboardPage.navigateToCoreInstructionsInLo("11.1");

			// Navigate to 4th core instruction of the 1st EO
			objProductCoreInstructionPage.clickOnElement("CoreInstructionNthCoreInstructionUnderNthEO:EONumber=1,CoreInstruction=4",
					"Clicking on the 4th Core instruction of the 1st EO");

			// Verify the image displayed for the 4th Core instruction
			objProductCoreInstructionPage.verifyImage("CoreInstruction1stEO4thCoreInstructionImage",
					"Verifying image on 4th Core instruction of 1st EO on core instruction page");

			// Display the alt text on the image
			objProductCoreInstructionPage.getElementAttribute("CoreInstruction1stEO4thCoreInstructionImage", "title",
					"Display the alt text of the image");

			// Navigate to 3th Core instruction of the 1st EO
			objProductCoreInstructionPage.clickOnElement("CoreInstructionNthCoreInstructionUnderNthEO:EONumber=1,CoreInstruction=3",
					"Clicking on the 3rd Core instruction of the 1st EO");

			// Navigate to 2nd EO
			objProductCoreInstructionPage.clickOnElement("CoreInstructionNthEO:EONumber=2", "Clicking on 2nd EO of 11.1 LO");

			// Click on Back button
			objProductCoreInstructionPage.clickOnElement("CoreInstructionBackButton",
					"Click on back button to go to Management dashboard page");
		} finally {
			webDriver.quit();
			webDriver = null;
		}
	}
}
