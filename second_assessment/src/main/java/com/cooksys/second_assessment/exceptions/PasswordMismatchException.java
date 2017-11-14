package com.cooksys.second_assessment.exceptions;

public class PasswordMismatchException extends UDException{

	private static final long serialVersionUID = -314385830455858168L;
	
	public String errorMessage = "The password given does not match the password in the system";
	public Integer errorCode = 403;
}
