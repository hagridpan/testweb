package org.pbc.qzgk.testweb.web.account;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.pbc.qzgk.testweb.entity.User;
import org.pbc.qzgk.testweb.service.account.AccountService;
import org.pbc.qzgk.testweb.service.account.ShiroDBRealm.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model){
		Long id = getCurrentUserId();
		model.addAttribute("user", accountService.getUser(id));
		return "account/profile";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user){
		accountService.updateUser(user);
		updateCurrentUserName(user.getName());
		return "redirect:/";
	}
	
	@ModelAttribute
	public void getUser(@RequestParam(value = "id",defaultValue = "-1") Long id, Model model){
		if(id != -1){
			model.addAttribute("user", accountService.getUser(id));
		}
	}
	
	
	/**
	 * 取出Shiro中的当前用户id
	 */
	private Long getCurrentUserId(){
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	/*
	 * 更新Shiro中当前用户的用户名
	 */
	private void updateCurrentUserName(String userName){
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		user.name = userName;
	}
}
