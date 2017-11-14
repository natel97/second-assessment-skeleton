package com.cooksys.second_assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	
	public User findUsersByUsername(String username);
	public List<User> findUsersByDeleted(boolean deleted);
}
