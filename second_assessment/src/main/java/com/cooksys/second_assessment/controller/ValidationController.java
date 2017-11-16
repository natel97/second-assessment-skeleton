package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.service.HashtagService;
import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("validate")
public class ValidationController {
	
	UsersService usersService;
	HashtagService hashtagService;
	
	ValidationController(UsersService usersService, HashtagService hashtagService){
		this.usersService = usersService;
		this.hashtagService = hashtagService;
	}
	
	@GetMapping("tag/exists/{label}")
	public boolean tagExists(@PathVariable String label) {
		return hashtagService.findHashtagByLabel(label) != null;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean userExists(@PathVariable String username) {
		try {
			return usersService.findUserByUsername(username) != null;
		} catch (UDException e) {
			return false;
		}
	}
	
	@GetMapping("username/available/@{username}")
	public boolean userAvailable(@PathVariable String username) {
		return !usersService.usernameTaken(username);
	}

}
