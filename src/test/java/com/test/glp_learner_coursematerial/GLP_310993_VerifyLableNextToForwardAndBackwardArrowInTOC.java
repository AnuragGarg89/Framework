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
 * @author ratnesh.singh
 * @date Jun 11, 2018
 * @description : Verify labels getting displayed next to forward and backward
 *              arrows. Label next to forward arrow should be same as next EO
 *              and label next to backward arrow should be same as previous EO
 */

public class GLP_310993_VerifyLableNextToForwardAndBackwardArrowInTOC
        extends BaseClass {

    public GLP_310993_VerifyLableNextToForwardAndBackwardArrowInTOC() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER,
            Groups.TOC }, enabled = true,
            description = "To verify lable getting displayed next to 'Forward' and 'Back' arrows in TOC.")
    public void verifyForwardAndBackwardArrowOnTOC() {
        startReport(getTestCaseId(),
                "To verify that 'Forward' and 'Back' arrows should be displayed at the bottom of the module content pageTo verify lable getting displayed next to 'Forward' and 'Back' arrows in TOC.");

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

            // learnerUserName = "GLP_Learner_350594_hdTI";

            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // objGLPLearnerCourseViewPage.clickOnElement(
            // "CourseViewCourseCardImage",
            // "Verify that Course Card is Clicked.");

            // Verify CourseTile Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            // Verify that start pre-assessment button is getting present.
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Skip all Diagnostic Test Question
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = objGLPLearnerDiagnosticTestPage
                    .attemptAdaptiveDiagnosticTest(0, 0, ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            // Verify that pre-assessment gets completed successfully
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();

            // Click on Go to Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Click on Module 11 collapsed arrow.
            objGLPLearnerCourseMaterialPage.clickOnElementContainsInnerText(
                    "CourseMaterialModuleTitleButton",
                    ResourceConfigurations.getProperty("module11Text"));

            // Click on 1st LO Start button under module 11
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedLOStartButtons",
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"));

            // Verify Drawer Icon present on Core Instructions Page
            objGLPLearnerCourseMaterialPage.verifyElementPresent("DrawerIcon",
                    "Verify Drawer Icon on Core Instruction page");

            // Get text of second EO link under EO list
            String SecondEOLinkText = objGLPLearnerCourseMaterialPage
                    .getText("SubHeadingListTwo");

            // Verify that text of label displayed next to forward arrow on
            // first EO detail page contains second EO link text
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "CoreInstructionForwardArrowLableText",
                    SecondEOLinkText.trim(),
                    "Verify that text of label displayed next to forward arrow on first EO detail page contains second EO link text.");

            // Click on forward/next arrow under first EO detail page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "EODetailPageNextArrow",
                    "Click on next arrow under first EO details page.");

            // Get text of first EO link under EO list
            String FirstEOLinkText = objGLPLearnerCourseMaterialPage
                    .getText("SubHeadingListOne");

            // Verify that text of label displayed next to backward arrow on
            // second EO detail page contains first EO link text
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "CoreInstructionBackwardArrowLableText",
                    FirstEOLinkText.trim(),
                    "Verify that text of label displayed next to backward arrow on second EO detail page contains first EO link text.");

            // Get text of third EO link under EO list
            String ThirdEOLinkText = objGLPLearnerCourseMaterialPage
                    .getText("SubHeadingListThree");

            // Verify that text of label displayed next to forward arrow on
            // second EO detail page contains third EO link text
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "CoreInstructionForwardArrowLableText",
                    ThirdEOLinkText.trim(),
                    "Verify that text of label displayed next to forward arrow on second EO detail page contains third EO link text.");

        }

        finally {

            webDriver.quit();
            webDriver = null;
        }

    }

}
