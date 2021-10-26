package tests;

import org.testng.annotations.Test;
import tests.managers.PetsManager;

public class DeletePet {
	PetsManager petsManager = new PetsManager();

	@Test
	public void deletePet() {
		petsManager.deletePet(14);
	}

}
