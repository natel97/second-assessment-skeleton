package com.cooksys.second_assessment.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	private Profile profile;
	
	private String username;
	private Date joined;
	
	@OneToMany
	private List<Tweet> tweets;
	
	
	public User() {	}
	
	public User(String username, Profile profile, Date joined) { 
		this.username = username;
		this.profile = profile;
		this.joined = joined;
	}
	
	
	public long getId() { return id; }
	public String getUsername() { return username; }
	public Profile getProfile() { return profile; }
	public Date getJoined() { return joined; }
	
	public void setId(long id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	public void setProfile(Profile profile) { this.profile = profile; }
	public void setJoined(Date joined) { this.joined = joined; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}