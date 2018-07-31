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
import com.glp.page.GLPConsole_LoginPage;
import com.glp.util.GLP_Utilities;

/**
 * @author nisha.pathria
 * @date Dec 16, 2017
 * @description : To verify that loading spinner is being displayed when course
 *              is created for an instructor user.
 */
public class GLP_283100_VerifyLoadingSpinnerOnCourseTile extends BaseClass {
    public GLP_283100_VerifyLoadingSpinnerOnCourseTile() {
        // TODO Auto-generated constructor stub
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.CONSOLE,
            Groups.NEWCOURSEREQUIRED }, enabled = false,
            description = "To verify that loading spinner is being displayed when course is created for an instructor user.")

    public void verifyLoadingSpinnerOnCourseTile() {
        startReport(getTestCaseId(),
                "To verify that loading spinner is being displayed when course is created for an instructor user.");
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_" + getTestCaseId()
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            GLPConsole_LoginPage objInstructorConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objInstructorConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));
            objInstructorConsoleLoginPage.verifyElementPresent(
                    "ConsoleTileLoadingSpinner",
                    "Verify Loading Spinner display on course tile on console home page.");
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