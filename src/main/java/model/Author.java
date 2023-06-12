// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 4.6.2023
// Description: to store author data from database 

package model;

import java.sql.Date;

public class Author {
	private int authorID;
	private String name;
	private String nationality;
	private Date birthDate;
	private String biography;
	private String link;
	
	public Author(int authorID) {
		this.authorID = authorID;
	}
	
	public Author(String name, String nationality, Date birthDate, String biography, String link) {
		this.name = name;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.biography = biography;
		this.link = link;
	}
	
	public Author(int authorID, String name, String nationality, Date birthDate, String biography, String link) {
		this.authorID = authorID;
		this.name = name;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.biography = biography;
		this.link = link;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}