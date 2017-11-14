package com.cooksys.second_assessment.exceptions;

public class InsufficentInformationException extends UDException {

	private static final long serialVersionUID = -3776316329990343449L;
	public String errorMessage = "Required information has not been provided! Please provide additional information!";
	public Integer errorCode = 400;
}
