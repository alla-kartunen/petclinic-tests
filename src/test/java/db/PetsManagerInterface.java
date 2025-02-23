package db;

import objects.Pet;

import java.util.List;

public interface PetsManagerInterface {

    int createPetAndGetId(Pet pet);

    void deletePet(int id);

    void deletePetByOwner(String ownerLastName);

    List<Pet> getNewPetByName(String name);

    List<Pet> getPetById(int petId);

    void checkPetByOwner(String ownerLastName);

}
