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

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_CourseHomePage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private FindElement findElement = new FindElement();

    public GLPLearner_CourseHomePage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;

    }

    /**
     * @author tarun.gupta
     * @date 18 September,2017
     * @description Navigate to Welcome screen page from course view page
     * @return The object of ProductApplication_DashboardPage
     */
    public GLPLearner_CourseHomePage
           verifyWelcomeScreenPageTitle(String Locator, String message) {
        APP_LOG.debug("Verify Page Title.");
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "CourseHomeStartYourPathBtn");
        verifyPageTitle(Locator, message);
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 18 Sep,2017
     * @description Click on specific element
     * @return The object of ProductApplication_DiagnosticTestPage
     */
    public void clickOnElement(String locator, String message) {
        APP_LOGS.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        if ((webDriver instanceof SafariDriver
                || capBrowserName.equalsIgnoreCase("Safari"))) {
            this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                    locator);
        } else {
            this.result = this.performAction.execute(ACTION_CLICK, locator);
        }
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author tarun.gupta
     * @date 18 September,2017
     * @description verify presence of any element
     * @return The object of ProductApplication_CourseViewPage
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOGS.debug("Element is present: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        for (int i = 0; i <= 1; i++) {
            if (performAction
                    .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator)
                    .contains(Constants.FAIL)) {
                returnDriver().navigate().refresh();
                FindElement ele = new FindElement();
                ele.checkPageIsReady();
            }
        }
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
     * @author tarun.gupta1
     * @date 18 September,2017
     * @description verify expected text with actual text
     * @return The object of ProductApplication_LoginPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        APP_LOG.debug("Verify text: " + text);
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        if (result.contains(Constants.PASS)) {
            result = Constants.PASS + ": Element- '" + element
                    + "' contains the correct text which is  '" + text + "'";
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = Constants.FAIL + ": Element- '" + element
                    + "' does not contains the correct text which is  '" + text
                    + "'";
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;

    }

    /**
     * @author tarun.gupta
     * @date 18 September,2017
     * @description Navigate to Welcome screen page from course view page
     * @return The object of ProductApplication_DashboardPage
     */
    public GLPLearner_DiagnosticTestPage navigateToDiagnosticPage() {
        APP_LOG.debug("Click on the Element.");
        try {
            performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "CourseHomeStartYourPathBtn");
            for (int i = 1; i <= 2; i++) {
                if (performAction
                        .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                "CourseHomeStartYourPathBtn")
                        .contains(Constants.FAIL)) {
                    returnDriver().navigate().refresh();
                    FindElement ele = new FindElement();
                    ele.checkPageIsReady();
                } else {
                    break;
                }
            }
            clickOnElement("CourseHomeStartYourPathBtn",
                    "Verify on clicking 'Start Button' user is navigated to Diagnostic test screen.");
            for (int i = 1; i <= 2; i++) {
                if (performAction
                        .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                                "DiagnosticSubmitButton")
                        .contains(Constants.FAIL)) {
                    returnDriver().navigate().back();
                    FindElement ele = new FindElement();
                    ele.checkPageIsReady();
                } else {
                    break;
                }
            }
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOG);
    }

    /**
     * @author lekh.bahl
     * @date 12 July, 2017
     * @description Verify page title
     */
    public void verifyPageTitle(String pageTitle, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE, "",
                pageTitle);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author mohit.gupta5
     * @date 23 Nov,2017
     * @description :Verify element not present.
     */
    public GLPLearner_CourseHomePage verifyElementNotPresent(String locator,
            String message) {
        APP_LOG.debug(locator + "Element is not present");
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date 24 Nov,2017
     * @description Verify Rio Course Start Diagnostic Button
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage navigateToCourseView() {
        APP_LOG.debug("Verify User is navigated to course view screen.");
        if (returnDriver() instanceof InternetExplorerDriver
                || "InternetExplorer".equalsIgnoreCase(capBrowserName)) {
            ((JavascriptExecutor) returnDriver()).executeScript(
                    "arguments[0].click()",
                    returnDriver().findElement(By.cssSelector(
                            ".o-header__logo.o-header__logo--pearson>img")));
            logResultInReport(Constants.PASS + ": Pearson logo is clicked",
                    "Click on 'Pearson logo' to Navigate to 'Course View'.",
                    reportTestObj);
        } else {
            clickOnElement("CourseHomePearsonLogo",
                    "Click on 'Pearson logo' to Navigate to 'Course View'.");
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseHomePage
     */
    public GLPLearner_CourseHomePage verifyFooterHyperLinks(String locator,
            String desc) {
        FindElement element = new FindElement();
        WebElement menuOptions = element.getElement(locator);
        String value = menuOptions.getAttribute("href");
        if (value.startsWith("https") || value.startsWith("http")) {
            result = Constants.PASS + ": Verify '" + locator + "' is a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        } else {
            result = Constants.FAIL + ": '" + locator + "' is not a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        }

        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPLearner_CourseHomePage verifyFooterHyperLinks() {
        // Verify 'Copyright ©2017 Pearson Education Inc. All rights
        // reserved.'Text is present
        verifyText("DashBoardFooterCopyRightText",
                ResourceConfigurations.getProperty("copyRightLinkText"),
                "Verify 'Copyright 2017 Pearson Education Inc. All rights reserved' Text is present.");

        // Verify 'Accessibilty' is a link
        verifyFooterHyperLinks("DashBoardFooterAccessibilityLink",
                "Verify 'Accessibilty' is a link");
        // Verify 'Accessibilty' link text is present
        verifyText("DashBoardFooterAccessibilityLink",
                ResourceConfigurations.getProperty("accessibilityText"),
                "Verify 'Accessibilty' link text is present");

        // Verify 'Privacy Policy' is a link
        verifyFooterHyperLinks("DashBoardPrivacyPolicyLink",
                "Verify 'Privacy Policy' is a link");
        // Verify 'Privacy Policy' link text is present
        verifyText("DashBoardPrivacyPolicyLink",
                ResourceConfigurations.getProperty("privacyPolicyText"),
                "Verify 'Privacy Policy' link text is present");

        // Verify 'Terms & Conditions' is a link
        verifyFooterHyperLinks("DashBoardFooterTermsAndConditionsLink",
                "Verify 'Terms & Conditions' is a link");
        // Verify 'Terms & Conditions' link text is present
        verifyText("DashBoardFooterTermsAndConditionsLink",
                ResourceConfigurations.getProperty("termsConditionsText"),
                "Verify 'Terms & Conditions' link text is present");
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 26 Dec,2017
     * @description verify expected text with actual text
     * @return The object of ProductApplication_LoginPage
     */
    public String switchToFrame(String element, String text, String stepDesc) {
        APP_LOG.debug("Switch to frame: " + text);
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_SWITCH_TO_FRAME,
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
     * @author yogesh.choudhary
     * @date Dec 26, 2017
     * @description Check the event loaded for every action.
     */

    public GLPLearner_CourseHomePage verifyGTMEventForAction(String event) {
        APP_LOG.debug("Verify GTM event logging.");
        HashMap<String, String> hm = new HashMap<String, String>();
        String eventName = null;
        /* Switch to frame */
        WebDriver driver = returnDriver();
        WebElement frame = driver
                .findElement(By.xpath("//iframe[@src='about:blank']"));
        driver.switchTo().frame(frame);

        try {

            if (event.equals("VirtualPageview")) {
                verifyElementPresent("GTMVirtualPageView",
                        "Verify Virtual Page View Event");
                clickOnElement("GTMVirtualPageView",
                        "Click on Virtual Page View Event");
            }

            if (event.equals("Start Learner")) {
                verifyElementPresent("GTMStartlearner",
                        "Verify GTM Startlearner Event");
                clickOnElement("GTMStartlearner",
                        "Click on GTM Startlearner Event");
            }
            if (event.equals("Assessment Started")) {
                verifyElementPresent("GTMAssessmentStarted",
                        "Verify GTMAssessmentStarted Event");
                clickOnElement("GTMAssessmentStarted",
                        "Click on GTMAssessmentStarted Event");
            }

            if (event.equals("AssessmentItemCompleted")) {
                verifyElementPresent("GTMAssessmentItemCompleted",
                        "Verify GTMAssessmentItemCompletedw Event");
                clickOnElement("GTMAssessmentItemCompleted",
                        "Click on GTMAssessmentItemCompleted Event");
            }

            if (event.equals("AssessmentItemStarted")) {
                verifyElementPresent("GTMAssessmentItemStarted",
                        "Verify GTM AssessmentItemStarted Event");
                clickOnElement("GTMAssessmentItemStarted",
                        "Click on GTM AssessmentItemStarted Event");
            }

            if (event.equals("Exit Learner")) {
                verifyElementPresent("GTMExitLearner",
                        "Verify Exit Learner Event");
                clickOnElement("GTMExitLearner", "Click on Exit Learner Event");
            }
            if (event.equals("Resume Learner")) {
                verifyElementPresent("GTMResumeLearner",
                        "Verify Resume Learner Event");
                clickOnElement("GTMResumeLearner",
                        "Click on Resume Learner Event");
            }

            verifyElementPresent("GtmDataLayer", "Verify Data Layer");
            clickOnElement("GtmDataLayer", "Click on Data Layers");

            List<WebElement> list = findElement
                    .getElements("GTMDatalayerVariable");

            // Get Event and event parameters
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i).getText();
                if (s.contains("event")) {
                    eventName = s.substring(s.indexOf("'") + 1,
                            s.lastIndexOf("'"));
                    System.out.println(eventName);
                    // Get Event Parameters
                    // hm = getGTMEventparameteres();
                    result = Constants.PASS + ": Verify '" + event
                            + "' is a link.";
                    logResultInReport(this.result, "", this.reportTestObj);
                    break;
                }
            }

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());

            result = Constants.FAIL + ": '" + event + "' is not a link.";
            logResultInReport(this.result, "", this.reportTestObj);

        }
        driver.switchTo().defaultContent();
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date Dec 28, 2017
     * @description Check the parameters event loaded for desired action.
     */

    public HashMap<String, String> getGTMEventparameteres() {
        HashMap<String, String> hm = new HashMap<String, String>();
        APP_LOG.debug("Get GTM event parameteres/variables.");
        String eventName = null;
        String key = null;
        String value = null;
        elements = findElement.getElements("GTMDatalayerVariable");

        try {
            for (int i = 2; i < elements.size() - 1; i++) {
                String s = elements.get(i).getText();
                key = s.substring(0, s.lastIndexOf(":"));
                if (i == (elements.size() - 2)) {
                    value = s.substring(s.indexOf(":") + 1).replace("'", "");
                } else {
                    value = s.substring(s.indexOf(":") + 1, s.lastIndexOf(","))
                            .replace("'", "");
                }
                hm.put(key, value);
            }
            for (HashMap.Entry<String, String> entry : hm.entrySet()) {
                System.out.println("Key=" + entry.getKey() + "-- Value="
                        + entry.getValue());
            }
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }

        return hm;

    }

    /**
     * @author yogesh.choudhary
     * @date Dec 26, 2017
     * @description Open and Close GTM Console.
     */

    public GLPLearner_CourseHomePage openCloseGTMConsole(String action) {
        APP_LOG.debug("Open Close GTM Console.");
        String eventName = null;
        /* Switch to frame */

        try {

            Thread.sleep(3000);
            List<WebElement> list = findElement.getElements("GtmConsole");
            WebElement frame = returnDriver()
                    .findElement(By.xpath("//iframe[@src='about:blank']"));

            if (action.equals("UP")) {
                verifyElementPresent("GtmUpArrow", "Gtm Up Arrow");
                clickOnElement("GtmUpArrow", "Click Gtm Up Arrow");

            } else if (action.equals("DOWN")) {
                returnDriver().switchTo().frame(frame);
                verifyElementPresent("GtmCloseArrow",
                        "Verify Gtm Close Arrow ");
                clickOnElement("GtmCloseArrow",
                        "Click on Gtm Close Arrow Event");

            }

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
            result = Constants.FAIL + ": '"
                    + "Unexpected error while clicking up/down arrow: '"
                    + e.getMessage();
            logResultInReport(result, "Verify GTM console open/closed.",
                    reportTestObj);

        }
        returnDriver().switchTo().defaultContent();
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOG);
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
                    .getElement("CourseHomeProgressBar").getAttribute("style");
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
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

    }

    /**
     * @author nitish.jaiswal
     * @date 10 May,2018
     * @description verify Text Comparison
     */

    public void compareProgressBarLengthEquality(String text1, String text2) {

        try {
            if (text1.equals(text2)) {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is same as Expected Text : " + text2;
                logResultInReport(this.result,
                        "Verify that Progress bar is saved and is same as earlier after relogin.",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is not same as Expected Text :" + text2;
                logResultInReport(this.result,
                        "Verify that Progress bar is saved and is same as earlier after relogin.",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

    }

}
