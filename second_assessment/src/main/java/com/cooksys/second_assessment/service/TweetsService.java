package com.cooksys.second_assessment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.repository.TweetsRepository;

@Service
public class TweetsService {
	
	private TweetsRepository tweetsRepository;
	
	public TweetsService(TweetsRepository tweetRepo) {
		this.tweetsRepository = tweetRepo;
	}
	
	public Tweet findById(Integer id) {
		return tweetsRepository.getOne(id);
	}
	
	public List<Tweet> findAllTweets(){
		return tweetsRepository.findAll();
	}
	
	public List<Tweet> findAvailableTweets(){
		return tweetsRepository.findByDeleted(false);
	}
	
	@Transactional
	public Integer addTweet(Tweet t) {
		tweetsRepository.save(t);
		return Integer.valueOf(String.valueOf(tweetsRepository.count()));
	}
	
	public List<Tweet> findTweetsByHashtag(Hashtag hashtag){
		return tweetsRepository.findByHashtags(hashtag);
	}
	

}
