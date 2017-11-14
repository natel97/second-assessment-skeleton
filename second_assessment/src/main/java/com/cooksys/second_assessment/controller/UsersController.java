package com.cooksys.second_assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("users")
public class UsersController {

	UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}


	@GetMapping
	public List<User> displayAllUsers() {
		return usersService.getAllUsers();
	}

	@PostMapping()
	public User addUser(@RequestBody User user) {
		return usersService.addUser(user);
	}

	@GetMapping("/@{username}")
	public User getUser(@PathVariable String username) {
		return usersService.findUserByUsername(username);
	}

	@PatchMapping("@{username}")
	public void updateUser(@PathVariable String username) {
		// TODO: Update a user here! Should return the updated user or an error
		// Should take in a credentials object and profile object
		System.out.println(username + " should be updated here if he/she exists");
	}

	@DeleteMapping("@{username}")
	public void deleteUser(@PathVariable String username) {
		// TODO: This should take in a credentials object and return the deleted user
		// NOTICE: Should not delete; only set flag that user should not be accessible

	}

	@PostMapping("@{username}/follow")
	public void followUser() {
		// TODO: Take a Credentials object and subscribe a user to tweets
	}

	@PostMapping("@{username}/unfollow")
	public void unfollowUser() {
		// TODO: Take a Credentials object and unsubscribe a user to tweets
	}

	@GetMapping("@{username}/feed")
	public String getUserFeed(@PathVariable String username) {
		// TODO: Get all authorized tweets, reposts, and replies in chronological order
		return username + "'s feed!";
	}

	@GetMapping("@{username}/mentions")
	public void getMentions(@PathVariable String username) {

	}

	@GetMapping("@{username}/followers")
	public void getFollowers(@PathVariable String username) {

	}

	@GetMapping("@{username}/following")
	public void getFollowing(@PathVariable String username) {

	}

}
