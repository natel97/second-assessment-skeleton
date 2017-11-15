package com.cooksys.second_assessment.Dto;

import java.sql.Timestamp;

public class UserDto {
	
	public UserDto() { }
	
	public UserDto(String username, Timestamp joined, ProfileDto profile) {
		this.username = username;
		this.joined = joined;
		this.profile = profile;
	}
	
	private String username;
	private Timestamp joined;
	private ProfileDto profile;
	
	public String getUsername() { return username; }
	public ProfileDto getProfile() { return profile; }
	public Timestamp getJoined() { return joined; }
	
	public void setProfile(ProfileDto profile) { this.profile = profile; }
	public void setUsername(String userName) { this.username = userName; }
	public void setJoined(Timestamp time) { this.joined = time; }

}
