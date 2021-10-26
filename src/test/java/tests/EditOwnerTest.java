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
public class EditOwnerTest extends BaseTest {

	private TestDataBuilder testData = new TestDataBuilder();

	@Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
	@Description("<b>Verify editing owner</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Owner CRUD")
	@Feature("Edit owner")
	void editOwnerTest(ArrayList<Object> data) {

		Owner correctOwner = testData.getOwner(data);
		Owner incorrectOwner = testData.getIncorrectOwner(correctOwner);

		int ownerId = ownersManager.createOwnerAndGetId(incorrectOwner);

		getOwnerPageById(ownerId);

		ownerCrudSteps.editOwner(correctOwner)
						.waitForPageTitle()
						.verifyOwnerInDB(correctOwner, ownerId);
		ownersManager.deleteOwner(ownerId);
		ownerInformationSteps.endOfSoftAssert();

	}

	@Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 2)
	@Description("<b>Verify information page after editing owner</b>")
	@Severity(SeverityLevel.NORMAL)
	@Epic("Owner CRUD")
	@Feature("Owner information after edit")
	void verifyOwnerInformationAfterEditTest(ArrayList<Object> data) {

		Owner correctOwner = testData.getOwner(data);
		Owner incorrectOwner = testData.getIncorrectOwner(correctOwner);

		int ownerId = ownersManager.createOwnerAndGetId(incorrectOwner);

		getOwnerPageById(ownerId);

		ownerCrudSteps.editOwner(correctOwner)
						.waitForPageTitle()
						.verifyOwnerInformationOnPage(correctOwner);
		ownersManager.deleteOwner(ownerId);
		ownerInformationSteps.endOfSoftAssert();

	}

}
