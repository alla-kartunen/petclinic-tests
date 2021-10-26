package testsData;

import org.testng.SkipException;
import tests.managers.OwnersManager;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.Pet;
import tests.objectsAndMappers.PetTypes;

import java.util.ArrayList;

public class TestDataBuilder {

    public Owner getOwner(ArrayList<Object> data) {

        Owner owner = Owner.builder()
                .firstName(data.get(0).toString())
                .lastName(data.get(1).toString())
                .address(data.get(2).toString())
                .city(data.get(3).toString())
                .telephone(data.get(4).toString())
                .build();

        return owner;
    }

    public Owner getIncorrectOwner(Owner owner) {
        Owner incorrectOwner = Owner.builder()
                .firstName(cutString(owner.getFirstName()))
                .lastName(cutString(owner.getLastName()))
                .address(cutString(owner.getAddress()))
                .city(cutString(owner.getCity()))
                .telephone(cutString(owner.getTelephone()))
                .build();

        return incorrectOwner;
    }

    public Pet getPet(ArrayList<Object> data) {

        int typeId = getTypeId(data.get(7).toString());

        Pet pet = Pet.builder()
                .name(data.get(5).toString())
                .birthDate(data.get(6).toString())
                .typeID(typeId)
                .build();

        return pet;
    }

    public Pet getIncorrectPet(Pet pet) {

        Pet incorrectPet = Pet.builder()
                .name(cutString(pet.getName()))
                .birthDate(cutString(pet.getBirthDate()) + (1 + (int) (Math.random() * 28)))
                .typeID(getRandomTypeId(pet.getTypeID()))
                .build();

        return incorrectPet;
    }

    private String cutString(String str) {
        String result = str.substring(0, str.length() - 2);
        return result;
    }

    private int getTypeId(String currentPetType) {
        PetTypes petTypes = new PetTypes();
        int typeId = petTypes.getPetTypeByName(currentPetType);
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

    public ArrayList<Owner> prepareSearchData(Object[][] allOwners) {

        ArrayList<Owner> ownersList = new ArrayList<>();
        OwnersManager ownersManager = new OwnersManager();

        int listLength = allOwners.length;

        for (int i = 0; i < listLength; i++) {

            ArrayList<Object> ownersData = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                ownersData.add(allOwners[i][j]);
            }

            TestDataBuilder testDataBuilder = new TestDataBuilder();
            Owner owner = testDataBuilder.getOwner(ownersData);

            int ownerID = ownersManager.createOwnerAndGetId(owner);
            owner.setId(ownerID);
            ownersList.add(owner);
        }

        String lastname = allOwners[0][1].toString();
        int lastnameCount = ownersManager.countOwnersWithLastname(lastname);

        if (lastnameCount != listLength) {
            throw new SkipException("Quantity of lastname " + lastname +
                    " doesn't match between database and data's from Excel file!\n" +
                    "In file: " + listLength + ", in database: " + lastnameCount);
        }
        return ownersList;
    }

}
