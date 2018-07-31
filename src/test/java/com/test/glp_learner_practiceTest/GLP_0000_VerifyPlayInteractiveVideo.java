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
package com.test.glp_learner_practiceTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_LearningObjectivePage;

/**
 * @author nitish.jaiswal
 * @date Sept 18, 2017
 * @description: Verify course chapter contains three items - Core instruction,
 *               Practice and Section test
 */
public class GLP_0000_VerifyPlayInteractiveVideo extends BaseClass {
    public GLP_0000_VerifyPlayInteractiveVideo() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LOCALIZATION, Groups.LEARNER },
            enabled = false,
            description = "To Verify learner is able to play audio and video from core instruction.")

    public void verifyPSPCourseChapterItems() {
        try {
            startReport(getTestCaseId(),
                    "Verify learner is able to play audio and video from core instruction.");
            // Login in the application
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login("GLP_Learner_310977_bgBj",
                    configurationsXlsMap.get("LEARNER_PASSWORD"));
            GLPLearner_CourseViewPage objProductApplicationCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            GLPLearner_LearningObjectivePage objProductApplication_LearningObjectivePage = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);
            GLPLearner_CourseMaterialPage objProductApplication_CourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            // objProductApplicationCourseViewPage
            // .clickOnElement("secondCourseTile", "Click on second tile");

            objProductApplication_CourseMaterialPage
                    .clickOnElement("ModuleTwelveExpand", "Expand module 12.");
            objProductApplication_CourseMaterialPage.clickOnElement(
                    "ModuleTwelveSecondChapter",
                    "Start second chapter of module 12.");
            objProductApplication_CourseMaterialPage.clickOnElement(
                    "ModuleTwelveSecondChapterInteractive",
                    "Click interactive link of module 12.");
            objProductApplication_CourseMaterialPage.playVideo();
            // // objProductApplication_LearningObjectivePage
            // // .verifyRiolVideoPlayback();
            // objCommonUtil.verifyVideoPlayback(
            // "CourseInstructionChapterImage2.1.1ChapterVideo",
            // "Verify rendering and playback of coming video");
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
