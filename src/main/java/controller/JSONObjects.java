// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: To test string with regular expressions

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