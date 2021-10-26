package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import testsData.AllureListener;
import testsData.GetData;
import testsData.TestDataBuilder;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class EditPetTest extends BaseTest {

	private TestDataBuilder testData = new TestDataBuilder();

	@Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 1)
	@Description("<b>Verify editing pet</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Pet CRUD")
	@Feature("Edit pet")
	void editPetTest(ArrayList<Object> data){

		Owner owner = testData.getOwner(data);
		Pet correctPet = testData.getPet(data);
		Pet incorrectPet = testData.getIncorrectPet(correctPet);

		int ownerId = ownersManager.createOwnerAndGetId(owner);
		incorrectPet.setOwnerID(ownerId);
		int petId = petsManager.createPetAndGetId(incorrectPet);

		getOwnerPageById(ownerId);

		ownerInformationSteps.clickEditPetButton()
								.editPet(correctPet)
								.waitForPageTitle()
								.verifyPetInDB(correctPet, ownerId, petId);

		petsManager.deletePet(petId);
		ownersManager.deleteOwner(ownerId);
		ownerInformationSteps.endOfSoftAssert();

	}

	@Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 2)
	@Description("<b>Verify editing pet</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Pet CRUD")
	@Feature("Pet information")
	void verifyPetInformationAfterEditTest(ArrayList<Object> data){

		Owner owner = testData.getOwner(data);
		Pet correctPet = testData.getPet(data);
		Pet incorrectPet = testData.getIncorrectPet(correctPet);

		int ownerId = ownersManager.createOwnerAndGetId(owner);
		incorrectPet.setOwnerID(ownerId);

		int petId = petsManager.createPetAndGetId(incorrectPet);

		getOwnerPageById(ownerId);

		ownerInformationSteps.clickEditPetButton()
								.editPet(correctPet)
								.waitForPageTitle()
								.verifyPetDataOnOwnerInformationPage(correctPet);

		petsManager.deletePet(petId);
		ownersManager.deleteOwner(ownerId);
		ownerInformationSteps.endOfSoftAssert();

	}
}
