package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private Users author;
	private Timestamp posted;
	private String content;
	@OneToOne
	private Tweet inReplyTo;
	@OneToOne
	private Tweet repostOf;
	@ManyToMany
	private List<Hashtag> Hashtags;
	private boolean deleted;

	Tweet() {
		this.deleted = false;
	}

	Tweet(Users author, Timestamp posted, String content, Tweet inReplyTo, Tweet repostOf) {
		this.author = author;
		this.posted = posted;
		this.content = content;
		this.inReplyTo = inReplyTo;
		this.repostOf = repostOf;
		this.deleted = false;
		search(content);
	}

	private void search(String content) {
		Matcher atMatcher = Pattern.compile("@\\w+ ").matcher(content);
		Matcher hashtagMatcher = Pattern.compile("#\\w+ ").matcher(content);
		while(atMatcher.find()) {
			System.out.println(atMatcher.group());
		}
		while(hashtagMatcher.find()) {
			System.out.println(hashtagMatcher.group());
		}
	}

	public Integer getId() {
		return id;
	}

	public Users getAuthor() {
		return author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public String getContent() {
		return content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void delete() {
		this.deleted = true;
	}

	public void unDelete() {
		this.deleted = false;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public void setContent(String content) {
		search(content);
		this.content = content;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
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
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
