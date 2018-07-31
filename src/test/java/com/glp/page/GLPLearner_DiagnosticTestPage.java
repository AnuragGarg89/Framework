/*
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_DiagnosticTestPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private FindElement findElement = new FindElement();
    private PerformAction performAction = new PerformAction();
    public int progressStartIndex = 0;
    public int progressMaxIndex = 0;

    public GLPLearner_DiagnosticTestPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
        /*
         * try { performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
         * "DiagnosticSubmitButton"); } catch (Exception e) { refreshPage();
         * performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
         * "DiagnosticSubmitButton"); }
         */
        // setProgressIndexValue();
    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Verify element is present
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOGS.debug("Element is present: " + locator);
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

    public String mouseHoverMoveToElement(WebElement element) {
        APP_LOG.debug("Func: Mouse hover over an element");
        try {
            WebDriver webDriver = returnDriver();
            Actions action = new Actions(webDriver);
            action.moveToElement(element).build().perform();
            return Constants.PASS + ": Mouse Hovering of element- is done.";
        } catch (Exception e) {
            APP_LOG.debug("Func:Mouse hover over an element=" + e);
            return Constants.FAIL
                    + ": Unexpected Error while Hovering for element: " + e;
        }
    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Verify element is present
     * @return The object of ProductApplication_courseHomePage
     */
    public String getText(String element) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, element);
        return valueText;
    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String locator, String text,
            String message) {
        System.out.println();
        APP_LOGS.debug("Element is present: " + locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, locator,
                text);
        logResultInReport(this.result, message, this.reportTestObj);
        return result;
    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Click on a web element
     * @return The object of ProductApplication_courseHomePage
     */

    public void clickOnElement(String locator, String message) {

        APP_LOGS.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        if ((webDriver instanceof SafariDriver
                || capBrowserName.equalsIgnoreCase("Safari"))
                && (locator.equalsIgnoreCase("FIBFreeResponse"))) {
            FindElement ele = new FindElement();
            ele.getElement(locator).sendKeys(Keys.TAB);
        } else if ((webDriver instanceof SafariDriver
                || capBrowserName.equalsIgnoreCase("Safari")
                || capBrowserName.equalsIgnoreCase("MicrosoftEdge"))) {
            if (locator.equals("FIBFreeResponse")) {
                this.result = this.performAction.execute(ACTION_CLICK, locator);
            } else {
                this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                        locator);
            }

        } else {
            this.result = this.performAction.execute(ACTION_CLICK, locator);
        }
        logResultInReport(this.result, message, this.reportTestObj);

    }

    /**
     * @author nisha.pathria
     * @date 10 May, 2017
     * @description To verify button is disabled or enabled
     */
    public void verifyButtonEnabledOrDisabled(String elementlocator,
            String value, String stepDesc) {
        result = performAction.execute(ACTION_VERIFY_ISENABLED, elementlocator,
                value);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author mayank.mittal
     * @date 10 May, 2017
     * @description To verify button is enabled for a List
     */
    public void verifyIsEnabledForList(String elementlocator, String stepDesc) {
        result = performAction.execute(ACTION_VERIFY_ISENABLE_IN_LIST,
                elementlocator);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author mayank.mittal
     * @date 10 May, 2017
     * @description To verify button is disabled for a List
     */
    public void verifyIsDisabledForList(String elementlocator,
            String stepDesc) {
        result = performAction.execute(ACTION_VERIFY_ISDISABLE_IN_LIST,
                elementlocator);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author nitish.jaiswal
     * @date 19 Sep,2017
     * @description Click on Pearson Logo
     * @return The object of ProductApplication_CourseHomePage
     */
    public GLPLearner_CourseViewPage navigateToCourseViewPage() {
        try {
            APP_LOGS.debug("Navigate to Course View Page");
            clickOnElement("PearsonLogo",
                    "Navigate to the course view page by clicking the Pearson logo.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author sumit.bhardwaj
     * @return ProductApplication_DiagnosticTestPage object
     */
    public GLPLearner_DiagnosticTestPage
           attempDiagnoisticTest(int questionToAttemptCount) {
        try {
            for (int i = 1; i <= questionToAttemptCount; i++) {
                getQuestionTypeAndAttempt();
            }
        } catch (Exception e) {
            APP_LOG.debug("Exception in attempTest");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mukul.sehra
     * @date 17 Oct, 2017
     * @description Skip diagnostic test questions by clicking "Submit" button
     */
    public GLPLearner_CourseMaterialPage skipDiagnosticTestQuestions(
            int diagnosticStartIteration, int diagnosticMaxIteration,
            String attemptType) {
        APP_LOG.debug(
                "Skip diagnostic test by clicking submit button without selecting/entering any value");

        try {
            setProgressIndexValue();
            int diagnosticMaxValue = progressMaxIndex;
            progressStartIndex = diagnosticStartIteration;
            if (diagnosticMaxIteration > 0) {
                progressMaxIndex = progressMaxIndex
                        - (progressMaxIndex - diagnosticMaxIteration);
            } else {
                progressMaxIndex = progressMaxIndex + progressMaxIndex;
            }
            for (int i = progressStartIndex; i < progressMaxIndex; i++) {

                FindElement ele = new FindElement();
                ele.checkPageIsReadyWithLogs();
                HashMap<String, List> availableQuestionTypes = returnSetOfQuestion();
                try {
                    List<WebElement> resultPageElement1 = returnDriver()
                            .findElements(By
                                    .cssSelector(".diagnostic-result__banner"));
                    List<WebElement> resultPageElement2 = returnDriver()
                            .findElements(By.cssSelector("#LOBtn_1"));

                    if (resultPageElement1.size() > 0
                            || resultPageElement2.size() > 0) {
                        logResultInReport(
                                Constants.PASS
                                        + ": Completed diagnostic test by clicking submit button without providing any answer",
                                "Attempt diagnostic test by clicking submit without answering any question",
                                reportTestObj);
                        break;
                    } else {
                        List<WebElement> iDontKnowButtons = ele
                                .getElements("SubmitWithoutAttempt");
                        for (int j = 0; j < iDontKnowButtons.size(); j++) {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript("arguments[0].click();",
                                    iDontKnowButtons.get(j));
                            ele.checkPageIsReadyWithLogs();
                        }
                        ele.checkPageIsReadyWithLogs();
                    }
                    if (progressMaxIndex < diagnosticMaxValue
                            && diagnosticMaxIteration != 0) {
                        returnSetOfQuestion();
                    }
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Exception while clicking Submit button - "
                                    + e.getMessage(),
                            "Attempt diagnostic test by clicking submit without answering any question",
                            reportTestObj);
                }

            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while attempting diagnostic test - "
                            + e.getMessage(),
                    "Attempt diagnostic test by clicking submit without answering any question",
                    reportTestObj);
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
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
     * @author sumit.bhardwaj
     * @return --> Return blank value after attempting question
     * @description --> Attempts Diagnostic Test
     * 
     */
    public void getQuestionTypeAndAttempt() {
        // Getting parent element of each activity type
        String element = "DiagnosticQuestionType";

        // Verifying if got it button is present then click it
        if (verifyElementPresentWithOutLog("DiagnosticGotItButton")
                .contains(Constants.PASS)) {

            clickOnElement("DiagnosticGotItButton", "Diagnostic Got it button");
        }

        // Getting 'id' of each activity type using its parent element
        String value = getAttribute(element, "id");
        try {

            // Creating a list to determine whether the question has a single
            // activity type or is multipart
            List<WebElement> numberOfQuestion = findElement
                    .findListAndHandleStaleElementException(element);
            try {
                // If list size is greater than 1 then its a multipart and we
                // will verify what all activity types are
                // part of question and attempt them
                if (numberOfQuestion.size() > 1) {
                    for (WebElement ek : numberOfQuestion) {
                        String local = ek.getAttribute("id");

                        // Checking for FIB free response
                        if (local.contains("FIB")) {
                            List<WebElement> el = findElement
                                    .findListAndHandleStaleElementException(
                                            "FIBFreeResponse");// FIBFreeResponse
                            if (el != null) {
                                if (el.size() != 0) {
                                    List<WebElement> list = findElement
                                            .findListAndHandleStaleElementException(
                                                    "FIBFreeResponse");// DiagnosticDisabledFibBox
                                    for (WebElement ele : list) {
                                        Actions actions = new Actions(
                                                returnDriver());
                                        actions.moveToElement(ele);
                                        actions.click();
                                        actions.sendKeys("Some Name");
                                        actions.build().perform();
                                    }
                                    clickOnElementAndHandleStaleException(
                                            "SubmitButton",
                                            "Click on Submit button");
                                    continue;
                                }
                            }
                            // Checking for FIB dropdown
                            List<WebElement> e = findElement
                                    .findListAndHandleStaleElementException(
                                            "FIBDropDown");
                            if (e.size() != 0) {
                                clickOnElementAndHandleStaleException(
                                        "DiagnosticDropdownMenu",
                                        "Open the dropdown menu");
                                clickOnElementAndHandleStaleException(
                                        "DiagnosticDropdownOptionOne",
                                        "Select first option from the dropdown.");
                                clickOnElementAndHandleStaleException(
                                        "SubmitButton",
                                        "Click on Submit button after attempting FIB_2 question");
                                APP_LOG.info("Attempted FIB_2 type question");
                                continue;
                            }
                        }
                        // Checking for Multiple choice single answer
                        else if (local.contains("McqSa")
                                || "McqSa_undefined".equalsIgnoreCase(local)) {

                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript(
                                        "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                                logResultInReport(
                                        Constants.PASS
                                                + ": Clicked on FirstRadio button",
                                        "Click on FirstRadio button",
                                        this.reportTestObj);
                                scrollWebPage(0, 400);
                            } catch (Exception e) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": Errorwhile clicking on FirstRadio button: "
                                                + e,
                                        "Click on FirstRadio button",
                                        this.reportTestObj);
                            }
                            continue;
                        }
                        // Checking for Multiple choice multiple answer
                        else if (local.contains("McqMa")
                                || "McqMa_undefined".equalsIgnoreCase(local)) {
                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript(
                                        "return document.querySelector(\"input[id*='_0'][type='checkbox']\").click();");
                                logResultInReport(
                                        Constants.PASS
                                                + ": Clicked on Firstcheckbox",
                                        "Click on FirstCheckBox",
                                        this.reportTestObj);
                                scrollWebPage(0, 400);
                            } catch (Exception e) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": Errorwhile clicking on Firstcheckbox button: "
                                                + e,
                                        "Click on FirstCheckBox button",
                                        this.reportTestObj);
                            }
                            continue;
                        } else {
                        }
                    }
                    return;
                }
            } catch (Exception e) {
                APP_LOG.error("Error while attemoting Diagnostic test " + e);
                return;
            }

            // Checking for individual FIB free response/Dropdown activity type
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
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button after attempting FIB_2 question");
                    APP_LOG.info("Attempted FIB_2 type question");
                    return;
                }
            }
            // Checking for individual MCQSA question
            else if (value.contains("McqSa")
                    || "McqSa_undefined".equalsIgnoreCase(value)) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                    logResultInReport(
                            Constants.PASS + ": Clicked on FirstRadio button",
                            "Click on FirstRadio button", this.reportTestObj);
                    scrollWebPage(0, 400);
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on FirstRadio button: "
                                    + e,
                            "Click on FirstRadio button", this.reportTestObj);
                }
            }
            // Checking for individual MCQMA question
            else if (value.contains("McqMa")
                    || "McqMa_undefined".equalsIgnoreCase(value)) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"label>input[type='checkbox']\").click();");
                    logResultInReport(
                            Constants.PASS + ": Clicked on Firstcheckbox",
                            "Click on FirstCheckBox", this.reportTestObj);
                    scrollWebPage(0, 400);
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on Firstcheckbox button: "
                                    + e,
                            "Click on FirstCheckBox button",
                            this.reportTestObj);
                }
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
    public String getAttribute(String element, String attribute) {

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String text = this.performAction.execute(ACTION_GET_ATTRIBUTE, element,
                attribute);
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
     *            --> Name of Element
     * @param stepDesc
     *            --> Description of Steps
     * 
     * @description --> clicks on element and handle StaleException if occurred.
     * 
     */

    public void clickOnElementAndHandleStaleException(String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION, element);
    }

    public boolean checkVisiblityOfIDontKnow() {
        boolean check = false;
        try {
            WebDriverWait wait = new WebDriverWait(returnDriver(),
                    maxTimeOutForElement);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.cssSelector(
                            "[id^='iDontKnowThisButton']:not([disabled])")));
            check = true;
            return check;

        } catch (Exception e) {

            return check;
        }

    }

    /**
     * @author mohit.gupta5
     * @date 23 Nov,2017
     * @description Click on Got it button
     * @return The object of ProductApplication_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage
           clickOnDiagnosticGotItButtonIfPresent() {
        APP_LOGS.debug("Click on Diagnostic Got it button");

        if (verifyElementPresentWithOutLog("DiagnosticGotItButton")
                .contains(Constants.PASS)) {

            clickOnElement("DiagnosticGotItButton",
                    "Click on Diagnostic 'Got it' button");

        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author mohit.gupta5
     * @date 14 Nov,2017
     * @description :Verify element not present.
     */
    public void verifyElementNotPresent(String locator, String message) {
        APP_LOGS.debug(locator + "Element is not present");
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 04 Dec, 2017
     * @description To verify the color Value of an element in HEX format
     */

    public void verifyElementColorValueHexFormat(String element,
            String colorValueHexadecimalFormat, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_COLOR_HEX_FORMAT);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(ELEMENT_INPUT_VALUE, colorValueHexadecimalFormat);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 05 Dec,2017
     * @description Verify element is Visible
     */
    public void verifyElementIsVisible(String locator, String message) {
        APP_LOGS.debug("Element is visible: " + locator);
        this.result = this.performAction
                .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 05 Dec,2017
     * @description Verify element is Not Visible
     */
    public void verifyElementIsNotVisible(String locator, String message) {
        APP_LOGS.debug("Element is Not visible: " + locator);
        this.result = this.performAction
                .execute(ACTION_WAIT_FOR_ELEMENT_IS_NOT_VISIBLE, locator);
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author mukul.sehra
     * @date 07 Dec,2017
     * @description Navigate a New Learner to a specified Activity type in
     *              Diagnostic Test
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToActivityType(String activityType) {
        APP_LOGS.debug("Navigating to '" + activityType
                + "' type question in Diagnostic test...");
        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + activityType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of fibFreeResponseTable activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponseTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of fibDropDown activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("fibDropDown"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown' type");
                    if (verifyElementPresentWithOutLog("DiagnosticFibDropDown")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of fibFreeResponse activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponse"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of multipart activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("multipart"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMultipart", "id")
                                    .toLowerCase()
                                    .contains(activityType.toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author sumit.bhardwaj
     * @description: attemptAllQuestionsOnMultiPart method will attempt all
     *               types of question on multipart
     */
    public void attemptAllQuestionsOnMultiPart() {

        String element = "DiagnosticQuestionType";

        // Creating a list to determine whether the question has a single
        // activity type or is multipart
        List<WebElement> numberOfQuestions = findElement
                .findListAndHandleStaleElementException(element);
        try {
            // If list size is greater than 1 then its a multipart and we
            // will verify what all activity types are
            // part of question and attempt them
            if (numberOfQuestions.size() > 1) {
                for (WebElement el : numberOfQuestions) {
                    String local = el.getAttribute("id");
                    // Checking for FIB free response
                    if (local.contains("FIB")) {
                        List<WebElement> sizeOfFreeResponse = findElement
                                .findListAndHandleStaleElementException(
                                        "FIBFreeResponse");// FIBFreeResponse
                        if (sizeOfFreeResponse != null) {
                            if (sizeOfFreeResponse.size() != 0) {
                                List<WebElement> list = findElement
                                        .findListAndHandleStaleElementException(
                                                "FIBFreeResponse");// DiagnosticDisabledFibBox
                                for (WebElement ele : list) {

                                    if ("Safari"
                                            .equalsIgnoreCase(capBrowserName)) {
                                        String clas = ele.getAttribute("class");
                                        WebDriver webDriver1 = returnDriver();
                                        JavascriptExecutor js1 = (JavascriptExecutor) webDriver1;
                                        js1.executeScript(
                                                "return window.jQuery(\"."
                                                        + clas
                                                        + "\").val(\"12345678A123456\")");
                                        ele.sendKeys(Keys.BACK_SPACE);
                                    } else {
                                        Actions actions = new Actions(
                                                returnDriver());
                                        actions.moveToElement(ele);
                                        actions.click();
                                        actions.sendKeys("Some Name");
                                        actions.build().perform();
                                    }
                                    try {
                                        WebDriver webDriver = returnDriver();
                                        JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                        js.executeScript(
                                                "arguments[0].click();",
                                                returnDriver().findElement(
                                                        By.cssSelector(
                                                                ".mathpallet-keyboard .mathpallate-close")));
                                        APP_LOG.info("Closed Math Pallate");
                                    } catch (Exception e) {
                                        APP_LOG.error(
                                                "Error while closing Math Pallate "
                                                        + e);
                                    }
                                }
                                clickOnElementAndHandleStaleException(
                                        "SubmitButton",
                                        "Click on Submit button ");
                                continue;
                            }
                        }
                        // Checking for FIB dropdown
                        List<WebElement> sizeOfDropDown = findElement
                                .findListAndHandleStaleElementException(
                                        "FIBDropDown");
                        if (sizeOfDropDown.size() != 0) {
                            clickOnElementAndHandleStaleException(
                                    "DiagnosticDropdownMenu",
                                    "Open the dropdown menu");
                            clickOnElementAndHandleStaleException(
                                    "DiagnosticDropdownOptionOne",
                                    "Select first option from the dropdown.");
                            clickOnElementAndHandleStaleException(
                                    "SubmitButton",
                                    "Click on Submit button after attempting FIB_2 question");
                            APP_LOG.info("Attempted FIB_2 type question");
                            continue;
                        }
                    }
                    // Checking for Multiple choice single answer
                    else if (local.contains("McqSa")
                            || "McqSa_undefined".equalsIgnoreCase(local)) {
                        WebDriver webDriver = returnDriver();
                        JavascriptExecutor js = (JavascriptExecutor) webDriver;
                        js.executeScript(
                                "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                        logResultInReport(this.result,
                                "Click on SelectFirstRadio button",
                                this.reportTestObj);
                        scrollWebPage(0, 400);
                        clickOnElementAndHandleStaleException("SubmitButton",
                                "Click on Submit button after attempting McqMa question");
                        APP_LOG.info("Attempted McqMa type question");
                        continue;
                    }
                    // Checking for Multiple choice multiple answer
                    else if (local.contains("McqMa")
                            || "McqMa_undefined".equalsIgnoreCase(local)) {
                        WebDriver webDriver = returnDriver();
                        JavascriptExecutor js = (JavascriptExecutor) webDriver;
                        js.executeScript(
                                "return document.querySelector(\"input[id*='_0'][type='checkbox']\").click();");
                        logResultInReport(this.result,
                                "Click on SelectFirstCheck button after attempting McqMa_5 question",
                                this.reportTestObj);
                        scrollWebPage(0, 400);
                        clickOnElementAndHandleStaleException("SubmitButton",
                                "Click on Submit button after attempting McqMa_5 question");
                        APP_LOG.info("Attempted McqMa_5 type question");
                        continue;
                    } else {
                    }
                }
                return;
            }

            else {
                APP_LOGS.info("Question is not of Multipart type");
            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting questions on Multipart " + e);
            return;
        }

    }

    /**
     * @author sumit.bhardwaj
     * @description: attemptFirstQuestionOnMultipart will attempt question on
     *               Multipart assessment
     */
    public void attemptFirstQuestionOnMultipart() {
        // Getting parent element of each activity type
        String element = "DiagnosticQuestionType";
        // Getting 'id' of each activity type using its parent element
        String value = getAttribute(element, "id");
        try {
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
                            if ("Safari".equalsIgnoreCase(capBrowserName)) {
                                String clas = ele.getAttribute("class");
                                WebDriver webDriver1 = returnDriver();
                                JavascriptExecutor js1 = (JavascriptExecutor) webDriver1;
                                js1.executeScript("return window.jQuery(\"."
                                        + clas
                                        + "\").val(\"12345678A123456\")");
                                ele.sendKeys(Keys.BACK_SPACE);
                            } else {
                                Actions actions = new Actions(returnDriver());
                                actions.moveToElement(ele);
                                actions.click();
                                actions.sendKeys("Some Name");
                                actions.build().perform();
                            }
                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript("arguments[0].click();",
                                        returnDriver()
                                                .findElement(By.cssSelector(
                                                        ".mathpallet-keyboard .mathpallate-close")));
                                APP_LOG.info("Closed Math Pallate");
                            } catch (Exception e) {
                                APP_LOG.error(
                                        "Error while closing Math Pallate "
                                                + e);
                            }
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
                            "DiagnosticDropdownMenu", "Open the dropdown menu");
                    clickOnElementAndHandleStaleException(
                            "DiagnosticDropdownOptionOne",
                            "Select first option from the dropdown.");
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button after attempting FIB_2 question");
                    APP_LOG.info("Attempted FIB_2 type question");
                    return;
                }
            }
            // Checking for individual MCQSA question
            else if (value.contains("McqSa")
                    || "McqSa_undefined".equalsIgnoreCase(value)) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                    scrollWebPage(0, 400);
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button after attempting McqSa question");
                    logResultInReport(
                            Constants.PASS + ": Clicked on FirstRadio button",
                            "Click on FirstRadio button", this.reportTestObj);
                    APP_LOG.info("Attempted McqSa type question");
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on FirstRadio button: "
                                    + e,
                            "Click on FirstRadio button", this.reportTestObj);
                }

            }
            // Checking for individual MCQMA question
            else if (value.contains("McqMa")
                    || "McqMa_undefined".equalsIgnoreCase(value)) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"label>input[type='checkbox']\").click();");
                    scrollWebPage(0, 400);
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on Submit button after attempting McqMa question");
                    logResultInReport(
                            Constants.PASS + ": Clicked on Firstcheckbox",
                            "Click on FirstCheckBox", this.reportTestObj);
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on Firstcheckbox button: "
                                    + e,
                            "Click on FirstCheckBox button",
                            this.reportTestObj);
                }
                APP_LOG.info("Attempted McqMa type question");
            } else {
                APP_LOG.info("No question shown on multipart ");
            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting questions on Multipart " + e);
        }
    }

    /**
     * @author lekh.bahl
     * @date 07 Dec,2017
     * @description Verify Multipart is summative
     */
    public boolean verifyMultipartIsSummative(String locator) {
        APP_LOGS.debug("Element is Not visible: " + locator);
        List<WebElement> multipartCount = findElement
                .findListAndHandleStaleElementException(locator);
        if (multipartCount.size() > 1) {
            logResultInReport(
                    Constants.PASS
                            + ": Multipart question is of summative type",
                    "Verify multipart question assesment type ",
                    this.reportTestObj);
            return true;

        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": Multipart question is not of summative type",
                    "Verify multipart question assesment type ",
                    this.reportTestObj);
            return false;

        }

    }

    /**
     * @author mohit.gupta5
     * @date 14 Nov,2017
     * @description Verify text message
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        this.APP_LOGS.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element,
                text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author sumit.bhardwaj
     * @description: returnTypeOfQuestion returns type of methods rendered on UI
     * @return: Type of question on UI
     */
    public String returnTypeOfQuestion() {
        // Getting parent element of each activity type
        String element = "DiagnosticQuestionType";
        // Getting 'id' of each activity type using its parent element
        String value = getAttribute(element, "id");
        List<WebElement> numberOfQuestions = findElement
                .findListAndHandleStaleElementException(element);
        try {
            if (!value.equals(null)) {
                if (numberOfQuestions.size() > 1) {
                    return ResourceConfigurations.getProperty("multipart");
                } else if (value.contains("FIB")) {
                    List<WebElement> el = findElement
                            .findListAndHandleStaleElementException(
                                    "FIBFreeResponse");// FIBFreeResponse
                    if (el != null) {
                        if (el.size() != 0) {
                            return ResourceConfigurations
                                    .getProperty("fibFreeResponse");
                        }
                    }
                    List<WebElement> e = findElement
                            .findListAndHandleStaleElementException(
                                    "FIBDropDown");
                    if (e.size() != 0) {
                        return ResourceConfigurations
                                .getProperty("fibDropDown");
                    }
                }
                // Checking for individual MCQSA question
                else if (value.contains("McqSa")
                        || "McqSa_undefined".equalsIgnoreCase(value)) {
                    APP_LOG.info("Attempted McqMa type question");
                    return ResourceConfigurations.getProperty("mcqSa");
                }
                // Checking for individual MCQMA question
                else if (value.contains("McqMa")
                        || "McqMa_undefined".equalsIgnoreCase(value)) {
                    APP_LOG.info("Attempted McqMa type question");
                    return ResourceConfigurations.getProperty("mcqMa");
                }
            } else {
                return Constants.FAIL
                        + ": No desired question avialable on page ";
            }
        } catch (Exception e) {

            return Constants.FAIL
                    + ": Exception accured while checking type of question:- "
                    + e.getMessage();
        }
        return null;

    }

    /**
     * @author sumit.bhardwaj
     * @description attemptUserChoiceQuestion method will attempt question and
     *              according to attempt type will click on
     *              Submit/iDon'tKnowThis button
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     * @param attemptType
     *            --> SubmitButton/DiagnosticIdontKnowThisButton
     */
    public void attemptUserChoiceQuestion(String activityType,
            String attemptType) {
        APP_LOGS.info(
                "Attempting question type specified by user : " + activityType
                        + " using attempt type : " + attemptType + "...");
        try {
            if (activityType.contains("Multipart")) {
                attemptAllQuestionsOnMultiPart();
                logResultInReport(
                        Constants.PASS
                                + ": Attempted Multipart type question using attempt type : "
                                + attemptType,
                        "Verify that user has attempted Multipart type question and Submitted the answer",
                        reportTestObj);
            }

            else if (activityType.contains("FIB")) {
                List<WebElement> el = findElement
                        .findListAndHandleStaleElementException(
                                "FIBFreeResponse");// FIBFreeResponse
                if (el != null) {
                    if (el.size() != 0) {
                        List<WebElement> list = findElement
                                .findListAndHandleStaleElementException(
                                        "FIBFreeResponse");// DiagnosticDisabledFibBox
                        for (WebElement ele : list) {
                            if ("Safari".equalsIgnoreCase(capBrowserName)) {
                                String clas = ele.getAttribute("class");
                                WebDriver webDriver1 = returnDriver();
                                JavascriptExecutor js1 = (JavascriptExecutor) webDriver1;
                                js1.executeScript("return window.jQuery(\"."
                                        + clas
                                        + "\").val(\"12345678A123456\")");
                                ele.sendKeys(Keys.BACK_SPACE);
                            } else {
                                Actions actions = new Actions(returnDriver());
                                actions.moveToElement(ele);
                                actions.click();
                                actions.sendKeys("Some Name");
                                actions.build().perform();
                            }
                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript("arguments[0].click();",
                                        returnDriver()
                                                .findElement(By.cssSelector(
                                                        ".mathpallet-keyboard .mathpallate-close")));
                                APP_LOG.info("Closed Math Pallate");
                            } catch (Exception e) {
                                APP_LOG.error(
                                        "Error while closing Math Pallate "
                                                + e);
                            }
                        }
                        if (attemptType.equalsIgnoreCase(
                                "DiagnosticIdontKnowThisButton")) {

                            clickOnElementAndHandleStaleException(
                                    "DiagnosticFIBFreeResponseIDontKnowButton",
                                    "Click on " + attemptType + " button");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Attempted FIB_FreeResponse type question using attempt type : "
                                            + attemptType,
                                    "Verify that user has attempted FIB_FreeResponse type question using 'IdontKnowThis' as attempt type ",
                                    this.reportTestObj);
                        } else if (attemptType
                                .equalsIgnoreCase("SubmitButton")) {
                            clickOnElementAndHandleStaleException(attemptType,
                                    "Click on " + attemptType + " button");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Attempted FIB_FreeResponse type question using attempt type : "
                                            + attemptType,
                                    "Verify that user has attempted FIB_FreeResponse type question and Submitted the answer",
                                    this.reportTestObj);
                        } else {
                            APP_LOG.info(activityType
                                    + " is not attempted as per user choice");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Attempted FIB_FreeResponse type question but didn't submitted",
                                    "Verify that user has attempted FIB_FreeResponse type question but didn't submitted it",
                                    this.reportTestObj);
                        }
                    }
                    return;
                }
                List<WebElement> e = findElement
                        .findListAndHandleStaleElementException("FIBDropDown");
                if (e.size() != 0) {
                    List<WebElement> fibDropdowns = findElement
                            .findListAndHandleStaleElementException(
                                    "DiagnosticDropdownMenu");
                    for (int dropdownCounter = 0; dropdownCounter < fibDropdowns
                            .size(); dropdownCounter++) {

                        // Click on the Dropdown to open it
                        fibDropdowns.get(dropdownCounter).click();
                        // Click the first option in the dropdown menu
                        clickOnElementAndHandleStaleException(
                                "DiagnosticDropdownOptionOne",
                                "Select first option from the dropdown.");
                    }
                    if (attemptType.equalsIgnoreCase(
                            "DiagnosticIdontKnowThisButton")) {
                        clickOnElementAndHandleStaleException(
                                "DiagnosticFIBDropdownIDontKnowButton",
                                "Click on " + attemptType + " button");
                        logResultInReport(
                                Constants.PASS
                                        + ": Attempted FIB_DropDown type question using attempt type : "
                                        + attemptType,
                                "Verify that user has attempted FIB_DropDown type question using 'IdontKnowThis' as attempt type ",
                                this.reportTestObj);
                    } else if (attemptType.equalsIgnoreCase("SubmitButton")) {
                        clickOnElementAndHandleStaleException(attemptType,
                                "Click on " + attemptType + " button");
                        logResultInReport(
                                Constants.PASS
                                        + ": Attempted FIB_DropDown type question using attempt type : "
                                        + attemptType,
                                "Verify that user has attempted FIB_DropDown type question and Submitted the answer",
                                this.reportTestObj);
                    } else {
                        APP_LOG.info(activityType
                                + " is not attempted as per user choice");
                        logResultInReport(
                                Constants.PASS
                                        + ": Attempted FIB_DropDown type question but didn't submitted",
                                "Verify that user has attempted FIB_DropDown type question but didn't submitted it",
                                this.reportTestObj);
                    }
                    APP_LOG.info("Attempted FIB_dropdown type question");
                    return;
                }
            }
            // Checking for individual MCQSA question
            else if (activityType.contains("McqSa")) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                    scrollWebPage(0, 400);
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on FirstRadio button: "
                                    + e,
                            "Click on FirstRadio button", this.reportTestObj);
                    return;
                }
                if (attemptType
                        .equalsIgnoreCase("DiagnosticIdontKnowThisButton")) {
                    clickOnElementAndHandleStaleException(
                            "DiagnosticMCQSAIDontKnowButton",
                            "Click on " + attemptType + " button");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqSa type question using attempt type : "
                                    + attemptType,
                            "Verify that user has attempted McqSa type question using 'IdontKnowThis' as attempt type ",
                            this.reportTestObj);

                } else if (attemptType.equalsIgnoreCase("SubmitButton")) {
                    clickOnElementAndHandleStaleException(attemptType,
                            "Click on " + attemptType + " button");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqSa type question using attempt type : "
                                    + attemptType,
                            "Verify that user has attempted McqSa type question and Submitted the answer",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(activityType
                            + " is not attempted as per user choice");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqSa type question but didn't submitted",
                            "Verify that user has attempted McqSa type question but didn't submitted it",
                            this.reportTestObj);
                }
                APP_LOG.info("Attempted McqSa type question");
            }
            // Checking for individual MCQMA question
            else if (activityType.contains("McqMa")) {
                try {
                    WebDriver webDriver = returnDriver();
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript(
                            "return document.querySelector(\"label>input[type='checkbox']\").click();");
                    scrollWebPage(0, 400);
                } catch (Exception e) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Errorwhile clicking on Firstcheckbox button: "
                                    + e,
                            "Click on FirstCheckBox button",
                            this.reportTestObj);
                    return;
                }
                if (attemptType
                        .equalsIgnoreCase("DiagnosticIdontKnowThisButton")) {
                    clickOnElementAndHandleStaleException(
                            "DiagnosticMCQMAIDontKnowButton",
                            "Click on " + attemptType + " button");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqMa type question using attempt type : "
                                    + attemptType,
                            "Verify that user has attempted McqMa type question using 'IdontKnowThis' as attempt type ",
                            this.reportTestObj);
                } else if (attemptType.equalsIgnoreCase("SubmitButton")) {
                    clickOnElementAndHandleStaleException(attemptType,
                            "Click on " + attemptType + " button");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqMa type question using attempt type : "
                                    + attemptType,
                            "Verify that user has attempted McqMa type question and Submitted the answer",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(activityType
                            + " is not attempted as per user choice");
                    logResultInReport(
                            Constants.PASS
                                    + ": Attempted McqMa type question but didn't submitted",
                            "Verify that user has attempted McqMa type question but didn't submitted it",
                            this.reportTestObj);
                }
                APP_LOG.info("Attempted McqMa type question");
            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting " + activityType
                    + " of question because of:- " + e.getMessage());
            logResultInReport(
                    Constants.FAIL + ": Exception while attempting the '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has attempted McqMa type question but didn't submitted it",
                    this.reportTestObj);
        }
    }

    /**
     * @author sumit.bhardwaj
     * @description Navigate a Learner to a specified Activity type in
     *              Diagnostic Test after attempting a question using submit
     *              button
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToActivityTypeAfterSubmit(String activityType) {
        // String currentTypeOfQuestion = returnTypeOfQuestion();
        try {
            int counter = progressStartIndex;
            while (!((counter < progressMaxIndex)
                    && activityType.equals(returnTypeOfQuestion()))) {
                String currentTypeOfQuestion = returnTypeOfQuestion();
                String fib = "";
                String fibType = "";
                if (currentTypeOfQuestion.contains("FIB")) {
                    fib = currentTypeOfQuestion.split("_")[0];
                    fibType = currentTypeOfQuestion.split("_")[1];
                }
                if (currentTypeOfQuestion.contains("FIB")) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is FIB type");
                    if (verifyElementPresentWithOutLog("DiagnosticFibDropDown")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticFibDropDown", "class")
                                    .contains(fibType.toLowerCase())
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                        APP_LOGS.debug(
                                "Attempting the question by clicking Submit button");
                        attemptUserChoiceQuestion(currentTypeOfQuestion,
                                "SubmitButton");
                        APP_LOGS.debug("Clicked Submit button");
                        counter++;
                        continue;
                    } else if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponse")
                                    .contains(Constants.PASS)
                            && getAttribute("DiagnosticFibFreeResponse",
                                    "class").toLowerCase()
                                            .contains(fibType.toLowerCase())
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        APP_LOGS.debug(
                                "Attempting the question by clicking Submit button");
                        attemptUserChoiceQuestion(currentTypeOfQuestion,
                                "SubmitButton");
                        APP_LOGS.debug("Clicked Submit button");
                        counter++;
                    }
                }

                else if (currentTypeOfQuestion.equalsIgnoreCase("Multipart")) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMultipart", "id")
                                    .toLowerCase()
                                    .contains(currentTypeOfQuestion
                                            .toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        APP_LOGS.debug(
                                "Attempting the question by clicking Submit button");
                        attemptAllQuestionsOnMultiPart();
                        counter++;
                        continue;
                    }
                } else if (currentTypeOfQuestion.contains("Mcq")) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQ type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMCQSA", "id")
                                    .toLowerCase().contains(
                                            currentTypeOfQuestion.toLowerCase())
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        APP_LOGS.debug(
                                "Attempting the question by clicking Submit button");
                        attemptUserChoiceQuestion(currentTypeOfQuestion,
                                "SubmitButton");
                        APP_LOGS.debug("Clicked Submit button");
                        counter++;
                        continue;
                    } else if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMCQMA", "id")
                                    .toLowerCase().contains(
                                            currentTypeOfQuestion.toLowerCase())
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        APP_LOGS.debug(
                                "Attempting the question by clicking Submit button");
                        attemptUserChoiceQuestion(currentTypeOfQuestion,
                                "SubmitButton");
                        APP_LOGS.debug("Clicked Submit button");
                        counter++;
                        continue;
                    }
                } else if (currentTypeOfQuestion.contains("multipart")) {

                }
            }
            if (counter < progressMaxIndex) {
                logResultInReport(
                        Constants.PASS
                                + ": Attempted previous questions and Successfully Navigated to '"
                                + activityType + "' type question",
                        "Verify that user has navigated to '" + activityType
                                + "' type question.",
                        this.reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": '" + activityType
                                + "' type question is not available",
                        "Verify that user has navigated to '" + activityType
                                + "' type question.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOGS.error(
                    "Error while navigating to user question after submiting because of:- "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL + ": Could not navigate to '" + activityType
                            + "' type question because : " + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author Lekh.bahl
     * @date 8 Dec,2017
     * @description Verify text in the list
     */
    public String verifyTextInList(String element, String text,
            String stepDesc) {
        this.APP_LOGS.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction
                .execute(ACTION_VERIFY_EQUALS_TEXT_IN_LIST, element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author sumit.bhardwaj
     * @description: getSelectedOptionsCount method calculate the size of given
     *               element using javaScript
     * @param mcqType
     *            : Multiple question type choice
     * @return: The size of elements on webPage
     */
    public Object getSelectedOptionsCount(String mcqType) {
        Object count = null;
        String ma = "[id^='McqMa_'] input:checked";
        String sa = "[id^='McqSa_'] input:checked";
        try {
            if (mcqType.equalsIgnoreCase("mcqma")) {
                WebDriver webDriver = returnDriver();
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                count = js.executeScript("return document.querySelectorAll(\""
                        + ma + "\").length;");
                APP_LOGS.debug("Current size of " + mcqType + " on page is:- "
                        + count.toString());
            } else if (mcqType.equalsIgnoreCase("mcqsa")) {
                WebDriver webDriver = returnDriver();
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                count = js.executeScript("return document.querySelectorAll(\""
                        + sa + "\").length;");
                APP_LOGS.debug("Current size of " + mcqType + " on page is:- "
                        + count.toString());
            }
        } catch (Exception e) {
            APP_LOGS.error("Error while finding  " + mcqType + " on page is:- "
                    + count.toString());
        }
        return count;

    }

    /**
     * @author lekh.bhal
     * @description: verifyInputIsclear method verify the check-box/radio button
     *               is cleared or not
     * @param questionType
     *            : Multiple question type choice
     */
    public void verifyInputIsclear(String questionType) {
        Object count = getSelectedOptionsCount(questionType);
        if (count.toString().equals("0")) {
            logResultInReport(
                    Constants.PASS
                            + ": Input get clear on clicking the I don't know this button",
                    "Verify input get clear on clicking the I don't know this button ",
                    this.reportTestObj);

        } else {
            logResultInReport(
                    Constants.FAIL
                            + ":: Input is not getting cleared on clicking the I don't know this button",
                    "Verify input get clear on clicking the I don't know this button",
                    this.reportTestObj);
        }
    }

    /**
     * @author mukul.sehra
     * @date 13 Dec,2017
     * @description Navigate a New Learner to a Multipart question containing
     *              specified Activity type in Diagnostic Test
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/McqSa/McqMa
     */
    public void
           navigateToMultipartWithSpecificActivityType(String activityType) {
        APP_LOGS.debug("Navigating to Multipart question containing '"
                + activityType + "' type question in it...");
        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                        .contains(Constants.PASS)) {
                    if (activityType.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("fibDropDown"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is Multipart with fibDropDown type question");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticFibDropDown")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'multiPart' type question with FIB_DropDown type question in it");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to multipart question containing '"
                                            + activityType + "' type question",
                                    "Verify that user has navigated to Multipart question containing '"
                                            + activityType
                                            + "' type question in it.",
                                    this.reportTestObj);
                            break;
                        }
                    } else if (activityType
                            .equalsIgnoreCase(ResourceConfigurations
                                    .getProperty("fibFreeResponse"))) {

                        if (verifyElementPresentWithOutLog(
                                "DiagnosticFibFreeResponse")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'multiPart' type question with FIB_FreeResponse type question in it");
                            logResultInReport(Constants.PASS
                                    + ": Skipped previous questions and Successfully Navigated to multipart question containing '"
                                    + activityType + "' type question in it",
                                    "Verify that user has navigated to Multipart question containing '"
                                            + activityType
                                            + "' type question in it.",
                                    this.reportTestObj);
                            break;

                        }
                    } else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("mcqSa"))) {
                        if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'multiPart' type question with MCQSA type question in it");
                            logResultInReport(Constants.PASS
                                    + ": Skipped previous questions and Successfully Navigated to multipart question containing '"
                                    + activityType + "' type question in it",
                                    "Verify that user has navigated to Multipart question containing '"
                                            + activityType
                                            + "' type question in it.",
                                    this.reportTestObj);
                            break;
                        }
                    } else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("mcqMa"))) {
                        if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'multiPart' type question with MCQMA type question in it");
                            logResultInReport(Constants.PASS
                                    + ": Skipped previous questions and Successfully Navigated to multipart question containing '"
                                    + activityType + "' type question in it",
                                    "Verify that user has navigated to Multipart question containing '"
                                            + activityType
                                            + "' type question in it.",
                                    this.reportTestObj);
                            break;
                        }
                    }
                }
                APP_LOGS.debug(
                        "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                iDontKnowThisButtons = findElement
                        .getElements("DiagnosticIdontKnowThisButton");
                for (WebElement button : iDontKnowThisButtons) {
                    x.scrollIntoView("DiagnosticIdontKnowThisButton");
                    button.click();
                    APP_LOGS.debug("'I don't know this' button is clicked");
                }
                iDontKnowThisButtons.clear();
                if (i == (progressMaxIndex - 1)) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type question is not available in the Multipart question",
                            "Verify that user has navigated to Multipart question containing '"
                                    + activityType + "' type question in it.",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified multipart question type because : "
                            + e);
            logResultInReport(Constants.FAIL
                    + ": Exception while navigating to Multipart question containing '"
                    + activityType + "' type question : " + e.getMessage(),
                    "Verify that user has navigated to Multipart question containing '"
                            + activityType + "' type question in it.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 30 Nov,2017
     * @description Get Multi Select Answers and Sequence
     * @return The HashMap having Answer and their sequence.
     */

    public HashMap<Integer, String> getMutliSelectAnwerAndSequence() {
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        List<WebElement> list = findElement.getElements("MultiSelectAnswer");
        try {

            APP_LOGS.debug("Get Multi Select Answers and Sequence");

            for (int i = 0; i < list.size(); ++i) {
                String checkbox = list.get(i).getText();
                System.out.println(checkbox);
                hm.put(i, checkbox);
            }

            for (Map.Entry m : hm.entrySet()) {
                System.out.println(m.getKey() + " " + m.getValue());
            }
        } catch (Exception e) {
            APP_LOG.debug("Exception in getting Multi Select Answers");
        }
        return hm;
    }

    /**
     * @author yogesh.choudhary
     * @date 30 Nov,2017
     * @description Compare Multi Select Answers and Sequence
     * @return The HashMap having Answer and their sequence.
     */

    public GLPLearner_DiagnosticTestPage compareMutliSelectAnwerAndSequence() {
        HashMap<Integer, String> hm1 = new HashMap<Integer, String>();
        List<WebElement> list = findElement.getElements("MultiSelectAnswer");
        try {
            APP_LOGS.debug("Get Multi Select Answers and Sequence");

            for (int i = 0; i < list.size(); ++i) {
                String checkbox = list.get(i).getText();
                System.out.println(checkbox);
                hm1.put(i, checkbox);
            }

            for (Map.Entry m : hm1.entrySet()) {
                System.out.println(m.getKey() + " " + m.getValue());
            }

            if (hm1.equals(getMutliSelectAnwerAndSequence())) {
                APP_LOGS.debug(
                        "Verified --> MCQMA answer shuffling done properly.");
                result = Constants.PASS;
                logResultInReport(this.result,
                        "Verify answer shuffling is working.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL;
                logResultInReport(this.result,
                        "Verify Answer shuffling is working",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in Compare MultiSelect Answer shuffling");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 30 Nov,2017
     * @description Wait and click on Continue button
     * @return
     */
    public GLPLearner_DiagnosticTestPage waitAndClickOnContinueButton() {
        try {
            APP_LOGS.debug(
                    "Wait and Click on Continue button appears on session time out");

            for (int i = 1; i <= 30; i++) {
                if (verifyElementPresent("TimeoutContinueButton",
                        "Verify Continue button") == null) {
                    Thread.sleep(60000);
                } else {
                    clickOnElement("TimeoutContinueButton",
                            "Click on Continue Button");
                    logResultInReport(this.result,
                            " Verify Continue button should display after timeout.",
                            this.reportTestObj);
                    break;
                }
            }

        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception in Wait and Click on Continue button appears on session time out");
            logResultInReport(this.result,
                    " Verify Continue button should display after timeout.",
                    this.reportTestObj);
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);

    }

    /**
     * @author yogesh.choudhary
     * @date 30 Nov,2017
     * @description Wait and click on Continue button
     * @return
     */

    public GLPLearner_DiagnosticTestPage
           attempDiagnoisticTestTillMultiSelectQuestion(String userName,
                   String distractor) {

        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        List<WebElement> iDontKnowThisButtons = new ArrayList<>();
        CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);
        try {
            GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj,
                    APP_LOG);
            APP_LOGS.debug(
                    "Attempt Diagonatic Test till Multi select question");
            for (int i = 1; i <= 20; i++) {

                if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                        .contains(Constants.PASS)) {

                    if (objRestUtil.getDistractor(userName,
                            distractor) == distractor) {

                        result = Constants.PASS;
                        logResultInReport(this.result,
                                "Verify Multi Select question with desired Distractor displayed.",
                                this.reportTestObj);
                        break;

                    } else {

                        APP_LOGS.debug(
                                "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                        iDontKnowThisButtons = findElement
                                .getElements("DiagnosticIdontKnowThisButton");
                        for (WebElement button : iDontKnowThisButtons) {
                            x.scrollIntoView("DiagnosticIdontKnowThisButton");
                            button.click();
                            APP_LOGS.debug(
                                    "'I don't know this' button is clicked");
                        }
                    }

                } else {

                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                }
            }

            logResultInReport(this.result,
                    "Multi Select question with desired Distractor not displayed.",
                    this.reportTestObj);

        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception in getting the question of desired distractor"
                            + distractor);
            logResultInReport(this.result,
                    "Multi Select question with desired Distractor not displayed.",
                    this.reportTestObj);
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 21 Dec,2017
     * @description to verify progress bar is empty when user has visited first
     *              time
     * @return
     */
    public GLPLearner_DiagnosticTestPage
           verifyProgressBarEmptyForNotAttemptedCourse() {

        try {
            FindElement findElement = new FindElement();
            String progressBarValue = findElement
                    .getElement("DiagnosticProgressBar")
                    .getAttribute("aria-valuenow");
            if (progressBarValue.contains("0")) {
                logResultInReport(
                        Constants.PASS
                                + ": Progress bar is blank with UI value '"
                                + progressBarValue
                                + "' when user has come to diagnostic first time.",
                        "Verify Progress bar is blank when user has come to diagnostic first time.",
                        this.reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": Progress bar is not blank with UI value '"
                                + progressBarValue
                                + "' when user has come to diagnostic first time.",
                        "Verify Progress bar is blank when user has come to diagnostic first time.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.debug("Exception in finding progress bar element");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date 20 Dec,2017
     * @description verify Text Comparison
     */

    public void compareText(String text1, String text2) {

        try {
            if (text1.equals(text2)) {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is same as Expected Text : " + text2;
                logResultInReport(this.result,
                        "Verify that Actual text is equal to Expected text",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is not same as Expected Text :" + text2;
                logResultInReport(this.result,
                        "Verify that Actual text is same as Expected text",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

    }

    /**
     * @author pallavi.tyagi
     * @date 20 Dec,2017
     * @description verify Text Comparison
     */

    public void compareBarLength(String text1, String text2) {

        if (text1.equals(text2)) {
            this.result = Constants.PASS + ": Actual Text : " + text1
                    + " is same as Expected Text : " + text2;
            logResultInReport(this.result,
                    "Verify learner resume to diagnostic test where he/she left off.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Actual Text : " + text1
                    + " is not same as Expected Text :" + text2;
            logResultInReport(this.result,
                    "Verify learner is not resume to diagnostic test where he/she left off.",
                    this.reportTestObj);

        }

    }

    /**
     * @author nitish.jaiswal
     * @date 21 Dec,2017
     * @description to get width of progress bar
     * @return
     */
    public String getCurrentWidthOfProgressBar() {

        String progressBarWidthValue = "";
        try {
            FindElement findElement = new FindElement();
            progressBarWidthValue = findElement
                    .getElement("DiagnosticProgressBar").getAttribute("style");
            logResultInReport(
                    Constants.PASS + ": Progress bar current width is '"
                            + progressBarWidthValue + "'.",
                    "Get current width of the progress bar.",
                    this.reportTestObj);
        } catch (Exception e) {
            APP_LOG.debug("Exception in finding progress bar width");
        }
        return progressBarWidthValue;

    }

    /**
     * @author nitish.jaiswal
     * @date 21 Dec,2017
     * @description to verify forward direction of progress bar
     * @return
     */
    public GLPLearner_DiagnosticTestPage verifyProgressBarForwardDirection(
            String progressBarWidthValueBefore,
            String progressBarWidthValueAfter) {
        if (!(progressBarWidthValueBefore.equals(progressBarWidthValueAfter))) {
            this.result = Constants.PASS
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is larger to previous value : "
                    + progressBarWidthValueBefore;
            logResultInReport(this.result,
                    "Verify that progress bar is moving in forward direction.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is not larger to previous value :"
                    + progressBarWidthValueBefore;
            logResultInReport(this.result,
                    "Verify that progress bar is moving in forward direction.",
                    this.reportTestObj);

        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 18 Dec,2017
     * @description Verify Multi Select question is displaying in Card View
     * @return object GLPLearner_DiagnosticTestPage
     */
    public void verifyCardView() {
        APP_LOGS.debug("Verify question is displaying in Card View");
        List<WebElement> cardViewBlock = new ArrayList<>();
        cardViewBlock = findElement.getElements("CardViewBlock");

        try {

            /* Verify Card View block count */
            if (cardViewBlock.size() == 2) {
                result = Constants.PASS;
                logResultInReport(this.result,
                        "Question displayed in Card View.", this.reportTestObj);
            } else {
                result = Constants.FAIL;
                logResultInReport(this.result,
                        "Question is not displaying in Card View.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Questions are not displaying in Card View");
        }

    }

    /**
     * @author anuj.tiwari
     * @date 22 Dec,2017
     * @description Get the integer values from text
     * 
     */
    public List<String> getIntegerValuesFromText(String text) {

        List numberList = new ArrayList<>();
        try {

            String pat = "\\d";

            Pattern regex = Pattern.compile(pat);

            Matcher matcherString = regex.matcher(text);
            while (matcherString.find()) {
                numberList.add(matcherString.group());
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Unable to get the Integer values form given Text beacuse of error "
                            + e);
        }
        return numberList;

    }

    /**
     * @author anuj.tiwari1
     * @date 22 Dec, 2017
     * @description To verify that the radio buttons/checkboxes are not selected
     */
    public void verifyIsNotSelected(String elementlocator, String stepDesc) {
        result = performAction.execute(ACTION_VERIFY_CHECKBOX_IS_NOT_SELECTED,
                elementlocator);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author anuj.tiwari1
     * @date 22 Dec,2017
     * @description verify Text Comparison
     */

    public Boolean compareList(List list1, List list2) {

        Boolean flag = null;
        try {

            if (list1.equals(list2)) {
                flag = true;
            }

            else {
                flag = false;
            }

        } catch (Exception e) {
            APP_LOG.error("Error while comparing the lists : " + e);

        }

        return flag;

    }

    /**
     * @author rashmi.z
     * @date 05 Jan,2018
     * @description Verify question is displaying in Card View
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage
           verifyQuestionAnswerInCardView(int index) {
        APP_LOGS.debug("Verify MCQ-SA rendering description on card one");

        try {
            String cardViewQuestionBlock = null;
            List<WebElement> cardViewBlock = new ArrayList<>();
            cardViewBlock = findElement.getElements("CardViewBlock");

            cardViewQuestionBlock = cardViewBlock.get(index).getText();
            System.out.println(cardViewQuestionBlock);

            /* Verify Card View Question in Card 1 */
            if (!cardViewQuestionBlock.equals(null)) {
                result = Constants.PASS;
                logResultInReport(this.result,
                        "Question/Answer is available in card view",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL;
                logResultInReport(this.result,
                        "Question/Answer is not available in card view",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Question is not displaying in Card View one");
        }

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 05 Jan,2018
     * @description Verify Resume Diagnostic test (continue) button working
     *              properly
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage verifyResumeDiagnosticTest() {
        APP_LOGS.debug(
                "Verify Resume Diagnostic test (continue) button working properly");
        try {
            // Verify mcqSa type question
            verifyQuestionAnswerInCardView(0);

            // Click on cross icon on diagnostic page
            clickOnCrossButton("DiagnosticCrossIconButton",
                    "Click on cross icon to exit diagnostic.");

            // Click on Leave button on poup
            clickOnElement("DiagnosticPopUpLeaveButton",
                    "Click on leave button of diagnostic popup");

            // Click on 'Continue' Button
            clickOnElement("DiagnosticContinueBtn", "Click on continue button");
        } catch (Exception e) {
            APP_LOG.error("Question is not displaying in Card View1");
        }

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 15 Jan,2018
     * @description Verify user is completing diagnostic test
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_CourseMaterialPage attemptDiagnosticTest() {
        APP_LOGS.debug("Verify user is completing diagnostic test");
        try {
            for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                String typeOfQuestion = returnTypeOfQuestion();
                if (!(typeOfQuestion.contains(Constants.FAIL))) {
                    attemptUserChoiceQuestion(typeOfQuestion,
                            ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"));
                } else {
                    logResultInReport(
                            Constants.FAIL
                                    + ": User not able to complete diagnostic test",
                            "Verify user has completed diagnostic test",
                            reportTestObj);
                    APP_LOG.debug("User could not complete diagnostic test");
                    break;
                }
            }
            GLPLearner_CourseMaterialPage objProductApplicationCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            this.result = objProductApplicationCourseMaterialPage
                    .verifyElementPresent("CourseMaterialResult",
                            "Verify Diagnostic test completed and Pearson logo is visible");
            if (this.result.contains(Constants.PASS)) {
                logResultInReport(
                        this.result
                                + ": User has Successfully navigated to course material page after completing diagnostic test ",
                        "Verify user has completed diagnostic test",
                        reportTestObj);
                APP_LOG.debug("User has Successfully skipped diagnostic test");
            } else {
                logResultInReport(
                        this.result
                                + ": User not able to complete diagnostic test",
                        "Verify user has completed diagnostic test",
                        reportTestObj);
                APP_LOG.debug("User could not complete diagnostic test");
            }

        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 12 Jan,2018
     * @description Verify drop-down options on Dynamic (Richview) UI
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage
           verifyDropDownOptions(String dropDownList) {
        APP_LOGS.debug("Verify drop-down options on Dynamic (Richview) UI");
        try {

            List<WebElement> dropDownOptions = new ArrayList<>();
            dropDownOptions = findElement.getElements(dropDownList);

            for (WebElement we : dropDownOptions) {
                if (!we.getText().equals(null)) {
                    result = Constants.PASS;
                    logResultInReport(this.result,
                            "All the possible option are displayed in the dropdown",
                            this.reportTestObj);
                } else {
                    result = Constants.FAIL;
                    logResultInReport(this.result,
                            "All the possible option are not displayed in the dropdown",
                            this.reportTestObj);
                }
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "All the possible option are not displayed in the dropdown");
        }

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);

    }

    /**
     * @author rashmi.z
     * @date 12 Jan,2018
     * @description Verify selected answer option should be displayed in the FIB
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage verifyDropDownOptionsSelection(
            String dropDownFIB, String dropDownList) {
        APP_LOGS.debug(
                "Verify drop-down values selection on Dynamic (Richview) UI");
        try {

            clickOnElement(dropDownFIB,
                    "Click on drop down FIB on Dynamic (Richview) page");
            List<WebElement> dropDownOptions = new ArrayList<>();
            dropDownOptions = findElement.getElements(dropDownList);
            for (int i = 2; i < dropDownOptions.size(); i++) {
                String dropDownValues = dropDownOptions.get(i).getText();
                if (!dropDownValues.equals(null)) {
                    dropDownOptions.get(i).click();
                    verifyTextContains("DropDownListValue", dropDownValues,
                            "Selected value is matched.");
                    result = Constants.PASS;
                    logResultInReport(this.result,
                            "Selected value is matched in the dropdown",
                            this.reportTestObj);
                } else {
                    result = Constants.FAIL;
                    logResultInReport(this.result,
                            "Selected value is not matched in the dropdown",
                            this.reportTestObj);
                }
            }

        } catch (Exception e) {
            APP_LOG.error("Selected value is not matched in the dropdown");
        }

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);

    }

    /**
     * @author mohit.gupta5
     * @date 16 Jan,2018
     * @description Handling Cross button on Safari
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void clickOnCrossButton(String locator, String message) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        WebDriver webDriver = returnDriver();
        if (webDriver instanceof SafariDriver
                || capBrowserName.equalsIgnoreCase("Safari")) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#closeIconId")));
            logResultInReport(
                    Constants.PASS
                            + ": Clicked on Cross Icon on Assessment player",
                    message, this.reportTestObj);
        } else {
            clickOnElement(locator, message);
        }
    }

    /**
     * @author mohit.gupta5
     * @date 18 Apr,2018
     * @description Verify free response text box functionalty
     */
    public void verifyTextBoxSizeVary() {
        Dimension width = returnDriver()
                .findElement(By.cssSelector(".mq-editable-field")).getSize();
        int bWidth = width.getWidth();
        // System.out.println("**Before width:** " + bWidth);

        String beforeWidth = Integer.toString(bWidth);

        /*
         * if (bWidth > 0) { logResultInReport(beforeWidth,
         * "Get default value for text box.", this.reportTestObj); } else {
         * result = Constants.PASS + ": Width of text box is " + beforeWidth +
         * "."; logResultInReport(this.result,
         * "Get default value for text box.", this.reportTestObj); }
         */
        moveToElementAndClick("FIBFreeResponse");
        enterInputDataViaActions("FIBFreeResponse",
                ResourceConfigurations.getProperty("longFreeResponseDigit"),
                "Enter long text in the FibResonse field");

        width = returnDriver().findElement(By.cssSelector(".mq-editable-field"))
                .getSize();
        int aWidth = width.getWidth();
        System.out.println("**After width:**  " + aWidth);

        String afterWidth = Integer.toString(aWidth);

        if (bWidth < aWidth) {
            this.result = Constants.PASS + ": Text box width increases from "
                    + bWidth + " to " + aWidth + ".";
            logResultInReport(this.result, "Verify if text box size increases.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Text box width is not increased.";
            logResultInReport(this.result, "Verify if text box size increases.",
                    this.reportTestObj);
        }

        Actions actions = new Actions(returnDriver());
        actions.click();
        actions.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        actions.build().perform();

        width = returnDriver().findElement(By.cssSelector(".mq-editable-field"))
                .getSize();
        int newWidth = width.getWidth();
        System.out.println("**After width:**  " + newWidth);

        String newWidthAfterClear = Integer.toString(newWidth);

        if (newWidth == bWidth) {
            this.result = Constants.PASS
                    + ": Text box width goes to initial value i.e.'"
                    + newWidthAfterClear + "' and shrinks.";
            logResultInReport(this.result,
                    "Verify if text box size remains shrinks after deleting values in it.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Text box width does not shrink";
            logResultInReport(this.result,
                    "Verify if text box size remains shrinks after deleting values in it.",
                    this.reportTestObj);
        }

    }

    /**
     * @author mukul.sehra
     * @date 10 April ,17
     * @description Verify "Search" is displayed as the placeholder.
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
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Enter input value in textbox
     * @return The object of GLPInstructor_ConsoleLoginPage
     */

    public GLPLearner_DiagnosticTestPage enterInputData(String locator,
            String value, String message) {
        value = "some name";
        APP_LOG.debug("Enter the input value- " + value);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                locator, value);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 07 Dec,2017
     * @description Navigate a New Learner to a specified Activity type in
     *              Diagnostic Test with Algorithmic Expression
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToActivityTypeWithAlgorithmicExpression(
            String activityType) {

        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {
                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + activityType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");

                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibFreeResponseTable activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponseTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibDropDown activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("fibDropDown"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown' type");
                    if (verifyElementPresentWithOutLog("DiagnosticFibDropDown")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibFreeResponse activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponse"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpressionFIBFreeResponse")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of multipart activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("multipart"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMultipart", "id")
                                    .toLowerCase()
                                    .contains(activityType.toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticMathExpression")
                                        .contains(Constants.PASS)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of Math expression"
                                            + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with Algorithamic Value",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 24 Jan 2018
     * @description Get FIB free response math expression and compare
     * @return The HashMap having Answer and their sequence.
     */

    public String getMathExpression(String element) {
        List<WebElement> list = findElement.getElements(element);
        String mathExp = null;
        try {
            APP_LOGS.debug("Get Question Alogorithmic Text");

            for (int i = 0; i < list.size(); ++i) {
                mathExp = list.get(i).getText();
                System.out.println(mathExp);

            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in getting Multi Select Answers");
        }
        return mathExp;
    }

    /**
     * @author yogesh.choudhary
     * @date 25 Jan,2017
     * @description Compare Compare Randomized Equation Text
     * @return
     */

    public GLPLearner_DiagnosticTestPage
           compareRandomizedText(String beforeText, String afterText) {

        try {
            APP_LOGS.debug("Compare Randomized Equation Text");

            if (beforeText.equals(afterText)) {
                APP_LOGS.debug(
                        "Algorithmic values are not properly after Refresh/Exit/Logout.");
                result = Constants.FAIL;
                logResultInReport(this.result,
                        "Verify Answer shuffling is working",
                        this.reportTestObj);

            } else {
                result = Constants.PASS;
                APP_LOGS.debug(
                        "Algorithmic values are changing properly after Refresh/Exit/Logout.");
                logResultInReport(this.result,
                        "Verified-- Algorithmic values are chaging properly after Refresh/Exit/Logout.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception in Comparing Randomaized Alogorithmic values");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 23 Jan,2018
     * @description Navigate a New Learner to a specified Figure type in
     *              Diagnostic Test
     * @param figureType
     * 
     */

    public void navigateToFigureType(String figureType) {
        APP_LOGS.debug("Navigating to '" + figureType
                + "' type question in Diagnostic test...");
        // Click on the I don't know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + figureType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + figureType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verification of figureTypeRectangle activity type
                if (figureType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("figureTypeRectangle"))) {
                    APP_LOGS.debug(
                            "Verified --> Figure type requested is  figureType Rectangle");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticfigureTypeRectangle")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of figureType Rectangle");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + figureType + "' type question",
                                "Verify that user has navigated to '"
                                        + figureType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of figureTypeOplus activity type
                else if (figureType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("figureTypeOplus"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is figure Type Oplus type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticfigureTypeOplus")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'figure Type Oplus");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + figureType + "' question",
                                "Verify that user has navigated to '"
                                        + figureType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }
                // Verification of figureType Elliptical activity type
                if (figureType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("figureTypeElliptical"))) {
                    APP_LOGS.debug(
                            "Verified --> Figure type requested is  figureType Elliptical");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticfigureTypeChip").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of figureType Elliptical");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + figureType + "' type question",
                                "Verify that user has navigated to '"
                                        + figureType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of figureTypeChip activity type
                else if (figureType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("figureTypeChip"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is figure Type Chip.");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticfigureTypeChip").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of  figureType Chip type");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + figureType + "' question",
                                "Verify that user has navigated to '"
                                        + figureType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified Figure type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + figureType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + figureType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author rashmi.z
     * @date 23 Jan,2018
     * @description Mouse Hover for Rectangle Figure on card one
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void verifyMousehourTextForRendringFigures() {
        // Actions actions = new Actions(returnDriver());
        // actions.moveToElement(findElement.getElement("DiagnosticfigureTypeRectangle")).build().perform();
        mouseHoverMoveToElement(
                findElement.getElement("DiagnosticfigureTypeRectangle"));
        // System.out.println(findElement.getElement("RectangleToolTip").getText());
        verifyElementPresent("RectangleToolTip",
                "Rectanle has tool tip on mouse hover.");

    }

    /**
     * @author rashmi.z
     * @date 25 Jan,2018
     * @description Getting height and width for Rectangle Figure on card one
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void verifyHeighttWidthForRendringFigures() {

        Dimension d = findElement.getElement("DiagnosticfigureTypeRectangle")
                .getSize();
        int RWidth = d.getWidth();
        int Rheight = d.getHeight();
        System.out.println(RWidth + "****" + Rheight);
        if (RWidth == new Integer(
                ResourceConfigurations.getProperty("RectangleWidth"))) {
            logResultInReport(
                    Constants.PASS
                            + ": Validating the width of Rectangle Figure with respect to TDX values",
                    "Width of Rectangle Figure with respect to TDX values",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": Validating the width of Rectangle Figure with respect to TDX values",
                    "Width of Rectangle Figure with respect to TDX values is not match.",
                    this.reportTestObj);
        }
        if (Rheight == new Integer(
                ResourceConfigurations.getProperty("RectangleHeight"))) {
            logResultInReport(
                    Constants.PASS
                            + ": Validating the height of Rectangle Figure with respect to TDX values",
                    "Height of Rectangle Figure with respect to TDX values is match",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": Validating the height of Rectangle Figure with respect to TDX values",
                    "Height of Rectangle Figure with respect to TDX values is not match",
                    this.reportTestObj);
        }

    }

    /**
     * @author rashmi.z
     * @date 23 Jan,2018
     * @description Right-click disable for Rectangle Figure on card one
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void verifyRightClickForRendringFigures() {
        JavascriptExecutor js = (JavascriptExecutor) returnDriver();
        String return_value = (String) js
                .executeScript("document.oncontextmenu");
        System.out.println("The right click is disabled." + return_value);
        if (return_value == null) {
            logResultInReport(
                    Constants.PASS
                            + ": The right click is disabled for Rectangle Figur in Card View One",
                    "The right click is disabled for Rectangle Figur in Card View One",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": The right click is enbaled for Rectangle Figur in Card View One",
                    "The right click is enbaled for Rectangle Figur in Card View One",
                    this.reportTestObj);
        }

    }

    /**
     * @author nisha.pathria
     * @date 25 Jan,2018
     * @description Verify free response text box functionalty
     */
    public String getElementWidth(String element) {

        String value = this.performAction.execute(ACTION_GET_WIDTH, element);
        return value;
    }

    /**
     * @author rashmi.z
     * @date 12 Feb,2018
     * @description Verify Dynamic Border Table
     */
    public void verifyDynamicBorderTable() {

        verifyElementAttributeValue("DyanamicBorderTable", "style",
                ResourceConfigurations.getProperty("dynamicBorderTable"),
                "Verify Dynamic Borderless table with equation");

    }

    /**
     * @author rashmi.z
     * @date 12 Feb,2018
     * @description Verify Dynamic Borderless Table
     */
    public void verifyDynamicBorderlessTable() {

        verifyElementAttributeValue("DyanamicBorderlessTable", "style",
                ResourceConfigurations.getProperty("staticBorderlessEquation"),
                "Verify Dynamic Borderless table with equation");

    }

    /**
     * @author rashmi.z
     * @date 12 Feb,2018
     * @description Verify Static Borderless Table Equation
     */
    public void verifyStaticBorderlessTableWithEquation() {

        verifyElementAttributeValue("StaticBorderlessTableEquation", "style",
                ResourceConfigurations.getProperty("staticBorderlessEquation"),
                "Verify Static Borderless table with equation");

    }

    /**
     * @author rashmi.z
     * @date 12 Feb,2018
     * @description Verify Static Borderless Table with Rectangle Figure
     */
    public void verifyStaticBorderlessRectangleFigure() {

        verifyElementAttributeValue("StaticBorderlessTableWithFigure", "style",
                ResourceConfigurations
                        .getProperty("staticBorderlessRectangleFigure"),
                "Verify Static Borderless table with Rectangle Figure");

    }

    /**
     * @author anuj.tiwari1
     * @date 26 Feb,2018
     * @description Enter values in an input box using Actions Class.
     */
    public void enterInputDataViaActions(String locator, String value,
            String message) {

        APP_LOG.debug("Enter the input value- " + value);

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLASS_TYPE, locator,
                value);

        if (this.result.contains(Constants.PASS)) {
            logResultInReport(
                    Constants.PASS
                            + " Value entered correctly in Free Response Box",
                    message, this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + " Value not entered correctly in Free Response Box",
                    message, this.reportTestObj);
        }

    }

    /**
     * @author anuj.tiwari1
     * @date 22 Feb,2018
     * @description Enter multiple values in input box and verify.
     */
    public void enterMultipleInputDataAndVerify(String locator, String message,
            String[] verificationLocator, String... value) {

        try {
            APP_LOG.debug("Enter the input value- " + value);
            int count = 0;
            for (String inputData : value) {
                FindElement ele = new FindElement();
                Actions actions = new Actions(returnDriver());
                actions.click(ele.getElement(locator));
                actions.sendKeys(inputData);
                actions.sendKeys(Keys.ENTER);
                actions.build().perform();
                verifyElementPresent(verificationLocator[count],
                        "Verfiying the Shortcut is present");
                actions.build().perform();
                actions.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                actions.sendKeys(Keys.DELETE);
                actions.build().perform();
                count++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Unexpected error occured while entering shortcuts in free response input box: "
                            + e.getMessage(),
                    "Verify shortcuts are entered successfully.",
                    this.reportTestObj);

        }

    }

    /**
     * @author rashmi.z
     * @date 21 Feb,2018
     * @description Getting height and width for Elliptical Figure on card one
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void verifyHeighttWidthForRendringEllipticalFigures() {

        Dimension d = findElement.getElement("DiagnosticfigureTypeChip")
                .getSize();
        int RWidth = d.getWidth();
        int Rheight = d.getHeight();
        System.out.println(RWidth + "****" + Rheight);
        if (RWidth == new Integer(
                ResourceConfigurations.getProperty("EllipticalFigureWidth"))) {
            logResultInReport(
                    Constants.PASS
                            + ": Validating the width of Elliptical Figure with respect to TDX values",
                    "Width of Elliptical Figure with respect to TDX values",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": Validating the width of Elliptical Figure with respect to TDX values",
                    "Width of Elliptical Figure with respect to TDX values is not match.",
                    this.reportTestObj);
        }
        if (Rheight == new Integer(
                ResourceConfigurations.getProperty("EllipticalFigureHeight"))) {
            logResultInReport(
                    Constants.PASS
                            + ": Validating the height of Elliptical Figure with respect to TDX values",
                    "Height of Elliptical Figure with respect to TDX values is match",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": Validating the height of Elliptical Figure with respect to TDX values",
                    "Height of Elliptical Figure with respect to TDX values is not match",
                    this.reportTestObj);
        }

    }

    /**
     * @author rashmi.z
     * @date 22 Feb,2018
     * @description Right-click disable for Elliptical Figure on card one
     * @return The object of GLPLearner_DiagnosticTestPage
     */
    public void verifyRightClickForEllipticalFigures() {
        try {
            FindElement ele = new FindElement();
            List<WebElement> element = ele.getElements(
                    "DiagnosticfigureTypeEllipticalPlusFigureOnAnswerCard");
            Actions action = new Actions(returnDriver());
            action.doubleClick(element.get(0));
            action.contextClick(element.get(0));
            action.build().perform();

            System.out.println("Sucessfully Right clicked on the element");
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is not attached to the page document "
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + element + " was not found in DOM "
                    + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Element " + element + " was not clickable "
                    + e.getStackTrace());
        }
        JavascriptExecutor js = (JavascriptExecutor) returnDriver();
        String return_value = (String) js
                .executeScript("document.oncontextmenu");
        System.out.println("The right click is disabled." + return_value);
        if (return_value == null) {
            logResultInReport(
                    Constants.PASS
                            + ": The right click is disabled for Elliptical Figur in Card View One",
                    "The right click is disabled for Elliptical Figur in Card View One",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": The right click is enbaled for Elliptical Figur in Card View One",
                    "The right click is enbaled for Elliptical Figur in Card View One",
                    this.reportTestObj);
        }

    }

    /**
     * @author rashmi.z
     * @date 23 Feb, 2018
     * @description To verify that the radio buttons/checkboxes are selected
     */
    public void verifyIsSelected(String elementlocator, String stepDesc) {
        // verifyElementPresent(elementlocator,
        // "Verfiying the Element present");
        // result = performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
        // elementlocator);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author yogesh.choudhary
     * @description: attemptAllDiagonasticQuestion method will attempt all types
     *               of question on Diagonatic
     */
    public void attemptAllQuestionsOnDiagonaticTest() {
        APP_LOGS.debug("Attem All Diagonatic Questions");
        // Click on the I don't know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + "Diagonastic Page not available",
                            "Verify that user has navigated to nextquestion.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verify Course Home Start Button
                if (verifyElementPresentWithOutLog(
                        "ResultDiagnosticStartButton")
                                .contains(Constants.PASS)) {
                    APP_LOGS.debug(
                            "Verified --> Presence of Course Home START button");
                    logResultInReport(
                            Constants.PASS + " Navigated to Course Home Page",
                            "Verify user should be navigated to Course Home Page",
                            this.reportTestObj);
                    break;
                } else {
                    continue;
                }

            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified Figure type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while navigating to next question "
                            + e.getMessage(),
                    "Verify that user has navigated to Course Home Page",
                    this.reportTestObj);

        }
    }

    public void setProgressIndexValue() {
        try {
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "DiagnosticSubmitButton");
            returnSetOfQuestion();
            progressStartIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemin"));
            progressMaxIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemax"));
        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Progress bar not visible - "
                            + e.getMessage(),
                    "Verify Progress bar visible", reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @description: returnSetOfQuestion returns types of questions rendered on
     *               UI
     * @return: Types of question on UI
     */
    public HashMap returnSetOfQuestion() {
        HashMap<String, List> availableQuestionTypes = returnSetOfQuestionsFound();
        try {

            try {
                if (returnDriver()
                        .findElement(
                                By.cssSelector(".diagnostic-result__banner"))
                        .isDisplayed()) {
                    return availableQuestionTypes;
                }
            } catch (Exception e) {
                APP_LOG.info("Test is not yet completed");
            }
            if (availableQuestionTypes.size() == 0) {
                availableQuestionTypes = returnSetOfQuestionsFound();
                if (availableQuestionTypes.size() == 0) {
                    APP_LOG.debug(
                            "Click the Pearson logo to navigate back to course tile to recover from error screen...");
                    if (performAction
                            .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                    "CourseTileStudent")
                            .contains(Constants.FAIL)) {
                        this.refreshPage();
                        this.performAction.execute(ACTION_CLICK, "PearsonLogo");
                    }
                    FindElement element = new FindElement();
                    element.checkPageIsReady();
                    GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                            reportTestObj, APP_LOG);
                    // Verify CourseTile Present and navigate to Welcome Learner
                    // Screen
                    if (performAction
                            .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                    "CourseTileStudent")
                            .contains(Constants.PASS)) {
                        objGLPLearnerCourseViewPage
                                .navigateToWelcomeScreenLearner();
                    }
                    // objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
                    if (performAction
                            .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                    "CourseHomeStartYourPathBtn")
                            .contains(Constants.PASS)) {
                        this.performAction.execute(ACTION_CLICK,
                                "CourseHomeStartYourPathBtn");
                        element.checkPageIsReady();
                        availableQuestionTypes = returnSetOfQuestionsFound();
                        if (availableQuestionTypes.size() == 0) {
                            APP_LOG.info(
                                    "No desired question avialable for diagnostic");
                            logResultInReport(
                                    Constants.FAIL
                                            + ": No desired question avialable on page",
                                    "Verify that user has completed diagnostic test.",
                                    this.reportTestObj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception accured while checking type of question:- "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception accured while checking type of question:- "
                            + e.getMessage(),
                    "Verify that user has completed diagnostic test.",
                    this.reportTestObj);
        }
        return availableQuestionTypes;
    }

    public HashMap returnSetOfQuestionsFound() {
        HashMap<String, List> availableQuestionTypes = new HashMap<String, List>();
        try {
            String getQuestionUrl = returnDriver().getCurrentUrl();
            List<String> OverallQuestionTypes = new ArrayList<String>();
            OverallQuestionTypes.add("fib-freeResponse");
            OverallQuestionTypes.add("fib-dropdown");
            OverallQuestionTypes.add("mcqma");
            OverallQuestionTypes.add("mcqsa");
            OverallQuestionTypes.add("numberline");
            for (int i = 0; i < OverallQuestionTypes.size(); i++) {
                if (OverallQuestionTypes.get(i).contains("fib-freeResponse")) {
                    List<WebElement> freeRepsonseQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    ".fib-form-group.fib-freeResponse:not([disabled])"));
                    if (freeRepsonseQuestions.size() > 0) {
                        availableQuestionTypes.put("FreeResponse",
                                freeRepsonseQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqsa")) {
                    List<WebElement> mcqSaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "input[id*='_0'][type='radio']"));
                    if (mcqSaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqSa", mcqSaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i)
                        .contains("fib-dropdown")) {
                    List<WebElement> fibDropdownQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "[id^=FIB] .fib-dropdown>div"));
                    if (fibDropdownQuestions.size() > 0) {
                        availableQuestionTypes.put("FIBDropDown",
                                fibDropdownQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqma")) {
                    List<WebElement> mcqMaQuestions = returnDriver()
                            .findElements(By.cssSelector("div[id^='McqMa']"));
                    if (mcqMaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqMa", mcqMaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("numberline")) {
                    List<WebElement> numberLineQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "[id*='numberline-cursor-num']"));
                    if (numberLineQuestions.size() > 0) {
                        availableQuestionTypes.put("NumberLine",
                                numberLineQuestions);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOGS.debug("Error while getting question type in diagnostic");
            logResultInReport(
                    Constants.FAIL
                            + ": Error while getting oquestion type in diagnostic: "
                            + e.getMessage(),
                    "Get the available diagnostic question type on page",
                    this.reportTestObj);
        }
        return availableQuestionTypes;
    }

    /**
     * @author nitish.jaiswal
     * @date 07 Mar,2018
     * @description Verify user is completing adaptive diagnostic test
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_CourseMaterialPage attemptAdaptiveDiagnosticTest(
            int diagnosticStartIteration, int diagnosticMaxIteration,
            String attemptType) {
        APP_LOGS.debug("Attempt diagnostic test");
        try {
            if (attemptType.contains("SubmitWithoutAttempt")) {
                skipDiagnosticTestQuestions(diagnosticStartIteration,
                        diagnosticMaxIteration, attemptType);
            } else {
                setProgressIndexValue();
                int diagnosticMaxValue = progressMaxIndex;
                progressStartIndex = diagnosticStartIteration;
                if (diagnosticMaxIteration > 0) {
                    progressMaxIndex = progressMaxIndex
                            - (progressMaxIndex - diagnosticMaxIteration);
                } else {
                    progressMaxIndex = progressMaxIndex + progressMaxIndex;
                }
                for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                    FindElement checkLoadState = new FindElement();
                    checkLoadState.checkPageIsReady();
                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestion();
                    try {
                        List<WebElement> resultPageElement1 = returnDriver()
                                .findElements(By.cssSelector(
                                        ".diagnostic-result__banner"));
                        List<WebElement> resultPageElement2 = returnDriver()
                                .findElements(By.cssSelector("#LOBtn_1"));

                        if (resultPageElement1.size() > 0
                                || resultPageElement2.size() > 0) {
                            break;
                        } else {

                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    attemptType);
                            FindElement ele = new FindElement();
                            ele.checkPageIsReadyWithLogs();
                        }
                        if (progressMaxIndex < diagnosticMaxValue
                                && diagnosticMaxIteration != 0) {
                            returnSetOfQuestion();
                        }
                    } catch (Exception e) {
                        attemptUIRenderedQuestion(availableQuestionTypes,
                                attemptType);
                    }

                }
            }

        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
        } finally {
            FindElement checkLoadState = new FindElement();
            checkLoadState.checkPageIsReady();
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @description attemptUIRenderedQuestionmethod will attempt question and
     *              according to attempt type will click on
     *              Submit/iDon'tKnowThis button
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     * @param attemptType
     *            --> SubmitButton/DiagnosticIdontKnowThisButton
     */
    public void attemptUIRenderedQuestion(HashMap activityType,
            String attemptType) {
        APP_LOGS.info(
                "Attempting question type specified by user : " + activityType
                        + " using attempt type : " + attemptType + "...");
        boolean bFreeResponse = false;
        boolean bFibDropDown = false;
        boolean bMcqMa = false;
        boolean bMcqSa = false;
        boolean bNumberLine = false;
        String questionDescription = "";
        try {

            for (int i = 0; i < activityType.size(); i++) {
                if ((activityType.containsKey("FreeResponse"))
                        && (bFreeResponse == false)) {
                    bFreeResponse = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("FreeResponse");
                    // list.get(0).
                    for (WebElement ele : list) {
                        if ("Safari".equalsIgnoreCase(capBrowserName)) {
                            List<WebElement> mathPallateElement = returnDriver()
                                    .findElements(By.cssSelector(
                                            ".mathpallet-keyboard.mq-mp"));
                            if (mathPallateElement.size() > 0) {
                                ele.sendKeys("Test Test");
                            } else {
                                ele.sendKeys(Keys.TAB);
                                mathPallateElement = returnDriver()
                                        .findElements(By.cssSelector(
                                                ".mathpallet-keyboard.mq-mp"));
                                if (mathPallateElement.size() > 0) {
                                    ele.sendKeys("Test Test");
                                }
                            }
                            // String clas = ele.getAttribute("class");
                            // WebDriver webDriver1 = returnDriver();
                            // JavascriptExecutor js1 = (JavascriptExecutor)
                            // webDriver1;
                            // js1.executeScript("return window.jQuery(\"." +
                            // clas
                            // + "\").val(\"12345678A123456\")");
                            // ele.sendKeys(Keys.BACK_SPACE);
                        } else {
                            Actions actions = new Actions(returnDriver());
                            actions.moveToElement(ele);
                            actions.click();
                            actions.sendKeys("Test Test");
                            actions.build().perform();
                        }
                        try {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript("arguments[0].click();",
                                    returnDriver().findElement(By.cssSelector(
                                            ".mathpalette-close")));
                            APP_LOG.info("Closed Math Pallate");
                        } catch (Exception e) {
                            APP_LOG.error(
                                    "Error while closing Math Pallate " + e);
                        }
                    }
                    APP_LOG.info("Attempted free response type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and Free Response";
                    } else {
                        questionDescription = "Free Response";
                    }
                }

                else if ((activityType.containsKey("FIBDropDown"))
                        && (bFibDropDown == false)) {
                    bFibDropDown = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("FIBDropDown");
                    List<WebElement> fibDropdowns = findElement
                            .findListAndHandleStaleElementException(
                                    "FIBDropDown");
                    // if (e.size() != 0) {DiagnosticDropdownMenu
                    // if (e.size() != 0) {
                    /*
                     * for (WebElement ele : list) { List<WebElement>
                     * fibDropdowns = findElement
                     * .findListAndHandleStaleElementException(
                     * "DiagnosticDropdownMenu");
                     */
                    for (int dropdownCounter = 0; dropdownCounter < fibDropdowns
                            .size(); dropdownCounter++) {

                        // Click on the Dropdown to open it
                        fibDropdowns.get(dropdownCounter).click();
                        // Click the first option in the dropdown menu
                        clickOnElementAndHandleStaleException(
                                "DiagnosticDropdownOptionOne",
                                "Select first option from the dropdown.");
                        clickOnElementAndHandleStaleException(
                                "DiagnosticDropdownOptionOne",
                                "Select first option from the dropdown.");
                        // Click the first option in the dropdown menu

                        // }
                    } /*
                       * for (WebElement ele : list) { List<WebElement>
                       * fibDropdowns = findElement
                       * .findListAndHandleStaleElementException(
                       * "DiagnosticDropdownMenu"); for (int dropdownCounter =
                       * 0; dropdownCounter < fibDropdowns .size();
                       * dropdownCounter++) {
                       * 
                       * // Click on the Dropdown to open it
                       * fibDropdowns.get(dropdownCounter).click(); // Click the
                       * first option in the dropdown menu
                       * clickOnElementAndHandleStaleException(
                       * "DiagnosticDropdownOptionOne",
                       * "Select first option from the dropdown."); } }
                       */
                    APP_LOG.info("Attempted FIB_dropdown type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and FIB Dropdown";
                    } else {
                        questionDescription = "FIB Dropdown";
                    }

                }

                else if ((activityType.containsKey("McqSa"))
                        && (bMcqSa == false)) {
                    bMcqSa = true;
                    if (!(getSelectedOptionsCount("mcqsa").toString()
                            .contains("1"))) {
                        List<WebElement> list = (List<WebElement>) activityType
                                .get("McqSa");
                        for (WebElement ele : list) {
                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript(
                                        "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                                scrollWebPage(0, 400);
                            } catch (Exception e) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": Errorwhile clicking on FirstRadio button: "
                                                + e,
                                        "Click on FirstRadio button",
                                        this.reportTestObj);
                                return;
                            }
                        }
                    }
                    APP_LOG.info("Attempted McqSa type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and McqSa";
                    } else {
                        questionDescription = "McqSa";
                    }
                }
                // Checking for individual MCQMA question
                else if ((activityType.containsKey("McqMa"))
                        && (bMcqMa == false)) {
                    bMcqMa = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("McqMa");
                    for (WebElement ele : list) {
                        try {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript(
                                    "return document.querySelector(\"label>input[type='checkbox']\").click();");
                            scrollWebPage(0, 400);
                        } catch (Exception e) {
                            logResultInReport(
                                    Constants.FAIL
                                            + ": Errorwhile clicking on Firstcheckbox button: "
                                            + e,
                                    "Click on FirstCheckBox button",
                                    this.reportTestObj);
                            return;
                        }
                        APP_LOG.info("Attempted McqMa type question");
                        if (questionDescription != "") {
                            questionDescription = questionDescription
                                    + " and McqMa";
                        } else {
                            questionDescription = "McqMa";
                        }
                    }
                }

                if ((activityType.containsKey("NumberLine"))
                        && (bNumberLine == false)) {
                    bNumberLine = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("NumberLine");
                    for (WebElement ele : list) {
                        try {

                            Actions actions = new Actions(returnDriver());
                            actions.moveToElement(ele);
                            actions.click();
                            actions.click();
                            actions.build().perform();

                        }

                        catch (Exception e) {
                            APP_LOG.error(
                                    "Error while clicking number line " + e);
                        }
                    }
                    APP_LOG.info("Attempted Number Line type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and Number Line";
                    } else {
                        questionDescription = "Number Line";
                    }
                }
            }
            // if
            // (attemptType.equalsIgnoreCase("DiagnosticIdontKnowThisButton")) {
            //
            // performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
            // "DiagnosticIdontKnowThisButton");
            // performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
            // "DiagnosticGotItButton");
            // clickOnElementAndHandleStaleException(
            // "DiagnosticIdontKnowThisButton",
            // "Click on " + attemptType + " button");
            // logResultInReport(
            // Constants.PASS + ": Attempted '" + questionDescription
            // + "' type question using attempt type : "
            // + attemptType,
            // "Verify that user has attempted '" + questionDescription
            // + "' type question using 'IdontKnowThis' as attempt type ",
            // this.reportTestObj);
            // } else
            if (attemptType.equalsIgnoreCase("SubmitButton")) {
                if (this.performAction
                        .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                attemptType)
                        .contains(Constants.FAIL)) {
                    logResultInReport(
                            Constants.FAIL + ": Submit button is not visible",
                            "Verify that user has attempted '"
                                    + questionDescription
                                    + "' type question and Submitted the answer",
                            this.reportTestObj);
                }
                FindElement ele = new FindElement();
                List<WebElement> iDontKnowButtons = ele
                        .getElements(attemptType);

                for (int j = 0; j < iDontKnowButtons.size(); j++) {

                    clickOnElementAndHandleStaleException(attemptType,
                            "Click on " + attemptType + " button");
                    Thread.sleep(2000);
                    try {
                        if (returnDriver()
                                .findElement(By.cssSelector(
                                        ".text-right>button,.o-coach-mark__got-it.pe-label"))
                                .isDisplayed()) {
                            performAction.execute(
                                    CLICK_AND_HANDLE_STALE_EXCEPTION,
                                    "DiagnosticGotItButton");
                            clickOnElementAndHandleStaleException(attemptType,
                                    "Click on " + attemptType + " button");
                        }
                    } catch (Exception e) {
                        APP_LOG.info("Got it burron already handled");
                    }

                }
                // clickOnElementAndHandleStaleException(attemptType,
                // "Click on " + attemptType + " button");
                logResultInReport(
                        Constants.PASS + ": Attempted '" + questionDescription
                                + "' type question using attempt type : "
                                + attemptType,
                        "Verify that user has attempted '" + questionDescription
                                + "' type question and Submitted the answer",
                        this.reportTestObj);
            } else {
                APP_LOG.info(
                        activityType + " is not attempted as per user choice");
                logResultInReport(
                        Constants.PASS + ": Attempted '" + questionDescription
                                + "' type question but didn't submitted",
                        "Verify that user has attempted '" + questionDescription
                                + "' type question but didn't submitted it",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occured while attempting diagnostic question:- "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occured while attempting diagnostic question:- "
                            + e.getMessage(),
                    "Verify that user has completed diagnostic test.",
                    this.reportTestObj);
        }
    }

    public void clickIdontKnowThisFirstQuestion() {
        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");
        clickOnElement("DiagnosticIdontKnowThisButton",
                "Click the IDontKnowThis button to move to the next question");
        verifyElementPresent("DiagnosticProgressBar",
                "Verify Progress bar is visible");
    }

    /**
     * @author nitish.jaiswal
     * @date 07 Mar,2018
     * @description Verify user is completing adaptive diagnostic test
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_CourseMaterialPage verifyDiagnosticTestCompleted() {
        APP_LOGS.debug("Verify user has completing diagnostic test");
        try {
            GLPLearner_CourseMaterialPage objProductApplicationCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                    reportTestObj, APP_LOG);
            this.result = objProductApplicationCourseMaterialPage
                    .verifyElementPresent("CourseMaterialResult",
                            "Verify Diagnostic test completed sucessfully");
            if (this.result.contains(Constants.PASS)) {
                logResultInReport(
                        this.result
                                + ": User has Successfully navigated to course material page after completing diagnostic test ",
                        "Verify user has completed diagnostic test",
                        reportTestObj);
                APP_LOG.debug("User has Successfully skipped diagnostic test");
            } else {
                logResultInReport(
                        this.result
                                + ": User not able to complete diagnostic test",
                        "Verify user has completed diagnostic test",
                        reportTestObj);
                APP_LOG.debug("User could not complete diagnostic test");
            }

        } catch (Exception e) {
            APP_LOG.error("Error while verifying diagnostic test");
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 12 Mar,2018
     * @description Compare Compare Randomized Equation Text
     * @return
     */

    public GLPLearner_DiagnosticTestPage compareQuestionId(String value1,
            String value2, String description) {

        try {
            APP_LOGS.debug("Compare Randomized Equation Text");

            if (value1.equals(value2)) {
                APP_LOGS.debug("Verify diffrent question is rendered on UI.");
                result = Constants.FAIL + ": New Question with Question ID '"
                        + value2
                        + "' is not diffrent from the previous question with Question ID '"
                        + value1 + "'.";
                logResultInReport(this.result, description, this.reportTestObj);

            } else {
                result = Constants.PASS + ": New Question with Question ID '"
                        + value2
                        + "' is diffrent from the previous question with Question ID '"
                        + value1 + "'.";
                logResultInReport(this.result, description, this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception in Comparing Randomaized Alogorithmic values");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 12 Mar,2018
     * @description Refresh page
     * @return
     */

    public void refreshPage() {

        try {
            APP_LOGS.debug("Refresh page");
            returnDriver().navigate().refresh();
            FindElement ele = new FindElement();
            ele.checkPageIsReady();

        } catch (Exception e) {
            APP_LOG.debug("Exception in refreshing page.");
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 9 March,2018
     * @description Navigate a New Learner to a specified Question type in
     *              Diagnostic Test linek Number Line
     * @param questionType
     * 
     */
    public void navigateToQuestionType(String questionType) {
        APP_LOGS.debug("Navigating to '" + questionType
                + "' type question in Diagnostic test...");
        // Click on the I don't know this button on the first question to allow
        // Got It button to appear

        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time"); // Click
        // overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + questionType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + questionType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verification of figureTypeRectangle activity type
                if (questionType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("questionTypeNumberLine"))) {
                    APP_LOGS.debug(
                            "Verified --> Question type requested is  Question Type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticNumberLineQuestion")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of Question Type");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + questionType + "' type question",
                                "Verify that user has navigated to '"
                                        + questionType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }
                if (questionType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("questionTypeGraph"))) {
                    APP_LOGS.debug(
                            "Verified --> Question type requested is  Question Type");
                    if (verifyElementPresentWithOutLog(
                            "XYGraphQuestionPopOutQuestion")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of Question Type");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + questionType + "' type question",
                                "Verify that user has navigated to '"
                                        + questionType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified Question Type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + questionType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + questionType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 18 Dec,2017
     * @description Verify Multi Select question is displaying in Card View
     * @return object GLPLearner_DiagnosticTestPage
     */
    public void verifyCardView(int viewcount) {
        APP_LOGS.debug("Verify question is displaying in Card View");
        List<WebElement> cardViewBlock = new ArrayList<>();
        cardViewBlock = findElement.getElements("CardViewBlock");

        try {

            /* Verify Card View block count */
            if (cardViewBlock.size() == viewcount) {
                result = Constants.PASS;
                logResultInReport(
                        this.result, "Verify Question is rendered in '"
                                + viewcount + "' Card View.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL;
                logResultInReport(
                        this.result, "Verify Question is rendered in '"
                                + viewcount + "' Card View.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Questions are not displaying in Card View");
        }
    }

    /**
     * @author anuj.tiwari1
     * @date 23 Mar,2018
     * @description Enter value in input box using Actions Class.
     */
    public void enterShortcutViaActionsClass(String locator, String inputData,
            String message) {

        APP_LOG.debug("Enter the input value- " + inputData);

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLASS_TYPE, locator,
                inputData);
        logResultInReport(this.result, message, this.reportTestObj);

        this.result = this.performAction.execute(ACTION_PRESS_ENTER_KEY,
                locator);
    }

    /*
     * @author nitish.jaiswal
     * 
     * @date 07 Mar,2018
     * 
     * @description Verify user is completing adaptive diagnostic test
     * 
     * @return object GLPLearner_DiagnosticTestPage
     */

    public int getTotalNoOfQuestions() {

        APP_LOG.debug("Get total no of question of practice test");
        int totalNoOfQuestion = 0;
        try {

            totalNoOfQuestion = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemax"));

        } catch (Exception e) {

        }
        return totalNoOfQuestion;
    }

    public GLPLearner_CourseMaterialPage attemptPracticeTest(
            int diagnosticStartIteration, int diagnosticMaxIteration,
            String attemptType) {
        APP_LOGS.debug("Attempt diagnostic test");
        try {
            // int diagnosticMaxValue = progressMaxIndex;
            if (attemptType.contains("DiagnosticIdontKnowThisButton")) {
                skipDiagnosticTestQuestions(diagnosticStartIteration,
                        diagnosticMaxIteration, attemptType);
            } else {

                setProgressIndexValue();
                this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                        "SubmitButton");
                progressStartIndex = Integer.parseInt(
                        getAttribute("DiagnosticProgressBar", "aria-valuemin"));
                progressMaxIndex = Integer.parseInt(
                        getAttribute("DiagnosticProgressBar", "aria-valuemax"));
                progressStartIndex = diagnosticStartIteration;
                if (diagnosticMaxIteration > 0) {
                    progressMaxIndex = progressMaxIndex
                            - (progressMaxIndex - diagnosticMaxIteration);
                }
                // for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                    FindElement checkLoadState = new FindElement();
                    checkLoadState.checkPageIsReady();
                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestionsFound();
                    try {
                        if ((availableQuestionTypes.size() == 0)
                                && (returnDriver()
                                        .findElement(By.cssSelector(
                                                ".diagnostic-result__banner"))
                                        .isDisplayed())) {
                            break;
                        } else if (availableQuestionTypes.size() == 0) {
                            logResultInReport(
                                    Constants.FAIL
                                            + ": No desired question avialable on page",
                                    "Verify that user has completed Practice test.",
                                    this.reportTestObj);
                        } else {
                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    attemptType);
                            verifyAndAttamptIfRetryRequired();
                        }
                    } catch (Exception e) {
                        attemptUIRenderedQuestion(availableQuestionTypes,
                                attemptType);
                    }
                }
            }

        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
        } finally {
            FindElement checkLoadState = new FindElement();
            checkLoadState.checkPageIsReady();
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    public void verifyAndAttamptIfRetryRequired() {
        if (verifyTextContains("SubmitButton", "Try Again",
                "Verify Text contains try again.").contains(Constants.PASS)) {
            this.performAction.execute(ACTION_CLICK, "SubmitButton");
            this.performAction.execute(ACTION_CLICK, "SubmitButton");
            if (verifyTextContains("SubmitButton", "Next Question",
                    "Verify Text contains try again.")
                            .contains(Constants.PASS)) {
                this.performAction.execute(ACTION_CLICK, "SubmitButton");
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": 'Next Question' Button is not visible after retrying twice.",
                        "Verify that user has completed Practice test.",
                        this.reportTestObj);
            }

        }

    }

    public int getDiagnosticQuestionsCount() {

        int totalQuestionCount = 0;
        try {

            totalQuestionCount = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemax"));
            return totalQuestionCount;

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
            logResultInReport(
                    Constants.FAIL + ": Progress bar not visible - "
                            + e.getMessage(),
                    "Verify Progress bar visible", reportTestObj);
        }
        return totalQuestionCount;
    }

    /**
     * @author ratnesh.singh
     * @param locator
     *            --> Locator of hidden Element on UI , attribute ---> Attribute
     *            for which we need value to be returned
     * @date 18 April,2018
     * @description Get attribute value of hidden input field
     * @return --> Attribute Value
     */
    public String getAttributeOfHiddenElement(String locator,
            String attribute) {
        APP_LOGS.debug("Get attribute value of hidden input element");
        this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE_BY_JS,
                locator, attribute);
        if (this.result.contains(Constants.FAIL)) {
            logResultInReport(this.result,
                    "Get attribute :" + attribute + " of element :" + locator,
                    this.reportTestObj);
            return null;
        } else {
            return this.result;
        }
    }

    /**
     * @author ratnesh.singh
     * @param locator
     *            -->Locator of Element on UI
     * @date 18 April,2018
     * @description Clear Free Response Text Box by Action
     * @return --> N/A
     */
    public void clearFreeResponseBoxByAction(String locator) {
        APP_LOGS.debug("Clear Free Response Box");
        this.result = this.performAction.execute(ACTION_CLEAR_VIA_ACTIONS,
                locator);
        if (this.result.contains(Constants.PASS)) {
            logResultInReport(this.result,
                    "Verify Element :" + locator + " gets cleared.",
                    this.reportTestObj);
        } else {
            logResultInReport(this.result,
                    "Verify Element :" + locator + " gets cleared.",
                    this.reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @param locator
     *            -->Locator of Element on UI
     * @date 18 April,2018
     * @description Move to Element and Click by Action
     * @return N/A
     */
    public void moveToElementAndClick(String locator) {
        this.APP_LOGS.debug("Move to element and click " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_MOVE_TO_ELEMENT,
                locator);

        if (this.result.contains(Constants.PASS)) {
            logResultInReport(
                    Constants.PASS + " Moved to Element " + locator
                            + "clicked by Action",
                    "Move to element and click by Action", this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL + " Moved to Element " + locator
                            + "clicked by Action",
                    "Move to element and click by Action", this.reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 3 May,2018
     * @description Verify specific question presence doing refresh
     * @return object GLPLearner_DiagnosticTestPage
     */
    public void navigateToSpecificQuestionType(String questionType) {

        APP_LOGS.debug("Look for " + questionType + " question");
        try {
            FindElement ele = new FindElement();
            ele.checkPageIsReady();

            if (questionType.equalsIgnoreCase("Multipart")) {
                searchGivenQuestionType(".diagnosticTest #multiPartAssessment",
                        questionType);
            } else if (questionType.equalsIgnoreCase("Hybrid")) {
                searchGivenQuestionType("", questionType);
            } else if (questionType.equalsIgnoreCase("FIB_FreeResponse")) {
                searchGivenQuestionType(
                        ".fib-form-group.fib-freeResponse:not([disabled])",
                        questionType);
            } else if (questionType.equalsIgnoreCase("FIB_DropDown")) {
                searchGivenQuestionType("[id^=FIB] .fib-dropdown>div>button",
                        questionType);
            } else if (questionType.equalsIgnoreCase("NumberLine")) {
                searchGivenQuestionType("[id*='numberline-cursor-num']",
                        questionType);
            } else if (questionType.equalsIgnoreCase("McqMa")) {
                searchGivenQuestionType("div[id^='McqMa']", questionType);
            } else if (questionType.equalsIgnoreCase("McqSa")) {
                searchGivenQuestionType("input[id*='_0'][type='radio']",
                        questionType);
            } else if (questionType.equalsIgnoreCase("FIB_DropDown_Table")) {
                searchGivenQuestionType("[id^=FIB] .fib-dropdown>div>button",
                        questionType);
            } else if (questionType
                    .equalsIgnoreCase("hybridMcqSaFreeResponse")) {
                searchGivenQuestionType(
                        ".fib-form-group.fib-freeResponse:not([disabled])",
                        questionType);
            } else if (questionType.equalsIgnoreCase("ThreeCardNumberLine")) {
                searchGivenQuestionType(".circle-button.popout button",
                        questionType);
            } else if (questionType.equalsIgnoreCase("MultipartFreeResponse")) {
                searchGivenQuestionType(
                        ".fib-form-group.fib-freeResponse:not([disabled])",
                        questionType);
            } else if (questionType.equalsIgnoreCase("FigureTypeElliptical")) {
                searchGivenQuestionType("", questionType);
            } else if (questionType.equalsIgnoreCase("XYGraph")) {
                searchGivenQuestionType(
                        "#Assessment > div:nth-child(1) div.circle-button.zoomin button",
                        questionType);
            } else if (questionType.equalsIgnoreCase("XYGraphAnswerCard")) {
                searchGivenQuestionType("", questionType);
            } else if (questionType.equalsIgnoreCase("XYGraphFIB")) {
                searchGivenQuestionType(
                        ".fib-form-group.fib-freeResponse:not([disabled])",
                        questionType);
            } else if (questionType.equalsIgnoreCase("figureTypeRectangle")) {
                searchGivenQuestionType(
                        ".fib-form-group.fib-freeResponse:not([disabled])",
                        questionType);
            }

        } catch (Exception e) {
            APP_LOG.error("Error while finding " + questionType + " question");
        } finally {
            FindElement ele = new FindElement();
            ele.checkPageIsReady();
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 4 May,2018
     * @description look for given question type
     * @return object GLPLearner_DiagnosticTestPage
     */
    public void searchGivenQuestionType(String locator, String questionType) {

        APP_LOGS.debug("Wait for " + questionType + " question");
        try {
            if (questionType.equalsIgnoreCase("Hybrid")) {
                for (int i = 0; i <= 100; i++) {
                    HashMap<String, List> isHybridFound = returnSetOfQuestionsFound();
                    List<WebElement> getMultipartQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    ".diagnosticTest #multiPartAssessment"));
                    if (isHybridFound.size() > 1
                            && getMultipartQuestions.size() == 0) {
                        APP_LOGS.debug(questionType + " question is found.");
                        result = Constants.PASS
                                + ": User is successfully navigated to "
                                + questionType + " question.";
                        logResultInReport(result,
                                "Navigate to " + questionType + " question.",
                                this.reportTestObj);
                        break;
                    } else {
                        refreshPage();
                        returnSetOfQuestion();
                        if (i == 100) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            } else if (questionType.equalsIgnoreCase("MultiPart")) {
                for (int i = 0; i <= 100; i++) {
                    List<WebElement> getQuestions = returnDriver()
                            .findElements(By.cssSelector(locator));
                    if (getQuestions.size() > 0) {
                        APP_LOGS.debug(questionType + " question is found.");
                        result = Constants.PASS
                                + ": User is successfully navigated to "
                                + questionType + " question.";
                        logResultInReport(result,
                                "Navigate to " + questionType + " question.",
                                this.reportTestObj);
                        break;
                    } else {
                        refreshPage();
                        returnSetOfQuestion();
                        if (i == 100) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            } else if (questionType.equalsIgnoreCase("FIB_DropDown_Table")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1201020",
                        questionType);

            } else if (questionType
                    .equalsIgnoreCase("HybridMcqSaFreeResponse")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1302032",
                        questionType);

            } else if (questionType.equalsIgnoreCase("FIB_DropDown")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1601021",
                        questionType);

            } else if (questionType.equalsIgnoreCase("McqMa")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1601020",
                        questionType);

            } else if (questionType.equalsIgnoreCase("FigureTypeElliptical")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1302032",
                        questionType);

            } else if (questionType.equalsIgnoreCase("MultipartFreeResponse")) {
                for (int i = 0; i <= 100; i++) {
                    HashMap<String, List> isMultiTypeQuestionsFound = returnSetOfQuestionsFound();
                    List<WebElement> mulipartQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    ".diagnosticTest #multiPartAssessment"));
                    List<WebElement> freeResponseQuestions = returnDriver()
                            .findElements(By.cssSelector(locator));
                    if (mulipartQuestions.size() > 0
                            && freeResponseQuestions.size() > 0
                            && isMultiTypeQuestionsFound.size() > 1) {
                        APP_LOGS.debug(questionType + " question is found.");
                        result = Constants.PASS
                                + ": User is successfully navigated to "
                                + questionType + " question.";
                        logResultInReport(result,
                                "Navigate to " + questionType + " question.",
                                this.reportTestObj);
                        break;
                    } else {
                        refreshPage();
                        returnSetOfQuestion();
                        if (i == 100) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            } else if (questionType.equalsIgnoreCase("ThreeCardNumberLine")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1202004",
                        questionType);

            } else if (questionType.equalsIgnoreCase("XYGraph")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1301001",
                        questionType);

            } else if (questionType.equalsIgnoreCase("XYGraphAnswerCard")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1803001",
                        questionType);

            } else if (questionType.equalsIgnoreCase("XYGraphFIB")) {
                navigateToQuestionWithQuestionId(locator, "GSQ-I1302005-6",
                        questionType);

            } else if (questionType.equalsIgnoreCase("figureTypeRectangle")) {
                navigateToQuestionWithQuestionId(locator, "Q-I1601032",
                        questionType);

            } else {
                for (int i = 0; i <= 100; i++) {
                    List<WebElement> getQuestions = returnDriver()
                            .findElements(By.cssSelector(locator));
                    List<WebElement> getMultipart = returnDriver()
                            .findElements(By.cssSelector(
                                    ".diagnosticTest #multiPartAssessment"));
                    HashMap<String, List> checkHybridNotFound = returnSetOfQuestionsFound();
                    if (getQuestions.size() == 1
                            && checkHybridNotFound.size() == 1
                            && getMultipart.size() == 0) {
                        APP_LOGS.debug(questionType + " question is found.");
                        result = Constants.PASS
                                + ": User is successfully navigated to "
                                + questionType + " question.";
                        logResultInReport(result,
                                "Navigate to " + questionType + " question.",
                                this.reportTestObj);
                        break;
                    } else {
                        refreshPage();
                        returnSetOfQuestion();
                        if (i == 100) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            }

        } catch (

        Exception e) {
            APP_LOG.error("Error while finding locator for " + questionType
                    + " question");
        }
    }

    /**
     * @author rashmi.z
     * @date 22 Apr,2018
     * @description Verify selected answer from answer option window in FIB
     *              dropdown box
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_DiagnosticTestPage

           validateSingleMultipleFIBFunctionality(String fibDropDownCount) {

        APP_LOGS.debug("Look for Single FIB type question");
        try {
            attemptCurrentUIQuestionsWithoutSubmit();
            verifyElementPresent("DiagnosticDropdownOptionOne",
                    "Verify Option window is not collapsed after selecting fib fropdown answer.");
            List<WebElement> fibDropdowns = findElement
                    .findListAndHandleStaleElementException("FIBDropDown");
            if (fibDropDownCount.equals("SingleFIBDropdown")) {
                fibDropdowns.get(0).click();
            } else {
                fibDropdowns.get(0).click();
                fibDropdowns.get(0).click();
            }
            verifyElementNotPresent("DiagnosticDropdownOptionOne",
                    "Verify Option window is not open after clicking FIB dropdwon again to change answer.");
            fibDropdowns.get(0).click();
            clickOnElementAndHandleStaleException("DiagnosticDropdownOptionTwo",
                    "Select first option from the dropdown.");
            clickOnElementAndHandleStaleException("DiagnosticDropdownOptionTwo",
                    "Select first option from the dropdown.");
            clickOnElement(
                    ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"),
                    "CLick on Submit to move to next question.");
        } catch (Exception e) {
            APP_LOGS.info("Unexpected error occoured: " + e.getMessage());
        }

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 07 Dec,2017
     * @description Navigate a New Learner to a specified Activity type in
     *              Diagnostic Test with XY Graph
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa/
     *            Collection
     */

    public void navigateToActivityTypeWithXYGraph(String activityType) {

        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {
                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + activityType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");

                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibFreeResponseTable activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponseTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibDropDown activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("fibDropDown"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown' type");
                    if (verifyElementPresentWithOutLog("DiagnosticFibDropDown")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibFreeResponse activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponse"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        if (verifyElementPresentWithOutLog("FIBFreeResponse")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of multipart activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("multipart"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMultipart", "id")
                                    .toLowerCase()
                                    .contains(activityType.toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        if (verifyElementPresentWithOutLog("")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author mohit.gupta5
     * @date 09 May,2018
     * @description Navigate a New Learner to a specified Activity type in
     *              Diagnostic Test
     * @param activityType
     *            ->
     *            FIB_DropDown/FIB_DropDown_Table/FIB_FreeReponse/Multipart/McqSa
     *            /McqMa/questionTypeNumberLine/questionTypeGraph
     */

    public void navigateToQuestionTypeOnPracticeTest(String activityType,
            String attemptType) {
        APP_LOGS.debug("Navigating to '" + activityType
                + "' type question in Diagnostic Test...");
        try {
            int progressStartIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemin"));
            int progressMaxIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemax"));
            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {
                if (i == progressMaxIndex) {
                    APP_LOG.info(
                            activityType + " type question is not available");
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");

                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestion1();

                    attemptUIRenderedQuestion(availableQuestionTypes,
                            attemptType);
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    List<WebElement> listMultipleDropDown = returnDriver()
                            .findElements(By.cssSelector(
                                    ".diagnosticTest .table-responsive .fib-dropdown"));
                    System.out.println(listMultipleDropDown.size());
                    if (listMultipleDropDown.size() > 1) {
                        if (verifyElementPresentWithOutLog(
                                "DiagnosticFibDropDownTable")
                                        .contains(Constants.PASS)
                                && verifyElementPresentWithOutLog(
                                        "DiagnosticMultipart")
                                                .contains(Constants.FAIL)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'fibDropDown' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to Multiple '"
                                            + activityType + "' type question",
                                    "Verify that user has navigated to Multiple '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }
                // Verification of fibFreeResponseTable activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponseTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");

                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of fibDropDown activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("fibDropDown"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown' type");

                    List<WebElement> listSingleDropDown = returnDriver()
                            .findElements(By.cssSelector(
                                    ".diagnosticTest .fib-dropdown"));
                    System.out.println(listSingleDropDown.size() - 1);
                    if (listSingleDropDown.size() - 1 == 1) {

                        if (verifyElementPresentWithOutLog(
                                "DiagnosticFibDropDown")
                                        .contains(Constants.PASS)
                                && verifyElementPresentWithOutLog(
                                        "DiagnosticMultipart")
                                                .contains(Constants.FAIL)) {
                            APP_LOGS.debug(
                                    "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType + "' type question",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of fibFreeResponse activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponse"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse' type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of multipart activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("multipart"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("DiagnosticMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("DiagnosticMultipart", "id")
                                    .toLowerCase()
                                    .contains(activityType.toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' type question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }
                // Verification of figureTypeRectangle activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("questionTypeNumberLine"))) {
                    APP_LOGS.debug(
                            "Verified --> Question type requested is  Question Type");
                    if (verifyElementPresentWithOutLog(
                            "DiagnosticNumberLineQuestion")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of Question Type");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("questionTypeGraph"))) {
                    APP_LOGS.debug(
                            "Verified --> Question type requested is  Question Type");
                    if (verifyElementPresentWithOutLog(
                            "XYGraphQuestionPopOutQuestion")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of Question Type");
                        logResultInReport(
                                Constants.PASS
                                        + ": Skipped previous questions and Successfully Navigated to '"
                                        + activityType + "' type question",
                                "Verify that user has navigated to '"
                                        + activityType + "' question.",
                                this.reportTestObj);
                        break;
                    } else {
                        continue;
                    }
                }

            }
        } catch (

        Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    public HashMap returnSetOfQuestion1() {
        HashMap<String, List> availableQuestionTypes = new HashMap<String, List>();
        try {
            String getQuestionUrl = returnDriver().getCurrentUrl();
            List<String> OverallQuestionTypes = new ArrayList<String>();
            OverallQuestionTypes.add("fib-freeResponse");
            OverallQuestionTypes.add("fib-dropdown");
            OverallQuestionTypes.add("mcqma");
            OverallQuestionTypes.add("mcqsa");
            OverallQuestionTypes.add("numberline");
            for (int i = 0; i < OverallQuestionTypes.size(); i++) {
                if (OverallQuestionTypes.get(i).contains("fib-freeResponse")) {
                    List<WebElement> freeRepsonseQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    ".fib-form-group.fib-freeResponse:not([disabled])"));
                    if (freeRepsonseQuestions.size() > 0) {
                        availableQuestionTypes.put("FreeResponse",
                                freeRepsonseQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqsa")) {
                    List<WebElement> mcqSaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "input[id*='_0'][type='radio']"));
                    if (mcqSaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqSa", mcqSaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i)
                        .contains("fib-dropdown")) {
                    List<WebElement> fibDropdownQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "[id^=FIB] .fib-dropdown button"));
                    if (fibDropdownQuestions.size() > 0) {
                        availableQuestionTypes.put("FIBDropDown",
                                fibDropdownQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqma")) {
                    List<WebElement> mcqMaQuestions = returnDriver()
                            .findElements(By.cssSelector("div[id^='McqMa']"));
                    if (mcqMaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqMa", mcqMaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("numberline")) {
                    List<WebElement> numberLineQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "[id*='numberline-cursor-num']"));
                    if (numberLineQuestions.size() > 0) {
                        availableQuestionTypes.put("NumberLine",
                                numberLineQuestions);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Done");
        }
        return availableQuestionTypes;
    }

    /**
     * @author nitish.jaiswal
     * @date 10 May,2017
     * @description verify Text Comparison
     */

    public void compareProgressBarLength(String text1, String text2) {

        try {

            text1 = text1.split("width:")[1];
            text1 = text1.split("%")[0].trim();
            text2 = text2.split("width:")[1];
            text2 = text2.split("%")[0].trim();
            if (Integer.parseInt(text1) < Integer.parseInt(text2)) {
                this.result = Constants.PASS + ": Progress bar value : '"
                        + text1
                        + "' before attemting questions is smaller than after attemting diagnostic question which is : '"
                        + text2 + "'";
                logResultInReport(this.result,
                        "Verify that progress bar value increases after attemting questions.",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Progress bar value : '"
                        + text1
                        + "' before attemting questions is not smaller than after attemting diagnostic question which is : '"
                        + text2 + "'";
                logResultInReport(this.result,
                        "Verify that progress bar value increases after attemting questions.",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing var length "
                    + e.getMessage());
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 17 Apr,2018
     * @description Verify less than and greater than symbol from math pallet
     */
    public void verifyFreeResponseGreaterLessThanSymbol() {
        try {
            if (performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                    "DiagnosticGotItButton").contains(Constants.PASS)) {
                performAction.execute(ACTION_CLICK, "DiagnosticGotItButton");
                FindElement ele = new FindElement();
                ele.checkPageIsReady();
            }
            clickOnElement("FreeResponseText",
                    "Click on free response box to get math pallate.");
            verifyElementPresent("LessThan",
                    "Verify Less than symbol is present in math pallate.");
            verifyElementPresent("GreaterThan",
                    "Verify Greater than symbol is present in math pallate.");
            clickOnElement("LessThan",
                    "Click on less than option in math pallate.");
            clickOnElement("GreaterThan",
                    "Click on greater than symbol in math pallate.");
            verifyTextContains("FreeResponseText", "≤",
                    "verify Less than is entered in free response box successfully.");
            verifyTextContains("FreeResponseText", "≥",
                    "verify Greater than is entered in free response box successfully.");
        } catch (Exception e) {
            APP_LOG.info("Unexpected erro occured: " + e.getMessage());
        }
    }

    /**
     * @author lekh.bahl
     * @date 31 Mar,2018
     * @description Verify progress bar remains same if learner exit and again
     *              go to same question
     * @return Object of GLPLearner_DiagnosticTestPage
     */
    public void verifyProgressBarNoIncrease(String progressBarWidthValueBefore,
            String progressBarWidthValueAfter, String message) {
        if ((progressBarWidthValueBefore.equals(progressBarWidthValueAfter))) {
            this.result = Constants.PASS
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is same to previous value : "
                    + progressBarWidthValueBefore;
            logResultInReport(this.result, message, this.reportTestObj);
        } else {
            this.result = Constants.FAIL
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is not same to previous value :"
                    + progressBarWidthValueBefore;
            logResultInReport(this.result, message, this.reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @description attemptCurrentUIQuestionsWithoutSubmit will find and attempt
     *              question without submit.
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */
    public void attemptCurrentUIQuestionsWithoutSubmit() {

        try {
            HashMap<String, List> availableQuestionTypes = returnSetOfQuestion();
            if (availableQuestionTypes.size() == 0) {
                this.result = Constants.FAIL
                        + "Something went wrong to find any question on UI.";
                logResultInReport(this.result,
                        "Attempt the displayed question without bhitting Submit.",
                        this.reportTestObj);
            } else {

                attemptUIRenderedQuestionWithoutSubmit(availableQuestionTypes);
                FindElement ele = new FindElement();
                ele.checkPageIsReadyWithLogs();
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occured while attempting/finding diagnostic question:- "
                            + e.getMessage());
        }
    }

    /**
     * @author nitish.jaiswal
     * @description attemptUIRenderedQuestionWithoutSubmit will attempt question
     *              without submit.
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */
    public void attemptUIRenderedQuestionWithoutSubmit(HashMap activityType) {
        APP_LOGS.info("Attempting question type specified by user");
        boolean bFreeResponse = false;
        boolean bFibDropDown = false;
        boolean bMcqMa = false;
        boolean bMcqSa = false;
        boolean bNumberLine = false;
        String questionDescription = "";
        try {
            for (int i = 0; i < activityType.size(); i++) {
                if ((activityType.containsKey("FreeResponse"))
                        && (bFreeResponse == false)) {
                    bFreeResponse = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("FreeResponse");
                    for (WebElement ele : list) {
                        if ("Safari".equalsIgnoreCase(capBrowserName)) {
                            String clas = ele.getAttribute("class");
                            WebDriver webDriver1 = returnDriver();
                            JavascriptExecutor js1 = (JavascriptExecutor) webDriver1;
                            js1.executeScript("return window.jQuery(\"." + clas
                                    + "\").val(\"12345678A123456\")");
                            ele.sendKeys(Keys.BACK_SPACE);
                        } else {
                            Actions actions = new Actions(returnDriver());
                            actions.moveToElement(ele);
                            actions.click();
                            actions.sendKeys("Some Name");
                            actions.build().perform();
                        }
                        try {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript("arguments[0].click();",
                                    returnDriver().findElement(By.cssSelector(
                                            ".mathpallet-keyboard .mathpalette-close")));
                            APP_LOG.info("Closed Math Pallate");
                        } catch (Exception e) {
                            APP_LOG.error(
                                    "Error while closing Math Pallate " + e);
                        }
                    }
                    APP_LOG.info("Attempted free response type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and Free Response";
                    } else {
                        questionDescription = "Free Response";
                    }
                }

                else if ((activityType.containsKey("FIBDropDown"))
                        && (bFibDropDown == false)) {
                    bFibDropDown = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("FIBDropDown");
                    List<WebElement> fibDropdowns = findElement
                            .findListAndHandleStaleElementException(
                                    "FIBDropDown");

                    for (int dropdownCounter = 0; dropdownCounter < fibDropdowns
                            .size(); dropdownCounter++) {

                        // Click on the Dropdown to open it
                        fibDropdowns.get(dropdownCounter).click();
                        clickOnElementAndHandleStaleException(
                                "DiagnosticDropdownOptionOne",
                                "Select first option from the dropdown.");
                        clickOnElementAndHandleStaleException(
                                "DiagnosticDropdownOptionOne",
                                "Select first option from the dropdown.");
                        // Click the first option in the dropdown menu

                    }
                    APP_LOG.info("Attempted FIB_dropdown type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and FIB Dropdown";
                    } else {
                        questionDescription = "FIB Dropdown";
                    }

                }

                else if ((activityType.containsKey("McqSa"))
                        && (bMcqSa == false)) {
                    bMcqSa = true;
                    if (!(getSelectedOptionsCount("mcqsa").toString()
                            .contains("1"))) {
                        List<WebElement> list = (List<WebElement>) activityType
                                .get("McqSa");
                        for (WebElement ele : list) {
                            try {
                                WebDriver webDriver = returnDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                js.executeScript(
                                        "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
                                scrollWebPage(0, 400);
                            } catch (Exception e) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": Errorwhile clicking on FirstRadio button: "
                                                + e,
                                        "Click on FirstRadio button",
                                        this.reportTestObj);
                                return;
                            }
                        }
                    }
                    APP_LOG.info("Attempted McqSa type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and McqSa";
                    } else {
                        questionDescription = "McqSa";
                    }
                }
                // Checking for individual MCQMA question
                else if ((activityType.containsKey("McqMa"))
                        && (bMcqMa == false)) {
                    bMcqMa = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("McqMa");
                    for (WebElement ele : list) {
                        try {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript(
                                    "return document.querySelector(\"label>input[type='checkbox']\").click();");
                            scrollWebPage(0, 400);
                        } catch (Exception e) {
                            logResultInReport(
                                    Constants.FAIL
                                            + ": Errorwhile clicking on Firstcheckbox button: "
                                            + e,
                                    "Click on FirstCheckBox button",
                                    this.reportTestObj);
                            return;
                        }
                        APP_LOG.info("Attempted McqMa type question");
                        if (questionDescription != "") {
                            questionDescription = questionDescription
                                    + " and McqMa";
                        } else {
                            questionDescription = "McqMa";
                        }
                    }
                }

                if ((activityType.containsKey("NumberLine"))
                        && (bNumberLine == false)) {
                    bNumberLine = true;
                    List<WebElement> list = (List<WebElement>) activityType
                            .get("NumberLine");
                    for (WebElement ele : list) {
                        try {

                            Actions actions = new Actions(returnDriver());
                            actions.moveToElement(ele);
                            actions.click();
                            actions.click();
                            actions.build().perform();

                        }

                        catch (Exception e) {
                            APP_LOG.error(
                                    "Error while clicking number line " + e);
                        }
                    }
                    APP_LOG.info("Attempted Number Line type question");
                    if (questionDescription != "") {
                        questionDescription = questionDescription
                                + " and Number Line";
                    } else {
                        questionDescription = "Number Line";
                    }
                }

            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occured while attempting diagnostic question:- "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occured while attempting diagnostic question:- "
                            + e.getMessage(),
                    "Attempt user choice question without submit.",
                    this.reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 11 May,2018
     * @description Press enter using keyboard
     */
    public void hitEnterUsingKeyboard() {

        try {
            APP_LOG.debug("Hit enter key using keyboard");
            this.result = this.performAction.execute(ACTION_PRESS_ENTER_KEY);
            logResultInReport(this.result, "Hit Enter key using keyboard.",
                    this.reportTestObj);
        } catch (Exception e) {
            APP_LOG.info("Exception occured while pressing enter key"
                    + e.getMessage());
        }
    }

    public void verifyRadioButtonIsSelectedAfterGivingInputInFib() {
        try {
            if (getSelectedOptionsCount("mcqsa").toString().contains("1")) {
                this.result = Constants.PASS + ": Radio button is selected";
                logResultInReport(this.result,
                        "Verify Radio button is selected after giving input in Fib Free Response.",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Radio button is not selected";
                logResultInReport(this.result,
                        "Verify Radio button is selected after giving input in Fib Free Response.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occured finding radio button." + e.getMessage());
        }
    }

    public void enterInpuInFib() {
        try {
            FindElement element = new FindElement();
            List<WebElement> freeResponse = element
                    .getElements("FIBFreeResponse");
            for (WebElement ele : freeResponse) {
                if ("Safari".equalsIgnoreCase(capBrowserName)) {
                    String clas = ele.getAttribute("class");
                    WebDriver webDriver1 = returnDriver();
                    JavascriptExecutor js1 = (JavascriptExecutor) webDriver1;
                    js1.executeScript("return window.jQuery(\"." + clas
                            + "\").val(\"12345678A123456\")");
                    ele.sendKeys(Keys.BACK_SPACE);
                    logResultInReport(
                            Constants.PASS
                                    + ": Value is entered in Free response box.",
                            "Enter value in FIB field.", this.reportTestObj);
                    break;
                } else {
                    Actions actions = new Actions(returnDriver());
                    actions.moveToElement(ele);
                    actions.click();
                    actions.sendKeys("Some Name");
                    actions.build().perform();
                    logResultInReport(
                            Constants.PASS
                                    + ": Value is entered in Free response box.",
                            "Enter value in FIB field.", this.reportTestObj);
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception occured inputting value." + e.getMessage());
        }
    }

    public void selectDiagnosticTestRadioButton() {

        try {
            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(
                    "return document.querySelector(\"input[id*='_1'][type='radio']\").click();");
            logResultInReport(
                    Constants.PASS + ": Select another MCQ radio button.",
                    "Click on Another Radio  button.", this.reportTestObj);
            // scrollWebPage(0, 400);
        } catch (Exception e) {
            logResultInReport(Constants.FAIL
                    + ": Errorwhile clicking on FirstRadio button: " + e,
                    "Click on FirstRadio button", this.reportTestObj);
            return;
        }

    }

    public void verifyFreeResponseTextIsSavedBySelectingNextMcq() {

        try {
            FindElement element = new FindElement();
            // enterInpuInFib();
            List<WebElement> list = element.getElements("FreeResponseInput");
            String fibTextBefore = list.get(0).getText();
            selectDiagnosticTestRadioButton();
            String fibTextAfter = list.get(0).getText();
            if (fibTextBefore.equals(fibTextAfter)) {
                logResultInReport(
                        Constants.PASS + ": Fib Text entered before '"
                                + fibTextBefore
                                + "' is same after selecting diffrent radio button which is '"
                                + fibTextAfter + "' .",
                        "Verify the first entered fib value is retained after selecting diffrent radio button.",
                        this.reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + ": Fib Text entered before '"
                                + fibTextBefore
                                + "' is not same after selecting diffrent radio button which is '"
                                + fibTextAfter + "' .",
                        "Verify the first entered fib value is retained after selecting diffrent radio button.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.info("Exception occured ." + e.getMessage());
        }
    }

    public void verifySubmitButtonDiasableInMultipart() {
        try {
            FindElement element = new FindElement();
            List<WebElement> submitButtons = element
                    .getElements("DiagnosticSubmitButton");
            if (submitButtons.size() > 0) {
                submitButtons.get(submitButtons.size() - 1).click();
                verifyButtonEnabledOrDisabled("DiagnosticSubmitButton", "No",
                        "Verify subnmit button for second part of multipart is disabled after clicking it.");
                for (int i = 0; i < submitButtons.size() - 1; i++) {
                    submitButtons.get(i).click();
                }
            } else {
                logResultInReport(
                        Constants.FAIL
                                + ": Submit button is not found in multipart",
                        "Verify subnmit button for second part of multipart is disabled after clicking it.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.info("Exception occured ." + e.getMessage());
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 07 Dec,2017
     * @description Navigate a New Learner to a specified Activity type in
     *              Diagnostic Test with XY Graph in Answer Card
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa/
     *            Collection
     */

    public void
           navigateToActivityTypeWithXYGraphInAnswerCard(String activityType) {

        // Click on the I dont know this button on the first question to allow
        // Got It button to appear
        APP_LOGS.debug(
                "Clicking 'I don't know this' button on the very first question");
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticIdontKnowThisButton");

        APP_LOGS.debug(
                "Clicking 'Got It' button which appears only once after clicking the 'I don't know this' button first time");
        // Click on the I got it button to remove the overlay
        performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                "DiagnosticGotItButton");

        try {
            List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {
                if (i == progressMaxIndex) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type question is not available",
                            "Verify that user has navigated to '" + activityType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }

                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying --> Presence of 'I don't know this' button and adding locators to a list");
                    iDontKnowThisButtons = findElement
                            .getElements("DiagnosticIdontKnowThisButton");
                    for (WebElement button : iDontKnowThisButtons) {
                        x.scrollIntoView("DiagnosticIdontKnowThisButton");
                        button.click();
                        APP_LOGS.debug("'I don't know this' button is clicked");
                    }
                    iDontKnowThisButtons.clear();
                }
                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        if (verifyElementPresentWithOutLog("GraphsOnAnswerCard")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("DiagnosticMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "DiagnosticMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        if (verifyElementPresentWithOutLog("GraphsOnAnswerCard")
                                .contains(Constants.PASS)) {
                            APP_LOGS.debug("Verified --> Presence of XY Graph"
                                    + activityType + "type question.");
                            logResultInReport(
                                    Constants.PASS
                                            + ": Skipped previous questions and Successfully Navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    "Verify that user has navigated to '"
                                            + activityType
                                            + "' type question with XY Graph",
                                    this.reportTestObj);
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to '"
                            + activityType + "' type question : "
                            + e.getMessage(),
                    "Verify that user has navigated to '" + activityType
                            + "' type question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @date 17 May,2018
     * @description Provide user inputs in Free Response Text Box and verify
     *              that input appears correctly in Free Response Box
     * @param userInput
     *            -> input value and inputType -> square root, modulus ,
     *            text/number
     * 
     */

    public void verifyUserInputDisplayedCorrectlyInFIBFreeResponse(
            String userInput, String inputType) {
        boolean compResult;
        String actualValue;
        try {
            clearFreeResponseBoxByAction("FIBFreeResponse");

            if (inputType.equalsIgnoreCase("square-root")) {

                moveToElementAndClick("FIBFreeResponse");
                moveToElementAndClick("DiagnosticMathPalletSquareRootIcon");
                TimeUnit.SECONDS.sleep(5);

                enterInputDataViaActions("DiagnosticMathPalletSquareRootInput",
                        userInput,
                        "Verify user is able to provide input in Free Response Box as :\\sqrt{"
                                + userInput + "}");
                actualValue = getAttributeOfHiddenElement(
                        "DiagnosticNotDisabledFibFreeResponseBox", "value");
                compResult = ("\\sqrt{" + userInput + "}").equals(actualValue);

            }

            else if (inputType.equalsIgnoreCase("modulus")) {

                moveToElementAndClick("FIBFreeResponse");
                moveToElementAndClick("DiagnosticMathPalletModulousIcon");
                TimeUnit.SECONDS.sleep(5);

                enterInputDataViaActions("DiagnosticMathPalletModulousInput",
                        userInput,
                        "Verify user is able to provide input in Free Response Box as :|"
                                + userInput + "|");

                actualValue = getAttributeOfHiddenElement(
                        "DiagnosticNotDisabledFibFreeResponseBox", "value");
                compResult = ("\\left|" + userInput + "\\right|")
                        .equals(actualValue);

            }

            else {

                enterInputDataViaActions("FIBFreeResponse", userInput,
                        "Verify user is able to provide input in Free Response Box as :"
                                + userInput);
                actualValue = getAttributeOfHiddenElement(
                        "DiagnosticNotDisabledFibFreeResponseBox", "value");
                compResult = userInput.equals(actualValue);
            }

            if (compResult) {

                logResultInReport(
                        Constants.PASS + ":" + actualValue
                                + " value is reflecting Correctly",
                        "Verify that user input is getting reflected correctly in Free Response Box for input",
                        reportTestObj);

            } else {

                logResultInReport(
                        Constants.FAIL + ":" + actualValue
                                + " value is not reflecting Correctly",
                        "Verify that user input is getting reflected correctly in Free Response Box for input",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Excention Occurred:" + e);

            logResultInReport(
                    Constants.FAIL + "Exception Occurred" + e.getMessage(),
                    "Verify that user input is getting reflected correctly in Free Response Box for input",
                    reportTestObj);
        }

    }

    public void navigateToQuestionWithQuestionId(String locator,
            String questionId, String questionType) {
        try {
            returnDriver().navigate().to(configurationsXlsMap.get("LaunchURL")
                    + "/#/question/Q-I1201020");
            performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "DiagnosticPopUpLeaveButton");

            performAction.execute(ACTION_CLICK_BY_JS,
                    "DiagnosticPopUpLeaveButton");
            performAction.execute(ACTION_CLICK_BY_JS,
                    "DiagnosticPopUpLeaveButton");
            returnDriver().navigate().to(configurationsXlsMap.get("LaunchURL")
                    + "/#/question/" + questionId);
            refreshPage();
            List<WebElement> getElement = returnDriver()
                    .findElements(By.cssSelector(locator));
            // HashMap<String, List> checkHybridNotFound =
            // returnSetOfQuestionsFound();
            if (getElement.size() > 0)// && checkHybridNotFound.size() == 1) {
            {
                APP_LOGS.debug(questionType + " question is found.");
                result = Constants.PASS + ": User is successfully navigated to "
                        + questionType + " question.";
                logResultInReport(result,
                        "Navigate to " + questionType + " question.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL + "Exception Occurred" + e.getMessage(),
                    "Verify that user input is navigated to " + questionType,
                    reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 07 Mar,2018
     * @description Verify user is completing adaptive diagnostic test
     * @return object GLPLearner_DiagnosticTestPage
     */
    public GLPLearner_CourseMaterialPage
           attemptAdaptiveDiagnosticTestWithoutErrorHandling(
                   int diagnosticStartIteration, int diagnosticMaxIteration,
                   String attemptType) {
        APP_LOGS.debug("Attempt diagnostic test");
        try {
            if (attemptType.contains("SubmitWithoutAttempt")) {
                skipDiagnosticTestQuestionsWithoutErrorHandle(
                        diagnosticStartIteration, diagnosticMaxIteration,
                        attemptType);
            } else {
                setProgressIndexValue();
                for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                    FindElement checkLoadState = new FindElement();
                    checkLoadState.checkPageIsReady();
                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestionsFound();
                    List<WebElement> resultPageElement1 = returnDriver()
                            .findElements(By
                                    .cssSelector(".diagnostic-result__banner"));
                    List<WebElement> resultPageElement2 = returnDriver()
                            .findElements(By.cssSelector("#LOBtn_1"));
                    if ((availableQuestionTypes.size() == 0)
                            && (resultPageElement1.size() == 0)
                            && (resultPageElement2.size() == 0)) {
                        this.result = Constants.FAIL
                                + ": No desired question avialable on page ";
                        logResultInReport(this.result,
                                "Verify Diagnostic test is completed",
                                this.reportTestObj);
                    }
                    try {
                        if (resultPageElement1.size() > 0
                                || resultPageElement2.size() > 0) {
                            break;
                        } else {

                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    attemptType);
                            FindElement ele = new FindElement();
                            ele.checkPageIsReadyWithLogs();
                        }
                    } catch (Exception e) {
                        APP_LOG.error("Error while attempting diagnostic"
                                + e.getMessage());
                    }

                }
            }

        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
        } finally {
            FindElement checkLoadState = new FindElement();
            checkLoadState.checkPageIsReady();
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    public void setProgressIndexValueWithoutErrorHandling() {
        try {
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "DiagnosticSubmitButton");
            progressStartIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemin"));
            progressMaxIndex = Integer.parseInt(
                    getAttribute("DiagnosticProgressBar", "aria-valuemax"));
        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Progress bar not visible - "
                            + e.getMessage(),
                    "Verify Progress bar visible", reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 17 Oct, 2017
     * @description Skip diagnostic test questions by clicking "Submit" button
     */
    public GLPLearner_CourseMaterialPage
           skipDiagnosticTestQuestionsWithoutErrorHandle(
                   int diagnosticStartIteration, int diagnosticMaxIteration,
                   String attemptType) {
        APP_LOG.debug(
                "Skip diagnostic test by clicking submit button without selecting/entering any value");

        try {
            setProgressIndexValueWithoutErrorHandling();
            for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                FindElement ele = new FindElement();
                ele.checkPageIsReadyWithLogs();
                HashMap<String, List> availableQuestionTypes = returnSetOfQuestionsFound();
                try {
                    List<WebElement> resultPageElement1 = returnDriver()
                            .findElements(By
                                    .cssSelector(".diagnostic-result__banner"));
                    List<WebElement> resultPageElement2 = returnDriver()
                            .findElements(By.cssSelector("#LOBtn_1"));
                    if ((availableQuestionTypes.size() == 0)
                            && (resultPageElement1.size() == 0)
                            && (resultPageElement2.size() == 0)) {
                        this.result = Constants.FAIL
                                + ": No desired question avialable on page ";
                        logResultInReport(this.result,
                                "Verify Diagnostic test is completed",
                                this.reportTestObj);
                    }

                    if (resultPageElement1.size() > 0
                            || resultPageElement2.size() > 0) {
                        logResultInReport(
                                Constants.PASS
                                        + ": Completed diagnostic test by clicking submit button without providing any answer",
                                "Attempt diagnostic test by clicking submit without answering any question",
                                reportTestObj);
                        break;
                    } else {
                        List<WebElement> iDontKnowButtons = ele
                                .getElements("SubmitWithoutAttempt");
                        for (int j = 0; j < iDontKnowButtons.size(); j++) {
                            WebDriver webDriver = returnDriver();
                            JavascriptExecutor js = (JavascriptExecutor) webDriver;
                            js.executeScript("arguments[0].click();",
                                    iDontKnowButtons.get(j));
                            ele.checkPageIsReadyWithLogs();
                        }
                        ele.checkPageIsReadyWithLogs();
                    }
                } catch (Exception e) {
                    performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION,
                            "SubmitWithoutAttempt");
                    ele.checkPageIsReadyWithLogs();
                    logResultInReport(
                            Constants.FAIL
                                    + ": Exception while clicking Submit button - "
                                    + e.getMessage(),
                            "Attempt diagnostic test by clicking submit without answering any question",
                            reportTestObj);
                }

            }
        } catch (Exception e) {
            APP_LOG.error("Error while attempting diagnostic");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while attempting diagnostic test - "
                            + e.getMessage(),
                    "Attempt diagnostic test by clicking submit without answering any question",
                    reportTestObj);
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

}