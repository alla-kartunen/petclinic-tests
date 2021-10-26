package steps;

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

	public PetCrudSteps clickAddPetButton() {

		ownerInformationPage.waitForPageTitle();
		ownerInformationPage.clickAddPetButton();

		return new PetCrudSteps();
	}

	public PetCrudSteps clickEditPetButton() {
		ownerInformationPage.clickEditPetButton();
		return new PetCrudSteps();
	}

	public OwnerInformationSteps waitForPageTitle() {
		ownerInformationPage.waitForPageTitle();
		return this;
	}

	public OwnerInformationSteps verifyOwnerInformationOnPage(Owner owner) {

		String expectedFullOwnerName = (owner.getFirstName() + " " + owner.getLastName());

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

	public OwnerInformationSteps verifyThatOwnerHaveNoPets() {
		int pets = ownerInformationPage.countPets();
		softAssert.assertEquals(pets, 0,
				"There's a pet's information table for owner without pet on the Owner Information Page!");
		return this;
	}

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

	public int verifyThatOwnerWasCorrectlyAddedToDB(Owner owner) {

		Owner actualOwnerInDB = ownersManager.getNewOwnerByLastName(owner.getLastName()).get(0);

		softAssert.assertEquals(owner.getLastName(), actualOwnerInDB.getLastName(),
				"Owner lastname in database is incorrect!");
		softAssert.assertEquals(owner.getFirstName(), actualOwnerInDB.getFirstName(),
				"Owner firstname in database is incorrect!");
		softAssert.assertEquals(owner.getAddress(), actualOwnerInDB.getAddress(),
				"Owner address in database is incorrect!");
		softAssert.assertEquals(owner.getCity(), actualOwnerInDB.getCity(),
				"Owner city in database is incorrect!");
		softAssert.assertEquals(owner.getTelephone(), actualOwnerInDB.getTelephone(),
				"Owner telephone in database is incorrect!");

		int ownerID = actualOwnerInDB.getId();

		return ownerID;
	}

	public int verifyOwnerInDB(Owner owner, int ownerId) {

		Owner actualOwnerInDB = ownersManager.getOwnerById(ownerId).get(0);

		softAssert.assertEquals(owner.getLastName(), actualOwnerInDB.getLastName(),
				"Owner lastname in database is incorrect!");
		softAssert.assertEquals(owner.getFirstName(), actualOwnerInDB.getFirstName(),
				"Owner firstname in database is incorrect!");
		softAssert.assertEquals(owner.getAddress(), actualOwnerInDB.getAddress(),
				"Owner address in database is incorrect!");
		softAssert.assertEquals(owner.getCity(), actualOwnerInDB.getCity(),
				"Owner city in database is incorrect!");
		softAssert.assertEquals(owner.getTelephone(), actualOwnerInDB.getTelephone(),
				"Owner telephone in database is incorrect!");

		int ownerID = actualOwnerInDB.getId();

		return ownerID;
	}

	public int verifyThatPetWasCorrectlyAddedToDB(Pet pet, int ownerId) {

		Pet actualPetInDB = petsManager.getNewPetByName(pet.getName()).get(0);

		softAssertForPet(actualPetInDB, pet, ownerId);

		int petID = actualPetInDB.getId();

		return petID;
	}

	public void verifyPetInDB(Pet pet, int ownerId, int petId) {
		Pet actualPetInDB = petsManager.getPetById(petId).get(0);
		softAssertForPet(actualPetInDB, pet, ownerId);
	}

	void softAssertForPet(Pet actualPet, Pet expectedPet, int ownerId) {
		softAssert.assertEquals(actualPet.getBirthDate(), expectedPet.getBirthDate(),
				"Pet's birth date in database is incorrect!");
		softAssert.assertEquals(actualPet.getName(), expectedPet.getName(),
				"Pet's name in database is incorrect!");
		softAssert.assertEquals(actualPet.getOwnerID(), ownerId,
				"Pet's owner id in database is incorrect!");
	}



	public void endOfSoftAssert() {
		softAssert.assertAll();
	}

}
