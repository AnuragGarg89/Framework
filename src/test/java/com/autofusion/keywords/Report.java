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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import com.autofusion.BaseClass;
import com.autofusion.InitializeWebDriver;
import com.autofusion.util.InitClass;
import com.relevantcodes.extentreports.ExtentReports;

public class Report extends BaseClass {
    public Report() {
        // TODO Auto-generated constructor stub
    }

    protected static String indexFilename;
    protected static String currentDir;
    protected static ArrayList<String> testDescAndResult = new ArrayList<>();
    protected static FileWriter fstream = null;
    protected static BufferedWriter out = null;
    protected static int testCaseCount = 1;

    public static synchronized void createHtmlReportHeader() {
        try {

            indexFilename = System.getProperty("user.dir") + "//" + projectPath
                    + "web" + File.separator + "htmlReports" + File.separator
                    + File.separator + BaseClass.reportStartTime
                    + File.separator + "SummaryReport_"
                    + BaseClass.reportStartTime + ".html";
            APP_LOG.debug("Indexfile created with name:- " + indexFilename);

            APP_LOG.debug("Current OS:- " + System.getProperty("os.name"));

            if (System.getProperty("os.name").contains("Mac")
                    || System.getProperty("os.name").contains("Linux")) {
                currentDir = indexFilename.substring(0,
                        indexFilename.lastIndexOf("//"));
                APP_LOG.debug("Current directory " + currentDir);
            } else {
                currentDir = indexFilename.substring(0,
                        indexFilename.lastIndexOf("\\"));
                APP_LOG.debug("Current directory " + currentDir);
            }
            new ExtentReports(indexFilename, true);
            APP_LOG.debug("Summarize Report folder created");
        } catch (Exception e) {
            APP_LOG.debug("Error while creating Report folder because of " + e);
        }
        // Clear Test Report folder
        new File(currentDir);

        InitializeWebDriver InitializeWebDriver = new InitializeWebDriver();
        try {
            // Create file

            fstream = new FileWriter(indexFilename);
            out = new BufferedWriter(fstream);

            String Browser = BaseClass.browser;
            out.newLine();

            out.write("<html>\n");
            out.write("<HEAD>\n");
            if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                out.write(
                        " <TITLE>Responsive Automation Test Results</TITLE>\n");
            } else {
                out.write(" <TITLE>UI Automation Test Results</TITLE>\n");
            }
            out.write("</HEAD>\n");

            out.write("<body>\n");
            if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                out.write(
                        "<h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Responsive Automation Test Results</u></b></FONT></h4>\n");
            } else {
                out.write(
                        "<h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> UI Automation Test Results</u></b></FONT></h4>\n");
            }

