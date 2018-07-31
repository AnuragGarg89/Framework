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
package com.autofusion.keywords;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.constants.Constants;
import com.autofusion.util.DateUtil;

public class VerificationKeywords extends Keyword {

    public String locatorId;
    public WebElement element;
    public String inputValue;
    public String componentName;
    private FindElement FindElement = new FindElement();
    private String result = null;

    public VerificationKeywords(Logger log) {
        APP_LOG = log;
    }

    public String verifyNotVisible(Map<String, String> argsList) {

        return Constants.PASS;
    }

    public String verifyAlertPresent(Map<String, String> argsList) {
        APP_LOG.debug("Func verifyAlertPresent");
        return isAlertPresent();
    }

    public String verifyAlertNotPresent(Map<String, String> argsList) {
        APP_LOG.debug("Func verifyAlertNotPresent");
        String isAlertPresent = isAlertPresent();
        if (!isAlertPresent.contains(Constants.PASS)) {
            return Constants.PASS + ": Alert is found and Switched.";
        } else {
            return isAlertPresent;
        }
    }

    public String verifySelectedValue(Map<String, String> argsList) {
        APP_LOG.info("Inside verifySelectedValue");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            return this.verifySelectedValue(this.element, this.inputValue);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while getting Element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifySelectedValue(WebElement element, String inputString) {
        APP_LOG.debug(" verifySelectedValue " + inputString);

        try {
            actualDataPresentOnUi = element.getText().trim();
            if (!element.getText().trim()
                    .equalsIgnoreCase(inputString.trim())) {
                return Constants.PASS + ": Selected InputText - '" + inputString
                        + "' is matched/found with Selected Expected Text - '"
                        + actualDataPresentOnUi + "'";
            } else {
                return Constants.FAIL + ": Selected InputText - '" + inputString
                        + "' is not matched/found with Selected Expected Text - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            APP_LOG.debug(" VerifySelectedValue " + e);
            return Constants.FAIL
                    + ": Unexpected error while selecting Element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyText(WebElement element, String inpuString) {
        APP_LOG.debug(" verifyText " + inpuString);

        String textValue = element.getText().trim();
        actualDataPresentOnUi = textValue;
        try {
            if (textValue.trim().equals(inpuString)) {
                return Constants.PASS + ": InputText - '" + inpuString
                        + "' is matched with Actual Text - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": InputText - '" + inpuString
                        + "' is not matched with Actual Text - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            APP_LOG.debug(" verifyTextById " + e);
            return Constants.FAIL + ": Error while Verifying Text for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyTextContains(WebElement element, String inpuString) {
        APP_LOG.debug(" verifyTextContains => " + inpuString);

        try {
            String textValue = element.getText();
            actualDataPresentOnUi = textValue;
            if (this.browser.trim().equalsIgnoreCase("Safari")) {
                if (actualDataPresentOnUi.trim().replaceAll("(\\s)+", " ")
                        .contains(
                                inpuString.trim().replaceAll("(\\s)+", " "))) {
                    return Constants.PASS + ": Element Text - '"
                            + actualDataPresentOnUi + "' contains - '"
                            + inpuString + "'";
                } else {
                    collectFailureMessage(
                            "Expected value is not matching with Actual value.");
                    return Constants.FAIL + ": Element Text - '"
                            + actualDataPresentOnUi + "' does not contain - '"
                            + inpuString + "'";
                }
            } else {
                if (textValue.trim().contains(inpuString)) {
                    return Constants.PASS + ": Element Text - '"
                            + actualDataPresentOnUi + "' contains - '"
                            + inpuString + "'";
                } else {
                    collectFailureMessage(
                            "Expected value is not matching with Actual value.");
                    return Constants.FAIL + ": Element Text - '"
                            + actualDataPresentOnUi + "' does not contain - '"
                            + inpuString + "'";
                }
            }
        } catch (Exception e) {
            failureErrorMessageCollector = e.toString();
            APP_LOG.debug(" verifyTextById " + e);
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL
                    + ": Unexpected Error while Verifying Text for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyisDispalyed(Map<String, String> argsList) {
        APP_LOG.info("To check if element is displayed");

        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            if (this.element.isDisplayed() == true) {
                return Constants.PASS + ": Element on UI- '" + this.elementId
                        + "' is displayed";
            } else {
                return Constants.PASS + ": Element on UI- '" + this.elementId
                        + "' is not displayed";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String verifyElementPresent(Map<String, String> argsList) {
        APP_LOG.debug("Func:verifyElementPresent");

        this.locatorId = argsList.get("ElementId");
        try {
            return isElementPresent(this.locatorId);
        } catch (Exception e) {
            APP_LOG.debug("Func:verifyElementPresent || " + e);
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL
                    + ": Unexpected error while Finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyElementNotPresent(Map<String, String> argsList)
            throws InterruptedException {
        this.locatorId = argsList.get("ElementId");
        try {

            if (isElementPresent(this.locatorId).contains(Constants.PASS)) {
                collectFailureMessage("Element is present");
                return Constants.FAIL + ": Element - '" + this.locatorId
                        + "' is present on UI";
            } else {
                return Constants.PASS + ": Element - '" + this.locatorId
                        + "' is not present on UI";
            }

        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL
                    + ": Unexpected error while Verfying Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyNotEmpty(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyNotEmpty");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String pageValueString = this.element.getText().trim();
            actualDataPresentOnUi = pageValueString;
            if (!"".equals(pageValueString)) {
                return Constants.PASS + ": Element with value - "
                        + actualDataPresentOnUi + "is not null";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": Element does not contain any text";
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String verifyisEnabled(Map<String, String> argsList) {
        APP_LOG.info("To check if element is enabled");

        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            if ("yes".equalsIgnoreCase(this.inputValue)) {
                if (this.element.isEnabled()) {
                    return Constants.PASS + ": Element- '" + this.elementId
                            + "' is Enabled";
                } else {
                    return Constants.FAIL + ": Element- '" + this.elementId
                            + "' is not Enabled";
                }
            } else if ("no".equalsIgnoreCase(this.inputValue)) {
                if (this.element.isEnabled()) {
                    return Constants.FAIL + ": Element- '" + this.elementId
                            + "' is not Disabled";
                } else {
                    return Constants.PASS + ": Element- '" + this.elementId
                            + "' is Disabled";
                }
            }

            else {
                return Constants.FAIL + ": Input value given which is-"
                        + this.inputValue
                        + "is not correct, kindly give YES or NO as input to check for Enabled or Disabled respectively";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String verifyIsEnabledForList(Map<String, String> argsList) {
        APP_LOG.info("To check if element is enabled in List.");
        this.elementId = argsList.get("ElementId");
        try {
            this.elements = getElements(this.elementId);
            int i = 0;
            for (WebElement ele : elements) {
                if (ele.isEnabled()) {
                    APP_LOG.debug(ele.getText() + i + " is enabled");
                } else {
                    APP_LOG.debug(ele.getText() + i + " is disabled");
                    return Constants.FAIL + ": Error while finding Element - '"
                            + this.elementId + "' : ";
                }
                i++;
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
        }
        return Constants.PASS + ": Input value given which is-"
                + this.inputValue + " Test Case is passed. Element is enabled";
    }

    public String verifyIsDisabledForList(Map<String, String> argsList) {
        APP_LOG.info("To check if element is enabled in List.");
        this.elementId = argsList.get("ElementId");
        try {
            this.elements = getElements(this.elementId);
            int i = 0;
            for (WebElement ele : elements) {
                if (ele.isEnabled()) {
                    APP_LOG.debug(ele.getText() + i + " is enabled");
                } else {
                    APP_LOG.debug(ele.getText() + i + " is disabled");
                    return Constants.PASS + ": Input value given which is-"
                            + " Test Case is passed. Element is enabled";

                }
                i++;
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
        }
        return Constants.FAIL + ": Error while finding Element - '"
                + this.elementId + "' : ";
    }

    public String verifyEmpty(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyEmpty");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String pageValueString = this.element.getText().trim();
            if ("".equals(pageValueString)) {
                return Constants.PASS + ": Element- '" + this.elementId
                        + "' is Empty";
            } else {
                return Constants.PASS + ": Element- '" + this.elementId
                        + "' is not Empty";
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String verifyHighLightElementByStyle(Map<String, String> argsList) {
        WebDriver webDriver = returnDriver();
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: highLightElement =>locatorId ="
                + argsList.get("ElementId") + " || inputValue = "
                + argsList.get("InputValue"));

        try {
            this.element = getElement(this.locatorId);
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].setAttribute('style','"
                    + this.inputValue + "')", this.element);
            Thread.sleep(1000);
            js.executeScript("arguments[0].setAttribute('style','')",
                    this.element);
        } catch (Exception e) {
            APP_LOG.debug("Func: highLightElement " + e);
            collectFailureMessage(
                    "Exception during highlighting of text || Exception:" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

        return Constants.PASS + ": Element- '" + this.elementId
                + "' is highlighted";
    }

    public String verifyAttributeValue(Map<String, String> argsList) {
        APP_LOG.info("inside verifyInputText");
        this.locatorId = argsList.get("ElementId");
        String attributeName = argsList.get("ComponentName");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String defaulText = this.element.getAttribute(attributeName);
            actualDataPresentOnUi = defaulText;
            APP_LOG.info("Default Text is :" + defaulText);
            if (defaulText.trim().contains(this.inputValue)) {
                return Constants.PASS + ": Attribute value - '"
                        + this.inputValue
                        + "' is matched with Expected attribute value - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": Attribute value - '"
                        + this.inputValue
                        + "' is not matched with Expected attribute value - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.locatorId + "' : " + e;
        }
    }

    public String verifyText(Map<String, String> argsList) {
        APP_LOG.info("Inside type");
        this.locatorId = argsList.get("ElementId");
        try {
            this.element = getElement(this.locatorId);
            return this.verifyText(this.element, argsList.get("InputValue"));
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error occured while Finding element:- '"
                    + this.locatorId + "' because of : " + e;
        }
    }

    public String verifyTextContains(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyTextContains");
        this.locatorId = argsList.get("ElementId");
        try {
            this.element = getElement(this.locatorId);
            return this.verifyTextContains(this.element,
                    argsList.get("InputValue"));
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error occured while Finding element:- '"
                    + this.locatorId + "' because of : " + e;
        }
    }

    public String verifyTitle(Map<String, String> argsList) {
        WebDriver webDriver = returnDriver();
        APP_LOG.info("inside verifyTitle");

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            actualDataPresentOnUi = webDriver.getTitle();
            if (webDriver.getTitle().equalsIgnoreCase(this.inputValue)) {
                return Constants.PASS + ": Expected Title - '" + this.inputValue
                        + "' is matched with actual title - '"
                        + actualDataPresentOnUi + "'";
            } else {
                failureErrorMessageCollector = "Expected: " + this.inputValue
                        + " || Actual=" + webDriver.getTitle();
                return Constants.FAIL + ": Expected Title - '" + this.inputValue
                        + "' is not matched with actual title - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while getting Title for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @description Verify text on input field type.
     * @param log
     *            Application log
     * @param args
     *            arguments
     */
    public String verifyInputText(Map<String, String> argsList) {
        APP_LOG.info("inside verifyInputText");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String defaulText = this.element.getAttribute("value");
            actualDataPresentOnUi = defaulText;
            APP_LOG.info("Default Text is :" + defaulText);
            if (actualDataPresentOnUi.equals(this.inputValue)) {
                return Constants.PASS + ": InputText for Element- '"
                        + this.inputValue
                        + "' is matched with Expected Text - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": InputText for Element- '"
                        + this.inputValue
                        + "' is not matched with Expected Text - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyIsLinkBroken(Map<String, String> argsList) {
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("Func: verifyIsLinkBroken =>elementId =" + this.locatorId
                + " || inputValue = " + this.inputValue);

        this.element = getElement(this.locatorId);
        String attributeValue = this.element.getAttribute(this.inputValue);
        APP_LOG.debug("Func: verifyIsLinkBroken => attributeValue = "
                + attributeValue);
        HttpURLConnection connection;
        try {
            URL url = new URL(attributeValue);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int response = connection.getResponseCode();
            connection.disconnect();
            if (response == 200) {
                return Constants.PASS;
            } else {
                collectFailureMessage("Link is broken");
                return Constants.FAIL;
            }
        } catch (IOException e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            APP_LOG.debug("Func: verifyIsLinkBroken " + e);
            return Constants.FAIL;
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            APP_LOG.debug("Func: verifyIsLinkBroken " + e);
            return Constants.FAIL;
        }
    }

    /**
     * @description To verify the element in the list of elements.
     * @param argsList
     *            arguments list
     * @return pass/fail
     */
    public String verifyTextContainsInList(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyTextContains");

        elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            elements = getElements(elementId);
            for (WebElement ele : elements) {
                actualDataPresentOnUi = ele.getText().trim();
                if (actualDataPresentOnUi
                        .equalsIgnoreCase(this.inputValue.trim())) {
                    return Constants.PASS + ": InputText for list with value- '"
                            + this.inputValue
                            + "' is matched with Expected Text - '"
                            + actualDataPresentOnUi + "'";
                }
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.FAIL + ": InputText for list with value- '"
                + this.inputValue + "' is not matched with Expected Text - '"
                + actualDataPresentOnUi + "'";
    }

    /**
     * @description To verify any particular element in the list of elements
     *              with index position.
     * @param argsList
     *            elementId inputValue indexPosition
     * @return pass/fail
     */
    public String
           verifyTextContainsInListByIndex(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyTextContains");

        elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        int indexPosition = Integer.valueOf(argsList.get("IndexPosition"));

        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            elements = getElements(elementId);
            actualDataPresentOnUi = elements.get(indexPosition).getText()
                    .trim();
            if (actualDataPresentOnUi.equalsIgnoreCase(this.inputValue)) {
                return Constants.PASS
                        + ": InputText for list at Index with value- '"
                        + indexPosition + ":" + this.inputValue
                        + "' is matched with Expected Text - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL
                        + ": InputText for list at Index with value- '"
                        + indexPosition + ":" + this.inputValue
                        + "' is not matched with Expected Text - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author richa.bajaj
     * @date 27 November,16
     * @description On mouse hover the font weight should change from normal to
     *              bold
     * @return PASS/FAIL
     */
    public String verifyFont(WebElement element) {
        APP_LOG.debug("Func:Find Value");
        try {
            String fontSize = element.getCssValue("font-weight");
            APP_LOG.info("Font Size  -> " + fontSize);
            return fontSize;

        } catch (Exception e) {
            APP_LOG.debug("Func:Static Click Exception=" + e);
            return Constants.FAIL + ": Error while verifying Font - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyFontWeight(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
            List<WebElement> elements = getElements(this.locatorId);
            APP_LOG.info(elements.size());

            int indexPos = Integer.valueOf(this.inputValue);
            return this.verifyFont(elements.get(indexPos));
        } catch (Exception e) {
            APP_LOG.debug("Verify Font wieght || " + e);
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 27 November,16
     * @description Validate date should be in format DDD DD MMM, YYYY and it
     *              Should be always future date
     * @return PASS/FAIL
     */
    public String verifyDate(Map<String, String> argsList) {
        APP_LOG.debug("Verify date format");

        try {
            elementId = argsList.get("ElementId");
            this.element = getElement(this.elementId);
            String textValue = this.element.getText();
            String isFutureDate = DateUtil.validateFutureDateFormat(textValue);
            if (isFutureDate.contains(Constants.PASS)) {
                return isFutureDate;
            } else {
                collectFailureMessage("Date format is not verified");
                return isFutureDate;
            }
        } catch (Exception e) {
            APP_LOG.debug("Verify date format || " + e);
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String verifyIsClickable(Map<String, String> argsList) {
        WebDriver webDriver = returnDriver();
        APP_LOG.info("inside verifyIsClickable");
        this.elementId = argsList.get("object");
        this.inputValue = argsList.get("data");

        try {
            int dataInt = (int) Double.parseDouble(this.inputValue);
            this.element = getElement(this.elementId);
            // code for explicit wait
            WebDriverWait wait = new WebDriverWait(webDriver, dataInt);
            wait.until(ExpectedConditions.elementToBeClickable(this.element));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.tagName(this.element.getTagName())));

        } catch (TimeoutException e) {
            APP_LOG.debug(
                    "TimeoutException comes when veryfing element is clickable",
                    e);
            return Constants.FAIL
                    + ": Timed out for element while verifying element - '"
                    + this.elementId + "' is clikable : " + e;
        } catch (Exception e) {
            APP_LOG.error("Exception comes when veryfing element is clikable",
                    e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.PASS + ": Element - '" + this.elementId
                + "' is Clickable.'";
    }

    public String verifyisItalic(Map<String, String> argsList) {
        APP_LOG.info("To check if element is Italic");

        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");
        try {

            this.element = getElement(this.elementId);
            if ("italic"
                    .equalsIgnoreCase(this.element.getCssValue("font-style"))) {
                return Constants.PASS + ": Element style for - '"
                        + this.elementId + "' is Italic.'";
            } else {
                return Constants.FAIL + ": Element style for - '"
                        + this.elementId + "' is not Italic.'";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String verifyisBold(Map<String, String> argsList) {
        APP_LOG.info("To check if element is Bold or not");

        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");
        try {

            this.element = getElement(this.elementId);
            if ("Bold".equalsIgnoreCase(
                    this.element.getCssValue("font-weight"))) {
                APP_LOG.info("Bold result Pass");
                return Constants.PASS + ": Element style for - '"
                        + this.elementId + "' is Bold.'";
            } else {
                return Constants.FAIL + ": Element style for - '"
                        + this.elementId + "' is not Bold.'";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @description To check whether the text color.
     * @param argsList
     *            arguments list
     * @return pass/fail
     */

    public String verifyTextColour(Map<String, String> argsList) {
        APP_LOG.info("To verify the text colour");

        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            String colour = this.element.getCssValue("color");
            return colour;

        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author mohit.gupta5
     * @date 17 January,17
     * @description To check if an element on a given index of list is displayed
     * @return PASS/FAIL
     */
    public String verifyIsDisplayedInListByIndex(Map<String, String> argsList) {
        APP_LOG.info(
                "Validate if an element on a given index of list is displayed");
        elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {

            int listPosition = Integer.parseInt(this.inputValue);
            this.elements = this.FindElement.getElements(this.elementId);
            if (elements.size() > 0
                    && elements.get(listPosition).isDisplayed() == true) {
                return Constants.PASS
                        + ": Element on the given index of list - '"
                        + this.inputValue + "' is displayed.'";
            } else {
                return Constants.FAIL
                        + ": Element on the given index of list - '"
                        + this.inputValue + "' is not displayed.'";
            }

        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of element || Exception:"
                            + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author reenajai.sharma
     * @desc-To verify style of text
     * @return pass/fail
     */
    public String verifyStyle(Map<String, String> argsList) {
        APP_LOG.info("To check style of an Element");

        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            String style = this.element.getCssValue("bottom");
            APP_LOG.info("style" + style);
            return style;
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + ": Error while getting style for - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 03 May,17
     * @description To check if element is not editable
     * @return PASS/FAIL
     */

    public String verifyIsElementEditable(Map<String, String> argsList) {
        APP_LOG.info("To check if element is not editable");

        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            this.element.clear();
            return Constants.FAIL + ": Element '" + this.elementId
                    + "' can not be verified - '" + "as not editable";
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            if (e.getMessage().contains(
                    "Element must be user-editable in order to clear it")
                    || e.getMessage().contains(
                            "Element must not be hidden, disabled or read-only")) {
                return Constants.PASS + ": Element - '" + this.elementId
                        + "' is not editable";
            }
            return Constants.FAIL + ": Element '" + this.elementId
                    + "' can not be verified - '" + "as not editable" + "' : "
                    + e;
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 03 May,17
     * @description To check if default value is present as expected in list
     * @return PASS/FAIL
     */

    public String verifyDropDownIsSelected(Map<String, String> argsList) {
        APP_LOG.info("inside selectDropdownValue");
        elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            Select list = new Select(this.element);
            String defaultSelectedValue = list.getFirstSelectedOption()
                    .getText();
            if (defaultSelectedValue.equals(this.inputValue)) {
                return Constants.PASS + ": Default selected value - '"
                        + this.inputValue + "' is matched with Actual Text - '"
                        + defaultSelectedValue + "'";
            } else {
                return Constants.FAIL + ": Default selected value - '"
                        + this.inputValue
                        + "' is not matched with Actual Text - '"
                        + defaultSelectedValue + "'";
            }
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL
                    + ": Error while verifying default selected value for Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 04 May,17
     * @description To verify dropdown values
     * @return PASS/FAIL
     */

    public String verifyDropDownOptions(Map<String, String> argsList) {

        APP_LOG.info("To Verify dropdown values");
        this.inputValue = argsList.get("InputValue");
        elementId = argsList.get("ElementId");

        String[] dropDownValues = this.inputValue.split("\\|");
        this.element = getElement(this.elementId);
        Select select = new Select(this.element);

        try {
            boolean match = false;
            List<WebElement> options = select.getOptions();
            if (options.size() == dropDownValues.length) {
                for (int i = 0; i < dropDownValues.length; i++) {
                    if (options.get(i).getText().equals(dropDownValues[i])) {
                        match = true;
                    } else {
                        return Constants.FAIL + ":  Given Dropdown value- '"
                                + dropDownValues[i]
                                + "' is not matched with actual Dropdown value - '"
                                + this.element.getText() + "'";
                    }
                }
                if (match == true) {
                    return Constants.PASS + ":  Given Dropdown values - '"
                            + Arrays.toString(dropDownValues).replace("[", "")
                                    .replace("]", "")
                            + "' are matched with actual Dropdown values - '"
                            + this.getDropdownValues(options) + "'";
                } else {
                    return Constants.FAIL + ":  Given Dropdown values - '"
                            + dropDownValues
                            + "' are not matched with actual Dropdown values - '"
                            + this.getDropdownValues(options) + "'";
                }
            } else {
                return Constants.FAIL + ":  Given Dropdown size- '"
                        + dropDownValues.length
                        + "' is not matched with actual Dropdown size - '"
                        + options.size() + "'";
            }
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL
                    + ": Error while verifying dropdown values for Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 04 May,17
     * @description Fetching dropdown values
     * @return String
     */

    public String getDropdownValues(List<WebElement> options) {
        String dropDownItems = "";
        for (int i = 0; i < options.size(); i++) {
            dropDownItems = dropDownItems + ", " + options.get(i).getText();
        }
        return dropDownItems.substring(2);
    }

    /**
     * @author mukul.sehra
     * @date 11 May,17
     * @description Verifying the focus on element
     * @return String
     */
    public String verifyIsElementFocused(Map<String, String> argsList) {
        APP_LOG.info("To Verify that the element is focused");

        // Get element
        elementId = argsList.get("ElementId");
        this.element = getElement(this.elementId);

        // Declare WebElement variables
        WebElement activeElementJS, elementActive;

        try {
            // Initialize webDriver instance
            WebDriver webDriver = returnDriver();

            // Getting an active element via selenium activeElement() method
            elementActive = webDriver.switchTo().activeElement();

            // Getting an active element in DOM via javaScript
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            activeElementJS = (WebElement) js
                    .executeScript("return document.activeElement");

            // Assert the equality of the objects
            if (elementActive.equals(activeElementJS)) {
                return Constants.PASS + ": The focus is on  - '"
                        + this.elementId + "'";
            } else {
                return Constants.FAIL + ": The focus is not on  - '"
                        + this.elementId;
            }

        } catch (Exception e) {
            APP_LOG.debug("Exception comes when trying to element is focused",
                    e);
            return Constants.FAIL
                    + ": Error while verifying focus on element - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 03 May,17
     * @description To check if element is selected
     * @return PASS/FAIL
     */

    public String verifyIsCheckboxSelected(Map<String, String> argsList) {
        APP_LOG.info("To check if element is selected");

        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            boolean isSelected = this.element.isSelected();
            if (isSelected == true) {
                return Constants.PASS + ": Element-'" + this.elementId
                        + "' is Selected";
            } else {
                return Constants.FAIL + ": Element-'" + this.elementId
                        + "' is not Selected";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author mehak.verma
     * @date 03 May,17
     * @description To check if element is not selected
     * @return PASS/FAIL
     */

    public String verifyIsCheckboxNotSelected(Map<String, String> argsList) {
        APP_LOG.info("To check if element is selected");

        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            boolean isSelected = this.element.isSelected();
            if (isSelected == false) {
                return Constants.PASS + ": Element '" + this.elementId
                        + "' is not Selected";
            } else {
                return Constants.FAIL + ": Element '" + this.elementId
                        + "' is Selected";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding element for - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author akshay.chimote
     * @date 19 May,17
     * @description To verify the CSS value of an element
     * @return PASS/FAIL
     */
    public String verifyCSSValue(Map<String, String> argsList) {
        APP_LOG.info("inside verifyInputText");
        this.locatorId = argsList.get("ElementId");
        String cssName = argsList.get("ComponentName");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String defaulText = this.element.getCssValue(cssName);
            actualDataPresentOnUi = defaulText;
            APP_LOG.info("Default Text is :" + defaulText);
            if (defaulText.equals(this.inputValue)) {
                return Constants.PASS + ": CSS value - '" + this.inputValue
                        + "' is matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": CSS value - '" + this.inputValue
                        + "' is not matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.locatorId + "' : " + e;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 24 May,17
     * @description To check if TextBox is cleared
     * @return PASS/FAIL
     */

    public String verifyIsTextBoxCleared(Map<String, String> argsList) {
        APP_LOG.info("To check if element is selected");

        elementId = argsList.get("ElementId");
        try {
            this.element = getElement(this.elementId);
            this.element.clear();
            if ("".equals(this.element.getText())) {
                return Constants.PASS + ": Element-'" + this.elementId
                        + "' is cleared";
            } else {
                return Constants.FAIL + ": Element-'" + this.elementId
                        + "' is not cleared";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding element for - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author lekh.bahl
     * @description To check if locator is link
     * @param argsList
     *            arguments list
     * @return pass/fail
     */

    public String verifyLocatorIsHyperLink(Map<String, String> argsList) {
        APP_LOG.info("inside verifyLocatorIsHyperLink");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String defaulText = this.element.getAttribute("role");
            if ("link".equalsIgnoreCase(defaulText)) {
                APP_LOG.info("Locator have role as '" + defaulText + "'");
                return Constants.PASS + ": Locator have role as '" + defaulText
                        + "'";
            } else {
                collectFailureMessage("Locator don't have role as link");
                APP_LOG.info("Locator have role as  '" + defaulText
                        + "' instead of link");
                return Constants.FAIL + ": Locator have role as  '" + defaulText
                        + "' instead of link";
            }

        } catch (Exception e) {
            APP_LOG.error("Error while getting element for - '" + this.elementId
                    + "' : " + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author Abhishek.Sharda
     * @description Verify images and rendering using java script.
     * @return Pass/Fail
     */
    public String verifyImages(Map<String, String> argsList) {
        APP_LOG.debug("Func:imageVerification");
        WebElement element;
        element = getElement(argsList.get("ElementId"));
        try {

            Boolean ImagePresent = (Boolean) ((JavascriptExecutor) returnDriver())
                    .executeScript(
                            "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                            element);
            if (!ImagePresent) {
                return Constants.FAIL + ": Element-'" + this.elementId
                        + "' is present and sucessfully rendered in the page";

            } else {

                return Constants.PASS + ": Element-'" + this.elementId
                        + "' is not present or not sucessfully rendered in the page";
            }

        } catch (Exception e) {
            APP_LOG.debug("Func:imageVerification = " + e);
            return Constants.FAIL + ": Error while getting Element - '"
                    + element + " : " + e;
        }

    }

    /**
     * @author Abhishek.Sharda
     * @description Verify videos playback using java script.
     * @return Pass/Fail
     */
    public String verifyvideosPlayback(Map<String, String> argsList) {
        APP_LOG.debug("Func:videosVerification");
        WebElement element;
        element = getElement(argsList.get("ElementId"));
        try {
            JavascriptExecutor js = (JavascriptExecutor) returnDriver();
            js.executeScript(
                    "document.getElementsByTagName('video')[0].pause()");
            js.executeScript(
                    "document.getElementsByTagName('video')[0].play()");
            Thread.sleep(1000);
            js.executeScript(
                    "document.getElementsByTagName('video')[0].volume=0.8");
            Thread.sleep(1000);
            // Mute Player
            js.executeScript(
                    "document.getElementsByTagName('video').muted = true");
            Thread.sleep(1000);
            // UnMute Player
            js.executeScript(
                    "document.getElementsByTagName('video').muted = false");

            return Constants.PASS + ":"
                    + "Video sucessfully rendered and playback in the page";

        } catch (Exception e) {
            APP_LOG.debug("Func: VideoVerification = " + e);
            return Constants.FAIL
                    + ": Error while rendered and playback video in the page'"
                    + element + " : " + e;
        }

    }

    /**
     * @author mohit.gupta5
     * @description Verify Color Hex Format
     * @return Pass/Fail
     */
    public String verifyColorHexFormat(Map<String, String> argsList) {
        APP_LOG.info("inside verifyInputText");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String color = this.element.getCssValue("color");
            String[] hexValue = null;

            if (color.substring(0, 4).equalsIgnoreCase("rgba")) {
                hexValue = color.replace("rgba(", "").replace(")", "")
                        .split(",");
            } else {
                hexValue = color.replace("rgb(", "").replace(")", "")
                        .split(",");
            }
            int hexValue1 = Integer.parseInt(hexValue[0]);
            hexValue[1] = hexValue[1].trim();
            int hexValue2 = Integer.parseInt(hexValue[1]);
            hexValue[2] = hexValue[2].trim();
            int hexValue3 = Integer.parseInt(hexValue[2]);
            String textColor = String.format("#%02x%02x%02x", hexValue1,
                    hexValue2, hexValue3);
            actualDataPresentOnUi = textColor;
            APP_LOG.info("Default Text is :" + color);
            if (textColor.equals(this.inputValue)) {
                return Constants.PASS + ": CSS value - '" + this.inputValue
                        + "' is matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": CSS value - '" + this.inputValue
                        + "' is not matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.locatorId + "' : " + e;
        }
    }

    /**
     * @description To verify text element in the list of elements.
     * @param argsList
     *            arguments list
     * @return pass/fail
     */
    public String verifyTextInList(Map<String, String> argsList) {
        APP_LOG.info("Inside verifyTextContains");

        elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            elements = getElements(elementId);
            for (WebElement ele : elements) {
                actualDataPresentOnUi = ele.getText().trim();
                if (!actualDataPresentOnUi.trim().replaceAll("(\\s)+", " ")
                        .equalsIgnoreCase(this.inputValue.trim()
                                .replaceAll("(\\s)+", " "))) {
                    return Constants.FAIL + ": InputText for list with value- '"
                            + this.inputValue
                            + "' is not matched with Expected Text - '"
                            + actualDataPresentOnUi + "'";
                }
            }
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while getting element for - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.PASS + ": InputText for list with value- '"
                + this.inputValue + "' is matched with Expected Text - '"
                + actualDataPresentOnUi + "'";
    }

    /**
     * @author pankaj.sarjal
     * @param argsList
     * @return
     * @description Verify background color of an element
     */
    public String verifyBackgourndColor(Map<String, String> argsList) {
        APP_LOG.info("inside verifyInputText");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            this.element = getElement(this.locatorId);
            String color = this.element.getCssValue("background-color");
            String[] hexValue = null;

            if (color.substring(0, 4).equalsIgnoreCase("rgba")) {
                hexValue = color.replace("rgba(", "").replace(")", "")
                        .split(",");
            } else {
                hexValue = color.replace("rgb(", "").replace(")", "")
                        .split(",");
            }
            int hexValue1 = Integer.parseInt(hexValue[0]);
            hexValue[1] = hexValue[1].trim();
            int hexValue2 = Integer.parseInt(hexValue[1]);
            hexValue[2] = hexValue[2].trim();
            int hexValue3 = Integer.parseInt(hexValue[2]);
            String textColor = String.format("#%02x%02x%02x", hexValue1,
                    hexValue2, hexValue3);
            actualDataPresentOnUi = textColor;
            APP_LOG.info("Default Text is :" + color);
            if (textColor.equals(this.inputValue)) {
                return Constants.PASS + ": CSS value - '" + this.inputValue
                        + "' is matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            } else {
                collectFailureMessage(
                        "Expected value is not matching with Actual value.");
                return Constants.FAIL + ": CSS value - '" + this.inputValue
                        + "' is not matched with Expected CSS value - '"
                        + actualDataPresentOnUi + "'";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.locatorId + "' : " + e;
        }
    }

    /**
     * @author pankaj.sarjal
     * @param argsList
     * @return
     * @description Verify element is present by JS
     */

    public String verifyElementPresentByJS(Map<String, String> argsList) {
        APP_LOG.debug("Func:verifyElementPresentByJS");
        this.locatorId = argsList.get("ElementId");
        try {
            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            this.hs = getElementAttribute(this.locatorId);
            WebElement ele = (WebElement) js
                    .executeScript("return document.querySelector('"
                            + this.hs.get(Constants.PREFIX_FIELD_CSS) + "'+)");
            if (ele != null) {
                return Constants.PASS + ": " + this.locatorId + " is present";
            } else {
                return Constants.FAIL + ": " + this.locatorId
                        + " is not present";
            }
        } catch (Exception e) {
            APP_LOG.debug("Func:verifyElementPresentByJS || " + e);
            collectFailureMessage("Exception during finding Element "
                    + this.locatorId + " || Exception:" + e);
            return Constants.FAIL
                    + ": Unexpected error while Finding Element - '"
                    + this.locatorId + "' : " + e;
        }
    }

}
