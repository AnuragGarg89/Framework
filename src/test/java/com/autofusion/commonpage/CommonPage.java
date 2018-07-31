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
package com.autofusion.commonpage;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.PostAPI;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

public class CommonPage extends BaseClass implements KeywordConstant {

    protected Logger APP_LOGS = null;
    public ExtentTest reportTestObj;
    public GLP_Utilities RestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private FindElement findElement = new FindElement();
    private JavascriptExecutor js;

    public CommonPage(ExtentTest reportTestObj, Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April, 2018
     * @description Verify element is clickable
     */
    public void verifyElementIsClickable(String elememtLocator,
            String stepDesc) {
        this.result = this.performAction
                .execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE, elememtLocator);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author lekh.bahl
     * @date 12 July, 2017
     * @description Verify page title
     */
    public void verifyPageTitle(String pageTitle, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE, "",
                pageTitle);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click on Tab
     */
    // Needs to be removed
    public void verifyClickOnElement(String element, String stepDesc) {

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE,
                element);
        this.result = this.performAction.execute(ACTION_CLICK, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click on Tab
     */
    public void clickOnElement(String element, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_CLICK, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 13 July ,17
     * @description Press Tab key
     */
    public void pressTabKey(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_PRESS_TAB_KEY, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Verify the content of listed tabs
     */
    public void verifyGetTabContent(String element, String tabName,
            String contentValue) {
        String content = ResourceConfigurations.getProperty(contentValue);

        if (content.contains(Constants.FAIL)) {

            this.result = content;

            logResultInReport(this.result,
                    "Error while comparing UserList data with WebList data",
                    this.reportTestObj);

            return;
        }

        try {
            String[] searchContent = content.split("\\|");
            List<WebElement> tabRealContent = this.findElement
                    .getElements(element);
            for (int i = 0; i < tabRealContent.size(); i++) {
                if (tabRealContent.get(i).getText()
                        .equalsIgnoreCase(searchContent[i])) {
                    this.result = Constants.PASS + ": Expected UserText: '"
                            + searchContent[i]
                            + "' in list matches with actualText '"
                            + tabRealContent.get(i).getText() + "' in WebList";
                    logResultInReport(this.result,
                            "Verify " + searchContent[i] + " is displayed",
                            this.reportTestObj);
                } else {
                    this.result = Constants.FAIL + ": Expected UserText: '"
                            + searchContent[i]
                            + "' in list doesn't matches with actualText '"
                            + tabRealContent.get(i).getText() + "' in WebList";
                    ;
                    logResultInReport(this.result,
                            "Verify " + searchContent[i] + " is displayed",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {

            this.result = Constants.FAIL
                    + "Error while comparing UserList data with WebList data because of: "
                    + e;

            logResultInReport(this.result,
                    "Error while comparing UserList data with WebList data",
                    this.reportTestObj);

        }
    }

    /**
     * @author mukul.sehra
     * @date 10 April ,17
     * @description Verify attribute value.
     */
    public void verifyElementAttributeValue(String element,
            String attributeName, String verifyText, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April, 2017
     * @description Verify Element Present/display
     */
    public String verifyElementPresent(String element, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        if (this.result.contains(Constants.PASS)) {
            return Constants.PASS;
        } else {
            return Constants.FAIL;
        }

    }

    /**
     * @author mohit.gupta5
     * @date 17 April ,17
     * @description Verify Element Text
     */
    public String verifyElementContainsText(String element, String text,
            String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author lekh.bahl
     * @date 17 April ,17
     * @description Verify Element link or not
     */
    public void verifyElementIsLink(String element, String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.result = this.performAction.execute(ACTION_VERIFY_HYPERLINK,
                element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 07 April ,17
     * @description Enter Valid Data in the Search Text Box.
     */
    public void enterInputData(String element, String text, String testDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                element, text);
        logResultInReport(this.result, testDesc, this.reportTestObj);
    }

    /**
     * @author sagar.pawar
     * @date 04 May ,17
     * @description Clear Session storage
     */
    public void clearSessionStorage() {
        String windowSessionStorage = "window.sessionStorage.clear();";
        this.js = (JavascriptExecutor) returnDriver();
        this.js.executeScript(String.format(windowSessionStorage));

    }

    /**
     * @author sagar.pawar
     * @date 04 May ,17
     * @description Clear Local storage
     */
    public void clearLocalStorage() {
        String windowStorageClear = "window.localStorage.clear();";
        this.js = (JavascriptExecutor) returnDriver();
        this.js.executeScript(String.format(windowStorageClear));

    }

    /**
     * @author nisha.pathria
     * @date 2 May, 2017
     * @description To verify Status Dropdown Values and functionality
     */

    public void selectDropdownValue(String element, String text,
            String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.result = this.performAction
                .execute(ACTION_SELECT_DROPDOWN_VISIBLE_TEXT, element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author lekh.bahl
     * @date 07 April ,17
     * @description Enter Valid Data in the Search Text Box.
     */
    public String verifyText(String element, String text, String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element,
                text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author nitish.jaiswal
     * @date 07 April ,17
     * @description Enter Valid Data in the Search Text Box.
     */
    public String verifyTextContains(String element, String text,
            String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author lekh.bahl
     * @date 05 May ,17
     * @description Get text from any element
     */
    public String getText(String element) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, element);
        return valueText;
    }

    /**
     * @author nisha.pathria
     * @date 2 May, 2017
     * @description To verify field is not editable
     */

    public void verifyNotEditable(String elementLocator, String stepDesc) {

        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_IS_NOT_EDITABLE, elementLocator);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author nisha.pathria
     * @date 2 May, 2017
     * @description To verify status Dropdown default Value is Public
     */

    public void verifyDefaultValueSelected(String dropDown, String defaultValue,
            String stepDesc) {

        this.result = this.performAction.execute(ACTION_VERIFY_ISSELECTED,
                dropDown, defaultValue);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author pallavi.tyagi
     * @date 28 April, 2017
     * @description Verify Element Not Present/display
     */
    public void verifyElementNotPresent(String element, String stepDesc) {

        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author nisha.pathria
     * @date 10 May, 2017
     * @description To verify button is disabled or enabled
     */
    public void verifyButtonDisabled(String elementLocator, String value,
            String stepDesc) {

        this.result = this.performAction.execute(ACTION_VERIFY_ISENABLED,
                elementLocator, value);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    public void verifyFocusOnElement(String element, String stepDesc) {
        this.result = this.performAction
                .execute(ACTION_VERIFY_IS_ELEMENT_FOCUSED, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 12 May, 2017
     * @description Get field width.
     */
    public int verifyFieldWidth(String element) {
        int elementWidth = 0;
        try {
            WebElement element1 = this.findElement.getElement(element);
            elementWidth = element1.getSize().getWidth();
        } catch (Exception e) {

            this.result = Constants.FAIL
                    + " : Could not get the width of the field because of : "
                    + e;
        }
        return elementWidth;

    }

    /**
     * @author nisha.pathria
     * @date 15 May, 2017
     * @description To select each dropdown value one by one
     */

    public void selectAllDropdownValuesOneByOne(String elementLocator,
            String dropdownValues, String DropdownList,
            String expectedDropdownSize) {

        String dropdownSize = this.performAction
                .execute(GET_NUMBER_OF_ELEMENT_IN_LIST, DropdownList);

        if (dropdownSize.contains(Constants.FAIL)) {
            this.result = dropdownSize;
            logResultInReport(this.result,
                    " Error while finding element ResourcesTypeOptions",
                    this.reportTestObj);
        } else {
            int actualDropDownLength = Integer.parseInt(dropdownSize);
            int expectedDropdownLength = Integer.parseInt(expectedDropdownSize);

            if (expectedDropdownLength == actualDropDownLength) {

                this.result = Constants.PASS
                        + ": Expected dropdown size and actual dropdown size is same";
                logResultInReport(this.result,
                        "Expected dropdown size and actual dropdown size match",
                        this.reportTestObj);

                for (int i = 0; i < expectedDropdownLength; i++) {
                    this.selectDropdownValue(elementLocator,
                            dropdownValues.split("\\|")[i],
                            "Verify " + dropdownValues.split("\\|")[i]
                                    + " value is selected");

                }
            } else {
                this.result = Constants.FAIL
                        + ": Expected dropdown size and actual dropdown size is not same";

                logResultInReport(this.result,
                        "Expected dropdown size and actual dropdown size does not match",
                        this.reportTestObj);
            }

        }

    }

    /**
     * @author tarun.gupta1
     * @date 19 May, 2017
     * @description Verify Element is checked by default
     * @return Object
     */
    public void verifyCheckboxIsSelected(String element, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction
                .execute(ACTION_VERIFY_CHECKBOX_IS_SELECTED, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author mayank.mittal
     * @date 19 May, 2017
     * @description Verify Element is unchecked
     * @return Object
     */
    public void verifyCheckboxIsNotSelected(String element, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction
                .execute(ACTION_VERIFY_CHECKBOX_IS_NOT_SELECTED, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

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
     * @date 22 May, 2017
     * @description To generate random string of numbers
     */

    public String generateRandomStringOfNumbers(int numofChars) {
        String inputNumbers = "1234567890";
        StringBuilder inputString = new StringBuilder();
        SecureRandom rnd = new SecureRandom();
        while (inputString.length() < numofChars) {
            // string.
            int index = (int) (rnd.nextDouble() * inputNumbers.length());
            inputString.append(inputNumbers.charAt(index));
        }
        String FinalStr = inputString.toString();
        APP_LOG.info(FinalStr);
        return FinalStr;
    }

    /**
     * @author tarun.gupta1
     * @date 22 May, 2017
     * @description To generate random string of Alphabets
     */
    public String generateRandomStringOfAlphabets(int numofChars) {
        try {
            String inputAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble() * inputAlphabets.length());
                inputString.append(inputAlphabets.charAt(index));
            }
            String FinalStr = inputString.toString();
            APP_LOG.info(FinalStr);
            return FinalStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 22 May, 2017
     * @description To generate random string of alphanumeric character
     */
    public String generateRandomStringOfAlphaNumericCharacters(int numofChars) {
        try {
            String inputAlphaNumerics = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble()
                        * inputAlphaNumerics.length());
                inputString.append(inputAlphaNumerics.charAt(index));
            }
            String FinalStr = inputString.toString();
            APP_LOG.info(FinalStr);
            return FinalStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 23 May, 2017
     * @description To generate random string of Special character
     */
    public String generateRandomStringOfSpecialCharacters(int numofChars) {
        try {
            String inputSpecialChars = "!@#$%^&*()_,.<>/?][{};:'";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble()
                        * inputSpecialChars.length());
                inputString.append(inputSpecialChars.charAt(index));
            }
            String FinalStr = inputString.toString();
            return FinalStr;
        } catch (RuntimeException e) {
            APP_LOG.error(
                    "unable to generate generateRandomStringOfSpecialCharacters "
                            + e);
            return null;
        }
    }

    /**
     * @author nisha.pathria
     * @date 22 May, 2017
     * @description To get attribute value of element
     */
    public String getAttribute(String element) {

        String text = this.performAction.execute(ACTION_GET_ATTRIBUTE, element);
        if (text.contains(Constants.FAIL)) {
            this.result = text;
            logResultInReport(this.result, text, this.reportTestObj);
        } else {
            this.result = Constants.PASS + ": Attribute value is " + "'" + text
                    + "'";
            logResultInReport(this.result, "Attribute value is " + text,
                    this.reportTestObj);
        }
        return text;

    }

    /**
     * @author tarun.gupta1
     * @date 24 May, 2017
     * @description Verify Element is Cleared
     * @return Object
     */
    public void verifyTextBoxIsCleared(String element, String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_BACK_KEY, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author tarun.gupta1
     * @date 24 May, 2017
     * @description Verify Element is Cleared
     * @return Object
     */
    // Method to be removed(This function are using in Resource page)
    public void verifyClickOnText(String element, String data,
            String stepDesc) {

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_CLICK_ON_TEXT, element,
                data);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author tarun.gupta1
     * @date 24 May, 2017
     * @description Verify Element is Cleared
     * @return Object
     */
    public void clickOnElementInListWithText(String element, String data,
            String stepDesc) {
        this.result = this.performAction.execute(ACTION_CLICK_ON_TEXT, element,
                data);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author nisha.pathria
     * @date 23 May, 2017
     * @description To get default Value selected in dropdown
     */

    public String getDefaultValueSelected(String dropDown, String stepDesc) {

        WebElement element = this.findElement.getElement(dropDown);
        try {
            Select list = new Select(element);
            String defaultSelectedValue = list.getFirstSelectedOption()
                    .getText();
            return defaultSelectedValue;
        } catch (Exception e) {
            return this.result = Constants.FAIL
                    + " : Could not get the selected value because of : " + e;
        }

    }

    /**
     * @author tarun.gupta1
     * @date 26 May, 2017
     * @description Verify ALert is present
     * @return Object
     */
    public void verifyAlertPresent(String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_ALERT_PRESENT);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author pallavi.tyagi
     * @date 6 june, 2017
     * @description To generate random email
     */
    public String generateRandomEmail() {
        String randomEmailId = this.generateRandomStringOfAlphabets(3) + "@"
                + this.generateRandomStringOfAlphabets(3) + ".com";
        return randomEmailId;
    }

    /**
     * @author nitish.jaiswal
     * @date 5 June 2017
     * @description :hitting API to unblock the given user
     */

    public void hitPostAPI(String apiUrl) {
        try {
            PostAPI.httpPost(apiUrl);

        } catch (AssertionError e) {
            APP_LOG.info(
                    "Unexpected error while deleting API for unblocking account: "
                            + e);
        } catch (Exception t) {
            APP_LOG.info(
                    "Unexpected error while deleting API for unblocking account: "
                            + t);
        }

    }

    /**
     * @author tarun.gupta1
     * @date 7 September 2017
     * @description :delete test user
     */
    public void deleteTestUser(String userName) {
        RestUtil.unpublishSubscribedCourseDatabase(userName, null);
    }

    /**
     * @author lekh.bahl
     * @date 03 Aug ,17
     * @description Click on list element by index
     */
    public void clickElementByIndex(String element, String position,
            String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.result = this.performAction.execute(ACTION_CLICK_INDEX_POSITION,
                element, position);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author lekh.bahl
     * @date 03 Aug ,17
     * @description Get current URL
     */
    public String getCurrentURL() {// (String element) {
        this.APP_LOGS.debug("Get current URL");
        // this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
        // element);
        this.result = this.performAction.execute(ACTION_GET_CURRENT_URL, "");
        return this.result;
    }

    /**
     * @author nitish.jaiswal
     * @date 17 Sep, 2017
     * @description To verify Element is active/selected
     */

    public void verifyElementSelected(String element, String stepDesc) {

        this.result = this.performAction.execute(ACTION_VERIFY_ISSELECTED,
                element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author sumit.bhardwaj
     * @param element
     *            --> Name of Element
     * @param stepDesc-->
     *            Description of Steps
     * 
     * @description --> clicks on element and handle StaleException if occurred.
     * 
     */

    public void clickOnElementAndHandleStaleException(String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction
                .execute(CLICK_AND_HANDLE_STALE_EXCEPTION, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author sumit.bhardwaj
     * @return --> Return blank value after attempting question
     * @description --> Attempts Diagnostic Test
     * 
     */
    public void getQuestionTypeAndAttempt() {
        String element = "DiagnosticQuestionType";

        if (verifyElementPresentWithOutLog("DiagnosticGotItButton")
                .contains(Constants.PASS)) {

            clickOnElement("DiagnosticGotItButton", "Diagnostic Got it button");

        }

        String value = getAttribute(element, "id");

        try {

            List<WebElement> numberOfQuestion = findElement
                    .findListAndHandleStaleElementException(element);

            try {
                if (numberOfQuestion.size() > 1) {

                    List<WebElement> list = findElement
                            .findListAndHandleStaleElementException(
                                    "DiagnosticDisabledFibBox");
                    for (WebElement ele : list) {
                        Actions actions = new Actions(returnDriver());
                        actions.moveToElement(ele);
                        actions.click();
                        actions.sendKeys("Some Name");
                        actions.build().perform();
                        closeMathPalette();
                    }
                    scrollWebPage(0, 200);
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button");
                    APP_LOG.info("Attempted Multi-Part type question");
                    return;
                }
            } catch (Exception e) {
                APP_LOG.error("Error while attemoting Diagnostic test " + e);
                return;
            }

            if (value.contains("FIB")) {

                List<WebElement> el = findElement
                        .findListAndHandleStaleElementException(
                                "FIBFreeResponse");// FIBFreeResponse
                if (el != null) {
                    if (el.size() != 0) {

                        List<WebElement> list = findElement
                                .findListAndHandleStaleElementException(
                                        "FIBFreeResponse");// DiagnosticDisabledFibBox
                        for (WebElement ele : list) {
                            Actions actions = new Actions(returnDriver());
                            actions.moveToElement(ele);
                            actions.click();
                            actions.sendKeys("Some Name");
                            actions.build().perform();
                            closeMathPalette();
                        }

                        clickOnElementAndHandleStaleException("SubmitButton",
                                "Click on Submit button");
                        return;
                    }
                }

                List<WebElement> e = findElement
                        .findListAndHandleStaleElementException("FIBDropDown");
                if (e.size() != 0) {

                    clickOnElementAndHandleStaleException(
                            "DiagnosticQuestionDropDown",
                            "Click on DropDownButton button on FIB_2 question");
                    clickOnElementAndHandleStaleException(
                            "DiagnosticDropDownMenu",
                            "Click on QuestionTypeDropDown button on FIB_2 question");
                    closeMathPalette();
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button after attempting FIB_2 question");
                    APP_LOG.info("Attempted FIB_2 type question");

                    return;

                }

            } else if (value.contains("McqSa_4")
                    || "McqSa_undefined".equalsIgnoreCase(value)) {
                clickOnElementAndHandleStaleException("DiagnosticSelectRadio",
                        "Click on SelectFirstRadio button after attempting McqSa_4 question");
                closeMathPalette();
                clickOnElementAndHandleStaleException("SubmitButton",
                        "Click on Submit button after attempting McqSa_4 question");
                APP_LOG.info("Attempted McqSa_4 type question");

            } else if (value.contains("McqMa_6")
                    || "McqMa_undefined".equalsIgnoreCase(value)) {

                WebDriver webDriver = returnDriver();
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript(
                        "return document.querySelector(\"label>input[type='checkbox']\").click();");
                logResultInReport(this.result,
                        "Click on SelectFirstCheck button after attempting McqMa_5 question",
                        this.reportTestObj);
                scrollWebPage(0, 400);
                closeMathPalette();
                clickOnElementAndHandleStaleException("SubmitButton",
                        "Click on Submit button after attempting McqMa_5 question");
                APP_LOG.info("Attempted McqMa_5 type question");

            } else {

            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting Diagnostic test " + e);
            return;
        }

    }

    /**
     * @author sumit.bhardwaj
     * @param element
     * @return --> Id attribute
     */
    public String getAttribute(String element, String attributeName) {

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element,
                attributeName);
        String text = this.performAction.execute(ACTION_GET_ATTRIBUTE, element);
        if (text.contains(Constants.FAIL)) {
            this.result = text;
            logResultInReport(this.result, text, this.reportTestObj);
        } else {
            this.result = Constants.PASS + ": Attribute value is " + "'" + text
                    + "'";
            logResultInReport(this.result, "Attribute value is " + text,
                    this.reportTestObj);
        }
        return text;

    }

    /**
     * @author sumit.bhardwaj Scrolls the webpage upto pixels passed in
     *         parameter.
     * 
     * @param pixelToScrollHorizontally
     *            : pixel to scroll horizontally
     * @param pixelToScrollVertically
     *            : pixel to scroll vertically
     * @return Pass or fail with the cause.
     */
    public String scrollWebPage(int pixelToScrollHorizontally,
            int pixelToScrollVertically) {

        System.out.println("Scrolling through web page ... ");
        BaseClass base = new BaseClass() {
        };
        WebDriver dr = base.returnDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) dr;
            js.executeScript("scroll(" + pixelToScrollHorizontally + ","
                    + pixelToScrollVertically + ")");
        }

        catch (Exception e) {

            // Log the exception
            APP_LOG.error("Error while scrolling through the web page : " + e);

            return "Fail : Error while scrolling through the web page : " + e;
        }

        return "Pass : Scrolled through the web page";

    }

    /**
     * @author sumit.bhardwaj
     * @param element
     *            --> Element to check visiblity
     * @return --> Pass if present else Fail
     */
    public String verifyElementPresentWithOutLog(String element) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                element);
        if (this.result.contains(Constants.PASS)) {
            return Constants.PASS;
        } else {
            return Constants.FAIL;
        }

    }

    /**
     * @author mukul.sehra
     * @description Press Enter Key
     */
    public void pressEnterKey() {
        performAction.execute(ACTION_PRESS_ENTER_KEY);
    }

    /**
     * @author mukul.sehra
     * @param webElementLocator
     *            --> element to scroll into
     */
    public void scrollIntoView(String webElementLocator) {
        this.performAction.execute(ACTION_SCROLL_INTO_VIEW, webElementLocator);
    }

    /**
     * @author Abhishek. Sharda
     * @date 10 April ,17
     * @description Verify videos
     */
    public void verifyVideoPlayback(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_VIDEOS, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author Abhishek. Sharda
     * @date 10 April ,17
     * @description Verify images
     */
    public void verifyimages(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_IMAGES, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author Abhishek.Sharda
     * @date 12 July, 2017
     * @description Verify page title
     */
    public void assertPageTitle(String pageTitle, String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE, "",
                pageTitle);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author sumit.bhardwaj
     * @description Close math palette is appears
     */

    public void closeMathPalette() {
        try {
            if (verifyElementPresentWithOutLog(
                    "DiagnosticMathPaletteCrossButton")
                            .contains(Constants.PASS)) {

                clickOnElement("DiagnosticMathPaletteCrossButton",
                        "Math Pallete close button");

                APP_LOG.info("Math Palette closed");
            }
            APP_LOG.info("Math Palette not appeared");
        } catch (Exception e) {
            APP_LOG.error("Error while closing Math Palette");
        }

    }

    /**
     * @author mukul.sehra
     * @date 10 April ,17
     * @description Verify "Search" is displayed as the placeholder.
     */
    public void getElementAttributeValue(String element, String attributeName,
            String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

}
