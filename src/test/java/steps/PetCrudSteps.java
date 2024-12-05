package steps;

import io.qameta.allure.Step;
import pages.PetCrudPage;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;

import static steps.BaseStep.Log.*;

public class PetCrudSteps extends BaseStep {

	private PetCrudPage petCrudPage = new PetCrudPage();

	@Step("Add Pet")
	public OwnerInformationSteps addPet(Owner owner, Pet pet) {
		petCrudPage.waitForPageTitle();
		log(FILL,"Add Pet");
		String fullOwnerName = owner.getFirstName() + " " + owner.getLastName();
		uiSoftAssert.assertEquals(petCrudPage.getOwnerName(), fullOwnerName,
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

		log(FILL, "Edit Pet");
		petCrudPage.fillPetNameField(pet.getName());
		petCrudPage.fillBirthDateField(pet.getBirthDate());
		petCrudPage.setType(pet.getTypeID());
		petCrudPage.clickUpdatePetButton();
		return new OwnerInformationSteps();
	}

	@Step("Summarizing soft assertion")
	public void endOfSoftAssert() {
		logger.info(separator + " TEST ENDS " + separator);
		uiSoftAssert.assertAll();
	}

}
