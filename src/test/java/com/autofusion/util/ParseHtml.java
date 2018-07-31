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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.autofusion.BaseClass;

public class ParseHtml extends BaseClass {

    protected static String finalContent = null;

    public static String parseHtmlContent(String path) {

        File input = new File(path);

        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://www.w3.org/1999/xhtml");
            doc.body().text();
            String s = doc.html().replaceAll("[^\\S\\r\\n]+", " ").trim();
            String content = Jsoup
                    .clean(s, "", Whitelist.none(),
                            new Document.OutputSettings().prettyPrint(false))
                    .trim();
            finalContent = content.replaceAll("(?m)(^ *| +(?= |$))", "")
                    .replaceAll("(?m)^$([\r\n]+?)(^$[\r\n]+?^)+", "$1")
                    .replaceAll("[\r\n]+", "\n");

        } catch (Exception e) {

            APP_LOG.error("Error occured wile trying to parse page" + e);

        }
        return finalContent;

    }
}
