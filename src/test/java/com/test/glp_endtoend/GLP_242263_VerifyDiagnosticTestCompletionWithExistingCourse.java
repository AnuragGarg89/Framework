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
import com.glp.page.GLPLearner_CourseMaterialPage;
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
public class GLP_242263_VerifyDiagnosticTestCompletionWithExistingCourse
        extends BaseClass {
    public GLP_242263_VerifyDiagnosticTestCompletionWithExistingCourse() {
    }

    @Test(groups = {Groups.REGRESSION, Groups.LEARNER },
            enabled = true,
            description = "Verify that learner is navigate to diagnostic test summary screen after attempting (by skipping) all question in diagnostic test.")
    public void verifyCompleteDiagnosticTestAttempted() {
        startReport(getTestCaseId(),
                "Verify that newly created learner is navigate to diagnostic test summary screen after attempting (by skipping) all question in diagnostic test.");
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
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("diagnosticIdontKnowThisButton"));
            objGLPLearnerDiagnosticTestPage.verifyDiagnosticTestCompleted();
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialResult",
                    "Verify Diagnostic test completed and Pearson logo is visible");
            objGLPLearnerCourseMaterialPage.navigateToCourseViewPage();
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify User is navigated back to course view page after clicking Pearson Logo.");
            objGLPLearnerCourseViewPage.verifyLogout();
        }
        // Delete User via API
        finally {
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
