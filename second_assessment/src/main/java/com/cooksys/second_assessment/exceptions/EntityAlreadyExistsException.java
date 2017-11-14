package com.cooksys.second_assessment.exceptions;

public class EntityAlreadyExistsException extends UDException {

	private static final long serialVersionUID = 4621808545751381943L;

	public String errorMessage = "Username already exists in system! Please select a different username";
	public Integer errorCode = 403;
}
