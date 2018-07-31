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
package com.test.glp_accessibility;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ChangeBrowser.changeBrowser;
import com.autofusion.groups.Groups;
import com.glp.util.GLP_Utilities;

/**
 * @author Abhishek.sharda
 * @date Nov 22, 2017
 * @description: Verify Web Content Accessibility Guidelines (WCAG) 2.1 for the
 *               Login-In page
 */
public class GLP_280647_VerifyAccessibilityConsoleSignInPage extends BaseClass {
    public GLP_280647_VerifyAccessibilityConsoleSignInPage() {
    }

    @changeBrowser(value = "firefox")
    @Test(groups = { Groups.REGRESSION, Groups.ACCESSIBILITY, Groups.INSTRUCTOR,
            Groups.LEARNER }, enabled = true,
            description = "Verify Web Content Accessibility Guidelines(WCAG) 2.0 with AA standards for the Sign-In page")
    public void verifyAccessibility() {
        startReport(getTestCaseId(),
                "Verify Web Content Accessibility Guidelines(WCAG) 2.0 with AA standards for the Sign-In page");
        try {
            GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj,
                    APP_LOG);
            objRestUtil.testAccessibility();
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
