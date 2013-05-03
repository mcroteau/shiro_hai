package org.aigua.dao;

import org.aigua.domain.User;
import java.util.List;
import java.util.Set;

public interface UserDao {
	
	public User findById(int id);
	
	public List<User> findAll();
	
	public List<User> findAllOffset(int max, int offset);
	
	public List<User> search(String term);
	
	public User save(User user);
	
	public User update(User user);
	
	public User delete(int id);
	
	public int count();
	
	public String getUserPassword(String username);
	
	public Set<String> getUserRoles(String username);
	
	public Set<String> getUserPermissions(String username);
	
}
