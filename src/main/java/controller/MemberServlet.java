//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date        : 6.6.2023
//Description : process member registration, 

package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import model.Book;
import model.BookDatabase;
import model.Member;
import model.MemberDatabase;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet(urlPatterns = { "/signup", "/admin/members", "/admin/memberUpdate/*", "/admin/memeberDelete/*" })
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub

		// create MemberDatabase object
		MemberDatabase member_db = new MemberDatabase();
		String formName = request.getParameter("formName");
System.out.println("in doPost");
		// [FROM SIGN UP FORM]
		if (formName.equals("signupForm")) {
			// [GET VALUES] assign values from "signup form"
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String address = request.getParameter("address");
			String postalCode = request.getParameter("postalCode");
			String phone = request.getParameter("phone"); // String ms_image = request.getParameter("image");
			if (TestReg.matchEmail(email) && TestReg.matchPassword(password) && TestReg.matchPhone(phone)
					&& TestReg.matchPostalCode(postalCode)) {
				address += " S" + postalCode;

				// call function from MemberDatabase
				int condition = member_db.registerMember(new Member(name, phone, address, email, password));
				if (condition == 1) {
					response.sendRedirect("signin.jsp");
				} else if (condition == -1) {
					response.sendRedirect("signup.jsp?errCode=invalidEmail");
				} else {
					response.sendRedirect("signup.jsp?errCode=serverError");
				}
			} else {
				response.sendRedirect("signup.jsp?errCode=invalid");
			}
		}
		// [FROM MEMBER REGISTRATION FORM]
		else if (formName.equals("memberRegistrationForm")) {

System.out.println("in memberRegistrationForm");
			// [CHECK AUTHENTICATION]
			Authentication auth = new Authentication();
			HttpSession session = request.getSession();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
				return;
			}
			// [CHECK AUTHENTICATION-END]

			ServletContext context = request.getServletContext();
			// Get the root path of the web application
			String rootPath = context.getRealPath("/");

			// Find the position of the ".metadata" directory
			int metadataIndex = rootPath.indexOf(".metadata");

			// Remove the ".metadata" and subsequent directories
			String currentProjectPath = rootPath.substring(0, metadataIndex);

			// Append the desired path relative to the root of the project
			String imagePath = currentProjectPath + "JAD CA1 BookHaven" + File.separator + "src" + File.separator
					+ "main" + File.separator + "webapp" + File.separator + "img" + File.separator + "books"
					+ File.separator;


			// check if the request is multipart/form-data
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					// create handler for file upload

					// [CREATE INSTANCE] of ServletFileUpload class from Apache Commons FileUpload
					// library]
					ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

					// parse the multipar request
					List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

					Map<String, String> fields = new HashMap<>();
					String image = null;

					// process the form fields and file uploads
					for (FileItem item : items) {
						String fieldName = item.getFieldName();
						// file upload
						String fileName = item.getName();
						System.out.println(fileName);
						InputStream fileContent = item.getInputStream(); // get image content
						String uploadPath;

						if (fieldName.equals("image") && !fileName.isEmpty()) {
							uploadPath = imagePath + "normal" + File.separator + fileName; // image upload destination
							File directory = new File(uploadPath).getParentFile();
							if (!directory.exists()) {
								directory.mkdirs();
							}
							Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
							image = uploadPath;
						}

					}

					// getting value from fields
					String status = fields.get("status");
					String name = fields.get("name");
					String gender = fields.get("gender");
					String birthDate = fields.get("birthDate");
					String phone = fields.get("phone");
					String address = fields.get("address");
					String postalCode = fields.get("postalCode");
					String email = fields.get("email");
					String password = fields.get("password");
					;
					String oldimage = fields.get("oldimage");

					// checking null and empty values
					if (name != null && !name.isEmpty() && gender != null && !gender.isEmpty() && birthDate != null
							&& !birthDate.isEmpty() && phone != null && !phone.isEmpty() && address != null
							&& !address.isEmpty() && postalCode != null && !postalCode.isEmpty() && email != null
							&& !email.isEmpty() && password != null && !password.isEmpty()) {

						// test with regular expressions
						if (TestReg.matchEmail(email) && TestReg.matchPassword(password) && TestReg.matchPhone(phone)
								&& TestReg.matchPostalCode(postalCode) && TestReg.matchGender(gender)
								&& TestReg.matchDate(birthDate) && TestReg.matchIntegerArrayList(authors)
								&& TestReg.matchIntegerArrayList(genres)) {

							Date birth_date = Date.valueOf(LocalDate.parse(birthDate));

							BookDatabase book_db = new BookDatabase();
							book_db.clearBookResult();
							int count = 0;

							if (status.equals("register")) {

System.out.println("in register");
								// call function from MemberDatabase
								int condition = member_db
										.registerMember(new Member(name, phone, address, email, password));
								if (condition == 1) {
									response.sendRedirect("signin.jsp");
								} else if (condition == -1) {
									request.setAttribute("erroCode", "invalidEmail");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								} else {
									request.setAttribute("erroCode", "serverError");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								}

							} else if (status.equals("update")) {
								if (image == null) {
									if (oldimage != null && !oldimage.isEmpty() && !oldimage.equals("null")) {
										image = oldimage;
									}
								}
								// call function from MemberDatabase
								int condition = member_db
										.registerMember(new Member(name, phone, address, email, password));
								if (condition == 1) {
									response.sendRedirect("signin.jsp");
								} else if (condition == -1) {
									request.setAttribute("erroCode", "invalidEmail");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								} else {
									request.setAttribute("erroCode", "serverError");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								}

							} else {
								// [status loop]
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
								return;
							}
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					request.setAttribute("error", "upload");
					request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
					return;
				}
			}
		}
	}
}