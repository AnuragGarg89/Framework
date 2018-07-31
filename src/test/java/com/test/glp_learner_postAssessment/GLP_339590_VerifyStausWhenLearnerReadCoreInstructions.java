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
package com.test.glp_learner_postAssessment;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date May 16, 2018
 * @description: Verify status on student list page for a learner when starts
 *               reading core instructions
 * 
 */
public class GLP_339590_VerifyStausWhenLearnerReadCoreInstructions
        extends BaseClass {
    public GLP_339590_VerifyStausWhenLearnerReadCoreInstructions() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify status on student list page for a learner when starts reading core instructions")
    public void verifyStatusWhenLearnerReadCoreInstructions() {
        startReport(getTestCaseId(),
                "Verify status on student list page for a learner when starts reading core instructions");
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
            // Verify CourseTile Present and navigate to Welcome Learner Screen
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
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");
            objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
                    ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            // Verify 'Start' button is displaying in front of 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    ResourceConfigurations.getProperty("module11StartButton"),
                    "Verify 'Start' button is displaying infornt of 'Module 16'.");

            // Verify 'Review' button is displaying in front of 'Module 16' for
            // last LO
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    ResourceConfigurations.getProperty("moduleLOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'Module 16' for last LO.");

            // Click on 'Review' button in front of 'seventh/last' LO of 'Module
            // 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    "Click on 'Review' button in front of 'seventh/last' LO of 'Module 16'.");

            GLPLearner_PracticeTestPage objProductApplicationPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on Practice Quiz link
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PracticeQuizLink", "Click on Practice Quiz link");

            // Click on 'Start' button of practice quiz button
            objProductApplicationPracticeTestPage.clickOnElement(
                    "StartButtonPracticeQuiz",
                    "Click on 'Start' button of practice quiz");

            // Click on 'close icon' to close practice test
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PracticeTestCloseButton",
                    "Click on 'close icon' to close practice test");

            // Click on 'Leave button' to close practice test
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PracticeTestPopUpLeaveButton",
                    "Click on 'close icon' to close practice test");

            // Click on 'back arrow' icon to go back to practice test page
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on 'back arrow' icon to go back to practice test page");

            // Logout of the application
            objGLPLearnerCourseViewPage.verifyLogout();

            // Login with instructor
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Click on all modules button
            objGLPInstructorDashboardPage.clickOnElement("ContentDropDown",
                    "Expand the menu.");
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardAllModules",
                    "Click on 'All Modules' option.");

            // Type 'learner' name in filter search box
            objGLPInstructorDashboardPage.enterInputData("SearchStudentFilter",
                    learnerUserName,
                    "Type 'learner' name in filter search box");

            // Press Enter key after typing learner name in filter search box
            objGLPInstructorDashboardPage.pressEnterKey();

            // Verify status 'Ready' for learner
            objGLPInstructorDashboardPage.verifyTextContains(
                    "InstructorDashBoardPerformanceStatusReady",
                    ResourceConfigurations.getProperty("statusReady"),
                    "Verify status 'Ready' for learner on student list page.");

            // Verify 'Current Content' display value as 'Module 16'
            objGLPInstructorDashboardPage.verifyTextContains(
                    "PerforManceDashBoardStudentCurrentContentColumn",
                    ResourceConfigurations
                            .getProperty("currentContentModule16"),
                    "Verify 'Module 16' is displaying in 'Current content' column.");

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
