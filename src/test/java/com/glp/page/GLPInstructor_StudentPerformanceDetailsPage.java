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
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPInstructor_StudentPerformanceDetailsPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();

    public GLPInstructor_StudentPerformanceDetailsPage(ExtentTest reportTestObj,
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
        this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
        this.result = this.performAction.execute(ACTION_CLICK, element);
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

    public GLPInstructor_StudentPerformanceDetailsPage
           verifyElementAttributeValue(String element, String attributeName,
                   String verifyText, String stepDesc) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
        dataMap.put(ELEMENT_LOCATOR, element);
        dataMap.put(COMPONENT_NAME, attributeName);
        dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
        this.result = this.performAction.execute(dataMap);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
        return new GLPInstructor_StudentPerformanceDetailsPage(reportTestObj,
                APP_LOG);
    }

    /**
     * @author Pallavi.Tyagi
     * @date 3 Oct,2017
     * @description Verify page title of performance dashboard
     * @return The object of ProductApplication_CourseHomePage
     */

    public GLPInstructor_StudentPerformanceDetailsPage
           verifyPerformanceDashboardTitle(String exptectedTitle,
                   String description) {
        this.result = this.performAction.execute(ACTION_VERIFY_TITLE,
                exptectedTitle);
        logResultInReport(this.result, description, this.reportTestObj);
        return new GLPInstructor_StudentPerformanceDetailsPage(reportTestObj,
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
     * @author anuj.tiwari1
     * @date 1 June,2018
     * @description Verify that all the modules are displayed on the student
     *              details page
     * 
     */
    public void verifyListOfModuleIsDisplayed(String locator) {
        int listOfModules = 0;
        try {
            FindElement ele = new FindElement();
            listOfModules = ele.getElements("StudentDetailsListOfModules")
                    .size();
            if (listOfModules == Integer.parseInt(ResourceConfigurations
                    .getProperty("modulesListOfStudentDetailsPage"))) {
                logResultInReport(
                        Constants.PASS + ": All modules are displayed.",
                        "Verify that List of all the module is displayed on the details page.",
                        reportTestObj);
            }

        } catch (NumberFormatException e) {
            logResultInReport(
                    Constants.FAIL + ": Failed to get List of Modules "
                            + e.getMessage(),
                    "Failed to get List of modules", reportTestObj);
        }
    }

    /**
     * @author anuj.tiwari1
     * @date 1 June,2018
     * @description Verify TOT for Modules, LOs and Pre-Assessment
     * @input1 Enter the activity Type. Possible Values: preAssessment , module
     *         or LO
     * @input2 Enter the Expected value of TOT. Possible Values: "hypehn" , "-",
     *         "Not Started" or TOT(time).
     * @input3 Enter the Module Number in case of Module or LO number in case of
     *         LOs.
     */

    public void verifyTOT(String... args) {
        APP_LOG.info("Starting TOT verification..");
        try {
            switch (args[0].toLowerCase()) {
            case "preassessment":
                APP_LOG.info("Verifying preAssessment..");
                if (args[1].equalsIgnoreCase("hyphen")
                        || args[1].equalsIgnoreCase("-")) {
                    verifyElementPresent(
                            "StudentDetailsPreAssessementTOTHyphen",
                            "Hyphen is displayed for the Pre Assessment.");
                } else {
                    verifyText("StudentDetailPreAssessmentTOTText", args[1],
                            "Verify that value of TOT Pre Assessment is displayed correctly.");
                }

                break;

            case "module":
                APP_LOG.info("Verifying Module..");
                if (args[1].equalsIgnoreCase("hyphen")
                        || args[1].equalsIgnoreCase("-")) {
                    clickOnElement(
                            "StudentDeatilsModuleNameLink:dynamicReplace="
                                    + args[2],
                            "Expanding the Module" + args[2]);
                    verifyElementPresent(
                            "StudentDetailModuleTOTHyphen:dynamicReplace="
                                    + args[2],
                            "Hyphen is displayed for the Module " + args[2]);
                } else {
                    clickOnElement(
                            "StudentDeatilsModuleNameLink:dynamicReplace="
                                    + args[2],
                            "Expanding the Module" + args[2]);

                    verifyText(
                            "StudentDetailModuleTotalTOTText:dynamicReplace="
                                    + args[2],
                            args[1], "Verify that value of TOT for Module "
                                    + args[2] + " is displayed correctly.");
                }

                break;

            case "lo":
                APP_LOG.info("Verifying LO..");
                if (args[1].equalsIgnoreCase("hyphen")
                        || args[1].equalsIgnoreCase("-")) {
                    String LOModule = args[2].trim().substring(0, 2);
                    clickOnElement(
                            "StudentDeatilsModuleNameLink:dynamicReplace="
                                    + LOModule,
                            "Expanding the Module" + args[2]);
                    verifyElementPresent(
                            "StudentDetailLOsTOTHyphen:dynamicReplace="
                                    + args[2],
                            "Hyphen is displayed for the Module " + args[2]);
                } else {
                    String LOModule = args[2].trim().substring(0, 2);
                    clickOnElement(
                            "StudentDeatilsModuleNameLink:dynamicReplace="
                                    + LOModule,
                            "Expanding the Module" + args[2]);
                    verifyText(
                            "StudentDetailLOsTOTText:dynamicReplace=" + args[2],
                            args[1], "Verify that value of TOT LO " + args[2]
                                    + " is displayed correctly.");
                }
                break;

            default:
                APP_LOG.info("No Match Found");
                logResultInReport(Constants.FAIL + " :No matched found for TOT",
                        "Verify the TOT value", reportTestObj);
                break;
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while verifying TOT: " + e.getMessage());
            logResultInReport(
                    Constants.FAIL + " :Exception while verifying TOT - "
                            + e.getMessage(),
                    "Verify TOT value", reportTestObj);

        }

    }

    /**
     * @author pankaj.sarjal
     * @param element
     * @param text
     * @param stepDesc
     * @return
     */
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

    /**
     * @author anurag.garg1
     * @date 2 April, 2018
     * @description Expand modules using accordian
     */

    public void expandAllModulesViaAccordians(String element, String message) {
        APP_LOG.debug("Clicking on accordians to expand");
        try {
            int count = 0;
            FindElement findElement = new FindElement();
            this.result = this.performAction
                    .execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
            if (this.result.contains("PASS")) {
                APP_LOG.debug("Accordians Visible");
                List<WebElement> accordian = findElement.getElements(element);
                for (int indexOfAccordian = 0; indexOfAccordian < accordian
                        .size(); indexOfAccordian++) {
                    accordian.get(indexOfAccordian).click();
                    count++;
                }
                if (count == accordian.size()) {
                    logResultInReport(Constants.PASS
                            + " : Total Modules expanded : " + count, message,
                            this.reportTestObj);
                } else {
                    logResultInReport(Constants.FAIL, "Modules not expanded",
                            this.reportTestObj);
                }
            } else {
                logResultInReport(Constants.FAIL, "Accordians not visible",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Error in exanding modules using accordians "
                    + e.getMessage());
        }
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

}