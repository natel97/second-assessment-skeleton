package com.cooksys.second_assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.TweetDto;

@RestController
@RequestMapping("tags")
public class TagsController {

	public TagsController() {
		
	}
	
	@GetMapping()
	public List<HashtagDto> getAllTags() {
		return null;
	}
	
	@GetMapping("{label}")
	public List<TweetDto> getTweetsByTag(@PathVariable String tag) {
		return null;
	}
	
}
