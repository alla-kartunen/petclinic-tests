package listeners;

import config.PropertiesManager;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;

public class AllureListener implements ISuiteListener, IInvokedMethodListener {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected static ITestContext iTestContext;
    private static WebDriver driver;

    private static PropertiesManager propertiesManager = new PropertiesManager().init();
    private final String resultsPropName = "allure.results.directory";
    private final String reportPropName = "allure.report.directory";
    private String allureResultsPath = propertiesManager.getProperty(resultsPropName);
    private String allureReportPath = propertiesManager.getProperty(reportPropName);
    private static boolean isAllureCleaned = false;
    private static boolean isAllureReportGenerated = false;

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = ".png")
    public static byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain", fileExtension = ".txt")
    public static String saveTestLog(String message) {
        return message;
    }

    @Override
    public void onStart(ISuite iSuite) {
        if (!isAllureCleaned) {
            try {
                File allureResultsFile = new File(allureResultsPath);
                if (allureResultsFile.exists()) {
                    FileUtils.cleanDirectory(allureResultsFile);
                    logger.info("Cleared allure results directory " + allureResultsFile);
                }
            } catch (IOException ignored) {
                logger.warn("Directory " + allureResultsPath + " not fount.");
            }
            isAllureCleaned = true;
        }

        if (BaseTest.getDriver() != null) { iTestContext.setAttribute("WebDriver", BaseTest.getDriver()); }
    }

    @Override
    public void onFinish(ISuite iSuite) {
        if (!isAllureReportGenerated) {
            try {
                Process process = getProcess();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    logger.info("Allure report is generated successfully.");
                } else {
                    logger.warn("Failed to generate Allure report.");
                }
            } catch (IOException | InterruptedException ignored) {
            }
            isAllureReportGenerated = true;
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() &&
                (testResult.getStatus() == ITestResult.FAILURE || testResult.getStatus() == ITestResult.SKIP)) {
            saveFailureScreenShot(driver);
        }
    }

    private Process getProcess() throws IOException {
        String os = System.getProperty("os.name");
        String command, option;
        if (os.contains("Windows")) {
            command = "cmd.exe";
            option = "/c";
        } else {
            command = "bash";
            option = "-c";
        }
        ProcessBuilder builder = new ProcessBuilder(
                command, option, "allure generate " + allureResultsPath + " -o " + allureReportPath + " --clean"
        );
        builder.inheritIO();
        logger.info("Start command: " + builder.command());
        return builder.start();
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

}
