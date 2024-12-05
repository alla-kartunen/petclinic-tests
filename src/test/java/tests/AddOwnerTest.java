package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import testsData.AllureListener;
import testsData.GetData;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class AddOwnerTest extends BaseTest {

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
    @Description("<b>Verify adding owner</b>")
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Owner CRUD")
    @Feature("Add owner")
    void addOwnerTest(ArrayList<Object> data) {
        Owner owner = prepareOwnerForCreate(data);

        openFindOwnerPage();
        findOwnerSteps.clickAddOwnerButton()
                                    .addOwner(owner)
                                    .waitForPageTitle();
        ownerInformationSteps.verifyThatOwnerWasCorrectlyAddedToDB(owner);
        clearTestData(owner.getId());
        ownerInformationSteps.endOfSoftAssert();
    }

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 2)
    @Description("<b>Verify owner's data on owner's information page</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Owner CRUD")
    @Feature("Owner information")
    void verifyOwnerInformationTest(ArrayList<Object> data) {
        Owner owner = createOwnerInDb(data);

        openOwnerPageById(owner.getId());
        ownerInformationSteps.waitForPageTitle()
                                .verifyOwnerInformationPage(owner);
        clearTestData(owner.getId());
        ownerInformationSteps.endOfSoftAssert();
    }

    @Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 3)
    @Description("<b>Verify that owner without pet has no pet information on owner's information page</b>")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Owner CRUD")
    @Feature("Owner information")
    void verifyOwnerHaveNoPetsTest(ArrayList<Object> data) {
        Owner owner = createOwnerInDb(data);

        openOwnerPageById(owner.getId());
        ownerInformationSteps.waitForPageTitle()
                                .verifyThatOwnerHasNoPets();
        clearTestData(owner.getId());
        ownerInformationSteps.endOfSoftAssert();
    }
}