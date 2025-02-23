package steps;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.FindOwnersPage;
import objects.Owner;

import java.util.ArrayList;

import static steps.BaseStep.Log.START_SEARCH;

public class FindOwnerSteps extends BaseStep {

    private FindOwnersPage findOwnersPage = new FindOwnersPage();
    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Step("Search Owner")
    public OwnerInformationSteps searchOwner(String lastName) {
        log(START_SEARCH, lastName);
        findOwnersPage.fillSearchFieldAndPressSubmit(lastName);
        return new OwnerInformationSteps();
    }

    @Step("Open Add Owner page")
    public OwnerCrudSteps clickAddOwnerButton() {
        logger.info("Start: add a new owner");
        findOwnersPage.pressAddOwnerButton();
        return new OwnerCrudSteps();
    }

    @Step("Search Owner")
    public SearchResultsSteps searchOwner(ArrayList<Owner> ownersList) {
        String lastName = ownersList.get(1).getLastName();
        log(START_SEARCH, lastName);
        findOwnersPage.fillSearchFieldAndPressSubmit(lastName);
        return new SearchResultsSteps();
    }

}
