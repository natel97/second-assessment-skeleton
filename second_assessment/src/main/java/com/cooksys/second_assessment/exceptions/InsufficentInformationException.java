package com.cooksys.second_assessment.exceptions;

public class InsufficentInformationException extends UDException {

	private static final long serialVersionUID = -3776316329990343449L;
	private String errorMessage = "Required information has not been provided! Please provide additional information!";
	private Integer errorCode = 400;
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	@Override
	public Integer getErrorCode() {
		return errorCode;
	}
	
}
