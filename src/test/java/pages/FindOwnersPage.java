package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindOwnersPage extends BasePage {

	@FindBy(id = "lastName")
	private WebElement searchField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitButton;

	@FindBy(css = "a.btn.btn-primary")
	private WebElement addOwnerButton;

	public void fillSearchFieldAndPressSubmit(String text) {
		wait.until(ExpectedConditions.visibilityOf(searchField));
		searchField.sendKeys(text);
		submitButton.click();
	}

	public void pressAddOwnerButton() {
		addOwnerButton.click();
	}

}
