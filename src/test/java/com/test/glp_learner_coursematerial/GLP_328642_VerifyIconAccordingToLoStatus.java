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
package com.test.glp_learner_coursematerial;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date Nov 23, 2017
 * @description : Verify that correct orientation message displays based on the
 *              progress of a student in the course and for the LO in a module
 *              on the course home page.
 */
public class GLP_328642_VerifyIconAccordingToLoStatus extends BaseClass {
	public GLP_328642_VerifyIconAccordingToLoStatus() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true, description = "Verify that correct orientation message displays based on the progress of a student in the course and for the LO in a module on the course home page.")
	public void verifyMessageAccordingToLoStatus() {
		startReport(
				getTestCaseId(),
				"Verify that correct orientation message displays based on the progress of a student in the course and for the LO in a module on the course home page.");
		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
		String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);
		try {

			objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
					ResourceConfigurations.getProperty("consolePassword"),
					ResourceConfigurations
							.getProperty("consoleUserTypeLearner"),
					configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
					true);
			// Login in the application
			GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
					reportTestObj, APP_LOG);
			objGLPConsoleLoginPage.login(learnerUserName,
					ResourceConfigurations.getProperty("consolePassword"));
			GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
					reportTestObj, APP_LOG);
			// Verify CourseTile Present and navigate to Welcome Learner Screen
			objGLPLearnerCourseViewPage.verifyCourseTilePresent();
			GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
					reportTestObj, APP_LOG);
			objGLPLearnerCourseHomePage
					.verifyElementPresent("CourseHomeStartYourPathBtn",
							"Verify learner is successfully navigated to welcome screen.");
			GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
					.navigateToDiagnosticPage();
			// Attempt diagnostic test
			GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
					reportTestObj, APP_LOG);

			objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
					ResourceConfigurations
							.getProperty("submitWithoutAttempt"));

			// verify diagnostic test completed
			objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
			// Click on Go TO Course Home Link
			objGLPLearnerDiagnosticTestPage
					.clickOnElement("DiagnosticGoToCourseHomeLink",
							"Click on Go To Course Home Link to navigate to course material page");
			// Verify star icon in blue colour

			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"CourseMaterialFirstModuleStar", "fill",
					ResourceConfigurations
							.getProperty("starColourInReadyState"),
					"Verify that star icon is in blue state.");
			// Verify star icon circle is in gray colour
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"CourseMaterialFirstModuleStarInnerCircle",
					"background-color", ResourceConfigurations
							.getProperty("starcircleColourInReadyState"),
					"Verify that circle is in blue state.");
			// ///////// Verify star icon outer circle is in gray colour
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"#accordion-example>div:nth-of-type(1) .circle-arc",
					"background-color", ResourceConfigurations
							.getProperty("starcircleColourInReadyState"),
					"Verify that outer circle arch colour is gray.");
			// complete 1st Lo
			objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName, "11.1");
			objCommonUtil.refreshCurrentPage();
			// Verify star icon in blue colour
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"CourseMaterialFirstModuleStar", "fill",
					ResourceConfigurations
							.getProperty("starColourInReadyState"),
					"Verify that star icon is in blue state.");
			// Verify star icon circle is in gray colour
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"CourseMaterialFirstModuleStarInnerCircle",
					"background-color", ResourceConfigurations
							.getProperty("starcircleColourInReadyState"),
					"Verify that circle is in blue state.");
			// ///////// Verify star icon outer circle is in gray colour
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"#accordion-example>div:nth-of-type(1) .circle-arc",
					"background-color", ResourceConfigurations
							.getProperty("starcircleColourInReadyState"),
					"Verify that outer circle arch colour is gray.");
			// complete 2nd Lo
			objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName, "11.2");
			objCommonUtil.refreshCurrentPage();

		}

		finally {
			if (unpublishData.equalsIgnoreCase("TRUE")) {
				objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
						ResourceConfigurations.getProperty("consolePassword"));
				System.out.println("Unpublish data from couchbase DB");
			}
			webDriver.quit();
			webDriver = null;
		}
	}
}
