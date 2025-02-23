package objects;

public class PetTypes {

	public String getPetTypeById(int typeID) {
		String typeName = "";

		switch (typeID) {
			case 1:
				typeName = "cat";
				break;
			case 2:
				typeName = "dog";
				break;
			case 3:
				typeName = "lizard";
				break;
			case 4:
				typeName = "snake";
				break;
			case 5:
				typeName = "bird";
				break;
			case 6:
				typeName = "hamster";
				break;
		}
		return typeName;
	}

	public int getPetTypeByName(String typeName) {
		int typeID = 0;

		switch (typeName) {
			case "cat":
				typeID = 1;
				break;
			case "dog":
				typeID = 2;
				break;
			case "lizard":
				typeID = 3;
				break;
			case "snake":
				typeID = 4;
				break;
			case "bird":
				typeID = 5;
				break;
			case "hamster":
				typeID = 6;
				break;
		}
		return typeID;
	}
}
