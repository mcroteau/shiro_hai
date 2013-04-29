package org.aigua.dao;

import org.aigua.domain.User;
import java.util.List;

public interface UserDao {
	
	public User findById(int id);
	
	public List<User> findAll();
	
	public List<User> findAllOffset(int max, int offset);
	
	public List<User> search(String term);
	
	public User save(User user);
	
	public User update(User user);
	
	public User delete(int id);
	
	public int count();
	
	
}
