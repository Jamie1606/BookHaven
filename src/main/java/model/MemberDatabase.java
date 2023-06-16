//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date        : 7.6.2023
//Description : database functions related to "Member" TABLE

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class MemberDatabase {

	// [DATABASE CONFIG]
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet memberResultSet;

	public MemberDatabase() {

	}

	// -1 => invalid email
	// 0 => server Error
	// 1 => success

	// [REGISTER MEMBER] insert member into database
	public int registerMember(Member member) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// [CHECK DUPLICATE EMAIL]
			String sqlStatement = "SELECT * FROM \"public\".\"Member\" WHERE \"Email\"=?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getEmail());
			ResultSet rs = st.executeQuery();
			int count = 0;

			while (rs.next()) {
				count++;
			}
			if (count > 0) {
				// duplication exists
				return -1;
			}
			// [CHECK DUPLICATE EMAIL-END]

			// [INSERT DATA TO TABLE]
			sqlStatement = "INSERT INTO \"public\".\"Member\" (\"Name\", \"Email\", \"Password\", \"Address\", \"Phone\",\"Gender\",\"BirthDate\",\"Image\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getName());
			st.setString(2, member.getEmail());
			st.setString(3, member.getPassword());
			st.setString(4, member.getAddress());
			st.setString(5, member.getPhone());
			if (member.getGender() != 'N') {
				st.setString(6, String.valueOf(member.getGender()));
			} else {
				st.setNull(6, Types.CHAR);
			}

			if (member.getBirthDate() == null) {
				st.setNull(7, Types.DATE);
			} else {
				st.setDate(7, Date.valueOf(member.getBirthDate().toString()));
			}
			if (member.getImage() == null) {
				st.setString(8, "");
			} else {
				st.setString(8, member.getImage());
			}
			int rowsAffected = st.executeUpdate();

			// [CLOSE CONNECTION]
			db.close();

			if (rowsAffected == 1) {
				// successful
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}

	}
	// [REGISTER MEMBER-END]

	// set resultset to null
	public void clearMemberResult() {
		this.memberResultSet = null;
	}

	// [RETIRIEVE ALL MEMBER]
	public boolean getMember() {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select all from "Genre" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Member\"";

			PreparedStatement st = db.prepareStatement(sqlStatement);

			memberResultSet = st.executeQuery();

			db.close();
			// successful
			return true;
		} catch (Exception e) {

			System.out.println("db error:");
			return false;
		}

	}
	// [RETIRIEVE ALL MEMBER-END]

	// [RETIRIEVE MEMBER BY ID] select member from database BY ID
	// [RETIRIEVE ALL MEMBER-END]

	// [RETIRIEVE MEMBER BY ID] select member from database BY ID
	public boolean getMemberByID(int ID) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			// select from "Member" TABLE
			String sqlStatement = "SELECT * FROM \"public\".\"Member\" WHERE \"MemberID\"=?";

			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setInt(1, ID);
			memberResultSet = st.executeQuery();
			
			db.close();
			// successful
			return true;
		} catch (Exception e) {

			System.out.println(e);
			return false;
		}

	}
	//[RETIRIEVE MEMBER BY ID-END]
	
	// [RETURN MEMBER RESULTSET]
	public ResultSet getMemberResult() {
		return memberResultSet;
	}
	// [RETURN MEMBER RESULTSET-END]


	// [UPDATE MEMBER] update member data to database
	public int updateMember(Member member) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			// [CHECK DUPLICATE EMAIL]
			String sqlStatement = "SELECT * FROM \"public\".\"Member\" WHERE \"Email\"=?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getEmail());
			ResultSet rs = st.executeQuery();
			int count = 0;

			while (rs.next()) {
				if(rs.getInt("MemberID")!=member.getMemberID()) {
					System.out.println(rs.getInt("MemberID"));
					System.out.println(member.getMemberID());
					count++;
				}
			}
			if (count > 0) {
				// duplication exists
				return -1;
			}
			// [CHECK DUPLICATE EMAIL-END]


			// select all from "Genre" TABLE
			sqlStatement = "UPDATE \"public\".\"Member\" SET \"Name\" = ?, \"Gender\" = ?, \"BirthDate\" = ?, \"Phone\" = ?, \"Address\" = ?, \"Email\" = ?, \"Password\" = ?, \"Image\" = ? WHERE \"MemberID\" = ?;";
			st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getName());
			st.setString(4, member.getPhone());
			st.setString(5, member.getAddress());
			st.setString(6, member.getEmail());
			st.setString(7, member.getPassword());
			st.setInt(9, member.getMemberID());
			if (member.getGender() != 'N') {
				st.setString(2, String.valueOf(member.getGender()));
			} else {
				st.setString(2, "");
			}

			if (member.getBirthDate() == null) {
				st.setNull(3, Types.DATE);
			} else {
				st.setDate(3, Date.valueOf(member.getBirthDate().toString()));
			}
			if (member.getImage() == null) {
				st.setString(8, "");
			} else {
				st.setString(8, member.getImage());
			}

			int rowsAffected = st.executeUpdate();

			db.close();
			if (rowsAffected == 1) {
				// successful
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {

			System.out.println(e);
			return 0;
		}

	}
	// [UPDATE MEMBER-END]

	// [UPDATE MEMBER-END]

	// [DELETE MEMBER] delete member from database
	public boolean deleteMember(int id) {
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);

			String sqlStatement = "DELETE FROM \"public\".\"Member\" WHERE \"MemberID\" = ?";
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
		// [DELETE MEMBER-END]
		// [DELETE MEMBER-END]
	}

// insert data into database (end)	

}