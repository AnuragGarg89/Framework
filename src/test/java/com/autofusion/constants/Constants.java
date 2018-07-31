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
package com.autofusion.constants;

/**
 * @author
 */
public abstract class Constants {

    public static final long WAIT_ELEMENT_PRESENT = 20;
    public static final String TEST_SUITE_SHEET = "Test Suite";
    public static final String EXECUTION_ON_DEVICE = "Device";
    public static final String COMPONENT_NAME = "ComponentName";
    public static final String TEST_WIN_SUITE_SHEET = "WindowTestSuite";
    public static final String TEST_MBL_SUITE_SHEET = "MobileTestSuite";

    public static final String BROWSER_DRIVER_LOCATION = "";
    public static final String SELENIUM_PROFILE_LOCATION = "";
    public static final String TEST_CASES_SHEET = "Test Cases";
    public static final String TEST_STEPS = "Test Steps";
    public static final String COL_HEAD_RUNMODE = "Runmode";
    public static final String COL_HEAD_DESCRIPTION = "TestCaseDescription";
    public static final String COL_HEAD_STEP_DESCRIPTION = "TestStepDescription";
    public static final String COL_HEAD_KEYWORD = "Keyword";
    public static final String COL_HEAD_KEYWORD_IOS = "KeywordIOS";
    public static final String COL_HEAD_ELEMENT_ID = "ElementId";
    public static final String COL_HEAD_DATA = "Data";
    public static final String COL_HEAD_INDEX_POS = "IndexPosition";

    public static final String COL_HEAD_DATA_DRIVEN = "DataDriven";
    public static final String COL_DYNAMIC_XPATH = "DynamicXpathValue";
    public static final String COL_HEAD_GO_TO = "JumpTo";
    public static final String COL_HEAD_EXECUTION_STATUS = "ExecutionStatus";
    public static final String EXE_RUNNING = "Running";
    public static final String EXE_HANG = "Hang";
    public static final String EXE_COMPLETE = "Complete";
    public static final String EXE_INTERUPTED = "Inturupted";
    public static final String TEST_COMMON_SHEET = "CommonSteps";
    public static final String DEFAULT_BROWSER = "Firefox";

    public static final String COL_HEAD_WAIT = "Wait";
    public static final String COL_SKIP_STEP = "SkipTestStep";
    public static final String SCREENSHOT_FOLDER = "screenshots";
    public static final String SUIT_FILE_NAME = "suits.xlsx";

    public static final String SUIT_FILE_NAME_HTMLBROWSERREPORT = "htmlXlsReport";
    public static final String SUIT_FILE_SHEET = "Sheet1";
    public static final String SUIT_FILE_SHEET_TWO = "Sheet2";
    public static final String COL_HEAD_PASS = "Pass";
    public static final String COL_HEAD_FAIL = "Fail";
    public static final String COL_HEAD_SKIP = "Skipped";
    public static final String COL_HEAD_TOTAL_EXECUTED_TC = "Total Executed Test Cases";

    public static final String COL_HEAD_STATUS = "Status";
    public static final String COL_HEAD_EndTime = "End Time";
    public static final String COL_HEAD_Fail_TotalCount = "Fail/TotalCount";
    public static final String COL_HEAD_StartTime = "Start Time";
    public static final String COL_HEAD_TCID = "TCID";
    public static final String COL_HEAD_TSID = "TSID";
    public static final String COL_HEAD_RESULT = "Result";
    public static final String COL_HEAD_TestScript = "TestScript#";
    public static final String COL_HEAD_CURRENT_SUITE_NAME = "Current_SuiteName";

    public static final String COL_HEAD_IDE_FILE_NAME = "IdeFileName";
    public static final String COL_HEAD_CREATE_NEW_SUIT = "CreateNewSuit";
    public static final String COL_HEAD_CREATE_NEW_TEST_CASE = "CreateNewTestCase";
    public static final String COL_HEAD_APPEND_IN_TEST_CASE = "AppendInTestCase";

    public static final String IDE_FILE_NAME = "configIde.xlsx";

    public static final String COL_VARIABLE_NAME = "Variable Name";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_CSS = "css";
    public static final String COL_XPATH = "xpath";

    public static final String PASS = "PASS";
    public static final String FAIL = "FAIL";
    public static final String SKIP = "SKIP";

    public static final String PREFIX_DATA_SHEET = "DS";
    public static final String PREFIX_DATA_CONFIG = "CON";
    public static final String PREFIX_DATA_OR = "OR";

    public static final String PREFIX_FIELD_XPATH = "xpath";
    public static final String PREFIX_FIELD_NAME = "name";
    public static final String PREFIX_FIELD_ID = "id";
    public static final String PREFIX_FIELD_CSS = "css";
    public static final String PREFIX_FIELD_LINKTEXT = "link";
    public static final String PREFIX_FIELD_PARTIALLINKTEXT = "partialLinkText";
    public static final String PREFIX_FIELD_TAGNAME = "tagName";
    public static final String PREFIX_FIELD_CLASSNAME = "className";

    public static final String EXPECTED_RESULT = "ER";
    public static final String SPLIT_PARAMETER = "|";
    protected static final String elementPrefixList[] = { "css", "id", "//",
            "name", "link", "partiallinktext", "tagName", "class" };

    public static final String COL_DATA_DRIVEN = "DataDriven";
    public static final String DATA_SHEET = "Data Sheet";
    public static final String COL_HEAD_TCDI = "TCDI";

    public static final String PREFIX_TEST_STEP = "TS-";
    public static final String PREFIX_TEST_CASE = "TC-";

    public static final String COL_HEAD_CHROME = "Chrome";
    public static final String COL_HEAD_IE = "InternetExplorer";
    public static final String COL_HEAD_FF = "Firefox";
    public static final String COL_HEAD_SAFARI = "Safari";
    public static final String COL_HEAD_ANDROID_BWR = "Android";
    public static final String COL_HEAD_IOS_BWR = "IOS";
    public static final String COL_HEAD_CLASS_NAME = "ClassName";
    public static final String COL_HEAD_PACKAGE = "Package";
    public static final String PREFIX_FIELD_XPATHCHROME = "xpathChrome";

    public static final String COL_HEAD_CHROME_LOG_PATH = "cr_logPath";
    public static final String COL_HEAD_IE_LOG_PATH = "ie_logPath";
    public static final String COL_HEAD_FF_LOG_PATH = "ff_logPath";
    public static final Long HANG_TIMEOUT = 1800L;

    public static final String COMMON_STEPS_VAR_PREFIX = "COMMON_";
    public static final String COMMON_STEPS_FILE_NAME = "CommonSteps";

    /***** Config.xlsx Column Heading *****************/

    public static final String CONFIG_COL_DOMAIN_NAME = "DomainURL";
    public static final String OR = "or";
    public static final int INTEGER_ZERO = 0;
    public static final int INTEGER_ONE = 1;
    public static final int INTEGER_TWO = 2;
    public static final int INTEGER_THREE = 3;
    public static final int INTEGER_FOUR = 4;
    public static final int INTEGER_FIVE = 5;
    public static final int INTEGER_SIX = 6;
    public static final int INTEGER_SIXTY = 60;
    public static final int INTEGER_THOUSAND = 1000;

}
