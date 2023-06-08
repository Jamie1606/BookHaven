//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date        : 6.6.2023
//Description : process member registration, 

package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Member;
import model.MemberDatabase;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		//[GET VALUES] assign values from "signup/memberRegistration form"
		String ms_name = request.getParameter("name");
		String ms_email = request.getParameter("email");
		String ms_password = request.getParameter("password");
		String ms_address = request.getParameter("address");
		String ms_postalCode = request.getParameter("postalCode");
		String ms_phone = request.getParameter("phone");		//String ms_image = request.getParameter("image");
		//[FROM SIGN UP FORM]
		if(formName.equals("signupForm")){
		if(TestReg.matchEmail(ms_email) && TestReg.matchPassword(ms_password) && TestReg.matchPhone(ms_phone) && TestReg.matchPostalCode(ms_postalCode)) {
			ms_address += " S" + ms_postalCode;
			//create MemberDatabase object
			MemberDatabase member_db = new MemberDatabase();
			
			//call function from MemberDatabase
			int condition =member_db.registerMember(new Member(ms_name, ms_phone, ms_address, ms_email, ms_password)); 
			if(condition == 1) {
				response.sendRedirect("signin.jsp");
			}
			else if(condition == -1) {
				response.sendRedirect("signup.jsp?errCode=invalidEmail");
			}
			else {
				response.sendRedirect("signup.jsp?errCode=serverError");
			}	
		} else {
			response.sendRedirect("signup.jsp?errCode=invalid");
		}
		}
		//[FROM MEMBER REGISTRATION FORM]
		else
		{
			if(TestReg.matchEmail(ms_email) && TestReg.matchPassword(ms_password) && TestReg.matchPhone(ms_phone) && TestReg.matchPostalCode(ms_postalCode)) {
			String ms_birthDate = request.getParameter("birthDate");
			char ms_gender = request.getParameter("gender").charAt(0);
			
			//[DATE]
			LocalDate date = null;
			if (ms_birthDate != null && !ms_birthDate.isEmpty()) {
				date = LocalDate.parse(ms_birthDate);
			}
			Date birthDate = Date.valueOf(date);
			
			//create MemberDatabase object
			MemberDatabase member_db = new MemberDatabase();
			
			//call function from MemberDatabase
			int condition =member_db.registerMember(new Member(ms_name, ms_gender, birthDate, ms_phone, ms_address, ms_email, ms_password)); 
			if(condition == 1) {
				response.sendRedirect("admin/memberRegistration.jsp?success=true");
			}
			else if(condition == -1) {
				response.sendRedirect("admin/memberRegistration.jsp?errCode=invalidEmail");
			}
			else {
				response.sendRedirect("admin/memberRegistration.jsp?errCode=serverError");
			}
			
		}else {
					response.sendRedirect("admin/memberRegistration.jsp?errCode=invalid");
				}
	}

}
}