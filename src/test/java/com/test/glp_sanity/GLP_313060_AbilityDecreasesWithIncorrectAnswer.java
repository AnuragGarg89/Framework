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
package com.test.glp_sanity;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.CouchBaseDB;
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
 * @author mukul.sehra
 * @date Mar 8, 2018
 * @description: To Verify the ability of a learner decreases after answering
 *               the question incorrectly.
 */
public class GLP_313060_AbilityDecreasesWithIncorrectAnswer extends BaseClass {
    public GLP_313060_AbilityDecreasesWithIncorrectAnswer() {
    }

    @Test(groups = { Groups.SANITY, Groups.LEARNER, Groups.HEARTBEAT },
            enabled = true,
            description = "To Verify the ability of a learner decreases after answering the question incorrectly.")
    public void verifyAbilityFromCouchBase() throws InterruptedException {
        startReport(getTestCaseId(),
                "To Verify the ability of a learner decreases after answering the question incorrectly.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        // String for learner userName
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create learner and subscribe course
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);

            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic Test page
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Fetching the ability for a learner before attempting diagnostic
            CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);
            String abilityBeforeAttepting = cb.getAbility(learnerUserName);

            // Click IDontKnowThis button for the first question
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Fetching the ability for a learner after attempting first
            // question incorrectly
            String abilityAfterAttepting = cb.getAbility(learnerUserName);

            // Comparing abilities
            if (Double.parseDouble(abilityBeforeAttepting) != Double
                    .parseDouble(abilityAfterAttepting)) {
                logResultInReport(
                        Constants.PASS + ": The ability of a learner i.e : '"
                                + abilityBeforeAttepting
                                + "' initially, changed to '"
                                + abilityAfterAttepting
                                + "' after answering a question in Diagnostic Test",
                        "Verify that the ability of learner : '"
                                + learnerUserName
                                + "' changes when the learner answers a question in Diagnostic Test",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": The abilityBeforeAttepting : "
                                + abilityBeforeAttepting
                                + "and abilityAfterAttepting : "
                                + abilityAfterAttepting,
                        "Verify that the ability of learner : '"
                                + learnerUserName
                                + "' changes when the learner answers a question in Diagnostic Test",
                        reportTestObj);
            }

        }
        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}