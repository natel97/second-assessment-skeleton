package com.cooksys.second_assessment.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private User author;
	private Date posted;
	private String content;
	private Tweet inReplyTo;
	private Tweet repostOf;
	
	Tweet() { }
	
	Tweet(User author, Date posted, String content, Tweet inReplyTo, Tweet repostOf){
		this.author = author;
		this.posted = posted;
		this.content = content;
		this.inReplyTo = inReplyTo;
		this.repostOf = repostOf;
	}

	public Integer getId() { return id;	}
	public User getAuthor() { return author; }
	public Date getPosted() { return posted; }
	public String getContent() { return content; }
	public Tweet getInReplyTo() { return inReplyTo;	}	
	public Tweet getRepostOf() { return repostOf; }

	public void setId(Integer id) {	this.id = id; }
	public void setAuthor(User author) { this.author = author; }
	public void setPosted(Date posted) { this.posted = posted; }
	public void setContent(String content) { this.content = content; }
	public void setInReplyTo(Tweet inReplyTo) {	this.inReplyTo = inReplyTo;	}
	public void setRepostOf(Tweet repostOf) { this.repostOf = repostOf; } 		
}
