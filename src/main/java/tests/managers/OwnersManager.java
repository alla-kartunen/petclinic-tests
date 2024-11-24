package tests.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import tests.DataSourceProvider;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.OwnersRowMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnersManager implements OwnersManagerInterface {

	private Logger logger = LogManager.getLogger(OwnersManager.class);;

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(
		DataSourceProvider.INSTANCE.getDataSource()
	);

	@Override
	public int createOwnerAndGetId(Owner owner) {
		Map<String, Object> map = new HashMap<>();
		map.put("first_name", owner.getFirstName());
		map.put("last_name", owner.getLastName());
		map.put("address", owner.getAddress());
		map.put("city", owner.getCity());
		map.put("telephone", owner.getTelephone());

		int id = new SimpleJdbcInsert(jdbcTemplate)
						.withTableName("owners")
						.usingGeneratedKeyColumns("id")
						.executeAndReturnKey(map).intValue();
		logger.info("Created a new owner with id = " + id);
		return id;
	}

	@Override
	public void deleteOwner(int id) {
		logger.info("DELETE FROM owners WHERE id = " + id);
		int count = jdbcTemplate.update("DELETE FROM owners WHERE id = ?", id);
		logger.info("Deleted " + count + " records");
	}

	@Override
	public void deleteOwner(String lastName) {
		logger.info("DELETE FROM owners WHERE last_name = '" + lastName + "'");
		int count = jdbcTemplate.update("DELETE FROM owners WHERE last_name = ?", lastName);
		logger.info("Deleted " + count + " records");
	}

	@Override
	public void deleteOwner(ArrayList<Owner> ownersList) {
		String idList = getIdList(ownersList);
		String updateString = "DELETE FROM owners WHERE id in (" + idList + ")";
		logger.info(updateString);
		int count = jdbcTemplate.update(updateString);
		logger.info("Deleted " + count + " records");
	}

	@Override
	public List<Owner> getNewOwnerByLastName(String lastName) {
		logger.info("SELECT * FROM owners WHERE last_name = '" + lastName + "'");
		List<Owner> list = jdbcTemplate.query("SELECT * FROM owners WHERE last_name = ? ORDER BY id DESC",
				new OwnersRowMapper(), new Object[] {lastName});
		logger.info("Selected " + list.size() + " owners");
		return list;
	}

	@Override
	public List<Owner> getOwnerById(int ownerId) {
		logger.info("SELECT * FROM owners WHERE id = " + ownerId);
		List<Owner> list = jdbcTemplate.query("SELECT * FROM owners WHERE id = ?",
				new OwnersRowMapper(), new Object[] {ownerId});
		logger.info("Selected " + list.size() + " owners");
		return list;
	}

	@Override
	public int countOwnersWithLastname(String lastName) {
		logger.info("SELECT COUNT(*) as count FROM owners WHERE last_name = '" + lastName + "'");
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM owners WHERE last_name = ?",
				new Object[] {lastName}, Integer.class);
		logger.info("Query result: " + result);
		return result;
	}

	private String getIdList(ArrayList<Owner> ownersList) {
		String idList = "";
		int listLength = ownersList.size();

		for (int i = 0; i < listLength; i++) {
			if (i == 0) {
				idList = "" + ownersList.get(i).getId(); // first id
			} else {
				idList = idList + "," + ownersList.get(i).getId();
			}
		}
		return idList;
	}
}
