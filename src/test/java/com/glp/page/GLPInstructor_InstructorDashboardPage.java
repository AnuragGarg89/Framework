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

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.FileDownloader;
import com.autofusion.util.FileUtil;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPInstructor_InstructorDashboardPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();

    public GLPInstructor_InstructorDashboardPage(ExtentTest reportTestObj,
            Logger APP_LOG) {
        this.APP_LOGS = APP_LOG;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author pallavi.tyagi
     * @date 10 April ,17
     * @description Click on Tab
     */
    public void clickOnElement(String element, String stepDesc) {

        APP_LOG.debug("Click on the Element: " + element);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        if ("Safari".equalsIgnoreCase(capBrowserName)) {
            this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                    element);
        } else {

            this.result = this.performAction.execute(ACTION_CLICK, element);
            if (this.result.contains(Constants.FAIL)) {
                this.result = this.performAction.execute(ACTION_CLICK_BY_JS,
                        element);
            }

        }
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify element is present
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyElementPresent(String locator, String message) {
        APP_LOG.debug("Element is present :" + locator);
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
     * @author akshay.chimote
     * @date 19 May, 2017
     * @description To verify the CSS Value of an element
     */
    public String verifyElementCSSValue(String element, String cssName,
            String verifyText, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_CSS_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, cssName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Click on specific element
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public GLPInstructor_ManagementDashboardPage switchToManagementTab() {
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "InstructorDashBoardManagementTab");
        APP_LOG.debug("Switch to Management Tab");
        clickOnElement("InstructorDashBoardManagementTab",
                "Switch to Management Tab");
        return new GLPInstructor_ManagementDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Click on specific element
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public GLPInstructor_InstructorDashboardPage switchToPerformaceTab() {
        APP_LOG.debug("Switch to Performance Tab");
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "InstructorDashBoardPerformanceTab");
        this.result = this.performAction.execute(ACTION_CLICK,
                "InstructorDashBoardPerformanceTab");
        logResultInReport(this.result,
                "Click on performance tab to switch to it.",
                this.reportTestObj);
        return this;
    }

    /**
     * @author tarun.gupta1
     * @date 12 July,2017
     * @description Verify text message
     * @return The object of ProductApplication_WelcomeInstructorPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        this.APP_LOG.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element,
                text);
        if (result.contains("PASS")) {
            result = "PASS: " + element + " contains the correct text i.e. "
                    + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: " + element
                    + " does not contains the correct text i.e. " + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;
    }

    public GLPInstructor_InstructorDashboardPage verifyElementAttributeValue(
            String element, String attributeName, String verifyText,
            String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author Pallavi.Tyagi
     * @date 3 Oct,2017
     * @description Verify page title of performance dashboard
     * @return The object of ProductApplication_CourseHomePage
     */

    public GLPInstructor_InstructorDashboardPage
           verifyPerformanceDashboardTitle(String exptectedTitle,
                   String description) {
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE,
                exptectedTitle);
        logResultInReport(this.result, description, this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author pallavi.tyagi
     * @date 3 Oct,2017
     * @description Verify element is present
     * @return The object of ProductApplication_courseHomePage
     */
    public String getText(String locator) {
        APP_LOG.debug("Element is present: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
        return valueText;
    }

    /**
     * @author Pallavi.tyagi
     * @date 3 Oct,2017
     * @description Verify Completion percentage is displayed or not.
     * @return The object of ProductApplication_PerformanceDashboardPage.
     */
    public GLPInstructor_InstructorDashboardPage verifyCompletionPercentage() {

        String completionText = getText("PerformanceDashboardCompletionValue");
        if (completionText.equalsIgnoreCase("")) {

            this.result = Constants.FAIL
                    + ": Completion percentage is not displayed in completion card";
            logResultInReport(this.result,
                    "Completion percentage is not displayed in completion card",
                    this.reportTestObj);
        } else {
            this.result = Constants.PASS
                    + ": Completion percentage is displayed in completion card";
            logResultInReport(this.result,
                    "Completion percentage is displayed in completion card",
                    this.reportTestObj);

        }

        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 4 oct 2017
     * @description :Verify element not present.
     */
    public GLPInstructor_InstructorDashboardPage
           verifyElementNotPresent(String locator, String message) {
        try {
            APP_LOG.debug(locator + "Element is not present");
            // Adding wait for synchronization
            Thread.sleep(2000);
            this.result = this.performAction
                    .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
            logResultInReport(this.result, message, this.reportTestObj);
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception occurred while verifying element's non-existence");
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage
           verifySectionValue(String section, String element) {
        String numberOfStudent = getText(element);
        if (numberOfStudent.matches("[0-9]+") && numberOfStudent.length() > 0) {
            this.result = Constants.PASS + ": Total " + section
                    + "section values are present";
            logResultInReport(this.result,
                    "Total " + section + "section values are present",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Total " + section
                    + "section values are not present";
            logResultInReport(this.result,
                    "Total " + section + "section values are not present",
                    this.reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage clickOnFirstModuleTime() {
        FindElement element = new FindElement();
        List<WebElement> timeOnTask = element
                .getElements("InstructorDashBoardModuleTime");
        timeOnTask.get(0).click();
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage verifyTimeBarsForALLLO() {
        FindElement element = new FindElement();
        try {

            List<WebElement> allTimeOnTask = element
                    .getElements("InstructorDashBoardModuleTime");
            int verticleCoordinates = 300;
            for (int i = 0; i < allTimeOnTask.size(); i++) {
                allTimeOnTask.get(i).click();
                verifyElementPresent(
                        "InstructorDashBoardChartColorBarPlacedOut",
                        "Verify placed out bar is present for Module "
                                + (i + 1));
                verifyElementPresent(
                        "InstructorDashBoardChartColorBarCompleted",
                        "Verify completed bar is present for Module "
                                + (i + 1));
                verifyElementPresent(
                        "InstructorDashBoardChartColorBarInProgress",
                        "Verify in progress bar is present for Module "
                                + (i + 1));
                verifyElementPresent(
                        "InstructorDashBoardChartColorBarNotStarted",
                        "Verify not started bar is present for Module "
                                + (i + 1));

                clickOnElement("InstructorDashBoardChartCollapseButton",
                        "Click on the collapse icon on the performance dashboard for Module "
                                + (i + 1));

                objCommonUtil.scrollWebPage(0, verticleCoordinates);
                verticleCoordinates = verticleCoordinates + 100;
            }
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception comes in ProductApplication_InstructorDashboardPage method"
                            + e);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage
           verifyTimeWithClockForEachLO() {
        FindElement element = new FindElement();
        try {
            List<WebElement> timeTextOnTask = element
                    .getElements("InstructorDashBoardModuleClockIcon");
            List<WebElement> timerIconOnTask = element
                    .getElements("InstructorDashBoardModuleTime");
            if (timeTextOnTask.size() != timerIconOnTask.size()) {
                this.result = Constants.FAIL
                        + ": total time fields doesnot match with clock icon";
                logResultInReport(this.result,
                        "total time fields doesnot match with clock icon",
                        this.reportTestObj);
            } else {
                for (int i = 0; i < timeTextOnTask.size(); i++) {
                    if (timeTextOnTask.get(i).isDisplayed()
                            && timerIconOnTask.get(i).isDisplayed()) {
                        this.result = Constants.PASS
                                + ": time and timer icon both are present for Module "
                                + (i + 1);
                        logResultInReport(this.result,
                                "time and timer icon both are present for Module "
                                        + (i + 1),
                                this.reportTestObj);
                    } else {
                        this.result = Constants.FAIL
                                + ": time and timer icon  are not present for Module "
                                + (i + 1);
                        logResultInReport(this.result,
                                "time and timer icon both are not present for Module "
                                        + (i + 1),
                                this.reportTestObj);
                    }
                }

            }

        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception comes in verifyTimeWithClockForEachLO method"
                            + e);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);

    }

    public GLPInstructor_InstructorDashboardPage
           verifyCompletionRateWithTickIconForEachLO() {
        FindElement element = new FindElement();
        try {
            List<WebElement> completionPercent = element
                    .getElements("InstructorDashBoardModuleCompletionPercent");
            List<WebElement> tickIcon = element
                    .getElements("InstructorDashBoardTickIcon");
            if (completionPercent.size() != tickIcon.size()) {
                this.result = Constants.FAIL
                        + ": total Completion percent doesnot match with Tick icon";
                logResultInReport(this.result,
                        "total Completion percent doesnot match with Tick icon",
                        this.reportTestObj);
            } else {
                for (int i = 0; i < completionPercent.size(); i++) {
                    if (completionPercent.get(i).isDisplayed()
                            && tickIcon.get(i).isDisplayed()) {
                        this.result = Constants.PASS
                                + ": completion percent and tick icon both are present for Module "
                                + (i + 1);
                        logResultInReport(this.result,
                                "Completion percent and tick icon both are present for Module "
                                        + (i + 1),
                                this.reportTestObj);
                    } else {
                        this.result = Constants.FAIL
                                + ": completion percent and tick icon both are not present for Module "
                                + (i + 1);
                        logResultInReport(this.result,
                                "Completion percent and tick icon both are not present for Module "
                                        + (i + 1),
                                this.reportTestObj);
                    }
                }

            }

        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception comes in verifyCompletionRateWithTickIconForEachLO method"
                            + e);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);

    }

    public GLPInstructor_InstructorDashboardPage
           verifyTickIconForEachModuleClickable() {
        FindElement element = new FindElement();
        try {

            List<WebElement> tickPercent = element
                    .getElements("InstructorDashBoardTickIcon");
            int verticleCoordinates = 300;
            for (WebElement tick : tickPercent) {
                tick.click();
                verifyElementPresent(
                        "InstructorDashBoardChartColorBarPlacedOut",
                        "Verify placed out bar is present");
                clickOnElement("InstructorDashBoardChartCollapseButton",
                        "Click on the collapse icon on the performance dashboard");

                objCommonUtil.scrollWebPage(0, verticleCoordinates);
                verticleCoordinates = verticleCoordinates + 100;
            }
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception comes in verifyTickIconForEachModuleClickable method"
                            + e);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage
           verifyCompletionTitleForModule() {
        FindElement element = new FindElement();
        try {

            List<WebElement> tickPercent = element
                    .getElements("InstructorDashBoardTickIcon");
            tickPercent.get(0).click();
            verifyText("InstructorDashBoardCompletionTitleName", "Completion",
                    "Verify Completion title is present");
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    public GLPInstructor_InstructorDashboardPage
           verify4BoxesUnderCompletionTitle() {
        FindElement element = new FindElement();
        try {

            List<WebElement> tickPercent = element
                    .getElements("InstructorDashBoardTickIcon");
            tickPercent.get(0).click();
            verifyText("InstructorDashBoardCompletionTitleName", "Completion",
                    "Verify Completion title is present");
            verifyElementPresent("InstructorDashBoardChartColorBarPlacedOut",
                    "Verify placed out bar is present");
            verifyElementPresent("InstructorDashBoardChartColorBarCompleted",
                    "Verify completed bar is present");
            verifyElementPresent("InstructorDashBoardChartColorBarInProgress",
                    "Verify in progress bar is present");
            verifyElementPresent("InstructorDashBoardChartColorBarNotStarted",
                    "Verify not started bar is present");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 1 Nov 2017
     * @description :Verify 1. Completion card should be opened with
     *              "Completion" title in pre-assessment section 2. Observe
     *              (A)Completion, Assessment and Placement Results test should
     *              be displayed in bold. (B)Placement Results text font size
     *              should be equal to Completion text font size. (C)Horizontal
     *              bar edges should be round.
     */
    public GLPInstructor_InstructorDashboardPage
           verifyPreAssessmentCompletionCardOnUI() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");
            verifyElementPresent("CompletionTextBold",
                    "Verify Completion card is opened with title 'Completion' in Pre-Assesment section.");
            verifyElementCSSValue("CompletionTextBold", "font-weight",
                    ResourceConfigurations.getProperty("fontWeight"),
                    "Verify 'Completion' Text is displayed as bold.");
            verifyElementCSSValue("AssessmentTextBold", "font-weight",
                    ResourceConfigurations.getProperty("fontWeight"),
                    "Verify 'Pre-Assessment' Text is displayed as bold.");
            verifyElementCSSValue("PlacementResultsTextBold", "font-weight",
                    ResourceConfigurations.getProperty("fontWeight"),
                    "Verify 'Placement' Text is displayed as bold.");

            compareFontSize(); // Compare font size of Completion and Placement
            // results texts.

            verifyElementCSSValue("HorizontalBar", "border-radius",
                    ResourceConfigurations.getProperty("borderRadius"),
                    "Verify Horizontal bar edges are round.");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 1 Nov 2017
     * @description :Verify Completion card should be opened, where Completed,
     *              in progress and not started blocks should be displayed below
     *              the completion text.
     */
    public GLPInstructor_InstructorDashboardPage verifyStatusBlocks() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");

            verifyElementPresent("CompletedBlock",
                    "Verify Completed block is displayed.");

            verifyElementPresent("InProgressBlock",
                    "Verify InProgress block is displayed.");

            verifyElementPresent("NotStartedBlock",
                    "Verify Not started block is displayed");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 1 Nov 2017
     * @description :Completion card should be opened,where colour of all
     *              categories:completed, in progress and not started should be
     *              different.
     */
    public GLPInstructor_InstructorDashboardPage verifyColorOfStatusBlocks() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");
            String completedColor = verifyElementCSSValue("CompletedBlock",
                    "background-color",
                    ResourceConfigurations.getProperty("completedBlockColor"),
                    "Verify Completed block color is different.");
            String inProgressColor = verifyElementCSSValue("InProgressBlock",
                    "background-color",
                    ResourceConfigurations.getProperty("inProgressBlockColor"),
                    "Verify InProgress block color is different.");
            String notStartedColor = verifyElementCSSValue("NotStartedBlock",
                    "background-color",
                    ResourceConfigurations.getProperty("notStartedBlockColor"),
                    "Verify Not started block color is different.");
            if (completedColor.equals(inProgressColor)) {
                result = inProgressColor;
                logResultInReport(result,
                        "Verify colour of all categories:completed, in progress and not started should be different.",
                        this.reportTestObj);
            } else {
                result = inProgressColor;
                logResultInReport(result,
                        "Verify colour of all categories:completed, in progress and not started should be different.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 2 Nov 2017
     * @description :Verify Completion card should be opened,where placement
     *              results is displayed below the bars with following content:
     *              (a)Number of student should be displayed in circular image.
     *              (b)Module number and module name should be displayed below
     *              the circle. (c)Circle should be filled with no colour if
     *              number of Student is zero.
     */
    public GLPInstructor_InstructorDashboardPage
           verifyCompletionCardForPlacementResults() {
        FindElement element = new FindElement();
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");
            List<WebElement> studentNumber = element
                    .getElements("StudentNumber");
            for (WebElement we : studentNumber) {
                if (!we.getCssValue("border-radius")
                        .equals(ResourceConfigurations
                                .getProperty("borderRadiusCircle")))
                    ;
                {
                    this.result = Constants.FAIL
                            + ": Number of student should not be displayed in circular image.";
                    logResultInReport(this.result,
                            "Number of student should not be displayed in circular image.",
                            this.reportTestObj);
                }

            }

            List<WebElement> moduleNumber = element.getElements("ModuleNumber");
            for (WebElement we : moduleNumber) {
                if (!we.getText().contains("Module")) {
                    this.result = Constants.FAIL
                            + ": Module number should not be displayed below the circle";
                    logResultInReport(this.result,
                            "Module number should not be displayed below the circle",
                            this.reportTestObj);
                }

            }
            List<WebElement> moduleName = element.getElements("ModuleName");
            for (WebElement we : moduleName) {
                if (we.getText().contains(" ")) {
                    this.result = Constants.FAIL
                            + ": Module name should not be displayed below the circle";
                    logResultInReport(this.result,
                            "Module name should not be displayed below the circle",
                            this.reportTestObj);
                }

            }
            List<WebElement> circleWithNoColor = element
                    .getElements("CircleWithNoColor");
            for (WebElement we : circleWithNoColor) {
                if (we.getCssValue("border-radius")
                        .equals(ResourceConfigurations
                                .getProperty("borderRadiusCircle")))
                    ;
                {
                    this.result = Constants.FAIL
                            + ": Circle should not be filled with no colour if number of Student is zero.";
                    logResultInReport(this.result,
                            "Circle should not be filled with no colour if number of Student is zero.",
                            this.reportTestObj);
                }

            }

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 2 Nov 2017
     * @description :Verify that Completion card should be collapsed by clicking
     *              arrow at the bottom of the card.
     */
    public GLPInstructor_InstructorDashboardPage
           verifyCompletionCardCollapsed() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");
            verifyElementPresent("CompletionCard",
                    "Verify Completion card should be collapsed by clicking arrow at the bottom of the card.");

            clickOnElement("CompletionCard", "Click on Completion card");
            verifyElementNotPresent("CompletionCard",
                    "Verify Completion card should be collapsed by clicking arrow at the bottom of the card.");
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * 
     * @date 3 Nov 2017
     * @description :verify that on clicking Time on task tab Time on task card
     *              should be opened.
     */
    public GLPInstructor_InstructorDashboardPage verifyTimeOnTaskCardOpened() {
        try {

            clickOnElement("InstructorDashBoardTimeOnTaskTab",
                    "Click on pre-assessment 'Time-on-Task' tab.");
            verifyElementPresent("InstructorDashBoardPreAssessmentChartSummary",
                    "Verify Time on task card should be opened.");
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author sumit.bhardwaj
     * @date Jan 29, 2018
     * @description Check if Gradebook downloaded or not.
     */
    public GLPInstructor_InstructorDashboardPage verifyScoreInGradeBook(
            String userName, String password, String attemptedTestScore,
            String overallTestScore, String assessmentType) {

        FindElement ele = new FindElement();
        ele.checkPageIsReady();
        FileDownloader fileDown = new FileDownloader();
        String destinationFolder = fileDown.downloader(userName, password);
        APP_LOG.info("Folder location: " + destinationFolder);
        if (destinationFolder != null) {
            Scanner inputStream = null;
            try {
                File folder = new File(destinationFolder);
                File[] files = folder.listFiles();
                String fileName = files[0].getName();
                String gradeBookTestScore = null;
                String gradeBookOverallScore = null;
                try {
                    FileReader reader;
                    if (System.getProperty("os.name").contains("Mac") || System
                            .getProperty("os.name").contains("Linux")) {
                        reader = new FileReader(
                                destinationFolder + "//" + fileName);
                    } else {
                        reader = new FileReader(
                                destinationFolder + "\\" + fileName);
                    }
                    APP_LOG.info("File location: " + reader);
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                    .withIgnoreHeaderCase().withTrim());
                    for (CSVRecord csvRecord : csvParser) {
                        if (assessmentType.equalsIgnoreCase("diagnostic")) {
                            // Accessing Values by Column Index
                            gradeBookTestScore = csvRecord
                                    .get("Course Diagnostic Correct Score");
                            gradeBookOverallScore = csvRecord
                                    .get("Course Diagnostic Possible Score");
                            APP_LOG.info("Record No. Diagnostic - "
                                    + csvRecord.getRecordNumber());
                            APP_LOG.info("gradeBookTestScore : "
                                    + gradeBookTestScore);
                            APP_LOG.info("gradeBookOverallScore : "
                                    + gradeBookOverallScore);
                        } else if (assessmentType
                                .equalsIgnoreCase("practice")) {
                            gradeBookTestScore = csvRecord.get(
                                    "16.1.1 Solve equations with variables on both sides. - Correct Score");
                            gradeBookOverallScore = csvRecord.get(
                                    "16.1.1 Solve equations with variables on both sides. - Possible Score");
                            APP_LOG.info("Record No. Practice- "
                                    + csvRecord.getRecordNumber());
                            APP_LOG.info("gradeBookTestScore : "
                                    + gradeBookTestScore);
                            APP_LOG.info("gradeBookOverallScore : "
                                    + gradeBookOverallScore);
                        }
                    }
                } catch (Exception e) {
                    APP_LOG.error(
                            "Exception occured while reading gradebook" + e);
                }
                if (attemptedTestScore.contains(".")) {
                    while (attemptedTestScore.endsWith("0")
                            && attemptedTestScore.contains(".")) {
                        attemptedTestScore = attemptedTestScore.substring(0,
                                attemptedTestScore.length() - 1);
                        if (attemptedTestScore.endsWith(".")) {
                            attemptedTestScore = attemptedTestScore.substring(0,
                                    attemptedTestScore.length() - 1);
                        }
                    }
                }
                if (gradeBookTestScore == null) {
                    result = Constants.FAIL + ": " + assessmentType
                            + " Test Score is not available in Gradebook.";
                    logResultInReport(this.result,
                            "Verify" + assessmentType
                                    + " test score in user gradebook.",
                            this.reportTestObj);
                } else {
                    if (attemptedTestScore.equals(gradeBookTestScore)
                            && (overallTestScore
                                    .equals(gradeBookOverallScore))) {
                        result = Constants.PASS + ": " + assessmentType
                                + " Test Score '" + attemptedTestScore + "/"
                                + overallTestScore
                                + "' is matching with corresponding "
                                + assessmentType + " Score '"
                                + gradeBookTestScore + "/"
                                + gradeBookOverallScore + "' in Gradebook.";
                        logResultInReport(this.result,
                                "Verify " + assessmentType
                                        + " test score in user gradebook.",
                                this.reportTestObj);
                    } else {
                        result = Constants.FAIL + ": Diagnostic Test Score '"
                                + attemptedTestScore + "/" + overallTestScore
                                + "' is not matching with corresponding "
                                + assessmentType + " Score '"
                                + gradeBookTestScore + "/"
                                + gradeBookOverallScore + "' in Gradebook.";
                        logResultInReport(this.result,
                                "Verify " + assessmentType
                                        + " test score in user gradebook.",
                                this.reportTestObj);
                    }
                }
                // f.deleteFolder(destinationFolder);

            } catch (Exception e) {
                APP_LOG.error(e.getMessage());
                result = Constants.FAIL
                        + ": Unexpected error while reading Gradebook: "
                        + e.getMessage();
                logResultInReport(this.result,
                        "Verify Diagnostic test score in user gradebook.",
                        this.reportTestObj);

            } finally {
                // f.deleteFolder(destinationFolder);
                try {
                    inputStream.close();
                } catch (Exception e) {

                    APP_LOG.info("Error while closing input steram:" + e);
                }

            }

        } else

        {
            logResultInReport(
                    Constants.FAIL + ": Error while downloading Gradebook",
                    "Verify gradebook downloaded.", reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * 
     * @date 3 Nov 2017
     * @description :Time on task card should be opened with following content
     *              (a)Average time taken by class should be displayed (b)After
     *              Average time, watch icon is displayed.
     */
    public GLPInstructor_InstructorDashboardPage

           verifyTimeOnTaskCardAverageTime() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * 
     * @date 3 Nov 2017
     * @description :Time on task card should be opened where 4 Boxes should be
     *              displayed showing time range for different categories i.e <
     *              30 min, 30-45 min, 45-60 min, > 60 min
     */
    public GLPInstructor_InstructorDashboardPage verify4BoxesOnTimeONTask() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * @date 3 Nov 2017
     * @description :Time on task card should be opened where colour of all
     *              categories:< 30 min, 30-45 min, 45-60 min, > 60 min should
     *              be different.
     */
    public GLPInstructor_InstructorDashboardPage verifyColourOfAllCategory() {
        try {

            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author rashmi.z
     * 
     * @date 3 Nov 2017
     * @description :Time on task card should be collapsed .
     */
    public GLPInstructor_InstructorDashboardPage verifyTimeCardCollapsed() {
        try {
            clickOnElement("InstructorPreAssessmentCompletionTitleName",
                    "Click on pre-assessment node of graph/Click on pre-assessment completion tab.");

        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * 
     * @date 13 Nov 2017
     * @description :compare font size
     */
    public void compareFontSize() {

        try {
            String completionFontSize = verifyElementCSSValue(
                    "CompletionTextBold", "font-size",
                    ResourceConfigurations.getProperty("fontSize"),
                    "Verify 'Completion' Text font-size.");
            String placementFontSize = verifyElementCSSValue(
                    "PlacementResultsTextBold", "font-size",
                    ResourceConfigurations.getProperty("fontSize"),
                    "Verify 'Placement' Text font-size.");
            if (completionFontSize.equals(placementFontSize)) {
                result = placementFontSize;
                logResultInReport(result,
                        "Verify Placement Results text font size is equal to Completion text font size.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
    }

    /**
     * @author mukul.sehra
     * @param webElementLocator
     *            --> element to scroll into
     */
    public void scrollIntoView(String webElementLocator) {
        this.performAction.execute(ACTION_SCROLL_INTO_VIEW, webElementLocator);
    }

    /**
     * @author mohit.gupta5
     * @date 08 Dec,2017
     * @description Check if element is a link.
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPInstructor_InstructorDashboardPage
           verifyFooterHyperLinks(String locator, String desc) {
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

        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date Dec 07, 2017
     * @description Check if footer elements are link.
     */
    public GLPInstructor_InstructorDashboardPage verifyFooterHyperLinks() {
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
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author sumit.bhardwaj
     * @date Jan 29, 2018
     * @description Check if Gradebook downloaded or not.
     */
    public GLPInstructor_InstructorDashboardPage verifyGradeBookDownloaded(
            String userName, String password, String desc) {
        FileDownloader fileDown = new FileDownloader();
        String destinationFolder = fileDown.downloader(userName, password);
        if (destinationFolder != null) {
            logResultInReport(
                    Constants.PASS + ": Gradebook downloaded successfully",
                    desc, reportTestObj);
            FileUtil f = new FileUtil();
            f.deleteFolder(destinationFolder);
        } else {
            logResultInReport(
                    Constants.FAIL + ": Error while downloading Gradebook",
                    desc, reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);

    }

    /**
     * @author nisha.pathria
     * @date 14 Nov,2017
     * @description Accessibility link open in new tab.
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public GLPInstructor_CourseViewPage verifyFooterLinksOpenInNewTab() {

        APP_LOG.debug("Footer link open in new tab.");
        verifyLinkOpenInNewTab("DashBoardFooterAccessibilityLink",
                ResourceConfigurations.getProperty(
                        "accessibilityInformationRevelPearsonText"),
                "Verify Accessibility link open in new tab.");
        verifyLinkOpenInNewTab("DashBoardPrivacyPolicyLink",
                ResourceConfigurations.getProperty("pearsonPrivacyPolicyText"),
                "Verify Privacy Policy link open in new tab.");
        verifyLinkOpenInNewTab("DashBoardFooterTermsAndConditionsLink",
                ResourceConfigurations.getProperty("pearsonTermsOfUseText"),
                "Verify Terms and Conditions link open in new tab.");

        return new GLPInstructor_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nisha.pathria
     * @date 14 Nov,2017
     * @description Accessibility link open in new tab.
     * @return The object of ProductApplication_InstructorDashboardPage
     */
    public GLPInstructor_CourseViewPage verifyLinkOpenInNewTab(String locator,
            String expectedTitle, String message) {

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
        return new GLPInstructor_CourseViewPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Mayank.MIttal
     * @date 13 Mar,2018
     * @description Verify All Elements Value In List with Keyword when 'Select'
     *              tag is present
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage getAllElementsInList(
            String stepDesc, String element, String listExpectedValues) {

        this.APP_LOGS.debug(stepDesc);
        String str = this.performAction.execute(ACTION_SELECT_ALLVALUESFROMLIST,
                element);
        System.out.println(str);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);

    }

    /**
     * @author Mayank.MIttal
     * @date 13 Mar,2018
     * @description Verify All Elements Value In List
     * @return The object of GLPInstructor_InstructorDashboardPage
     */

    public GLPInstructor_InstructorDashboardPage
           verifyAllElementsValueInList(String elementID, String value) {
        String allValuesinDropDown = "";
        FindElement element = new FindElement();
        List<WebElement> listValues = element.getElements(elementID);
        System.out.println("listValues-------" + listValues);
        for (WebElement webElement : listValues) {
            String s = webElement.getText();
            allValuesinDropDown = allValuesinDropDown + s + " ";
        }
        String ActualResult = allValuesinDropDown;
        System.out.println(ActualResult);
        String ExpectedValue = value;
        System.out.println(ExpectedValue);
        if (ActualResult.trim().contains(ExpectedValue.trim())) {
            logResultInReport(Constants.PASS,
                    "Verify Drop Down contains all expected values.",
                    this.reportTestObj);
        } else {
            logResultInReport(Constants.FAIL,
                    "Verify Drop Down does not contains all expected values.",
                    this.reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author Mayank.MIttal
     * @date 13 Mar,2018
     * @description select a value from list
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage
           selectAValueFromList(String inputValue, String elementID) {
        FindElement element = new FindElement();
        List<WebElement> listValues = element.getElements(elementID);
        for (WebElement option : listValues) {
            if (inputValue.equals(option.getText()))
                option.click();
        }
        logResultInReport(
                Constants.PASS + ": Element -'" + elementID + " 'is clicked",
                "Verify '" + inputValue + "' is selcted in the drop down.",
                this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author Mayank.MIttal
     * @date 13 Mar,2018
     * @description List count for Student list (Total rows)
     * @return listCount
     */
    public int getListCount(String elementID) {
        FindElement element = new FindElement();
        List<WebElement> listValues = element.getElements(elementID);
        int listCount = listValues.size();
        return listCount;
    }

    /**
     * @author Mayank.MIttal
     * @date 13 Mar,2018
     * @description Verify Logout
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage verifyLogout() {
        try {
            clickOnElement("CourseViewUserName",
                    "Click on User name to open Signout DropDown.");
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
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOGS);
    }

    public String verifyWebtableValuesOnFilterSelection(String locator1,
            String locator2) {

        String result1 = performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                locator1);
        String result2 = performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
                locator2);
        if (result1.contains(Constants.PASS)
                || result2.contains(Constants.PASS)) {
            logResultInReport(
                    Constants.PASS + " : No record is found or \n" + locator2
                            + " is present.",
                    "No record is found or " + locator2 + " is present.",
                    this.reportTestObj);
            return Constants.PASS;
        } else {
            logResultInReport(Constants.FAIL + locator2 + ": is not present.",
                    locator2 + " is not present.", this.reportTestObj);
            return Constants.FAIL;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 20 Mar,2018
     * @description handle popup for unlock if exists
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage
           handleUnlockPopupIfExists(String locator) {
        APP_LOG.debug("Handle unlock popup");
        if (this.performAction
                .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator)
                .contains(Constants.PASS)) {
            clickOnElement(locator, "Click on 'Unlock Now' button if exists.");
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOGS);
    }

    /**
     * @author pankaj.sarjal
     * @date 21 Mar,2018
     * @description Switch to 'Student List' tab
     * @return
     */
    public GLPInstructor_InstructorDashboardPage switchToStudentListTab() {
        APP_LOG.debug("Switch to Student List Tab");
        clickOnElement("InstructorDashBoardPerformanceStudentList",
                "Switch to Student List Tab");
        return this;
    }

    /**
     * @author pankaj.sarjal
     * @param argsList
     * @description : Press 'Escape' key and verify 'Welcome back' message
     *              pop-up disappear
     * @return
     */

    public void pressEscapeAndVerifyWelcomeBackPopUp() {
        APP_LOG.debug("Func:pressEscapeKey");

        try {
            Actions action = new Actions(returnDriver());
            action.sendKeys(Keys.ESCAPE).perform();
            if (performAction
                    .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT,
                            "InstructorDashBoardUnlockPopup")
                    .contains(Constants.PASS)) {
                result = Constants.PASS
                        + ": 'Welcome back' popup disappear from UI after pressing Escape key.";
                logResultInReport(this.result,
                        "Verify 'Welcome back' popup disappear after presssing Escape key.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL
                        + ": 'Welcome back' popup does not disappear from UI after pressing Escape key.";
                logResultInReport(this.result,
                        "Verify 'Welcome back' popup disappear after presssing Escape key.",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.debug(" Func:pressEscapeKey = " + e);
        }

    }

    /**
     * @author pankaj.sarjal
     * @param element
     * @param colorValueHexadecimalFormat
     * @param stepDesc
     * @description To verify background-color of an element
     */
    public void verifyBackgourndColor(String element,
            String colorValueHexadecimalFormat, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM,
                ACTION_VERIFY_COLOR_BACKGROUND_HEX_FORMAT);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(ELEMENT_INPUT_VALUE, colorValueHexadecimalFormat);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author mayank.mittal
     * @date 26 Mar,2018
     * @description verify Ascending/Descending order of list.
     * @return GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage
           verifyUserNameOrder(String locator, String order, String desc) {
        APP_LOG.debug("Order of Name displayed in Student List");
        FindElement element = new FindElement();
        List<WebElement> listValues = element.getElements(locator);
        List<String> names = new ArrayList<String>();
        for (WebElement option : listValues) {
            names.add(option.getText().toLowerCase());
        }

        if (order.equalsIgnoreCase("asc")) {
            Collections.sort(names);
            for (String opt1 : names) {
                APP_LOG.info(opt1.toLowerCase());
            }
        } else {
            Collections.sort(names);
            Collections.reverse(names);
        }

        int i = 0;
        boolean bFlag = false;
        for (String string : names) {

            if (string.equalsIgnoreCase(listValues.get(i).getText())) {
                bFlag = true;
            } else {
                logResultInReport(Constants.FAIL
                        + ": List is not sorted as expected value '" + string
                        + "' is not matching with actual value '"
                        + listValues.get(i).getText() + "'", desc,
                        reportTestObj);
            }
            i++;
        }
        if (bFlag) {
            logResultInReport(
                    Constants.PASS
                            + ": Student list is dorted in the desired order.",
                    desc, reportTestObj);
        }

        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOGS);
    }

    public String verifyTextContains(String element, String text,
            String stepDesc) {
        this.APP_LOG.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        if (result.contains("PASS")) {
            result = "PASS: " + element + " contains the correct text i.e. "
                    + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        } else {
            result = "FAIL: " + element
                    + " does not contains the correct text i.e. " + text;
            logResultInReport(this.result, stepDesc, this.reportTestObj);
        }
        return this.result;
    }

    public void verifyAllTextInInstructorPopup(String instructorName) {
        try {
            verifyTextContains("InstructorDashBoardUnlockPopupHeader",
                    ResourceConfigurations.getProperty(
                            "InstructorDashBoardUnlockPopupHeader"),
                    "Verify InstructorDashBoardUnlockPopupHeader text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyTextContains("InstructorDashBoardUnlockPopupHeader",
                    instructorName,
                    "Verify InstructorDashBoardUnlockPopupHeader text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("InstructorDashBoardUnlockPopupBody",
                    ResourceConfigurations
                            .getProperty("InstructorDashBoardUnlockPopupBody"),
                    "Verify InstructorDashBoardUnlockPopupBody text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("InstructorDashBoardUnlockNowButton",
                    ResourceConfigurations
                            .getProperty("InstructorDashBoardUnlockNowButton"),
                    "Verify InstructorDashBoardUnlockNowButton Button text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("InstructorDashBoardUnlockLaterButton",
                    ResourceConfigurations.getProperty(
                            "InstructorDashBoardUnlockLaterButton"),
                    "Verify InstructorDashBoardUnlockLaterButton Button text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
    }

    public void navigateToUnlockPreAssessmentPopUp() {
        try {
            clickOnElement("InstructorDashBoardUnlockLaterButton",
                    "Click on Unlock Later button");
            clickOnElement("InstructorDashBoardPerformanceStudentList",
                    "Click on Student List Tab");
            ((JavascriptExecutor) returnDriver()).executeScript(
                    "arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#selectAll")));
            // clickOnElement("StudentUnlockButton",
            // "Click on unlock button at student level");
            clickOnElement("FilterUnlockButton",
                    "Click on unlock button at filter level");
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
    }

    public void verifyPreAssessmentPopUp(String learnerUserName,
            boolean unlock) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) returnDriver();
            js.executeScript("arguments[0].scrollIntoView(true)",
                    returnDriver().findElement(
                            By.cssSelector(".modalCancel.pe-btn--btn_large")));
            js.executeScript("scroll(" + 0 + "," + 4000 + ")");
            if (unlock) {
                verifyText("UnlockPreAssessmentTest",
                        ResourceConfigurations
                                .getProperty("UnlockPreAssessmentTest"),
                        "Verify UnlockPreAssessmentTest text is displayed in"
                                + ResourceConfigurations
                                        .getProperty("language"));
                verifyText("UnlockPreAssessmentUnlock0now",
                        ResourceConfigurations
                                .getProperty("UnlockPreAssessmentUnlock0now"),
                        "Verify UnlockPreAssessmentUnlock0now text is displayed in"
                                + ResourceConfigurations
                                        .getProperty("language"));
            } else {
                verifyText("UnlockPreAssessmentTest",
                        ResourceConfigurations
                                .getProperty("LockPreAssessmentTest"),
                        "Verify LockPreAssessmentTest text is displayed in"
                                + ResourceConfigurations
                                        .getProperty("language"));
                verifyText("UnlockPreAssessmentUnlock0now",
                        ResourceConfigurations
                                .getProperty("lockPreAssessmentUnlock0now"),
                        "Verify LockPreAssessmentUnlock0now text is displayed in"
                                + ResourceConfigurations
                                        .getProperty("language"));
            }
            verifyText("UnlockPreAssessmentBody",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentBody"),
                    "Verify UnlockPreAssessmentBody text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentName",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentName"),
                    "Verify UnlockPreAssessmentName text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentTests",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentTests"),
                    "Verify UnlockPreAssessmentTests text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentLastlogin",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentLastlogin"),
                    "Verify UnlockPreAssessmentLastlogin text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentGLPLearner",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentGLPLearner") + " "
                            + learnerUserName,
                    "Verify UnlockPreAssessmentGLPLearner text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentPreassessment",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentPreassessment"),
                    "Verify UnlockPreAssessmentPreassessment text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
            verifyText("UnlockPreAssessmentCancel",
                    ResourceConfigurations
                            .getProperty("UnlockPreAssessmentCancel"),
                    "Verify UnlockPreAssessmentCancel text is displayed in"
                            + ResourceConfigurations.getProperty("language"));
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
    }

    public void navigateToLockPreAssessmentPopUp() {
        try {
            clickOnElement("UnlockPreAssessmentCancel",
                    "Click on cancel Button");
            ((JavascriptExecutor) returnDriver()).executeScript(
                    "arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#selectAll")));
            ((JavascriptExecutor) returnDriver()).executeScript(
                    "arguments[0].click();",
                    webDriver.findElement(By.cssSelector("#selectAll")));
            clickOnElement("FilterLockButton", "Click on Filter Lock Button");
        } catch (Exception e) {
            APP_LOG.error(e.getMessage());
        }
    }

    /**
     * @author pankaj.sarjal
     * @param argsList
     * @return
     * @description : Press 'Escape' key
     */
    public String pressEscapeKey() {
        APP_LOG.debug("Func:pressEscapeKey");
        WebDriver webDriver = returnDriver();

        try {
            Actions action = new Actions(webDriver);
            action.sendKeys(Keys.ESCAPE).perform();

        } catch (Exception e) {
            APP_LOG.debug(" Func:pressEscapeKey = " + e);
            return Constants.FAIL + " : " + e;
        }
        return Constants.PASS;

    }

    /**
     * @author pankaj.sarjal
     * @param element
     * @description Method to scroll to particular element
     */
    public void scrollToElement(String element) {
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        performAction.execute(ACTION_SCROLL_INTO_VIEW, element);
    }

    /**
     * @author mukul.sehra
     * @date 20 Dec,2017
     * @description Check whether button is disabled or not
     * @param locator
     * @return true/false
     */
    public boolean isButtonDisabled(String locator) {
        String classAttributeValue = this.performAction
                .execute(ACTION_GET_ATTRIBUTE, locator, "class");

        if (classAttributeValue.contains("disabled")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author mayank.mittal
     * @date 10 May, 2017
     * @description To verify button is disabled or enabled
     */
    public boolean verifyButtonEnabledOrDisabled(String locator, String value) {
        String classAttributeValue = this.performAction
                .execute(ACTION_GET_ATTRIBUTE, locator, "class");

        if (classAttributeValue.contains(value)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author mukul.sehra
     * @date 13 Apr,2018
     * @description Clicks a checkbox corresponding to desired learner's name
     * @param learnerName
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage
           clickCheckboxCorrespondingToLearner(String learnerName) {
        try {
            APP_LOG.info("Inside clickCheckbox Method...");
            // Search for a learner by entering the learner name in filter and
            // hit ENTER key
            enterValueInFilterBox(learnerName + "\uE007");
            FindElement element = new FindElement();
            List<WebElement> studentListFullName = element
                    .getElements("InstructorDashBoardPerformanceFullName");
            List<WebElement> studentListCheckbox = element
                    .getElements("StudentListCheckbox");
            for (int nameCounter = 0; nameCounter < studentListFullName
                    .size(); nameCounter++) {
                if ((studentListFullName.get(nameCounter).getText())
                        .contains(learnerName)) {
                    studentListCheckbox.get(nameCounter).click();
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception while clicking learner specific checkbox : "
                    + e);
            logResultInReport(Constants.FAIL
                    + " : Exception while clicking checkbox corresponding to learner : "
                    + e.getMessage(),
                    "Verify that the checkbox corresponding to learner '"
                            + learnerName + "' has been clicked",
                    reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author mukul.sehra
     * @date 13 Apr,2018
     * @description Clicks the username from the student list to navigate to
     *              profile page
     * @param learnerName
     * @return The object of GLPInstructor_StudentPerformanceDetailsPage
     */
    public GLPInstructor_StudentPerformanceDetailsPage
           navigateToLearnerProfile(String learnerName) {
        try {
            enterValueInFilterBox(learnerName + "\uE007");
            FindElement element = new FindElement();
            List<WebElement> studentListFullName = element
                    .getElements("InstructorDashBoardPerformanceFullName");
            for (int nameCounter = 0; nameCounter < studentListFullName
                    .size(); nameCounter++) {
                if ((studentListFullName.get(nameCounter).getText())
                        .contains(learnerName)) {
                    studentListFullName.get(nameCounter).click();
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception while navigating to the student details page : "
                            + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while navigating to learner : "
                            + e.getMessage(),
                    "Verify navigation to learner '" + learnerName
                            + "' details page",
                    reportTestObj);
        }
        return new GLPInstructor_StudentPerformanceDetailsPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author mayank.mittal
     * @date 16 April,2018
     * @description Enter input value in text box
     * @return The object of GLPInstructor_InstructorDashboardPage
     */

    public GLPInstructor_InstructorDashboardPage enterInputData(String locator,
            String value, String message) {
        APP_LOG.debug("Enter the input value- " + value);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                locator, value);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author mayank.mittal
     * @date 16 April,2018
     * @description Enter text value in filter box
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public GLPInstructor_InstructorDashboardPage
           enterValueInFilterBox(String value) {
        APP_LOG.debug("Enter the input value- " + value);
        enterInputData("PerforManceDashBoardStudentFilterBox", value,
                "enter value in the filter box.");
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author mayank.mittal
     * @date 16 April,2018
     * @description Click using keys.Enter and check the values coming in list
     *              as per filter applied.
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public void pressEnterforFilterAndVerifyResult(String value) {
        APP_LOG.debug("pressEnterforFilterAndVerifyResult");

        try {
            Actions action = new Actions(returnDriver());
            action.sendKeys(Keys.CLEAR).perform();
            action.sendKeys(Keys.ENTER).perform();
            if (performAction
                    .execute(ACTION_VERIFY_ELEMENT_PRESENT,
                            "InstructorDashBoardPerformanceNoRecordFound")
                    .contains(Constants.PASS)) {
                result = Constants.PASS
                        + "No result is found as per the value entered in filter.";
                logResultInReport(this.result,
                        "No result is found as per the value entered in filter.",
                        this.reportTestObj);
            } else {
                int count = getListCount(
                        "PerforManceDashBoardStudentStatusColumn");
                for (int rowNum = 0; rowNum < count; rowNum++) {
                    FindElement element = new FindElement();
                    List<WebElement> listValues = element.getElements(
                            "InstructorDashBoardPerformanceFullName");
                    String value1 = listValues.get(rowNum).getText();
                    if (value1.contains(value))
                        result = Constants.PASS + value
                                + " :Value is present in list row: " + rowNum;
                    else
                        result = Constants.FAIL + value
                                + " :Value is not present in list row: "
                                + rowNum;
                }
                logResultInReport(this.result,
                        "Verify values present in filter are present ",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.debug("Func:pressEscapeKey = " + e);
        }

    }

    /**
     * @author mayank.mittal
     * @date 16 April,2018
     * @description verify student list row count when filter value is removed.
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public void verifyListCount(int originalListCount) {
        APP_LOG.debug("verifyListCount");
        try {
            Actions action = new Actions(returnDriver());
            action.sendKeys(Keys.ENTER).perform();
            int actualListCount = getListCount(
                    "PerforManceDashBoardStudentStatusColumn");
            if (originalListCount == actualListCount) {
                result = Constants.PASS + "List count values remains same.";
            } else {
                result = Constants.FAIL + "List count values is changed.";
            }
        } catch (Exception exception) {
            APP_LOG.debug("verifyListCount" + exception);
        }
    }

    /**
     * @author mayank.mittal
     * @date 19 April,2018
     * @description verify pagination is present.
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public void verifyPaginationPresent() {
        APP_LOG.debug("verifyPaginationPresent");
        try {
            if (getListCount("PerforManceDashBoardStudentStatusColumn") >= 50) {
                verifyElementPresent(
                        "PerforManceDashBoardStudentListPagination",
                        "Pagination is present.");
                verifyElementPresent(
                        "PerforManceDashBoardStudentListPaginationPrevious",
                        "Pagination Previous button is present.");
                verifyElementPresent(
                        "PerforManceDashBoardStudentListPaginationNext",
                        "Pagination Next button is present.");
                result = Constants.PASS + "Pagination is present.";
            } else {
                logResultInReport(this.result, "Pagination is not present.",
                        this.reportTestObj);
                result = Constants.FAIL + "Pagination is not present.";
            }
        } catch (Exception exception) {
            APP_LOG.debug("verifyListCount" + exception);
        }
        logResultInReport(this.result, "Pagination is present.",
                this.reportTestObj);
    }

    /**
     * @author mayank.mittal
     * @date 19 April,2018
     * @description verify pagination Next button is disabled.
     * @return The object of GLPInstructor_InstructorDashboardPage
     */
    public void verifyPaginationNextButtonIsDisabled() {
        APP_LOG.debug("verifyPaginationNextButtonIsDisabled");
        try {
            for (int count = 0; count < 15; count++) {
                if (verifyButtonEnabledOrDisabled(
                        "PerforManceDashBoardStudentListPaginationNext",
                        "enabled"))
                    clickOnElement(
                            "PerforManceDashBoardStudentListPaginationNext",
                            "Click on Next Pagination Button.");
                else
                    break;
            }
        } catch (Exception exception) {
            APP_LOG.debug("verifyListCount" + exception);
        }
        result = Constants.PASS + "Pagination Next button is disabled.";
        logResultInReport(this.result, "Pagination Next button is disabled.",
                this.reportTestObj);
    }

    /**
     * @author pankaj.sarjal
     * @param argsList
     * @return
     * @description : Press 'Enter' key
     */
    public String pressEnterKey() {
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
     * @author pankaj.sarjal
     * @param inDate
     * @return
     */
    public void isValidDate(String locator) {
        APP_LOG.debug("Func:isValidDate");
        Pattern datePattern = null;
        Matcher dateMatcher = null;
        String dateText = null;
        try {
            dateText = getText(locator);
            datePattern = Pattern.compile("([0-9]{1,2}/[0-9]{1,2}/[0-9]{4})");
            dateMatcher = datePattern.matcher(dateText);
            if (dateMatcher.find()) {
                dateText = dateMatcher.group(1);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "MM/dd/yyyy");
                dateFormat.setLenient(false);
                dateFormat.parse(dateText.trim());
                logResultInReport(
                        Constants.PASS + ": Element Text contains valid date",
                        "Verify that Element Text contains valid date.",
                        this.reportTestObj);
            } else {
                APP_LOG.info("Func:isValidDate- Element Text:" + locator
                        + " does not contain Date pattern");
                logResultInReport(Constants.FAIL
                        + ": Element Text does not contain Date pattern",
                        "Verify that Element Text contains valid date.",
                        this.reportTestObj);
            }
        }

        catch (Exception e) {
            APP_LOG.error("Element Text does not contain valid date :"
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Element Text does not contain valid date :"
                            + e.getMessage(),
                    "Verify that Element Text contains valid date.",
                    this.reportTestObj);

        }
    }

    /**
     * @author lekh.bahl
     * @date 30 May,2018
     * @description verify Text not equals
     */

    public void verifyTextNotEquals(String text1, String text2,
            String message) {

        try {
            if (!(text1.equals(text2))) {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is not equal to Expected Text : " + text2;
                logResultInReport(this.result, message, this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is same as Expected Text :" + text2;
                logResultInReport(this.result, message, this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

    }

    /**
     * @author lekh.bahl
     * @date 06 Apr,2018
     * @description Click on browser back button
     */
    public GLPInstructor_InstructorDashboardPage
           clickBrowserBackButton(String message) {
        this.result = this.performAction.execute(ACTION_NAVIGATE_BROWSER_BACK);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author anuj.tiwari1
     * @date 31 May, 2018
     * @description Verify list of Students on Performance Dashboard page
     */

    public void verifyStudentListIsDisplayed() {
        int listOfStudents = 0;
        try {
            FindElement ele = new FindElement();
            listOfStudents = ele
                    .getElements("InstructorDashboardListOfStudents").size();
            if (listOfStudents > 2) {
                logResultInReport(
                        Constants.PASS + ": List of students is displayed. ",
                        "Verify that List of student is displayed on the details page.",
                        reportTestObj);
            }

        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Failed to get List of Students "
                            + e.getMessage(),
                    "Failed to get List of students", reportTestObj);
        }
    }

    /**
     * @author Lekh.bahl
     * @date 8 Dec,2017
     * @description Verify text in the list
     */
    public String verifyTextInList(String element, String text, String position,
            String stepDesc) {
        this.APP_LOGS.debug("Verify text message " + text);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        /*
         * this.result = this.performAction.execute(
         * ACTION_VERIFY_TEXT_CONTAINS_IN_LIST_BY_INDEX, element, text,
         * position);
         */
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author pankaj.sarjal
     * @param
     * @return
     */
    public void isValidTimeOnTask(String locator) {
        APP_LOG.debug("Func:isValidTimeOnTask");
        Pattern timePattern = null;
        Matcher timeMatcher = null;
        String timeText = null;
        try {
            timeText = getText(locator).trim();
            timePattern = Pattern.compile(
                    "^\\b(([1-9]|1[0-9]|2[0-3])\\s?(hr|Hr|HR)\\s?+([1-9]|[1-5][0-9]|59)\\s?(min|Min|MIN)\\s?([0-9]|[1-5][0-9]|59)\\s?(sec|Sec|SEC))$|^\\b(([1-9]|[1-5][0-9]|59)\\s?(min|Min|MIN)\\s?([0-9]|[1-5][0-9]|59)\\s?(sec|Sec|SEC))$|^\\b(([1-9]|[1-5][0-9]|59)\\s?(min|Min|MIN))$|^\\b(([0-9]|[1-5][0-9]|59)\\s?(sec|Sec|SEC))$|^\\b(([1-9]|1[0-9]|2[0-3])\\s?(hr|Hr|HR)\\s?+([1-9]|[1-5][0-9]|59)\\s?(min|Min|MIN))|^\\b(0)$");
            timeMatcher = timePattern.matcher(timeText);
            if (timeMatcher.find()) {
                logResultInReport(
                        Constants.PASS + ": Element text contains valid time.",
                        "Verify that element text contains valid time.",
                        this.reportTestObj);
            } else {
                APP_LOG.info("Func:isValidTimeOnTask- Element Text:" + locator
                        + " does not contain time");
                logResultInReport(
                        Constants.FAIL + ": Element text does not contain time",
                        "Verify that element text contains valid time.",
                        this.reportTestObj);
            }
        }

        catch (Exception e) {
            APP_LOG.error("Element text does not contain valid time :"
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Element text does not contain valid time :"
                            + e.getMessage(),
                    "Verify that element text contains valid time.",
                    this.reportTestObj);

        }
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
     * @author pankaj.sarjal
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
     * @date 7 June,2018
     * @description Click on a web element containing text in element list
     * @return N/A
     */
    public void clickOnElementContainsInnerText(String locator,
            String innerTextSubString) {
        APP_LOGS.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLICK_TEXT_IN_LIST,
                locator, innerTextSubString);

        if (this.result.contains(Constants.PASS)) {
            logResultInReport(this.result,
                    "Verify that element containing Innertext is clicked",
                    this.reportTestObj);
        } else {
            logResultInReport(this.result,
                    "Verify that element containing Innertext is clicked",
                    this.reportTestObj);
        }

    }

    /**
     * @author ratnesh.singh
     * @date 7 June,2018
     * @description Verifies that text is present in list of elements
     * @return N/A
     */
    public void verifyTextContainsInList(String locator, String text,
            String message) {
        APP_LOGS.info("Entering func: verifyTextContainsInList");
        this.APP_LOGS.debug(message);
        this.result = this.performAction
                .execute(ACTION_VERIFY_TEXT_CONTAINS_IN_LIST, locator, text);
        logResultInReport(this.result, message, this.reportTestObj);
    }

    /**
     * @author pankaj.sarjal
     * @param locator
     * @param attribute
     * @return
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
     * @author pankaj.sarjal
     * @param element
     * @param stepDesc
     */
    public void clickByJS(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_CLICK_BY_JS, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author nitish.jaiswal
     * @date Jun 17, 2018
     * @description Verify headers localization in gradebook
     */
    public GLPInstructor_InstructorDashboardPage
           verifyGradeBookColumnsLocalization(String userName,
                   String password) {

        FindElement ele = new FindElement();
        ele.checkPageIsReady();
        FileDownloader fileDown = new FileDownloader();
        String destinationFolder = fileDown.downloader(userName, password);
        APP_LOG.info("Folder location: " + destinationFolder);
        if (destinationFolder != null) {
            Scanner inputStream = null;
            try {
                File folder = new File(destinationFolder);
                File[] files = folder.listFiles();
                String fileName = files[0].getName();
                String gradeBookTestScore = null;
                String gradeBookOverallScore = null;
                String gradeBookFirstName = null;
                String gradeBookLastName = null;
                String gradeBookEmailAddress = null;
                try {
                    FileReader reader;
                    if (System.getProperty("os.name").contains("Mac") || System
                            .getProperty("os.name").contains("Linux")) {
                        reader = new FileReader(
                                destinationFolder + "//" + fileName);
                    } else {
                        reader = new FileReader(
                                destinationFolder + "\\" + fileName);
                    }
                    APP_LOG.info("File location: " + reader);
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withIgnoreEmptyLines());
                    for (CSVRecord csvRecord : csvParser) {
                        // Accessing Values by Column Index
                        gradeBookFirstName = csvRecord.get(0);
                        gradeBookLastName = csvRecord.get(1);
                        gradeBookEmailAddress = csvRecord.get(2);
                        gradeBookTestScore = csvRecord.get(3);
                        gradeBookOverallScore = csvRecord.get(4);
                        APP_LOG.info("Record No. Diagnostic - "
                                + csvRecord.getRecordNumber());
                        APP_LOG.info(
                                "gradeBookTestScore : " + gradeBookTestScore);
                        APP_LOG.info("gradeBookOverallScore : "
                                + gradeBookOverallScore);
                        break;
                    }
                    String gradeBookItems[] = { gradeBookFirstName,
                            gradeBookLastName, gradeBookEmailAddress,
                            gradeBookTestScore, gradeBookOverallScore };
                    String gradeBookItemsToVerify[] = {
                            ResourceConfigurations
                                    .getProperty("gradeBookFirstName"),
                            ResourceConfigurations
                                    .getProperty("gradeBookLastName"),
                            ResourceConfigurations
                                    .getProperty("gradeBookEmailAddress"),
                            ResourceConfigurations.getProperty(
                                    "gradeBookDiagnosticCorrectScore"),
                            ResourceConfigurations.getProperty(
                                    "gradeBookDiagnosticPossibleScore") };
                    String gradeBookItemsToPrint[] = { "First Name",
                            "Last Name", "EmailAddress",
                            "Diagnostic Test Score",
                            "Diagnostic Overall Score" };
                    for (int i = 0; i < gradeBookItems.length; i++) {
                        if (gradeBookItems[0].trim()
                                .equals(gradeBookItemsToVerify[0])) {
                            result = Constants.PASS + ": gradeBookItems[0] '"
                                    + gradeBookFirstName
                                    + "' is matched with expected text '"
                                    + gradeBookItemsToVerify[0] + "'";
                            logResultInReport(this.result,
                                    "Verify '" + gradeBookItemsToPrint
                                            + "' column text is displayed in"
                                            + ResourceConfigurations
                                                    .getProperty("language")
                                            + " in GradeBook.",
                                    this.reportTestObj);
                        }
                    }

                } catch (Exception e) {
                    APP_LOG.error(
                            "Exception occured while reading gradebook" + e);
                }

                // f.deleteFolder(destinationFolder);

            } catch (Exception e) {
                APP_LOG.error(e.getMessage());
                result = Constants.FAIL
                        + ": Unexpected error while reading Gradebook: "
                        + e.getMessage();
                logResultInReport(this.result,
                        "Verify Diagnostic test score in user gradebook.",
                        this.reportTestObj);

            } finally {
                // f.deleteFolder(destinationFolder);
                try {
                    inputStream.close();
                } catch (Exception e) {

                    APP_LOG.info("Error while closing input steram:" + e);
                }

            }

        } else

        {
            logResultInReport(
                    Constants.FAIL + ": Error while downloading Gradebook",
                    "Verify gradebook downloaded.", reportTestObj);
        }
        return new GLPInstructor_InstructorDashboardPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date Jun 17, 2018
     * @description Verify headers localization in gradebook
     */
    public String getGradeBookDesiredColumnValue(String userName,
            String password, String columnName) {

        FindElement ele = new FindElement();
        ele.checkPageIsReady();
        FileDownloader fileDown = new FileDownloader();
        String destinationFolder = fileDown.downloader(userName, password);
        APP_LOG.info("Folder location: " + destinationFolder);
        if (destinationFolder != null) {
            Scanner inputStream = null;
            try {
                File folder = new File(destinationFolder);
                File[] files = folder.listFiles();
                String fileName = files[0].getName();
                String gradeBookTestScore = null;
                String gradeBookOverallScore = null;
                String gradeBookFirstName = null;
                String gradeBookLastName = null;
                String gradeBookEmailAddress = null;
                try {
                    FileReader reader;
                    if (System.getProperty("os.name").contains("Mac") || System
                            .getProperty("os.name").contains("Linux")) {
                        reader = new FileReader(
                                destinationFolder + "//" + fileName);
                    } else {
                        reader = new FileReader(
                                destinationFolder + "\\" + fileName);
                    }
                    APP_LOG.info("File location: " + reader);
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                    .withIgnoreHeaderCase().withTrim());
                    for (CSVRecord csvRecord : csvParser) {
                        // Accessing Values by Column Index
                        gradeBookFirstName = csvRecord.get("First Name");
                        gradeBookLastName = csvRecord.get("Last Name");
                        gradeBookEmailAddress = csvRecord.get("Email Address");
                        gradeBookTestScore = csvRecord
                                .get("Course Diagnostic Correct Score");
                        gradeBookOverallScore = csvRecord
                                .get("Course Diagnostic Possible Score");
                        APP_LOG.info("Record No. Diagnostic - "
                                + csvRecord.getRecordNumber());
                        APP_LOG.info(
                                "gradeBookTestScore : " + gradeBookTestScore);
                        APP_LOG.info("gradeBookOverallScore : "
                                + gradeBookOverallScore);
                        if (columnName.toLowerCase().equals("firstname")) {
                            columnName = gradeBookFirstName;
                        } else if (columnName.toLowerCase()
                                .equals("lastname")) {
                            columnName = gradeBookLastName;
                        } else if (columnName.toLowerCase()
                                .equals("emailaddress")) {
                            columnName = gradeBookEmailAddress;
                        } else if (columnName.toLowerCase()
                                .equals("diagnostictestscore")) {
                            columnName = gradeBookTestScore;
                        } else if (columnName.toLowerCase()
                                .equals("diagnosticoverallscore")) {
                            columnName = gradeBookOverallScore;
                        }
                        break;
                    }

                } catch (Exception e) {
                    APP_LOG.error(
                            "Exception occured while reading gradebook" + e);
                    result = Constants.FAIL
                            + ": Unexpected error while reading Gradebook: "
                            + e.getMessage();
                    logResultInReport(this.result,
                            "Verify Diagnostic test score in user gradebook.",
                            this.reportTestObj);
                }

                if (columnName == null) {
                    result = Constants.FAIL + ": " + columnName
                            + " is having no value in Gradebook.";
                    logResultInReport(this.result,
                            "Get value of " + columnName
                                    + " from user gradebook.",
                            this.reportTestObj);
                }

                // f.deleteFolder(destinationFolder);

            } catch (Exception e) {
                APP_LOG.error(e.getMessage());
                result = Constants.FAIL
                        + ": Unexpected error while reading Gradebook: "
                        + e.getMessage();
                logResultInReport(this.result,
                        "Verify Diagnostic test score in user gradebook.",
                        this.reportTestObj);

            } finally {
                // f.deleteFolder(destinationFolder);
                try {
                    inputStream.close();
                } catch (Exception e) {

                    APP_LOG.info("Error while closing input steram:" + e);
                }

            }

        } else

        {
            logResultInReport(
                    Constants.FAIL + ": Error while downloading Gradebook",
                    "Verify gradebook downloaded.", reportTestObj);
        }
        return columnName;
    }

    /**
     * @author lekh.bahl
     * @date 30 May,2018
     * @description verify Text not equals
     */

    public void verifyTextEquals(String text1, String text2, String message) {

        try {
            if (text1.trim().equals(text2.trim())) {
                this.result = Constants.PASS + ": Actual Text : " + text1
                        + " is not equal to Expected Text : " + text2;
                logResultInReport(this.result, message, this.reportTestObj);
            } else {
                this.result = Constants.FAIL + ": Actual Text : " + text1
                        + " is same as Expected Text :" + text2;
                logResultInReport(this.result, message, this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.info("Unknow error found while comparing bar length "
                    + e.getMessage());
        }

    }

    /**
     * @author nitish.jaiswal
     * @date Jun 17, 2018
     * @description Verify headers localization in gradebook
     */
    public void verifyGradeBookIsSorted(String userName, String password) {

        userName = "GLP_Instructor_Common_uMlH";
        String columnName = null;
        FileUtil f = new FileUtil();
        FindElement ele = new FindElement();
        ele.checkPageIsReady();
        FileDownloader fileDown = new FileDownloader();
        String destinationFolder = fileDown.downloader(userName, password);
        APP_LOG.info("Folder location: " + destinationFolder);
        if (destinationFolder != null) {
            Scanner inputStream = null;
            try {
                File folder = new File(destinationFolder);
                File[] files = folder.listFiles();
                String fileName = files[0].getName();
                String gradeBookFirstName = null;
                try {
                    FileReader reader;
                    if (System.getProperty("os.name").contains("Mac") || System
                            .getProperty("os.name").contains("Linux")) {
                        reader = new FileReader(
                                destinationFolder + "//" + fileName);
                    } else {
                        reader = new FileReader(
                                destinationFolder + "\\" + fileName);
                    }
                    APP_LOG.info("File location: " + reader);
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                    .withIgnoreHeaderCase().withTrim());
                    for (CSVRecord csvRecord : csvParser) {
                        // Accessing Values by Column Index
                        columnName = csvRecord.get("First Name");
                        gradeBookFirstName = gradeBookFirstName + ","
                                + csvRecord.get("First Name");
                    }
                    String[] firstNameCollection = gradeBookFirstName
                            .split(",");
                    if (firstNameCollection.length == 2) {
                        result = Constants.PASS
                                + ": GradeBook is sorted by First Name";
                        logResultInReport(this.result,
                                "Verify GradeBook is sorted by First Name.",
                                this.reportTestObj);
                    }
                    boolean isSorted = false;
                    for (int k = 2; k < firstNameCollection.length; k++) {
                        if (firstNameCollection[k - 1]
                                .compareTo(firstNameCollection[k]) > 0) {
                            isSorted = true;

                        } else {
                            isSorted = false;
                            break;
                        }
                    }
                    if (isSorted) {
                        result = Constants.PASS
                                + ": GradeBook is sorted by First Name";
                        logResultInReport(this.result,
                                "Verify GradeBook is sorted by First Name.",
                                this.reportTestObj);
                    } else {
                        result = Constants.FAIL
                                + ": GradeBook is sorted by First Name";
                        logResultInReport(this.result,
                                "Verify GradeBook is sorted by First Name.",
                                this.reportTestObj);
                    }

                } catch (Exception e) {
                    APP_LOG.error(
                            "Exception occured while reading gradebook" + e);
                    result = Constants.FAIL
                            + ": Unexpected error while reading Gradebook: "
                            + e.getMessage();
                    logResultInReport(this.result,
                            "Verify GradeBook is sorted by First Name.",
                            this.reportTestObj);
                }

                if (columnName == null) {
                    result = Constants.FAIL + ": " + columnName
                            + " is having no value in Gradebook.";
                    logResultInReport(this.result,
                            "Verify GradeBook is sorted by First Name.",
                            this.reportTestObj);
                }

                f.deleteFolder(destinationFolder);

            } catch (Exception e) {
                APP_LOG.error(e.getMessage());
                result = Constants.FAIL
                        + ": Unexpected error while reading Gradebook: "
                        + e.getMessage();
                logResultInReport(this.result,
                        "Verify GradeBook is sorted by First Name.",
                        this.reportTestObj);

            } finally {
                f.deleteFolder(destinationFolder);
                try {
                    inputStream.close();
                } catch (Exception e) {

                    APP_LOG.info("Error while closing input steram:" + e);
                }

            }

        } else

        {
            logResultInReport(
                    Constants.FAIL + ": Error while downloading Gradebook",
                    "Verify gradebook downloaded.", reportTestObj);
        }
    }

    /**
     * @author pankaj.sarjal
     * @date 20 June,2018
     * @description Verify element is present by JS
     * @return N/A
     */
    public void verifyElementPresentByJS(String locator, String message) {
        APP_LOG.debug("Element is present :" + locator);
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_PRESENT_JS, locator);
        if (this.result.contains(Constants.PASS)) {
            logResultInReport(this.result, message, this.reportTestObj);
        } else {
            logResultInReport(this.result, message, this.reportTestObj);
        }
    }

    /**
     * @author pankaj.sarjal
     * @param text1
     * @param text2
     * @param message
     */
    public void verifyStatusIsPresent(String desc) {

        try {
            FindElement findElement = new FindElement();
            List<WebElement> allstatus = findElement
                    .getElements("StatusOnStatusColumn");

            int Statusready = 0;
            int Statuslearning = 0;
            int statuscompleted = 0;
            int statusTesting = 0;
            int statusNotStarted = 0;
            int statusPlacedout = 0;
            for (WebElement e : allstatus) {
                if (e.getText().contains("Completed")) {
                    statuscompleted++;
                }
                if (e.getText().contains("Not started")) {
                    statusNotStarted++;
                }
                if (e.getText().contains("Testing")) {
                    statusTesting++;
                }
                if (e.getText().contains("Learning")) {
                    Statuslearning++;
                }
                if (e.getText().contains("Placed out")) {
                    statusPlacedout++;
                }
                if (e.getText().contains("Ready")) {
                    Statusready++;
                }

            }
            if (Statusready > 0) {
                this.result = Constants.PASS
                        + ": 'Ready' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
            if (Statuslearning > 0) {
                this.result = Constants.PASS
                        + ": 'Learning' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
            if (statusTesting > 0) {
                this.result = Constants.PASS
                        + ": 'Testing' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
            if (statuscompleted > 0) {
                this.result = Constants.PASS
                        + ": 'Completed' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
            if (statusNotStarted > 0) {
                this.result = Constants.PASS
                        + ": 'Not Started' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
            if (statusPlacedout > 0) {
                this.result = Constants.PASS
                        + ": 'Placed Out' status is found on student list page.";
                logResultInReport(this.result, desc, this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured while finding status from 'Status' column on student list page"
                            + e);
            result = Constants.FAIL
                    + ": Unexpected error occured while finding status from 'Status' column on student list page. "
                    + e.getMessage();
            logResultInReport(this.result,
                    "Verify status from 'Status' column on student list page.",
                    this.reportTestObj);
        }

    }

    /*
     * @author anuj.tiwari1
     * 
     * @date 21st June, 2018
     * 
     * @description Get the text of multiple elements in the form of a list.
     * 
     * @return List<String>
     */

    public List<String> getTextList(String element, String message) {

        FindElement findElement = new FindElement();
        List<WebElement> listofelements = findElement.getElements(element);
        List<String> listOfText = new ArrayList<String>();
        for (WebElement ele : listofelements) {
            listOfText.add(ele.getText());
        }
        return listOfText;
    }

    /**
     * @author pankaj.sarjal
     * @description : This method verifies 'Overall Completion' field contains
     *              valid values like - 0% or <Any Number>% on student list page
     * @date : June 22,2018
     */
    public void verifyValidOverallCompletionValue() {

        FindElement findElement = new FindElement();
        String overallCompletionField = findElement
                .getElement("OverallCompletionField").getText();
        try {
            if (overallCompletionField.matches("[0-9]+%+")) {

                this.result = Constants.PASS
                        + ": Overall Completion field verified successfully.";
                logResultInReport(this.result,
                        "Verify 'Overall Completion field on student list page",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL
                        + ": Overall Completion field is not verified .";
                logResultInReport(this.result,
                        "Verify 'Overall Completion field on student list page",
                        this.reportTestObj);

            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured while verifying 'Overall Completion' field on student list page."
                            + e.getMessage());
        }

    }
}