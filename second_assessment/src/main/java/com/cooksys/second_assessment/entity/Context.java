package com.cooksys.second_assessment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Context {
	@Id
	@GeneratedValue
	Integer id;
	
	@OneToOne
	private Tweet target;
	
	@ManyToMany
	private List<Tweet> before;
	
	@ManyToMany
	private List<Tweet> after;
	
	public Context() {
		this.before = new ArrayList<>();
		this.after = new ArrayList<>();
	}
	
	public Tweet getTarget() { return target; }
	public List<Tweet> getBefore() { return before; }
	public List<Tweet> getAfter() { return after; }
	public Integer getId() { return id; }
	
	public void setTarget(Tweet tweet) { this.target = tweet; }
	public void setBefore(List<Tweet> before) { this.before = before; }
	public void setAfter(List<Tweet> after) { this.after = after; }
	public void setId(Integer id) { this.id = id; }
	
	public void addBefore(Tweet tweet) { this.before.add(tweet); }
	public void addAfter(Tweet tweet) { this.after.add(tweet); }
}
