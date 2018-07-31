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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.constants.Constants;

public class ClickKeywords extends Keyword {

    public String locatorId = "";
    public String inputValue = "";
    public Map<String, String> argsMap = new HashMap<>();

    public ClickKeywords(Logger log) {
        APP_LOG = log;
    }

    public String click(String locator) {
        this.argsMap.put("ElementId", locator);
        this.argsMap.put("InputValue", this.inputValue);

        return this.click(this.argsMap);
    }

    public String click(Map<String, String> argsList) {
        APP_LOG.debug("Func:Click");
        elementId = argsList.get("ElementId");
        WebElement element;
        try {
            element = getElement(elementId);
            return this.click(element, elementId);
        } catch (Exception e) {
            APP_LOG.debug("Func:Click Exception:" + e);
            return Constants.FAIL + ": Error while finding Element - "
                    + elementId + " : " + e;
        }
    }

    public String click(WebElement element, String elementId) {
        APP_LOG.debug("Func:Static Click");
        try {
            element.click();
        } catch (Exception e) {
            APP_LOG.debug("Func:Static Click Exception=" + e);
            return Constants.FAIL
                    + ": Unexpected Error while clicking Element - '"
                    + elementId + "' : " + e;
        }
        return Constants.PASS + ": Element - '" + elementId + "' is clicked";
    }

