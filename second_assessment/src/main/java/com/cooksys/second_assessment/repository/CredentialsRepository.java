package com.cooksys.second_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second_assessment.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

}
