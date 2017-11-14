package com.cooksys.second_assessment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.deconstructors.NewTweetDeconstructor;
import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.repository.TweetsRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class TweetsService {
	
	private TweetsRepository tweetsRepository;
	private DtoMapper mapper;
	private UsersRepository userRepository;
	
	public TweetsService(TweetsRepository tweetRepo, DtoMapper mapper, UsersRepository userRepository) {
		this.tweetsRepository = tweetRepo;
		this.mapper = mapper;
		this.userRepository = userRepository;
	}
	
	public TweetDto findById(Integer id) {
		return mapper.toTweetDto(tweetsRepository.getOne(id));
	}
	
	public List<TweetDto> findAllTweets(){
		return tweetsRepository.findAll().stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}
	
	public List<TweetDto> findAvailableTweets(){
		return tweetsRepository.findByDeleted(false).stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}
	
	@Transactional
	public TweetDto addTweet(NewTweetDeconstructor newTweetDeconstructor) {
		tweetsRepository.save(new Tweet(userRepository.findUsersByUsername((newTweetDeconstructor.getCred().getUsername())),newTweetDeconstructor.getContent(), null, null));
		return mapper.toTweetDto(tweetsRepository.findOne(Integer.valueOf(String.valueOf(tweetsRepository.count()))));
	}
	
	public List<TweetDto> findTweetsByHashtag(Hashtag hashtag){
		return tweetsRepository.findByHashtags(hashtag).stream().map(mapper::toTweetDto).collect(Collectors.toList());
	}
	

}
