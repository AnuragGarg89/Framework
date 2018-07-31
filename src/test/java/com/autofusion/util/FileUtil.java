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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;

public final class FileUtil extends BaseClass {
    public FileUtil() {
        // TODO Auto-generated constructor stub
    }

    public static void writeFile(String FILENAME, String content) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(FILENAME));

            bw.write(content);
            System.out.println("Written into text file");

        } catch (IOException e) {

            APP_LOG.error("error occured" + e);

        } finally {
            try {
                bw.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                APP_LOG.error("error occured while writing in text file" + e);
            }
            try {
                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                APP_LOG.error("error occured while writing in text file" + e);
            }
        }
    }

    public static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {

            // if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                APP_LOG.info("Directory copied from " + src + "  to " + dest);
            }

            // list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                // construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                // recursive copy
                copyFolder(srcFile, destFile);
            }

        } else {
            InputStream in = null;
            OutputStream out = null;
            // if file, then copy it
            // Use bytes stream to support all file types
            try {
                in = new FileInputStream(src);

                try {
                    out = new FileOutputStream(dest);

                    byte[] buffer = new byte[1024];

                    int length;
                    // copy the file content in bytes
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }

                catch (Exception e) {
                    APP_LOG.error("error occured" + e);
                } finally {
                    out.close();
                    APP_LOG.info("File copied from " + src + " to " + dest);
                }
            }

            catch (Exception e) {
                APP_LOG.error("error occured" + e);
            } finally {
                in.close();
                APP_LOG.info("File copied from " + src + " to " + dest);
            }

        }

    }

    public static void deleteFile(String dest) {
        File file = new File(dest);
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                boolean b = myFile.delete();
                if (b) {
                    APP_LOG.debug("File is deleted");
                }
            }
        }
    }

    public static void renameSummaryReport() {
        try {
            File oldfile = new File(projectPath
                    + "web/htmlReports/SummaryLatestReport/SummaryReport_"
                    + BaseClass.reportStartTime + ".html");
            APP_LOG.info(oldfile.getAbsolutePath());
            File newfile = new File(projectPath
                    + "web//htmlReports/SummaryLatestReport/SummaryReport.html");
            if (oldfile.renameTo(newfile)) {
                APP_LOG.info("Rename successfull for summary report");
            } else {
                APP_LOG.info("Rename failed for summary report");
            }
        } catch (Exception e) {
            APP_LOG.error("Error occured while finding file" + e);
        }

    }

    public static void renameExtentReport(String Browser) {
        try {
            File oldfile = new File("src/test/resources/jenkinsLatestReport/"
                    + Browser + "_ExecutionReport_" + BaseClass.reportStartTime
                    + ".html");
            File newfile = new File(
                    "src/test/resources/jenkinsLatestReport/ExtentReport.html");
            if (oldfile.renameTo(newfile)) {
                APP_LOG.info("Rename successfull for extent report");
            } else {
                APP_LOG.info("Rename failed for extent report");
            }
        } catch (Exception e) {
            APP_LOG.error("Error occured while finding file" + e);

        }

    }

    /**
     * @author sumit.bhardwaj
     * @param dest
     *            -> Folder Path
     * @return --> Pass/Fail
     * @description --> Deletes folder
     */
    public synchronized String deleteFolder(String dest) {
        try {
            File f = new File(dest);
            deleteFile(dest);
            f.delete();
            APP_LOG.info("Deleted " + dest + " folder and it's files");
            return Constants.PASS + ": Deleted " + dest
                    + " folder and it's files";

        } catch (Exception e) {
            APP_LOG.error(
                    "Error while delting " + dest + " folder and it's files");
            return Constants.FAIL + "Error while delting " + dest
                    + " folder and it's files";
        }
    }

    public static void deleteFile(String dest, String extention) {
        File file = new File(dest);
        File[] fetchAllFiles;
        boolean b;
        if (file.isDirectory()) {
            fetchAllFiles = file.listFiles();
            for (File f : fetchAllFiles) {
                b = false;
                if (f.getName().contains(extention)) {
                    b = f.delete();
                    if (b)
                        APP_LOG.info(f.getName() + " File is deleted");
                }
            }
        }
    }

}
