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

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;

public class ActionKeywords extends Keyword {

    public WebElement element;
    public WebElement element1;
    public String locatorId;
    public String inputValue;
    public Keyword objKeyWord;

    public ActionKeywords(Logger log) {
        APP_LOG = log;
        this.objKeyWord = new Keyword();
    }

    /**
     * @description moves to the respective element on hovering of mouse over.
     * @param args
     *            Map contains LocatorId
     */
    public String actionMousehover(String[] args) {
        this.locatorId = args[0];
        this.inputValue = args[1];
        try {
            this.element = getElement(this.locatorId);
        } catch (Exception e) {
            APP_LOG.error(
                    " Nothing happens on hovering by mouse over an area" + e);
            return Constants.FAIL + ": Error while getting Element for - '"
                    + this.locatorId + "' : " + e;
        }
        return Constants.PASS + ": Mouse hover on ELement - '" + this.locatorId
                + "' is performed.";

    }

    public String actionMoveToElement(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionMoveToElement");
        try {
            WebDriver webDriver = returnDriver();
            this.element = getElement(
                    argsList.get(KeywordConstant.ELEMENT_LOCATOR));
            Actions action = new Actions(webDriver);
            action.moveToElement(this.element).perform();
            action.click(this.element).perform();
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionMoveToElement=" + e);
            return Constants.FAIL + ": Error while getting Element for - '"
                    + KeywordConstant.ELEMENT_LOCATOR + "' : " + e;
        }
        return Constants.PASS + ": Mouse is moved to element - '"
                + KeywordConstant.ELEMENT_LOCATOR + "' .";

    }

    public String actionFocusElement() {
        APP_LOG.info("in focusOnUI");
        try {
            WebDriver webDriver = returnDriver();
            webDriver.switchTo().activeElement();
            // Switch to currently active element in a page
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while moving to the Active element - '"
                    + KeywordConstant.ELEMENT_LOCATOR + "' : " + e;
        }
        return Constants.PASS + ": Focus is moved to the Active Element on UI.";
    }

    /**
     * @description popup features.
     */

    public WebElement actionFocusElementPopUp() {
        APP_LOG.info("in focusOnUI");
        try {
            WebDriver webDriver = returnDriver();
            this.element = webDriver.switchTo().activeElement();
            return this.element;
        } catch (Exception e) {
            APP_LOG.error("Unable to switch to focus" + e);
        }
        return this.element;
    }

    public String sendKeysPopUp(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionMoveToElement");
        try {
            this.element = this.actionFocusElementPopUp();
            this.element.sendKeys("this is test chat");
            return Constants.PASS + ": Text '" + "this is test chat"
                    + "' is entered in the pop up";
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionMoveToElement=" + e);
            return Constants.FAIL
                    + ": Unexpected Error while entering text in the POP UP - '"
                    + KeywordConstant.ELEMENT_LOCATOR + "' : " + e;
        }

    }

    public String actionType(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionMoveToElement");
        try {
            WebDriver webDriver = returnDriver();
            this.element = getElement(
                    argsList.get(KeywordConstant.ELEMENT_LOCATOR));
            this.inputValue = argsList.get("InputValue");
            Actions actions = new Actions(webDriver);
            actions.moveToElement(this.element);
            actions.click();
            actions.sendKeys(inputValue);
            actions.build().perform();
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionMoveToElement=" + e);
            return Constants.FAIL + ": Error while getting Element for - '"
                    + KeywordConstant.ELEMENT_LOCATOR + "' : " + e;
        }
        return Constants.PASS + ": Mouse is moved to element - '"
                + KeywordConstant.ELEMENT_LOCATOR + "' .";

    }
}
