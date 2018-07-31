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

package com.autofusion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.zeroturnaround.zip.ZipUtil;

import com.autofusion.analyzer.RetryListener;
import com.autofusion.capabilities.AndroidCaps;
import com.autofusion.capabilities.Caps;
import com.autofusion.capabilities.IosCaps;
import com.autofusion.commonpage.CommonPage;
import com.autofusion.constants.Constants;
import com.autofusion.keywords.Report;
import com.autofusion.util.ALM;
import com.autofusion.util.CommonUtil;
import com.autofusion.util.FileUtil;
import com.autofusion.util.InitClass;
import com.autofusion.util.ReadConfigXlsFiles;
import com.google.api.client.util.Base64;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.saucelabs.saucerest.SauceREST;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxy;

public abstract class BaseClass {

    protected WebDriver webDriver;
    protected AppiumDriver appiumDriver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<RemoteWebDriver> remotewebdriver = new ThreadLocal<>();
    private static ThreadLocal<String> runningComponentName = new ThreadLocal<>();
    protected static ThreadLocal<String> methodName = new ThreadLocal<>();
    private static HashSet<String> extentParentNodelist = new HashSet<String>();
    private static List<String> almIDs = new ArrayList<>();
    private static Map<String, ExtentTest> extentNodeAndPackageName = new LinkedHashMap<>();
    private static Map<String, ExtentTest> report = new LinkedHashMap<>();
    public String packageName = "";
    protected static String failureErrorMessageCollector = "";
    protected String actualDataPresentOnUi = "";
    protected static int totalCount = 0;
    protected static String harFile = "src/test/resources/essentials/web/networkLogs";

    /****************************
     * Configuration Parameter (should be pass from UI, testng.xml ,
     * config.property)
     **************************/
    protected static String browser = null;
    protected static String device = null;
    protected static String runOnMachine = null;
    protected static String executionEnviroment = null;
    protected static String projectPath = null;
    protected static String userRole = null;
    protected static String language = null;
    protected static String alm = null;
    protected static String extentReportFile = null;
    protected static int passCount = 0;
    protected static int failCount = 0;
    protected static String responsiveness = null;
    protected static String height = null;
    protected static String width = null;
    protected static String sauce_username = null;
    protected static String sauce_authkey = null;
    protected static String sprint = null;
    protected static String release = null;
    protected static String platform = null;
    protected static String version = null;
    protected static String branchName = null;
    protected static Integer pollingEverySecond = null;
    protected static int maxRetryCount = 0;
    protected static int maxTimeOutForElement = 0;
    protected static String couchBaseUserName = null;
    protected static String couchBasePassword = null;

    /*************************
     * Configuration Parameter.
     **************************/

    public WebDriverWait wait;
    protected static Logger APP_LOG = null;
    protected static Map<String, String> configurationsXlsMap = new HashMap<>();
    protected static int batchId;
    protected static ExtentReports reportObj = null;
    public ExtentTest reportTestObj = null;
    public List<WebElement> listOfElements = null;
    public List<WebElement> elements = null;
    public WebElement element = null;
    protected static int testCaseIdPom = 1;
    protected static int testStepIdPom = 1;
    protected static String reportStartTime = null;
    protected static String runningSuitName = null;
    protected String almResult = null;
    protected static String appiumPort = null;
    public ExtentTest extentParentNode;
    protected static String IOS_PLATFORM_VER = null;
    protected static String IOS_DEVICE_NAME = null;
    protected String deviceName = null;
    protected static String APPIUM_VERSION = null;
    protected static String PLATFORM_NAME = null;
    protected CommonUtil objCommonUtil = null;
    protected CommonPage objCommonPage = null;
    private ALM objAlm;
    protected InitializeWebDriver objInitializeWebDriver;
    protected Map<String, Object> sauceJob = new HashMap<String, Object>();
    // protected static String appUrl;
    protected static boolean isExecuted;
    protected boolean proceedOnFail;
    protected boolean isGTMTest = false;
    protected String jobID = null;
    protected String localDownloadPath = null;
    protected static String testCaseName = null;;
    protected static String fileName = null;
    protected static String capBrowserName = null;
    protected static String course = null;
    private static ThreadLocal<Integer> retryCounterThread = new ThreadLocal<>();
    protected static BrowserMobProxy proxy;
    protected static String downloadHAR = null;
    protected static String unpublishData = null;
    protected static int learnerCount;
    protected static String almUserName = null;
    protected static String almPassword = null;
    protected static String masteryLevel = null;
    protected static String diagnosisTest;

