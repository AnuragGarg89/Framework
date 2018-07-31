
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
package com.test.glp_instructor_managementdashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPInstructor_ManagementDashboardPage;

/**
 * @author pankaj.sarjal
 * @date June 03, 2018
 * @description: Verify Module 11-16 displaying on TOC on instructor management
 *               page
 */
public class GLP_350626_VerifyTOCOnManagementDashboardPage extends BaseClass {
    public GLP_350626_VerifyTOCOnManagementDashboardPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER, Groups.INSTRUCTOR },
            enabled = true,
            description = "Verify Module 11-16 displaying on TOC on instructor management page")
    public void verifyTOCOnMangementDashboardPage() {
        startReport(getTestCaseId(),
                "Verify Module 11-16 displaying on TOC on instructor management page");
       
        try {

        	// Login in the application
          GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                  reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on 'Course Tile'
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor",
                    "Click on 'Course Tile' successfully.");

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Management' tab
            GLPInstructor_ManagementDashboardPage objGLPInstructorManagementDashboardPage = objGLPInstructorDashboardPage
                    .switchToManagementTab();

            // Verify 'Module 11' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=1",
                    ResourceConfigurations.getProperty("module11StudentDetail"),
                    "Verify 'Module 11' is displaying in TOC on instructor management page.");

            // Verify 'Module 12' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=2",
                    ResourceConfigurations.getProperty("module12StudentDetail"),
                    "Verify 'Module 12' is displaying in TOC on instructor management page.");

            // Verify 'Module 13' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=3",
                    ResourceConfigurations.getProperty("module13StudentDetail"),
                    "Verify 'Module 13' is displaying in TOC on instructor management page.");

            // Verify 'Module 14' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=4",
                    ResourceConfigurations.getProperty("module14StudentDetail"),
                    "Verify 'Module 14' is displaying in TOC on instructor management page.");

            // Verify 'Module 15' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=5",
                    ResourceConfigurations.getProperty("module15StudentDetail"),
                    "Verify 'Module 15' is displaying in TOC on instructor management page.");

            // Verify 'Module 16' is displaying in TOC on student detail page
            objGLPInstructorManagementDashboardPage.verifyTextContains(
                    "Module11to16:dynamicReplace=6",
                    ResourceConfigurations.getProperty("module16StudentDetail"),
                    "Verify 'Module 16' is displaying in TOC on instructor management8 page.");

        } finally {

            webDriver.quit();
            webDriver = null;
        }
    }
}
