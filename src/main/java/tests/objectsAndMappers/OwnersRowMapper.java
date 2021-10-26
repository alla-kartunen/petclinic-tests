package tests.objectsAndMappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnersRowMapper implements RowMapper<Owner> {

	@Override
	public Owner mapRow(ResultSet resultSet, int i) throws SQLException {

		Owner owner = new Owner();

		owner.setId(resultSet.getInt("id"));
		owner.setFirstName(resultSet.getString("first_name"));
		owner.setLastName(resultSet.getString("last_name"));
		owner.setAddress(resultSet.getString("address"));
		owner.setCity(resultSet.getString("city"));
		owner.setTelephone(resultSet.getString("telephone"));

		return owner;
	}
}
