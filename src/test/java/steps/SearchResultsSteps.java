package steps;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import pages.SearchResultsPage;
import objects.Owner;
import objects.OwnerInfo;

import java.util.ArrayList;

public class SearchResultsSteps extends BaseStep {

    private SearchResultsPage searchResultsPage = new SearchResultsPage();
    protected SoftAssert softAssert = new SoftAssert();
    private OwnerInfo ownerMapper = new OwnerInfo();

    @Step("Verify list of Owners on search result page")
    public SearchResultsSteps verifyListOfOwnersOnSearchPage(ArrayList<Owner> ownersList) {
        searchResultsPage.waitForPageTitle();
        logger.info("Verify list of Owners on search result page");
        int page = 1;

        for (int i = 1; i <= ownersList.size(); i++) {
            int index;
            if ((i != 1) & ((i % 5 == 1))) {
                logger.info("Next page");
                searchResultsPage.clickOnNextPageArrow();
                index = 1;
                page++;
            } else if (i % 5 == 0) {
                index = 5;
            } else {
                index = i % 5;
            }

            Owner owner = ownersList.get(i - 1);
            verifyOwnerOnSearchPage(page, index, ownerMapper.mapOwner(owner));
        }
        logger.info("Verification of Owners list on search result page is ended");
        return this;
    }

    public void verifyOwnerOnSearchPage(int page, int index, OwnerInfo expectedOwner) {
        logger.info("Start: verification of Owner information [" + expectedOwner.getFullName() + "]");
        OwnerInfo actualOwner = new OwnerInfo();
        actualOwner.setFullName(searchResultsPage.getOwnerNameByIndex(index));
        actualOwner.setAddress(searchResultsPage.getOwnerAddressByIndex(index));
        actualOwner.setCity(searchResultsPage.getOwnerCityByIndex(index));
        actualOwner.setTelephone(searchResultsPage.getOwnerTelephoneByIndex(index));
        Assert.assertEquals(actualOwner, expectedOwner,
                    "Owner data is incorrect on page " + page + ", line " + (index) + ": ");
        logger.info("Verification of Owner information is ended [" + expectedOwner.getFullName() +
                "].");
    }

    @Step("Verify page numbers on search results page")
    public SearchResultsSteps verifyPageNumbers(ArrayList<Owner> ownersList) {
        logger.info("Start: verification of page numbers");
        int pagination = getPaginationSize(ownersList);
        logger.info("Expected pagination size: " + pagination);

        if (pagination > 1) {
            checkActivePageNumber(1);
            for (int pageNum = 2; pageNum <= pagination; pageNum++) {
                checkLinkToPageNumber(pageNum);
                // Verify there is no additional page after the last one
                if (pageNum == pagination) {
                    checkNoLinkToPageNumber(pageNum + 1);
                }
            }
        } else if (pagination == 1) {
            checkNoPaginationDisplayed();
        }
        logger.info("End: verification of page numbers");
        return this;
    }

    @Step("Verify page navigation by number")
    public SearchResultsSteps verifyPageNavigationByNumber(ArrayList<Owner> ownersList) {
        int pagination = getPaginationSize(ownersList);
        if (pagination > 1) {
            // Go forward
            for (int pageNum = 2; pageNum <= pagination; pageNum++) {
                clickOnPageNumber(pageNum);
                checkLinkToPageNumber(pageNum - 1);
                checkActivePageNumber(pageNum);
                // Verify there is no additional page after the last one
                if (pageNum == pagination) {
                    checkNoLinkToPageNumber(pageNum + 1);
                }
            }
            // Go back
            for (int pageNum = pagination; pageNum >= 1; pageNum--) {
                pageNum--;
                clickOnPageNumber(pageNum);
                checkActivePageNumber(pageNum);
                checkLinkToPageNumber(pageNum + 1);
            }
        } else {
            throw new SkipException(
                    "Incorrect data in Excel file! It must contains more than 5 owners for pagination tests!");
        }
        return this;
    }

