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
public class AddPetTest extends BaseTest {

    @Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify adding pet</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Pet CRUD")
    @Feature("Add pet")
    void addPetTest(ArrayList<Object> data) {
        Owner owner = createOwnerInDb(data);
        Pet pet = preparePetForCreate(data, owner);

        openOwnerPageById(owner.getId());
        ownerInformationSteps.clickAddPetButton();
        petCrudSteps.addPet(owner, pet)
                                .waitForPageTitle();
        ownerInformationSteps.verifyThatPetWasCorrectlyAddedToDB(pet);

        clearTestData(owner.getId(), pet.getId());
        petCrudSteps.endOfSoftAssert();
        ownerInformationSteps.endOfSoftAssert();
    }

    @Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 2)
    @Description("<b>Verify pet information</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Pet CRUD")
    @Feature("Pet information")
    void verifyPetInformationTest(ArrayList<Object> data) {
        Owner owner = createOwnerInDb(data);
        Pet pet = createPetInDb(data, owner);

        openOwnerPageById(owner.getId());
        ownerInformationSteps.waitForPageTitle()
                                .verifyPetDataOnOwnerInformationPage(pet);

        clearTestData(owner.getId(), pet.getId());
        ownerInformationSteps.endOfSoftAssert();
    }
}
