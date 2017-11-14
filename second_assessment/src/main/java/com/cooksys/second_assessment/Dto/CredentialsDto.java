package com.cooksys.second_assessment.Dto;

public class CredentialsDto {
	private String username;
	private String password;
	
	
	public CredentialsDto() { }
	public CredentialsDto(String userName, String password) {
		this.username = userName;
		this.password = password;
	}
	
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
}
