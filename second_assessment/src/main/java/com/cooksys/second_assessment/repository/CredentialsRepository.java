package com.cooksys.second_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Credential;


public interface CredentialsRepository extends JpaRepository<Credential, Integer> {
	
	public Credential findCredentialByUsername(String username);

}