    static {
        init();
    }

    /**
     * c
     * 
     * @author mohit.gupta5
     * @date 15 Feb,17
     */
    @BeforeSuite(alwaysRun = true)
    public static synchronized void getSuitName(ITestContext ctx) {
        try {
            runningSuitName = ctx.getSuite().getName();
            FileUtil.deleteFile(harFile, ".har"); // Delete existing .HAR file
        } catch (Exception e) {
            APP_LOG.error("Exception occured", e);
        }

    }

    /**
     * @author sumit.bhardwaj
     * @date 16 March 2017 getDriver methods returns WebDriver for parallel
     *       Executions
     * @return--> Instance of WebDriver
     */
    public static WebDriver getDriver() {

        return driver.get();
    }

    /**
     * @author sumit.bhardwaj
     * @date 16 March 2017 setWebDriver methods sets WebDriver for parallel
     *       Executions
     */

    public static void setWebDriver(WebDriver d) {

        driver.set(d);

    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 getDriver methods returns Remote WebDriver for
     *       parallel Executions
     * @return--> Instance of WebDriver
     */

    public WebDriver getRemoteDriver() {
        return remotewebdriver.get();
    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 getDriver methods to set Remote WebDriver for
     *       parallel Executions
     * @return--> Instance of WebDriver
     */

    protected static void setRemoteWebDriver(RemoteWebDriver d) {
        remotewebdriver.set(d);
    }

    /**
     * @author mohit.gupta5
     * @date 15 Feb,17 Initialize webdriver or RemoteWebDriver driver using
     *       thread local Initialize extent report
     */
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        try {
            methodName.set(getClass().getName());
            initializeReportObject();
            this.webDriver = this.getWebDriver();
            objInitializeWebDriver = new InitializeWebDriver();
            testCaseName = this.objInitializeWebDriver.getTestCaseName();
            APP_LOG.info("Web driver sucessfully initialized");
            if ("Local".equalsIgnoreCase(runOnMachine)) {
                setWebDriver(this.webDriver);
                getDriver();
            } else {
                setRemoteWebDriver((RemoteWebDriver) this.webDriver);
                this.getRemoteDriver();
                if ("JenkinsSauceLab".equalsIgnoreCase(runOnMachine)
                        || "SauceLab".equalsIgnoreCase(runOnMachine)) {
                    this.objInitializeWebDriver.sauceLabSessionID();
                    this.objInitializeWebDriver.printSessionId();
                    jobID = ((RemoteWebDriver) this.getRemoteDriver())
                            .getSessionId().toString();

                }
            }
        } catch (NullPointerException e) {
            APP_LOG.error(
                    "Exception occured while setting up webdriver in setup method in base class "
                            + e);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 Return webdriver on the basis of browser and run on
     *       machine
     * @return--> Instance of WebDriver
     */

    public WebDriver returnDriver() {
        try {
            if ("android".equalsIgnoreCase(browser)
                    || "ios".equalsIgnoreCase(browser)) {
                this.webDriver = this.getMobileDriver();
                return this.webDriver;
            } else {
                if ("local".equalsIgnoreCase(runOnMachine)) {
                    this.webDriver = getDriver();
                    return this.webDriver;
                } else if ("Remote".equalsIgnoreCase(runOnMachine)
                        || "SauceLab".equalsIgnoreCase(runOnMachine)
                        || "JenkinsSauceLab".equalsIgnoreCase(runOnMachine)) {
                    this.webDriver = this.getRemoteDriver();
                    return this.webDriver;
                }

            }
        } catch (NullPointerException e) {
            APP_LOG.error("Exception occured in return driver method " + e);
        }
        return this.webDriver;
    }

    // Mandatory method need to be called from every test cases.
    public synchronized void startReport(String almId, String testDesc) {
        WebDriver d = null;
        try {
            if (this.webDriver == null) {
                if ("Local".equalsIgnoreCase(runOnMachine)) {
                    d = this.getWebDriver();
                    setWebDriver(d);
                    getDriver();
                } else {
                    APP_LOG.info("Web driver sucessfully initialized");
                    d = this.getWebDriver();
                    setRemoteWebDriver((RemoteWebDriver) d);
                    getDriver();
                    if ("JenkinsSauceLab".equalsIgnoreCase(runOnMachine)
                            || "SauceLab".equalsIgnoreCase(runOnMachine)) {
                        this.objInitializeWebDriver = new InitializeWebDriver();
                        this.objInitializeWebDriver.sauceLabSessionID();
                        this.objInitializeWebDriver.printSessionId();
                        jobID = ((RemoteWebDriver) this.getRemoteDriver())
                                .getSessionId().toString();
                    }
                }
            }
            returnDriver().manage().deleteAllCookies();
            if (testCaseName.contains("GTM")) {
                returnDriver().get(
                        "https://www.googletagmanager.com/start_preview/gtm?uiv2&id=GTM-W5BMGF7&gtm_auth=8ZLzO9Ia3PxV4HVd9LltIg&gtm_preview=env-7&gtm_debug=x&url=https://loginui-qa.gl-poc.com/#/");
            }
            this.returnDriver().get(configurationsXlsMap.get("ConsoleUrl"));
            APP_LOG.info("URL sucessfully launched in browser");
            this.addParentReport();
            this.reportTestObj = addReportTestObj(
                    "GLP_" + almId + " --> " + testDesc);
            almIDs.add(almId);
        } catch (Exception e) {
            APP_LOG.error("Error while starting report, because of:- " + e);
        }
    }

    // This is a tear down method for all the test cases.
    @AfterClass(alwaysRun = true)
    public synchronized void tearDown() {
        try {
            totalCount++;
            CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
            try {
                if ("Pass".equalsIgnoreCase(
                        this.reportTestObj.getTest().getStatus().toString())) {
                    Report.addPassTestCase(
                            this.reportTestObj.getTest().getName().toString(),
                            this.reportTestObj.getTest().getStatus().toString()
                                    .toUpperCase(Locale.ENGLISH));
                    this.almResult = "Passed";
                    if ("saucelab".equalsIgnoreCase(runOnMachine)
                            || "JenkinsSauceLab"
                                    .equalsIgnoreCase(runOnMachine)) {
                        SauceREST client = new SauceREST(sauce_username,
                                sauce_authkey);
                        client.jobPassed(jobID);
                        client.updateJobInfo(jobID, this.sauceJob);
                        try {
                            if ("True".equalsIgnoreCase(downloadHAR)) {
                                if (getmethodName().toUpperCase()
                                        .contains("LOADUNLOAD")) {
                                    objCommonUtil.getTrackingID();
                                }
                            }
                        } catch (Exception e) {
                            APP_LOG.error(
                                    "Exception occured while getting tracking id:- "
                                            + e);
                        }

                    }
                }
            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured in updating Pass result:- " + e);
            }

            try {
                if ("Fail".equalsIgnoreCase(
                        this.reportTestObj.getTest().getStatus().toString())) {
                    Report.addFailTestCase(
                            this.reportTestObj.getTest().getName().toString(),
                            this.reportTestObj.getTest().getStatus().toString()
                                    .toUpperCase(Locale.ENGLISH));
                    Reporter.getCurrentTestResult()
                            .setStatus(ITestResult.FAILURE);
                    this.almResult = "Failed";
                    if ("Saucelab".equalsIgnoreCase(runOnMachine)
                            || "JenkinsSauceLab"
                                    .equalsIgnoreCase(runOnMachine)) {
                        SauceREST client = new SauceREST(sauce_username,
                                sauce_authkey);
                        client.jobFailed(jobID);
                        client.updateJobInfo(jobID, this.sauceJob);
                        try {
                            if ("True".equalsIgnoreCase(downloadHAR)) {
                                objCommonUtil.downloadHarFileSauceLab();
                            }
                        } catch (Exception e) {
                            APP_LOG.error(
                                    "Error while downloding HAR file:- " + e);
                        }
                    }
                }

            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured in updating Fail result:- " + e);
            }
            try {
                if ("true".equalsIgnoreCase(alm)) {
                    this.createAlmClassObject().updateAlmResults(
                            this.getTestCaseId(), this.almResult);
                }
            } catch (Exception e) {
                APP_LOG.error("Exception occured in creating ALM object " + e);
            }
            this.endReport();
        } catch (

        Exception e) {
            APP_LOG.error("Exception occured in after class:- " + e);
        } finally {
            if ("android".equalsIgnoreCase(browser)
                    || "ios".equalsIgnoreCase(browser)) {
                this.appiumDriver.quit();
                APP_LOG.info("appium driver has been sucessfully quit");
            } else {
                /*
                 * this.webDriver.quit();
                 * APP_LOG.info("Web driver has been sucessfully quit");
                 * this.webDriver = null;
                 */
            }
        }

    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            Report.createHtmlReportHeader();
            Report.addReportSummary();
            Report.addTestCase();
            Report.endSuite();
            String destReport = projectPath
                    + "web//htmlReports/SummaryLatestReport";
            // FileUtil.renameExtentReport(browser);
            APP_LOG.info("Report copy to Jenkins Folder");
            FileUtil.deleteFile(destReport);
            File srcFolderReports = new File(
                    projectPath + "web//htmlReports/" + reportStartTime);
            File destFolderReports = new File(
                    projectPath + "web//htmlReports/SummaryLatestReport");
            FileUtil.copyFolder(srcFolderReports, destFolderReports);
            FileUtil.renameSummaryReport();
            APP_LOG.info("Report copy to Summary Folder");
            if ("Yes".equalsIgnoreCase(alm)) {
                APP_LOG.info("Test Cases executed by our automation suite : "
                        + almIDs.size());
                Collections.sort(almIDs);
                APP_LOG.info(almIDs);
                List<String> testCaseIDs = new ArrayList<>();
                testCaseIDs = this.createAlmClassObject().getALMCount();
                APP_LOG.info("Test Cases marked as yes in ALM : "
                        + testCaseIDs.size());
                APP_LOG.info(testCaseIDs);
            }
            try {
                ZipUtil.pack(
                        new File(
                                "src/test/resources/essentials/web/networkLogs/"),
                        new File(
                                "src/test/resources/essentials/web/networkLogs/netwroklogs.zip"));
                Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
            } catch (Exception e) {
                APP_LOG.error("Exception occured while zip the HAR file :" + e);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in afterSuite method :" + e);
        }

    }

    /**
     * @author sumit.bhardwaj
     * @date 03 April,17 addParentReport method adds the instance of
     *       ExtentParent Node and current package name in
     *       extentNodeAndPackageName map.
     */

    public synchronized void addParentReport() {
        try {
            this.packageName = getClass().getPackage().getName();
            this.packageName = this.packageName
                    .split("\\.")[Constants.INTEGER_TWO]
                            .toUpperCase(Locale.ENGLISH);
            this.packageName = this.packageName.replaceAll("_", ": ");
            if (extentParentNodelist.contains(this.packageName)) {
                extentNodeAndPackageName.get(this.packageName);
                APP_LOG.info(extentParentNodelist);

            } else {
                extentParentNodelist.add(this.packageName);
                this.extentParentNode = reportObj.startTest(this.packageName)
                        .assignCategory("Regression");
                extentNodeAndPackageName.put(this.packageName,
                        this.extentParentNode);
                APP_LOG.info(extentParentNodelist);
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Func: addParentNode Error while adding Parent node into report beacuse of "
                            + e);

        }
    }

    /**
     * @author sumit.bhardwaj
     * @date 03 April,17 endReport method to write all the test logs to the
     *       report file.
     */

    public synchronized void endReport() {
        try {
            this.packageName = getClass().getPackage().getName();
            this.packageName = this.packageName
                    .split("\\.")[Constants.INTEGER_TWO]
                            .toUpperCase(Locale.ENGLISH);
            this.packageName = this.packageName.replaceAll("_", ": ");
            if (this.reportTestObj != null) {
                extentNodeAndPackageName.get(this.packageName)
                        .appendChild(this.reportTestObj);
            } else {
                extentNodeAndPackageName.get(this.packageName)
                        .appendChild(this.reportTestObj);
            }
            reportObj.endTest(extentNodeAndPackageName.get(this.packageName));
            if (this.reportTestObj != null) {
                if ("pass".equalsIgnoreCase(
                        this.reportTestObj.getTest().getStatus().toString())) {
                    passCount++;
                }
            }
            APP_LOG.info("Pass count:- " + passCount);

            if (this.reportTestObj != null) {

                if ("fail".equalsIgnoreCase(
                        this.reportTestObj.getTest().getStatus().toString())
                        || "UNKNOWN".equalsIgnoreCase(this.reportTestObj
                                .getTest().getStatus().toString())) {
                    failCount++;
                }

            }

            APP_LOG.info("Fail count:- " + failCount);
            reportObj.flush();
        } catch (NullPointerException e) {
            APP_LOG.error("Exception occured in endReport method : " + e);
        }
    }

    /**
     * Initialize config.property available under resource folder
     * 
     */
    private static synchronized void init() {
        try {
            if (projectPath == null || "".equals(projectPath)) {
                // As java
                projectPath = PropertyManager.getInstance()
                        .getValueForKey("projectPath").trim();
                browser = PropertyManager.getInstance()
                        .valueFromConfig("browser").trim();
                device = PropertyManager.getInstance().valueFromConfig("device")
                        .trim();
                executionEnviroment = PropertyManager.getInstance()
                        .valueFromConfig("execution_envoirment").trim();
                alm = PropertyManager.getInstance().valueFromConfig("alm");
                runOnMachine = PropertyManager.getInstance()
                        .valueFromConfig("runOnMachine").trim();
                language = PropertyManager.getInstance()
                        .valueFromConfig("language").trim();
                responsiveness = PropertyManager.getInstance()
                        .valueFromConfig("responsiveness").trim();
                height = PropertyManager.getInstance().valueFromConfig("height")
                        .trim();
                width = PropertyManager.getInstance().valueFromConfig("width")
                        .trim();
                maxTimeOutForElement = Integer.valueOf(PropertyManager
                        .getInstance().valueFromConfig("maxWaitForElement"));
                pollingEverySecond = Integer.valueOf(PropertyManager
                        .getInstance().valueFromConfig("pollingEverySecond"));
                sauce_username = PropertyManager.getInstance()
                        .valueFromConfig("sauce_username").trim();
                sauce_authkey = PropertyManager.getInstance()
                        .valueFromConfig("sauce_authkey").trim();
                sprint = PropertyManager.getInstance().valueFromConfig("sprint")
                        .trim();
                release = PropertyManager.getInstance()
                        .valueFromConfig("release").trim();
                platform = PropertyManager.getInstance()
                        .valueFromConfig("platform").trim();
                branchName = PropertyManager.getInstance()
                        .valueFromConfig("branchName").trim();
                course = PropertyManager.getInstance().valueFromConfig("course")
                        .trim();
                maxRetryCount = Integer.valueOf(PropertyManager.getInstance()
                        .valueFromConfig("maxRetryCount"));
                downloadHAR = PropertyManager.getInstance()
                        .valueFromConfig("downloadHAR");
                couchBaseUserName = PropertyManager.getInstance()
                        .valueFromConfig("couchBaseUserName");
                couchBasePassword = PropertyManager.getInstance()
                        .valueFromConfig("couchBasePassword");
                unpublishData = PropertyManager.getInstance()
                        .valueFromConfig("unpublishData");
                learnerCount = Integer.parseInt(PropertyManager.getInstance()
                        .valueFromConfig("learnerCount").trim());
                almUserName = PropertyManager.getInstance()
                        .valueFromConfig("almUserName").trim();
                almPassword = PropertyManager.getInstance()
                        .valueFromConfig("almPassword").trim();
                masteryLevel = PropertyManager.getInstance()
                        .valueFromConfig("masteryLevel").trim();
                diagnosisTest = PropertyManager.getInstance()
                        .valueFromConfig("diagnosisTest").trim();

            }

            if (null != APP_LOG) {
            } else {
                APP_LOG = InitClass.initializeLogger(projectPath, device);
                PropertyConfigurator.configure(System.getProperty("user.dir")
                        + "/src/test/resources/configFiles/propertiesFile/log4j.properties");
            }

            if (configurationsXlsMap.isEmpty()) {
                readEnvironmentConfigurationFile();
            }
            InitClass.initializeExternalConfigFile(projectPath);
        } catch (NullPointerException e) {

            if (APP_LOG == null) {
                APP_LOG = InitClass.initializeLogger(projectPath, device);
                PropertyConfigurator.configure(System.getProperty("user.dir")
                        + "/src/test/resources/configFiles/propertiesFile/log4j.properties");
                APP_LOG.error(
                        "Error during reading of config.property file, this is expected if user is "
                                + "running it from testng.xml and passing params from xml it self",
                        e);
            }
        }
    }

    /**
     * Initialize Extent report objects.
     */
    public static synchronized ExtentReports initializeReportObject() {

        try {
            if (reportObj == null) {
                String str = browser;
                String cap = str.substring(0, 1).toUpperCase(Locale.ENGLISH)
                        + str.substring(1);
                reportStartTime = InitClass.now("dd.MMMMM.yyyy hh.mm.ss");
                if ("JenkinsSauceLab".equalsIgnoreCase(runOnMachine)) {
                    extentReportFile = System.getProperty("user.dir")
                            + "//src//test//resources//jenkinsLatestReport//"
                            + "ExtentReport.html";
                } else if ("true".equalsIgnoreCase(responsiveness)) {
                    extentReportFile = projectPath + "//" + device
                            + "//report//Responsive//" + browser + "//"
                            + reportStartTime + "//" + cap + "_ExecutionReport_"
                            + reportStartTime + ".html";
                } else {
                    extentReportFile = projectPath + "//" + device
                            + "//report//Browser//" + browser + "//"
                            + reportStartTime + "//" + cap + "_ExecutionReport_"
                            + reportStartTime + ".html";
                }
                reportObj = new ExtentReports(extentReportFile, true);

                File f = null;
                try {
                    if ("true".equalsIgnoreCase(responsiveness)) {
                        f = new File(
                                projectPath + "//extent-config-responsive.xml");
                    } else {
                        f = new File(projectPath + "//extent-config.xml");
                    }

                } catch (NullPointerException e) {
                    APP_LOG.error("Extent report configuration " + e);
                }
                reportObj.loadConfig(f);
                reportObj.addSystemInfo("Run On",
                        runOnMachine.toUpperCase(Locale.ENGLISH));
                reportObj.addSystemInfo("Browser",
                        browser.toUpperCase(Locale.ENGLISH));
                reportObj.addSystemInfo("Language",
                        language.toUpperCase(Locale.ENGLISH));
                reportObj.addSystemInfo("Environment",
                        executionEnviroment.toUpperCase(Locale.ENGLISH));
                reportObj.addSystemInfo("GLP URL",
                        configurationsXlsMap.get("DomainURL"));
                reportObj.addSystemInfo("Product Console URL",
                        configurationsXlsMap.get("ConsoleUrl"));
                APP_LOG.info("reportObj sucessfully initialized");
            }
            return reportObj;
        } catch (Exception e) {
            APP_LOG.info(
                    "Error while Initilizing Report Object beacause of:- " + e);
        }
        return reportObj;
    }

    /**
     * @return webdriver instance
     */
    public WebDriver getWebDriver() {
        try {
            String deviceToRun = device;

            if (this.webDriver == null) {
                if (configurationsXlsMap.isEmpty()) {
                    readEnvironmentConfigurationFile();
                }

                if ("WEB".equalsIgnoreCase(deviceToRun)) {
                    this.objInitializeWebDriver = new InitializeWebDriver();
                    this.webDriver = this.objInitializeWebDriver.getWebDriver(
                            getClass().getName(), browser, this.webDriver,
                            projectPath, APP_LOG);
                }

            } else {
                APP_LOG.debug("No device define in config.property");
                return null;
            }
        } catch (

        Exception e) {
            APP_LOG.error(
                    "Exception occured in  getWebDriver method in base class",
                    e);
        }
        return this.webDriver;
    }

    public synchronized AppiumDriver getMobileDriver() {

        AndroidCaps objAndroidCaps = new AndroidCaps();
        if (this.appiumDriver == null) {
            Caps deviceCaps = null;
            if ("Android".equalsIgnoreCase(device)) {
                deviceCaps = new AndroidCaps();
            } else if ("iOS".equalsIgnoreCase(device)) {
                deviceCaps = new IosCaps();
            }
            try {

                appiumPort = InitClass.loadExternalConfig()
                        .getProperty("APPIUM_PORT");
                runOnMachine = InitClass.loadExternalConfig()
                        .getProperty("RunOnMachine");
                this.deviceName = InitClass.loadExternalConfig()
                        .getProperty("DEVICE_NAME");

                if (configurationsXlsMap.isEmpty()) {
                    readEnvironmentConfigurationFile();
                }
                if (!"Android".equalsIgnoreCase(objAndroidCaps.browserName)) {
                    if (deviceCaps != null) {
                        this.appiumDriver = new AndroidDriver(
                                new URL("http://" + runOnMachine + ":"
                                        + appiumPort + "/wd/hub"),
                                deviceCaps.getCapabilities(APP_LOG));
                    }
                    if (!"".equals(configurationsXlsMap
                            .get(Constants.CONFIG_COL_DOMAIN_NAME))) {

                        if (getClass().getPackage().getName()
                                .contains("productApplication")) {

                            this.appiumDriver
                                    .get(configurationsXlsMap.get("DomainURL"));
                        } else {

                            this.appiumDriver.get(configurationsXlsMap
                                    .get("ProductBuilderUrl"));
                        }
                    } else {
                        APP_LOG.debug("Domain URL is not define");
                        return null;
                    }
                    APP_LOG.info("value :" + this.appiumDriver);
                } else {
                    if (deviceCaps != null) {
                        this.appiumDriver = new AndroidDriver(
                                new URL("http://" + runOnMachine + ":"
                                        + appiumPort + "/wd/hub"),
                                deviceCaps.getCapabilities(APP_LOG));
                    }
                }

            } catch (Exception e) {
                APP_LOG.error("Base class appium- " + e);
            }

        }
        if (this.appiumDriver != null) {
            this.appiumDriver.manage().timeouts()
                    .implicitlyWait(Constants.INTEGER_SIXTY, TimeUnit.SECONDS);
        }

        return this.appiumDriver;
    }

    /**
     * @author mukul.sehra
     * @date 27 April, 2017
     * @description Extract the test case Id from the class name.
     */
    public String getTestCaseId() {
        Pattern regex = Pattern.compile("(\\d{6,7})");
        String testCaseId = "";
        try {
            Matcher matcherString = regex.matcher(getClass().getSimpleName());
            while (matcherString.find()) {
                testCaseId = matcherString.group();
            }
            return testCaseId;
        } catch (IllegalArgumentException ex) {
            APP_LOG.error("Error in generating test case id" + ex);
            return null;
        }

    }

    public static synchronized void readEnvironmentConfigurationFile() {
        APP_LOG.debug("Reading Environment configuration file");
        ReadConfigXlsFiles objConfigXlsFiles = new ReadConfigXlsFiles();
        configurationsXlsMap = objConfigXlsFiles.readConfigurationsXls(
                projectPath, executionEnviroment.trim(), APP_LOG);
    }

    public String captureScreenshot() {
        WebDriver webDriverScrnShot = this.returnDriver();
        File scrFile = ((TakesScreenshot) webDriverScrnShot)
                .getScreenshotAs(OutputType.FILE);
        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);

            int l = (int) scrFile.length();
            byte[] bytes = new byte[l];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));

        } catch (FileNotFoundException e) {
            APP_LOG.error("Error in captureScreenshot method" + e);
        } catch (IOException e) {
            APP_LOG.error("Error in captureScreenshot" + e);
        } finally {
            if (fileInputStreamReader != null) {
                try {
                    fileInputStreamReader.close();
                } catch (IOException e) {
                    APP_LOG.error(
                            "Func: captureScreenshot Exception occured while capturing screenshot"
                                    + e);
                }
            }
        }

