package com.spring.training5.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.training5.config.AppInitConfig;
import com.spring.training5.jdbc.JdbcUser;
import com.spring.training5.jdbc.JdbcUserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppInitConfig.class)
public class SpringJdbcTest {

	@Autowired
	JdbcUserRepository repository;


	 @Test
	public void test2() {
		JdbcUser jdbcUser = repository.getUser(1);
		System.out.println(jdbcUser);
	}

	@Test
	public void test3() {
		JdbcUser user = new JdbcUser();
		user.setFirstName("Spring");
		user.setLastName("Jdbc");
		user.setEmail("jdbc@spring.io");
		repository.saveUser(user);
	}

	@Test
	public void test4() {
		JdbcUser user = new JdbcUser();
		user.setId(13);
		user.setFirstName("Spring-Edit");
		user.setLastName("Jdbc-Edit");
		user.setEmail("jdbc@spring.io");
		repository.editUser(user);
	}

	 @Test
	public void test1() {
		List<JdbcUser> jdbcUsers = repository.getUsers();
		for (JdbcUser user : jdbcUsers) {
			System.out.println(user);
		}
	}
}
