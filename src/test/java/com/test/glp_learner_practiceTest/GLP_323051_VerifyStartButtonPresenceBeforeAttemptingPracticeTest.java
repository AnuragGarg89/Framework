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
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_LearningObjectivePage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date April 02, 2018
 * @description: To verify if the student is now able to see the practice test
 *               page with a start button when he/she hasn't started the
 *               practice test before.
 */
public class GLP_323051_VerifyStartButtonPresenceBeforeAttemptingPracticeTest
        extends BaseClass {
    public GLP_323051_VerifyStartButtonPresenceBeforeAttemptingPracticeTest() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify if the student is now able to see the practice test page with a start button when he/she hasn't started the practice test before.")
    public void verifyStartButtonPresenceBeforeAttemptingPracticeTest() {
        startReport(getTestCaseId(),
                "To verify if the student is now able to see the practice test page with a start button when he/she hasn't started the practice test before.");
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
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);

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

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElementIfVisible(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on desired Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.navigateCourseModuleByName(
                    ResourceConfigurations.getProperty("module11Text"));

            // Click on Start button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on start button of first module");

            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of first LO");

            // verify learner navigated to practice welcome screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestWelcomeScreenStartButton",
                    "verify that student is navigated to practice test welcome page with a start button.");

            GLPLearner_LearningObjectivePage objProductApplicationLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);

            objProductApplicationLearningObjectivePage.clickOnElement(
                    "LearningObjectivemenuwrapper",
                    "Click on back arrown button");

            // logout from application
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage.verifyLogout();
            // Login to the application again with same learner
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Click on desired Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.navigateCourseModuleByName(
                    ResourceConfigurations.getProperty("module11Text"));

            // Click on continue button of first module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click on continue button of same Lo of first module");
            // Click on practice test of first LO
            objGLPLearnerPracticeTestPage.clickOnElement("PracticeQuiz",
                    "Click on practice quiz of same EO");
            // verify learner navigated to practice welcome screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "PracticeTestWelcomeScreenStartButton",
                    "verify that student is navigated to practice test welcome page with a start button when he/she hasn't started the practice test before.");

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
