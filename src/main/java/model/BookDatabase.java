// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: database functions related to book

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class BookDatabase {
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet bookResultSet;

	public BookDatabase() {
	}

	// insert book into database
	public boolean registerBook(Book book) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "INSERT INTO \"public\".\"Book\" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, book.getISBNNo());
			st.setString(2, book.getTitle());
			st.setInt(3, book.getPage());
			st.setDouble(4, book.getPrice());
			st.setString(5, book.getPublisher());
			st.setDate(6, Date.valueOf(book.getPublicationDate().toString()));
			st.setInt(7, book.getQty());
			st.setShort(8, book.getRating());
			st.setString(9, book.getDescription());
			st.setString(10, book.getImage());
			st.setString(11, book.getImage3D());
			st.setString(12, book.getStatus());

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

	// set resultset to null
	public void clearBookResult() {
		this.bookResultSet = null;
	}

	// get specific book by isbn
	public boolean getBookByISBN(String isbn) {
		// select book data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Book\" WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database (end)
	}

	// get book resultset
	public ResultSet getBookResult() {
		return bookResultSet;
	}

	// insert and link book and author into database
	public boolean registerBookAuthor(ArrayList<String> authors, String isbn) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// to check whether each record is successfully inserted
			boolean isSuccess = true;
			for (int i = 0; i < authors.size(); i++) {
				String sqlStatement = "INSERT INTO \"public\".\"BookAuthor\" VALUES (?, ?)";
				PreparedStatement st = db.prepareStatement(sqlStatement);
				st.setString(1, isbn);
				st.setInt(2, Integer.parseInt(authors.get(i)));
				if (st.executeUpdate() != 1) {
					isSuccess = false;
				}
			}

			db.close();
			return isSuccess;
		} catch (Exception e) {
			return false;
		}
		// insert data into database (end)
	}

	// insert and link book and genre into database
	public boolean registerBookGenre(ArrayList<String> genres, String isbn) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// to check whether each record is successfully inserted
			boolean isSuccess = true;
			for (int i = 0; i < genres.size(); i++) {
				String sqlStatement = "INSERT INTO \"public\".\"BookGenre\" VALUES (?, ?)";
				PreparedStatement st = db.prepareStatement(sqlStatement);
				st.setString(1, isbn);
				st.setInt(2, Integer.parseInt(genres.get(i)));
				if (st.executeUpdate() != 1) {
					isSuccess = false;
				}
			}

			db.close();
			return isSuccess;
		} catch (Exception e) {
			return false;
		}
		// insert data into database (end)
	}

	// delete author and genre and unlink them
	// if you want to use isbn, give 0 to genreID
	// if you want to use genreID, give null or empty string to isbn
	public boolean deleteBookGenre(int genreID, String isbn) {
		// delete data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			String sqlStatement;
			PreparedStatement st;

			if (genreID != 0) {
				sqlStatement = "DELETE FROM \"public\".\"BookGenre\" WHERE \"GenreID\" = ?";
				st = db.prepareStatement(sqlStatement);
				st.setInt(1, genreID);
			} else if (isbn != null && !isbn.isEmpty()) {
				sqlStatement = "DELETE FROM \"public\".\"BookGenre\" WHERE \"ISBNNo\" = ?";
				st = db.prepareStatement(sqlStatement);
				st.setString(1, isbn);
			} else {
				return false;
			}

			st.executeUpdate();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// delete data from database (end)
	}

	// delete author and book and unlink them
	// if you want to use isbn, give 0 to authorID
	// if you want to use authorID, give null or empty string to isbn
	public boolean deleteBookAuthor(int authorID, String isbn) {
		// delete data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			String sqlStatement;
			PreparedStatement st;

			if (authorID != 0) {
				sqlStatement = "DELETE FROM \"public\".\"BookAuthor\" WHERE \"AuthorID\" = ?";
				st = db.prepareStatement(sqlStatement);
				st.setInt(1, authorID);
			} else if (isbn != null && !isbn.isEmpty()) {
				sqlStatement = "DELETE FROM \"public\".\"BookAuthor\" WHERE \"ISBNNo\" = ?";
				st = db.prepareStatement(sqlStatement);
				st.setString(1, isbn);
			} else {
				return false;
			}

			st.executeUpdate();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// delete data from database (end)
	}
}