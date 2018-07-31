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
package com.test.glp_learner_coursehome;

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
 * @author shefali.jain
 * @date June 14, 2018
 * @description : Verify mastered and non mastered module in expanded and collapsed state.
 */

public class GLP_305653_VerifyMasteredNonMasteredModuleInExpandedAndCollapsedState extends BaseClass {
	public GLP_305653_VerifyMasteredNonMasteredModuleInExpandedAndCollapsedState() {
	}

	@Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

			description = "Verify mastered and non mastered module in expanded and collapsed state.")
	public void verifyMasteredNonMasteredModuleInExpandedAndCollapsedState() {
		startReport(getTestCaseId(),
				"Verify mastered and non mastered module in expanded and collapsed state.");

		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

		String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
				+ objCommonUtil.generateRandomStringOfAlphabets(4);

		try {
			// Create user and subscribe course using corresponding APIs.
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

			GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
					.navigateToDiagnosticPage();

			// Attempt diagnostic test
			GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
					reportTestObj, APP_LOG);
			objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
					ResourceConfigurations
					.getProperty("submitWithoutAttempt"));
			   
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            // Click on Go TO Course Home Link
			objGLPLearnerDiagnosticTestPage.clickOnElement(
					"DiagnosticGoToCourseHomeLink",
					"Click on Go To Course Home Link to navigate to course material page");
			
			// Click on first mastered Module to expand it.
			objGLPLearnerCourseMaterialPage.clickOnElement(
					"CourseMaterialExpandModule11",
					"Click module 11 to expand it");

			//verify review button against all LOs for each mastered modules in expanded state.
			objGLPLearnerCourseMaterialPage.verifyCountOfElements("CourseMaterialReviewButtonsOfModule11", 4 , "Verify number of review button in expanded mastered module with expected count : 4.");

			//Verify filled circles with star icon surrounded by circle is displayed in blue color in background for mastered module.
			objGLPLearnerCourseMaterialPage.verifyElementCssAttributeValue(
					"CourseMaterialFirstModuleStarInnerCircle",
					"background-color", ResourceConfigurations
					.getProperty("starcircleColourInReadyState"),
					"Verify that circle is in blue state.");

			//Verify module test button should not be displayed for mastered module.
			objGLPLearnerCourseMaterialPage.verifyElementNotPresent("CourseMaterialStartButtonOfModule11","Verify start button is not present in mastered module.");

			//verify start button displayed against non mastered module.
			objGLPLearnerCourseMaterialPage.verifyElementPresent("CourseMaterialStartTestButtonOfModule16", "Verify Start test button present on module 16");

			//Expand non mastered module
			objGLPLearnerCourseMaterialPage.clickOnElement("CourseMaterialExpandModule16", "Expand module 16 non mastred module");
			
			//Verify start test button in non mastered module.
			objGLPLearnerCourseMaterialPage.verifyElementPresent("CourseMaterialModule16StartTestButton", "Verify Start test button present on module 16 after expanding");


		}

		finally {
			if (unpublishData.equalsIgnoreCase("TRUE")) {
				/*
				 * objRestUtil.unpublishSubscribedCourseDatabase(
				 * learnerUserName,
				 * ResourceConfigurations.getProperty("consolePassword"));
				 */
				System.out.println("Unpublish data from couchbase DB");
			}
			webDriver.quit();
			webDriver = null;
		}
	}
}
