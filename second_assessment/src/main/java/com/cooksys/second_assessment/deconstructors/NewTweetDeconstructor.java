package com.cooksys.second_assessment.deconstructors;

import com.cooksys.second_assessment.entity.Credentials;

public class NewTweetDeconstructor {

	public NewTweetDeconstructor() { }

	public NewTweetDeconstructor(String content, Credentials cred) {
		this.content = content;
		this.cred = cred;
	}
	private String content;
	private Credentials cred;
	
	public String getContent() { return content; }
	public Credentials getCred() { return cred; }
	
	public void setContent(String content) { this.content = content; }
	public void setCred(Credentials cred) { this.cred = cred; }
	
	
}
