package com.cooksys.second_assessment.deconstructors;

import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.ProfileDto;

public class UserDeconstructor {
	
	CredentialsDto credentials;
	ProfileDto profile;
	
	UserDeconstructor(){}
	
	UserDeconstructor(CredentialsDto credentials, ProfileDto profile){
		this.credentials = credentials;
		this.profile = profile;
	}

	public CredentialsDto getCredentials() {return credentials; }
	public ProfileDto getProfile() { return profile; }
	
	public void setCredentials(CredentialsDto cred) { this.credentials = cred; }
	public void setProfile(ProfileDto profile) { this.profile = profile; }
	
}
