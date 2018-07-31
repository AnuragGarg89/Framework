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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertXlsToXml {
    public ConvertXlsToXml() {
        // TODO Auto-generated constructor stub
    }

    protected static Logger APP_LOG;

    public static void main(String[] args) {

        Document doc = null;
        String xlsFilePath;
        xlsFilePath = args[0];
        String fileName = args[1];

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = null;
            try {
                docBuilder = docFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                APP_LOG.error("Func: ConvertXlsToXml" + e);
            }

            if (docBuilder != null) {
                doc = docBuilder.newDocument();
            }
            Element rootElement = doc.createElement("or");
            doc.appendChild(rootElement);

            Xls_Reader programXls = new Xls_Reader(
                    xlsFilePath + "\\" + fileName);
            for (int suiteCount = 2; suiteCount <= programXls
                    .getRowCount("OR"); suiteCount++) {
                String variableName = programXls.getCellData("OR",
                        "Variable Name", suiteCount);
                String xpathChrome = programXls.getCellData("OR", "xpathChrome",
                        suiteCount);
                String id = programXls.getCellData("OR", "id", suiteCount);

                Element xmlDescEle = doc.createElement(variableName);
                rootElement.appendChild(xmlDescEle);

                if (!"".equalsIgnoreCase(xpathChrome)) {

                    Element xmlCreateEle1 = doc.createElement("xpathChrome");
                    xmlCreateEle1.appendChild(doc.createTextNode(xpathChrome));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }

                if (!"".equalsIgnoreCase(id)) {

                    Element xmlCreateEle1 = doc.createElement("id");
                    xmlCreateEle1.appendChild(doc.createTextNode(id));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String name = programXls.getCellData("OR", "name", suiteCount);
                if (!"".equalsIgnoreCase(name)) {

                    Element xmlCreateEle1 = doc.createElement("name");
                    xmlCreateEle1.appendChild(doc.createTextNode(name));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String css = programXls.getCellData("OR", "css", suiteCount);
                if (!"".equalsIgnoreCase(css)) {

                    Element xmlCreateEle1 = doc.createElement("css");
                    xmlCreateEle1.appendChild(doc.createTextNode(css));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String xpath = programXls.getCellData("OR", "xpath",
                        suiteCount);
                if (!"".equalsIgnoreCase(xpath)) {

                    Element xmlCreateEle1 = doc.createElement("xpath");
                    xmlCreateEle1.appendChild(doc.createTextNode(xpath));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String link = programXls.getCellData("OR", "link", suiteCount);
                if (!"".equalsIgnoreCase(link)) {

                    Element xmlCreateEle1 = doc.createElement("link");
                    xmlCreateEle1.appendChild(doc.createTextNode(link));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String type = programXls.getCellData("OR", "type", suiteCount);
                if (!"".equalsIgnoreCase(type)) {

                    Element xmlCreateEle1 = doc.createElement("type");
                    xmlCreateEle1.appendChild(doc.createTextNode(type));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String dynamic = programXls.getCellData("OR", "dynamic",
                        suiteCount);
                if (!"".equalsIgnoreCase(dynamic)) {

                    Element xmlCreateEle1 = doc.createElement("dynamic");
                    xmlCreateEle1.appendChild(doc.createTextNode(dynamic));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String tagName = programXls.getCellData("OR", "tagName",
                        suiteCount);
                if (!"".equalsIgnoreCase(tagName)) {

                    Element xmlCreateEle1 = doc.createElement("tagName");
                    xmlCreateEle1.appendChild(doc.createTextNode(tagName));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }
                String className = programXls.getCellData("OR", "className",
                        suiteCount);
                if (!"".equalsIgnoreCase(className)) {

                    Element xmlCreateEle1 = doc.createElement("className");
                    xmlCreateEle1.appendChild(doc.createTextNode(className));
                    xmlDescEle.appendChild(xmlCreateEle1);

                }

            }

            /*********************** Saving File. ***********************/
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(
                    new File(xlsFilePath + "\\OR.xml").getAbsolutePath());

            transformer.transform(source, result);
            APP_LOG.info("File saved!");
        }

        catch (TransformerException e) {
            APP_LOG.error("Func: ConvertXlsToXml" + e);
        }

    }
}