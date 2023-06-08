// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 4.6.2023
// Description: database functions related to author

package model;

import java.sql.*;

public class AuthorDatabase {
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet authorResultSet;
	
	public AuthorDatabase() {
	}

	
	// select author from database
	public boolean getAuthor() {
		// select author data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Author\" ORDER BY \"Name\"";
			PreparedStatement st = db.prepareStatement(sqlStatement);

			authorResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select author data from database (end)
	}
	
	
	// get author resultset
	public ResultSet getAuthorResult() {
		return authorResultSet;
	}
	
	
	// insert author into database
	public boolean registerAuthor(Author author) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "INSERT INTO \"public\".\"Author\" (\"Name\", \"Nationality\", \"BirthDate\", \"Biography\", \"Link\") VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, author.getName());
			st.setString(2, author.getNationality());
			// setting null value for birthdate
			if (author.getBirthDate() == null) {
				st.setNull(3, Types.DATE);
			} else {
				st.setDate(3, Date.valueOf(author.getBirthDate().toString()));
			}
			st.setString(4, author.getBiography());
			st.setString(5, author.getLink());

			int rowsAffected = st.executeUpdate();
			
			db.close();
			
			if(rowsAffected == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		// insert data into database (end)
	}
}