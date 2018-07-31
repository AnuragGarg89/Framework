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
 * @author Saurabh.Sharma
 * @date May 19, 2018
 * @description: Verify state of Recommended Learning Aids remains the same on
 *               closing the browser and reopening it
 */

public class GLP_339424_VerifyStateOfRecommendedLAOnBrowserClosure
        extends BaseClass {
    public GLP_339424_VerifyStateOfRecommendedLAOnBrowserClosure() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify state of Recommended Learning Aids on closing the browser and reopening")
    public void verifyStateOfRecommendedLA() {
        startReport(getTestCaseId(),
                "Verify state of Recommended Learning Aids on closing the browser and reopening");
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

            // Navigate to Recommended Learning Aids screen
            objGLPLearnerPracticeTestPage.attempPracticeTestTillHMST();
            // Verify Progress Bar on HMST Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "HMSTProgressBar",
                    "Verify Progress bar is displayed on top of Recommended LA window");
            // Verify HMST title on Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent("HMSTHeader",
                    "Verify the tile of the Recommended Learning Aids window");
            // Get the total number of parts of the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Navigate to interactive part on HMST full screen int
            int interactivePart = objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalParts);

            // Get the number of tries for the part int originalTries =
            int originalTries = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInHMSTLA(
                            interactivePart);
            // Select the answer for rendered question
            objGLPLearnerPracticeTestPage
                    .attemptInteractivePartinHMSTLA(interactivePart);

            // Verify tries get decreased
            objGLPLearnerPracticeTestPage.verifyExpectedNumberOfTriesForHMST(
                    interactivePart, originalTries, false);
            // Verify the feedback is displayed for the part
            objGLPLearnerPracticeTestPage
                    .verifyFeedbackIsDisplayedForSinglePart(interactivePart);
            // Close the browser and reopen it
            webDriver = objGLPLearnerPracticeTestPage
                    .closeReopenBrowserAndNavigateGLPConsole();
            // Login to the GLP application
            objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Verify CourseTile is Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module11Text"),
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Fetch number of tries for interactive part
            int reopenTriesCount = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInHMSTLA(
                            interactivePart);
            objGLPLearnerPracticeTestPage.verifyExpectedNumberOfTriesForHMST(
                    interactivePart, reopenTriesCount, true);
            // Verify the feedback is still displayed for the part
            objGLPLearnerPracticeTestPage
                    .verifyFeedbackIsDisplayedForSinglePart(interactivePart);
        } finally {
            webDriver.close();
            webDriver = null;
        }
    }
}