    @Step("Verify navigation arrows")
    public SearchResultsSteps verifyNavigationArrows(ArrayList<Owner> ownersList) {
        int pagination = getPaginationSize(ownersList);
        if (pagination > 1) {
            for (int pageNum = 1; pageNum <= pagination; pageNum++) {
                checkArrows(pageNum, pagination);
                searchResultsPage.clickOnNextPageArrow();
            }
            for (int pageNum = pagination; pageNum >= 1; pageNum--) {
                checkArrows(pageNum, pagination);
                searchResultsPage.clickOnPreviewsPageArrow();
            }
        } else {
            throw new SkipException(
                    "Incorrect data in Excel file! Data must contains more than 5 owners for pagination tests!");
        }
        return this;
    }

    @Step("Click on page N{pageNum}")
    private void clickOnPageNumber(int pageNum) {
        logger.info("Click on Page N" + pageNum);
        searchResultsPage.clickOnPageByNumber(pageNum);
    }

    @Step("Summarizing soft assertion")
    public void endOfSoftAssert() {
        logger.info(separator + " TEST ENDS " + separator);
        softAssert.assertAll();
    }

    private int getPaginationSize(ArrayList<Owner> ownersList) {
        int listLength = ownersList.size();
        if (listLength % 5 == 0) {
            return listLength / 5;
        } else {
            return 1 + listLength / 5;
        }
    }

    @Step("Check active page N{num}")
    private void checkActivePageNumber(int num) {
        logger.info("Check active page number " + num);
        Assert.assertEquals(searchResultsPage.isPageNumberDisplayed(num), true,
                "Incorrect pagination. Active page N" + num + " is not displayed.");
    }

    @Step("Check link to page N{num}")
    private void checkLinkToPageNumber(int num) {
        logger.info("Check link to page number " + num);
        Assert.assertEquals(searchResultsPage.isPageLinkDisplayed(num), true,
                "Incorrect pagination. Link to page N" + num + " is not displayed.");
    }

    @Step("Check no link to page N{num}")
    private void checkNoLinkToPageNumber(int num) {
        logger.info("Check no link to page number " + num);
        Assert.assertEquals(searchResultsPage.isPageLinkNotDisplayed(num), true,
                "Incorrect pagination. Link to page N" + num + " is displayed.");
    }

    @Step("Check no pagination is displayed")
    private void checkNoPaginationDisplayed() {
        logger.info("Check no pagination is displayed");
        Assert.assertEquals(searchResultsPage.isPaginationDisplayed(), false,
                "Search result page for less than 6 search result contains pagination.");
    }

    @Step("Check arrows on page {pageNum}/{pageTotal}")
    private void checkArrows(int pageNum, int pageTotal) {
        logger.info("Check arrows on page " + pageNum + "/" + pageTotal);
        String page = pageNum + "/" + pageTotal;
        if (pageNum == 1) {
            // If it is the first page, verify that first and previous arrows are present as an inactive icon
            checkInactiveArrow("First", "Previous", page);
            checkActiveArrow("Next", "Last", page);
        } else if (pageNum == pageTotal) {
            // If it is the last page, verify that next and last arrows are present as an inactive icon
            checkInactiveArrow("Next", "Last", page);
            checkActiveArrow("First", "Previous", page);
        } else {
            checkActiveArrow("Next", "Last", page);
            checkActiveArrow("First", "Previous", page);
        }
    }

    private void checkInactiveArrow(String typeOfArrowFirst, String typeOfArrowSecond, String page) {
        Assert.assertTrue(searchResultsPage.isArrowDisplayed(typeOfArrowFirst),
                "The \"" + typeOfArrowFirst + "\" arrow is not displayed as an inactive icon on the page N" + page);
        Assert.assertTrue(searchResultsPage.isArrowDisplayed(typeOfArrowSecond),
                "The \"" + typeOfArrowSecond + "\" arrow is not displayed as an inactive icon on the page N" + page);
    }

    private void checkActiveArrow(String typeOfArrowFirst, String typeOfArrowSecond, String page) {
        Assert.assertTrue(searchResultsPage.isArrowLinkDisplayed(typeOfArrowFirst),
                "The \"" + typeOfArrowFirst + "\" arrow is not displayed as an active icon on the page N" + page);
        Assert.assertTrue(searchResultsPage.isArrowLinkDisplayed(typeOfArrowSecond),
                "The \"" + typeOfArrowSecond + "\" arrow is not displayed as an active icon on the page N" + page);
    }

}
