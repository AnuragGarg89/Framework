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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;

public class ReadConfigXlsFiles extends BaseClass {
    public ReadConfigXlsFiles() {
        // TODO Auto-generated constructor stub
    }

    private Map<String, String> programSpecificData;
    public Map<String, HashMap<String, String>> orMap;
    private Map<String, String> enviromentMap;
    private Map<String, HashMap<String, String>> globalVariableMap;
    private Map<String, HashMap<String, String>> masterMap = new HashMap<>();

    /**
     * 
     * @description Read Object Repository, This method will read the program
     *              specific data sheet for current program.
     * @param folderLocation
     *            Folder location
     * @param APP_LOGS
     *            Application log
     */
    public Map<String, HashMap<String, String>>
           readObjectRepository(String folderLocation, Logger APP_LOGS) {
        orMap = new HashMap<>();

        APP_LOGS.info("Reading Object Repository");

        try {
            Xls_Reader webORXls = new Xls_Reader(folderLocation + "/OR.xlsx");
            for (int row = 2; row <= webORXls.getRowCount("OR"); row++) {
                LinkedHashMap<String, String> detailMap = new LinkedHashMap<>();

                detailMap.put("xpathChrome",
                        webORXls.getCellData("OR", "xpathChrome", row));
                detailMap.put("id", webORXls.getCellData("OR", "id", row));
                detailMap.put("name", webORXls.getCellData("OR", "name", row));
                detailMap.put("css", webORXls.getCellData("OR", "css", row));
                detailMap.put("xp", webORXls.getCellData("OR", "xpath", row));
                detailMap.put("tagName",
                        webORXls.getCellData("OR", "tagName", row));
                detailMap.put("link", webORXls.getCellData("OR", "link", row));
                detailMap.put("type", webORXls.getCellData("OR", "type", row));
                String key = webORXls.getCellData("OR", "Variable Name", row);
                orMap.put(key, detailMap);
            }
        } catch (Exception e) {
            APP_LOGS.debug("Error in reading object repository" + e);
        }

        setOrMap(orMap);
        return orMap;
    }

    public Map<String, String> readConfigurations(Xls_Reader webORXls,
            Map<String, String> configurationMap, Logger APP_LOG) {
        APP_LOG.info("Reading Configurations");
        if (!configurationMap.isEmpty()) {
            APP_LOG.info("isEmpty");
            return configurationMap;
        }

        try {
            for (int row = 2; row <= webORXls
                    .getRowCount("Configurations"); row++) {
                String key = webORXls.getCellData("Configurations", "Key", row);
                String value = webORXls.getCellData("Configurations", "Value",
                        row);
                configurationMap.put(key, value);
            }
        } catch (Exception e) {
            APP_LOG.debug("Error in reading object repository" + e);
        }

        return configurationMap;
    }

    public Map<String, String> readEnviromentsUrls(Xls_Reader webORXls,
            Map<String, String> configurationMap, String enviroment,
            Logger APP_LOGS) {
        APP_LOGS.info("Reading readEnviromentsUrls");
        try {

            for (int row = 2; row <= webORXls
                    .getRowCount("EnviromentConfig"); row++) {

                String key = webORXls
                        .getCellData("EnviromentConfig", "Enviroment", row)
                        .toLowerCase();
                if (enviroment.trim().equalsIgnoreCase(key)) {
                    configurationMap.put("Enviroment", webORXls
                            .getCellData("EnviromentConfig", "Enviroment", row)
                            .toLowerCase());
                    configurationMap.put("DomainUrl", webORXls
                            .getCellData("EnviromentConfig", "DomainUrl", row));
                    configurationMap.put("DB_URL", webORXls
                            .getCellData("EnviromentConfig", "DB_URL", row)
                            .toLowerCase());
                    configurationMap.put("DB_USER", webORXls
                            .getCellData("EnviromentConfig", "DB_USER", row));
                    configurationMap.put("DB_PASS", webORXls
                            .getCellData("EnviromentConfig", "DB_PASS", row));
                }
            }
        } catch (Exception e) {
            APP_LOGS.debug("Error in reading readEnviromentsUrls" + e);
        }

        setEnviromentMap(configurationMap);
        return configurationMap;
    }

