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
package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_MasterySettingPage;
import com.glp.page.GLPInstructor_WelcomeInstructorPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author deepak.bithar
 * @date March 22, 2018
 * @description: Verify 'Welcome Back' message appears when learner subscribe
 *               course
 *
 */
public class GLP_316529_LockUnlockPopUpMessageVerification extends BaseClass {
    public GLP_316529_LockUnlockPopUpMessageVerification() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify Welcome back message should appear when learner has subscribe course")
    public void verifyLockUnlockDiagnostic() throws InterruptedException {
        startReport(getTestCaseId(),
                "Verify Welcome back message should appear when learner has subscribe course");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create Instructor with new course
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            // Create Learner subscribing the new course created by the
            // Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, false);
            // Login to the application as a Instructor
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_WelcomeInstructorPage objGLPInstructorWelcomeInstructorPage = new GLPInstructor_WelcomeInstructorPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            GLPInstructor_MasterySettingPage objGLPInstructorMasterySettingPage = new GLPInstructor_MasterySettingPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Navigate to the Mastery Settings page
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");
            objGLPInstructorCourseViewPage.navigateToWelcomeScreenInstructor();
            objGLPInstructorWelcomeInstructorPage
                    .navigateToPreAssessmentMastryLevel();
            // Navigate to the Performance dashboard page
            objGLPInstructorMasterySettingPage.navigateToInstructorDashboard();
            objGLPInstructorDashboardPage.switchToPerformaceTab();
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardUnlockPopup",
                    "Verify Welcome back message successfully.");
            objGLPInstructorDashboardPage
                    .verifyAllTextInInstructorPopup(instructorName);
            objGLPInstructorDashboardPage.navigateToUnlockPreAssessmentPopUp();
            objGLPInstructorDashboardPage
                    .verifyPreAssessmentPopUp(learnerUserName, true);
            objGLPInstructorDashboardPage.navigateToLockPreAssessmentPopUp();
            objGLPInstructorDashboardPage
                    .verifyPreAssessmentPopUp(learnerUserName, false);

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }

    }
}