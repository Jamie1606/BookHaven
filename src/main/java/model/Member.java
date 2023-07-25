//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Group       : 10
//Date        : 7.6.2023
//Description : to store member data from database

package model;

import java.sql.Date;

public class Member {
	private int memberID;
	private String name;
	private char gender = 'N';
	private Date birthDate;
	private String phone;
	private String address;
	
	public Member() {
		
	}

	public Member(String name, char gender, Date birthDate, String phone, String address, String email, String image) {
		super();
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.image = image;
	}

	private String email;
	private String password;
	private String image;
	private Date lastActive;

	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Member(String name, String phone, String address, String email, String password, String image) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
		this.image = image;
	}

	public Member(String name, String phone, String address, String email, String password) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
	}

	public Member(int memberID, String name, char gender, Date birthDate, String phone, String address, String image) {
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.image = image;
	}

	public Member(int memberID, String name, char gender, Date birthDate, String phone, String address, String email,
			String image) {
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.image = image;
	}

	public Member(int memberID, String name, Date birthDate, String phone, String address, String password, char gender,
			String image) {
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.image = image;
	}

	public Member(int memberID, String name, char gender, Date birthDate, String phone, String address, String email,
			String password, String image) {
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
		this.image = image;
	}

	public Member(String name, char gender, Date birthDate, String phone, String address, String email, String password,
			String image) {
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
		this.image = image;
	}

	public Member(int memberID, String name, char gender, Date birthDate, String phone, String address, String email,
			String password, String image, Date lastActive) {
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
		this.image = image;
		this.lastActive = lastActive;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

}
