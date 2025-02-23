package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import objects.Owner;
import objects.Pet;
import dataproviders.GetData;

import java.util.ArrayList;

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
								.verifyPetInDB(editedPet, owner.getId(), notEditedPet.getId())
								.endOfSoftAssert();
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
								.verifyPetDataOnOwnerInformationPage(editedPet)
								.endOfSoftAssert();
	}
}
