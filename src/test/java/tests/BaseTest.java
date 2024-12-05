package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import steps.*;
import tests.managers.OwnersManager;
import tests.managers.PetsManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;

import static tests.BaseTest.Log.*;
import java.util.ArrayList;

public abstract class BaseTest {

	protected enum Log {
		START_TEST, END_TEST,
		START_CLEANING, END_CLEANING,
		OPEN_PAGE
	}

	public static WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	protected final Logger logger = LogManager.getLogger(this.getClass());
	private final String host = "http://localhost:8080";
	private final String ownerStartPage = host + "/owners/";
	private final String ownerFindPage = host + "/owners/find";
	private final String separator = "-------------------------";

	BaseStep baseStep;
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

		baseStep = new BaseStep();
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

	protected void openOwnerPageById(int ownerId) {
		log(START_TEST, null);
		log(OPEN_PAGE, ownerStartPage + ownerId);
		driver.get(ownerStartPage + ownerId);
	}

	protected void openFindOwnerPage() {
		log(START_TEST, null);
		log(OPEN_PAGE, ownerFindPage);
		driver.get(ownerFindPage);
	}

	protected Owner prepareOwnerForCreate(ArrayList<Object> data) {
		return baseStep.clearOwnerInDb(data);
	}

	protected Owner prepareOwnerForEdit(Owner owner) {
		return baseStep.prepareOwnerForEdit(owner);
	}

	protected Owner createOwnerInDb(ArrayList<Object> data) {
		return baseStep.createOwnerInDb(data);
	}

	protected ArrayList<Owner> createListOfOwnersInDb(String testCase) {
		return baseStep.createListOfOwnersInDb(testCase);
	}

	protected Pet preparePetForCreate(ArrayList<Object> data, Owner owner) {
		return baseStep.preparePet(data, owner);
	}

	protected Pet preparePetForEdit(Pet pet, Owner owner) {
		return baseStep.preparePetForEdit(pet, owner);
	}

	protected Pet createPetInDb(ArrayList<Object> data, Owner owner) {
		return baseStep.createPetInDb(data, owner);
	}

	protected void clearTestData(int ownerId) {
		log(START_CLEANING, null);
		ownersManager.deleteOwner(ownerId);
		log(END_CLEANING, null);
	}

	protected void clearTestData(int ownerId, int petId) {
		log(START_CLEANING, null);
		petsManager.deletePet(petId);
		ownersManager.deleteOwner(ownerId);
		log(END_CLEANING, null);
	}

	protected void log(Log log, String object) {
		switch (log) {
			case START_TEST:
				logger.info(separator + " TEST STARTS " + separator);
				break;
			case END_TEST:
				logger.info(separator + "{} data is prepared " + separator, object);
				break;
			case START_CLEANING:
				logger.info(separator + " Start cleaning of test data " + separator);
				break;
			case END_CLEANING:
				logger.info(separator + " End cleaning of test data " + separator);
				break;
			case OPEN_PAGE:
				logger.info("Open {}", object);
				break;
		}
	}
}
