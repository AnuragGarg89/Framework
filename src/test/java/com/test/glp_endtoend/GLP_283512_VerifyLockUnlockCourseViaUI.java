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
import com.autofusion.util.DiagnosticAttemptThroughAPI;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mukul.sehra
 * @date Feb 08, 2018
 * @description: Verify Lock/Unlock feature implementation for the Diagnostic
 *               Test via UI
 */
public class GLP_283512_VerifyLockUnlockCourseViaUI extends BaseClass {
    public GLP_283512_VerifyLockUnlockCourseViaUI() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.LEARNER },
            enabled = true,
            description = "Verify Lock/Unlock feature implementation for the Diagnostic Test via UI")
    public void verifyLockUnlockDiagnosticViaUI() throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify Lock/Unlock feature implementation for the Diagnostic Test via UI");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create Learner subscribing the new course created by the
            // Instructor and unlock it via api

            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"), true);
            // Login to the application as a learner
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            // Verify that the 'Start your path' button is available on the
            // welcome screen
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseHomePage.verifyElementPresent(
                    "CourseHomeStartYourPathBtn",
                    "Verify that the 'Start Assessment' button is available on the welcome screen");
            DiagnosticAttemptThroughAPI attemptDiagnostic = new DiagnosticAttemptThroughAPI(
                    reportTestObj, APP_LOG);
            attemptDiagnostic.attemptDiagnosticTestThroughAPI(learnerUserName,
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));
            // Logout learner from application
            objGLPLearnerCourseViewPage.verifyLogout();

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}