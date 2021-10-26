package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static tests.BaseTest.getDriver;

public abstract class BasePage {

	WebDriver driver;
	WebDriverWait wait;

	public BasePage() {
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
}
