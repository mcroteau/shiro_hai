package org.aigua.dao;

import java.util.List;
import org.aigua.domain.Role;

public interface RoleDao {
	
	public int count();
	
	public Role findById(int id);
	
	public List<Role> findAll();
	
	public List<Role> findAllOffset(int max, int offset);
	
	public Role save(Role role);
	
	public Role update(Role role);
	
	public Role delete(int id);
	
}