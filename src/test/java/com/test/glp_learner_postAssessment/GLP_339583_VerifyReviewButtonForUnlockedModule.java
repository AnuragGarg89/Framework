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
package com.test.glp_learner_postAssessment;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author pankaj.sarjal
 * @date May 16, 2018
 * @description: Verify review button display for all LO's when instructor
 *               unlock module test regardless of readiness achieved. This test
 *               case also cover another test which is test ID : 339582 and
 *               339585
 * 
 */
public class GLP_339583_VerifyReviewButtonForUnlockedModule extends BaseClass {
    public GLP_339583_VerifyReviewButtonForUnlockedModule() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify review button display for all LO's when instructor unlock module test regardless of readiness achieved")
    public void verifyReviewButtonForUnlockedModule() {
        startReport(getTestCaseId(),
                "Verify review button display for all LO's when instructor unlock module test regardless of readiness achieved");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            // Click on go to course home link on diagnostic result page
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");
            // objRestUtil.lockUnlockPostAssessmentForLearner(learnerUserName,
            // ResourceConfigurations.getProperty("statusUnlocked"), "16");
            // Refresh the screen
            objGLPLearnerCourseMaterialPage.refreshPage();

            // Verify 'Review' button is displaying in front of 'seventh/last'
            // LO of 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'seventh/last' LO of 'Module 16'.");

            // Click on 'Review' button in front of 'seventh/last' LO of 'Module
            // 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    "Click on 'Review' button in front of 'seventh/last' LO of 'Module 16'.");

            GLPLearner_PracticeTestPage objProductApplicationPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Click on 'Second Guide' of Last EO
            objProductApplicationPracticeTestPage.clickOnElement(
                    "EOSecondGuide", "Click on 'Second Guide' of Last EO");

            // Click on 'back arrow' button to go to course material
            objProductApplicationPracticeTestPage.clickOnElement(
                    "PraciceTestCourseDrawerBackArrow",
                    "Click on 'back arrow' button to go to course material page");

            // Verify 'Start' button is displaying in front of 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=8",
                    ResourceConfigurations.getProperty("module11StartButton"),
                    "Verify 'Start' button is displaying infornt of 'Module 16'.");

            // Verify 'Review' button is displaying in front of first LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=1",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'first' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of second LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=2",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'second' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of third LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=3",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'third' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of fourth LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=4",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'fourth' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of fifth LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=5",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'fifth' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of sixth LO of
            // 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=6",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'sixth' LO of 'Module 16'.");

            // Verify 'Review' button is displaying in front of 'seventh/last'
            // LO of 'Module 16'
            objGLPLearnerCourseMaterialPage.verifyTextContains(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    ResourceConfigurations
                            .getProperty("module11LOReviewButton"),
                    "Verify 'Review' button is displaying in front of 'seventh/last' LO of 'Module 16'.");

            // Click on 'Review' button in front of 'seventh/last' LO of 'Module
            // 16'
            objGLPLearnerCourseMaterialPage.clickOnElement(
                    "LOStartReviewDynamicButton:dynamicReplace=7",
                    "Click on 'Review' button in front of 'seventh/last' LO of 'Module 16'.");

            // Verify learner navigate to last saved state on TOC
            objProductApplicationPracticeTestPage.verifyTextContains(
                    "EOSecondGuide",
                    ResourceConfigurations.getProperty("eoSecondGuide"),
                    "Verify learner navigate to last saved state on TOC");

            // Verify progress indicator for last EO
            objProductApplicationPracticeTestPage.verifyElementPresent(
                    "EOProgressIndicator",
                    "Verify progress indicator for last EO is present on UI.");

            // Verify 'Star Icon' against last EO
            objProductApplicationPracticeTestPage.verifyElementPresent(
                    "StarIcon", "Verify 'Star Icon' against last EO.");

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
