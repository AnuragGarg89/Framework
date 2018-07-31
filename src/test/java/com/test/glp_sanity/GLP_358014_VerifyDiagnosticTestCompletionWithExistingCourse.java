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
public class GLP_358014_VerifyDiagnosticTestCompletionWithExistingCourse
        extends BaseClass {
    public GLP_358014_VerifyDiagnosticTestCompletionWithExistingCourse() {
    }

    @Test(groups = { Groups.SANITY, Groups.REGRESSION, Groups.LEARNER,
            Groups.HEARTBEAT, Groups.DEV }, enabled = true,
            description = "Verify that learner is navigate to diagnostic test completion screen after attempting all question in diagnostic test.")
    public void verifyCompleteDiagnosticTestAttempted() {
        startReport(getTestCaseId(),
                "Verify that learner is navigate to diagnostic test completion screen after attempting all question in diagnostic test.");
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
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify 'Rio' course tile is present
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify learner is successfully navigated to welcome screen.");

            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage
                    .attemptAdaptiveDiagnosticTestWithoutErrorHandling(0, 0,
                            ResourceConfigurations
                                    .getProperty("submitWithoutAttempt"));
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
