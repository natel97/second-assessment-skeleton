package com.cooksys.second_assessment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.ProfileDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.entity.Credentials;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.InsufficentInformationException;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.repository.CredentialsRepository;
import com.cooksys.second_assessment.repository.ProfileRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class UsersService {
	
	private UsersRepository usersRepository;
	private ProfileRepository profileRepository;
	private CredentialsRepository credentialsRepository;
	private DtoMapper mapper;
	
	public UsersService(UsersRepository usersRepository, CredentialsRepository credentialsRepository, ProfileRepository profileRepository, DtoMapper mapper) {
		this.usersRepository = usersRepository;
		this.credentialsRepository = credentialsRepository;
		this.profileRepository = profileRepository;
		this.mapper = mapper;
	}

	public UserDto findUserByID(Integer id) {
		return mapper.toUserDto(usersRepository.getOne(id));
	}
	
	public List<UserDto> getAllUsers(){
		return usersRepository.findAll().stream().map(mapper::toUserDto).collect(Collectors.toList());
	}
	
	public UserDto findUserByUsername(String username) {
		return mapper.toUserDto(usersRepository.findUsersByUsername(username));
	}
	
	public void validateUser(CredentialsDto cred) throws PasswordMismatchException {
		if(cred.getPassword().equals(credentialsRepository.findCredentialsByUsername(cred.getUsername()).getPassword()))
			throw new PasswordMismatchException();
	}
	
	public List<UserDto> findExistingUsers(){
		return usersRepository.findUsersByDeleted(false).stream().map(mapper::toUserDto).collect(Collectors.toList());
	}
	
	public List<UserDto> getFollowers(String username){
		return usersRepository.findUsersByFollowing(usersRepository.findUsersByUsername(username)).stream().map(mapper::toUserDto).collect(Collectors.toList());
	}
	
	public List<UserDto> getFollowing(String username){
		return usersRepository.findUsersByUsername(username).getFollowing().stream().map(mapper::toUserDto).collect(Collectors.toList());
	}
	
	@Transactional
	public UserDto addUser(Credentials cred, User u) throws InsufficentInformationException {
		if(cred.getUsername() == null || cred.getPassword() == null || u.getProfile().getEmail() == null) {
			System.out.println("Username, password, or email not provided");
			throw new InsufficentInformationException();
		}
		else {
			System.out.println(u);
		profileRepository.save(u.getProfile());
		usersRepository.save(u);
		return mapper.toUserDto(u);
		}
	}
	@Transactional
	public void makeAFollowB(String A, String B) {
		usersRepository.findUsersByUsername(A).followUser(usersRepository.findUsersByUsername(B));
	}
	
	public void makeAUnfollowB(String A, String B) {
		usersRepository.findUsersByUsername(A).unfollowUser(usersRepository.findUsersByUsername(B));
	}

	@Transactional
	public UserDto deleteUser(String username) {
		User user = usersRepository.findUsersByUsername(username);
		user.deleteUser();
		usersRepository.save(user);
		return mapper.toUserDto(usersRepository.findUsersByUsername(username));
	}

	public UserDto updateUser(String username, CredentialsDto credentials, ProfileDto profile) throws PasswordMismatchException {
		validateUser(credentials);
		profileRepository.save(mapper.toProfile(profile));
		return mapper.toUserDto(usersRepository.findUsersByUsername(username));
	}
}
