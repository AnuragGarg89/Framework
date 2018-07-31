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
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_LearningObjectivePage;
import com.glp.util.GLP_Utilities;

/**
 * @author shefali.jain
 * @date June 6, 2018
 * @description: To verify that all EOs and its contents are available to the
 *               learner and learner can complete the practice test available in
 *               each EO of mentioned LO.
 * 
 */
public class GLP_358640_VerifyLearnerEOsInLO16_3 extends BaseClass {
    public GLP_358640_VerifyLearnerEOsInLO16_3() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER,
            Groups.SANITY, Groups.DEV, Groups.M1 }, enabled = true,
            description = "LO-16.3 : To verify that all EOs and its contents are available to the learner and learner can complete the practice test available in each EO")
    public void verifyLearnerEOsInL016_3() {
        startReport(getTestCaseId(),
                "LO-16.3 : To verify that all EOs and its contents are available to the learner and learner can complete the practice test available in each EO");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = configurationsXlsMap.get("INSTRUCTOR_GS_OFF");
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create learner and subscribe course with unlocked pre-assessment,
            // masteryLevel set to Zero and No guided solutions
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Login in the application as Learner
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile Present and navigate to Welcome Learner Screen
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Click the Start Pre-assessment button to navigate to the
            // Diagnostic assessment player
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objDiagnosticTest = objProductApplicationCourseHomePage
                    .navigateToDiagnosticPage();

            // Attempt the diagnostic test
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = objDiagnosticTest
                    .attemptAdaptiveDiagnosticTest(0, 0, ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            // Click on 'go to course home' link on diagnostic result page

            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");

            // Verify Module Start Button presence
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "ModuleStartButton", "Verify Module Start Button");

            // Navigate to the Learning objectives page for LO 16.3
            GLPLearner_LearningObjectivePage objLO = objGLPLearnerCourseMaterialPage
                    .navigateToCoreInstructionsInLo("16.3");

            // Traverse Each EO and attempt its Practice Test
            objLO.traverseAndVerifyContentEo();
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}