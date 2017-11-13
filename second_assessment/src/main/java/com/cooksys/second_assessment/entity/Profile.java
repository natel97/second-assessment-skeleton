package com.cooksys.second_assessment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	public Profile() { }
	
	public Profile(String firstName, String lastName, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}
	
	public String getFirstName() {return firstName; }
	public String getLastName() { return lastName; }
	public String getEmail() { return email; }
	public String getPhone() { return phone; }
	public int getId() { return id; }
	
	public void setId(int id) {	this.id = id; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setEmail(String email) { this.email = email; }
	public void setPhone(String phone) { this.phone = phone; }
	
	
}