    public Map<String, String> readConfigurationsXls(String testCaseBasePath,
            String enviroment, Logger APP_LOGS) {
        testCaseBasePath = testCaseBasePath + "web//testData//";
        APP_LOGS.info("Reading " + testCaseBasePath + "Config.xlsx");
        Map<String, String> configurationMap = new HashMap<>();
        Xls_Reader webORXls = null;
        if (course.trim().equalsIgnoreCase("Rio-SP32-CoreInstructions")) {
            webORXls = new Xls_Reader(
                    testCaseBasePath + "Config_Rio-SP32-CoreInstructions.xlsx");
            APP_LOGS.info("Course " + course
                    + "was provided from config.property file");
        } else if (course.trim().equalsIgnoreCase("Rio-AllChapters")) {
            webORXls = new Xls_Reader(
                    testCaseBasePath + "Config_Rio-AllChapters.xlsx");
            APP_LOGS.info("Course " + course
                    + "was provided from config.property file");
        }

        if (webORXls.isSheetExist(enviroment.trim() + "Configurations")) {
            configurationMap = readEnvironmentConfiguration(webORXls,
                    configurationMap, enviroment, APP_LOGS);
            APP_LOG.info("Environment configuration file sucessfully readed");
        } else {
            APP_LOGS.debug("Environemnt Configuration sheet not exist");
            APP_LOG.info("Environemnt Configuration sheet not exist");
        }

        return configurationMap;
    }

    /**
     * @description Read environment related configuration elements and it will
     *              over right the configuration element. Priority will be given
     *              to EnvironmentConfiguration
     * @param webORXls
     *            Row number
     * @param configurationMap
     *            configuration list
     * @param enviroment
     *            Environment details
     * @param APP_LOGS
     *            Application log
     * @return configuration list
     */
    public Map<String, String> readEnvironmentConfiguration(Xls_Reader webORXls,
            Map<String, String> configurationMap, String enviroment,
            Logger APP_LOGS) {
        APP_LOGS.info("Reading readEnviromentsUrls");
        try {
            for (int row = 2; row <= webORXls
                    .getRowCount(enviroment + "Configurations"); row++) {
                String key = webORXls.getCellData(enviroment + "Configurations",
                        "Key", row);
                String value = webORXls.getCellData(
                        enviroment + "Configurations", "Value", row);
                configurationMap.put(key, value);
            }

        } catch (Exception e) {
            APP_LOGS.debug("Error in reading readEnviromentsUrls" + e);
        }

        setEnviromentMap(configurationMap);
        return configurationMap;
    }

    /**
     * @description NewDone This function is used for the reading the global var
     *              from common and local sheet.
     * @param xlsFileName
     *            xsl file name
     * @param getCellDataName
     *            Column data
     * @return glsMap for local and globalVariableMap for common sheet
     */
    public Map<String, HashMap<String, String>> readObjectGlobalVar(
            String xlsFileName, String getCellDataName, Logger APP_LOGS) {
        APP_LOGS.info("Reading global variable");
        globalVariableMap = new HashMap<>();
        try {
            Xls_Reader webORXls = new Xls_Reader(xlsFileName + ".xlsx");
            for (int row = 2; row <= webORXls
                    .getRowCount(getCellDataName.toString()); row++) {
                HashMap<String, String> detailMap = new HashMap<>();
                String key = webORXls.getCellData(getCellDataName.toString(),
                        "ElementID", row);
                detailMap.put("Data", webORXls
                        .getCellData(getCellDataName.toString(), "Data", row));

                detailMap.put("AppendRandom", webORXls.getCellData(
                        getCellDataName.toString(), "AppendRandom", row));
                globalVariableMap.put(key, detailMap);
            }
        } catch (Exception e) {
            APP_LOGS.debug(e);
        }

        setGlobalVariableMap(globalVariableMap);
        return globalVariableMap;
    }

    public Map<String, String> getProgramSpecificData() {
        return programSpecificData;
    }

    public void
           setProgramSpecificData(Map<String, String> programSpecificData) {
        this.programSpecificData = programSpecificData;
    }

