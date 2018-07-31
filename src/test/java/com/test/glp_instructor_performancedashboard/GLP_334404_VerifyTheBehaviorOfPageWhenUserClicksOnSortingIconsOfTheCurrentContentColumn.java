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
package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;

/**
 * @author mayank.mittal
 * @date 10/05/2018
 * @description :To verify the behavior of page when user clicks on sorting
 *              icons of the Current content column.
 * 
 */
public class GLP_334404_VerifyTheBehaviorOfPageWhenUserClicksOnSortingIconsOfTheCurrentContentColumn
        extends BaseClass {
    public GLP_334404_VerifyTheBehaviorOfPageWhenUserClicksOnSortingIconsOfTheCurrentContentColumn() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = false,
            description = "To verify the behavior of page when user clicks on sorting icons of the Current content column.")
    public void
           verifyTheBehaviorOfPageWhenUserClicksOnSortingIconsOfTheCurrentContentColumn()
                   throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify the behavior of page when user clicks on sorting icons of the Current content column.");

        try {
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

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Verify Student List is clicked.");
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceModuleDropDown",
                    "Verify Module List is clicked.");
            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("allModulesValueInModuleDropDown"),
                    "InstructorDashBoardPerformanceModuleDropDownValues");
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Verify Module List is clicked.");
            objProductApplicationInstructorDashboardPage.selectAValueFromList(
                    ResourceConfigurations
                            .getProperty("allStudentsValueInStudentDropDown"),
                    "InstructorDashBoardPerformanceStudentDropDownValues");

            // check the default list sorting is in ascending order
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "PerforManceDashBoardStudentCurrentContentColumn", "asc",
                    "Verify Student list is sorted in ascending order by name.");
            // Down arrow is clicked and list is converted in descending order.
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceCurrentContentDesc",
                    "Student list will be sorted in descending order by Current Content.");
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "PerforManceDashBoardStudentCurrentContentColumn", "desc",
                    "Verify Student list is sorted in ascending order by name.");

        } finally {
            // finally delete the created user
            // objRestUtil.deleteUserViaApi(learnerUserName1);
            webDriver.quit();
            webDriver = null;
        }
    }
}
