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

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

public class GLPConsole_CreateAccountPage extends BaseClass
        implements KeywordConstant {
    protected Logger APP_LOGS = null;
    protected Logger APP_LOG;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);

    public GLPConsole_CreateAccountPage(ExtentTest reportTestObj,
            Logger APP_LOG) {
        this.APP_LOG = APP_LOG;
        this.reportTestObj = reportTestObj;
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
     * @author nitish.jaiswal
     * @date 26 Dec,2017
     * @description Create account from UI
     */
    public void createAccountFromUI() {

        String userName = "GLP_" + getTestCaseId()
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        enterInputData("CreateAccountEmailAddressTextBox",
                "abhishek.sharda@pearson.com", "Enter Email Address.");
        enterInputData("CreateAccountConfirmEmailAddressTextBox",
                "abhishek.sharda@pearson.com", "Enter confirm Email Address.");
        enterInputData("CreateAccountUserNameTextBox", userName,
                "Enter user Name.");
        enterInputData("CreateAccountPasswordTextBox",
                ResourceConfigurations.getProperty("createNewUserPassword"),
                "Enter password.");
        enterInputData("CreateAccountFirstNameTextBox", userName,
                "Enter First Name.");
        enterInputData("CreateAccountLastNameTextBox", userName,
                "Enter Last Name.");
        performAction.execute(ACTION_SELECT_BY_VISIBLE_TEXT,
                "createAccountRoleDropDown", "Instructor");
        enterInputData("CreateAccountSchoolTextBox",
                "Brown Mackie - Boise - Boise, ID, USA", "Select school.");
        WebDriver webDriver = returnDriver();
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].click();",
                webDriver.findElement(By.cssSelector("#privacyTermsInput")));
        js.executeScript("arguments[0].click();",
                webDriver.findElement(By.cssSelector("#minAgeInput")));

        clickOnElement("CreateAccountButton",
                "Click on Create account button.");
        clickOnElement("CreateAccountContinueButton",
                "Click on continue button after Create account.");
        performAction.execute(ACTION_CLICK, "ConsoleSkipButton");
        performAction.execute(ACTION_CLICK, "ConsoleSkipButton");
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        objRestUtil.validateWebCredentials(userName,
                ResourceConfigurations.getProperty("createNewUserPassword"));
        objRestUtil.sendVerificationEmail(userName, ResourceConfigurations
                .getProperty("consoleUserTypeInstructor"));
        objRestUtil.glpConsoleValidateEmail(
                ResourceConfigurations.getProperty("consoleUserTypeInstructor"),
                userName);
        // objRestUtil.groupManagerUpdateMembership(userName);

    }
}
