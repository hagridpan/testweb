package org.pbc.qzgk.testweb.rest;

import java.net.URI;

import javax.validation.Validator;

import org.pbc.qzgk.testweb.entity.Task;
import org.pbc.qzgk.testweb.service.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

public class TaskRestController {

	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Task get(@PathVariable("id") Long id){
		Task task = taskService.getTask(id);
		if(task == null){
			String message = "任务不存在(id:"+ id +")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND,message);
		}
		return task;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody Task task, UriComponentsBuilder uriBuilder){
		BeanValidators.validateWithException(validator, task);
		
		taskService.saveTask(task);
		
		Long id = task.getId();
		URI uri = uriBuilder.path("api/v1/task/"+id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		
		return new ResponseEntity(headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Task task){
		BeanValidators.validateWithException(validator, task);
		
		taskService.saveTask(task);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id){
		taskService.deleteTask(id);
	}
	
	
}
