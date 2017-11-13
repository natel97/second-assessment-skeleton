package com.cooksys.second_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	public Users findUserByUsername(String username);

}
