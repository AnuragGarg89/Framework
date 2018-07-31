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
package com.test.glp_sanity;

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
 * @author anurag.garg1
 * @date 2018-05-23
 * @description Verify that Instructor is able to verify core instruction for LO 13.1
 *
 */
public class GLP_350893_VerifyCoreInstructionLo13_1 extends BaseClass {
	public GLP_350893_VerifyCoreInstructionLo13_1() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.SANITY,
			Groups.INSTRUCTOR, Groups.TEST }, enabled = false, description = "Verify that Instructor is able to verify core instruction for LO 13.1")
	public void verifyCoreInstructionLo13_1() throws InterruptedException {
		startReport(getTestCaseId(), "Verify that Instructor is able to verify core instruction for LO 13.1");

		try {
			GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj,
					APP_LOG);

			GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
					reportTestObj, APP_LOG);

			GLPInstructor_InstructorDashboardPage objProductInstructorPerformanceDashboardPage = new GLPInstructor_InstructorDashboardPage(
					reportTestObj, APP_LOG);

			GLPInstructor_ManagementDashboardPage objProductInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
					reportTestObj, APP_LOG);

			GLPInstructor_CoreInstructionsPage objProductCoreInstructionPage = new GLPInstructor_CoreInstructionsPage(
					reportTestObj, APP_LOG);

			// Login with an Existing user
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

			// Click on course tile
			objProductApplicationCourseViewPage.clickOnCourseTile();

			// Switch to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();

			// Navigate to the EO's of LO 13.1
			objProductInstructorManagementDashboardPage.navigateToCoreInstructionsInLo("13.1");
			
			// Verify the Core Instruction content
			objProductCoreInstructionPage.traverseAndVerifyContentEo();

		} finally {
			webDriver.quit();
			webDriver = null;
		}
	}
}