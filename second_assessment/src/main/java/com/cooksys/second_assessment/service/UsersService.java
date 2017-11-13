package com.cooksys.second_assessment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.entity.Users;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class UsersService {
	
	private UsersRepository usersRepository;
	
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public Users findUserByID(Integer id) {
		return usersRepository.getOne(id);
	}
	
	public List<Users> getAllUsers(){
		return usersRepository.findAll();
	}
	
	public Users findUserByUsername(String username) {
		return usersRepository.findUserByUsername(username);
	}
	
	
	@Transactional
	public Users addUser(Users u) {
		usersRepository.save(u);
		return findUserByID(Integer.valueOf(String.valueOf(usersRepository.count())));
		
	}
}
