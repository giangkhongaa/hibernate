package com.giang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.giang.model.User;
import com.giang.service.UserService;

@Controller
public class AjaxController {

	@Autowired
	UserService userService;
	
	/**
	 * Performs search for information kind like (use Ajax)
	 * 
	 * @param ModelMap
	 * @param String
	 * 
	 * @return List<User>
	 */
	@RequestMapping(value = "/student/accountsearch/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public List<User> searchAccountsByUserName( @PathVariable("userName") String userName) {
		List<User> userList = userService.findUserListByUserNameService(userName);
		return userList;
	}
	
	@RequestMapping(value = "/student/update/user", method = RequestMethod.POST)
	@ResponseBody
	public User updateUser(@RequestBody User user) {
		userService.updateUserService(user);
		User userResult= userService.findUserByIdService(user.getId());
		return userResult;
	}
		
}
