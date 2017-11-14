package com.cooksys.second_assessment.Dto;

import java.sql.Timestamp;

import com.cooksys.second_assessment.entity.Profile;

public class UserDto {
	
	public UserDto() { }
	
	public UserDto(String username, Timestamp joined, Profile profile) {
		this.username = username;
		this.joined = joined;
		this.profile = profile;
	}
	
	private String username;
	private Timestamp joined;
	private Profile profile;
	
	public String getUsername() { return username; }
	public Profile getProfile() { return profile; }
	public Timestamp getJoined() { return joined; }
	
	public void setProfile(Profile profile) { this.profile = profile; }
	public void setUsername(String userName) { this.username = userName; }
	public void setJoined(Timestamp time) { this.joined = time; }

}
