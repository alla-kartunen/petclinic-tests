package tests.managers;

import tests.objectsAndMappers.Owner;

import java.util.ArrayList;
import java.util.List;

public interface OwnersManagerInterface {

	int createOwnerAndGetId(Owner owner);

	void deleteOwner(int id);

	void deleteOwner(String lastName);

	void deleteOwner(ArrayList<Owner> ownersList);

	List<Owner> getNewOwnerByLastName(String lastName);

	List<Owner> getOwnerById(int ownerId);

	int countOwnersWithLastname(String lastName);

}
