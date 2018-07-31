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

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPI {

    @Test
    public static void httpPost(String APIUrl) {

        // Building request using requestSpecBuilder
        RequestSpecBuilder builder = new RequestSpecBuilder();

        // Setting content type as application/json or application/xml
        builder.setContentType("application/json; charset=UTF-8");

        RequestSpecification requestSpec = builder.build();

        // Making post request with authentication, leave blank in case there
        // are no credentials- basic("","")
        Response response = given().spec(requestSpec).when().post(APIUrl);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
