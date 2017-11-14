package com.cooksys.second_assessment.exceptions;

public class NotFoundException extends UDException{

	private static final long serialVersionUID = -3947234280613880138L;

	public String errorMessage = "The information you requested is unavailable!";
	public Integer errorCode = 404;
}
