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
package com.test.glp_learner_coursehome;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nitish.jaiswal
 * @date Nov 22, 2017
 * @description: Verify that all the elements and components of welcome page is
 *               rendered correctly as per the VD and properly aligned for the
 *               learner login first time.
 */
public class GLP_270318_VerifyFirstTimeLoginAndWelcomePageVD extends BaseClass {
    public GLP_270318_VerifyFirstTimeLoginAndWelcomePageVD() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify that all the elements and components of welcome page is rendered correctly as per the VD and properly aligned for the learner login first time to appear in Pre-assessment.")
    public void verifyFirstTimeLoginAndWelcomePageVD() {
        startReport(getTestCaseId(),
                "Verify that all the elements and components of welcome page is rendered correctly as per the VD and properly aligned for the learner login first time.");
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
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);

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

            // Verify Welcome Screen as per VD
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeText",
                    ResourceConfigurations.getProperty("welcomeText"),
                    "Verify 'Welcome,' Text is present.");
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeLetTryText",
                    ResourceConfigurations.getProperty("letsTryText"),
                    "Verify 'Let's try some practice problems to get you on a path to math you're ready to learn.' Text is present.");
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeStartPreAssessmentButton",
                    ResourceConfigurations
                            .getProperty("startPreAssessmentTestButton"),
                    "Verify 'Start Pre-assessment' Button is present.");

            // Verify welcome back screen with Resume your path button is
            // displayed when user has navigated to diagnostic page but have not
            // attempted any question
            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");

            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            objGLPLearnerCourseViewPage.verifyLogout();
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify 'Rio' course tile is present.");

            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeBackText",
                    ResourceConfigurations.getProperty("welcomeBackText") + "  "
                            + learnerUserName + ".",
                    "Verify 'Welcome back, " + learnerUserName
                            + "' Text is present.");
            objProductApplicationCourseHomePage.verifyElementPresent(
                    "CourseHomeProgressBar",
                    "Verify 'Progress Bar' is present.");
            // Verify 'Continue' Button
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeContinueDiagnosticButton",
                    ResourceConfigurations.getProperty("continue"),
                    "Verify 'Continue' Button is present.");

            // Verify welcome back screen with Resume your path button is
            // displayed when user has navigated to diagnostic page but have
            // attempted few questions
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            objProductApplication_DiagnosticTestPage.attemptUserChoiceQuestion(
                    objProductApplication_DiagnosticTestPage
                            .returnTypeOfQuestion(),
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on cross icon on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");

            // Click on Leave button on diagnostic page
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            objGLPLearnerCourseViewPage.verifyLogout();
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
