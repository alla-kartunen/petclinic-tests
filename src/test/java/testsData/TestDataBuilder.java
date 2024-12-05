package testsData;

import org.testng.SkipException;
import tests.managers.OwnersManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import tests.objectsAndMappers.PetTypes;

import java.util.ArrayList;

/**
 *  Convert data from DataProvider file to objects.
 *  Clear test data in database.
 */

public class TestDataBuilder {

    private OwnersManager ownersManager = new OwnersManager();
    private final int firstName = 0;
    private final int lastName = 1;
    private final int address = 2;
    private final int city = 3;
    private final int phone = 4;
    private final int petName = 5;
    private final int birthDate = 6;
    private final int petType = 7;

    public Owner getOwner(ArrayList<Object> data) {
        return Owner.builder()
                .firstName(data.get(firstName).toString())
                .lastName(data.get(lastName).toString())
                .address(data.get(address).toString())
                .city(data.get(city).toString())
                .telephone(data.get(phone).toString())
                .build();
    }

    public Owner getEditedOwner(Owner owner) {
        return Owner.builder()
                .firstName(cutString(owner.getFirstName()))
                .lastName(cutString(owner.getLastName()))
                .address(cutString(owner.getAddress()))
                .city(cutString(owner.getCity()))
                .telephone(cutString(owner.getTelephone()))
                .id(owner.getId())
                .build();
    }

    public Pet getPet(ArrayList<Object> data, Owner owner) {
        return Pet.builder()
                .name(data.get(petName).toString())
                .birthDate(data.get(birthDate).toString())
                .typeID(getTypeId(data.get(petType).toString()))
                .ownerID(owner.getId())
                .build();
    }

    public Pet getPet(ArrayList<Object> data) {
        return Pet.builder()
                .name(data.get(petName).toString())
                .birthDate(data.get(birthDate).toString())
                .typeID(getTypeId(data.get(petType).toString()))
                .build();
    }

    public Pet getEditedPet(Pet pet) {
        return Pet.builder()
                .name(cutString(pet.getName()))
                .birthDate(cutString(pet.getBirthDate()) + (1 + (int) (Math.random() * 28)))
                .typeID(getRandomTypeId(pet.getTypeID()))
                .ownerID(pet.getOwnerID())
                .build();
    }

    private String cutString(String str) {
        return str.substring(0, str.length() - 2);
    }

    private int getTypeId(String currentPetType) {
        int typeId = new PetTypes().getPetTypeByName(currentPetType);
        if (typeId == 0) {
            throw new SkipException("Incorrect data in Excel file! Pet type dictionary doesn't contains \""
                    + currentPetType + "\"");
        }
        return typeId;
    }

    private int getRandomTypeId(int id) {
        int random = id;
        while (random == id) {
            random = 1 + (int) (Math.random() * 6);
        }
        return random;
    }

    public ArrayList<Owner> prepareTestData(Object[][] allOwners) {
        ArrayList<Owner> ownersList = new ArrayList<>();

        String lastName = allOwners[0][1].toString();
        ownersManager.deleteOwner(lastName);

        int listLength = allOwners.length;
        for (int i = 0; i < listLength; i++) {
            ArrayList<Object> ownersData = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                ownersData.add(allOwners[i][j]);
            }

            Owner owner = getOwner(ownersData);
            owner.setId(ownersManager.createOwnerAndGetId(owner));
            ownersList.add(owner);
        }

        int lastNameCount = ownersManager.countOwnersWithLastName(lastName);
        if (lastNameCount != listLength) {
            throw new SkipException("Quantity of lastName " + lastName +
                    " doesn't match between database and data's from Excel file!\n" +
                    "In file: " + listLength + ", in database: " + lastNameCount);
        }
        return ownersList;
    }

    public String getPetData(ArrayList<Object> data) {
        return "[" + data.get(petName) + ", " + data.get(birthDate) + ", " + data.get(petType) + "]";
    }

}
