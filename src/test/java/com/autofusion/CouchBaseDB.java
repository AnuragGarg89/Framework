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

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autofusion.constants.Constants;
import com.glp.util.GLP_Utilities;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class CouchBaseDB extends BaseClass {

    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj;
    protected String result = "";
    protected String stepDescription = "";

    public CouchBaseDB(ExtentTest reportTestObj, Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
    }

    /**
     * @author deepak.bithar
     * @date Mar 18 2018
     * @description: Enum to map bucket and cluster in CouchDB.
     * 
     */
    enum Clusters {
        LED("kernel"), LEAMOSE("analytics"), AUTOBAHNPRO("eses");
        String clusterName = "";

        Clusters(String clusterName) {
            this.clusterName = clusterName;
        }

        String getClusterName() {
            return clusterName;
        }
    }

    private String plaStatus;

    /**
     * @author deepak.bithar/ Mukul Sehra
     * @date Mar 08 2018
     * @description: To execute query via couch api's.
     * 
     */
    public Response couchBaseQuery(String query) {
        Response queryResponse = null;
        try {
            String bucket = null;
            if (query.contains("from")) {
                bucket = query.split("from ")[1].split(" ")[0];
            } else {
                bucket = query.split("Update ")[1].split(" ")[0];
            }
            APP_LOG.info("bucket for given query is: " + bucket);

            String cluster = Clusters.valueOf(bucket.toUpperCase())
                    .getClusterName();
            APP_LOG.info("cluster for given " + bucket + " is: " + cluster);

            String loginURL = "https://couchbase-" + cluster + "-"
                    + executionEnviroment.toLowerCase() + ".gl-poc.com/uilogin";
            APP_LOG.info("LoginURL is: " + loginURL);

            String queryURL = "https://couchbase-" + cluster + "-"
                    + executionEnviroment.toLowerCase()
                    + ".gl-poc.com/_p/query/query/service";
            APP_LOG.info("QueryURL is: " + queryURL);

            // Login to couchbase via api and post the query
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            Response authenticationResponse = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("user", couchBaseUserName)
                    .formParam("password", couchBasePassword).config(rac)
                    .post(loginURL);

            queryResponse = given()
                    .contentType("application/x-www-form-urlencoded")
                    .cookies(authenticationResponse.cookies())
                    .formParam("statement", query).config(rac).post(queryURL);

            APP_LOG.info("Query executed is: " + query);
            APP_LOG.info("Query response is: " + queryResponse.asString());
            APP_LOG.info("Query execution status code is : "
                    + queryResponse.statusCode());
            if (queryResponse.statusCode() != 200) {
                logResultInReport(
                        Constants.FAIL
                                + ": Error while couchbase query execution - "
                                + queryResponse.statusLine(),
                        "Verify that query executed successfully on couchbase db",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while authentication/Executing query via CouchDB api's"
                            + queryResponse.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while couchbase query execution - "
                            + e.getMessage(),
                    "Verify that query executed successfully on couchbase db",
                    reportTestObj);
        }
        return queryResponse;
    }

    /**
     * @author deepak.bithar
     * @date Mar 08 2018
     * @description: To fetch specific data field from response.
     * 
     */
    public List<Object> couchBaseQuery(Response queryResponse, String keyWord) {
        List<Object> data = new ArrayList<Object>();
        try {
            data = queryResponse.then().extract().body().jsonPath().getList(
                    "results.leamose.properties.mastery.algorithm.parameters."
                            + keyWord);
            APP_LOG.info(keyWord + " fetched is: " + data);
            if (data.size() == 0) {
                logResultInReport(
                        Constants.FAIL + ": Failed to retrieve the " + keyWord
                                + ", data is - " + data,
                        "Verify that " + keyWord
                                + " retrieved from couchbase db",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while fetching Ability" + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while retrieving the "
                            + keyWord + " - " + e.getMessage(),
                    "Verify that " + keyWord + " retrieved from couchbase db",
                    reportTestObj);
        }
        return data;
    }

    /**
     * @author deepak.bithar
     * @date Mar 08 2018
     * @description: Method to fetch the ability of given user.
     * 
     */
    public String getAbility(String userName) {
        String data = null;
        List<Object> dataList = new ArrayList<Object>();
        try {
            GLP_Utilities gp = new GLP_Utilities(reportTestObj, APP_LOG);
            String subscriptionId = gp.getSubscriptionId(userName,
                    ResourceConfigurations.getProperty("consolePassword"));
            APP_LOG.info("SubscriptionId fetched is: " + subscriptionId);
            Response queryResponse = couchBaseQuery(
                    "select * from led where docType='LearnerAnalyticsSession' and subscriptionId='"
                            + subscriptionId + "'");
            String sessionId = queryResponse.then().extract().body().jsonPath()
                    .getString("results.led.id").toString()
                    .replaceAll("[\\[\\]]", "");
            APP_LOG.info("SessionId fetched is: " + sessionId);
            queryResponse = couchBaseQuery(
                    "select * from leamose where scope.TAGS.sessionId='"
                            + sessionId + "'order by _created asc");
            dataList = couchBaseQuery(queryResponse, "ability");
            data = dataList.get(dataList.size() - 1).toString();
            APP_LOG.info("Ability fetched is: " + data);
        } catch (Exception e) {
            APP_LOG.error("Exception while reteriving Ability for user " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while retrieving the ability - "
                            + e.getMessage(),
                    "Verify that ability retrieved successfully from couchbase db",
                    reportTestObj);
        }
        return data;
    }

    /**
     * @author Abhishek.Sharda
     * @date Mar 08 2018
     * @description: Method to fetch the PLA Status for verifying load Unload
     *               activity.
     * 
     */
    public String updatePreAssessmentMasteryLevel(String glpID,
            String masterylevel, String userName) {
        String newMasterylevel = null;
        try {
            String updatequery = "Update led set learningModel.configurations.courseMastery.PreAssessment.mastery = "
                    + masterylevel + " where id ='" + glpID + "'";
            Response updatequeryResponse = couchBaseQuery(updatequery);
            String query = "Select learningModel.configurations.courseMastery.PreAssessment.mastery from led where id ='"
                    + glpID + "'";
            Response queryResponse = couchBaseQuery(query);
            queryResponse.prettyPrint();
            newMasterylevel = queryResponse.then().extract().body().jsonPath()
                    .getString("results[0].mastery").toString();
            APP_LOG.info("Mastery level Status fetched is: " + newMasterylevel);
            if (newMasterylevel.equalsIgnoreCase(masterylevel)) {
                this.result = Constants.PASS
                        + "New Mastery level has been sucessfully updated in LED Bucket";
                logResultInReport(this.result,
                        "Verify New Mastery level sucessfully updated in LED Bucket for the user, "
                                + userName,
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL
                        + " New Mastery level failed to updated in LED Bucket";
                logResultInReport(this.result,
                        "Verify New Mastery level sucessfully updated in LED Bucket",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Error while reteriving MasteryLevel Status for user " + e);
        }
        return newMasterylevel;
    }

    /**
     * @author Abhishek.Sharda
     * @date Mar 08 2018
     * @description: Method to fetch the PLA Status for verifying load Unload
     *               activity.
     * 
     */
    public String verifyEventInPLA(String correlationID) {
        try {
            Response queryResponse = couchBaseQuery(
                    "select * from autobahnpro where id ='" + correlationID
                            + "'");
            plaStatus = queryResponse.then().extract().body().jsonPath()
                    .getString("status").toString();
            APP_LOG.info("PLA Status fetched is: " + plaStatus);
            if (plaStatus.equalsIgnoreCase("success")) {
                this.result = Constants.PASS
                        + " Load/Unload events sucessfully stored in Autobahnpro Bucket";
                logResultInReport(this.result,
                        "Verify Load/Unload events sucessfully stored in Autobahnpro Bucket",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL
                        + " Load/Unload events not found in Autobahnpro Bucket";
                logResultInReport(this.result,
                        "Verify Load/Unload events sucessfully stored in Autobahnpro Bucket",
                        this.reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Error while reteriving PLA Status for user " + e);
        }
        return plaStatus;
    }

    /**
     * @author pankaj.sarjal
     * @param queryString
     *            : Actual query to be fire in couchbase db
     * @param arrayName
     *            : Pass the array name against which data need to be verify
     * @param queryKey
     *            : Pass the 'Key' against which value need to be validate
     * @param expectedData
     *            : Pass the string value that need to be validate
     * @param dataIndex
     *            : Pass the index in 'arrayName' to fetch the actual data value
     *            to be verify
     */
    public void verifyCouchbaseQueryData(String queryString, String arrayName,
            String queryKey, String expectedData, int dataIndex) {
        try {
            JSONObject root = new JSONObject(
                    couchBaseQuery(queryString).asString());
            JSONArray resultsArray = root.getJSONArray(arrayName);
            JSONObject dataObj = resultsArray.getJSONObject(dataIndex);
            if (expectedData
                    .equalsIgnoreCase(dataObj.get(queryKey).toString())) {
                this.result = Constants.PASS + " Expected data '" + expectedData
                        + "' verified successfully in couchbase db.";
                logResultInReport(this.result,
                        "Verify data '" + expectedData + "' in couchbase db.",
                        this.reportTestObj);
            } else {
                this.result = Constants.FAIL + " Expected data '" + expectedData
                        + "' could not verified in couchbase db.";
                logResultInReport(this.result,
                        "Verify data '" + expectedData + "' in couchbase db.",
                        this.reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while verifying expected data '"
                    + expectedData + "' in couchbase db " + e);

        }
    }

    /**
     * @author ratnesh.singh
     * @param queryString
     *            : Actual query to be fired in couchbase db
     * @param arrayName
     *            : Pass the array name against which data need to be verified
     * @param dataIndex
     *            : Pass the index of "results" array to fetch the data objects
     *            having required responses
     * @param lAId
     *            : Pass the Multipart Question LAId of the part for which DB
     *            values to be fetched
     * @param queryKeysList
     *            : Pass the Map of 'Keys' against which values need to be
     *            fetched
     * 
     * 
     */
    public Map<String, String> fetchCouchbaseDBStateSavedForMultipart(
            String queryString, String arrayName, String lAId, int dataIndex,
            Map<String, String> queryKeys) {
        Map<String, String> returndataMap = new HashMap();

        try {
            JSONObject root = new JSONObject(
                    couchBaseQuery(queryString).asString());
            JSONArray resultsArray = root.getJSONArray(arrayName);
            JSONObject dataObjRoot = resultsArray.getJSONObject(dataIndex);
            JSONObject dataObjLed = dataObjRoot.getJSONObject("led");
            JSONObject dataObjOutcomeValues = dataObjLed
                    .getJSONObject("outcomesValue");
            JSONObject dataObjLAId = dataObjOutcomeValues.getJSONObject(lAId);

            for (Map.Entry<String, String> entry : queryKeys.entrySet()) {

                returndataMap.put(entry.getKey(),
                        dataObjLAId.get(entry.getKey()).toString());

            }

            return returndataMap;

        } catch (Exception e) {
            APP_LOG.error("Exception while verifying expected data '"
                    + "' in couchbase db " + e);
            logResultInReport(
                    Constants.FAIL + ": Exception Occurred :" + e.getMessage(),
                    "Fetch data saved in CouchBase DB for provided LAID of Multipart Question",
                    this.reportTestObj);
            return null;

        }

    }
}