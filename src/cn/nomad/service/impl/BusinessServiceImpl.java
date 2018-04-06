package cn.nomad.service.impl;

import cn.nomad.dao.UserDao;
import cn.nomad.dao.impl.UserDaoImpl;
import cn.nomad.domain.User;
import cn.nomad.exception.UserExistException;
import cn.nomad.service.BusinessService;
import cn.nomad.utils.ServiceUtils;

//对web层提供所有的业务服务
public class BusinessServiceImpl implements BusinessService {

	private UserDao dao = new UserDaoImpl();
	
	//对web层提供注册服务
	public void register(User user) throws UserExistException{
		//判断用户是否存在
		boolean b = dao.find(user.getUsername());
		if(b){
			throw new UserExistException(); //发现要注册的用户已经存在，则给web层抛一个编译时异常，给用户友好提示
		}else {
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	
	//对web层提供登录服务
	public User login(String username, String password){
		password = ServiceUtils.md5(password);
		return dao.find(username, password);
	}
}
