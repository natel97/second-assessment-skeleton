package com.cooksys.second_assessment.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.Dto.ContextDto;
import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.deconstructors.NewTweetDeconstructor;
import com.cooksys.second_assessment.entity.Context;
import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.NotFoundException;
import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.repository.ContextRepository;
import com.cooksys.second_assessment.repository.HashtagRepository;
import com.cooksys.second_assessment.repository.TweetsRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class TweetsService {

	private TweetsRepository tweetsRepository;
	private DtoMapper mapper;
	private UsersRepository userRepository;
	private HashtagRepository hashtagRepository;
	private ContextRepository contextRepository;

	public TweetsService(TweetsRepository tweetRepo, DtoMapper mapper, UsersRepository userRepository, HashtagRepository hashtagRepository, ContextRepository contextRepository) {
		this.tweetsRepository = tweetRepo;
		this.mapper = mapper;
		this.userRepository = userRepository;
		this.contextRepository = contextRepository;
		this.hashtagRepository = hashtagRepository;
	}
	
	//Gets any tweet by its id. Can choose to include deleted tweets or not
	public TweetDto findById(Integer id, boolean includeDeletedTweets) throws UDException {
		try {
			Tweet tweet = tweetsRepository.getOne(id);
			System.out.println(tweet);
			if (tweet == null)
				throw new Exception();
			if (tweet.isDeleted() && !includeDeletedTweets)
				throw new Exception();
			return mapper.toTweetDto(tweet);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	//Gets all tweets and can or cannot include deleted tweets
	public List<TweetDto> findAllTweets(boolean includeDeletedTweets) {
		return tweetsRepository.findAll().stream().filter(x -> includeDeletedTweets ? true : !x.isDeleted()).map(mapper::toTweetDto).collect(Collectors.toList());
	}
	
	//Creates a new tweet
	@Transactional
	public TweetDto addTweet(NewTweetDeconstructor newTweetDeconstructor) {
		Tweet thisTweet = new Tweet(userRepository.findUsersByUsername((newTweetDeconstructor.getCred().getUsername())),
				newTweetDeconstructor.getContent(), null, null);
		Context context = new Context();
		context.setTarget(thisTweet);
		contextRepository.save(context);
		thisTweet.setContext(context);
		tweetsRepository.save(thisTweet);
		thisTweet = tweetsRepository.findOne(thisTweet.getId());
		search(thisTweet);
		return mapper.toTweetDto(thisTweet);
	}

	//Gets not deleted tweets by hashtag
	public List<TweetDto> findTweetsByHashtag(Hashtag hashtag) {
		return tweetsRepository.findByHashtags(hashtag).stream().filter(x -> !x.isDeleted()).map(mapper::toTweetDto).collect(Collectors.toList());
	}

	//Searches through a tweet to determine if there are hashtags or at symbols
	@Transactional
	private void search(Tweet tweet) {
		Matcher atMatcher = Pattern.compile("@\\w+").matcher(tweet.getContent());
		Matcher hashtagMatcher = Pattern.compile("#\\w+").matcher(tweet.getContent());
		while (atMatcher.find()) {
			User currentMention = userRepository
					.findUsersByUsername(atMatcher.group().substring(1));
			if (currentMention != null)
				tweet.addMention(currentMention);
		}
		while (hashtagMatcher.find()) {
			Hashtag currentHashtag = hashtagRepository
					.findByLabel(hashtagMatcher.group().substring(1));
			if (currentHashtag == null)
				currentHashtag = new Hashtag(hashtagMatcher.group().substring(1));
			else
				currentHashtag.updateLastUsed();
			hashtagRepository.save(currentHashtag);
			tweet.getHashtags().add(currentHashtag);
		}
	}

	@Transactional
	public TweetDto deleteTweet(Integer id) {
		Tweet tweet = tweetsRepository.findOne(id);
		tweet.delete();
		tweetsRepository.save(tweet);
		return mapper.toTweetDto(tweet);
	}

	public List<Tweet> findTweetByAuthor(String username) throws UDException {
		try {
			if(userRepository.findUsersByUsername(username) == null)
				throw new Exception();
			return tweetsRepository.findByAuthor(userRepository.findUsersByUsername(username));
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<HashtagDto> getHashtagsFromTweet(Integer id) throws UDException {
		try {
			Tweet tweet = tweetsRepository.findOne(id);
			if (tweet.isDeleted())
				throw new Exception();
			return tweet.getHashtags().stream().map(mapper::toHashtagDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<UserDto> getLikes(Integer id) throws UDException {
		try {
			return tweetsRepository.getOne(id).getLikes().stream().map(mapper::toUserDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<UserDto> getMentions(Integer id) throws UDException {
		try {
			if(tweetsRepository.getOne(id) == null)
				throw new Exception();
			return tweetsRepository.getOne(id).getMentions().stream().map(mapper::toUserDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<TweetDto> findTweetsByPersonMentioned(String username) throws UDException {
		if(userRepository.findUsersByUsername(username) == null)
			throw new NotFoundException();
		return tweetsRepository.findByMentions(userRepository.findUsersByUsername(username)).stream()
				.filter(x -> x.isDeleted() == false).map(mapper::toTweetDto).collect(Collectors.toList());
	}

	public List<TweetDto> getTweetReposts(Integer id) {
		return tweetsRepository.findByRepostOf(tweetsRepository.findOne(id)).stream()
				.filter(x -> x.isDeleted() == false).map(mapper::toTweetDto).collect(Collectors.toList());

	}

	@Transactional
	public TweetDto repostTweet(Integer id, String username) {
		Tweet repostable = tweetsRepository.getOne(id);
		User userRetweeted = userRepository.findUsersByUsername(username);
		Tweet tweet = new Tweet();
		Context context = new Context();
		contextRepository.save(context);
		tweet.setContext(context);
		tweet.setAuthor(userRetweeted);
		tweet.setContent(repostable.getContent());
		tweet.setRepostOf(repostable);
		repostable.getHashtags().forEach(x -> tweet.addHashtag(x));
		repostable.getMentions().forEach(x -> tweet.addMention(x));
		tweetsRepository.save(tweet);
		return mapper.toTweetDto(tweet);
	}

	@Transactional
	public TweetDto addReply(Integer id, String content, CredentialsDto credentials) throws UDException {
		Tweet origin = tweetsRepository.findOne(id);
		if (userRepository.findUsersByUsername(credentials.getUsername()) == null)
			throw new NotFoundException();
		Tweet replyTweet = new Tweet(userRepository.findUsersByUsername(credentials.getUsername()), content, origin,
				origin.getRepostOf());
		Context context = new Context();
		origin.getContext().getBefore().stream().forEach(x -> context.addBefore(x));
		context.addBefore(origin);
		contextRepository.save(context);
		replyTweet.setContext(context);
		tweetsRepository.save(replyTweet);
		origin.addTweetToAfter__Context(replyTweet);
		tweetsRepository.save(origin);
		return mapper.toTweetDto(replyTweet);
	}

	public ContextDto getContextFromTweet(Integer id) {
		return mapper.toContextDto(tweetsRepository.findOne(id).getContext());
	}

	public List<TweetDto> getReplies(Integer id) {
		return tweetsRepository.findByInReplyTo(tweetsRepository.findOne(id)).stream().map(mapper::toTweetDto)
				.collect(Collectors.toList());
	}
}
