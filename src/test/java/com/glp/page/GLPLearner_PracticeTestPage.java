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
package com.glp.page;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.BaseClass;
import com.autofusion.CouchBaseDB;
import com.autofusion.InitializeWebDriver;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class GLPLearner_PracticeTestPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private FindElement findElement = new FindElement();
    private PerformAction performAction = new PerformAction();
    public int progressStartIndex = 0;
    public int progressMaxIndex = 0;

    public GLPLearner_PracticeTestPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
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

    /**
     * @author lekh.bahl
     * @date 12 July,2017
     * @description Mouse Hovering on element
     * @return String PASS/FAIL
     */
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
     * @description Getting text of a particular element
     * @return Text of the particular element
     */
    public String getText(String element) {
        APP_LOGS.debug("Inside Fucn: getText");

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, element);
        if (valueText.contains(Constants.FAIL)) {
            logResultInReport(valueText,
                    "Getting text of a particular element :" + element,
                    this.reportTestObj);
            return "";
        } else {
            return valueText;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String locator, String text,
            String stepDesc) {
        APP_LOGS.debug("Element is present: " + locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, locator,
                text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Click on a web element
     * @return The object of ProductApplication_courseHomePage
     */
    public GLPLearner_PracticeTestPage clickOnElement(String locator,
            String message) {
        APP_LOGS.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLICK, locator);
        if (this.result.contains(Constants.FAIL)) {
            this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                    locator);
        }
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOGS);
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
     * @author Nitish.Jaiswal
     * @date 19 Sep,2017
     * @description Verify Logout
     * @return The object of GLPLearner_PracticeTestPage
     */
    public GLPLearner_PracticeTestPage verifyLogout() {
        GLPInstructor_CourseViewPage objCourseViewPage = new GLPInstructor_CourseViewPage(
                reportTestObj, APP_LOGS);
        try {
            // Check whether page is ready for execution
            findElement.checkPageIsReady();
            objCourseViewPage.clickOnElement("CourseViewUserName",
                    "Click on User name to open Signout DropDown.");
            objCourseViewPage.clickOnElement("LogoutButton",
                    "Click on Logout Button.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOGS);
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
     * @author sumit.bhardwaj
     * @param element
     *            --> Element to check visiblity
     * @return --> Pass if present else Fail
     */
    public String verifyElementPresentWithOutLog(String element) {
        // this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
        // element);
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
     * @date 14 Nov,2017
     * @description :Verify element not present.
     */
    public GLPLearner_PracticeTestPage verifyElementNotPresent(String locator,
            String message) {
        APP_LOGS.debug(locator + "Element is not present");
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOG);
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
    public String verifyElementIsVisible(String locator, String message) {
        APP_LOGS.debug("Element is visible: " + locator);
        this.result = this.performAction
                .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return this.result;
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
        String ma = ".diagnosticTest [id^='McqMa_'] input:checked";
        String sa = ".diagnosticTest [id^='McqSa_'] input:checked";
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
     * @author mohit.gupta5
     * @date 20 Dec,2017
     * @description verify Text Comparison
     */

    public void compareText(String text1, String text2,
            String... isStateCompare) {
        String isStateCompareCheck = isStateCompare.length > 0
                ? isStateCompare[0] : "no";
        String reverseCompareCheck = isStateCompare.length > 3
                ? isStateCompare[3] : "NoReverseCompare";
        if (text1.equals(text2)) {
            if (isStateCompareCheck.equalsIgnoreCase("yes")) {

                if (reverseCompareCheck.equalsIgnoreCase("ReverseCompare")) {
                    this.result = Constants.FAIL + ": Item 1 : "
                            + isStateCompare[1] + " is same as Item 2 :"
                            + isStateCompare[2];
                    logResultInReport(this.result,
                            "Verify that the compared items are not same.",
                            this.reportTestObj);

                } else {

                    this.result = Constants.PASS + ": Item 1 : "
                            + isStateCompare[1] + " is same as Item 2 :"
                            + isStateCompare[2];
                    logResultInReport(this.result,
                            "Verify that the compared items are same.",
                            this.reportTestObj);
                }

            } else {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is same as Expected Text : " + text2;
                logResultInReport(this.result,
                        "Verify that Actual text is equal to Expected text",
                        this.reportTestObj);
            }
        } else {

            if (isStateCompareCheck.equalsIgnoreCase("yes")) {

                if (reverseCompareCheck.equalsIgnoreCase("ReverseCompare")) {
                    this.result = Constants.PASS + ": Item 1 : "
                            + isStateCompare[1] + " is not same as Item 2 :"
                            + isStateCompare[2];
                    logResultInReport(this.result,
                            "Verify that the compared items are not same.",
                            this.reportTestObj);

                } else {

                    this.result = Constants.FAIL + ": Item 1 : "
                            + isStateCompare[1] + " is not same as Item 2 :"
                            + isStateCompare[2];
                    logResultInReport(this.result,
                            "Verify that the compared items are same.",
                            this.reportTestObj);
                }

            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is not same as Expected Text :" + text2;
                logResultInReport(this.result,
                        "Verify that Actual text is same as Expected text",
                        this.reportTestObj);
            }

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
                    webDriver.findElement(By.cssSelector(
                            ".diagnosticTest .assementHeader .playerTopPanel>button.closeIcon")));
            logResultInReport(
                    Constants.PASS
                            + ": Clicked on Cross Icon on Assessment player",
                    message, this.reportTestObj);
        } else {
            clickOnElement(locator, message);
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
     * @author yogesh.choudhary
     * @description: Click and Verify TOC
     */

    public GLPLearner_DiagnosticTestPage verifyTOC() {

        try {
            APP_LOGS.debug("Navigate to Table of Content");

            // Click on Drawer ICON
            clickOnElement("DrawerIcon", "Click on Drawer ICON");
            // Verify TOC opened
            verifyElementPresent("LoListActive", "Verify TOC is opened");

        } catch (Exception e) {
            APP_LOG.debug("Exception in navaigating TOC");
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @description: Navigate using Next arrow till last Sub Headings
     */

    public GLPLearner_PracticeTestPage navigateToAllNextQuestions() {

        List<WebElement> list = findElement.getElements("SubHeadingsFirstEo");
        try {
            APP_LOGS.debug("Attempt all subheadings");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                if (verifyElementPresentWithOutLog("EODetailPageNextArrow")
                        .contains(Constants.FAIL)) {
                    APP_LOG.debug(
                            "Next arrow is not available all content attempted");
                    break;
                }

            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating till last EO content");
        }
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author lekh.bahl
     * @date 06 Apr,2018
     * @description Click on browser back button
     */
    public GLPLearner_CourseViewPage clickBrowserBackButton(String message) {
        this.result = this.performAction.execute(ACTION_NAVIGATE_BROWSER_BACK);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
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
    public int attemptPracticeTest(int diagnosticStartIteration,
            int diagnosticMaxIteration, String attemptType) {
        APP_LOGS.debug("Start attempting practice test");
        int totalNumberOfQuestionAttempted = 0;
        try {
            // setProgressIndexValue();
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "SubmitButton");
            int progressStartIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemin"));
            int progressMaxIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemax"));
            progressStartIndex = diagnosticStartIteration;
            if (diagnosticMaxIteration > 0) {
                progressMaxIndex = progressMaxIndex
                        - (progressMaxIndex - diagnosticMaxIteration);
            }
            // for (int i = progressStartIndex; i < progressMaxIndex; i++) {
            for (int i = progressStartIndex; i < progressMaxIndex; i++) {
                FindElement checkLoadState = new FindElement();
                checkLoadState.checkPageIsReady();
                HashMap<String, List> availableQuestionTypes = returnSetOfQuestion1();
                try {
                    if ((availableQuestionTypes.size() == 0) && (returnDriver()
                            .findElement(By.cssSelector("#nextTopic"))
                            .isDisplayed())) {
                        break;
                    } else if (availableQuestionTypes.size() == 0) {
                        logResultInReport(
                                Constants.FAIL
                                        + ": No desired question avialable on page",
                                "Verify that user is able to attempt the practice question.",
                                this.reportTestObj);
                    } else {
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestMultipart")
                                        .contains(Constants.PASS)) {
                            attemptMultipartQuestionInPracticeTest(1,
                                    getNumberOfPartsInMultipartQuestion());
                            totalNumberOfQuestionAttempted = verifyAndAttamptIfRetryRequired();
                        } else {
                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    attemptType);
                            if (!("".equalsIgnoreCase(attemptType))) {
                                totalNumberOfQuestionAttempted = verifyAndAttamptIfRetryRequired();
                            }
                        }
                    }
                } catch (Exception e) {
                    attemptUIRenderedQuestion(availableQuestionTypes,
                            attemptType);
                }
            }

        } catch (Exception e) {
            APP_LOG.error("Error while attempting practice question");
        } finally {
            FindElement checkLoadState = new FindElement();
            checkLoadState.checkPageIsReady();
        }

        return totalNumberOfQuestionAttempted;
    }

    /**
     * @author lekh.bahl
     * @date 06 Apr,2018
     * @description verify and attempt question in practice test according to
     *              Try again and next question button
     */
    public synchronized int verifyAndAttamptIfRetryRequired() {
        int count = 0;
        boolean areTriesExhausted = false;
        int totalAttemptedQuestions = 0;

        try {
            FindElement checkLoadState = new FindElement();
            checkLoadState.checkPageIsReady();

            // Check whether the negative feedback is displayed
            if (performAction
                    .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                            "PracticeTestFailFeedbackText")
                    .contains(Constants.PASS)) {
                // Click on Submit button until the tries get exhausted
                while (!areTriesExhausted && count < 4) {
                    this.performAction.execute(ACTION_CLICK, "SubmitButton");
                    if (performAction
                            .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                    "NextButton")
                            .contains(Constants.PASS)
                            || performAction
                                    .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                            "PracticeTestSummaryButton")
                                    .contains(Constants.PASS)) {
                        totalAttemptedQuestions = getPracticeTestQuestionsCount();
                        areTriesExhausted = true;
                        APP_LOGS.info(
                                "Successfully attempted the displayed practice question.");
                        break;
                    }
                    count = count + 1;
                }
                // Check if the Next button is still not visible after maximum
                // tries
                if (!areTriesExhausted) {
                    APP_LOGS.info(
                            "Next Question' button is not visible after retrying "
                                    + count + " times.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": ' Next Question' button is not visible after retrying "
                                    + count + " times.",
                            "Verify that user is able to successfully reattempt the practice test question.",
                            this.reportTestObj);
                }
                // Check if Next button is displayed after all tries are
                // exhausted
                if (performAction
                        .execute(ACTION_VERIFY_ELEMENT_PRESENT, "NextButton")
                        .contains(Constants.PASS)) {
                    this.performAction.execute(ACTION_CLICK, "NextButton");
                    APP_LOGS.info(
                            "Successfully attempted the displayed practice question.");
                } else if (performAction
                        .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                "PracticeTestSummaryButton")
                        .contains(Constants.PASS)) {
                    totalAttemptedQuestions = getPracticeTestQuestionsCount();
                    // Click on Practice Test Summary button
                    performAction.execute(ACTION_CLICK,
                            "PracticeTestSummaryButton");
                    APP_LOGS.info(
                            "Successfully attempted the displayed practice question.");
                }
            } else {
                if (performAction
                        .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                "PracticeTestPassFeedbackText")
                        .contains(Constants.PASS)) {
                    if (performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                            "NextButton").contains(Constants.PASS)) {
                        // Check if Next button is displayed
                        this.performAction.execute(ACTION_CLICK, "NextButton");
                        APP_LOGS.info(
                                "Successfully attempted the displayed practice question.");
                    } else if (performAction
                            .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                    "PracticeTestSummaryButton")
                            .contains(Constants.PASS)) {

                        totalAttemptedQuestions = getPracticeTestQuestionsCount();
                        // Click on Practice Test Summary button
                        performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                "PracticeTestSummaryButton");
                        APP_LOGS.info(
                                "Successfully attempted the displayed practice question.");
                    }
                } else {
                    logResultInReport(
                            Constants.FAIL
                                    + ": ' Unable to successfully attempt the practice test question.",
                            "Verify that user is able to successfully reattempt the practice test question.",
                            this.reportTestObj);
                }
            }
            return totalAttemptedQuestions;
        } catch (Exception e) {
            APP_LOGS.info(
                    "Exception occurred while reattempting the practice test question.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while reattempting the practice test question.",
                    "Verify that user is able to successfully reattempt the practice test question",
                    this.reportTestObj);
            return 0;
        }
        /*
         * FindElement checkLoadState = new FindElement();
         * checkLoadState.checkPageIsReady(); if (performAction
         * .execute(ACTION_VERIFY_ELEMENT_PRESENT,
         * "PracticeTestFailFeedbackText") .contains(Constants.PASS)) {
         * this.performAction.execute(ACTION_CLICK, "SubmitButton");
         * this.performAction.execute(ACTION_CLICK, "SubmitButton"); if
         * (performAction .execute(ACTION_VERIFY_ELEMENT_PRESENT, "NextButton")
         * .contains(Constants.PASS)) { this.performAction.execute(ACTION_CLICK,
         * "NextButton"); } else if (performAction
         * .execute(ACTION_VERIFY_ELEMENT_PRESENT, "PracticeTestSummaryButton")
         * .contains(Constants.PASS)) { this.performAction.execute(ACTION_CLICK,
         * "PracticeTestSummaryButton"); } else { logResultInReport(
         * Constants.FAIL +
         * ": 'Next Question' Button is not visible after retrying twice.",
         * "Verify that user has completed Practice test.", this.reportTestObj);
         * }
         * 
         * } else if (performAction .execute(ACTION_VERIFY_ELEMENT_PRESENT,
         * "PracticeTestPassFeedbackText") .contains(Constants.PASS)) {
         * this.performAction.execute(ACTION_CLICK, "NextButton"); } else {
         * logResultInReport( Constants.FAIL +
         * ": 'Something went wrong in practice test.",
         * "Verify that user has completed Practice test.", this.reportTestObj);
         * }
         */
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
     * @description attemptUIRenderedQuestionmethod will attempt question and
     *              according to attempt type will click on
     *              Submit/iDon'tKnowThis button
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa/Number
     *            Line
     * @param attemptType
     *            --> SubmitButton/DiagnosticIdontKnowThisButton
     */
    public void attemptUIRenderedQuestion(HashMap activityType,
            String attemptType, boolean... toSubmitWithoutAnswer) {
        APP_LOGS.info(
                "Attempting question type specified by user : " + activityType
                        + " using attempt type : " + attemptType + "...");
        boolean bFreeResponse = false;
        boolean bFibDropDown = false;
        boolean bMcqMa = false;
        boolean bMcqSa = false;
        boolean bNumberLine = false;
        String questionDescription = "";
        boolean toSubmitWithoutAnswercheck = toSubmitWithoutAnswer.length > 0
                ? toSubmitWithoutAnswer[0] : false;
        try {
            if (toSubmitWithoutAnswercheck == false) {
                for (int i = 0; i < activityType.size(); i++) {
                    if ((activityType.containsKey("FreeResponse"))
                            && (bFreeResponse == false)) {
                        bFreeResponse = true;
                        List<WebElement> list = (List<WebElement>) activityType
                                .get("FreeResponse");
                        // list.get(0).
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
                                                        ".mathpallet-keyboard .mathpalette-close")));
                                APP_LOG.info("Closed Math Pallate");
                            } catch (Exception e) {
                                APP_LOG.error(
                                        "Error while closing Math Pallate "
                                                + e);
                            }
                        }
                        APP_LOG.info("Attempted free response type question");
                        if (questionDescription != "") {
                            questionDescription = questionDescription
                                    + " and Free Response";
                        } else {
                            questionDescription = "Free Response";
                        }
                    } else if ((activityType.containsKey("FIBDropDown"))
                            && (bFibDropDown == false)) {
                        bFibDropDown = true;
                        List<WebElement> fibDropdowns = (List<WebElement>) activityType
                                .get("FIBDropDown");
                        // List<WebElement> fibDropdowns = findElement
                        // .findListAndHandleStaleElementException(
                        // "FIBDropDown");
                        // if (e.size() != 0) {
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
                                    "PracticeTestDropdownOptionOne",
                                    "Select first option from the dropdown.");
                            // }
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
                                        "Error while clicking number line "
                                                + e);
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
            }
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
                clickOnElementAndHandleStaleException(attemptType,
                        "Click on " + attemptType + " button");
                logResultInReport(
                        Constants.PASS + ": Attempted '" + questionDescription
                                + "' type question using attempt type : "
                                + attemptType,
                        "Verify that user has attempted '" + questionDescription
                                + "' type question and Submitted the answer",
                        this.reportTestObj);
            } else if (attemptType
                    .equalsIgnoreCase("learningAidsSubmitPartButton")) {
                if (this.performAction
                        .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                attemptType)
                        .contains(Constants.FAIL)) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Learning Aids submit button is not visible",
                            "Verify that user has attempted '"
                                    + questionDescription
                                    + "' type question and Submitted the answer",
                            this.reportTestObj);
                }
                clickOnElementAndHandleStaleException(attemptType,
                        "Click on " + attemptType + " button");
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
                    "Exception occured while attempting practice question:- "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occured while attempting practice question:- "
                            + e.getMessage(),
                    "Verify that user has attempted the practice question.",
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
                    .getElement("PracticeTestProgressBar")
                    .getAttribute("style");
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
    public GLPLearner_PracticeTestPage verifyProgressBarForwardDirection(
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
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Pallavi.Tyagi
     * @date 29 Mar,2018
     * @description get total no of question on practice test summary screen
     */
    public int verifyTotalNoOfQuestionOnPracticeResultScreen() {

        APP_LOGS.debug("");
        FindElement element = new FindElement();
        List<WebElement> totalNo = element
                .getElements("PracticeTestResultScreenTotalQuestion");

        int totalQuestion = totalNo.size();
        return totalQuestion;
    }

    /**
     * @author Pallavi.Tyagi
     * @date 29 Mar,2018
     * @description compare no of question
     */
    public void compareTotalNoOfQuestion(int total1, int total2) {

        if (total1 == total2) {
            logResultInReport(Constants.PASS,
                    "Verify all attempted questions are displayed on summary screen",
                    this.reportTestObj);

        } else {
            logResultInReport(Constants.FAIL,
                    "Verify all attempted questions are not displayed on summary screen",
                    this.reportTestObj);
        }
    }

    /**
     * @author lekh.bahl
     * @date 31 Mar,2018
     * @description Verify progress bar remains same if learner exit and again
     *              go to same question
     * @return Object of GLPLearner_PracticeTestPage
     */
    public GLPLearner_PracticeTestPage verifyProgressBarNoIncrease(
            String progressBarWidthValueBefore,
            String progressBarWidthValueAfter) {
        if ((progressBarWidthValueBefore.equals(progressBarWidthValueAfter))) {
            this.result = Constants.PASS
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is same to previous value : "
                    + progressBarWidthValueBefore;
            logResultInReport(this.result,
                    "Verify that progress bar length is same if learner again lands on same question",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL
                    + ": Current progress bar with value : "
                    + progressBarWidthValueAfter
                    + " is not same to previous value :"
                    + progressBarWidthValueBefore;
            logResultInReport(this.result,
                    "Verify that progress bar length is same if learner again lands on same question",
                    this.reportTestObj);

        }
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOG);

    }

    /**
     * @author Pallavi.Tyagi
     * @date 30 Mar,2018
     * @description Verify that on summary page list of questions are shown with
     *              correct and incorrect status
     */
    public GLPLearner_PracticeTestPage
           verifyCorrectIncorrectSignAgainstEachQuestion(String locator,
                   int questionNo) {

        APP_LOGS.debug("");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element.getElements(locator);
        int totalImageCount = menuOptions.size();
        if (totalImageCount == questionNo) {
            logResultInReport(Constants.PASS,
                    "Verify that on summary page list of questions are shown with correct and incorrect status",
                    this.reportTestObj);
        } else {
            logResultInReport(Constants.FAIL,
                    "Verify that on summary page list of questions are not shown with correct and incorrect status",
                    this.reportTestObj);
        }
        return new GLPLearner_PracticeTestPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nitish.jaiswal
     * @date 21 Dec,2017
     * @description to get width of progress bar
     * @return
     */
    public String getCurrentWidthOfPracticeProgressBar() {

        String progressBarWidthValue = "";
        try {
            FindElement findElement = new FindElement();
            progressBarWidthValue = findElement
                    .getElement("PracticeTestProgressBar")
                    .getAttribute("style");
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
     * @author pallavi.tyagi
     * @date 20 Dec,2017
     * @description verify bar length Comparison
     */

    public void compareBarLength(String text1, String text2) {

        if (text1.equals(text2)) {
            this.result = Constants.PASS + ": Actual Text : " + text1
                    + " is same as Expected Text : " + text2;
            logResultInReport(this.result,
                    "Verify learner resume to practice test where he/she left off.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Actual Text : " + text1
                    + " is not same as Expected Text :" + text2;
            logResultInReport(this.result,
                    "Verify learner is not resume to practice test where he/she left off.",
                    this.reportTestObj);

        }

    }

    /**
     * @author lekh.bahl
     * @date 06 Apr,2018
     * @description get number of questions in practice test
     */
    public int getPracticeTestQuestionsCount() {

        int totalQuestionCount = 0;
        try {

            totalQuestionCount = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemax"));
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
     * @author lekh.bahl
     * @date 06 Apr,2018
     * @description verify arc filled in circle on practice test summary page
     */
    public void verifyArcFilled(String elementLocator) {
        String arcFilledSize = this.performAction
                .execute(GET_NUMBER_OF_ELEMENT_IN_LIST, elementLocator);

        if (Integer.valueOf(arcFilledSize) == 1) {
            result = Constants.PASS
                    + "One arc filled in the circle on the completeion of one LO";
            logResultInReport(result,
                    "One arc filled in the circle on the completeion of one LO ",
                    this.reportTestObj);
        } else {
            result = Constants.FAIL
                    + "One arc filled in the circle on the completeion of one LO";
            logResultInReport(result,
                    "One arc filled in the circle on the completeion of one LO",
                    this.reportTestObj);
        }

    }

    /**
     * @author mohit.gupta5
     * @date 10 Apr,2018
     * @description Navigate a New Learner to a specified Activity type in
     *              Practice Test
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToQuestionTypeOnPracticeTest(String activityType,
            String attemptType) {
        Boolean endofpracticetest = false;
        APP_LOGS.debug("Navigating to '" + activityType
                + "' type question in Practice Test...");

        try {
            // List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            // CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);
            int progressStartIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemin"));
            int progressMaxIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemax"));

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                // if (getText("PracticeTestSummarypagePracticeAgainButton")
                // .contains("Practice again")) {
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

                    if (verifyElementPresentWithOutLog(
                            "PracticeTestLearningAidAccessmentHeader")
                                    .contains(Constants.PASS)) {
                        APP_LOG.info(
                                "Successfully navigated to Learning Aid type in Practice test");
                        attemptStudentInitiatedLA(1,
                                getNumberOfPartsLearningAids(true));

                        if (verifyElementPresentWithOutLog("NextButton")
                                .contains(Constants.PASS)) {
                            clickOnElement("NextButton",
                                    "Click on Next Question button");
                            endofpracticetest = false;
                            findElement.checkPageIsReady();
                        }

                        else if (verifyElementPresentWithOutLog(
                                "PracticeTestCompleteSummaryButton")
                                        .contains(Constants.PASS)) {

                            logResultInReport(
                                    Constants.FAIL
                                            + ": Reached to end of practice test and could not find multipart question type with HMST Learning Aids",
                                    "Navigate to multipart question type with HMST Learning Aids in Practice test.",
                                    reportTestObj);
                            endofpracticetest = true;
                            break;

                        }

                    } else if (verifyElementPresentWithOutLog(
                            "PracticeTestMultipart").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestLearningAidAccessmentHeader")
                                            .contains(Constants.FAIL)) {
                        APP_LOG.info(
                                "Successfully navigated to Multipart question type in Practice test");
                        attemptMultipartQuestionInPracticeTest(1,
                                getNumberOfPartsInMultipartQuestion());
                        if (verifyElementPresentWithOutLog("NextButton")
                                .contains(Constants.PASS)) {
                            clickOnElement("NextButton",
                                    "Click on Next Question button");
                            endofpracticetest = false;
                            findElement.checkPageIsReady();
                        }

                        else if (verifyElementPresentWithOutLog(
                                "PracticeTestCompleteSummaryButton")
                                        .contains(Constants.PASS)) {

                            logResultInReport(
                                    Constants.FAIL
                                            + ": Reached to end of practice test and could not find multipart question type with HMST Learning Aids",
                                    "Navigate to multipart question type with HMST Learning Aids in Practice test.",
                                    reportTestObj);
                            endofpracticetest = true;
                            break;

                        }

                    } else {

                        HashMap<String, List> availableQuestionTypes = returnSetOfQuestion1();

                        attemptUIRenderedQuestion(availableQuestionTypes,
                                attemptType);
                        verifyAndAttamptIfRetryRequired();
                    }
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
                            "PracticeTestFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDown").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
                            "PracticeTestFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
                    if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("PracticeTestMultipart", "id")
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
                    if (verifyElementPresentWithOutLog("PracticeTestMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
                    if (verifyElementPresentWithOutLog("PracticeTestMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
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
     * @author mohit.gupta5
     * @date 10 Apr,2018
     * @description Enter input value in textbox
     */

    public void enterInputData(String locator, String value, String message) {
        APP_LOG.debug("Enter the input value- " + value);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                locator, value);
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 12 Apr,2018
     * @description Select radio button
     */

    public void selectRadioButton() {
        try {
            WebDriver webDriver = returnDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(
                    "return document.querySelector(\"input[id*='_0'][type='radio']\").click();");
            scrollWebPage(0, 400);
            reportTestObj.log(LogStatus.PASS, "Select Radio button",
                    Constants.PASS
                            + ": Successfully Click on First Radio button");
        } catch (Exception e) {
            reportTestObj.log(LogStatus.FAIL, "Select Radio button",
                    Constants.FAIL
                            + ": Error while clicking on First Radio button");
            return;
        }

    }

    /**
     * @author saurabh.sharma5
     * @date 16 Apr,2018
     * @description Navigate to a particular practice question having Learning
     *              Aid drop down
     */
    public void navigateToQuestionWithLearningAids() {
        try {
            while (verifyElementPresentWithOutLog("PracticeTestSummaryButton")
                    .contains(Constants.FAIL)
                    && verifyElementPresentWithOutLog(
                            "HelpMeAnswerThisDropdown")
                                    .contains(Constants.FAIL)) {
                attemptPracticeTest(0, 1, ResourceConfigurations
                        .getProperty("diagnosticSubmitButton"));
            }

            if (verifyElementPresentWithOutLog("HelpMeAnswerThisDropdown")
                    .contains(Constants.PASS)) {
                APP_LOG.info(
                        "Learning Aids drop down is successfully displayed to the learner.");
                logResultInReport(
                        Constants.PASS
                                + ": Learning Aids drop down is successfully displayed to the learner.",
                        "Verify Learnig Aids is visible for the displayed question.",
                        this.reportTestObj);
            } else {
                // Report and exit if no question of the practice test has LA
                // associated
                APP_LOG.info(
                        "Reached end of practice test for the module without finding Learning Aids drop down");
                logResultInReport(
                        Constants.FAIL
                                + ": Reached end of practice test for the module without finding Learning Aids drop down",
                        "Verify Learnig Aids is visible for the displayed question.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while searching for Learning Aids.",
                    "Verify Learnig Aids is visible for the displayed question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 16 Apr,2018
     * @description Open the desired Learning Aids from the Help Me Solve This
     *              drop down
     */
    public void openLearningAids(String learningAidOption) {
        // Click on Help Me Answer This drop down
        clickOnElement("HelpMeAnswerThisDropdown",
                "Click on Help Me Answer This dropdown");
        // Select Help Me Solve This value from the drop down
        if (learningAidOption.equalsIgnoreCase(ResourceConfigurations
                .getProperty("learningAidsHelpMeSolveThis"))) {
            clickOnElement("HelpMeSolveThisLAOption",
                    "Select 'Help Me Solve This' option from the dropdown");
        } else {
            clickOnElement("ViewAnExampleLAOption",
                    "Select 'View An Example' option from the dropdown");
        }
        // Verify Help Me Solve This window opens
        verifyElementIsVisible("LearningAidsBody",
                "Verify Learning Aids window is displayed to the learner");
    }

    /**
     * @author saurabh.sharma5
     * @date 19 Apr,2018
     * @description Get the total number of parts for the Learning Aids
     */
    public int getNumberOfPartsLearningAids(boolean... isLAAsAssessment) {
        int totalPartsCount = 0;
        boolean isLAAsAssessmentCheck = isLAAsAssessment.length > 0
                ? isLAAsAssessment[0] : false;

        try {
            // Verify Number of Parts Remaining Label is displayed
            String strResult = verifyElementIsVisible(
                    "LearningAidsRemainingPartsLabel",
                    "Verify Remaining Parts Label is displayed on the top of first displayed part.");
            if (strResult.contains("PASS")) {
                // Get text of the Label
                String strText = getText("LearningAidsRemainingPartsLabel");
                APP_LOG.info("Text fetched from the Remaining Parts Label is: "
                        + strText);
                totalPartsCount = Integer.parseInt(strText.substring(0, 1));
            } else {
                logResultInReport(
                        Constants.FAIL
                                + " : Remaining parts label is not displayed for the Learning Aids.",
                        "Verify Remaining parts label is displayed for the Learning Aids",
                        this.reportTestObj);
            }
        } catch (Exception e) {

        }
        if (isLAAsAssessmentCheck == true) {
            return totalPartsCount + 1;
        } else {
            return totalPartsCount;
        }

    }

    /**
     * @author saurabh.sharma5
     * @date 19 Apr,2018
     * @description Attempt the Student Initiated Learning Aids parts depending
     *              on user input
     */
    public void attemptStudentInitiatedLA(int startIndex,
            int numberOfPartsToAttempt, boolean... ifLAToBeClosed) {

        HashMap<String, List> availableQuestionTypes = null;
        int noOftriesLeft = 0;
        String strText = "";
        boolean toCloseCheckFlag = ifLAToBeClosed.length > 0 ? ifLAToBeClosed[0]
                : false;
        try {
            findElement.checkPageIsReady();
            for (int i = startIndex; i <= numberOfPartsToAttempt; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Continue/Submit button is not displayed for the Learning Aid parts",
                            "Verify Continue/Submit button is displayed for the Learning Aid part",
                            this.reportTestObj);
                    return;
                } else {
                    // Get the text of the displayed button
                    strText = getText("LearningAidsSubmitPartButton");
                    // Check if Close button is displayed and all attempts have
                    // been consumed
                    if (!strText.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("closeText"))) {
                        // Verify if non-interactive part is displayed
                        if (strText.equalsIgnoreCase(ResourceConfigurations
                                .getProperty("courseMaterialContinueButton"))) {
                            // Click on Continue button for non-interactive part
                            clickOnElement("LearningAidsSubmitPartButton",
                                    "Click on Continue button on the displayed LA part.");
                        } else {
                            // Get the displayed question type
                            availableQuestionTypes = returnSetOfQuestionForSpecifiedPartInHMSTLA(
                                    i);
                            if ((availableQuestionTypes.size() == 0)
                                    && verifyElementPresentWithOutLog(
                                            "PracticeTestSummarypagePracticeAgainButton")
                                                    .contains(Constants.PASS)) {
                            } else if (availableQuestionTypes.size() == 0) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": No known activity type available on page",
                                        "Verify any known activity type is displayed for the Learning Aid",
                                        this.reportTestObj);
                            } else {
                                // Get total number of attempts/tries left for
                                // question.
                                noOftriesLeft = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                                        i);
                                int attemptCnt = 1;
                                for (int j = noOftriesLeft; j >= 1; j--) {
                                    logResultInReport(
                                            Constants.PASS
                                                    + " Attempting Help Me Solve This Learning Aid part: "
                                                    + i + " for " + attemptCnt
                                                    + " attempt.",
                                            "Verify the attempt made on Help Me Solve This Learning Aid question.",
                                            this.reportTestObj);
                                    // Attempt the rendered question
                                    attemptUIRenderedQuestion(
                                            availableQuestionTypes,
                                            ResourceConfigurations.getProperty(
                                                    "learningAidsSubmitButton"));
                                    findElement.checkPageIsReady();

                                    // Verify whether the correct feedback is
                                    // displayed and moving to next part
                                    List<WebElement> feedback = returnDriver()
                                            .findElements(By.cssSelector(
                                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                                            + i + ") div[class$='display-flex align-items-center mb-10']"));
                                    if (feedback.isEmpty()) {
                                        APP_LOG.info(
                                                "No feedback is displayed on submitting the Learning Aid part.");
                                        logResultInReport(
                                                Constants.FAIL
                                                        + " No feedback is displayed on submitting the Learning Aid part.",
                                                "Verify the attempt made on Help Me Solve This Learning Aid question.",
                                                this.reportTestObj);
                                    } else if (feedback.get(0)
                                            .getAttribute("class")
                                            .contains("success")) {
                                        break;
                                    }
                                    attemptCnt = attemptCnt + 1;
                                }
                            }
                        }
                    }
                    if (toCloseCheckFlag == true) {
                        if (i == numberOfPartsToAttempt
                                && verifyElementPresentWithOutLog(
                                        "PracticeTestHMSTLearningAidsCloseButton")
                                                .contains(Constants.PASS)) {
                            clickOnElement(
                                    "PracticeTestHMSTLearningAidsCloseButton",
                                    "Click on close button displayed at end of Learning Aid.");
                        }
                    }
                    // Check in case of HMST, last submission is required
                    if (i == numberOfPartsToAttempt
                            && verifyElementPresentWithOutLog(
                                    "HMSTFMultipartPlaceHolderImg")
                                            .contains(Constants.FAIL)
                            && !getText("LearningAidsSubmitPartButton")
                                    .equalsIgnoreCase(ResourceConfigurations
                                            .getProperty("closeText"))) {
                        numberOfPartsToAttempt = numberOfPartsToAttempt + 1;
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception occurred while attempting Learning Aids");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Learning Aids",
                    "Attempt the displayed part of the Learning Aid",
                    this.reportTestObj);
        }
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
     * @author ratnesh.singh
     * @date 28 Apr,2018
     * @description Navigate to multi-part Question in Practice Test
     */
    public void navigateToMultipartinPracticeTest() {

        Boolean endofpracticetest = false;
        int noOftriesLeft = 0;
        APP_LOGS.debug(
                "Navigating to multipart type question in Practice Test");
        // FindElement findElement = new FindElement();
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;

        try {

            // Verify that Multipart question is reached / present on page.
            while (verifyElementPresentWithOutLog("PracticeTestMultipart")
                    .contains(Constants.FAIL)) {

                // Get set of available question types on current page.
                availableQuestionTypes = returnSetOfQuestion1();

                // Verify that available question types on current page are
                // known
                // activity.
                if (availableQuestionTypes.size() == 0) {
                    logResultInReport(
                            Constants.FAIL
                                    + ": No known activity type available on page",
                            "Verify any known activity type is displayed in Practice Test",
                            this.reportTestObj);
                    break;
                } else {

                    // Get total number of attempts/tries left for question.
                    noOftriesLeft = getNumberOfTriesLeftParticularQuestionInPracticeTest(
                            1, "PracticeTestRemainingTriesLabelforSinglePart");

                    // Attempt current question till Next Question or Summary
                    // button is displayed.
                    while (verifyElementPresentWithOutLog("NextButton")
                            .contains(Constants.FAIL)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestCompleteSummaryButton")
                                            .contains(Constants.FAIL)
                            && noOftriesLeft >= 1)

                    {

                        attemptUIRenderedQuestion(availableQuestionTypes,
                                ResourceConfigurations
                                        .getProperty("diagnosticSubmitButton"));
                        findElement.checkPageIsReady();
                        // clickOnElement("SubmitButton",
                        // "Click on Submit/Try Again button");
                        noOftriesLeft = noOftriesLeft - 1;

                    }

                    // Click on Next Question button in-case displayed after
                    // fortunate correct attempt of all tries exhausted for
                    // question.
                    if (verifyElementPresentWithOutLog("NextButton")
                            .contains(Constants.PASS)) {
                        clickOnElement("NextButton",
                                "Click on Next Question button");
                        endofpracticetest = false;
                        findElement.checkPageIsReady();
                        if (verifyElementPresentWithOutLog("NextButton")
                                .contains(Constants.PASS)) {
                            clickOnElement("NextButton",
                                    "Click on Next Question button");
                            endofpracticetest = false;
                            findElement.checkPageIsReady();
                            if (verifyElementPresentWithOutLog("NextButton")
                                    .contains(Constants.PASS)) {
                                break;

                            }

                        }

                    }

                    // Verify if summary button is displayed.
                    else if (verifyElementPresentWithOutLog(
                            "PracticeTestCompleteSummaryButton")
                                    .contains(Constants.PASS)) {

                        endofpracticetest = true;

                    }
                }

                // Log in report incase end of Practice test reached without
                // getting any multipart question.
                if (endofpracticetest == true) {

                    logResultInReport(
                            Constants.FAIL
                                    + ": multipart question type not found in Practice Test",
                            "Navigate to multipart type question in Practice Test",
                            this.reportTestObj);
                    break;
                }

            }
            // Log in report in-case multi-part question type is reached but not
            // Learning Aid as an Assessment item.
            if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                    .contains(Constants.PASS)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestLearningAidAccessmentHeader")
                                    .contains(Constants.FAIL)) {
                APP_LOG.info(
                        "Successfully navigated to Multipart question type in Practice test");
                logResultInReport(
                        Constants.PASS
                                + ": Successfully navigated to Multipart question type in Practice test",
                        "Verify that user navigated to Multipart question type in Practice Test",
                        this.reportTestObj);

            } else {

                logResultInReport(
                        Constants.FAIL
                                + ": Failed to navigated to Multipart question type in Practice test",
                        "Verify that user navigated to Multipart question type in Practice Test",
                        this.reportTestObj);
            }

        }

        catch (

        Exception e) {
            APP_LOG.info(
                    "Exception occurred while navigating to Multipart question type in Practice test");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while navigating to Multipart question type in Practice test",
                    "Verify that user navigated to Multipart question type in Practice Test",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 26 Apr,2018
     * @description Attempt active part of Multipart question under Practice
     *              Test
     */
    public void attemptActivePartOfMultipartInPracticeTest(int part) {

        // FindElement findElement = new FindElement();
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;

        try {
            // Get set of active question types for specified part in multi-part
            // question type.
            availableQuestionTypes = returnSetOfQuestionForSpecifiedPartInMultipart(
                    part);
            if ((availableQuestionTypes.size() == 0)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestSummarypagePracticeAgainButton")
                                    .contains(Constants.PASS)) {
            }
            // Verify that available question types in specified part are known
            // activity.
            else if (availableQuestionTypes.size() == 0) {
                logResultInReport(
                        Constants.FAIL
                                + ": No known activity type available under part :"
                                + part,
                        "Verify any known activity type is displayed for the Multipart Question",
                        this.reportTestObj);
            }
            // Attempt the questions available in specified part.
            else {

                attemptUIRenderedQuestion(availableQuestionTypes,
                        ResourceConfigurations
                                .getProperty("diagnosticSubmitButton"));

            }

        } catch (

        Exception e) {
            APP_LOG.info(
                    "Exception occurred while attempting Multipart Question");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Multipart Question",
                    "Attempt the displayed part of the Multipart Question",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 26 Apr,2018
     * @description Get the total number of parts for the Multi-part question in
     *              Practice Test
     */
    public int getNumberOfPartsInMultipartQuestion() {
        int remainingPartsCount = 0;

        try {
            // Verify Number of Parts Remaining Label is displayed
            String strResult = verifyElementIsVisible(
                    "PracticeTestMultipartRemainingPartsLabel",
                    "Verify Remaining Parts Label is displayed on the top of first displayed part.");
            if (strResult.contains("PASS")) {
                // Get text of the Label
                String strText = getText(
                        "PracticeTestMultipartRemainingPartsLabel");
                APP_LOG.info("Text fetched from the Remaining Parts Label is: "
                        + strText);
                remainingPartsCount = Integer.parseInt(strText.substring(0, 1));
            } else {
                logResultInReport(
                        Constants.FAIL
                                + " : Remaining parts label is not displayed for the Multipart question.",
                        "Verify Remaining parts label is displayed for the Multipart question.",
                        this.reportTestObj);
            }
        } catch (Exception e) {

        }
        return remainingPartsCount + 1;
    }

    /**
     * @author ratnesh.singh
     * @date 26 Apr,2018
     * @description Get the total number of tries left for the
     *              Single-part/Multi-part question type in Practice Test
     */
    public int getNumberOfTriesLeftParticularQuestionInPracticeTest(int part,
            String triesLocator) {
        int totalTriesCountLeft = 0;
        String strResult;

        try {
            // Verify Number of tries Remaining Label is displayed for specified
            // part in Single-part/Multi-part question
            strResult = verifyElementIsVisible(triesLocator,
                    "Verify Remaining Tries Label is displayed for part: "
                            + part);
            if (strResult.contains("PASS")) {
                // Get text of the Label
                String strText = getText(triesLocator);
                APP_LOG.info("Text fetched from the Remaining Tries Label is: "
                        + strText);
                totalTriesCountLeft = Integer.parseInt(strText.substring(0, 1));
            }

        } catch (Exception e) {

        }
        return totalTriesCountLeft;
    }

    /**
     * @author ratnesh.singh
     * @date 26 Apr,2018
     * @description Returns map having set questions which are not disabled for
     *              specified part in Multipart in Practice Test
     */
    public HashMap returnSetOfQuestionForSpecifiedPartInMultipart(int part) {
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
                                    "div.multipartPanel div.questionsWrapper:nth-of-type("
                                            + part + ") .fib-form-group.fib-freeResponse:not([disabled])"));
                    if (freeRepsonseQuestions.size() > 0) {
                        availableQuestionTypes.put("FreeResponse",
                                freeRepsonseQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqsa")) {
                    List<WebElement> mcqSaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.multipartPanel div.questionsWrapper:nth-of-type("
                                            + part + ") input[id*='_0'][type='radio']:not([disabled])"));
                    if (mcqSaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqSa", mcqSaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i)
                        .contains("fib-dropdown")) {
                    List<WebElement> fibDropdownQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.multipartPanel div.questionsWrapper:nth-of-type("
                                            + part + ") [id^=FIB] .fib-dropdown button:not([disabled])"));
                    if (fibDropdownQuestions.size() > 0) {
                        availableQuestionTypes.put("FIBDropDown",
                                fibDropdownQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqma")) {
                    List<WebElement> mcqMaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.multipartPanel div.questionsWrapper:nth-of-type("
                                            + part + ") div[id^='McqMa']:not([disabled])"));
                    if (mcqMaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqMa", mcqMaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("numberline")) {
                    List<WebElement> numberLineQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.multipartPanel div.questionsWrapper:nth-of-type("
                                            + part + ") [id*='numberline-cursor-num']"));
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
     * @author ratnesh.singh
     * @date 2 May,2018
     * @description Returns map having set questions which are not disabled for
     *              specified part in Help Me Solve This Learning Aid in
     *              Practice Test
     */
    public HashMap returnSetOfQuestionForSpecifiedPartInHMSTLA(int part) {
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
                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                            + part + ") .fib-form-group.fib-freeResponse:not([disabled])"));
                    if (freeRepsonseQuestions.size() > 0) {
                        availableQuestionTypes.put("FreeResponse",
                                freeRepsonseQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqsa")) {
                    List<WebElement> mcqSaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                            + part + ") input[id*='_0'][type='radio']:not([disabled])"));
                    if (mcqSaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqSa", mcqSaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i)
                        .contains("fib-dropdown")) {
                    List<WebElement> fibDropdownQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                            + part + ") [id^=FIB] .fib-dropdown button:not([disabled])"));
                    if (fibDropdownQuestions.size() > 0) {
                        availableQuestionTypes.put("FIBDropDown",
                                fibDropdownQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqma")) {
                    List<WebElement> mcqMaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                            + part + ") div[id^='McqMa']:not([disabled])"));
                    if (mcqMaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqMa", mcqMaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("numberline")) {
                    List<WebElement> numberLineQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                            + part + ") [id*='numberline-cursor-num']"));
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
     * @author ratnesh.singh
     * @date 2 May,2018
     * @description Get the total number of tries left for the particular part
     *              in Help Me Solve This Learning Aid.
     * 
     */
    public int getNumberOfTriesLeftParticularQuestionInHMSTLA(int part) {
        int totalTriesCountLeft = 0;
        List<WebElement> triesLocatorHMSTLA;

        try {
            // Verify Number of tries Remaining Label is displayed for specified
            // part in Help Me Solve This Learning Aid.
            triesLocatorHMSTLA = returnDriver().findElements(By.cssSelector(
                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                            + part + ") div.gray-text"));
            if (triesLocatorHMSTLA.size() != 0) {
                // Get text of the Label
                String strText = triesLocatorHMSTLA.get(0).getText();
                APP_LOG.info("Text fetched from the Remaining Tries Label is: "
                        + strText);
                totalTriesCountLeft = Integer.parseInt(strText.substring(0, 1));
            }

        } catch (Exception e) {

        }
        return totalTriesCountLeft;
    }

    /**
     * @author ratnesh.singh
     * @date 3 May,2018
     * @description Attempt Help Me Solve This Learning Aid under Practice Test
     *              and Verify that negative feed and Response is getting
     *              displayed for interactive part
     * @return Part number for which negative Feedback is displayed
     */
    public int verifyNegativeFeedbackForInteractivePartinLA(
            boolean... ifResponseToBeChecked) {

        findElement.checkPageIsReady();
        int numberOfPartsToAttempt = 0;
        int numberOfTriesLeft = 0;
        int attemptCnt = 0;
        String buttonText = "";
        boolean incorrectFeedbackDispayed = false;
        boolean incorrectJustificationDispayed = false;
        HashMap<String, List> availableQuestionTypes = null;
        boolean reponseCheckFlag = ifResponseToBeChecked.length > 0
                ? ifResponseToBeChecked[0] : false;
        int partWithNegativeFeedback = 0;

        try {
            numberOfPartsToAttempt = getNumberOfPartsLearningAids();

            for (int i = 1; i <= numberOfPartsToAttempt; i++) {

                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains(Constants.FAIL)) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Continue/Submit button is not displayed for the Learning Aid parts",
                            "Verify Continue/Submit button is displayed for the Learning Aid part",
                            this.reportTestObj);
                    return 0;
                }

                else {

                    // Get the text of the displayed button
                    buttonText = getText("LearningAidsSubmitPartButton");
                    // Verify if non-interactive part is displayed
                    if (buttonText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsSubmitPartButton",
                                "Click on Continue button on the displayed LA part.");
                    }

                    else {

                        // Get total number of attempts/tries left for
                        // interactive
                        // part.
                        numberOfTriesLeft = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                                i);
                        attemptCnt = 1;
                        for (int j = numberOfTriesLeft; j >= 1; j--) {

                            logResultInReport(Constants.PASS
                                    + " Attempting Help Me Solve This Learning Aid part: "
                                    + i + " for " + attemptCnt + " attempt.",
                                    "Verify the attempt made on Help Me Solve This Learning Aid question.",
                                    this.reportTestObj);

                            // Attempt interactive part in HMST Learning Aids

                            // Get set of active question types for specified
                            // part
                            // in HMST
                            // LA

                            availableQuestionTypes = returnSetOfQuestionForSpecifiedPartInHMSTLA(
                                    i);

                            // Verify if Practice Test Summary Page is displayed
                            if ((availableQuestionTypes.size() == 0)
                                    && verifyElementPresentWithOutLog(
                                            "PracticeTestSummarypagePracticeAgainButton")
                                                    .contains(Constants.PASS)) {
                            }
                            // Verify that available question types in specified
                            // part are
                            // known
                            // activity.
                            else if (availableQuestionTypes.size() == 0) {
                                logResultInReport(
                                        Constants.FAIL
                                                + ": No known activity type available under part :"
                                                + i,
                                        "Verify any known activity type is displayed for the Learning Aid",
                                        this.reportTestObj);
                            }
                            // Attempt the questions available in specified
                            // part.
                            else {

                                attemptUIRenderedQuestion(
                                        availableQuestionTypes,
                                        ResourceConfigurations.getProperty(
                                                "diagnosticSubmitButton"));
                                findElement.checkPageIsReady();

                            }

                            // Verify that feedback gets displayed for incorrect
                            // attempt
                            if (verifyElementPresentWithOutLog(
                                    "learningAidInCorrectFeedBackDynamicLocator:hmstPart="
                                            + String.valueOf(i))
                                                    .contains(Constants.PASS)) {

                                String strText = getText(
                                        "learningAidInCorrectFeedBackDynamicLocator:hmstPart="
                                                + String.valueOf(i));

                                if (strText.contains(
                                        "That's incorrect. Try again.")
                                        && attemptCnt == 1) {

                                    logResultInReport(Constants.PASS
                                            + " Feedback displayed as That's incorrect. Try again. for part: "
                                            + i + " attempt :" + attemptCnt,
                                            "Verify that Incorrect feedback is displayed for Help Me Solve This Learning Aid question.",
                                            this.reportTestObj);
                                    incorrectFeedbackDispayed = true;

                                } else if (strText.contains(
                                        "That's incorrect. Give it another try.")
                                        && attemptCnt == 2) {

                                    logResultInReport(Constants.PASS
                                            + " Feedback displayed as That's incorrect. Give it another try. for part: "
                                            + i + " attempt :" + attemptCnt,
                                            "Verify that Incorrect feedback is displayed for Help Me Solve This Learning Aid question.",
                                            this.reportTestObj);
                                    incorrectFeedbackDispayed = true;

                                } else if (strText.contains("That's incorrect.")
                                        && attemptCnt == 3) {
                                    logResultInReport(Constants.PASS
                                            + " Feedback displayed as That's incorrect. for part: "
                                            + i + " attempt :" + attemptCnt,
                                            "Verify that Incorrect feedback is displayed for Help Me Solve This Learning Aid question.",
                                            this.reportTestObj);
                                    incorrectFeedbackDispayed = true;

                                }

                                if (reponseCheckFlag == true) {
                                    // Verify that Justification/Response gets
                                    // displayed
                                    // for incorrect attempt until last attempt.
                                    if (verifyElementPresentWithOutLog(
                                            "learningAidResponseDynamicLocator:hmstPart="
                                                    + String.valueOf(i))
                                                            .contains(
                                                                    Constants.PASS)
                                            && j > 1) {

                                        String strResponse = getText(
                                                "learningAidResponseDynamicLocator:hmstPart="
                                                        + String.valueOf(i));

                                        if (strResponse != "") {
                                            logResultInReport(
                                                    Constants.PASS
                                                            + " Justification/Response displayed for part: "
                                                            + i + " attempt :"
                                                            + attemptCnt,
                                                    "Verify that justification/response for Incorrect attempt is displayed for Help Me Solve This Learning Aid question.",
                                                    this.reportTestObj);
                                            incorrectJustificationDispayed = true;

                                        }

                                    }
                                }

                            }

                            // Verify whether the correct feedback is
                            // displayed and moving to next part

                            if (verifyElementPresentWithOutLog(
                                    "learningAidFeedBackDynamicLocator:hmstPart="
                                            + String.valueOf(i))
                                                    .contains(Constants.FAIL)) {
                                APP_LOG.info(
                                        "No feedback is displayed on submitting the Learning Aid part.");
                                logResultInReport(
                                        Constants.FAIL
                                                + " No feedback is displayed on submitting the Learning Aid part.",
                                        "Verify the feedback getting diaplyed under Help Me Solve This Learning Aid question.",
                                        this.reportTestObj);
                            } else if (getAttribute(
                                    "learningAidFeedBackDynamicLocator:hmstPart="
                                            + String.valueOf(i),
                                    "class").contains("success")) {
                                break;
                            }
                            attemptCnt = attemptCnt + 1;

                        }
                    }

                }

                if (incorrectFeedbackDispayed == true) {
                    partWithNegativeFeedback = i;
                    break;
                }
                if (i == numberOfPartsToAttempt
                        && verifyElementPresentWithOutLog(
                                "PracticeTestHMSTLearningAidsCloseButton")
                                        .contains(Constants.PASS)) {
                    clickOnElement("PracticeTestHMSTLearningAidsCloseButton",
                            "Click on close button displayed at end of HMST Learning Aid.");

                }

            }

            // Verify that even after attempting all the parts incorrect
            // feedback not found.
            if (incorrectFeedbackDispayed == false) {

                logResultInReport(
                        Constants.FAIL
                                + " Incorrect feedback is not getting displayed.",
                        "Verify that Incorrect feedback is displayed for Help Me Solve This Learning Aid question.",
                        this.reportTestObj);
            }

            if (reponseCheckFlag == true) {

                // Verify that Justification/Response not found for incorrect
                // attempt.
                if (incorrectJustificationDispayed == false) {

                    logResultInReport(
                            Constants.FAIL
                                    + " Justification/Response is not getting displayed for Incorrect Attempt.",
                            "Verify that justification/response for Incorrect attempt is displayed for Help Me Solve This Learning Aid question.",
                            this.reportTestObj);
                }

            }

            return partWithNegativeFeedback;

        } catch (

        Exception e) {
            APP_LOG.info("Exception occurred while attempting Learning Aids");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Learning Aids",
                    "Attempt the displayed part of the Learning Aid",
                    this.reportTestObj);
            return 0;
        }

    }

    /**
     * @author ratnesh.singh
     * @date 6 May,2018
     * @description Attempt multipart question type in Practice test based on
     *              user input
     */
    public void attemptMultipartQuestionInPracticeTest(int startIndex,
            int noOfPartToBeAttempted) {

        findElement.checkPageIsReady();
        int numberOfTriesLeft = 0;
        int attemptCnt = 0;

        try {
            for (int i = startIndex; i <= noOfPartToBeAttempted; i++) {

                // Get total number of attempts/tries left for current part.
                numberOfTriesLeft = getNumberOfTriesLeftParticularQuestionInPracticeTest(
                        i,
                        "PracticeTestMultipartRemainingTriesDynamicLabel:dynamicReplace="
                                + String.valueOf(i));

                attemptCnt = 1;
                for (int j = numberOfTriesLeft; j >= 1; j--) {

                    // Attempt current part of multipart question in practice
                    // test.

                    logResultInReport(
                            Constants.PASS + ": Attempting part: " + i + " for "
                                    + attemptCnt + " attempt.",
                            "Verify the attempt made on multipart question.",
                            this.reportTestObj);

                    attemptActivePartOfMultipartInPracticeTest(i);
                    findElement.checkPageIsReady();

                    // Verify whether the correct feedback is
                    // displayed and moving to next part

                    if (verifyElementPresentWithOutLog(
                            "PracticeTestMultipartFeedBackDynamicLocator:multipartToReplace="
                                    + String.valueOf(i))
                                            .contains(Constants.FAIL)) {
                        APP_LOG.info(
                                "No feedback is displayed on submitting the Mulipart part: "
                                        + i);
                        logResultInReport(
                                Constants.FAIL
                                        + ": No feedback is displayed on submitting the Mulipart part: "
                                        + i,
                                "Verify the feedback is getting displayed under Multipart question in Practice Test.",
                                this.reportTestObj);
                    } else {

                        // Check if the feedback is positive
                        if (getAttribute(
                                "PracticeTestMultipartFeedBackDynamicLocator:multipartToReplace="
                                        + String.valueOf(i),
                                "class").contains("success")) {
                            break;
                        }
                        // Check if the feedback is negative
                        else if (getAttribute(
                                "PracticeTestMultipartFeedBackDynamicLocator:multipartToReplace="
                                        + String.valueOf(i),
                                "class").contains("fail")) {
                            logResultInReport(Constants.PASS
                                    + ": Incorrect/Negative feedback is displayed on submitting part :"
                                    + i + " for attempt/tries :" + attemptCnt,
                                    "Verify the feedback is getting displayed under Multipart question in Practice Test.",
                                    this.reportTestObj);
                        }

                    }

                    attemptCnt = attemptCnt + 1;

                }

            }

        } catch (

        Exception e) {
            APP_LOG.info("Exception occurred while Multipart Question");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Multipart Question",
                    "Attempt the displayed part of the Multipart Question",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 6 May,2018
     * @description Navigate to Learning Aids as an Assessment Item in Practice
     *              Test
     */
    public void navigateToLAidsAsAssessmentinPracticeTest() {

        Boolean endofpracticetest = false;
        int totalNumberOfParts = 0;
        int noOftriesLeft = 0;
        APP_LOGS.debug(
                "Navigating to Learning Aids as an Assessment in Practice Test");
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;

        try {

            // Verify that Learning Aid as an Assessment is reached / present on
            // page.
            while (verifyElementPresentWithOutLog(
                    "PracticeTestLearningAidAccessmentHeader")
                            .contains(Constants.FAIL)) {

                // Verify that Multipart Question is present on Current Page and
                // attempt if present
                if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                        .contains(Constants.PASS)
                        && verifyElementPresentWithOutLog(
                                "PracticeTestLearningAidAccessmentHeader")
                                        .contains(Constants.FAIL)) {

                    totalNumberOfParts = getNumberOfPartsInMultipartQuestion();

                    attemptMultipartQuestionInPracticeTest(1,
                            totalNumberOfParts);

                }

                else {

                    // Get set of available question types on current page.
                    availableQuestionTypes = returnSetOfQuestion1();

                    // Verify that available question types on current page are
                    // known
                    // activity.
                    if (availableQuestionTypes.size() == 0) {
                        logResultInReport(
                                Constants.FAIL
                                        + ": No known activity type available on page",
                                "Verify any known activity type is displayed in Practice Test",
                                this.reportTestObj);
                        break;
                    }

                    // Get total number of attempts/tries left for question.
                    noOftriesLeft = getNumberOfTriesLeftParticularQuestionInPracticeTest(
                            1, "PracticeTestRemainingTriesLabelforSinglePart");

                    // Attempt current question till tries are left or
                    // successful attempt

                    for (int j = noOftriesLeft; j >= 1; j--) {
                        attemptUIRenderedQuestion(availableQuestionTypes,
                                ResourceConfigurations
                                        .getProperty("diagnosticSubmitButton"));
                        findElement.checkPageIsReady();

                        // Verify whether the correct feedback is
                        // displayed and moving to next part

                        if (getAttribute("PracticeTestFeedbackText", "class")
                                .contains("success")) {
                            break;
                        }

                        noOftriesLeft = noOftriesLeft - 1;

                    }
                }

                // Click on Next Question button in-case displayed after
                // fortunate correct attempt or all tries exhausted for
                // question.
                if (verifyElementPresentWithOutLog("NextButton")
                        .contains(Constants.PASS)) {
                    clickOnElement("NextButton",
                            "Click on Next Question button");
                    endofpracticetest = false;
                    findElement.checkPageIsReady();
                    if (verifyElementPresentWithOutLog("NextButton")
                            .contains(Constants.PASS)) {
                        clickOnElement("NextButton",
                                "Click on Next Question button");
                        endofpracticetest = false;
                        findElement.checkPageIsReady();
                        if (verifyElementPresentWithOutLog("NextButton")
                                .contains(Constants.PASS)) {
                            break;

                        }

                    }

                }

                // Verify if summary button is displayed.
                else if (verifyElementPresentWithOutLog(
                        "PracticeTestCompleteSummaryButton")
                                .contains(Constants.PASS)) {

                    endofpracticetest = true;

                }

                // Log in report in-case end of Practice test reached
                // without
                // getting any Learning Aids as an assessment.
                if (endofpracticetest == true) {

                    logResultInReport(
                            Constants.FAIL
                                    + ": Learning Aids as an Assessment not found in Practice Test",
                            "Navigate to Learning Aids as an Assessment in Practice Test",
                            this.reportTestObj);
                    break;
                }

            }
            // Log in report in-case multi-part question type is reached but
            // not
            // Learning Aid as an Assessment item.
            if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                    .contains(Constants.PASS)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestLearningAidAccessmentHeader")
                                    .contains(Constants.PASS)) {
                APP_LOG.info(
                        "Successfully navigated to Learning Aids as an Assessment in Practice test");
                logResultInReport(
                        Constants.PASS
                                + ": Successfully navigated Learning Aids as an Assessment in Practice test",
                        "Verify that user navigated to Learning Aids as an Assessment in Practice Test",
                        this.reportTestObj);

            } else {

                logResultInReport(
                        Constants.FAIL
                                + ": Failed to navigated to Learning Aids as an Assessment in Practice test",
                        "Verify that user navigated to Learning Aids as an Assessment in Practice Test",
                        this.reportTestObj);
            }

        }

        catch (

        Exception e) {
            APP_LOG.info(
                    "Exception occurred while navigating to Learning Aids as an Assessment in Practice test");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while navigating to Learning Aids as an Assessment in Practice test",
                    "Verify that user navigated to Learning Aids as an Assessment in Practice Test",
                    this.reportTestObj);
        }

    }

    /**
     * @author saurabh.sharma5
     * @date 05 May,2018
     * @description Get the text of the desired part of a multi part question
     */
    public String getTextOfSinglePartInMultipart(int partIndex) {
        String sContent = "";
        try {
            WebElement sPartBlock = returnDriver().findElement(By.cssSelector(
                    "div.learning-aids-body div.questionsWrapper:nth-of-type("
                            + partIndex + ")"));
            sContent = sPartBlock.getText();
            if (sContent.isEmpty()) {
                APP_LOG.info(
                        "No text found for the displayed part of the Learning Aids.");
                logResultInReport(
                        Constants.FAIL
                                + ": No text found for the displayed part of the Learning Aids.",
                        "Get the text of the displayed Learning Aid part",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while getting text of Learning Aid's part");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while getting text of Learning Aid's part",
                    "Get the text of the displayed Learning Aid part",
                    this.reportTestObj);
        }
        return sContent;
    }

    /**
     * @author saurabh.sharma5
     * @date 05 May,2018
     * @description Verify the student initiated Learning Aid gets refreshed
     */
    public void verifyLearningAidsPartsAreRefreshed(int expectedParts) {
        int currentPartsCount;
        try {
            currentPartsCount = getNumberOfPartsLearningAids();
            if (currentPartsCount != 0) {
                if (currentPartsCount == expectedParts) {
                    APP_LOG.info(
                            "The learning aid parts are refreshed and displayed from the starting.");
                    logResultInReport(
                            Constants.PASS
                                    + "The learning aid parts are refreshed and displayed from the starting.",
                            "Verify the Learning Aid parts are refreshed.",
                            this.reportTestObj);
                } else {
                    APP_LOG.info("The learning aid parts are not refreshed.");
                    logResultInReport(
                            Constants.FAIL
                                    + "The learning aid parts are not refreshed.",
                            "Verify the Learning Aid parts are refreshed.",
                            this.reportTestObj);
                }
            } else {
                APP_LOG.info("Remaining parts count could not be fetched.");
                logResultInReport(
                        Constants.FAIL
                                + " Remaining parts count could not be fetched.",
                        "Verify the Learning Aid parts are refreshed.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while verifying Learning Aids parts are refreshed.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while verifying Learning Aids parts are refreshed.",
                    "Verify the Learning Aid parts are refreshed.",
                    this.reportTestObj);
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 05 May,2018
     * @description Verify whether the original text of a Learning Aid part
     *              matches the updated one
     */
    public void verifyLearningAidPartContentIsUpdated(String originalText,
            String updatedText, boolean shouldMatch) {
        try {
            // Check whether any of the content variable is empty
            if (originalText.isEmpty() || updatedText.isEmpty()) {
                APP_LOG.info(
                        "The original/updated text of the Learning Aid part is empty.");
                logResultInReport(
                        Constants.FAIL
                                + ": The original/updated text of the Learning Aid part is empty.",
                        "Verify whether the original text of the Learning Aid part matches with the updated text.",
                        this.reportTestObj);
                return;
            }
            // Check whether user wants the text to match or not
            if (shouldMatch) {
                if (originalText.equalsIgnoreCase(updatedText)) {
                    APP_LOG.info(
                            "The original text matches the updated text of the Learning Aid part.");
                    logResultInReport(
                            Constants.PASS
                                    + ": The original text matches the updated text of the Learning Aid part",
                            "Verify whether the original text of the Learning Aid part matches with the updated text.",
                            this.reportTestObj);

                } else {
                    APP_LOG.info(
                            "The original text does not matches the updated text of the Learning Aid part.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": The original text does not matches the updated text of the Learning Aid part.",
                            "Verify whether the original text of the Learning Aid part matches with the updated text.",
                            this.reportTestObj);
                }
            } else {
                if (originalText.equalsIgnoreCase(updatedText)) {
                    APP_LOG.info(
                            "The original text matches the updated text of the Learning Aid part.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": The original text matches the updated text of the Learning Aid part.",
                            "Verify whether the original text of the Learning Aid part matches with the updated text.",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(
                            "The original text does not matches the updated text of the Learning Aid part.");
                    logResultInReport(
                            Constants.PASS
                                    + ": The original text does not matches the updated text of the Learning Aid part.",
                            "Verify whether the original text of the Learning Aid part matches with the updated text.",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while comparing the text of the Learning Aid part.");
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 08 May,2018
     * @description Get the index of interactive part of the HMST
     */
    public int navigateToInteractiveHMSTPart(int totalParts) {
        int partIndex = 0;
        String strText = "";
        try {
            for (int i = 1; i <= totalParts + 1; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Continue/Submit button is not displayed for the Learning Aid parts",
                            "Get the interactive part index of Help Me Solve This",
                            this.reportTestObj);
                    return partIndex;
                } else {
                    // Get the text of the displayed button
                    strText = getText("LearningAidsSubmitPartButton");
                    // Verify if interactive part is displayed
                    if (!strText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        APP_LOG.info(
                                "Successfully fetched the HMST interactive part index as "
                                        + i);
                        logResultInReport(
                                Constants.PASS
                                        + ": Successfully fetched the HMST interactive part index as "
                                        + i,
                                "Get the interactive part index of Help Me Solve This",
                                this.reportTestObj);
                        partIndex = i;
                        break;
                    } else {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsSubmitPartButton",
                                "Click on Continue button on the displayed LA part.");
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while getting the interactive part of the Help Me Solve This.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while getting the interactive part of the Help Me Solve This.",
                    "Get the interactive part index of Help Me Solve This.",
                    this.reportTestObj);
        }
        return partIndex;
    }

    /**
     * @author saurabh.sharma5
     * @date 08 May,2018
     * @description Verify whether the interactive part of the HMST gets
     *              refreshed
     */
    public void verifyInteractiveHMSTPartIsRefreshed(int partIndex,
            int originalTries) {
        int updatedTries = 0;
        try {
            // Get the current number of tries of the HMST part
            updatedTries = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                    partIndex);
            if (updatedTries != 0 && originalTries == updatedTries) {
                APP_LOG.info(
                        "The interactive part of the HMST gets refreshed with original number of tries.");
                logResultInReport(
                        Constants.PASS
                                + ": The interactive part of the HMST gets refreshed with original number of tries.",
                        "Verify original number of tries for the interactive part of the HMST are refreshed",
                        this.reportTestObj);
            } else if (updatedTries != 0 && originalTries != updatedTries) {
                APP_LOG.info(
                        "The interactive part of the HMST gets refreshed with original number of tries.");
                logResultInReport(
                        Constants.FAIL
                                + ": The original number of tries for interactive part of the HMST is not refreshed.",
                        "Verify original number of tries for the interactive part of the HMST are refreshed",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while verifying the interactive part of HMST gets refreshed.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while verifying the interactive part of HMST gets refreshed.",
                    "Verify original number of tries for the interactive part of the HMST are refreshed",
                    this.reportTestObj);
        }
    }

    /**
     * @author mohit.gupta5
     * @date 10 May,2018
     * @description Verify Logout
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPConsole_LoginPage verifySingout() {
        try {
            clickOnElement("PracticeTestUserNameLogOut",
                    "Click on User name to open Signout DropDown.");
            TimeUnit.SECONDS.sleep(3);
            if (webDriver instanceof SafariDriver
                    || capBrowserName.equalsIgnoreCase("Safari")) {
                ((JavascriptExecutor) returnDriver()).executeScript(
                        "arguments[0].click();", webDriver.findElement(
                                By.cssSelector("a[class*='sign-out-button']")));
            } else {
                clickOnElement("LogoutButton", "Click on Logout Button.");
            }
            GLPConsole_LoginPage objLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOGS);
            objLoginPage.verifyElementPresent("ConsoleLoginUserName",
                    "Verify user has successfully logged out.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPConsole_LoginPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author ratnesh.singh
     * @date 12 May,2018
     * @description Returns map with <String,String> specifying attempted
     *              question and corresponding state (text/style in case of
     *              number line)
     */
    public HashMap
           checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                   int part, boolean... toSkipAttemptedCheck) {
        boolean skipAttemptedCheck = toSkipAttemptedCheck.length > 0
                ? toSkipAttemptedCheck[0] : false;
        HashMap<String, String> attemptedQuestionState = new HashMap<String, String>();
        try {
            List<WebElement> freeRepsonseQuestions = returnDriver()
                    .findElements(By.cssSelector(
                            "div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part + ") .fib-form-group.fib-freeResponse.status-border-wrong"));
            if (freeRepsonseQuestions.size() > 0) {

                for (int i = 0; i < freeRepsonseQuestions.size(); i++) {

                    attemptedQuestionState.put(
                            "AttemptedFreeResponseTextBox" + i,
                            freeRepsonseQuestions.get(i).getText());

                }

            }

            List<WebElement> mcqSaQuestions = returnDriver()
                    .findElements(By.cssSelector(
                            "div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqSa'] div[class*='correct-answer-choose'],div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqSa'] [class*='wrong-answer-choose'],div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqSa'] [class=' disabled']"));
            if (mcqSaQuestions.size() > 0) {
                for (int i = 0; i < mcqSaQuestions.size(); i++) {

                    attemptedQuestionState.put("AttemptedMCQSARadioButton" + i,
                            mcqSaQuestions.get(i).getText());

                }
            }

            List<WebElement> fibDropdownQuestions = returnDriver()
                    .findElements(By.cssSelector(
                            "div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[class*='dropdown-incorrect'],div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part + ") [class*='dropdown-correct']"));
            if (fibDropdownQuestions.size() > 0) {
                for (int i = 0; i < fibDropdownQuestions.size(); i++) {

                    attemptedQuestionState.put("AttemptedFIBDropDown" + i,
                            fibDropdownQuestions.get(i).getText());

                }
            }

            List<WebElement> mcqMaQuestions = returnDriver()
                    .findElements(By.cssSelector(
                            "div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqMa'] div[class*='correct-answer-choose'],div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqMa'] [class*='wrong-answer-choose'],div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part
                                    + ") div[id*='McqMa'] [class=' disabled']"));
            if (mcqMaQuestions.size() > 0) {
                for (int i = 0; i < mcqMaQuestions.size(); i++) {

                    attemptedQuestionState.put("AttemptedMCQMACheckBox" + i,
                            mcqMaQuestions.get(i).getText());

                }
            }

            List<WebElement> numberLineQuestions = returnDriver()
                    .findElements(By.cssSelector(
                            "div.multipartPanel div.questionsWrapper:nth-of-type("
                                    + part + ") .numberline-cursor-pointer.disableNumberLine"));
            if (mcqMaQuestions.size() > 0) {
                for (int i = 0; i < numberLineQuestions.size(); i++) {

                    attemptedQuestionState.put("AttemptedNumberLine" + i,
                            numberLineQuestions.get(i).getAttribute("style"));

                }

            }

            if (skipAttemptedCheck == false) {
                if (attemptedQuestionState.size() > 0) {
                    logResultInReport(
                            Constants.PASS
                                    + ": Question elements are in attempted state for part :"
                                    + part,
                            "Verify that question elements are in attempted state for specified part.",
                            this.reportTestObj);
                    return attemptedQuestionState;

                } else {
                    logResultInReport(
                            Constants.FAIL
                                    + ": Question elements are not in attempted state for part :"
                                    + part,
                            "Verify that question elements are in attempted state for specified part.",
                            this.reportTestObj);
                    return null;

                }
            } else {

                return attemptedQuestionState;
            }
        } catch (

        Exception e) {
            APP_LOG.error(
                    "Exception occurred while fetching attempted question state of part :"
                            + part + " as " + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while fetching attempted question state of part :"
                            + part + " as " + e.getMessage(),
                    "Fetch Attempted Question State For Specified Part In Multipart Question",
                    this.reportTestObj);
            return null;
        }

    }

    /**
     * @author ratnesh.singh
     * @date 14 May,2018
     * @description Validates DB state of parameters like
     *              'status','isCorrectAnswer','Score','noOfTriesAttempted' for
     *              completed part in multipart question under Practice Test
     * 
     */
    public void validateCouchBaseDBStateForCompletedPartInMultipart(int part,
            int totalNumberOfintialTriesForCompletedPart,
            int totalNumberOfTriesLeftForCompleted,
            Map<String, String> dataMapafterCompletingPart) {

        if (dataMapafterCompletingPart.get("status").contains("Completed")) {

            logResultInReport(
                    Constants.PASS + ": 'Status' set in DB for part :" + part
                            + " is Completed.",
                    "Verify the 'status' set in DB for part :" + part
                            + " under multipart question attempted.",
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL + ": 'Status' set in DB for part :" + part
                            + " is not Completed.",
                    "Verify the 'status' set in DB for part :" + part
                            + " under multipart question attempted.",
                    this.reportTestObj);
        }

        if (dataMapafterCompletingPart.get("isCorrectAnswer")
                .contains("false")) {

            if (Double.parseDouble(
                    dataMapafterCompletingPart.get("score")) == 0) {

                logResultInReport(
                        Constants.PASS + ": 'Score' set in DB for part :" + part
                                + " is 0 for Incorrect Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " under multipart question attempted for Incorrect Attempt.",
                        this.reportTestObj);

            } else {

                logResultInReport(
                        Constants.FAIL + ": 'Score' set in DB for part :" + part
                                + " is not 0 for Incorrect Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " under multipart question attempted for Incorrect Attempt.",
                        this.reportTestObj);
            }

        } else if (dataMapafterCompletingPart.get("isCorrectAnswer")
                .contains("true")) {

            if (Double
                    .parseDouble(dataMapafterCompletingPart.get("score")) > 0) {

                logResultInReport(
                        Constants.PASS + ": 'Score' set in DB for part :" + part
                                + " is greater than 0 for correct Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " under multipart question attempted for correct Attempt.",
                        this.reportTestObj);
            } else {

                logResultInReport(
                        Constants.FAIL + ": 'Score' set in DB for part :" + part
                                + " is not greater than 0 for correct Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " under multipart question attempted for correct Attempt.",
                        this.reportTestObj);
            }
        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'IsCorrectAnswer' flag set in DB for part :"
                            + part + " is neither 'true' nor 'false'.",
                    "Verify the 'isCorrectAnswer' set in DB for part :" + part
                            + " under multipart question attempted.",
                    this.reportTestObj);

        }

        if (Integer.parseInt(dataMapafterCompletingPart
                .get("noOfTriesAttempted")) == (totalNumberOfintialTriesForCompletedPart
                        - totalNumberOfTriesLeftForCompleted)) {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAttempted' set in DB for part :"
                            + part + " is correct as "
                            + Integer.parseInt(dataMapafterCompletingPart
                                    .get("noOfTriesAttempted")),
                    "Verify the 'noOfTriesAttempted' set in DB for part :"
                            + part + " under multipart question attempted.",
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'noOfTriesAttempted' set in DB for part :"
                            + part + " is incorrect as "
                            + Integer.parseInt(dataMapafterCompletingPart
                                    .get("noOfTriesAttempted")),
                    "Verify the 'noOfTriesAttempted' set in DB for part :"
                            + part + " under multipart question attempted.",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 15 May,2018
     * @description Close-Reopen Browser and navigate to GLP console URL
     * @return New WebDriver instance
     */
    public WebDriver closeReopenBrowserAndNavigateGLPConsole() {
        WebDriver d = null;
        try {
            this.webDriver.quit();
            this.webDriver = null;
            APP_LOG.info("Browser closed successfully");
            setUp();
            if (this.webDriver == null) {
                if ("Local".equalsIgnoreCase(runOnMachine)) {
                    d = this.getWebDriver();
                    setWebDriver(d);
                    getDriver();
                } else {
                    APP_LOG.info("Web driver sucessfully initialized");
                    d = this.getWebDriver();
                    setRemoteWebDriver((RemoteWebDriver) d);
                    getDriver();
                    if ("JenkinsSauceLab".equalsIgnoreCase(runOnMachine)
                            || "SauceLab".equalsIgnoreCase(runOnMachine)) {
                        this.objInitializeWebDriver = new InitializeWebDriver();
                        this.objInitializeWebDriver.sauceLabSessionID();
                        this.objInitializeWebDriver.printSessionId();
                        jobID = ((RemoteWebDriver) this.getRemoteDriver())
                                .getSessionId().toString();
                    }
                }
            }
            this.returnDriver().manage().deleteAllCookies();
            if (testCaseName.contains("GTM")) {
                this.returnDriver().get(
                        "https://www.googletagmanager.com/start_preview/gtm?uiv2&id=GTM-W5BMGF7&gtm_auth=8ZLzO9Ia3PxV4HVd9LltIg&gtm_preview=env-7&gtm_debug=x&url=https://loginui-qa.gl-poc.com/#/");
            }
            this.returnDriver().get(configurationsXlsMap.get("ConsoleUrl"));
            APP_LOG.info("URL sucessfully launched in browser");
            TimeUnit.SECONDS.sleep(10);
            logResultInReport(
                    Constants.PASS
                            + ": Successfully Closed-Reopened Browser and navigated to GLP console URL.",
                    "Close-Reopen Browser and navigate to GLP console URL",
                    this.reportTestObj);
            return this.returnDriver();

        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occurred while closing-Reopening Browser and navigate to GLP console URL :"
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Failed to Close-Reopen Browser and navigate to GLP console URL :"
                            + e.getMessage(),
                    "Close-Reopen Browser and navigate to GLP console URL",
                    this.reportTestObj);
            return null;

        }

    }

    /**
     * @author saurabh.sharma5
     * @date 10 May,2018
     * @description Verify whether the original text of a practice question
     *              matches the updated one
     */
    public void verifyPracticeQuestionContentIsUpdated(String originalText,
            String updatedText, boolean shouldMatch) {
        try {
            // Check whether any of the content variable is empty
            if (originalText.isEmpty() || updatedText.isEmpty()) {
                APP_LOG.info(
                        "The original/updated text of the practice question is empty.");
                logResultInReport(
                        Constants.FAIL
                                + ": The original/updated text of the practice question is empty.",
                        "Verify whether the original text of the practice question matches with the updated text.",
                        this.reportTestObj);
                return;
            }
            // Check whether user wants the text to match or not
            if (shouldMatch) {
                if (originalText.equalsIgnoreCase(updatedText)) {
                    APP_LOG.info(
                            "The original text matches the updated text of the practice question.");
                    logResultInReport(
                            Constants.PASS
                                    + ": The original text matches the updated text of the practice question",
                            "Verify whether the original text of practice question matches with the updated text.",
                            this.reportTestObj);

                } else {
                    APP_LOG.info(
                            "The original text does not matches the updated text of the practice question.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": The original text does not matches the updated text of practice question.",
                            "Verify whether the original text of practice question matches with the updated text.",
                            this.reportTestObj);
                }
            } else {
                if (originalText.equalsIgnoreCase(updatedText)) {
                    APP_LOG.info(
                            "The original text matches the updated text of practice question.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": The original text matches the updated text of the practice question.",
                            "Verify whether the original text of the practice question matches with the updated text.",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(
                            "The original text does not matches the updated text of the practice question.");
                    logResultInReport(
                            Constants.PASS
                                    + ": The original text does not matches the updated text of the practice question.",
                            "Verify whether the original text of the practice question matches with the updated text.",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while comparing the text of the practice question.");
        }
    }

    /**
     * @author ratnesh.singh
     * @date 16 May,2018
     * @description Close practice and apply as you go pop up in-case getting
     *              displayed
     * @return N/A
     */

    public void closePracticeAndApplyPopup() {
        if (verifyElementPresentWithOutLog("StartPracticeTestPopupGotItButton")
                .contains(Constants.PASS)) {

            clickOnElement("StartPracticeTestPopupGotItButton",
                    "Click on Start Practice Test Pop Up Got It button");
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 10 May,2018
     * @description Returns map having set questions which are not disabled for
     *              specified part in Help Me Solve This Learning Aid in
     *              Practice Test
     */
    public HashMap returnSetOfQuestionTypeForInHMSTLA() {
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
                                    "input[id*='_0'][type='radio']:not([disabled])"));
                    if (mcqSaQuestions.size() > 0) {
                        availableQuestionTypes.put("McqSa", mcqSaQuestions);
                    }
                } else if (OverallQuestionTypes.get(i)
                        .contains("fib-dropdown")) {
                    List<WebElement> fibDropdownQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "[id^=FIB] .fib-dropdown button:not([disabled])"));
                    if (fibDropdownQuestions.size() > 0) {
                        availableQuestionTypes.put("FIBDropDown",
                                fibDropdownQuestions);
                    }
                } else if (OverallQuestionTypes.get(i).contains("mcqma")) {
                    List<WebElement> mcqMaQuestions = returnDriver()
                            .findElements(By.cssSelector(
                                    "div[id^='McqMa']:not([disabled])"));
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
     * @author yogesh.choudhary
     * @date 10 May,2018
     * @description Navigate a New Learner to a specified Activity type in HMST
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToQuestionTypeOnHMST(String activityType) {

        findElement.checkPageIsReady();
        int numberOfPartsToAttempt = 0;
        int noOftriesLeft = 0;
        String strText = "";
        APP_LOGS.debug("Navigating to '" + activityType
                + "' type question in HMST...");

        try {
            for (int i = 1; i <= 10; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    // Get the text of the displayed button
                    strText = getText("LearningAidsContinuePartButton");
                    // Verify if non-interactive part is displayed
                    if (strText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsContinuePartButton",
                                "Click on Continue button on the displayed LA part.");
                    }
                } else {

                    // Verification of fibDropDownTable activity type
                    if (activityType.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("fibDropDownTableActivity"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestFibDropDownTable")
                                        .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of fibFreeResponseTable activity type
                    else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty(
                                    "fibFreeResponseTableActivity"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestFibFreeResponseTable")
                                        .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of fibDropDown activity type
                    else if (activityType
                            .equalsIgnoreCase(ResourceConfigurations
                                    .getProperty("fibDropDown"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is 'FIB_DropDown' type");
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestFibDropDown")
                                        .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of fibFreeResponse activity type
                    else if (activityType
                            .equalsIgnoreCase(ResourceConfigurations
                                    .getProperty("fibFreeResponse"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is 'FIB_FreeResponse' type");
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestFibFreeResponse")
                                        .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of multipart activity type
                    else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("multipart"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is Multipart type");
                        if (verifyElementPresentWithOutLog(
                                "PracticeTestMultipart")
                                        .contains(Constants.PASS)
                                && getAttribute("PracticeTestMultipart", "id")
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of mcqSa activity type
                    else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("mcqSa"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is MCQSA type");
                        if (verifyElementPresentWithOutLog("PracticeTestMCQSA")
                                .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
                        }
                    }

                    // Verification of mcqMa activity type
                    else if (activityType.equalsIgnoreCase(
                            ResourceConfigurations.getProperty("mcqMa"))) {
                        APP_LOGS.debug(
                                "Verified --> Activity type requested is MCQMA type");
                        if (verifyElementPresentWithOutLog("PracticeTestMCQMA")
                                .contains(Constants.PASS)) {
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
                            logResultInReport(Constants.FAIL
                                    + ": Exception while navigating to '"
                                    + activityType + "' type question : ",
                                    "Verify that user has navigated to '"
                                            + activityType + "' type question.",
                                    this.reportTestObj);
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
     * @description attemptUIRenderedQuestionmethod will attempt question and
     *              according to attempt type will click on
     *              Submit/iDon'tKnowThis button
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa/Number
     *            Line
     * @param attemptType
     *            --> SubmitButton/DiagnosticIdontKnowThisButton
     */
    public void selectAnswerForUIRenderedQuestion(HashMap activityType,
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
                } else if ((activityType.containsKey("FIBDropDown"))
                        && (bFibDropDown == false)) {
                    bFibDropDown = true;
                    List<WebElement> fibDropdowns = (List<WebElement>) activityType
                            .get("FIBDropDown");

                    for (int dropdownCounter = 0; dropdownCounter < fibDropdowns
                            .size(); dropdownCounter++) {

                        // Click on the Dropdown to open it
                        fibDropdowns.get(dropdownCounter).click();
                        // Click the first option in the dropdown menu
                        clickOnElementAndHandleStaleException(
                                "PracticeTestDropdownOptionOne",
                                "Select first option from the dropdown.");
                        // }
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
                    "Verify that user has completed diagnostic test.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 3 May,2018
     * @description Select active part of Help Me Solve This Learning Aid under
     *              Practice Test
     */
    public void selectInteractivePartAnswerinHMSTLA(int part) {

        // FindElement findElement = new FindElement();
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;

        try {

            // Get set of active question types for specified part in HMST
            // LA

            availableQuestionTypes = returnSetOfQuestionTypeForInHMSTLA();

            // Verify that available question types in specified part are
            // known
            // activity.
            if (availableQuestionTypes.size() == 0) {
                logResultInReport(
                        Constants.FAIL
                                + ": No known activity type available under part :"
                                + part,
                        "Verify any known activity type is displayed for the Learning Aid",
                        this.reportTestObj);
            }
            // Attempt the questions available in specified part.
            else {

                selectAnswerForUIRenderedQuestion(availableQuestionTypes,
                        ResourceConfigurations
                                .getProperty("diagnosticSubmitButton"));
                findElement.checkPageIsReady();

            }

        } catch (Exception e) {
            APP_LOG.info("Exception occurred while attempting Learning Aids");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Learning Aids",
                    "Attempt the displayed part of the Learning Aid",
                    this.reportTestObj);
        }

    }

    /**
     * @author yogesh.choudhary
     * @date 19 Apr,2018
     * @description Attempt the Student Initiated Learning Aids parts depending
     *              on user input
     */
    public void selectStudentInitiatedLA(int startIndex, int attemptCount) {
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;
        int numberOfPartsToAttempt = 0;
        int noOftriesLeft = 0;
        String strText = "";

        try {
            for (int i = startIndex; i <= attemptCount; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Continue/Submit button is not displayed for the Learning Aid parts",
                            "Verify Continue/Submit button is displayed for the Learning Aid part",
                            this.reportTestObj);
                    return;
                } else {
                    // Get the text of the displayed button
                    strText = getText("LearningAidsSubmitPartButton");
                    // Verify if non-interactive part is displayed
                    if (strText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsSubmitPartButton",
                                "Click on Continue button on the displayed LA part.");
                    } else {
                        // Get the displayed question type
                        availableQuestionTypes = returnSetOfQuestionForSpecifiedPartInHMSTLA(
                                i);
                        if ((availableQuestionTypes.size() == 0)
                                && verifyElementPresentWithOutLog(
                                        "PracticeTestSummarypagePracticeAgainButton")
                                                .contains(Constants.PASS)) {
                        } else if (availableQuestionTypes.size() == 0) {
                            logResultInReport(
                                    Constants.FAIL
                                            + ": No known activity type available on page",
                                    "Verify any known activity type is displayed for the Learning Aid",
                                    this.reportTestObj);
                        } else {
                            // Get total number of attempts/tries left for
                            // question.
                            noOftriesLeft = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                                    i);
                            int attemptCnt = 1;
                            for (int j = noOftriesLeft; j >= 1; j--) {
                                logResultInReport(
                                        Constants.PASS
                                                + " Attempting Help Me Solve This Learning Aid part: "
                                                + i + " for " + attemptCnt
                                                + " attempt.",
                                        "Verify the attempt made on Help Me Solve This Learning Aid question.",
                                        this.reportTestObj);

                                attemptUIRenderedQuestion(
                                        availableQuestionTypes,
                                        ResourceConfigurations.getProperty(
                                                "diagnosticSubmitButton"));
                                findElement.checkPageIsReady();

                                // Verify whether the correct feedback is
                                // displayed and moving to next part
                                WebElement correctfeedbackText = returnDriver()
                                        .findElement(By.cssSelector(
                                                "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                                        + i + ") div[class='success d-flex align-items-center mb-2']"));

                                if (correctfeedbackText.isDisplayed()
                                        && j > 1) {
                                    break;
                                }
                                attemptCnt = attemptCnt + 1;
                            }

                        }
                    }
                }

                if (i == numberOfPartsToAttempt
                        && verifyElementPresentWithOutLog(
                                "PracticeTestHMSTLearningAidsCloseButton")
                                        .contains(Constants.PASS)) {
                    clickOnElement("PracticeTestHMSTLearningAidsCloseButton",
                            "Click on close button displayed at end of HMST Learning Aid.");
                }

            }

        } catch (Exception e) {
            APP_LOG.info("Exception occurred while attempting Learning Aids");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Learning Aids",
                    "Attempt the displayed part of the Learning Aid",
                    this.reportTestObj);
        }

    }

    /*
     * @author yogesh.hcoudhary
     * 
     * @date 10 May,2018
     * 
     * @description Navigate to HMST full screen
     * 
     * @return object GLPLearner_DiagnosticTestPage
     */

    public void verifyNumberOfTriesforInteractive() {
        APP_LOG.info("Verify number of tries on Interactive card");
        int noOftriesLeft = 0;
        String strText = "";
        try {
            for (int i = 1; i <= 10; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    // Get the text of the displayed button
                    strText = getText("LearningAidsContinuePartButton");
                    // Verify if non-interactive part is displayed
                    if (strText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsContinuePartButton",
                                "Click on Continue button on the displayed LA part.");
                    }
                } else {
                    APP_LOG.info("Verify number of tries on Interactive card");
                    verifyElementPresent("LearningAidsTriesLeft",
                            "Verify number of tried on Interactive part");
                }

            }
        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL
                            + " : Exception occurred while searching for Learning Aids.",
                    "Verify Learnig Aids is visible for the displayed question.",
                    this.reportTestObj);
        }
    }

    /*
     * @author yogesh.hcoudhary
     * 
     * @date 10 May,2018
     * 
     * @description Navigate to HMST full screen
     * 
     * @return object GLPLearner_DiagnosticTestPage
     */
    public void attempPracticeTestTillHMST() {
        boolean isLearningAidsVisible = false;
        String strStepResult = "PASS";
        int count = 1;

        try {
            while (!isLearningAidsVisible && strStepResult.contains("PASS")) {
                // Verify Help Me Answer This drop down is displayed in the
                // header
                String strResult = verifyElementPresentWithOutLog("HMSTHeader");
                if (strResult.contains("PASS")) {
                    isLearningAidsVisible = true;
                    break;
                } else {
                    APP_LOG.info("Question number " + count
                            + " does not has Learning Aids.");
                    attemptPracticeTest(0, 1, ResourceConfigurations
                            .getProperty("diagnosticSubmitButton"));
                }
                // Verify the Assessment player is still displayed
                strStepResult = verifyElementIsVisible(
                        "PracticeTestCloseButton",
                        "Verify Practice Assessment player is displayed to the learner");
                // Increasing the question count
                count = count + 1;
            }
            if (isLearningAidsVisible) {
                APP_LOG.info(
                        "Learning Aids drop down is successfully displayed to the learner for the "
                                + count + " question.");
                logResultInReport(
                        Constants.PASS
                                + " Learning Aids drop down is successfully displayed to the learner for the "
                                + count + " question.",
                        "Verify Learnig Aids is visible for the displayed question.",
                        this.reportTestObj);
            } else {
                // Report and exit if no question of the practice test has LA
                // associated
                APP_LOG.info(
                        "Learning Aids drop down is not displayed to the learner even after "
                                + count + " questions.");
                logResultInReport(
                        Constants.FAIL
                                + "Learning Aids drop down is not displayed to the learner even after "
                                + count + " questions.",
                        "Verify Learnig Aids is visible for the displayed question.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL
                            + " : Exception occurred while searching for Learning Aids.",
                    "Verify Learnig Aids is visible for the displayed question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 10 May,2018
     * @description Validate Number of tries is equal to number of choices-1
     */
    public void validateNumberOfTriesCount(String tries, String element) {
        APP_LOGS.debug("Verified The tries count");
        int noOftriesLeft = getNumberOfTriesLeftParticularQuestionInPracticeTest(
                1, tries);

        List<WebElement> list = findElement.getElements(element);
        int choiceCount = list.size();

        if (noOftriesLeft == choiceCount - 1) {
            logResultInReport(
                    Constants.PASS
                            + " : Verify getting the number of tries count.",
                    "Verify Learnig Aids number of tries count.",
                    this.reportTestObj);
        } else {
            logResultInReport(
                    Constants.FAIL
                            + " : Exception in comapring  number of tries count.",
                    "Verify Learnig Aids number of tries count is not correct.",
                    this.reportTestObj);
        }

    }

    /**
     * @author yogesh.choudhary
     * @date 10 May,2018
     * @description Navigate to a particular practice question having Learning
     *              Aid drop down
     */
    public void navigateToQuestionTypeWithLearningAids(String activityType,
            String attemptType) {
        APP_LOGS.debug(
                "Verified --> Navigate to particular question type in LA ");
        boolean isLearningAidsVisible = false;
        String strStepResult = "PASS";
        int count = 1;

        try {
            // List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            // CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);
            int progressStartIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemin"));
            int progressMaxIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemax"));

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                if (getTextWithoutLog(
                        "PracticeTestSummarypagePracticeAgainButton")
                                .contains(("Practice again"))) {
                    logResultInReport(
                            Constants.FAIL + ": " + activityType
                                    + " type questions is not available",
                            "Verify that user has navigated to '" + activityType
                                    + "' type question.",
                            this.reportTestObj);
                    break;
                }
                // Creating a list of iDontKnowThisButton after checking the
                // first visible activity type and clicking them acc.
                if (i > 0) {
                    APP_LOGS.debug(
                            "Verifying -->Presence of 'I don't know this' button and adding locators to a list");

                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestion1();

                    attemptUIRenderedQuestion(availableQuestionTypes,
                            attemptType, true);
                    verifyAndAttamptIfRetryRequired();
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");

                        String strResult = verifyElementIsVisible(
                                "HelpMeAnswerThisDropdown",
                                "Verify Help Me Answer This dropdown is displayed to the learner");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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
                // Verification of fibFreeResponseTable activity type
                else if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibFreeResponseTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_FreeResponse_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' Table type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        String strResult = verifyElementIsVisible(
                                "HelpMeAnswerThisDropdown",
                                "Verify Help Me Answer This dropdown is displayed to the learner");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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

                // Verification of fibDropDown activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("fibDropDown"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown' type");
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDown").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibDropDown' type question, absence of Multipart type question and 'dropdown' in class attribute of element");
                        String strResult = verifyElementPresentWithOutLog(
                                "HelpMeAnswerThisDropdown");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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
                            "PracticeTestFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'fibFreeResponse' type question, absence of Multipart type question and 'freeResponse' in class attribute of element");
                        String strResult = verifyElementIsVisible(
                                "HelpMeAnswerThisDropdown",
                                "Verify Help Me Answer This dropdown is displayed to the learner");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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

                // Verification of multipart activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("multipart"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is Multipart type");
                    if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("PracticeTestMultipart", "id")
                                    .toLowerCase()
                                    .contains(activityType.toLowerCase())) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'multiPart' type question and 'multipart' in id attribute of element");
                        String strResult = verifyElementIsVisible(
                                "HelpMeAnswerThisDropdown",
                                "Verify Help Me Answer This dropdown is displayed to the learner");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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

                // Verification of mcqSa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqSa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQSA type");
                    if (verifyElementPresentWithOutLog("PracticeTestMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqSa' type question, absence of Multipart type question and 'McqSa' in id attribute of element");
                        String strResult = verifyElementPresentWithOutLog(
                                "HelpMeAnswerThisDropdown");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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

                // Verification of mcqMa activity type
                else if (activityType.equalsIgnoreCase(
                        ResourceConfigurations.getProperty("mcqMa"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is MCQMA type");
                    if (verifyElementPresentWithOutLog("PracticeTestMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.FAIL)) {
                        APP_LOGS.debug(
                                "Verified --> Presence of 'McqMa' type question, absence of Multipart type question and 'McqMa' in id attribute of element");
                        String strResult = verifyElementIsVisible(
                                "HelpMeAnswerThisDropdown",
                                "Verify Help Me Answer This dropdown is displayed to the learner");
                        if (strResult.contains("PASS")) {
                            isLearningAidsVisible = true;
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

            }
        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL
                            + " : Exception occurred while searching for Learning Aids.",
                    "Verify Learnig Aids is visible for the displayed question.",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 10 May,2018
     * @description Navigate a New Learner to a specified Activity type in HMST
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToQuestionOnHMST() {

        findElement.checkPageIsReady();
        int numberOfPartsToAttempt = 0;
        int noOftriesLeft = 0;
        String strText = "";
        APP_LOGS.debug("Navigating to question in HMST...");

        try {
            for (int i = 1; i <= 10; i++) {
                // Verify if non-interactive part is displayed
                String strStepResult = verifyElementPresentWithOutLog(
                        "LearningAidsSubmitPartButton");
                if (strStepResult.contains("FAIL")) {
                    APP_LOG.info(
                            "Continue/Submit button is not displayed for the Learning Aid parts");
                    // Get the text of the displayed button
                    strText = getText("LearningAidsContinuePartButton");
                    // Verify if non-interactive part is displayed
                    if (strText.equalsIgnoreCase(ResourceConfigurations
                            .getProperty("courseMaterialContinueButton"))) {
                        // Click on Continue button for non-interactive part
                        clickOnElement("LearningAidsContinuePartButton",
                                "Click on Continue button on the displayed LA part.");
                    }
                } else {
                    logResultInReport(
                            Constants.PASS + ": Navigated to uestion : ",
                            "Verify that user has navigated to question.",
                            this.reportTestObj);
                    break;
                }
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Error while navigating to the specified question type because : "
                            + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while navigating to question "
                            + e.getMessage(),
                    "Verify that user has navigated question",
                    this.reportTestObj);
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 16 May,2018
     * @description Navigate a New Learner to a specified Activity type with
     *              MultiPart in Practice Test
     * @param activityType
     *            -> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     */

    public void navigateToQuestionTypeWithMultiPartOnPracticeTest(
            String activityType, String attemptType) {
        APP_LOGS.debug("Navigating to '" + activityType
                + "' type question in Practice Test...");

        try {
            // List<WebElement> iDontKnowThisButtons = new ArrayList<>();
            // CommonUtil x = new CommonUtil(reportTestObj, APP_LOGS);
            int progressStartIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemin"));
            int progressMaxIndex = Integer.parseInt(
                    getAttribute("PracticeTestProgressBar", "aria-valuemax"));

            for (int i = progressStartIndex; i <= progressMaxIndex; i++) {

                // if (getText("PracticeTestSummarypagePracticeAgainButton")
                // .contains("Practice again")) {
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

                    HashMap<String, List> availableQuestionTypes = returnSetOfQuestion1();

                    attemptUIRenderedQuestion(availableQuestionTypes,
                            attemptType);
                    verifyAndAttamptIfRetryRequired();
                }

                // Verification of fibDropDownTable activity type
                if (activityType.equalsIgnoreCase(ResourceConfigurations
                        .getProperty("fibDropDownTableActivity"))) {
                    APP_LOGS.debug(
                            "Verified --> Activity type requested is 'FIB_DropDown_Table' type");
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDownTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
                            "PracticeTestFibFreeResponseTable")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestFibDropDown").contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
                            "PracticeTestFibFreeResponse")
                                    .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
                    if (verifyElementPresentWithOutLog("PracticeTestMultipart")
                            .contains(Constants.PASS)
                            && getAttribute("PracticeTestMultipart", "id")
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
                    if (verifyElementPresentWithOutLog("PracticeTestMCQSA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
                    if (verifyElementPresentWithOutLog("PracticeTestMCQMA")
                            .contains(Constants.PASS)
                            && verifyElementPresentWithOutLog(
                                    "PracticeTestMultipart")
                                            .contains(Constants.PASS)) {
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
     * @author ratnesh.singh
     * @date 3 May,2018
     * @description Attempt active part of Help Me Solve This Learning Aid under
     *              Practice Test
     */
    public void attemptInteractivePartinHMSTLA(int part,
            boolean... toAttemptAllTries) {

        // FindElement findElement = new FindElement();
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;
        boolean toAttemptAllTriesFlag = toAttemptAllTries.length > 0
                ? toAttemptAllTries[0] : false;
        boolean toAttemptWithoutAnswer = toAttemptAllTries.length > 1
                ? toAttemptAllTries[1] : false;

        try {

            // Get set of active question types for specified part in HMST
            // LA

            availableQuestionTypes = returnSetOfQuestionForSpecifiedPartInHMSTLA(
                    part);

            // Verify if Practice Test Summary Page is displayed
            if ((availableQuestionTypes.size() == 0)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestSummarypagePracticeAgainButton")
                                    .contains(Constants.PASS)) {
            }
            // Verify that available question types in specified part are
            // known
            // activity.
            else if (availableQuestionTypes.size() == 0) {
                logResultInReport(
                        Constants.FAIL
                                + ": No known activity type available under part :"
                                + part,
                        "Verify any known activity type is displayed for the Learning Aid",
                        this.reportTestObj);
            }
            // Attempt the questions available in specified part.
            else {

                if (toAttemptWithoutAnswer == true) {
                    attemptUIRenderedQuestion(
                            availableQuestionTypes, ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"),
                            true);
                    findElement.checkPageIsReady();

                    if (toAttemptAllTriesFlag == true) {
                        int noOftriesLeft = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                                part);
                        for (int j = noOftriesLeft; j >= 1; j--) {
                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    ResourceConfigurations.getProperty(
                                            "diagnosticSubmitButton"),
                                    true);
                            findElement.checkPageIsReady();

                        }
                    }

                } else {

                    attemptUIRenderedQuestion(availableQuestionTypes,
                            ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"));
                    findElement.checkPageIsReady();

                    if (toAttemptAllTriesFlag == true) {
                        int noOftriesLeft = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                                part);
                        for (int j = noOftriesLeft; j >= 1; j--) {
                            attemptUIRenderedQuestion(availableQuestionTypes,
                                    ResourceConfigurations.getProperty(
                                            "diagnosticSubmitButton"));
                            findElement.checkPageIsReady();

                        }
                    }
                }
                logResultInReport(
                        Constants.PASS
                                + ": Successfully attempted interactive part :"
                                + part + " of Learning Aids",
                        "Attempt the interactive part of the Learning Aids.",
                        this.reportTestObj);

            }

        } catch (Exception e) {
            APP_LOG.info("Exception occurred while attempting Learning Aids");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Learning Aids",
                    "Attempt the interactive part of the Learning Aids.",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 18 May,2018
     * @description Click on Practice Test Tool Tip Got it button
     * @return N/A
     */
    public GLPLearner_DiagnosticTestPage clickOnPracticeGotItButtonIfPresent() {
        APP_LOGS.debug("Click on Practice Test Tool Tip Got it button");

        if (verifyElementPresentWithOutLog("PracticeGotItButton")
                .contains(Constants.PASS)) {

            clickOnElement("PracticeGotItButton",
                    "Click on Practice Test Tool Tip Got it button");

        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author saurabh.sharma5
     * @date 17 May,2018
     * @description Navigate to the desired practice test of a module's section
     */
    public void navigateToDesiredPracticeTest(String learningObjectiveName,
            String moduleName, String moduleSectionLocator,
            String practiceQuizLocator) {
        GLPLearner_CourseMaterialPage objGLPLearnerCourseMaterialPage = new GLPLearner_CourseMaterialPage(
                reportTestObj, APP_LOG);
        try {
            // Verify page has loaded correctly
            FindElement.waitForPageToLoad();

            // Click on go to course home link on diagnostic result page
            if (objGLPLearnerCourseMaterialPage
                    .verifyElementPresentWithOutLog(
                            "DiagnosticGoToCourseHomeLink")
                    .contains(Constants.PASS)) {
                objGLPLearnerCourseMaterialPage.clickOnElement(
                        "DiagnosticGoToCourseHomeLink",
                        "Click on Go To Course Home Link to navigate to course material page");
            }
            // Check whether the any module is already expanded
            String strResult = objGLPLearnerCourseMaterialPage
                    .verifyElementPresentWithOutLog("ExpandedModuleBlock");
            if (strResult.contains(Constants.FAIL)
                    || !(objGLPLearnerCourseMaterialPage
                            .getText("ExpandedModuleBlock")
                            .contains(moduleName))) {
                // Expand the desired Learning Objective
                objGLPLearnerCourseMaterialPage.clickOnElementContainsInnerText(
                        "CourseMaterialModuleTitleButton",
                        learningObjectiveName);
            }
            // Click on the desired Module of the LO
            objGLPLearnerCourseMaterialPage.clickOnElementContainsLabel(
                    "CourseMaterialExpandedSubModuleStartButtons", moduleName);
            // Verify that page is loaded
            FindElement.waitForPageToLoad();
            // Verify that Practice and apply as you go pop up gets displayed
            // and click on Got it
            closePracticeAndApplyPopup();

            // Expand the desired section of the module
            if (verifyElementPresentWithOutLog(practiceQuizLocator)
                    .contains(Constants.FAIL)) {
                clickOnElement(moduleSectionLocator,
                        "Expand the desired section of the module");
            }
            // Click on the Practice Quiz of the section
            clickOnElement(practiceQuizLocator,
                    "Click on practice quiz of the desired section.");
            // Click on start button on practice test welcome screen
            clickOnElement("PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");
            // Verify whether the first question of practice test is
            // displayed
            verifyElementIsVisible("PracticeTestCloseButton",
                    "Verify Practice Assessment player is displayed to the learner");
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while navigating to the desired practice test.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while navigating to the desired practice test.",
                    "Navigate to the desired practice test",
                    this.reportTestObj);
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 17 May,2018
     * @description Verify the text is displayed in the browser selected
     *              language
     */
    public void verifyLocalizedText(String actualText, String displayedText,
            String objectName) {
        try {
            // Check whether any of the content variable is empty
            if (actualText.isEmpty() || displayedText.isEmpty()) {
                APP_LOG.info(
                        "The actual or displayed text is empty. Cannot compare the strings.");
                logResultInReport(
                        Constants.FAIL
                                + ": The actual or displayed text is empty. Cannot compare the strings.",
                        "Verify the displayed localized text of " + objectName
                                + " matches the expected text.",
                        this.reportTestObj);
                return;
            }

            if (actualText.equalsIgnoreCase(displayedText)) {
                APP_LOG.info("The actual localized text of " + objectName
                        + " matches the expected text.");
                logResultInReport(
                        Constants.PASS + ": The actual localized text of "
                                + objectName + " matches the expected text.",
                        "Verify the displayed localized text of " + objectName
                                + " matches the expected text.",
                        this.reportTestObj);
            } else {
                APP_LOG.info("The actual localized text of " + objectName
                        + " does not matches the expected text.");
                logResultInReport(
                        Constants.FAIL + ": The actual localized text of "
                                + objectName
                                + " does not matches the expected text.",
                        "Verify the displayed localized text of " + objectName
                                + " matches the expected text.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while comparing the localized text of "
                            + objectName);
            logResultInReport(
                    Constants.FAIL
                            + " Exception occurred while comparing the localized text of "
                            + objectName,
                    "Verify the displayed localized text of " + objectName
                            + " matches the expected text.",
                    this.reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @date 19 May,2018
     * @description Returns DB state of required attributes for specified part
     *              in multi-part question / HMST / HMST as Assessment under
     *              Practice Test
     */

    public Map<String, String>
           getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(int part,
                   String query, String attributesRequired,
                   Set<String> lAIdsForMultipart) {
        APP_LOG.debug(
                "Fetch DB state of required attributes for specified part in multi-part question under Practice Test.");
        int partCounter = 1;
        String[] attributes = null;
        Map<String, String> mapWithDBStateOfRequiredAttributes = new HashMap<String, String>();
        CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);

        try {

            // Building DataMap with required attributes to gather DB state
            // for specified part of Multipart Question
            attributes = attributesRequired.split("\\|");

            for (String attribute : attributes) {

                mapWithDBStateOfRequiredAttributes.put(attribute, null);

            }

            Iterator<String> iT = lAIdsForMultipart.iterator();

            // Fetch saved DB state from CouchBase into DataMaps created
            while (iT.hasNext()) {

                String lAID = iT.next();
                if (partCounter == part) {
                    mapWithDBStateOfRequiredAttributes = cb
                            .fetchCouchbaseDBStateSavedForMultipart(query,
                                    "results", lAID, 0,
                                    mapWithDBStateOfRequiredAttributes);
                }
                partCounter = partCounter + 1;

            }
            logResultInReport(
                    Constants.PASS + ": DB State for part :" + part
                            + " is fetched successfully",
                    "Fetch DB state of required attributes for specified part in multi-part question under Practice Test.",
                    this.reportTestObj);

            return mapWithDBStateOfRequiredAttributes;

        } catch (Exception e) {

            APP_LOG.error(
                    "Exception occurred while fetching DB State for part :"
                            + part + " as " + e.getMessage());

            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while fetching DB State for part :"
                            + part + " as " + e.getMessage(),
                    "Fetch DB state of required attributes for specified part in multi-part question under Practice Test.",
                    this.reportTestObj);

            return null;
        }

    }

    /**
     * @author ratnesh.singh
     * @date 19 May,2018
     * @description Compares corresponding Saved DB or Attempted UI states for
     *              specified part in multipart question before and after
     *              performing any operations.
     */

    public void compareSavedDBOrAttemptedUIStates(int part, String Type,
            String operation, Map<String, String> beforeState,
            Map<String, String> afterState) {
        APP_LOG.debug(
                "Compare Saved DB or Attempted UI states for specified part in multipart question");

        try {
            if (beforeState.equals(afterState)) {

                logResultInReport(
                        Constants.PASS
                                + ": Multipart Question is displayed with same "
                                + Type + " for part :" + part,
                        "Verify that " + Type + " of part :" + part
                                + " of Multipart Question is sustained after :"
                                + operation,
                        this.reportTestObj);

            }

            else {

                logResultInReport(
                        Constants.FAIL
                                + ": Multipart Question is not displayed with same "
                                + Type + " for part :" + part,
                        "Verify that " + Type + " of part :" + part
                                + " of Multipart Question is sustained after :"
                                + operation,
                        this.reportTestObj);

            }

        } catch (Exception e) {
            APP_LOG.error("Exception occurred while comparing " + Type
                    + " for part :" + part + " as " + e.getMessage());
            logResultInReport(
                    Constants.FAIL + ": Exception occurred while comparing "
                            + Type + " state for part :" + part + " as "
                            + e.getMessage(),
                    "Verify that " + Type + " of part :" + part
                            + " of Multipart Question is sustained after :"
                            + operation,
                    this.reportTestObj);

        }
    }

    /**
     * @author ratnesh.singh
     * @date 24 May,2018
     * @description Returns CouchBase Query to fetch DB state.
     */

    public String getDBQuery(String username, String password) {
        APP_LOG.debug("Entering Func: getDBQuery");
        String glpCourseId = null;
        String glpLearnerId = null;
        String glpSubscriptionId = null;
        try {
            GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj,
                    APP_LOG);

            // Fetching GLP course id of the course subscribed by learner
            glpCourseId = objRestUtil.getGlpCourseId(username, password,
                    objRestUtil.getCreatedCourseSectionId(username, password));

            // Fetching GLP learner id
            glpLearnerId = objRestUtil.getGLPId(username, password);

            // Fetching GLP subscription id
            glpSubscriptionId = objRestUtil.getSubscriptionId(username,
                    password);

            // Building CouchBase query to fire on DB
            String query = "select * from led where id='" + glpCourseId
                    + glpLearnerId + glpSubscriptionId
                    + "learnercourseoutcomes'";
            return query;
        } catch (Exception e) {
            APP_LOG.debug("Exception occurred while building DB query as :"
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while building DB query as :"
                            + e.getMessage(),
                    "Get CouchBase Query to get DB state.", this.reportTestObj);
            return null;

        }

    }

    /**
     * @author ratnesh.singh
     * @date 25 May,2018
     * @description Validates DB state of parameters like 'isCorrectAnswer' for
     *              HMST / 'status','isCorrectAnswer','Score' for HMST as
     *              Assessment part with negative feedback in Practice Test.
     */
    public void
           validateCouchBaseDBStateForHMSTOrHMSTAssessmentNegativeFeedbackPart(
                   int part, Map<String, String> dataMapafterCompletingPart,
                   boolean... isHMSTAssessment) {

        boolean isHMSTAssessmentFlag = isHMSTAssessment.length > 0
                ? isHMSTAssessment[0] : false;
        if (isHMSTAssessmentFlag == true) {
            if (Double.parseDouble(
                    dataMapafterCompletingPart.get("score")) == 0) {

                logResultInReport(
                        Constants.PASS + ": 'Score' set in DB for part :" + part
                                + " is 0 for Incorrect Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " with negative feedabck under HMST Assessment in Practice Test.",
                        this.reportTestObj);

            } else {

                logResultInReport(
                        Constants.FAIL + ": 'Score' set in DB for part :" + part
                                + " is not 0 for Incorrect Attempt.",
                        "Verify the 'score' set in DB for part :" + part
                                + " with negative feedabck under HMST Assessment in Practice Test.",
                        this.reportTestObj);
            }

        }
        if (dataMapafterCompletingPart.get("isCorrectAnswer")
                .contains("false")) {

            logResultInReport(
                    Constants.PASS + ": 'isCorrectAnswer' set in DB for part :"
                            + part + " is 'false' for Incorrect Attempt.",
                    "Verify the 'isCorrectAnswer' set in DB for part :" + part
                            + " with negative feedabck under HMST / HMST Assessment in Practice Test.",
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'IsCorrectAnswer' flag set in DB for part :"
                            + part + " is not 'false'.",
                    "Verify the 'isCorrectAnswer' set in DB for part :" + part
                            + " with negative feedabck under HMST / HMST Assessment in Practice Test.",
                    this.reportTestObj);

        }
        if (dataMapafterCompletingPart.get("status").contains("Completed")) {

            logResultInReport(
                    Constants.PASS + ": 'Status' set in DB for part :" + part
                            + " is Completed.",
                    "Verify the 'status' set in DB for part :" + part
                            + " with negative feedabck under HMST Assessment in Practice Test.",
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL + ": 'Status' set in DB for part :" + part
                            + " is not Completed.",
                    "Verify the 'status' set in DB for part :" + part
                            + " with negative feedabck under HMST Assessment in Practice Test.",
                    this.reportTestObj);
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 23 May,2018
     * @description Verify whether feedback is displayed on submitting response
     *              for a single part of Learning Aids
     */
    public HashMap<String, String> verifyFeedbackIsDisplayedForSinglePart(
            int partIndex, boolean... isMultiPart) {

        WebElement feedback;
        String strFeedbackText = "";
        HashMap<String, String> feedBackType = new HashMap<String, String>();
        try {
            boolean isMultiPartDisplayed = isMultiPart.length > 0
                    ? isMultiPart[0] : false;

            findElement.checkPageIsReady();
            // Verify whether any feedback is displayed
            if (!isMultiPartDisplayed) {
                feedback = returnDriver().findElement(By.cssSelector(
                        "div.learning-aids-body div.questionsWrapper:nth-of-type("
                                + partIndex
                                + ") div[class$='display-flex align-items-center mb-10']"));
            } else {
                feedback = returnDriver().findElement(By.cssSelector(
                        "div.multipartPanel div.questionsWrapper:nth-of-type("
                                + partIndex
                                + ") div[class$='display-flex align-items-center mb-10']"));
            }

            if (feedback == null) {
                APP_LOG.info(
                        "No feedback is displayed on submitting response for a single part.");
                logResultInReport(
                        Constants.FAIL
                                + ": No feedback is displayed on submitting response for a single part",
                        "Verify feedback is displayed on submitting response for a single part",
                        this.reportTestObj);
            } else {
                // Get the text of the feedback
                strFeedbackText = feedback.getText();

                // Check if the feedback is positive
                if (feedback.getAttribute("class").contains("success")) {
                    logResultInReport(
                            Constants.PASS
                                    + ": Correct feedback is displayed on submitting the Learning Aid part.",
                            "Verify feedback is displayed on submitting response for a single part",
                            this.reportTestObj);
                    // put the positive value in the output hash map
                    feedBackType.put("positive",
                            strFeedbackText.substring(0, 6));
                }
                // Check if the feedback is negative
                else if (feedback.getAttribute("class").contains("fail")) {
                    logResultInReport(
                            Constants.PASS
                                    + ": Incorrect feedback is displayed on submitting the Learning Aid part.",
                            "Verify feedback is displayed on submitting response for a single part",
                            this.reportTestObj);
                    // put the negative value in the output hash map
                    feedBackType.put("negative", strFeedbackText);
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while verifying the feedback for a single part");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while verifying the feedback for a single part",
                    "Verify feedback is displayed on submitting response for a single part",
                    this.reportTestObj);
        }
        return feedBackType;
    }

    /**
     * @author saurabh.sharma5
     * @date 24 May,2018
     * @description Verify the original count of tries for a single part in HMST
     *              matches the current tries depending on the boolean value
     *              shouldMatch
     */
    public void verifyExpectedNumberOfTriesForHMST(int partIndex,
            int originalTriesCount, boolean shouldMatch) {
        // Get the current number of tries for the active part
        int currentTries = getNumberOfTriesLeftParticularQuestionInHMSTLA(
                partIndex);
        if (!shouldMatch) {
            // Check whether the try count is still the same
            if (currentTries == originalTriesCount) {
                APP_LOG.info("The count of tries remains the same as "
                        + originalTriesCount);
                logResultInReport(
                        Constants.FAIL
                                + ": The count of tries remains the same as "
                                + originalTriesCount,
                        "Verify the count of tries for the active part is as expected.",
                        reportTestObj);
            } else {
                APP_LOG.info(" The count of tries gets changed to "
                        + currentTries + " from the original count of "
                        + originalTriesCount);
                logResultInReport(
                        Constants.PASS + ": The count of tries gets changed to "
                                + currentTries + " from the original count of "
                                + originalTriesCount,
                        "Verify the count of tries for the active part is as expected.",
                        reportTestObj);
            }
        } else {
            // Check whether the try count is still the same
            if (currentTries == originalTriesCount) {
                APP_LOG.info("The count of tries remains the same as "
                        + originalTriesCount);
                logResultInReport(
                        Constants.PASS
                                + ": The count of tries remains the same as "
                                + originalTriesCount,
                        "Verify the count of tries for the active part is as expected.",
                        reportTestObj);
            } else {
                APP_LOG.info(" The count of tries gets changed to "
                        + currentTries + " from the original count of "
                        + originalTriesCount);
                logResultInReport(
                        Constants.FAIL + ": The count of tries gets changed to "
                                + currentTries + " from the original count of "
                                + originalTriesCount,
                        "Verify the count of tries for the active part is as expected.",
                        reportTestObj);
            }
        }
    }

    /**
     * @author ratnesh.singh
     * @date 28 May,2018
     * @description Navigate to Algorithmic or Non-Algorithmic multi-part
     *              Question with Help Me Solve This LA in Practice Test
     */
    public void
           navigateToAlgorithmicOrNonAlgorithmicMultipartinPracticeTestWithHMST(
                   String username, String password, String multipartType) {
        String baseReqURL, completeRequestURL = null;
        String lAIDFirstPart = null;
        String glpCourseId, glpLearnerId, glpSubscriptionId = null;
        String policy = null;
        boolean isAlgorithmicMultipartWithHMST = false;
        boolean endofpracticetest = false;
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);

        try {
            do {
                navigateToMultipartinPracticeTest();
                objRestUtil.clearPerformanceEntries();
                refreshPage();
                findElement.checkPageIsReady();
                verifyElementPresent("PracticeTestMultipart",
                        "Verify that Multipart Question is displayed after refreshing Page.");
                Set<String> lAIdsForMultipart = objRestUtil
                        .getMatchingNetworkEntries(
                                ResourceConfigurations
                                        .getProperty("multipartLAIDPattern"),
                                "MultipartLAIds");
                Set<String> baseRequestURL = objRestUtil
                        .getMatchingNetworkEntries(ResourceConfigurations
                                .getProperty("baseRequestURLPattern"),
                                "requestURL");
                Iterator<String> iTLAID = lAIdsForMultipart.iterator();
                Iterator<String> iTURL = baseRequestURL.iterator();
                lAIDFirstPart = iTLAID.next();
                baseReqURL = iTURL.next();

                glpCourseId = objRestUtil.getGlpCourseId(username, password,
                        objRestUtil.getCreatedCourseSectionId(username,
                                password));

                glpLearnerId = objRestUtil.getGLPId(username, password);

                glpSubscriptionId = objRestUtil.getSubscriptionId(username,
                        password);

                completeRequestURL = baseReqURL + lAIDFirstPart + "?learnerId="
                        + glpLearnerId + "&courseId=" + glpCourseId
                        + "&subscriptionId=" + glpSubscriptionId
                        + "&docType=learnercourse";

                Response response = given()
                        .header("Authorization",
                                objRestUtil.Oauth2(username, password))
                        .get(completeRequestURL);

                if (response.getStatusCode() == 200) {

                    policy = response.then().extract().body().jsonPath()
                            .getString(
                                    "data.extensions.variableResolution[0].policy")
                            .toString();

                    if (policy.equalsIgnoreCase(multipartType)
                            && verifyElementPresentWithOutLog(
                                    "HelpMeAnswerThisDropdown")
                                            .contains(Constants.PASS)) {

                        logResultInReport(Constants.PASS
                                + ": Navigated successfully to " + multipartType
                                + " multipart question type." + multipartType,
                                "Navigate to " + multipartType
                                        + " multipart question type.",
                                reportTestObj);
                        isAlgorithmicMultipartWithHMST = true;

                        break;

                    } else {
                        isAlgorithmicMultipartWithHMST = false;
                        attemptMultipartQuestionInPracticeTest(1,
                                getNumberOfPartsInMultipartQuestion());

                        if (verifyElementPresentWithOutLog("NextButton")
                                .contains(Constants.PASS)) {
                            clickOnElement("NextButton",
                                    "Click on Next Question button");
                            endofpracticetest = false;
                            findElement.checkPageIsReady();
                        }

                        else if (verifyElementPresentWithOutLog(
                                "PracticeTestCompleteSummaryButton")
                                        .contains(Constants.PASS)) {

                            logResultInReport(
                                    Constants.FAIL
                                            + ": Reached to end of practice test and could not find "
                                            + multipartType
                                            + " multipart question type.",
                                    "Navigate to " + multipartType
                                            + " multipart question type.",
                                    reportTestObj);
                            endofpracticetest = true;

                            break;

                        }

                    }
                } else {
                    logResultInReport(
                            Constants.FAIL
                                    + ": API to fetch Algorithmic/Non-Algorithmic got failed showing status code :"
                                    + response.getStatusCode(),
                            "Navigate to " + multipartType
                                    + " multipart question type.",
                            reportTestObj);

                    break;

                }

            } while (isAlgorithmicMultipartWithHMST == false
                    && endofpracticetest == false);

        }

        catch (Exception e) {

            APP_LOG.error(
                    "Exception occurred while navigating to " + multipartType
                            + " multipart question type as :" + e.getMessage());
            logResultInReport(
                    Constants.FAIL + ": Exception occurred while navigating to "
                            + multipartType + " multipart question type as :"
                            + e.getMessage(),
                    "Navigate to " + multipartType
                            + " multipart question type.",
                    reportTestObj);

        }
    }

    /**
     * @author ratnesh.singh
     * @date 28 May,2018
     * @description Verify that partially attempted Algorithmic Multipart
     *              Question gets reset (both UI & DB) on opening and closing
     *              student initiated Learning Aids or opening Learning Aids and
     *              refreshing Page.
     */

    public void verifyUIAndDBStateResetOfPartiallyAttemptedAlgorithmicMultipart(
            int numberOfTriesBefore, int numberOfTriesAfter,
            String completeInnerTextBefore, String completeInnerTextAfter,
            Set<String> lAIdsForMultipart, String operationType,
            String username, String password) {

        if (numberOfTriesBefore == numberOfTriesAfter) {
            logResultInReport(
                    Constants.PASS
                            + ": Number of Attempts Left for attempted part successfully resets to same as before attempting part.",
                    "Verify that Number of Attempts left for attempted part of Algorithmic multipart question gets reset on UI after :"
                            + operationType,
                    reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": Number of Attempts Left for attempted part failed to reset to same as before attempting part.",
                    "Verify that Number of Attempts left for attempted part of Algorithmic multipart question gets reset on UI after :"
                            + operationType,
                    reportTestObj);

        }

        if (completeInnerTextBefore.equalsIgnoreCase(completeInnerTextAfter)) {
            logResultInReport(
                    Constants.FAIL
                            + ": attempted part is failed to loaded with new variable values.",
                    "Verify that question is loaded with new variable values for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    reportTestObj);

        } else {

            logResultInReport(
                    Constants.PASS
                            + ": attempted part is successfully loaded with new variable values.",
                    "Verify that question is loaded with new variable values for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    reportTestObj);

        }

        Map<String, String> attemptedQuestionStatePartOne = checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                1, true);

        if (attemptedQuestionStatePartOne.size() > 0) {
            logResultInReport(
                    Constants.FAIL
                            + ": Question elements are in attempted state",
                    "Verify that question elements are in attempted state on UI for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);

        } else {
            logResultInReport(
                    Constants.PASS
                            + ": Question elements are not in attempted state",
                    "Verify that question elements are in attempted state on UI for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);

        }

        String query = getDBQuery(username, password);

        Map<String, String> dataMapOfDBStatePartOne = getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                1, query, "noOfTriesAllowed|noOfTriesAttempted|score|status",
                lAIdsForMultipart);

        if (dataMapOfDBStatePartOne.get("status")
                .equalsIgnoreCase("Not Started")) {

            logResultInReport(
                    Constants.PASS
                            + ": 'Status' gets reset in DB for attempted part to 'Not Started'.",
                    "Verify the 'status' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'Status' does not get reset in DB for attempted part to 'Not Started'.",
                    "Verify the 'status' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);
        }

        if (Integer.parseInt(dataMapOfDBStatePartOne
                .get("noOfTriesAllowed")) == numberOfTriesBefore) {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAllowed' gets reset in DB for attempted part to :"
                            + numberOfTriesBefore,
                    "Verify the 'noOfTriesAllowed' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAllowed' does not get reset in DB for attempted part to :"
                            + numberOfTriesBefore,
                    "Verify the 'noOfTriesAllowed' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);
        }

        if (Double.parseDouble(dataMapOfDBStatePartOne.get("score")) == 0)

        {

            logResultInReport(
                    Constants.PASS
                            + ": 'Score' gets reset in DB for attempted part to '0'",
                    "Verify the 'score' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);
        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'Score' does not get reset in DB for attempted part to '0'",
                    "Verify the 'score' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);
        }

        if (Integer.parseInt(
                dataMapOfDBStatePartOne.get("noOfTriesAttempted")) == 0) {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAttempted' gets reset in DB for attempted part to '0'",
                    "Verify the 'noOfTriesAttempted' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'noOfTriesAttempted' does not get reset in DB for attempted part to '0'",
                    "Verify the 'noOfTriesAttempted' field gets reset in DB for for attempted part of Algorithmic multipart question after :"
                            + operationType,
                    this.reportTestObj);
        }

    }

    /**
     * @author saurabh.sharma5
     * @date 28 May,2018
     * @description Exit the practice test and again open it
     */
    public void closePracticeTestAndReopen() {
        try {
            // Close the displayed assessment player
            closePracticeAssessmentPlayer();
            // Click on the Practice Quiz of the section
            clickOnElement("PracticeTestFirstLOPracticeQuiz",
                    "Click on practice quiz of the desired section.");
            // Click on start button on practice test welcome screen
            clickOnElement("PracticeTestWelcomeScreenStartButton",
                    "Click on start button on practice test welcome screen");
            // Verify whether the assessment player is again displayed
            verifyElementIsVisible("PracticeTestCloseButton",
                    "Verify Practice Assessment player is again displayed to the learner");
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while closing practice test and reopening it.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while closing practice test and reopening it.",
                    "Close practice test and again open the same test.",
                    reportTestObj);
        }
    }

    /**
     * @author saurabh.sharma5
     * @date 28 May,2018
     * @description Exit the practice test and navigate to module material page
     */
    public void closePracticeAssessmentPlayer() {
        try {
            // Click on cross icon to close the assessment player
            clickOnElement("PracticeTestCloseButton",
                    "Click on cross icon to close the practice test.");
            // Verify the confirmation pop up is displayed
            verifyElementPresent("PracticeTestPopUpLeaveButton",
                    "Verify the close confirmation pop up is displayed");
            // Click on the Leave button on the pop up displayed
            clickOnElement("PracticeTestPopUpLeaveButton",
                    "Click on the Leave button on the confirmation pop up displayed");
            findElement.checkPageIsReady();
            // Verify the assessment player gets closed
            verifyElementNotPresent("PracticeTestCloseButton",
                    "Verify the assessment player gets closed");
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while closing practice assessment player");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while closing practice assessment player.",
                    "Close practice test and navigate to module material page",
                    reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @date 1 June,2018
     * @description Returns attempted UI state for all parts of Fully attempted
     *              multipart question.
     * @Return Map<string,Map<String,String>> having attempted UI state for all
     *         parts of fully attempted multipart question.
     */
    public Map<String, Map<String, String>>
           getAttemptedUIStateOfAllPartsInMultipart(int totalNoOfParts) {
        APP_LOG.info("Entering Func: getAttemptedUIStateOfAllPartsInMultipart");
        Map<String, Map<String, String>> attemptedUIStateForAllParts = new TreeMap<String, Map<String, String>>();
        try {
            for (int i = 1; i <= totalNoOfParts; i++) {
                attemptedUIStateForAllParts.put("Part" + i + "UIState",
                        checkAndReturnAttemptedQuestionStateForSpecifiedPartInMultipart(
                                i));
            }
            logResultInReport(
                    Constants.PASS
                            + ": Succefully fetched attempted UI state for all parts of Fully attempted multipart question.",
                    "Fetch attempted UI state for all parts of fully attempted Multipart question in Practice Test.",
                    reportTestObj);
            return attemptedUIStateForAllParts;
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occurred while fetching attempted UI state for all parts of Fully attempted multipart question as :"
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while fetching attempted UI state for all parts of Fully attempted multipart question as :"
                            + e.getMessage(),
                    "Fetch attempted UI state for all parts of fully attempted Multipart question in Practice Test.",
                    reportTestObj);
            return null;

        }
    }

    /**
     * @author ratnesh.singh
     * @date 1 June,2018
     * @description Returns attempted DB state for all parts of of fully
     *              attempted multipart question.
     * @Return Map<string,Map<String,String>> having attempted DB state for all
     *         parts of fully attempted multipart question.
     */
    public Map<String, Map<String, String>>
           getAttemptedDBStateOfAllPartsInMultipart(int totalNoOfParts,
                   Set<String> lAIdsForMultipart, String username,
                   String password) {
        APP_LOG.info("Entering Func: getAttemptedDBStateOfAllPartsInMultipart");

        Map<String, Map<String, String>> attemptedDBStateForAllParts = new TreeMap<String, Map<String, String>>();
        try {

            String query = getDBQuery(username, password);

            for (int i = 1; i <= totalNoOfParts; i++) {
                attemptedDBStateForAllParts.put("Part" + i + "DBState",
                        getDBStateForSpecifiedPartInMultipartOrHMSTOrHMSTAssessment(
                                i, query,
                                ResourceConfigurations.getProperty(
                                        "dbStateRequiredAttributes"),
                                lAIdsForMultipart));
            }
            logResultInReport(
                    Constants.PASS
                            + ": Succefully fetched attempted DB state for all parts of Fully attempted multipart question.",
                    "Fetch attempted DB state for all parts of fully attempted Multipart question in Practice Test.",
                    reportTestObj);
            return attemptedDBStateForAllParts;
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while fetching attempted DB state for all parts of Fully attempted multipart question as :"
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while fetching attempted DB state for all parts of Fully attempted multipart question as :"
                            + e.getMessage(),
                    "Fetch attempted DB state for all parts of fully attempted Multipart question in Practice Test.",
                    reportTestObj);
            return null;

        }
    }

    /**
     * @author ratnesh.singh
     * @date 1 June,2018
     * @description Compares corresponding Saved DB or Attempted UI states for
     *              all parts in fully attempted multipart question before and
     *              after performing any operations.
     */
    public void compareSavedDBOrAttemptedUIStatesForAllPartsInMultipart(
            int totalNoOfParts, String type, String operation,
            Map<String, Map<String, String>> beforeState,
            Map<String, Map<String, String>> afterState) {
        APP_LOG.info(
                "Entering Func: compareSavedDBOrAttemptedUIStatesForAllPartsInMultipart");
        try {

            if (type.equalsIgnoreCase("DB")) {
                for (int i = 1; i <= totalNoOfParts; i++) {
                    Map<String, String> attemptedDBStatePartBeforePerPart = beforeState
                            .get("Part" + i + "DBState");
                    Map<String, String> attemptedDBStatePartAfterPerPart = afterState
                            .get("Part" + i + "DBState");

                    compareSavedDBOrAttemptedUIStates(i, "CouchBase DB State",
                            operation, attemptedDBStatePartBeforePerPart,
                            attemptedDBStatePartAfterPerPart);
                }
                logResultInReport(
                        Constants.PASS + " : Saved " + type
                                + " state of fully attempted Multipart Question is getting sustained after :"
                                + operation,
                        "Compare saved" + type
                                + " state of fully attempted Multipart Question before and after :"
                                + operation,
                        this.reportTestObj);

            } else if (type.equalsIgnoreCase("UI")) {
                for (int i = 1; i <= totalNoOfParts; i++) {
                    Map<String, String> attemptedUIStatePartBeforePerPart = beforeState
                            .get("Part" + i + "UIState");
                    Map<String, String> attemptedUIStatePartAfterPerPart = afterState
                            .get("Part" + i + "UIState");

                    compareSavedDBOrAttemptedUIStates(i, "Attempted UI State",
                            operation, attemptedUIStatePartBeforePerPart,
                            attemptedUIStatePartAfterPerPart);
                }
                logResultInReport(
                        Constants.PASS + " : Attempted " + type
                                + " state of fully attempted Multipart Question is getting sustained after :"
                                + operation,
                        "Compare attempted" + type
                                + " state of fully attempted Multipart Question before and after :"
                                + operation,
                        this.reportTestObj);

            }

            else {
                logResultInReport(
                        Constants.FAIL
                                + ": type argument must be either DB or UI",
                        "Compare saved/attempted" + type
                                + " state of fully attempted Multipart Question before and after :"
                                + operation,
                        this.reportTestObj);

            }

        } catch (

        Exception e) {
            APP_LOG.error(
                    "Exception occurred while comparing saved/attempted " + type
                            + " state of fully attempted Multipart Question before and after :"
                            + operation + " as " + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while comparing saved/attempted "
                            + type
                            + " state fof fully attempted Multipart Question before and after :"
                            + operation + " as " + e.getMessage(),
                    "Compare saved/attempted" + type
                            + " state of fully attempted Multipart Question before and after :"
                            + operation,
                    this.reportTestObj);

        }
    }

    /**
     * @author ratnesh.singh
     * @date 2 June,2018
     * @description Navigate to multi-part question having student initiated
     *              learning Aids in Practice Test.
     */
    public void navigateToMultipartinPracticeTestWithHMST() {
        APP_LOG.info(
                "Entering Func: navigateToMultipartinPracticeTestWithHMST");
        boolean isMultipartWithHMST = false;
        boolean endofpracticetest = false;
        try {
            do {
                navigateToMultipartinPracticeTest();
                if (verifyElementPresentWithOutLog("HelpMeAnswerThisDropdown")
                        .contains(Constants.PASS)) {

                    logResultInReport(
                            Constants.PASS
                                    + ": Navigated successfully to multipart question type in Practice test with HMST Learning Aids",
                            "Navigate to multipart question type with HMST Learning Aids in Practice test.",
                            reportTestObj);
                    isMultipartWithHMST = true;
                    break;

                } else {
                    isMultipartWithHMST = false;
                    attemptMultipartQuestionInPracticeTest(1,
                            getNumberOfPartsInMultipartQuestion());

                    if (verifyElementPresentWithOutLog("NextButton")
                            .contains(Constants.PASS)) {
                        clickOnElement("NextButton",
                                "Click on Next Question button");
                        endofpracticetest = false;
                        findElement.checkPageIsReady();
                    }

                    else if (verifyElementPresentWithOutLog(
                            "PracticeTestCompleteSummaryButton")
                                    .contains(Constants.PASS)) {

                        logResultInReport(
                                Constants.FAIL
                                        + ": Reached to end of practice test and could not find multipart question type with HMST Learning Aids",
                                "Navigate to multipart question type with HMST Learning Aids in Practice test.",
                                reportTestObj);
                        endofpracticetest = true;
                        break;

                    }

                }
            } while (isMultipartWithHMST == false
                    && endofpracticetest == false);

        }

        catch (

        Exception e) {

            APP_LOG.error(
                    "Exception occurred while navigating to multipart question type with HMST Learning Aids as :"
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while navigating to multipart question type with HMST Learning Aids as :"
                            + e.getMessage(),
                    "Navigate to multipart question type with HMST Learning Aids in Practice test.",
                    reportTestObj);

        }

    }

    /**
     * @author ratnesh.singh
     * @date 2 June,2018
     * @description Verifies that the DB state gets reset for attempted part of
     *              HMST Learning Aids every time user Reopens HMST.
     */
    public void verifyDBStateResetOfAttemptedPartInHMST(int part,
            Map<String, String> dataMapInteractivePartHMST) {
        if (Integer.parseInt(
                dataMapInteractivePartHMST.get("noOfTriesAttempted")) == 0) {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAttempted' resets in DB for interactive part :"
                            + part + " to 0.",
                    "Verify the 'noOfTriesAttempted' resets in DB for interactive part in HMST every time user reopens HMST.",
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'noOfTriesAttempted' does not get reset in DB for interactive part :"
                            + part + " to 0.",
                    "Verify the 'noOfTriesAttempted' resets in DB for interactive part in HMST every time user reopens HMST.",
                    this.reportTestObj);
        }

        if (dataMapInteractivePartHMST.get("status").contains("Not Started")) {

            logResultInReport(
                    Constants.PASS
                            + ": 'Status' resets in DB for interactive part :"
                            + part + " to 'Not Started'.",
                    "Verify the 'Status' resets in DB for interactive part in HMST every time user reopens HMST.",
                    this.reportTestObj);

        } else {
            logResultInReport(
                    Constants.FAIL
                            + ": 'Status' does not get reset in DB for interactive part :"
                            + part + " to 'Not Started'.",
                    "Verify the 'Status' resets in DB for interactive part in HMST every time user reopens HMST.",
                    this.reportTestObj);
        }

    }

    /**
     * @author saurabh.sharma5
     * @date 28 May,2018
     * @description Verify the basic UI features of a formative multi part
     *              question
     */
    public void verifyFormativeMultiPartUI() {
        try {
            // Verify only a single part is displayed for the multi part
            // question
            if (verifyElementPresentWithOutLog(
                    "PracticeTestMultipartQuestionPartOne")
                            .contains(Constants.PASS)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestMultipartQuestionPartTwo")
                                    .contains(Constants.FAIL)) {
                APP_LOGS.info(
                        "Only a single part is displayed for the multi part question");
                logResultInReport(
                        Constants.PASS
                                + ": Only a single part is displayed for the multi part question.",
                        "Verify only a single part is displayed for the multi part question",
                        reportTestObj);
            } else if (verifyElementPresentWithOutLog(
                    "PracticeTestMultipartQuestionPartTwo")
                            .contains(Constants.PASS)) {
                APP_LOGS.info(
                        "More than 1 part is displayed for the multi part question.");
                logResultInReport(
                        Constants.FAIL
                                + ": More than 1 part is displayed for the multi part question.",
                        "Verify only a single part is displayed for the multi part question",
                        reportTestObj);
            }
            // Verify the number of parts remaining is displayed on top of first
            // part
            if (verifyElementPresentWithOutLog(
                    "PracticeTestMultipartRemainingPartsLabel")
                            .contains(Constants.PASS)) {
                APP_LOGS.info(
                        "Number of parts remaining is displayed on top of first part.");
                logResultInReport(
                        Constants.PASS
                                + ": Number of parts remaining is displayed on top of first part.",
                        "Verify the number of parts remaining is displayed on top of first part.",
                        reportTestObj);
            } else {
                APP_LOGS.info(
                        "Number of parts remaining is NOT displayed on top of first part.");
                logResultInReport(
                        Constants.FAIL
                                + ": Number of parts remaining is NOT displayed on top of first part.",
                        "Verify the number of parts remaining is displayed on top of first part",
                        reportTestObj);
            }
            // Verify the maximum number of tries is displayed for the first
            // part
            if (verifyElementPresentWithOutLog(
                    "PracticeTestMultipartRemainingTriesLabelPartOne")
                            .contains(Constants.PASS)) {
                APP_LOGS.info(
                        "Maximum number of tries is displayed for the first part.");
                logResultInReport(
                        Constants.PASS
                                + ": Maximum number of tries is displayed for the first part.",
                        "Verify the maximum number of tries is displayed for the first part.",
                        reportTestObj);
            } else {
                APP_LOGS.info(
                        "Maximum number of tries is NOT displayed for the first part.");
                logResultInReport(
                        Constants.FAIL
                                + ": Maximum number of tries is NOT displayed for the first part.",
                        "Verify the maximum number of tries is displayed for the first part.",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOGS.info(
                    "Exception occurred while verifying the basic UI components of formative multi part question: "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while verifying the basic UI components of formative multi part question.",
                    "Verify the basic UI features of a formative multi part question",
                    reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @date 26 Apr,2018
     * @description Attempt single part question under Practice Test with or
     *              without submitting answer
     */
    public void attemptSinglePartQuestionInPracticeTest(
            boolean... toSubmitWithoutAnswer) {

        // FindElement findElement = new FindElement();
        findElement.checkPageIsReady();
        HashMap<String, List> availableQuestionTypes = null;
        boolean toSubmitWithoutAnswercheck = toSubmitWithoutAnswer.length > 0
                ? toSubmitWithoutAnswer[0] : false;

        try {
            // Get set of active question types for single part question
            availableQuestionTypes = returnSetOfQuestion1();

            if ((availableQuestionTypes.size() == 0)
                    && verifyElementPresentWithOutLog(
                            "PracticeTestSummarypagePracticeAgainButton")
                                    .contains(Constants.PASS)) {
            }
            // Verify that available question types in single part are known
            // activity.
            else if (availableQuestionTypes.size() == 0) {
                logResultInReport(
                        Constants.FAIL
                                + ": No known activity type available under Single Part Question",
                        "Verify any known activity type is displayed for the Single Part Question",
                        this.reportTestObj);
            }
            // Attempt the question available in single part.
            else {
                // Get total number of attempts/tries left for question.
                int noOftriesLeft = getNumberOfTriesLeftParticularQuestionInPracticeTest(
                        1, "PracticeTestRemainingTriesLabelforSinglePart");

                for (int j = noOftriesLeft; j >= 1; j--) {

                    // Check if the positive feedback is present in case
                    // question is already in attempted state for initial tries
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestSinglePartFeedBackLocator")
                                    .contains(Constants.PASS)) {

                        if (getAttribute(
                                "PracticeTestSinglePartFeedBackLocator",
                                "class").contains("success")) {
                            break;
                        }
                    }

                    if (toSubmitWithoutAnswercheck == true) {
                        attemptUIRenderedQuestion(
                                availableQuestionTypes, ResourceConfigurations
                                        .getProperty("diagnosticSubmitButton"),
                                true);
                        findElement.checkPageIsReady();
                    } else {
                        attemptUIRenderedQuestion(availableQuestionTypes,
                                ResourceConfigurations
                                        .getProperty("diagnosticSubmitButton"));
                        findElement.checkPageIsReady();

                    }

                    // Check if the feedback displayed and stop attempting
                    // question in case feedback is positive
                    if (verifyElementPresentWithOutLog(
                            "PracticeTestSinglePartFeedBackLocator")
                                    .contains(Constants.FAIL)) {

                        APP_LOG.info(
                                "No feedback is displayed on submitting the single part question");
                        logResultInReport(
                                Constants.FAIL
                                        + ": No feedback is displayed on submitting the single part question",
                                "Verify the feedback is getting displayed under single part question in Practice Test.",
                                this.reportTestObj);
                    } else if (getAttribute(
                            "PracticeTestSinglePartFeedBackLocator", "class")
                                    .contains("success")) {
                        break;
                    }

                }

                logResultInReport(
                        Constants.PASS
                                + ": Successfully attempted Single Part Question",
                        "Attempt the displayed Single Part Question",
                        this.reportTestObj);

            }
        } catch (

        Exception e) {
            APP_LOG.info(
                    "Exception occurred while attempting Single Part Question");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while attempting Single Part Question",
                    "Attempt the displayed Single Part Question",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 18 Jun,2018
     * @description Returns DB state of required attributes for single part
     *              question under Practice Test
     */

    public Map<String, String> getDBStateForSinglePartQuestionInPracticeTest(
            String query, String attributesRequired, Set<String> questionIDs,
            String questionType) {
        APP_LOG.debug(
                "Fetch DB state of required attributes for single part question of type :"
                        + questionType + " under Practice Test.");
        int partCounter = 1;
        String[] attributes = null;
        Map<String, String> mapWithDBStateOfRequiredAttributes = new HashMap<String, String>();
        CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);

        try {

            // Building DataMap with required attributes to gather DB state
            // for single part question
            attributes = attributesRequired.split("\\|");

            for (String attribute : attributes) {

                mapWithDBStateOfRequiredAttributes.put(attribute, null);

            }

            Iterator<String> iT = questionIDs.iterator();

            // Fetch saved DB state from CouchBase into DataMaps created
            while (iT.hasNext()) {

                String questionID = iT.next();

                mapWithDBStateOfRequiredAttributes = cb
                        .fetchCouchbaseDBStateSavedForMultipart(query,
                                "results", questionID, 0,
                                mapWithDBStateOfRequiredAttributes);
                break;

            }
            logResultInReport(
                    Constants.PASS
                            + ": DB State for single part question of type :"
                            + questionType + " fetched successfully",
                    "Fetch DB state of required attributes for single part question under Practice Test.",
                    this.reportTestObj);

            return mapWithDBStateOfRequiredAttributes;

        } catch (Exception e) {

            APP_LOG.error(
                    "Exception occurred while fetching DB State for single part question of type :"
                            + questionType + " as " + e.getMessage());

            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while fetching DB State for single part question of type :"
                            + questionType + " as " + e.getMessage(),
                    "Fetch DB state of required attributes for single part question under Practice Test.",
                    this.reportTestObj);

            return null;
        }

    }

    /**
     * @author ratnesh.singh
     * @date 18 Jun,2018
     * @description Validates DB state of parameters like
     *              'status','isCorrectAnswer','Score','noOfTriesAttempted' for
     *              completed single part question under Practice Test
     * 
     */
    public void
           validateCouchBaseDBStateForCompletedSinglePartQuestionInPractice(
                   int totalNumberOfIntialTriesBeforeAttempt,
                   int totalNumberOfTriesLeftAfterAttempt,
                   Map<String, String> dataMapafterCompletingPart,
                   String questionType) {

        if (dataMapafterCompletingPart.get("status").contains("Completed")) {

            logResultInReport(
                    Constants.PASS
                            + ": 'Status' set in DB for attempted single part question type :"
                            + questionType + " is Completed.",
                    "Verify the 'status' set in DB for attempted single part question type :"
                            + questionType,
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'Status' set in DB for attempted single part question type :"
                            + questionType + " is not Completed.",
                    "Verify the 'status' set in DB for attempted single part question type :"
                            + questionType,
                    this.reportTestObj);
        }

        if (dataMapafterCompletingPart.get("isCorrectAnswer")
                .contains("false")) {

            if (Double.parseDouble(
                    dataMapafterCompletingPart.get("score")) == 0) {

                logResultInReport(
                        Constants.PASS
                                + ": 'Score' set in DB for attempted single part question type :"
                                + questionType + " is 0 for Incorrect Attempt.",
                        "Verify the 'Score' set in DB for attempted single part question type :"
                                + questionType + " for Incorrect attempt.",
                        this.reportTestObj);

            } else {

                logResultInReport(Constants.FAIL
                        + ": 'Score' set in DB for attempted single part question type :"
                        + questionType + " is not 0 for Incorrect Attempt.",
                        "Verify the 'Score' set in DB for attempted single part question type :"
                                + questionType + " for Incorrect attempt.",
                        this.reportTestObj);
            }

        } else if (dataMapafterCompletingPart.get("isCorrectAnswer")
                .contains("true")) {

            if (Double
                    .parseDouble(dataMapafterCompletingPart.get("score")) > 0) {

                logResultInReport(
                        Constants.PASS
                                + ": 'Score' set in DB for attempted single part question type :"
                                + questionType
                                + " is greater than 0 for correct Attempt.",
                        "Verify the 'Score' set in DB for attempted single part question type :"
                                + questionType + " for Correct attempt.",
                        this.reportTestObj);
            } else {

                logResultInReport(
                        Constants.FAIL
                                + ": 'Score' set in DB for attempted single part question type :"
                                + questionType
                                + " is not greater than 0 for correct Attempt.",
                        "Verify the 'Score' set in DB for attempted single part question type :"
                                + questionType + " for Correct attempt.",
                        this.reportTestObj);
            }
        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'IsCorrectAnswer' flag set in DB for attempted single part question type :"
                            + questionType + " is neither 'true' nor 'false'.",
                    "Verify the 'isCorrectAnswer' set in DB for attempted single part question type :"
                            + questionType,
                    this.reportTestObj);

        }

        if (Integer.parseInt(dataMapafterCompletingPart
                .get("noOfTriesAttempted")) == (totalNumberOfIntialTriesBeforeAttempt
                        - totalNumberOfTriesLeftAfterAttempt)) {

            logResultInReport(
                    Constants.PASS
                            + ": 'noOfTriesAttempted' set in DB for attempted single part question type :"
                            + questionType + " is correct as "
                            + Integer.parseInt(dataMapafterCompletingPart
                                    .get("noOfTriesAttempted")),
                    "Verify the 'noOfTriesAttempted' set in DB for attempted single part question type :"
                            + questionType,
                    this.reportTestObj);

        } else {

            logResultInReport(
                    Constants.FAIL
                            + ": 'noOfTriesAttempted' set in DB for attempted single part question type :"
                            + questionType + " is incorrect as "
                            + Integer.parseInt(dataMapafterCompletingPart
                                    .get("noOfTriesAttempted")),
                    "Verify the 'noOfTriesAttempted' set in DB for attempted single part question type :"
                            + questionType,
                    this.reportTestObj);
        }

    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Getting text of a particular element
     * @return Text of the particular element
     */
    public String getTextWithoutLog(String element) {
        APP_LOGS.debug("Inside Fucn: getText");

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        return this.performAction.execute(ACTION_GET_TEXT, element);
    }

    /**
     * @author saurabh.sharma5
     * @date 20 June,2018
     * @description Get the order with text of MCQSA single part practice
     *              question in an Hash Map
     */
    public HashMap<Integer, String> getOrderOfMCQSAQuestionPractice() {
        FindElement element = new FindElement();
        HashMap<Integer, String> optionOrder = new HashMap<Integer, String>();
        List<WebElement> radioOption;

        try {
            // Get the options count for the MCQSA
            radioOption = element.getElements("RadioOptionPracticeMCQSA");
            int listLength = radioOption.size();

            // Add the value id of each option to a Hash Map
            for (int i = 0; i < listLength; i++) {
                optionOrder.put(i, radioOption.get(i).getText());
            }
        } catch (Exception e) {
            APP_LOGS.info(
                    "Exception occured while getting the order of MCQSA single part question");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occured while getting the order of MCQSA single part question",
                    "Get the order of the options of MCQSA single part question",
                    reportTestObj);
        }
        return optionOrder;
    }

    /**
     * @author saurabh.sharma5
     * @date 20 June,2018
     * @description Compare that the order of MCQSA options are shuffled or not
     *              depending on desired condition
     */
    public void verifyMCQSAOptionsAreShuffled(
            HashMap<Integer, String> originalSequence,
            HashMap<Integer, String> refreshedSequence, boolean shouldShuffle) {

        try {
            if (shouldShuffle) {
                if (!originalSequence.equals(refreshedSequence)) {
                    APP_LOGS.info(
                            "Options of the MCQSA question are shuffled successfully.");
                    logResultInReport(
                            Constants.PASS
                                    + ": Options of the MCQSA question are shuffled successfully.",
                            "Verify the options of the MCQSA question are shuffled",
                            reportTestObj);
                } else {
                    APP_LOGS.info(
                            "Options of the MCQSA question are NOT shuffled.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Options of the MCQSA question are NOT shuffled.",
                            "Verify the options of the MCQSA question are shuffled",
                            reportTestObj);
                }
            } else {
                if (originalSequence.equals(refreshedSequence)) {
                    APP_LOGS.info(
                            "Options of the MCQSA question are NOT shuffled.");
                    logResultInReport(
                            Constants.PASS
                                    + ": Options of the MCQSA question are NOT shuffled.",
                            "Verify the options of the MCQSA question are NOT shuffled",
                            reportTestObj);
                } else {
                    APP_LOGS.info(
                            "Options of the MCQSA question are shuffled when they should not.");
                    logResultInReport(
                            Constants.FAIL
                                    + ": Options of the MCQSA question are shuffled when they should not.",
                            "Verify the options of the MCQSA question are NOT shuffled",
                            reportTestObj);
                }
            }
        } catch (Exception e) {
            APP_LOGS.info(
                    "Exception occured while verifying the sequence change of MCQSA options.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occured while verifying the sequence change of MCQSA options.",
                    "Verify the sequence change of MCQSA options",
                    reportTestObj);
        }
    }

}