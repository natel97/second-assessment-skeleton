package com.cooksys.second_assessment.deconstructors;

import com.cooksys.second_assessment.Dto.CredentialsDto;

public class NewTweetDeconstructor {

	public NewTweetDeconstructor() { }

	public NewTweetDeconstructor(String content, CredentialsDto credentials) {
		this.content = content;
		this.credentials = credentials;
	}
	private String content;
	private CredentialsDto credentials;
	
	public String getContent() { return content; }
	public CredentialsDto getCred() { return credentials; }
	
	public void setContent(String content) { this.content = content; }
	public void setCred(CredentialsDto cred) { this.credentials = cred; }
	
}