    public Map<String, HashMap<String, String>> getOrMap() {
        return orMap;
    }

    public void setOrMap(Map<String, HashMap<String, String>> orMap) {
        this.orMap = orMap;
    }

    public Map<String, String> getEnviromentMap() {
        return enviromentMap;
    }

    public void setEnviromentMap(Map<String, String> enviromentMap) {
        this.enviromentMap = enviromentMap;
    }

    public Map<String, HashMap<String, String>> getGlobalVariableMap() {
        return globalVariableMap;
    }

    public void setGlobalVariableMap(
            Map<String, HashMap<String, String>> globalVariableMap) {
        this.globalVariableMap = globalVariableMap;
    }

    public String getGlobalVariableMapData(String data) {

        if (globalVariableMap.containsKey(data)) {
            data = globalVariableMap.get(data).toString();
            return data;
        }

        return data;
    }

    public String getProgramSpecificData(String data) {

        String psdKey = data.split("\\|")[1];
        if (programSpecificData.containsKey(psdKey)) {
            data = programSpecificData.get(psdKey);
            return data;
        }

        return data;
    }

    // Read Data from DataSheet
    public void readDataFromDataSheet(String testStepsCaseId,
            Xls_Reader currentSuiteXls) {
        String tcaseId;
        masterMap.clear();
        for (int i = 2; i <= currentSuiteXls
                .getRowCount(Constants.DATA_SHEET); i++) {
            tcaseId = currentSuiteXls.getCellData(Constants.DATA_SHEET, 0, i);
            if (tcaseId.equalsIgnoreCase(testStepsCaseId)) {
                HashMap<String, String> rowData = new HashMap<>();

                for (int j = 0; j < currentSuiteXls
                        .getColumnCount(Constants.DATA_SHEET); j++) {

                    String colHeading = currentSuiteXls
                            .getCellData(Constants.DATA_SHEET, j, 1);
                    rowData.put(colHeading, currentSuiteXls
                            .getCellData(Constants.DATA_SHEET, colHeading, i));
                }
                masterMap.put(currentSuiteXls.getCellData(Constants.DATA_SHEET,
                        Constants.COL_HEAD_TCDI, i), rowData);

            }
        }

    }

    public Map<String, HashMap<String, String>> getMasterMap() {
        return masterMap;
    }

    public void setMasterMap(Map<String, HashMap<String, String>> masterMap) {
        this.masterMap = masterMap;
    }

    public static String getSQLQuery(String testCaseBasePath, String sqlId,
            Logger APP_LOGS) {
        APP_LOGS.info("Reading Object Repository");
        String query = "";
        try {
            Xls_Reader webORXls = new Xls_Reader(
                    testCaseBasePath + "/CommonSteps.xlsx");
            for (int row = 2; row <= webORXls.getRowCount("SQLQuery"); row++) {

                String sqlIdXls = webORXls.getCellData("SQLQuery", "SQLID", row)
                        .toLowerCase();
                if (sqlId.equalsIgnoreCase(sqlIdXls)) {
                    query = webORXls.getCellData("SQLQuery", "Query", row)
                            .toLowerCase();
                }
            }
        } catch (Exception e) {
            APP_LOGS.error("Error in reading object repository" + e);
        }

        return query;
    }

