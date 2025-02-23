package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import objects.Owner;
import objects.Pet;
import dataproviders.GetData;

import java.util.ArrayList;

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
        ownerInformationSteps.clickAddPetButton()
                                .addPet(owner, pet)
                                .waitForPageTitle()
                                .verifyThatPetWasCorrectlyAddedToDB(pet)
                                .endOfSoftAssert();
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
                                .verifyPetDataOnOwnerInformationPage(pet)
                                .endOfSoftAssert();
    }
}
