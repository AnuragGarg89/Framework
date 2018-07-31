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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;

public class FindElement extends ReadObjectRepoXml {

    public FindElement() {
        // TODO Auto-generated constructor stub
    }

    public ConcurrentHashMap<String, String> hs = new ConcurrentHashMap<>();

    public WebElement getElement(String elementName) {
        APP_LOG.info("Inside findElement");
        this.hs = getElementAttribute(elementName);
        WebElement element = this.findlocatorIdElement(this.hs);
        return element;
    }

    public List<WebElement> getElements(String elementName) {
        APP_LOG.info("Inside findElement");
        this.hs = getElementAttribute(elementName);
        List<WebElement> listOfElements = this.findlocatorIdElements(this.hs);
        return listOfElements;
    }

    public WebElement iterateElement(String value) {
        Iterator it = this.hs.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            if (value != null) {
                it.remove();
                break;
            }
        }
        element = this.findlocatorIdElement(this.hs);
        return element;

    }

    public List<WebElement> iterateElements(String value) {
        Iterator it = this.hs.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            if (value != null) {
                it.remove();
                break;
            }
        }
        elements = this.findlocatorIdElements(this.hs);
        return elements;

    }

    private WebElement
            findlocatorIdElement(ConcurrentHashMap<String, String> hs) {
        String key = "";
        String value = "";
        WebElement element = null;
        Iterator entries = hs.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            key = (String) thisEntry.getKey();
            value = (String) thisEntry.getValue();
            APP_LOG.info("Key and value in Map:" + key + value);
            break;
        }

        if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_XPATH)
                || key.startsWith("//") || key.startsWith("xp")
                || key.startsWith("xpath=(//")) {

            try {
                element = this.waitForVisibilityOfElement(By.xpath(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_ID)) {

            try {
                element = this.waitForVisibilityOfElement(By.id(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_NAME)) {

            try {
                element = this.waitForVisibilityOfElement(By.name(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CSS)) {

            try {
                element = this
                        .waitForVisibilityOfElement(By.cssSelector(value));

            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CLASSNAME)) {

            try {
                element = this.waitForVisibilityOfElement(By.className(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_LINKTEXT)) {
            try {
                element = this.waitForVisibilityOfElement(By.linkText(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        } else if (key
                .equalsIgnoreCase(Constants.PREFIX_FIELD_PARTIALLINKTEXT)) {

            try {
                element = this
                        .waitForVisibilityOfElement(By.partialLinkText(value));
            } catch (org.openqa.selenium.TimeoutException e) {

                APP_LOG.error("Error while Session time out" + e);

                this.iterateElement(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_TAGNAME)) {

            try {
                element = this.waitForVisibilityOfElement(By.tagName(value));
            } catch (org.openqa.selenium.TimeoutException e) {
                APP_LOG.error("" + e);
                this.iterateElement(value);
            }
        }
        return element;
    }

    /**
     * This function is to find elements.
     * 
     * @return element
     */
    public List<WebElement>
           findlocatorIdElements(ConcurrentHashMap<String, String> hs) {
        String key = "";
        String value = "";
        List<WebElement> element = null;
        Iterator entries = hs.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            key = (String) thisEntry.getKey();
            value = (String) thisEntry.getValue();
            APP_LOG.info("Key and Value in Map:" + key + value);
            break;
        }
        if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_XPATH)
                || key.startsWith("//") || key.startsWith("xp")
                || key.startsWith("xpath=(//")) {

            try {
                element = this.waitForVisibilityOfElements(By.xpath(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_ID)) {

            try {
                element = this.waitForVisibilityOfElements(By.id(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_NAME)) {

            try {
                element = this.waitForVisibilityOfElements(By.name(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CSS)) {

            try {
                element = this
                        .waitForVisibilityOfElements(By.cssSelector(value));

            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CLASSNAME)) {

            try {
                element = this.waitForVisibilityOfElements(By.className(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_LINKTEXT)) {
            try {
                element = this.waitForVisibilityOfElements(By.linkText(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key
                .equalsIgnoreCase(Constants.PREFIX_FIELD_PARTIALLINKTEXT)) {

            try {
                element = this
                        .waitForVisibilityOfElements(By.partialLinkText(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        } else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_TAGNAME)) {

            try {
                element = this.waitForVisibilityOfElements(By.tagName(value));
            } catch (Exception e) {
                APP_LOG.error("" + e);
                this.iterateElements(value);

            }
        }
        return element;

    }

    public WebElement waitForVisibilityOfElement(By locator) {
        try {
            APP_LOG.debug("inside waitForVisibilityOfElement");
            WebDriver webDriver = returnDriver();
            this.checkPageIsReady();
            WebDriverWait wait = new WebDriverWait(webDriver,
                    maxTimeOutForElement);
            element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator));
            this.highlight(element);
            APP_LOG.info("Following locator visible on the page: " + locator);

        } catch (org.openqa.selenium.TimeoutException e) {
            APP_LOG.debug("waitForVisibilityOfElement bye bye after timeout="
                    + maxTimeOutForElement + " : Locator=" + locator + e);
            throw e;

        }

        catch (NoSuchElementException e) {
            throw e;

        } catch (StaleElementReferenceException e) {
            APP_LOG.error("Stale Exception occured", e);
        }

        return element;
    }

    public void navigateBack() {
        try {
            WebDriver webDriver = returnDriver();
            webDriver.navigate().back();
            webDriver.navigate().refresh();
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception comes when navigating back to previous page", e);
        }
    }

    public void refresh() {
        try {
            WebDriver webDriver = returnDriver();
            webDriver.navigate().refresh();
        } catch (Exception e) {
            APP_LOG.error("Exception comes when using refresh function", e);
        }
    }

    public List<WebElement> waitForVisibilityOfElements(By locator)
            throws Exception {
        try {
            APP_LOG.debug("inside waitForVisibilityOfElements");
            WebDriver webDriver = returnDriver();
            this.checkPageIsReady();
            WebDriverWait wait = new WebDriverWait(webDriver,
                    maxTimeOutForElement);
            elements = wait.until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(locator));
            APP_LOG.debug("Web element Found:" + locator);

        } catch (org.openqa.selenium.TimeoutException e) {
            APP_LOG.debug("waitForVisibilityOfElement bye bye after timeout="
                    + maxTimeOutForElement + " : Locator=" + locator);
            throw e;

        }

        catch (Exception e) {
            throw e;

        }
        return elements;

    }

    /**
     * @author Abhishek Sharda
     * @throws InterruptedException
     * @description: Wait till 30 seconds for React(API calls) to load on page.
     */

    public void checkPageIsReady() {

        WebDriver webDriver = returnDriver();
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 120);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("pe-loadingIndicator-chip")));
            TimeUnit.SECONDS.sleep(1);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("pe-loadingIndicator-chip")));
            APP_LOG.info(
                    "All API calls sucessfully completed on this page and test is ready for execution");
        } catch (TimeoutException | InterruptedException e) {
            APP_LOG.error(
                    "Error occured while waiting for API calls completed on the page: ",
                    e);
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param element-->
     *            WebElement on which highlight will be done
     */

    public void highlight(WebElement element) {
        final int wait = 500;
        String originalStyle = element.getAttribute("style");
        try {

            this.setAttribute(element, "style",
                    "background-color: yellow; outline: 1px solid rgb(136, 255, 136);;");
            Thread.sleep(wait);
            this.setAttribute(element, "style", originalStyle);

        } catch (InterruptedException e) {
            APP_LOG.error("Exception comes when highlight the webelement", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param element-->
     *            WebElement on which highlight will be done
     * @param value-->
     *            Highlight type
     */

    public void setAttribute(WebElement element, String attributeName,
            String value) {

        WebDriver webDriver = returnDriver();
        int attempts = 0;
        try {
            while (attempts < 6) {
                try {
                    for (int i = 0; i < 3; i++) {
                        JavascriptExecutor js = (JavascriptExecutor) webDriver;
                        js.executeScript(
                                "arguments[0].setAttribute('style', arguments[1]);",
                                element, value);
                    }
                    APP_LOG.info(
                            "No 'Stale element exception' occurred on highlighting");
                    break;
                } catch (StaleElementReferenceException staleElementException) {
                    APP_LOG.info(
                            "'Stale element exception' occurred on highlighting and handeled");
                    System.err.println(
                            "'Stale element exception' occurred and is handled");
                }
                attempts++;
            }
        } catch (Exception t) {
            APP_LOG.info("Error came : " + t);
        }

    }

    /**
     * @author sumit.bhardwaj
     * @param :no
     *            parameters passed
     * @description public static void waitForPageToLoad() method specification.
     *              :- 1) Waits for a new page to load completely 2) new
     *              WebDriverWait(driver, 60) -> Waits for 60 seconds 3)
     *              wait.until((ExpectedCondition<Boolean>) Wait until expected
     *              condition (All documents present on the page get ready)met
     * @return : void
     */

    public static void waitForPageToLoad() {

        BaseClass b = new BaseClass() {
        };
        WebDriver webDriver = b.returnDriver();
        try {

            // Waits for 60 seconds
            WebDriverWait wait = new WebDriverWait(webDriver, 60);
            // Wait until expected condition (All documents present on the page
            // get ready) met
            wait.until(
                    (ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            if (!(d instanceof JavascriptExecutor)) {
                                return true;
                            }
                            Object result = ((JavascriptExecutor) d)
                                    .executeScript(
                                            "return document['readyState'] ? 'complete' == document.readyState : true");
                            if (result != null && result instanceof Boolean
                                    && (Boolean) result) {
                                return true;
                            }
                            return false;
                        }
                    });
        }

        catch (Exception waitForPageToLoadException) {
            APP_LOG.info("Error came while waiting for page to load "
                    + waitForPageToLoadException);

        }

    }

    /**
     * </p>
     * <ul>
     * <li>1. This function will attempt 3 times to click the element.</li>
     * <li>2. If Stale Element Reference exception occurs then handle it in
     * catch block and give another try</li>
     * <li>3. If element is clicked successfully then return result as true</li>
     * </ul>
     * 
     * @author sumit.bhardwaj
     * @param--> HashMap containing Element name and Element value(if any)
     * @return : True if element is clicked successfully, otherwise false
     * @throws InterruptedException
     */
    public List<WebElement>
           findListAndHandleStaleElementException(String elementName) {
        APP_LOG.info("Inside findElement");
        this.hs = getElementAttribute(elementName);
        List<WebElement> listOfEle = new ArrayList<WebElement>();
        int attempts = 0;
        try {

            while (attempts < 5) {

                try {
                    listOfEle = this.findlocatorIdElements(this.hs);
                    APP_LOG.info("No 'Stale element exception' occurred");
                    break;

                } catch (StaleElementReferenceException staleElementException) {

                    APP_LOG.info(
                            "'Stale element exception' occurred and is handled");
                    System.err.println(
                            "'Stale element exception' occurred and is handled");
                }

                attempts++;
            }

        } catch (Exception t) {

            // Log the exception
            APP_LOG.error(
                    "Error while clicking and handling stale element reference: "
                            + t);
            System.err.println(
                    "Error while clicking and handling stale element reference: "
                            + t.getMessage());

            return listOfEle;
        }

        return listOfEle;
    }

    /**
     * @author Nitish Jaiswal
     * @throws InterruptedException
     * @description: Wait till 60 seconds for React(API calls) to load on page.
     */

    public void checkPageIsReadyWithLogs() {

        WebDriver webDriver = returnDriver();
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 90);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("pe-loadingIndicator-chip")));
            TimeUnit.SECONDS.sleep(1);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("pe-loadingIndicator-chip")));
            APP_LOG.info(
                    "All API calls sucessfully completed on this page and test is ready for execution");
        } catch (Exception e) {
            APP_LOG.error(
                    "Error occured while waiting for API calls completed on the page: ",
                    e);
            logResultInReport(
                    Constants.FAIL
                            + ": Error occured while waiting for API calls completed on the page: "
                            + e.getMessage(),
                    "Verify page is loaded.", reportTestObj);
        }
    }

}