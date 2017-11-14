package com.cooksys.second_assessment.deconstructors;

import com.cooksys.second_assessment.entity.Credentials;
import com.cooksys.second_assessment.entity.Profile;

public class UserDeconstructor {
	
	Credentials cred;
	Profile profile;
	
	UserDeconstructor(){}
	
	UserDeconstructor(Credentials cred, Profile profile){
		this.cred = cred;
		this.profile = profile;
	}

	public Credentials getCred() {return cred; }
	public Profile getProfile() { return profile; }
	
	public void setCred(Credentials cred) { this.cred = cred; }
	public void setProfile(Profile profile) { this.profile = profile; }
	
}
