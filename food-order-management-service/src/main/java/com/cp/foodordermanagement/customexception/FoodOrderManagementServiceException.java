package com.cp.foodordermanagement.customexception;

public class FoodOrderManagementServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public FoodOrderManagementServiceException() {
		super();
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public FoodOrderManagementServiceException(String errorCode, String meessage) {

		super(meessage);
		this.errorCode = errorCode;

	}

}
