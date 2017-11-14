package com.cooksys.second_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	public Hashtag findByLabel(String label);
	
}
