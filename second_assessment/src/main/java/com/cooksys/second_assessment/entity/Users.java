package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Users {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Profile profile;
	
	private String username;
	private Timestamp joined;
	
	@OneToMany
	private List<Tweet> tweets;
	
	
	public Users() {	
		this.joined = Timestamp.from(Instant.now());
	}
	
	public Users(String username, Profile profile, Timestamp joined) { 
		this.username = username;
		this.profile = profile;
		this.joined = joined;
	}
	
	
	public Integer getId() { return id; }
	public String getUsername() { return username; }
	public Profile getProfile() { return profile; }
	public Timestamp getJoined() { return joined; }
	public List<Tweet> getTweets() { return tweets; }
	
	public void setId(Integer id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	public void setProfile(Profile profile) { this.profile = profile; }
	public void setJoined(Timestamp joined) { this.joined = joined; }
	public void setTweets(List<Tweet> tweets) { this.tweets = tweets; }

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
		Users other = (Users) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}