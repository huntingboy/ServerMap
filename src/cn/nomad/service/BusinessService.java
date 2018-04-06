package cn.nomad.service;

import cn.nomad.domain.User;
import cn.nomad.exception.UserExistException;

public interface BusinessService {

	//对web层提供注册服务
	void register(User user) throws UserExistException;

	//对web层提供登录服务
	User login(String username, String password);

}