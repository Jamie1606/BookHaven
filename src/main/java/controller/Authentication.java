// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: To test session authentication

package controller;

import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import model.AdminDatabase;
import model.MemberDatabase;

public class Authentication {
	public Authentication() {
	}
	
	public boolean testAdmin(HttpSession session) {
		if(session == null || session.isNew()) {
			return false;
		}
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
	

	public boolean testMember(HttpSession session) {
		if(session == null || session.isNew()) {
			return false;
		}
		String memberID = (String)session.getAttribute("memberID");
		String role = (String)session.getAttribute("role");
		MemberDatabase member_db = new MemberDatabase();
		member_db.clearMemberResult();
		
		if(role != null && role.equals("member") && memberID != null && TestReg.matchInteger(memberID)) {
			if(member_db.getMemberByID(Integer.parseInt(memberID))) {
				ResultSet rs = member_db.getMemberResult();
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