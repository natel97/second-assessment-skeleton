package com.cooksys.second_assessment.exceptions;

public class NotFoundException extends UDException{

	private static final long serialVersionUID = -3947234280613880138L;

	private String errorMessage = "The information you requested is unavailable!";
	private Integer errorCode = 404;
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	@Override
	public Integer getErrorCode() {
		return errorCode;
	}
}
