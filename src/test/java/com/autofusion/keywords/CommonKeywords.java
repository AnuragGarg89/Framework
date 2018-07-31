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
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.constants.Constants;

public class CommonKeywords extends Keyword {
    public Map<String, String> presistanceMap = new HashMap<>();
    public String elementId;
    public WebElement element = null;
    public String inputValue;
    protected String result = "";
    private FindElement FindElement = new FindElement();

    public CommonKeywords(Logger log,
            Map<String, HashMap<String, String>> orMap) {
        APP_LOG = log;
    }

    public CommonKeywords(Logger log) {
        APP_LOG = log;
    }

    /*
     * public String open(Map<String, String> argsList) throws Exception {
     * 
     * WebDriver webDriver = getWebDriver(); if (webDriver == null) { return
     * Constants.FAIL; } else { return Constants.PASS;
     * 
     * } }
     */

    /**
     * @author reenajai.sharma
     *
     */
    public String mouseHoverOnMoveToElement(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            this.element = getElement(this.elementId);
            // int indexPos = Integer.valueOf(this.inputValue);
            return this.mouseHoverMoveToElement(this.element);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while hovering on Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String mouseHoverMoveToElement(WebElement element) {
        APP_LOG.debug("Func: Mouse hover over an element");
        try {
            WebDriver webDriver = returnDriver();
            Actions action = new Actions(webDriver);
            action.moveToElement(element).build().perform();
            return Constants.PASS + ": Mouse Hovering of element- " + elementId
                    + " is done.";
        } catch (Exception e) {
            APP_LOG.debug("Func:Mouse hover over an element=" + e);
            return Constants.FAIL
                    + ": Unexpected Error while Hovering for element - '"
                    + elementId + "' : " + e;
        }
    }

    public String waitForTitle(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
            webDriverWait.until(
                    ExpectedConditions.titleIs(argsList.get("InputValue")));
            APP_LOG.info("Wait For Loading the Page:" + this.inputValue);
            return Constants.PASS
                    + " : Page is loaded and Page title is present.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while waiting for title of Page- '"
                    + e;
        }

    }

