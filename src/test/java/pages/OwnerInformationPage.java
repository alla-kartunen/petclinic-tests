package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class OwnerInformationPage extends BasePage {

	@FindBy(xpath = "//h2[contains(text(),'Owner Information')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//table//tr[1]/td")
	private WebElement ownerName;

	@FindBy(xpath = "//table//tr[2]/td")
	private WebElement ownerAddress;

	@FindBy(xpath = "//table//tr[3]/td")
	private WebElement ownerCity;

	@FindBy(xpath = "//table//tr[4]/td")
	private WebElement ownerTelephone;

	@FindBy(css = "a.btn")
	private WebElement editOwnerButton;

	@FindBy(linkText = "Add New Pet")
	private WebElement addPetButton;

	@FindBy(css = ".table-condensed a")
	private WebElement editPetButton;

	@FindBy(xpath = "//dl[@class='dl-horizontal']/dd")
	private WebElement firstPetName;

	@FindBy(xpath = "(//dl[@class='dl-horizontal']/dd)[2]")
	private WebElement firstPetBirthDate;

	@FindBy(xpath = "(//dl[@class='dl-horizontal']/dd)[3]")
	private WebElement firstPetType;

	public void waitForPageTitle() {
		wait.until(ExpectedConditions.visibilityOf(pageTitle));
	}

	public String getOwnerName() {
		String result = ownerName.getText();
		return result;
	}

	public String getOwnerAddress() {
		String result = ownerAddress.getText();
		return result;
	}

	public String getOwnerCity() {
		String result = ownerCity.getText();
		return result;
	}

	public String getOwnerTelephone() {
		String result = ownerTelephone.getText();
		return result;
	}

	public String getFirstPetName() {
		String result = firstPetName.getText();
		return result;
	}

	public String getFirstPetBirthDate() {
		String result = firstPetBirthDate.getText();
		return result;
	}

	public String getFirstPetType() {
		String result = firstPetType.getText();
		return result;
	}

	public void clickEditOwnerButton() {
		editOwnerButton.click();
	}

	public void clickAddPetButton() {
		addPetButton.click();
	}

	public void clickEditPetButton() {
		editPetButton.click();
	}

	public int countPets() {
		return driver.findElements(By.xpath("//table//dt[contains(text(),'Name')]")).size();
	}

}
