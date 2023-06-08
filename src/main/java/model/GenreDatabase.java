//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : database functions related to "Genre" TABLE

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GenreDatabase {
	
	//[DATABASE CONFIG]
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet genreResultSet;
	
	public GenreDatabase() {
		
	}
	
	//[GENRE DATA FROM DATABSE]
	public boolean getGenre() {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			
			//select all from "Genre" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Genre\"";
			PreparedStatement st = db.prepareStatement(sqlStatement);

			genreResultSet = st.executeQuery();
			db.close();
			//successful
			return true;
		} catch (Exception e) {

			System.out.println("db error:");
			return false;
		}

	}// select genre from database (end)
	
	
	//[RETURN GENRE RESULTSET]
	public ResultSet getGenreResult() {
		return genreResultSet;
	}
}
