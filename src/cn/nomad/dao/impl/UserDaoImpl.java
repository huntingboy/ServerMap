package cn.nomad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.xml.internal.bind.v2.TODO;

import cn.nomad.dao.UserDao;
import cn.nomad.domain.User;
import cn.nomad.utils.JdbcUtils;

public class UserDaoImpl implements UserDao{
	public void add(User user) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into User(id, username, password) values(?, ?, ?, ?)";
			//TODO id自动增长，可以不要
			Object params[] = {user.getId(), user.getUsername(), user.getPassword()};
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public User find(int id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from User where id = ?";
			@SuppressWarnings({ "deprecation" })
			User user = (User) runner.query(sql, id, new BeanHandler<User>(User.class));
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User find(String username, String password){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from User where username = ? and password = ?";
			Object params[] = {username, password};
			@SuppressWarnings({ "deprecation" })
			User user = (User) runner.query(sql, params, new BeanHandler<User>(User.class));
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//查找是否有同名用户
	public boolean find(String username){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from User where username = ?";
			@SuppressWarnings({ "deprecation" })
			User user = (User) runner.query(sql, username, new BeanHandler<User>(User.class));
			return (user == null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<User> getAll(){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from User";
			List<User> list = (List<User>) runner.query(sql, new BeanListHandler<User>(User.class));
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
