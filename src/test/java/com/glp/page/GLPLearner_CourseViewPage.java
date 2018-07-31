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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_CourseViewPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj = null;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private FindElement findElement = new FindElement();

    public GLPLearner_CourseViewPage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author Pallavi.tyagi
     * @date 12 July,2017
     * @description Verify element is present
     * @return String PASS/FAIL
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
     * @author tarun.gupta1
     * @date 18 September,2017
     * @description verify expected text with actual text
     * @return The object of GLPInstructor_ConsoleLoginPage
     */
    public String verifyTextContains(String element, String text,
            String stepDesc) {
        APP_LOG.debug("Verify text: " + text);
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
     * @author Mayank.mittal
     * @date 12 July,2017
     * @description Click on specific element
     * @return The object of ProductApplication_LoginPage
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
     * @author pallavi.tyagi
     * @date 15 Nov,2017
     * @description Click on Coursetile element
     */
    public void clickOnCourseTile() {
        APP_LOGS.debug("Click on course tile");
        try {
            boolean isAccessNeeded = verifyDaysRemaniningRibbon();
            if (!isAccessNeeded) {
                clickOnElement("CourseTileStudent", "Click on course tile");
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

    }

    /**
     * @author Lekh.bahl
     * @date 02 Aug,2017
     * @description Click on course tile and verify the start button
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseHomePage clickCourseTileByIndex(String element,
            String position, String message) {
        APP_LOGS.debug("Click on course tile:" + (position + 1));
        this.APP_LOGS.debug(message);
        this.result = this.performAction.execute(ACTION_CLICK_INDEX_POSITION,
                element, position);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nisha.Pathria
     * @date 02 Aug,2017
     * @description Click on start button
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_DiagnosticTestPage clickDiagnosticStartButton() {
        clickOnElement("DiagnosticStartButton",
                "Click on 'Start' button of Diagnostic Test.");
        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 14 Sep,2017
     * @description Navigate to welcome screen
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseHomePage navigateToWelcomeScreenLearner() {
        APP_LOGS.debug("Navigate to welcome screen");
        try {
            clickOnElement("CourseTileStudent",
                    "Click on Course Card in 'List' view.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 14 Sep,2017
     * @description Verify Rio Course Start Diagnostic Button
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage navigateToCourseView() {
        APP_LOGS.debug("Verify User is navigated to course view screen.");
        clickOnElement("PearsonLogo",
                "Click on 'Pearson logo' to Navigate to 'Course View'.");
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 13 Sep,2017
     * @description Verify Menu Options on course view page
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyMenuOptions() {
        APP_LOGS.debug(
                "Verify menu options are displayed on 'Course View' Page after successfull login");

        int i = 0;
        try {
            FindElement element = new FindElement();
            List<WebElement> menuOptions = element
                    .getElements("CourseViewMenuOptions");
            for (i = 0; i < menuOptions.size(); i++) {
                if (menuOptions.get(i).getText().trim()
                        .equals(ResourceConfigurations.getProperty(
                                "learnerViewPageMenuOption" + (i + 1)))) {
                    result = Constants.PASS
                            + ": Verify menu option are presnt as expected text and sequence";
                    logResultInReport(result, "Verify Menu Option '"
                            + ResourceConfigurations.getProperty(
                                    "learnerViewPageMenuOption" + (i + 1))
                            + "' is displayed on Home page.",
                            this.reportTestObj);
                }
            }
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 17 Sep,2017
     * @description verify that by default list view should be selected and
     *              course tile should be displayed in list view
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyDefaultListView() {
        verifyElementAttributeValue("CourseViewListIcon", "Class", "active",
                "Verify Grid view is highlighted after Switching to Grid view.");
        verifyElementAttributeValue("CourseViewRioTile", "Class",
                "grid-listview",
                "Verify course tile is displayed in Grid view after Switching to Grid view.");
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 17 Sep,2017
     * @description Verify learner is able to switch the view from list to grid
     *              or from grid to list
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage switchToListOrGridView(String locator,
            String description) {
        APP_LOGS.debug(
                "Verify learner is able to switch the view from list to grid.");
        try {
            clickOnElement(locator, description);
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 17 Sep,2017
     * @description Verify learner is able to switch the view from list to grid
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifySwitchedToGridView() {
        APP_LOGS.debug(
                "Verify learner is able to switch the view from list to grid.");
        verifyElementAttributeValue("CourseViewGridIcon", "Class", "active",
                "Verify Grid view is highlighted after Switching to Grid view.");
        verifyElementAttributeValue("CourseViewRioTile", "Class",
                "grid-columnView",
                "Verify course tile is displayed in Grid view after Switching to Grid view.");
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
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
     * @author Nitish.Jaiswal
     * @date 19 Jan,2018
     * @description Verify Logout
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPConsole_LoginPage verifyLogout() {
        try {
            clickOnElement("CourseViewUserName",
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
     * @author tarun gupta
     * @date 20 Sep,2017
     * @description Verify Course navigation to course material page
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage navigateToCourseMaterialPage() {
        clickOnElement("CourseViewLaunchCourseBtn",
                "Click on 'Launch course button' in 'List' view.");
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 29 Sep,2017
     * @description Verify alert on overview poage if any
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage handleAlertMsgPopup() {
        APP_LOGS.debug("Verify Rio Course name and its description");
        result = performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "CourseViewAlertButton");
        if (result.contains(Constants.PASS)) {
            WebElement element = findElement
                    .getElement("CourseViewAlertButton");
            element.click();
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author tarun.gupta1
     * @date 11 Nov,2017
     * @description Navigate to Course material from Course Voew screen
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           navigateToLearnerDashboardFromCourseView() {

        APP_LOG.debug("Navigate to Course Material Page");
        try {
            clickOnElement("CourseViewLaunchCourseButton",
                    "Click on 'Launch Course button'");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
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
     * @date 14 Nov,2017
     * @description Accessibility link open in new tab.
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public GLPLearner_CourseViewPage VerifyFooterLinksOpenInNewTab() {

        APP_LOG.debug("Footer link open in new tab.");
        VerifyLinkOpenInNewTab("DashBoardFooterAccessibilityLink",
                ResourceConfigurations.getProperty(
                        "accessibilityInformationRevelPearsonText"),
                "Verify Accessibility link open in new tab.");
        VerifyLinkOpenInNewTab("DashBoardPrivacyPolicyLink",
                ResourceConfigurations.getProperty("pearsonPrivacyPolicyText"),
                "Verify Privacy Policy link open in new tab.");
        VerifyLinkOpenInNewTab("DashBoardFooterTermsAndConditionsLink",
                ResourceConfigurations.getProperty("pearsonTermsOfUseText"),
                "Verify Terms and Conditions link open in new tab.");

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 14 Nov,2017
     * @description FooterLinks Open in new tab.
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public GLPLearner_CourseViewPage VerifyLinkOpenInNewTab(String locator,
            String expectedTitle, String message) {

        APP_LOG.debug("VerifyLinkOpenInNewTab for Learner");
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
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date 14 Nov,2017
     * @description :Verify element not present.
     */
    public GLPLearner_CourseViewPage verifyElementNotPresent(String locator,
            String message) {
        APP_LOGS.debug(locator + "Element is not present");
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 23 Nov,2017
     * @description Verify pearson logo and user anme
     * @return String PASS/FAIL
     */
    public void verifyPearsonLogoAndUserName() {

        APP_LOGS.debug("Verify Pearson Logo and User Name");
        verifyElementPresent("PearsonLogo", "Verify Pearson Logo is Displayed");
        verifyElementPresent("CourseViewUserName",
                "Verify Given/User Name is present");
    }

    /**
     * @author Nitish.Jaiswal
     * @date 30 Nov,2017
     * @description Verify and wait course is visible after login
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage waitForCourseToAppearInstructor() {
        APP_LOGS.debug("Verify Rio Course name and its description");

        int counter = 0;
        try {
            result = performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "CourseTileInstructor");
            if (result.contains(Constants.FAIL)) {
                handleAlertMsgPopup();
                while (counter < 20) {
                    WebElement element1 = findElement
                            .getElement("CourseViewPastCourseMenuOption");
                    element1.click();
                    WebElement element2 = findElement
                            .getElement("CourseViewCurrentCourseMenuOption");
                    element2.click();
                    handleAlertMsgPopup();
                    result = performAction.execute(
                            ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                            "CourseTileInstructor");
                    if (result.contains(Constants.PASS)) {
                        break;
                    }
                    try {
                        counter = counter + 1;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        APP_LOG.error(
                                "Error occured while wait for course to appears"
                                        + e);
                    }
                }
            }
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyFooterHyperLinks(String locator,
            String desc) {
        FindElement element = new FindElement();
        WebElement menuOptions = element.getElement(locator);
        String value = menuOptions.getAttribute("href");
        if (value.startsWith("https")) {
            result = Constants.PASS + ": Verify '" + locator + "' is a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        } else {
            result = Constants.FAIL + ": '" + locator + "' is not a link.";
            logResultInReport(this.result, desc, this.reportTestObj);
        }

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPLearner_CourseViewPage verifyFooterHyperLinks() {
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
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Header elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyDiagonasticPageHeaders() {

        verifyElementNotPresent("PearsonLogo", "Person Logo");
        verifyElementNotPresent("UserNameTitle", "Username Title");

        result = Constants.PASS + ": Verify Diagonatoic Page Header";
        logResultInReport(this.result, "Verify Diagonatoic Page Header",
                this.reportTestObj);

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Footer elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyDiagonasticPageFooters() {

        verifyElementNotPresent("InstructorDashBoardFooterCopyRightText",
                "Verify text in left bottom corner of footer section.");
        verifyElementNotPresent("InstructorDashBoardFooterAccessibilityLink",
                "Verify Accessibility Link");
        verifyElementNotPresent("InstructorDashBoardPrivacyPolicyLink",
                "Verify Privacy Policy Link");
        verifyElementNotPresent(
                "InstructorDashBoardFooterTermsAndConditionsLink",
                "Verify Term and condition");

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Header elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public GLPLearner_CourseViewPage
           verifyDiagonasticNonContentPageHeadersLogo() {

        verifyElementPresent("PearsonLogo", "Person Logo");
        result = Constants.PASS
                + ": Verify Diagonastic NonContent Page Headers Logo.";
        logResultInReport(this.result,
                " Verify Diagonastic NonContent Page Headers Logo.",
                this.reportTestObj);

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Header elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public GLPLearner_CourseViewPage
           verifyDiagonasticNonContentPageHeadersUserName() {

        verifyElementPresent("UserNameTitle", "Username Title");
        result = Constants.PASS
                + ": Verify Diagonastic NonContent Page UserName.";
        logResultInReport(this.result,
                " Verify Diagonastic NonContent Page UserName.",
                this.reportTestObj);
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Header elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public synchronized String getQuestionIdOnUI() {

        String question = "";
        String regex1 = "Q-\\w\\d{7,8}";
        Pattern regex = Pattern.compile(regex1);
        try {

            String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
            String netData = ((JavascriptExecutor) returnDriver())
                    .executeScript(scriptToExecute).toString();
            Matcher matcherString = regex.matcher(netData);
            while (matcherString.find()) {
                question = matcherString.group();
            }
            APP_LOG.debug("Extarcted questionId--> '" + question + "'");
            System.out.println(question);
            return question;
        } catch (Exception e) {
            APP_LOG.error("Exception in getQuestionIdOnUI method : " + e);
            return null;
        }
    }

    /**
     * @author yogesh.choudhary
     * @date 29 Nov,2017
     * @description Check if Footer elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    public GLPLearner_CourseViewPage verifyDiagonasticNonContentPageFooters() {
        verifyElementPresent("DashBoardFooterAccessibilityLink",
                "Verify Accessibility Link in footer section");
        verifyElementPresent("DashBoardPrivacyPolicyLink",
                "Verify Privacy Policy Link in footer section");
        verifyElementPresent("DashBoardFooterTermsAndConditionsLink",
                "Verify Term and condition Link in footer section");

        result = Constants.PASS
                + ": Verify Diagonastic NonContent Page footer.";
        logResultInReport(this.result,
                " Verify Diagonastic NonContent Page footer.",
                this.reportTestObj);
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 5 Dec,2017
     * @description verify expected text with actual text
     * @return Element Text
     */
    public String getText(String element, String stepDesc) {
        APP_LOG.debug("Get Element Text");
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        String text = this.performAction.execute(ACTION_GET_TEXT, element);
        logResultInReport(this.result, "", this.reportTestObj);
        return text;
    }

    /**
     * @author yogesh.choudhary
     * @date 5 Dec,2017
     * @description Check if Footer elements are link.
     * @return The object of GLPLearner_CourseViewPage
     */
    @SuppressWarnings("unused")
    public GLPLearner_CourseViewPage verifyDistractor(String distractor,
            String userName) {
        GLPLearner_DiagnosticTestPage objProductApplication_DiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                reportTestObj, APP_LOG);
        APP_LOGS.debug(
                "Verify disctrator on UI based on distractor value fetched from response");
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        // Distractor Value Returned from Response
        String distractortype = objRestUtil.getDistractor(userName, distractor);
        String distractorvalue = null;
        try {

            if (distractortype == "None") {
                distractorvalue = getText("DistractorValue",
                        "Get Element Text");
                if (!(distractorvalue.contains("A.")
                        || distractorvalue.contains("B.")
                        || distractorvalue.contains("C.")
                        || distractorvalue.contains("D.")
                        || distractorvalue.contains("1.")
                        || distractorvalue.contains("2.")
                        || distractorvalue.contains("3.")
                        || distractorvalue.contains("4."))) {
                    result = Constants.PASS
                            + ": Distractor value diaplyed on UI";
                    logResultInReport(this.result,
                            "Distractor value diaplyed on UI",
                            this.reportTestObj);
                    return new GLPLearner_CourseViewPage(reportTestObj,
                            APP_LOG);
                }
            }
            if (distractortype == "numeric") {
                distractorvalue = getText("DistractorValue",
                        "Get Element Text");

                if (distractorvalue.contains("1.")
                        && distractorvalue.contains("2.")
                        && distractorvalue.contains("3.")
                        && distractorvalue.contains("4.")) {
                    result = Constants.PASS
                            + ": Distractor value diaplyed on UI";
                    logResultInReport(this.result,
                            "Distractor value diaplyed on UI",
                            this.reportTestObj);
                    return new GLPLearner_CourseViewPage(reportTestObj,
                            APP_LOG);

                }

            }
            if (distractortype == null) {
                distractorvalue = getText("DistractorValue",
                        "Get Element Text");
                if (distractorvalue.contains("A.")
                        && distractorvalue.contains("B.")
                        && distractorvalue.contains("C.")
                        && distractorvalue.contains("D.")) {
                    result = Constants.PASS
                            + ": Distractor value diaplyed on UI";
                    logResultInReport(this.result,
                            "Distractor value diaplyed on UI",
                            this.reportTestObj);
                    return new GLPLearner_CourseViewPage(reportTestObj,
                            APP_LOG);

                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception in verifying distractor" + e);

            result = Constants.FAIL;
            logResultInReport(this.result,
                    "Distractor not displyed correctly on UI",
                    this.reportTestObj);

        }

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 25 Jan,2017
     * @description Grant access to student
     * @return boolean
     */
    public boolean verifyDaysRemaniningRibbon() {
        APP_LOGS.debug("Grant test acces to student");
        try {
            if (performAction
                    .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                            "DaysRemainRibbonCourseTileStudent")
                    .contains(Constants.PASS)) {
                performAction.execute(ACTION_CLICK,
                        "DaysRemainRibbonCourseTileStudent");
                if (performAction
                        .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                                "RioTestAccessLinkStudent")
                        .contains(Constants.PASS)) {
                    performAction.execute(ACTION_CLICK,
                            "RioTestAccessLinkStudent");
                    performAction.execute(ACTION_CLICK,
                            "RioTestGrantButtonStudent");
                    return true;
                } else {
                    performAction.execute(ACTION_CLICK_BY_JS,
                            "RioTestPopUpStudent");
                    return false;
                }
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return false;
    }

    /**
     * @author Nitish.Jaiswal
     * @date 25 Jan,2017
     * @description Grant access to student
     * @return boolean
     */
    public void verifyCourseTilePresent() {
        APP_LOGS.debug(
                "Verify course tile is presnr and Grant test acces to student");
        try {
            // CHeck if access to course is needed
            verifyElementPresent("CourseTileStudent",
                    "Verify Learner is logged in successfully.");
            boolean isAccessNeeded = verifyDaysRemaniningRibbon();
            if (!isAccessNeeded) {
                navigateToWelcomeScreenLearner();
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
    }

    /**
     * @author lekh.bahl
     * @date 30 Mar,2018
     * @description Navigate to course home page from course view page
     */
    public void navigateToCourseHomePage() {
        APP_LOGS.debug("Click on course tile");
        try {
            boolean isAccessNeeded = verifyDaysRemaniningRibbon();
            if (!isAccessNeeded) {
                clickOnElement("CourseTileStudent", "Click on course tile");
            }

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

    }

    /**
     * @author Nitish.Jaiswal
     * @date 19 Sep,2017
     * @description Click on account settings option
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CourseViewPage clickOnSettingsOption() {
        try {
            clickOnElement("CourseViewUserName",
                    "Click on User name to open Signout DropDown.");
            clickOnElement("CourseViewAccountSettingsText",
                    "Click on Settings option.");

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 19 Sep,2017
     * @description type value in element
     */
    public void typeText(String locator, String stepDesc, String value) {
        try {
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    locator);
            this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                    locator, value);
            logResultInReport(this.result, stepDesc, this.reportTestObj);

        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
    }

}