package steps;

import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;
import pages.OwnerInformationPage;
import tests.managers.OwnersManager;
import tests.managers.PetsManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import tests.objectsAndMappers.PetTypes;

public class OwnerInformationSteps {

	private OwnerInformationPage ownerInformationPage = new OwnerInformationPage();

	private OwnersManager ownersManager = new OwnersManager();
	private PetsManager petsManager = new PetsManager();

	private SoftAssert softAssert = new SoftAssert();

	@Step("Open Add Pet page")
	public PetCrudSteps clickAddPetButton() {
		ownerInformationPage.waitForPageTitle();
		ownerInformationPage.clickAddPetButton();
		return new PetCrudSteps();
	}

	@Step("Open Edit Pet page")
	public PetCrudSteps clickEditPetButton() {
		ownerInformationPage.clickEditPetButton();
		return new PetCrudSteps();
	}

	@Step("Wait for Owner information page title")
	public OwnerInformationSteps waitForPageTitle() {
		ownerInformationPage.waitForPageTitle();
		return this;
	}

	@Step("Verify Owner information")
	public OwnerInformationSteps verifyOwnerInformationOnPage(Owner owner) {
		String expectedFullOwnerName = owner.getFirstName() + " " + owner.getLastName();

		softAssert.assertEquals(ownerInformationPage.getOwnerName(), expectedFullOwnerName,
			"Incorrect owner name on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerAddress(), owner.getAddress(),
			"Incorrect owner address on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerCity(), owner.getCity(),
			"Incorrect owner city on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerTelephone(), owner.getTelephone(),
			"Incorrect owner telephone on Owner Information page:");

		return this;
	}

	@Step("Verify that Owner has no Pets")
	public OwnerInformationSteps verifyThatOwnerHasNoPets() {
		int pets = ownerInformationPage.countPets();
		softAssert.assertEquals(pets, 0,
				"There's a pet's information table for owner without pet on the Owner Information Page!");
		return this;
	}

	@Step("Verify Pet data on Owner information page")
	public OwnerInformationSteps verifyPetDataOnOwnerInformationPage(Pet pet) {
		PetTypes petTypes = new PetTypes();
		String expectedType = petTypes.getPetTypeById(pet.getTypeID());

		softAssert.assertEquals(ownerInformationPage.getFirstPetName(), pet.getName(),
			"Incorrect Pet's name on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getFirstPetBirthDate(), pet.getBirthDate(),
			"Incorrect Pet's birthday on Owner Information page!");
		softAssert.assertEquals(ownerInformationPage.getFirstPetType(), expectedType,
			"Incorrect Pet's type on Owner Information page!");
		return this;
	}

	@Step("Verify that Owner was correctly added to DB")
	public int verifyThatOwnerWasCorrectlyAddedToDB(Owner owner) {
		Owner actualOwnerInDB = ownersManager.getNewOwnerByLastName(owner.getLastName()).get(0);

		softAssert.assertEquals(owner.getLastName(), actualOwnerInDB.getLastName(),
				"Owner last name in database is incorrect!");
		softAssert.assertEquals(owner.getFirstName(), actualOwnerInDB.getFirstName(),
				"Owner first name in database is incorrect!");
		softAssert.assertEquals(owner.getAddress(), actualOwnerInDB.getAddress(),
				"Owner address in database is incorrect!");
		softAssert.assertEquals(owner.getCity(), actualOwnerInDB.getCity(),
				"Owner city in database is incorrect!");
		softAssert.assertEquals(owner.getTelephone(), actualOwnerInDB.getTelephone(),
				"Owner telephone in database is incorrect!");

		int ownerID = actualOwnerInDB.getId();
		return ownerID;
	}

	@Step("Verify Owner data in DB")
	public int verifyOwnerInDB(Owner owner, int ownerId) {
		Owner actualOwnerInDB = ownersManager.getOwnerById(ownerId).get(0);

		softAssert.assertEquals(owner.getLastName(), actualOwnerInDB.getLastName(),
				"Owner last name in database is incorrect!");
		softAssert.assertEquals(owner.getFirstName(), actualOwnerInDB.getFirstName(),
				"Owner first name in database is incorrect!");
		softAssert.assertEquals(owner.getAddress(), actualOwnerInDB.getAddress(),
				"Owner address in database is incorrect!");
		softAssert.assertEquals(owner.getCity(), actualOwnerInDB.getCity(),
				"Owner city in database is incorrect!");
		softAssert.assertEquals(owner.getTelephone(), actualOwnerInDB.getTelephone(),
				"Owner telephone in database is incorrect!");

		int ownerID = actualOwnerInDB.getId();
		return ownerID;
	}

	@Step("Verify that Pet was correctly added to DB")
	public int verifyThatPetWasCorrectlyAddedToDB(Pet pet, int ownerId) {
		Pet actualPetInDB = petsManager.getNewPetByName(pet.getName()).get(0);

		softAssertForPet(actualPetInDB, pet, ownerId);
		return actualPetInDB.getId();
	}

	@Step("Verify Pet data in DB")
	public void verifyPetInDB(Pet pet, int ownerId, int petId) {
		Pet actualPetInDB = petsManager.getPetById(petId).get(0);
		softAssertForPet(actualPetInDB, pet, ownerId);
	}

	@Step("Summarizing soft assertion")
	public void endOfSoftAssert() {
		softAssert.assertAll();
	}

	private void softAssertForPet(Pet actualPet, Pet expectedPet, int ownerId) {
		softAssert.assertEquals(actualPet.getBirthDate(), expectedPet.getBirthDate(),
				"Pet's birth date in database is incorrect!");
		softAssert.assertEquals(actualPet.getName(), expectedPet.getName(),
				"Pet's name in database is incorrect!");
		softAssert.assertEquals(actualPet.getOwnerID(), ownerId,
				"Pet's owner id in database is incorrect!");
	}

}
