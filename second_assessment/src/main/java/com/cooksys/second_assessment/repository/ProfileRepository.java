package com.cooksys.second_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

	
}