        return "data:image/jpeg;base64," + encodedBase64;
    }

    /**
     * @description Log result in report and also update ALM and Sauce Lab
     *              report.
     * @param testResult
     *            Result of test-step
     * @param stepDescription
     *            Description of test steps
     * @param reportTestObj
     *            Current extend report object
     */
    public synchronized void logResultInReport(String testResult,
            String stepDescription, ExtentTest reportTestObj) {
        boolean bFlag = false;
        try {

            if (testResult.contains(Constants.PASS)) {
                reportTestObj.log(LogStatus.PASS, stepDescription, testResult);
                APP_LOG.info("Log result in Extent report:" + testResult);
                Assert.assertTrue(true);
            } else if (testResult.contains(Constants.FAIL)) {
                int countOfTry = getRetryCount();
                if (countOfTry == 0 && maxRetryCount > 0
                        && RetryListener.instanceCounter > 0) {
                    reportTestObj.log(LogStatus.INFO, stepDescription,
                            testResult);
                    APP_LOG.info("Retry Number for Info status: " + countOfTry);
                    String filePath = this.captureScreenshot();
                    reportTestObj.log(LogStatus.INFO, "Failure Screenshot",
                            "Snapshot below having URL: '"
                                    + returnDriver().getCurrentUrl()
                                    + reportTestObj.addScreenCapture(filePath));
                } else if ((countOfTry == 1 || maxRetryCount >= 0)) {
                    APP_LOG.info(
                            "Retry Number for failed Status: " + countOfTry);
                    reportTestObj.log(LogStatus.FAIL, stepDescription,
                            testResult);
                    String filePath = this.captureScreenshot();
                    reportTestObj.log(LogStatus.FAIL, "Failure Screenshot",
                            "Snapshot below having URL: '"
                                    + returnDriver().getCurrentUrl()
                                    + reportTestObj.addScreenCapture(filePath));
                    bFlag = true;

                }
                flushRetry(bFlag);
                try {
                    Assert.assertTrue(false);
                } catch (AssertionError e) {
                    APP_LOG.debug("Assert " + e);
                    throw e;
                }
            }
        } catch (Exception e) {

            APP_LOG.error("Exception in logResultInReport method" + e);
            bFlag = true;
            flushRetry(bFlag);
            reportTestObj.log(LogStatus.FAIL, "Unexpected Error occurred",
                    "Error while logging which is: '" + e.getMessage() + "'");
            try {
                Assert.assertTrue(false);
            } catch (AssertionError f) {
                APP_LOG.debug("Assert " + f);
                throw f;
            }

        }
    }

    public static synchronized void
           collectFailureMessage(String failureMessage) {
        failureErrorMessageCollector = failureMessage;
    }

    public void collectActualUiData(String actualdData) {
        this.actualDataPresentOnUi = actualdData;
    }

    /**
     * @returnthe runningComponentName.
     */
    public static String getRunningComponentName() {
        return runningComponentName.get();
    }

    /**
     * @param runningComponentName
     *            The runningComponentName to set.
     */
    public static void setRunningComponentName(String value) {
        runningComponentName.set(value);
    }

    /**
     * @return the methodName.
     */
    public static String getmethodName() {
        return methodName.get();
    }

    /**
     * @param methodName
     *            the methodName to set.
     */
    public static void setmethodName(String value) {
        methodName.set(value);
    }

    public static int getPassCount() {
        return passCount;
    }

    public static int getFailCount() {
        return failCount;
    }

    public static int getTotalCount() {
        return totalCount;
    }

    public ALM createAlmClassObject() {

        if (this.objAlm == null) {
            this.objAlm = new ALM();
        }
        return this.objAlm;
    }

    /**
     * @author sumit.bhardwaj
     * @description addReportTestObj method add report test object in report if
     *              not added earlier
     * @param testCase
     *            --> name of test case
     * @return --> Return Extenttest Object
     */
    public synchronized ExtentTest addReportTestObj(String testCase) {

        ExtentTest rep = null;
        if (report.containsKey(testCase)) {
            return report.get(testCase);
        } else {
            rep = reportObj.startTest(testCase);
            report.put(testCase, rep);
            return rep;
        }
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void checkBrowserChange() {
        Annotation[] annotation = this.getClass().getDeclaredMethods()[0]
                .getAnnotations();
        String newBrowser = browser;

        for (Annotation a : annotation) {
            if (a.toString().contains("changeBrowser")) {
                newBrowser = a.toString().split("value")[1]
                        .replaceAll("\\)", "").replace("=", "");
                System.out.println("Changing Browser to " + newBrowser);
            }
        }

        if (!newBrowser.equalsIgnoreCase(browser)) {

            String url = this.webDriver.getCurrentUrl();
            this.webDriver.quit();

            browser = newBrowser;

            this.webDriver = this.objInitializeWebDriver.getWebDriver(
                    getClass().getName(), browser, this.webDriver, projectPath,
                    APP_LOG);

            this.webDriver.get(url);

            APP_LOG.info("Web driver sucessfully initialized Again to "
                    + newBrowser);

            if ("Local".equalsIgnoreCase(runOnMachine)) {
                setWebDriver(this.webDriver);
                getDriver();
            } else {
                setRemoteWebDriver((RemoteWebDriver) this.webDriver);
                this.getRemoteDriver();
                if ("JenkinsSauceLab".equalsIgnoreCase(runOnMachine)
                        || "SauceLab".equalsIgnoreCase(runOnMachine)) {
                    this.objInitializeWebDriver = new InitializeWebDriver();
                    this.objInitializeWebDriver.sauceLabSessionID();
                    this.objInitializeWebDriver.printSessionId();
                }
            }

        }
    }

    /**
     * @author nitish.jaiswal
     * @description getting retry count for synchronized multiple thread
     *              execution support
     * 
     */
    public static int getRetryCount() {

        if (ObjectUtils.isEmpty(retryCounterThread.get())) {
            setRetryCount(-1);
            APP_LOG.info("Set Retry Count");
        }

        return retryCounterThread.get() + 1;// .incrementAndGet();
    }

    /**
     * @author nitish.jaiswal
     * @description setting retry count for synchronized multiple thread
     *              execution support
     * 
     */
    public static void setRetryCount(int value) {
        // retryCounterThread.get().set(value);
        retryCounterThread.set(value);
    }

    /**
     * @author nitish.jaiswal
     * @description flushing the retry count for synchronized multiple thread
     *              execution support
     * 
     */
    public void flushRetry(boolean bFlag) {
        if (bFlag) {
            setRetryCount(-1);
        } else {
            setRetryCount(0);
        }
    }
}
