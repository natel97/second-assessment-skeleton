package com.cooksys.second_assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.ProfileDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.deconstructors.UserDeconstructor;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.EntityAlreadyExistsException;
import com.cooksys.second_assessment.exceptions.InsufficentInformationException;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.service.CredentialsService;
import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("users")
public class UsersController {

	UsersService usersService;
	CredentialsService credentialsService;

	public UsersController(UsersService usersService, CredentialsService credService) {
		this.usersService = usersService;
		this.credentialsService = credService;
	}


	@GetMapping
	public List<UserDto> displayAllUsers() {
		return usersService.findExistingUsers();
	}

	@PostMapping()
	public UserDto addUser(@RequestBody UserDeconstructor userDeconstructor) {
		try {
		if(usersService.findUserByUsername(userDeconstructor.getCred().getUsername()) != null)
			throw new EntityAlreadyExistsException();
		credentialsService.addCredentials(userDeconstructor.getCred());
		return usersService.addUser(userDeconstructor.getCred(), new User(userDeconstructor.getProfile(), userDeconstructor.getCred().getUsername()));
		}
		catch(UDException e) {
			System.out.println(e.errorMessage);
		}
		return null;
	}

	@GetMapping("/@{username}")
	public UserDto getUser(@PathVariable String username) {
		return usersService.findUserByUsername(username);
	}

	@PatchMapping("@{username}")
	public UserDto updateUser(@PathVariable String username, @RequestBody CredentialsDto credentials, @RequestBody ProfileDto profile) throws PasswordMismatchException, InsufficentInformationException {
		if(!username.equals(credentials.getUsername()))
			throw new PasswordMismatchException();
		return usersService.updateUser(username, credentials, profile);
	}

	@DeleteMapping("@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto cred) throws PasswordMismatchException, InsufficentInformationException {
		// TODO: This should take in a credentials object and return the deleted user
		// NOTICE: Should not delete; only set flag that user should not be accessible
		if(!username.equals(cred.getUsername()))
			throw new PasswordMismatchException();
		usersService.validateUser(cred);
		return usersService.deleteUser(username);
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
