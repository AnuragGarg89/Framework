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
 */package com.autofusion.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.autofusion.BaseClass;
import com.autofusion.InitializeWebDriver;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarContent;
import de.sstoehr.harreader.model.HarEntry;
import net.lightbody.bmp.core.har.Har;

/**
 * @author abhishek.sharda
 *
 */
/**
 * @author abhishek.sharda
 *
 */
public class CommonUtil extends BaseClass implements KeywordConstant {

    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";
    private PerformAction performAction = new PerformAction();
    private JavascriptExecutor js;
    public static String trackingID;
    private String Activity;
    private String saucelabuRL = "https://eds.saucelabs.com/SauceSessionId/network.har";
    protected String sFileName = "src/test/resources/essentials/web/networkLogs/api.har";

    public CommonUtil(ExtentTest reportTestObj, Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author mohit.gupta5
     * @date 13 July ,17
     * @description Press Tab key
     */
    public void pressTabKey() {
        this.result = this.performAction.execute(ACTION_PRESS_TAB_KEY);
    }

    /**
     * @author sagar.pawar
     * @date 04 May ,17
     * @description Clear Session storage
     */
    public void clearSessionStorage() {
        String windowSessionStorage = "window.sessionStorage.clear();";
        this.js = (JavascriptExecutor) returnDriver();
        this.js.executeScript(String.format(windowSessionStorage));

    }

    /**
     * @author sagar.pawar
     * @date 04 May ,17
     * @description Clear Local storage
     */
    public void clearLocalStorage() {
        String windowStorageClear = "window.localStorage.clear();";
        this.js = (JavascriptExecutor) returnDriver();
        this.js.executeScript(String.format(windowStorageClear));

    }

    /**
     * @author tarun.gupta1
     * @date 22 May, 2017
     * @description To generate random string of numbers
     */

    public String generateRandomStringOfNumbers(int numofChars) {
        String inputNumbers = "1234567890";
        StringBuilder inputString = new StringBuilder();
        SecureRandom rnd = new SecureRandom();
        while (inputString.length() < numofChars) {
            // string.
            int index = (int) (rnd.nextDouble() * inputNumbers.length());
            inputString.append(inputNumbers.charAt(index));
        }
        String FinalStr = inputString.toString();
        APP_LOG.info(FinalStr);
        return FinalStr;
    }

    /**
     * @author tarun.gupta1
     * @date 22 May, 2017
     * @description To generate random string of Alphabets
     */
    public String generateRandomStringOfAlphabets(int numofChars) {
        try {
            String inputAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble() * inputAlphabets.length());
                inputString.append(inputAlphabets.charAt(index));
            }
            String FinalStr = inputString.toString();
            APP_LOG.info(FinalStr);
            return FinalStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 22 May, 2017
     * @description To generate random string of alphanumeric character
     */
    public String generateRandomStringOfAlphaNumericCharacters(int numofChars) {
        try {
            String inputAlphaNumerics = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble()
                        * inputAlphaNumerics.length());
                inputString.append(inputAlphaNumerics.charAt(index));
            }
            String FinalStr = inputString.toString();
            APP_LOG.info(FinalStr);
            return FinalStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @author tarun.gupta1
     * @date 23 May, 2017
     * @description To generate random string of Special character
     */
    public String generateRandomStringOfSpecialCharacters(int numofChars) {
        try {
            String inputSpecialChars = "!@#$%^&*()_,.<>/?][{};:'";
            StringBuilder inputString = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            while (inputString.length() < numofChars) {
                // string.
                int index = (int) (rnd.nextDouble()
                        * inputSpecialChars.length());
                inputString.append(inputSpecialChars.charAt(index));
            }
            String FinalStr = inputString.toString();
            return FinalStr;
        } catch (RuntimeException e) {
            APP_LOG.error(
                    "unable to generate generateRandomStringOfSpecialCharacters "
                            + e);
            return null;
        }
    }

    /**
     * @author sumit.bhardwaj Scrolls the webpage upto pixels passed in
     *         parameter.
     * 
     * @param pixelToScrollHorizontally
     *            : pixel to scroll horizontally
     * @param pixelToScrollVertically
     *            : pixel to scroll vertically
     * @return Pass or fail with the cause.
     */
    public String scrollWebPage(int pixelToScrollHorizontally,
            int pixelToScrollVertically) {

        System.out.println("Scrolling through web page ... ");
        BaseClass base = new BaseClass() {
        };
        WebDriver dr = base.returnDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) dr;
            js.executeScript("scroll(" + pixelToScrollHorizontally + ","
                    + pixelToScrollVertically + ")");
            logResultInReport("PASS, Sucessfully scroll through the page",
                    "Scroll through the page to make the element visible",
                    this.reportTestObj);
        }

