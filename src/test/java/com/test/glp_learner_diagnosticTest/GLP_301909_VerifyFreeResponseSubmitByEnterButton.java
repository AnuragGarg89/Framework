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
package com.test.glp_learner_diagnosticTest;

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
 * @author lekh.bahl
 * @date Feb 19, 2018
 * @description: Verify that in single part free response pressing enter submits
 *               the response.
 * 
 */
public class GLP_301909_VerifyFreeResponseSubmitByEnterButton
        extends BaseClass {
    public GLP_301909_VerifyFreeResponseSubmitByEnterButton() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.FIBFREERESPONSE,
            Groups.LEARNER }, enabled = true,
            description = "Verify that in single part free response pressing enter submits the response")
    public void verifyMultiPartPageLocalization() {
        startReport(getTestCaseId(),
                "Verify that in single part free response pressing enter submits the response.");
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

            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            // Login in the application GLPConsole_LoginPage
            objGLPConsoleLoginPage = new GLPConsole_LoginPage(reportTestObj,
                    APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
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
            // Navigate to Free Response Question

            objGLPLearnerDiagnosticTestPage.navigateToSpecificQuestionType(
                    ResourceConfigurations.getProperty("fibFreeResponse"));
            String questionIdBeforeSubmit = objRestUtil.getQuestionIdOnUI();

            // Attempt Free response question by pressing Enter button
            objGLPLearnerDiagnosticTestPage
                    .attemptCurrentUIQuestionsWithoutSubmit();
            objGLPLearnerDiagnosticTestPage.hitEnterUsingKeyboard();
            String questionIdAfterSubmit = objRestUtil.getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.compareQuestionId(
                    questionIdBeforeSubmit, questionIdAfterSubmit,
                    "Verify Enter is pressed successfully and new question is rendered.");

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
