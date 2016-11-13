package com.spring.training5.jdbc;

import java.util.List;

public interface JdbcUserRepository {
	
	List<JdbcUser> getUsers();
	
	JdbcUser getUser(int id);
	
	void saveUser(JdbcUser user);
	
	void editUser(JdbcUser user);
	

}
