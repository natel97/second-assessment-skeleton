package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tweets")
public class TweetsController {

	public TweetsController() {
		
	}
	
	@GetMapping()
	public void getAllTweets() {
		//TODO: Should return all tweets!
		
	}
	
	@PostMapping()
	public void addATweet() {
		//Takes a content and credentials; returns tweet
		
	}
	
	@GetMapping("{id}")
	public void getTweetByID(@RequestParam Integer id) {
		
	}
	
	@DeleteMapping("{id}")
	public void deleteTweet(@RequestParam Integer id) {
		//Takes credentials; returns tweet
	}
	
	@PostMapping("{id}/like")
	public void likeTweet(@RequestParam Integer id){
		
	}
	
	@PostMapping("{id}/reply")
	public void reply(@RequestParam Integer id) {
		//Needs to parse for @Mentions and #ashtags
		
	}
	
	@PostMapping("{id}/repost")
	public void repost(@RequestParam Integer id) {
		//Takes credentials; returns new tweet
	}
	
	@GetMapping("{id}/tags")
	public void getHashTags(@RequestParam Integer id) {
		//Returns a list of hashtags!
		
	}
	@GetMapping("{id}/likes")
	public void getLikes(@RequestParam Integer id) {
		//Return a list of users who like it!
	}
	
	@GetMapping("{id}/context")
	public void getContext(@RequestParam Integer id) {
		
	}
	
	@GetMapping("{id}/replies")
	public void getReplies(@RequestParam Integer id) {
		
	}
	
	@GetMapping("{id}/reposts")
	public void getReposts(@RequestParam Integer id) {
		
	}
	@GetMapping("{id}/mentions")
	public void getMentions(@RequestParam Integer id) {
		
	}
	
}
