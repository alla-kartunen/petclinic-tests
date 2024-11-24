package testsData;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class AllureListener implements ITestListener {

    public static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTestLog(String message) {
        return message;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        if (BaseTest.getDriver() != null) { iTestContext.setAttribute("WebDriver", BaseTest.getDriver()); }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        WebDriver driver = BaseTest.getDriver();

        if (driver instanceof WebDriver) {
            saveFailureScreenShot(driver);
        }
        saveTestLog(getTestMethodName(iTestResult) + " failed! Screenshot is taken.");
    }

}
