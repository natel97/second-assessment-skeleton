package com.cooksys.second_assessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.cooksys.second_assessment.entity.Credentials;
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
	public UserDto addUser(@RequestBody UserDeconstructor userDeconstructor, HttpServletResponse httpResponse) {
		try {
			if (usersService.findUserByUsername(userDeconstructor.getCred().getUsername()) != null)
				throw new EntityAlreadyExistsException();
			credentialsService.addCredentials(userDeconstructor.getCred());
			return usersService.addUser(userDeconstructor.getCred(),
					new User(userDeconstructor.getProfile(), userDeconstructor.getCred().getUsername()));
		} catch (UDException e) {
			httpResponse.setStatus(e.errorCode);
			System.out.println(e.errorMessage);
		}
		return null;
	}

	@GetMapping("/@{username}")
	public UserDto getUser(@PathVariable String username) {
		return usersService.findUserByUsername(username);
	}

	@PatchMapping("@{username}")
	public UserDto updateUser(HttpServletResponse httpResponse, @PathVariable String username,
			@RequestBody CredentialsDto credentials, @RequestBody ProfileDto profile)
			throws PasswordMismatchException, InsufficentInformationException {
		try {
			if (!username.equals(credentials.getUsername()))
				throw new PasswordMismatchException();
			return usersService.updateUser(username, credentials, profile);
		} catch (UDException e) {
			httpResponse.setStatus(e.errorCode);
			return null;
		}
	}

	@DeleteMapping("@{username}")
	public UserDto deleteUser(HttpServletResponse httpResponse, @PathVariable String username,
			@RequestBody CredentialsDto cred) throws PasswordMismatchException, InsufficentInformationException {
		try {
			System.out.println(cred);
			if (!username.equals(cred.getUsername()))
				throw new PasswordMismatchException();
			else {
				usersService.validateUser(cred);
				return usersService.deleteUser(username);
			}
		} catch (UDException e) {
			httpResponse.setStatus(e.errorCode);
		}
		return null;
	}

	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String userToFollow, @RequestBody CredentialsDto cred, HttpServletResponse resp) {
		try {
			usersService.validateUser(cred);
			usersService.makeAFollowB(cred.getUsername(),userToFollow);
		} catch (UDException e) {
			resp.setStatus(e.errorCode);
			e.printStackTrace();
		}
	}

	@PostMapping("@{username}/unfollow")
	public void unfollowUser(@PathVariable String userToUnfollow, @RequestBody CredentialsDto cred, HttpServletResponse resp) {
		try {
			usersService.validateUser(cred);
			usersService.makeAUnfollowB(cred.getUsername(),userToUnfollow);
		} catch (UDException e) {
			resp.setStatus(e.errorCode);
			e.printStackTrace();
		}	
		
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
	public List<UserDto> getFollowers(@PathVariable String username) {
		return usersService.getFollowers(username);
	}

	@GetMapping("@{username}/following")
	public List<UserDto> getFollowing(@PathVariable String username) {
		return usersService.getFollowing(username);
	}

}
