package steps;

import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import pages.SearchResultsPage;
import tests.managers.OwnersManager;
import tests.objectsAndMappers.Owner;
import testsData.CustomSoftAssert;

import java.util.ArrayList;

public class SearchResultsSteps {

    private SearchResultsPage searchResultsPage = new SearchResultsPage();
    private CustomSoftAssert customSoftAssert = new CustomSoftAssert();
    private SoftAssert softAssert = new SoftAssert();
    private OwnersManager ownersManager = new OwnersManager();

    public SearchResultsSteps verifyListOfOwnersOnSearchPage(ArrayList<Owner> ownersList) {

        searchResultsPage.waitForPageTitle();

        int page = 1;

        for (int i = 1; i <= ownersList.size(); i++) {
            int j;
            if ((i != 1) & ((i % 5 == 1))) {
                searchResultsPage.clickOnNextPageArrow();
                j = 1;
                page++;
            } else if (i % 5 == 0) {
                j = 5;
            } else {
                j = i % 5;
            }

            Owner owner = ownersList.get(i - 1);
            String expectedFullOwnerName = (owner.getFirstName() + " " + owner.getLastName());

            softAssert.assertEquals(searchResultsPage.getOwnerNameByIndex(j), expectedFullOwnerName,
                    "On page " + page + ", line " + (j) + " incorrect full name:");
            softAssert.assertEquals(searchResultsPage.getOwnerAddressByIndex(j), owner.getAddress(),
                    "On page " + page + ", line " + (j) + " incorrect address:");
            softAssert.assertEquals(searchResultsPage.getOwnerCityByIndex(j), owner.getCity(),
                    "On page " + page + ", line " + (j) + " incorrect city:");
            softAssert.assertEquals(searchResultsPage.getOwnerTelephoneByIndex(j), owner.getTelephone(),
                    "On page " + page + ", line " + (j) + " incorrect telephone:");
        }
        return this;
    }

    public SearchResultsSteps deleteOwnersListFromDB(ArrayList<Owner> ownersList) {
        OwnersManager ownersManager = new OwnersManager();
        int listLength = ownersList.size();

        for (int i = 0; i < listLength; i++) {
            int ownerId = ownersList.get(i).getId();
            ownersManager.deleteOwner(ownerId);
        }
        return this;
    }

    public SearchResultsSteps verifyQuantityOfOwners(ArrayList<Owner> ownersList) {
        int listLength = ownersList.size();

        if (listLength == 1) {
            throw new SkipException("Incorrect data in Excel file! " +
                    "There must be more than 1 owner for search pagination test.");
        }
        return this;
    }

    public SearchResultsSteps verifyPageNumbers(ArrayList<Owner> ownersList) {

        int pagination = getPaginationSize(ownersList);

        if (pagination > 1) {

            for (int i = 2; i < pagination; i++) {
                // Check quantity of elements with pagination link "i"
                int count = searchResultsPage.getPageByNumber(i);

                if (count != 1) {
                    customSoftAssert.assertEquals(count, 1,
                            "Incorrect pagination. Elements with link " + i + ": " + count);
                }
            }
            int next = searchResultsPage.getPageByNumber(pagination + 1);
            customSoftAssert.assertEquals(next, 0,
                    "Incorrect pagination. For pagination size " + pagination +
                            " there's " + (pagination + 1) + "page");
        } else if (pagination == 1) {
            boolean result = searchResultsPage.isPaginationDisplayed();

            customSoftAssert.assertEquals(result, false,
                    "Search result page for less than 6 search result contains pagination!");
        }
        return this;
    }

    public SearchResultsSteps verifyPageNavigationByNumber(ArrayList<Owner> ownersList) {

        int pagination = getPaginationSize(ownersList);

        if (pagination > 1) {
            for (int i = 2; i <= pagination; i++) {
                int count = searchResultsPage.getPageByNumber(i);

                customSoftAssert.assertEquals(count, 1,
                        "Unexpected quantity of pages with number " + i);

                // If this is a last page, verify there isn't next page
                if (i == pagination) {
                    boolean result = searchResultsPage.isPageNumberDisplayed(pagination + 1);
                    customSoftAssert.assertEquals(result, false,
                            "There's extra page number on the last page of search results!" +
                                    "Expected number of pages: " + pagination + ".\n");
                }
                searchResultsPage.clickOnPageByNumber(i);
            }
            for (int i = pagination; i >= 1; i--) {
                searchResultsPage.clickOnPageByNumber(i);
            }
        } else {
            throw new SkipException(
                    "Incorrect data in Excel file! It must contains more than 5 owners for pagination tests!");
        }
        return this;
    }

    public SearchResultsSteps verifyNavigationArrows(ArrayList<Owner> ownersList) {

        int pagination = getPaginationSize(ownersList);

        if (pagination > 1) {
            for (int i = 1; i < pagination; i++) {
                // If this is the first page, verify that previous arrow isn't active
                if (i == 1) {
                    boolean result = searchResultsPage.isArrowInactive("Previous");
                    customSoftAssert.assertEquals(result, true,
                            "Arrow \"Previous\" is active on the first page of search results!");
                }

                searchResultsPage.clickOnNextPageArrow();

                // If this is the last page, verify that next arrow isn't active
                if (i == (pagination - 1)) {
                    boolean result = searchResultsPage.isArrowInactive("Next");
                    customSoftAssert.assertEquals(result, true,
                            "Arrow \"Next\" is active on the last page of search results!" +
                                    "Expected total pages: " + pagination + ".\n");
                }
            }
            for (int j = pagination; j > 1; j--) {
                searchResultsPage.clickOnPreviewsPageArrow();
            }
        } else {
            throw new SkipException(
                    "Incorrect data in Excel file! Data must contains more than 5 owners for pagination tests!");
        }
        return this;
    }

    public boolean verifyThatLastnameIsUnique(String lastname) {
        int lastnameCount = ownersManager.countOwnersWithLastname(lastname);

        if (lastnameCount == 0) {
            return true;
        }
        return false;
    }

    private int getPaginationSize(ArrayList<Owner> ownersList) {
        int listLength = ownersList.size();
        int pagination;
        if (listLength % 5 == 0) {
            pagination = listLength / 5;
        } else {
            pagination = 1 + listLength / 5;
        }
        return pagination;
    }

    public SearchResultsSteps endOfSoftAssert() {
        softAssert.assertAll();
        return this;
    }

    public SearchResultsSteps endOfCustomSoftAssert() {
        customSoftAssert.assertAll();
        return this;
    }

}
