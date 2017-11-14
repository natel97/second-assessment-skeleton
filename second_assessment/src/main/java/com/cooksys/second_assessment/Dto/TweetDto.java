package com.cooksys.second_assessment.Dto;

import java.sql.Timestamp;

public class TweetDto {

	private Timestamp posted;
	private String content;
	
	public TweetDto(Timestamp posted, String content) {
		this.setPosted(posted);
		this.setContent(content);
	}

	public Timestamp getPosted() { return posted; }
	public String getContent() { return content; }
	
	public void setPosted(Timestamp posted) { this.posted = posted; }
	public void setContent(String content) { this.content = content; }
	
}
