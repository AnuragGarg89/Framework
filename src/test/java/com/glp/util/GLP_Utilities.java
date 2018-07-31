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

package com.glp.util;

import static io.restassured.RestAssured.given;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import com.autofusion.BaseClass;
import com.autofusion.CouchBaseDB;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.util.Accessibility;
import com.google.api.client.util.Charsets;
import com.google.common.io.Resources;
import com.jayway.jsonpath.JsonPath;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GLP_Utilities extends BaseClass {

    private String getAdminTokenApi = "/iam/v1/tokens?action=FrToken";
    private String fetchLearnerIdApi = "/iam/v1/users/";
    private String courseSection = "/api/coursesection";
    protected Logger APP_LOGS = null;
    protected ExtentTest reportTestObj = null;
    protected String result = "";
    protected String stepDescription = "";
    private String xAuthToken = "";
    private String getConsoleXTokenApi = "/tokens";
    private String escrow = "/piui/get-create-account-props";
    private String escrowToken = "";
    private String validateWebCredentials = "/login/webcredentials";
    private String glpConsoleValidateEmail = "/api/emailValidation";
    private String pi_Id = "";
    private String accessToken = "";
    private String emailAddress = "";
    private String webToken = "";
    private String consoleAuthToken = "";
    private String userDetails = "";
    private String glpID = "";
    private String glpCourseIdApi = "/lec/v1/section/sectionId";
    private String sectionId;
    private String courseRegisterApi = "/api/coursesection/sectionId/courseregistration";
    private String glpCourseId = null;
    private String inviteKey;
    private String Account_Id;
    private String consolePid = "/credentials/?username=userId";
    private String accountId = "/usercomposite/piId";
    private String updateAccountEmail = "/identityprofiles/piId/emails/AccountId";
    private String assessmentLockApi = "/ims/v1/course/glpCourseId/lockstatus";
    private String courseName;
    private String getIamsiToken = "/iamsi/v1/oauth/token";
    private String templateApiUri = "/gb/v1/templates";
    private String correlationID;
    private String lockUnlockApi = "/ims/v1/course/glp-courseId/lockUnlockAssessment";
    private long finalTimeSeconds;
    private long startTimeSeconds;
    private String totEndPoint = "/tot/v1/assets?assetId=ASSETID&courseId=COURSEID&aggregation=AGGREGATION";
    private String tot;
    private String webtoken_auth;
    private String automationProductCode;

    /**
     * GLP_Utilities constructor Environment.
     */
    public GLP_Utilities(ExtentTest reportTestObj, Logger APP_LOGS) {
        this.APP_LOGS = APP_LOGS;
        this.reportTestObj = reportTestObj;
        this.courseName = configurationsXlsMap.get("courseName");
        this.automationProductCode = configurationsXlsMap
                .get("automationProductId");
    }

    /**
     * @author sumit.bhardwaj
     * @description Method to alter the desired key values in the JSON and
     *              returning the JSON as string
     * @return alteredJson as string
     */
    public synchronized String alterJson(String jsonName,
            Map<String, String> jsonValues) {
        try {
            URL file = Resources
                    .getResource("configFiles/jsonFiles/" + jsonName);
            String jsonString = Resources.toString(file, Charsets.UTF_8);

            for (Map.Entry<String, String> keyVal : jsonValues.entrySet()) {
                jsonString = jsonString.replaceAll(keyVal.getKey(),
                        keyVal.getValue());
            }
            APP_LOG.info("JSON altered successfully");
            return jsonString;
        } catch (Exception e) {

            APP_LOG.info("Error while altering json : " + e);
            return null;
        }
    }

    /**
     * @author sumit.bhardwaj
     * @description Method to retrieve the QuestionID from the Network logs
     * @return questionId
     */
    public synchronized String getQuestionIdOnUI() {

        String question = "";
        String regex1 = "Q-\\w\\d{7,8}";
        Pattern regex = Pattern.compile(regex1);
        try {

            String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
            String netData = ((JavascriptExecutor) returnDriver())
                    .executeScript(scriptToExecute).toString();
            APP_LOG.debug(netData);
            Matcher matcherString = regex.matcher(netData);
            while (matcherString.find()) {
                question = matcherString.group();
            }
            APP_LOG.debug("Extarcted questionId--> '" + question + "'");

            return question;
        } catch (Exception e) {
            APP_LOG.error("Exception in getQuestionIdOnUI method : " + e);
            return null;
        }
    }

    /**
     * @author sumit.bhardwaj
     * @description Method to retrieve the token(Admin Access) which would be
     *              used as header for creating Active user
     * @return tokenId
     */

    private synchronized String getFrTokenId() {
        try {
            xAuthToken = getConsoleXAuthtoken();
            Response res = given().header("X-Authorization", xAuthToken)
                    .contentType("application/json").post(getAdminTokenApi);
            String tokenId = res.then().extract().body().jsonPath()
                    .getString("data").toString();
            APP_LOG.debug("Status code for retrieve FRToken --> '\""
                    + res.statusCode() + "\"");
            APP_LOG.debug("Extracted FRTokenId--> '\"" + tokenId + "\"");

            return tokenId;
        } catch (Exception e) {
            APP_LOG.error("Exception in fetching tokenId for Admin : " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while retrieving the FrToken - "
                            + e.getMessage(),
                    "Verify FrToken retrieval", reportTestObj);
            return null;
        }
    }

    /**
     * @author mukul.sehra
     * @description Method to retrieve the learnerId of a user
     * @param userName
     * @return learnerId
     */
    public synchronized String getLearnerIdUsingAccessToken(String userName) {
        try {
            Response res = given().contentType("application/json")
                    .header("x-authorization", xAuthToken)
                    .get(fetchLearnerIdApi + userName);
            // Need to update the jsonPathString when GLP-13731 would be fixed
            String learnerId = res.then().extract().body().jsonPath()
                    .getString("data.learnerId").toString();
            APP_LOG.debug(
                    "Status Code for getLearnerId : " + res.getStatusCode());
            if (res.getStatusCode() == 200) {
                APP_LOG.debug("Retrieved learnerId:" + learnerId + " for \""
                        + userName + "\" successfully");
            } else {
                APP_LOG.debug("Failed to retrieve the learnerId : "
                        + res.getStatusLine());
                logResultInReport(
                        Constants.FAIL
                                + ": Error while retrieving the learnerId - "
                                + res.getStatusLine(),
                        "Verify learner Id retrieval", reportTestObj);
            }
            return learnerId;
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception while retrieving learnerId for the provided User \""
                            + userName + "\" because : " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while retrieving the learnerId- "
                            + e.getMessage(),
                    "Verify learner Id retrieval", reportTestObj);
            return null;
        }
    }

    /**
     * @author Abhishek Sharda
     * @description Method to delete the user and course data via API
     * @param userName
     * @param password
     */
    public synchronized void unpublishSubscribedCourseDatabase(String userName,
            String password) {
        Response respCourseDelete = null;
        Response respSubscriptionDelete = null;
        RequestSpecification spec = null;

        // Adding specification
        spec = new RequestSpecBuilder()
                .setBaseUri(configurationsXlsMap.get("DomainURL"))
                .setContentType("application/json").build();
        // Generating oauth
        String auth = Oauth2(userName, password);
        // Generating glp courseId
        String glpCourseId = getGlpCourseId(userName, password);

        // Replacing glpCourseId in deleteCourseApi and deleteSubscriptionApi
        // strings
        String deleteCourseApi = "/lec/v1/courseId/glpCourseID";
        deleteCourseApi = deleteCourseApi.replaceAll("glpCourseID",
                glpCourseId);
        String deletesubscriptionIdApi = "/lad/v1/subscriptionId/glpCourseID";
        deletesubscriptionIdApi = deletesubscriptionIdApi
                .replaceAll("glpCourseID", glpCourseId);
        try {
            for (int i = 1; i <= 3; i++) {
                // Delete course from LEC bucket
                respCourseDelete = given().spec(spec)
                        .header("Authorization", auth).delete(deleteCourseApi);
                APP_LOG.info("Response body for the delete course api : "
                        + respCourseDelete.getBody().asString());

                // Delete subscriptions corresponding to the deleted course from
                // LAD buket
                respSubscriptionDelete = given().spec(spec)
                        .header("Authorization", auth)
                        .delete(deletesubscriptionIdApi);
                APP_LOG.info("Response body for the delete subscription api : "
                        + respSubscriptionDelete.getBody().asString());

                if (respCourseDelete.getStatusCode() == 200
                        && respSubscriptionDelete.getStatusCode() == 200) {
                    APP_LOG.info(
                            "Course details successfully deleted from couchbase buckets LEC and LAD for courseId: "
                                    + glpCourseId);
                    break;
                } else {
                    APP_LOG.debug(
                            "Course deletion and subscription api failed");
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Course deletion failed and exception coming " + e);
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

    /**
     * normalApiCall method execute http request without JsonBody
     * 
     * @param requestType
     *            --> Type of HTTP request(Post,Put,Get)
     * @param url
     *            --> URL of API
     * @return--> Return the Response
     */

    public Response apiCallWithoutJson(String requestType, String url) {
        Response response = null;
        try {
            // Checking if Request type is post and execute API
            if (requestType.trim().equalsIgnoreCase("post")) {
                APP_LOG.info("Request Type: POST");
                APP_LOG.info("Requested URL: " + url);
                response = given().contentType("application/json").post(url);
                return response;
            }

            // Checking if Request type is get and execute API
            if (requestType.trim().equalsIgnoreCase("get")) {
                APP_LOG.info("Request Type: GET");
                APP_LOG.info("Requested URL: " + url);
                response = given().contentType("application/json").get(url);
                return response;
            }

            // Checking if Request type is put and execute API
            if (requestType.trim().equalsIgnoreCase("put")) {
                APP_LOG.info("Request Type: PUT");
                APP_LOG.info("Requested URL: " + url);
                response = given().contentType("application/json").put(url);
                return response;
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while hitting API because : " + e);
        }
        return response;
    }

    /**
     * ApiCall method execute http request with JsonBody
     * 
     * @param requestType
     *            --> Type of HTTP request(Post,Put,Get)
     * @param url
     *            --> URL of API
     * @return--> Return the Response
     */
    public Response apiCallWithJson(String requestType, String url,
            String jsonFileName) {
        Response response = null;
        // Checking if Request type is post and execute API
        try {
            if (requestType.trim().equalsIgnoreCase("post")) {
                APP_LOG.info("Request Type: POST");
                APP_LOG.info("Requested URL: " + url);
                URL file = Resources
                        .getResource("configFiles/jsonFiles/" + jsonFileName);
                String jsonString = Resources.toString(file, Charsets.UTF_8);
                response = given().contentType("application/json")
                        .body(jsonString).post(url);
                return response;
            }

            // Checking if Request type is get and execute API
            if (requestType.trim().equalsIgnoreCase("get")) {
                APP_LOG.info("Request Type: GET");
                APP_LOG.info("Requested URL: " + url);
                response = given().contentType("application/json").get(url);
                return response;
            }

            // Checking if Request type is put and execute API
            if (requestType.trim().equalsIgnoreCase("put")) {
                APP_LOG.info("Request Type: PUT");
                APP_LOG.info("Requested URL: " + url);
                response = given().contentType("application/json").put(url);
                return response;
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while hitting API because : " + e);
        }
        return response;
    }

    /**
     * @author abhishek.sharda
     * @date 13 March,17
     * @description to check accessibility issues using
     * @return null
     */

    public void testAccessibility() {
        try {
            APP_LOGS.debug("Verify Acessabilty issues on Sign-on Page");
            Accessibility accessibilty = new Accessibility();
            List<String> getAccessibilityErrors = accessibilty.runAcopChecks();
            ListIterator<String> itr = getAccessibilityErrors.listIterator();
            if (itr.hasNext()) {
                logResultInReport(itr.next(),
                        "Accesabilty result as per Web Content Accessibility Guidelines 2.0 & Sect 508",
                        reportTestObj);
            } else {
                logResultInReport("PASS, No voilation found on the page",
                        "Accesabilty result as per Web Content Accessibility Guidelines 2.0 & Sect 508",
                        reportTestObj);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @author Abhishek.Sharda
     * @description Method to retrieve the access token which would be used as
     *              X-Authorization header
     * @return accessToken for X-Authorization header
     */
    public synchronized String getConsoleXAuthtoken() {
        Response res = null;
        RequestSpecification spec = null;
        try {
            for (int i = 0; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleAPI"))
                        .build();
                res = given().spec(spec).contentType("application/json")
                        .body("\r\n{\"userName\":\""
                                + configurationsXlsMap
                                        .get("consoleUserForXAuth").trim()
                                + "\",\r\n\"password\":\""
                                + configurationsXlsMap
                                        .get("consolePasswordForXAuth").trim()
                                + "\"\r\n}")
                        .post(getConsoleXTokenApi);
                APP_LOG.debug("Current Status code--> '\"" + res.getStatusCode()
                        + "\"");
                APP_LOG.debug("Current Try--> '\"" + (i + 1) + "\"");
                if (res.getStatusCode() == 201) {
                    break;
                }
            }
            String xAuth = res.then().extract().body().jsonPath()
                    .getString("data").toString();
            APP_LOG.debug(
                    "Extracted access token to be used for X-Authorization header --> '\""
                            + xAuth + "\"");
            return xAuth;
        }

        catch (Exception e) {
            APP_LOG.error("Exception in fetching x-auth token : " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while retrieving the ConsoleXAuthtoken - "
                            + res.getStatusLine(),
                    "Verify ConsoleXAuthtoken retrieval", reportTestObj);
            return null;
        }
    }

    /**
     * @author abhishek.sharda
     * @date 22 Dec 2017
     * @description :Create instructor users with new course
     */
    public synchronized void createInstructorWithNewCourse(String userName,
            String password, boolean masteryLevel) {
        try {
            createConsoleUserViaApi(userName, password, "instructor");
            validateWebCredentials(userName, password);
            sendVerificationEmail(userName, "instructor");
            glpConsoleValidateEmail("instructor", userName);
            groupManagerUpdateMembership(userName);
            sectionId = createCourseSection(userName, password);
            inviteKey(userName, password);
            // Setting PreAssessmentMasteryLevel in couchbase
            try {
                if (masteryLevel) {
                    CouchBaseDB cb = new CouchBaseDB(reportTestObj, APP_LOG);
                    cb.updatePreAssessmentMasteryLevel(
                            getGlpCourseId(userName, password, sectionId), "0",
                            userName);
                }
            } catch (Exception e) {
                APP_LOG.error(
                        "Exception occured while updating couchbase DB: " + e);
            }

        } catch (Exception e) {
            this.result = Constants.FAIL
                    + ": User Creation and subscribe course failed for"
                    + userName + "'";
            logResultInReport(result,
                    "Verify user creation and course subscription for: "
                            + userName,
                    reportTestObj);
            APP_LOG.error(
                    "Exception occured in instructor User creation With Email Validation: "
                            + e);
        }
    }

    /**
     * @author Abhishek.Sharda
     * @description Method to return PiId.
     * @return PiId
     */

    public synchronized String getPiId(String userName) {
        Response resp = null;
        RequestSpecification spec = null;
        consoleAuthToken = this.getConsoleXAuthtoken();
        spec = new RequestSpecBuilder()
                .setBaseUri(configurationsXlsMap.get("GetPidUrl")).build();
        consolePid = consolePid.replaceAll("userId", userName);
        try {
            for (int i = 1; i <= 3; i++) {
                resp = given().spec(spec)
                        .header("X-Authorization", consoleAuthToken)
                        .get(consolePid);
                if (resp.getStatusCode() == 200
                        || resp.getStatusCode() == 201) {
                    pi_Id = resp.then().extract().body().jsonPath()
                            .getString("data.identity.id").toString();
                    APP_LOG.info("getpi_Id" + pi_Id);
                    break;
                } else {
                    APP_LOG.debug("getpi_Id : " + resp.getStatusLine());
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + ": Error while retrieving the PI Id - "
                                        + resp.getStatusLine(),
                                "Verify PI Id retrieval", reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in getPi_Id API " + e);
            logResultInReport(
                    Constants.FAIL + ": Exception while retrieving the PI Id - "
                            + e.getMessage(),
                    "Verify PI Id retrieval", reportTestObj);
        }
        consolePid = "/credentials/?username=userId";
        return pi_Id;
    }

    /**
     * @author Abhishek.Sharda
     * @description Method to return AccountId
     * @return PiId
     */

    public synchronized String getAccountId() {
        Response resp = null;
        RequestSpecification spec = null;
        consoleAuthToken = this.getConsoleXAuthtoken();
        spec = new RequestSpecBuilder()
                .setBaseUri(configurationsXlsMap.get("GetPidUrl")).build();
        accountId = accountId.replaceAll("piId", pi_Id);
        try {
            for (int i = 1; i <= 3; i++) {
                resp = given().spec(spec)
                        .header("X-Authorization", consoleAuthToken)
                        .get(accountId);
                if (resp.getStatusCode() == 200
                        || resp.getStatusCode() == 201) {
                    Account_Id = resp.then().extract().body().jsonPath()
                            .getString("data.identityProfile.emails.id")
                            .replaceAll("\\W", "").toString();

                    APP_LOG.info("getAccountId" + Account_Id);
                    break;
                } else {
                    APP_LOG.debug("getAccountId : " + resp.getStatusLine());
                    logResultInReport(
                            Constants.FAIL
                                    + ": Error while retrieving the Account Id - "
                                    + resp.getStatusLine(),
                            "Verify Account Id retrieval", reportTestObj);
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in getAccountId API " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while retrieving the Account Id - "
                            + resp.getStatusLine(),
                    "Verify Account Id retrieval", reportTestObj);
        }

        return Account_Id;
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to validate email address.
     * @param accessToken
     * @param userDetails
     */
    public synchronized void updateAccountEmail(String userRole,
            String userName) {
        Response response = null;
        RequestSpecification spec = null;
        consoleAuthToken = this.getConsoleXAuthtoken();
        spec = new RequestSpecBuilder()
                .setBaseUri(configurationsXlsMap.get("GetPidUrl")).build();
        // String url = "
        // http://tst-piapi-internal.dev-openclass.com/identityprofiles/piId/emails/AccountId";
        updateAccountEmail = updateAccountEmail
                .replaceAll("AccountId", Account_Id).replaceAll("piId", pi_Id)
                .trim();
        try {
            for (int i = 1; i <= 3; i++) {
                response = given().spec(spec).contentType("application/json")
                        .header("X-Authorization", consoleAuthToken)
                        .body("{\r\n\t\"isValidated\": \"Y\"\r\n}")
                        .put(updateAccountEmail);
                APP_LOG.info("Email Verification response : "
                        + response.prettyPrint());
                if (response.statusCode() == 200) {
                    this.result = Constants.PASS
                            + ": Successfully  created following user with email validation '"
                            + userName + "'";
                    logResultInReport(result,
                            "Verify user creation and email validation for: "
                                    + userName,
                            reportTestObj);
                    APP_LOG.info(
                            "Email sucessfully validated for newly created user");

                    break;
                }
            }
            if (response.getStatusCode() != 200) {
                this.result = Constants.FAIL
                        + ": Error while Email validation for user : "
                        + userName + "'";
                logResultInReport(result,
                        "Verify user creation and email validation for: "
                                + userName,
                        reportTestObj);
            }
            Assert.assertTrue(
                    response.getStatusCode() == 200
                            || response.getStatusCode() == 201,
                    "User Creation failed from the console API");
        } catch (Exception e) {
            APP_LOG.error("Exception occured in glpConsoleValidateEmail API"
                    + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while Email validation for user : "
                            + userName + "'",
                    "Verify user creation and email validation for: "
                            + userName,
                    reportTestObj);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Add instructor to group manager
     * @param userName
     * @param pi_Id
     */
    public synchronized void groupManagerUpdateMembership(String userName) {
        Response rs = null;
        RequestSpecification spec = null;
        try {
            consoleAuthToken = this.getConsoleXAuthtoken();
            Map<String, String> membership = new HashMap<>();
            membership.put("pi_Id", pi_Id);
            for (int i = 1; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("GroupManagerAPI"))
                        .build();
                rs = given().spec(spec).contentType("application/json")
                        .pathParam("groupId",
                                "d9d14cda-fb4f-4ef3-a208-c37256a486c4")
                        .header("X-Authorization", consoleAuthToken)
                        .body(alterJson("RegistrarUser.json", membership))
                        .post("/groups/{groupId}/members");
                APP_LOG.debug(
                        "Status code for User Creation and update membership\""
                                + rs.getStatusCode());
                if (rs.getStatusCode() == 201 || rs.getStatusCode() == 200) {
                    APP_LOG.debug(
                            "Instructor sucessfully addeed into admin group");
                    break;
                } else {
                    APP_LOG.debug(
                            "Instructor addition into admin group failed: "
                                    + rs.getStatusLine());
                    logResultInReport(
                            Constants.FAIL
                                    + ": Error while adding the instructor to the Group manager --> "
                                    + rs.getStatusLine(),
                            "Verify group manager membership updation",
                            reportTestObj);
                }
            }
            Assert.assertTrue(
                    rs.getStatusCode() == 200 || rs.getStatusCode() == 201,
                    "Course Creation failed for the user");
        } catch (Exception e) {
            APP_LOG.error("Instructor addition into admin group failed: " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while updating group manager membership - "
                            + e.getMessage(),
                    "Verify group manager membership updation", reportTestObj);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to validate email address.
     * @param accessToken
     * @param userDetails
     */
    public synchronized void glpConsoleValidateEmail(String userRole,
            String userName) {
        Response response = null;
        RequestSpecification spec = null;
        try {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("webToken", userDetails.split("\\|")[0]);
            data.put("emailAddress", userDetails.split("\\|")[1]);
            data.put("userName", userDetails.split("\\|")[2]);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            for (int i = 1; i <= 3; i++) {
                RequestSpecification request = given().spec(spec)
                        .contentType(ContentType.JSON)
                        .header("authorization", webtoken_auth)
                        .header("x-is-qa", "true").header("x-role", userRole);
                RestAssuredConfig rac = RestAssured.config()
                        .sslConfig(new SSLConfig().allowAllHostnames()
                                .relaxedHTTPSValidation("TLSv1.2"));
                response = request.body(data).config(rac)
                        .put(glpConsoleValidateEmail);
                if (response.statusCode() == 200) {
                    this.result = Constants.PASS
                            + ": Successfully created following user with email validation '"
                            + userName + "'";
                    logResultInReport(result,
                            "Verify user creation and email validation for: "
                                    + userName,
                            reportTestObj);
                    APP_LOG.info(
                            "Email sucessfully validated for newly created user");

                    break;
                }
            }
            if (response.getStatusCode() != 200) {
                this.result = Constants.FAIL
                        + ": Error while validating email for user : '"
                        + userName + "'";
                logResultInReport(result,
                        "Verify new course creation for the user: " + userName,
                        reportTestObj);
            }
            Assert.assertTrue(
                    response.getStatusCode() == 200
                            || response.getStatusCode() == 201,
                    "User Creation completed from the console API");
        } catch (Exception e) {
            APP_LOG.error("Exception occured in glpConsoleValidateEmail API"
                    + response.statusCode() + e);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to send validation email
     * @param accessToken
     * @param userName
     * @param role
     * @return
     */
    public synchronized void courseEnrollmentInstructor(String userName,
            String userRole) {
        Response response = null;
        RequestSpecification spec = null;
        try {
            HashMap<String, String> enrollment = new HashMap<String, String>();
            enrollment.put("username", userName);
            enrollment.put("piId", pi_Id);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 3; i++) {
                response = given().spec(spec).contentType(ContentType.JSON)
                        .pathParam("sectionid", sectionId)
                        .header("authorization", accessToken)
                        .header("x-is-qa", "true").header("x-role", userRole)
                        .header("DNT", "1")
                        .header("Referer",
                                "https://console-qa.pearsoned.com/courses/{sectionid}/manage/people")
                        .body(alterJson("CourseEnrollment.json", enrollment))
                        .config(rac)
                        .put("/api/coursesection/{sectionid}/courseregistration");
                if (response.getStatusCode() == 200
                        || response.getStatusCode() == 201) {
                    APP_LOG.info(
                            "Existing Course enrolled for newly created instructor user"
                                    + response.asString());
                    break;
                }
            }
            if (response.getStatusCode() != 200) {
                logResultInReport(
                        Constants.FAIL + " : Error while course enrollment : "
                                + response.statusLine(),
                        "Verify course enrollment", reportTestObj);
            }

        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendVerificationEmail API"
                    + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL + " : Exception while course enrollment : "
                            + e.getMessage(),
                    "Verify course enrollment", reportTestObj);
        }
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to send validation email
     * @param accessToken
     * @param userName
     * @param role
     * @return
     */
    public synchronized String sendVerificationEmail(String userName,
            String userRole) {
        Response response = null;
        RequestSpecification spec = null;
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 3; i++) {
                RequestSpecification request = given().spec(spec)
                        .contentType(ContentType.JSON)
                        .header("authorization", webtoken_auth)
                        .header("x-is-qa", "true").header("x-role", userRole)
                        .config(rac).pathParam("userName", userName);
                response = request.request().post(
                        "/api/verification/verificationEmail/{userName}/abhishek.sharda@pearson.com");
                if (response.getStatusCode() == 200
                        || response.getStatusCode() == 201) {
                    webToken = response.then().extract().body().jsonPath()
                            .getString("eventModel.user.webToken").toString();
                    emailAddress = response.then().extract().body().jsonPath()
                            .getString("eventModel.user.emailAddress")
                            .toString();
                    userName = response.then().extract().body().jsonPath()
                            .getString("eventModel.user.userName").toString();
                    userDetails = webToken + "|" + emailAddress + "|"
                            + userName;
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendVerificationEmail API"
                    + response.statusCode() + e);
        }
        return (userDetails);
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to validate web credtionals
     * @param userName
     * @param password
     * @return
     */
    public synchronized String validateWebCredentials(String userName,
            String password) {
        Response response = null;
        RequestSpecification spec = null;
        try {
            String clientId = "KCpjnbqANB0RkI8aohz5uRBMAGeN5FGA";
            long timeStamp = System.currentTimeMillis();
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleAPI")).build();
            for (int i = 1; i <= 3; i++) {
                RequestSpecification request = given().spec(spec)
                        .formParam("client_id", clientId)
                        .formParam("username", userName)
                        .formParam("password", password)
                        .header("grant_type", "password")
                        .header("login_success_url",
                                "https%3A%2F%2Fconsole-qa.pearsoned.com%2F")
                        .header("ts", timeStamp);
                response = request.post(validateWebCredentials);
                APP_LOG.info("Response code for validateWebCredentials"
                        + response.getStatusCode());
                if (response.getStatusCode() == 200
                        || response.getStatusCode() == 201) {
                    webtoken_auth = response.then().extract().body().jsonPath()
                            .getString("data.access_token").toString();
                    break;
                } else {
                    APP_LOG.error("Response code for validateWebCredentials"
                            + response.getStatusCode());
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while validating email of the user, as status code is - "
                                        + response.getStatusCode(),
                                "Verify email validation of the user",
                                reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Response code for validateWebCredentials: "
                    + response.getStatusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception coming while validating email of the user, as status code is - "
                            + response.getStatusCode(),
                    "Verify email validation of the user", reportTestObj);
        }
        return webtoken_auth;
    }

    /**
     * @author Abhishek.Sharda
     * @description Method to return Escrow token
     * @return escrowToken
     */

    public synchronized String getPiGetEscrow() {
        Response resp = null;
        RequestSpecification spec = null;
        try {
            for (int i = 1; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleAPI"))
                        .build();
                resp = given().spec(spec).when().get(escrow);
                if (resp.getStatusCode() == 200
                        || resp.getStatusCode() == 201) {
                    escrowToken = resp.then().extract().body().jsonPath()
                            .getString("data.createAccountEscrow").toString();
                    APP_LOG.info("getPiGetEscrow API" + escrowToken);
                    break;
                } else {
                    APP_LOG.debug("getPiGetEscrow : " + resp.getStatusLine());
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in getPiGetEscrow API " + e);
        }
        return escrowToken;

    }

    /**
     * @author Abhishek.Sharda
     * @description Method to create an active user via console API
     * @param userName
     * @return pi_Id
     */
    public synchronized String createConsoleUserViaApi(String userName,
            String password, String userRole) {
        Response rs = null;
        RequestSpecification spec = null;
        try {
            Map<String, String> createConsoleUser = new HashMap<>();
            createConsoleUser.put("name", userName);
            createConsoleUser.put("pwd", password);
            createConsoleUser.put("fname", userName);
            if (executionEnviroment.equalsIgnoreCase("Stage")
                    || executionEnviroment.equalsIgnoreCase("StageBiteSize")
                    || executionEnviroment.contains("UAT")
                    || executionEnviroment.equalsIgnoreCase("PERF")) {
                createConsoleUser.put("instID", "54dbca1e3004d01c8d3ba89a");
            } else if (executionEnviroment.equalsIgnoreCase("QA")
                    || executionEnviroment.equalsIgnoreCase("QABiteSize")
                    || executionEnviroment.equalsIgnoreCase("Int")
                    || executionEnviroment.toUpperCase().contains("DEV")) {
                createConsoleUser.put("instID", "57b4ca0c77c871c9e082274b");
            }
            if (userRole.equalsIgnoreCase("instructor")) {
                createConsoleUser.put("role", "self_identified_instructor");
            } else if (userRole.equalsIgnoreCase("student")) {
                createConsoleUser.put("role", "console_student");
            }
            for (int i = 1; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleAPI"))
                        .build();
                rs = given().spec(spec).contentType("application/json")
                        .pathParam("token", getPiGetEscrow())
                        .body(alterJson("ConsoleUser.json", createConsoleUser))
                        .post("/login/createAccount/{token}");
                APP_LOG.debug("Status code for User Creation \""
                        + rs.getStatusCode());
                if (rs.getStatusCode() == 201 || rs.getStatusCode() == 200) {
                    pi_Id = rs.then().extract().body().jsonPath()
                            .getString("data").toString();
                    APP_LOG.debug(
                            "Extracted  Pi_Id be used for Registrar API --> '\""
                                    + pi_Id + "\"");
                    APP_LOG.debug("Successfully Created user \"" + userName
                            + " in console");
                    break;
                } else {
                    APP_LOG.debug("User creation failed because : "
                            + rs.getStatusLine());
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while creating user, as status code is - "
                                        + rs.getStatusCode(),
                                "Verify New test user creation", reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception creating User because : " + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Eexception occured while creating user, " + e,
                    "Verify New test user creation", reportTestObj);
        }
        return pi_Id;
    }

    /**
     * @author yogesh.choudhary
     * @description Method to retrieve the QuestionID from the Network logs
     * @return questionIdListOn UI
     */
    public List<String> getQuestionIdListOnUI() {

        List<String> qlist = new ArrayList<String>();

        String question = "";
        String regex1 = "Q-\\w\\d{7,8}";
        Pattern regex = Pattern.compile(regex1);
        try {
            String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
            String netData = ((JavascriptExecutor) returnDriver())
                    .executeScript(scriptToExecute).toString();
            Matcher matcherString = regex.matcher(netData);
            while (matcherString.find()) {
                question = matcherString.group();
                qlist.add(question);
            }
            APP_LOG.debug("Extarcted questionId--> '" + question + "'");
            return qlist;
        } catch (Exception e) {
            APP_LOG.error("Exception in getQuestionIdOnUI method : " + e);
            return null;
        }
    }

    /**
     * @author yogesh.choudhary
     * @description Method to retrieve Distractor from the Network logs
     * @return questionId
     */
    public synchronized String getDistractor(String userName,
            String distractor) {
        distractor = null;
        String questionType = null;
        List<String> questionlist = getQuestionIdListOnUI();
        try {
            for (String s : questionlist) {

                // Getting Response
                Response createUserResponse = given()
                        .contentType("application/json")
                        .header("pearsonextssosession", getFrTokenId())
                        .header("X-Authorization", xAuthToken)
                        .header("x-user-id",
                                getLearnerIdUsingAccessToken(userName))
                        .when()
                        .get("https://learnerui-qa.gl-poc.com/dcms/v1/contents/"
                                + s
                                + "?subscriptionId=LED-Squires-1512550437461&isSavedState=false");
                APP_LOG.debug("Status code for User Creation \""
                        + createUserResponse.getStatusCode());

                // Get Question Type
                questionType = createUserResponse.then().extract().body()
                        .jsonPath().getString("data.model").toString();

                try {
                    if (questionType.equals("glp-assessment-mcqma")) {
                        distractor = createUserResponse.then().extract().body()
                                .jsonPath()
                                .getString("data.extensions.distractor")
                                .toString();
                        System.out.println(distractor);

                    }
                } catch (Exception e) {
                    APP_LOG.info("Exception Getting Distractor value: " + e);
                }

            }
        } catch (Exception e) {
            APP_LOG.info("Exception Getting Distractor value: " + e);
        }
        return distractor;
    }

    /**
     * @author abhishek.sharda
     * @date 15 Dec 2017
     * @description :Method to return GLP ID for PI_ID
     * @return glpID
     */
    public synchronized String getGLPId(String userName, String password) {
        Response response = null;
        RequestSpecification spec = null;
        String outh2 = Oauth2(userName, password);
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            RequestSpecification request = given().spec(spec).config(rac)
                    .contentType(ContentType.JSON)
                    .header("Authorization", outh2)
                    .pathParam("pi_Id", getPiId(userName));
            for (int i = 1; i <= 3; i++) {
                response = request.request()
                        .get("/iamsi/v1/users?externalUserId={pi_Id}");
                APP_LOG.info(response.asString());
                if (response.statusCode() == 200) {
                    glpID = response.then().extract().body().jsonPath()
                            .getString("data.id").toString();
                    break;
                }

                else {
                    APP_LOG.info("Failed to retrieve glp learner id : "
                            + response.asString());
                    APP_LOG.info("Status code for get glp learner id : "
                            + response.statusCode());
                    TimeUnit.MINUTES.sleep(1);
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while retrieving glpLearnerId, as status line is - "
                                        + response.statusLine(),
                                "Verify glpLearnerId retrieval", reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendVerificationEmail API"
                    + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while retrieving glpLearnerId, as status line is - "
                            + response.statusLine()
                            + " and Exception thrown is : " + e.getMessage(),
                    "Verify glpLearnerId retrieval", reportTestObj);
        }
        return (glpID);

    }

    /**
     * @author mukul.sehra
     * @date 20 Dec 2017
     * @description :Method to get GLP Course ID
     * @param sectionId
     * @return glpCourseId
     */
    public synchronized String getGlpCourseId(String userName,
            String password) {
        try {
            sectionId = getCreatedCourseSectionId(userName, password);
            String getGlpCourseIdApi = glpCourseIdApi.replaceAll("sectionId",
                    sectionId);
            String outh2 = getOauthWithOutCourseId(userName, password);
            Response glpCourseResponse = null;
            RequestSpecification spec = null;
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 5; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("DomainURL"))
                        .build();
                glpCourseResponse = given().spec(spec).config(rac)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + outh2)
                        .get(getGlpCourseIdApi);
                if (glpCourseResponse.getStatusCode() == 200) {
                    glpCourseId = glpCourseResponse.then().extract().body()
                            .jsonPath().getString("data.glpCourseId")
                            .toString();
                    APP_LOG.debug(
                            "Successfully retrieved the glpCourseId for Instructor's course -> "
                                    + sectionId + " : " + glpCourseId);
                    break;
                } else {
                    APP_LOG.debug(
                            "Error while getting glpCourseId and following code returned: "
                                    + glpCourseResponse.getStatusCode() + ", "
                                    + glpCourseResponse.getStatusLine());
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while getting courseGLPId because : " + e);
            return null;
        }
        return glpCourseId;
    }

    /**
     * @author tarun.gupta
     * @description Method to create Course section
     * @param userName
     *            , password
     */
    public synchronized String createCourseSection(String userName,
            String password) {
        Response res = null;
        RequestSpecification spec = null;
        try {
            Map<String, String> createCourse = new HashMap<>();
            createCourse.put("courseSection", courseName);
            createCourse.put("automationProductCode", automationProductCode);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            for (int i = 0; i <= 3; i++) {
                res = given().spec(spec).contentType("application/json")
                        .body(alterJson("CourseCreation.json", createCourse))
                        .header("x-is-qa", "true")
                        .header("x-role", "instructor").header("DNT", "1")
                        .header("origin", "https://console-stg.pearson.com")
                        .header("Referer",
                                "https://console-stg.pearson.com/exchange/undefined/create?url=https%3A%2F%2Fexchange-stg.pearson.com%2Fproducts%2F0a381eda-d457-4145-be78-642c303ace32%2Frio-test-1%3Fuuid%3D0a381eda-d457-4145-be78-642c303ace32%26ref%3Dconsole%26returnurl%3Dhttps%3A%252F%252Fconsole-stg.pearson.com")
                        .header("authorization", webtoken_auth)
                        .post(courseSection);
                if (res.getStatusCode() == 200) {
                    long startTimeStamp = System.currentTimeMillis();
                    startTimeSeconds = TimeUnit.MILLISECONDS
                            .toSeconds(startTimeStamp);
                    APP_LOG.debug("Course Creation time:" + startTimeSeconds);
                    sectionId = res.then().extract().body().jsonPath()
                            .getString("section.id").toString();
                    this.result = Constants.PASS
                            + ": Successfully created new course for the user: "
                            + userName + "'";
                    logResultInReport(result,
                            "Verify new course creation for the user: "
                                    + userName,
                            reportTestObj);
                    APP_LOG.debug(
                            "Successfully hit courseSection api and retrieved sectionId as:"
                                    + sectionId);
                    TimeUnit.MINUTES.sleep(2);
                    break;
                } else {
                    APP_LOG.debug("Error while hitting courseSection api"
                            + res.getStatusCode() + ", " + res.getStatusLine());
                }
            }
            if (res.getStatusCode() != 200) {
                this.result = Constants.FAIL
                        + ": Course Creation failed for the user: " + userName
                        + "'";
                logResultInReport(result,
                        "Verify new course creation for the user: " + userName,
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Error while hitting courseSection api" + e);
        }
        Assert.assertTrue(
                res.getStatusCode() == 200 || res.getStatusCode() == 201,
                "Course Creation failed for the user");
        return sectionId;
    }

    /**
     * @author tarun.gupta1
     * @date 21 Dec 2017
     * @description :Method to retrieve invite key
     * @param auth
     * @param sectionid
     * @return inviteKey
     */
    public synchronized String inviteKey(String userName, String password) {
        Response response = null;
        RequestSpecification spec = null;
        RequestSpecification request = null;
        try {
            webtoken_auth = validateWebCredentials(userName, password);
            sectionId = getCreatedCourseSectionId(userName, password);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 3; i++) {
                request = given().spec(spec).contentType(ContentType.JSON)
                        .header("authorization", webtoken_auth).config(rac)
                        .pathParam("sectionid", sectionId);
                response = request.request()
                        .get("/api/coursesection/{sectionid}");
                APP_LOG.info(response.asString());
                if (response.statusCode() == 200) {
                    String sectionKey = response.then().extract().body()
                            .jsonPath().getString("sectionKeys.key").toString();
                    inviteKey = sectionKey.replace("[", "").replace("]", "")
                            .trim();
                    APP_LOG.debug(
                            "Successfully hit sendInvite api and retrieved inviteKey as :"
                                    + inviteKey);
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendInvite API"
                    + response.statusCode() + e);
        }
        return inviteKey;
    }

    /**
     * @author Abhishek.Sharda
     * @description Method to retrieve the access token which would be used as
     *              X-Authorization header
     * @return accessToken for X-Authorization header
     */
    public synchronized String getConsoleXAuth() {
        Response res = null;
        RequestSpecification spec = null;
        try {
            for (int i = 0; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("GetPidUrl"))
                        .build();
                res = given().spec(spec).contentType("application/json")
                        .body("\r\n{\"userName\":\""
                                + configurationsXlsMap
                                        .get("glConsoleUserForXAuth").trim()
                                + "\",\r\n\"password\":\""
                                + configurationsXlsMap
                                        .get("glConsolePasswordForXAuth").trim()
                                + "\"\r\n}")
                        .post(getConsoleXTokenApi);
                System.out.println(res.prettyPrint());
                APP_LOG.debug("Current Status code--> '\"" + res.getStatusCode()
                        + "\"");
                APP_LOG.debug("Current Try--> '\"" + (i + 1) + "\"");
                if (res.getStatusCode() == 201) {
                    break;
                }
            }
            String xAuth = res.then().extract().body().jsonPath()
                    .getString("data").toString();
            APP_LOG.debug(
                    "Extracted access token to be used for X-Authorization header --> '\""
                            + xAuth + "\"");
            return xAuth;
        }

        catch (Exception e) {
            APP_LOG.error("Exception in fetching x-auth token : " + e);
            return null;
        }
    }

    /**
     * @author Abhishek.sharda
     * @date 08 Mar 2017
     * @description :Method to retrieve invite key
     * @param auth
     * @param sectionid
     * @return inviteKey
     */
    public synchronized String getCorrelationID(String trackingID) {
        Response response = null;
        RequestSpecification spec = null;
        RequestSpecification request = null;
        consoleAuthToken = this.getConsoleXAuth();
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri("https://messaging-status-int.dev-prsn.com")
                    .build();
            for (int i = 1; i <= 3; i++) {
                request = given().spec(spec).contentType(ContentType.JSON)
                        .header("x-authorization", consoleAuthToken)
                        .pathParam("trackingID", trackingID);
                response = request.request()
                        .get("/messaging/activities/{trackingID}/status");
                APP_LOG.info(response.asString());
                if (response.statusCode() == 200) {
                    correlationID = response.then().extract().body().jsonPath()
                            .getString("correlationId").toString();
                    APP_LOG.debug("Successfully fetched correlationID  :"
                            + correlationID);
                    if (correlationID != null) {
                        this.result = Constants.PASS
                                + " Sucessfully fetched correlationID from load unload activity";
                        logResultInReport(this.result,
                                "Verify correlationID from laod unload activity",
                                this.reportTestObj);
                    } else {
                        this.result = Constants.FAIL
                                + " Failed to fetched correlationID from laod unload activity";
                        logResultInReport(this.result,
                                "Verify correlationID from laod unload activity",
                                this.reportTestObj);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendInvite API"
                    + response.statusCode() + e);
        }
        return correlationID;
    }

    /**
     * @author sumit.bhardwaj
     * @description Method to retrieve the access token for specific user which
     *              would be used as X-Authorization header
     * @return accessToken for X-Authorization header
     */
    public synchronized String getCreatedCourseSectionId(String username,
            String password) {
        Response res = null;
        RequestSpecification spec = null;
        String sectionId = null;
        try {
            for (int i = 0; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleUrl"))
                        .build();
                RestAssuredConfig rac = RestAssured.config()
                        .sslConfig(new SSLConfig().allowAllHostnames()
                                .relaxedHTTPSValidation("TLSv1.2"));
                res = given().spec(spec).config(rac)
                        .contentType("application/json")
                        .contentType("application/json")
                        .header("X-Authorization",
                                getConsoleXAuthtoken(username, password))
                        .get(courseSection);
                APP_LOG.debug("Current Status code--> '\"" + res.getStatusCode()
                        + "\"");
                APP_LOG.debug("Current Try--> '\"" + (i + 1) + "\"");
                if (res.getStatusCode() == 201 || res.getStatusCode() == 200) {
                    break;
                }
            }

            List<String> jocksIn = JsonPath.read(res.body().asString(),
                    "$.courses[*].section.[?(@.sectionStatus==\"active\")].sectionId");
            if (jocksIn.size() != 0) {
                sectionId = jocksIn.get(0);
            }

            if (jocksIn.size() == 0) {
                List<String> jocks = JsonPath.read(res.body().asString(),
                        "$.courses[*].section.sectionId");
                sectionId = jocks.get(0);
            }

            APP_LOG.debug("Extracted sectionId for existing user - " + username
                    + "--> \"" + sectionId + "\"");
            return sectionId;
        }

        catch (Exception e) {
            APP_LOG.error("Exception in extracting sectionId  : " + e);
            return null;
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param username->
     *            name of user
     * @param password->
     *            password of user
     * @param sectionId->
     *            sectionId
     * @return glpCourseId
     */
    public synchronized String getGlpCourseId(String userName, String password,
            String sectionId) {
        String glpCourseId = null;
        String outh2 = getOauthWithOutCourseId(userName, password);
        try {
            // xAuthToken = getXAuth();
            String getGlpCourseIdApi = glpCourseIdApi.replaceAll("sectionId",
                    sectionId);
            Response glpCourseResponse = null;
            RequestSpecification spec = null;
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 6; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("DomainURL"))
                        .build();
                glpCourseResponse = given().spec(spec).config(rac)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + outh2)
                        .get(getGlpCourseIdApi);
                if (glpCourseResponse.getStatusCode() == 200) {

                    long timeMillis = System.currentTimeMillis();
                    finalTimeSeconds = TimeUnit.MILLISECONDS
                            .toSeconds(timeMillis);
                    glpCourseId = glpCourseResponse.then().extract().body()
                            .jsonPath().getString("data.glpCourseId")
                            .toString();
                    APP_LOG.debug(
                            "Successfully retrieved the glpCourseId for Instructor's course -> "
                                    + sectionId + " : " + glpCourseId);
                    break;
                } else {
                    APP_LOG.debug(
                            "Error while getting glpCourseId and following code returned: "
                                    + glpCourseResponse.getStatusCode() + ", "
                                    + glpCourseResponse.getStatusLine());
                    TimeUnit.SECONDS.sleep(30);
                    if (i == 6) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while getting the glpCourseId : "
                                        + glpCourseId + ",  status code - "
                                        + glpCourseResponse.statusCode() + " - "
                                        + glpCourseResponse.statusLine(),
                                "Verify glpCourseId retrieval", reportTestObj);
                    }
                }
            }

        } catch (Exception e) {
            APP_LOG.error("Exception while getting courseGLPId because : " + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting the glpCourseId : "
                            + glpCourseId + ", because of - " + e.getMessage(),
                    "Verify glpCourseId retrieval", reportTestObj);
            return null;
        }
        return glpCourseId;
    }

    /**
     * @author sumit.bhardwaj
     * @description Method to retrieve the access token for specific user which
     *              would be used as X-Authorization header
     * @return accessToken for X-Authorization header
     */
    public synchronized String getConsoleXAuthtoken(String username,
            String password) {
        Response res = null;
        RequestSpecification spec = null;
        try {
            for (int i = 0; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleAPI"))
                        .build();
                res = given().spec(spec).contentType("application/json")
                        .body("\r\n{\"userName\":\"" + username.trim()
                                + "\",\r\n\"password\":\"" + password.trim()
                                + "\"\r\n}")
                        .post(getConsoleXTokenApi);
                APP_LOG.debug("Current Status code--> '\"" + res.getStatusCode()
                        + "\"");
                APP_LOG.debug("Current Try--> '\"" + (i + 1) + "\"");
                if (res.getStatusCode() == 201) {
                    break;
                }
            }
            if (res.getStatusCode() != 201) {
                APP_LOG.info(
                        "Error while retrieving the Console Xauth Token, Status code - "
                                + res.getStatusCode());
                logResultInReport(
                        Constants.FAIL
                                + " : Error while retrieving the Console Xauth Token, Status code : "
                                + res.statusCode() + ", " + res.getStatusLine(),
                        "Verify Console Xauth token retrieval", reportTestObj);
            }
            String xAuth = res.then().extract().body().jsonPath()
                    .getString("data").toString();
            APP_LOG.debug(
                    "Extracted access token to be used for X-Authorization header --> '\""
                            + xAuth + "\"");
            return xAuth;
        }

        catch (Exception e) {
            APP_LOG.error("Exception in fetching x-auth token : " + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while retrieving the Console Xauth Token, Status code : "
                            + res.statusCode() + ", " + e.getMessage(),
                    "Verify Console Xauth token retrieval", reportTestObj);
            return null;
        }
    }

    /**
     * @author mukul.sehra
     * @description Method to Lock Diagnostic Test
     * @params glpCourseId, xAuthToken
     */
    public synchronized void lockDiagnosticTest(String glpCourse,
            String xAuth) {
        APP_LOG.debug("Lock course api.....");
        Response res = null;
        RequestSpecification spec = null;
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));

        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            res = given().spec(spec).contentType("application/json")
                    .header("Authorization", xAuth)
                    .body("\r\n{\"assessmentStatus\": \"locked\" \r\n}")
                    .config(rac).post(assessmentLockApi);
            assessmentLockApi = assessmentLockApi.replaceAll("glpCourseId",
                    glpCourse);
            APP_LOG.debug(
                    "Lock Api status --> '\"" + res.getStatusCode() + "\"");
            if (res.getStatusCode() == 200) {
                logResultInReport(
                        Constants.PASS + ": Diagnostic Test has been Locked",
                        "Lock the Diagnostic Test for the Learner",
                        reportTestObj);
            } else {
                logResultInReport(Constants.FAIL
                        + ": Diagnostic test didn't Lock because : "
                        + res.getStatusCode() + " - " + res.getStatusLine(),
                        "Lock the Diagnostic Test for the Learner",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception in locking assessment : " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while Locking Diagnostic Test - "
                            + e.getMessage() + " - " + res.getStatusCode()
                            + " - " + res.getStatusLine(),
                    "Lock the Diagnostic Test for the Learner", reportTestObj);
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 09 Feb 2017
     * @description :Method to return Subscription Id for learner
     * @return subscriptionId
     */
    public synchronized String getSubscriptionId(String userName,
            String password) {
        Response response = null;
        RequestSpecification spec = null;
        String subscriptionId = "";
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            String glpId = getGLPId(userName, password);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            for (int i = 1; i <= 40; i++) {
                response = given().spec(spec).contentType("application/json")
                        .header("Authorization", Oauth2(userName, password))
                        .config(rac).get("/lcd/v1/assets?learnerId=" + glpId);
                APP_LOG.info(response.asString());
                if (response.statusCode() == 200) {
                    subscriptionId = response.then().extract().body().jsonPath()
                            .getString("data.id").toString();
                    subscriptionId = subscriptionId.replace("[", "")
                            .replace("]", "");
                    APP_LOG.info(subscriptionId);
                    break;
                }
                TimeUnit.SECONDS.sleep(2);
            }
            if (response.statusCode() != 200) {
                APP_LOG.info(
                        "Error while retrieving the subscription Id, Status code - "
                                + response.statusCode());
                APP_LOG.info("subscription id : " + subscriptionId);
                logResultInReport(Constants.FAIL
                        + " : Error while getting the subscription id : "
                        + subscriptionId + ",  status code - "
                        + response.statusCode() + " - " + response.statusLine(),
                        "Verify subscription id retrieval", reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured in Fetching subscription ID from the console API"
                            + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting the subscription id : "
                            + subscriptionId + ",  status code - "
                            + response.statusCode() + " - " + e.getMessage(),
                    "Verify subscription id retrieval", reportTestObj);

        }
        return subscriptionId;

    }

    /**
     * @author nitish.jaiswal
     * @date 09 Feb 2017
     * @description :Method to return diagnostic score
     * @return diagnosticAttemptedScore
     */
    public synchronized String getDiagnosticAttemptedScore(String userName,
            String password) {
        Response response = null;
        RequestSpecification spec = null;
        String diagnosticAttemptedScore = "";
        String courseId = "";
        String glpId = "";
        String SubscriptionId = "";
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            String sectionId = getCreatedCourseSectionId(userName, password);
            courseId = getGlpCourseId(userName, password, sectionId);
            glpId = getGLPId(userName, password);
            SubscriptionId = getSubscriptionId(userName, password);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            response = given().spec(spec).contentType("application/json")
                    .header("Authorization", Oauth2(userName, password))
                    .config(rac).get("/lae/v1/outcomes/" + courseId + glpId
                            + SubscriptionId + "learnercourseoutcomes");
            APP_LOG.info(response.asString());
            if (response.statusCode() == 200) {
                // courseName = response.path("data.outcomesValue" + "." +
                // getGlpCourseId() + "." + "courseTitle");
                // courseName = "RIO-DIAGNOSTIC-TEST-M0-New";
                diagnosticAttemptedScore = response.path(
                        "data.outcomesValue" + "." + courseId + "." + "score");
                APP_LOG.info(diagnosticAttemptedScore);

            } else {
                APP_LOG.info(
                        "Error while getting score of attempted questions in diagnostic test, score is : "
                                + diagnosticAttemptedScore);
                logResultInReport(Constants.FAIL
                        + " : Error while getting the score of Diagnostic test - "
                        + diagnosticAttemptedScore + ",  status code - "
                        + response.statusCode() + " - " + response.statusLine(),
                        "Verify score acheived after attempting Diagnostic Test",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured in sendVerificationEmail API fetching subscription API"
                            + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting the score of Diagnostic test - "
                            + diagnosticAttemptedScore + ",  status code - "
                            + response.statusCode() + " - " + e.getMessage(),
                    "Verify score acheived after attempting Diagnostic Test",
                    reportTestObj);
        }
        return diagnosticAttemptedScore;

    }

    /**
     * @author sumit.bhardwaj
     * @param username
     *            --> name of user
     * @param password
     *            --> password of user
     * @return --> Bearer + oAuth2
     */
    public synchronized String Oauth2(String username, String password) {
        RequestSpecification spec = null;
        Response response = null;
        spec = new RequestSpecBuilder()
                .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {

            for (int i = 0; i <= 6; i++) {
                response = given().spec(spec).config(rac).auth().preemptive()
                        .basic("console", "pearson")
                        .formParam("grant_type", "password")
                        .formParam("username", getPiId(username))
                        .formParam("password",
                                getConsoleXAuthtoken(username, password))
                        .formParam("courseId",
                                getGlpCourseId(username,
                                        password, getCreatedCourseSectionId(
                                                username, password)))
                        .post(getIamsiToken);
                if (response.getStatusCode() == 200) {
                    break;
                }
            }
            String oAuth2 = response.then().extract().body().jsonPath()
                    .getString("data.access_token").toString();
            APP_LOG.info("Outh Genrated" + "Bearer " + oAuth2);
            return "Bearer " + oAuth2;
        } catch (Exception e) {
            APP_LOG.error("Exception in fetching Oath2 token via : "
                    + getIamsiToken + " : " + e);
            logResultInReport(
                    Constants.FAIL + " : Exception in fetching Oath2 token via "
                            + getIamsiToken + ", because status code : "
                            + e.getMessage(),
                    "Verify Outh2 token sucessfully generated", reportTestObj);
            return null;
        }
    }

    /**
     * @author sumit.bhardwaj
     * @param username
     *            --> name of user
     * @param password
     *            --> password of user
     * @return --> Access token generated without glp course id
     */
    public synchronized String getOauthWithOutCourseId(String username,
            String password) {
        RequestSpecification spec = null;
        Response res = null;
        RestAssuredConfig config = null;
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            String piID = getPiId(username);
            for (int i = 0; i <= 6; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("DomainURL"))
                        .build();
                APP_LOG.info(
                        "Trying to find access token to be used for get Oath2 token");
                config = new RestAssuredConfig().encoderConfig(
                        EncoderConfig.encoderConfig().encodeContentTypeAs(
                                "x-www-form-urlencoded", ContentType.URLENC));
                res = given().spec(spec).config(config).config(rac)
                        .contentType(ContentType.URLENC.withCharset("UTF-8"))
                        .auth().preemptive().basic("system", "glpApp")
                        .formParam("grant_type", "password")
                        .formParam("username", piID)
                        .formParam("password", "allowMeAccess").config(rac)
                        .post(getIamsiToken);
                if (res.getStatusCode() == 200) {
                    break;
                }
            }
            String xAuth = res.then().extract().body().jsonPath()
                    .getString("data.access_token").toString();
            APP_LOG.debug(
                    "Extracted access token to be used for get Oath2 token--> '\""
                            + xAuth + "\"");
            return xAuth;
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception in fetching token to be used for get Oath2 token : "
                            + e);
            logResultInReport(
                    Constants.FAIL + " : Exception in fetching Oath2 token via "
                            + getIamsiToken + ", because status code : "
                            + e.getMessage(),
                    "Verify the Outh2 token '", reportTestObj);
            return null;
        }
    }

    /**
     * @author Saurabh.Sharma
     * @description Method to create grade book template at learner model level
     *              through the API
     * @date 21th Feb 2018
     * @return Template Id of the template created
     */
    public synchronized String createGradebookTemplate() {
        String templateId = "";
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            // Adding the required headers into a map
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "application/json");
            headers.put("Authorization",
                    Oauth2(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                            configurationsXlsMap.get("INSTRUCTOR_PASSWORD")));
            // Adding the LearnerModelId to a map
            Map<String, String> JSONKeys = new HashMap<>();
            JSONKeys.put("Id_LearnerModel",
                    configurationsXlsMap.get("LearnerModelId"));

            // Get the base URL for the request
            RequestSpecification spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            // Fetch the response
            Response createGBTemplateResponse = given().spec(spec)
                    .headers(headers).config(rac)
                    .body(alterJson("CreateGBTemplate.json", JSONKeys))
                    .post(templateApiUri);

            APP_LOG.debug("Status code for GB Template Creation is: "
                    + createGBTemplateResponse.getStatusCode());
            if (createGBTemplateResponse.getStatusCode() == 200) {
                templateId = createGBTemplateResponse.getBody().jsonPath()
                        .getString("data.id");
                this.result = Constants.PASS
                        + ": Successfully created template with Id: "
                        + templateId;
                logResultInReport(result, "Verify GB template creation",
                        reportTestObj);
                APP_LOG.debug(
                        "Successfully created template with Id: " + templateId);

            } else if (createGBTemplateResponse.getStatusCode() == 409) {
                this.result = Constants.PASS
                        + ": Template is already created for the learner model";
                logResultInReport(result, "Verify GB template deletion",
                        reportTestObj);
                APP_LOG.debug(
                        "Template is already created for the learner model");

            } else {
                this.result = Constants.FAIL
                        + ": Unable to create gradebook template.";
                logResultInReport(result,
                        "Unable to create gradebook template" + "because :"
                                + createGBTemplateResponse.getStatusLine(),
                        reportTestObj);
                APP_LOG.debug(
                        "Gradebook template creation failed because of following error: "
                                + createGBTemplateResponse.getStatusLine());
            }
        } catch (Exception e) {
            logResultInReport(Constants.FAIL,
                    "Exception creating gradebook template: " + e.getMessage(),
                    reportTestObj);
            APP_LOG.error(
                    "Exception creating gradebook template: " + e.getMessage());
        }
        return templateId;
    }

    /**
     * @author Saurabh.Sharma
     * @description Method to delete grade book template through the API
     * @date 21th Feb 2018
     * @params templateId - The id of the template to be deleted
     */
    public synchronized void deleteGradebookTemplate(String templateId) {
        try {
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            // Adding the required headers into a map
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("cache-control", "no-cache");
            headers.put("Authorization",
                    Oauth2(configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                            configurationsXlsMap.get("INSTRUCTOR_PASSWORD")));
            // Adding
            // Get the base URL for the request
            RequestSpecification spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();

            // Fetch the response
            Response deleteGBTemplateResponse = given().spec(spec).config(rac)
                    .headers(headers).param("templateId", templateId)
                    .delete(templateApiUri);
            APP_LOG.debug("Status code for GB Template Deletion is: "
                    + deleteGBTemplateResponse.getStatusCode());

            if (deleteGBTemplateResponse.getStatusCode() == 200) {
                this.result = Constants.PASS
                        + ": Successfully deleted template with Id: "
                        + templateId;
                logResultInReport(result, "Verify GB template deletion",
                        reportTestObj);
                APP_LOG.debug(
                        "Successfully deleted template with Id: " + templateId);

            } else if (deleteGBTemplateResponse.getStatusCode() == 404) {
                this.result = Constants.PASS + ": Template with Id: "
                        + templateId + " is already deleted";
                logResultInReport(result, "Verify GB template deletion",
                        reportTestObj);
                APP_LOG.debug("Template with Id: " + templateId
                        + " is already deleted");
            } else {
                this.result = Constants.FAIL
                        + ": Unable to delete gradebook template.";
                logResultInReport(result,
                        "Unable to delete gradebook template " + templateId
                                + " because: "
                                + deleteGBTemplateResponse.getStatusLine(),
                        reportTestObj);
                APP_LOG.debug("Unable to delete gradebook template "
                        + templateId + " because: "
                        + deleteGBTemplateResponse.getStatusLine());
            }
        } catch (Exception e) {
            logResultInReport(Constants.FAIL,
                    "Exception occured while deleting gradebook template: "
                            + templateId + e.getMessage(),
                    reportTestObj);
            APP_LOG.error("Exception in deleting gradebook template: "
                    + templateId + e.getMessage());
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 12 Mar 2018
     * @description :Method to retrieve invite key
     * @param auth
     * @param sectionid
     * @return inviteKey
     */
    public synchronized String getInviteKey(String consoleXAuth,
            String courseSectionId) {
        Response response = null;
        RequestSpecification spec = null;
        RequestSpecification request = null;
        try {
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("ConsoleUrl")).build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            for (int i = 1; i <= 3; i++) {
                request = given().spec(spec).contentType(ContentType.JSON)
                        .header("authorization", consoleXAuth).config(rac)
                        .pathParam("sectionid", courseSectionId);
                response = request.request()
                        .get("/api/coursesection/{sectionid}");
                APP_LOG.info(response.asString());
                if (response.statusCode() == 200) {
                    String sectionKey = response.then().extract().body()
                            .jsonPath().getString("sectionKeys.key").toString();
                    inviteKey = sectionKey.replace("[", "").replace("]", "")
                            .trim();
                    APP_LOG.debug(
                            "Successfully hit sendInvite api and retrieved inviteKey as :"
                                    + inviteKey);
                    break;
                }
            }
            if (response.statusCode() != 200) {
                APP_LOG.info(
                        "Error while fetching Invite key corresponding the sectionId : "
                                + courseSectionId + "; status code is : "
                                + response.statusCode());
                logResultInReport(
                        Constants.FAIL
                                + " : Error while fetching Invite key corresponding the sectionId : "
                                + courseSectionId + "; status code is : "
                                + response.statusCode(),
                        "Verify Invite key retrieval", reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in sendInvite API"
                    + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while fetching Invite key corresponding the sectionId : "
                            + courseSectionId + ", because : " + e.getMessage(),
                    "Verify Invite key retrieval", reportTestObj);
        }
        return inviteKey;
    }

    /**
     * @author mukul.sehra
     * @description Method to lock/unlock diagnostic test for a particular user
     * @param userName,
     *            lockStatus.
     */
    public synchronized void lockUnlockDiagnosticForLearner(String userName,
            String lockStatus) {
        APP_LOG.info("Inside lockUnlockDiagnosticForLearner method....");
        Response lockUnlockResponse = null;
        RequestSpecification spec = null;
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            String password = ResourceConfigurations
                    .getProperty("consolePassword");
            String courseId = getGlpCourseId(userName, password,
                    getCreatedCourseSectionId(userName, password));
            Long diffSec = finalTimeSeconds - startTimeSeconds;
            long sec = diffSec / 60;
            APP_LOG.debug("GLP ID Creation time:" + diffSec);
            String learnerId = getGLPId(userName, password);
            lockUnlockApi = lockUnlockApi.replace("glp-courseId", courseId);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            String requestJsonBody = "";
            if (lockStatus.equals(
                    ResourceConfigurations.getProperty("statusUnlocked"))) {
                requestJsonBody = "{  \r\n   \"courseId\":\"courseIdLearner\",\r\n   \"unlockAll\":\"false\",\r\n   \"assessmentType\":\"preAssessment\",\r\n   \"lockStatus\":\"unlocked\",\r\n   \"assessmentModulesModels\":[  \r\n      {  \r\n         \"learnerId\":\"userLearnerId\",\r\n         \"modules\":[  \r\n            \"\"\r\n         ]\r\n      }\r\n   ]\r\n}";
            } else {
                requestJsonBody = "{  \r\n   \"courseId\":\"courseIdLearner\",\r\n   \"unlockAll\":\"false\",\r\n   \"assessmentType\":\"preAssessment\",\r\n   \"lockStatus\":\"locked\",\r\n   \"assessmentModulesModels\":[  \r\n      {  \r\n         \"learnerId\":\"userLearnerId\",\r\n         \"modules\":[  \r\n            \"\"\r\n         ]\r\n      }\r\n   ]\r\n}";
            }
            requestJsonBody = requestJsonBody
                    .replaceAll("courseIdLearner", courseId)
                    .replaceAll("userLearnerId", learnerId);
            for (int i = 1; i <= 4; i++) {
                lockUnlockResponse = given().spec(spec)
                        .contentType("application/json").config(rac)
                        .header("Authorization", Oauth2(userName, password))
                        .body(requestJsonBody).post(lockUnlockApi);
                APP_LOG.info(lockUnlockResponse.body().asString());
                if (lockUnlockResponse.statusCode() == 200) {
                    APP_LOG.info("Successfully '" + lockStatus
                            + "' diagnostic test for learner : " + userName);
                    logResultInReport(
                            Constants.PASS + ": Diagnostic Test has been '"
                                    + lockStatus.toUpperCase()
                                    + "' successfully. And total time taken to fetch GLP Course ID is "
                                    + sec + "Seconds",
                            "Verify that the Diagnostic Test has been '"
                                    + lockStatus.toUpperCase()
                                    + "' for the Learner",
                            reportTestObj);
                    break;
                } else {
                    APP_LOG.info("lock/unlock learner API failed for the user");
                    TimeUnit.SECONDS.sleep(30);
                    if (i == 4) {
                        logResultInReport(Constants.FAIL
                                + " : Error while getting course "
                                + lockStatus.toUpperCase() + " for learner : "
                                + userName + ", because status code : "
                                + lockUnlockResponse.statusCode(),
                                "Verify the Diagnostic test for learner '"
                                        + userName + "' has been unlocked",
                                reportTestObj);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception occured while lock/unlock learner "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL + " : Exception while getting course "
                            + lockStatus.toUpperCase() + " for learner : "
                            + userName + ", because status code : "
                            + lockUnlockResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify the Diagnostic test for learner '" + userName
                            + "' has been unlocked",
                    reportTestObj);

        }
    }

    /**
     * @author mukul.sehra
     * @date 26 Mar 2018
     * @description :creating test user for learner and subscribe course
     *              specific to instructor with course unlocked if
     *              unlockRequired param is set to true
     * @param userName,
     *            password, userRole, instructorName, unlockRequired
     */
    public synchronized void createLearnerAndSubscribeCourse(String userName,
            String password, String userRole, String instructorName,
            boolean unlockRequired) {
        try {
            createConsoleUserViaApi(userName, password, userRole);
            validateWebCredentials(userName, password);
            sendVerificationEmail(userName, userRole);
            glpConsoleValidateEmail(userRole, userName);
            courseEnrollement(userRole, userName, instructorName);
            if (unlockRequired) {
                lockUnlockDiagnosticForLearner(userName,
                        ResourceConfigurations.getProperty("statusUnlocked"));
            }

        } catch (Exception e) {
            this.result = Constants.FAIL
                    + ": User Creation and subscribe course failed for"
                    + userName + "'";
            logResultInReport(result,
                    "Verify user creation and course subscription for: "
                            + userName,
                    reportTestObj);
            APP_LOG.error(
                    "Exception occured in learner User creation With Email Validation: "
                            + e);
        }
    }

    /**
     * @author mukul.sehra
     * @date 26 March 2018
     * @description : Method for course registration specific to Instructor
     * @param userRole,
     *            learnerName, instructorName
     */
    public synchronized void courseEnrollement(String userRole, String userName,
            String instructorName) {
        Response courseRegisterResponse = null;
        RequestSpecification spec = null;
        try {

            String instructorInviteKey = null;
            String instructorSectionId = null;
            instructorSectionId = getCreatedCourseSectionId(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"));

            instructorInviteKey = getInviteKey(
                    getConsoleXAuthtoken(instructorName,
                            ResourceConfigurations
                                    .getProperty("consolePassword")),
                    instructorSectionId);
           /* if (executionEnviroment.toUpperCase().contains("INT")) {
                instructorSectionId = getCreatedCourseSectionId(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));

                instructorInviteKey = getInviteKey(getConsoleXAuthtoken(),
                        instructorSectionId);

            } else {
                instructorSectionId = getCreatedCourseSectionId(instructorName,
                        ResourceConfigurations.getProperty("consolePassword"));

                instructorInviteKey = getInviteKey(
                        getConsoleXAuthtoken(instructorName,
                                ResourceConfigurations
                                        .getProperty("consolePassword")),
                        instructorSectionId);
            }*/
            for (int i = 0; i <= 3; i++) {
                spec = new RequestSpecBuilder()
                        .setBaseUri(configurationsXlsMap.get("ConsoleUrl"))
                        .build();
                RestAssuredConfig rac = RestAssured.config()
                        .sslConfig(new SSLConfig().allowAllHostnames()
                                .relaxedHTTPSValidation("TLSv1.2"));
                courseRegisterResponse = given().spec(spec)
                        .contentType("application/json")
                        .header("x-is-qa", "true").header("x-role", userRole)
                        .header("DNT", "1")
                        .header("Referer",
                                "https://console-qa.pearsoned.com/enrollment/"
                                        + instructorInviteKey)
                        .header("authorization", webtoken_auth).config(rac)
                        .post(courseRegisterApi.replaceAll("sectionId",
                                instructorSectionId));
                APP_LOG.debug(courseRegisterResponse.asString());
                if (courseRegisterResponse.getStatusCode() == 200) {
                    APP_LOG.debug(
                            "Successfully subscribed course with section id : '"
                                    + instructorSectionId + "' and inviteKey : "
                                    + instructorInviteKey);
                    this.result = Constants.PASS
                            + ": Successfully subscribed course : '"
                            + instructorSectionId + "' for the username '"
                            + userName + "' using inviteKey : "
                            + instructorInviteKey;
                    logResultInReport(result,
                            "Verify course subscription for the user: "
                                    + userName,
                            reportTestObj);

                    APP_LOG.debug("Successfully registered course");
                    TimeUnit.SECONDS.sleep(60);
                    break;
                } else {
                    APP_LOG.debug("Error while registering course : "
                            + courseRegisterResponse.getStatusCode() + ", "
                            + courseRegisterResponse.getStatusLine());
                }
            }
            if (courseRegisterResponse.getStatusCode() != 200) {
                this.result = Constants.FAIL
                        + ": Course Creation failed for the user: '" + userName
                        + "'";
                logResultInReport(result,
                        "Verify new course creation for the user: " + userName,
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error("Exception while registering course : " + e);
            logResultInReport(
                    Constants.FAIL
                            + ": Exception while Course Creation for the user: '"
                            + userName + "', " + e.getMessage(),
                    "Verify new course creation for the user: " + userName,
                    reportTestObj);
        }

    }

    /**
     * @author mukul.sehra
     * @date 11 Apr, 2018
     * @description Method to lock/unlock post-assessment for a particular user
     * @param userName,
     *            lockStatus, chapterNumber
     */
    public synchronized void lockUnlockPostAssessmentForLearner(String userName,
            String lockStatus, String chapterNumber) {
        APP_LOG.info("Inside lockUnlockPostAssessmentForLearner method....");
        Response lockUnlockResponse = null;
        RequestSpecification spec = null;
        String moduleNumber = "";
        Map<String, Object> courseData = new HashMap<String, Object>();
        try {
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            // Getting the modules available in the course and other course
            // related data
            courseData = getModules(userName);
            String oauthToken = courseData.get("oauthToken").toString();
            String courseId = courseData.get("courseId").toString();
            String learnerId = courseData.get("learnerId").toString();
            @SuppressWarnings("unchecked")
            List<String> modules = (List<String>) courseData.get("modules");
            lockUnlockApi = lockUnlockApi.replace("glp-courseId", courseId);

            // Getting the module id per the chapter number provided
            for (String moduleIterator : modules) {
                if (moduleIterator.contains("CHAPTER-" + chapterNumber + "-")) {
                    moduleNumber = moduleIterator;
                    break;
                }
            }

            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", oauthToken)
                    .setContentType("application/json").setConfig(rac).build();
            String requestJsonBody = "";
            if (lockStatus.equals(
                    ResourceConfigurations.getProperty("statusUnlocked"))) {
                requestJsonBody = "{\n\n  \"unlockAll\": \"false\",\n\n  \"assessmentType\": \"postAssessment\",\n\n  \"lockStatus\": \"unlocked\",\n\n  \"assessmentModulesModels\": [\n\n    {\n\n      \"learnerId\": \"userLearnerId\",\n\n      \"modules\": [\n\n        \"moduleNumber\"\n\n      ]\n\n    }\n   \n  ]\n\n}";
            } else {
                requestJsonBody = "{\n\n  \"unlockAll\": \"false\",\n\n  \"assessmentType\": \"postAssessment\",\n\n  \"lockStatus\": \"locked\",\n\n  \"assessmentModulesModels\": [\n\n    {\n\n      \"learnerId\": \"userLearnerId\",\n\n      \"modules\": [\n\n        \"moduleNumber\"\n\n      ]\n\n    }\n   \n  ]\n\n}";
            }
            requestJsonBody = requestJsonBody
                    .replaceAll("moduleNumber", moduleNumber)
                    .replaceAll("userLearnerId", learnerId);
            for (int i = 1; i <= 3; i++) {
                lockUnlockResponse = given().spec(spec).body(requestJsonBody)
                        .post(lockUnlockApi);
                APP_LOG.info(lockUnlockResponse.body().asString());
                if (lockUnlockResponse.statusCode() == 200) {
                    APP_LOG.info("Successfully '" + lockStatus
                            + "' post-assessment learner : " + userName);
                    logResultInReport(
                            Constants.PASS + ": Post-assessment has been '"
                                    + lockStatus.toUpperCase()
                                    + "' successfully",
                            "Verify that the Post-assessment has been '"
                                    + lockStatus.toUpperCase()
                                    + "' for the Learner",
                            reportTestObj);
                    break;
                } else {
                    APP_LOG.info("lock/unlock learner API failed for the user");
                    if (i == 3) {
                        logResultInReport(Constants.FAIL
                                + " : Error while getting post-assessment "
                                + lockStatus.toUpperCase() + " for learner : "
                                + userName + ", because status code : "
                                + lockUnlockResponse.statusCode(),
                                "Verify that the Post-assessment has been '"
                                        + lockStatus.toUpperCase()
                                        + "' for the Learner",
                                reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception occured while lock/unlock learner "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting post-assessment "
                            + lockStatus.toUpperCase() + "for learner : "
                            + userName + ", because status code : "
                            + lockUnlockResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify that the Post-assessment has been '"
                            + lockStatus.toUpperCase() + "' for the Learner",
                    reportTestObj);
        }
    }

    /**
     * @author mukul.sehra
     * @date 11 Apr, 2018
     * @description Method to get Module Ids, It returns a MAP; to retrieve list
     *              of Modules/Chapters, you need to get key 'modules' and
     *              typeCast it to List<String>
     * @param userName
     * @return Map containing modules/Chapter Ids
     */
    private synchronized Map<String, Object> getModules(String userName) {
        APP_LOG.info("Inside getModules method....");
        Response getModulesResponse = null;
        RequestSpecification spec = null;
        List<String> modules = new ArrayList<String>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String password = "";
        String courseId = "";
        String learnerId = "";
        String subscriptionId = "";
        String getModulesApi = "";
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            password = ResourceConfigurations.getProperty("consolePassword");
            courseId = getGlpCourseId(userName, password,
                    getCreatedCourseSectionId(userName, password));
            learnerId = getGLPId(userName, password);
            subscriptionId = getSubscriptionId(userName, password);
            String oauthToken = Oauth2(userName, password);

            // Adding the data to a MAP
            dataMap.put("courseId", courseId);
            dataMap.put("learnerId", learnerId);
            dataMap.put("oauthToken", oauthToken);
            dataMap.put("subscriptionId", subscriptionId);
            getModulesApi = "/lcd/v1/course/" + courseId + "/learner/"
                    + learnerId + "/subscriptionId/" + subscriptionId
                    + "/studyplan/?pageName=COURSE_HOME";
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", oauthToken)
                    .setContentType("application/json").setConfig(rac).build();
            for (int i = 1; i <= 3; i++) {
                getModulesResponse = given().spec(spec).get(getModulesApi);
                APP_LOG.info(getModulesResponse.body().asString());
                if (getModulesResponse.statusCode() == 200) {
                    modules = getModulesResponse.then().extract().body()
                            .jsonPath()
                            .getList("data.outcomes.studyPlan.objId");
                    dataMap.put("modules", modules);
                    APP_LOG.info(
                            "Retrieved all the available modules for the course : "
                                    + courseId + " - > " + modules);
                    break;
                } else {
                    APP_LOG.info("getModules API failed...");
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while retrieving the modules available in course - "
                                        + courseId + ", because status code : "
                                        + getModulesResponse.statusCode(),
                                "Verify modules retrieval for the course '"
                                        + courseId + "'",
                                reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info(
                    "Exception occured while retrieving modules for course : "
                            + courseId + ", because of exception : "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while retrieving modules for course : "
                            + courseId + ", because status code : "
                            + getModulesResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify modules retrieval for the course : " + courseId,
                    reportTestObj);
        }
        return dataMap;
    }

    /**
     * @author mukul.sehra
     * @date 11 Apr, 2018
     * @description Method to get LO Section Ids, It returns a MAP; to retrieve
     *              list of loLAIDs, you need to get key 'loLAIDs' and typeCast
     *              it to List<String> and then iterate over List of List
     * @param userName,
     *            chapterNumber
     * @return Map containing loLAIDs
     */
    private synchronized Map<String, Object> getLoSectionIds(String userName,
            String loNumber) {
        APP_LOG.info("Inside getLoSection method....");
        Response getLoIdsResponse = null;
        RequestSpecification spec = null;
        String moduleNumber = "";
        String getLoSectionIdsApi = "";
        String courseId = "";
        Map<String, Object> courseData = new HashMap<String, Object>();
        try {
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            // Getting the modules available in the course and other course
            // related data
            courseData = getModules(userName);
            String oauthToken = courseData.get("oauthToken").toString();
            courseId = courseData.get("courseId").toString();
            String learnerId = courseData.get("learnerId").toString();
            String subscriptionId = courseData.get("subscriptionId").toString();
            @SuppressWarnings("unchecked")
            List<String> modules = (List<String>) courseData.get("modules");

            // Getting the module id per the chapter number provided
            for (String moduleIterator : modules) {
                if (moduleIterator.contains(
                        "CHAPTER-" + loNumber.split("\\.")[0] + "-")) {
                    moduleNumber = moduleIterator;
                    break;
                }
            }
            getLoSectionIdsApi = "/lcd/v1/loByLAId/" + moduleNumber
                    + "?learnerId=" + learnerId + "&courseId=" + courseId
                    + "&subscriptionId=" + subscriptionId
                    + "&docType=learnercourse";

            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", oauthToken)
                    .setContentType("application/json").setConfig(rac).build();
            List<String> loLAIDs = new ArrayList<String>();

            for (int i = 1; i <= 3; i++) {
                getLoIdsResponse = given().spec(spec).get(getLoSectionIdsApi);
                APP_LOG.info(getLoIdsResponse.body().asString());
                if (getLoIdsResponse.statusCode() == 200) {
                    APP_LOG.info(
                            "Successfully retrieved LO Seciton ids for course : "
                                    + courseId);
                    loLAIDs = getLoIdsResponse.then().extract().body()
                            .jsonPath()
                            .getList("data.resourceElements.objectiveId");

                    break;
                } else {
                    APP_LOG.info("getLoSectionIds api failed ...");
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while getting the loSectionIds, because status code : "
                                        + getLoIdsResponse.statusCode(),
                                "Verify LO Section Ids retrieval for course : "
                                        + courseId,
                                reportTestObj);
                    }
                }
            }
            courseData.put("loSectionIds", loLAIDs);
        } catch (Exception e) {
            APP_LOG.info("Exception occured while retrieving LO Section Ids "
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting the loSectionIds, because status code : "
                            + getLoIdsResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify LO Section Ids retrieval for course : " + courseId,
                    reportTestObj);
        }
        return courseData;
    }

    /**
     * @author mukul.sehra
     * @date 17 Apr, 2018
     * @description Method to attempt LO in a course via API providing
     *              parameters -> learnerName and loNumber e.g 11.1, 11.2
     * @param userName,
     *            loNumber
     */
    public synchronized void attemptLearningObjectiveViaAPI(String userName,
            String loNumber) {
        APP_LOG.info("Inside attemptLearningObjective method....");
        Response attemptLoResponse = null;
        RequestSpecification spec = null;
        String attemptLoApi = "";
        String loSectionId = "";
        Map<String, Object> loSectionData = new HashMap<String, Object>();
        try {
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            // Getting the LO Sections available in the course and other course
            // related data
            loSectionData = getLoSectionIds(userName, loNumber);
            String oauthToken = loSectionData.get("oauthToken").toString();
            String courseId = loSectionData.get("courseId").toString();
            String learnerId = loSectionData.get("learnerId").toString();
            String subscriptionId = loSectionData.get("subscriptionId")
                    .toString();
            @SuppressWarnings("unchecked")
            List<List<String>> loSectionIds = (List<List<String>>) loSectionData
                    .get("loSectionIds");

            // Getting the module id per the chapter number provided
            for (List<String> listOfListCounter : loSectionIds) {
                for (String sectionIdsIterator : listOfListCounter) {
                    if (sectionIdsIterator
                            .contains("SECTION-" + loNumber + "-")) {
                        loSectionId = sectionIdsIterator;
                        break;
                    }
                }
            }
            attemptLoApi = "/lcd/v1/resource/section/status";

            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", oauthToken)
                    .setContentType("application/json").setConfig(rac).build();
            String requestBody = "{\r\n\"laId\":\"" + loSectionId
                    + "\",\r\n\"learnerId\":\"" + learnerId
                    + "\",\r\n\"courseId\":\"" + courseId
                    + "\",\r\n\"subscriptionId\":\"" + subscriptionId
                    + "\",\r\n\"docType\":\"learnercourse\"\r\n}";

            for (int i = 1; i <= 3; i++) {
                attemptLoResponse = given().spec(spec).body(requestBody)
                        .put(attemptLoApi);
                APP_LOG.info(attemptLoResponse.body().asString());
                if (attemptLoResponse.statusCode() == 200) {
                    APP_LOG.info(
                            "Successfully completed the LO with LO_Section_Id : "
                                    + loSectionId);
                    logResultInReport(
                            Constants.PASS + ": Attempted the LO - '" + loNumber
                                    + "' successfully",
                            "Verify Learner has completed the LO successfully",
                            reportTestObj);

                    break;
                } else {
                    APP_LOG.info("Attempt LO api failed...");
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + " : Error while attempting LO, because status code : "
                                        + attemptLoResponse.statusCode(),
                                "Verify LO Attempted successfully",
                                reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.info("Exception occured while attempting LO..."
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while attempting LO, because status code : "
                            + attemptLoResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify LO Attempted successfully", reportTestObj);
        }
    }

    /**
     * @author mukul.sehra
     * @date 18 Apr, 2018
     * @description Method to get the list of Module IDs
     * @param userName
     */
    @SuppressWarnings("unchecked")
    public List<String> getModuleIdsList(String userName) {
        APP_LOG.info("Inside getModuleIdsList method");
        return (List<String>) getModules(userName).get("modules");
    }

    /**
     * @author mukul.sehra
     * @date 18 Apr, 2018
     * @description Method to get the list of LO LAIDs by providing learnerName
     *              and chapterNumber e.g 11, 12 ...
     * @param userName,
     *            chapterNumber
     */
    public List<String> getLoSectionIdsList(String userName,
            String chapterNumber) {
        APP_LOG.info("Inside getLoSectionIdsList method");
        @SuppressWarnings("unchecked")
        List<List<String>> listOfLaIds = (List<List<String>>) getLoSectionIds(
                userName, chapterNumber).get("loSectionIds");
        return listOfLaIds.get(0);
    }

    /**
     * @author mukul.sehra
     * @date 7 May, 2018
     * @description Method to attempt all LOs in a course via API providing
     *              parameters -> learnerName and chapterNumber e.g 11, 12
     * @param userName,
     *            chapterNumber
     */
    public synchronized void attemptCompleteChapterViaAPI(String userName,
            String chapterNumber) {
        APP_LOG.info("Inside attemptCompleteChapterViaAPI method....");
        Response attemptLoResponse = null;
        RequestSpecification spec = null;
        String attemptLoApi = "";
        List<String> loSectionId = new ArrayList<String>();
        Map<String, Object> loSectionData = new HashMap<String, Object>();
        try {
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            // Getting the LO Sections available in the course and other course
            // related data
            loSectionData = getLoSectionIds(userName, chapterNumber);
            String oauthToken = loSectionData.get("oauthToken").toString();
            String courseId = loSectionData.get("courseId").toString();
            String learnerId = loSectionData.get("learnerId").toString();
            String subscriptionId = loSectionData.get("subscriptionId")
                    .toString();
            @SuppressWarnings("unchecked")
            List<List<String>> loSectionIds = (List<List<String>>) loSectionData
                    .get("loSectionIds");

            // Getting the module id per the chapter number provided
            for (List<String> listOfListCounter : loSectionIds) {
                for (String sectionIdsIterator : listOfListCounter) {
                    {
                        if (sectionIdsIterator.contains("SECTION"))
                            loSectionId.add(sectionIdsIterator);
                    }
                }
            }
            attemptLoApi = "/lcd/v1/resource/section/status";

            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL"))
                    .addHeader("Authorization", oauthToken)
                    .setContentType("application/json").setConfig(rac).build();
            String requestBody = "";
            for (String loLaid : loSectionId) {
                requestBody = "{\r\n\"laId\":\"" + loLaid
                        + "\",\r\n\"learnerId\":\"" + learnerId
                        + "\",\r\n\"courseId\":\"" + courseId
                        + "\",\r\n\"subscriptionId\":\"" + subscriptionId
                        + "\",\r\n\"docType\":\"learnercourse\"\r\n}";
                for (int i = 1; i <= 3; i++) {
                    attemptLoResponse = given().spec(spec).body(requestBody)
                            .put(attemptLoApi);
                    APP_LOG.info(attemptLoResponse.body().asString());
                    if (attemptLoResponse.statusCode() == 200) {
                        APP_LOG.info(
                                "Successfully completed the LO with LO_Section_Id : "
                                        + loLaid);
                        break;
                    } else {
                        APP_LOG.info("Attempt LO api failed for LO LAId : "
                                + loLaid);
                        if (i == 3) {
                            logResultInReport(Constants.FAIL
                                    + " : Error while attempting LO : " + loLaid
                                    + ", because status code : "
                                    + attemptLoResponse.statusCode(),
                                    "Verify LO Attempted successfully",
                                    reportTestObj);
                        }
                    }
                }
            }
            logResultInReport(
                    Constants.PASS
                            + " : Successfully attempted all the LOs for chapter : "
                            + chapterNumber,
                    "Verify that all the LOs in Chapter : " + chapterNumber
                            + ", attempted successfully",
                    reportTestObj);
        } catch (Exception e) {
            APP_LOG.info("Exception occured while attempting LO..."
                    + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while attempting LO, because status code : "
                            + attemptLoResponse.statusCode() + "; Exception : "
                            + e.getMessage(),
                    "Verify that all the LOs in Chapter : " + chapterNumber
                            + ", attempted successfully",
                    reportTestObj);
        }
    }

    /**
     * @author ratnesh.singh
     * @description Method to retrieve network Entries/Data matching with RegEx
     *              from the Network logs
     * @return Network Entries/Data matching with RegEx
     */
    public synchronized Set<String> getMatchingNetworkEntries(String regex,
            String dataType) {
        Set<String> matchDataSet = new LinkedHashSet<>();
        Pattern pattern = Pattern.compile(regex);
        try {

            String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
            String netData = ((JavascriptExecutor) returnDriver())
                    .executeScript(scriptToExecute).toString();
            Matcher matcherString = pattern.matcher(netData);
            while (matcherString.find()) {
                matchDataSet.add(matcherString.group());
            }
            APP_LOG.debug("Extarcted Data from Network Entries--> " + dataType
                    + " : " + matchDataSet);
            logResultInReport(
                    Constants.PASS + ": Extarcted Data from Network Entries--> "
                            + dataType + " : " + matchDataSet,
                    "Extarct Data from Network Entries.", reportTestObj);
            return matchDataSet;
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occurred while extarcting Data from Network Entries as: "
                            + e.getMessage());
            logResultInReport(
                    Constants.FAIL
                            + ": Exception occurred while extarcting Data from Network Entries as: "
                            + e.getMessage(),
                    "Extarct Data from Network Entries.", reportTestObj);
            return null;
        }
    }

    /**
     * @author mukul.sehra
     * @description Clears the performance entries from the
     *              performanceEntriesBuffer
     */
    public synchronized void clearPerformanceEntries() {
        ((JavascriptExecutor) returnDriver())
                .executeScript("performance.clearResourceTimings();");
    }

    /**
     * @author Abhishek.sharda
     * @description Get TOT
     * @param userName
     * @param password
     * @param chapterNumber
     *            (e.g 12,13,14,15)
     * @param sectionID
     *            (1,2,3,4)
     * @param aggregation
     *            (min, max, avg)
     * @return
     */
    public synchronized String getTotAggregation(String userName,
            String password, String chapterNumber, String sectionID,
            String aggregation) {
        Response resp = null;
        RequestSpecification spec = null;
        String assetId = null;
        try {
            String outh2 = Oauth2(userName, password);
            String glpCourseID = getGlpCourseId(userName, password);
            List<String> assetIdList = getLoSectionIdsList(userName,
                    chapterNumber);
            for (int i = 0; i <= assetIdList.size() - 1; i++) {
                assetId = assetIdList.get(i);
                String[] temp = assetId.split("\\.");
                String finalSection = temp[1].substring(0, 1).toString();
                if (finalSection.equalsIgnoreCase(sectionID)) {
                    break;
                }
            }
            APP_LOG.info("Asset Id : " + assetId);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            RestAssuredConfig rac = RestAssured.config()
                    .sslConfig(new SSLConfig().allowAllHostnames()
                            .relaxedHTTPSValidation("TLSv1.2"));
            totEndPoint = totEndPoint.replaceAll("ASSETID", assetId)
                    .replaceAll("COURSEID", glpCourseID)
                    .replaceAll("AGGREGATION", aggregation);
            for (int i = 1; i <= 3; i++) {
                resp = given().spec(spec).header("authorization", outh2)
                        .config(rac).get(totEndPoint);
                resp.prettyPeek().prettyPrint();
                if (resp.getStatusCode() == 200
                        || resp.getStatusCode() == 201) {
                    tot = resp.then().extract().body().jsonPath()
                            .getString("data[0].properties.tot").toString();
                    APP_LOG.info("Get TOT" + tot);
                    break;
                } else {
                    APP_LOG.debug("get TOT : " + resp.getStatusLine());
                    if (i == 3) {
                        logResultInReport(
                                Constants.FAIL
                                        + ": Error while retrieving TOT - "
                                        + resp.getStatusLine(),
                                "Verify TOT retrieval", reportTestObj);
                    }
                }
            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in getting TOT " + e);
            logResultInReport(
                    Constants.FAIL + ": Exception occured in getting TOT - "
                            + e.getMessage(),
                    "Verify TOT retrieval", reportTestObj);
        }
        return tot;
    }

    /**
     * @author Abhishek
     * @date 26 Mar 2018
     * @description :creating test user for learner
     * @param userName,
     *            password, userRole, instructorName, unlockRequired
     */
    public synchronized void createLearner(String userName, String password,
            String userRole) {
        try {
            createConsoleUserViaApi(userName, password, userRole);
            validateWebCredentials(userName, password);
            sendVerificationEmail(userName, userRole);
            glpConsoleValidateEmail(userRole, userName);
        } catch (Exception e) {
            this.result = Constants.FAIL
                    + ": User Creation and email verification failed for"
                    + userName + "'";
            logResultInReport(result,
                    "Verify following user sucessfully created: " + userName,
                    reportTestObj);
            APP_LOG.error(
                    "Exception occured in learner User creation With Email Validation: "
                            + e);
        }
    }

    /**
     * @author nitish.jaiswal
     * @description Method to retrieve the QuestionID from the Network logs
     * @return questionId
     */
    public synchronized String getModuleNumberInPractice() {

        String moduleNumber = "";
        String regex1 = "Assessment-([0-9][0-9].[0-9].[0-9]-[0-9]{0,26})";
        Pattern regex = Pattern.compile(regex1);
        try {

            String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
            String netData = ((JavascriptExecutor) returnDriver())
                    .executeScript(scriptToExecute).toString();
            APP_LOG.debug(netData);
            Matcher matcherString = regex.matcher(netData);
            while (matcherString.find()) {
                moduleNumber = matcherString.group();
            }
            APP_LOG.debug("Extarcted Module Id--> '" + moduleNumber + "'");

            return moduleNumber;
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception in getModuleNumberInPractice method : " + e);
            return null;
        }
    }

    /**
     * @author nitish.jaiswal
     * @date 09 Feb 2017
     * @description :Method to return diagnostic score
     * @return diagnosticAttemptedScore
     */
    public synchronized String
           getDiagnosticAndPracticeAttemptedScoreAndMaxScore(String userName,
                   String password, String assessmentType, String scoreType) {
        Response response = null;
        RequestSpecification spec = null;
        String score = "";
        String maxScore = "";
        String courseId = "";
        String glpId = "";
        String SubscriptionId = "";
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig()
                .allowAllHostnames().relaxedHTTPSValidation("TLSv1.2"));
        try {
            String sectionId = getCreatedCourseSectionId(userName, password);
            courseId = getGlpCourseId(userName, password, sectionId);
            glpId = getGLPId(userName, password);
            SubscriptionId = getSubscriptionId(userName, password);
            spec = new RequestSpecBuilder()
                    .setBaseUri(configurationsXlsMap.get("DomainURL")).build();
            response = given().spec(spec).contentType("application/json")
                    .header("Authorization", Oauth2(userName, password))
                    .config(rac).get("/lae/v1/outcomes/" + courseId + glpId
                            + SubscriptionId + "learnercourseoutcomes");
            APP_LOG.info(response.asString());
            if (response.statusCode() == 200) {
                // courseName = response.path("data.outcomesValue" + "." +
                // getGlpCourseId() + "." + "courseTitle");
                // courseName = "RIO-DIAGNOSTIC-TEST-M0-New";
                if (assessmentType.equalsIgnoreCase("diagnostic")) {
                    score = response.path("data.outcomesValue" + "." + courseId
                            + "." + "score");
                    APP_LOG.info("DiagnosticAttemptedScore:" + score);
                } else if (assessmentType.equalsIgnoreCase("practice")) {

                    if (scoreType.equalsIgnoreCase("maxScore")) {
                        maxScore = response.path("data.outcomesValue" + ".'"
                                + getModuleNumberInPractice() + "'."
                                + "maxScore");
                        APP_LOG.info("PracticeMaxScore:" + maxScore);
                    } else {
                        score = response.path("data.outcomesValue" + ".'"
                                + getModuleNumberInPractice() + "'." + "score");

                        APP_LOG.info("PracticeAttemptedScore :" + score);
                    }

                }

            } else {
                APP_LOG.info(
                        "Error while getting score of attempted questions in "
                                + assessmentType + " test, score is : "
                                + score);
                logResultInReport(
                        Constants.FAIL + " : Error while getting the score of "
                                + assessmentType + " test - " + score
                                + ",  status code - " + response.statusCode()
                                + " - " + response.statusLine(),
                        "Verify score acheived after attempting "
                                + assessmentType + " Test",
                        reportTestObj);
            }
        } catch (Exception e) {
            APP_LOG.error(
                    "Exception occured in sendVerificationEmail API fetching subscription API"
                            + response.statusCode() + e);
            logResultInReport(
                    Constants.FAIL
                            + " : Exception while getting the score of Diagnostic test - "
                            + score + ",  status code - "
                            + response.statusCode() + " - " + e.getMessage(),
                    "Verify score acheived after attempting Diagnostic Test",
                    reportTestObj);
        }
        return score;

    }
}