package com.spring.training5.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class JdbcUserRepositoryImpl extends JdbcDaoSupport implements JdbcUserRepository {


	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public JdbcUserRepositoryImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<JdbcUser> getUsers() {
		String sql = "select id,first_name,last_name,email from tbl_user";
		return getJdbcTemplate().query(sql, new UserRowMapper());
	}
	
	public JdbcUser getUser(int id) {
		String sql = "select id,first_name,last_name,email from tbl_user where id=?";
		return getJdbcTemplate().queryForObject(sql, new UserRowMapper(), id);
	}
	
	public void saveUser(JdbcUser user) {
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		
		try {
			String sql = "insert into tbl_user(first_name,last_name,email) values(?,?,?)";
			getJdbcTemplate().update(sql,user.getFirstName(), user.getLastName(), user.getEmail());
			
			if(null != user)
				throw new RuntimeException();
			
			String sql2 = "insert into tbl_user(first_name,last_name,email) values(?,?,?)";
			getJdbcTemplate().update(sql2,user.getFirstName() + "2", user.getLastName(), user.getEmail());
			
			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			transactionManager.rollback(transactionStatus);
		}
		
	}
	
	public void editUser(JdbcUser user) {
		String sql = "update tbl_user set first_name=?,last_name=?,email=? where id=?";
		getJdbcTemplate().update(sql,user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
	}

	private class UserRowMapper implements RowMapper<JdbcUser> {
		public JdbcUser mapRow(ResultSet rset, int index) throws SQLException {
			JdbcUser user = new JdbcUser();
			user.setId(rset.getInt("id"));
			user.setFirstName(rset.getString("first_name"));
			user.setLastName(rset.getString("last_name"));
			user.setEmail(rset.getString("email"));
			return user;
		}
	}

}
