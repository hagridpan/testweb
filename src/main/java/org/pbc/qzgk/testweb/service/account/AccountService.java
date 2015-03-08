package org.pbc.qzgk.testweb.service.account;

import java.util.List;

import org.pbc.qzgk.testweb.entity.User;
import org.pbc.qzgk.testweb.repository.TaskDao;
import org.pbc.qzgk.testweb.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	private UserDao userDao;
	private TaskDao taskDao;
	private Clock clock = Clock.DEFAULT;
	
	public List<User> getAllUser(){
		return (List<User>)userDao.findAll();
	}
	
	public User getUser(Long id){
		return userDao.findOne(id);
	}
	
	public User findUserByLoginName(String loginName){
		return userDao.findByLoginName(loginName);
	}
	
	public void registerUser(User user){
		entryptPassword
	}
}
