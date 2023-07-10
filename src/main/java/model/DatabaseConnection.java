package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	public static Connection getConnection() {
		String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
		String db_username = "mhekoapk";
		String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
		
		Connection conn = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(connURL, db_username, db_password);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}