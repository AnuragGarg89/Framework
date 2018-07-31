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
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author yogesh.choudhary
 * @date Nov 29, 2017
 * @description : Verification of Headers and footers are visible on the
 *              non-content related pages
 */

public class GLP_276345_VerifyHeaderFooterOnDigonasticNonContentPages
        extends BaseClass {
    public GLP_276345_VerifyHeaderFooterOnDigonasticNonContentPages() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verification of Headers and footers are visible on the non-content related pages")
    public void verifyFooterLinks() {
        startReport(getTestCaseId(),
                "Verification of Headers and footers are visible on the non-content related pages");

        GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId()
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create user and subscribe course using corresponding APIs.

            /*
             * objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
             * ResourceConfigurations.getProperty("consolePassword"),
             * ResourceConfigurations .getProperty("consoleUserTypeLearner"),
             * true);
             */

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    "GLP_Learner_270314_kKiu",
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Verify "Copyright © 2017 Pearson Education Inc. All rights

            objGLPLearnerCourseViewPage.verifyText(
                    "DashBoardFooterCopyRightText",
                    ResourceConfigurations.getProperty("copyRightLinkText"),
                    "Verify Copyright footer text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Accessibility text localization
            objGLPLearnerCourseViewPage.verifyText(
                    "DashBoardFooterAccessibilityLink",
                    ResourceConfigurations.getProperty("accessibilty"),
                    "Verify Accessibilty footer text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Privacy Policy text localization
            objGLPLearnerCourseViewPage.verifyText("DashBoardPrivacyPolicyLink",
                    ResourceConfigurations.getProperty("privacyPolicy"),
                    "Verify Privacy Policy footer text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Privacy Policy text localization
            objGLPLearnerCourseViewPage.verifyText(
                    "DashBoardFooterTermsAndConditionsLink",
                    ResourceConfigurations.getProperty("termsNConditions"),
                    "Verify Terms & Conditions footer text is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify Footer on Non content Page
            objGLPLearnerCourseViewPage
                    .verifyDiagonasticNonContentPageFooters();

            // Verify Header on Non content Page
            objGLPLearnerCourseViewPage
                    .verifyDiagonasticNonContentPageHeadersLogo();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            // Try to attempt few questions
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 1,
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));

            // Click on cross icon on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnCrossButton(
                    "DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");
            // Click on Leave button on diagnostic page
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticPopUpLeaveButton",
                    "Click on Leave button on pop up to exit diagnostic.");

            // LogOut from the application
            objGLPLearnerCourseViewPage.verifyLogout();
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