    /**
     * @param argsList
     *            Map contains ElementId
     */
    public String clickAndWait(Map<String, String> argsList) {
        APP_LOG.info("Func:clickAndWait");

        elementId = argsList.get("ElementId");
        try {

            WebElement element = getElement(elementId);
            if (element == null) {
                APP_LOG.info("Func:clickAndWait || Element is Null");
                return Constants.FAIL + ": Element is - '" + elementId
                        + "' null";
            }
            WebDriver webDriver = returnDriver();
            if (webDriver instanceof InternetExplorerDriver) {
                Actions action = new Actions(webDriver);
                action.moveToElement(element).perform();
            }

            this.click(element, argsList.get("ElementId"));
            Long wait = Long.parseLong(argsList.get("InputValue"));
            APP_LOG.info("Func:clickAndWait || wait for =" + wait);
            Thread.sleep(wait);
        } catch (Exception e) {
            APP_LOG.debug("Func:clickAndWait || Exception : " + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + elementId + "' : " + e;
        }
        return Constants.PASS;
    }

    /**
     * @description This action is to perform double click.
     * @param argsList
     *            Map contains ElementId .
     */
    public String doubleClick(Map<String, String> argsList) {
        APP_LOG.debug("Func: doubleClick ");
        WebDriver webDriver = returnDriver();
        element = getElement(argsList.get("ElementId"));
        Actions action = new Actions(webDriver);
        action.doubleClick(element).perform();
        return Constants.PASS;
    }

    public String clickByJs(WebElement element, String elementId) {
        try {
            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", element);

            return Constants.PASS + ": Element - '" + elementId
                    + "' is clicked";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while Clicking Elmenet - '"
                    + elementId + "' by JS: " + e;
        }
    }

    public String clickByJs(Map<String, String> argsList)
            throws InterruptedException {
        APP_LOG.debug("Func:clickByJs");
        String elementId = argsList.get("ElementId");
        WebElement element;
        try {
            element = getElement(elementId);
            if (element == null) {
                APP_LOG.debug("Func:clickByJs Element is Null");
                return Constants.FAIL + ": Element - '" + elementId
                        + "' is null";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while Finding Element - '"
                    + elementId + "' by JS: " + e;
        }
        return this.clickByJs(element, elementId);
    }

    public String clickControlandPrint(String object, String data) {
        APP_LOG.debug("Func:clickControlandPrint");
        try {
            WebDriver webDriver = returnDriver();
            Actions actionObject = new Actions(webDriver);
            actionObject.keyDown(Keys.CONTROL).sendKeys("p").keyUp(Keys.CONTROL)
                    .perform();
            return Constants.PASS;
        } catch (Exception e) {
            APP_LOG.debug("Func:clickControlandPrint" + e);
            return Constants.FAIL;
        }
    }

    public String clickHoldingModifierKey(Map<String, String> argsList) {
        String object = argsList.get("ElementId");
        APP_LOG.debug("Func clickHoldingModifierKey");
        WebDriver webDriver = returnDriver();
        WebElement element = getElement(object);
        Actions builder = new Actions(webDriver);
        builder.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL);

        Action execute = builder.build();
        execute.perform();

        return Constants.PASS;
    }

    public String clickViaJs(WebElement element) {
        try {
            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", element);

            return Constants.PASS;
        } catch (Exception e) {
            APP_LOG.error("Error in ClickViaJs" + e);
            return Constants.FAIL;
        }
    }

    public String clickViaJs(Map<String, String> argsList) {
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("clickViaJs");
        try {
            element = getElement(this.locatorId);
            return this.clickViaJs(element);

        } catch (Exception e) {
            APP_LOG.debug("clickViaJs:" + e);
            return Constants.FAIL;
        }
    }

    public String clickCheckBoxInList(Map<String, String> argsList) {
        APP_LOG.info("inside clickCheckBox");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        element = getElement(this.locatorId);
        try {
            WebDriver webDriver = returnDriver();
            List<WebElement> chkbox = webDriver
                    .findElements(By.tagName("label"));
            for (int i = 0; i < chkbox.size(); i++) {
                if (chkbox.get(i).getText().equals(this.inputValue)) {
                    chkbox.get(i).click();
                    break;
                }
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while findng Element - '"
                    + this.locatorId + "' : " + e;
        }
        return Constants.PASS + ": CheckBox in the list for Element '"
                + this.locatorId + "' is clicked";
    }

    /**
     * @description Returns the result of the link clicked.
     * @param argsList
     *            map contains ElementId and Input Value.
     */
    public String clickLink(Map<String, String> argsList) {
        APP_LOG.info("inside clickLink");
        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        String res = this.click(argsList);
        APP_LOG.info("The click link is :" + res);
        return res;
    }

    public String clickOnText(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
        try {
            List<WebElement> elements = getElements(this.locatorId);

            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).getText();
                if (this.inputValue.equals(elements.get(i).getText())) {

                    return this.click(elements.get(i),
                            argsList.get("ElementId"));
                }
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - "
                    + this.locatorId + " : " + e;
        }
        return Constants.FAIL
                + ": Unexpected error Error while performing action on Element - '"
                + this.locatorId + "' ";
    }

    public String clickIndexPosition(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
        try {
            List<WebElement> elements = getElements(this.locatorId);
            APP_LOG.info(elements.size());

            int indexPos = Integer.valueOf(this.inputValue);
            return this.click(elements.get(indexPos),
                    argsList.get("ElementId"));
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Elements - "
                    + this.locatorId + " : " + e;
        }
    }

    public String clickAllElementsInList(Map<String, String> argsList) {
        APP_LOG.info("inside clickCheckBox");
        this.locatorId = argsList.get("ElementId");
        try {
            List<WebElement> list = getElements(this.locatorId);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).click();
                break;

            }
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL + ": Error while findng Element - '"
                    + this.locatorId + "' : " + e;
        }

