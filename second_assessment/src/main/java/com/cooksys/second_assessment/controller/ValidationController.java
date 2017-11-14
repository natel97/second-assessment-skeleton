package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("validate")
public class ValidationController {
	
	UsersService usersService;
	
	ValidationController(UsersService usersService){
		this.usersService = usersService;
	}
	
	@GetMapping("tag/exists/{label}")
	public boolean tagExists(@PathVariable String label) {
		//TODO Implement this!
		return true;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean userExists(@PathVariable String username) {
		return usersService.findUserByUsername(username) != null;
	}
	
	@GetMapping("username/available/@{username}")
	public boolean userAvailable(@PathVariable String username) {
		return !userExists(username);
	}

}
