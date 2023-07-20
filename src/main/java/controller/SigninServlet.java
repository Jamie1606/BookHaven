// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 3.6.2023
// Description: To test whether the email and password are correct for sign in


package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TestReg;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Servlet implementation class signinServlet
 */
@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean isValid = false;
		
		String connURL = "jdbc:postgresql://satao.db.elephantsql.com/mhekoapk";
 		String db_username = "mhekoapk";
 	    String db_password = "o9w2O25Afleif9CCVCEBDQZX4tT79MH7";
 	    
 	    int id = 0;
		
		if(TestReg.matchEmail(email) && TestReg.matchPassword(password)) {
			try {
		 		Class.forName("org.postgresql.Driver");
		 		
		 		Connection db = DriverManager.getConnection(connURL, db_username, db_password);
		 		
		 		String sqlStatement = "SELECT * FROM \"public\".\"Member\" WHERE \"Email\" = ? AND \"Password\" = ?";
		        PreparedStatement st = db.prepareStatement(sqlStatement);
		        st.setString(1, email);
		        st.setString(2, password);
		 		
		        ResultSet rs = st.executeQuery();
		     	
		        int count = 0;
		        while (rs.next()) {
		        	id = rs.getInt("MemberID");
		        	isValid = true;
		        	count++;
		        	if(count > 1) {
		        		isValid = false;
		        		break;
		        	}
		        }

		 		db.close();
		 		} catch (Exception e) {
		 			response.sendRedirect("sigin.jsp?errCode=serverError");
		 	}
			if(isValid) {
				try {
			 		Class.forName("org.postgresql.Driver");
			 		
			 		Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			 		
			 		String sqlStatement = "UPDATE  \"public\".\"Member\" SET \"LastActive\" = ? WHERE \"MemberID\" = ?";
			        PreparedStatement st = db.prepareStatement(sqlStatement);
			        st.setObject(1, LocalDateTime.now());
			        st.setInt(2, id);
			     	
			        int count = st.executeUpdate();

			 		db.close();
			 		} catch (Exception e) {
			 	}
				session.setAttribute("memberID", id + "");
				session.setAttribute("role", "member");
				session.setMaxInactiveInterval(60 * 60);		// 1 hour
				response.sendRedirect("index.jsp");
			}
			else {
				try {
			 		Class.forName("org.postgresql.Driver");
			 		
			 		Connection db = DriverManager.getConnection(connURL, db_username, db_password);
			 		
			 		String sqlStatement = "SELECT * FROM \"public\".\"Admin\" WHERE \"Email\" = ? AND \"Password\" = ?";
			        PreparedStatement st = db.prepareStatement(sqlStatement);
			        st.setString(1, email);
			        st.setString(2, password);
			 		
			        ResultSet rs = st.executeQuery();
			     
			        int count = 0;
			        while (rs.next()) {
			        	id = rs.getInt("AdminID");
			        	isValid = true;
			        	count++;
			        	if(count > 1) {
			        		isValid = false;
			        		break;
			        	}
			        }

			 		db.close();
			 		} catch (Exception e) {
			 			response.sendRedirect("sigin.jsp?errCode=serverError");
			 	}
				if(isValid) {
					try {
				 		Class.forName("org.postgresql.Driver");
				 		
				 		Connection db = DriverManager.getConnection(connURL, db_username, db_password);
				 		
				 		String sqlStatement = "UPDATE  \"public\".\"Admin\" SET \"LastActive\" = ? WHERE \"AdminID\" = ?";
				        PreparedStatement st = db.prepareStatement(sqlStatement);
				        st.setObject(1, LocalDateTime.now());
				        st.setInt(2, id);
				     	
				        int count = st.executeUpdate();

				 		db.close();
				 		} catch (Exception e) {
				 	}
					session.setAttribute("adminID", id + "");
					session.setAttribute("role", "admin");
					session.setMaxInactiveInterval(3* 60 * 60);		// 3 hours
					response.sendRedirect("admin/adminHomePage.jsp");
				}
				else {
					response.sendRedirect("signin.jsp?errCode=invalid");
				}
			}
		}
		else {
			response.sendRedirect("signin.jsp?errCode=invalid");
		}
	}

}
