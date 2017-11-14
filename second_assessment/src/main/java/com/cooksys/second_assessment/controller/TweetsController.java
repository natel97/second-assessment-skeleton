package com.cooksys.second_assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.service.TweetsService;

@RestController
@RequestMapping("tweets")
public class TweetsController {
	
	TweetsService tweetService;

	public TweetsController(TweetsService tweetService) {
		this.tweetService = tweetService;
	}
	
	@GetMapping()
	public List<Tweet> getAllTweets() {
		return tweetService.findAllTweets();		
	}
	
	@PostMapping()
	public Tweet addATweet(@RequestBody Tweet tweet) {
		tweetService.addTweet(tweet);
		return tweet;
	}
	
	@GetMapping("{id}")
	public Tweet getTweetByID(@PathVariable Integer id) {
		return tweetService.findById(id);
	}
	
	@DeleteMapping("{id}")
	public void deleteTweet(@PathVariable Integer id) {
		//Takes credentials; returns tweet
	}
	
	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Integer id){
		
	}
	
	@PostMapping("{id}/reply")
	public void reply(@PathVariable Integer id) {
		//Needs to parse for @Mentions and #ashtags
		
	}
	
	@PostMapping("{id}/repost")
	public void repost(@PathVariable Integer id) {
		//Takes credentials; returns new tweet
	}
	
	@GetMapping("{id}/tags")
	public void getHashTags(@PathVariable Integer id) {
		//Returns a list of hashtags!
		
	}
	@GetMapping("{id}/likes")
	public void getLikes(@PathVariable Integer id) {
		//Return a list of users who like it!
	}
	
	@GetMapping("{id}/context")
	public void getContext(@PathVariable Integer id) {
		
	}
	
	@GetMapping("{id}/replies")
	public void getReplies(@PathVariable Integer id) {
		
	}
	
	@GetMapping("{id}/reposts")
	public void getReposts(@PathVariable Integer id) {
		
	}
	@GetMapping("{id}/mentions")
	public void getMentions(@PathVariable Integer id) {
		
	}
	
}
