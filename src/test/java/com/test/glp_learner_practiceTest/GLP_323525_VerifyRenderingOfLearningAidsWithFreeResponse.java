package com.test.glp_learner_practiceTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

public class GLP_323525_VerifyRenderingOfLearningAidsWithFreeResponse
        extends BaseClass {
    public GLP_323525_VerifyRenderingOfLearningAidsWithFreeResponse() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Test to verify the rendering of learning aids for the free response activity type")
    public void verifyRenderingOfLearningAidsWithFR() {
        startReport(getTestCaseId(),
                "Test to verify the rendering of learning aids for the free response activity type");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_GS_OFF"), true);

            // Login in the application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);

            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile Present and navigate to Welcome LearnerScreen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            GLPLearner_CourseHomePage objProductApplicationCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            // Click on 'Start Pre-assessment' button
            objProductApplicationCourseHomePage.navigateToDiagnosticPage();

            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = new GLPLearner_DiagnosticTestPage(
                    reportTestObj, APP_LOG);

            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module12Text"),
                    ResourceConfigurations
                            .getProperty("subModule12_6AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Navigate to the question of the practice test having LA
            objGLPLearnerPracticeTestPage
                    .navigateToQuestionTypeWithLearningAids(
                            ResourceConfigurations
                                    .getProperty("fibFreeResponse"),
                            ResourceConfigurations
                                    .getProperty("diagnosticSubmitButton"));

            // Get the text of the question displayed
            String strOriginalText = objGLPLearnerPracticeTestPage
                    .getText("PracticeQuestionDisplayedText");
            // Open the desired Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsHelpMeSolveThis"));
            // Close the Help Me Solve This window
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "LearningAidsHeaderCloseIcon",
                    "Click on cross icon in the header of the HMST window");
            // Check whether the Learning Aids window gets closed
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "LearningAidsBody",
                    "Verify the Learning Aids window gets closed");
            // Verify the original Practice item gets refreshed
            String strRefreshedText = objGLPLearnerPracticeTestPage
                    .getText("PracticeQuestionDisplayedText");
            objGLPLearnerPracticeTestPage
                    .verifyPracticeQuestionContentIsUpdated(strOriginalText,
                            strRefreshedText, false);
            // Assigning the value of strRefreshedText to strOriginalText
            strOriginalText = strRefreshedText;

            // Open View An Example
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsViewAnExample"));
            // Close the View An Example window
            objGLPLearnerPracticeTestPage.clickOnElement(
                    "LearningAidsHeaderCloseIcon",
                    "Click on cross icon in the header of the View An Example window");
            // Check whether the Learning Aids window gets closed
            objGLPLearnerPracticeTestPage.verifyElementNotPresent(
                    "LearningAidsBody",
                    "Verify the Learning Aids window gets closed");
            // Verify the original Practice item does not gets refreshed
            strRefreshedText = objGLPLearnerPracticeTestPage
                    .getText("PracticeQuestionDisplayedText");
            objGLPLearnerPracticeTestPage
                    .verifyPracticeQuestionContentIsUpdated(strOriginalText,
                            strRefreshedText, true);
        } finally {
            webDriver.quit();
            webDriver = null;
        }
    }
}
