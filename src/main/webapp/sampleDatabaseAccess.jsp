<%@ page import ="java.sql.*" %>

<%
	int id;
	String name;
	
 	try {
 		// Step1: Load JDBC Driver
 		Class.forName("org.postgresql.Driver");
 		
 		// Step 2: Define Connection URL
 		String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
 		String username = "mhekoapk";
 	    String password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
 		
 		// Step 3: Establish connection to URL
 		Connection db = DriverManager.getConnection(connURL, username, password);
 		
 		// Step 4: Create Statement object
        Statement st = db.createStatement();
 		
     	// Step 5: Execute SQL Command
        ResultSet rs = st.executeQuery("SELECT * FROM \"public\".\"Admin\"");
     	
     	// Step 6: Process Result
        while (rs.next()) {
            out.println("HELLO");
        }

 		// Step 7: Close connection
 		db.close();
 		} catch (Exception e) {
 			out.println("Error: " + e);
 	}
%>