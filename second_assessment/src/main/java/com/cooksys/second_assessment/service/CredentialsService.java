package com.cooksys.second_assessment.service;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Credentials;
import com.cooksys.second_assessment.repository.CredentialsRepository;

@Service
public class CredentialsService {

	private CredentialsRepository credentialsRepository;
	public CredentialsService(CredentialsRepository cRepo) {
		this.credentialsRepository = cRepo;
	}
	
	public void addCredentials(Credentials cred) {
		credentialsRepository.save(cred);
	}
}
