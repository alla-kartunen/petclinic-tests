package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import testsData.AllureListener;
import testsData.GetData;
import testsData.TestDataBuilder;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class AddOwnerTest extends BaseTest {

    private TestDataBuilder testData = new TestDataBuilder();

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify adding owner</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Owner CRUD")
    @Feature("Add owner")
    void addOwnerTest(ArrayList<Object> data) {

        Owner owner = testData.getOwner(data);

        getFindOwnerPage();

        int ownerId = findOwnerSteps.clickAddOwnerButton()
                                    .addOwner(owner)
                                    .waitForPageTitle()
                                    .verifyThatOwnerWasCorrectlyAddedToDB(owner);
        ownersManager.deleteOwner(ownerId);
        ownerInformationSteps.endOfSoftAssert();
    }

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 2)
    @Description("<b>Verify owner's data on owner's information page</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Owner CRUD")
    @Feature("Owner information")
    void verifyOwnerInformationTest(ArrayList<Object> data) {

        Owner owner = testData.getOwner(data);
        int ownerId = ownersManager.createOwnerAndGetId(owner);

        getOwnerPageById(ownerId);

        ownerInformationSteps.waitForPageTitle()
                                .verifyOwnerInformationOnPage(owner);
        ownersManager.deleteOwner(ownerId);
        ownerInformationSteps.endOfSoftAssert();

    }

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 3)
    @Description("<b>Verify that owner without pet have clear pet information on owner's information page</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Owner CRUD")
    @Feature("Owner information")
    void verifyOwnerHaveNoPetsTest(ArrayList<Object> data) {

        Owner owner = testData.getOwner(data);
        int ownerId = ownersManager.createOwnerAndGetId(owner);

        getOwnerPageById(ownerId);

        ownerInformationSteps.waitForPageTitle()
                                .verifyThatOwnerHaveNoPets();
        ownersManager.deleteOwner(ownerId);
        ownerInformationSteps.endOfSoftAssert();

    }
}