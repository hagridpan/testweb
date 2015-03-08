package org.pbc.qzgk.testweb.data;

import org.pbc.qzgk.testweb.entity.Task;
import org.pbc.qzgk.testweb.entity.User;
import org.springside.modules.test.data.RandomData;

public class TaskData {

	public static Task randomTask(){
		Task task = new Task();
		task.setTitle(randomTitle());
		User user = new User(1L);
		task.setUser(user);
		return task;
	}
	
	public static String randomTitle(){
		return RandomData.randomName("task");
	}
}
