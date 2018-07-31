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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_PostAssessmentPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private FindElement findElement = new FindElement();
    private PerformAction performAction = new PerformAction();
    protected String validAnswer1 = "", validAnswer2 = "", invalidAnswer = "",
            locator = "";
    public int progressStartIndex = 0;
    public int progressMaxIndex = 0;

    public GLPLearner_PostAssessmentPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Click on a web element
     * @return The object of GLPLearner_PostAssessmentPage
     */
    public GLPLearner_PostAssessmentPage clickOnElement(String locator,
            String message) {
        System.out.println("");
        APP_LOGS.debug("Click on the Element: " + locator);
        try {
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    locator);
            this.result = this.performAction.execute(ACTION_CLICK, locator);
            if (this.result.contains(Constants.FAIL)) {
                this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                        locator);
            }

            logResultInReport(this.result, message, this.reportTestObj);
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_PostAssessmentPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author tarun.gupta1
     * @date 18 September,2017
     * @description verify expected text with actual text
     * @return
     */
    public String verifyTextContains(String element, String text,
            String stepDesc) {
        APP_LOG.debug("Verify text: " + text);
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        if (result.contains("PASS")) {
            result = "PASS: Element- '" + element
                    + "' contains the correct text which is  '" + text + "'";
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: Element- '" + element
                    + "' does not contains the correct text which is  '" + text
                    + "'";
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;

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
     * @author lekh.bahl
     * @date 17 apr,2018
     * @description Verify question id changes on refreshing or exiting the post
     *              assessment
     * @return GLPLearner_PostAssessmentPage
     */
    public GLPLearner_PostAssessmentPage verifyDifferentQuestionID(
            String questionIDBeforeExit, String questionIDAfterExit,
            String message) {
        if (!(questionIDBeforeExit.equals(questionIDAfterExit))) {
            this.result = Constants.PASS + ": Current question ID : "
                    + questionIDAfterExit
                    + " is different from previous question ID "
                    + questionIDBeforeExit;
            logResultInReport(this.result, message, this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Current question ID : "
                    + questionIDAfterExit
                    + " is not different from previous question ID :"
                    + questionIDBeforeExit;
            logResultInReport(this.result, message, this.reportTestObj);

        }
        return new GLPLearner_PostAssessmentPage(reportTestObj, APP_LOG);
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
     * @author mohit.gupta5
     * @date 05 May, 2018
     * @description To verify button is disabled or enabled
     */
    public void verifyButtonEnabledOrDisabled(String elementlocator,
            String value, String stepDesc) {
        result = performAction.execute(ACTION_VERIFY_ISENABLED, elementlocator,
                value);
        logResultInReport(result, stepDesc, reportTestObj);
    }

    /**
     * @author mohit.gupta5
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
     * @author mohit.gupta5
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
     * @author mohit.gupta5
     * @date 05 May,2018
     * @description Verify element is present
     */
    public String getText(String element) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, element);
        return valueText;
    }

    /**
     * @author mohit.gupta5
     * @param element
     *            --> Name of Element
     * @param stepDesc
     *            --> Description of Steps
     * @description --> clicks on element and handle StaleException if occurred.
     */

    public void clickOnElementAndHandleStaleException(String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.performAction.execute(CLICK_AND_HANDLE_STALE_EXCEPTION, element);
    }

    /**
     * @author mohit.gupta5 Scrolls the webpage upto pixels passed in parameter.
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
                searchGivenQuestionType("[id^=FIB] .fib-dropdown>div",
                        questionType);
            } else if (questionType.equalsIgnoreCase("NumberLine")) {
                searchGivenQuestionType("[id*='numberline-cursor-num']",
                        questionType);
            } else if (questionType.equalsIgnoreCase("McqMa")) {
                searchGivenQuestionType("div[id^='McqMa']", questionType);
            } else if (questionType.equalsIgnoreCase("McqSa")) {
                searchGivenQuestionType("input[id*='_0'][type='radio']",
                        questionType);
            }

        } catch (Exception e) {
            APP_LOG.error("Error while finding " + questionType + " question");
        } finally {
            FindElement ele = new FindElement();
            ele.checkPageIsReady();
        }
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
            logResultInReport(Constants.FAIL
                    + ": Error while getting oquestion type in diagnostic: "
                    + e.getMessage(),
                    "Get the available diagnostic question type on page",
                    this.reportTestObj);
        }
        return availableQuestionTypes;
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
                if (returnDriver().findElement(By.cssSelector(".resultHeader"))
                        .isDisplayed()) {
                    return availableQuestionTypes;
                }
            } catch (Exception e) {
                APP_LOG.info("Test is not yet completed");
            }
            if (availableQuestionTypes.size() == 0) {
                returnDriver().navigate().back();
                FindElement element = new FindElement();
                element.checkPageIsReady();
                GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                        reportTestObj, APP_LOG);
                // Verify CourseTile Present and navigate to Welcome Learner
                // Screen
                objGLPLearnerCourseViewPage.navigateToWelcomeScreenLearner();
                // Automate the remaining steps of test case
                GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                        reportTestObj, APP_LOG);
                /*
                 * objGLPLearnerCourseHomePage.verifyElementPresent(
                 * "CourseHomeStartYourPathBtn",
                 * "Verify learner is successfully navigated to welcome screen."
                 * );
                 */
                objGLPLearnerCourseHomePage.navigateToDiagnosticPage();
                this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                        "CourseHomeStartYourPathBtn");
                this.performAction.execute(ACTION_CLICK,
                        "CourseHomeStartYourPathBtn");
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
        } catch (Exception e) {
            APP_LOG.info("Exception accured while checking type of question:- "
                    + e.getMessage());
            logResultInReport(Constants.FAIL
                    + ": Exception accured while checking type of question:- "
                    + e.getMessage(),
                    "Verify that user has completed diagnostic test.",
                    this.reportTestObj);
        }
        return availableQuestionTypes;
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
                for (int i = 0; i <= 30; i++) {
                    HashMap<String, List> isHybridFound = returnSetOfQuestionsFound();
                    if (isHybridFound.size() > 1) {
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
                        if (i == 30) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            } else if (questionType.equalsIgnoreCase("MultiPart")) {
                for (int i = 0; i <= 30; i++) {
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
                        if (i == 30) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            } else {
                for (int i = 0; i <= 30; i++) {
                    List<WebElement> getQuestions = returnDriver()
                            .findElements(By.cssSelector(locator));
                    HashMap<String, List> checkHybridNotFound = returnSetOfQuestionsFound();
                    if (getQuestions.size() > 0
                            && checkHybridNotFound.size() == 1) {
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
                        if (i == 30) {
                            result = Constants.FAIL + ": No " + questionType
                                    + " question is found.";
                            logResultInReport(result,
                                    "Navigate to " + questionType + " question",
                                    this.reportTestObj);
                        }

                    }

                }
            }
        } catch (Exception e) {
            APP_LOG.error("Error while finding locator for " + questionType
                    + " question");
        }
    }

    /**
     * @author mohit.gupta5
     * @description attemptUserChoiceQuestion method will attempt question and
     *              according to attempt type will click on Submit
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     * @param attemptType
     *            --> SubmitButton
     */
    public void attemptUserChoiceQuestion(String activityType,
            String attemptType) {
        APP_LOGS.info(
                "Attempting question type specified by user : " + activityType
                        + " using attempt type : " + attemptType + "...");

        try {
            if (activityType.contains("Multipart")) {
                attemptAllQuestionsOnMultiPart();
                logResultInReport(Constants.PASS
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
                                                        ".mathpallet-keyboard .mathpalette-close")));
                                APP_LOG.info("Closed Math Pallate");
                            } catch (Exception e) {
                                APP_LOG.error(
                                        "Error while closing Math Pallate "
                                                + e);
                            }
                        }
                        if (attemptType.equalsIgnoreCase("Submit")) {
                            clickOnElementAndHandleStaleException(
                                    "SubmitButton",
                                    "Click on " + attemptType + " button");
                            logResultInReport(Constants.PASS
                                    + ": Attempted FIB_FreeResponse type question using attempt type : "
                                    + attemptType,
                                    "Verify that user has attempted FIB_FreeResponse type question and Submitted the answer",
                                    this.reportTestObj);
                        } else {
                            APP_LOG.info(activityType
                                    + " is not attempted as per user choice");
                            logResultInReport(Constants.PASS
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
                    if (attemptType.equalsIgnoreCase("Submit")) {
                        clickOnElementAndHandleStaleException("SubmitButton",
                                "Click on " + attemptType + " button");
                        logResultInReport(Constants.PASS
                                + ": Attempted FIB_DropDown type question using attempt type : "
                                + attemptType,
                                "Verify that user has attempted FIB_DropDown type question and Submitted the answer",
                                this.reportTestObj);
                    } else {
                        APP_LOG.info(activityType
                                + " is not attempted as per user choice");
                        logResultInReport(Constants.PASS
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
                    logResultInReport(Constants.FAIL
                            + ": Errorwhile clicking on FirstRadio button: "
                            + e, "Click on FirstRadio button",
                            this.reportTestObj);
                    return;
                }
                if (attemptType.equalsIgnoreCase("Submit")) {
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on " + attemptType + " button");
                    logResultInReport(Constants.PASS
                            + ": Attempted McqSa type question using attempt type : "
                            + attemptType,
                            "Verify that user has attempted McqSa type question and Submitted the answer",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(activityType
                            + " is not attempted as per user choice");
                    logResultInReport(Constants.PASS
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
                    logResultInReport(Constants.FAIL
                            + ": Errorwhile clicking on Firstcheckbox button: "
                            + e, "Click on FirstCheckBox button",
                            this.reportTestObj);
                    return;
                }
                if (attemptType.equalsIgnoreCase("Submit")) {
                    clickOnElementAndHandleStaleException("SubmitButton",
                            "Click on " + attemptType + " button");
                    logResultInReport(Constants.PASS
                            + ": Attempted McqMa type question using attempt type : "
                            + attemptType,
                            "Verify that user has attempted McqMa type question and Submitted the answer",
                            this.reportTestObj);
                } else {
                    APP_LOG.info(activityType
                            + " is not attempted as per user choice");
                    logResultInReport(Constants.PASS
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
                                verifyButtonEnabledOrDisabled("SubmitButton",
                                        "yes",
                                        "Verify 'Submit' button is enabled after answer is selected.");
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
     * @author mohit.gupta5
     * @date 07 May,2018
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
     * @description verifyNoOfTryAndLearnigAidFunctionality method will
     * @param activityType
     *            --> FIB_DropDown/FIB_FreeReponse/Multipart/McqSa/McqMa
     * @param attemptType
     *            --> SubmitButton
     */
    public void verifyNoOfTryAndLearnigAidFunctionality(String activityType,
            String attemptType) {
        System.out.println("");
        List<WebElement> list = findElement
                .findListAndHandleStaleElementException("FIBFreeResponse");
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
                APP_LOG.error("Error while closing Math Pallate " + e);
            }
        }

        // Verify the no. of tries is not displayed for the first part
        verifyElementNotPresent("PostAssessmentTestNoOfTries",
                "Verify the number of tries is Not displayed.");
        verifyButtonEnabledOrDisabled("SubmitButton", "yes",
                "Verify 'Submit' button is enabled after answer is selected.");
        // Verify Help Me Solve This window opens
        verifyElementIsNotVisible("PostAssessmentLearningAidsBody",
                "Verify Learning Aids window is displayed to the learner");
        // Click on 'Submit' Button
        clickOnElement("SubmitButton", "Click On the 'Submit' button");
        // Verify the no. of tries is not displayed for the first part
        verifyElementNotPresent("PostAssessmentTestNoOfTries",
                "Verify the 'Only 1 Try is given for submission of a reason.'");

    }

    /**
     * @author lekh.bahl
     * @date 14 May,2018
     * @description Get number of LO in module
     */
    public int getLoCount() {
        int loCount = 0;
        try {
            FindElement ele = new FindElement();
            loCount = ele.getElements("LoCount").size();
        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Failed to get number of LO "
                            + e.getMessage(),
                    "Failed to get number of LO", reportTestObj);
        }
        return loCount - 1;
    }

    /**
     * @author lekh.bahl
     * @date 14 May,2018
     * @description Get number of questions on post assessment
     */
    public int getQuestionCount() {
        int questionCount = 0;
        try {
            questionCount = Integer.parseInt(getAttribute(
                    "PostAssessmentTestProgressBar", "aria-valuemax"));

        } catch (NumberFormatException e) {
            logResultInReport(Constants.FAIL
                    + ": Failed to get number of question in post assessment "
                    + e.getMessage(),
                    "Failed to get number question in post assessment",
                    reportTestObj);
        }
        return questionCount;
    }

    /**
     * @author mohit.gupta5
     * @date 20 Dec,2017
     * @description verify Text Comparison
     */
    public void compareText(String text1, String text2, String desc) {

        try {
            if (text1.equals(text2)) {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is same as Expected Text : " + text2;
                logResultInReport(this.result, desc, this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is not same as Expected Text :" + text2;
                logResultInReport(this.result, desc, this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

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

}
