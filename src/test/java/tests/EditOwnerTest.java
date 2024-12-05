package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.objectsAndMappers.Owner;
import testsData.AllureListener;
import testsData.GetData;

import java.util.ArrayList;

@Listeners({AllureListener.class})
public class EditOwnerTest extends BaseTest {

	@Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 1)
	@Description("<b>Verify editing owner</b>")
	@Severity(SeverityLevel.BLOCKER)
	@Epic("Owner CRUD")
	@Feature("Edit owner")
	void editOwnerTest(ArrayList<Object> data) {
		Owner notEditedOwner = createOwnerInDb(data);
		Owner editedOwner = prepareOwnerForEdit(notEditedOwner);

		openOwnerPageById(notEditedOwner.getId());
		ownerCrudSteps.editOwner(editedOwner)
						.waitForPageTitle();
		ownerInformationSteps.verifyOwnerInDB(editedOwner, notEditedOwner.getId());
		clearTestData(notEditedOwner.getId());
		ownerInformationSteps.endOfSoftAssert();
	}

	@Test(dataProvider = "ownerTestData", dataProviderClass = GetData.class, priority = 2)
	@Description("<b>Verify information page after editing owner</b>")
	@Severity(SeverityLevel.NORMAL)
	@Epic("Owner CRUD")
	@Feature("Owner information after edit")
	void verifyOwnerInformationAfterEditTest(ArrayList<Object> data) {
		Owner notEditedOwner = createOwnerInDb(data);
		Owner editedOwner = prepareOwnerForEdit(notEditedOwner);

		openOwnerPageById(notEditedOwner.getId());
		ownerCrudSteps.editOwner(editedOwner)
						.waitForPageTitle();
		ownerInformationSteps.verifyOwnerInformationPage(editedOwner);
		clearTestData(notEditedOwner.getId());
		ownerInformationSteps.endOfSoftAssert();
	}

}
