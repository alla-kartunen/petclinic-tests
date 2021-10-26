package steps;

import pages.FindOwnersPage;
import tests.objectsAndMappers.Owner;

import java.util.ArrayList;

public class FindOwnerSteps {

    private FindOwnersPage findOwnersPage = new FindOwnersPage();
    private SearchResultsSteps searchResultsSteps = new SearchResultsSteps();

    public OwnerInformationSteps searchUniqueOwner(String lastName) {
        findOwnersPage.fillSearchFieldAndPressSubmit(lastName);
        return new OwnerInformationSteps();
    }

    public OwnerCrudSteps clickAddOwnerButton() {
        findOwnersPage.pressAddOwnerButton();
        return new OwnerCrudSteps();
    }

    public SearchResultsSteps searchMoreThanOneOwner(ArrayList<Owner> ownersList) {
        Owner owner = ownersList.get(1);
        findOwnersPage.fillSearchFieldAndPressSubmit(owner.getLastName());
        return new SearchResultsSteps();
    }

}
