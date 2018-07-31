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

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;

public class Keyword extends PerformAction {

    public Keyword() {
        // TODO Auto-generated constructor stub
    }

    public Connection conn = null;
    public String elementId;
    public WebElement element;
    public List<WebElement> elements;
    public String inputValue;

    /**
     * @description checks whether the element is present or not.
     * @param locatorId
     *            locator
     */
    public String isElementPresent(String locatorId) {
        APP_LOG.debug("Func: isElementPresent");
        try {
            this.elements = getElements(locatorId);
            APP_LOG.info("element is " + this.elements);
            if (this.elements.get(0) != null) {
                return Constants.PASS + ": Element - '" + locatorId
                        + "' is present on UI";
            } else {
                return Constants.FAIL + ": Element - '" + locatorId
                        + "' is not present on UI";
            }
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + locatorId + "' : " + e;
        }

    }

    public String isAlertPresent() {
        APP_LOG.debug("Func: isAlertPresent");
        try {
            WebDriver webDriver = returnDriver();
            webDriver.switchTo().alert();
            return Constants.PASS + ": Alert is found and Switched";
        } catch (NoAlertPresentException e) {
            APP_LOG.debug("Func: isAlertPresent Exception" + e);
            return Constants.FAIL + ": Alert is not present - : " + e;
        } catch (Exception e) {
            APP_LOG.debug("Func: isAlertPresent Exception" + e);
            return Constants.FAIL
                    + ": Unexpected error while finding Alert with Exception - : "
                    + e;
        }
    }

    public String acceptAlert(String args[]) {
        try {
            WebDriver webDriver = returnDriver();
            String isAlertPresent = isAlertPresent();
            if (isAlertPresent.contains(Constants.PASS)) {
                Alert alert = webDriver.switchTo().alert();
                alert.accept();
                return Constants.PASS + ": Alert is found and Switched.";
            } else {
                return isAlertPresent;
            }
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected error while finding Alert with Exception - : "
                    + e;
        }
    }

    public String getNumberOfElementInList(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            this.elements = getElements(this.elementId);

            return Integer.toString(this.elements.size());
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - "
                    + this.elementId + " : " + e;
        }
    }

}
