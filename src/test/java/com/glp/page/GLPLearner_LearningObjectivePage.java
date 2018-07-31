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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.autofusion.util.FileUtil;
import com.autofusion.util.ParseHtml;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.response.Response;

public class GLPLearner_LearningObjectivePage extends BaseClass
        implements KeywordConstant {

    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private FindElement findElement = new FindElement();
    private PerformAction performAction = new PerformAction();
    protected int id;
    protected String validAnswer1, validAnswer2, invalidAnswer, locator;
    protected int quantity;
    protected String htmlfilepath = "chapter-02";
    protected String uiContent = "";
    protected String parseContent = "";

    public GLPLearner_LearningObjectivePage(String validAnswer1,
            String validAnswer2, String invalidAnswer) {
        this.validAnswer1 = validAnswer1;
        this.validAnswer2 = validAnswer2;
        this.invalidAnswer = invalidAnswer;

    }

    public GLPLearner_LearningObjectivePage(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author nitish.jaiswal
     * @date 12 July,2017
     * @description Verify text is present as expedted
     * @return The object of ProductApplication_courseHomePage
     */
    public String getQuestionCount(String Locator) {
        APP_LOGS.debug("Element is present: " + Locator);
        String textHeader = getText(Locator);
        if (!(textHeader.contains(Constants.FAIL))) {
            return textHeader.split("of")[1].trim();
        } else {
            return result;
        }

    }

    /**
     * @author mohit.gupta5
     * @date 07 April ,17
     * @description Enter Valid Data in the Search Text Box.
     */
    public void enterInputData(String element, String text, String testDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR,
                element, text);
        logResultInReport(this.result, testDesc, this.reportTestObj);
    }

    public String getIdentifier(String Locator) {

        String identifier = performAction.execute(ACTION_GET_ATTRIBUTE,
                Locator);
        APP_LOGS.debug(identifier);
        return identifier;
    }

    public String getText(String locator) {
        APP_LOGS.debug("Element is present: " + locator);
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
        return valueText;
    }

    public GLPLearner_LearningObjectivePage clickOnRadioButton(String Value,
            String Message) {
        APP_LOGS.debug("Click on the Element: " + "");
        List<WebElement> checkBox = findElement
                .getElements("AnswersRadioButton");
        int checkboxSize = checkBox.size();
        for (int i = 0; i < checkboxSize; i++) {
            String answerText = checkBox.get(i).getAttribute("value");
            if (answerText.equalsIgnoreCase(Value)) {
                checkBox.get(i).click();
                break;
            }
        }
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    public void practiceTest(String question, String identifier) {
        String key;
        Map<String, GLPLearner_LearningObjectivePage> map = new HashMap<>();
        GLPLearner_LearningObjectivePage b1 = new GLPLearner_LearningObjectivePage(
                "It is a mathematical statement that two expressions are equal. It always contains an equals sign.",
                "",
                "It is a letter or symbol that represents an unknown quantity.");
        /*
         * ProductApplication_LearningObjectivePage b2 = new
         * ProductApplication_LearningObjectivePage( "2x+y=92", "", "(28x/9)");
         * ProductApplication_LearningObjectivePage b3 = new
         * ProductApplication_LearningObjectivePage( "n+50", "", "n");
         * ProductApplication_LearningObjectivePage b4 = new
         * ProductApplication_LearningObjectivePage( "21x", "", "(1/4)");
         */
        map.put("What is an equation?", b1);
        /*
         * map. put(
         * "Which of the following is NOT part of the procedure for writing an equation from a word problem?"
         * , b2); map. put(
         * "Which of the following is NOT part of the procedure for writing an equation from a word problem?"
         * , b3); map. put(
         * "Which of the following is NOT part of the procedure for writing an equation from a word problem?"
         * , b4);
         */

        /*
         * map.put("Which of the following is NOT part of the translation of",
         * b3); map. put(
         * "Choose the word problem that can be represented by an equation in one variable"
         * , b4); map.put("Which of the following is an equation", b5);
         */

        for (Map.Entry<String, GLPLearner_LearningObjectivePage> entry : map
                .entrySet()) {
            key = entry.getKey();
            GLPLearner_LearningObjectivePage answer = entry.getValue();
            APP_LOGS.debug(key + " Details:");
            if (question.contains(key.trim()) && identifier
                    .toLowerCase(Locale.ENGLISH).contains("mcqma")) {
                APP_LOGS.debug("Matched key = " + question);
                clickOnRadioButton(answer.validAnswer1,
                        "Click on the radio button");
                clickOnRadioButton(answer.validAnswer2,
                        "Click on the radio button");
                APP_LOGS.debug("ok");

            } else if (question.contains(key) && identifier
                    .toLowerCase(Locale.ENGLISH).contains("mcqsa_1")) {
                APP_LOGS.debug("Matched key = " + question);
                clickOnRadioButton(answer.validAnswer1,
                        "Click on the radio button");
            } else if (question.contains(key)
                    && identifier.toLowerCase(Locale.ENGLISH).contains("fib")) {
                APP_LOGS.debug("Matched key = " + question);
                enterInputData("AnswersTextBox", "n+60",
                        "Enter correct answer using input box");
            } else {

                APP_LOGS.debug("No Matched key");
            }
        }

    }

    public GLPLearner_LearningObjectivePage clickOnElement(String locator,
            String message) {
        APP_LOGS.debug("Click on the Element: " + locator);

        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
        this.result = this.performAction.execute(ACTION_CLICK, locator);
        logResultInReport(this.result, message, this.reportTestObj);
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    public String verifyTextContains(String Locator, String Text,
            String Message) {
        APP_LOGS.debug("Element is present: " + Locator);
        performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
        result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator,
                Text);
        return result;
    }

    public void atemptPracticeTest() {

        String questionCount = getQuestionCount("TestQuestionCount");
        if (!(questionCount.contains("FAIL"))) {
            for (int i = 0; i < Integer.parseInt(questionCount); i++) {

                String identifier = getIdentifier("QuestionIdentifier");
                String question = getText("Questions");
                practiceTest(question, identifier);
                clickOnElement("SubmitButton", "Click On the 'Submit' button");
                result = verifyTextContains("TestResultHeader",
                        "Based on the Diagnostic Test Result",
                        "Verify whether question is completed as per correct answers ratio.");
                if (result.contains("PASS")) {
                    break;
                }
            }
        } else {
            logResultInReport(questionCount,
                    "Question Count text is not displayed", reportTestObj);
        }

    }

    /**
     * @author Pallavi.tyagi
     * @date 22 Aug,2017
     * @description Verify element is present
     * @return The object of ProductApplication_LearningObjectivePage
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
     * @author Pallavi.tyagi
     * @date 16 Sep,2017
     * @description Verify feedback toggling status in practice test
     * @return The object of ProductApplication_LearningObjectivePage
     */
    public GLPLearner_LearningObjectivePage verifyFeedBackTogglingInPractice() {
        clickOnElement("LearningObjective1stQuestion1stradiobutton",
                "Select first radio button");
        clickOnElement("SubmitButton", "Click On the 'Submit' button");
        String feedbackText = getText(
                "LearningObjective1stQuestionFeedBackToggling");
        if (feedbackText.equalsIgnoreCase("")) {

            this.result = Constants.FAIL
                    + ": Feedback toggling is not displayed in practice test";
            logResultInReport(this.result,
                    "Feedback toggling is not displayed in practice test",
                    this.reportTestObj);
        } else {
            this.result = Constants.PASS
                    + ": Feedback toggling is displayed in practice test";
            logResultInReport(this.result,
                    "Feedback toggling is displayed in practice test",
                    this.reportTestObj);

        }

        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /*
     * public ProductApplication_LearningObjectivePage
     * verifyExpectedSubchapterOpened(String subTopicLinkText) {
     * 
     * // clickOnElement("CourseMaterialMMLSubChapter", "Click on sub topic of
     * // the selected chapter.");
     * objCommonPage.verifyText("LearningObjectiveCourseSubjectHeader",
     * subTopicLinkText,
     * "Verify user can successfully see the the slected chapter contents.");
     * 
     * return new ProductApplication_LearningObjectivePage(reportTestObj,
     * APP_LOGS); }
     */

    public GLPLearner_LearningObjectivePage verifyNextPreviousSubTopic(
            String locator, String subTopicActiveTopicText, String desc) {

        // clickOnElement("CourseMaterialMMLSubChapter", "Click on sub topic of
        // the selected chapter.");
        String currentSubTopic = getText(locator);
        if (subTopicActiveTopicText.trim().equals(currentSubTopic.trim())) {
            this.result = Constants.FAIL + ": Current topic '"
                    + subTopicActiveTopicText
                    + "' is not changed to next/previous sub topic '"
                    + currentSubTopic + "'.";
            logResultInReport(this.result, desc, this.reportTestObj);
        } else {
            this.result = Constants.PASS + ": Current topic '"
                    + subTopicActiveTopicText
                    + "' is changed to next/previous sub topic '"
                    + currentSubTopic + "'.";
            logResultInReport(this.result, desc, this.reportTestObj);
        }

        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    public GLPLearner_LearningObjectivePage scrollToElement(String element) {

        APP_LOGS.debug("Scroll to the Element: " + element);
        PerformAction performAction = new PerformAction();
        performAction.execute(ACTION_SCROLL_INTO_VIEW, element);
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify that next arrow are given at the end of the page and
     *              on clicking they should take user to their respective name
     *              pages
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_LearningObjectivePage verifRevelCourseNextButton() {

        APP_LOGS.debug(
                "Verify that next arrow are given at the end of the page and on clicking they should take user to their respective name pages");
        performAction.execute(ACTION_SCROLL_INTO_VIEW,
                "LearningObjectiveChapterNextButton");
        verifyElementPresent("LearningObjectiveChapterNextButton",
                "Verify that 'Next' arrow are given at the end of the page");
        clickOnElement("LearningObjectiveChapterNextButton",
                "Verify on clicking next arrow user navigated to their respective next pages.");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify that Previous arrow are given at the end of the page
     *              and on clicking they should take user to their respective
     *              name pages
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_LearningObjectivePage verifRevelCoursePreviousButton() {

        APP_LOGS.debug(
                "Verify that next arrow are given at the end of the page and on clicking they should take user to their respective name pages");
        performAction.execute(ACTION_SCROLL_INTO_VIEW,
                "LearningObjectiveChapterPreviousButton");
        verifyElementPresent("LearningObjectiveChapterPreviousButton",
                "Verify that 'Previous' arrow are given at the end of the page");
        clickOnElement("LearningObjectiveChapterPreviousButton",
                "Verify on clicking Previous arrow user navigated to their respective next pages.");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify that revel course image is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_LearningObjectivePage verifyRioCourseImage() {

        APP_LOGS.debug("Verify that revel course image is displayed.");
        clickOnElement("LearningObjectiveChapterNextButton",
                "Click on the next button");
        objCommonUtil.scrollWebPage(0, 400);
        objCommonUtil.verifyimages("CourseInstruction2.1.1ChapterImage",
                "Verify Images are avilable and sucessfully rendering on the page");

        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);

    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify that revel course video is displayed
     * @return The object of ProductApplication_CourseMaterialPage
     * @throws InterruptedException
     */
    public GLPLearner_LearningObjectivePage verifyRiolVideoPlayback() {

        APP_LOGS.debug("Verify that revel course video is displayed.");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        objCommonUtil.verifyVideoPlayback(
                "CourseInstructionChapterImage2.1.1ChapterVideo",
                "Verify rendering and playback of coming video");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Abhishek.Sharda
     * @date 2 Oct,2017
     * @description Content validation for core instructions.
     * @return The object of ProductApplication_courseHomePage
     */
    public GLPLearner_LearningObjectivePage conentValidation() {

        try {
            getText("CourseAllHeading");
            objCommonUtil.scrollWebPage(0, 300);
            clickOnElement("CourseAllHeading", "");
            String parseContent = ParseHtml.parseHtmlContent(
                    "D:\\abc\\Ciccarelli-P-4e-R2-Brix-Update_v2 (1)\\OPS\\text\\chapter-01\\ch1_sec_00.xhtml");
            FileUtil.writeFile("D:\\abc\\Actual_ch1_sec_00", parseContent);
            clickOnElement("CourseMaterialExpend-Section",
                    "Click on the sections in courses");
            String uiContent = getText("PageContent");
            FileUtil.writeFile("D:\\abc\\UI_ch1_sec_00", uiContent);
            String parseContent01 = ParseHtml.parseHtmlContent(
                    "D:\\abc\\Ciccarelli-P-4e-R2-Brix-Update_v2 (1)\\OPS\\text\\chapter-01\\ch1_sec_01.xhtml");
            FileUtil.writeFile("D:\\abc\\Actual_ch1_sec_01", parseContent01);
            clickOnElement("CourseMaterialExpend-Section1",
                    "Click on the sections 1 in courses");
            clickOnElement("CourseMaterialExpend-Section1.1",
                    "Click on the sections 1.1 in courses");
            String uiContent01 = getText("PageContent");
            FileUtil.writeFile("D:\\abc\\UI_ch1_sec_01", uiContent01);
            String parseContent02 = ParseHtml.parseHtmlContent(
                    "D:\\abc\\Ciccarelli-P-4e-R2-Brix-Update_v2 (1)\\OPS\\text\\chapter-01\\ch1_sec_02.xhtml");
            FileUtil.writeFile("D:\\abc\\Actual_ch1_sec_02", parseContent02);
            clickOnElement("CourseMaterialExpend-Section1.2",
                    "Click on the sections 1.2 in courses");
            String uiContent02 = getText("PageContent");
            FileUtil.writeFile("D:\\abc\\UI_ch1_sec_02", uiContent02);

            returnDriver().get("https://www.diffchecker.com/diff");
            FindElement find = new FindElement();
            Actions actions = new Actions(returnDriver());
            actions.moveToElement(find.getElement("OriginalTextBox"));
            actions.click();
            actions.sendKeys(readFile("D:\\abc\\Actual_ch1_sec_01",
                    Charset.defaultCharset()));
            actions.build().perform();

            actions.moveToElement(find.getElement("ChangedTextBox"));
            actions.click();
            actions.sendKeys(readFile("D:\\abc\\UI_ch1_sec_01",
                    Charset.defaultCharset()));
            actions.build().perform();
            clickOnElement("Submit Button Difference", "");
        } catch (Exception e) {
            APP_LOG.error("Error Occured" + e);

        }
        return this;
    }

    public static String readFile(String path, Charset encoding) {
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            APP_LOG.error("Error while reading file content " + e);
            return null;
        }
        return new String(encoded, encoding);
    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Assert correct url loded
     * @return The object of ProductApplication_CourseMaterialPage
     * @throws InterruptedException
     */
    public GLPLearner_LearningObjectivePage assertPageTitle(String pageTitle,
            String element) {
        APP_LOGS.debug("Verify that navigation to page working sucesfully.");
        assertPageTitle(pageTitle, element,
                "Verify user Navigated to the expected webpage");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author Abhishek.Sharda
     * @date 12 July, 2017
     * @description Verify page title
     */
    public void assertPageTitle(String pageTitle, String element,
            String stepDesc) {
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE, "",
                pageTitle);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author Abhishek Sharda
     * @date 10 Oct,2017
     * @description Verify that Previous arrow are given at the end of the page
     *              and on clicking they should take user to their respective
     *              name pages
     * @return The object of ProductApplication_CourseMaterialPage
     */
    public GLPLearner_LearningObjectivePage
           verifRevelCoursePreviousButtonDisabled(String elementlocator,
                   String value) {
        APP_LOGS.debug(
                "Verify previous button coming as disabled  on 'Core Instruction'.");
        performAction.execute(ACTION_SCROLL_INTO_VIEW, elementlocator);
        verifyElementPresent(elementlocator,
                "Verify that 'Next' arrow are given at the end of the page");
        verifyButtonDisabled(elementlocator, value,
                "Verify previous button coming as disabled  on 'Core Instruction'");
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    /**
     * @author nisha.pathria
     * @date 10 May, 2017
     * @description To verify button is disabled or enabled
     */
    public void verifyButtonDisabled(String elementLocator, String value,
            String stepDesc) {

        this.result = this.performAction.execute(ACTION_VERIFY_ISENABLED,
                elementLocator, value);
        logResultInReport(this.result, stepDesc, this.reportTestObj);

    }

    /**
     * @author Rashmi Gaidhane
     * @date 10 Oct,2017/10 Oct 2017
     * @description Content validation for core instructions.
     * @return The object of ProductApplication_courseHomePage
     */

    public GLPLearner_CourseMaterialPage getAllRevelChapterContent() {
        int count = 0;
        returnDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        List<String> filePaths = htmlFiles();
        FindElement element = new FindElement();
        /*
         * List<WebElement> chapterTitles = returnDriver()
         * .findElements(By.xpath(
         * "//div[@class='inner-accordian-section']/div/div"));
         */
        List<WebElement> chapterTitles = element.getElements("ChapterTitles");

        for (int i = 3; i < chapterTitles.size(); i++) {
            WebElement w = chapterTitles.get(i);
            ((JavascriptExecutor) returnDriver()).executeScript(
                    "arguments[0].scrollIntoView();", chapterTitles.get(i - 2));
            w.click();

            int c = i + 1;
            WebElement chapterSubTitles = returnDriver().findElement(
                    By.xpath("//div[@class='inner-accordian-section']/div/div["
                            + c + "]//ul/li[1]/span/span[2]"));
            chapterSubTitles.click();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                APP_LOGS.debug("Timeout Exception" + e1);
            }
            /*
             * List<WebElement> chapterInnerSubTitles = returnDriver()
             * .findElements(By.
             * xpath("//div[@class='panel-collapse collapse in']/ul/li"));
             */
            List<WebElement> chapterInnerSubTitles = element
                    .getElements("ChapterInnerSubTitles");
            for (int j = 0; j < chapterInnerSubTitles.size(); j++) {
                /*
                 * chapterInnerSubTitles = returnDriver() .findElements(By.
                 * xpath("//div[@class='panel-collapse collapse in']/ul/li"));
                 */

                chapterInnerSubTitles = element
                        .getElements("ChapterInnerSubTitles");
                c = j + 1;
                WebElement wl = returnDriver().findElement(By
                        .xpath("//div[@class='panel-collapse collapse in']/ul/li["
                                + c + "]/span/span[2]"));
                if (!wl.getText().contains("Chapter Quiz:")) {
                    wl.click();
                    try {

                        /*
                         * List<WebElement> chapterbullets = returnDriver()
                         * .findElements(By. xpath(
                         * "//div[@class='panel-collapse collapse in']/ul/li//ul/li"
                         * ));
                         */

                        List<WebElement> chapterbullets = element
                                .getElements("ChapterBullets");
                        if (chapterbullets.size() > 0) {
                            for (WebElement chapterbullet : chapterbullets) {
                                if (!chapterbullet.getText()
                                        .contains("Quiz:")) {
                                    chapterbullet.click();
                                    // Read html content
                                    uiContent = getText("PageContent");
                                    parseContent = ParseHtml.parseHtmlContent(
                                            filePaths.get(count));
                                    count++;
                                    diffResults(parseContent, uiContent, count);
                                }
                            }
                            wl.click();
                        } else {
                            uiContent = getText("PageContent");
                            parseContent = ParseHtml
                                    .parseHtmlContent(filePaths.get(count));
                            count++;
                            diffResults(parseContent, uiContent, count);
                        }
                    } catch (Exception e) {
                        uiContent = getText("PageContent");
                        parseContent = ParseHtml
                                .parseHtmlContent(filePaths.get(count));
                        count++;
                        diffResults(parseContent, uiContent, count);
                    }
                }

            }
            break;

        }

        return null;

    }

    /**
     * @author Rashmi Gaidhane
     * @date 11 Oct 2017
     * @description Get the file names from directory folder
     * @return The path of files
     */
    public List<String> htmlFiles() {
        List<String> filePaths = new ArrayList<String>();
        try {
            File[] files = new File(htmlfilepath).listFiles();
            for (File file : files) {
                if (file.getName().contains(".xhtml")
                        && !(file.getName().contains("sw"))) {
                    filePaths.add(file.getAbsolutePath());
                }
            }
        } catch (Exception e) {

        }
        return filePaths;
    }

    /**
     * @author Rashmi.Gaidhane
     * @date 11 Oct,2017
     * @description Verify that Revel content on differentiator tool
     * 
     */

    public void diffResults(String parsedText, String UIText, int count) {
        // returnDriver().findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL,
        // "t"));
        ((JavascriptExecutor) returnDriver()).executeScript("window.open();");
        ArrayList<String> tabs = new ArrayList<String>(
                returnDriver().getWindowHandles());
        returnDriver().switchTo().window(tabs.get(1));
        returnDriver().get("https://www.diffchecker.com/diff");
        FindElement find = new FindElement();
        Actions actions = new Actions(returnDriver());
        actions.moveToElement(find.getElement("OriginalTextBox"));
        actions.click();
        actions.sendKeys(parsedText);
        actions.build().perform();
        /*
         * ((JavascriptExecutor)
         * returnDriver()).executeScript("arguments[0].value = arguments[1];",
         * find.getElement("OriginalTextBox"), parsedText);
         */
        actions.moveToElement(find.getElement("ChangedTextBox"));
        actions.click();
        actions.sendKeys(UIText);
        actions.build().perform();
        clickOnElement("SubmitButtonDifference", "");

        FileUtil.writeFile("D:\\abc\\Differentiator_ch2_" + count + ".html",
                returnDriver().getPageSource());
        returnDriver().close();
        returnDriver().switchTo().window(tabs.get(0));

    }

    /**
     * @author mukul.sehra
     * @date 17 Oct,2017
     * @description Verify element Text
     * 
     */
    public GLPLearner_LearningObjectivePage verifyText(String locator,
            String text, String stepDescription) {
        verifyText(locator, text, stepDescription);
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
    }

    public void compareModuleNoText(String text1, String text2) {
        String subText = text1.substring(7, 9);
        String subText2 = text2.substring(0, 2);
        if (subText.contains(subText2)) {
            this.result = Constants.PASS + ": Actual Text : " + subText
                    + " is same as Expected Text : " + subText2;
            logResultInReport(this.result,
                    "Verify that student is redirected to core instruction of First LO of respective module.",
                    this.reportTestObj);
        } else {
            this.result = Constants.FAIL + ": Actual Text : " + subText
                    + " is not same as Expected Text :" + subText2;
            logResultInReport(this.result,
                    "Verify that student is not redirected to core instruction of First LO of respective module.",
                    this.reportTestObj);

        }

    }

    /**
     * @author mukul.sehra
     * @date 17 Oct,2017
     * @description Enter data in Free Response field
     * 
     */
    public GLPLearner_LearningObjectivePage
           enterDataInFreeResponseField(String locator, String text) {
        this.result = performAction.execute(ACTION_TYPE_VIA_ACTIONS, locator,
                text);
        logResultInReport(this.result, "Enter data in Free Response Field",
                this.reportTestObj);
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOGS);
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
     * @author mukul.sehra
     * @date 2018-05-21
     * @return GLPLearner_LearningObjectivePage
     * @description Verifies that the video tutorial is displayed, checks for
     *              the video poster and its link validity
     * @precondition - learner should be on coreInstructions page for an LO
     */
    public GLPLearner_LearningObjectivePage verifyVideoPoster() {
        verifyElementPresent("CoreInstructionVideoPlayBackProgressBar",
                "Verify that Video player is present for the Video Tutorial");
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String videoPosterUrl = getElementAttribute(
                "CoreInstructionVideoPlayBackProgressBar", "poster",
                "Verify the video poster availability.");
        Response videoResponse = objRestUtil.apiCallWithoutJson("GET",
                videoPosterUrl);
        if (videoResponse.getStatusCode() == 200)
            logResultInReport(
                    Constants.PASS
                            + ": Poster url is valid and not a broken link : "
                            + videoPosterUrl + ", Status code : "
                            + videoResponse.getStatusCode(),
                    "Verify that Video poster url is not broken",
                    reportTestObj);
        else
            logResultInReport(
                    Constants.FAIL
                            + ": Poster url is invalid and is a broken link : "
                            + videoPosterUrl + ", Status code : "
                            + videoResponse.getStatusCode(),
                    "Verify that Video poster url is not broken",
                    reportTestObj);
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOG);
    }

    public void verifyContentNotNull(String contentType) {
        try {
            String contentText = this.performAction.execute(ACTION_GET_TEXT,
                    "CoreInstructionContent");
            if (contentText.equals("")) {
                logResultInReport(
                        Constants.FAIL + " : "
                                + "No text displayed for the Core Instructions for content type : "
                                + contentType,
                        "Verify core instruction content text is not null",
                        reportTestObj);
            } else {
                logResultInReport(
                        Constants.PASS + " : "
                                + " Text displayed for the Core Instructions for content type : "
                                + contentType,
                        "Verify core instruction content text is not null",
                        reportTestObj);
            }
        } catch (Exception e) {
            logResultInReport(
                    Constants.FAIL + " : "
                            + "Exception displayed while verifying content for core instruction  for content type : "
                            + contentType + e.getMessage(),
                    "Verify core instruction content text is not null",
                    reportTestObj);
        }
    }

    /**
     * @author mukul.sehra
     * @date 2018-05-21
     * @return GLPLearner_LearningObjectivePage
     * @description Traverse each EO in an LO and verify that the content is
     *              displayed
     * @precondition - learner should be on coreInstructions page for an LO
     */
    public GLPLearner_LearningObjectivePage traverseAndVerifyContentEo() {
        APP_LOG.info("Inside traverseAndVerifyContentEo in Learner's end...");
        try {
            FindElement find = new FindElement();
            List<WebElement> eoList = find
                    .getElements("CoreInstructionButtonEO");
            List<WebElement> eoListItem = new ArrayList<WebElement>();
            String eoLabel = "";
            String eoItemLabel = "";
            for (int listCounter = 0; listCounter < eoList
                    .size(); listCounter++) {
                eoList = find.getElements("CoreInstructionButtonEO");
                eoLabel = eoList.get(listCounter).getText();
                if (listCounter == 0) {
                    if (!(eoList.get(listCounter).getAttribute("class")
                            .contains("active")))
                        logResultInReport(
                                Constants.FAIL + ": First EO link '" + eoLabel
                                        + "' is not active",
                                "Verify that First EO link '" + eoLabel
                                        + "'  is by default active",
                                reportTestObj);
                } else {
                    eoList.get(listCounter).click();
                    if (eoList.get(listCounter).getAttribute("class")
                            .contains("active"))
                        logResultInReport(
                                Constants.PASS + ": EO link '" + eoLabel
                                        + "'  is clicked and is active",
                                "Click the EO link '" + eoLabel
                                        + "' and verify that its active",
                                reportTestObj);
                    else
                        logResultInReport(
                                Constants.FAIL + ": EO link '" + eoLabel
                                        + "'  is clicked but is not active",
                                "Click the EO link '" + eoLabel
                                        + "' and verify that its active",
                                reportTestObj);
                }
                eoListItem = find.getElements("CoreInstructionEoListItem");
                List<WebElement> eoListItemType = find
                        .getElements("CoreInstructionEoType");
                for (int listItemCounter = 0; listItemCounter < eoListItem
                        .size(); listItemCounter++) {
                    eoItemLabel = eoListItem.get(listItemCounter).getText();
                    if (listItemCounter == 0) {
                        if (!(eoListItem.get(listItemCounter)
                                .getAttribute("class").contains("active")))
                            logResultInReport(
                                    Constants.FAIL + ": First EO item link '"
                                            + eoItemLabel + "' is not active",
                                    "Verify that First EO item link '"
                                            + eoItemLabel
                                            + "'  is by default active",
                                    reportTestObj);
                    }

                    else {
                        eoListItem.get(listItemCounter).click();
                        if (eoListItem.get(listItemCounter)
                                .getAttribute("class").contains("active"))
                            logResultInReport(
                                    Constants.PASS + ": EO item link '"
                                            + eoItemLabel
                                            + "'  is clicked and is active",
                                    "Click the EO item link '" + eoItemLabel
                                            + "' and verify that its active",
                                    reportTestObj);
                        else
                            logResultInReport(
                                    Constants.FAIL + ": EO item link '"
                                            + eoItemLabel
                                            + "'  is clicked but is not active",
                                    "Click the EO item link '" + eoItemLabel
                                            + "' and verify that its active",
                                    reportTestObj);
                    }

                    // Verify Video tutorial availability
                    if (eoListItemType.get(listItemCounter).getAttribute("type")
                            .equals("video")) {
                        verifyVideoPoster();
                    }

                    // Verify content availability
                    else if (eoListItemType.get(listItemCounter)
                            .getAttribute("type").equals("content"))
                        verifyContentNotNull("content");

                    // Verify narrative availability
                    else if (eoListItemType.get(listItemCounter)
                            .getAttribute("type").equals("edit"))
                        verifyContentNotNull("edit");

                    // Verify Practice Test availability and attempt it
                    else if (eoListItemType.get(listItemCounter)
                            .getAttribute("type").equals("quizz")) {
                        GLPLearner_PracticeTestPage objPracticeTest = new GLPLearner_PracticeTestPage(
                                reportTestObj, APP_LOG);
                        objPracticeTest.clickOnElement(
                                "PracticeTestWelcomeScreenStartButton",
                                "Click the Start practice test button");
                        objPracticeTest.attemptPracticeTest(0, 0,
                                "SubmitButton");
                        FindElement checkLoadState = new FindElement();
                        checkLoadState.checkPageIsReady();
                        this.verifyElementPresent(
                                "CoreInstructionPracticeAgainButton",
                                "Verify that, user has completed Practice test");
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception in verifying EOs content because : "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while verifying EOs content : "
                            + e.getMessage(),
                    "Verify that EOs content is not blank", reportTestObj);
        }
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOG);
    }

    /**
     * @author mukul.sehra
     * @date 2018-06-18
     * @return GLPLearner_LearningObjectivePage
     * @description Click the element by text in the list of elements
     * @precondition - learner should be on coreInstructions page for an LO
     */
    public GLPLearner_LearningObjectivePage clickElementWithText(String locator,
            String text) {
        HashMap<String, String> locatorAndText = new HashMap<String, String>();
        locatorAndText.put("ElementId", locator);
        locatorAndText.put("InputValue", text);
        this.result = performAction.execute(ACTION_CLICK_TEXT_IN_LIST,
                locatorAndText);
        if (this.result.contains(Constants.PASS)) {
            logResultInReport(this.result,
                    "Click the element with text - " + text, reportTestObj);
        } else {
            logResultInReport(this.result,
                    "Click the element with text - " + text, reportTestObj);
        }
        return new GLPLearner_LearningObjectivePage(reportTestObj, APP_LOG);
    }
}
