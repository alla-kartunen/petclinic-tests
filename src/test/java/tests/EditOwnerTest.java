package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import objects.Owner;
import dataproviders.GetData;

import java.util.ArrayList;

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
						.waitForPageTitle()
						.verifyOwnerInDB(editedOwner, notEditedOwner.getId())
						.endOfSoftAssert();
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
						.waitForPageTitle()
						.verifyOwnerInformationPage(editedOwner)
						.endOfSoftAssert();
	}

}
