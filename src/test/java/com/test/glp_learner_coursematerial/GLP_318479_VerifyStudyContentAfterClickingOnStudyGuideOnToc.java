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
 * @author yogesh.choudhary
 * @date Dec 18, 2017
 * @description: Verify that on On clicking Study guide (read) content in TOC ,
 *               respective study guide content should be displayed on right
 *               side of page
 * 
 */
public class GLP_318479_VerifyStudyContentAfterClickingOnStudyGuideOnToc
        extends BaseClass {
    public GLP_318479_VerifyStudyContentAfterClickingOnStudyGuideOnToc() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER,
            Groups.TOC }, enabled = true,
            description = "Verify that on On clicking Study guide (read) content in TOC , respective study guide content should be displayed on right side of page")
    public void verifyStudyContentAfterClickingOnStudyGuideOnToc() {
        startReport(getTestCaseId(),
                "Verify that on On clicking Study guide (read) content in TOC , respective study guide content should be displayed on right side of page");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId()
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

            // Navigate to Diagnostic test and attempt it
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Skip all Diagnostic Test Question
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = objGLPLearnerDiagnosticTestPage
                    .attemptAdaptiveDiagnosticTest(0, 0, ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();

            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Open second non-mastered module
            objGLPLearnerCourseMaterialPage.navigateCourseModuleByIndex(
                    "CourseMaterialModuleTitleButton", 6,
                    "Verify second unmastered module is clicked.");
            // Verify Module Start Button
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialLOStartButton",
                    "Click Drawer Icon on Core Instruction page");

            // Verify Module Start Button
            objGLPLearnerCourseMaterialPage.verifyElementPresent("DrawerIcon",
                    "Verify Drawer Icon on Core Instruction page");

            // Click the study guide eo content
            GLPLearner_LearningObjectivePage objLearningObjective = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);
            objLearningObjective.clickElementWithText(
                    "CoreInstructionEoListItem", "Study Guide");

            // Verify study guide content is available
            objGLPLearnerCourseMaterialPage.verifyText("ContentHeading",
                    "Study Guide", "Verify content is available.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
