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
 * @description : Verify that once a student selects 'Start' button and starts
 *              working on any LO in a module, then Start should change to
 *              'Continue' button and it should be displayed next to LOs name on
 *              right side against all LOs that are in progress. Also, verify
 *              that on clicking the 'Continue button', the student should land
 *              on Core Instruction page of that LO. After clicking on the
 *              'Continue' button for the LOs already in progress and completing
 *              it, the state of the button should change to 'Review'"
 * 
 */
public class GLP_311718_VerifyStartContinueReviewStatusOfLo extends BaseClass {
    public GLP_311718_VerifyStartContinueReviewStatusOfLo() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

            description = "Verify that once a student selects Start button and starts working on any LO in a module, then Start should change to Continue button and it should be displayed next to LOs name on right side against all LOs that are in progress.Also, verify that on clicking the Continue button the student should land on Core Instruction page of that LO.After clicking on the Continue button for the LOs already in progress and completing it, the state of the button should change to Review")
    public void verifyStartContinueReviewStatusOfLo() {
        startReport(getTestCaseId(),
                "Verify that once a student selects Start button and starts working on any LO in a module, then Start should change to Continue button and it should be displayed next to LOs name on right side against all LOs that are in progress.Also, verify that on clicking the Continue button the student should land on Core Instruction page of that LO.After clicking on the Continue button for the LOs already in progress and completing it, the state of the button should change to Review.");
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

            // Attempt diagnostic test
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

            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMatrerialmoduleExpendedLo",
                    "Verify All the Lo(s) of the expanded module is displayed");

            objGLPLearnerCourseMaterialPage
                    .verifyStateOftButtonNextToEachLo("Start");

            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the First Lo Start button is highlighted.");
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialStartButton",
                    "Click on First Lo 'Start' button.");

            GLPLearner_LearningObjectivePage objGLPLearnerLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);

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

            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the continue button is highlighted.");

            // Complete First Lo
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + ".1");

            objCommonUtil.refreshCurrentPage();

            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstReviewButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialReviewButton"),
                    "Verify continue button text changed to Review.");

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialFirstReviewButton",
                    "Click on First Lo 'Review' button.");
            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

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

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialSecondNonHighlightedStartButton",
                    "Click on second nonhighlighted Lo 'Start' button.");
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

            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the First Continue is highlighted.");
            // Click on Pearson Logo
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToCourseView();
            // Click on course tile navigate to course home screen
            objGLPLearnerCourseViewPage.navigateToCourseHomePage();
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the First Continue button of module is highlighted as before navigating away from the page.");
            // LogOut from the application
            objGLPLearnerCourseViewPage.verifyLogout();
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Click on course tile navigate to course home screen
            objGLPLearnerCourseViewPage.navigateToCourseHomePage();
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialFirstHighlightedStartButton", "class",
                    "pe-btn__primary--btn_large",
                    "Verify the First Continue button of module is highlighted as before logout.");
            // complete Lo11.2
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName, "11.2");
            objCommonUtil.refreshCurrentPage();
            // complete Lo11.3
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName, "11.3");
            objCommonUtil.refreshCurrentPage();
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstnonHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialReviewButton"),
                    "Verify continue button text changed to Review.");
            // complete Lo11.4
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName, "11.4");
            objCommonUtil.refreshCurrentPage();
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialSecondNonHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialReviewButton"),
                    "Verify continue button text changed to Review.");
            // LogOut from the application
            objGLPLearnerCourseViewPage.verifyLogout();
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            // Click on course tile navigate to course home screen
            objGLPLearnerCourseViewPage.navigateToCourseHomePage();
            objGLPLearnerCourseMaterialPage
                    .verifyStateOftButtonNextToEachLo("Review");

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
