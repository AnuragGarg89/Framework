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
import com.glp.page.GLPLearner_CourseViewPage;

/**
 * @author nisha.pathria
 * @date Dec 16, 2017
 * @description : To verify Instructor Sign In page appears when user enter
 *              internal URL after instructor signed out from application.
 * 
 */
public class GLP_283448_VerifyLoginPageAppearsOnEnteringInternalURL
        extends BaseClass {
    public GLP_283448_VerifyLoginPageAppearsOnEnteringInternalURL() {
        // TODO Auto-generated constructor stub
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.CONSOLE },
            enabled = true,
            description = "To verify Instructor Sign In page appears when user enter internal URL after instructor signed out from application.")

    public void verifyLoginPageAppearsOnEnteringInternalURL() {
        startReport(getTestCaseId(),
                "To verify Instructor Sign In page appears when user enter internal URL after instructor signed out from application.");
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
            objLearnerCourseViewPage.verifyElementPresent("CourseViewUserName",
                    "Verify user is redirected to GLP Course View Page.");
            String internalURL = objInstructorConsoleLoginPage.getInternalURL();
            System.out.println("Internal url is " + internalURL);
            objLearnerCourseViewPage.verifyLogout();
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleLoginSignInHeading",
                    "verify user is redirected to console login page on opening internal URL.");
            objInstructorConsoleLoginPage.openInternalURL(internalURL);
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleLoginSignInHeading",
                    "verify user is redirected to consoe login page on opening internal URL.");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
