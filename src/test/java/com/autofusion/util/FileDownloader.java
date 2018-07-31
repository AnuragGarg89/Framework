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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.autofusion.BaseClass;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentReports;

public class FileDownloader extends BaseClass {

    private int httpStatusOfLastDownloadAttempt;

    /**
     * Get the current location that files will be downloaded to.
     *
     * @return The filepath that the file will be downloaded to.
     */
    public String localDownloadPath() {
        try {
            localDownloadPath = System.getProperty("user.dir") + File.separator
                    + File.separator + "DownloadFolder"
                    + InitClass.now("dd.MMMMM.yyyy hh.mm.ss");
            new ExtentReports(localDownloadPath + File.separator + fileName,
                    true);
            File f = new File(localDownloadPath + File.separator + fileName);
            f.createNewFile();
            return localDownloadPath + File.separator + fileName;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Gets the HTTP status code of the last download file attempt
     *
     * @return
     */
    public int httpStatusOfLastDownloadAttempt() {
        return this.httpStatusOfLastDownloadAttempt;
    }

    /**
     * Load in all the cookies WebDriver currently knows about so that we can
     * mimic the browser cookie state
     *
     * @param seleniumCookieSet
     * @return
     */
    private CookieStore mimicCookieState(
            Set<org.openqa.selenium.Cookie> seleniumCookieSet) {

        try {
            CookieStore cookieStore = new BasicCookieStore();

            for (org.openqa.selenium.Cookie seleniumCookie : seleniumCookieSet) {

                BasicClientCookie basicClientCookie = new BasicClientCookie(
                        seleniumCookie.getName(), seleniumCookie.getValue());
                basicClientCookie.setDomain(seleniumCookie.getDomain());
                basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
                basicClientCookie.setPath(seleniumCookie.getPath());
                cookieStore.addCookie(basicClientCookie);

                cookieStore.addCookie(basicClientCookie);
            }

            return cookieStore;
        } catch (Exception e) {

            APP_LOG.error("Error while setting Webbrowser Cookies" + e);
            return null;
        }

    }

    /**
     * Perform the SSL cerification ignored.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */

    private CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException {
        // // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        // SSLContext sslContext = SSLContextBuilder.create()
        // .loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[]
                           getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }
                } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        // we can optionally disable hostname verification.
        // if you don't want to further weaken the security, you don't have to
        // include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        // create an SSL Socket Factory to use the SSLContext with the trust
        // self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
                sc, allowAllHosts);

        // finally create the HttpClient using HttpClient factory methods and
        // assign the ssl socket factory
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.NETSCAPE).build();
        return HttpClients.custom().setSSLSocketFactory(connectionFactory)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(
                        mimicCookieState(returnDriver().manage().getCookies()))
                .build();
    }

    /**
     * Perform the file/image download.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public String downloader(String userName, String password) {
        GLP_Utilities glp = new GLP_Utilities(reportTestObj, APP_LOG);
        String baseUrl = configurationsXlsMap.get("InstructorUrlForGradeBook");
        long timeStamp = System.currentTimeMillis();
        String exportPath = "/gb/v1/export?format=csv&courseId=COURSEID&instructorId=INSTRUCTORID&timestamp="
                + timeStamp;
        String sectionId = glp.getCreatedCourseSectionId(userName, password);

        try {
            exportPath = exportPath.replaceAll("COURSEID",
                    glp.getGlpCourseId(userName, password, sectionId));
            exportPath = exportPath.replaceAll("INSTRUCTORID",
                    glp.getGLPId(userName, password));
            String fileToDownloadLocation = baseUrl + exportPath;
            HttpResponse response = null;
            try {
                CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient();
                HttpGet request = new HttpGet(fileToDownloadLocation);
                request.addHeader("Authorization",
                        glp.Oauth2(userName, password));
                response = httpclient.execute(request);
            } catch (Exception e) {
                APP_LOG.error("Error while downloading file:" + e);
            }

            fileName = response.getFirstHeader("X-fileName").getValue();
            this.httpStatusOfLastDownloadAttempt = response.getStatusLine()
                    .getStatusCode();
            File downloadedFile = new File(this.localDownloadPath());
            if (downloadedFile.canWrite() == false) {
                boolean b = downloadedFile.setWritable(true);
                APP_LOG.info("setWritable() succeeded  ? : " + b);
            }
            FileUtils.copyInputStreamToFile(response.getEntity().getContent(),
                    downloadedFile);
            return localDownloadPath;
        } catch (Exception e) {
            APP_LOG.error("Error while downloading file:" + e);

            return null;
        }
    }

}