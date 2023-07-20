//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Group       : 10
//Date		  : 8.6.2023
//Description : database functions related to "Genre" TABLE

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class GenreDatabase {

	// [DATABASE CONFIG]

	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet genreResultSet;
	private ResultSet bookResultSet;

	// [GENRE DATA FROM DATABSE]

	public GenreDatabase() {
	}

//addGenre INSERT INTO "public"."Genre" ("Genre") VALUES ('Fiction');
	// select genre from database
	public boolean getGenre() {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select all from "Genre" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Genre\" ORDER BY \"GenreID\"";

			PreparedStatement st = db.prepareStatement(sqlStatement);

			genreResultSet = st.executeQuery();

			db.close();
			// successful
			return true;
		} catch (Exception e) {

			System.out.println("db error:");
			return false;
		}

	}// select genre from database (end)

	// select genre from database BY ID
	public boolean getGenreByID(int ID) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select all from "Genre" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Genre\" WHERE \"GenreID\"=?";

			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, ID);
			genreResultSet = st.executeQuery();

			db.close();
			// successful
			return true;
		} catch (Exception e) {

			System.out.println("db error:");
			return false;
		}

	}// select genre from database by ID (end)

	// set resultset to null
	public void clearGenreResult() {
		this.genreResultSet = null;
	}
	
	// [RETURN GENRE RESULTSET]
	public ResultSet getGenreResult() {
		return genreResultSet;
	}

	// addGenre INSERT INTO "public"."Genre" ("Genre") VALUES ('Fiction');
	// insert genre to database
	public boolean addGenre(Genre genre) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select all from "Genre" TABLE
			String sqlStatement = "INSERT INTO \"public\".\"Genre\" (\"Genre\") VALUES (?)";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, genre.getGenre());
			int rowsAffected = st.executeUpdate();

			db.close();
			if (rowsAffected == 1) {

				// successful
				return true;

			} else {

				// fail
				return false;
			}
		} catch (Exception e) {

			return false;
		}

	}// insert genre to database (end)
	
	
	// update genre to database
	public boolean updateGenre(Genre genre) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select all from "Genre" TABLE
			String sqlStatement = "UPDATE \"public\".\"Genre\" SET \"Genre\"=? WHERE \"GenreID\"=?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, genre.getGenre());
			st.setInt(2, genre.getGenreID());
			int rowsAffected = st.executeUpdate();

			db.close();
			if (rowsAffected == 1) {

				// successful
				return true;

			} else {

				// fail
				return false;
			}
		} catch (Exception e) {

			System.out.println("db error:");
			return false;
		}

	}// update genre to database (end)
	
	// delete genre from database
	public boolean deleteGenre(int id) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "DELETE FROM \"public\".\"Genre\" WHERE \"GenreID\" = ?";
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

	// Retrieve books according to specific genre
	public boolean getBookByGenreID(int ID) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Book\" b JOIN \"public\".\"BookGenre\" g ON b.\"ISBNNo\" = g.\"ISBNNo\" WHERE g.\"GenreID\"=? ;";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, ID);
			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}// Retrieve books according to specific genre (end)

	// [RETURN BOOK RESULTSET]
	public ResultSet getBookResult() {
		return bookResultSet;
	}

}