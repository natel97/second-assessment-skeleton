package com.cooksys.second_assessment.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
	private User author;
	
	@ManyToMany
	private List<User> mentions;
	
	@ManyToMany
	private List<Hashtag> hashtags;
	
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	@OneToOne
	private Context context;

	@ManyToMany
	private List<User> likes;
	
	private Timestamp posted;
	private String content;
	private boolean deleted;
	

	public Tweet() {
		this.deleted = false;
		this.posted = Timestamp.from(Instant.now());
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", author=" + author + ", inReplyTo=" + inReplyTo + ", repostOf=" + repostOf
				+ ", mentions=" + mentions + ", hashtags=" + hashtags + ", posted=" + posted + ", content=" + content
				+ ", deleted=" + deleted + "]";
	}

	public Tweet(User author, String content, Tweet inReplyTo, Tweet repostOf) {
		this.author = author;
		this.posted = Timestamp.from(Instant.now());
		this.content = content;
		this.inReplyTo = inReplyTo;
		this.repostOf = repostOf;
		this.deleted = false;
		this.hashtags = new ArrayList<Hashtag>();
		this.mentions = new ArrayList<User>();
		this.likes = new ArrayList<User>();
	}


	public Integer getId() { return id; }
	public User getAuthor() { return author; }
	public Timestamp getPosted() { return posted; }
	public String getContent() { return content; }
	public List<User> getLikes() { return likes; }
	public List<User> getMentions(){ return mentions; }
	public List<Hashtag> getHashtags() { return hashtags; }
	public Tweet getInReplyTo() { return inReplyTo; }
	public Tweet getRepostOf() { return repostOf; }
	public boolean isDeleted() { return deleted; }
	public Context getContext() { return context; }
	public void setContext(Context c) { this.context = c; }
	
	public void setId(Integer id) { this.id = id; }
	public void setAuthor(User author) { this.author = author; }
	public void setPosted(Timestamp posted) { this.posted = posted; }
	public void setContent(String content) { this.content = content; }
	public void setMentions(List<User> mentions) { this.mentions = mentions; }
	public void setHashtags(List<Hashtag> hashtags) { this.hashtags = hashtags; }
	public void setInReplyTo(Tweet inReplyTo) { this.inReplyTo = inReplyTo; }
	public void setRepostOf(Tweet repostOf) { this.repostOf = repostOf; }
	public void addMention(User u) { mentions.add(u); }
	public void delete() { this.deleted = true; }
	public void unDelete() { this.deleted = false; }
	public void addTweetToAfter__Context(Tweet tweet) { this.context.addAfter(tweet); }
	public void setBefore__Context(Context context) { context.getBefore().stream().sorted((x, y) -> x.getPosted().compareTo(y.getPosted())).forEach(x -> this.context.addBefore(x)); }
	
	public void addLike(User u) { 
		if(!this.likes.contains(u))
			this.likes.add(u); 
		}
	
	public void removeLike(User u) { 
		if (this.likes.contains(u))
			likes.remove(u); 
		}
	
	public void clearHashtagsAndMentions() {
		mentions.clear();
		hashtags.clear();
	}
	
	public void addHashtag(Hashtag h) {
		System.out.println(hashtags);
		hashtags.add(h);
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
