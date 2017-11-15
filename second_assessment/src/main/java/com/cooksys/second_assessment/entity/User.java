package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity(name="users")
public class User {
	
	@Override
	public String toString() {
		return "User [id=" + id + ", profile=" + profile + ", username=" + username + ", joined=" + joined
				+ ", following=" + following + ", tweets=" + tweets + ", deleted=" + deleted + "]";
	}

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Profile profile;
	private String username;
	private Timestamp joined;
	
	@ManyToMany
	private List<User> following;
	
	@OneToMany
	private List<Tweet> tweets;
	
	@ManyToMany 
	private List<Tweet> mentioned;
	private boolean deleted;
	
	public User() {	
		this.joined = Timestamp.from(Instant.now());
		this.deleted = false;
	}
	
	public User(Profile profile, String username) { 
		this.profile = profile;
		this.deleted = false;
		this.joined = Timestamp.from(Instant.now());
		this.username = username;
	}
	
	public void followUser(User u) { following.add(u); }
	public void unfollowUser(User u) { following.remove(u); }
	
	public Integer getId() { return id; }
	public String getUsername() { return username; }
	public Profile getProfile() { return profile; }
	public Timestamp getJoined() { return joined; }
	public List<Tweet> getTweets() { return tweets; }
	public boolean isDeleted() { return deleted; }
	public List<User> getFollowing() { return following; }
	
	public void setId(Integer id) { this.id = id; }
	public void setProfile(Profile profile) { this.profile = profile; }
	public void setUsername(String userName) { this.username = userName; }
	public void deleteUser() {this.deleted = true; }
	public void recoverUser() { this.deleted = false; }
	
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