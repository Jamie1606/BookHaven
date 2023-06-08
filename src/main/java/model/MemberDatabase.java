//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date        : 7.6.2023
//Description : member related database

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class MemberDatabase {
	
	//[DATABASE CONFIG]
	private final String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
	private final String db_username = "mhekoapk";
	private final String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
	private ResultSet memberResultSet;
	
	public MemberDatabase() {
		
	}
	
	// -1 => invalid email
	// 0 => server Error
	// 1 => success
	// insert member into database
	public int registerMember(Member member) {
		// insert data into database (start)
		try {
			// loading postgresql driver
			Class.forName("org.postgresql.Driver");

			// get database connection
			Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			
			
			//[CHECK DUPLICATE EMAIL]
			String sqlStatement = "SELECT * FROM \"public\".\"Member\" WHERE \"Email\"=?";
			PreparedStatement st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getEmail());
			ResultSet rs = st.executeQuery();
			int count = 0;
			
			while(rs.next())
			{
				count++;
			}
			if(count > 0)
			{
				//duplication exists
				return -1;
			}
			
			//[INSERT DATA TO TABLE]
			sqlStatement = "INSERT INTO \"public\".\"Member\" (\"Name\", \"Email\", \"Password\", \"Address\", \"Phone\",\"Gender\",\"BirthDate\") VALUES (?, ?, ?, ?, ?, ?, ?)";
			st = db.prepareStatement(sqlStatement);
			st.setString(1, member.getName());
			st.setString(2, member.getEmail());
			st.setString(3, member.getPassword());
			st.setString(4, member.getAddress());
			st.setString(5, member.getPhone());
			if(member.getGender()!='\0') {
				//System.out.println(true);
				st.setString(6, String.valueOf(member.getGender()));
			}
			else {
				st.setString(6, "");
				//System.out.println(false);
			}
			
			if (member.getBirthDate() == null) {
				st.setNull(7, Types.DATE);
			} else {
				st.setDate(7, Date.valueOf(member.getBirthDate().toString()));
			}
			int rowsAffected = st.executeUpdate();
			
			//[CLOSE CONNECTION]
			db.close();
			
			if(rowsAffected == 1) {
				//successful
				return 1;
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
		
}
// insert data into database (end)	
	
}