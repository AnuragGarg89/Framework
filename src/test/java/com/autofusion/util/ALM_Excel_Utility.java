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

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.glp.util.GLP_Utilities;

/**
 * @author tarun.gupta1
 * @date Oct 27, 2017
 * @description : Verify Intstructor is able to login and can view the
 *              performance dashboard
 */
public class ALM_Excel_Utility extends BaseClass {
    public ALM_Excel_Utility() {
    }

    @Test(groups = { Groups.REGRESSION }, enabled = true,
            description = "Verify newly created Intstructor is able to login and can view the performance dashboard")

    public void verifyMasteringScoreSetting() {
        startReport(getTestCaseId(),
                "Verify newly created Intstructor is able to login and can view the performance dashboard");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        String instructorName = "GLP_Instructor_Common" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName1 = "GLP_Instructor_Reset" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName2 = "GLP_Instructor_LockUnlock" + getTestCaseId()
                + "_" + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName3 = "GLP_Instructor_ResetAll" + getTestCaseId()
                + "_" + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName4 = "GLP_Instructor_EditScore" + getTestCaseId()
                + "_" + objCommonUtil.generateRandomStringOfAlphabets(4);
        String instructorName5 = "GLP_Instructor_Practice" + getTestCaseId()
                + "_" + objCommonUtil.generateRandomStringOfAlphabets(4);
        String learnerName = "GLP_Learner_Common" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);

        // Create user with role Instructor, subscribe RIO-Squires course and
        // Login
        try {
            GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj,
                    APP_LOG);

            /*objRestUtil.createInstructorWithNewCourse(instructorName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);*/

            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName1,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName2,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName3,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName4,
                    ResourceConfigurations.getProperty("consolePassword"),
                    false);
            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createInstructorWithNewCourse(instructorName5,
                    ResourceConfigurations.getProperty("consolePassword"),
                    true);

            objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
            objRestUtil.createLearnerAndSubscribeCourse(learnerName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations.getProperty(
                            "consoleUserTypeLearner"),
                    instructorName, true);

            // Fetching Section id and Invite key
            // String sectionId = objRestUtil.getCreatedCourseSectionId(
            // instructorName2,
            // ResourceConfigurations.getProperty("consolePassword"));
            // String inviteKey = objRestUtil.sendInvite(sectionId);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("INSTRUCTOR_PRACTICE_USER_NAME", instructorName);
            map.put("INSTRUCTOR_USER_NAME_RESET", instructorName1);
            map.put("INSTRUCTOR_USER_NAME_LOCKUNLOCK", instructorName2);
            map.put("INSTRUCTOR_USER_NAME_RESETALL", instructorName3);
            map.put("INSTRUCTOR_USER_NAME_EDITSCORE", instructorName4);
            map.put("INSTRUCTOR_PRACTICE_USER_NAME", instructorName5);
            map.put("LEARNER_USER_NAME", learnerName);
            // map.put("sectionId", sectionId);
            // map.put("inviteKey", inviteKey);
            ReadConfigXlsFiles updateExcel = new ReadConfigXlsFiles();
            try {
                String sheetToUpdate = null;
                executionEnviroment = executionEnviroment.trim().toLowerCase();
                switch (executionEnviroment) {
                case "qa":
                    sheetToUpdate = "QAConfigurations";
                    break;

                case "stage":
                    sheetToUpdate = "StageConfigurations";
                    break;

                case "uat":
                    sheetToUpdate = "UATConfigurations";
                    break;

                case "int":
                    sheetToUpdate = "IntConfigurations";
                    break;

                case "qabitesize":
                    sheetToUpdate = "QABiteSizeConfigurations";
                    break;

                case "stagebitesize":
                    sheetToUpdate = "UATBiteSizeConfigurations";
                    break;
                }
                updateExcel.updateKeysInConfigFiles("Config_" + course,
                        sheetToUpdate, map);
            } catch (IOException e) {
                APP_LOG.error("Error in Update Keys in Config files" + e);
            }
        }

        // Delete User via API
        finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
