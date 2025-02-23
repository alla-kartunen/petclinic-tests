package steps;

import io.qameta.allure.Step;
import config.TestContext;
import pages.OwnerInformationPage;
import db.OwnersManager;
import db.PetsManager;
import objects.Owner;
import objects.Pet;
import objects.PetTypes;

public class OwnerInformationSteps extends BaseStep {

	private OwnerInformationPage ownerInformationPage = new OwnerInformationPage();

	private OwnersManager ownersManager = new OwnersManager();
	private PetsManager petsManager = new PetsManager();

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
	public OwnerInformationSteps verifyOwnerInformationPage(Owner owner) {
		String ownerFullName = owner.getFirstName() + " " + owner.getLastName();
		logger.info("Start: verification of Owner information [" + ownerFullName + "]");
		softAssert.assertEquals(ownerInformationPage.getOwnerName(), ownerFullName,
				"Incorrect owner name on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerAddress(), owner.getAddress(),
				"Incorrect owner address on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerCity(), owner.getCity(),
				"Incorrect owner city on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getOwnerTelephone(), owner.getTelephone(),
				"Incorrect owner telephone on Owner Information page:");
		logger.info("End: verification of Owner information [" + ownerFullName +
				"]. In case of failures, soft assert results will be shown later.");
		return this;
	}

	@Step("Verify that Owner has no Pets")
	public OwnerInformationSteps verifyThatOwnerHasNoPets() {
		logger.info("Start: verification that Owner has no Pets");
		int pets = ownerInformationPage.countPets();
		softAssert.assertEquals(pets, 0,
				"There's a pet's information table for owner without pet on the Owner Information Page!");
		logger.info("End: verification that Owner has no Pets. In case of failures, soft assert results will be shown later.");
		return this;
	}

	@Step("Verify Pet data on Owner information page")
	public OwnerInformationSteps verifyPetDataOnOwnerInformationPage(Pet pet) {
		PetTypes petTypes = new PetTypes();
		logger.info("Start: verification of Pet data on Owner information page");
		softAssert.assertEquals(ownerInformationPage.getFirstPetName(), pet.getName(),
				"Incorrect Pet's name on Owner Information page:");
		softAssert.assertEquals(ownerInformationPage.getFirstPetBirthDate(), pet.getBirthDate(),
				"Incorrect Pet's birthday on Owner Information page!");
		softAssert.assertEquals(ownerInformationPage.getFirstPetType(), petTypes.getPetTypeById(pet.getTypeID()),
				"Incorrect Pet's type on Owner Information page!");
		logger.info("End: verification of Pet data on Owner information page. In case of failures, soft assert results will be shown later.");
		return this;
	}

	@Step("Verify that Owner was correctly added to DB")
	public OwnerInformationSteps verifyThatOwnerWasCorrectlyAddedToDB(Owner owner) {
		Owner actualOwnerInDB = ownersManager.getNewOwnerByLastName(owner.getLastName()).get(0);
		softAssertOwnerInDb(owner, actualOwnerInDB);
		owner.setId(actualOwnerInDB.getId());
		TestContext.setOwnerToClear(owner.getId());
		return this;
	}

	@Step("Verify Owner data in DB")
	public OwnerInformationSteps verifyOwnerInDB(Owner owner, int ownerId) {
		Owner actualOwnerInDB = ownersManager.getOwnerById(ownerId).get(0);
		softAssertOwnerInDb(owner, actualOwnerInDB);
		return this;
	}

	@Step("Verify that Pet was correctly added to DB")
	public OwnerInformationSteps verifyThatPetWasCorrectlyAddedToDB(Pet pet) {
		Pet actualPetInDB = petsManager.getNewPetByName(pet.getName()).get(0);
		softAssertPetInDb(actualPetInDB, pet, pet.getOwnerID());
		pet.setId(actualPetInDB.getId());
		TestContext.setPetToClear(actualPetInDB.getId());
		return this;
	}

	@Step("Verify Pet data in DB")
	public OwnerInformationSteps verifyPetInDB(Pet pet, int ownerId, int petId) {
		Pet actualPetInDB = petsManager.getPetById(petId).get(0);
		softAssertPetInDb(actualPetInDB, pet, ownerId);
		return this;
	}

	@Step("Summarizing soft assertion")
	public void endOfSoftAssert() {
		logger.info(separator + " TEST ENDS " + separator);
		softAssert.assertAll();
	}

	private void softAssertOwnerInDb(Owner owner, Owner actualOwnerInDB) {
		logger.info("Start: verification of Owner in DB.");
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
		logger.info("End: verification of Owner in DB. In case of failures, soft assert results will be shown later.");
	}
	
	private void softAssertPetInDb(Pet actualPet, Pet expectedPet, int ownerId) {
		logger.info("Start: verification of a Pet");
		softAssert.assertEquals(actualPet.getBirthDate(), expectedPet.getBirthDate(),
				"Pet's birth date in database is incorrect!");
		softAssert.assertEquals(actualPet.getName(), expectedPet.getName(),
				"Pet's name in database is incorrect!");
		softAssert.assertEquals(actualPet.getOwnerID(), ownerId,
				"Pet's owner id in database is incorrect!");
		logger.info("End: verification of a Pet. In case of failures, soft assert results will be shown later.");
	}

}
