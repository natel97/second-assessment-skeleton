package com.cooksys.second_assessment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Credentials {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	
	public Credentials() { }
	public Credentials(String userName, String password) {
		this.username = userName;
		this.password = password;
	}
	
	public Integer getId() { return id; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	
	public void setId(Integer id) {this.id = id;}
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
