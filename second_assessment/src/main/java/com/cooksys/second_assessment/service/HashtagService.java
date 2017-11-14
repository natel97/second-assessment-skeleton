package com.cooksys.second_assessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.repository.HashtagRepository;

@Service
public class HashtagService {
	
	HashtagRepository hashtagRepository;
	
	public HashtagService(HashtagRepository hashtagRepository) {
		this.hashtagRepository = hashtagRepository;
	}
	
	public Hashtag getHashtagById(Integer id) {
		return hashtagRepository.findOne(id);
	}

	public List<Hashtag> getAllHashtags(){
		return hashtagRepository.findAll();
	}
	
	public Hashtag findHashtagByLabel(String label) {
		return hashtagRepository.findByLabel(label);
	}
}
