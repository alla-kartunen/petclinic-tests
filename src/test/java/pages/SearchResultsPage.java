package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.TestException;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//h2[contains(text(),'Owners')]")
    private WebElement pageTitle;

    @FindBy(css = ".fa.fa-step-forward")
    private WebElement nextPage;

    @FindBy(css = ".fa.fa-step-backward")
    private WebElement previewsPage;

    public void waitForPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
    }

    public String getOwnerNameByIndex(int num) {
        return driver.findElement(By.xpath("//table//tr[" + num + "]/td[1]")).getText();
    }

    public String getOwnerAddressByIndex(int num) {
        return driver.findElement(By.xpath("//table//tr[" + num + "]/td[2]")).getText();
    }

    public String getOwnerCityByIndex(int num) {
        return driver.findElement(By.xpath("//table//tr[" + num + "]/td[3]")).getText();
    }

    public String getOwnerTelephoneByIndex(int num) {
        return driver.findElement(By.xpath("//table//tr[" + num + "]/td[4]")).getText();
    }

    public void clickOnNextPageArrow() {
        nextPage.click();
    }

    public void clickOnPreviewsPageArrow() {
        previewsPage.click();
    }

    public void clickOnPageByNumber(int num) {
        driver.findElement(By.xpath("//a[text()='" + num + "']")).click();
    }

    public boolean isPaginationDisplayed() {
        return !driver.findElements(By.xpath("//span[contains(text(),'Pages:')]")).isEmpty();
    }

    public boolean isPageNumberDisplayed(int num) {
        return !driver.findElements(By.xpath("//span[contains(text(),'Pages:')]/../span/span[contains(text(),'" + num + "')]")).isEmpty();
    }

    public boolean isPageLinkDisplayed(int num) {
        return !driver.findElements(By.xpath("//a[text()='" + num + "']")).isEmpty();
    }

    public boolean isPageLinkNotDisplayed(int num) {
        return driver.findElements(By.xpath("//a[text()='" + num + "']")).isEmpty();
    }

    public boolean isArrowDisplayed(String typeOfArrow) {
        checkTypeOfArrow(typeOfArrow);
        return !driver.findElements(By.xpath("//span[contains(text(),'Pages:')]/../span/span[@title='" + typeOfArrow + "']")).isEmpty();
    }

    public boolean isArrowLinkDisplayed(String typeOfArrow) {
        checkTypeOfArrow(typeOfArrow);
        return !driver.findElements(By.xpath("//a[@title='" + typeOfArrow + "']")).isEmpty();
    }

    private void checkTypeOfArrow(String typeOfArrow) {
        if (!(typeOfArrow.equals("First") || (typeOfArrow.equals("Previous"))||
                (typeOfArrow.equals("Next"))|| (typeOfArrow.equals("Last"))))
                throw new TestException(
                        "Pagination arrow with title \"" + typeOfArrow + "\" doesn't exist");
    }

}
