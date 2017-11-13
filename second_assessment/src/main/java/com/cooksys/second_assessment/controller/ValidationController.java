package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("validate")
public class ValidationController {
	
	@GetMapping("tag/exists/{label}")
	public boolean tagExists(@RequestParam String label) {
		//TODO Implement this!
		return true;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean userExists(@RequestParam String username) {
		//TODO: Implement this!
		return true;
	}
	
	@GetMapping("username/available/@{username}")
	public boolean userAvailable(@RequestParam String username) {
		return !userExists(username);
	}

}
