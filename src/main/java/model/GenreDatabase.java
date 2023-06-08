<<<<<<< HEAD
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : database functions related to "Genre" TABLE

=======
>>>>>>> 0917a56a9936e9ed390e0e5ee7cf2c25d9fcae2c
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GenreDatabase {
<<<<<<< HEAD
	
	//[DATABASE CONFIG]
=======
>>>>>>> 0917a56a9936e9ed390e0e5ee7cf2c25d9fcae2c
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet genreResultSet;
<<<<<<< HEAD
	
	public GenreDatabase() {
		
	}
	
	//[GENRE DATA FROM DATABSE]
=======

	public GenreDatabase() {
	}

	// select genre from database
>>>>>>> 0917a56a9936e9ed390e0e5ee7cf2c25d9fcae2c
	public boolean getGenre() {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
<<<<<<< HEAD
			
			
			//select all from "Genre" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Genre\"";
=======

			String sqlStatement = "SELECT * FROM \"public\".\"Genre\" ORDER BY \"Genre\"";
>>>>>>> 0917a56a9936e9ed390e0e5ee7cf2c25d9fcae2c
			PreparedStatement st = db.prepareStatement(sqlStatement);

			genreResultSet = st.executeQuery();
			db.close();
<<<<<<< HEAD
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
=======
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// get genre resultset
	public ResultSet getGenreResult() {
		return genreResultSet;
	}
}
>>>>>>> 0917a56a9936e9ed390e0e5ee7cf2c25d9fcae2c
