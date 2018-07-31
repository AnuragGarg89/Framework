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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
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
 * @author anuj.tiwari1
 * @date Feb 19, 2018
 * @description: Verify that when the user enters shortcut for the Math Pallet
 *               expression in free response, it gets converted to the
 *               corresponding expression.
 */

public class GLP_294229_VerifyExpressionEnteredMultipleTimesMathPallet
        extends BaseClass {

    public GLP_294229_VerifyExpressionEnteredMultipleTimesMathPallet() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT }, enabled = true,
            description = "Verify that an expression can be entered multiple time via Math Pallet.")

    public void enterExpresionMultipleTimes() {
        startReport(getTestCaseId(),
                "Verify that an expression can be entered multiple time via Math Pallet.");
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

            // objProductApplicationConsoleLoginPage
            // .login("GLP_Learner_294229_llwJ", "Password11");

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

            // Navigate to free response
            objProductApplication_DiagnosticTestPage
                    .navigateToSpecificQuestionType(ResourceConfigurations
                            .getProperty("fibFreeResponse"));

            // Click inside the free response field.
            objProductApplication_DiagnosticTestPage.clickOnElement(
                    "FIBFreeResponse", "Click on the Free Response");

            // Select the expression from the math pallet.
            int enterExpressionCount = 0;
            Object count = null;
            while (enterExpressionCount < 5) {

                objProductApplication_DiagnosticTestPage.clickOnElement(
                        "SquareRootExpressionMathPallet",
                        "Click on the Square Root expression from Math Pallet");

                Actions actions = new Actions(returnDriver());
                actions.sendKeys(Keys.ARROW_RIGHT).perform();
                JavascriptExecutor js = ((JavascriptExecutor) returnDriver());
                // count = (String) js.executeScript(
                // "document.getElementsByClassName('mq-root-block')[0].childElementCount;");
                count = js.executeScript(
                        "return document.getElementsByClassName('mq-root-block')[0].getElementsByClassName('mq-sqrt-prefix').length;");

                enterExpressionCount++;
            }

            if (Integer.parseInt(count.toString()) == enterExpressionCount) {
                logResultInReport(
                        Constants.PASS
                                + ": Entered expresions multiple times which is: "
                                + enterExpressionCount,
                        "Succesfuly entered expresions multiple times.",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": Not Entered expresions multiple times which is: "
                                + enterExpressionCount,
                        "Succesfuly entered expresions multiple times.",
                        reportTestObj);
            }

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
