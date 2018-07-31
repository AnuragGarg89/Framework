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
import com.glp.page.GLPLearner_WelcomeLearnerPage;
import com.glp.util.GLP_Utilities;

/**
 * @author rashmi.z
 * @date Dec 22, 2017
 * @description: To verify header text on CourseHome page should be in the
 *               language selected Localization Support with "Arabic - Saudi
 *               Arabia" languages for Header in Learner flow Localization
 *               Support with "Spanish (Latin America) - español
 *               (Latinoamérica)" language for Header in Learner flow
 *               GLP_284176_VerifyHeaderLocalization and
 *               GLP_284178_VerifyHeaderLocalization
 */
public class GLP_284178_VerifyHeaderLocalization extends BaseClass {
    public GLP_284178_VerifyHeaderLocalization() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "To verify footer on coursehome page localization")
    public void verifyHeaderLocalization() {
        startReport(getTestCaseId(),
                "To verify header on coursehome page localization");
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
            GLPLearner_WelcomeLearnerPage obj_ProductApplication_WelcomeLearnerPage = new GLPLearner_WelcomeLearnerPage(
                    reportTestObj, APP_LOG);

            // Navigate to the welcome page
            obj_ProductApplication_WelcomeLearnerPage.verifyElementPresent(
                    "WelcomeLearnerPageWelcomeText",
                    "Verify that learner is navigated to Welcome screen.");

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            objProductApplicationCourseHomePage.clickOnElement(
                    "CourseHomeHeaderAccountName",
                    "Click on Learner Account Name.");
            // Verify "My Account text localization
            objProductApplicationCourseHomePage.verifyText("MyAccount",
                    ResourceConfigurations.getProperty("myAccount"),
                    "Verify My Account header text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Account Settings text localization
            objProductApplicationCourseHomePage.verifyText("AccountSettings",
                    ResourceConfigurations.getProperty("accountSettings"),
                    "Verify Account Settings header text is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // Verify Sign Out text localization
            objProductApplicationCourseHomePage.verifyText("SignOut",
                    ResourceConfigurations.getProperty("signOut"),
                    "Verify Sign Out header text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

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
