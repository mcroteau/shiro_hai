package org.aigua.dao.impl.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

import org.aigua.dao.UserDao;
import org.aigua.domain.User;


public class UserJdbcDaoImpl extends JdbcDaoSupport implements UserDao  {

	@Value("${find.sql}")
	private String findSql;

	@Value("${find.all.sql}")
	private String findAllSql;

	@Value("${search.sql}")
	private String searchSql;

	@Value("${save.sql}")
	private String insertSql;

	@Value("${count.sql}")
	private String countSql;

	@Value("${update.sql}")
	private String updateSql;

	@Value("${delete.sql}")
	private String deleteSql;
	
	@Value("${auth_query}")
	private String authenticationQuery;

	@Value("${user_roles_query}")
	private String userRolesQuery;
	
	@Value("${user_permissions_query}")
	private String permissionsQuery;
	
	
	public User findById(int id) {
		try{

			User user = getJdbcTemplate().queryForObject(permissionsQuery, new Object[] { id }, 
				new BeanPropertyRowMapper<User>(User.class));

			return user;

		}catch (Exception e){
			e.printStackTrace();
		}	
		return null;	
		
	}

	public List<User> findAll() {
		try{
			
			List<User> users = getJdbcTemplate().query(findAllSql, new BeanPropertyRowMapper<User>(User.class));
			return users;

		}catch (Exception e){
			e.printStackTrace();
		}	
		return null;	
	}

	
	public List<User> findAllOffset(int max, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<User> search(String term) {
		// TODO Auto-generated method stub
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
		return getJdbcTemplate().queryForObject(authenticationQuery, String.class);
	}

	public Set<String> getUserRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getUserPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
}