package com.cooksys.second_assessment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.Dto.UserDto;

@RestController
@RequestMapping("users")
public class UsersController {
	
	//TODO: Add stuff for the initializer!

	public UsersController() {
		//TODO: create an initializer!
	}

	@GetMapping
	public String displayAllUsers() {
		// TODO: return a list of all active users
		return "Displaying all users!";
	}

	@PostMapping()
	public void addUser(@RequestBody UserDto user) {
		/* TODO: add a user to the list of users. Should return updated user
		 * Should take in a credentials object and profile object
		*/
		System.out.println("This will add a user in the future!");
	}

	@GetMapping("/@{username}")
	public String getUser(@RequestParam String username) {
		//TODO: get a user! Return a user
		return username;
	}

	@PatchMapping("@{username}")
	public void updateUser(@RequestParam String username) {
		//TODO: Update a user here! Should return the updated user or an error
		//Should take in a credentials object and profile object
		System.out.println(username + " should be updated here if he/she exists");
	}
	
	@DeleteMapping("@{username}")
	public void deleteUser(@RequestParam String username) {
		//TODO: This should take in a credentials object and return the deleted user
		//NOTICE: Should not delete; only set flag that user should not be accessible
	
	}
	
	@PostMapping("@{username}/follow")
	public void followUser() {
		//TODO: Take a Credentials object and subscribe a user to tweets
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollowUser() {
		//TODO: Take a Credentials object and unsubscribe a user to tweets
	}
	
	@GetMapping("@{username}/feed")
	public String getUserFeed(@RequestParam String username) {
		//TODO: Get all authorized tweets, reposts, and replies in chronological order
		return username + "'s feed!";
	}
	
	@GetMapping("@{username}/mentions")
	public void getMentions(@RequestParam String username) {
		
	}
	
	@GetMapping("@{username}/followers")
	public void getFollowers(@RequestParam String username) {
		
	}
	
	@GetMapping("@{username}/following")
	public void getFollowing(@RequestParam String username) {
		
	}
	
	
}
