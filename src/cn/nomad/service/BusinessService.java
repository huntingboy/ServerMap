package cn.nomad.service;

import java.util.List;

import cn.nomad.domain.Checkin;
import cn.nomad.domain.Fence;
import cn.nomad.domain.User;
import cn.nomad.exception.FenceExistException;
import cn.nomad.exception.FriendExistException;
import cn.nomad.exception.UserExistException;
import cn.nomad.exception.UserNotExistException;

public interface BusinessService {

	//对web层提供注册服务
	void register(User user) throws UserExistException;

	//对web层提供登录服务
	User login(String username, String password);
	
	//对web层提供好友服务
	void addFriend(String username, String toMail) throws UserNotExistException, FriendExistException;
	List<String> getFriends(String username);

	//对web层提供围栏服务
	List<Fence> getFences(String username);
	void addFence(Fence fence) throws FenceExistException;

	void checkin(Checkin checkin);

	List<Checkin> getCheckins(String username);
}