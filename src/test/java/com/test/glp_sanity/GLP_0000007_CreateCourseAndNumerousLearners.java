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
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mukul.sehra
 * @date Jun 8, 2018
 * @description: Verify course creation and multiple subscriptions
 */
public class GLP_0000007_CreateCourseAndNumerousLearners extends BaseClass {
    public GLP_0000007_CreateCourseAndNumerousLearners() {
    }

    @Test(groups = { Groups.USER_CREATION }, enabled = true,
            description = "Verify course creation and multiple subscriptions")
    public void courseAndLearnersCreation() throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify course creation and multiple subscriptions");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        String instructorName = "GLP_Instructor_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            try {
                if (masteryLevel.equalsIgnoreCase("True")) {
                    // Login to the application as an Instructor
                    GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                            reportTestObj, APP_LOG);
                    objProductApplicationConsoleLoginPage.login(instructorName,
                            ResourceConfigurations
                                    .getProperty("consolePassword"));

                    GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                            reportTestObj, APP_LOG);
                    objProductApplicationCourseViewPage.verifyElementPresent(
                            "CourseTileInstructor",
                            "Verify Instructor is logged in successfully.");

                    GLPInstructor_WelcomeInstructorPage objProductApplicationWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                            reportTestObj, APP_LOG);
                    // Navigate to the Welcome page for Instructor.
                    objProductApplicationCourseViewPage
                            .navigateToWelcomeScreenInstructor();
                    GLPInstructor_MasterySettingPage objProductApplicationMasterSettingPage = new GLPInstructor_MasterySettingPage(
                            reportTestObj, APP_LOG);
                    // Navigate to the Pre Assessment mastery level page.
                    objProductApplicationWelcomeInstructorPage
                            .navigateToPreAssessmentMastryLevel();
                    objProductApplicationMasterSettingPage.verifyElementPresent(
                            "PreAssessmentMasteryNextBtn",
                            "Verify that user is navigated to the Pre-assessment mastery lavel page");
                    objProductApplicationMasterSettingPage.clickOnElement(
                            "PreAssessmentMasteryNextBtn",
                            "Click on the Next button displayed on the Pre Assessment mastery page.");
                    GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                            reportTestObj, APP_LOG);
                    objGLPInstructorCourseViewPage.verifyLogout();
                    GLP_Utilities obj = new GLP_Utilities(reportTestObj,
                            APP_LOG);
                    String sectionID = obj.getCreatedCourseSectionId(
                            instructorName, ResourceConfigurations
                                    .getProperty("consolePassword"));
                    String glpID = obj.getGlpCourseId(instructorName,
                            instructorName, sectionID);
                    CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);
                    cb.updatePreAssessmentMasteryLevel(glpID, "0",
                            instructorName);

                }
            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured while updating couchbase DB: " + e);
            }
            // Create Learner subscribing the new course created by the
            // Instructor and unlock it via api
            for (int i = 1; i <= learnerCount; i++) {
                objRestUtil.createLearnerAndSubscribeCourse(
                        (learnerUserName + "_" + i),
                        ResourceConfigurations.getProperty("consolePassword"),
                        ResourceConfigurations
                                .getProperty("consoleUserTypeLearner"),
                        instructorName, true);
                if (diagnosisTest.equalsIgnoreCase("TRUE")) {
                    // Create user and subscribe course using corresponding
                    // APIs.
                    GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                            reportTestObj, APP_LOG);
                    objGLPConsoleLoginPage.login(learnerUserName + "_" + i,
                            ResourceConfigurations
                                    .getProperty("consolePassword"));
                    GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                            reportTestObj, APP_LOG);
                    // Verify 'Rio' course tile is present
                    objGLPLearnerCourseViewPage.verifyCourseTilePresent();
                    GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                            reportTestObj, APP_LOG);
                    objProductApplicationCourseHomePage
                            .navigateToDiagnosticPage();
                    GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                            reportTestObj, APP_LOG);
                    objGLPLearnerDiagnosticTestPage
                            .attemptAdaptiveDiagnosticTest(0, 0,
                                    ResourceConfigurations.getProperty(
                                            "submitWithoutAttempt"));
                    GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                            reportTestObj, APP_LOG);
                    objGLPInstructorCourseViewPage.verifyLogout();

                }
            }
        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}