        catch (Exception e) {

            // Log the exception
            APP_LOG.error("Error while scrolling through the web page : " + e);

            return "Fail : Error while scrolling through the web page : " + e;
        }

        return "Pass : Scrolled through the web page";

    }

    /**
     * @author mukul.sehra
     * @description Press Enter Key
     */
    public void pressEnterKey() {
        performAction.execute(ACTION_PRESS_ENTER_KEY);
    }

    /**
     * @author Abhishek. Sharda
     * @date 10 April ,17
     * @description Verify videos
     */
    public void verifyVideoPlayback(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_VIDEOS, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author Abhishek. Sharda
     * @date 10 April ,17
     * @description Verify images
     */
    public void verifyimages(String element, String stepDesc) {
        this.result = this.performAction.execute(ACTION_VERIFY_IMAGES, element);
        logResultInReport(this.result, stepDesc, this.reportTestObj);
    }

    /**
     * @author mukul.sehra
     * @param webElementLocator
     *            --> element to scroll into
     */
    public void scrollIntoView(String webElementLocator) {
        this.performAction.execute(ACTION_SCROLL_INTO_VIEW, webElementLocator);
    }

    /**
     * @description Refreshes the web page
     */
    public void refreshCurrentPage() {
        returnDriver().navigate().refresh();
    }

    public String searchString(String fileName, String regex)
            throws IOException {
        String line = null;
        Scanner scanner = null;
        try {
            Pattern pattern = Pattern.compile(regex);
            scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (pattern.matcher(line).matches()) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured:- " + e);
        } finally {
            scanner.close();
        }

        return line;
    }

    /**
     * @author Abhishek. Sharda
     * @date 03 March ,18
     * @description download HAR file from sauce Lab
     */

    public synchronized void downloadHarFileSauceLab() {
        InputStream in = null;
        OutputStream out = null;
        try {
            String sauceURL = saucelabuRL.replace("SauceSessionId",
                    InitializeWebDriver.getSauceLabSessionId());
            APP_LOG.info("Sauce Lab URL is following: " + sauceURL);
            URL url = new URL(sauceURL);
            String encoded = Base64.getEncoder()
                    .encodeToString((sauce_username + ":" + sauce_authkey)
                            .getBytes(StandardCharsets.UTF_8));
            trustEveryone();
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoded);
            for (int i = 0; i <= 4; i++) {
                TimeUnit.SECONDS.sleep(10);
                int responseCode = connection.getResponseCode();
                APP_LOG.info("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    fileName = sFileName.replace("api", getmethodName());
                    APP_LOG.info("HAR File location is: " + fileName);
                    File file = new File(fileName);
                    in = (InputStream) connection.getInputStream();
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    for (int b; (b = in.read()) != -1;) {
                        out.write(b);
                    }
                    break;
                } else {
                    APP_LOG.info("GET request not worked");
                }
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Exception comes during HAR file downlaod from SauceLab: "
                            + e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                APP_LOG.error("Exception comes while closing file: " + e);
            }
            try {
                out.close();
            } catch (IOException e) {
                APP_LOG.error("Exception comes while closing file: " + e);
            }
        }
    }

    /**
     * @author Abhishek. Sharda
     * @date 03 March ,18
     * @description trust all certificates
     */

    private synchronized void trustEveryone() {
        try {
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname,
                                SSLSession session) {
                            return true;
                        }
                    });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) { // should never happen
            APP_LOG.error(
                    "Exception comes while trusting all certificates: " + e);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 26 Feb 2018
     * @description :Method to create HAR or download HAR from SauceLab.
     * @return tracking ID
     * @throws Throwable
     */
    public synchronized void createHARFile() {
        try {
            Har har = null;
            har = proxy.getHar();
            har.toString();
            fileName = sFileName.replace("api", getmethodName());
            File harFile = new File(fileName);
            har.writeTo(harFile);
            proxy.stop();
            APP_LOG.info("HAR file sucessfully created: " + getmethodName());
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception coming while trying to create HAR file: " + e);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 26 Feb 2018
     * @description :To get tracking ID from HAR File.
     * @return
     */
    public synchronized String getTrackingID() {
        try {
            if (runOnMachine.equalsIgnoreCase("local")) {
                createHARFile();
            } else {
                downloadHarFileSauceLab();
            }
            searchTrackingID();
            if (trackingID != null) {
                this.result = Constants.PASS
                        + " Sucessfully fetched tracking ID from load unload activity";
                logResultInReport(this.result,
                        "Verify tracking ID from laod unload activity",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL
                        + " Failed to fetched tracking ID from load unload activity";
                logResultInReport(this.result,
                        "Verify tracking ID from laod unload activity",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception coming while trying to get network id: " + e);
        }
        return trackingID;
    }

    /**
     * @author abhishek.sharda
     * @date 03 Mar 2018
     * @description :To search tracking ID from HAR File.
     * @return
     */
    public String searchTrackingID() {
        try {
            HarReader harReader = new HarReader(new MyMapperFactory());
            de.sstoehr.harreader.model.Har har1 = null;
            try {
                har1 = harReader.readFromFile(new File(fileName));
            } catch (HarReaderException e) {
                APP_LOG.error(
                        "Exception coming while trying to fetch tracking Id: "
                                + e);
            }
            List<HarEntry> entries = har1.getLog().getEntries();
            for (HarEntry entry : entries) {
                String method = entry.getRequest().getMethod().name();
                if (method.equalsIgnoreCase("Post")) {
                    try {
                        HarContent response = entry.getResponse().getContent();
                        if (response != null) {
                            Activity = response.getText();
                        }
                        if (Activity.contains("trackingId")) {
                            APP_LOG.info(
                                    "Following activity occured" + Activity);
                            ArrayList<String> aList = new ArrayList<String>(
                                    Arrays.asList(Activity.split(",")));
                            for (int i = 0; i < aList.size(); i++) {
                                System.out.println(" -->" + aList.get(i));
                                String listContent = aList.get(i);
                                if (listContent.contains("trackingId")) {
                                    String regex1 = "\\[(.*?)\\]";
                                    Pattern regex = Pattern.compile(regex1);
                                    Matcher matcherString = regex
                                            .matcher(aList.get(i));
                                    while (matcherString.find()) {
                                        String tracking = matcherString.group();
                                        trackingID = tracking
                                                .replaceAll("\\[", "")
                                                .replaceAll("\\]", "")
                                                .replaceAll("\"", "");
                                        APP_LOG.info(
                                                "Tracking Id for the activity is following: "
                                                        + trackingID);
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        APP_LOG.error(
                                "Exception coming while trying to fetch tracking Id: "
                                        + e);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception coming while trying to search tracking Id: "
                            + e);
        }
        //trackingID = "77f80cf3-0b1f-4e36-86e0-0e01798a6b32";
        return trackingID;
    }

    /**
     * @author Nitish.Jaiswal
     * @date 29 Sep,2017
     * @description handle run time pop ups
     */
    public void handleRunTimePopUp(String consoleCrosslocator,
            String consoleBacklocator) {
        try {
            String res = "";
            APP_LOG.debug("handle run time pop ups");
            res = performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                    consoleCrosslocator);
            if (res.contains(Constants.PASS)) {
                JavascriptExecutor js = (JavascriptExecutor) returnDriver();
                js.executeScript("arguments[0].click();", returnDriver()
                        .findElement(By.cssSelector(".close-title")));
                if (performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE,
                        consoleBacklocator).contains(Constants.PASS)) {
                    performAction.execute(ACTION_CLICK, consoleBacklocator);
                }
            }
        } catch (Exception e) {

        }
    }
}