        return Constants.PASS
                + ": All Element on the list is Clicked for Element '" + "'"
                + this.locatorId;
    }

    public String clickContentDescFromList(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            List<WebElement> elementIdList = getElements(this.locatorId);
            String data = argsList.get(1);
            for (int i = 0; i < elementIdList.size(); i++) {
                String lstItem = elementIdList.get(i).getAttribute("name")
                        .trim();
                if (lstItem.equalsIgnoreCase(data)) {
                    elementIdList.get(i).click();
                    return Constants.PASS
                            + ": Element on the list for Element '"
                            + this.locatorId + "' is clicked at index: " + i;
                } else {
                    return Constants.FAIL
                            + ": Element on the list for Element '"
                            + this.locatorId + "' is not clicked at index: "
                            + i;
                }
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while findng Element - '"
                    + this.locatorId + "' : " + e;
        }
        return Constants.FAIL
                + ": Unexpected Error while clicking content in list";
    }

    /**
     * @author sumit.bhardwaj
     * @param -->
     *            HashMap containing Element name and Element value(if any)
     * @return : Pass if element is clicked successfully, otherwise fail
     * @description --> 1. This function will attempt 3 times to click the
     *              element.</li> 2. If Stale Element Reference exception occurs
     *              then handle it in catch block and give another try 3. If
     *              element is clicked successfully then return result as pass
     */
    public String
           clickAndHandleStaleElementException(Map<String, String> argsList) {
        CommonKeywords common = new CommonKeywords(APP_LOG);
        this.elementId = argsList.get("ElementId");
        this.element = getElement(this.elementId);
        int attempts = 0;
        try {

            while (attempts < 6) {

                try {
                    if ((webDriver instanceof SafariDriver
                            || capBrowserName.equalsIgnoreCase("Safari"))) {
                        JavascriptExecutor js = (JavascriptExecutor) returnDriver();
                        js.executeScript("arguments[0].click();", element);
                    } else {
                        element.click();
                    }

                    APP_LOG.info(
                            "No 'Stale element exception' occurred on CLick");
                    break;

                } catch (StaleElementReferenceException staleElementException) {

                    APP_LOG.info(
                            "'Stale element exception' occurred and is handled on CLick");
                    System.err.println(
                            "'Stale element exception' occurred and is handled");
                }

                attempts++;
            }

            // wait for loader to dissappear

        } catch (Exception e) {
            return Constants.FAIL
                    + ": Exception occured while handling Stale Exception for '"
                    + this.elementId;
        }

        return Constants.PASS + ": Clicked - '" + this.elementId
                + "' after handling Stale exception ";
    }

    /**
     * @author ratnesh.singh
     * @param -->
     *            HashMap containing Element locator and InnerText substring as
     *            InputValue
     * @return : Pass if element is clicked successfully, otherwise fail
     * @description --> This function will traverse the list of Elements
     *              matching with locator and click in element containing
     *              inner-text
     */
    public String clickTextContainsInElementList(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
        try {
            List<WebElement> elements = getElements(this.locatorId);

            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).getText();
                if (elements.get(i).getText().contains(this.inputValue)) {
                    this.highlight(elements.get(i));
                    elements.get(i).click();
                    return Constants.PASS + ": Clicked on Element : "
                            + this.locatorId + " containing Text as : "
                            + this.inputValue;
                }
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - "
                    + this.locatorId + " : " + e;
        }
        return Constants.FAIL
                + ": Unexpected error Error while performing action on Element - '"
                + this.locatorId + "' ";
    }

    /**
     * @author ratnesh.singh
     * @param -->
     *            HashMap containing Element locator and aria-lable substring as
     *            InputValue
     * @return : Pass if element is clicked successfully, otherwise fail
     * @description --> This function will traverse the list of Elements
     *              matching with locator and click in element containing
     *              aria-label
     */
    public String
           clickLabelContainsInElementList(Map<String, String> argsList) {

        this.locatorId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
        try {
            List<WebElement> elements = getElements(this.locatorId);

            for (int i = 0; i < elements.size(); i++) {
                String lable = elements.get(i).getAttribute("aria-label");
                if (lable.contains(this.inputValue)) {
                    this.highlight(elements.get(i));
                    elements.get(i).click();
                    return Constants.PASS + ": Clicked on Element : "
                            + this.locatorId + ": containing Label as : "
                            + this.inputValue;
                }
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - "
                    + this.locatorId + " : " + e;
        }
        return Constants.FAIL
                + ": Unexpected error Error while performing action on Element - '"
                + this.locatorId + "' ";
    }

    /**
     * @author pankaj.sarjal
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public String clickByJs(String locator) throws InterruptedException {
        this.argsMap.put("ElementId", locator);

        return this.clickByJs(this.argsMap);
    }

}
