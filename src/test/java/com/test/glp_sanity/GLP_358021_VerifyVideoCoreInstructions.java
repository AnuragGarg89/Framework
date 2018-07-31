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
package com.test.glp_sanity;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CoreInstructionsPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author anurag.garg1 2018-04-23
 * 
 */
public class GLP_358021_VerifyVideoCoreInstructions extends BaseClass {
    public GLP_358021_VerifyVideoCoreInstructions() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "Verify learner can access videos (User can play, pause, seek, stop, fullscreen mode, enable subtitle etc.) in core instruction section")
    public void verifyVideoOfCI() throws InterruptedException {

        startReport(getTestCaseId(),
                "Verify learner can access videos (User can play, pause, seek, stop, fullscreen mode, enable subtitle etc.) in core instruction section");

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

            // Expand Module 11
            objProductInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementModule11Name", "Module 11 Expanded");

            // Click on Preview button of LO11.1
            objProductInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementPreviewButtonOfLO11.1",
                    "Preview Button of LO 11.1 clicked");

            // Verify video playback
            objProductCoreInstructionPage
                    .verifyVideoPlayback("Verifying video playback");

            // Click on Back button
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstructionBackButton",
                    "Click on back button to go to Management dashboard page");

            // Verify in collapsed form "PREVIEW" button is not displayed
            objProductInstructorManagementDashboardPage.verifyElementNotPresent(
                    "InstructorManagemenPreviewButtonOnModulesInCollapedForm",
                    "Preview Button is not Displayed in collapsed form");

            // Expand Module 12
            objProductInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementModule12Name", "Module 12 Expanded");

            // Click on Preview button of LO 12.4
            objProductInstructorManagementDashboardPage.clickOnElement(
                    "InstructorManagementPreviewButtonOfLO12.4",
                    "Preview Button of LO 12.4 clicked");

            // Collapse the 1st EO
            objProductCoreInstructionPage.clickOnElement("CoreInstruction1stEO",
                    "Collapse first EO of LO 12.4");

            // Click on Second EO to expand on Core Instruction Page
            objProductCoreInstructionPage.clickOnElement("CoreInstruction2ndEO",
                    "Expand second EO of LO 12.4");

            // Click on 7th Core Instruction of 2nd EO
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstruction2ndEO7thCoreInstruction",
                    "Clicking on 7th Core Instruction of 12.4");

            // Click on Hamburger icon to verify full screen is displayed
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstructionHamburgerIconWhenDrawerIsExpanded",
                    "Clicking on Hamburger icon");
            objProductCoreInstructionPage.verifyElementNotPresent(
                    "CoreInstructionLeftHandDrawerContainer",
                    "Drawer container is Collapsed and Full screen is Displayed");

            // Verify on full screen content is displayed
            objProductCoreInstructionPage.verifyElementPresent(
                    "CoreInstructionContentOnRightHandSide",
                    "Content displayed on Full screen");

            // Click on Hamburger icon to verify full screen is closed
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstructionHamburgerIconWhenDrawerIsCollapsed",
                    "Clicking on Hamburger icon");
            objProductCoreInstructionPage.verifyElementPresent(
                    "CoreInstructionLeftHandDrawerContainer",
                    "Drawer container is Expanded and Full screen is Collapsed");

            // Click on 8th Core Instruction of 2nd EO
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstruction2ndEO8thCoreInstruction",
                    "Clicking on 8th Core Instruction of 12.4");

            // Click on 3rd EO
            objProductCoreInstructionPage.clickOnElement("CoreInstruction3rdEO",
                    "Clicking on 3rd EO of 12.4");

            // Click on Back button
            objProductCoreInstructionPage.clickOnElement(
                    "CoreInstructionBackButton",
                    "Click on back button to go to Management dashboard page");

            // Verify in collapsed form "PREVIEW" button is not displayed
            objProductInstructorManagementDashboardPage.verifyElementNotPresent(
                    "InstructorManagemenPreviewButtonOnModulesInCollapedForm",
                    "Preview Button is not Displayed in collapsed form");

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}
