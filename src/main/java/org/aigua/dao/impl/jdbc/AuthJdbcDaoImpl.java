package org.aigua.dao.impl.jdbc;

import org.aigua.dao.RoleDao;


public class RoleJdbcDaoImpl implements RoleDao {
	
	public int count();
	
	public User findById(int id);
	
	public User findByName(String name);
	
	public List<User> findAll();
	
	public List<User> findAllOffset(int max, int offset);
	
	public User save(Role role);
	
	public User update(Role role);
	
	public User delete(int id);
	
}