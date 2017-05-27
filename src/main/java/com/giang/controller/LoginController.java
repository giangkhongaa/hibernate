package com.giang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.giang.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/")
	public String login(ModelMap modelMap) {
		return "redirect:/student/login";
	}

	@RequestMapping(value = "/student/login", method = RequestMethod.GET)
	public String login(ModelMap modelMap, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		// case login failed
		if (error != null) {
			modelMap.addAttribute("message", "Invalid username and password!");
		}
		// case logout system
		if (logout != null) {
			modelMap.addAttribute("message", "You've been logged out successfully.");
		}

		// check either logged in or not logged in
		if (isLogged()) {
			return "redirect:/student/home";
		}

		return "login";
	}

	/**
	 * check  logged in or not logged in
	 * 
	 * @return boolean
	 *
	 */
	private boolean isLogged() {
		String userName = null;
		// get object principal 
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// check principal has instance UserDetails
		if (principal instanceof UserDetails) {
			// Get the username that is logged on to the system
			userName = ((UserDetails) principal).getUsername();

		}
		// check has userName
		if (userName != null) {
			return true;
		} else {
			return false;
		}

	}
}
