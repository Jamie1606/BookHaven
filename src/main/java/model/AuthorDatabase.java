// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.7.2023
// Description	: all database functions related to author

package model;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.text.StringEscapeUtils;

public class AuthorDatabase {
	
	public AuthorDatabase() {
	}

	// get all author data from database
	public ArrayList<Author> getAuthor() throws SQLException {
		Connection conn = null;
		ArrayList<Author> authors = new ArrayList<Author>();
		try {			
			// connecting to database
			conn = DatabaseConnection.getConnection();
			
			// get author data ordered by name ascending
			String sqlStatement = "SELECT * FROM \"public\".\"Author\" ORDER BY \"Name\"";
			PreparedStatement st = conn.prepareStatement(sqlStatement);

			ResultSet rs = st.executeQuery();
			
			// author data is added to arraylist
			// escaping html special characters
			while(rs.next()) {
				authors.add(new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")), 
						StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"),
						StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
						StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..... Error in getAuthor in AuthorDatabase .....");
		}
		finally {
			conn.close();
		}
		return authors;
	}

	// get one author data by authorID from database
	public Author getAuthorByID(int id) throws SQLException {
		Author author = null;
		Connection conn = null;
		
		try {
			// connecting to database
			conn = DatabaseConnection.getConnection();

			// get author data by author id
			String sqlStatement = "SELECT * FROM \"public\".\"Author\" WHERE \"AuthorID\" = ?";
			PreparedStatement st = conn.prepareStatement(sqlStatement);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			// escaping html special characters
			// author data is added to author object
			if(rs.next()) {
				author = new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")), 
						StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"),
						StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
						StringEscapeUtils.escapeHtml4(rs.getString("Link")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..... Error in getAuthorByID in AuthorDatabase .....");
		}
		finally {
			conn.close();
		}
		return author;
	}

	// insert author into database
	public int registerAuthor(Author author) throws SQLException {
		Connection conn = null;
		int rowsAffected = 0;
		
		try {
			// connecting to database
			conn = DatabaseConnection.getConnection();
			
			String sqlStatement = "INSERT INTO \"public\".\"Author\" (\"Name\", \"Nationality\", \"BirthDate\", \"Biography\", \"Link\") VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = conn.prepareStatement(sqlStatement);
			st.setString(1, author.getName());
			st.setString(2, author.getNationality());
			
			// checking null value for birthdate
			if (author.getBirthDate() == null) {
				st.setNull(3, Types.DATE);
			} else {
				st.setDate(3, Date.valueOf(author.getBirthDate().toString()));
			}
			
			st.setString(4, author.getBiography());
			st.setString(5, author.getLink());

			rowsAffected = st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..... Error in registerAuthor in AuthorDatabase .....");
		}
		finally {
			conn.close();
		}
		return rowsAffected;
	}

	// update author by author id in database 
	public int updateAuthor(Author author) throws SQLException {
		Connection conn = null;
		int rowsAffected = 0;
		
		try {
			// connecting to database
			conn = DatabaseConnection.getConnection();

			String sqlStatement = "UPDATE \"public\".\"Author\" SET \"Name\" = ?, \"Nationality\" = ?, \"BirthDate\" = ?, \"Biography\" = ?, \"Link\" = ? WHERE \"AuthorID\" = ?";
			PreparedStatement st = conn.prepareStatement(sqlStatement);
			st.setString(1, author.getName());
			st.setString(2, author.getNationality());

			// check null value for birthdate
			if (author.getBirthDate() == null) {
				st.setNull(3, Types.DATE);
			} else {
				st.setDate(3, Date.valueOf(author.getBirthDate().toString()));
			}
			
			st.setString(4, author.getBiography());
			st.setString(5, author.getLink());
			st.setInt(6, author.getAuthorID());

			rowsAffected = st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..... Error in updateAuthor in AuthorDatabase .....");
		}
		finally {
			conn.close();
		}
		return rowsAffected;
	}

	// delete author by author id in database
	public int deleteAuthor(int id) throws SQLException {
		Connection conn = null;
		int rowsAffected = 0;
		
		try {
			// connecting to database
			conn = DatabaseConnection.getConnection();

			String sqlStatement = "DELETE FROM \"public\".\"Author\" WHERE \"AuthorID\" = ?";
			PreparedStatement st = conn.prepareStatement(sqlStatement);
			st.setInt(1, id);

			rowsAffected = st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..... Error in deleteAuthor in AuthorDatabase .....");
		}
		finally {
			conn.close();
		}
		return rowsAffected;
	}
}