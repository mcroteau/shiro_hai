package org.aigua.dao.impl.mock;

import java.util.List;
import java.util.ArrayList;
import org.aigua.domain.User;
import org.aigua.dao.UserDao;

public class UserMockDaoImpl implements UserDao {

	/**
		Mohandas Karamchand Gandhi
		Jiddu Krishnamurti
		Jesus
		Budda - Siddhattha Gotama
		Confucius - Kong Qiu
		J Fresco
		Muhammad - Abū al-Qāsim Muḥammad
		Martin Luther King
		Tesla Nikola
		Epicurus
	**/
	
	private static final int MAX = 100;
	
	private String[] firstNames = {"J", "M.K", "J", "J", "S", "K", "A", "M", "N", "Epicurus"};
	private String[] lastNames = {"Fresco", "Gandhi", "Krishnamurti", "C", "Gotama", "Qiu", "al-Qasim Muhammad", "King", "Tesla", ""};


	public User findById(int id){
		return getMockUsers(1).get(1);
	}
	
	public List<User> findAll(){
		return getMockUsers(MAX);
	}
	
	public List<User> findAllOffset(int max, int offset){
		return getMockUsers(max);
	}
	
	public List<User> search(String term){
		return getMockUsers(MAX);
	}
	
	public User save(User user){
		return getMockUsers(1).get(1);
	}
	
	public User update(User user){
		return getMockUsers(1).get(1);
	}
	
	public User delete(int id){
		return getMockUsers(1).get(1);
	}
	
	public int count(){
		return 10;
	}
	
	
	private List<User> getMockUsers(int numberOfUsers){
	
		if(numberOfUsers > MAX)numberOfUsers = MAX;
		List<User> users = new ArrayList<User>();
		
		for(int k = 0; k < numberOfUsers; k++){
			User mockUser = new User();
			mockUser.setId(k);
			mockUser.setEmail("mockuser" + k + "@email.com");
			mockUser.setUsername("mockuser" + k);
			mockUser.setPassword("password");
			mockUser.setFirstName("first" + k);
			mockUser.setLastName("last" + k);
			users.add(mockUser);
		}
		
		return users;
	}
	
	
}