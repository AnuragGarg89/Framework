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
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PostAssessmentPage;
import com.glp.util.GLP_Utilities;

/**
 * @author shefali.jain
 * @date May 29, 2018
 * @description: Verify that Last EO star icon should be change into circle on TOC page, if student fail in post assessment
 * 
 */
public class GLP_24701_VerifyLastEOStarIconChangedToCircleAfterFailedInPostAssessment
extends BaseClass {
    public GLP_24701_VerifyLastEOStarIconChangedToCircleAfterFailedInPostAssessment() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify that Last EO star icon should be change into circle on TOC page, if student fail in post assessment")
    public void VerifyLastEOStarIconChangedToCircle() {
        startReport(getTestCaseId(),
                "Verify that Last EO star icon should be change into circle on TOC page, if student fail in post assessment");
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

            // Click on Module test Start button of 16th module
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CoreInstructionModuleTestStartButton",
                    "Click on Start test link to start post assessment test");
            
            GLPLearner_PostAssessmentPage objGLPLearnerPostAssessmentPage = new GLPLearner_PostAssessmentPage(
                    reportTestObj, APP_LOG);

            // Click on start button on module test welcome screen
            objGLPLearnerPostAssessmentPage.clickOnElement(
                    "ModuleTestWelcomeScreenStartButton",
                    "Click on start button on module test welcome screen");

            //Attempt post assessment test
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                    .getProperty("diagnosticSubmitButton"));

            // Click on restart link  for failed LO
            objGLPLearnerCourseMaterialPage.clickOnElement("CoreInstructionReStartButton", "Click on restart button for failed LO");

            //Verifying first EO is expanded on tapping restart button for failed LO.
            objGLPLearnerCourseMaterialPage.verifyFirstEOIsExpanded();
            
            //Tap on last EO
            objGLPLearnerCourseMaterialPage.clickOnLastEO("Click on last LO");
            
            //Verify Circle icon is coming for last EO.
           objGLPLearnerCourseMaterialPage.verifyElementPresent("CoreInstructionCircularIconOn16.1.3", "Verify circle icon is there for last LO");
            

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

