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
 * @date may 23, 2017
 * @description :Verify that 'Continue' button in collapsed state would be
 *              displayed when the first LO is completed and rest all are in not
 *              started state.
 * 
 */
public class GLP_357815_VerifyContinueStateOnFirstLOCompletion extends BaseClass {
    public GLP_357815_VerifyContinueStateOnFirstLOCompletion() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

            description = "Verify that 'Continue' button in collapsed state would be displayed when the first LO is completed and rest all are in not started state.")
    public void verifyStartAndContinueInitialState() {
        startReport(getTestCaseId(),
                "Verify that 'Continue' button in collapsed state would be displayed when the first LO is completed and rest all are in not started state.");
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

            // Click on Go TO Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            String moduleNonMastered = objGLPLearnerCourseMaterialPage
                    .getText("CourseMaterialLetsStartModuleText");
            String moduleNumber = moduleNonMastered.substring(19, 21);

            int numberOfLo = objGLPLearnerCourseMaterialPage.getLoCount();

            GLPLearner_LearningObjectivePage objGLPLearnerLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);
            // Complete First Lo
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + ".1");

            objCommonUtil.refreshCurrentPage();

            // Click on first Module collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMeterialExpandedModuleArrowICon",
                    "Click on expanded module arrow icon");
            // verify start button text changed to continue.
            objGLPLearnerCourseMaterialPage.verifyText(
                    "CourseMaterialFirstHighlightedStartButton",
                    ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"),
                    "Verify start button text changed to continue.");
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialStartButton",
                    "Click on module 'Continue' button.");
            // Verify student is navigated to core instruction page of first LO
            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page");

            // Click on back arrow
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectiveBackArrow", "Click on back arrow image.");

            // Verify student is navigated to course home page.
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

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
