package com.test.glp_sanity;

import java.io.IOException;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.util.GLP_Utilities;

import de.sstoehr.harreader.HarReaderException;

public class GLP_358013_VerifyCourseSubscriptionFromUI extends BaseClass {
    private String learnerUserName = null;
    private String instructorUserName = null;
    private String url = configurationsXlsMap.get("ConsoleUrl")
            + "/enrollment/";

    public GLP_358013_VerifyCourseSubscriptionFromUI() {

    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR, Groups.LEARNER,
            Groups.SANITY }, enabled = true,
            description = "Verify new course subscription and navigation to GLP Home Page working for newly created user from UI")
    public void VerifyCourseSubscriptionFromUI()
            throws IOException, HarReaderException {
        startReport(getTestCaseId(),
                "Verify new course subscription and navigation to GLP Home Page working for newly created user from UI");

        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        GLPConsole_LoginPage objGLPConsoleLoginPage = new GLPConsole_LoginPage(
                reportTestObj, APP_LOG);
        GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                reportTestObj, APP_LOG);

        try {
            learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                    + objCommonUtil.generateRandomStringOfAlphabets(4);
            instructorUserName = configurationsXlsMap
                    .get("INSTRUCTOR_USER_NAME");
            // Create learner
            objRestUtil.createLearner(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    "student");

            // Get the invite key using the section ID
            String invitekey = objRestUtil.inviteKey(instructorUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Open the invite link in the browser
            objGLPConsoleLoginPage.openInternalURL(url + invitekey);

            // Click on Sign in
            objGLPConsoleLoginPage.clickOnElement("SignInFromSubscriptionLink",
                    "SignIn Button clicked from the subscription page");

            // Login in the application
            objGLPConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            // Click on info button on the course tile
            objGLPLearnerCourseViewPage.clickOnElement("CourseInfoButton",
                    "Course tile Info Button Clicked");

            // Verify the course
            objGLPLearnerCourseViewPage.verifyText("CourseName",
                    configurationsXlsMap.get("courseName"),
                    "Verify that the subscribed course is correct");

        } finally {
            if (unpublishData.equalsIgnoreCase("TRUE")) {
                objRestUtil.unpublishSubscribedCourseDatabase(learnerUserName,
                        ResourceConfigurations.getProperty("consolePassword"));
            }
            webDriver.quit();
            webDriver = null;
        }

    }

}