    public String explicitWait(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            this.elementId = argsList.get("ElementId");
            this.element = getElement(this.elementId);
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
            webDriverWait.until(ExpectedConditions.visibilityOf(this.element));
            APP_LOG.info("Wait For Loading the Page:" + this.inputValue);
            return Constants.PASS + " : Page is loaded";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while waiting for element- '" + e;
        }

    }

    public String elementIsVisible(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            this.elementId = argsList.get("ElementId");
            this.element = getElement(this.elementId);
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            WebElement element = wait
                    .until(ExpectedConditions.visibilityOf(this.element));

            APP_LOG.info(
                    "Following element visible on the page:" + this.element);
            return Constants.PASS + ": Element '" + this.elementId
                    + "' : is visible.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while checking visiblity of element- '"
                    + e;
        }

    }

    public String waitTillEementIsNotVisible(Map<String, String> argsList) {

        try {
            WebDriver webDriver = returnDriver();
            this.elementId = argsList.get("ElementId");
            List<WebElement> element = getElements(this.elementId);
            FluentWait<WebDriver> wait = new FluentWait<>(webDriver);
            wait.withTimeout(10000, TimeUnit.MILLISECONDS);
            wait.pollingEvery(250, TimeUnit.MILLISECONDS);
            wait.ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.invisibilityOfAllElements(element));
            APP_LOG.info("Wait For Element to be invisible");
            return Constants.PASS + "Element '" + this.elementId
                    + "' : is not visible.";
        } catch (Exception e) {
            APP_LOG.info("Wait For visiblity of element:" + this.inputValue);
            return Constants.FAIL
                    + ": Unexpected Error while waiting for visiblity of element- '"
                    + e;
        }
    }

    public String elementIsClickable(Map<String, String> argsList) {
        try {

            WebDriver webDriver = returnDriver();
            this.elementId = argsList.get("ElementId");
            this.element = getElement(this.elementId);
            FluentWait<WebDriver> wait = new FluentWait<>(webDriver);
            wait.withTimeout(10000, TimeUnit.MILLISECONDS);
            wait.pollingEvery(250, TimeUnit.MILLISECONDS);
            wait.ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(this.element));
            APP_LOG.info("Wait For Loading the Page:" + this.inputValue);
            return Constants.PASS + ": Element '" + this.elementId
                    + "' : is clickable.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while checking element '"
                    + this.elementId + "' : is clickable- '" + e;
        }
    }

    public String waitForLoadingPage(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            Long waitTime = Long.valueOf(argsList.get("InputValue"));
            webDriver.manage().timeouts().pageLoadTimeout(waitTime,
                    TimeUnit.SECONDS);
            APP_LOG.debug("Apply Wait For Loading Page");
            return Constants.PASS + ": Page is loaded.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while waiting for element- '" + e;
        }

    }

    public String implicitWait(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            Long waitTime = Long.valueOf(argsList.get("InputValue"));
            webDriver.manage().timeouts().implicitlyWait(waitTime,
                    TimeUnit.SECONDS);
            APP_LOG.debug("Apply Implicit wait");
            return Constants.PASS + "Page is loaded.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while waiting for element- '" + e;
        }
    }

    public ExpectedCondition<Boolean> angularHasFinishedProcessing() {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                String hasAngularFinishedScript = "var callback = arguments[arguments.length - 1];\n"
                        + "var el = document.querySelector('html');\n"
                        + "if (!window.angular) {\n" + "    callback('false')\n"
                        + "}\n" + "if (angular.getTestability) {\n"
                        + "    angular.getTestability(el).whenStable(function(){callback('true')});\n"
                        + "} else {\n"
                        + "    if (!angular.element(el).injector()) {\n"
                        + "        callback('false')\n" + "    }\n"
                        + "    var browser = angular.element(el).injector().get('$browser');\n"
                        + "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n"
                        + "}";

                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                String isProcessingFinished = javascriptExecutor
                        .executeAsyncScript(hasAngularFinishedScript)
                        .toString();

                return Boolean.valueOf(isProcessingFinished);
            }
        };
    }

    public String angularWaitForLoadingPage(Map<String, String> argsList) {
        try {
            WebDriver webDriver = returnDriver();
            Long waitTime = Long.valueOf(argsList.get("InputValue"));
            WebDriverWait webDriverWait = new WebDriverWait(webDriver,
                    waitTime);
            webDriverWait.until(this.angularHasFinishedProcessing());
            APP_LOG.info("Wait For Loading the Page:" + this.inputValue);
            return Constants.PASS + "Page is loaded.";
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while waiting for element- '" + e;
        }

    }

    /**
     * @desc Sends the keys which are entered.
     * @param log
     *            Application logs
     * @param args
     *            Map contains elementId.
     */
    public String enterKey(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");

        APP_LOG.info("Inside enterKey");
        this.element = getElement(this.elementId);

        if (this.element != null) {
            try {
                this.element.sendKeys(Keys.ENTER);
            } catch (Exception e) {
                APP_LOG.debug("Inside enterKey : " + e);
                return Constants.FAIL;
            }
        } else {
            return Constants.FAIL;
        }
        return Constants.PASS;
    }

    public String compareSavedValue(Map<String, String> argsList) {
        try {
            this.elementId = argsList.get("ElementId");
            this.inputValue = argsList.get("InputValue");
            APP_LOG.info("Compare values");
            String savedValue = "";

            if (!"".equals(this.getValueFromMemory(this.inputValue))) {
                this.element = getElement(this.elementId);
                String currentValue = this.element.getText().toString().trim();
                if (savedValue.equalsIgnoreCase(currentValue)) {
                    return Constants.PASS + " : Values '" + currentValue
                            + "' and '" + savedValue
                            + "'  are successfully compared.";
                } else {
                    return Constants.FAIL + " : Values '" + currentValue
                            + "' and '" + savedValue
                            + "'  are not successfully compared.";
                }
            } else {
                return Constants.FAIL;
            }
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while comparing saved values '" + e;
        }

    }

    public String saveUiValue(Map<String, String> argsList) {
        APP_LOG.info("Inside type");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        this.element = getElement(this.elementId);
        if (this.element != null) {
            String pageValueString = this.element.getText().trim();
            this.setValueInMemory(this.inputValue, pageValueString);
            return Constants.PASS + "UI value is saved successfully.";
        } else {
            return Constants.FAIL + "UI value is not saved successfully.";
        }

    }

    public String getValueFromMemory(String key) {
        String savedValue = "";

        APP_LOG.debug("Func getValueFromMemory : key=" + key);

        if (this.presistanceMap.containsKey(key)) {
            savedValue = this.presistanceMap.get(key);
        }
        return savedValue;
    }

    public String setValueInMemory(String key, String value) {
        APP_LOG.debug("saveValueInMemory : key=" + key + " || value=" + value);
        try {
            if (!"".equals(key)) {
                this.presistanceMap.put(key, value);
                return Constants.PASS + "Value is set in memory.";
            } else {
                return Constants.FAIL + "Value is not set in memory.";
            }
        } catch (UnsupportedOperationException | ClassCastException
                | NullPointerException | IllegalArgumentException e) {
            return Constants.FAIL
                    + ": Unexpected Error while setting value in memory.'" + e;
        }

    }

    public void getViewSource() {
        APP_LOG.debug("/******************************/");
        WebDriver webDriver = returnDriver();
        try {
            String pageSource = webDriver.getPageSource();
            APP_LOG.debug("/******************************/");
            APP_LOG.debug(pageSource);
            APP_LOG.debug("/******************************/");
        } catch (Exception e) {
            APP_LOG.error("" + e);
        }
    }

    public String acceptAlert(Map<String, String> argsList) {
        WebDriver webDriver = returnDriver();
        String isAlertPresent = isAlertPresent();
        if (isAlertPresent.contains(Constants.PASS)) {
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
            return Constants.PASS + ": Alert is found and accepted.";
        } else {
            return isAlertPresent;
        }
    }

    public String focus() {
        APP_LOG.info("in focusOnUI");
        WebDriver webDriver = returnDriver();
        webDriver = returnDriver();
        try {
            webDriver.switchTo().activeElement();
            // Switch to currently active element in a page
        } catch (Exception t) {
            APP_LOG.error("Error in captureScreenshot" + t);
            return Constants.FAIL;
        }
        return Constants.PASS;
    }

    public String closeBrowser() {
        APP_LOG.info("in closeBrowser");
        WebDriver webDriver = returnDriver();
        try {
            webDriver.quit();
            return Constants.PASS + "Browser is closed successfully.";
        } catch (Exception e) {
            APP_LOG.debug("Unable to close the  browser" + e);
            return Constants.FAIL + " Browser is not closed.";
        }
    }

    public String navigateBrowserBack(Map<String, String> argsList) {
        APP_LOG.info("Navigate Browser Back");
        WebDriver webDriver = returnDriver();
        try {
            webDriver.navigate().back();
            // webDriver.navigate().refresh();
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return Constants.PASS + "Navigate back";
        } catch (Exception e) {
            APP_LOG.debug("Unable to Navigate to page" + e);
            return Constants.FAIL + "Back Button is not Navigated";
        }
    }

    public String navigateToUrl(Map<String, String> argsList) {

        WebDriver webDriver = returnDriver();
        try {
            APP_LOG.info("Navigate Browser Back");
            this.inputValue = argsList.get("InputValue");
            webDriver.navigate().to(this.inputValue);
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return Constants.PASS + "navigated to specific URL";
        } catch (Exception e) {
            APP_LOG.debug("Unable to Navigate to page" + e);
            return Constants.FAIL + "Not navigated to specific URL.";
        }
    }

    /**
     * @author nitish.jaiswal
     * @description to check if element is enabled
     * @param argsList
     *            map contains elementId and input value .
     */
    public String verifyisEnabled(Map<String, String> argsList) {
        APP_LOG.info("To check if element is enabled");

        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            WebElement element = getElement(this.elementId);
            if (element.isEnabled() == true) {
                return Constants.PASS;
            } else {
                return Constants.FAIL;
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL;
        }

    }

    /**
     * @author nitish.jaiswal
     * @description to check if element is diplayed
     * @return pass/fail
     */
    public String verifyisDispalyed(Map<String, String> argsList) {
        APP_LOG.info("To check if element is displayed");
        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            WebElement element = getElement(this.elementId);
            if (element.isDisplayed() == true) {
                return Constants.PASS + "Element '" + this.elementId
                        + "' is displayed.";
            } else {
                return Constants.FAIL + "Element '" + this.elementId
                        + "' is not displayed.";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL
                    + ": Unexpected Error while setting value in memory.'" + e;
        }

    }

    /**
     * @author nitish.jaiswal
     * @description to check if element is selected
     * @param argsList
     *            map contains
     * @return pass/fail
     */
    public String verifyisSelected(Map<String, String> argsList) {
        APP_LOG.info("To check if element is selected");
        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            WebElement element = getElement(this.elementId);
            if (element.isSelected() == true) {
                return Constants.PASS + "Element '" + this.elementId
                        + "' is selected.";
            } else {
                return Constants.FAIL + "Element '" + this.elementId
                        + "' is not selected.";
            }
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL
                    + ": Unexpected Error while setting value in memory.'" + e;
        }

    }

    public String switchBrowserWindow(Map<String, String> argsList) {
        APP_LOG.info("inside switchBrowserWindow");
        WebDriver webDriver = returnDriver();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            APP_LOG.debug("InterruptedException", e);
            Thread.currentThread().interrupt();
        }
        String parentHandle = webDriver.getWindowHandle();
        APP_LOG.info("parentHandle");
        APP_LOG.info("parent " + parentHandle);
        boolean childWindow = false;
        for (String winHandle : webDriver.getWindowHandles()) {
            APP_LOG.info("winHandle");
            APP_LOG.info("WindowHandle" + winHandle);
            if (!winHandle.equals(parentHandle)) {
                childWindow = true;
                webDriver.switchTo().window(winHandle);
                break;
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            APP_LOG.debug("InterruptedException", e);
            Thread.currentThread().interrupt();
        }
        if (childWindow) {
            APP_LOG.info("Window Switch Pass");
            APP_LOG.info("Window Switch Pass");
            return Constants.PASS;
        } else {
            APP_LOG.info("Window Switch Fail");
            APP_LOG.info("Window Switch Fail");
            return Constants.FAIL;
        }

    }

    /**
     * @desc Keyword added for uploading of the file component.
     * @param log
     *            application logs
     * @param args
     *            map contains elementId and input value.
     */
    public String uploadFile(Map<String, String> argsList) {
        APP_LOG.info("inside uploadFile");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            this.element.sendKeys(this.inputValue);
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL;
        }
        return Constants.PASS;
    }

    /**
     * @desc Adds the delay value.
     * @param args
     *            map contains elementId
     */
    public String addDelay(Map<String, String> argsList) {
        APP_LOG.info("inside addDelay");
        String delay = argsList.get("ElementId");
        Long delayinLong = Long.parseLong(delay);
        try {
            Thread.sleep(delayinLong);
        } catch (InterruptedException e) {
            APP_LOG.error("Func: addDelay" + e);
            Thread.currentThread().interrupt();
            return Constants.FAIL;
        }
        return Constants.PASS;
    }

    /**
     * @desc Switches to a particular frame.
     * @param args
     *            map contains elementId
     */
    public String switchToFrame(Map<String, String> argsList) {
        APP_LOG.info("inside switchToFrame");
        WebDriver webDriver = returnDriver();
        this.elementId = argsList.get("ElementId");
        try {
            webDriver.switchTo().defaultContent();
            webDriver.switchTo().frame(getElement(this.elementId));

        } catch (Exception e) {
            APP_LOG.debug("Switch frame exception" + e);
            return Constants.FAIL + "Frame is not switched.";
        }
        return Constants.PASS + "Frame is switched.";
    }

    /**
     * @desc Switches out of that particular frame.
     * @param elementId
     *            element locator
     */
    public String switchOutFrame(String elementId) {
        APP_LOG.info("inside switchoutFrame");
        WebDriver webDriver = returnDriver();
        try {
            webDriver.switchTo().defaultContent();

        } catch (Exception e) {
            APP_LOG.debug("Switch frame exception" + e);
            return Constants.FAIL;
        }
        return Constants.PASS;
    }

    /*
     * public String executeAutoItScript(Map<String, String> argsList) {
     * APP_LOG.debug("Func: executeAutoItScript "); this.inputValue =
     * argsList.get("InputValue");
     * 
     * try { java.lang.Runtime.getRuntime().exec(inputValue);
     * Thread.sleep(2000);
     * 
     * return Constants.PASS; } catch (IOException e) {
     * APP_LOG.debug("Func: executeAutoItScript || Exception:" + e); return
     * Constants.FAIL; } catch (InterruptedException e) {
     * APP_LOG.debug("Func: executeAutoItScript || Exception:" + e);
     * Thread.currentThread().interrupt(); return Constants.FAIL; } }
     */

    /**
     * @description This function is to perform the mouse hover.
     * @param argsList
     *            map contains elementId and input value.
     */
    public String mouseHover(Map<String, String> argsList) {
        APP_LOG.info("Inside dateParameter");
        WebDriver webDriver = returnDriver();
        this.inputValue = argsList.get("InputValue");
        this.elementId = argsList.get("ElementId");
        try {
            long temp_data = Integer.parseInt(this.inputValue);

            Actions actions = new Actions(webDriver);
            WebElement menuHoverLink = getElement(this.elementId);
            actions.moveToElement(menuHoverLink);
            Action a = actions.moveToElement(menuHoverLink).build();
            Thread.sleep(temp_data);
            a.perform();
            Thread.sleep(temp_data);
            return Constants.PASS + "Element with '" + this.elementId
                    + "' is hovered.";
        } catch (Exception e) {
            APP_LOG.debug("Inside dateParameter : " + e);
            return Constants.FAIL + "Element with '" + this.elementId
                    + "' is not hovered." + e;
        }

    }

    public String getSubstring(Map<String, String> argsList) {
        APP_LOG.info("Fetching the substring from the given string");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            String spiltValues[] = this.inputValue.split("\\|");
            int startIndex = Integer.parseInt(spiltValues[0]);
            int endIndex = Integer.parseInt(spiltValues[1]);
            this.element = getElement(this.elementId);
            String eleText = this.element.getText().trim();
            eleText = eleText.substring(startIndex, endIndex);
            return eleText;
        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during getting substring of text || Exception:"
                            + e);
            return Constants.FAIL + "";
        }
    }

    public String mouseHoverOnElement(Map<String, String> argsList) {
        try {
            this.elementId = argsList.get("ElementId");
            this.inputValue = argsList.get("InputValue");
            APP_LOG.info("in element on mousehover" + this.elementId
                    + this.inputValue);
            APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);
            List<WebElement> elements = getElements(this.elementId);
            APP_LOG.info("size mouse hover" + elements.size());
            int indexPos = Integer.valueOf(this.inputValue);
            APP_LOG.info("indexPos" + indexPos);
            return this.mouseHover(elements.get(indexPos));
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected Error while hovering on an element.'" + e;
        }

    }

    public String mouseHover(WebElement element) {
        APP_LOG.debug("Func:Mouse hover over an element");

        try {
            WebDriver webDriver = returnDriver();
            String fontSizeBefore = element.getCssValue("font-weight");
            APP_LOG.info("Before:" + fontSizeBefore);
            Actions action = new Actions(webDriver);
            action.moveToElement(element).build().perform();
            Thread.sleep(5000);
            String fontSizeAfter = element.getCssValue("font-weight");
            APP_LOG.info("After:" + fontSizeAfter);
            action.moveToElement(element).click().release().build().perform();
            if ("bold".equalsIgnoreCase(fontSizeAfter)) {
                return Constants.PASS;
            }

        } catch (Exception e) {
            APP_LOG.debug("Func:Mouse hover over an element=" + e);
            return Constants.FAIL;
        }
        return Constants.FAIL;

    }

    public String getListSubstring(Map<String, String> argsList) {
        APP_LOG.info(
                "Fetching the substring from the given of the list string");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            String spiltValues[] = this.inputValue.split("\\|");
            int startIndex = Integer.parseInt(spiltValues[0]);
            int endIndex = Integer.parseInt(spiltValues[1]);
            int listPosition = Integer.parseInt(spiltValues[2]);
            List<WebElement> element = this.FindElement
                    .getElements(this.elementId);

            if (element.size() > 0) {
                String eleText = element.get(listPosition).getText().trim();
                eleText = eleText.substring(startIndex, endIndex);
                return eleText;
            } else {
                return Constants.FAIL;
            }

        } catch (Exception e) {
            collectFailureMessage(
                    "Exception during getting substring of text || Exception:"
                            + e);
            return Constants.FAIL
                    + ": Unexpected Error while getting substring from List.'"
                    + e;
        }
    }

    /**
     * @author mukul.sehra
     * @description Drag a sourceElement and drop it over the
     *              destinationElement.
     * @param argsList
     *            map contains elements id.
     */
    public String dragAndDrop(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionDragNdrop");

        WebElement sourceElement, destinationElement;
        sourceElement = getElement(argsList.get("ElementId"));
        destinationElement = getElement(argsList.get("ElementId1"));
        WebDriver webDriver = returnDriver();

        // DragAndDrop using Actions class
        try {
            Actions action = new Actions(webDriver);
            action.dragAndDrop(sourceElement, destinationElement).perform();
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionDragNdrop = " + e);
            return Constants.FAIL + ": Error while getting Element - "
                    + sourceElement + "or" + destinationElement + " : " + e;
        }
        return Constants.PASS + "Source element - '" + sourceElement
                + "' has been dragged on dropped over Destination Element"
                + destinationElement + ".";
    }

    /**
     * @author mukul.sehra
     * @description Right click the webElement to open the context menu.
     * @param argsList
     *            map contains element id.
     * @return Pass/Fail
     */
    public String rightClick(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionRightClick");

        WebElement element;
        element = getElement(argsList.get("ElementId"));
        WebDriver webDriver = returnDriver();

        // RightClick on the webElement
        try {
            Actions action = new Actions(webDriver);
            action.contextClick(element).perform();
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionRightClick = " + e);
            return Constants.FAIL + ": Error while getting Element - '"
                    + element + " : " + e;
        }
        return Constants.PASS + "Element - " + element + ".";

    }

    /**
     * @author pallavi.tyagi
     * @description Scroll into view of a particular element on the page.
     * @return Pass/Fail
     */
    public String scrollIntoView(Map<String, String> argsList) {
        APP_LOG.debug("Func:actionScrollIntoView");
        WebDriver driver = returnDriver();

        WebElement element;
        element = getElement(argsList.get("ElementId"));

        // scroll into view using javascript
        try {

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true); window.scrollBy(0, -176);",
                    element);

        } catch (Exception e) {
            APP_LOG.debug(" Func:scrollIntoView = " + e);
            return Constants.FAIL + ": Error while getting Element - '"
                    + element + " : " + e;
        }
        return Constants.PASS + "Successfuly scrolled to Element - " + element
                + ".";

    }

    /**
     * Press Enter Key.
     * 
     * @author : Pallavi Tyagi
     * @return Pass/Fail
     */
    public String pressEnterKey(Map<String, String> argsList) {
        APP_LOG.debug("Func:pressEnterKey");
        WebDriver webDriver = returnDriver();

        try {
            Actions action = new Actions(webDriver);
            action.sendKeys(Keys.ENTER).perform();

        } catch (Exception e) {
            APP_LOG.debug(" Func:pressEnterKey = " + e);
            return Constants.FAIL + " : " + e;
        }
        return Constants.PASS;

    }

    /**
     * Press Tab Key.
     * 
     * @author : Mehak Verma
     * @return Pass/Fail
     */
    public String pressTabKey(Map<String, String> argsList) {
        APP_LOG.debug("Func:pressTabKey");
        WebDriver webDriver = returnDriver();

        try {
            Actions action = new Actions(webDriver);
            action.sendKeys(Keys.TAB).perform();

        } catch (Exception e) {
            APP_LOG.debug(" Func:pressTabKey = " + e);
            return Constants.FAIL + " : " + e;
        }
        return Constants.PASS + ": Element - '" + "" + "Press Tab Key";

    }

    /**
     * @author tarun.gupta1
     * @date 24 May, 2017
     * @description To press backspace inside text area
     */
    public String backKey(Map<String, String> argsList)
            throws InterruptedException {
        this.elementId = argsList.get("ElementId");

        APP_LOG.info("Inside enterKey");
        this.element = getElement(this.elementId);
        String val = this.element.getAttribute("value");
        APP_LOG.info(this.element.getAttribute("value"));
        int valLength = val.length();
        APP_LOG.info(valLength);

        try {
            do {
                this.element.sendKeys(Keys.BACK_SPACE);
                valLength--;
            } while (valLength != 0);
            return Constants.PASS + ": Element-'" + this.elementId
                    + "' is cleared";
        } catch (Exception e) {
            APP_LOG.debug("Inside backKey : " + e);
            return Constants.FAIL + ": Error while finding element for - '"
                    + this.elementId + "' : " + e;

        }
    }

    /**
     * @author mohit.gupta5
     * @param element
     *            textbox text locator
     * @param elementId
     *            textbox locator
     */
    public String clearTextBox(WebElement element, String elementId) {

        if (element != null) {
            try {
                element.clear();
                return Constants.PASS + ": Element text box is - '" + elementId
                        + "' cleared ";
            } catch (WebDriverException e) {
                return Constants.FAIL + ": Error while clearing Element - '"
                        + elementId + "' text box. " + e;
            }
        } else {

            return Constants.FAIL + ": Element is - '" + elementId + "' null ";
        }
    }

    /**
     * @author mohit.gupta5
     * @description : Clear Input text field
     * @return Pass/Fail
     */
    public String clearTextBox(Map<String, String> argsList) {
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            if (this.element != null) {

                String isCleared = this.clearTextBox(this.element,
                        this.elementId);
                if (isCleared.contains(Constants.PASS)) {
                    return Constants.PASS + ": InputText - '" + this.elementId
                            + "' is cleared ";
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

    public String scrollIntoView(String element) {
        APP_LOG.debug("Func:actionScrollIntoView");
        WebDriver driver = returnDriver();

        WebElement ele;
        ele = getElement(element);

        // scroll into view using javascript
        try {

            ((JavascriptExecutor) returnDriver())
                    .executeScript("arguments[0].scrollIntoView()", ele);
        } catch (Exception e) {
            APP_LOG.debug(" Func:actionRightClick = " + e);
            return Constants.FAIL + ": Error while getting Element - '"
                    + element + " : " + e;
        }
        return Constants.PASS + "Element - " + element + ".";

    }

    public String elementIsNotVisible(Map<String, String> argsList) {

        try {
            WebDriver webDriver = returnDriver();
            this.elementId = argsList.get("ElementId");
            this.element = getElement(this.elementId);
            FluentWait<WebDriver> wait = new FluentWait<>(webDriver);
            wait.withTimeout(10000, TimeUnit.MILLISECONDS);
            wait.pollingEvery(250, TimeUnit.MILLISECONDS);
            wait.ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(this.element));
            APP_LOG.info("Following element visible Not on the page:"
                    + this.element);
            return Constants.FAIL + ": Element '" + this.elementId
                    + "' is visible.";
        } catch (Exception e) {
            return Constants.PASS + ": Element '" + this.elementId
                    + "' is Not visible.";
        }
    }

    /**
     * @author ratnesh.singh
     * @description Clear text box via Actions.
     * @param argsList
     *            map contains element id.
     * @return Pass/Fail
     */
    public String clearViaActions(Map<String, String> argsList) {
        APP_LOG.debug("Func:clearViaActions");

        try {
            WebElement element;
            element = getElement(argsList.get("ElementId"));
            Actions actions = new Actions(returnDriver());
            actions.click(element);
            // actions.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                    .perform();
            actions.sendKeys(Keys.DELETE);
            actions.build().perform();

        } catch (Exception e) {
            APP_LOG.debug(" Func:clearViaActions = " + e);
            return Constants.FAIL + ": Error while getting Element - '"
                    + argsList.get("ElementId") + " : " + e;
        }
        return Constants.PASS + ": Cleared Element- "
                + argsList.get("ElementId") + ".";

    }

}