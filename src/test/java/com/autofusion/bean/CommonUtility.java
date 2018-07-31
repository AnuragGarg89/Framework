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
/*
 * Common files like to read excel files.
 */
package com.autofusion.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xls_Reader;
import com.autofusion.util.Xlsx_Reader;

/**
 * @author nitin.singh
 */
import io.appium.java_client.AppiumDriver;

public class CommonUtility {

    public AppiumDriver driver = null;
    public Properties prop = new Properties();
    protected static ArrayList<String> strState = new ArrayList<>();
    public int i = 0, j = 0;
    protected static List<WebElement> tab2;
    protected static int apilevel;
    protected static String deviceversion;
    protected static String devicemodel;
    protected static String deviceserial;
    protected static String devicetype;
    protected static Properties CONFIG;
    protected static Properties USER_CONFIG;
    protected static String fileSeprator = System.getProperty("file.separator");
    protected static Logger APP_LOG;

    public CommonUtility(String path) {
    }

    public CommonUtility() {
    }

    public String[] listFile(String folder) {

        File dir = new File(folder);

        if (dir.isDirectory() == false) {
            APP_LOG.info("Directory does not exists : ");
            return null;
        }

        String[] list = dir.list();

        return list;
    }

    /*
     * public StringBuilder makeReport() throws IOException {
     * 
     * FileInputStream fstream = new FileInputStream(
     * "D://Projects//CEB-Automation//datamigration//DataMigrationDumpFiles//report//21//IRR_2_21.April.201410.57.37.html"
     * ); DataInputStream di; InputStreamReader in; di = new
     * DataInputStream(fstream); in= new InputStreamReader(di); BufferedReader
     * br = new BufferedReader(in); String strLine; StringBuilder buf = new
     * StringBuilder(); while ((strLine = br.readLine()) != null) {
     * buf.append(strLine); } fstream.close(); return buf; }
     */

