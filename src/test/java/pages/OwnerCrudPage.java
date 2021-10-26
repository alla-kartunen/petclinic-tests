package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OwnerCrudPage extends BasePage {

	@FindBy(id = "firstName")
	private WebElement firstNameField;

	@FindBy(id = "lastName")
	private WebElement lastNameField;

	@FindBy(id = "address")
	private WebElement addressField;

	@FindBy(id = "city")
	private WebElement cityField;

	@FindBy(id = "telephone")
	private WebElement telephoneField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement addOwnerButton;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement updateOwnerButton;

	public void waitForFirstNameField() {
		wait.until(ExpectedConditions.visibilityOf(firstNameField));
	}

	public void fillFirstNameField(String firstName) {
		firstNameField.sendKeys(firstName);
	}

	public void fillLastNameField(String lastName) {
		lastNameField.sendKeys(lastName);
	}

	public void fillAddressField(String address) {
		addressField.sendKeys(address);
	}

	public void fillCityField(String city) {
		cityField.sendKeys(city);
	}

	public void fillTelephoneField(String telephone) {
		telephoneField.sendKeys(telephone);
	}

	public void clickAddOwnerButton() {
		addOwnerButton.click();
	}

	public void clearAllFields() {

		firstNameField.clear();
		lastNameField.clear();
		addressField.clear();
		cityField.clear();
		telephoneField.clear();

	}

	public void clickUpdateOwnerButton() {
		updateOwnerButton.click();
	}
}
