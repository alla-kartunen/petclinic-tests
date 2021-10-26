package tests.managers;

import tests.objectsAndMappers.Pet;

import java.util.List;

public interface PetsManagerInterface {

    int createPetAndGetId(Pet pet);

    void deletePet(int id);

    List<Pet> getNewPetByName(String name);

    List<Pet> getPetById(int petId);

}
