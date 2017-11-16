package com.cooksys.second_assessment.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.deconstructors.UserDeconstructor;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.EntityAlreadyExistsException;
import com.cooksys.second_assessment.exceptions.InsufficentInformationException;
import com.cooksys.second_assessment.exceptions.NotFoundException;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.service.CredentialsService;
import com.cooksys.second_assessment.service.TweetsService;
import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("users")
public class UsersController {

	UsersService usersService;
	CredentialsService credentialsService;
	DtoMapper mapper;
	TweetsService tweetsService;

	public UsersController(TweetsService tweetService, UsersService usersService, CredentialsService credService, DtoMapper mapper) {
		this.usersService = usersService;
		this.mapper = mapper;
		this.credentialsService = credService;
		this.tweetsService = tweetService;
	}

	@GetMapping
	public List<UserDto> displayAllUsers() {
		return usersService.findExistingUsers();
	}

	@PostMapping()
	public UserDto addUser(@RequestBody UserDeconstructor userDeconstructor, HttpServletResponse httpResponse) {
		if(userDeconstructor.getProfile() == null || userDeconstructor.getCredentials() == null) {
			httpResponse.setStatus(403);
			return null;
		}
		try {
			usersService.findUserByUsername(userDeconstructor.getCredentials().getUsername());
		}
		catch(UDException e) {
			try {
				return usersService.addUser(userDeconstructor.getCredentials(), mapper.toUserDto(new User(mapper.toProfile(userDeconstructor.getProfile()), userDeconstructor.getCredentials().getUsername())));
			} catch (UDException e1) {
				httpResponse.setStatus(e1.getErrorCode());
			}
		}
		httpResponse.setStatus(new EntityAlreadyExistsException().getErrorCode());
		return null;
	}

	@GetMapping("/@{username}")
	public UserDto getUser(@PathVariable String username, HttpServletResponse httpResponse) {
		UserDto toReturn = null;
		try {
		toReturn = usersService.findUserByUsername(username);
		if (toReturn == null)
				throw new NotFoundException();
			} catch (UDException e) {
				httpResponse.setStatus(e.getErrorCode());
				e.printStackTrace();
			}
		return toReturn;
	}

	@PatchMapping("@{username}")
	public UserDto updateUser(HttpServletResponse httpResponse, @PathVariable String username, @RequestBody UserDeconstructor userDeconstructor)
			throws PasswordMismatchException, InsufficentInformationException {
		if(userDeconstructor.getProfile() == null || userDeconstructor.getCredentials() == null) {
			httpResponse.setStatus(403);
			return null;
		}
		try {
			if (!username.equals(userDeconstructor.getCredentials().getUsername()))
				throw new PasswordMismatchException();
			return usersService.updateUser(username, userDeconstructor.getCredentials(), userDeconstructor.getProfile());
		} catch (UDException e) {
			httpResponse.setStatus(e.getErrorCode());
			return null;
		}
	}

	@DeleteMapping("@{username}")
	public UserDto deleteUser(HttpServletResponse httpResponse, @PathVariable String username,
			@RequestBody CredentialsDto cred) throws PasswordMismatchException, InsufficentInformationException {
		try {
			if (!username.equals(cred.getUsername()))
				throw new PasswordMismatchException();
			else {
				usersService.validateUser(cred);
				return usersService.deleteUser(username);
			}
		} catch (UDException e) {
			httpResponse.setStatus(e.getErrorCode());
		}
		return null;
	}

	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentials, HttpServletResponse httpResponse) {
		try {
			if(credentials.getUsername() == null || credentials.getPassword() == null)
				throw new NotFoundException();
			usersService.validateUser(credentials);
			usersService.makeAFollowB(credentials.getUsername(),username);
		} catch (UDException e) {
			httpResponse.setStatus(e.getErrorCode());
			e.printStackTrace();
		}
	}

	@PostMapping("@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentials, HttpServletResponse resp) {
		try {
			usersService.validateUser(credentials);
			usersService.makeAUnfollowB(credentials.getUsername(),username);
		} catch (UDException e) {
			resp.setStatus(e.getErrorCode());
			e.printStackTrace();
		}	
		
	}

	@GetMapping("@{username}/feed")
	public List<TweetDto> getUserFeed(@PathVariable String username, HttpServletResponse response) {
		try {
			return usersService.getUserFeed(username);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}
	
	@GetMapping("@{username}/tweets")
	public List<TweetDto> getUserTweets(String username, HttpServletResponse response){
		try {
			return tweetsService.findTweetByAuthor(username).stream().filter(x -> x.isDeleted() == false).map(mapper::toTweetDto).collect(Collectors.toList());
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

	@GetMapping("@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username, HttpServletResponse response) {
		try {
		return tweetsService.findTweetsByPersonMentioned(username);
		}
		catch(UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

	@GetMapping("@{username}/followers")
	public List<UserDto> getFollowers(@PathVariable String username, HttpServletResponse response) {
		try {
			return usersService.getFollowers(username);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

	@GetMapping("@{username}/following")
	public List<UserDto> getFollowing(@PathVariable String username, HttpServletResponse response) {
		try {
		return usersService.getFollowing(username);
		}
		catch(Exception e) {
			response.setStatus(404);
		}
		return null;
	}
}
