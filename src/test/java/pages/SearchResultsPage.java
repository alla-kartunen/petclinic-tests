package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//h2[contains(text(),'Owners')]")
    private WebElement pageTitle;

    @FindBy(css = ".glyphicon-triangle-right")
    private WebElement nextPage;

    @FindBy(css = ".glyphicon-triangle-left")
    private WebElement previewsPage;

    public void waitForPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
    }

    public String getOwnerNameByIndex(int num) {
        String result = driver.findElement(By.xpath("//table//tr[" + num + "]/td[1]")).getText();
        return result;
    }

    public String getOwnerAddressByIndex(int num) {
        String result = driver.findElement(By.xpath("//table//tr[" + num + "]/td[2]")).getText();
        return result;
    }

    public String getOwnerCityByIndex(int num) {
        String result = driver.findElement(By.xpath("//table//tr[" + num + "]/td[3]")).getText();
        return result;
    }

    public String getOwnerTelephoneByIndex(int num) {
        String result = driver.findElement(By.xpath("//table//tr[" + num + "]/td[4]")).getText();
        return result;
    }

    public void clickOnNextPageArrow() {
        nextPage.click();
    }

    public void clickOnPreviewsPageArrow() {
        previewsPage.click();
    }

    public int getPageByNumber(int num) {
        int result = driver.findElements(By.linkText("" + num)).size();
        return result;
    }

    public void clickOnPageByNumber(int num) {
        driver.findElement(By.linkText("" + num)).click();
    }

    public boolean isPaginationDisplayed() {
        if (driver.findElements(By.xpath("//span[contains(text(),'Pages:')]")).size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPageNumberDisplayed(int num) {
        if (driver.findElements(By.linkText("" + num)).size() == 0) {
            return false;
        }  else {
            return true;
        }
    }

    public boolean isArrowInactive(String typeOfArrow) {
        switch (typeOfArrow) {
            case "First":
                break;
            case "Previous":
                break;
            case "Next":
                break;
            case "Last":
                break;
            default:
                throw new NullPointerException(
                        "Pagination's arrows names doesn't contains title \"" + typeOfArrow + "\"");
        }

        if (driver.findElements(By.xpath("//a[@title='" + typeOfArrow + "']")).size() == 0) {
            return true;
        }  else {
            return false;
        }
    }


}
