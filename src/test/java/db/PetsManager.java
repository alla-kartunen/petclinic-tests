package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import dataproviders.DataSourceProvider;
import objects.Pet;
import objects.PetsRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static db.BaseManager.Log.*;

public class PetsManager extends BaseManager implements PetsManagerInterface {

	private Logger logger = LogManager.getLogger(OwnersManager.class);
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

		log(CREATE_PET, map);
		int id = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("pets")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(map)
			.intValue();
		logger.info("Created a new pet with id = " + id);
		return id;
	}

	@Override
	public void deletePet(int id) {
		log(DELETE_PET, "id = " + id);
		log(COUNT_DELETED, jdbcTemplate.update("DELETE FROM pets WHERE id = ?", id));
	}

	@Override
	public void deletePetByOwner(String ownerLastName) {
		log(DELETE_PET, "owner_id in (SELECT id FROM owners WHERE last_name = '" + ownerLastName + "')");
		log(COUNT_DELETED, jdbcTemplate.update("DELETE FROM pets WHERE owner_id in " +
				"(SELECT id FROM owners WHERE last_name = ?)", ownerLastName));
	}

	@Override
	public List<Pet> getNewPetByName(String name) {
		log(SELECT_PET, "name = '" + name + "' ORDER BY id DESC");
		List<Pet> list = jdbcTemplate.query("SELECT * FROM pets WHERE name = ? ORDER BY id DESC",
			new PetsRowMapper(), new Object[] {name});
		log(COUNT_SELECTED, list.size());
		return list;
	}

	@Override
	public List<Pet> getPetById(int petId) {
		log(SELECT_PET, "id = " + petId);
		List<Pet> list = jdbcTemplate.query("SELECT * FROM pets WHERE id = ?",
				new PetsRowMapper(), new Object[] {petId});
		log(COUNT_SELECTED, list.size());
		return list;
	}

	@Override
	public void checkPetByOwner(String ownerLastName) {
		logger.info("Check if owner has pets");
		logger.info("SELECT COUNT(*) as count FROM pets where owner_id in " +
						"(SELECT id FROM owners WHERE last_name = '" + ownerLastName + "')");
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM pets where owner_id in " +
						"(SELECT id FROM owners WHERE last_name = ?)",
				new Object[] {ownerLastName}, Integer.class);
		logger.info("Founded " + count + " pet(s) for owner(s) with " + ownerLastName + " last name");
		if (count > 0) {
			deletePetByOwner(ownerLastName);
		}
	}

}