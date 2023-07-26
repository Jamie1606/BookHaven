package model;

public class Status {
	
	// error status
	public static final String unauthorized = "unauthorized";
	public static final String invalidData = "invaliddata";
	public static final String invalidRequest = "invalidrequest";
	public static final String serverError = "servererror";
	public static final String duplicateData = "duplicate";
	
	
	// success status
	public static final String insertSuccess = "insertsuccess";
	public static final String updateSuccess = "updatesuccess";
	public static final String deleteSuccess = "deletesuccess";
	
	
	// servlet status
	public static final String servletStatus = "servlet";
}