package org.aigua.dao.impl.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.Logger;

import org.aigua.dao.UserDao;
import org.aigua.domain.User;


public class UserJdbcDaoImpl extends JdbcDaoSupport implements UserDao  {

    private static final Logger log = Logger.getLogger(UserJdbcDaoImpl.class.getName());
    
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

	@Value("${user.next.id.sql}")
	private String nextIdSql;
	
	@Value("${user.update.sql}")
	private String updateSql;

	@Value("${user.delete.sql}")
	private String deleteSql;
	
	@Value("${user.auth.sql}")
	private String userAuthSql;

	@Value("${user.save.role}")
	private String insertUserRoleSql;
	
	@Value("${user.save.permission}")
	private String insertUserPermissionSql;
	
	@Value("${user.roles.sql}")
	private String userRolesSql;
	
	@Value("${user.permissions.sql}")
	private String userPermissionsSql;

	private static final String REPLACE_ID = "{{ID}}";
	private static final String REPLACE_USERNAME = "{{USERNAME}}";
	
	private static final String MAX    = "{{MAX}}";
	private static final String OFFSET = "{{OFFSET}}";
	
	// @Autowired
	// private JdbcTemplate jdbcTemplate;
	
	
	public User findById(int id) {
		User user = getJdbcTemplate().queryForObject(findByIdSql, new Object[] { id }, 
			new BeanPropertyRowMapper<User>(User.class));
		return user;
	}

	
	public User findByUsername(String username) {
		String searchSql = findByUsernameSql.replace(REPLACE_USERNAME, username);
		User user = getJdbcTemplate().queryForObject(searchSql, new Object[] {}, 
			new BeanPropertyRowMapper<User>(User.class));
		return user;	
	}

	
	public List<User> findAll() {
		List<User> users = getJdbcTemplate().query(findAllSql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	
	public List<User> findAllOffset(int max, int offset) {
		String sql = findAllSql;
		sql+= " " + paginate;
		sql = sql.replace(MAX, Integer.toString(max));
		sql = sql.replace(OFFSET, Integer.toString(offset));

		List<User> users = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class));

		return users;
	}
	


	public User save(User user) {
		int id = getJdbcTemplate().queryForInt(nextIdSql, new Object[0]);
		getJdbcTemplate().update(insertSql, new Object[] { 
			id, user.getName(), user.getEmail(), user.getUsername(), user.getPasswordHash()  
		});
		User savedUser = findById(id);
		return savedUser;
	}

	
	public User update(User user) {
		getJdbcTemplate().update(updateSql, new Object[] { 
			user.getName(), user.getEmail(), user.getUsername(), user.getPasswordHash(), user.getId()  
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
		User user = findByUsername(username);
		return user.getPasswordHash();
	}
	
	
	public void saveUserRole(int userId, int roleId){
		getJdbcTemplate().update(insertUserRoleSql, new Object[] { 
			roleId, userId
		});
	}
	
	
	public void saveUserPermission(int userId, String permission){
		getJdbcTemplate().update(insertUserPermissionSql, new Object[] { 
			userId, permission
		});
	}
	
	
	public Set<String> getUserRoles(int id) {	
		String search = userRolesSql.replace(REPLACE_ID, Integer.toString(id));
		List<String> rolesList = getJdbcTemplate().queryForList(search, String.class);
		Set<String> roles = new HashSet<String>(rolesList);
		return roles;
	}

	
	public Set<String> getUserPermissions(int id) {
		String search = userPermissionsSql.replace(REPLACE_ID, Integer.toString(id));
		List<String> permissionsList = getJdbcTemplate().queryForList(search, String.class);
		Set<String> permissions = new HashSet<String>(permissionsList);
		return permissions;
	}
	

	
	public Set<String> getUserRoles(String username) {	
		User user = findByUsername(username);
		String search = userRolesSql.replace(REPLACE_ID, Integer.toString(user.getId()));
		List<String> rolesList = getJdbcTemplate().queryForList(search, String.class);
		log.debug(rolesList);
		Set<String> roles = new HashSet<String>(rolesList);
		return roles;
	}

	
	public Set<String> getUserPermissions(String username) {
		User user = findByUsername(username);
		String search = userPermissionsSql.replace(REPLACE_ID, Integer.toString(user.getId()));
		List<String> permissionsList = getJdbcTemplate().queryForList(search, String.class);
		Set<String> permissions = new HashSet<String>(permissionsList);
		return permissions;
	}
	
}