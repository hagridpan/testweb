package org.pbc.qzgk.testweb.data;

import org.pbc.qzgk.testweb.entity.User;
import org.springside.modules.test.data.RandomData;

public class UserData {
	
	public static User randomNewUser(){
		User user = new User();
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("user"));
		user.setPlainPassword(RandomData.randomName("password"));
		
		return user;
	}
}
