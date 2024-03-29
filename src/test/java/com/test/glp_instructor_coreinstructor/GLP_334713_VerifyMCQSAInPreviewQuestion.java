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
package com.test.glp_instructor_coreinstructor;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CoreInstructionsPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

public class GLP_334713_VerifyMCQSAInPreviewQuestion extends BaseClass {

    public GLP_334713_VerifyMCQSAInPreviewQuestion() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "To verify MCQSA is displayed in preview mode on Preview Questions")
    public void verifyMCQSAInPreviewQuestion() {

        startReport(getTestCaseId(),
                "To verify MCQSA is displayed in preview mode on Preview Questions");

        GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);

        GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                reportTestObj, APP_LOG);

        GLPInstructor_InstructorDashboardPage objProductInstructorPerformanceDashboardPage = new GLPInstructor_InstructorDashboardPage(
                reportTestObj, APP_LOG);

        GLPInstructor_ManagementDashboardPage objProductInstructorManagementDashboardPage = new GLPInstructor_ManagementDashboardPage(
                reportTestObj, APP_LOG);

        GLPInstructor_CoreInstructionsPage objProductCoreInstructionPage = new GLPInstructor_CoreInstructionsPage(
                reportTestObj, APP_LOG);

        try {
            // Login with an Existing user
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));

            // Click on course tile
            objProductApplicationCourseViewPage.clickOnCourseTile();

            // Switch to Management tab
            objProductInstructorPerformanceDashboardPage
                    .switchToManagementTab();

            // Navigate to the Preview Questions of LO 11.1
            objProductInstructorManagementDashboardPage
                    .navigateToPreviewQuestionsInLo("11.1");

            // Navigate to MCQSA Question
            objProductCoreInstructionPage.navigateToQuestionType("MCQSA",
                    "Verify MCQSA is displayed in preview mode on Preview Questions");

            // Verify only 1 answer is displayed for MCQSA type of Question
            objProductCoreInstructionPage.verifyElementPresent(
                    "CoreInstructionPreviewQuestionMCQSAAnswer",
                    "Verifying only 1 answer is displayed for MCQSA type of Question");
        } finally {
            webDriver.quit();
            webDriver = null;

        }
    }
}
