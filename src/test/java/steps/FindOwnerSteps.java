package steps;

import io.qameta.allure.Step;
import pages.FindOwnersPage;
import tests.objectsAndMappers.Owner;

import java.util.ArrayList;

public class FindOwnerSteps {

    private FindOwnersPage findOwnersPage = new FindOwnersPage();
    private SearchResultsSteps searchResultsSteps = new SearchResultsSteps();

    @Step("Search Owner")
    public OwnerInformationSteps searchUniqueOwner(String lastName) {
        findOwnersPage.fillSearchFieldAndPressSubmit(lastName);
        return new OwnerInformationSteps();
    }

    @Step("Open Add Owner page")
    public OwnerCrudSteps clickAddOwnerButton() {
        findOwnersPage.pressAddOwnerButton();
        return new OwnerCrudSteps();
    }

    @Step("Search Owner")
    public SearchResultsSteps searchMoreThanOneOwner(ArrayList<Owner> ownersList) {
        Owner owner = ownersList.get(1);
        findOwnersPage.fillSearchFieldAndPressSubmit(owner.getLastName());
        return new SearchResultsSteps();
    }

}
