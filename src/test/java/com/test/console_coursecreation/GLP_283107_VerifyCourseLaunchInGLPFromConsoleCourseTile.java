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

package com.test.console_coursecreation;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseViewPage;

/**
 * @author nisha.pathria
 * @date Dec 16, 2017
 * @description : Verify that course is launched in GLP when instructor clicks
 *              the course tile from console.
 * 
 */
public class GLP_283107_VerifyCourseLaunchInGLPFromConsoleCourseTile
        extends BaseClass {
    public GLP_283107_VerifyCourseLaunchInGLPFromConsoleCourseTile() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.CONSOLE },
            enabled = true,
            description = "Verify that course is launched in GLP when instructor clicks the course tile from console.")

    public void verifyCourseLaunchInGLPFromConsoleCourseTile() {
        startReport(getTestCaseId(),
                "Verify that course is launched in GLP when instructor clicks the course tile from console.");
        try {
            GLPConsole_LoginPage objInstructorConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            GLPLearner_CourseViewPage objLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objInstructorConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            GLPInstructor_InstructorDashboardPage objLearnerInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);
            objLearnerInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardCourseContentText",
                    "Verify user is redirected to Instructor Dashboard Page.");
            objLearnerCourseViewPage.verifyLogout();
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleLoginSignInHeading",
                    "Verify user is redirected to Console Login Page");
            objInstructorConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleCourseTile",
                    "Verify user is redirected to console home page.");
            objInstructorConsoleLoginPage.clickOnElement("ConsoleCourseTile",
                    "Click on Course Tile.");
            objLearnerInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardCourseContentText",
                    "Verify user is redirected to Instructor Dashboard Page.");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
