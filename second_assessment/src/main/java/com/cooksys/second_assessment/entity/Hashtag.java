package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Hashtag {

	@Id
	@GeneratedValue
	private Integer id;
	private String label;
	private Timestamp firstUsed;
	private Timestamp lastUsed;
	
	@ManyToMany
	private List<Tweet> tweets;
	
	public Hashtag() { }
	
	public Hashtag(String label) {
		firstUsed =  Timestamp.from(Instant.now());
		lastUsed = firstUsed;
	}
	
	public void updateLastUsed() { lastUsed = Timestamp.from(Instant.now()); }
	
	public Integer getId() { return id; }
	public String getLabel() { return label; }
	public Timestamp getFirstUsed() { return firstUsed; }
	public Timestamp getLastUsed() { return lastUsed; }
	
	public void setId(Integer id) { this.id = id; }
	
	public void addTweet(Tweet t) {
		tweets.add(t);
	}

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
		Hashtag other = (Hashtag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
