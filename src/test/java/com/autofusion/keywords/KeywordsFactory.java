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
package com.autofusion.keywords;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class KeywordsFactory {
    public KeywordsFactory() {
        // TODO Auto-generated constructor stub
    }

    public HashMap<String, Keyword> objKeywordsMap = new HashMap<>();

    public Keyword getInstance(String keywordClass, Logger logger) {
        if (keywordClass == null) {
            return null;
        }

        if (keywordClass.startsWith("type")) {
            if (this.objKeywordsMap.containsKey("keywordClass")) {
                return this.objKeywordsMap.get("keywordClass");
            } else {
                Keyword objKeyword = new TypeKeywords(logger);
                this.objKeywordsMap.put("keywordClass", objKeyword);
                return objKeyword;
            }

        } else if (keywordClass.startsWith("click")) {
            if (this.objKeywordsMap.containsKey("clickClass")) {
                return this.objKeywordsMap.get("clickClass");
            } else {
                Keyword objKeyword = new ClickKeywords(logger);
                this.objKeywordsMap.put("clickClass", objKeyword);
                return objKeyword;
            }

        } else if (keywordClass.startsWith("verify")) {
            if (this.objKeywordsMap.containsKey("verifyClass")) {
                return this.objKeywordsMap.get("verifyClass");
            } else {
                Keyword objKeyword = new VerificationKeywords(logger);
                this.objKeywordsMap.put("verifyClass", objKeyword);
                return objKeyword;
            }

        } else if (keywordClass.startsWith("app")) {
            if (this.objKeywordsMap.containsKey("appClass")) {
                return this.objKeywordsMap.get("appClass");
            } else {
                Keyword objKeyword = new ApplicationSpecificKeywords(null,
                        logger);
                this.objKeywordsMap.put("appClass", objKeyword);
                return objKeyword;
            }

        } else if (keywordClass.startsWith("action")) {
            if (this.objKeywordsMap.containsKey("actionClass")) {
                return this.objKeywordsMap.get("actionClass");
            } else {
                Keyword objKeyword = new ActionKeywords(logger);
                this.objKeywordsMap.put("actionClass", objKeyword);
                return objKeyword;
            }
        } else if (keywordClass.startsWith("select")) {
            if (this.objKeywordsMap.containsKey("selectClass")) {
                return this.objKeywordsMap.get("selectClass");
            } else {
                Keyword objKeyword = new SelectKeywords(logger);
                this.objKeywordsMap.put("selectClass", objKeyword);
                return objKeyword;
            }
        } else if (keywordClass.startsWith("get")) {
            if (this.objKeywordsMap.containsKey("getClass")) {
                return this.objKeywordsMap.get("getClass");
            } else {
                Keyword objKeyword = new GetElementsFromUi(logger);
                this.objKeywordsMap.put("getClass", objKeyword);
                return objKeyword;
            }

        }

        else {
            if (this.objKeywordsMap.containsKey("commonClass")) {
                return this.objKeywordsMap.get("commonClass");
            } else {
                Keyword objKeyword = new CommonKeywords(logger);
                this.objKeywordsMap.put("commonClass", objKeyword);
                return objKeyword;
            }
        }

    }

}
