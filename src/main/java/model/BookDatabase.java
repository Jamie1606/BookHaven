// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
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
			st.setDouble(8, book.getRating());
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
			System.out.println(e);
			return false;
		}
		// insert data into database (end)
	}
	
	// get book qty by isbn
	public int getBookQtyByISBN(String isbn) {
		// select book qty from database (start)
		int qty = 0;
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT \"Qty\" FROM \"public\".\"Book\" WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			while(bookResultSet.next()) {
				qty = bookResultSet.getInt("Qty");
			}
			clearBookResult();
			db.close();
			return qty;
		} catch (Exception e) {
			return -1;
		}
		// select book qty from database (end)
	}

	// select latest book from database
	public boolean getLatestBook(int limit) {
		// select book data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Book\" ORDER BY \"PublicationDate\" DESC LIMIT ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, limit);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database (end)
	}

	// update book in database
	public boolean updateBook(Book book) {
		// update data in database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "UPDATE \"public\".\"Book\" SET \"Title\" = ?, \"Page\" = ?, \"Price\" = ?, \"Publisher\" = ?, \"PublicationDate\" = ?, \"Qty\" = ?, \"Description\" = ?, \"Image\" = ?, \"Image3D\" = ?, \"Status\" = ? WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, book.getTitle());
			st.setInt(2, book.getPage());
			st.setDouble(3, book.getPrice());
			st.setString(4, book.getPublisher());
			st.setDate(5, Date.valueOf(book.getPublicationDate().toString()));
			st.setInt(6, book.getQty());
			st.setString(7, book.getDescription());
			st.setString(8, book.getImage());
			st.setString(9, book.getImage3D());
			st.setString(10, book.getStatus());
			st.setString(11, book.getISBNNo());

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

	// set resultset to null
	public void clearBookResult() {
		this.bookResultSet = null;
	}

	// select book from database
	public boolean getBook() {
		// select book data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Book\"";
			PreparedStatement st = db.prepareStatement(sqlStatement);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database (end)
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

	// get bookauthor data from database
	public boolean getBookAuthorByISBN(String isbn) {
		// select bookauthor data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"BookAuthor\" WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select bookauthor data from database (end)
	}

	// get book data from database by genre
	public boolean getBookByGenre(String genre) {
		// select book data from database by genre (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT b.* "
					+ "FROM \"public\".\"BookGenre\" AS bg, "
					+ "\"public\".\"Book\" AS b, "
					+ "\"public\".\"Genre\" AS g "
					+ "WHERE bg.\"ISBNNo\" = b.\"ISBNNo\" "
					+ "AND bg.\"GenreID\" = g.\"GenreID\" "
					+ "AND g.\"Genre\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, genre);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database by genre (end)
	}

	// get book data from database by genreid
	public boolean getBookByGenreID(int id) {
		// select book data from database by genreID (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT b.*, bg.\"GenreID\" FROM \"public\".\"BookGenre\" AS bg, \"public\".\"Book\" AS b WHERE bg.\"ISBNNo\" = b.\"ISBNNo\" AND bg.\"GenreID\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, id);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database by genreID (end)
	}

	// get book data from database by authorid
	public boolean getBookByAuthorID(int id) {
		// select book data from database by authorID (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"Book\" AS b JOIN \"public\".\"BookAuthor\" AS ba ON b.\"ISBNNo\" = ba.\"ISBNNo\" WHERE ba.\"AuthorID\"=?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, id);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select book data from database by authorID (end)
	}
	
	// get bookgenre and genre data from database
	public boolean getGenreByISBN(String isbn) {
		// select genre and bookgenre data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT g.* FROM \"public\".\"BookGenre\" AS bg, \"public\".\"Genre\" AS g "
					+ "WHERE bg.\"ISBNNo\" = ? " + "AND bg.\"GenreID\" = g.\"GenreID\"";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select genre and bookgenre data from database (end)
	}

	// get bookgenre and author data from database
	public boolean getAuthorByISBN(String isbn) {
		// select author and bookauthor data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT a.* FROM \"public\".\"BookAuthor\" AS ba, \"public\".\"Author\" AS a "
					+ "WHERE ba.\"ISBNNo\" = ? " + "AND ba.\"AuthorID\" = a.\"AuthorID\"";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select author and bookauthor data from database (start)
	}

	// get bookgenre data from database
	public boolean getBookGenreByISBN(String isbn) {
		// select bookgenre data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "SELECT * FROM \"public\".\"BookGenre\" WHERE \"ISBNNo\" = ?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			bookResultSet = st.executeQuery();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// select bookgenre data from database (end)
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

	// delete book and unlink them
	public boolean deleteBook(String isbn) {
		// delete data from database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			String sqlStatement;
			PreparedStatement st;

			sqlStatement = "DELETE FROM \"public\".\"Book\" WHERE \"ISBNNo\" = ?";
			st = db.prepareStatement(sqlStatement);
			st.setString(1, isbn);

			st.executeUpdate();
			db.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		// delete data from database (end)
	}
}