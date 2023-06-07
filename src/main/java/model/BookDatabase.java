// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 7.6.2023
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
	// -1 => book already exists
	// 1 => success
	// 0 => server error
	public int registerBook(Book book) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select book data from database to avoid data duplication
			String sqlStatement = "SELECT * FROM \"public\".\"Book\" WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, book.getISBNNo());
			ResultSet rs = st.executeQuery();
			int count = 0;
			while (rs.next()) {
				count++;
			}

			// return false to avoid duplication if there is 1 record or more
			if (count >= 1) {
				return -1;
			}

			sqlStatement = "INSERT INTO \"public\".\"Book\" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			st = db.prepareStatement(sqlStatement);
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
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
		// insert data into database (end)
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
				if(st.executeUpdate() != 1) {
					isSuccess = false;
				}
			}

			db.close();

			// if unsuccessful, delete the book data that is already inserted into database
			if (!isSuccess) {

			} else {
				return true;
			}

		} catch (Exception e) {
			return false;
		}
		// insert data into database (end)
		return false;
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
					if(st.executeUpdate() != 1) {
						isSuccess = false;
					}
				}

				db.close();

				// if unsuccessful, delete the book data that is already inserted into database
				if (!isSuccess) {

				} else {
					return true;
				}

			} catch (Exception e) {
				return false;
			}
			// insert data into database (end)
			return false;
		}
}