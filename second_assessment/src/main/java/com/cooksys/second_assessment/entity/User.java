package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity(name="users")
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Profile profile;
	
	private Timestamp joined;
	
	@OneToOne
	private Credentials credentials;
	
	@OneToMany
	private List<Tweet> tweets;
	
	
	public User() {	
		this.joined = Timestamp.from(Instant.now());
	}
	
	public User(String username, Profile profile, Timestamp joined) { 
		this.profile = profile;
		this.joined = joined;
	}
	
	
	public Integer getId() { return id; }
	public String getUsername() { return credentials.getUsername(); }
	public Profile getProfile() { return profile; }
	public Timestamp getJoined() { return joined; }
	public List<Tweet> getTweets() { return tweets; }
	public Credentials getCredentials() { return credentials; }
	
	public void setId(Integer id) { this.id = id; }
	public void setProfile(Profile profile) { this.profile = profile; }
	public void setCredentials(Credentials credentials) {this.credentials = credentials; }

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