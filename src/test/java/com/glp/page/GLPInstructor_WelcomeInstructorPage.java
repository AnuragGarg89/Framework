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
package com.glp.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPInstructor_WelcomeInstructorPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOG = null;
    protected ExtentTest reportTestObj = null;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();

    public GLPInstructor_WelcomeInstructorPage(ExtentTest reportTestObj,
            Logger APP_LOG) {
        this.APP_LOG = APP_LOG;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click an element
     */
    public GLPInstructor_WelcomeInstructorPage clickOnElement(String element,
            String stepDesc) {

        APP_LOG.debug("Click on the Element: " + element);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        if ("Safari".equalsIgnoreCase(capBrowserName)) {
            this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                    element);
        } else {
            this.result = this.performAction.execute(ACTION_CLICK, element);
        }
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPInstructor_WelcomeInstructorPage(reportTestObj, APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify element is present
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOG.debug("Element is present :" + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                locator);
        logResultInReport(this.result, message, this.reportTestObj);
        if (this.result.contains(Constants.PASS)) {
            return Constants.PASS;
        } else {
            return Constants.FAIL;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 07 Nov,2017
     * @description Verify element Attribute like class, id
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public GLPInstructor_WelcomeInstructorPage verifyElementAttributeValue(
            String element, String attributeName, String verifyText,
            String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPInstructor_WelcomeInstructorPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Pallavi.Tyagi
     * @date 3 Oct,2017
     * @description Navigate to the mastery settings page
     * @return The object of ProductApplication_MasterySettingPage
     */
    public GLPInstructor_MasterySettingPage
           navigateToPreAssessmentMastryLevel() {

        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "WelcomeInstructorGetStartedButton");
        APP_LOG.debug("Click on 'Get Started' button.");
        clickOnElement("WelcomeInstructorGetStartedButton",
                "Click on 'Get Started' button.");
        return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Pallavi.Tyagi
     * @date 10 Nov,2017
     * @description Navigate to the mastery settings page
     * @return The object of ProductApplication_MasterySettingPage
     */
    public GLPLearner_CourseViewPage
           navigateToCourseViewPageFromWelcomescreen() {
        APP_LOG.debug("Click on 'Course' link.");
        clickOnElement("WelcomeInstructorCourseLink",
                "Click on 'Courses' link.");
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify text
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        this.APP_LOG.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element,
                text) + " i.e. " + text;
        if (result.contains("PASS")) {
            result = "PASS: " + element + " contains the correct text i.e. "
                    + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: " + element
                    + " does not contains the correct text i.e. " + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;
    }

    /**
     * @author pallavi.tyagi
     * @date 15 Nov,2017
     * @description Verify Element Not Present/display
     */
    public void verifyElementNotPresent(String element, String stepDesc) {

        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author pallavi.tyagi
     * @date 15 Nov,2017
     * @description Verify welcome screen is not displayed
     */
    public void verifywelcomeScreenNotDisplayed() {
        APP_LOG.debug("verify welcome screen is not displayed");
        verifyElementNotPresent("WelcomeInstructorGetStartedButton",
                "Welcome screen is not displayed again");

    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String Locator, String Text,
            String stepDesc) {
        APP_LOG.debug("Element is present: " + Locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator,
                Text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author pallavi.tyagi
     * @date 30 Nov ,17
     * @description To Get tital attribute value
     * 
     */
    public String getElementAttribute(String element, String attributeName,
            String stepDesc) {

        this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element,
                attributeName);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPInstructor_WelcomeInstructorPage
           verifyFooterHyperLinks(String locator, String desc) {
        FindElement element = new FindElement();
        WebElement menuOptions = element.getElement(locator);
        String value = menuOptions.getAttribute("href");
        if (value.startsWith("https")) {
            result = Constants.PASS + ": Verify '" + locator + "' is a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        } else {
            result = Constants.FAIL + ": '" + locator + "' is not a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        }

        return new GLPInstructor_WelcomeInstructorPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPInstructor_WelcomeInstructorPage verifyFooterHyperLinks() {
        // Verify 'Copyright ©2017 Pearson Education Inc. All rights
        // reserved.'Text is present
        verifyText("DashBoardFooterCopyRightText",
                ResourceConfigurations.getProperty("copyRightLinkText"),
                "Verify 'Copyright 2018 Pearson Education Inc. All rights reserved' Text is present.");

        // Verify 'Accessibilty' is a link
        verifyFooterHyperLinks("DashBoardFooterAccessibilityLink",
                "Verify 'Accessibilty' is a link");
        // Verify 'Accessibilty' link text is present
        verifyText("DashBoardFooterAccessibilityLink",
                ResourceConfigurations.getProperty("accessibilityText"),
                "Verify 'Accessibilty' link text is present");

        // Verify 'Privacy Policy' is a link
        verifyFooterHyperLinks("DashBoardPrivacyPolicyLink",
                "Verify 'Privacy Policy' is a link");
        // Verify 'Privacy Policy' link text is present
        verifyText("DashBoardPrivacyPolicyLink",
                ResourceConfigurations.getProperty("privacyPolicyText"),
                "Verify 'Privacy Policy' link text is present");

        // Verify 'Terms & Conditions' is a link
        verifyFooterHyperLinks("DashBoardFooterTermsAndConditionsLink",
                "Verify 'Terms & Conditions' is a link");
        // Verify 'Terms & Conditions' link text is present
        verifyText("DashBoardFooterTermsAndConditionsLink",
                ResourceConfigurations.getProperty("termsConditionsText"),
                "Verify 'Terms & Conditions' link text is present");
        return new GLPInstructor_WelcomeInstructorPage(reportTestObj, APP_LOG);
    }

}
