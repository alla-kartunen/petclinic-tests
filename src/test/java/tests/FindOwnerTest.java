package tests;

import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import testsData.AllureListener;
import testsData.TestDataBuilder;
import testsData.GetData;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class FindOwnerTest extends BaseTest {

    private TestDataBuilder testData = new TestDataBuilder();

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify finding owner with unique lastname</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Search")
    @Feature("Find owners with unique lastname")
    void findUniqueOwnerTest(ArrayList<Object> data) {

        getFindOwnerPage();

        Owner owner = testData.getOwner(data);

        int ownerID = ownersManager.createOwnerAndGetId(owner);

        boolean isUniqueLastname = searchResultsSteps.verifyThatLastnameIsUnique(owner.getLastName());

        if (isUniqueLastname == false) {
            throw new SkipException("Incorrect data in Excel file! Owner with such lastname already exist in database.");
        }

        findOwnerSteps.searchUniqueOwner(owner.getLastName())
                        .verifyOwnerInformationOnPage(owner);

        ownersManager.deleteOwner(ownerID);
        ownerInformationSteps.endOfSoftAssert();

    }

    @Test(priority = 1)
    @Description("<b>Verify finding owners with same lastname and their data</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Search")
    @Feature("Find owners with same lastname")
    void findMoreThanOneOwnerTest() {

        getFindOwnerPage();

        GetData getData = new GetData();

        Object[][] allOwners = getData.ownersWithSameLastnameData("findMoreThanOneOwnerTest");

        ArrayList<Owner> ownersList = testData.prepareSearchData(allOwners);

        findOwnerSteps.searchMoreThanOneOwner(ownersList)
                        .verifyListOfOwnersOnSearchPage(ownersList)
                        .deleteOwnersListFromDB(ownersList)
                        .endOfSoftAssert();

    }

    @Test(priority = 2)
    @Description("<b>Verify numbers of page on search result page</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifySearchPaginationNumbersTest() {

        getFindOwnerPage();

        GetData getData = new GetData();

        Object[][] allOwners = getData.ownersWithSameLastnameData("verifySearchPaginationNumbersTest");

        ArrayList<Owner> ownersList = testData.prepareSearchData(allOwners);

        findOwnerSteps.searchMoreThanOneOwner(ownersList)
                        .verifyQuantityOfOwners(ownersList)
                        .verifyPageNumbers(ownersList)
                        .deleteOwnersListFromDB(ownersList)
                        .endOfCustomSoftAssert();

    }

    @Test(priority = 2)
    @Description("<b>Verify search pagination by number of page</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifyClickOnSearchPageNavigatorTest() {

        getFindOwnerPage();

        GetData getData = new GetData();

        Object[][] allOwners = getData.ownersWithSameLastnameData("verifyClickOnSearchPageNavigatorTest");

        ArrayList<Owner> ownersList = testData.prepareSearchData(allOwners);

        findOwnerSteps.searchMoreThanOneOwner(ownersList)
                        .verifyQuantityOfOwners(ownersList)
                        .verifyPageNavigationByNumber(ownersList)
                        .deleteOwnersListFromDB(ownersList)
                        .endOfCustomSoftAssert();
    }

    @Test(priority = 2)
    @Description("<b>Verify search pagination by arrow</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifyClickOnSearchPageArrowTest() {

        getFindOwnerPage();

        GetData getData = new GetData();

        Object[][] allOwners = getData.ownersWithSameLastnameData("verifyClickOnSearchPageArrowTest");

        ArrayList<Owner> ownersList = testData.prepareSearchData(allOwners);

        findOwnerSteps.searchMoreThanOneOwner(ownersList)
                        .verifyQuantityOfOwners(ownersList)
                        .verifyNavigationArrows(ownersList)
                        .deleteOwnersListFromDB(ownersList)
                        .endOfCustomSoftAssert();
    }

}
