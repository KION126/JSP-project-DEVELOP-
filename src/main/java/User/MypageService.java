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

public class MypageService implements CommandHandler{
	String viewPage = null;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String userID = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "myPage.do": {
				viewPage = myPage(request, userID);		// 마이페이지 이동
				break;
			} case "myInterest.do" : {
				viewPage = myInterest(request);		// 관심분야설정 페이지 이동
				break;
			} case "myUserEdit.do" : {
				viewPage = myUserEdit(request);		// 게인정보관리 페이지 이동
				break;
			}
		}
			
		// userID로 사용자 정보 가져오기
		UserDAO DAO = new UserDAO();
		String userEmail = DAO.getUserEmail(userID);
		String userName = DAO.getUserName(userID);
		String userType = DAO.getUserType(userID);

		request.setAttribute("userID", userID);
		request.setAttribute("userEmail", userEmail);
		request.setAttribute("userName", userName);
		request.setAttribute("userType", userType);
		
		return viewPage;
	}
		
	private String myPage(HttpServletRequest request, String userID) {
		
		// 회원이 수강중인 강의들의 classID 가져오기
		EnrolDAO enrol_dao = new EnrolDAO();
		List<EnrolDTO> myClassIDs = enrol_dao.getMyClassID(userID);
		
		// classID로 강의 정보 가져오기
		LectureDAO class_dao = new LectureDAO();
		List<List<LectureDTO>> myClassInfosList = new ArrayList<>();
		for (EnrolDTO myClassID : myClassIDs) {
		    List<LectureDTO> myClassInfos = class_dao.getClassInfos(myClassID.getClassID());
		    myClassInfosList.add(myClassInfos);
		}
		
		request.setAttribute("myClassInfosList", myClassInfosList);
		
		return "myPage";
	}
	
	private String myInterest(HttpServletRequest request) {
		
		return "myInterest";
	}
	
	private String myUserEdit(HttpServletRequest request) {
		
		return "myUserEdit";
	}
}