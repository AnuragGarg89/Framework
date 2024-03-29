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
import com.autofusion.constants.Constants;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author rashmi.z
 * @date April 21, 2018
 * @description: Verify learner can consume the FIB for single FIB type
 *               questions
 * 
 */
public class GLP_335537_VerifyFIBTypeQuestionForCorrectIncorrectAnswer
        extends BaseClass {
    public GLP_335537_VerifyFIBTypeQuestionForCorrectIncorrectAnswer() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.FIB, Groups.LEARNER },
            enabled = false,
            description = "To verify learner can consume the FIB for single FIB type questions")
    public void verifyScreenGreyedOutWhenCoachmarkIsPresent() {
        startReport(getTestCaseId(),
                "To verify learner can consume the FIB for single FIB type questions");
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
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            // Navigate to Single FIB type question
            objProductApplication_DiagnosticTestPage.navigateToActivityType(
                    ResourceConfigurations.getProperty("fibDropDown"));
            logResultInReport(
                    Constants.PASS + ": Single FIB type question is seen.",
                    "Verify Single FIB type question .", this.reportTestObj);
            // ---------------------------------------------------------------------------------------------------------------------------------
            // Verify the question rendering in card view one
            objProductApplication_DiagnosticTestPage
                    .verifyQuestionAnswerInCardView(0);
            logResultInReport(
                    Constants.PASS
                            + ": Single FIB type question/answer is seen.",
                    "Verify Single FIB type question/answer .",
                    this.reportTestObj);
            // ---------------------------------------------------------------------------------------------------------------------------------
            // Validate Single FIB functionality
            // objProductApplication_DiagnosticTestPage
            // .validateSingleMultipleFIBFunctionality();

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
