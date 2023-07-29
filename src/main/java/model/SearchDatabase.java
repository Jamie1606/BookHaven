// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group       	: 10
// Date		  	: 16.6.2023
// Description 	: database functions relating to search functions

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


	
	//[SEARCH BOOK BY AUTHOR]
	public boolean searchBookByAuthor(String searchValue) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			String sqlStatement="SELECT b.\"ISBNNo\", b.\"Title\",b.\"Image\",b.\"Status\", a.\"AuthorID\", a.\"Name\"  FROM \"public\".\"Book\" AS b JOIN \"public\".\"BookAuthor\" AS ba ON b.\"ISBNNo\"=ba.\"ISBNNo\" JOIN \"public\".\"Author\" AS a ON ba.\"AuthorID\"=a.\"AuthorID\" WHERE Lower(a.\"Name\") LIKE ?";
			PreparedStatement st=db.prepareStatement(sqlStatement);
			String search_value="%"+searchValue.toLowerCase()+"%";
			st.setString(1,search_value);
			searchResultSet=st.executeQuery();
			
			db.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	//[SEARCH BOOK BY AUTHOR-END]
	
	//[SEARCH BOOK]
	public boolean searchBook(String searchValue) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			String sqlStatement="SELECT * FROM \"public\".\"Book\" WHERE Lower(\"Title\") LIKE ?";
			PreparedStatement st=db.prepareStatement(sqlStatement);
			String search_value="%"+searchValue.toLowerCase()+"%";
			st.setString(1,search_value);
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
