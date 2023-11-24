package Lecture;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import Lecture.LectureDAO;
import Lecture.LectureDTO;
import User.UserDAO;

public class LectureService implements CommandHandler {
	String viewPage = null;
	
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 강의정보, 강의실 페이지 이동
		// 수강신청 수행
		
		String userID = null;
        int classID = 0;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
        
        if(Integer.parseInt(request.getParameter("classID")) > 0) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "lectureInfo.do": {
				viewPage = lectureInfo(request, userID, classID);
				break;
			} case "lectureEnrol.do": {
				viewPage = lectureEnrol(request, userID, classID);
				break;
			} case "lectureRoom.do": {
				viewPage = lectureRoom(request, userID, classID);
				break;
			}
		}
		
		return viewPage;
    }
    
    private String lectureInfo(HttpServletRequest request, String userID, int classID) {
    	// 해당 강좌의 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
        LectureDTO classInfos = class_dao.getClassInfo(classID);

        // 강좌 정보 전달
        request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("img", classInfos.getClassImg());
        request.setAttribute("title", classInfos.getClassTitle());
        request.setAttribute("thema", classInfos.getClassThema());
        request.setAttribute("pro", classInfos.getClassProfessor());
        request.setAttribute("con", classInfos.getClassContent());
        
        // 해당 강좌를 수강중인지 확인여부 전달
        EnrolDAO enrol_dao = new EnrolDAO();
		boolean classEnrolChecked = enrol_dao.classEnrolCheck(userID, classID);
		request.setAttribute("classEnrolChecked", classEnrolChecked);

		// 이메일 인증을 한 회원인지 확인여부 전달
		UserDAO user_dao = new UserDAO();
		boolean userEmailChecked = user_dao.getUserEmailChecked(userID);
		request.setAttribute("userEmailChecked", userEmailChecked);
		
    	return "lectureInfo";
    }
    
    private String lectureEnrol(HttpServletRequest request, String userID, int classID) {
    	boolean NewEnrol = true;
    	
    	// enrol테이블에 insert하여 수강신청
    	EnrolDAO enrol_dao = new EnrolDAO();
        enrol_dao.enrolClass(userID, classID);

        // 해당 강좌의 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
        LectureDTO classInfos = class_dao.getClassInfo(classID);

        // 해당 강좌를 수강중인지 확인여부 전달
        boolean classEnrolChecked = enrol_dao.classEnrolCheck(userID, classID);

        // 이메일 인증을 한 회원인지 확인여부 전달
        UserDAO user_dao = new UserDAO();
        boolean userEmailChecked = user_dao.getUserEmailChecked(userID);
        
        request.setAttribute("NewEnrol", NewEnrol);
        
        request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        
        request.setAttribute("img", classInfos.getClassImg());
        request.setAttribute("title", classInfos.getClassTitle());
        request.setAttribute("thema", classInfos.getClassThema());
        request.setAttribute("pro", classInfos.getClassProfessor());
        request.setAttribute("con", classInfos.getClassContent());

        request.setAttribute("classEnrolChecked", classEnrolChecked);
        request.setAttribute("userEmailChecked", userEmailChecked);
    	
    	return "lectureInfo";
    }
    
    private String lectureRoom(HttpServletRequest request, String userID, int classID) {
    	//userType가져오기
        UserDAO DAO = new UserDAO();
		String userType = DAO.getUserType(userID);
        
        // lecture 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
		List<LectureDTO> classInfos = class_dao.getClassInfos(classID);
		String lec_title = null;
		String lec_pro = null;
		for (LectureDTO classInfo : classInfos) {
			lec_title = classInfo.getClassTitle();
			lec_pro = classInfo.getClassProfessor();
		}
		
		// notice정보 가져오기
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfos = notice_dao.getLists(1, classID,0,3);
		
        request.setAttribute("userID", userID); 
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
        request.setAttribute("lectuerType", "lecture"+Integer.toString(classID));
        
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("noticeInfos", noticeInfos);
    	
    	return "lectureRoom";
    }
}
