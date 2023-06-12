package controller;

import java.util.ArrayList;

public class JSONObjects<T> {
	private ArrayList<T> list;
	private String status;
	
	public JSONObjects(ArrayList<T> list, String status) {
		this.list = list;
		this.status = status;
	}
}