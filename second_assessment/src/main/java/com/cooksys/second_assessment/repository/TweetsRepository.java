package com.cooksys.second_assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Tweet;

public interface TweetsRepository extends JpaRepository<Tweet, Integer> {
	
	List<Tweet> findByAuthorId(Integer id);
	
}
