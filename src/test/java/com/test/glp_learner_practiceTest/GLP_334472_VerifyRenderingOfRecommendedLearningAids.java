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
package com.test.glp_learner_practiceTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author yogesh.choudhary
 * @date May 8, 2018
 * @description: To verify rendering of Recommended Learning aids along with
 *               View an Example is as expected
 */

public class GLP_334472_VerifyRenderingOfRecommendedLearningAids
        extends BaseClass {
    public GLP_334472_VerifyRenderingOfRecommendedLearningAids() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify rendering of Recommended Learning aids along with View an Example is as expected")
    public void verifyLearnerCanAccessViewAnExampleLA() {
        startReport(getTestCaseId(),
                "To verify rendering of Recommended Learning aids along with View an Example is as expected");
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
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome LearnerScreen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module11Text"),
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");
            // Navigate to Recommended LA screen
            objGLPLearnerPracticeTestPage.attempPracticeTestTillHMST();

            // Verify HMST Progress Bar on Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "HMSTProgressBar",
                    "Verify HMST Progress bar on Full screen ");

            // Verify HMST title on Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent("HMSTHeader",
                    "Verify HMST title on Full screen ");

            // verify Help me solve this Dropdown
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "HelpMeAnswerThisDropdown",
                    "Verify HMST Dropdown on Full screen ");

            // Click Help me solve this Dropdown
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "HelpMeAnswerThisDropdown",
                    "Click on HMST Dropdown on Full screen ");

            // Verify View an example
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "ViewAnExOnHMSTFullScreen",
                    "Verify View an Example on Full screen ");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }

}