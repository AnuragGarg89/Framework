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

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DiagnosticAttemptThroughAPI extends BaseClass {

    protected Logger APP_LOGS = null;
    private String coachMark = "/lcd/v1/coachMark";
    private String xauth;
    private String getSlinkToStartDiagnosticTest = null;

    /**
     * Diagnostic Attempt through API constructor Environment.
     */
    public DiagnosticAttemptThroughAPI(ExtentTest reportTestObj,
            Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author mayank.mittal
     * @date 20 Mar 2018
     * @description :Method to complete Diagnostic Test Through APIs' for
     *              existing instructor
     * @param auth
     * @param sectionid
     */

    public void attemptDiagnosticTestThroughAPI(String userName,
            String password) {
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        Response response, StartDiagnosticTest, start = null;
        RequestSpecification spec = null;
        String auth = objRestUtil.Oauth2(userName, password);
        String courseId = objRestUtil.getGlpCourseId(userName, password);
        String glpID = objRestUtil.getGLPId(userName, password);
        String subscriptionID = objRestUtil.getSubscriptionId(userName,
                password);
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", auth)
                    .setContentType("application/json").build();
            response = given().spec(spec).get("/lcd/assets/course?learnerId="
                    + glpID + "&courseId=" + courseId);

            String getSlinkToStartDiagnosticTest = response.then().extract()
                    .body().jsonPath()
                    .getString(
                            "data[0].resources." + courseId + ".links[0].href")
                    .toString();

            StartDiagnosticTest = given().spec(spec)
                    .get(getSlinkToStartDiagnosticTest);

            String nextLink = StartDiagnosticTest.then().extract().body()
                    .jsonPath().getString("data.links[6].href").toString();
            String outcomesLink = StartDiagnosticTest.then().extract().body()
                    .jsonPath().getString("data.links[3].href").toString();

            start = given().spec(spec).get(nextLink);

            String dontknow = start.then().extract().body().jsonPath()
                    .getString("data.links[5].href").toString();
            // Coachmark is clicked once for DiagnosticTest
            given().spec(spec)
                    .body("{\"subscriptionId\":\"" + subscriptionID
                            + "\",\"preAssessmentGotIt\":\"true\"}")
                    .post(coachMark);

            int value = 0, count = 1, maxCount = 0;
            // 20: Default value of Diagnostic Test cases.
            while (count < (Integer
                    .parseInt((ResourceConfigurations
                            .getProperty("DiagnosticTestQuestionCount")))
                    - value) && (maxCount < 30)) {
                // don'tknow is hit each time for Diagnostic test
                given().spec(spec).body("[{\"value\": \"testing\"}]\r\n")
                        .post(dontknow);
                APP_LOG.info("total question Attempted " + value);

                given().spec(spec).get(nextLink);
                Response outcome = given().spec(spec).get(outcomesLink);

                value = Integer.parseInt(outcome.then().extract().body()
                        .jsonPath().getString("data.completed").toString());
                maxCount++;
            }
            given().spec(spec).body("[{\"value\": \"testing\"}]\r\n")
                    .post(dontknow);
            given().spec(spec)
                    .get("/lcd/v1/course/" + courseId + "/learner/" + glpID
                            + "/subscriptionId/" + subscriptionID
                            + "/studyplan/?pageName=DIAGNOSTIC_RESULT");
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured while attempting Diagnostic question."
                            + e.getMessage());
            logResultInReport(Constants.FAIL,
                    "Exception occured while attempting Diagnostic question."
                            + e.getMessage(),
                    reportTestObj);

        }
    }

    /**
     * @author mayank.mittal
     * @date 20 Mar 2018
     * @description :Method to complete Diagnostic Test Through APIs' for fresh
     *              instructor
     * @param auth
     * @param sectionid
     */

    public void attemptDiagnosticTestThroughAPI(String learnerUserName,
            String instructorName, String password) {
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        Response response = null, StartDiagnosticTest = null;
        RequestSpecification spec = null;
        RequestSpecification spec1 = null;
        String auth = null;
        auth = objRestUtil.Oauth2(learnerUserName, password);
        String instructorSectionId = objRestUtil.getCreatedCourseSectionId(
                instructorName,
                ResourceConfigurations.getProperty("consolePassword"));
        String courseId = objRestUtil.getGlpCourseId(learnerUserName,
                ResourceConfigurations.getProperty("consolePassword"),
                instructorSectionId);
        String glpId = objRestUtil.getGLPId(learnerUserName, password);
        String subscriptionId = objRestUtil.getSubscriptionId(learnerUserName,
                password);
        String result = "lcd/v1/course/courseID/learner/glpID/subscriptionId/subscriptionID/studyplan/?pageName=DIAGNOSTIC_RESULT&t=1528292380788";
        String resultEndpoint = result.replace("courseID", courseId)
                .replace("glpID", glpId)
                .replace("subscriptionID", subscriptionId);
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", auth)
                    .setContentType("application/json").build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 0; i < 3; i++) {
                response = given().spec(spec).config(rac)
                        .get("/lcd/assets/course?learnerId=" + glpId
                                + "&courseId=" + courseId);
                if (response.getStatusCode() == 200) {
                    getSlinkToStartDiagnosticTest = response.then().extract()
                            .body().jsonPath()
                            .getString("data.lcdNext.links[6].href").toString();
                    break;
                }
            }
            String nextLink = null;
            String submitLink = null;
            for (int i = 0; i < 3; i++) {
                StartDiagnosticTest = given().spec(spec).config(rac)
                        .get(getSlinkToStartDiagnosticTest);
                if (StartDiagnosticTest.getStatusCode() == 200) {
                    nextLink = StartDiagnosticTest.then().extract().body()
                            .jsonPath().getString("data.links[6].href")
                            .toString();
                    submitLink = StartDiagnosticTest.then().extract().body()
                            .jsonPath().getString("data.links[5].href")
                            .toString();
                    break;
                }
            }
            String submit = submitLink.replaceAll("^/lcd/v1/doNotKnow", "")
                    .trim();
            String submitendpoint = "/lcd/v1/answer";
            submitendpoint = submitendpoint.concat(submit);
            int i = 0;
            while (i <= 20) {
                Response res = null;
                String outcome = null;
                Response start = null;
                String questiontype = null;
                for (int j = 0; j < 3; j++) {
                    start = given().spec(spec).get(nextLink);
                    if (start.statusCode() == 200) {
                        questiontype = start.then().extract().body().jsonPath()
                                .getString("data.links[6].href").toString();
                        break;
                    }
                }
                if (questiontype.toString().contains(("answerSummative"))) {
                    res = given().spec(spec).body("[]").config(rac)
                            .post(questiontype);
                    if (res.statusCode() == 200) {
                        outcome = res.then().extract().body().jsonPath()
                                .getString("data.link[0].href").toString();
                        break;
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        res = given().spec(spec).body("[{\"value\":\"\"}]")
                                .config(rac).post(submitendpoint);
                        if (res.statusCode() == 200) {
                            outcome = res.then().extract().body().jsonPath()
                                    .getString("data.link[0].href").toString();
                            break;
                        }
                    }
                }
                i++;
                TimeUnit.SECONDS.sleep(1);
                APP_LOG.info("NO of question attempt." + i);
            }
            response = given().spec(spec).config(rac).get(resultEndpoint);
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured while attempting Diagnostic question."
                            + e.getMessage());
            logResultInReport(Constants.FAIL,
                    "Exception occured while attempting Diagnostic question."
                            + e.getMessage(),
                    reportTestObj);
        }
    }

}
