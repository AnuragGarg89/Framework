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
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;

public class GetElementsFromUi extends Keyword {
    protected static Map<String, String> presistanceMap = new HashMap<>();
    protected String elementId;
    protected String attributeName;
    public String inputValue;
    private FindElement FindElement = new FindElement();

    public GetElementsFromUi(Logger log) {
        APP_LOG = log;
    }

    public String getText(Map<String, String> argsList) {
        APP_LOG.info("Inside type");
        this.elementId = argsList.get("ElementId");
        try {
            element = getElement(this.elementId);
            return this.getText(element);
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during verification of text || Exception:" + e);
            return Constants.FAIL + ": Error while finding Element - "
                    + this.elementId + " : " + e;
        }
    }

    public String getText(WebElement element) {
        APP_LOG.debug("Func:Find Value");
        try {
            String value = element.getText();
            return value;
        } catch (Exception e) {
            APP_LOG.debug(
                    "Unexpected error while getting Text for Element=" + e);
            return Constants.FAIL
                    + ": Unexpected error while getting Text for Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String getCurrentUrl(Map<String, String> argsList) {
        WebDriver webDriver = returnDriver();
        try {
            APP_LOG.info("Get current URL");
            return webDriver.getCurrentUrl();
        } catch (Exception e) {
            APP_LOG.debug("Unable to get current URL" + e);
            return Constants.FAIL + "Uanble to get current URL";
        }
    }

    /**
     * @author nitish jaiswal
     * @date 8 August, 2017
     * @description To get attribute value of element
     */

    public String getAttribute(Map<String, String> argsList) {
        APP_LOG.info("Fetching the size of list");
        this.elementId = argsList.get("ElementId");
        this.attributeName = argsList.get("InputValue");
        element = getElement(this.elementId);
        try {
            String text = element.getAttribute(attributeName);
            return text;
        } catch (Exception e) {
            collectFailureMessage("Exception getting attribute value" + e);
            return Constants.FAIL + ": Error while finding an Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String getListItemOnIndex(Map<String, String> argsList) {
        APP_LOG.info("Fetching the list item corresponding to given index");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            int listPosition = Integer.parseInt(this.inputValue);
            List<WebElement> element = this.FindElement
                    .getElements(this.elementId);
            if (element.size() > 0) {
                String eleText = element.get(listPosition).getText().trim();
                return eleText;
            } else {
                return Constants.FAIL + ":Element - '" + this.elementId
                        + "' does not contain any value and is null";
            }

        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during getting text of list item || Exception:"
                            + e);
            return Constants.FAIL + ": Error while finding an Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String getNumberOfElementInList(Map<String, String> argsList) {
        APP_LOG.info("Fetching the size of list");
        this.elementId = argsList.get("ElementId");

        try {
            List<WebElement> allOptions = this.FindElement
                    .getElements(this.elementId);
            int actualDropDownSize = allOptions.size();
            if (actualDropDownSize > 0) {

                String dropdownSize = Integer.toString(actualDropDownSize);
                return dropdownSize;
            } else {
                return Constants.FAIL + ":Element - '" + this.elementId
                        + "' does not contain any value and is null";
            }
        } catch (Exception e) {
            collectFailureMessage("Exception during getting size of list" + e);
            return Constants.FAIL + ": Error while finding an Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    /**
     * @author nisha.pathria
     * @date 30 Jan, 2018
     * @description To get size of element
     */

    public String getWidth(Map<String, String> argsList) {
        APP_LOG.info("Fetching size of element");
        this.elementId = argsList.get("ElementId");
        try {
            element = getElement(this.elementId);
            org.openqa.selenium.Dimension value = element.getSize();
            int width = value.getWidth();
            String width1 = Integer.toString(width);
            return width1;
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during getting width of element || Exception:"
                            + e);
            return Constants.FAIL + ": Error while finding Element - "
                    + this.elementId + " : " + e;
        }
    }

    /**
     * @author nisha.pathria
     * @date 30 Jan, 2018
     * @description To get height of element
     */

    public String getHeight(Map<String, String> argsList) {
        APP_LOG.info("Fetching size of element");
        this.elementId = argsList.get("ElementId");
        try {
            element = getElement(this.elementId);
            org.openqa.selenium.Dimension value = element.getSize();
            int height = value.getHeight();
            String height1 = Integer.toString(height);
            return height1;
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during getting height of element || Exception:"
                            + e);
            return Constants.FAIL + ": Error while finding Element - "
                    + this.elementId + " : " + e;
        }
    }

    /**
     * @author ratnesh singh
     * @date 17 May, 2018
     * @description To get attribute value of element by JavaScript Executor
     */

    public String getAttributeByJs(Map<String, String> argsList) {
        APP_LOG.info("Inside Get Attribute Value By JavaScript Executor");
        this.elementId = argsList.get("ElementId");
        this.attributeName = argsList.get("InputValue");
        ConcurrentHashMap<String, String> locatorValueMap;
        String JsQuery = null;

        try {
            locatorValueMap = getElementAttribute(this.elementId);
            for (Map.Entry<String, String> entry : locatorValueMap.entrySet()) {

                if (entry.getKey()
                        .equalsIgnoreCase(Constants.PREFIX_FIELD_CSS)) {

                    JsQuery = "return document.querySelector('"
                            + entry.getValue() + "').getAttribute('"
                            + this.attributeName + "')";

                } else if (entry.getKey()
                        .equalsIgnoreCase(Constants.PREFIX_FIELD_ID)) {

                    JsQuery = "return document.getElementById('"
                            + entry.getValue() + "').getAttribute('"
                            + this.attributeName + "')";

                } else if (entry.getKey()
                        .equalsIgnoreCase(Constants.PREFIX_FIELD_NAME)) {

                    JsQuery = "return document.getElementsByName('"
                            + entry.getValue() + "').getAttribute('"
                            + this.attributeName + "')";

                } else if (entry.getKey()
                        .equalsIgnoreCase(Constants.PREFIX_FIELD_CLASSNAME)) {

                    JsQuery = "return document.getElementsByClassName('"
                            + entry.getValue() + "').getAttribute('"
                            + this.attributeName + "')";

                } else if (entry.getKey()
                        .equalsIgnoreCase(Constants.PREFIX_FIELD_TAGNAME)) {

                    JsQuery = "return document.getElementsByTagName('"
                            + entry.getValue() + "').getAttribute('"
                            + this.attributeName + "')";
                } else {

                    return Constants.FAIL
                            + ": Error while finding an Element - '"
                            + this.elementId
                            + "' : Locator Type Not Supported in JavaScript.";

                }

            }

            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            String attValue = (String) js.executeScript(JsQuery);
            return attValue;

        } catch (Exception e) {
            collectFailureMessage("Exception getting attribute value" + e);
            return Constants.FAIL + ": Error while finding an Element - '"
                    + this.elementId + "' : " + e;
        }

    }

}