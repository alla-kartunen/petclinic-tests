package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import testsData.AllureListener;
import testsData.GetData;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class EditPetTest extends BaseTest {

	@Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 1)
	@Description("<b>Verify editing pet</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Pet CRUD")
	@Feature("Edit pet")
	void editPetTest(ArrayList<Object> data){
		Owner owner = createOwnerInDb(data);
		Pet notEditedPet = createPetInDb(data, owner);
		Pet editedPet = preparePetForEdit(notEditedPet, owner);

		openOwnerPageById(owner.getId());
		ownerInformationSteps.clickEditPetButton()
								.editPet(editedPet)
								.waitForPageTitle()
								.verifyPetInDB(editedPet, owner.getId(), notEditedPet.getId());

		clearTestData(owner.getId(), notEditedPet.getId());
		ownerInformationSteps.endOfSoftAssert();
	}

	@Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 2)
	@Description("<b>Verify Pet Information after editing pet</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Pet CRUD")
	@Feature("Pet information")
	void verifyPetInformationAfterEditTest(ArrayList<Object> data){
		Owner owner = createOwnerInDb(data);
		Pet notEditedPet = createPetInDb(data, owner);
		Pet editedPet = preparePetForEdit(notEditedPet, owner);

		openOwnerPageById(owner.getId());
		ownerInformationSteps.clickEditPetButton()
								.editPet(editedPet)
								.waitForPageTitle()
								.verifyPetDataOnOwnerInformationPage(editedPet);

		clearTestData(owner.getId(), notEditedPet.getId());
		ownerInformationSteps.endOfSoftAssert();
	}
}
