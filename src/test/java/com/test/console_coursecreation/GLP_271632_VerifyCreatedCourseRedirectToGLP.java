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
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Nov 9, 2017
 * @description : Verified User with instructor access :To verify that the
 *              instructor should be able to create a course under REVEL product
 *              and launch the course.
 */
public class GLP_271632_VerifyCreatedCourseRedirectToGLP extends BaseClass {
    public GLP_271632_VerifyCreatedCourseRedirectToGLP() {
        // TODO Auto-generated constructor stub
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.CONSOLE,
            Groups.NEWCOURSEREQUIRED }, enabled = false,
            description = "Verified User with instructor access :To verify that the instructor should be able to create a course under REVEL product and launch the course.")

    public void verifyCreatedCourseRedirectToGLP() {
        startReport(getTestCaseId(),
                "Verified User with instructor access :To verify that the instructor should be able to create a course under REVEL product and launch the course.");

        GLPConsole_LoginPage objProductInstructorConsoleLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_" + getTestCaseId()
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objProductInstructorConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objProductInstructorConsoleLoginPage.clickOnElement(
                    "ConsolePearsonLogo", "Click on Pearson Logo.");
            objProductInstructorConsoleLoginPage.courseCreation();
            objProductInstructorConsoleLoginPage.clickOnElement(
                    "ConsoleCourseTileCloseButton",
                    "Close the pop up on Course tile.");
            objProductInstructorConsoleLoginPage.clickOnElement(
                    "ConsoleCourseTile", "Click on Course Tile.");
        }

        // Delete User via API
        finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }
    }
}