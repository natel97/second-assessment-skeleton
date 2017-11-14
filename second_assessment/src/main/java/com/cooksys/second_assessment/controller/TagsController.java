package com.cooksys.second_assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.service.HashtagService;
import com.cooksys.second_assessment.service.TweetsService;

@RestController
@RequestMapping("tags")
public class TagsController {

	private HashtagService hashtagService;
	private TweetsService tweetsService;
	
	public TagsController(HashtagService hashtagService, TweetsService tweetsService) {
		this.hashtagService = hashtagService;
		this.tweetsService = tweetsService;
	}
	
	@GetMapping()
	public List<Hashtag> getAllTags() {
		return hashtagService.getAllHashtags();
	}
	
	@GetMapping("{label}")
	public List<Tweet> getTweetsByTag(@PathVariable String tag) {
		return tweetsService.findTweetsByHashtag(hashtagService.findHashtagByLabel(tag));
	}
	
}
