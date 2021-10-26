package tests.managers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import tests.DataSourceProvider;
import tests.objectsAndMappers.Owner;
import tests.objectsAndMappers.OwnersRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnersManager implements OwnersManagerInterface {

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

		return new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(map).intValue();
	}

	@Override
	public void deleteOwner(int id) {
		jdbcTemplate.update("DELETE FROM owners WHERE id = ?", id);
	}

	@Override
	public List<Owner> getNewOwnerByLastName(String lastName) {
		return jdbcTemplate.query("SELECT * FROM owners WHERE last_name = ? ORDER BY id DESC",
			new OwnersRowMapper(), new Object[] {lastName});
	}

	@Override
	public List<Owner> getOwnerById(int ownerId) {
		return jdbcTemplate.query("SELECT * FROM owners WHERE id = ?",
				new OwnersRowMapper(), new Object[] {ownerId});
	}

	@Override
	public int countOwnersWithLastname(String lastName) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM owners WHERE last_name = ?",
				new Object[] {lastName}, Integer.class);
	}

}
