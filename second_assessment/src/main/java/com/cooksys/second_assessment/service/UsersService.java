package com.cooksys.second_assessment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Credentials;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class UsersService {
	
	private UsersRepository usersRepository;
	
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public User findUserByID(Integer id) {
		return usersRepository.getOne(id);
	}
	
	public List<User> getAllUsers(){
		return usersRepository.findAll();
	}
	
	public User findUserByUsername(String username) {
		return usersRepository.findUsersByCredentialsUsername(username);
	}
	
	public void validateUser(Credentials cred) throws PasswordMismatchException {
		if(!usersRepository.findUsersByCredentialsUsername(cred.getUsername()).getCredentials().getPassword().equals(cred.getPassword()));
	throw new PasswordMismatchException();
	}
	
	
	@Transactional
	public User addUser(User u) {
		usersRepository.save(u);
		return findUserByID(Integer.valueOf(String.valueOf(usersRepository.count())));
		
	}
}
