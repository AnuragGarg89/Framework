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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPLearner_CourseMaterialPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    protected PerformAction performAction = new PerformAction();
    private FindElement findElement = new FindElement();

    public GLPLearner_CourseMaterialPage(ExtentTest reportTestObj,
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
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Click on a web element
     * @return The object of ProductApplication_courseHomePage
     */
    public GLPLearner_CourseMaterialPage clickOnElement(String locator,
            String message) {

        APP_LOGS.debug("Click on the Element: " + locator);
        try {
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    locator);
            this.result = this.performAction.execute(ACTION_CLICK, locator);
            logResultInReport(this.result, message, this.reportTestObj);

            // Hack for bypassing error screen that appears on clicking
            // DiagnosticGoToCourseHomeLink on SauceLab
            if (locator.equals("DiagnosticGoToCourseHomeLink")) {
                if (performAction.execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT,
                        "ModuleStartButton").contains(Constants.PASS)) {
                    this.refreshPage();
                    performAction.execute(ACTION_CLICK,
                            "CourseMaterialPearsonLogo");
                    GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                            reportTestObj, APP_LOG);
                    objGLPLearnerCourseViewPage.clickOnElement(
                            "CourseTileStudent",
                            "Click on course tile to navigate to TOC to bypass error screen");
                }
            }
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Lekh.bahl
     * @date 02 Aug,2017
     * @description Click on course tile and verify the start button
     * @return The object of ProductApplication_CourseViewPage
     */
    public GLPLearner_CoreInstructionsPage clickCoreInstructionByIndex(
            String Element, String Position, String Message) {
        APP_LOGS.debug("Click on core instruction" + Position + 1);
        clickElementByIndex(Element, Position, Message);
        return new GLPLearner_CoreInstructionsPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author lekh.bahl
     * @date 03 Aug ,17
     * @description Click on list element by index
     */
    public void clickElementByIndex(String element, String position,
            String stepDesc) {
        this.APP_LOGS.debug(stepDesc);
        this.result = this.performAction.execute(ACTION_CLICK_INDEX_POSITION,
                element, position);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author Nisha.Pathria
     * @date 02 Aug,2017
     * @description Click on start button
     * @return The object of ProductApplication_CourseViewPage
     */

    public GLPLearner_DiagnosticTestPage clickDiagnosticStartButton() {
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "DiagnosticStartButton");
        clickOnElement("DiagnosticStartButton",
                "Click on 'Start' button of Diagnostic Test.");

        return new GLPLearner_DiagnosticTestPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Tarun Gupta
     * @date 13 Sep,2017
     * @description Verify Menu Options on Course material page for RIO Course
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMenuOptionsforRio() {

        APP_LOGS.debug(
                "Verify menu options are displayed on 'Course Material' Page after successfull completion of diagnostic test for RIO");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element
                .getElements("CourseMaterialMenuOptions");
        for (i = 0; i < menuOptions.size(); i++) {
            if (menuOptions.get(i).getText().trim()
                    .equals(ResourceConfigurations.getProperty(
                            "courseMaterialViewPageMenuOption" + (i + 1)))) {
                result = Constants.PASS
                        + ": Verify menu option are present as expected text and sequence";
                logResultInReport(result, "Verify Menu Option '"
                        + ResourceConfigurations.getProperty(
                                "courseMaterialViewPageMenuOption" + (i + 1))
                        + "' is displayed on Course Material page.",
                        this.reportTestObj);
            }
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Tarun Gupta
     * @date 13 Sep,2017
     * @description Verify Menu Options on Course material page for RIO Course
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMenuOptionsforRevel() {

        APP_LOGS.debug(
                "Verify menu options are displayed on 'Course Material' Page for Revel");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element
                .getElements("CourseMaterialMenuOptions");
        for (i = 0; i < menuOptions.size(); i++) {
            if (menuOptions.get(i).getText().trim()
                    .equals(ResourceConfigurations
                            .getProperty("revelMenuOption" + (i + 1)))) {
                result = Constants.PASS
                        + ": Verify menu option are present as expected text and sequence";
                logResultInReport(result, "Verify Menu Option '"
                        + ResourceConfigurations.getProperty(
                                "courseMaterialViewPageMenuOption" + (i + 1))
                        + "' is displayed on Course Material page.",
                        this.reportTestObj);
            }
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Tarun Gupta
     * @date 13 Sep,2017
     * @description Verify Menu Options on Course material page for RIO Course
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMenuOptionsforMathLab() {

        APP_LOGS.debug(
                "Verify menu options are displayed on 'Course Material' Page for MathLab");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element
                .getElements("CourseMaterialMenuOptions");
        for (i = 0; i < menuOptions.size(); i++) {
            if (menuOptions.get(i).getText().trim()
                    .equals(ResourceConfigurations
                            .getProperty("mathLabMenuOption" + (i + 1)))) {
                result = Constants.PASS
                        + ": Verify menu option are present as expected text and sequence";
                logResultInReport(result, "Verify Menu Option '"
                        + ResourceConfigurations.getProperty(
                                "courseMaterialViewPageMenuOption" + (i + 1))
                        + "' is displayed on Course Material page.",
                        this.reportTestObj);
            }
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Tarun Gupta
     * @date 13 Sep,2017
     * @description Verify Menu Options on Course material page for RIO Course
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMenuOptionsforMastering() {

        APP_LOGS.debug(
                "Verify menu options are displayed on 'Course Material' Page for Mastering");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element
                .getElements("CourseMaterialMenuOptions");
        for (i = 0; i < menuOptions.size(); i++) {
            if (menuOptions.get(i).getText().trim()
                    .equals(ResourceConfigurations
                            .getProperty("masteringMenuOption" + (i + 1)))) {
                result = Constants.PASS
                        + ": Verify menu option are present as expected text and sequence";
                logResultInReport(result, "Verify Menu Option '"
                        + ResourceConfigurations.getProperty(
                                "courseMaterialViewPageMenuOption" + (i + 1))
                        + "' is displayed on Course Material page.",
                        this.reportTestObj);
            }
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expected
     * @return The object of ProductApplication_courseHomePage
     */
    public String verifyTextContains(String Locator, String Text,
            String Message) {
        APP_LOGS.debug("Element is present: " + Locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator,
                Text);
        return result;
    }

    /**
     * @author mohit.gupta5
     * @date 19 Sept 2017
     * @description :Verify Attribute value Not present
     * @return The object of ProductApplication_CreateAccountPage
     */
    public GLPLearner_CourseMaterialPage verifyElementNotPresent(String locator,
            String message) {
        APP_LOG.debug(locator + "Element is not present");
        this.result = this.performAction
                .execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author mohit.gupta5
     * @date 19 Sept,2017
     * @description Verify expected text with actual text
     * @return The object of ProductApplication_LoginPage
     */
    public String verifyText(String element, String text, String stepDesc) {
        APP_LOG.debug("Verify text: " + text);
        this.APP_LOGS.debug(stepDesc);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TEXT_CONTAINS,
                element, text);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * @author nitish.jaiswal
     * @date 20 Sept 2017
     * @description :Verify PSP course sub sections
     * @return The object of ProductApplication_PersonalizedStudyPlanPage
     */
    public GLPLearner_CourseMaterialPage verifyPSPSubSectionItems() {

        APP_LOG.debug("Verify PSP sub section items.");

        int i = 0;
        try {
            FindElement element = new FindElement();
            List<WebElement> chapterSubSections = element
                    .getElements("CourseMaterialChaperSubSectionsText");
            for (i = 0; i < chapterSubSections.size(); i++) {
                if (chapterSubSections.get(i).getText().trim()
                        .equals(ResourceConfigurations.getProperty(
                                "CourseMaterialChaperSubSection" + (i + 1)))) {
                    result = Constants.PASS + ": Chapter Sub Section '"
                            + ResourceConfigurations.getProperty(
                                    "CourseMaterialChaperSubSection" + (i + 1))
                            + "' is displayed on PSP Page.";
                    logResultInReport(result,
                            "Verify Chapter Sub Section is presnt as expected.",
                            this.reportTestObj);
                }
            }
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 20 Sept 2017
     * @description :Select required modules filter
     * @return The object of ProductApplication_PersonalizedStudyPlanPage
     */
    public GLPLearner_CourseMaterialPage
           selectRequiredModulesFilter(String Locator, String Description) {

        APP_LOG.debug("Verify PSP sub section items.");

        try {
            clickOnElement("CourseMaterialFilter",
                    "Click on Module Filter dropdown to open dropdown options.");
            clickOnElement(Locator, Description);
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 20 Sept 2017
     * @description :Verify Linear order of chapters
     * @return The object of ProductApplication_PersonalizedStudyPlanPage
     */
    public GLPLearner_CourseMaterialPage verifyLinearOrderOfChapters() {

        APP_LOG.debug("Verify linear order of items.");

        try {
            int i, j;
            boolean bFlag = false;
            FindElement element = new FindElement();
            List<WebElement> chapterTexts = element
                    .getElements("CourseMatrerialChapersText");
            for (i = 0; i < chapterTexts.size(); i++) {
                if (chapterTexts.get(i).getText().trim()
                        .contains(Integer.toString(i + 1))) {
                    bFlag = true;
                } else {
                    bFlag = false;
                    break;
                }
            }

            if (bFlag) {
                result = Constants.PASS
                        + ": All Chapters are displayed as expected and in linear order";
                logResultInReport(result,
                        "Verify Chapters are displayed in linear order.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL
                        + ": Linear order of chapters is not verified.";
                logResultInReport(result,
                        "Verify Chapters are displayed in linear order.",
                        this.reportTestObj);
            }
        } catch (Exception ex) {
            APP_LOG.error(ex.getMessage());
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author nitish.jaiswal
     * @date 19 Sep,2017
     * @description Click on Pearson Logo
     * @return The object of ProductApplication_CourseHomePage
     */

    public GLPLearner_CourseViewPage navigateToCourseViewPage() {

        try {
            clickOnElement("CourseMaterialPearsonLogo",
                    "Click on 'Pearson Logo' button.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author nitish.jaiswal
     * @date 04 Oct,2017
     * @description Click on sub topic of the selected chapter
     * @return The object of ProductApplication_LearningObjectivePage
     */

    public GLPLearner_LearningObjectivePage navigateToLearnerObjectivePage() {

        clickOnElement("CourseMaterialMMLSubChapter",
                "Click on sub topic of the selected chapter.");

        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);

    }

    /**
     * @author mohit.gupta5
     * @date 20 Sept,2017
     * @description Verify Tool Tips Text on Rio Course material page for RIO
     *              Course
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifyToolTipForRioCourseMaterialPage() {

        APP_LOGS.debug(
                "Verify Tool Tips text are displayed on 'Rio Course Material' Page after successfull completion of diagnostic test for RIO");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> tooltips = element
                .getElements("CourseMaterialToolTip");
        for (i = 0; i < tooltips.size(); i++) {
            if (tooltips.get(i).getAttribute("title").trim()
                    .equals(ResourceConfigurations
                            .getProperty("rioToolTips" + (i + 1)))) {
                result = Constants.PASS
                        + ": Verify Tool Tips text are present as 'Actual Text:- '"
                        + tooltips.get(i).getAttribute("title").trim()
                        + " , Expected Text:- " + ResourceConfigurations
                                .getProperty("rioToolTips" + (i + 1));
                logResultInReport(result,
                        "Verify Tool Tips text '"
                                + ResourceConfigurations
                                        .getProperty("rioToolTips" + (i + 1))
                                + "' is displayed on Rio Course Material page.",
                        this.reportTestObj);
            }
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nisha pathria
     * @date 19 Sep,2017
     * @description To verify dialogue box of successful completion of
     *              diagnostic test after user complete diagnostic test.
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyDiagnosticSuccessDialogueBox() {

        verifyElementPresent("CourseMaterialGreatJobRemoveIcon",
                "Verify 'X' icon is present on diagnostic test success dialogue box.");
        verifyElementPresent("CourseMaterialGreatJobIcon",
                "Verify 'thumbs up' icon is present on diagnostic test success dialogue box.");
        result = verifyTextContains("PSPGreatJobText",
                ResourceConfigurations
                        .getProperty("courseMaterialGreatJobText"),
                "Verify great job message on diagnostic test success dialogue box.");
        if (result.contains(Constants.PASS)) {
            logResultInReport(
                    this.result + "correct great job message text i.e. "
                            + ResourceConfigurations
                                    .getProperty("courseMaterialGreatJobText")
                            + " is displayed.",
                    "Verify great job message text on diagnostic test success dialogue box.",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author abhishek.sharda
     * @date 12 July,2017
     * @description Verify element is present
     * @return The object of ProductApplication_courseHomePage
     */
    public String getText(String locator) {
        APP_LOGS.debug("Element is present: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
        System.out.println(valueText);
        return valueText;
    }

    /**
     * @author nitish.jaiswal
     * @date 21 Sep,2017
     * @description To verify and collapse if chapter is already expanded
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyAndCollapseChapterIfExpanded() {

        try {
            verifyElementPresent("CourseMaterialCollapseIcon",
                    "Verify if chapter is already expanded.");
            clickOnElement("CourseMaterialCollapseIcon",
                    "Click on inverted arrow to collapse the chapter.");
        } catch (Exception t) {
            APP_LOG.error(t.getMessage());
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author nisha.pathria
     * @date 22 sep,2017
     * @description Get Values from dropdown
     * @return The object of ProductApplication_courseHomePage
     */
    public GLPLearner_CourseMaterialPage getListItems() {

        try {

            FindElement element = new FindElement();
            List<WebElement> filterOptions = element
                    .getElements("CourseMaterialFilterValues");

            int i = 0;
            for (i = 0; i < filterOptions.size(); i++) {
                if (filterOptions.get(i).getText().trim()
                        .equals(ResourceConfigurations
                                .getProperty("filterValue" + (i + 1)))) {
                    result = Constants.PASS
                            + ": Verify Filter values are present as expected text and sequence";
                    logResultInReport(result,
                            "Verify Filter Option '"
                                    + ResourceConfigurations.getProperty(
                                            "filterValue1" + (i + 1))
                                    + "' is displayed in Filter By dropdown.",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {

            this.result = Constants.FAIL
                    + "Error while comparing UserList data with WebList data because of: "
                    + e;

            logResultInReport(this.result,
                    "Error while comparing UserList data with WebList data",
                    this.reportTestObj);

        }

        return this;
    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that Revel course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyCourseImage(String locator,
            String desc) {

        APP_LOGS.debug("Verify that Revel course image is displayed.");

        verifyElementPresent(locator, desc);

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 14 Sep,2017
     * @description Verify Revel Course name and its description
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyCourseNameAndDescription(
            String locator1, String locator2, String courseName,
            String courseDescription, String desc) {

        APP_LOGS.debug("Verify Rio Course name and its description");

        verifyText(locator1, courseName,
                "Verify " + desc + " name is displayed.");
        verifyTextContains(locator2, courseDescription,
                "Verify " + desc + " Description is displayed.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that Revel course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyRevelCourseChapters() {

        APP_LOGS.debug("Verify that Revel course chapters are displayed.");

        verifyElementPresent("CourseMaterialExpandButton",
                "Verify Revel Course chapters are present.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that Revel course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMMLCourseChapters() {

        APP_LOGS.debug("Verify that Revel course chapters are displayed.");

        verifyElementPresent("CourseMaterialExpandButton",
                "Verify MML course chapters are present.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author nitish.jaiswal
     * @date 03 Oct,2017
     * @description Verify Position of Revel Course, which should come at top
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyRevelImagePosition() {

        APP_LOGS.debug("Verify Position of Rio Course image in Grid View.");

        FindElement element = new FindElement();
        WebElement item = element.getElement("CourseMaterialCourseImage");
        int rioImagePosition = item.getLocation().getX();

        WebElement item1 = element.getElement("CourseMaterialMMLExpandButton");
        int rioCourseDecriptionPosition = item1.getLocation().getX();

        if (rioImagePosition < rioCourseDecriptionPosition) {
            result = Constants.PASS
                    + ": Revel Course Image and its description '"
                    + rioImagePosition
                    + "' is in the Top of Revel Chapters position on UI'"
                    + rioCourseDecriptionPosition + "'";
            logResultInReport(result,
                    "Verify Revel Image and its description is on the Top of its chapters.",
                    this.reportTestObj);
        } else {
            result = Constants.FAIL
                    + ": Revel Course Image and its description '"
                    + rioImagePosition
                    + "' is in the Top of Revel Chapters position on UI'"
                    + rioCourseDecriptionPosition + "'";
            logResultInReport(result,
                    "Verify Revel Image and its description is on the Top of its chapters.",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that Revel course image ,position and description
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifyRevelCourseImageAndDescription() {

        APP_LOGS.debug("Verify that Revel course image is displayed.");

        verifyCourseImage("CourseMaterialCourseImage",
                "Verify Revel Course Image is present.");
        verifyCourseNameAndDescription("CourseMaterialCourseNameText",
                "CourseMaterialCourseDescriptionText",
                ResourceConfigurations.getProperty("revelSubscribedCourseName"),
                ResourceConfigurations.getProperty(
                        "revelSubscribedCourseDescription"),
                "Revel Course");
        verifyRevelImagePosition();

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that Revel course image ,position and description
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMMLCourseImageAndDescription() {

        APP_LOGS.debug("Verify that Revel course image is displayed.");

        verifyCourseImage("CourseMaterialCourseImage",
                "Verify MML Course Image is present.");
        verifyCourseNameAndDescription("CourseMaterialCourseNameText",
                "CourseMaterialCourseDescriptionText",
                ResourceConfigurations.getProperty("mmlSubscribedCourseName"),
                ResourceConfigurations.getProperty(
                        "mmlSubscribedCourseDescription"),
                "MML Course");
        // verifyRevelImagePosition();

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author nitish.jaiswal
     * @date 03 Oct,2017
     * @description Verify Position of Revel Course, which should come at top
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyMMLStartButtonPosition() {

        APP_LOGS.debug("Verify Position of Rio Course image in Grid View.");

        int startButtonPosition = 0;
        int chpaterNamePosition = 0;

        FindElement element = new FindElement();
        List<WebElement> startButtons = element
                .getElements("CourseMaterialAllMMLStartButton");

        List<WebElement> chapterNames = element
                .getElements("CourseMaterialAllMMLChapterName");

        if (startButtons.size() == chapterNames.size()) {
            boolean bFlag = true;
            for (int i = 0; i < startButtons.size(); i++) {
                startButtonPosition = startButtons.get(i).getLocation().getX();
                chpaterNamePosition = chapterNames.get(i).getLocation().getX();
                if (startButtonPosition > chpaterNamePosition) {
                    bFlag = false;
                    break;
                }
            }
            if (bFlag) {
                result = Constants.PASS
                        + ": Start button position is ahead of its respective chapters poistion. '";
                logResultInReport(result,
                        "Verify MML start button position is ahead of chapter name.",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL + ": Start button position '"
                        + startButtonPosition
                        + "' is not ahead of its respective chapters poistion '"
                        + chpaterNamePosition + "'.";
                logResultInReport(result,
                        "Verify MML start button position is ahead of chapter name.",
                        this.reportTestObj);
            }
        } else {
            result = Constants.FAIL + ": Start button count '"
                    + startButtons.size()
                    + "' and its respective chapters count '"
                    + chapterNames.size() + "' is not matching.";
            logResultInReport(result,
                    "Verify MML start button position is ahead of chapter name.",
                    this.reportTestObj);
        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nitish.jaiswal
     * @date 03 Oct,2017
     * @description verify course material page title
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifyCourseMaterialPageTitle(String title, String message) {

        APP_LOG.debug("Click page title for course material page.");
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                "CourseMaterialCourseNameText");
        verifyPageTitle(title, message);
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
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
     * @author nisha.pathria
     * @date 22 sep,2017
     * @description verify dropdown Values dropdown
     * @return The object of ProductApplication_courseHomePage
     */
    public GLPLearner_CourseMaterialPage verifyFilterByDropdownValues() {

        try {

            FindElement element = new FindElement();
            List<WebElement> filterOptions = element
                    .getElements("CourseMaterialFilterValues");

            int i = 0;
            for (i = 0; i < filterOptions.size(); i++) {
                if (filterOptions.get(i).getText().trim()
                        .equals(ResourceConfigurations
                                .getProperty("filterValue" + (i + 1)))) {
                    result = Constants.PASS
                            + ": Verify Filter values are present as expected text and sequence";
                    logResultInReport(result,
                            "Verify Filter Option '"
                                    + ResourceConfigurations.getProperty(
                                            "filterValue" + (i + 1))
                                    + "' is displayed in Filter By dropdown.",
                            this.reportTestObj);
                }
            }
        } catch (Exception e) {

            this.result = Constants.FAIL
                    + ":Error while comparing UserList data with WebList data because of: "
                    + e;

            logResultInReport(this.result,
                    "Error while comparing UserList data with WebList data",
                    this.reportTestObj);

        }

        return this;
    }

    /**
     * @author Abhishek.Sharda
     * @date 21 Sep,2017
     * @description To verify all chapters are expendable
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyCollapseAndExpandChapters() {
        FindElement element = new FindElement();
        try {
            clickOnElement("CourseMaterialCollapseIcon",
                    "Click on the collapse icon on the course material page");
            List<WebElement> expendIcon = element
                    .getElements("CourseMaterialExpendIcon");
            int verticleCoordinates = 300;
            for (int i = 0; i < expendIcon.size(); i++) {
                expendIcon.get(i).click();
                clickOnElement("CourseMaterialCollapseIcon",
                        "Click on the collapse icon on the course material page");
                objCommonUtil.scrollWebPage(0, verticleCoordinates);
                verticleCoordinates = verticleCoordinates + 100;
                result = Constants.PASS
                        + ":Verify expend & collapse icons are present & expending/collapsing as expected on 'Course Material' page";
                logResultInReport(result,
                        "Verify expend & collapse icons are present & expending/collapsing as expected on 'Course Material' page",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            this.result = Constants.FAIL
                    + ":Verify expend & collapse icons are present & expending/collapsing as expected on 'Course Material' page"
                    + e;

            logResultInReport(this.result,
                    "Error while clicking on expending & collapse icons on 'Course Material' page",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    public GLPLearner_CourseMaterialPage verifyAllChapterContent() {
        FindElement element = new FindElement();
        try {

            List<WebElement> allChapters = element
                    .getElements("CourseMaterialChapterTile");
            int verticleCoordinates = 300;
            for (WebElement chapter : allChapters) {
                String chapterName = chapter.getText();
                System.out.println("chapters value :" + chapter.getText());
                chapter.click();
                verifyElementPresent("CourseMaterialCollapseIcon",
                        "Verify arrow icon gets inverted");
                verifyElementPresent("CourseChapterContents",
                        "Verify chapter contents " + chapterName
                                + "are present.");
                clickOnElement("CourseMaterialCollapseIcon",
                        "Click on the collapse icon on the course material page");

                objCommonUtil.scrollWebPage(0, verticleCoordinates);
                verticleCoordinates = verticleCoordinates + 100;
            }
        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception occured while verfying chapter contesnt \"" + e);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that myMathLab course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifymyMathLabCourseImage() {

        APP_LOGS.debug("Verify that myMathLab course image is displayed.");

        verifyElementPresent("CourseMaterialCourseImage",
                "Verify myMathLab Course Image is present.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 14 Sep,2017
     * @description Verify myMathLab Course name and its description
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifymyMathLabCourseNameAndDescription() {

        APP_LOGS.debug("Verify Rio Course name and its description");

        verifyText("CourseMaterialCourseNameText",
                ResourceConfigurations
                        .getProperty("myMathLabSubscribedCourseName"),
                "Verify 'myMathLab Course Name' is displayed.");
        verifyTextContains("CourseMaterialCourseDescriptionText",
                ResourceConfigurations
                        .getProperty("myMathLabSubscribedCourseDescription"),
                "Verify 'myMathLab Course Description' is displayed.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that myMathLab course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifymyMathLabCourseChapters() {

        APP_LOGS.debug("Verify that myMathLab course chapters are displayed.");

        verifyElementPresent("CourseMaterialExpendIcon",
                "Verify myMathLab Course chapters are present.");

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author nitish.jaiswal
     * @date 03 Oct,2017
     * @description Verify Position of myMathLab Course, which should come at
     *              top
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifymyMathLabImagePosition() {

        APP_LOGS.debug("Verify Position of Rio Course image in Grid View.");

        FindElement element = new FindElement();
        WebElement item = element.getElement("CourseMaterialCourseImage");
        int rioImagePosition = item.getLocation().getX();

        WebElement item1 = element.getElement("CourseMaterialExpendIcon");
        int rioCourseDecriptionPosition = item1.getLocation().getX();

        if (rioImagePosition < rioCourseDecriptionPosition) {
            result = Constants.PASS
                    + ": myMathLab Course Image and its description '"
                    + rioImagePosition
                    + "' is in the Top of myMathLab Chapters position on UI'"
                    + rioCourseDecriptionPosition + "'";
            logResultInReport(result,
                    "Verify myMathLab Image and its description is on the Top of its chapters.",
                    this.reportTestObj);
        } else {
            result = Constants.FAIL
                    + ": myMathLab Course Image and its description '"
                    + rioImagePosition
                    + "' is in the Top of myMathLab Chapters position on UI'"
                    + rioCourseDecriptionPosition + "'";
            logResultInReport(result,
                    "Verify myMathLab Image and its description is on the Top of its chapters.",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Nitish.Jaiswal
     * @date 03 Oct,2017
     * @description Verify that myMathLab course image ,position and description
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifymyMathLabCourseImageAndDescription() {

        APP_LOGS.debug("Verify that myMathLab course image is displayed.");

        verifymyMathLabCourseImage();
        verifymyMathLabCourseNameAndDescription();
        verifymyMathLabImagePosition();

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify navigation to learning Objective Page
     * @return The object of ProductApplication_CourseMaterialPage
     * @throws InterruptedException
     */
    public GLPLearner_CourseMaterialPage navigationToCoreInstructions() {
        APP_LOGS.debug(
                "Verify that navigation to PracticeTest working sucesfully.");
        clickOnElement("CourseMaterial2.1Chapter", "Click on the chapter 2.1");
        clickOnElement("CourseMaterial2.1.1Chapter",
                "Click on the chapter 2.1.1");
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify navigation to Practice test
     * @return The object of ProductApplication_CourseMaterialPage
     * @throws InterruptedException
     */
    public GLPLearner_LearningObjectivePage navigationToPracticeTest() {
        APP_LOGS.debug(
                "Verify that navigation to PracticeTest working sucesfully.");
        clickOnElement("CourseMaterial2.1Chapter", "Click on the chapter 2.1");
        clickOnElement("CourseMaterialPracticeTest",
                "Click on the Practice Test");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    public GLPLearner_CourseMaterialPage scrollToElement(String element) {
        APP_LOGS.debug("Scroll to the Element: " + element);
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        objCommonUtil.scrollIntoView(element);
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Mayank Mittal
     * @date 26 Feb,2018
     * @description verify no. of Start button in a module and partition of the
     *              progress circle.
     * @return The object of ProductApplication_CourseMaterialPage
     */

    public GLPLearner_CourseMaterialPage
           verifyNumberOfLOs(String elementLocator1, String elementLocator2) {

        String dropdownSize = this.performAction
                .execute(GET_NUMBER_OF_ELEMENT_IN_LIST, elementLocator1);
        String progressCircleSize = this.performAction
                .execute(GET_NUMBER_OF_ELEMENT_IN_LIST, elementLocator2);
        if (Integer.valueOf(dropdownSize) == Integer
                .valueOf(progressCircleSize)) {
            result = Constants.PASS
                    + ":dropdownSize and progressCircleSize matches.'";
            logResultInReport(result,
                    "dropdownSize and progressCircleSize matches. ",
                    this.reportTestObj);
        } else {
            result = Constants.FAIL
                    + ":dropdownSize and progressCircleSize doesn't matches.";
            logResultInReport(result,
                    "dropdownSize and progressCircleSize doesn't matches.",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

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
     * @author pallavi.tyagi
     * @date 23 April,2018
     * @description get element css value
     */
    public void verifyElementCssAttributeValue(String element,
            String attributeName, String verifyText, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_CSS_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    public GLPLearner_CourseHomePage clickCourseModuleByIndex(String element,
            String position, String message) {
        APP_LOGS.debug("Click on course module:" + (position + 1));
        this.APP_LOGS.debug(message);
        this.result = this.performAction.execute(ACTION_CLICK_INDEX_POSITION,
                element, position);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author shivam.garg
     * @date 07 March 2018
     * @description :Click on browser back button
     * @return The object of ProductApplication_CreateAccountPage
     */
    public GLPLearner_CourseViewPage clickBrowserBackButton(String message) {
        this.result = this.performAction.execute(ACTION_NAVIGATE_BROWSER_BACK);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_CourseViewPage(reportTestObj, APP_LOG);
    }

    public GLPLearner_CourseMaterialPage playVideo() {

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        objCommonUtil.verifyVideoPlayback("InteractiveVideo",
                "Verify rendering and playback of coming video");
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Pallavi.Tyagi
     * @date 20 Mar,2018
     * @description verify start button next to each lo (Start,Review)
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage
           verifyStateOftButtonNextToEachLo(String state) {

        APP_LOGS.debug("");

        int i = 0;
        FindElement element = new FindElement();
        List<WebElement> menuOptions = element
                .getElements("CourseMatrerialmoduleAllLo");
        for (i = 0; i < (menuOptions.size() - 1); i++) {
            if (menuOptions.get(i).getText().trim().contains(state)) {
                result = Constants.PASS + ": Verify " + state
                        + " button is displayed next to :" + (i + 1)
                        + " Lo's name.";
                logResultInReport(Constants.PASS,
                        "Verify " + state + " button is displayed next to :"
                                + (i + 1) + " Lo's name,",
                        this.reportTestObj);
            } else {
                result = Constants.FAIL + ": Verify " + state
                        + " button is not displayed next to :" + (i + 1)
                        + " Lo's name.";
                logResultInReport(Constants.FAIL,
                        "Verify " + state + " button is not displayed next to :"
                                + (i + 1) + " Lo's name,",
                        this.reportTestObj);
            }
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Rashmi.z
     * @date 19 March,2018
     * @description Getting all contents from chapters
     * @return The object of GLPLearner_CourseMaterialPage
     * @throws InterruptedException
     */
    public GLPLearner_CourseMaterialPage getAllChapters()
            throws InterruptedException {
        FindElement element = new FindElement();
        Thread.sleep(5000);
        List<WebElement> chapterTitles = element.getElements("ChapterTitles");
        int childListSize = 0;
        for (int i = 0; i < chapterTitles.size(); i++) {
            // WebElement chapterTitle = chapterTitles.get(i);
            if (i != 0) {
                chapterTitles.get(i).click();
                logResultInReport(
                        Constants.PASS + ": Parent Chapter Title is clicked",
                        "Click on Parent Chapter Title", this.reportTestObj);
            }
            List<WebElement> chapterSubTitles = null;
            if (childListSize != 0) {
                chapterSubTitles = element.getElements("ChapterSubTitles");

                for (int k = 0; k < childListSize; k++) {
                    chapterSubTitles.remove(0);
                }
                childListSize = childListSize + chapterSubTitles.size();

            } else {
                chapterSubTitles = element.getElements("ChapterSubTitles");
                childListSize = chapterSubTitles.size();
            }

            for (int j = 0; j < chapterSubTitles.size(); j++) {
                WebElement chapterSubTitle = chapterSubTitles.get(j);
                if (chapterSubTitle.getText().contains("Practice Quiz")) {
                    break;
                }
                chapterSubTitle.click();
                logResultInReport(
                        Constants.PASS + ": Child Chapter Title is clicked",
                        "Click on Child Chapter Title", this.reportTestObj);
                // Method to validate image is available and not broken
                verifyImageIsAvailble();
            }
            chapterTitles.get(i).click();

        }

        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Rashmi.z
     * @date 19 March,2018
     * @description Image validation for learner content.
     * @return The object of GLPLearner_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyImageValidationChapter() {

        try {
            FindElement element = new FindElement();
            List<WebElement> ToggleButton = element
                    .getElements("CourseMaterialToggleModulesArrow");
            for (int i = 0; i < ToggleButton.size(); i++) {
                int d = i + 1;
                System.out.println("Before D:" + d);
                WebElement ToggleButton1 = returnDriver().findElement(By.xpath(
                        "//div[@class='courseContent panel panel-default'][" + d
                                + "]//button[contains(@class, 'toggleModule')]"));

                WebElement moduleNme = returnDriver().findElement(By.xpath(
                        "//div[@class='courseContent panel panel-default'][" + d
                                + "]//button[contains(@class, 'toggleModule')]/../div[3]/button/label"));
                if (!moduleNme.getText().equalsIgnoreCase("Module 13:")
                        || !moduleNme.getText().equalsIgnoreCase("Module 14:")
                        || !moduleNme.getText()
                                .equalsIgnoreCase("Module 16:")) {
                    System.out.println("Module Name = " + moduleNme.getText());
                    ToggleButton1.click();
                }
                Thread.sleep(3000);
                List<WebElement> ContinueButton = element
                        .getElements("ContinueBtn");
                int compare = ContinueButton.size() - 1;
                for (int j = 0; j < ContinueButton.size(); j++) {
                    int c = j + 1;
                    WebElement continueButton = returnDriver().findElement(By
                            .xpath("//div[@class='panelExpanded panel panel-default']//span[contains(@class,'list-group-item')]["
                                    + c + "]//button"));
                    continueButton.click();
                    Thread.sleep(3000);
                    logResultInReport(
                            Constants.PASS + ": Continue button is clicked.",
                            "Click on Continue Button.", this.reportTestObj);
                    getAllChapters();
                    clickOnElement("BackButton", "Click on Back Button");

                    Thread.sleep(5000);
                    System.out.println("After D:" + d);
                    WebElement ToggleButton2 = returnDriver().findElement(By
                            .xpath("//div[@class='courseContent panel panel-default']["
                                    + d
                                    + "]//button[contains(@class, 'toggleModule')]"));

                    WebElement moduleNme1 = returnDriver().findElement(By
                            .xpath("//div[@class='courseContent panel panel-default']["
                                    + d
                                    + "]//button[contains(@class, 'toggleModule')]/../div[3]/button/label"));

                    if (!moduleNme1.getText().equalsIgnoreCase("Module 13:")
                            || !moduleNme1.getText()
                                    .equalsIgnoreCase("Module 14:")
                            || !moduleNme1.getText()
                                    .equalsIgnoreCase("Module 16:")) {
                        System.out.println(
                                "Module Name = " + moduleNme1.getText());

                        if (j != compare) {
                            ToggleButton2.click();
                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Rashmi.z
     * @date 20 March,2018
     * @description Broken Image validation for learner content.
     * @return The object of GLPLearner_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyImageIsAvailble() {
        FindElement element = new FindElement();
        try {

            // Find total No of images on page and print In console.
            List<WebElement> total_images = element.getElements("ImageName");
            System.out.println("Total Number of images found on page = "
                    + total_images.size());
            logResultInReport(
                    Constants.PASS + ": Total Number of images found on page ="
                            + total_images.size(),
                    "Total Number of images found on page", this.reportTestObj);

            // for loop to open all images one by one to check response code.
            int invalidImageCount = 0;
            if (total_images.size() > 0) {

                for (int i = 0; i < total_images.size(); i++) {
                    String url = total_images.get(i).getAttribute("src");
                    if (url != null) {

                        HttpClient client = HttpClientBuilder.create().build();
                        HttpGet request = new HttpGet(
                                total_images.get(i).getAttribute("src"));
                        HttpResponse response = client.execute(request);
                        // verifying response code he HttpStatus should be 200
                        // if not, increment as invalid images count
                        if (response.getStatusLine().getStatusCode() == 200) {

                            System.out.println("Valid image:" + url);
                            logResultInReport(
                                    Constants.PASS
                                            + ": Image is available and it is valid image.",
                                    "Verify the image is available.",
                                    this.reportTestObj);
                        } else if (response.getStatusLine()
                                .getStatusCode() != 200) {
                            logResultInReport(
                                    Constants.PASS
                                            + ": Image is available and it is broken image.",
                                    "Verify for the broken image.",
                                    this.reportTestObj);
                            System.out.println("Broken image ------> " + url);

                        }

                    } else {
                        // If <a> tag do not contain href attribute and value
                        // then print this message
                        System.out.println("String null");
                        logResultInReport(
                                Constants.PASS + ": Image is not available.",
                                "Verify the image is available.",
                                this.reportTestObj);
                        continue;
                    }
                    System.out.println(
                            " Image count is::: " + invalidImageCount++);
                }

            } else {
                System.out.println("No Image available");
                logResultInReport(Constants.PASS + ": Image is not available.",
                        "Verify the image is available.", this.reportTestObj);
            }

        } catch (Exception e) {
            System.out.println("No Image available");
            logResultInReport(Constants.PASS + ": Image is not available.",
                    "Verify the image is available.", this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author Rashmi.z
     * @date 19 March,2018
     * @description Image validation for learner content.
     * @return The object of GLPLearner_CourseMaterialPage
     */
    public GLPLearner_CourseMaterialPage verifyImageValidation() {

        try {
            getAllChapters();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 20 Dec,2017
     * @description verify Text Comparison
     */

    public void compareText(String text1, String text2) {

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

    }

    /**
     * @author yogesh.choudhary
     * @description: Click and Verify TOC
     */

    public GLPLearner_CourseMaterialPage verifyTOC() {

        try {
            APP_LOGS.debug("Navigate to Table of Content");

            // Verify TOC opened
            verifyElementPresent("TOC", "Verify TOC is opened");

        } catch (Exception e) {
            APP_LOG.debug("Exception in navaigating TOC");
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @description: Navigate using Next arrow till last Sub Headings
     */

    public GLPLearner_CourseMaterialPage navigateToAllNextQuestions() {
        List<WebElement> list = findElement.getElements("ActiveEoItems");
        String nextLinkText = "";
        String contentTitle = "";
        try {
            APP_LOGS.debug("Attempt all subheadings");
            for (int i = 0; i < (list.size() - 1); ++i) {
                nextLinkText = this
                        .getText("CoreInstructionForwardArrowLableText");
                this.clickOnElement("CoreInstructionForwardArrowLableText",
                        "Click on Next arrow");
                contentTitle = this.getText("ContentTitle");
                if (i != (list.size() - 2))
                    this.compareTextContains(nextLinkText, contentTitle);
                if (verifyElementPresentWithOutLog("EODetailPageNextArrow")
                        .contains(Constants.FAIL)) {
                    APP_LOG.debug(
                            "Next arrow is not available all content attempted");
                    this.verifyElementPresent("PracticeTestStartButton",
                            "Verify Next arrow is not visible and practice test opened");
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating till last EO content");
            logResultInReport(
                    Constants.FAIL
                            + ": Not able to navigate to next content of EO : "
                            + e.getMessage(),
                    "Verify user navigates to next content in EO",
                    reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @date 20 Dec,2017
     * @description verify Text should not be same
     */

    public void compareTextNotSame(String text1, String text2) {

        if (text1.equals(text2)) {
            this.result = Constants.FAIL + ": Actual Text : " + text1
                    + " is not same as Expected Text :" + text2;
            logResultInReport(this.result,
                    "Verify that Actual text is same as Expected text",
                    this.reportTestObj);

        } else {
            this.result = Constants.PASS + ": Actual Text : " + text1
                    + " is same as Expected Text : " + text2;
            logResultInReport(this.result,
                    "Verify that Actual text is equal to Expected text",
                    this.reportTestObj);

        }

    }

    /**
     * @author yogesh.choudhary
     * @date 29 Mar,2017
     * @description Verify footer verbs for selected sub heading on TOC
     */
    public GLPLearner_CourseMaterialPage VerifyFooterVerbs(String contentType,
            String verb) {
        String content = null;
        WebDriver webDriver = returnDriver();
        List<WebElement> list = findElement.getElements("SubHeadingsFirstEo");

        try {
            APP_LOGS.debug(
                    "Verify footer verbs for selected subheading on TOC");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                content = (String) js.executeScript(
                        "return document.getElementsByClassName('icon-content')["
                                + i + "].getAttribute('type');");

                if (content.equals(contentType)) {
                    // Click on Right Arrow
                    clickOnElement("EODetailPageNextArrow",
                            "Click on Right Arrow");
                    // Verify verb on left side
                    verifyText("FooterVerbs", verb, "Verify Footer Text ");
                    break;
                } else {
                    continue;
                }

            }

        } catch (Exception e) {
            APP_LOG.debug(
                    "Exception in getting the footer verb for select EO subheadings");
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);

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
     * @author yogesh.choudhary
     * @description: Navigate using All EO menu Item
     */

    public GLPLearner_CourseMaterialPage navigateToAllEOMenuItem() {

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
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @description: Verify the TOC progress on EC Circle
     */

    public GLPLearner_CourseMaterialPage verifyEOProgressOnCircle() {
        WebDriver webDriver = returnDriver();
        List<WebElement> list = findElement.getElements("SubHeadingsFirstEo");
        String circleCOnstant = returnDriver()
                .findElement(By.cssSelector(".toc__circular__progress__bar"))
                .getAttribute("stroke-dasharray");

        try {
            APP_LOGS.debug("Verify the TOC progress on EC Circle");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                if (circleCOnstant != returnDriver()
                        .findElement(
                                By.cssSelector(".toc__circular__progress__bar"))
                        .getAttribute("stroke-dashoffset")) {
                    APP_LOG.debug("Progress is updated on EO circle.");

                    this.result = Constants.PASS
                            + ": Verify the TOC progress on EC Circle";
                    logResultInReport(this.result,
                            "Verify the TOC progress on EC Circle",
                            this.reportTestObj);
                    break;
                }

            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating till last EO content");
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     * @description: Return Default value of EO Progress bar
     */

    public String getDefaultEoProgressValue() {
        String circleConstant = null;
        try {
            WebDriver webDriver = returnDriver();
            circleConstant = returnDriver()
                    .findElement(By.cssSelector(".circular-progress-bar"))
                    .getAttribute("stroke-dasharray");

        } catch (Exception e) {
            APP_LOG.debug("Exception in getting the EP Progress value");
        }
        return circleConstant;
    }

    /**
     * @author yogesh.choudhary
     * @description: Return Actual value of EO Progress bar
     */

    public String getActualEoProgressValue() {
        String circleProgress = null;
        try {
            WebDriver webDriver = returnDriver();
            circleProgress = returnDriver()
                    .findElement(By.cssSelector(".circular-progress-bar"))
                    .getAttribute("stroke-dashoffset");

        } catch (Exception e) {
            APP_LOG.debug("Exception in getting the EP Progress value");
        }
        return circleProgress;
    }

    /**
     * @author yogesh.choudhary
     * @description: Return Default value of EO Progress bar
     */

    public GLPLearner_CourseMaterialPage completeAndVerifyTocProgress() {
        String content = null;
        WebDriver webDriver = returnDriver();
        List<WebElement> list = findElement.getElements("SubheadingImg");

        try {
            APP_LOGS.debug("Navigate to each menu item under EO");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                String itemType = list.get(i).getAttribute("type");
                if (itemType.equals("quizz")) {
                    GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                            reportTestObj, APP_LOG);

                    clickOnElement("PracticeTestStartButton",
                            "Click on Practice Test Start Button");
                    // Attempt Practice Test
                    objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                            ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"));

                }
            }
            // Check the value is zero
            if (getActualEoProgressValue().equals("0")) {
                this.result = Constants.PASS;
                logResultInReport(this.result,
                        "Verify that user naviagted through all menu items",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating through EO menu items");
            this.result = Constants.FAIL;
            logResultInReport(this.result,
                    "Verify that user naviagted through all menu items",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);

    }

    /**
     * @author yogesh.choudhary
     * @description: Return Default value of EO Progress bar
     */

    public GLPLearner_CourseMaterialPage verifyTocProgressPercentage() {
        String circleConstant = null;
        List<WebElement> list = findElement.getElements("SubheadingImg");
        circleConstant = getDefaultEoProgressValue();
        circleConstant = circleConstant.substring(0,
                circleConstant.indexOf(".") + 2);
        float circleprogress = Float.parseFloat(circleConstant);
        float singleProgressValue = circleprogress / (list.size());

        try {
            APP_LOGS.debug("Navigate to each menu item under EO");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                String itemType = list.get(i).getAttribute("type");
                if (itemType.equals("quizz")) {
                    GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                            reportTestObj, APP_LOG);

                    /* Click on Practice Test Start Button */
                    clickOnElement("PracticeTestWelcomeScreenStartButton",
                            "Click on Pratice Start Button");
                    // Attempt Practice Test
                    objGLPLearnerPracticeTestPage.attemptPracticeTest(0, 0,
                            ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"));

                }
                String actualProgressvalue = getActualEoProgressValue();
                actualProgressvalue = actualProgressvalue.substring(0,
                        actualProgressvalue.indexOf(".") + 2);
                float actualProgress = Float.parseFloat(actualProgressvalue);
                DecimalFormat f = new DecimalFormat("##.#");
                float percentageProgress = circleprogress
                        - (singleProgressValue * (i + 1));
                percentageProgress = Float
                        .parseFloat(f.format(percentageProgress));

                if (actualProgress == percentageProgress) {
                    APP_LOG.debug(
                            "TOC progress increases after selecting Menu Items");
                    this.result = Constants.PASS;
                    logResultInReport(this.result,
                            "Verify that user naviagted through all menu items",
                            this.reportTestObj);
                }

            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating through EO menu items");
            this.result = Constants.FAIL;
            logResultInReport(this.result,
                    "Verify that user naviagted through all menu items",
                    this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);

    }

    /**
     * @author yogesh.choudhary
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
     * @date 16 May,2018
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
     * @author ratnesh.singh
     * @date 16 May,2018
     * @description Click on a web element containing aria-label in element list
     * @return N/A
     */
    public void clickOnElementContainsLabel(String locator,
            String labelSubString) {
        APP_LOGS.debug("Click on the Element: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLICK_LABEL_IN_LIST,
                locator, labelSubString);

        if (this.result.contains(Constants.PASS)) {
            logResultInReport(this.result,
                    "Verify that element containing label is clicked",
                    this.reportTestObj);
        } else {
            logResultInReport(this.result,
                    "Verify that element containing label is clicked",
                    this.reportTestObj);
        }

    }

    /**
     * @author lekh.bahl
     * @date 14 May,2018
     * @description Get number of LO in module
     */
    public int getCompletedLoCount() {
        int loCount = 0;
        try {
            FindElement ele = new FindElement();
            loCount = ele.getElements("CompleteLoCount").size();
        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Failed to get number of LO "
                            + e.getMessage(),
                    "Failed to get number of LO", reportTestObj);
        }
        return loCount;
    }

    /**
     * @author anurag.garg1
     * @date 2018-04-19
     * @returnType String
     * @param element
     * @param attributeName
     * @param stepDesc
     * @return the attribute value
     * @description
     * @precondition
     */

    public String getElementAttribute(String element, String attributeName,
            String stepDesc) {
        this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element,
                attributeName);
        logResultInReport(
                Constants.PASS + ": " + "Attribute " + attributeName
                        + " 's value is : " + this.result,
                stepDesc, this.reportTestObj);
        return this.result;
    }

    /**
     * verifyElementsAreGryedOut method check given elements are gryed out using
     * attribute value
     * 
     * @author sumit.bhardwaj
     * @param element
     *            --> element name
     * @param attribute
     *            --> name of attribute
     * @param value
     *            --> value need to assert
     * @param description
     *            --> description of elements
     * @return --> Pass/Fail
     */
    public GLPLearner_CourseMaterialPage verifyElementsAreGryedOut(
            String element, String attribute, String value,
            String description) {
        FindElement el = new FindElement();
        boolean flag = false;
        try {
            List<WebElement> startButton = el.getElements(element);

            for (WebElement e : startButton) {

                if (!(value).equalsIgnoreCase(e.getAttribute(attribute))) {
                    flag = true;
                }
            }

            if (flag) {
                this.result = Constants.FAIL
                        + ":All given elements in LO are not gryed out";

                logResultInReport(this.result, description, this.reportTestObj);
            } else {
                this.result = Constants.PASS
                        + ":All given elements in LO are gryed out";

                logResultInReport(this.result, description, this.reportTestObj);
            }
        } catch (Exception e) {
            this.result = Constants.FAIL
                    + ":Error while checking elements in LO are gyryed out beacuse of "
                    + e;

            logResultInReport(this.result, description, this.reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author yogesh.choudhary
     * @description: Verify the Progress changes after Video Navigation
     */

    public GLPLearner_CourseMaterialPage
           verifyEOProgressOnCircleAfterVideoNavigation() {
        WebDriver webDriver = returnDriver();
        List<WebElement> list = findElement.getElements("SubHeadingsFirstEo");
        String circleCOnstant = returnDriver()
                .findElement(By.cssSelector(".toc__circular__progress__bar"))
                .getAttribute("stroke-dasharray");

        try {
            APP_LOGS.debug("Verify the TOC progress on EC Circle");

            for (int i = 0; i < list.size(); ++i) {
                list.get(i).click();
                String itemType = list.get(i).getAttribute("type");
                if (itemType.equals("video")) {
                    if (circleCOnstant != returnDriver()
                            .findElement(By.cssSelector(
                                    ".toc__circular__progress__bar"))
                            .getAttribute("stroke-dashoffset")) {
                        APP_LOG.debug("Progress is updated on EO circle.");

                        this.result = Constants.PASS
                                + ": Verify the TOC progress on EC Circle";
                        logResultInReport(this.result,
                                "Verify the TOC progress on EC Circle",
                                this.reportTestObj);
                        break;
                    }
                } else {
                    verifyElementPresentWithOutLog("EODetailPageNextArrow");
                    clickOnElement("EODetailPageNextArrow",
                            "Click on Next Arrow");
                }

            }

        } catch (Exception e) {
            APP_LOG.debug("Exception in navigating Video Content");
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     * @author yogesh.choudhary
     *
     * @description: Close any existing opened module & Open desired module
     */
    public GLPLearner_CourseHomePage navigateCourseModuleByIndex(String element,
            int position, String message) {
        APP_LOGS.debug("Click on course module:" + (position));
        this.APP_LOGS.debug(message);

        if (verifyElementPresentWithOutLog("OpenedModule")
                .contains(Constants.PASS)) {
            // Close the Opened Module
            clickOnElement("OpenedModule", "Close any Opened Module");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        FindElement webelement = new FindElement();
        List<WebElement> modulelist = webelement
                .getElements("CourseMaterialModuleTitleButton");
        modulelist.get(position - 1).click();
        logResultInReport(this.result, message, this.reportTestObj);

        return new GLPLearner_CourseHomePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author mukul.sehra
     * @date 2018-06-01
     * @param moduleNumber
     *            - Example - 11/12/13
     * @return GLPLearner_CourseMaterialPage
     * @description Expands a particular module
     * @precondition - Learner should be on course material page
     */
    public GLPLearner_CourseMaterialPage expandModule(String moduleNumber) {
        APP_LOG.info("inside expandModule method ....");
        try {
            performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "ModuleStartButton");
            String listIndex = String
                    .valueOf(Integer.parseInt(moduleNumber) - 11);
            WebElement moduleHeader = returnDriver().findElement(By.cssSelector(
                    ".course-home [id='header_" + listIndex + "']"));
            if (!(moduleHeader.getAttribute("class").contains("active"))) {
                returnDriver()
                        .findElement(By.cssSelector(
                                ".course-home [id='header_" + listIndex
                                        + "'] button.accordian-toggle-icon"))
                        .click();
                if (moduleHeader.getAttribute("class").contains("active"))
                    logResultInReport(
                            Constants.PASS + " : Section for module - "
                                    + moduleNumber + " has been expanded",
                            "Expand Module " + moduleNumber
                                    + " to view its LOs",
                            reportTestObj);
                else
                    logResultInReport(
                            Constants.FAIL + " : Section for module - "
                                    + moduleNumber + " didn't expand",
                            "Expand Module " + moduleNumber
                                    + " to view its LOs",
                            reportTestObj);
            } else
                logResultInReport(
                        Constants.PASS + " : Section for module - "
                                + moduleNumber + " is already expanded",
                        "Expand Module " + moduleNumber + " to view its LOs",
                        reportTestObj);
        } catch (Exception e) {
            APP_LOG.info("Exception while expanding module section : "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while expanding Section for module - "
                            + moduleNumber + ", " + e.getMessage(),
                    "Expand Module " + moduleNumber + " to view its LOs",
                    reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOGS);
    }

    /**
     * @author mukul.sehra
     * @date 2018-06-01
     * @param loNumber
     *            - Example - 11.1/11.2/12.1/12.2
     * @return GLPLearner_LearningObjectivePage
     * @description navigateToCoreInstructionsInLo
     * @precondition - user should be on course material page
     */
    public GLPLearner_LearningObjectivePage
           navigateToCoreInstructionsInLo(String loNumber) {
        APP_LOG.info("Inside navigateToCoreInstructionsInLo method .....");
        try {
            APP_LOG.debug("Click the Module caret icon..");
            expandModule(loNumber.split("\\.")[0]);
            FindElement element = new FindElement();
            element.checkPageIsReady();
            List<WebElement> listOfLO = returnDriver().findElements(
                    By.cssSelector(".accordian__body--active button"));
            for (WebElement loButton : listOfLO) {
                if (loButton.getAttribute("aria-label")
                        .contains(loNumber + " ")) {
                    loButton.click();
                    break;
                }
            }
            GLPLearner_LearningObjectivePage objLO = new GLPLearner_LearningObjectivePage(
                    reportTestObj, APP_LOG);
            objLO.verifyElementPresent("CoreInstructionButtonEO",
                    "Verify user has successfully navigated to CoreInstructions page of LO : "
                            + loNumber);
        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while navigating to LO page, Exception : "
                            + e.getMessage(),
                    "Verify user has navigated to learner's LO : " + loNumber,
                    reportTestObj);
        }
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOG);
    }

    /**
     * @author shefali.jain
     * @date 2018-06-04
     * @return void
     * @description -Verifying first EO is expanded on tapping restart button
     *              after any LO is failed in post assessment
     * @precondition - Restart button is tapped for failed LO after post
     *               assessment.
     */
    public void verifyFirstEOIsExpanded() {

        APP_LOG.info(
                "Verifying first eo is expanded on tapping restart button for failed LO...");
        try {
            String eoDefaultState = getElementAttribute(
                    "CoreInstruction1stEODefaultState", "class",
                    "Get the class attribute of the default expanded EO");
            if (eoDefaultState.contains("active")) {
                logResultInReport(
                        Constants.PASS + " : " + "First EO link is expanded ",
                        "Verifying first eo is expanded on tapping restart button for failed LO.",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + " : "
                                + "First EO link is not expanded ",
                        "Verifying first eo is expanded on tapping restart button for failed LO.",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured in method verifyFirstEOIsExpanded : "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL + " : "
                            + "Exception occured in method verifyFirstEOIsExpanded : "
                            + e.getMessage(),
                    "Verifying first eo is expanded on tapping restart button for failed LO.",
                    reportTestObj);
        }

    }

    /**
     * @author shefali.jain
     * @date 2018-06-04
     * @returnType void
     * @param stepDesc
     * @description clicking on last EO on TOC
     * @precondition
     */
    public void clickOnLastEO(String stepDesc) {
        APP_LOG.debug("Click on Last EO on Core Instructions page");
        try {

            FindElement findElement = new FindElement();
            List<WebElement> listofelements = findElement
                    .getElements("CoreInstructionLastEO");
            if (listofelements.size() > 0) {
                this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                        "CoreInstructionLastEO");
                listofelements.get(listofelements.size() - 1).click();
                logResultInReport(
                        Constants.PASS + " " + " : " + "Last LO is clicked",
                        stepDesc, reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + " " + " : " + "Last LO is not clicked",
                        stepDesc, reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Exception occured in method clickOnLastEO : "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL + " " + " : "
                            + "Exception occured in method clickOnLastEO ",
                    stepDesc, reportTestObj);
        }
    }

    /**
     * @author mohit.gupta5
     * @date 12 June,2018
     * @description verify course material page
     * @return The object of ProductApplication_CourseMaterialPage
     * @throws Throwable
     */
    public GLPLearner_CourseMaterialPage verifyCourseMaterialPage()
            throws Throwable {

        APP_LOG.debug("Verify Course material page.");
        String moduleNumber = null;
        try {
            // TimeUnit.SECONDS.sleep(5);
            this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    "CourseMaterialModuleHeader");
            List<WebElement> headerList = returnDriver()
                    .findElements(By.cssSelector(".accordian__header__data a"));
            System.out.println("Total Heder size:" + headerList.size());
            for (int i = 0; i < headerList.size(); i++) {
                moduleNumber = headerList.get(i).getText().substring(7, 9);
                System.out.println(moduleNumber);
                System.out.println(
                        ResourceConfigurations.getProperty("moduleHeader" + i));

                if (headerList.get(i).getText().contains(ResourceConfigurations
                        .getProperty("moduleHeader" + i))) {

                    logResultInReport(
                            Constants.PASS + " : Section for module - "
                                    + moduleNumber + " has been displayed",
                            "Expand Module " + moduleNumber
                                    + " to view its LOs",
                            reportTestObj);
                    this.performAction.execute(
                            ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                            "CourseMaterialFirstHeader");

                    expandModule(moduleNumber);
                    this.performAction.execute(
                            ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                            "CourseMaterialLOs");
                    System.out.println("Successfully Expand module ");

                    List<WebElement> LOList = returnDriver()
                            .findElements(By.cssSelector(
                                    ".accordian__body--active .accordian-content"));
                    System.out.println("Total LOs size:" + LOList.size());

                    for (int j = 0; j < LOList.size(); j++) {
                        String LoNumber = ResourceConfigurations.getProperty(
                                "lo" + LOList.get(j).getText().split("\\.")[0]
                                        + "_" + (j + 1));
                        System.out.println(LoNumber);

                        if (LOList.get(j).getText().contains(LoNumber)) {
                            logResultInReport(
                                    Constants.PASS + " : Section for LOs - "
                                            + LoNumber + " is display",
                                    "Expanded Module " + LoNumber
                                            + " LOs is display under"
                                            + moduleNumber,
                                    reportTestObj);

                        } else {
                            try {
                                String LastLo = "Module 16 Assessment";
                                if (LastLo.equals(ResourceConfigurations
                                        .getProperty("lo16_8"))) {
                                    break;
                                }
                            } catch (Exception e) {

                            }
                            logResultInReport(
                                    Constants.FAIL + " : Section for Modules - "
                                            + moduleNumber + " didn't display",
                                    "LOs '" + LoNumber + "' is Not Display",
                                    reportTestObj);
                        }

                    }

                } else {

                    logResultInReport(
                            Constants.FAIL + " : Section for Modules - "
                                    + moduleNumber + " is Not display",
                            "LOs '" + moduleNumber + "' is Not Display",
                            reportTestObj);
                }

            }
        } catch (Exception e) {
            APP_LOG.info("Exception while expanding module section : "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while expanding Section for module - "
                            + moduleNumber + ", " + e.getMessage(),
                    "Expand Module " + moduleNumber + " to view its LOs",
                    reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);
    }

    /**
     ** @author shefali.jain
     * @date 14 June,2018
     * @description Verify number of elements for specified locator
     * @return - GLPLearner_CourseMaterialPage
     */

    public GLPLearner_CourseMaterialPage verifyCountOfElements(String locator,
            int expectedCount, String stepdesc) {

        APP_LOG.info(
                "Verify the number of elements present for the passed locator");
        int countElements = 0;
        try {
            FindElement count = new FindElement();
            List<WebElement> countOfElements = count.getElements(locator);
            countElements = countOfElements.size();
            if (countElements == expectedCount) {
                logResultInReport(
                        Constants.PASS + " " + " : "
                                + "Number of elements present is same as expected count ",
                        stepdesc, reportTestObj);
            } else {
                logResultInReport(
                        Constants.FAIL + " " + " : "
                                + "Number of elements present is not same as expected count",
                        stepdesc, reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.info("Exception while counting number of elements : "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while counting number of elements:  "
                            + locator + ", " + e.getMessage(),
                    "Count number of " + locator + " in expanded module",
                    reportTestObj);
        }
        return new GLPLearner_CourseMaterialPage(reportTestObj, APP_LOG);

    }

    public void compareTextContains(String text1, String text2) {

        if (text1.contains(text2)) {
            this.result = Constants.PASS + ": Actual Text : " + text1
                    + " contains the Expected Text : " + text2;
            logResultInReport(this.result,
                    "Verify that Actual text contains the Expected text",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Actual Text : " + text1
                    + " does not contains Expected Text :" + text2;
            logResultInReport(this.result,
                    "Verify that Actual text contains the Expected text",
                    this.reportTestObj);

        }

    }

    /**
     * @author sumit.bhardwaj
     * @param moduleName
     *            --> Name of module which would expand
     */
    public void navigateCourseModuleByName(String moduleName) {

        try {
            // Check whether the any module is already expanded
            String strResult = verifyElementPresentWithOutLog("ActiveModule");
            if (strResult.contains(Constants.FAIL)
                    || !(getText("ActiveModule").contains(moduleName))) {
                // Expand the desired Learning Objective
                clickOnElementContainsInnerText(
                        "CourseMaterialModuleTitleButton", moduleName);
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while navigating to the desired module.");
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while navigating to the desired module",
                    "Navigate to the desired module", this.reportTestObj);
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param locator
     *            --> Name of locator
     * @param description-->
     *            Step description
     */
    public void clickOnElementIfVisible(String locator, String description) {

        try {

            // Click on go to course home link on diagnostic result page
            if (verifyElementPresentWithOutLog("DiagnosticGoToCourseHomeLink")
                    .contains(Constants.PASS)) {
                clickOnElement("DiagnosticGoToCourseHomeLink",
                        "Click on Go To Course Home Link to navigate to course material page");
            }

            APP_LOG.info(locator + ": is visible on screen and clicked");
            logResultInReport(
                    Constants.PASS + ": " + locator
                            + ": is visible on screen and clicked",
                    description, this.reportTestObj);

        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occurred while clicking on element: " + locator);
            logResultInReport(Constants.FAIL
                    + ": Exception occurred while clicking on element: "
                    + locator, description, this.reportTestObj);
        }
    }
}