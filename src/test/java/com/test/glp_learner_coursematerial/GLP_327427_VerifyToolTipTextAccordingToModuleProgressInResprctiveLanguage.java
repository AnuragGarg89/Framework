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
package com.test.glp_learner_coursematerial;

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
import com.glp.util.GLP_Utilities;

/**
 * @author pallavi.tyagi
 * @date May 02, 2018
 * @description : Verify the localization of below mentioned : Tool-tip hover
 *              text for the images that display based on the progress of a
 *              student for the LO in a module on the course home page
 * 
 * 
 */
public class GLP_327427_VerifyToolTipTextAccordingToModuleProgressInResprctiveLanguage
        extends BaseClass {
    public GLP_327427_VerifyToolTipTextAccordingToModuleProgressInResprctiveLanguage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,

            description = "Verify the localization of below mentioned :Tool-tip hover text for the images that display based on the progress of a student for the LO in a module on the course home page")
    public void
           verifyToolTipTextAccordingToModuleProgressInResprctiveLanquage() {
        startReport(getTestCaseId(),
                "Verify the localization of below mentioned : Tool-tip hover text for the images that display based on the progress of a student for the LO in a module on the course home page");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        int locompletedCount = 1;
        try {
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);
            // Login in the application
            GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            // Attempt diagnostic test
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations
                            .getProperty("submitWithoutAttempt"));

            // Click on Go TO Course Home Link
            objGLPLearnerDiagnosticTestPage.clickOnElement(
                    "DiagnosticGoToCourseHomeLink",
                    "Click on Go To Course Home Link to navigate to course material page");
            String moduleNonMastered = objGLPLearnerCourseMaterialPage
                    .getText("CourseMaterialLetsStartModuleText");
            String moduleNumber = moduleNonMastered.substring(19, 21);
            int numberOfLo = objGLPLearnerCourseMaterialPage.getLoCount();
            // Verify the tool tip text of the image displaying against the
            // Lo, student has not even started the LO.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialCircleAgainstFirstLo", "title",
                    ResourceConfigurations
                            .getProperty("notStartedLoCircelText"),
                    "Verify the tool tip text of the image displaying against the Lo, when student has not even started the LO is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Verify the tool tip text of the image displaying against the
            // module, when Module not started.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialNonMasteredModuleStarCircle", "title",
                    ResourceConfigurations
                            .getProperty("nonMasteredModuleStarCircleText1"),
                    "Verify the tool tip text of the image displaying against the module, when Module not started is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Complete First Lo
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + ".1");

            objCommonUtil.refreshCurrentPage();

            // Verify the tool tip text of the image displaying against the
            // Lo, student has completed the LO.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialCircleAgainstFirstLo", "title",
                    ResourceConfigurations.getProperty("completedLoCircelText"),
                    "Verify the tool tip text of the image displaying against the Lo, when student has completed the LO is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            // Verify the tool tip text of the image displaying against the
            // module, if student has completed a LO of a Module.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialNonMasteredModuleStarCircle", "title",
                    ResourceConfigurations
                            .getProperty("nonMasteredModuleStarCircleText2"),
                    "Verify the tool tip text of the image displaying against the module, if student has completed a LO of a Module is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            // Complete First Lo
            for (int i = 2; i <= Math.ceil(numberOfLo / 2); i++) {
                objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                        moduleNumber + "." + i);
                locompletedCount++;

            }
            objCommonUtil.refreshCurrentPage();
            // Verify the tool tip text of the image displaying against the
            // module, if student has completed around half of the total no.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialNonMasteredModuleStarCircle", "title",
                    ResourceConfigurations
                            .getProperty("nonMasteredModuleStarCircleText3"),
                    "Verify the tool tip text of the image displaying against the module, if student has completed around half of the total no is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + "." + locompletedCount + 1);
            objCommonUtil.refreshCurrentPage();

            // Verify the tool tip text of the image displaying against the
            // module, student has completed more than half of the total LOs.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialNonMasteredModuleStarCircle", "title",
                    ResourceConfigurations
                            .getProperty("nonMasteredModuleStarCircleText4"),
                    "Verify the tool tip text of the image displaying against the module, if student has completed more than half of the total LOs is displayed in "
                            + ResourceConfigurations.getProperty("language"));

            objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                    moduleNumber + "." + locompletedCount + 1);

            // Complete First Lo
            for (int i = locompletedCount + 2; i <= numberOfLo; i++) {
                objRestUtil.attemptLearningObjectiveViaAPI(learnerUserName,
                        moduleNumber + "." + i);

            }

            objCommonUtil.refreshCurrentPage();
            // Verify the tool tip text of the image displaying against the
            // module, student has completed all LOs.
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialNonMasteredModuleStarCircle2", "title",
                    ResourceConfigurations
                            .getProperty("nonMasteredModuleStarCircleText5"),
                    "Verify the tool tip text of the image displaying against the module, if student has completed all LOs is displayed in "
                            + ResourceConfigurations.getProperty("language"));
            objGLPLearnerCourseMaterialPage.verifyElementAttributeValue(
                    "CourseMaterialCircleAgainstFirstLo", "title",
                    ResourceConfigurations
                            .getProperty("notStartedLoCircelText"),
                    "Verify the tool tip text of the image displaying against the Lo, when student has not even started the LO is displayed in "
                            + ResourceConfigurations.getProperty("language"));

        }

        finally {
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
