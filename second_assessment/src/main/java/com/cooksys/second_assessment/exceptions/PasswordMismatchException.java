package com.cooksys.second_assessment.exceptions;

public class PasswordMismatchException extends UDException{

	private static final long serialVersionUID = -314385830455858168L;
	
	private String errorMessage = "The password given does not match the password in the system";
	private Integer errorCode = 403;
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	@Override
	public Integer getErrorCode() {
		return errorCode;
	}
	
}
