// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: database functions related to author

package model;

import java.sql.*;

public class AuthorDatabase {
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet authorResultSet = null;

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

	// set resultset to null
	public void clearAuthorResult() {
		this.authorResultSet = null;
	}

	// get author resultset
	public ResultSet getAuthorResult() {
		return authorResultSet;
	}

	// get specific author by authorID
	public boolean getAuthorByID(int id) {
		// select author data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Author\" WHERE \"AuthorID\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, id);

			authorResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select author data from database (end)
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

			if (rowsAffected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		// insert data into database (end)
	}

	// update author in database
	public boolean updateAuthor(Author author) {
		// update data in database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "UPDATE \"public\".\"Author\" SET \"Name\" = ?, \"Nationality\" = ?, \"BirthDate\" = ?, \"Biography\" = ?, \"Link\" = ? WHERE \"AuthorID\" = ?";
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
			st.setInt(6, author.getAuthorID());

			int rowsAffected = st.executeUpdate();

			db.close();

			if (rowsAffected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		// update data in database (end)
	}

	// delete author in database
	public boolean deleteAuthor(int id) {
		// delete data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "DELETE FROM \"public\".\"Author\" WHERE \"AuthorID\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();
			db.close();

			if (rowsAffected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		// delete data from database (end)
	}
}