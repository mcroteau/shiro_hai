package org.aigua.domain;


public class User{
	
	private int id;
	private String username;
	private String passwordHash;

	private String name;
	private String email;

	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	
	public String getPasswordHash(){
		return this.passwordHash;
	}
	
	public void setPasswordHash(String passwordHash){
		this.passwordHash = passwordHash;
	}
	
		
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	
}