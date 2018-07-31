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
 * @author mayank.mittal
 * @date March 07, 2018
 * @description : Verify First Non Mastered Circle Icon Divided Into Small Lines
 *              Equals To Number Of LOs Present.
 */
public class GLP_306043_VerifyFirstNonMasteredCircleIconDividedIntoSmallLinesEqualsToNumberOfLOsPresent
		extends BaseClass {
	public GLP_306043_VerifyFirstNonMasteredCircleIconDividedIntoSmallLinesEqualsToNumberOfLOsPresent() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = false, description = "Verify First Non Mastered Circle Icon Divided Into Small Lines Equals To Number Of LOs Present.")
	public void VerifyFirstNonMasteredCircleIconDividedIntoSmallLinesEqualsToNumberOfLOsPresent()
			throws InterruptedException {
		startReport(
				getTestCaseId(),
				"Verify First Non Mastered Circle Icon Divided Into Small Lines Equals To Number Of LOs Present.");
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
			// Automate the remaining steps of test case
			GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
					reportTestObj, APP_LOG);

			objGLPLearnerCourseHomePage
					.verifyElementPresent("CourseHomeStartYourPathBtn",
							"Verify learner is successfully navigated to welcome screen.");

			GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
					.navigateToDiagnosticPage();

			// Skip all Diagnostic Test Question

			GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = objGLPLearnerDiagnosticTestPage
					.attemptAdaptiveDiagnosticTest(0, 0, ResourceConfigurations
							.getProperty("submitWithoutAttempt"));

			objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();

			objGLPLearnerDiagnosticTestPage
					.clickOnElement("DiagnosticGoToCourseHomeLink",
							"Click on Go To Course Home Link to navigate to course material page");

			// Open second non-mastered module
			objGLPLearnerCourseMaterialPage.navigateCourseModuleByIndex(
					"CourseMaterialModuleTitleButton", 6,
					"Verify second unmastered module is clicked.");
			// Verify number LO & Circle Partition
			objGLPLearnerCourseMaterialPage.verifyNumberOfLOs(
					"CourseMaterialLOStartButton",
					"CourseMaterialprogressCircleCount");

		} finally {
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
