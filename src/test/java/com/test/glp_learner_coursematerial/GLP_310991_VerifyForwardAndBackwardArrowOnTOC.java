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
 * 
 * @author yogesh.choudhary
 * @date Feb 18, 2018
 * @description :
 * 
 */

public class GLP_310991_VerifyForwardAndBackwardArrowOnTOC extends BaseClass {
    public GLP_310991_VerifyForwardAndBackwardArrowOnTOC() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER,
            Groups.TOC }, enabled = true,
            description = "To verify that 'Forward' and 'Back' arrows should be displayed at the bottom of the module content page.")
    public void verifyForwardAndBackwardArrowOnTOC() {
        startReport(getTestCaseId(),
                "To verify that 'Forward' and 'Back' arrows should be displayed at the bottom of the module content page.");

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

            // Automate the remaining steps of test case
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

            // Verify Back Arrow not present on first EO
            objGLPLearnerCourseMaterialPage.verifyElementNotPresent(
                    "EODetailPageBackArrow",
                    "Verify that Back Arrow is not present on EO Detail Page for first EO.");

            // Verify Forward Arrow present on first EO
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "EODetailPageNextArrow",
                    "Verify that Forward Arrow is not present on EO Detail Page for first EO.");

            // Click on Second EO
            objGLPLearnerCourseMaterialPage.clickOnElement("SubHeadingListTwo",
                    "Click on Second SubHeading for first EO.");

            // Verify Back Arrow present on second EO
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "EODetailPageBackArrow",
                    "Verify that Back Arrow is present on EO Detail Page for second EO.");

            // Verify Forward Arrow present on second EO
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "EODetailPageNextArrow",
                    "Verify that Forward Arrow is present on EO Detail Page for second EO.");

            // Click on Last EO of current LO
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CoreInstructionLastEOOfActiveLO",
                    "Click on Last EO of current LO.");

            // Verify Back Arrow present on last EO
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "EODetailPageBackArrow",
                    "Verify that Back Arrow is present on EO Detail Page for last EO.");

            // Verify Forward Arrow not present on last EO
            objGLPLearnerCourseMaterialPage.verifyElementNotPresent(
                    "EODetailPageNextArrow",
                    "Verify that Forward Arrow is not present on EO Detail Page for last EO.");

        }

        finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}