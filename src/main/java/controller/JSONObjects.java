// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: To test string with regular expressions

package controller;

import java.util.ArrayList;
import model.Author;
import model.Genre;

public class JSONObjects<T> {
	private ArrayList<T> list;
	private ArrayList<Author> authorList;
	private ArrayList<Genre> genreList;
	private String status;
	
	public JSONObjects(String status) {
		this.status = status;
	}
	
	public JSONObjects(ArrayList<T> list, String status) {
		this.list = list;
		this.status = status;
	}
	
	public JSONObjects(ArrayList<T> list, ArrayList<Author> authorList, String status) {
		this.list = list;
		this.authorList = authorList;
		this.status = status;
	}
	
	public JSONObjects(ArrayList<T> list, ArrayList<Author> authorList, ArrayList<Genre> genreList, String status) {
		this.list = list;
		this.authorList = authorList;
		this.genreList = genreList;
		this.status = status;
	}

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	public ArrayList<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(ArrayList<Author> authorList) {
		this.authorList = authorList;
	}

	public ArrayList<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(ArrayList<Genre> genreList) {
		this.genreList = genreList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}