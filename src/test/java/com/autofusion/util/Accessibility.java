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

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.rules.TestName;
import org.openqa.selenium.By;

import com.autofusion.BaseClass;
import com.deque.axe.AXE;

public class Accessibility extends BaseClass {
    public Accessibility() {
        // TODO Auto-generated constructor stub
    }

    protected TestName testName = new TestName();

    private static final URL scriptUrl = Accessibility.class
            .getResource("/axe.min.js");
    protected static List<String> acopErrorList = new LinkedList<String>();

    public List<String> runAcopChecks() {
        this.testAccessibility();
        return acopErrorList;
    }

    public void testAccessibility() {
        JSONObject responseJSON = new AXE.Builder(returnDriver(), scriptUrl)
                .analyze();
        JSONArray violations = responseJSON.getJSONArray("violations");
        AXE.writeResults(testName.getMethodName(), responseJSON);
        acopErrorList.add(AXE.report(violations));
        for (String element : acopErrorList) {
            if (element.contains("Found 0")) {
                acopErrorList.remove(element);
            }
        }
    }

    /**
     * Test with skip frames
     *//*
       * @Test public void testAccessibilityWithSkipFrames() { JSONObject
       * responseJSON = ((Object) new AXE.Builder(driver, scriptUrl))
       * .skipFrames() .analyze();
       * 
       * JSONArray violations = responseJSON.getJSONArray("violations");
       * 
       * if (violations.length() == 0) { assertTrue("No violations found",
       * true); } else { AXE.writeResults(testName.getMethodName(),
       * responseJSON);
       * 
       * assertTrue(AXE.report(violations), false); } }
       */

    /**
     * Test with options
     */
    public void testAccessibilityWithOptions() {
        JSONObject responseJSON = new AXE.Builder(returnDriver(), scriptUrl)
                .options("{ rules: { 'accesskeys': { enabled: false } } }")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults(testName.getMethodName(), responseJSON);

            assertTrue(AXE.report(violations), false);
        }
    }

    /**
     * Test a specific selector or selectors
     */
    public void testAccessibilityWithSelector() {
        JSONObject responseJSON = new AXE.Builder(returnDriver(), scriptUrl)
                .include("title").include("p").analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults(testName.getMethodName(), responseJSON);

            assertTrue(AXE.report(violations), false);
        }
    }

    /**
     * Test includes and excludes
     */
    public void testAccessibilityWithIncludesAndExcludes() {
        JSONObject responseJSON = new AXE.Builder(returnDriver(), scriptUrl)
                .include("div").exclude("h1").analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults(testName.getMethodName(), responseJSON);

            assertTrue(AXE.report(violations), false);
        }
    }

    /**
     * Test a WebElement
     */
    public void testAccessibilityWithWebElement() {
        JSONObject responseJSON = new AXE.Builder(returnDriver(), scriptUrl)
                .analyze(returnDriver().findElement(By.tagName("p")));

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults(testName.getMethodName(), responseJSON);

            assertTrue(AXE.report(violations), false);
        }
    }
}
