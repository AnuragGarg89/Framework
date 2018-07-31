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
 * Create instance for webdriver as per firefox, chrome and internet explorer.
 * Configure remote webdriver to run on Jenkins and SauceLab machine
 */

package com.autofusion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class InitializeWebDriver extends BaseClass implements
        SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    public InitializeWebDriver() {

    }

    private DesiredCapabilities cap = new DesiredCapabilities();
    public final String sauceLabUrl = "http://" + sauce_username + ":"
            + sauce_authkey + "@ondemand.saucelabs.com:80/wd/hub";
    public final String SAUCE_JENKINSUSERNAME = System.getenv("SAUCE_USERNAME");
    public final String SAUCE_JENKINSACCESS_KEY = System
            .getenv("SAUCE_ACCESS_KEY");
    public static final String currentTimeStamp = new SimpleDateFormat(
            "yyyy.MM.dd.HH.mm.ss").format(new Date());
    public static final String buildTag = "TestGLP-".concat(course).concat("-")
            .concat(executionEnviroment).concat("-").concat(currentTimeStamp);
    protected static String browserVersion = "";
    /**
     * ThreadLocal variable which contains the Sauce Job Id
     */
    private static ThreadLocal<String> sessionId = new ThreadLocal<>();

    public static void pressShortcut(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("m")
                .keyUp(Keys.SHIFT).keyUp(Keys.CONTROL).perform();
    }

    public static void setViewSize(FirefoxProfile profile, Integer height,
            Integer width) {
        String responsiveMode = "[{\"width\": " + width + ", \"key\": \""
                + width + "x" + height + "\", \"height\": " + height + "}]";
        profile.setPreference("devtools.responsiveUI.presets", responsiveMode);
    }

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the
     * supplied user name/access key. To use the authentication supplied by
     * environment variables or from an external file, use the no-arg
     * {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
            this.SAUCE_JENKINSUSERNAME, this.SAUCE_JENKINSACCESS_KEY);
    private static String version;

    /**
     * @param className
     * @param browser
     * @param driver
     * @param projectPath.
     * @param appLogs
     * @return driver
     */
    public WebDriver getWebDriver(String className, String browser,
            WebDriver driver, String projectPath, Logger appLogs) {
        if ("local".equalsIgnoreCase(BaseClass.runOnMachine)) {
            if ("Firefox".equalsIgnoreCase(browser)) {
                if (driver instanceof FirefoxDriver) {
                    return driver;
                }
                if (System.getProperty("os.name").contains("Mac")) {
                    System.setProperty("webdriver.gecko.driver",
                            "src/test/resources/drivers/Projects/geckodriver");
                    System.setProperty(
                            FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,
                            "true");
                    System.setProperty(
                            FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                            "logs.txt");
                } else {
                    System.setProperty("webdriver.gecko.driver",
                            "src/test/resources/drivers/Projects/geckodriver.exe");
                    System.setProperty(
                            FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,
                            "true");
                    // System.//setProperty(
                    // FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                    // "C:\\temp\\logs.txt");
                }

                FirefoxProfile profile = new FirefoxProfile();
                if (language.contains("es_ES")) {
                    profile.setPreference("intl.accept_languages", "es");

                } else if (language.contains("ar_SA")) {
                    profile.setPreference("intl.accept_languages", "ar");
                }
                FirefoxOptions option = new FirefoxOptions()
                        .setProfile(new FirefoxProfile());
                option.getProfile();
                option.setAcceptInsecureCerts(true);
                option.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                        UnexpectedAlertBehaviour.ACCEPT);
                option.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                    setViewSize(profile, Integer.valueOf(BaseClass.height),
                            Integer.valueOf(BaseClass.width));
                    option.setCapability(FirefoxDriver.PROFILE, profile);
                    driver = new FirefoxDriver(option);
                    setBrowserVersion(this.getFirefoxBrowserVersion(driver));
                    pressShortcut(driver);

                } else {
                    driver = new FirefoxDriver(option);
                    setBrowserVersion(this.getFirefoxBrowserVersion(driver));
                    capBrowserName = option.getBrowserName();
                }
            } else if ("Chrome".equalsIgnoreCase(browser)) {
                if (driver instanceof ChromeDriver) {
                    return driver;
                }
                ChromeOptions chromeOptions = new ChromeOptions();
                if (System.getProperty("os.name").contains("Mac")) {
                    System.setProperty("webdriver.chrome.driver",
                            "src/test/resources/drivers/Projects/chromedriver");
                } else {
                    System.setProperty("webdriver.chrome.driver",
                            "src/test/resources/drivers/Projects/chromedriver.exe");
                }

                if (language.contains("es_ES")) {
                    chromeOptions.addArguments("--lang=es-419");
                } else if (language.contains("ar_SA")) {
                    chromeOptions.addArguments("--lang=ar");
                }
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("test-type=browser");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--incognito");
                chromeOptions.setCapability(
                        CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
                        true);
                chromeOptions.setCapability(
                        CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                        UnexpectedAlertBehaviour.ACCEPT);
                chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
                        true);
                
                // If we need to verify load unload events using BrowserMobProxy
                if (getTestCaseName().contains("LoadUnload")) {
                    proxy = new BrowserMobProxyServer();
                    proxy.setTrustAllServers(true);
                    proxy.start(80);
                    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
                    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
                            CaptureType.RESPONSE_CONTENT);
                    proxy.newHar("Pearson.com");
                    chromeOptions.setCapability(CapabilityType.PROXY,
                            seleniumProxy);
                }
                chromeOptions.setCapability("chrome.switches",
                        Arrays.asList("--incognito"));
                if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                    Map<String, Object> deviceMetrics = new HashMap<>();
                    deviceMetrics.put("width",
                            Integer.valueOf(BaseClass.width));
                    deviceMetrics.put("height",
                            Integer.valueOf(BaseClass.height));
                    Map<String, Object> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceMetrics", deviceMetrics);
                    Map<String, Object> chromeOption = new HashMap<>();
                    chromeOption.put("mobileEmulation", mobileEmulation);
                    chromeOptions.setCapability(ChromeOptions.CAPABILITY,
                            chromeOption);
                }
                driver = new ChromeDriver(chromeOptions);
                setBrowserVersion(this.getBrowserVersion(driver));
                driver.manage().window().maximize();
                capBrowserName = chromeOptions.getBrowserName();

            } else if ("MicrosoftEdge".equalsIgnoreCase(browser)) {
                if (driver instanceof EdgeDriver) {
                    return driver;
                }
                EdgeOptions option = new EdgeOptions();
                if (System.getProperty("os.name").contains("Mac")) {
                    System.setProperty("webdriver.edge.driver",
                            "src/test/resources/drivers/Projects/MicrosoftWebDriver");
                } else {
                    System.setProperty("webdriver.edge.driver",
                            "src/test/resources/drivers/Projects/MicrosoftWebDriver.exe");
                }

                try {
                    Runtime.getRuntime()
                            .exec("taskkill /F /IM MicrosoftWebDriver.exe");
                } catch (IOException e) {
                    APP_LOG.info("Launching new Instance of Edge Browser");

                }

                EdgeOptions edgeOptions = new EdgeOptions();
                if (language.contains("es_ES")) {
                    edgeOptions.setPageLoadStrategy("--lang=es");
                } else if (language.contains("ar_SA")) {
                    edgeOptions.setPageLoadStrategy("--lang=ar");
                }
                edgeOptions.setPageLoadStrategy("disable-infobars");
                edgeOptions.setPageLoadStrategy("test-type=browser");
                edgeOptions.setPageLoadStrategy("--start-maximized");
                edgeOptions.setCapability(
                        CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
                        true);
                edgeOptions.setCapability("InPrivate", true);
                if ("true".equalsIgnoreCase(BaseClass.responsiveness)) {
                    Map<String, Object> deviceMetrics = new HashMap<>();
                    deviceMetrics.put("width",
                            Integer.valueOf(BaseClass.width));
                    deviceMetrics.put("height",
                            Integer.valueOf(BaseClass.height));
                    Map<String, Object> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceMetrics", deviceMetrics);
                    Map<String, Object> edgeoptions = new HashMap<>();
                    edgeoptions.put("mobileEmulation", mobileEmulation);
                }
                driver = new EdgeDriver(option);
                setBrowserVersion(this.getBrowserVersion(driver));
                capBrowserName = edgeOptions.getBrowserName();

            } else if (("InternetExplorer".equalsIgnoreCase(browser))
                    || ("IE".equalsIgnoreCase(browser))) {
                if (driver instanceof InternetExplorerDriver) {
                    return driver;
                }
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.setCapability(
                        CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
                        true);
                ieOptions.setCapability(
                        InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,
                        true);
                ieOptions.setCapability("requireWindowFocus", true);
                ieOptions.setCapability("ignoreZoomSetting", true);
                ieOptions.setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
                System.setProperty("webdriver.ie.driver",
                        "src/test/resources/drivers/Projects/IEDriverServer.exe");
                driver = new InternetExplorerDriver(ieOptions);
                setBrowserVersion(this.getBrowserVersion(driver));
                capBrowserName = ieOptions.getBrowserName();
            }

            else if ("Safari".equalsIgnoreCase(browser)) {
                if (driver instanceof SafariDriver) {
                    return driver;
                }
                SafariOptions safariOption = new SafariOptions();
                // safariOption.setUseTechnologyPreview(true);
                // safariOption.useCleanSession(true);
                driver = new SafariDriver();
                driver.manage().window().maximize();
                capBrowserName = safariOption.getBrowserName();
            }

        }

        else if ("SAUCELAB".equalsIgnoreCase(BaseClass.runOnMachine)) {
            try {
                return this.setSauceLabDriver(browser, driver, appLogs);
            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured while initializaion of remotedriver for Sauce Lab "
                                + e);
            }
        } else if ("JenkinsSAUCELAB".equalsIgnoreCase(BaseClass.runOnMachine)) {
            try {
                return this.setJenkinsSauceLabDriver(driver, appLogs);
            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured while initializaion of remotedriver for Jenkins SauceLab "
                                + e);
            }

        } else if ("Remote".equalsIgnoreCase(BaseClass.runOnMachine)) {
            try {
                return this.setRemoteDriver(browser, driver, appLogs);
            } catch (NullPointerException e) {
                APP_LOG.error(
                        "Exception occured while initializaion of remotedriver for Docker "
                                + e);
            }

        }
        return driver;
    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 Configure remote webdriver to run directly on
     *       SauceLab machine
     * @param browser
     *            Browser name
     * @param driver
     *            Object of webdriver
     * @param appLogs
     *            Application logs
     * @return Instance of Remotedriver
     */
    private synchronized WebDriver setJenkinsSauceLabDriver(WebDriver driver,
            Logger appLogs) {
        URL selserverhost = null;
        browser = System.getenv("SELENIUM_BROWSER");
        platform = System.getenv("SELENIUM_PLATFORM");
        version = System.getenv("SELENIUM_VERSION");

        try {
            selserverhost = new URL(
                    "http://" + this.authentication.getUsername() + ":"
                            + this.authentication.getAccessKey()
                            + "@ondemand.saucelabs.com:80/wd/hub");
        } catch (MalformedURLException e) {
            APP_LOG.error("Exception occured in sauceLabUrl", e);
        }
        if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions firefoxoption = new FirefoxOptions();
            firefoxoption.setCapability("platformName", platform);
            firefoxoption.setCapability("browserVersion", version);
            firefoxoption.setCapability("screenResolution", "1920x1080");
            capBrowserName = firefoxoption.getBrowserName();
            if (language.contains("es_ES")) {
                firefoxoption.addArguments("--lang=es");
            } else if (language.contains("ar_SA")) {
                firefoxoption.addArguments("--lang=ar");
            }
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 3600);
            sauceCaps.setCapability("maxDuration", 11800);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build",
                        System.getenv("BUILD_TAG")
                                .concat(", Execution_Envoirment = ")
                                .concat(executionEnviroment)
                                .concat(", Course Name = ").concat(course));
            }
            firefoxoption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, firefoxoption);
        } else if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions chromeOption = new ChromeOptions();
            chromeOption.setCapability("platformName", platform);
            chromeOption.setCapability("browserVersion", version);
            chromeOption.setExperimentalOption("w3c", true);
            capBrowserName = chromeOption.getBrowserName();
            if (language.contains("es_ES")) {
                chromeOption.addArguments("--lang=es-419");
            } else if (language.contains("ar_SA")) {
                chromeOption.addArguments("--lang=ar");
            }
            chromeOption.setCapability("name", this.getTestCaseName());
            chromeOption.setCapability("idleTimeout", 3600);
            chromeOption.setCapability("maxDuration", 3600);
            chromeOption.setCapability("extendedDebugging", downloadHAR);
            chromeOption.addArguments("test-type=browser");
            chromeOption.addArguments("--start-maximized");
            chromeOption.setCapability("screenResolution", "1920x1080");
            chromeOption.addArguments("--incognito");

            if (buildTag != null) {
                chromeOption.setCapability("build",
                        System.getenv("BUILD_TAG")
                                .concat(", Execution_Envoirment = ")
                                .concat(executionEnviroment)
                                .concat(", Course Name = ").concat(course));
            }
            return new RemoteWebDriver(selserverhost, chromeOption);
        } else if ("Safari".equalsIgnoreCase(browser)) {
            SafariOptions safariOption = new SafariOptions();
            safariOption.setCapability("platformName", platform);
            safariOption.setCapability("browserVersion", "latest");
            safariOption.setCapability("screenResolution", "1920x1440");
            capBrowserName = safariOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 3600);
            sauceCaps.setCapability("maxDuration", 3600);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);

            if (buildTag != null) {
                sauceCaps.setCapability("build",
                        System.getenv("BUILD_TAG")
                                .concat(", Execution_Envoirment = ")
                                .concat(executionEnviroment)
                                .concat(", Course Name = ").concat(course));
            }
            safariOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, safariOption);

        } else if (("ie".equalsIgnoreCase(browser))
                || ("internetexplorer".equalsIgnoreCase(browser))
                || ("internet explorer".equalsIgnoreCase(browser))) {
            InternetExplorerOptions ieOption = new InternetExplorerOptions();
            ieOption.setCapability("platformName", platform);
            ieOption.setCapability("browserVersion", version);
            ieOption.setCapability("screenResolution", "1920x1080");
            capBrowserName = ieOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 600);
            sauceCaps.setCapability("maxDuration", 2000);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build",
                        System.getenv("BUILD_TAG")
                                .concat(", Execution_Envoirment = ")
                                .concat(executionEnviroment)
                                .concat(", Course Name = ").concat(course));
            }
            ieOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, ieOption);
        } else if ("MicrosoftEdge".equalsIgnoreCase(browser)) {
            EdgeOptions edgeOption = new EdgeOptions();
            edgeOption.setCapability("platformName", platform);
            edgeOption.setCapability("browserVersion", version);
            edgeOption.setCapability("screenResolution", "1920x1080");
            capBrowserName = edgeOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 3600);
            sauceCaps.setCapability("maxDuration", 3600);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build",
                        System.getenv("BUILD_TAG")
                                .concat(", Execution_Envoirment = ")
                                .concat(executionEnviroment)
                                .concat(", Course Name = ").concat(course));
            }
            edgeOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, edgeOption);
        }
        return driver;
    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 Configure remote webdriver to run directly on
     *       SauceLab machine
     * @param browser
     *            Browser name
     * @param driver
     *            Object of webdriver
     * @param appLogs
     *            Application logs
     * @return Instance of Remotedriver
     */
    private synchronized WebDriver setSauceLabDriver(String browser,
            WebDriver driver, Logger appLogs) {
        URL selserverhost = null;
        try {
            selserverhost = new URL(this.sauceLabUrl);
        } catch (MalformedURLException e) {
            APP_LOG.error("Exception occured in sauceLabUrl", e);
        }
        if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions firefoxoption = new FirefoxOptions();
            firefoxoption.setCapability("platformName", platform);
            firefoxoption.setCapability("browserVersion", "latest");
            firefoxoption.setCapability("screenResolution", "1920x1080");
            capBrowserName = firefoxoption.getBrowserName();
            if (language.contains("es_ES")) {
                firefoxoption.addArguments("--lang=es");
            } else if (language.contains("ar_SA")) {
                firefoxoption.addArguments("--lang=ar");
            }
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 600);
            sauceCaps.setCapability("maxDuration", 1800);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build", buildTag);
            }
            firefoxoption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, firefoxoption);
        } else if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions chromeOption = new ChromeOptions();
            chromeOption.setCapability("platformName", platform);
            chromeOption.setCapability("browserVersion", "latest");
            chromeOption.setExperimentalOption("w3c", true);
            capBrowserName = chromeOption.getBrowserName();
            if (language.contains("es_ES")) {
                chromeOption.addArguments("--lang=es-419");
            } else if (language.contains("ar_SA")) {
                chromeOption.addArguments("--lang=ar");
            }
            chromeOption.setCapability("name", this.getTestCaseName());
            chromeOption.setCapability("idleTimeout", 600);
            chromeOption.setCapability("maxDuration", 1800);
            chromeOption.setCapability("screenResolution", "1920x1080");
            chromeOption.setCapability("extendedDebugging", downloadHAR);
            chromeOption.addArguments("test-type=browser");
            chromeOption.addArguments("test-type=browser");
            chromeOption.addArguments("--start-maximized");
            chromeOption.addArguments("--incognito");
            if (buildTag != null) {
                chromeOption.setCapability("build", buildTag);
            }
            return new RemoteWebDriver(selserverhost, chromeOption);
        } else if ("Safari".equalsIgnoreCase(browser)) {
            SafariOptions safariOption = new SafariOptions();
            safariOption.setCapability("platformName", platform);
            safariOption.setCapability("browserVersion", "latest");
            safariOption.setCapability("screenResolution", "2048x1536");
            capBrowserName = safariOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 600);
            sauceCaps.setCapability("maxDuration", 1800);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build", buildTag);
            }
            safariOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, safariOption);

        } else if (("ie".equalsIgnoreCase(browser))
                || ("internetexplorer".equalsIgnoreCase(browser))
                || ("internet explorer".equalsIgnoreCase(browser))) {
            InternetExplorerOptions ieOption = new InternetExplorerOptions();
            ieOption.setCapability("platformName", platform);
            ieOption.setCapability("browserVersion", "11.103");
            ieOption.setCapability("screenResolution", "1920x1080");
            capBrowserName = ieOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 600);
            sauceCaps.setCapability("maxDuration", 1800);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build", buildTag);
            }
            ieOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, ieOption);
        } else if ("MicrosoftEdge".equalsIgnoreCase(browser)) {
            EdgeOptions edgeOption = new EdgeOptions();
            edgeOption.setCapability("platformName", platform);
            edgeOption.setCapability("browserVersion", "latest");
            edgeOption.setCapability("screenResolution", "1920x1080");
            capBrowserName = edgeOption.getBrowserName();
            MutableCapabilities sauceCaps = new MutableCapabilities();
            sauceCaps.setCapability("seleniumVersion", "3.11.0");
            sauceCaps.setCapability("name", this.getTestCaseName());
            sauceCaps.setCapability("idleTimeout", 600);
            sauceCaps.setCapability("maxDuration", 1800);
            sauceCaps.setCapability("extendedDebugging", downloadHAR);
            if (buildTag != null) {
                sauceCaps.setCapability("build", buildTag);
            }
            edgeOption.setCapability("sauce:options", sauceCaps);
            return new RemoteWebDriver(selserverhost, edgeOption);
        }
        return driver;
    }

    /**
     * @author abhishek.sharda
     * @date 31st May 2017 Configure remote webdriver to run on Docker machine
     * @param browser
     *            Browser Name
     * @param driver
     *            webdriver object
     * @param appLogs
     *            Application logs
     * @return Instance of Remotedriver
     */
    private WebDriver setRemoteDriver(String browser, WebDriver driver,
            Logger appLogs) {

        if ("firefox".equalsIgnoreCase(browser)) {
            cap = DesiredCapabilities.firefox();
            cap.setCapability("version", "");
            cap.setPlatform(Platform.LINUX);
        } else if ("chrome".equalsIgnoreCase(browser)) {

            System.setProperty("webdriver.chrome.driver",
                    "src/test/resources/drivers/Projects/chromedriver.exe");
            cap = DesiredCapabilities.chrome();
            cap.setCapability("version", "");
            cap.setPlatform(Platform.LINUX);
        } else if ("Safari".equalsIgnoreCase(browser)) {
            cap.setCapability("version", "");
            cap.setPlatform(Platform.LINUX);

        } else if (("ie".equalsIgnoreCase(browser))
                || ("internetexplorer".equalsIgnoreCase(browser))
                || ("internet explorer".equalsIgnoreCase(browser))) {
            cap.setCapability("version", "");
            cap.setPlatform(Platform.LINUX);
        }
        String seleniuhubaddress = PropertyManager.getInstance()
                .getValueForKey("seleniuhubaddress").trim();
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            APP_LOG.error("Exception occured in sauceLabUrl", e);
        }
        cap.setJavascriptEnabled(true);
        capBrowserName = cap.getBrowserName();
        return new RemoteWebDriver(selserverhost, cap);
    }

    /**
     * @return The {@link SauceOnDemandAuthentication} instance containing the
     *         Sauce username/access key.
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return this.authentication;
    }

    /**
     * @return the Sauce Job id for the current thread.
     */
    @Override
    public String getSessionId() {
        APP_LOG.info("Sauce session ID" + getSessionId());
        return this.sessionId.get();
    }

    /**
     * @author abhishek.sharda
     * @date 2nd May, 2017
     * @description Extract the test case name from the class name.
     */
    public synchronized String getTestCaseName() {
        Pattern regex = Pattern.compile("(\\_(.*))");
        // create matcher for pattern 'regex' and given string 'className'
        try {
            Matcher matcherString = regex.matcher(methodName.get().toString());
            String testCaseName = "";
            while (matcherString.find()) {
                testCaseName = matcherString.group();
            }
            return testCaseName.substring(1);
        } catch (Exception e) {
            APP_LOG.error("Exception occured in getTestCaseName method", e);
            return "Not Available ";
        }
    }

    /**
     * @author abhishek.sharda
     * @date 2nd July, 2017
     * @description Return session id for sauce lab.
     */
    public void sauceLabSessionID() {
        try {
            String id = ((RemoteWebDriver) getRemoteDriver()).getSessionId()
                    .toString();
            InitializeWebDriver.sessionId.set(id);

        } catch (Exception e) {
            APP_LOG.error("Exception occured while sauceLab session Id :" + e);
        }
    }

    /**
     * @return the methodName.
     */
    public static String getSauceLabSessionId() {
        return sessionId.get();
    }

    /**
     * @author abhishek.sharda
     * @date 2nd June, 2017
     * @description Print the Sauce Lab test cases results on Jenkins.
     */
    public void printSessionId() {
        String message = String.format(
                "SauceOnDemandSessionID=%1$s job-name=%2$s",
                (((RemoteWebDriver) getRemoteDriver()).getSessionId())
                        .toString(),
                "PearsonTestJob");
        System.out.println(message);
        APP_LOG.info(message);
    }

    /**
     * @author sumit.bhardwaj
     * @date 28June, 2017
     * @param driver
     *            Instance of Driver can be IE/Chrome
     * @return Version of Browser
     */

    public String getBrowserVersion(WebDriver driver) {

        try {
            Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
            return caps.getVersion().toString();
        } catch (Exception e) {
            return "Error while finding " + BaseClass.browser
                    + " version because of " + e;
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param driver
     *            Instance of FireFox driver
     * @return Version of Firefox browser
     */

    public String getFirefoxBrowserVersion(WebDriver driver) {

        try {
            String s = (String) ((JavascriptExecutor) driver)
                    .executeScript("return navigator.userAgent;");
            return s.split("Firefox/")[1];
        } catch (Exception e) {
            return "Error while finding Firefox browser version because of "
                    + e;
        }
    }

    public static void setBrowserVersion(String version) {

        InitializeWebDriver.browserVersion = version;
    }

    public String getBrowserVersion() {
        return InitializeWebDriver.browserVersion;
    }

}
