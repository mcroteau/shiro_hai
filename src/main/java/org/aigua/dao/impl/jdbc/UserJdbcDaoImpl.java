package org.aigua.dao.impl.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.aigua.dao.UserDao;
import org.aigua.domain.User;


public class UserJdbcDaoImpl extends JdbcDaoSupport implements UserDao  {

    private static final Logger logger = Logger.getLogger(UserJdbcDaoImpl.class.getName());
    
	@Value("${paginate}")
	private String paginate;
	
	@Value("${user.find.id.sql}")
	private String findByIdSql;

	@Value("${user.find.username.sql}")
	private String findByUsernameSql;

	@Value("${user.find.all.sql}")
	private String findAllSql;
	
	@Value("${user.save.sql}")
	private String insertSql;

	@Value("${user.count.sql}")
	private String countSql;

	@Value("${user.update.sql}")
	private String updateSql;

	@Value("${user.delete.sql}")
	private String deleteSql;
	
	@Value("${user.auth.sql}")
	private String userAuthSql;

	@Value("${user.roles.sql}")
	private String userRolesSql;
	
	@Value("${user.permissions.sql}")
	private String userPermissionsSql;

	private static final String REPLACE_ID = "{{ID}}";
	
	private static final String MAX    = "{{MAX}}";
	private static final String OFFSET = "{{OFFSET}}";
	
	
	
	public User findById(int id) {
		User user = getJdbcTemplate().queryForObject(findByIdSql, new Object[] { id }, 
			new BeanPropertyRowMapper<User>(User.class));
		return user;
	}

	
	public User findByUsername(String username) {
		User user = getJdbcTemplate().queryForObject(findByUsernameSql, new Object[] { username }, 
			new BeanPropertyRowMapper<User>(User.class));
		return user;	
	}

	
	public List<User> findAll() {
		List<User> users = getJdbcTemplate().query(findAllSql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	
	public List<User> findAllOffset(int max, int offset) {
		try{

			String sql = findAllSql;
			sql+= " " + paginate;
			sql = sql.replace(MAX, Integer.toString(max));
			sql = sql.replace(OFFSET, Integer.toString(offset));

			System.out.println("find all " + sql);
			List<User> types = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class));

			return types;

		}catch (Exception e){
			e.printStackTrace();
		}	
		return null;	
	}
	


	public User save(User user) {
		int id = getJdbcTemplate().queryForInt(countSql, new Object[0]);
		getJdbcTemplate().update(insertSql, new Object[] { 
			id, user.getName(), user.getEmail(), user.getUsername(), user.getPassword()  
		});
		User savedUser = findById(id);
		return savedUser;
	}

	
	public User update(User user) {
		getJdbcTemplate().update(updateSql, new Object[] { 
			user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getId()  
		});

		return findById(user.getId());
	}

	
	public User delete(int id) {
		User user = findById(id);
		getJdbcTemplate().update(deleteSql, new Object[] {id });
		return user;
	}

	
	public int count() {
		int id = getJdbcTemplate().queryForInt(countSql, new Object[0]);
	 	return id; 
	}

	
	public String getUserPassword(String username) {
		User user = getJdbcTemplate().queryForObject(userAuthSql, new Object[] { username }, 
				new BeanPropertyRowMapper<User>(User.class));	
		return user.getPassword();
	}

	
	@SuppressWarnings("unchecked")
	public Set<String> getUserRoles(String username) {		
		User user = findByUsername(username);
		String search = userRolesSql.replace(REPLACE_ID, Integer.toString(user.getId()));
		Set<String> roles = (Set<String>)getJdbcTemplate().query(search, new BeanPropertyRowMapper<String>(String.class));
		return roles;
	}

	
	@SuppressWarnings("unchecked")
	public Set<String> getUserPermissions(String username) {
		User user = findByUsername(username);
		String search = userPermissionsSql.replace(REPLACE_ID, Integer.toString(user.getId()));
		Set<String> permissions = (Set<String>)getJdbcTemplate().query(search, new BeanPropertyRowMapper<String>(String.class));
		return permissions;
	}
	
}