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
	
	public JSONObjects(ArrayList<T> list, String status) {
		this.list = list;
		this.status = status;
	}
	
	public JSONObjects(ArrayList<T> list, ArrayList<Author> authorList, ArrayList<Genre> genreList, String status) {
		this.list = list;
		this.authorList = authorList;
		this.genreList = genreList;
		this.status = status;
	}
}