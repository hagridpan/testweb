package org.pbc.qzgk.testweb.service.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.pbc.qzgk.testweb.entity.User;
import org.pbc.qzgk.testweb.repository.TaskDao;
import org.pbc.qzgk.testweb.repository.UserDao;
import org.pbc.qzgk.testweb.service.account.ShiroDBRealm.ShiroUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

@Component
@Transactional
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
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(clock.getCurrentDate());
		
		userDao.save(user);
	}
	
	public void updateUser(User user){
		if(StringUtils.isNotBlank(user.getPlainPassword())){
			entryptPassword(user);
		}
		userDao.save(user);
	}
	
	public void deleteUser(Long id){
		if(isSupervisor(id)){
		//	logger.warn("操作员{}尝试删除超级管理员用户",getCu);
		}
	}
	
	private boolean isSupervisor(Long id){
		return id == 1;
	}
	
	
	private String getCurrentUserName(){
		ShiroUser user  = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}
	
	
	private void entryptPassword(User user){
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	@Autowired
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	@Autowired
	public void setTaskDao(TaskDao taskDao){
		this.taskDao = taskDao;
	}
	
	public void setClock(Clock clock){
		this.clock = clock;
	}
	
}
