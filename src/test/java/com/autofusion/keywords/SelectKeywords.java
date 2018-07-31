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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.autofusion.constants.Constants;

public class SelectKeywords extends Keyword {

    public WebElement element;
    public String elementId;
    public String inputValue;

    public SelectKeywords(Logger log,
            Map<String, HashMap<String, String>> orMap) {
        APP_LOG = log;
    }

    public SelectKeywords(Logger log) {
        APP_LOG = log;
    }

    /**
     * @description Selects from DropDown the shop location.
     * @param args
     *            arguments
     */
    public String selectByVisibleText(Map<String, String> argsList) {
        APP_LOG.info("Inside selectDropDown");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            return this.selectByVisibleText(this.element, this.inputValue,
                    this.elementId);
        } catch (Exception e) {
            APP_LOG.debug(" DropDown value is not selected" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String selectByVisibleText(WebElement element, String inpuString,
            String elementId) {
        APP_LOG.debug(" selectByVisibleText => " + inpuString);

        try {
            new Select(element).selectByVisibleText(inpuString);
        } catch (Exception e) {
            APP_LOG.debug(" DropDown value is not selected" + e);
            return Constants.FAIL
                    + ": Unexpected Error while Selecting list Item for - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.PASS + ": List Item- '" + inpuString
                + "' is selected for element- '" + this.elementId + "'.";
    }

    public String selectType(Map<String, String> argsList) {
        APP_LOG.info("Inside selecttype");

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            APP_LOG.info(this.element);
            this.element.sendKeys(this.inputValue);
        } catch (Exception e) {
            APP_LOG.debug(" The type is not selected" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.PASS + ": Item- '" + this.inputValue
                + "' is entered for element- '" + this.elementId + "'.";
    }

    public String findvalue(WebElement element, String elementId) {
        APP_LOG.debug("Func:Find Value");
        try {
            String value = element.getText();
            return Constants.PASS + ": Text- '" + value
                    + "' is fetched for element- '" + this.elementId + "'.";
        } catch (Exception e) {
            APP_LOG.debug("Func:Static Click Exception=" + e);
            return Constants.FAIL
                    + ": Unexpected error while getting Text for Element - "
                    + this.elementId + " : " + e;
        }
    }

    public String selectvalueFromlist(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            List<WebElement> elements = getElements(this.elementId);
            APP_LOG.info(elements.size());
            int indexPos = Integer.valueOf(this.inputValue);
            return this.findvalue(elements.get(indexPos), this.elementId);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public String selectvisibleTextFromlist(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        boolean bFlag = false;
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            List<WebElement> elements = getElements(this.elementId);
            APP_LOG.info(elements.size());
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getText().trim().equals(this.inputValue)) {
                    elements.get(i).click();
                    bFlag = true;
                    break;
                }
            }
            if (bFlag) {
                return Constants.PASS + ": List item- '" + this.inputValue
                        + "' for Element - '" + this.elementId
                        + "' is selected from dropdown items. ";
            } else {
                return Constants.FAIL + ": List item- '" + this.inputValue
                        + "' for Element - '" + this.elementId
                        + "' is not selected from dropdown items. ";
            }

        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
    }

    public List<WebElement> getListItems(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            List<WebElement> elements = getElements(this.elementId);
            return elements;
        } catch (Exception e) {
            APP_LOG.error("Error while fatching the list:" + e);
            return elements;
        }
    }

    public String mouseHover(WebElement element, String elementId) {
        APP_LOG.debug("Func:Mouse hover over an element");
        try {
            element.click();
        } catch (Exception e) {
            APP_LOG.debug("Func:Mouse hover over an element=" + e);
            return Constants.FAIL
                    + ": Unexpected error while mouse hovering/clicking Element - "
                    + this.elementId + " : " + e;
        }
        return Constants.PASS + ": Mouse over for Element - '" + this.elementId
                + "' is performed and is clicked";
    }

    public String elementOnmouseHover(Map<String, String> argsList) {

        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.info(
                "in element on mousehover" + this.elementId + this.inputValue);
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            List<WebElement> elements = getElements(this.elementId);
            APP_LOG.info("size mouse hover" + elements.size());
            int indexPos = Integer.valueOf(this.inputValue);
            APP_LOG.info("indexPos" + indexPos);
            return this.mouseHover(elements.get(indexPos), this.elementId);
        } catch (Exception e) {
            APP_LOG.debug("Func:Mouse hover over an element=" + e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

    }

    public String selectDropdownValue(Map<String, String> argsList) {
        APP_LOG.info("inside selectDropdownValue");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            Select dropdown = new Select(this.element);
            dropdown.selectByValue(this.inputValue);
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

        return Constants.PASS + ": Text- '" + this.inputValue
                + "' is selected for element- '" + this.elementId + "'.";
    }

    /**
     * @description The option is selected from the dropdown based on the
     *              visible text.
     * @param args
     *            argumnets
     */
    public String selectDropdownVisibleText(Map<String, String> argsList) {
        APP_LOG.info("inside selectDropdownVisibleText");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");

        try {
            this.element = getElement(this.elementId);
            Select dropdown = new Select(this.element);
            dropdown.selectByVisibleText(this.inputValue);
        } catch (Exception e) {
            APP_LOG.debug(e);

            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

        return Constants.PASS + ": List Item- '" + this.inputValue
                + "' is selected for element- '" + this.elementId + "'.";
    }

    public String select(Map<String, String> argsList) {
        APP_LOG.info("inside select");
        this.elementId = argsList.get("object");
        this.inputValue = argsList.get("data");

        try {
            this.element = getElement(this.elementId);
            this.element.sendKeys(this.inputValue);
        } catch (Exception e) {
            APP_LOG.debug(e);
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
        return Constants.PASS + ": Text- '" + this.inputValue
                + "' is entered for element- '" + this.elementId + "'.";
    }

    /**
     * @description Selection of integer values.
     * @param log
     *            Application log
     * @param args
     *            arguments
     */
    public String selectIntegerValue(Map<String, String> argsList) {
        APP_LOG.info("inside selectIntegerValue");
        this.elementId = argsList.get("object");
        this.inputValue = argsList.get("data");

        try {
            int d = (int) Double.parseDouble(this.inputValue);

            argsList.put("data", String.valueOf(d));

            String result = this.select(argsList);

            APP_LOG.info("Integer value selected is :" + result);
            return result;
        } catch (Exception e) {
            return Constants.FAIL
                    + ": Unexpected error while performing action on - "
                    + this.elementId + " : " + e;
        }
    }

    /**
     * @description The option is selected from the dropdown based on the
     *              visible text.
     * @param args
     *            argumnets
     */
    public String selectDropdownByIndex(Map<String, String> argsList) {
        APP_LOG.info("inside selectDropdownVisibleText");
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        int indexPos = Integer.valueOf(this.inputValue);

        try {
            this.element = getElement(this.elementId);
            Select dropdown = new Select(this.element);
            dropdown.selectByIndex(indexPos);
        } catch (Exception e) {
            APP_LOG.debug(e);

            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }

        return Constants.PASS + ": Selected '" + this.inputValue
                + "' index from- '" + this.elementId + "'.";
    }

    public String selectAllvaluesFromlist(Map<String, String> argsList) {
        String str;
        this.elementId = argsList.get("ElementId");
        this.inputValue = argsList.get("InputValue");
        APP_LOG.debug("Func: Type|| inputValue=" + this.inputValue);

        try {
            this.element = getElement(this.elementId);
            Select dropdown = new Select(this.element);
            str = dropdown.getOptions().toString();
            System.out.println(str);
        } catch (Exception e) {
            return Constants.FAIL + ": Error while finding Element - '"
                    + this.elementId + "' : " + e;
        }
        return str;
    }

}