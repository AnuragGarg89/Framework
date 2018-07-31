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
import com.autofusion.keywords.FindElement;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * 
 * @author ratnesh.singh
 * @date June 6, 2018
 * @description: Verify All students with Learning and Not started status should
 *               also be displayed on the unlock pop up If instructor selects
 *               Unlock Selected button for all students and Nth module.
 */

public class GLP_350594_VerifyNotStartedAndLearningStudentsAlsoDisplayedInUnlockSelectedPopUp
        extends BaseClass {
    public void
           GLP_350594_VerifyNotStartedAndLearningStudentsAlsoDisplayedInUnlockSelectedPopUp() {
    }

    @Test(groups = { Groups.INSTRUCTOR, Groups.REGRESSION,
            Groups.NEWCOURSEREQUIRED }, enabled = true,
            description = "Verify that students with 'Not Started' and 'Learning' status also gets displayed on the unlock pop up in case instructor selects All Students (student filter) and Nth Module (content filter) and click on unlock selected button.")
    public void
           VerifyNotStartedAndLearningStudentsAlsoDisplayedInUnlockSelectedPopUp() {
        startReport(getTestCaseId(),
                "Verify that students with 'Not Started' and 'Learning' status also gets displayed on the unlock pop up in case instructor selects All Students (student filter) and Nth Module (content filter) and click on unlock selected button.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        FindElement findElement = new FindElement();

        String instructorName = "GLP_Instructor_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName1 = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerUserName2 = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {

            // Create Instructor with new course with 0 mastry
            objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    true);

            // Create First Learner subscribing the new course created by the
            // Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName1,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Create Second Learner subscribing the new course created by
            // the Instructor
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName2,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Login to the GLP application with first learner
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName1,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify CourseTile is Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic first question for first learner
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            APP_LOG.info("Navigating to the diagnostic page for first learner");
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Attempt the diagnostic for the first learner
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Click on Pearson logo on course material page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialPearsonLogo",
                    "Click on Pearson Logo on Course Material Page.");

            // Click on username dropdown menu on course view page

            objGLPLearnerCourseViewPage.clickOnElement("CourseViewUserName",
                    "Click on username dropdown menu on course view page.");

            // Click on logout button displayed in dropdown menu

            objGLPLearnerCourseViewPage.clickOnElement("LogoutButton",
                    "Click on Logout button dispalyed in dropdown menu.");

            // Login to the GLP application with Second learner
            objProductApplicationConsoleLoginPage.login(learnerUserName2,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Verify CourseTile is Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic first question for second learner
            APP_LOG.info(
                    "Navigating to the diagnostic page for second Learner");
            objGLPLearnerCourseHomePage.navigateToDiagnosticPage();

            // Attempt the diagnostic for the second learner
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Click on Module start button
            objGLPLearnerCourseMaterialPage.clickOnElement("ResultButton",
                    "Click on module start button of Module 16 to navigate to core instructions page.");

            // Verify that Practice and apply as you go pop up gets displayed
            // and click on Got it
            objGLPLearnerPracticeTestPage.closePracticeAndApplyPopup();

            // Click on Practice test Course Drawer Back Arrow
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on Practice test Course Drawer Back Arrow.");

            // Click on Pearson logo on course material page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "CourseMaterialPearsonLogo",
                    "Click on Pearson Logo on Course Material Page.");

            // Click on username dropdown menu on course view page
            objGLPLearnerCourseViewPage.clickOnElement("CourseViewUserName",
                    "Click on username dropdown menu on course view page.");

            // Click on logout button displayed in dropdown menu
            objGLPLearnerCourseViewPage.clickOnElement("LogoutButton",
                    "Click on Logout button dispalyed in dropdown menu.");

            // Login to the application as a Instructor
            objProductApplicationConsoleLoginPage.login(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPInstructor_CourseViewPage objGLPInstructorCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify Instructor is logged in
            objGLPInstructorCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Verify Instructor is logged in successfully.");

            // Click on instructor course tile
            objGLPInstructorCourseViewPage.clickOnElement(
                    "CourseTileInstructor", "Click on course tile.");

            findElement.checkPageIsReady();

            GLPInstructor_InstructorDashboardPage objGLPInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Switch to 'Performance' tab
            objGLPInstructorDashboardPage.switchToPerformaceTab();

            // Switch to 'Student List' tab
            objGLPInstructorDashboardPage.switchToStudentListTab();

            // Click on "Students" dropdown
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentDropDown",
                    "Click on Students dropdown.");

            // Select "All students" from dropdown list
            objGLPInstructorDashboardPage.clickOnElementContainsInnerText(
                    "InstructorDashBoardPerformanceStudentDropDownList",
                    ResourceConfigurations
                            .getProperty("studentDropdownAllStudents"));

            // Click on "Modules" dropdown
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceModuleDropDown",
                    "Click on Modules dropdown.");

            // Select "Module 16" from dropdown list
            objGLPInstructorDashboardPage.clickOnElementContainsInnerText(
                    "InstructorDashBoardPerformanceContentDropDownList",
                    ResourceConfigurations
                            .getProperty("module16StudentDetail"));

            // Verify first learner is in "Not started" status
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStudentStatusButtonDynamic:dynamicLearnerReplace="
                            + learnerUserName1
                            + ",dynamicStatusReplace=notstarted",
                    "Verify first learner is in 'Not started' status.");

            // Verify second learner is in "Learning" status
            objGLPInstructorDashboardPage.verifyElementPresent(
                    "InstructorDashBoardPerformanceStudentStatusButtonDynamic:dynamicLearnerReplace="
                            + learnerUserName2
                            + ",dynamicStatusReplace=learning",
                    "Verify second learner is in 'Learning' status.");

            // Click on 'Unlock Selected' button
            objGLPInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardUnLockSelectedIcon",
                    "Click on 'Unlock Selected' button");

            // Verify 'UnLock Module Test' pop-up is present on UI
            objGLPInstructorDashboardPage.verifyTextContains(
                    "UnlockModuleTestText",
                    ResourceConfigurations.getProperty("unlockModulePopUp"),
                    "Verify 'UnLock Module Test' pop-up is present on UI.");

            // Verify that first student having status as "Not started" is
            // present
            // in student list under unlock selected popup
            objGLPInstructorDashboardPage.verifyTextContainsInList(
                    "InstructorDashBoardPerformanceUnlockSelectedPopUpStudentNameList",
                    "Test, " + learnerUserName1,
                    "Verify that student having status as 'Not started' is present in student list under unlock pop-up.");

            // Verify that second student having status as "Learning" is present
            // in student list under unlock selected popup
            objGLPInstructorDashboardPage.verifyTextContainsInList(
                    "InstructorDashBoardPerformanceUnlockSelectedPopUpStudentNameList",
                    "Test, " + learnerUserName2,
                    "Verify that student having status as 'Learning' is present in student list under unlock pop-up.");

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName1,
                        ResourceConfigurations.getProperty("consolePassword"));
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName2,
                        ResourceConfigurations.getProperty("consolePassword"));
                objRestUtil.unpublishSubscribedCourseDatabase(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));
                System.out.println("Unpublish data from couchbase DB");
            }
            webDriver.quit();
            webDriver = null;
        }

    }

}
