//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 16.6.2023
//Description : database functions relating to search functions

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchDatabase {
	// [DATABASE CONFIG]
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet searchResultSet;

	public SearchDatabase() {

	}

	//[SEARCH AUTHOR]
	public boolean searchAuthor(String searchValue) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			String sqlStatement="SELECT * FROM \"public\".\"Author\" WHERE Lower(\"Name\") LIKE '%?%'";
			PreparedStatement st=db.prepareStatement(sqlStatement);
			st.setString(1,searchValue);
			searchResultSet=st.executeQuery();
			
			db.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	//[SEARCH AUTHOR-END]
	
	//[SEARCH BOOK]
	public boolean searchBook(String searchValue) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			String sqlStatement="SELECT * FROM \"public\".\"Book\" WHERE Lower(\"Title\") LIKE '%?%'";
			PreparedStatement st=db.prepareStatement(sqlStatement);
			st.setString(1,searchValue);
			searchResultSet=st.executeQuery();
			
			db.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	//[SEARCH BOOK-END]
	
	//[RETURN RESULTSET]
	public ResultSet getSearchResultSet() {
		return searchResultSet;
	}
	//[RETURN RESULTSET-END]
}
