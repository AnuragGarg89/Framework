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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPInstructor_MasterySettingPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj = null;
    protected String result = "";
    protected String stepDescription = "";
    protected PerformAction performAction = new PerformAction();

    public GLPInstructor_MasterySettingPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author lekh.bahl
     * @date 09 Aug 2017
     * @description :To verify button is disable
     * @return The object of ProductApplication_CoreInstructionsPage
     */
    public GLPInstructor_MasterySettingPage verifyButtonIsDisabled(
            String locator, String value, String message) {
        APP_LOGS.debug("To verify button is disabled:" + locator);
        this.result = this.performAction.execute(ACTION_VERIFY_ISENABLED,
                locator, value);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify text message
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyText(String locator, String text, String stepDesc) {
        this.APP_LOGS.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, locator,
                text);
        if (result.contains("PASS")) {
            result = "PASS: " + locator + " contains the correct text i.e. "
                    + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: " + locator
                    + " does not contains the correct text i.e. " + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;
    }

    /**
     * @author tarun.gupta1
     * @date 14 Sep,2017
     * @description Navigate to Management dashboard
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public GLPInstructor_ManagementDashboardPage
           navigateToInstructorDashboard() {

        APP_LOG.debug("Navigate to Management dashboard");

        try {
            clickOnElement("PreAssessmentMasteryNextBtn",
                    "Click on Next button and verify user sucessfully navigated to Instructor Dashboard Page.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

        return new GLPInstructor_ManagementDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 10 Nov ,17
     * @description Setting slider value to 90
     */
    public GLPInstructor_MasterySettingPage setMasteryScoreTo90() {
        clickOnElement("PreAssessmentMasteryPageSlider", "Click on Slider");
        return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOG);
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click on element
     */
    public void clickOnElement(String locator, String stepDesc) {

        APP_LOG.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        if ("Safari".equalsIgnoreCase(capBrowserName)) {
            this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                    locator);
        } else {
            this.result = this.performAction.execute(ACTION_CLICK, locator);

        }
        logResultInReport(this.result, stepDesc, this.reportTestObj);
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
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Get text of the element
     * @return The object of ProductApplication_courseHomePage
     */
    public String getText(String locator) {
        APP_LOGS.debug("Element is present: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
        return valueText;
    }

    /**
     * @author anuj.tiwari1
     * @date 31 October,2017
     * @description Verify element is present
     * @return The object of ProductApplication_MasterySettingPage
     */
    public GLPInstructor_MasterySettingPage
           verifySliderAttributeMinAndMaxValue() {

        APP_LOGS.debug(
                "Verify that the minimum and maximum value of the slider is 80 and 100 respectively.");

        verifyElementAttributeValue("PreAssessmentMasteryPageSlider", "min",
                ResourceConfigurations.getProperty("sliderMinimumValue"),
                "Verify that the minimum value of the slider is 80.");
        verifyElementAttributeValue("PreAssessmentMasteryPageSlider", "max",
                ResourceConfigurations.getProperty("sliderMaximumValue"),
                "Verify that the maximum value of the slider is 100");
        return this;
    }

    /**
     * @author mukul.sehra
     * @date 10 April ,17
     * @description Verify attribute value.
     */
    public void verifyElementAttributeValue(String element,
            String attributeName, String verifyText, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author lekh.bahl
     * @date 12 July,2017
     * @description Enter input value in textbox
     * @return The object of ProductApplication_LoginPage
     */

    public GLPInstructor_MasterySettingPage enterInputData(String locator,
            String value, String message) {
    	APP_LOG.debug("Enter the input value- " + value);
		if (capBrowserName.equalsIgnoreCase("Safari")) {
			APP_LOG.debug("Entering value on Safari");
			returnDriver().findElement(By.cssSelector(".slider-value")).click();
			this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR, locator, value);
			logResultInReport(this.result, message, this.reportTestObj);
		} else {

			this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
			this.performAction.execute(ACTION_CLEAR_TEXT, locator);
			this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLICK, locator, value);
			logResultInReport(this.result, message, this.reportTestObj);
		}
		return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOG);
	}

    /**
     * @author akshay.chimote
     * @date 19 May, 2017
     * @description To verify the CSS Value of an element
     */

    public String verifyElementCSSValue(String element, String cssName,
            String verifyText, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_CSS_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, cssName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        return this.result;
    }

    /**
     * @author tarun.gupta1
     * @date 14 Sep,2017
     * @description Navigate to welcome screen Instructor
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public GLPInstructor_WelcomeInstructorPage
           navigateToWelcomeScreenFromMasterySetting() {
        APP_LOGS.debug("Navigate to welcome screen");
        try {
            clickOnElement("PreAssessmentMasteryBackBtn",
                    "Click on back button");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPInstructor_WelcomeInstructorPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author tarun.gupta1
     * @date 14 Sep,2017
     * @description Navigate to welcome screen Instructor
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public GLPLearner_CourseViewPage navigateToCourseViewFromCourseLink() {
        APP_LOGS.debug(
                "Navigate to Course View from Mastery Setting by clicking course link");
        try {
            clickOnElement("MasterySettingCourseLink",
                    "Click on course link on mastery setting page");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nisha.pathria
     * @date 21 Nov,2017
     * @description set mastery percentage
     * @return The object of ProductApplication_MasterySettingPage
     */
    public GLPInstructor_MasterySettingPage setMasteryPercentage() {

        APP_LOGS.debug("Set mastery percentage.");

        // Work around for safari browser
        this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                "PreAssessmentMasteryPercentageTextBox", ResourceConfigurations
                        .getProperty("changeMasteryPercentageValue"));
        enterInputData("PreAssessmentMasteryPercentageTextBox",
                ResourceConfigurations
                        .getProperty("changeMasteryPercentageValue"),
                "Set mastery setting percentage to 85%.");
        return this;
    }

    /*
     * @author tarun.gupta1
     * 
     * @date 14 Sep,2017
     * 
     * @description Navigate to welcome screen Instructor
     * 
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public void verifyMasteringScore(String sliderValue, String masteryValue) {
        if (sliderValue.equals(masteryValue)) {
            this.result = Constants.PASS + ": Mastery score :" + masteryValue
                    + " in mastery screen is same as before browser refresh :"
                    + sliderValue;
            logResultInReport(this.result,
                    "Verify Mastery score in mastery screen with the score set in settings page",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Mastery score :" + masteryValue
                    + " in mastery screen is same as before browser refresh :"
                    + sliderValue;
            logResultInReport(this.result,
                    "Verify Mastery score in mastery screen with the score set in settings page",
                    this.reportTestObj);
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String Locator, String Text,
            String Message) {
        APP_LOGS.debug("Element is present: " + Locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator,
                Text);
        return result;
    }

    /**
     * @author pallavi.tyagi
     * @date 30 Nov ,17
     * @description To Get title attribute value
     * 
     */
    public String getElementAttribute(String element, String attributeName,
            String stepDesc) {
        this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element,
                attributeName);
        return this.result;
    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPInstructor_MasterySettingPage
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

        return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPInstructor_MasterySettingPage verifyFooterHyperLinks() {
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
        return new GLPInstructor_MasterySettingPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 31 March ,18
     * @description Click on element
     */
    public void clickOnMasterySlider(String locator, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        WebDriver webDriver = returnDriver();
        if (webDriver instanceof EdgeDriver
                || capBrowserName.equalsIgnoreCase("MicrosoftEdge")) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#myRange")));
        } else {

            this.result = this.performAction.execute(ACTION_CLICK, locator);
        }
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author yogesh.choudhary
     * @param element
     * @param stepDesc
     */
    public void clickByJS(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_CLICK_BY_JS, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }
}