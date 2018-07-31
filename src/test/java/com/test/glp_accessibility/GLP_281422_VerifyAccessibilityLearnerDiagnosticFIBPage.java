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
package com.test.glp_accessibility;

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
 * @author Abhishek.sharda
 * @date Nov 22, 2017
 * @description: Verify Web Content Accessibility Guidelines (WCAG) 2.0 with AA
 *               standards for the dignosist: FIB question type page
 */
public class GLP_281422_VerifyAccessibilityLearnerDiagnosticFIBPage
        extends BaseClass {
    public GLP_281422_VerifyAccessibilityLearnerDiagnosticFIBPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.ACCESSIBILITY, Groups.LEARNER },
            enabled = true,
            description = "Verify Web Content Accessibility Guidelines (WCAG) 2.0 with AA standards for the dignosist: FIB question type page")
    public void verifyAccessibilityLearnerDiagnosticFIBPage() {
        startReport(getTestCaseId(),
                "Verify Web Content Accessibility Guidelines (WCAG) 2.0 with AA standards for the 'CourseMaterial' page");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_" + getTestCaseId()
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),

                    true);
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objProductApplicationCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseViewPage
                    .navigateToWelcomeScreenLearner();
            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();
            GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objProductApplication_DiagnosticTestPage.navigateToActivityType(
                    ResourceConfigurations.getProperty("fibDropDown"));
            objRestUtil.testAccessibility();
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
