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
package com.test.glp_learner_doubts;

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
 * @date Dec 09, 2017
 * @description: To verify "Submit" button for each part should work
 *               independently from other parts for a multipart question
 * 
 */
public class GLP_270716_VerifySubmitButtonWorkIndependently extends BaseClass {
    public GLP_270716_VerifySubmitButtonWorkIndependently() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = false,
            description = "To verify \"Submit\" button for each part should work independently from other parts for a multipart question")
    public void verifySubmitButtonWorkIndependently() {
        startReport(getTestCaseId(),
                "To verify \"Submit\" button for each part should work independently from other parts for a multipart question");
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
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(learnerUserName,
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

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 3,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
            // Navigate to Multipart Question
            objGLPLearnerDiagnosticTestPage.navigateToSpecificQuestionType(
                    ResourceConfigurations.getProperty("multipart"));

            String currentQuestionId = objRestUtil.getQuestionIdOnUI();

            // Attempt first question on multipart
            objGLPLearnerDiagnosticTestPage
                    .attemptCurrentUIQuestionsWithoutSubmit();

            // Verify on clicking the Submit button of one part will not affet
            // other parts
            objGLPLearnerDiagnosticTestPage
                    .verifySubmitButtonDiasableInMultipart();

            String nextQuestionId = objRestUtil.getQuestionIdOnUI();
            objGLPLearnerDiagnosticTestPage.compareQuestionId(currentQuestionId,
                    nextQuestionId,
                    "Verify learner clicks now on the left submit buttons to land on to the next question.");

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
