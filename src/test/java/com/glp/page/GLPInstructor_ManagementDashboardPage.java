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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class GLPInstructor_ManagementDashboardPage extends BaseClass implements KeywordConstant {
	protected Logger APP_LOGS;
	protected ExtentTest reportTestObj;
	protected String result = "";
	protected String stepDescription = "";
	private PerformAction performAction = new PerformAction();

	public GLPInstructor_ManagementDashboardPage(ExtentTest reportTestObj, Logger APP_LOG) {
		this.APP_LOGS = APP_LOG;
		this.reportTestObj = reportTestObj;
		objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
	}

	/**
	 * @author nitish.jaiswal
	 * @date 12 July,2017
	 * @description Verify text is present as expected
	 * @return The object of ProductApplication_courseHomePage
	 */
	public String verifyTextContains(String Locator, String Text, String Message) {
		APP_LOGS.debug("Element is present: " + Locator);
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, Locator);
		result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, Locator, Text);
		return result;
	}

	/**
	 * @author mohit.gupta5
	 * @date 14 Nov,2017
	 * @description Click on specific element
	 * @return The object of ProductApplication_WelcomeInstructorPage
	 */
	public GLPInstructor_ManagementDashboardPage switchToManagementTab() {
		APP_LOGS.debug("Switch to Management Tab");
		clickOnElement("InstructorDashBoardManagementTab", "Switch to Management Tab");
		return this;
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
		this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element, text);
		logResultInReport(this.result, stepDesc, this.reportTestObj);
		return this.result;
	}

	/**
	 * @author mohit.gupta5
	 * @date 14 Nov,2017
	 * @description Click on Tab
	 */
	public void clickOnElement(String element, String stepDesc) {
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		this.result = this.performAction.execute(ACTION_CLICK, element);
		logResultInReport(this.result, stepDesc, this.reportTestObj);
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-31
	 * @returnType void
	 * @description click on save button on Instructor Management Dashboard Page
	 * @precondition
	 */

	public void clickOnSaveButton() {
		WebDriver webDriver = returnDriver();
		if (capBrowserName.equalsIgnoreCase("Safari")) {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();",
					webDriver.findElement(By.cssSelector("#ManagementDashboard_save")));

		} else {
			clickOnElement("InstructorManagementSaveButton",
					"Click on save button on Instructor Managament Dashboard Page");
		}

	}

	/**
	 * @author mohit.gupta5
	 * @date 14 Nov,2017
	 * @description :Verify element not present.
	 */
	public GLPInstructor_ManagementDashboardPage verifyElementNotPresent(String locator, String message) {
		APP_LOGS.debug(locator + "Element is not present");
		this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, locator);
		logResultInReport(this.result, message, this.reportTestObj);
		return new GLPInstructor_ManagementDashboardPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author mayank.mittal
	 * @date 14, Nov ,2017
	 * @description Verify element is present
	 * @return String PASS/FAIL
	 */
	public String verifyElementPresent(String locator, String message) {
		APP_LOGS.debug("Element is present: " + locator);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT, locator);
		logResultInReport(this.result, message, this.reportTestObj);
		if (this.result.contains(Constants.PASS)) {
			return Constants.PASS;
		} else {
			return Constants.FAIL;
		}
	}

	public void verifyMasteringScore(String sliderValue, String masteryValue) {
		if (sliderValue.equals(masteryValue)) {
			this.result = Constants.PASS + ": Mastery score :" + masteryValue
					+ " in management tab is same as set in settings page :" + sliderValue;
			logResultInReport(this.result, "Verify Mastery score in management tab with the score set in settings page",
					this.reportTestObj);
		} else {
			this.result = Constants.FAIL + ": Mastery score :" + masteryValue
					+ " in management tab is not same as set in settings page :" + sliderValue;
			logResultInReport(this.result, "Verify Mastery score in management tab with the score set in settings page",
					this.reportTestObj);
		}

	}

	/**
	 * @author abhishek.sharda
	 * @date 12 July,2017
	 * @description Get text of the element
	 * @return The object of ProductApplication_courseHomePage
	 */
	public String getText(String locator) {
		APP_LOGS.debug("Element is present: " + locator);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
		return valueText;
	}

	/**
	 * @author nisha.pathria
	 * @date 22 Nov,2017
	 * @description Verify Management section UI
	 * @return The object of ProductApplication_WelcomeInstructorPage
	 */
	public GLPInstructor_ManagementDashboardPage verifyPreAssessmentMasteryLevelSection() {
		APP_LOG.debug("Verify Pre-assessment mastery level section.");

		verifyText("PreAssessmentMasteryLevelHeading",
				ResourceConfigurations.getProperty("preAssessmentMasteryHeadingOnManagementPage"),
				"Verify correct heading appears for Pre-assessment mastery level section.");

		verifyText("InstructorManagementMasteryPercentage",
				ResourceConfigurations.getProperty("changeMasteryPercentageValue") + "%", "Verify percentage set to "
						+ ResourceConfigurations.getProperty("changeMasteryPercentageValue") + " is appearing.");

		verifyElementPresent("InstructorManagementEditButton", "Verify edit button present.");
		verifyText("InstructorManagementPreAssessmentPreview",
				ResourceConfigurations.getProperty("preAssessmentPreviewHeading"),
				"Verify Pre-assessment text in Pre Assessment preview section.");

		verifyText("InstructorManagementResetcoursedataText",
				ResourceConfigurations.getProperty("previewResetCourseDataText"),
				"Verify Reset course data text appearing in preview section.");

		verifyElementPresent("InstructorManagementPreviewButton", "Verify Preview button present.");

		verifyText("InstructorManagementModule11Name", ResourceConfigurations.getProperty("moduleName11"),
				"Verify Module name.");
		verifyElementPresent("InstructorManagementModuleCollapsedArrow", "Verify Module Collpased icon for Module 11.");
		clickOnElement("InstructorManagementModuleCollapsedArrow", "Click on Module Collpased icon for Module 11.");

		// verifyText("InstructorManagementLOName",
		// ResourceConfigurations.getProperty("lO11.1Name"),
		// "Verify LO name.");

		return new GLPInstructor_ManagementDashboardPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author mukul.sehra
	 * @date 10 April ,17
	 * @description Verify "Search" is displayed as the placeholder.
	 */
	public void verifyElementAttributeValue(String element, String attributeName, String verifyText, String stepDesc) {
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
	 * @date 23 Nov,2017
	 * @description Verify element is not enable
	 * @return String
	 */
	public void verifyElementIsNotEnabled(String locator) {
		APP_LOGS.debug("Element is not enable: " + locator);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		this.result = this.performAction.execute(ACTION_VERIFY_ISENABLED, locator);
		if (this.result.contains(Constants.PASS)) {
			this.result = Constants.FAIL + ": Element :" + locator + "save button is enable.";

			logResultInReport(this.result, "Verify save button is enable", this.reportTestObj);
		} else {
			this.result = Constants.PASS + ": Element :" + locator + "save button is disable.";

			logResultInReport(this.result, "Verify save button is disable.", this.reportTestObj);
		}
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-06-19
	 * @returnType void
	 * @param locator
	 * @param value
	 * @param message
	 * @description
	 * @precondition
	 */
	public void enterInputData(String locator, String value, String message) {
		APP_LOG.debug("Enter the input value- " + value);
		if (capBrowserName.equalsIgnoreCase("Safari")) {
			APP_LOG.debug("Entering value on Safari");
			returnDriver().findElement(By.cssSelector(".slider-value")).click();
			this.result = this.performAction.execute(ACTION_TYPE_AFTER_CLEAR, locator, value);
		} else {
			returnDriver().findElement(By.cssSelector(".slider-value")).sendKeys(Keys.TAB);
			APP_LOG.debug("Tab key pressed");
			returnDriver().findElement(By.cssSelector(".slider-value")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			APP_LOG.debug("Ctrl+a key chord sequence pressed");
			returnDriver().findElement(By.cssSelector(".slider-value")).sendKeys(Keys.DELETE);
			APP_LOG.debug("Delete key pressed");
			this.result = this.performAction.execute(ACTION_TYPE, locator, value);
		}

		logResultInReport(this.result, message, this.reportTestObj);

	}

	/**
	 * @author pallavi.tyagi
	 * @date 26 Nov,2017
	 * @description move slider
	 */
	public void moveSlider() {

		try {
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_LEFT);
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_LEFT);
			logResultInReport(Constants.PASS + ": Mastery settings slider has been moved",
					"Move the slider to change the Mastery Threshold value", this.reportTestObj);
		} catch (Exception e) {
			logResultInReport(Constants.FAIL + ": Slider failed to move because : " + e.getMessage(),
					"Move the slider to change the Mastery Threshold value", this.reportTestObj);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-06-13
	 * @returnType void
	 * @description move mastery setting slider to right by 5 percentage points
	 * @precondition
	 */
	public void moveSliderToRight() {

		try {
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_RIGHT);
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_RIGHT);
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_RIGHT);
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_RIGHT);
			returnDriver().findElement(By.cssSelector("#myRange")).sendKeys(Keys.ARROW_RIGHT);
			logResultInReport(Constants.PASS + ": Mastery settings slider has been moved",
					"Move the slider to change the Mastery Threshold value", this.reportTestObj);
		} catch (Exception e) {
			logResultInReport(Constants.FAIL + ": Slider failed to move because : " + e.getMessage(),
					"Move the slider to change the Mastery Threshold value", this.reportTestObj);
		}

	}

	/**
	 * @author pallavi.tyagi
	 * @date 27 Nov,2017
	 * @description verify slider movement
	 */

	public void verifySliderMovement(String sliderValueBeforeEdit, String sliderValueafterEdit) {
		String masteryValueBeforeEdit = sliderValueBeforeEdit + "%";

		if (masteryValueBeforeEdit.equals(sliderValueafterEdit)) {
			this.result = Constants.FAIL + ": Mastery score value :" + masteryValueBeforeEdit
					+ " is same as before slider movement :" + sliderValueafterEdit;
			logResultInReport(this.result, "Verify that slider value is not changed by moving it", this.reportTestObj);
		} else {
			this.result = Constants.PASS + ": Mastery score value :" + masteryValueBeforeEdit
					+ " is not same as before slider movement :" + sliderValueafterEdit;
			logResultInReport(this.result, "Verify that slider value can be changed by moving it", this.reportTestObj);

		}

	}

	/**
	 * @author anuj.tiwari1
	 * @date 23 Nov,2017
	 * @description Click on the Edit button displayed on Management Dashboard Page
	 * @return The object of
	 */
	public GLPInstructor_ManagementDashboardPage clickOnEditButton() {
		APP_LOGS.debug("Click on the Edit button");
		clickOnElement("InstructorManagementEditButton", "Click on the Edit button");

		return this;
	}

	/**
	 * @author nisha.pathria
	 * @date 21 Nov,2017
	 * @description set mastery percentage
	 * @return The object of ProductApplication_ManagementDashboardPage
	 */
	public GLPInstructor_ManagementDashboardPage verifySliderInputBoxSyncronization() {

		APP_LOGS.debug("Set mastery percentage.");

		enterInputData("InstructorManagementMasteryPercentageTextBox",
				ResourceConfigurations.getProperty("changeMasteryPercentageValue"),
				"Set mastery setting percentage to 85%.");
		verifyElementAttributeValue("InstructorManagementTabSliderValue", "value",
				ResourceConfigurations.getProperty("changeMasteryPercentageValue"),
				"verify slider value changes to 85%");
		moveSliderToRight();
		verifyElementAttributeValue("InstructorManagementMasteryPercentageTextBox", "value",
				ResourceConfigurations.getProperty("changeMasteryPercentage90"), "verify slider value changes to 90%");

		return this;
	}

	/*
	 * @author pallavi.tyagi
	 * 
	 * @date 1 Dec,2017
	 * 
	 * @description Get the attribute value of an element
	 * 
	 * @return Returns the value of attribute 'Value'
	 */

	public String getElementAttribute(String element, String attributeName, String stepDesc) {
		this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element, attributeName);
		return this.result;
	}

	/**
	 * @author anuj.tiwari1
	 * @date 31 October,2017
	 * @description Verify element is present
	 * @return The object of ProductApplication_MasterySettingPage
	 */
	public void verifySliderAttributeMinAndMaxValue() {

		APP_LOGS.debug("Verify that the minimum and maximum value of the slider is 80 and 100 respectively.");

		verifyElementAttributeValue("InstructorManagementTabSliderValue", "min",
				ResourceConfigurations.getProperty("sliderMinimumValue"),
				"Verify that the minimum value of the slider is 80.");
		verifyElementAttributeValue("InstructorManagementTabSliderValue", "max",
				ResourceConfigurations.getProperty("sliderMaximumValue"),
				"Verify that the maximum value of the slider is 100");

	}

	/**
	 * @author anuj.tiwari1
	 * @date 05 December,2017
	 * @description Verify that the value is saved or cancelled after pressing the
	 *              respective button.
	 * 
	 */

	public void verifySavedValueAfterEdit(String newSliderValue, String sliderValueAfterSave, String functionality) {
		String newMasteryValue = newSliderValue + "%";

		if (functionality.equalsIgnoreCase("save")) {

			if (newMasteryValue.equals(sliderValueAfterSave)) {
				this.result = Constants.PASS + ": Mastery score value :" + newMasteryValue
						+ " is same as entered by the user :" + sliderValueAfterSave + ". Value is saved successfully.";
				logResultInReport(this.result, "Verify that slider value can be saved by clicking the save button.",
						this.reportTestObj);
			} else {
				this.result = Constants.FAIL + ": Mastery score value :" + newMasteryValue
						+ " is not same as entered by the user :" + sliderValueAfterSave + ". Value is not saved.";
				logResultInReport(this.result, "Verify that slider value can be saved by clicking the save button.",
						this.reportTestObj);

			}
		} else {

			if (functionality.equalsIgnoreCase("cancel")) {

				if (newMasteryValue.equals(sliderValueAfterSave)) {
					this.result = Constants.FAIL + ": Mastery score value :" + newMasteryValue
							+ " is same as entered by the user :" + sliderValueAfterSave
							+ ". Value is saved, Cancel fumctionality is not working.";
					logResultInReport(this.result,
							"Verify that when cancel button is clicked value should not be saved.", this.reportTestObj);
				} else {
					this.result = Constants.PASS + ": Mastery score value :" + newMasteryValue
							+ " is not same as entered by the user :" + sliderValueAfterSave
							+ ". Value is not saved, Cancel is working working fine";
					logResultInReport(this.result,
							"Verify that when cancel button is clicked value should not be saved.", this.reportTestObj);

				}
			}
		}
	}

	/**
	 * @author tarun.gupta1
	 * @date 12 July,2017
	 * @description Click on specific element
	 * @return The object of ProductApplication_ManagementDashboardPage
	 */
	public GLPInstructor_ManagementDashboardPage switchToPerformaceTab() {
		APP_LOG.debug("Switch to Performance Tab");
		clickOnElement("InstructorDashBoardPerformanceTab", "Switch to Performance Tab");
		return this;
	}

	/**
	 * @author nisha.pathria
	 * @date 10 May, 2017
	 * @description To verify button is disabled or enabled
	 */
	public void verifyButtonEnabledOrDisabled(String elementlocator, String value, String stepDesc) {
		result = performAction.execute(ACTION_VERIFY_ISENABLED, elementlocator, value);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @author anurag.garg1
	 * @date 2 April, 2018
	 * @description collapsing Lo's inside a Module
	 */

	public void expandAllLOs(String element, String message) {
		APP_LOG.debug("Clicking on LO's to expand");
		try {
			int count = 0;
			FindElement findElement = new FindElement();
			this.result = this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
			if (this.result.contains("PASS")) {
				List<WebElement> lo = findElement.getElements(element);
				for (int indexOfLO = 0; indexOfLO < lo.size(); indexOfLO++) {
					lo.get(indexOfLO).click();
					count++;
					this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, "InstructorManagementLO-EO");

				}
				if (count != 0) {
					logResultInReport(Constants.PASS + " : Total LO's expanded : " + count, message,
							this.reportTestObj);
				} else {
					logResultInReport(Constants.FAIL, "LO's not expanded", this.reportTestObj);
				}
			} else {
				logResultInReport(Constants.FAIL, "LO's not Visible", this.reportTestObj);
			}

		} catch (Exception e) {
			APP_LOG.error("Error in expanding LO's " + e.getMessage());
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2 April, 2018
	 * @description collapsing Lo's inside a Module
	 */

	public void collapseAllLOs(String element, String message) {
		APP_LOG.debug("Clicking on LO's to collapse");
		try {
			int count = 0;
			FindElement findElement = new FindElement();
			this.result = this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
			if (this.result.contains("PASS")) {
				List<WebElement> lo = findElement.getElements(element);
				for (int indexOfLO = 0; indexOfLO < lo.size(); indexOfLO++) {

					lo.get(indexOfLO).click();
					count++;
					this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_NOT_VISIBLE, "InstructorManagementLO-EO");

				}
				if (count != 0) {
					logResultInReport(Constants.PASS + " : Total LO's collapsed : " + count, message,
							this.reportTestObj);
				} else {
					logResultInReport(Constants.FAIL, "LO's not collapsed", this.reportTestObj);
				}
			} else {
				logResultInReport(Constants.FAIL, "LO's not Visible", this.reportTestObj);
			}

		} catch (Exception e) {
			APP_LOG.error("Error in collapsing LO's " + e.getMessage());
		}

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
			this.result = this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
			if (this.result.contains("PASS")) {
				APP_LOG.debug("Accordians Visible");
				List<WebElement> accordian = findElement.getElements(element);
				for (int indexOfAccordian = 0; indexOfAccordian < accordian.size(); indexOfAccordian++) {
					accordian.get(indexOfAccordian).click();
					count++;
				}
				if (count == accordian.size()) {
					logResultInReport(Constants.PASS + " : Total Modules expanded : " + count, message,
							this.reportTestObj);
				} else {
					logResultInReport(Constants.FAIL, "Modules not expanded", this.reportTestObj);
				}
			} else {
				logResultInReport(Constants.FAIL, "Accordians not visible", this.reportTestObj);
			}
		} catch (Exception e) {
			APP_LOG.error("Error in exanding modules using accordians " + e.getMessage());
		}
	}

	/**
	 * @author anurag.garg1
	 * @date 2 April, 2018
	 * @description collapsing modules using module name
	 */

	public void collapseAllModulesViaModuleName(String element, String message) {
		APP_LOG.debug("Clicking on accordians to Collapse");
		try {
			int count = 0;
			FindElement findElement = new FindElement();
			this.result = this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
			if (this.result.contains("PASS")) {
				APP_LOG.debug("Module name Visible");
				List<WebElement> moduleName = findElement.getElements(element);
				for (int indexOfModuleName = 0; indexOfModuleName < moduleName.size(); indexOfModuleName++) {
					moduleName.get(indexOfModuleName).click();
					count++;
				}
				if (count == moduleName.size()) {
					logResultInReport(Constants.PASS + " : Total Modules collapsed : " + count, message,
							this.reportTestObj);
				} else {
					logResultInReport(Constants.FAIL, "Modules not collapsed", this.reportTestObj);
				}
			} else {
				logResultInReport(Constants.FAIL, "Module name not visible", this.reportTestObj);
			}
		} catch (Exception e) {
			APP_LOG.error("Error in exanding modules using accordians " + e.getMessage());
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 6th April, 2018
	 * @description store all the EO's under a LO on anInstructor management
	 *              dashboard page
	 * @return String array
	 */

	public String[] getText(String element, String message) {

		FindElement findElement = new FindElement();
		List<WebElement> listofelements = findElement.getElements(element);
		String[] strarray = new String[listofelements.size()];
		for (int index = 0; index < listofelements.size(); index++) {
			strarray[index] = listofelements.get(index).getText();
		}
		return strarray;
	}

	/**
	 * @author mukul.sehra
	 * @date 2018-05-10
	 * @returnType GLPInstructor_CoreInstructionsPage
	 * @param loNumber
	 *            - Example - 11.1/11.2/12.1/12.2
	 * @return GLPInstructor_CoreInstructionsPage
	 * @description navigateToPreviewQuestionsInLO
	 * @precondition - user should be on management dashboard page
	 */
	public GLPInstructor_CoreInstructionsPage navigateToPreviewQuestionsInLo(String loNumber) {
		APP_LOG.info("Inside navigateToPreviewQuestionsInLO method .....");
		try {
			FindElement findElement = new FindElement();
			List<WebElement> listofModules = findElement.getElements("InstructorManagementModuleCaretIcon");
			APP_LOG.debug("Click the Module caret icon..");
			listofModules.get(Integer.parseInt(loNumber.split("\\.")[0]) - 11).click();
			APP_LOG.debug("Click the preview button corresponding to LO : " + loNumber);
			returnDriver().findElement(By.cssSelector(".pe-btn__tertiary--btn_small[id^='SECTION-" + loNumber + "']"))
					.click();
			APP_LOG.debug("Preview button corresponding to LO : " + loNumber + " clicked");
			GLPInstructor_CoreInstructionsPage objInstructorCoreInstructionsPage = new GLPInstructor_CoreInstructionsPage(
					reportTestObj, APP_LOG);
			APP_LOG.debug("Click the preview questions button...");
			objInstructorCoreInstructionsPage.clickOnPreviewQuestions("Verify user clicked on Preview Questions Link");
			logResultInReport(Constants.PASS + ": Successfully navigated to Preview Questions page of LO : " + loNumber,
					"Verify user has navigated to preview questions of LO : " + loNumber, reportTestObj);
			APP_LOG.debug("Verify the presence of the NextQuestion button on the Preview questions page...");
			objInstructorCoreInstructionsPage.verifyElementPresent("CoreInstructionPreviewQuestionsNextQuestion",
					"Verify Next question button link is present on Preview questions page");

		} catch (Exception e) {
			logResultInReport(
					Constants.FAIL + ": Exception while navigating to preview questions, Exception : " + e.getMessage(),
					"Verify user has navigated to preview questions of LO : " + loNumber, reportTestObj);
		}
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);

	}

	/**
	 * @author mukul.sehra
	 * @date 2018-05-21
	 * @returnType GLPInstructor_CoreInstructionsPage
	 * @param loNumber
	 *            - Example - 11.1/11.2/12.1/12.2
	 * @return GLPInstructor_CoreInstructionsPage
	 * @description navigateToCoreInstructionsInLo
	 * @precondition - user should be on management dashboard page
	 */
	public GLPInstructor_CoreInstructionsPage navigateToCoreInstructionsInLo(String loNumber) {
		APP_LOG.info("Inside navigateToCoreInstructionsInLo method .....");
		try {
			FindElement findElement = new FindElement();
			List<WebElement> listofModules = findElement.getElements("InstructorManagementModuleCaretIcon");
			APP_LOG.debug("Click the Module caret icon..");
			listofModules.get(Integer.parseInt(loNumber.split("\\.")[0]) - 11).click();
			APP_LOG.debug("Click the preview button corresponding to LO : " + loNumber);
			returnDriver()
					.findElement(
							By.cssSelector("[class='pe-btn__tertiary--btn_small'][id^='SECTION-" + loNumber + "']"))
					.click();
			GLPInstructor_CoreInstructionsPage objCoreInstruction = new GLPInstructor_CoreInstructionsPage(
					reportTestObj, APP_LOG);
			objCoreInstruction.verifyElementPresent("CoreInstructionButtonEO",
					"Verify user has successfully navigated to CoreInstructions page");
		} catch (Exception e) {
			logResultInReport(
					Constants.FAIL + ": Exception while navigating to preview questions, Exception : " + e.getMessage(),
					"Verify user has navigated to preview questions of LO : " + loNumber, reportTestObj);
		}
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author pankaj.sarjal
	 * @param element
	 * @param stepDesc
	 */
	public void clickByJS(String element, String stepDesc) {
		this.result = this.performAction.execute(ACTION_CLICK, element);
		logResultInReport(this.result, stepDesc, this.reportTestObj);
	}

}