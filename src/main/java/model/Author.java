// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 11.7.2023
// Description: to store author data from database 

package model;

import java.sql.Date;

import jakarta.json.bind.annotation.JsonbDateFormat;

public class Author {
	private int authorID;
	private String name;
	private String nationality;
	
	@JsonbDateFormat(value = "yyyy-MM-dd")
	private Date birthDate;
	
	private String biography;
	private String link;

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
	
	public String formatNull(String value) {
		if(value == null) {
			return "";
		}
		else {
			return value;
		}
	}
}