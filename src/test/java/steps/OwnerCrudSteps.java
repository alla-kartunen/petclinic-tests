package steps;

import io.qameta.allure.Step;
import pages.OwnerCrudPage;
import pages.OwnerInformationPage;
import tests.objectsAndMappers.Owner;

public class OwnerCrudSteps {

	private OwnerCrudPage ownerCrudPage = new OwnerCrudPage();
	private OwnerInformationPage ownerInformationPage = new OwnerInformationPage();

	@Step("Add Owner")
	public OwnerInformationSteps addOwner(Owner owner) {
		ownerCrudPage.fillFirstNameField(owner.getFirstName());
		ownerCrudPage.fillLastNameField(owner.getLastName());
		ownerCrudPage.fillAddressField(owner.getAddress());
		ownerCrudPage.fillCityField(owner.getCity());
		ownerCrudPage.fillTelephoneField(owner.getTelephone());
		ownerCrudPage.clickAddOwnerButton();
		return new OwnerInformationSteps();
	}

	@Step("Edit Owner")
	public OwnerInformationSteps editOwner(Owner owner) {
		ownerInformationPage.clickEditOwnerButton();
		ownerCrudPage.waitForFirstNameField();
		ownerCrudPage.clearAllFields();

		ownerCrudPage.fillFirstNameField(owner.getFirstName());
		ownerCrudPage.fillLastNameField(owner.getLastName());
		ownerCrudPage.fillAddressField(owner.getAddress());
		ownerCrudPage.fillCityField(owner.getCity());
		ownerCrudPage.fillTelephoneField(owner.getTelephone());
		ownerCrudPage.clickUpdateOwnerButton();
		return new OwnerInformationSteps();
	}

}
