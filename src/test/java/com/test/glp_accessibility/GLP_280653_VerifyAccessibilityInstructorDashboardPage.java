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
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.util.GLP_Utilities;

/**
 * @author Abhishek.sharda
 * @date Nov 22, 2017
 * @description: Verify Web Content Accessibility Guidelines (WCAG) 2.1 for the
 *               Instructor ManagementDashboard page
 */
public class GLP_280653_VerifyAccessibilityInstructorDashboardPage
        extends BaseClass {
    public GLP_280653_VerifyAccessibilityInstructorDashboardPage() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.ACCESSIBILITY,
            Groups.INSTRUCTOR }, enabled = true,
            description = "Verify Web Content Accessibility Guidelines(WCAG) 2.0 with AA standards for the Instructor Management Dashboard page")

    public void verifyAccessibilityInstructorDashboardPage() {
        startReport(getTestCaseId(),
                "Verify Web Content Accessibility Guidelines(WCAG) 2.0 with AA standards for the Instructor Management Dashboard Page");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");
            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage.navigateToManagementDashboard();
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "PearsonLogo", "Verify pearson logo is displayed.");
            GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj,
                    APP_LOG);
            objRestUtil.testAccessibility();
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}