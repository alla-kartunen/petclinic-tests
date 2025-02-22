package steps;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import tests.managers.OwnersManager;
import tests.managers.PetsManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import testsData.GetData;
import testsData.SoftAssertWithScreenShot;
import testsData.TestDataBuilder;

import java.util.ArrayList;

import static steps.BaseStep.Log.*;

public class BaseStep {

    protected enum Log {
        START_DATA_PREPARATION,
        END_DATA_PREPARATION,
        START_CLEANING,
        END_CLEANING,
        START_SEARCH,
        PROCESS,
        FILL
    }

    private final TestDataBuilder testData = new TestDataBuilder();
    private final GetData getData = new GetData();
    private final OwnersManager ownersManager = new OwnersManager();
    private final PetsManager petsManager = new PetsManager();

    protected SoftAssertWithScreenShot uiSoftAssert = new SoftAssertWithScreenShot();
    protected SoftAssert dbSoftAssert = new SoftAssert();
    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected final String separator = "-------------------------";

    @Step("Prepare test data. Delete Owner by last name")
    public Owner clearOwnerInDb(ArrayList<Object> data) {
        log(START_DATA_PREPARATION, "Owner");
        log(PROCESS, "Owner data " + data);
        Owner owner = testData.getOwner(data);
        ownersManager.deleteOwner(owner.getLastName());
        log(END_DATA_PREPARATION, "Owner");
        return owner;
    }

    @Step("Prepare test data. Create Owner in DB")
    public Owner createOwnerInDb(ArrayList<Object> data) {
        log(START_DATA_PREPARATION, "Owner");
        log(PROCESS, "Owner data " + data);
        Owner owner = testData.getOwner(data);
        ownersManager.deleteOwner(owner.getLastName());
        owner.setId(ownersManager.createOwnerAndGetId(owner));
        log(END_DATA_PREPARATION, "Owner");
        return owner;
    }

    @Step("Prepare test data. Prepare Owner for editing")
    public Owner prepareOwnerForEdit(Owner owner) {
        log(START_DATA_PREPARATION, "Owner");
        testData.getEditedOwner(owner);
        log(PROCESS, "Edited Owner data " + owner);
        log(END_DATA_PREPARATION, "Owner");
        return owner;
    }

    @Step("Prepare test data. Create a list of Owners in DB")
    public ArrayList<Owner> createListOfOwnersInDb(String testCase) {
        log(START_DATA_PREPARATION, "Owner");
        ArrayList<Owner> ownersList = testData.prepareTestData(
                                                getData.ownersWithTheSameLastNameData(testCase));
        if (testCase.contains("NoPagination")) {
            verifyQuantityOfOwners(ownersList, false);
        } else if (testCase.contains("Pagination") || testCase.contains("Navigation")) {
            verifyQuantityOfOwners(ownersList, true);
        }
        log(END_DATA_PREPARATION, "Owner");
        return ownersList;
    }

    @Step("Prepare test data. Prepare Pet")
    public Pet preparePet(ArrayList<Object> data, Owner owner) {
        log(START_DATA_PREPARATION, "Pet");
        log(PROCESS, "Pet data " + testData.getPetData(data) + " for owner id = " + owner.getId());
        Pet pet = testData.getPet(data);
        pet.setOwnerID(owner.getId());
        log(END_DATA_PREPARATION, "Pet");
        return pet;
    }

    @Step("Prepare test data. Prepare Pet for edit")
    public Pet preparePetForEdit(Pet pet, Owner owner) {
        log(START_DATA_PREPARATION, "Pet");
        testData.getEditedPet(pet);
        log(PROCESS, "Edited Pet data " + pet + " for owner id = " + owner.getId());
        log(END_DATA_PREPARATION, "Pet");
        return pet;
    }

    @Step("Prepare test data. Create Pet in DB")
    public Pet createPetInDb(ArrayList<Object> data, Owner owner) {
        log(START_DATA_PREPARATION, "Pet");
        log(PROCESS, "Pet data " + testData.getPetData(data) + " for owner id = " + owner.getId());
        Pet pet = testData.getPet(data, owner);
        pet.setId(petsManager.createPetAndGetId(pet));
        log(END_DATA_PREPARATION, "Pet");
        return pet;
    }

    @Step("Clear test data. Delete owner from DB")
    public void clearTestData(int ownerId) {
        log(START_CLEANING, null);
        ownersManager.deleteOwner(ownerId);
        log(END_CLEANING, null);
    }

    @Step("Clear test data. Delete owner & pet from DB")
    public void clearTestData(int ownerId, int petId) {
        log(START_CLEANING, null);
        petsManager.deletePet(petId);
        ownersManager.deleteOwner(ownerId);
        log(END_CLEANING, null);
    }

    @Step("Delete Owners list from DB")
    public void clearTestData(ArrayList<Owner> ownersList) {
        log(START_CLEANING, null);
        OwnersManager ownersManager = new OwnersManager();
        ownersManager.deleteOwner(ownersList);
        log(END_CLEANING, null);
    }

    public void verifyQuantityOfOwners(ArrayList<Owner> ownersList, boolean forPagination) {
        logger.info("Verify quantity of Owners for pagination test");
        if (forPagination) {
            if (ownersList.size() < 6) {
                String message = "Incorrect data in Excel file! " +
                        "There must be more than 6 owners for search pagination test.";
                logger.error(message);
                throw new SkipException(message);
            }
        } else {
            if (ownersList.size() > 5) {
                String message = "Incorrect data in Excel file! " +
                        "There must be less than 6 owners to check displaying of only one search result page.";
                logger.error(message);
                throw new SkipException(message);
            }
        }
        logger.info("Verification of Owner's quantity passed");
    }

    protected void log(Log log, String object) {
        switch (log) {
            case START_DATA_PREPARATION:
                logger.info(separator + " Prepare {} data " + separator, object);
                break;
            case END_DATA_PREPARATION:
                logger.info(separator + "{} data is prepared " + separator, object);
                break;
            case START_CLEANING:
                logger.info(separator + " Start cleaning of test data " + separator);
                break;
            case END_CLEANING:
                logger.info(separator + " End cleaning of test data " + separator);
                break;
            case START_SEARCH:
                logger.info("Start: \"Search Owner\" step. Search by last name = {}", object);
                break;
            case PROCESS:
                logger.info(object);
                break;
            case FILL:
                logger.info("Fill fields on \"{}\" page", object);
        }
    }
}