    public ArrayList readWindowFinalReport(String testCaseBasePath) {

        ArrayList arrList = new ArrayList();
        File f1 = new File(testCaseBasePath + "/window/TestReport.xlsx");
        if (!f1.exists()) {
            return arrList;
        }
        Xls_Reader currentSuiteXls = new Xls_Reader(
                testCaseBasePath + "/window/TestReport.xlsx");

        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "SuiteName", 2));
        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "DateTime", 2));
        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "passed", 2));
        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "failed", 2));
        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "skip", 2));
        arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET,
                "TotalTestCase", 2));

        return arrList;
    }

    public boolean createNewXls(String testSuiteFileName, String basePath) {

        FileOutputStream fileOut = null;
        try {
            File f = new File(basePath + "/" + testSuiteFileName + ".xlsx");
            if (f.exists()) {
                return false;
            }

            fileOut = new FileOutputStream(
                    basePath + "/" + testSuiteFileName + ".xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet tCasesWorksheet = workbook
                    .createSheet(Constants.TEST_CASES_SHEET);

            XSSFRow row1 = tCasesWorksheet.createRow((short) 0);
            row1.createCell(0);
            row1.createCell(1);
            row1.createCell(2);
            row1.createCell(3);

            row1.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
            row1.getCell(1).setCellValue(Constants.COL_HEAD_DESCRIPTION);
            row1.getCell(2).setCellValue(Constants.COL_HEAD_RUNMODE);
            row1.getCell(3).setCellValue(Constants.COL_DATA_DRIVEN);

            XSSFSheet tStepWorksheet = workbook
                    .createSheet(Constants.TEST_STEPS);
            XSSFRow tStepWorksheetRow = tStepWorksheet.createRow((short) 0);

            tStepWorksheetRow.createCell(0);
            tStepWorksheetRow.createCell(1);
            tStepWorksheetRow.createCell(2);
            tStepWorksheetRow.createCell(3);
            tStepWorksheetRow.createCell(4);
            tStepWorksheetRow.createCell(5);
            tStepWorksheetRow.createCell(6);

            tStepWorksheetRow.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
            tStepWorksheetRow.getCell(1).setCellValue(Constants.COL_HEAD_TSID);
            tStepWorksheetRow.getCell(2)
                    .setCellValue(Constants.COL_HEAD_DESCRIPTION);
            tStepWorksheetRow.getCell(3)
                    .setCellValue(Constants.COL_HEAD_KEYWORD);
            tStepWorksheetRow.getCell(4)
                    .setCellValue(Constants.COL_HEAD_ELEMENT_ID);
            tStepWorksheetRow.getCell(5).setCellValue(Constants.COL_HEAD_DATA);
            tStepWorksheetRow.getCell(6).setCellValue(Constants.COL_HEAD_GO_TO);

            XSSFSheet tDataWorksheet = workbook
                    .createSheet(Constants.DATA_SHEET);
            XSSFRow tDataWorksheetRow = tDataWorksheet.createRow((short) 0);

            tDataWorksheetRow.createCell(0);
            tDataWorksheetRow.createCell(1);
            tDataWorksheetRow.createCell(2);

            tDataWorksheetRow.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
            tDataWorksheetRow.getCell(1).setCellValue("TCDI");
            tDataWorksheetRow.getCell(2)
                    .setCellValue(Constants.COL_HEAD_RUNMODE);

            XSSFSheet tCommonWorksheet = workbook
                    .createSheet(Constants.TEST_COMMON_SHEET);
            XSSFRow tCommonWorksheetRow = tCommonWorksheet.createRow((short) 0);

            tCommonWorksheetRow.createCell(0);
            tCommonWorksheetRow.createCell(1);
            tCommonWorksheetRow.createCell(2);
            tCommonWorksheetRow.createCell(3);
            tCommonWorksheetRow.createCell(4);
            tCommonWorksheetRow.createCell(5);

            tCommonWorksheetRow.getCell(0)
                    .setCellValue(Constants.COL_HEAD_TCID);
            tCommonWorksheetRow.getCell(1)
                    .setCellValue(Constants.COL_HEAD_TSID);
            tCommonWorksheetRow.getCell(2)
                    .setCellValue(Constants.COL_HEAD_DESCRIPTION);
            tCommonWorksheetRow.getCell(3)
                    .setCellValue(Constants.COL_HEAD_KEYWORD);
            tCommonWorksheetRow.getCell(4)
                    .setCellValue(Constants.COL_HEAD_ELEMENT_ID);
            tCommonWorksheetRow.getCell(5)
                    .setCellValue(Constants.COL_HEAD_DATA);

            workbook.write(fileOut);

        } catch (IOException e) {
            APP_LOG.info("Xls not created" + e);
            return false;
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    APP_LOG.error(
                            "Fuc: createNewXls Exception occured in creating new xls file"
                                    + e);
                }
            }
        }
        return true;
    }

    public ArrayList<ArrayList<String>> readTestStepsXls(
            String testCaseBasePath, String testSuit, String testCaseId,
            String fileExtention) {

        ArrayList<ArrayList<String>> testCaseName = new ArrayList<>();

        try {
            File file = new File(
                    testCaseBasePath + "/" + testSuit + fileExtention);

            if (!file.exists()) {
                return testCaseName;
            }

            Xlsx_Reader suiteXls = new Xlsx_Reader(
                    testCaseBasePath + "/" + testSuit + fileExtention);

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
                            Constants.COL_HEAD_DESCRIPTION, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_ELEMENT_ID, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_KEYWORD, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_DATA, suiteCount));
                    testCaseDetailList
                            .add(suiteXls.getCellData(Constants.TEST_STEPS,
                                    Constants.COL_HEAD_GO_TO, suiteCount));

                    testCaseName.add(testCaseDetailList);
                }

            }
        } catch (Exception e) {
            APP_LOG.info("File Not found" + e);
        }

        return testCaseName;
    }

    public static String[] splitMailAddresses(String address) {

        String[] addressSplited = {};

        if (!"".equals(address)) {
            addressSplited = address.split(";");
        }
        return addressSplited;
    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

}