            out.write(
                    "<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Suite Details :</u></FONT></h4><br/>");
            out.write("<table  border=1 cellspacing=1 cellpadding=1 >\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date </b></FONT></td>\n");
            out.write(
                    "<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
                            + InitClass.now("dd.MMMMM.yyyy")
                            + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run StartTime</b></FONT></td>\n");
            String RUN_DATE = InitClass.now("dd.MMMMM.yyyy hh.mm.ss");
            out.write(
                    "<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
                            + RUN_DATE + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Sprint</b></FONT></td>\n");
            out.write(
                    "<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
                            + BaseClass.sprint + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Release</b></FONT></td>\n");
            out.write(
                    "<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
                            + BaseClass.release + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Browser</b></FONT></td>\n");

            if ("JenkinsSAUCELAB".equalsIgnoreCase(BaseClass.runOnMachine)) {
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + System.getenv("SELENIUM_BROWSER").toUpperCase(
                                        Locale.ENGLISH)
                                + "</b></FONT></td>\n");
            } else {
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + Browser + "</b></FONT></td>\n");
            }
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Runs On</b></FONT></td>\n");
            out.write(
                    "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.runOnMachine.toUpperCase(Locale.ENGLISH)
                            + "</b></FONT></td>\n");
            out.write("</tr>\n");

            if ("local".equalsIgnoreCase(BaseClass.runOnMachine)) {
                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>"
                                + Browser + " Version</b></FONT></td>\n");
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + InitializeWebDriver.getBrowserVersion()
                                + "</b></FONT></td>\n");
                out.write("</tr>\n");
            } else if ("SauceLab".equalsIgnoreCase(BaseClass.runOnMachine)) {

                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>"
                                + Browser + " Version</b></FONT></td>\n");
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>Latest</b></FONT></td>\n");
                out.write("</tr>\n");
            } else if ("JenkinsSauceLab"
                    .equalsIgnoreCase(BaseClass.runOnMachine)) {

                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>"
                                + System.getenv("SELENIUM_BROWSER")
                                        .toUpperCase()
                                + " Version</b></FONT></td>\n");
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + System.getenv("SELENIUM_VERSION")
                                + "</b></FONT></td>\n");
                out.write("</tr>\n");
            }

            if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Responsive Dimension</b></FONT></td>\n");
                out.write(
                        "<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + BaseClass.width + " x " + BaseClass.height
                                + "</b></FONT></td>\n");
                out.write("</tr>\n");
            }

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Login Application URL</b></FONT></td>\n");
            out.write(
                    "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.executionEnviroment
                                    .toUpperCase(Locale.ENGLISH)
                            + ":"
                            + BaseClass.configurationsXlsMap.get("ConsoleUrl")
                            + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Git Branch Name</b></FONT></td>\n");
            out.write(
                    "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.branchName + "</b></FONT></td>\n");
            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Course Subscribed</b></FONT></td>\n");
            out.write(
                    "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.course + "</b></FONT></td>\n");
            out.write("</tr>\n");

            if ("local".equalsIgnoreCase(BaseClass.runOnMachine)) {
                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Platform</b></FONT></td>\n");
                out.write(
                        "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + System.getProperty("os.name").toUpperCase()
                                + "</b></FONT></td>\n");
                out.write("</tr>\n");

            } else if ("SauceLab".equalsIgnoreCase(BaseClass.runOnMachine)) {
                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Platform</b></FONT></td>\n");
                out.write(
                        "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + BaseClass.platform + "</b></FONT></td>\n");
                out.write("</tr>\n");

            } else if ("JenkinsSauceLab"
                    .equalsIgnoreCase(BaseClass.runOnMachine)) {
                out.write("<tr>\n");
                out.write(
                        "<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Platform</b></FONT></td>\n");
                out.write(
                        "<td width=300 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                + System.getenv("SELENIUM_PLATFORM")
                                        .toUpperCase()
                                + "</b></FONT></td>\n");
                out.write("</tr>\n");
            }
            out.write("</table>\n");
        } catch (Exception e) {

            BaseClass.APP_LOG
                    .info("Error occured in createHtmlReportHeader : " + e);
        } finally {
            // Close the output stream
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {

                    APP_LOG.info(
                            "Exception occured while closing the file: " + e);
                }
            }
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {

                    APP_LOG.info(
                            "Exception occured while closing the file: " + e);
                }
            }
        }
    }

    public static void addReportSummary() {
        try {

            fstream = new FileWriter(indexFilename, true);
            out = new BufferedWriter(fstream);

            out.write(
                    "<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Summarize Report :</u></FONT></h4>\n");
            out.write(
                    "<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Number Of Test Cases</b></FONT></td>\n");

            out.write(
                    "<td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Passed</b></FONT></td>\n");
            out.write(
                    "<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Failed</b></FONT></td>\n");

            out.write("</tr>\n");

            out.write("<tr>\n");
            out.write(
                    "<td width=150 align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.getTotalCount() + "</b></FONT></td>\n");

            out.write(
                    "<td width=150 align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.getPassCount() + "</b></FONT></td>\n");

            out.write(
                    "<td width=150 align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                            + BaseClass.getFailCount() + "</b></FONT></td>\n");

            out.write("</tr>\n");
            out.write("</table>\n");
            out.write(
                    "<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> Detailed Report :</FONT></h4>");
            out.write(
                    "<table  border=1 cellspacing=1    cellpadding=1 width=100%>");
            out.write("<tr> ");
            out.write(
                    "<td align=center width=10%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Row#</b></FONT></td>");
            out.write(
                    "<td align=center width=50% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Description</b></FONT></td>");
            out.write(
                    "<td align=center width=15% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Result</b></FONT></td>");
            out.write("</tr>");

        } catch (Exception e) {
            BaseClass.APP_LOG.info("Error in addReportSummary : " + e);
        } finally {

            if (out != null) {

                try {
                    out.close();
                } catch (IOException e) {

                    APP_LOG.info(
                            "Exception occured while closing the file: " + e);
                }
            }
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {

                    APP_LOG.info(
                            "Exception occured while closing the file: " + e);
                }
            }
        }
    }

    public static void addPassTestCase(String desc, String res) {
        try {
            testDescAndResult.add(desc + "|" + res);
        } catch (UnsupportedOperationException | ClassCastException
                | NullPointerException | IllegalArgumentException
                | IllegalStateException e) {
            BaseClass.APP_LOG.info("Error in addPassTestCase : " + e);
        }
    }

    public static void addFailTestCase(String desc, String res) {
        try {

            testDescAndResult.add(desc + "|" + res);
        } catch (UnsupportedOperationException | ClassCastException
                | NullPointerException | IllegalArgumentException
                | IllegalStateException e) {
            BaseClass.APP_LOG.info("Error in addFailTestCase : " + e);
        }
    }

    public static synchronized void addTestCase() {
        try {
            fstream = new FileWriter(indexFilename, true);
            out = new BufferedWriter(fstream);
            String testDescription = "";
            String testResult = "";
            if (testDescAndResult != null) {
                Collections.sort(testDescAndResult);
                for (int i = 0; i < testDescAndResult.size(); i++) {
                    testDescription = testDescAndResult.get(i).split("\\|")[0]
                            .trim();
                    testResult = testDescAndResult.get(i).split("\\|")[1]
                            .trim();
                    out.write("<tr> ");

                    out.write(
                            "<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"
                                    + testCaseCount + "</b></FONT></td>");

                    if (testResult.equalsIgnoreCase("PASS")) {
                        out.write(
                                "<td align=left width=50%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
                                        + testDescription + "</b></FONT></td>");
                        out.write(
                                "<td align=center bgcolor=\"#66BB6A\" width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
                                        + testResult + "</b></FONT></td>");
                    } else if (testResult.equalsIgnoreCase("FAIL")) {
                        out.write(
                                "<td align=left width=50% bgcolor=\"#ffeb3b\"><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
                                        + testDescription + "</b></FONT></td>");
                        out.write(
                                "<td width=20% align=center bgcolor=\"#EF5350\"><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"
                                        + testResult + "</b></FONT></td>");
                    }
                    out.write("</tr> ");
                    testCaseCount++;
                }
            }
            APP_LOG.info("File is created!");
        } catch (IOException e) {
            APP_LOG.error("Func: addTestCase" + e);
            return;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    APP_LOG.info("Error: " + e);
                }
            }
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {

                    APP_LOG.info("Error: " + e);
                }
            }
        }
    }

    public static void endSuite() {
        FileWriter fstream = null;
        BufferedWriter out = null;
        APP_LOG.info("Func: endSuite to close table, body and html tags.");
        try {
            fstream = new FileWriter(indexFilename, true);
            out = new BufferedWriter(fstream);
            out.write("</table><br><br>");
            out.write("<b>Thanks</b><br>");
            out.write("<b><font size=2>QA Automation Team</font></b>");
            out.write("</body>");
            out.write("</html>");
        } catch (IOException e) {
            BaseClass.APP_LOG.info("Error: " + e);
        } finally {
            if (out != null) {

                try {
                    out.close();
                } catch (IOException e) {

                    APP_LOG.info("Error: " + e);
                }
            }

            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {

                    APP_LOG.info("Error: " + e);
                }
            }
        }
    }
}