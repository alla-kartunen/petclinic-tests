package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import steps.*;
import tests.managers.OwnersManager;
import tests.managers.PetsManager;

import java.io.File;

public abstract class BaseTest {

	public static WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

	FindOwnerSteps findOwnerSteps;
	OwnerInformationSteps ownerInformationSteps;
	OwnerCrudSteps ownerCrudSteps;
	SearchResultsSteps searchResultsSteps;
	PetCrudSteps petCrudSteps;

	OwnersManager ownersManager;
	PetsManager petsManager;

	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}

	@BeforeClass
	public WebDriver setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		tdriver.set(driver);

		findOwnerSteps = new FindOwnerSteps();
		ownerInformationSteps = new OwnerInformationSteps();
		ownerCrudSteps = new OwnerCrudSteps();
		searchResultsSteps = new SearchResultsSteps();
		petCrudSteps = new PetCrudSteps();

		ownersManager = new OwnersManager();
		petsManager = new PetsManager();

		return getDriver();
	}

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public void getOwnerPageById(int ownerId) {
		driver.get("http://localhost:8080/owners/" + ownerId);
	}

	public void getFindOwnerPage() {
		driver.get("http://localhost:8080/owners/find");
	}

}
