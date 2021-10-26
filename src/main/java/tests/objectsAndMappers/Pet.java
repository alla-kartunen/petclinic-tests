package tests.objectsAndMappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {

	int id;
	String birthDate, name;
	int ownerID, typeID;

}
