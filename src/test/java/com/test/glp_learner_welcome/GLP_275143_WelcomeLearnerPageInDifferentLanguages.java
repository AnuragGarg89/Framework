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
package com.test.glp_learner_welcome;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_WelcomeLearnerPage;
import com.glp.util.GLP_Utilities;

public class GLP_275143_WelcomeLearnerPageInDifferentLanguages
        extends BaseClass {

    public GLP_275143_WelcomeLearnerPageInDifferentLanguages() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify UI of Welcome Page of Learner in respective language.")

    public void verifyContents() {
        startReport(getTestCaseId(),
                "Verify UI of Welcome Page of Learner in respective language.");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId()
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

            // Verify the Learner Welcome Page title.
            GLPLearner_WelcomeLearnerPage obj_ProductApplication_WelcomeLearnerPage = new GLPLearner_WelcomeLearnerPage(
                    reportTestObj, APP_LOG);

            obj_ProductApplication_WelcomeLearnerPage.verifyTextInList(
                    "WelcomeLearnerPageWelcomeTitlePart1",
                    ResourceConfigurations
                            .getProperty("welcomeLearnerPageTitle") + " "
                            + learnerUserName + "!",
                    "Verify Welcome Learner page welcome title in different languages."
                            + ResourceConfigurations.getProperty("language"));
            // Verify the Learner Welcome Page Text.
            obj_ProductApplication_WelcomeLearnerPage.verifyTextInList(
                    "WelcomeLearnerPageWelcomeText",
                    ResourceConfigurations
                            .getProperty("welcomeLearnerPageText"),
                    "Verify Welcome Learner page welcome text in different languages"
                            + ResourceConfigurations.getProperty("language"));

            // Verify the Learner Welcome Page Start Pre-Assessment button text.
            obj_ProductApplication_WelcomeLearnerPage.verifyTextInList(
                    "WelcomeLearnerPageStartPreAssessmentBtn",
                    ResourceConfigurations.getProperty(
                            "welcomeLearnerPageStartPreAssessmentButton"),
                    "Verify Start Pre Assessment Button Text on Welcome Learner page in different languages."
                            + ResourceConfigurations.getProperty("language"));
        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
