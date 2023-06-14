// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: To test session authentication

package controller;

import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import model.AdminDatabase;

public class Authentication {
	public Authentication() {
	}
	
	public boolean testAdmin(HttpSession session) {
		String adminID = (String)session.getAttribute("adminID");
		String role = (String)session.getAttribute("role");
		AdminDatabase admin_db = new AdminDatabase();
		admin_db.clearAdminResult();
		
		if(role != null && role.equals("admin") && adminID != null && TestReg.matchInteger(adminID)) {
			if(admin_db.getAdminByID(Integer.parseInt(adminID))) {
				ResultSet rs = admin_db.getAdminResult();
				int count = 0;
				try {
					while(rs.next()) {
						count++;
					}
				}
				catch(Exception e) {
					session.invalidate();
					return false;
				}
				if(count == 1) {
					return true;
				}
			}
		}
		session.invalidate();
		return false;
	}
	
	// unfinished
	public boolean testMember(HttpSession session) {
		String memberID = (String)session.getAttribute("memberID");
		String role = (String)session.getAttribute("role");
		AdminDatabase admin_db = new AdminDatabase();
		admin_db.clearAdminResult();
		
		if(role != null && role.equals("member") && memberID != null && TestReg.matchInteger(memberID)) {
			if(admin_db.getAdminByID(Integer.parseInt(memberID))) {
				ResultSet rs = admin_db.getAdminResult();
				int count = 0;
				try {
					while(rs.next()) {
						count++;
					}
				}
				catch(Exception e) {
					session.invalidate();
					return false;
				}
				if(count == 1) {
					return true;
				}
			}
		}
		session.invalidate();
		return false;
	}
}