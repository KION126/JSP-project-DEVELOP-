package User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import Enrol.EnrolDTO;
import Lecture.LectureDAO;
import Lecture.LectureDTO;

public class PasswordService implements CommandHandler{
	String viewPage = null;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "passwordFindConfirm.do": {
				viewPage = passwordFindConfirm(request);		// 비밀번호 찾기 페이지 이동
				break;
			} case "passwordFindEmailCheck.do" : {
				viewPage = passwordFindEmailCheck(request);			// 이메일 인증 페이지 이동
				break;
			} case "passwordFindChange.do" : {
				viewPage = passwordFindChange(request);		// 비밀번호 변경 페이지 이동
				break;
			} 
		}
		
		return viewPage;
	}
		
	private String passwordFindConfirm(HttpServletRequest request) {
		
		
		return "passwordFindConfirm";
	}
	
	private String passwordFindEmailCheck(HttpServletRequest request) {
		
		return "passwordFindEmailCheck";
	}
	
	private String passwordFindChange(HttpServletRequest request) {
		
		return "passwordFindChange";
	}
}