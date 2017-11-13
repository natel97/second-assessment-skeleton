package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public boolean tagExists(@RequestParam String label) {
		//TODO Implement this!
		return true;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean userExists(@RequestParam String username) {
		return usersService.findUserByUsername(username) != null;
	}
	
	@GetMapping("username/available/@{username}")
	public boolean userAvailable(@RequestParam String username) {
		return !userExists(username);
	}

}
