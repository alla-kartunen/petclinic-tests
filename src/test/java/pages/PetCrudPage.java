package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PetCrudPage extends BasePage {

	@FindBy(css = ".col-sm-10 span:not(:empty)")
	private WebElement ownerName;

	@FindBy(id = "name")
	private WebElement petNameField;

	@FindBy(id = "birthDate")
	private WebElement birthDateField;

	@FindBy(id = "type")
	private WebElement typeSelect;

	@FindBy(css = "option[value='cat']")
	private WebElement typeCat;

	@FindBy(css = "option[value='dog']")
	private WebElement typeDog;

	@FindBy(css = "option[value='lizard']")
	private WebElement typeLizard;

	@FindBy(css = "option[value='snake']")
	private WebElement typeSnake;

	@FindBy(css = "option[value='bird']")
	private WebElement typeBird;

	@FindBy(css = "option[value='hamster']")
	private WebElement typeHamster;

	@FindBy(css = "button[type='submit']")
	private WebElement addPetButton;

	@FindBy(css = "button[type='submit']")
	private WebElement updatePetButton;

	public void waitForPageTitle() {
		wait.until(ExpectedConditions.visibilityOf(typeSelect));
	}

	public String getOwnerName() {
		String result = ownerName.getText();
		return result;
	}

	public void fillPetNameField(String petName) {
		petNameField.sendKeys(petName);
	}

	public void fillBirthDateField(String birthDate) {
		birthDateField.sendKeys(birthDate);
	}

	public void setType(int typeId) {
		typeSelect.click();
		wait.until(ExpectedConditions.visibilityOf(typeCat));

		switch (typeId) {
			case 1:
				typeCat.click();
				break;
			case 2:
				typeDog.click();
				break;
			case 3:
				typeLizard.click();
				break;
			case 4:
				typeSnake.click();
				break;
			case 5:
				typeBird.click();
				break;
			case 6:
				typeHamster.click();
				break;
		}
	}

	public void clearFields() {
		petNameField.clear();
		birthDateField.clear();
	}

	public void clickAddPetButton() {
		addPetButton.click();
	}

	public void clickUpdatePetButton() {
		updatePetButton.click();
	}
}
