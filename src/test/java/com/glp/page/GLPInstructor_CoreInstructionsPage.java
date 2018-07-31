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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.CommonUtil;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.response.Response;

/**
 * @author anurag.garg1
 *
 */
public class GLPInstructor_CoreInstructionsPage extends BaseClass implements KeywordConstant {
	protected Logger APP_LOGS;
	protected ExtentTest reportTestObj;
	protected String result = "";
	protected String stepDescription = "";
	private PerformAction performAction = new PerformAction();

	public GLPInstructor_CoreInstructionsPage(ExtentTest reportTestObj, Logger APP_LOG) {
		this.APP_LOGS = APP_LOG;
		this.reportTestObj = reportTestObj;
		objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-07-08
	 * @returnType String
	 * @param locator
	 * @param stepDesc
	 * @return text value
	 * @description get the text of locator passed
	 * @precondition
	 */

	public String getText(String locator, String stepDesc) {
		APP_LOGS.debug("Element is present: " + locator);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		String valueText = this.performAction.execute(ACTION_GET_TEXT, locator);
		logResultInReport(Constants.PASS + ":" + " " + " Text of Locator is : " + valueText, stepDesc, reportTestObj);
		return valueText;

	}

	public GLPInstructor_CoreInstructionsPage scrollToElement(String element) {
		APP_LOGS.debug("Scroll to the Element: " + element);
		CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
		objCommonUtil.scrollIntoView(element);
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOGS);
	}

