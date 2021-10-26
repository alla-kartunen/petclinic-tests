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
public class AddPetTest extends BaseTest {

    private TestDataBuilder testData = new TestDataBuilder();

    @Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify adding pet</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Pet CRUD")
    @Feature("Add pet")
    void addPetTest(ArrayList<Object> data) {

        Owner owner = testData.getOwner(data);

        Pet pet = testData.getPet(data);
        int ownerId = ownersManager.createOwnerAndGetId(owner);
        pet.setOwnerID(ownerId);

        getOwnerPageById(ownerId);

        ownerInformationSteps.clickAddPetButton();

        int petId = petCrudSteps.addPet(owner, pet)
                                .waitForPageTitle()
                                .verifyThatPetWasCorrectlyAddedToDB(pet, ownerId);

        petsManager.deletePet(petId);
        ownersManager.deleteOwner(ownerId);
        petCrudSteps.endOfSoftAssert();
        ownerInformationSteps.endOfSoftAssert();

    }

    @Test(dataProvider = "ownerWithPetsTestData", dataProviderClass = GetData.class, priority = 2)
    @Description("<b>Verify pet information</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Pet CRUD")
    @Feature("Pet information")
    void verifyPetInformationTest(ArrayList<Object> data) {

        Owner owner = testData.getOwner(data);

        Pet pet = testData.getPet(data);
        int ownerId = ownersManager.createOwnerAndGetId(owner);
        pet.setOwnerID(ownerId);
        int petId = petsManager.createPetAndGetId(pet);

        getOwnerPageById(ownerId);

        ownerInformationSteps.waitForPageTitle()
                                .verifyPetDataOnOwnerInformationPage(pet);

        petsManager.deletePet(petId);
        ownersManager.deleteOwner(ownerId);
        ownerInformationSteps.endOfSoftAssert();

    }
}
