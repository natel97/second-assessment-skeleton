package com.cooksys.second_assessment.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.deconstructors.NewTweetDeconstructor;
import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.NotFoundException;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.repository.HashtagRepository;
import com.cooksys.second_assessment.repository.TweetsRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class TweetsService {

	private TweetsRepository tweetsRepository;
	private DtoMapper mapper;
	private UsersRepository userRepository;
	private HashtagRepository hashtagRepository;

	public TweetsService(TweetsRepository tweetRepo, DtoMapper mapper, UsersRepository userRepository,
			HashtagRepository hashtagRepository) {
		this.tweetsRepository = tweetRepo;
		this.mapper = mapper;
		this.userRepository = userRepository;
		this.hashtagRepository = hashtagRepository;
	}

	public TweetDto findById(Integer id) throws NotFoundException {
		try {
			Tweet tweet = tweetsRepository.getOne(id);
			System.out.println(tweet);
			if (tweet == null)
				throw new NotFoundException();
			if (tweet.isDeleted())
				throw new NotFoundException();
			return mapper.toTweetDto(tweet);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<TweetDto> findAllTweets() {
		return tweetsRepository.findAll().stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}

	public List<TweetDto> findAvailableTweets() {
		return tweetsRepository.findByDeleted(false).stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}

	@Transactional
	public TweetDto addTweet(NewTweetDeconstructor newTweetDeconstructor) {
		Tweet thisTweet = new Tweet(userRepository.findUsersByUsername((newTweetDeconstructor.getCred().getUsername())),
				newTweetDeconstructor.getContent(), null, null);
		tweetsRepository.save(thisTweet);
		thisTweet = tweetsRepository.findOne(thisTweet.getId());
		search(thisTweet);
		return mapper.toTweetDto(thisTweet);
	}

	public List<TweetDto> findTweetsByHashtag(Hashtag hashtag) {
		return tweetsRepository.findByHashtags(hashtag).stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}

	@Transactional
	private void search(Tweet tweet) {
		Matcher atMatcher = Pattern.compile("@\\w+ ").matcher(tweet.getContent());
		Matcher hashtagMatcher = Pattern.compile("#\\w+ ").matcher(tweet.getContent());
		while (atMatcher.find()) {
			User currentMention = userRepository
					.findUsersByUsername(atMatcher.group().substring(1, atMatcher.group().length() - 1));
			if (currentMention != null)
				tweet.addMention(currentMention);
		}
		while (hashtagMatcher.find()) {
			Hashtag currentHashtag = hashtagRepository
					.findByLabel(hashtagMatcher.group().substring(1, hashtagMatcher.group().length() - 1));

			if (currentHashtag == null) {
				currentHashtag = new Hashtag(hashtagMatcher.group().substring(1, hashtagMatcher.group().length() - 1));
				hashtagRepository.save(currentHashtag);
			} else
				currentHashtag.updateLastUsed();
			System.out.println(tweet);
			tweet.getHashtags().add(currentHashtag);
		}
		System.out.println(tweet.getHashtags());
	}

	@Transactional
	public TweetDto deleteTweet(Integer id) {
		Tweet tweet = tweetsRepository.findOne(id);
		tweet.delete();
		tweetsRepository.save(tweet);
		return mapper.toTweetDto(tweet);
	}

	public List<Tweet> findTweetByAuthor(String username) throws NotFoundException {
		try {
			User u = userRepository.findUsersByUsername(username);
			return tweetsRepository.findByAuthor(u);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<HashtagDto> getHashtagsFromTweet(Integer id) throws NotFoundException {
		try {
			Tweet tweet = tweetsRepository.findOne(id);
			if(tweet.isDeleted())
				throw new NotFoundException();
			return tweet.getHashtags().stream().map(mapper::toHashtagDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

}
