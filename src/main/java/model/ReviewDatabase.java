// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date		: 15.6.2023
// Description	: database functions related to review

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewDatabase {
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet reviewResultSet = null;

	public ReviewDatabase() {
	}

	// select review from database
	public boolean getPendingReview() {
		// select review data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Review\" WHERE \"Status\" != 'approved'";
			PreparedStatement st = db.prepareStatement(sqlStatement);

			reviewResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select review data from database (end)
	}

	// set resultset to null
	public void clearReviewResult() {
		this.reviewResultSet = null;
	}

	// get author resultset
	public ResultSet getReviewResult() {
		return reviewResultSet;
	}
	
	// approve review (change status)
	
	
	// deny review (change status)
}