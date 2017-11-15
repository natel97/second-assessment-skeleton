package com.cooksys.second_assessment.exceptions;

public abstract class UDException extends Exception{

	private static final long serialVersionUID = 2165622220334494850L;

	public abstract String getErrorMessage();
	
	public abstract Integer getErrorCode();
	
}
