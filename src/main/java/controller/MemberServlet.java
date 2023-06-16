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

import org.apache.commons.text.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;
import model.Member;
import model.MemberDatabase;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet(urlPatterns = { "/signup", "/profile", "/admin/members", "/admin/memberUpdate/*",
		"/admin/memeberDelete/*" })
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURi = request.getRequestURI();
		// [DEFINE] database and resultSet arrayList(Member)
		MemberDatabase member_db = new MemberDatabase();
		ArrayList<Member> memberList = new ArrayList<Member>();

		if (requestURi.contains("admin/members")) {
			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]

			boolean condition = member_db.getMember();
			if (condition) {
				ResultSet rs = member_db.getMemberResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						memberList.add(
								new Member(rs.getInt("MemberID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")),
										StringEscapeUtils.escapeHtml4(rs.getString("Gender")).charAt(0),
										rs.getDate("BirthDate"), StringEscapeUtils.escapeHtml4(rs.getString("Phone")),
										StringEscapeUtils.escapeHtml4(rs.getString("Address")),
										StringEscapeUtils.escapeHtml4(rs.getString("Email")),
										StringEscapeUtils.escapeHtml4(rs.getString("Password")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverRetrieveError");
				}
			} else {
				request.setAttribute("error", "serverRetrieveError");
			}

			// set the author arraylist as an attribute
			request.setAttribute("memberList", memberList);
			request.setAttribute("servlet", "true");

			// forward the data to the jsp
			request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);
			return;
		} else if (requestURi.contains("admin/memberUpdate")) {

			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]

			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/members").forward(request, response);
				return;
			} else {
				String id = parts[parts.length - 1];
				if (TestReg.matchInteger(id)) {
					if (member_db.getMemberByID(Integer.parseInt(id))) {
						ResultSet rs = member_db.getMemberResult();
						Member memberData = null;

						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								memberData = new Member(StringEscapeUtils.escapeHtml4(rs.getString("Name")),
										StringEscapeUtils.escapeHtml4(rs.getString("Gender")).charAt(0),
										rs.getDate("BirthDate"), StringEscapeUtils.escapeHtml4(rs.getString("Phone")),
										StringEscapeUtils.escapeHtml4(rs.getString("Address")),
										StringEscapeUtils.escapeHtml4(rs.getString("Email")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image")));
								break;
							}

							request.setAttribute("member", memberData); // [SEND TO registration form]
							request.setAttribute("status", "update");
							request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
							return;
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/members").forward(request, response);
							return;
						}
					} else {
						request.setAttribute("error", "invalid");
						request.getRequestDispatcher("/admin/members").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/admin/members").forward(request, response);
					return;
				}

			}
		} else if (requestURi.contains("admin/memberDelete")) {

			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]
			doDelete(request, response);
		} else if (requestURi.endsWith("/profile")) {

			// [CHECK USER AUTHENTICATION]

			HttpSession session = request.getSession();
			// [CHECK USER AUTHENTICATION-END]

			// [GET MEMBERID] from session
			String id = session.getAttribute("memberID").toString();
			if (TestReg.matchInteger(id)) {
				member_db.clearMemberResult();
				boolean condition = member_db.getMemberByID(Integer.parseInt(id));
				if (condition) {
					ResultSet rs = member_db.getMemberResult();
					Member memberData = null;

					try {
						while (rs.next()) {
							// sanitizing output by escaping html special characters
							memberData = new Member(StringEscapeUtils.escapeHtml4(rs.getString("Name")),
									StringEscapeUtils.escapeHtml4(rs.getString("Gender")).charAt(0),
									rs.getDate("BirthDate"), StringEscapeUtils.escapeHtml4(rs.getString("Phone")),
									StringEscapeUtils.escapeHtml4(rs.getString("Address")),
									StringEscapeUtils.escapeHtml4(rs.getString("Email")),
									StringEscapeUtils.escapeHtml4(rs.getString("Image")));
							break;
						}

						request.setAttribute("servlet", "true");
						request.setAttribute("member", memberData); // [SEND TO registration form]
						request.getRequestDispatcher("/profile.jsp").forward(request, response);
						return;
					} catch (Exception e) {
						request.setAttribute("error", "serverError");
						request.getRequestDispatcher("/profile.jsp").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/profile.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
				return;
			}

		}

		else {

			request.setAttribute("error", "invalid");
			request.getRequestDispatcher("/admin/memberList.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// create MemberDatabase object
		MemberDatabase member_db = new MemberDatabase();
		String formName = request.getParameter("formName");
		// [FROM SIGN UP FORM]
		if (formName == null) {

			ServletContext context = request.getServletContext();
			// Get the root path of the web application
			String rootPath = context.getRealPath("/");

			// Find the position of the ".metadata" directory
			int metadataIndex = rootPath.indexOf(".metadata");

			// Remove the ".metadata" and subsequent directories
			String currentProjectPath = rootPath.substring(0, metadataIndex);

			// Append the desired path relative to the root of the project
			String imagePath = currentProjectPath + "JAD CA1 BookHaven" + File.separator + "src" + File.separator
					+ "main" + File.separator + "webapp" + File.separator + "img" + File.separator + "members"
					+ File.separator;
			String storedImagePath = "/img/members/";

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
						if (item.isFormField()) {
							// regular form field
							String fieldValue = item.getString();
							fields.put(fieldName, fieldValue);
						} else {
							// file upload
							String fileName = item.getName();
							InputStream fileContent = item.getInputStream(); // get image content
							String uploadPath;

							if (fieldName.equals("image") && !fileName.isEmpty()) {
								uploadPath = imagePath + fileName; // image upload destination
								File directory = new File(uploadPath).getParentFile();
								if (!directory.exists()) {
									directory.mkdirs();
								}
								// image file, upload path, replace if exists
								Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
								image = storedImagePath + fileName;
							}
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
					String oldimage = fields.get("oldimage");
					String currentPassword = fields.get("currentPassword");
					String newPassword = fields.get("newPassword");
					Date birth_date = null;

					// checking null and empty values
					if (name != null && !name.isEmpty() && phone != null && !phone.isEmpty() && address != null
							&& !address.isEmpty() && postalCode != null && !postalCode.isEmpty() && email != null
							&& !email.isEmpty()) {

						// test with regular expressions
						if (TestReg.matchEmail(email) && TestReg.matchPhone(phone)
								&& TestReg.matchPostalCode(postalCode)) {

							address += " S" + postalCode;
							if (birthDate != null && !birthDate.isEmpty() && TestReg.matchDate(birthDate)) {
								birth_date = Date.valueOf(LocalDate.parse(birthDate));
							}

							BookDatabase book_db = new BookDatabase();
							book_db.clearBookResult();
							int count = 0;
							char genderChar = 'N';
							if (gender != null && !gender.isEmpty() && TestReg.matchGender(gender) && !gender.equals("N")) {
								genderChar = gender.charAt(0);
							}

							if (status.equals("register")) {
								// [CHECK AUTHENTICATION]
								Authentication auth = new Authentication();
								HttpSession session = request.getSession();
								if (!auth.testAdmin(session)) {
									request.setAttribute("error", "unauthorized");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
									return;
								}
								// [CHECK AUTHENTICATION-END]
								// call function from MemberDatabase
								if (TestReg.matchPassword(password)) {
									int condition = member_db.registerMember(new Member(name, genderChar, birth_date,
											phone, address, email, password, image));
									if (condition == 1) {
										request.setAttribute("success", "register");
										request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
												response);
									} else if (condition == -1) {
										request.setAttribute("errorCode", "invalidEmail");
										request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
												response);
									} else {
										request.setAttribute("errorCode", "serverError");
										request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
												response);
									}
								} else {

								}

							} else if (status.equals("update")) {
								// [CHECK AUTHENTICATION]
								Authentication auth = new Authentication();
								HttpSession session = request.getSession();
								if (!auth.testAdmin(session)) {
									request.setAttribute("error", "unauthorized");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
									return;
								}
								// [CHECK AUTHENTICATION-END]
								if (image == null) {
									if (oldimage != null && !oldimage.isEmpty() && !oldimage.equals("null")) {
										image = oldimage;
									}
								}
								
								//[RETRIEVE PASSWORD]
								String id = fields.get("MemberID");
								if (TestReg.matchInteger(id)) {
									member_db.clearMemberResult();
									
									if (member_db.getMemberByID(Integer.parseInt(id))) {
										ResultSet rs = member_db.getMemberResult();
										try {
											while (rs.next()) {
												// sanitizing output by escaping html special characters
												password = StringEscapeUtils
														.escapeHtml4(rs.getString("Password"));
												break;
											}
											

										} catch (Exception e) {
											request.setAttribute("error", "serverError");
											request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
											return;
										}
									} else {
										request.setAttribute("error", "invalid");
										request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
										return;
									}
								
								// call function from MemberDatabase
								
							}
								int condition = member_db.updateMember(new Member(Integer.parseInt(id), name, genderChar, birth_date, phone,
								address, email, password, image));
								if (condition == 1) {
									request.setAttribute("success", "update");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								} else if (condition == -1) {
									request.setAttribute("errorCode", "invalidEmail");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								} else {
									request.setAttribute("errorCode", "serverError");
									request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
											response);
								}

							} // [PROFILE EDIT]
							else if (status.equals("profileEdit")) {
								// [CHECK USER AUTHENTICATION]
								Authentication auth = new Authentication();
								HttpSession session = request.getSession();

								// [CHECK USER AUTHENTICATION-END]

								String id = session.getAttribute("memberID").toString();
								
								// [CHECK NEW PASS EXISTS]
									if (TestReg.matchInteger(id)) {
										member_db.clearMemberResult();
										boolean condition = member_db.getMemberByID(Integer.parseInt(id));
										if (condition) {
											ResultSet rs = member_db.getMemberResult();
											try {
												while (rs.next()) {
													// sanitizing output by escaping html special characters
													password = StringEscapeUtils
															.escapeHtml4(rs.getString("Password"));
													break;
												}
												if (currentPassword != null && !currentPassword.isEmpty() && newPassword != null
														&& !newPassword.isEmpty()) {
												if (password.equals(currentPassword) && TestReg.matchPassword(newPassword)) {
													password=newPassword;
												}
												}
											} catch (Exception e) {
												request.setAttribute("errCode", "serverError");
												request.getRequestDispatcher("/profile.jsp").forward(request, response);
												return;
											}
										} else {
											request.setAttribute("errCode", "invalid");
											request.getRequestDispatcher("/profile.jsp").forward(request, response);
											return;
										}
									}
								// [CHECK NEW PASS EXIST-END]
								
								if (image == null) {
									if (oldimage != null && !oldimage.isEmpty() && !oldimage.equals("null")) {
										image = oldimage;
									}
								}
								// call function from MemberDatabase
								int condition = member_db.updateMember(new Member(Integer.parseInt(id), name, genderChar, birth_date, phone,
										address, email, password, image));
								if (condition == 1) {
									request.setAttribute("success", "update");
									request.getRequestDispatcher("/profile.jsp").forward(request, response);
								} else if (condition == -1) {
									request.setAttribute("errCode", "invalidEmail");
									request.getRequestDispatcher("/profile.jsp").forward(request, response);
								} else {
									request.setAttribute("errCode", "serverError");
									request.getRequestDispatcher("/profile.jsp").forward(request, response);
								}

							} else {
								// [status loop]
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request,
										response);
								return;
							}
						}
						// [PROFILE EDIT-END]
						else {
							// [status loop]
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
							return;
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					request.setAttribute("error", "upload");
					request.getRequestDispatcher("/admin/memberRegistration.jsp").forward(request, response);
					return;
				}
			}
		} else {
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
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MemberDatabase member_db = new MemberDatabase();
		String requestURi = request.getRequestURI();
		String[] parts = requestURi.split("/");

		if (parts.length == 0) {
			request.setAttribute("error", "invalid");
			request.getRequestDispatcher("/admin/members").forward(request, response);
			return;
		} else {
			String id = parts[parts.length - 1];
			if (TestReg.matchInteger(id)) {
				if (member_db.deleteMember(Integer.parseInt(id))) {
					request.setAttribute("success", "delete");
					request.getRequestDispatcher("/admin/members").forward(request, response);
					return;
				} else {
					request.setAttribute("error", "serverError");
					request.getRequestDispatcher("/admin/members").forward(request, response);
					return;
				}

			} else {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/members").forward(request, response);
				return;
			}
		}
	}
}