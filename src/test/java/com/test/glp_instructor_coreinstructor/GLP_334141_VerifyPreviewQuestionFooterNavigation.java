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
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CoreInstructionsPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author anurag.garg1 2018-05-03
 * 
 */
public class GLP_334141_VerifyPreviewQuestionFooterNavigation extends BaseClass {

	public GLP_334141_VerifyPreviewQuestionFooterNavigation() {

	}

	@Test(groups = { Groups.REGRESSION,
			Groups.INSTRUCTOR }, enabled = true, description = "To verify correct footer links are displayed on Preview Questions")
	public void verifyPreviewQuestionFooterNavigation() {

		startReport(getTestCaseId(), "To verify correct footer links are displayed on Preview Questions");

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
			// // Login with an Existing user
			objProductApplicationConsoleLoginPage.login(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
					configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

			// Click on course tile
			objProductApplicationCourseViewPage.clickOnCourseTile();

			// Switch to Management tab
			objProductInstructorPerformanceDashboardPage.switchToManagementTab();

			// Navigate to the Preview Questions of LO 11.3
			objProductInstructorManagementDashboardPage.navigateToPreviewQuestionsInLo("11.3");

			// verify header text of preview questions
			objProductCoreInstructionPage.verifyText("CoreInstructionPreviewQuestionsHeaderText",
					ResourceConfigurations.getProperty("previewQuestionsHeader"),
					"Verifying header text of preview questions");

			// Verify that Previous Question link is not present for 1st
			// Question
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionPreviewQuestionsPreviousQuestion",
					"Verify Previous Question link is not displayed on 1st Question on Preview Questions");

			// Verify the Text of Next Question link
			objProductCoreInstructionPage.verifyText("CoreInstructionPreviewQuestionsNextQuestion",
					ResourceConfigurations.getProperty("nextQuestion"), "Verifying Text of Next Question Link");

			// Click on Next Question link
			objProductCoreInstructionPage.clickOnElement("CoreInstructionPreviewQuestionsNextQuestion",
					"Clicking on Next Question Link");

			// Verify the Text of Previous Question link
			objProductCoreInstructionPage.verifyText("CoreInstructionPreviewQuestionsPreviousQuestion",
					ResourceConfigurations.getProperty("previousQuestion"), "Verifying Text of Previous Question Link");

			// Click on Previous Question link
			objProductCoreInstructionPage.clickOnElement("CoreInstructionPreviewQuestionsPreviousQuestion",
					"Clicking on Previous Question Link");

			// Navigate to the Last Question
			objProductCoreInstructionPage.navigateToLastQuestionOnPreviewQuestion();

			// Verify that Next Question link is not present for LAST Question
			objProductCoreInstructionPage.verifyElementNotPresent("CoreInstructionPreviewQuestionsNextQuestion",
					"Verify Next Question link is not displayed on Last Question on Preview Questions");

			// Get the Text of Previous Question link
			objProductCoreInstructionPage.getText("CoreInstructionPreviewQuestionsPreviousQuestion");

		} finally {
			webDriver.quit();
			webDriver = null;
		}

	}
}
