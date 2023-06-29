// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 15.6.2023
// Description: middleware for review

package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import model.Review;
import model.ReviewDatabase;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/admin/reviews")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewServlet() {
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
		// create review_db object and clear the result
		ReviewDatabase review_db = new ReviewDatabase();
		review_db.clearReviewResult();
		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}

		String requestURi = request.getRequestURI();
		if (requestURi.endsWith("admin/reviews")) {

			ArrayList<Review> reviewList = new ArrayList<Review>();

			if (review_db.getPendingReview()) {
				ResultSet rs = review_db.getReviewResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						reviewList.add(new Review(rs.getInt("ReviewID"),
								StringEscapeUtils.escapeHtml4(rs.getString("Title")),
								StringEscapeUtils.escapeHtml4(rs.getString("Description")), rs.getDate("ReviewDate"),
								rs.getShort("Rating"), StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
								rs.getInt("MemberID"), StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverRetrieveError");
				}
			} else {
				request.setAttribute("error", "serverRetrieveError");
			}

			// set the author arraylist as an attribute
			request.setAttribute("reviewList", reviewList);
			request.setAttribute("servlet", "true");

			// forward the data to the jsp
			request.getRequestDispatcher("/admin/reviewList.jsp").forward(request, response);
			return;
		} else {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
