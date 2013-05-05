package org.aigua.dao.impl.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.log4j.Logger;
import java.util.List;

import org.aigua.dao.RoleDao;
import org.aigua.domain.Role;


public class RoleJdbcDaoImpl extends JdbcDaoSupport implements RoleDao {

	private static final Logger log = Logger.getLogger(RoleJdbcDaoImpl.class.getName());
	
	@Value("${paginate}")
	private String paginate;
	
	@Value("${role.count.sql}")
	private String countSql;
	
	@Value("${role.next.id.sql}")
	private String nextIdSql;
	
	@Value("${role.find.id.sql}")
	private String findByIdSql;
	
	@Value("${role.find.name.sql}")
	private String findByNameSql;
	
	@Value("${role.find.all.sql}")
	private String findAllSql;
	
	@Value("${role.save.sql}")
	private String insertSql;
	
	@Value("${role.update.sql}")
	private String updateSql;
	
	@Value("${role.delete.sql}")
	private String deleteSql;
	
	private static final String MAX    = "{{MAX}}";
	private static final String OFFSET = "{{OFFSET}}";
	
	private static final String REPLACE_NAME = "{{NAME}}";
	
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	
	public int count() {
		int id = jdbcTemplate.queryForInt(countSql, new Object[0]);
		log.debug("count id : " + id);
	 	return id; 
	}
	
	public Role findById(int id) {
		Role role = jdbcTemplate.queryForObject(findByIdSql, new Object[] { id }, 
				new BeanPropertyRowMapper<Role>(Role.class));
		return role;
	}

	
	public Role findByName(String name) {
		String search = findByNameSql.replace(REPLACE_NAME, name);
		Role role = jdbcTemplate.queryForObject(search, new Object[] {}, 
				new BeanPropertyRowMapper<Role>(Role.class));
		return role;
	}
	
	
	public List<Role> findAll() {
		List<Role> roles = jdbcTemplate.query(findAllSql, new BeanPropertyRowMapper<Role>(Role.class));
		return roles;
	}
	
	
	public List<Role> findAllOffset(int max, int offset) {
		String sql = findAllSql;
		sql+= " " + paginate;
		sql = sql.replace(MAX, Integer.toString(max));
		sql = sql.replace(OFFSET, Integer.toString(offset));
	
		System.out.println("find all " + sql);
		List<Role> roles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
	
		return roles;
	}
	
	
	public Role save(Role role) {
		int id = jdbcTemplate.queryForInt(nextIdSql, new Object[0]);
		jdbcTemplate.update(insertSql, new Object[] { 
			id, role.getName()  
		});
		Role savedRole = findById(id);
		return savedRole;
	}
	
	
	public Role update(Role role) {
		jdbcTemplate.update(updateSql, new Object[] { 
			role.getName(), role.getId()  
		});
		return findById(role.getId());
	}
	
	
	public Role delete(int id) {
		Role role = findById(id);
		jdbcTemplate.update(deleteSql, new Object[] {id });
		return role;
	}
	

}