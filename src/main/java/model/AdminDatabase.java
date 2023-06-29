// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 8.6.2023
// Description: database functions related to admin

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDatabase {
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet adminResultSet = null;

	public AdminDatabase() {
	}

	// get specific admin by adminID
	public boolean getAdminByID(int id) {
		// select admin data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Admin\" WHERE \"AdminID\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, id);

			adminResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select admin data from database (end)
	}
	
	// check whether email exists in admin database
	public boolean checkAdminEmailExists(String email) {
		// select admin data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Admin\" WHERE \"Email\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, email);

			adminResultSet = st.executeQuery();
			db.close();
			int count = 0;
			while(adminResultSet.next()) {
				count++;
			}
			if(count > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		// select admin data from database (end)
	}

	// get admin resultset
	public ResultSet getAdminResult() {
		return adminResultSet;
	}

	public void clearAdminResult() {
		this.adminResultSet = null;
	}
}
