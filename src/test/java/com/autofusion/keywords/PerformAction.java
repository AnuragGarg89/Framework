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
package com.autofusion.keywords;

/**
 * @author nitin.singh
 */
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.autofusion.bean.CommonUtility;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;

public class PerformAction extends FindElement {
    public PerformAction() {
        // TODO Auto-generated constructor stub
    }

    protected String filePath = "";
    protected String st = "";
    protected String et = "";
    protected String stepDescription = "";
    protected String userInputValue = "";
    private KeywordsFactory KeywordsFactory = new KeywordsFactory();

    /**
     * This function take args in Map.
     * dataMap.put(KeywordConstant.ACTION_TO_PERFORM, ACTION_CLICK);
     * dataMap.put(KeywordConstant.ELEMENT_LOCATOR, "Xpath");
     * dataMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");
     * dataMap.put(KeywordConstant.STEP_DESCRIPTION, "Click on ..");
     */
    public String execute(Map<String, String> argsMap) {

        String currentKeyword = "";
        String result = "";
        try {
            if (argsMap.containsKey(KeywordConstant.ACTION_TO_PERFORM)) {
                currentKeyword = argsMap.get(KeywordConstant.ACTION_TO_PERFORM);
            }
            result = this.execute(currentKeyword, argsMap);
        } catch (Exception e) {
            APP_LOG.debug("execute " + e);
        }
        return result;
    }

    /**
     * @description Function to execute
     * @param currentKeyword
     *            Keywords
     * @return keywordResult Keyword Results
     */

    public String execute(String currentKeyword, Map<String, String> argsMap) {
        this.st = CommonUtility.now("yyyy-MM-dd hh:mm:ss");
        String keywordResult = Constants.FAIL;

        try {
            this.userInputValue = argsMap
                    .get(KeywordConstant.ELEMENT_INPUT_VALUE);
            Object objKeyword = this.KeywordsFactory.getInstance(currentKeyword,
                    APP_LOG);
            Method method = objKeyword.getClass().getDeclaredMethod(
                    currentKeyword, new Class[] { Map.class });
            keywordResult = (String) method.invoke(objKeyword,
                    new Object[] { argsMap });
        } catch (Exception e) {
            APP_LOG.error("" + e);
        }
        this.et = CommonUtility.now("yyyy-MM-dd hh:mm:ss");

        return keywordResult;
    }

    /**
     * @param currentKeyword
     *            Current Keyword
     * @param elementLocator
     *            get Locators
     * @param inputValue
     *            Input Data
     * @return result Result
     */

    public String execute(String currentKeyword, String elementLocator,
            String inputValue) {
        String result;
        Map<String, String> argsMap = new HashMap<>();
        argsMap.put(KeywordConstant.ELEMENT_LOCATOR, elementLocator);
        this.APP_LOG.debug("Verify text message " + elementLocator);
        argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, inputValue);
        result = this.execute(currentKeyword, argsMap);
        return result;
    }

    /**
     * @param currentKeyword
     *            Current Keywords
     * @param elementLocator
     *            get Locators
     * @return result Result
     */

    public String execute(String currentKeyword, String elementLocator) {
        Map<String, String> argsMap = new HashMap<>();
        argsMap.put(KeywordConstant.ELEMENT_LOCATOR, elementLocator);
        this.APP_LOG.debug("Verify text message " + elementLocator);
        argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");
        String result = this.execute(currentKeyword, argsMap);
        return result;
    }

    /**
     * @param currentKeyword.
     * @return result
     */

    public String execute(String currentKeyword) {

        Map<String, String> argsMap = new HashMap<>();
        argsMap.put(KeywordConstant.ELEMENT_LOCATOR, "");
        argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");
        String result = this.execute(currentKeyword, argsMap);

        return result;

    }

}