package com.cooksys.second_assessment.Dto;

public class ProfileDto {

	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	public ProfileDto() { }
	
	public ProfileDto(String firstName, String lastName, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}
	
	public String getFirstName() {return firstName; }
	public String getLastName() { return lastName; }
	public String getEmail() { return email; }
	public String getPhone() { return phone; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setEmail(String email) { this.email = email; }
	public void setPhone(String phone) { this.phone = phone; }
}
