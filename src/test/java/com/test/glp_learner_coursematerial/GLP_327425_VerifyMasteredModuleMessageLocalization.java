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
package com.test.glp_learner_coursematerial;

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
 * @author pallavi.tyagi
 * @date May 28, 2018
 * @description :Verify mastered module localization text on course home header
 * 
 */
public class GLP_327425_VerifyMasteredModuleMessageLocalization
        extends BaseClass {
    public GLP_327425_VerifyMasteredModuleMessageLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

            description = "Verify mastered module localization text on course home header")
    public void verifyMessageDisplayeOnTopAccordingToModuleStatus() {
        startReport(getTestCaseId(),
                "Verify mastered module localization text on course home header");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            // Attempt diagnostic test
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            // Click on Go TO Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");
            String moduleNonMastered = objGLPLearnerCourseMaterialPage
                    .getText("CourseMaterialLetsStartModuleText");
            String moduleNumber = moduleNonMastered.substring(19, 21);
            // Verify course home header text
            objGLPLearnerCourseMaterialPage.verifyText("CourseHomeHeader",
                    ResourceConfigurations.getProperty("CourseHomeHeading"),
                    "Verify 'You're on your way!' displayed in course home header in"
                            + ResourceConfigurations.getProperty("language"));

            // Verify the Let's start module 11 text is displayed.
            objGLPLearnerCourseMaterialPage.verifyText("CourseHomeBanner",
                    ResourceConfigurations.getProperty("letsStartModuleText"),
                    "Verify Let's Start module text is diaplyed in "
                            + ResourceConfigurations.getProperty("language"));

            // Click on first non mastered LO start button
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialStartButton",
                    "Click on non mastered LO 'Start' button.");

            GLPLearner_LearningObjectivePage objGLPLearnerLearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);

            objGLPLearnerLearningObjectivePage.verifyElementPresent(
                    "LearningObjectivemenuwrapper",
                    "Verify student is navigated to core instruction page of that LO.");
            objGLPLearnerLearningObjectivePage.clickOnElement(
                    "LearningObjectivemenuwrapper",
                    "Click on back arrow image.");
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "CourseMaterialFirstLoCircle",
                    "Verify student is navigated to course home page.");

            // Verify course home header text in LO in progress state
            objGLPLearnerCourseMaterialPage.verifyText("CourseHomeHeader",
                    ResourceConfigurations.getProperty("welcomeBackText") + " "
                            + learnerUserName + ".",
                    "Verify course home header text with learner name is displayed in"
                            + ResourceConfigurations.getProperty("language"));

            // verify Continue working from where you left off text in
            // respective language
            objGLPLearnerCourseMaterialPage.verifyText("CourseHomeBanner",
                    ResourceConfigurations.getProperty("welcomeBackText2"),
                    "Verify 'Continue working from where you left off.' text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // Complete All the LO of chapter 16
            objRestUtil.attemptCompleteChapterViaAPI(learnerUserName,
                    moduleNumber);

            objCommonUtil.refreshCurrentPage();

            // Verify the message You're ready for the Module 11 test! We've
            // notified your instructor to unlock it.
            objGLPLearnerCourseMaterialPage.verifyText("CourseHomeHeader",
                    ResourceConfigurations.getProperty(
                            "courseMaterialModuleTestNotificationMessage"),
                    "Verify the message 'You're ready for the Module 16 test! We've notified your instructor to unlock it' is displayed after completing all Lo.");

        }

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