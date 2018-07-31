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
import com.glp.page.GLPLearner_CourseMaterialPage;
import com.glp.page.GLPLearner_CourseViewPage;

/**
 * @author mohit.gupta5
 * @date June 12, 2018
 * @description: Verify learner is able to access toc from coursehome/diagnostic
 *               result page
 */
public class GLP_358033_VerifyLearnerTOCPage extends BaseClass {
    public GLP_358033_VerifyLearnerTOCPage() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.SANITY, Groups.LEARNER,
            Groups.HEARTBEAT }, enabled = true,
            description = "Verify learner is able to access toc from coursehome/diagnostic result page")
    public void verifyLearnerTOCPage() throws Throwable {
        startReport(getTestCaseId(),
                "Verify learner is able to access toc from coursehome/diagnostic result page");
        try {
            // Login in the application
            GLPConsole_LoginPage objProductApplicationLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationLoginPage.login(
                    configurationsXlsMap.get("LEARNER_USER_NAME"),
                    configurationsXlsMap.get("LEARNER_PASSWORD"));
            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome Learner Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();
            GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            // Verify course home header text in LO in progress state
            objGLPLearnerCourseMaterialPage.verifyElementPresent(
                    "LockOverlayHeading", "Welcome Back Header Present");
            objGLPLearnerCourseMaterialPage.verifyCourseMaterialPage();
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
