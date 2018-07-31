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
import com.glp.page.GLPLearner_LearningObjectivePage;
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date Nov 23, 2017
 * @description : Verify that 'Start' button in collapsed state would be
 *              displayed when the module has not been started. Also, there
 *              should be one module with a Start button at a time. - Verify
 *              that 'Continue' button in collapsed state would be displayed
 *              when any module has been started. Also, there should be one
 *              module with a Continue button at a time.
 * 
 */
public class GLP_342232_VerifyStartAndContinueState extends BaseClass {
    public GLP_342232_VerifyStartAndContinueState() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

            description = "Verify that 'Start' button in collapsed state would be displayed when the module has not been started. Also, there should be one module with a Start button at a time.Verify that 'Continue' button in collapsed state would be displayed when any module has been started. Also, there should be one module with a Continue button at a time.")
    public void verifyStartAndContinueState() {
        startReport(getTestCaseId(),
                "Verify that 'Start' button in collapsed state would be displayed when the module has not been started. Also, there should be one module with a Start button at a time.Verify that 'Continue' button in collapsed state would be displayed when any module has been started. Also, there should be one module with a Continue button at a time.");
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

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // // Attempt diagnostic test
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            // Click on Go TO Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            String moduleNonMastered = objGLPLearnerCourseMaterialPage
                    .getText("CourseMaterialLetsStartModuleText");
            String moduleNumber = moduleNonMastered.substring(18, 21);
            int numberOfLo = objGLPLearnerCourseMaterialPage.getLoCount();

            // Click on first Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMeterialExpandedModuleArrowICon",
                    "Click on expanded module arrow icon");

            // First module start button is highlighted
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify first non mastered module Start button is highlighted.");

            // Click on start button
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialStartButton",
                    "Click on module 'Start' button.");

            GLPLearner_LearningObjectivePage objGLPLearnerLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);

            // Verify student is navigated to core instruction page of that LO
            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");

            // Click on back arrow
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");

            // Verify student is navigated to course home page.
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

            // verify start button text changed to continue.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    "Verify start button text changed to continue.");

            // LogOut from the application
            objGLPLearnerCourseViewPage.verifyLogout();
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on course tile navigate to course home screen
            objGLPLearnerCourseViewPage.navigateToCourseHomePage();

            // Click on first non mastered Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMeterialExpandedModuleArrowICon",
                    "Click on expanded module arrow icon");

            // Verify the continue button is highlighted.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the First Continue button of module is highlighted as before logout.");

            // Click on continue button
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialStartButton",
                    "Click on module 'Continue' button.");

            // Verify student is navigated to core instruction page of that LO
            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");

            // Click on back arrow
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");

            // Verify student is navigated to course home page.
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");
            // Complete First Lo
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + ".1");

            objCommonUtil.refreshCurrentPage();

            // Verify continue button text changed to Review.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialReviewButton"),
                    "Verify continue button text changed to Review.");

            // Verify Second Lo start button is highlighted
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify that highlighted 'Start' button should appear which is the next one to begin and redirect to second LO of a module.");

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialFirstHighlightedStartButton",
                    "Click on First highlighted Lo 'Start' button after review button.");

            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");

            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    "Verify start button text changed to continue.");

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialFirstnonHighlightedStartButton",
                    "Click on First Nonhighlighted Lo 'Start' button.");
            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstnonHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    "Verify start button text changed to continue.");

            // Click on first Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMeterialExpandedModuleArrowICon",
                    "Click on expanded module arrow icon");

            // verify start button of module changed to continue.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    "Verify on collapsed state the 'Continue' button should appear next to module.");

            // Click on 'Continue' button from collapsed.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialFirstHighlightedStartButton",
                    "Click on 'Continue' button from collapsed.");

            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

            // Complete rest Lo
            for (int i = 2; i <= 7; i++) {
                objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                        numberOfLo + "." + i);
            }

            objCommonUtil.refreshCurrentPage();

            // objGLPLearnerCourseMaterialPage.verifyElementPresent(
            // "CourseMaterialSecondModuleStartButton",
            // "Verify Start button appear against second module. .");
            // objGLPLearnerCourseMaterialPage.clickOnElement(
            // "CourseMaterialSecondModuleStartButton",
            // "Click on 'start' button from collapsed.");
            // objGLPLearnerLearningObjectivePage.verifyElementPresent(
            // "LearningObjectivemenuwrapper",
            // "Verify student is navigated to core instruction page of that
            // LO.");
            // objGLPLearnerLearningObjectivePage.clickOnElement(
            // "LearningObjectiveBackArrow", "Click on back arrow image.");
            //
            // objGLPLearnerCourseMaterialPage.verifyElementPresent(
            // "CourseMaterialFirstLoCircle",
            // "Verify student is navigated to course home page.");
            // objGLPLearnerCourseMaterialPage.verifyText(
            // "CourseMaterialSecondModuleStartButton",
            // ResourceConfigurations
            // .getProperty("courseMaterialContinueButton"),
            // "Verify start button text changed to continue.");

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
