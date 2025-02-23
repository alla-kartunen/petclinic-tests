package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import objects.Owner;
import dataproviders.GetData;

import java.util.ArrayList;

public class FindOwnerTest extends BaseTest {

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify finding owner with unique last name</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Search")
    @Feature("Find owner with unique last name")
    void findUniqueOwnerTest(ArrayList<Object> data) {
        Owner owner = createOwnerInDb(data);

        openFindOwnerPage();
        findOwnerSteps.searchOwner(owner.getLastName())
                        .verifyOwnerInformationPage(owner)
                        .endOfSoftAssert();
    }

    @Test(priority = 2)
    @Description("<b>Verify finding owners with the same last name and their data</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Search")
    @Feature("Find owners with the same last name")
    void findMoreThanOneOwnerTest() {
        ArrayList<Owner> ownersList = createListOfOwnersInDb("findMoreThanOneOwnerTest");

        openFindOwnerPage();
        findOwnerSteps.searchOwner(ownersList)
                        .verifyListOfOwnersOnSearchPage(ownersList)
                        .endOfSoftAssert();
    }

    @Test(priority = 3)
    @Description("<b>Verify no pagination on search result page</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifyNoPaginationTest() {
        ArrayList<Owner> ownersList = createListOfOwnersInDb("verifyNoPaginationTest");

        openFindOwnerPage();
        findOwnerSteps.searchOwner(ownersList)
                        .verifyPageNumbers(ownersList);
    }

    @Test(priority = 4)
    @Description("<b>Verify numbers of page on search result page</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifySearchPaginationNumbersTest() {
        ArrayList<Owner> ownersList = createListOfOwnersInDb("verifySearchPaginationNumbersTest");

        openFindOwnerPage();
        findOwnerSteps.searchOwner(ownersList)
                        .verifyPageNumbers(ownersList);
    }

    @Test(priority = 5)
    @Description("<b>Verify search pagination by number of page</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifyPageNavigationByNumberTest() {
        ArrayList<Owner> ownersList = createListOfOwnersInDb("verifyPageNavigationByNumberTest");

        openFindOwnerPage();
        findOwnerSteps.searchOwner(ownersList)
                        .verifyPageNavigationByNumber(ownersList);
    }

    @Test(priority = 6)
    @Description("<b>Verify search pagination by arrow</b>")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Search")
    @Feature("Verify search pagination")
    void verifyPageNavigationByArrowTest() {
        ArrayList<Owner> ownersList = createListOfOwnersInDb("verifyPageNavigationByArrowTest");

        openFindOwnerPage();
        findOwnerSteps.searchOwner(ownersList)
                        .verifyNavigationArrows(ownersList);
    }

}
