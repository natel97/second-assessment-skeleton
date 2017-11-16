package com.cooksys.second_assessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second_assessment.Dto.ContextDto;
import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.deconstructors.NewTweetDeconstructor;
import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.service.TweetsService;
import com.cooksys.second_assessment.service.UsersService;

@RestController
@RequestMapping("tweets")
public class TweetsController {

	TweetsService tweetService;
	UsersService userService;

	public TweetsController(TweetsService tweetService, UsersService usersService) {
		this.tweetService = tweetService;
		this.userService = usersService;
	}

	@GetMapping()
	public List<TweetDto> getAllTweets() {
		return tweetService.findAllTweets(false);
	}

	@PostMapping()
	public TweetDto addATweet(@RequestBody NewTweetDeconstructor newTweetDeconstructor, HttpServletResponse response) {
		try {
			userService.validateUser(newTweetDeconstructor.getCred());
			return tweetService.addTweet(newTweetDeconstructor);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;

	}

	@GetMapping("{id}")
	public TweetDto getTweetByID(@PathVariable Integer id, HttpServletResponse response) {
		try {
			return tweetService.findById(id, false);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}

	@DeleteMapping("{id}")
	public TweetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentials,
			HttpServletResponse response) {
		try {
			userService.validateUser(credentials);
			return tweetService.deleteTweet(id);

		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentials,
			HttpServletResponse response) {
		try {
			userService.validateUser(credentials);
			userService.addLike(id, credentials.getUsername());
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
	}

	@PostMapping("{id}/reply")
	public TweetDto reply(@PathVariable Integer id, @RequestBody NewTweetDeconstructor newTweetDeconstructor,
			HttpServletResponse response) {
		try {
			return tweetService.addReply(id, newTweetDeconstructor.getContent(), newTweetDeconstructor.getCred());
		} catch (UDException e) {
			response.setStatus(404);
		}
		return null;
	}

	@PostMapping("{id}/repost")
	public TweetDto repost(@PathVariable Integer id, @RequestBody CredentialsDto credentials,
			HttpServletResponse response) {
		return tweetService.repostTweet(id, credentials.getUsername());
	}

	@GetMapping("{id}/tags")
	public List<HashtagDto> getHashTags(@PathVariable Integer id, HttpServletResponse response) {
		try {
			return tweetService.getHashtagsFromTweet(id);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;

	}

	@GetMapping("{id}/likes")
	public List<UserDto> getLikes(@PathVariable Integer id, HttpServletResponse response) {
		try {
			return tweetService.getLikes(id);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

	@GetMapping("{id}/context")
	public ContextDto getContext(@PathVariable Integer id, HttpServletResponse response) {
		try {
			return tweetService.getContextFromTweet(id);
		} catch (Exception e) {
			response.setStatus(404);
		}
		return null;
	}

	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable Integer id) {
		return tweetService.getReplies(id);
	}

	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable Integer id) {
		return tweetService.getTweetReposts(id);
	}

	@GetMapping("{id}/mentions")
	public List<UserDto> getMentions(@PathVariable Integer id, HttpServletResponse response) {
		try {
			return tweetService.getMentions(id);
		} catch (UDException e) {
			response.setStatus(e.getErrorCode());
		}
		return null;
	}

}
