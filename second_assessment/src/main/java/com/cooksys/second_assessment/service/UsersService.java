package com.cooksys.second_assessment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.ProfileDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.entity.Credential;
import com.cooksys.second_assessment.entity.Profile;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.entity.User;
import com.cooksys.second_assessment.exceptions.InsufficentInformationException;
import com.cooksys.second_assessment.exceptions.NotFoundException;
import com.cooksys.second_assessment.exceptions.PasswordMismatchException;
import com.cooksys.second_assessment.exceptions.UDException;
import com.cooksys.second_assessment.mapper.DtoMapper;
import com.cooksys.second_assessment.repository.CredentialsRepository;
import com.cooksys.second_assessment.repository.ProfileRepository;
import com.cooksys.second_assessment.repository.TweetsRepository;
import com.cooksys.second_assessment.repository.UsersRepository;

@Service
public class UsersService {

	private UsersRepository usersRepository;
	private ProfileRepository profileRepository;
	private CredentialsRepository credentialsRepository;
	private DtoMapper mapper;
	private TweetsRepository tweetsRepository;

	public UsersService(UsersRepository usersRepository, CredentialsRepository credentialsRepository,
			ProfileRepository profileRepository, DtoMapper mapper, TweetsRepository tweetsRepository) {
		this.usersRepository = usersRepository;
		this.credentialsRepository = credentialsRepository;
		this.profileRepository = profileRepository;
		this.mapper = mapper;
		this.tweetsRepository = tweetsRepository;
	}

	public UserDto findUserByID(Integer id) {
		return mapper.toUserDto(usersRepository.getOne(id));
	}

	public List<UserDto> getAllUsers() {
		return usersRepository.findAll().stream().filter(x -> x.isDeleted() == false).map(mapper::toUserDto)
				.collect(Collectors.toList());
	}

	public UserDto findUserByUsername(String username) throws UDException {
		try {
			User u = usersRepository.findUsersByUsername(username);
			return u.isDeleted() ? null : mapper.toUserDto(usersRepository.findUsersByUsername(username));
		} catch (Exception e) {
			throw new NotFoundException();
		}

	}

	public boolean usernameTaken(String username) {
		return usersRepository.findUsersByUsername(username) != null;
	}

	public void validateUser(CredentialsDto cred) throws UDException {
		if (cred == null)
			throw new InsufficentInformationException();
		if (cred.getPassword() == null || cred.getPassword() == null)
			throw new InsufficentInformationException();
		System.out.println(credentialsRepository.findCredentialByUsername(cred.getUsername().toLowerCase()));
		boolean matches;
		try {
			String a = credentialsRepository.findCredentialByUsername(cred.getUsername()).getPassword();
			String b = cred.getPassword();
			matches = a.equals(b);
		} catch (Exception e) {
			throw new NotFoundException();
		}
		if (!matches)
			throw new PasswordMismatchException();
	}

	public List<UserDto> findExistingUsers() {
		return usersRepository.findUsersByDeleted(false).stream().map(mapper::toUserDto).collect(Collectors.toList());
	}

	public List<UserDto> getFollowers(String username) throws UDException {
		if (usersRepository.findUsersByUsername(username) == null)
			throw new NotFoundException();
		return usersRepository.findUsersByFollowing(usersRepository.findUsersByUsername(username)).stream()
				.map(mapper::toUserDto).collect(Collectors.toList());
	}

	public List<UserDto> getFollowing(String username) throws UDException {
		if (usersRepository.findUsersByUsername(username) == null)
			throw new NotFoundException();
		return usersRepository.findUsersByUsername(username).getFollowing().stream().map(mapper::toUserDto)
				.collect(Collectors.toList());
	}

	@Transactional
	public UserDto addUser(CredentialsDto cred, UserDto u) throws UDException {
		if (cred.getUsername() == null || cred.getPassword() == null || u.getProfile().getEmail() == null) {
			System.out.println("Username, password, or email not provided");
			throw new InsufficentInformationException();
		}
		System.out.println(u);
		Credential c = mapper.toCredentials(cred);
		c.setUsername(c.getUsername().toLowerCase());
		credentialsRepository.save(c);
		User user = mapper.toUser(u);
		u.setUsername(u.getUsername().toLowerCase());
		profileRepository.save(user.getProfile());
		usersRepository.save(user);
		return mapper.toUserDto(user);
	}

	@Transactional
	public void makeAFollowB(String A, String B) throws UDException {
		if (usersRepository.findUsersByUsername(A) == null || usersRepository.findUsersByUsername(B) == null)
			throw new NotFoundException();
		usersRepository.save(usersRepository.findUsersByUsername(A).followUser(usersRepository.findUsersByUsername(B)));
	}

	public void makeAUnfollowB(String A, String B) throws UDException {
		if (usersRepository.findUsersByUsername(A) == null || usersRepository.findUsersByUsername(B) == null)
			throw new NotFoundException();
		usersRepository.findUsersByUsername(A).unfollowUser(usersRepository.findUsersByUsername(B));
	}

	@Transactional
	public UserDto deleteUser(String username) {
		User user = usersRepository.findUsersByUsername(username);
		user.deleteUser();
		usersRepository.save(user);
		return mapper.toUserDto(usersRepository.findUsersByUsername(username));
	}

	public UserDto updateUser(String username, CredentialsDto credentials, ProfileDto profile) throws UDException {
		validateUser(credentials);
		User userToUpdate = usersRepository.findUsersByUsername(username);
		Profile currentProfile = userToUpdate.getProfile();
		if (profile.getEmail() != null)
			currentProfile.setEmail(profile.getEmail());
		if (profile.getFirstName() != null)
			currentProfile.setFirstName(profile.getFirstName());
		if (profile.getPhone() != null)
			currentProfile.setPhone(profile.getPhone());
		if (profile.getLastName() != null)
			currentProfile.setLastName(profile.getLastName());
		profileRepository.save(currentProfile);
		return mapper.toUserDto(userToUpdate);
	}

	@Transactional
	public void addLike(Integer id, String username) throws UDException {
		try {
			Tweet tweet = tweetsRepository.getOne(id);
			tweet.addLike(usersRepository.findUsersByUsername(username));
			tweetsRepository.save(tweet);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public List<TweetDto> getUserFeed(String username) throws UDException {
		try {
			User user = usersRepository.findUsersByUsername(username);
			List<Tweet> tweets = new ArrayList<>();
			user.getFollowing().stream().filter(x -> !x.isDeleted())
					.forEach(x -> tweetsRepository.findByAuthor(x).forEach(y -> tweets.add(y)));
			System.out.println(tweets);
			tweets.addAll(tweetsRepository.findByAuthor(user));
			tweets.forEach(x -> Stream.concat(x.getContext().getBefore().stream(), x.getContext().getAfter().stream())
					.forEach(z -> tweets.add(z)));
			System.out.println(tweets);
			return tweets.stream().filter(x -> !x.isDeleted()).collect(Collectors.toSet()).stream().sorted((x, y) -> y.getPosted().compareTo(x.getPosted()))
					.map(mapper::toTweetDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}
}
