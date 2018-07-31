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
package com.test.glp_learner_coursehome;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * @author mohit.gupta5
 * @date Nov 22, 2017
 * @description: Verify that the welcome page will be displayed to the learner
 *               if the learner does not click the Start your path button and
 *               exits the application.
 */
public class GLP_270314_VerifyWelcomeScreenDisplayed extends BaseClass {
    public GLP_270314_VerifyWelcomeScreenDisplayed() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.HEARTBEAT, Groups.LEARNER },
            enabled = true,
            description = "Verify that the welcome page will be displayed to the learner if the learner does not click the Start your path button and exits the application.")
    public void verifyWelcomeScreenDisplayed() {
        startReport(getTestCaseId(),
                "Verify that the welcome page will be displayed to the learner if the learner does not click the Start your path button and exits the application.");
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

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Verify 'Welcome,' Text is present.
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeText",
                    ResourceConfigurations.getProperty("welcomeText"),
                    "Verify 'Welcome,' Text is present.");

            // LogOut from the application
            objGLPLearnerCourseViewPage.verifyLogout();
            // Login again in the application
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objGLPLearnerCourseViewPage.verifyElementPresent(
                    "CourseTileStudent",
                    "Verify 'Rio' course tile is present.");

            // Navigate to Welcome Screen Learner
            objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();
            // Verify 'Welcome,' Text is present.
            objProductApplicationCourseHomePage.verifyText(
                    "CourseHomeWelcomeText",
                    ResourceConfigurations.getProperty("welcomeText"),
                    "Verify Welcome page is opened and 'Welcome,' Text is present.");

        } finally {
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
