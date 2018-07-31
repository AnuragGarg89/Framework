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
package com.autofusion.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;

public class ReadXlsForUI {

    protected static Properties CONFIG;
    protected static Logger APP_LOG;
    public String xlsSheet = "Sheet1";
    public String fileExtention = ".xlsx";

    public List<String> readSuitXls(String testCaseBasePath, String device) {
        List<String> suitList = new ArrayList<>();
        try {
            Xlsx_Reader suiteXls = new Xlsx_Reader(
                    testCaseBasePath + File.separator + device + File.separator
                            + Constants.SUIT_FILE_NAME);
            String testSheetName;
            if ("window".equalsIgnoreCase(device)) {
                testSheetName = Constants.TEST_WIN_SUITE_SHEET;
            } else if ("Web".equalsIgnoreCase(device)) {
                testSheetName = Constants.TEST_SUITE_SHEET;
            } else if ("Mobile".equalsIgnoreCase(device)) {
                testSheetName = Constants.TEST_SUITE_SHEET;
            } else {
                testSheetName = Constants.TEST_SUITE_SHEET;
            }

            for (int suiteCount = 2; suiteCount <= suiteXls
                    .getRowCount(testSheetName); suiteCount++) {

                ArrayList<String> suitListDetail = new ArrayList<>();
                if ("".equals(suiteXls.getCellData(testSheetName,
                        Constants.COL_HEAD_TSID, suiteCount))
                        || "N".equals(suiteXls.getCellData(testSheetName,
                                device, suiteCount))) {
                    continue;
                }

                suitListDetail.add(suiteXls.getCellData(testSheetName,
                        Constants.COL_HEAD_TSID, suiteCount));
                suitListDetail.add(suiteXls.getCellData(testSheetName,
                        Constants.COL_HEAD_DESCRIPTION, suiteCount));
                suitListDetail.add(suiteXls.getCellData(testSheetName,
                        Constants.COL_HEAD_RUNMODE, suiteCount));

                if ("web".equalsIgnoreCase(device)) {
                    suitListDetail.add(suiteXls.getCellData(testSheetName,
                            Constants.COL_HEAD_IE, suiteCount));
                    suitListDetail.add(suiteXls.getCellData(testSheetName,
                            Constants.COL_HEAD_FF, suiteCount));
                    suitListDetail.add(suiteXls.getCellData(testSheetName,
                            Constants.COL_HEAD_CHROME, suiteCount));
                } else if ("mobile".equalsIgnoreCase(device)) {
                    suitListDetail.add(suiteXls.getCellData(testSheetName,
                            Constants.COL_HEAD_ANDROID_BWR, suiteCount));
                    suitListDetail.add(suiteXls.getCellData(testSheetName,
                            Constants.COL_HEAD_IOS_BWR, suiteCount));
                }

                suitListDetail.add(suiteXls.getCellData(testSheetName,
                        Constants.COL_HEAD_EXECUTION_STATUS, suiteCount));

                suitList.addAll(suitListDetail);
            }

            return suitList;

        } catch (Exception e) {
            APP_LOG.error("" + e);

            return suitList;
        }
    }

