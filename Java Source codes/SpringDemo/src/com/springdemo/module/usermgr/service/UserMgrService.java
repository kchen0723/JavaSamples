package com.springdemo.module.usermgr.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.springdemo.core.BaseService;
import com.springdemo.core.ServiceException;
import com.springdemo.module.usermgr.dao.UserMgrDao;
import com.springdemo.module.usermgr.vo.SUser;
@Service("userMgrService")
public class UserMgrService extends BaseService{

	@Autowired
	private UserMgrDao userMgrDao;
	public void signUp(SUser user) throws ServiceException{
		try {
			user.setSignUpTime(new Date());
			userMgrDao.addUser(user);
		} catch (Exception e) {
			throw new ServiceException("◊¢≤· ß∞‹", e);
		}
		
	}
	public SUser getUserById(Integer id) throws ServiceException{
		try {
			return userMgrDao.getUserByColunm("id",id);
		} catch (Exception e) {
			throw new ServiceException("ªÒ»° ß∞‹", e);
		}
	}
	public List<SUser> getUsers() throws ServiceException{
		try {
			return userMgrDao.getUsers();
		} catch (Exception e) {
			throw new ServiceException("ªÒ»° ß∞‹", e);
		}
	}
	public SUser getUserByUserName(String userName) throws ServiceException {
		try {
			return userMgrDao.getUserByColunm("userName",userName);
		} catch (Exception e) {
			if(e instanceof EmptyResultDataAccessException){
				return null;
			}
			throw new ServiceException("ªÒ»° ß∞‹", e);
		}
	}
}
