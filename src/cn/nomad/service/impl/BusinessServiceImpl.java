package cn.nomad.service.impl;

import java.util.List;

import cn.nomad.dao.CheckinDao;
import cn.nomad.dao.FenceDao;
import cn.nomad.dao.FriendDao;
import cn.nomad.dao.UserDao;
import cn.nomad.dao.impl.CheckinDaoImpl;
import cn.nomad.dao.impl.FenceDaoImpl;
import cn.nomad.dao.impl.FriendDaoImpl;
import cn.nomad.dao.impl.UserDaoImpl;
import cn.nomad.domain.Checkin;
import cn.nomad.domain.Fence;
import cn.nomad.domain.User;
import cn.nomad.exception.FenceExistException;
import cn.nomad.exception.FriendExistException;
import cn.nomad.exception.UserExistException;
import cn.nomad.exception.UserNotExistException;
import cn.nomad.service.BusinessService;
import cn.nomad.utils.ServiceUtils;

//对web层提供所有的业务服务
public class BusinessServiceImpl implements BusinessService {

	private UserDao userDao = new UserDaoImpl();
	private FriendDao friendDao = new FriendDaoImpl();
	private FenceDao fenceDao = new FenceDaoImpl();
	private CheckinDao checkinDao = new CheckinDaoImpl();

	//对web层提供注册服务
	public void register(User user) throws UserExistException{
		//判断用户是否存在
		boolean b = userDao.find(user.getUsername());
		if(b){
			throw new UserExistException(); //发现要注册的用户已经存在，则给web层抛一个编译时异常，给用户友好提示
		}else {
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			userDao.add(user);
		}
	}
	
	//对web层提供登录服务
	public User login(String username, String password){
		password = ServiceUtils.md5(password);
		return userDao.find(username, password);
	}
	
	//对web层提供加好友服务:添加好友，查询所有好友
	public void addFriend(String username, String toMail) throws UserNotExistException, FriendExistException{
		boolean b = userDao.find(toMail);
		if(b){		//要添加的好友用户已注册   查找username的好友，比对，如果没有该好友就注册该好友
			boolean c = friendDao.findFriend(username, toMail);
			if(c){
				throw new FriendExistException();
			}else{
				friendDao.addFriend(username, toMail);
			}
		}else{  //用户不存在，抛一个用户不存在运行时异常
			throw new UserNotExistException();
		}
	}
	public List<String> getFriends(String username){
		return friendDao.findFriends(username);
	}
	
	//对web层提供加围栏服务:添加围栏，查询所有围栏
	@Override
	public List<Fence> getFences(String username) {
		return fenceDao.findFences(username);
	}
	@Override
	public void addFence(Fence fence) throws FenceExistException {
		boolean b = fenceDao.find(fence);
		if(b){ //围栏已经存在
			throw new FenceExistException();
		}else{
			fenceDao.addFence(fence);
		}
	}
	
	//对web层提供签到服务
	@Override
	public void checkin(Checkin checkin) {
		checkinDao.checkin(checkin);
	}

	@Override
	public List<Checkin> getCheckins(String username) {
		return checkinDao.getCheckins(username);
	}
	
	
	
}
