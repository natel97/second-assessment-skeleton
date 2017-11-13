package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {

	public TagsController() {
		
	}
	
	@GetMapping()
	public void getAllTags() {
		
	}
	
	@GetMapping("{label}")
	public void getTweetsByTag(@RequestParam String tag) {
		
	}
	
}
