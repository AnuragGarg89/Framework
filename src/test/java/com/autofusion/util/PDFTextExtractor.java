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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.autofusion.BaseClass;

public class PDFTextExtractor extends BaseClass {

    public static String readParaFromPDF(String pdfPath, int pageNo,
            String strStartIndentifier, String strEndIdentifier) {
        String returnString = "";

        try {
            PDDocument document = PDDocument.load(new File(pdfPath));
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                tStripper.setStartPage(pageNo);
                tStripper.setEndPage(pageNo);
                String pdfFileInText = tStripper.getText(document);
                String strStart = strStartIndentifier;
                String strEnd = strEndIdentifier;
                int startInddex = pdfFileInText.indexOf(strStart);
                int endInddex = pdfFileInText.indexOf(strEnd);
                returnString = pdfFileInText.substring(startInddex, endInddex)
                        + strEnd;
            }
        } catch (Exception e) {
            APP_LOG.error("No ParaGraph Found" + e);
        }
        return returnString;
    }
}