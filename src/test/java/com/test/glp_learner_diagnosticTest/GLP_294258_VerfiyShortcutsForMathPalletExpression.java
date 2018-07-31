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
 * @author anuj.tiwari1
 * @date Feb 19, 2018
 * @description: Verify that when the user enters shortcut for the Math Pallet
 *               expression in free response, it gets converted to the
 *               corresponding expression.
 */

public class GLP_294258_VerfiyShortcutsForMathPalletExpression
        extends BaseClass {

    public GLP_294258_VerfiyShortcutsForMathPalletExpression() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER },
            enabled = true,
            description = "Shortcut for mathmatical expression should convert into the corresponding expression in the free response.")

    public void mathPalleteShortcutsVerification() {
        startReport(getTestCaseId(),
                "Shortcut for mathmatical expression should convert into the corresponding expression in the free response.");
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

            // // Login in the application
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

            // Navigate to msqsa
            objProductApplication_DiagnosticTestPage
                    .navigateToSpecificQuestionType(ResourceConfigurations
                            .getProperty("fibFreeResponse"));

            // Click inside the free response field.
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "FIBFreeResponse", "Click on the Free Response");

            String[] verificationLocator = { "DiagnosticMathPalletNotEqualTo",
                    "DiagnosticMathPalletPi" };/*
                                                * ,
                                                * "DiagnosticMathPalletNthRoot",
                                                * "DiagnosticMathPalletSquareRoot"
                                                * };
                                                */
            String[] shourtcutValue = { "\\neq",
                    "\\pi" };/* , "\\nrt", "\\sqrt" }; */
            objProductApplication_DiagnosticTestPage
                    .enterMultipleInputDataAndVerify(
                            "DiagnosticMathPalletInput",
                            "Enter the shourtcuts in the free response field",
                            verificationLocator, shourtcutValue);

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
