package testsData;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

public class SoftAssertWithScreenShot extends SoftAssert {

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        WebDriver driver = BaseTest.getDriver();
        AllureListener allureListener = new AllureListener();
        allureListener.saveFailureScreenShot(driver);
    }

}
