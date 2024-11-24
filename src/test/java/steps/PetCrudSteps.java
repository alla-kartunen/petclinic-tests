package steps;

import io.qameta.allure.Step;
import pages.PetCrudPage;
import tests.managers.OwnersManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import testsData.SoftAssertWithScreenShot;
import testsData.TestDataBuilder;

public class PetCrudSteps {

	private PetCrudPage petCrudPage = new PetCrudPage();
	private TestDataBuilder testData = new TestDataBuilder();
	private OwnersManager ownersManager = new OwnersManager();
	private SoftAssertWithScreenShot softAssert = new SoftAssertWithScreenShot();

	@Step("Add Pet")
	public OwnerInformationSteps addPet(Owner owner, Pet pet) {
		petCrudPage.waitForPageTitle();
		String fullOwnerName = owner.getFirstName() + " " + owner.getLastName();
		softAssert.assertEquals(petCrudPage.getOwnerName(), fullOwnerName,
			"Incorrect Owner's name on Add Pet page!");

		petCrudPage.fillPetNameField(pet.getName());
		petCrudPage.fillBirthDateField(pet.getBirthDate());
		petCrudPage.setType(pet.getTypeID());
		petCrudPage.clickAddPetButton();
		return new OwnerInformationSteps();
	}

	@Step("Edit Pet")
	public OwnerInformationSteps editPet(Pet pet) {
		petCrudPage.waitForPageTitle();
		petCrudPage.clearFields();

		petCrudPage.fillPetNameField(pet.getName());
		petCrudPage.fillBirthDateField(pet.getBirthDate());
		petCrudPage.setType(pet.getTypeID());
		petCrudPage.clickUpdatePetButton();
		return new OwnerInformationSteps();
	}

	@Step("Summarizing soft assertion")
	public void endOfSoftAssert() {
		softAssert.assertAll();
	}

}
