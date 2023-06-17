// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 15.6.2023
// Description: to store review data from database 

package model;

import java.sql.Date;

public class Review {
	private int reviewID;
	private String title;
	private String description;
	private Date reviewDate;
	private short rating;
	private String ISBNNo;
	private int memberID;
	private String status;
	
	
	public Review(int reviewID, String title, String description, Date reviewDate, short rating, String iSBNNo,
			int memberID, String status) {
		super();
		this.reviewID = reviewID;
		this.title = title;
		this.description = description;
		this.reviewDate = reviewDate;
		this.rating = rating;
		ISBNNo = iSBNNo;
		this.memberID = memberID;
		this.status = status;
	}


	public int getReviewID() {
		return reviewID;
	}


	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getReviewDate() {
		return reviewDate;
	}


	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}


	public short getRating() {
		return rating;
	}


	public void setRating(short rating) {
		this.rating = rating;
	}


	public String getISBNNo() {
		return ISBNNo;
	}


	public void setISBNNo(String iSBNNo) {
		ISBNNo = iSBNNo;
	}


	public int getMemberID() {
		return memberID;
	}


	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}