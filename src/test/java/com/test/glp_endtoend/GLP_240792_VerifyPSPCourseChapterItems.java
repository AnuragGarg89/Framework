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
package com.test.glp_endtoend;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;

/**
 * @author nitish.jaiswal
 * @date Sept 18, 2017
 * @description: Verify course chapter contains three items - Core instruction,
 *               Practice and Section test
 */
public class GLP_240792_VerifyPSPCourseChapterItems extends BaseClass {
    public GLP_240792_VerifyPSPCourseChapterItems() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = false,
            description = "To Verify course chapter contains three items - Core instruction, Practice and Section test.")
    public void verifyPSPCourseChapterItems() {
        startReport(getTestCaseId(),
                "Verify course chapter contains three items - Core instruction, Practice and Section test.");
        // Login in the application
        GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);
        objProductApplicationLoginPage.login(
                configurationsXlsMap.get("LEARNER_USER_NAME"),
                configurationsXlsMap.get("LEARNER_PASSWORD"));
        GLPLearner_CourseViewPage objProductApplicationCourseViewPage = new GLPLearner_CourseViewPage(
                reportTestObj, APP_LOG);
        // Automate the remaining steps of test case
        GLPLearner_CourseMaterialPage objProductApplicationCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                reportTestObj, APP_LOG);
        objProductApplicationCourseViewPage
                .navigateToLearnerDashboardFromCourseView();
        objProductApplicationCourseMaterialPage.verifyTextContains(
                "CourseMaterialFilter", "Non-Mastered modules only",
                "Verify list of non mastered chapter is displayed by default.");
        objProductApplicationCourseMaterialPage
                .verifyAndCollapseChapterIfExpanded();
        objProductApplicationCourseMaterialPage.clickOnElement(
                "CourseMaterialExpandIcon",
                "Click on the arrow icon to expand the chapter to view sections for non mastered chapter.");
        objProductApplicationCourseMaterialPage.clickOnElement(
                "CourseMaterialChapterLink",
                "Click on the chapter link to view the sub sections for non mastered chapter.");
        objProductApplicationCourseMaterialPage.verifyPSPSubSectionItems();
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        objCommonUtil.scrollWebPage(0, 0);
        objProductApplicationCourseMaterialPage.selectRequiredModulesFilter(
                "CourseMatrerialMasteredDropdownText",
                "Select 'Mastered Modules' from dropdown.");
        objProductApplicationCourseMaterialPage.clickOnElement(
                "CourseMaterialExpandIconMastered",
                "Click on the arrow icon to expand the chapter to view sections for mastered chapter.");
        objProductApplicationCourseMaterialPage.clickOnElement(
                "CourseMaterialChapterLinkMastered",
                "Click on the chapter link to view the sub sections for mastered chapter.");
        objProductApplicationCourseMaterialPage.verifyElementPresent(
                "CourseMaterialChaperSubSectionsText",
                "Verify the subsections are expandable further to access subsections for mastered module.");
        objCommonUtil.scrollWebPage(0, 0);
        objProductApplicationCourseMaterialPage.selectRequiredModulesFilter(
                "CourseMatrerialAllDropdownText",
                "Select 'All Modules' from dropdown.");
        objProductApplicationCourseMaterialPage.verifyLinearOrderOfChapters();
        objProductApplicationCourseMaterialPage.navigateToCourseViewPage();
        objProductApplicationCourseViewPage.verifyElementPresent(
                "CourseViewStartDiagnosticButton",
                "Verify User is navigated back to course view page after clicking Pearson Logo.");
        objProductApplicationCourseViewPage.verifyLogout();
    }
}