    public List<String> readTestCaseXls(String testCaseBasePath,
            String testCaseSheet) {
        List<String> suitList = new ArrayList<>();
        try {
            File file = new File(testCaseBasePath + File.separator + "TestCases"
                    + File.separator + testCaseSheet + fileExtention);
            if (!file.exists()) {
                return suitList;
            }
            Xlsx_Reader suiteXls = new Xlsx_Reader(
                    testCaseBasePath + File.separator + "TestCases"
                            + File.separator + testCaseSheet + fileExtention);

            for (int suiteCount = 2; suiteCount <= suiteXls
                    .getRowCount(Constants.TEST_CASES_SHEET); suiteCount++) {

                ArrayList<String> testCaseDetailList = new ArrayList<>();

                if ("".equals(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                        Constants.COL_HEAD_TCID, suiteCount))) {
                    continue;
                }
                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_TCID, suiteCount));
                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_DESCRIPTION, suiteCount));
                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_RUNMODE, suiteCount));
                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_DATA_DRIVEN, suiteCount));

                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_PACKAGE, suiteCount));
                testCaseDetailList
                        .add(suiteXls.getCellData(Constants.TEST_CASES_SHEET,
                                Constants.COL_HEAD_CLASS_NAME, suiteCount));

                suitList.addAll(testCaseDetailList);
            }

        } catch (Exception e) {
            APP_LOG.info("File Not found" + e);
        }

        return suitList;
    }

    public List<String> readTestStepsXls(String testCaseBasePath,
            String testSuit, String testCaseId) {

        List<String> testCaseName = new ArrayList<>();

        try {
            File file = new File(testCaseBasePath + File.separator + "TestCases"
                    + File.separator + testSuit + fileExtention);

            if (!file.exists()) {
                return testCaseName;
            }

            Xlsx_Reader suiteXls = new Xlsx_Reader(
                    testCaseBasePath + File.separator + "TestCases"
                            + File.separator + testSuit + fileExtention);

            for (int suiteCount = 2; suiteCount <= suiteXls
                    .getRowCount(Constants.TEST_STEPS); suiteCount++) {

                String tCId = suiteXls.getCellData(Constants.TEST_STEPS,
                        Constants.COL_HEAD_TCID, suiteCount);

                if (testCaseId.equalsIgnoreCase(tCId)) {
                    ArrayList<String> testCaseDetailList = new ArrayList<>();

                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_TCID, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_TSID, suiteCount));
                    testCaseDetailList.add(suiteXls.getCellData(
                            Constants.TEST_STEPS,
                            Constants.COL_HEAD_STEP_DESCRIPTION, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_KEYWORD, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_ELEMENT_ID, suiteCount));
                    if ("Login_Password"
                            .equals(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_ELEMENT_ID, suiteCount))
                            || "Sign_Password".equals(
                                    suiteXls.getCellData(Constants.TEST_STEPS,
                                            Constants.COL_HEAD_ELEMENT_ID,
                                            suiteCount))) {
                        testCaseDetailList.add("*****");
                    } else {
                        testCaseDetailList
                                .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                        Constants.COL_HEAD_DATA, suiteCount));
                    }

                    testCaseName.addAll(testCaseDetailList);
                }

            }
        } catch (Exception e) {
            APP_LOG.info("File Not found" + e);
        }

        return testCaseName;
    }

    public static void main(String[] args) {
        // TODO Auto-generated
    }

    public int test() {
        return 10;
    }

    public List<String> readConfigIdeData(String testCaseBasePath)
            throws IOException {

        Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath + File.separator
                + "ide" + File.separator + Constants.IDE_FILE_NAME);
        List<String> suitList = new ArrayList<>();
        List<String> l = checkExistanceTests(testCaseBasePath);

        for (int suiteCount = 2; suiteCount <= suiteXls
                .getRowCount(xlsSheet); suiteCount++) {

            if ("N".equalsIgnoreCase(suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_RUNMODE, suiteCount))) {
                continue;
            }
            ArrayList<String> suitListDetail = new ArrayList<>();

            String newSuitName = suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_IDE_FILE_NAME, suiteCount);
            String flagNew = suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_CREATE_NEW_SUIT, suiteCount);

            if (l.contains(newSuitName) && "Y".equalsIgnoreCase(flagNew)) {
                // skip duplicate suits
                continue;
            }

            suitListDetail.add(newSuitName);
            suitListDetail.add(flagNew);
            suitListDetail.add(suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_CREATE_NEW_TEST_CASE, suiteCount));
            suitListDetail.add(suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_APPEND_IN_TEST_CASE, suiteCount));
            suitListDetail.add(suiteXls.getCellData(xlsSheet,
                    Constants.COL_HEAD_DESCRIPTION, suiteCount));

            suitList.addAll(suitListDetail);
        }

        return suitList;
    }

    public List<String> checkExistanceTests(String testCaseBasePath) {

        Xlsx_Reader suiteXls = new Xlsx_Reader(
                testCaseBasePath + File.separator + Constants.SUIT_FILE_NAME);
        ArrayList<String> list = new ArrayList<>();

        for (int suiteCount = 2; suiteCount <= suiteXls
                .getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++) {
            String suit = suiteXls.getCellData(Constants.TEST_SUITE_SHEET,
                    Constants.COL_HEAD_TSID, suiteCount);
            list.add(suit);
        }

        return list;

    }

    public String getMachineNameSuitXls(String testCaseBasePath) {

        Xlsx_Reader suiteXls = new Xlsx_Reader(
                testCaseBasePath + File.separator + "Config.xlsx");

        String machineDetail = "";
        String envDetail = "";

        for (int suiteCount = 2; suiteCount <= suiteXls
                .getRowCount("UIConfigration"); suiteCount++) {

            String data = suiteXls
                    .getCellData("UIConfigration", "MachineName", suiteCount)
                    .trim();
            String dataDesc = suiteXls.getCellData("UIConfigration",
                    "MachineDescription", suiteCount).trim();

            String dataEnv = suiteXls
                    .getCellData("EnviromentConfig", "Enviroment", suiteCount)
                    .trim();
            String dataEnvDesc = suiteXls
                    .getCellData("EnviromentConfig", "Enviroment", suiteCount)
                    .trim();

            if (!"".equals(data)) {
                if ("".equals(dataDesc)) {
                    dataDesc = data;
                }
                if ("localhost".equalsIgnoreCase(data)) {
                    machineDetail += "<option value='" + data + "' selected>"
                            + dataDesc + "</option>";
                } else {
                    machineDetail += "<option value='" + data + "' >" + dataDesc
                            + "</option>";
                }
            }

            if (!"".equals(dataEnv)) {
                if ("".equals(dataEnvDesc)) {
                    dataEnvDesc = data;
                }
                envDetail += "<option value='" + dataEnv + "' >" + dataEnvDesc
                        + "</option>";
            }

        }

        return machineDetail + "##" + envDetail;
    }

}
