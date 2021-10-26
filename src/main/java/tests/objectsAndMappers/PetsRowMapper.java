package tests.objectsAndMappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetsRowMapper implements RowMapper<Pet> {

	@Override
	public Pet mapRow(ResultSet resultSet, int i) throws SQLException {
		Pet pet = new Pet();

		pet.setId(resultSet.getInt("id"));
		pet.setBirthDate(resultSet.getString("birth_date"));
		pet.setName(resultSet.getString("name"));
		pet.setOwnerID(resultSet.getInt("owner_id"));
		pet.setTypeID(resultSet.getInt("type_id"));

		return pet;
	}
}
