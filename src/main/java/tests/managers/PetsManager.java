package tests.managers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import tests.DataSourceProvider;
import tests.objectsAndMappers.Pet;
import tests.objectsAndMappers.PetsRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetsManager implements PetsManagerInterface {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(
		DataSourceProvider.INSTANCE.getDataSource()
	);

	@Override
	public int createPetAndGetId(Pet pet) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", pet.getName());
		map.put("birth_date", pet.getBirthDate());
		map.put("type_id", pet.getTypeID());
		map.put("owner_id", pet.getOwnerID());

		return new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("pets")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(map)
			.intValue();
	}

	@Override
	public void deletePet(int id) {
		jdbcTemplate.update("DELETE FROM pets WHERE id = ?", id);
	}

	@Override
	public List<Pet> getNewPetByName(String name) {
		return jdbcTemplate.query("SELECT * FROM pets WHERE name = ? ORDER BY id DESC",
			new PetsRowMapper(), new Object[] {name});
	}

	@Override
	public List<Pet> getPetById(int petId) {
		return jdbcTemplate.query("SELECT * FROM pets WHERE id = ?",
				new PetsRowMapper(), new Object[] {petId});
	}
}
