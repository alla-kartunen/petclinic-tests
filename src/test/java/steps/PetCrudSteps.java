package steps;

import pages.PetCrudPage;
import tests.managers.OwnersManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import testsData.CustomSoftAssert;
import testsData.TestDataBuilder;

public class PetCrudSteps {

	private PetCrudPage petCrudPage = new PetCrudPage();
	private TestDataBuilder testData = new TestDataBuilder();
	private OwnersManager ownersManager = new OwnersManager();
	private CustomSoftAssert softAssert = new CustomSoftAssert();

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

	public OwnerInformationSteps editPet(Pet pet) {

		petCrudPage.waitForPageTitle();

		petCrudPage.clearFields();

		petCrudPage.fillPetNameField(pet.getName());
		petCrudPage.fillBirthDateField(pet.getBirthDate());
		petCrudPage.setType(pet.getTypeID());
		petCrudPage.clickUpdatePetButton();

		return new OwnerInformationSteps();
	}

	public void endOfSoftAssert() {
		softAssert.assertAll();
	}

}