    public void updateKeysInConfigFiles(String workbooksToUpdate,
            String sheetsToUpdate, HashMap<String, String> masterMap)
            throws IOException {// ,Logger
        // APP_LOGS)
        // {
        try {

            HashMap<String, String> map = new HashMap<String, String>();
            String[] files = workbooksToUpdate.split(",");
            int workbookCount = files.length;
            List<String> configWorkbook = new ArrayList<String>();
            // will change after restructuring
            String basePath = System.getProperty("user.dir")
                    + "/src/test/resources/essentials/web/testData/";

            for (int i = 0; i < workbookCount; i++)
                configWorkbook.add(files[i]);

            System.out.println(
                    "------Workbooks to Iterate----- " + configWorkbook);
            // APP_LOGS.info("------Workbooks to Iterate----- "+configWorkbook);

            String[] sheets = sheetsToUpdate.split(",");
            int sheetCount = sheets.length;
            List<String> configSheets = new ArrayList<String>();
            String temp = null;

            for (int i = 0; i < sheetCount; i++) {

                if (sheets[i].toLowerCase().contains("int")) {
                    temp = "IntConfigurations";
                }

                else if (sheets[i].toLowerCase().contains("qabite")) {
                    temp = "QABiteSizeConfigurations";
                }

                else if (sheets[i].toLowerCase().contains("stagebite")) {
                    temp = "StageBiteSizeConfigurations";
                }

                else if (sheets[i].toLowerCase().contains("qa")) {
                    temp = "QAConfigurations";
                }

                else if (sheets[i].toLowerCase().contains("stage")) {
                    temp = "StageConfigurations";
                }

                else if (sheets[i].toLowerCase().contains("uat")) {
                    temp = "UATConfigurations";
                }

                else
                    temp = sheets[i].toLowerCase();
                configSheets.add(temp);

            }

            for (int k = 0; k < configWorkbook.size(); k++) {
                Xls_Reader excelUtil = new Xls_Reader(
                        basePath + configWorkbook.get(k) + ".xlsx");
                XSSFWorkbook workbook = excelUtil.openXls();

                if (sheetCount == 1
                        && configSheets.get(0).equalsIgnoreCase("ALL")) {
                    configSheets.remove(0);
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        temp = workbook.getSheetAt(i).getSheetName().toString();
                        configSheets.add(temp);
                    }
                }

                System.out.println(
                        "--------Sheets to Iterate------ " + configSheets);
                // APP_LOGS.info("--------Sheets to Iterate------
                // "+configSheets);

                sheetCount = configSheets.size();
                XSSFSheet sheet;
                int lastRowNumber;
                String key = null;

                for (int i = 0; i < sheetCount; i++) {

                    sheet = workbook.getSheet(configSheets.get(i));
                    lastRowNumber = sheet.getLastRowNum() + 1;

                    System.out.println("Currently Iterating sheet --- "
                            + configSheets.get(i));
                    // APP_LOGS.info("Currently Iterating sheet ---
                    // "+configSheets.get(i));

                    map.putAll(masterMap);
                    int counter = 0;

                    for (int j = 1; j < lastRowNumber; j++) {

                        key = excelUtil.getCellData(configSheets.get(i), "Key",
                                j);

                        counter = counter + 1;

                        if (key.equals(""))
                            break;

                        if (map.containsKey(key)) {
                            System.out.println("Updating sheet for key ---- "
                                    + key + " with value " + map.get(key));
                            // APP_LOGS.info("Updating sheet for key ----
                            // "+key+"
                            // with value "+map.get(key));
                            excelUtil.setCellData(configSheets.get(i), "Value",
                                    j, map.get(key));
                            key = excelUtil.getCellData(configSheets.get(i),
                                    "Value", j);
                            System.out.println("New Value Update ---" + key);
                            // APP_LOGS.info("New Value Update ---"+key);
                            map.remove(excelUtil.getCellData(
                                    configSheets.get(i), "Key", j));

                        }
                    }

                    if (map.size() > 0) {

                        System.out.println(
                                "Some key value pairs need to be added");
                        // APP_LOGS.info("Some key value pairs need to be
                        // added");
                        for (Entry<String, String> es : map.entrySet()) {
                            lastRowNumber = lastRowNumber + 1;
                            System.out.println("Adding Key -- " + es.getKey()
                                    + " with value -- " + es.getValue());
                            // APP_LOGS.info("Adding Key -- "+es.getKey()+" with
                            // value -- "+es.getValue());

                            if (!excelUtil.getCellData(configSheets.get(i),
                                    "Key", counter).equals(""))
                                counter = counter + 1;

                            excelUtil.setCellData(configSheets.get(i), "Key",
                                    counter, es.getKey());
                            excelUtil.setCellData(configSheets.get(i), "Value",
                                    counter, es.getValue());
                        }
                    }

                }

                FileOutputStream fileOut = new FileOutputStream(
                        basePath + configWorkbook.get(k) + ".xlsx");
                workbook.write(fileOut);
                fileOut.close();

            }

        } catch (Exception e) {
            System.out.println("OK");

        }
    }
}
