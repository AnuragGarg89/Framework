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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPConsole_LoginPage extends BaseClass implements KeywordConstant {
    protected Logger APP_LOGS = null;
    protected Logger APP_LOG;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);

    public GLPConsole_LoginPage(ExtentTest reportTestObj, Logger APP_LOG) {
        this.APP_LOG = APP_LOG;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Successful Login into console
     * @return The object of ProductApplication_CourseHomePage
     */
    public void login(String username, String password) {
        APP_LOG.debug("Successfull Login into console application");

        try {
            WebDriver webDriver = returnDriver();
            if (webDriver instanceof SafariDriver
                    || capBrowserName.equalsIgnoreCase("Safari")) {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                TimeUnit.SECONDS.sleep(3);
                try {
                    if (webDriver
                            .findElement(By.cssSelector(".osb-modal-header"))

                            .isDisplayed()) {
                        APP_LOG.debug("Modal window is displayed");
                        TimeUnit.SECONDS.sleep(3);
                        js.executeScript("arguments[0].click();", webDriver
                                .findElement(By.cssSelector(".close")));
                        APP_LOG.debug("Modal window closed");
                    }
                } catch (Exception e) {
                    APP_LOG.debug("Popup Not Displayed");
                }
            }
            if (capBrowserName.equalsIgnoreCase("MicrosoftEdge")) {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                TimeUnit.SECONDS.sleep(3);
                try {
                    if (webDriver.findElement(By.cssSelector(".browserIcon2"))
                            .isDisplayed()) {
                        APP_LOG.debug("Modal window is displayed");
                        TimeUnit.SECONDS.sleep(3);
                        js.executeScript("arguments[0].click();", webDriver
                                .findElement(By.cssSelector(".close")));
                        APP_LOG.debug("Modal window closed");
                    }
                } catch (Exception e) {

                    APP_LOG.debug("Popup Not Displayed");
                }
            }

            enterInputData("ConsoleLoginUserName", username,
                    "Type user name in username field on Login Page.");
            enterInputData("ConsoleLoginPassword", password,
                    "Type password in password field on Login Page.");
            clickOnSignInButton();
            if (performAction
                    .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                            "SafariBrowserCheckCloseButton")
                    .contains(Constants.PASS)) {
                performAction.execute(ACTION_CLICK,
                        "SafariBrowserCheckCloseButton");
            }
            objCommonUtil.handleRunTimePopUp("ConsoleCrossButton",
                    "ConsoleBackButtonAccountSettings");
            String result = performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                    "ConsoleSkipButton");
            if (result.contains(Constants.PASS)) {
                performAction.execute(ACTION_CLICK, "ConsoleSkipButton");
                performAction.execute(ACTION_CLICK, "ConsoleSkipButton");
            }

            /*
             * if (performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
             * "EulaContinueButton").contains(Constants.PASS)) {
             * 
             * ((JavascriptExecutor) returnDriver()).executeScript(
             * "document.getElementById('acceptCheckbox').click()"); //
             * performAction.execute(ACTION_CLICK, "EulaPopUpCheckbox");
             * performAction.execute(ACTION_CLICK, "EulaContinueButton");
             * 
             * }
             */

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

        // new GLPInstructor_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Enter input value in textbox
     * @return The object of GLPInstructor_ConsoleLoginPage
     */

    public GLPConsole_LoginPage enterInputData(String locator, String value,
            String message) {
        APP_LOG.debug("Enter the input value- " + value);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                locator, value);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Click on specific element
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public GLPConsole_LoginPage clickOnElement(String locator, String message) {
        APP_LOG.debug(locator + "Click on the Element.");
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLICK, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Click on specific element
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public GLPConsole_LoginPage clickViaJS(String locator, String message) {
        APP_LOG.debug(locator + "Click on the Element.");
        // this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
        // locator);
        this.result = this.performAction.execute(ACTION_CLICK, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Verify element is present
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOG.debug(locator + "Element is present");
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
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Enter start and end date
     * @return The object of
     */

    public GLPConsole_LoginPage enterStartAndEndDate() {
        APP_LOG.debug("Enter start and end date");
        DateTime dt = new DateTime();
        String sevenDaysAhead = dt.plusDays(7).toString("MM/dd/yyyy");
        enterInputData("ConsoleCourseStartDateTextBox",
                dt.toString("MM/dd/yyyy"), "Enter start date as current date.");
        enterInputData("ConsoleCourseEndDateTextBox", sevenDaysAhead,
                "Enter end date of 7 days later.");
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 6 Dec,2017
     * @description Create course on console
     */

    public void courseCreation() {
        APP_LOG.debug("Enter start and end date");

        clickOnElement("ConsoleSearchForMaterialSearchBox",
                "Click on Console SearchForMaterial SearchBox.");
        clickOnElement("ConsoleMaterialLink",
                "Click on Humanities material link.");
        clickOnElement("ConsoleCourseLink", "Click on QAB socialogy link.");
        clickOnElement("ConsoleCreateCourseButton",
                "Click on create course button.");
        String courseName = "Course_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        // objCommonUtil.handleRunTimePopUp("ConsoleCrossButton");
        enterInputData("ConsoleCourseNameTextBox", courseName,
                "Enter course name.");
        clickOnElement("ConsoleCourseSubmitButton", "Click on Next button.");
        enterStartAndEndDate();
        enterInputData("ConsoleStartTimeTextBox",
                ResourceConfigurations.getProperty("courseStartTime"),
                "Enter couse start time.");
        WebDriver webDriver = returnDriver();
        if (webDriver instanceof SafariDriver || browser.contains("Safari")) {
            moveToElement("ConsoleEndTimeTextBox", "Move on End time.");
        } else {
            enterInputData("ConsoleEndTimeTextBox",
                    ResourceConfigurations.getProperty("courseEndTime"),
                    "Enter couse end time.");
        }
        clickOnElement("ConsoleCourseSaveButton", "Click on Save button.");

    }

    /**
     * @author tarun.gupta1
     * @date 18 September,2017
     * @description verify expected text with actual text
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        APP_LOG.debug("Verify text: " + text);
        System.out.println("");
        // this.APP_LOGS.debug(stepDesc);
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
     * @author nisha.pathria
     * @date 18 Dec,2017
     * @description Move to element
     * @return The object of GLPInstructor_ConsoleLoginPage
     */

    public GLPConsole_LoginPage moveToElement(String locator, String message) {
        APP_LOG.debug("Movingto element - " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.performAction.execute(ACTION_MOVE_TO_ELEMENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 18 Dec,2017
     * @description Handling signon button on Safari
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public void clickOnSignInButton() {

        WebDriver webDriver = returnDriver();
        if (webDriver instanceof SafariDriver
                || capBrowserName.equalsIgnoreCase("Safari")) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#mainButton")));

        } else {
            clickOnElement("ConsoleSignInButton",
                    "Click on 'SignIn' button on 'SignOn' Page.");
        }

    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPConsole_LoginPage verifyFooterHyperLinks(String locator,
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

        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPConsole_LoginPage verifyFooterHyperLinks() {
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
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 14 Nov,2017
     * @description Accessibility link open in new tab.
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public void verifyFooterLinksBackButtonDisabled() {

        APP_LOG.debug("Footer link open in new tab.");
        verifyDisNewTab("DashBoardFooterAccessibilityLink",
                "Accessibility Information | Revel | Pearson",
                "Verify Accessibility link open in new tab.");
        verifyDisNewTab("DashBoardPrivacyPolicyLink", "Pearson Privacy Policy",
                "Verify Privacy Policy link open in new tab.");
        verifyDisNewTab("DashBoardFooterTermsAndConditionsLink",
                "Pearson Terms of Use",
                "Verify Terms and Conditions link open in new tab.");
    }

    public void verifyDisNewTab(String locator, String expectedTitle,
            String message) {

        APP_LOG.debug("VerifyLinkOpenInNewTab for Instructor");
        try {
            // Getting the current window handle
            String parentWindow = returnDriver().getWindowHandle();
            ArrayList<String> windows = new ArrayList<String>();
            int i = 0;

            // Adding current window to the 0th index in a list
            windows.add(i, parentWindow);
            clickOnElement(locator,
                    "Click on '" + locator + "' link in footer.");
            APP_LOG.info(locator + " is clicked.");

            // Using sleep to wait for the new window to load
            Thread.sleep(10000);

            // Getting all the window handles after the footer link has been
            // clicked
            Set<String> uniqueWindow = returnDriver().getWindowHandles();

            // Adding the window handles other than the parent window in the
            // list from 1st index
            for (String iterator : uniqueWindow) {
                if (!iterator.equals(parentWindow)) {
                    windows.add(++i, iterator);
                }
            }

            // Getting the window handle string of the last window in the list
            String footerLinkNewWindow = windows.get(windows.size() - 1);

            // Assert that more than one windows are opened currently and the
            // window the application switched to has the same title as expected
            if (windows.size() > 1) {
                returnDriver().switchTo().window(footerLinkNewWindow);
                performAction.execute(ACTION_WAIT_FOR_TITLE);
                if (returnDriver().getTitle().equals(expectedTitle)) {
                    this.result = Constants.PASS
                            + ": Correct window has opened, since actual page title '"
                            + returnDriver().getTitle()
                            + "' and expected page title '" + expectedTitle
                            + "' are same.";
                    logResultInReport(this.result, message, this.reportTestObj);
                    // Closing the footerLink window
                    returnDriver().close();
                    // Switch to the parent Window
                    returnDriver().switchTo().window(parentWindow);
                }
            } else {
                this.result = Constants.FAIL
                        + ": Footer link window doesn't opened in new tab";
                logResultInReport(this.result, message, this.reportTestObj);
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
            this.result = Constants.FAIL
                    + ": Exception while switching to window : "
                    + t.getMessage();
            logResultInReport(this.result, message, this.reportTestObj);
        }

    }

    /**
     * @author nisha.pathria
     * @date 11 Nov,2017
     * @description Switch to new tab
     * @return The object of ProductApplication_LoginPage
     */
    public GLPConsole_LoginPage switchToNewTab(String expectedTitle,
            String pageContent, String description) {

        APP_LOG.debug("Accessibility link open in new tab.");
        try {

            Set<String> openWindows = returnDriver().getWindowHandles();
            String parentWindow = null;
            String newWindow = null;

            for (String string : openWindows) {
                returnDriver().switchTo().window(string);
                if (!returnDriver().getTitle()
                        .equalsIgnoreCase(expectedTitle)) {
                    parentWindow = string;

                } else {
                    newWindow = string;
                }
            }

            int tabCountBeforeTab = openWindows.size();

            if (tabCountBeforeTab > 1) {
                result = Constants.PASS + " : Tab count is 2.";
                logResultInReport(this.result, "Verify link open is new tab.",
                        this.reportTestObj);

                Thread.sleep(15000);

                returnDriver().switchTo().window(newWindow);
                verifyContent(pageContent, description);
                returnDriver().switchTo().window(newWindow).close();
                returnDriver().switchTo().window(parentWindow);
            } else {
                result = Constants.FAIL + "Multiple tabs are already open.";
                logResultInReport(this.result,
                        "Verify if only one window is open.",
                        this.reportTestObj);
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPConsole_LoginPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 16 Nov,2017
     * @description Verify content
     */
    public void verifyContent(String pageContent, String description) {

        try {
            String result = performAction.execute(ACTION_VERIFY_NOT_VISIBLE,
                    "FooterLinkText");
            String bodyContent = null;
            if (result.equalsIgnoreCase("pass")) {
                bodyContent = returnDriver()
                        .findElement(By.className("extra-light-grey-bg"))
                        .getText();
                bodyContent = bodyContent.replaceAll("\n", "");
                bodyContent = bodyContent.replaceAll(" ", "");
                bodyContent = bodyContent.replaceAll(" ", "");
            } else {
                bodyContent = returnDriver()
                        .findElement(
                                By.className("eulacol col-9 push-2 pull-1"))
                        .getText();
                bodyContent = bodyContent.replaceAll("\n", "");
                bodyContent = bodyContent.replaceAll(" ", "");
                bodyContent = bodyContent.replaceAll(" ", "");
            }

            if (bodyContent.contains(pageContent)) {
                result = Constants.PASS + ": " + "Text on body '" + bodyContent
                        + "' is matched with expected text '" + pageContent
                        + "'.";
                logResultInReport(result, description, this.reportTestObj);
            } else {
                result = Constants.FAIL + ": " + "Text on body '" + bodyContent
                        + "' is not matched with expected text '" + pageContent
                        + "'.";
                logResultInReport(result, description, this.reportTestObj);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author nisha.pathria
     * @date 25 Dec,2017
     * @description Verify back button is disabled.
     */
    public void verifyBrowserBackButtonEnabledDisabled() {

        JavascriptExecutor js = (JavascriptExecutor) returnDriver();
        String length = js.executeScript("return window.history.length;")
                .toString();
        if ("1".equals(length)) {
            this.result = Constants.FAIL + ": browser back button is disabled";
            logResultInReport(result, "Verify browser back button is enabled.",
                    this.reportTestObj);
        } else {

            this.result = Constants.PASS + ": browser back button is enabled";
            logResultInReport(result, "Verify browser back button is enabled.",
                    this.reportTestObj);
        }
    }

    /**
     * @author nisha.pathria
     * @date 26 Dec,2017
     * @description Get internal url
     */
    public String getInternalURL() {
        String url = "";
        try {
            url = performAction.execute(ACTION_GET_CURRENT_URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return url;
    }

    /**
     * @author nisha.pathria
     * @date 26 Dec,2017
     * @description Open internal url
     */
    public void openInternalURL(String url) {

        try {
            String result = performAction.execute(ACTION_NAVIGATE_URL, "", url);
            if (result.contains(Constants.PASS)) {
                result = Constants.PASS + ": Internal URL is entered.";
                logResultInReport(result, "Enter internal url.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL + ": Unable to open Internal URL.";
                logResultInReport(result, "Enter internal url.",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author nisha.pathria
     * @date 26 Dec,2017
     * @description Open internal url
     */
    public void browserBack() {

        try {
            String result = performAction.execute(ACTION_NAVIGATE_BROWSER_BACK);
            if (result.contains(Constants.PASS)) {
                result = Constants.PASS + ": Navigated to browser back.";
                logResultInReport(result, "Verify browser back.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL
                        + ": Unable to navigate to browser back.";
                logResultInReport(result, "Verify browser back.",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
