// Author		: Zay Yar Tun, Thu Htet San
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 27.7.2023
// Description	: status message collection

package model;

public class Status {
	
	// error status
	public static final String unauthorized = "unauthorized";
	public static final String invalidData = "invaliddata";
	public static final String invalidRequest = "invalidrequest";
	public static final String serverError = "servererror";
	public static final String duplicateData = "duplicate";
	public static final String maxProduct = "max";
	public static final String fail = "fail";
	public static final String duplicateEmail = "duplicateemail";
	
	
	
	// success status
	public static final String insertSuccess = "insertsuccess";
	public static final String updateSuccess = "updatesuccess";
	public static final String deleteSuccess = "deletesuccess";
	public static final String ok = "ok";
	
	
	// servlet status
	public static final String servletStatus = "servlet";
}