package com.cooksys.second_assessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.service.HashtagService;
import com.cooksys.second_assessment.service.TweetsService;

@RestController
@RequestMapping("tags")
public class TagsController {

	private HashtagService hashtagService;
	private TweetsService tweetsService;
	private DtoMapper mapper;
	
	public TagsController(HashtagService hashtagService, TweetsService tweetsService, DtoMapper mapper) {
		this.hashtagService = hashtagService;
		this.tweetsService = tweetsService;
		this.mapper = mapper;
	}
	
	@GetMapping()
	public List<HashtagDto> getAllTags() {
		return hashtagService.getAllHashtags().stream().map(mapper::toHashtagDto).collect(Collectors.toList());
	}
	
	@GetMapping("{label}")
	public List<TweetDto> getTweetsByTag(@PathVariable String label) {
		return tweetsService.findTweetsByHashtag(hashtagService.findHashtagByLabel(label));
	}
	
}
