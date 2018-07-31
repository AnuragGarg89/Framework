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
package com.test.glp_endtoend;

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
 * @date Dec 12, 2017
 * @description: Test to Verify that learner is navigate to diagnostic test
 *               summary screen after attempting all question in diagnostic
 *               test.
 */
public class GLP_313057_VerifyDiagnosticTestResume extends BaseClass {
    public GLP_313057_VerifyDiagnosticTestResume() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify Resume diagnostic where any learner has left the test in between.")
    public void verifyCompleteDiagnosticTestAttempted() {
        startReport(getTestCaseId(),
                "Verify Resume diagnostic where any learner has left the test in between.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),

                    true);
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify 'Rio' course tile is present
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            String firstQuestionId = objRestUtil.getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 2,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            String secondQuestionId = objRestUtil.getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.compareQuestionId(firstQuestionId,
                    secondQuestionId,
                    "Verify diffrent question is rendered on UI after clicking Submit.");
            objGLPLearnerDiagnosticTestPage.refreshPage();
            String SecondQuestionIdAfterRefresh = objRestUtil
                    .getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.compareQuestionId(secondQuestionId,
                    SecondQuestionIdAfterRefresh,
                    "Verify diffrent question is rendered on UI after Refreshing the page.");
            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");
            objGLPLearnerCourseHomePage.verifyText("CourseHomeStartYourPathBtn",
                    ResourceConfigurations.getProperty("continue"),
                    "Verify User is redirected to continue diagnostic screen to continue test.");
            objGLPLearnerCourseHomePage.navigateToCourseView();
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify User is navigated back to course view page after clicking Pearson Logo.");
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            String thirdQuestionIdAfterComingBackFromConsole = objRestUtil
                    .getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.compareQuestionId(
                    SecondQuestionIdAfterRefresh,
                    thirdQuestionIdAfterComingBackFromConsole,
                    "Verify diffrent question is rendered on UI after rsuming back again post leaving diagnostic test in middle.");
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(2, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
            objGLPLearnerCourseViewPage.verifyLogout();
        }
        // Delete User via API
        finally {

            webDriver.quit();
            webDriver = null;
        }

    }
}
