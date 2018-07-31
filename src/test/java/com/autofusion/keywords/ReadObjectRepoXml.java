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

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;

public class ReadObjectRepoXml extends BaseClass {
    public ReadObjectRepoXml() {
        // TODO Auto-generated constructor stub
    }

    public String runningComponent = null;

    /**
     * @description ConcurrentHashMap.
     * @param basePath=
     *            Base path of xml
     * @param coponentName
     *            = File Name
     * @param elementName
     *            = Variable Name
     * @param APP_LOG
     *            = logger
     * @return id, name , xpath, css , classname
     */
    public synchronized ConcurrentHashMap<String, String>
           getElementAttribute(String elementName) {
        boolean dynamicElement = false;
        Pattern locatorPattern = null;
        Pattern keyValuePattern = null;
        Matcher locatorMatcher = null;
        Matcher keyValueMatcher = null;
        String dynamicReplaceKeyValues = null;

        try {
            if (elementName.contains(":")) {
                locatorPattern = Pattern.compile("^([^:]+)");
                keyValuePattern = Pattern.compile("(?<=:)(.+)");
                locatorMatcher = locatorPattern.matcher(elementName);
                keyValueMatcher = keyValuePattern.matcher(elementName);
                if (locatorMatcher.find()) {
                    elementName = locatorMatcher.group(1);
                    if (keyValueMatcher.find()) {
                        dynamicReplaceKeyValues = keyValueMatcher.group(1);
                        dynamicElement = true;
                    }
                }

            }

            StackTraceElement[] stacktrace = Thread.currentThread()
                    .getStackTrace();
            String s1 = null;
            for (StackTraceElement s : stacktrace) {
                if (s.getClassName().contains("Page")
                        && s.getClassName().contains("GLP")) {
                    s1 = s.getClassName();
                    break;
                }
            }
            String[] temp = s1.split("\\.");
            this.runningComponent = temp[temp.length - 1];
        } catch (

        NullPointerException e) {
            APP_LOG.error(
                    "Error while getting running component name because of:- "
                            + e);
        }
        String elementValue = "";
        ConcurrentHashMap<String, String> detailMap = new ConcurrentHashMap<>();
        try {
            APP_LOG.info("XML  reading start || getElement : elementName = "
                    + elementName + " runningComponentName="
                    + this.runningComponent);
            String filePath;
            if ("android".equalsIgnoreCase(device)) {
                filePath = projectPath + "//" + "web" + "//objectRepository"
                        + "//";
            } else {
                filePath = projectPath + "//" + device + "//objectRepository"
                        + "//";
            }
            String tempFilePath = filePath;
            if (!"".equals(this.runningComponent)) {
                filePath = filePath + this.runningComponent + ".xml";
            } else {
                filePath = filePath + Constants.OR + ".xml";
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            File fXmlFile = null;
            try {
                fXmlFile = new File(filePath);
                dbf = DocumentBuilderFactory.newInstance();
            } catch (FactoryConfigurationError e) {
                APP_LOG.error("" + e);
                fXmlFile = new File(filePath + Constants.OR + ".xml");
                dbf = DocumentBuilderFactory.newInstance();
            }
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            APP_LOG.debug(
                    " Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName(elementName);

            APP_LOG.debug(
                    " element :" + elementName + " = " + nList.getLength());

            if (nList.getLength() == 0 && !"".equals(this.runningComponent)) {
                filePath = tempFilePath + Constants.OR + ".xml";

                fXmlFile = new File(filePath);
                dbf = DocumentBuilderFactory.newInstance();
                dBuilder = dbf.newDocumentBuilder();
                doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();

                APP_LOG.debug(" Re Try with OR.XML : Root element :"
                        + doc.getDocumentElement().getNodeName());
                APP_LOG.debug(" Root element :"
                        + doc.getDocumentElement().getNodeName());

            }
            nList = doc.getElementsByTagName(elementName);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                APP_LOG.info("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    if (eElement.getElementsByTagName("id").getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("id")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("id", elementValue);
                    } else if (eElement.getElementsByTagName("name")
                            .getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("name")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("name", elementValue);
                    } else if (eElement.getElementsByTagName("xpath")
                            .getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("xpath")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("xpath", elementValue);
                    } else if (eElement.getElementsByTagName("css")
                            .getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("css")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("css", elementValue);
                    } else if (eElement.getElementsByTagName("tagName")
                            .getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("tagName")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("tagName", elementValue);
                    } else if (eElement.getElementsByTagName("class")
                            .getLength() == 1) {
                        elementValue = eElement.getElementsByTagName("class")
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put("class", elementValue);
                    } else if (eElement
                            .getElementsByTagName(
                                    Constants.PREFIX_FIELD_CLASSNAME)
                            .getLength() == 1) {
                        elementValue = eElement
                                .getElementsByTagName(
                                        Constants.PREFIX_FIELD_CLASSNAME)
                                .item(0).getTextContent();
                        if (dynamicElement) {
                            elementValue = getDynamicLocatorValue(
                                    dynamicReplaceKeyValues, elementValue);
                        }
                        detailMap.put(Constants.PREFIX_FIELD_CLASSNAME,
                                elementValue);
                    } else {
                        elementValue = "";
                    }
                }
            }

            APP_LOG.debug("XML reading done ||  getElement : elementValue = "
                    + elementValue);

        } catch (Exception e) {
            APP_LOG.error("Element not found in the xml", e);
        }

        return detailMap;
    }

    /**
     * @description Replace Key=Values in Dynamic Locator String
     * @return Replaced Locator String
     */

    public synchronized String getDynamicLocatorValue(
            String dynamicReplaceKeyValues, String elementValue) {

        try {
            String[] keyValues = dynamicReplaceKeyValues.split(",");
            String[] replace;
            for (String key : keyValues) {
                replace = key.split("=");
                if (replace.length != 2) {
                    elementValue = elementValue.replace(replace[0], "");
                } else {
                    elementValue = elementValue.replace(replace[0], replace[1]);
                }
            }

        } catch (Exception e) {
            APP_LOG.error("Exception occurred while building dynamic locator :"
                    + e.getMessage());
        }

        return elementValue;
    }

}
