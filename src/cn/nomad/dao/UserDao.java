package cn.nomad.dao;

import java.util.List;

import cn.nomad.domain.Fence;
import cn.nomad.domain.User;

public interface UserDao {

	void add(User user);

	User find(int id);

	User find(String username, String password);

	//查找是否有同名用户
	boolean find(String username);
	public List<User> getAll();

}