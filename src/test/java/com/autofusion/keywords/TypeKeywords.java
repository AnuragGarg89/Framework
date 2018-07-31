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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.constants.Constants;

public class TypeKeywords extends Keyword {

    public WebElement element;
    public String elementId;
    public String inputValue;
    public String componentName = "";
    public Map<String, String> argsMap = new HashMap<>();
    public ClickKeywords ClickKeywords = new ClickKeywords(APP_LOG);

    public TypeKeywords(Logger log) {
        APP_LOG = log;

    }

    public String type(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.type(this.argsMap);
    }

    // FINAL SIGNATURE
    public String type(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        try {
            APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
            this.element = getElement(this.elementId);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
        return this.type(this.element, this.inputValue, this.elementId);
    }

    public String typeByJs(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.typeByJs(this.argsMap);
    }

    public String typeByJs(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            return this.typeByJs(this.element, this.inputValue, this.elementId);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String typeByJs(WebElement element, String inputString,
            String elementId) {
        try {

            WebDriver webdriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webdriver;
            js.executeScript("arguments[0].click();", element);
            js.executeScript("arguments[0].setAttribute('value', '"
                    + inputString + "');", element);

            return Constants.PASS + ": InputText - '" + inputString
                    + "' is typed in Element: '" + this.elementId;
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while typing on Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String typeAfterClickViaJs(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.typeAfterClickViaJs(this.argsMap);
    }

    public String typeAfterClickViaJs(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("typeViaJs");
        WebDriver webdriver = returnDriver();

        try {
            this.element = getElement(this.elementId);
            JavascriptExecutor js = (JavascriptExecutor) webdriver;
            String isClicked = this.ClickKeywords.clickViaJs(this.element);
            if (isClicked.contains(Constants.PASS)) {
                js.executeScript("arguments[0].setAttribute('value', '"
                        + this.inputValue + "');", this.element);

                APP_LOG.debug("typeViaJs:" + this.inputValue);
                return Constants.PASS + ": InputText - '" + this.inputValue
                        + "' is typed in Element: '" + this.elementId;
            } else {
                return isClicked;
            }

        } catch (Exception e) {
            APP_LOG.debug("typeViaJs:" + e);
            return Constants.FAIL
                    + ": Unexpected Error while typing on Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String type(WebElement element, String inputString,
            String elementId) {

        if (element != null) {
            try {
                String isCleared = this.clearTextBox(element, elementId);
                if (isCleared.contains(Constants.PASS)) {
                    if ((webDriver instanceof SafariDriver
                            || capBrowserName.equalsIgnoreCase("Safari"))
                            && (inputString.length() == 0)) {
                        return Constants.PASS
                                + ": Input with blank value is passed in Element: '"
                                + this.elementId;
                    } else {
                        element.sendKeys(inputString);
                        return Constants.PASS + ": InputText - '" + inputString
                                + "' is typed in Element: '" + this.elementId;
                    }
                } else {
                    return isCleared;
                }

            } catch (Exception e) {
                return Constants.FAIL
                        + ": Unexpected Error while typing on Element - '"
                        + this.elementId + "' : " + e;
            }
        } else {
            return Constants.FAIL + ": Element is - '" + this.elementId
                    + "' null ";
        }
    }

    public String typeAfterClear(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.typeAfterClear(this.argsMap);
    }

    public String typeAfterClear(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            if (this.element != null) {

                String isCleared = this.clearTextBox(this.element,
                        this.elementId);
                if (isCleared.contains(Constants.PASS)) {
                    return this.type(this.element, this.inputValue,
                            this.elementId);
                } else {
                    return isCleared;
                }
            } else {
                return Constants.FAIL + ": Element is - '" + this.elementId
                        + "' null ";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String typeAfterClick(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.typeAfterClick(this.argsMap);
    }

    public String typeAfterClick(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("KeywordActions TYPE : " + this.inputValue);

        try {
            this.element = getElement(this.elementId);
            if (this.element != null) {
                String isClicked = this.ClickKeywords.click(this.element,
                        this.elementId);
                if (isClicked.contains(Constants.PASS)) {
                    return this.type(this.element, this.inputValue,
                            this.elementId);
                } else {
                    return isClicked;
                }
            } else {
                return Constants.FAIL + ": Element is - '" + this.elementId
                        + "' null ";
            }

        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String clearTextBox(WebElement element, String elementId) {

        if (element != null) {
            try {
                element.clear();
                return Constants.PASS + ": Element text box is - '"
                        + this.elementId + "' Cleared ";
            } catch (WebDriverException e) {
                APP_LOG.error("Error while clear text box" + e);
                return Constants.FAIL + ": Error while clearing Element - '"
                        + this.elementId + "' text box. ";
            }
        } else {

            return Constants.FAIL + ": Element is - '" + this.elementId
                    + "' null ";
        }
    }

    public String typeViaJs(String locator, String inputValue) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.typeViaJs(this.argsMap);
    }

    public String typeViaJs(Map<String, String> argsList) {

        WebDriver webDriver = returnDriver();
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("typeViaJs");
        try {
            this.element = getElement(this.elementId);
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            String isClicked = this.ClickKeywords.clickByJs(this.element,
                    this.elementId);
            if (isClicked.contains(Constants.PASS)) {
                js.executeScript("arguments[0].setAttribute('value', '"
                        + this.inputValue + "');", this.element);

                APP_LOG.debug("typeViaJs:" + this.inputValue);
                return Constants.PASS + ": InputText - '" + this.inputValue
                        + "' is typed in Element: '" + this.elementId;
            } else {
                return isClicked;
            }

        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    /**
     * @author sumit.bhardwaj
     * @param--> HashMap containing Element name and Element value(if any)
     * @return : True if element is clicked successfully, otherwise false
     * @throws InterruptedException
     */
    public String actionTypeAndHandleStaleElementException(
            Map<String, String> argsList, String elemName) {
        CommonKeywords common = new CommonKeywords(APP_LOG);
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        this.element = getElement(this.elementId);
        boolean result = false;
        int attempts = 0;
        try {

            while (attempts < 4) {

                try {

                    // Scroll to the element
                    // javascriptPageScroll(locators);
                    common.scrollIntoView(argsList);
                    Actions actions = new Actions(returnDriver());
                    actions.moveToElement(element);
                    actions.click();
                    actions.sendKeys(inputValue);
                    actions.build().perform();
                    result = true;
                    break;

                } catch (StaleElementReferenceException e) {
                    APP_LOG.info(
                            "Stale element exception' occurred and is handled:"
                                    + e);
                }

                attempts++;
            }

            // wait for loader to dissappear

        } catch (Exception e) {

            APP_LOG.error(
                    "Error while clicking and handling stale element reference:"
                            + e);
            return Constants.FAIL
                    + ": Exception occured while handling Stale Exception for '"
                    + this.elementId;
        }

        return Constants.PASS + ": Clicked - '" + this.elementId
                + "' after handling Stale exception ";
    }

    /**
     * @author mukul.sehra
     * @param--> HashMap containing Element name and Element value(if any)
     * @return : PASS if value entered, otherwise FAIL
     * @throws Exception
     */
    public String typeViaActions(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        this.element = getElement(this.elementId);

        try {
            Actions actions = new Actions(returnDriver());
            actions.moveToElement(element);
            actions.click();
            actions.sendKeys(inputValue);
            actions.build().perform();
        } catch (Exception e) {
            APP_LOG.info(
                    "Stale element exception' occurred and is handled:" + e);
            return Constants.FAIL + ": Error while finding element '"
                    + this.elementId + "' " + e;
        }
        return Constants.PASS + ": Entered data in Free Response field "
                + this.elementId;
    }

}