	/**
	 * @author anurag.garg1
	 * @date 6th April, 2018
	 * @description store all the EO's under a LO on a core instruction page
	 * @return String array
	 */
	public String[] getText(String element) {

		FindElement findElement = new FindElement();
		List<WebElement> listofelements = findElement.getElements(element);
		String[] strarray = new String[listofelements.size()];
		for (int index = 0; index < listofelements.size(); index++) {
			strarray[index] = listofelements.get(index).getText();
		}
		return strarray;
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
	 * @author mohit.gupta5
	 * @date 14 Nov,2017
	 * @description Verify text message
	 * @return The object of ProductApplication_WelcomeInstructorPage
	 */

	public void verifyText(String element, String text, String stepDesc) {
		this.APP_LOGS.debug("Verify text message " + text);
		this.APP_LOGS.debug("Element " + element);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		this.result = this.performAction.execute(ACTION_VERIFY_TEXT, element, text);
		logResultInReport(this.result, stepDesc, this.reportTestObj);

	}

	/**
	 * @author anurag.garg1
	 * @date 6th April, 2018
	 * @description Compare EO's from Management dashboard page with EO's from core
	 *              instruction page
	 * @return
	 */

	public void verifyEoOnCoreInstructionPage(String[] eoArrayFromManagementTab,
			String[] eoArrayFromCoreInstructionPage, String stepDesc) {
		String[] eoArrayWithoutPreviewQuestions = new String[eoArrayFromCoreInstructionPage.length - 1];
		for (int i = 0; i < eoArrayFromCoreInstructionPage.length - 1; i++) {
			eoArrayWithoutPreviewQuestions[i] = eoArrayFromCoreInstructionPage[i];
		}
		boolean verify = Arrays.equals(eoArrayFromManagementTab, eoArrayWithoutPreviewQuestions);
		if (verify) {
			logResultInReport(
					Constants.PASS + ":"
							+ " EO's displayed on Core Instruction Page matched with EO's from Management Tab",
					stepDesc, this.reportTestObj);
		} else {
			logResultInReport(Constants.FAIL, stepDesc, this.reportTestObj);
		}

	}

	/**
	 * @author mayank.mittal
	 * @date 14, Nov ,2017
	 * @description Verify element is present
	 * @return String PASS/FAIL
	 */

	public void verifyElementPresent(String locator, String message) {
		APP_LOGS.debug("Element is present: " + locator);
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT, locator);
		logResultInReport(this.result, message, this.reportTestObj);
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

	public String getElementAttribute(String element, String attributeName, String stepDesc) {
		this.result = this.performAction.execute(ACTION_GET_ATTRIBUTE, element, attributeName);
		logResultInReport(Constants.PASS + ": " + "Attribute " + attributeName + " 's value is : " + this.result,
				stepDesc, this.reportTestObj);
		return this.result;
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
	 * @author anurag.garg1
	 * @date 2018-04-04
	 * @returnType void
	 * @param locator
	 * @param stepDesc
	 * @description store web element of same type in a list and click on the
	 *              elements in a list one by one
	 * @precondition elements should be enabled
	 */

	public void clickOnElements(String locator, String stepDesc) {
		APP_LOG.debug("Click on elememts in a list one by one");
		int count = 0;
		FindElement findElement = new FindElement();
		List<WebElement> listofelements = findElement.getElements(locator);
		for (int index = 0; index < listofelements.size(); index++) {
			listofelements.get(index).click();
			this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
			count++;
		}
		if (count == listofelements.size()) {
			logResultInReport(Constants.PASS + ":" + " Total " + locator + " Clicked : " + count, stepDesc,
					reportTestObj);
		} else {
			logResultInReport(Constants.FAIL + ":" + " Total " + locator + " Clicked : " + count, stepDesc,
					reportTestObj);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-04-17
	 * @returnType void
	 * @param locatorOfNextOnFooter
	 * @param locatorOfNextCoreInstruction
	 * @param stepDesc
	 * @description verify the verb for next core instruction on footer with the
	 *              type of next core instruction in TOC
	 * @precondition next core instruction on footer should be displayed and enabled
	 */
	public void verifyNextVerbOnFooter(String locatorOfNextOnFooter, String locatorOfNextCoreInstruction,
			String stepDesc) {

		APP_LOG.debug(
				"verify the verb for next core instruction on footer with the type of next core instruction in TOC");

		String nextVerb = this.performAction.execute(ACTION_GET_TEXT, locatorOfNextOnFooter);
		if (nextVerb.equals(ResourceConfigurations.getProperty("read"))) {
			nextVerb = ResourceConfigurations.getProperty("typeOfCiRead");
			verifyElementAttributeValue(locatorOfNextCoreInstruction, "type", nextVerb, stepDesc);
		} else if (nextVerb.equals(ResourceConfigurations.getProperty("watch"))) {
			nextVerb = ResourceConfigurations.getProperty("typeOfCiVideo");
			verifyElementAttributeValue(locatorOfNextCoreInstruction, "type", nextVerb, stepDesc);
		} else if (nextVerb.equals(ResourceConfigurations.getProperty("interact"))) {
			nextVerb = ResourceConfigurations.getProperty("typeOfCiInteractive");
			verifyElementAttributeValue(locatorOfNextCoreInstruction, "type", nextVerb, stepDesc);
		} else if (nextVerb.equals(ResourceConfigurations.getProperty("practiceQuiz"))) {
			nextVerb = ResourceConfigurations.getProperty("typeOfCiPracticeQuiz");
			verifyElementAttributeValue(locatorOfNextCoreInstruction, "type", nextVerb, stepDesc);
		}
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-04-17
	 * @returnType void
	 * @param locatorOfPreviousOnFooter
	 * @param locatorOfPreviousCoreInstruction
	 * @param stepDesc
	 * @description verify the verb for previous core instruction on footer with the
	 *              type of previous core instruction in TOC
	 * @precondition previous core instruction on footer should be displayed and
	 *               enabled
	 */
	public void verifyPreviousVerbOnFooter(String locatorOfPreviousOnFooter, String locatorOfPreviousCoreInstruction,
			String stepDesc) {
		APP_LOG.debug(
				"verify the verb for previous core instruction on footer with the type of previous core instruction in TOC");
		String previousVerb = this.performAction.execute(ACTION_GET_TEXT, locatorOfPreviousOnFooter);
		if (previousVerb.equals(ResourceConfigurations.getProperty("Read"))) {
			previousVerb = ResourceConfigurations.getProperty("typeOfCiRead");
			verifyElementAttributeValue(locatorOfPreviousCoreInstruction, "type", previousVerb, stepDesc);
		} else if (previousVerb.equals(ResourceConfigurations.getProperty("Watch"))) {
			previousVerb = ResourceConfigurations.getProperty("typeOfCiVideo");
			verifyElementAttributeValue(locatorOfPreviousCoreInstruction, "type", previousVerb, stepDesc);
		} else if (previousVerb.equals(ResourceConfigurations.getProperty("Interact"))) {
			previousVerb = ResourceConfigurations.getProperty("typeOfCiInteractive");
			verifyElementAttributeValue(locatorOfPreviousCoreInstruction, "type", previousVerb, stepDesc);
		} else if (previousVerb.equals(ResourceConfigurations.getProperty("PracticeQuiz"))) {
			previousVerb = ResourceConfigurations.getProperty("typeOfCiPracticeQuiz");
			verifyElementAttributeValue(locatorOfPreviousCoreInstruction, "type", previousVerb, stepDesc);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-04-18
	 * @returnType void
	 * @param locator
	 * @param stepDesc
	 * @return nothing
	 * @description Verify the image is displayed in content of core instruction
	 * @precondition
	 */
	public void verifyImage(String locator, String stepDesc) {
		APP_LOGS.debug("Verify the image is displayed in content of core instruction.");
		this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, locator);
		this.result = this.performAction.execute(ACTION_VERIFY_IMAGES, locator);
		logResultInReport(this.result, stepDesc, this.reportTestObj);

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-04-20
	 * @returnType void
	 * @param locator
	 * @param stepDesc
	 * @description
	 * @precondition
	 */
	public void verifyVideoPlayback(String stepDesc) {
		APP_LOG.debug("Func:videosVerification");
		try {

			clickOnElement("CoreInstructionVideoPlayBackButton", "Play the video");
			Thread.sleep(5000);
			clickOnElement("CoreInstructionVideoPlayBackButtonOnProgressBar", "Pause the video");
			Thread.sleep(5000);
			clickOnElement("CoreInstructionVideoPlayBackButtonOnProgressBar", "Resume the video from progress bar");
			Thread.sleep(5000);
			clickOnElement("CoreInstructionMuteButtonOnProgressBar", "Mute the Video");
			Thread.sleep(5000);
			clickOnElement("CoreInstructionMuteButtonOnProgressBar", "UnMute the Video");
			Thread.sleep(5000);
			clickOnElement("CoreInstructionVideoPlayBackButtonOnProgressBar", "Pause the video");
			Thread.sleep(5000);

			logResultInReport(Constants.PASS + ":" + "Video sucessfully rendered and playback in the page", stepDesc,
					this.reportTestObj);

		} catch (Exception e) {
			APP_LOG.debug("Func: VideoVerification = " + e);
			logResultInReport(Constants.FAIL + ": Error while rendered and playback video in the page", stepDesc,
					this.reportTestObj);
		}
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-05
	 * @returnType void
	 * @param locator
	 * @param stepDesc
	 * @description clicking on preview questions link on TOC
	 * @precondition
	 */
	public void clickOnPreviewQuestions(String stepDesc) {
		APP_LOG.debug("Click on Preview Question on Core Instructions page");
		FindElement findElement = new FindElement();
		List<WebElement> listofelements = findElement.getElements("CoreInstructionPreviewQuestionEO");
		if (listofelements.size() > 0) {
			this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, "CoreInstructionPreviewQuestionEO");
			listofelements.get(listofelements.size() - 1).click();
			this.performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
					"CoreInstructionPreviewQuestionsNextQuestion");
			logResultInReport(Constants.PASS + " " + " : " + "Preview Questions clicked", stepDesc, reportTestObj);
		} else {
			logResultInReport(Constants.FAIL + " " + " : " + "Preview Questions NOT found", stepDesc, reportTestObj);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-05
	 * @returnType GLPInstructor_CoreInstructionsPage
	 * @param nextQuestionLocator
	 * @param stepDesc
	 * @description navigating to last question on preview questions EO
	 * @precondition Instructor should be on First question on preview questions
	 */
	public GLPInstructor_CoreInstructionsPage navigateToLastQuestionOnPreviewQuestion() {
		APP_LOG.info("Starting to navigate to Last Question");
		int countOfQuestions = 1;
		try {
			String currentQuestionText = "";
			String currentQuestionId = "";
			String nextQuestionId = "";
			GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
			while (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsNextQuestion")
					.contains(Constants.PASS) && countOfQuestions < 60) {
				currentQuestionText = this.performAction.execute(ACTION_GET_TEXT,
						"CoreInstructorPreviewQuestionsQuestionText");
				currentQuestionId = objRestUtil.getQuestionIdOnUI();
				if (countOfQuestions == 1) {
					logResultInReport(
							Constants.PASS + ":" + " Successfully traversed to Question : " + countOfQuestions
									+ ", with questionId : " + currentQuestionId,
							"Verify that user has navigted to Question : " + countOfQuestions, this.reportTestObj);
				}
				objRestUtil.clearPerformanceEntries();
				if (currentQuestionText.equals("")) {
					logResultInReport(Constants.FAIL + ":" + " Blank page is displayed ",
							"Verify that the question text has been rendered on the screen", this.reportTestObj);
				}
				this.performAction.execute(ACTION_CLICK, "CoreInstructionPreviewQuestionsNextQuestion");
				TimeUnit.SECONDS.sleep(4);
				FindElement obj = new FindElement();
				obj.checkPageIsReady();
				nextQuestionId = objRestUtil.getQuestionIdOnUI();
				countOfQuestions++;

				APP_LOG.debug(
						"Previous Question Id : " + currentQuestionId + "\n Current Question Id : " + nextQuestionId);
				if (currentQuestionId.trim().equals(nextQuestionId.trim()))
					this.result = Constants.PASS;
				else
					this.result = Constants.FAIL;

				if (this.result.contains(Constants.FAIL)) {
					logResultInReport(
							Constants.PASS + ":" + " Successfully traversed to Question : " + countOfQuestions
									+ ", with questionId : " + nextQuestionId,
							"Verify that user has navigted to Question : " + countOfQuestions, this.reportTestObj);
				} else if (this.result.contains(Constants.PASS)) {
					logResultInReport(
							Constants.FAIL + ":" + " Couldn't traversed to question : " + countOfQuestions
									+ " because question has been repeated as previousQuestionId : " + currentQuestionId
									+ " is same as currentQuestionId : " + nextQuestionId,
							"Verify that user has navigted to Question : " + countOfQuestions, this.reportTestObj);
				}
			}
			verifyElementPresent("CoreInstructionPreviewQuestionEO", "Verify Preview Questions link is Present");

		} catch (Exception e) {
			logResultInReport(
					Constants.FAIL + ":"
							+ " Exception while traversing to the last question, Total Questions Traversed : "
							+ countOfQuestions + " " + e.getMessage(),
					"Verify that user is able to traverse to the last question", this.reportTestObj);
			APP_LOG.error("Error in navigating to the Last Question: " + e.getMessage());
		}
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-07
	 * @returnType void
	 * @param nextQuestionLocator
	 * @param questionHeadingText
	 * @param stepDesc
	 * @description navigating to First question on preview questions EO
	 * @precondition Instructor should be on Last question on preview questions
	 */
	public GLPInstructor_CoreInstructionsPage navigateToFirstQuestionOnPreviewQuestion() {
		APP_LOG.info("Starting to navigate to First Question");
		int countOfQuestions = 1;
		try {
			while (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsPreviousQuestion")
					.contains(Constants.PASS) && countOfQuestions < 40) {
				String questionTextBeforePrevious = this.performAction.execute(ACTION_GET_TEXT,
						"CoreInstructorPreviewQuestionsQuestionText");
				if (questionTextBeforePrevious.equals("")) {
					logResultInReport(Constants.FAIL + ":" + " Blank page displayed ",
							"Verify that the question text is rendered on the screen", this.reportTestObj);
				}
				this.performAction.execute(ACTION_CLICK, "CoreInstructionPreviewQuestionsPreviousQuestion");
				countOfQuestions++;
				this.result = this.performAction.execute(ACTION_VERIFY_TEXT,
						"CoreInstructorPreviewQuestionsQuestionText", questionTextBeforePrevious);
				if (this.result.contains(Constants.FAIL)) {
					logResultInReport(
							Constants.PASS + ":" + " Successfully traversed to Question : " + countOfQuestions,
							"Verify that user has navigted to Question : " + countOfQuestions, this.reportTestObj);
				} else if (countOfQuestions == 40 || this.result.contains(Constants.PASS)) {
					logResultInReport(Constants.FAIL + ":" + " Couldn't traversed to question : " + countOfQuestions,
							"Verify that user has navigted to Question : " + countOfQuestions, this.reportTestObj);
				}
			}

		} catch (Exception e) {
			logResultInReport(
					Constants.FAIL + ":" + " Error while traversing to the first question, Total Questions Traversed : "
							+ countOfQuestions,
					"Verify that user is able to traverse to the first question", this.reportTestObj);
			APP_LOG.error("Error in navigating to the First Question: " + e.getMessage());
		}
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-10
	 * @returnType void
	 * @param questionType
	 *            : Values
	 *            :NumberLine/NumberLineWithCursor/MCQSA/MCQMA/Table/FIB_FreeResponse/FIB_Dropdown/FIB_Undefined
	 * @param stepDesc
	 * @description
	 * @precondition
	 */

	public void navigateToQuestionType(String questionType, String stepDesc) {
		APP_LOG.info("Starting to navigate to Question type : " + questionType);
		int count = 0;
		try {
			while (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsNextQuestion")
					.contains(Constants.PASS) && count < 60) {
				if (questionType.equalsIgnoreCase("NumberLine")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionNumberLine");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("NumberLineWithCursor")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionNumberLineWithCursor");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("MCQSA")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionMCQSA");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("MCQMA")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionMCQMA");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("TABLE")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionTable");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("FIB_FreeResponse")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionFIBFreeResponse");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("XYGraph")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionXYGraph");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("CallAndResponse")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionCallResponse");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("FIB_Dropdown")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionsPreviewQuestionFIBDropdown");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("Hybrid")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionHybrid");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else if (questionType.equalsIgnoreCase("MultiPart")) {
					this.result = this.performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT,
							"CoreInstructionPreviewQuestionMultiPart");
					if (this.result.contains("PASS")) {
						logResultInReport(
								Constants.PASS + ":" + " Successfully found question of type : " + questionType,
								stepDesc, reportTestObj);
						break;
					}
				} else {
					logResultInReport(Constants.FAIL + ":" + questionType + " is not from the Type Specified", stepDesc,
							reportTestObj);
				}
				this.performAction.execute(ACTION_CLICK, "CoreInstructionPreviewQuestionsNextQuestion");
				count++;
			}
			if (count == 60) {
				logResultInReport(Constants.FAIL + ":" + " Unable to found question of type : " + questionType,
						stepDesc, reportTestObj);
			} else if (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsNextQuestion")
					.contains(Constants.FAIL)) {
				logResultInReport(Constants.FAIL + ":" + " Reached end to Module, Unable to found question of type : "
						+ questionType, stepDesc, reportTestObj);
			}
		} catch (Exception e) {
			APP_LOG.error("Exception encountered while navigating to the specified type " + questionType + " "
					+ e.getMessage());
			logResultInReport(Constants.FAIL + ":" + " Exception encountered while navigating to the specified type "
					+ questionType + " " + e.getMessage(), stepDesc, reportTestObj);
		}

	}

	/**
	 * @author mukul.sehra
	 * @date 2018-05-10
	 * @returnType GLPInstructor_ManagementDashboardPage
	 * @return GLPInstructor_ManagementDashboardPage
	 * @description this method takes you from core instruction page to the
	 *              management dashboard page
	 * @precondition user should be on core instruction page in instructor flow
	 */

	public GLPInstructor_ManagementDashboardPage navigateToManagementDashboardFromCoreInstructions() {
		this.clickOnElement("CoreInstructionBackButton",
				"Click on the back chevron icon to navigate back to Management Dashboard");
		GLPInstructor_ManagementDashboardPage objManagementDashboard = new GLPInstructor_ManagementDashboardPage(
				reportTestObj, APP_LOG);
		objManagementDashboard.verifyElementPresent("InstructorManagementModule11Name",
				"Verify successfull navigation to Management Dashboard");
		return new GLPInstructor_ManagementDashboardPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-14
	 * @returnType void
	 * @description Verify 1 or more than 1 answer is present for MCQMA
	 * @precondition User should be on mcqma type of question on preview question
	 *               screen in instructor flow
	 */

	public void verifyMultipleAnswerForMCQMA() {
		APP_LOG.info("Verifying 1 or more than 1 answer is present for MCQMA question...");
		FindElement findElement = new FindElement();
		List<WebElement> listofelements = findElement.getElements("CoreInstructionPreviewQuestionMCQMAAnswer");
		if (listofelements.size() >= 1) {
			logResultInReport(
					Constants.PASS + ":" + " Number of answers present for this MCQMA Question are :"
							+ listofelements.size(),
					"Verify 1 or more than 1 answer is present for MCQMA", reportTestObj);
		} else {
			logResultInReport(Constants.FAIL + ":" + "No answer provided for the MCQMA question",
					"Verify 1 or more than 1 answer is present for MCQMA", reportTestObj);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-14
	 * @returnType void
	 * @param stepDesc
	 * @description
	 * @precondition
	 */
	public void verifyCollectionBasedQuestion(String stepDesc) {
		APP_LOG.info("Verifying question is of Collection type...");
		int count = 0;
		try {
			while (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsNextQuestion")
					.contains(Constants.PASS) && count < 40) {
				String questionText = this.performAction.execute(ACTION_GET_TEXT,
						"CoreInstructorPreviewQuestionsQuestionText");
				if (questionText.contains(ResourceConfigurations.getProperty("collectionQuestion"))) {
					logResultInReport(Constants.PASS + ":" + " Question of type : Collection found", stepDesc,
							reportTestObj);
					break;
				}
				this.performAction.execute(ACTION_CLICK, "CoreInstructionPreviewQuestionsNextQuestion");
				count++;
			}
			if (count == 40) {
				logResultInReport(Constants.FAIL + ":" + " Unable to found question of type : Collection", stepDesc,
						reportTestObj);
			} else if (this.performAction
					.execute(ACTION_VERIFY_ELEMENT_PRESENT, "CoreInstructionPreviewQuestionsNextQuestion")
					.contains(Constants.FAIL)) {
				logResultInReport(
						Constants.FAIL + ":" + " Reached end to Module, Unable to found question of type : Collection",
						stepDesc, reportTestObj);
			}

		} catch (Exception e) {
			logResultInReport(Constants.FAIL + ":" + " Exception encountered while navigating to Collection type "
					+ e.getMessage(), stepDesc, reportTestObj);
		}
	}

	/**
	 * @author anurag.garg1
	 * @date 2018-05-15
	 * @returnType void
	 * @param stepDesc
	 * @description get total parts of a multipart question
	 * @precondition
	 */
	public void getTotalNumberOfPartsForMultiPart(String stepDesc) {
		APP_LOG.info("Extracting Total Parts for a MultiPart assessment...");
		try {
			FindElement findElement = new FindElement();
			List<WebElement> listofelements = findElement
					.getElements("CoreInstructionPreviewQuestionMultiPartQuestionIds");
			if (listofelements.size() > 1) {
				logResultInReport(
						Constants.PASS + " : " + "Total parts in this Question are : " + listofelements.size(),
						stepDesc, reportTestObj);
				Iterator<WebElement> itr = listofelements.iterator();
				while (itr.hasNext()) {
					logResultInReport(
							Constants.PASS + " : " + " Question Id's for this Question are: "
									+ itr.next().getAttribute("id"),
							"Display Question id's of a MultiPart Question", reportTestObj);
				}
			} else {
				logResultInReport(Constants.FAIL + " : " + "Not a MultiPart Question", stepDesc, reportTestObj);
			}

		} catch (Exception e) {
			logResultInReport(Constants.FAIL + ":"
					+ " Exception encountered while displaying total parts of a multipart question " + e.getMessage(),
					stepDesc, reportTestObj);
		}

	}

	/**
	 * @author mukul.sehra
	 * @date 2018-05-21
	 * @return GLPInstructor_CoreInstructionsPage
	 * @description Verifies that the video tutorial is displayed, checks for the
	 *              video poster and its link validity
	 * @precondition - user should be on coreInstructions page for an LO
	 */
	public GLPInstructor_CoreInstructionsPage verifyVideoPoster() {
		verifyElementPresent("CoreInstructionVidePlayBackProgressBar",
				"Verify that Video player is present for the Video Tutorial");
		GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
		String videoPosterUrl = getElementAttribute("CoreInstructionVidePlayBackProgressBar", "poster",
				"Verify the video poster availability.");
		Response videoResponse = objRestUtil.apiCallWithoutJson("GET", videoPosterUrl);
		if (videoResponse.getStatusCode() == 200)
			logResultInReport(
					Constants.PASS + ": Poster url is valid and not a broken link : " + videoPosterUrl
							+ ", Status code : " + videoResponse.getStatusCode(),
					"Verify that Video poster url is not broken", reportTestObj);
		else
			logResultInReport(
					Constants.FAIL + ": Poster url is invalid and is a broken link : " + videoPosterUrl
							+ ", Status code : " + videoResponse.getStatusCode(),
					"Verify that Video poster url is not broken", reportTestObj);
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);
	}

	public void verifyContentNotNull(String contentType) {
		try {
			String contentText = this.performAction.execute(ACTION_GET_TEXT, "CoreInstructionContent");
			if (contentText.equals("")) {
				logResultInReport(Constants.FAIL + " : "
						+ "No text displayed for the Core Instructions for content type : " + contentType,
						"Verify core instruction content text is not null", reportTestObj);
			} else {
				logResultInReport(Constants.PASS + " : "
						+ " Text displayed for the Core Instructions for content type : " + contentType,
						"Verify core instruction content text is not null", reportTestObj);
			}
		} catch (Exception e) {
			logResultInReport(
					Constants.FAIL + " : "
							+ "Exception displayed while verifying content for core instruction  for content type : "
							+ contentType + e.getMessage(),
					"Verify core instruction content text is not null", reportTestObj);
		}
	}

	/**
	 * @author mukul.sehra
	 * @date 2018-05-21
	 * @return GLPInstructor_CoreInstructionsPage
	 * @description Traverse each EO in an LO and verify that the content is
	 *              displayed
	 * @precondition - user should be on coreInstructions page for an LO
	 */
	public GLPInstructor_CoreInstructionsPage traverseAndVerifyContentEo() {
		try {
			FindElement find = new FindElement();
			List<WebElement> eoList = find.getElements("CoreInstructionButtonEO");
			List<WebElement> eoListItem = new ArrayList<WebElement>();
			String eoLabel = "";
			String eoItemLabel = "";
			for (int listCounter = 0; listCounter < eoList.size() - 1; listCounter++) {
				eoLabel = eoList.get(listCounter).getText();
				if (listCounter == 0) {
					if (!(eoList.get(listCounter).getAttribute("class").contains("active")))
						logResultInReport(Constants.FAIL + ": First EO link '" + eoLabel + "' is not active",
								"Verify that First EO link '" + eoLabel + "'  is be default active", reportTestObj);
				} else {
					eoList.get(listCounter).click();
					if (eoList.get(listCounter).getAttribute("class").contains("active"))
						logResultInReport(Constants.PASS + ": EO link '" + eoLabel + "'  is clicked and is active",
								"Verify that EO link '" + eoLabel + "'  is clicked and is active", reportTestObj);
					else
						logResultInReport(Constants.FAIL + ": EO link '" + eoLabel + "'  is clicked but is not active",
								"Verify that EO link '" + eoLabel + "'  is clicked and is active", reportTestObj);
				}
				eoListItem = find.getElements("CoreInstructionEoListItem");
				if (null == eoListItem || eoListItem.isEmpty()) {
					logResultInReport(Constants.FAIL + ": LO '" + eoItemLabel + "' is not expandable",
							"Verify that LO '" + eoItemLabel + "'  is Expendable", reportTestObj);
				}
				List<WebElement> eoListItemType = find.getElements("CoreInstructionEoType");
				for (int listItemCounter = 0; listItemCounter < eoListItem.size(); listItemCounter++) {
					eoItemLabel = eoListItem.get(listItemCounter).getText();
					if (listItemCounter == 0) {
						if (!(eoListItem.get(listItemCounter).getAttribute("class").contains("active")))
							logResultInReport(
									Constants.FAIL + ": First EO item link '" + eoItemLabel + "' is not active",
									"Verify that First EO item link '" + eoItemLabel + "'  is be default active",
									reportTestObj);
					}

					else {
						eoListItem.get(listItemCounter).click();
						if (eoListItem.get(listItemCounter).getAttribute("class").contains("active"))
							logResultInReport(
									Constants.PASS + ": EO item link '" + eoItemLabel + "'  is clicked and is active",
									"Verify that EO item link '" + eoItemLabel + "'  is clicked and is active",
									reportTestObj);
						else
							logResultInReport(
									Constants.FAIL + ": EO item link '" + eoItemLabel
											+ "'  is clicked but is not active",
									"Verify that EO item link '" + eoItemLabel + "'  is clicked and is active",
									reportTestObj);
					}

					if (eoListItemType.get(listItemCounter).getAttribute("type").equals("video")) {
						verifyVideoPoster();
					}

					else if (eoListItemType.get(listItemCounter).getAttribute("type").equals("content"))
						verifyContentNotNull("content");
					else if (eoListItemType.get(listItemCounter).getAttribute("type").equals("edit"))
						verifyContentNotNull("edit");
				}
			}
		} catch (Exception e) {
			APP_LOG.info("Exception in verifying EOs content because : " + e.getMessage());
			logResultInReport(Constants.FAIL + ": Exception while verifying EOs content : " + e.getMessage(),
					"Verify that EOs content is not blank", reportTestObj);
		}
		return new GLPInstructor_CoreInstructionsPage(reportTestObj, APP_LOG);
	}

	/**
	 * @author shefali.jain
	 * @date 2018-056-04
	 * @return void
	 * @description -Verifying first EO on instructor TOC page is expanded by
	 *              default when any LO is opened..
	 * @precondition - Any LO is tapped on management screen
	 */
	public void verifyFirstEOIsExpanded() {

		APP_LOG.info("Verifying first eo on instructor toc page is expanded by defualt...");
		try {
			String eoDefaultState = getElementAttribute("CoreInstruction1stEODefaultState", "class",
					"Get the class attribute of the default expanded EO");
			if (eoDefaultState.contains("active")) {
				logResultInReport(Constants.PASS + " : " + "First EO link is active ",
						"Verify first EO is expanded on opening any LO", reportTestObj);
			} else {
				logResultInReport(Constants.FAIL + " : " + "First EO link is not active ",
						"Verify first EO is expanded on opening any LO", reportTestObj);
			}
		} catch (Exception e) {
			APP_LOG.error("Exception occured in method verifyFirstEOIsExpanded : " + e.getMessage());
			logResultInReport(
					Constants.FAIL + " : " + "Exception occured in method verifyFirstEOIsExpanded : " + e.getMessage(),
					"Verify first EO is expanded on opening any LO", reportTestObj);
		}

	}

	/**
	 * @author anurag.garg1
	 * @date 2018-06-22
	 * @returnType void
	 * @description
	 * @precondition
	 */
	public void verifyFirstCoreInstructionIsSelectedByDefault() {

		APP_LOG.info("Verifying 1st Core instruction is selected by default...");
		try {
			String eoDefaultState = getElementAttribute("CoreInstruction1stDefaultCoreInstruction", "class",
					"Get the class attribute of the 1st Core instruction group");
			if (eoDefaultState.contains("active")) {
				logResultInReport(Constants.PASS + " : " + "First Core Instruction is active",
						"Verify first Core Instruction is selected by default", reportTestObj);
			} else {
				logResultInReport(Constants.FAIL + " : " + "First Core Instruction is active",
						"Verify 1st Core instruction is selected by default", reportTestObj);
			}
		} catch (Exception e) {
			APP_LOG.error(
					"Exception occured in method verifyFirstCoreInstructionIsSelectedByDefault : " + e.getMessage());
			logResultInReport(Constants.FAIL + " : "
					+ "Exception occured in method verifyFirstCoreInstructionIsSelectedByDefault : " + e.getMessage(),
					"Verify 1st Core instruction is selected by default", reportTestObj);
		}

	}
}