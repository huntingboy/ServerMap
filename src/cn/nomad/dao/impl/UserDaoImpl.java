package cn.nomad.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import cn.nomad.dao.UserDao;
import cn.nomad.domain.Fence;
import cn.nomad.domain.Friend;
import cn.nomad.domain.User;
import cn.nomad.utils.JdbcUtils;

public class UserDaoImpl implements UserDao{
	public void add(User user) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into User(username, password) values(?, ?)";
			Object params[] = {user.getUsername(), user.getPassword()};
			runner.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			List<User> list = (List<User>) runner.query(sql, username, new BeanListHandler<User>(User.class));  //list ！= null恒成立
			System.out.println("UserDaoImpl->boolean find(string)查找同名用户:list.toString():" + list.toString());  //当list.size()=0时，list.toString()变为[]
			return (list.size() != 0);
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
