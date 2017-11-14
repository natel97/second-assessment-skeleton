package com.cooksys.second_assessment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Credentials;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.InsufficentInformationException;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.repository.CredentialsRepository;
import com.cooksys.second_assessment.repository.ProfileRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class UsersService {
	
	private UsersRepository usersRepository;
	private ProfileRepository profileRepository;
	private CredentialsRepository credentialsRepository;
	
	public UsersService(UsersRepository usersRepository, CredentialsRepository credentialsRepository, ProfileRepository profileRepository) {
		this.usersRepository = usersRepository;
		this.credentialsRepository = credentialsRepository;
		this.profileRepository = profileRepository;
	}

	public User findUserByID(Integer id) {
		return usersRepository.getOne(id);
	}
	
	public List<User> getAllUsers(){
		return usersRepository.findAll();
	}
	
	public User findUserByUsername(String username) {
		return usersRepository.findUsersByUsername(username);
	}
	
	public void validateUser(Credentials cred) throws PasswordMismatchException {
		if(cred.getPassword().equals(credentialsRepository.findCredentialsByUsername(cred.getUsername()).getPassword()))
			throw new PasswordMismatchException();
	}
	
	public List<User> findExistingUsers(){
		return usersRepository.findUsersByDeleted(false);
	}
	
	
	@Transactional
	public User addUser(Credentials cred, User u) throws InsufficentInformationException {
		if(cred.getUsername() == null || cred.getPassword() == null || u.getProfile().getEmail() == null) {
			System.out.println("Username, password, or email not provided");
			throw new InsufficentInformationException();
		}
		
		profileRepository.save(u.getProfile());
		
		usersRepository.save(u);
		return findUserByID(Integer.valueOf(String.valueOf(usersRepository.count())));
		
	}
}
