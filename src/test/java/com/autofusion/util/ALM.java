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

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.autofusion.BaseClass;
import com.google.api.client.util.Charsets;
import com.google.common.io.Resources;

import io.restassured.response.Response;

public class ALM extends BaseClass {
    public ALM() {
        // TODO Auto-generated constructor stubS
    }

    private Response res;
    private String parentId;
    private String testSetFolderId;
    private String testSetId;
    private String testInstanceId;
    private Map<String, String> cook;
    public List<String> idALM = new ArrayList<>();
    private String almAccountPasswordDecoded = new String(
            Base64.decodeBase64(almPassword.getBytes()));

    public synchronized void updateAlmResults(String almId, String almResult) {
        this.res = this.login(almUserName, this.almAccountPasswordDecoded);
        this.getParentFolderId(this.res, "GL-GLP");
        this.getTestSetFolderId(this.res, "AcceptanceTesting", this.parentId);
        this.getTestSetId(this.res, this.testSetFolderId, "AutomationJob");
        this.getTestInstanceId(this.res, this.testSetId, almId);
        this.updateTestInstanceID(this.res, this.testInstanceId, almResult);
    }

    public synchronized Response updateTestInstanceID(Response res,
            String testInstanceId, String testResult) {
        try {
            URL file = Resources.getResource("configFiles/jsonFiles/Alm.json");
            String myJson = Resources.toString(file, Charsets.UTF_8)
                    .replace("result", testResult);
            res = given().cookies(this.cook).contentType("application/json")
                    .body(myJson)
                    .put("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/test-instances/"
                            + testInstanceId + "");
            res.prettyPrint();
            APP_LOG.info("Test case result was " + testResult
                    + " and the same was sucessfully updated to the ALM");
        }

        catch (Exception e) {

            APP_LOG.info("Error while updating test cases in ALM:- " + e);
        }
        return res;
    }

    public Response login(String username, String Password) {
        try {
            this.res = given().auth().preemptive().basic(username, Password)
                    .contentType("application/json")
                    .get("http://global-alm.pearson.com:8080/qcbin/api/authentication/sign-in");
            this.cook = this.res.getCookies();
            return this.res;
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while creating session in ALM because of:- " + e);
            return null;
        }
    }

    public String getParentFolderId(Response res, String parentFolderName) {
        try {
            res = given().cookies(this.cook).accept("application/json")
                    .formParam("query",
                            "{name[" + parentFolderName + "];parent-id[0]}")
                    .formParam("fields", "id")
                    .get("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/test-set-folders");
            res.prettyPrint();
            this.parentId = this.modifyResponse(res.then().extract().body()
                    .path("entities.Fields.values.value").toString());

            return "Successfully extracted praentId:- '" + this.parentId
                    + "' from json response";
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while extracting praentId from json response because of:- "
                            + e);
            return "Error while extracting praentId from json response because of:- "
                    + e;
        }

    }

    public String getTestSetFolderId(Response res, String testSetFolderName,
            String parentId) {
        try {
            res = given().cookies(cook).accept("application/json")
                    .formParam("query",
                            "{name[" + testSetFolderName + "];parent-id["
                                    + parentId + "]}")
                    .formParam("fields", "id")
                    .get("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/test-set-folders");
            res.prettyPrint();
            this.testSetFolderId = this.modifyResponse(res.then().extract()
                    .body().path("entities.Fields.values.value").toString());

            return "Successfully extracted testSetFolderId:- '"
                    + testSetFolderId + "' from json response";
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while extracting testSetFolderId from json response because of:- "
                            + e);
            return "Error while extracting testSetFolderId from json response because of:- "
                    + e;
        }

    }

    public synchronized String getTestSetId(Response res,
            String testSetFolderId, String testSetName) {
        try {
            res = given().cookies(this.cook).accept("application/json")
                    .formParam("query",
                            "{name[" + testSetName + "];test-set-folder.id["
                                    + this.testSetFolderId + "]}")
                    .formParam("fields", "id")
                    .get("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/test-sets");
            res.prettyPrint();
            this.testSetId = this.modifyResponse(res.then().extract().body()
                    .path("entities.Fields.values.value").toString());

            return "Successfully extracted testSetId:- '" + this.testSetId
                    + "' from json response";
        } catch (Exception e) {
            APP_LOG.error(
                    "Error while extracting testSetId from json response because of:- "
                            + e);
            return "Error while extracting testSetId from json response because of:- "
                    + e;
        }

    }

    /**
     * @description get instance id.
     * @param res
     *            response
     * @param testSetId
     *            Test Set ID
     * @param testId
     *            Test ID
     */
    public synchronized String getTestInstanceId(Response res, String testSetId,
            String testId) {
        try {
            res = given().cookies(this.cook).accept("application/json")
                    .formParam("query",
                            "{cycle-id[" + this.testSetId + "];test-id["
                                    + testId + "]}")
                    .formParam("fields", "id")
                    .get("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/test-instances");
            String keys = this.modifyResponse(res.then().extract().body()
                    .path("entities.Fields.Name").toString());
            String values = this.modifyResponse(res.then().extract().body()
                    .path("entities.Fields.values.value").toString());
            String[] keysArray = keys.split(",");
            String[] valuesArray = values.split(",");
            Map<String, String> keyValue = new LinkedHashMap<>();
            for (int i = 0; i < keysArray.length; i++) {
                keyValue.put(keysArray[i], valuesArray[i]);
            }
            this.testInstanceId = keyValue.get("id");
            return "Successfully extracted testInstanceId:- '"
                    + this.testInstanceId + "' from json response";

        } catch (Exception e) {
            APP_LOG.error(
                    "Error while extracting testInstanceId from json response because of:- "
                            + e);
            return "Error while extracting testSetId from json response because of:- "
                    + e;
        }

    }

    public String modifyResponse(String modifyId) {
        if (modifyId.contains("[") || modifyId.contains("]")
                || modifyId.contains("}") || modifyId.contains("{")
                || modifyId.contains(" ")) {
            return modifyId.replaceAll("\\[|\\]|\\}|\\{| ", "");
        }
        return null;
    }

    public List<String> getTestCases() {
        try {
            this.res = given().cookies(this.cook).accept("application/json")
                    .formParam("query",
                            "{name[Sprint*];user-template-10[Sprint*];user-template-13[=Y];status[=Approved];user-template-20[NOT ('*Dashboard*' OR '*Builder_Resource*')]}")
                    .formParam("order-by", "{id[ASC]}")
                    .formParam("fields", "id")
                    .get("http://global-alm.pearson.com:8080/qcbin/rest/domains/PRODUCTION/projects/Product/tests");
            String testCaseId = this.modifyResponse(this.res.then().extract()
                    .body().path("entities.Fields.values.value").toString());
            String[] tests = testCaseId.split(",");
            for (String i : tests) {
                this.idALM.add(i);
            }

        } catch (Exception e) {
            APP_LOG.error(
                    "Error while extracting testCasesId from ALM because of:- "
                            + e);
        }
        return this.idALM;
    }

    public List<String> getALMCount() {
        this.login("VSHARAB", this.almAccountPasswordDecoded);
        return this.getTestCases();
    }

}
