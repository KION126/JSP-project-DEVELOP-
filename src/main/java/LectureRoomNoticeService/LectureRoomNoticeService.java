package LectureRoomNoticeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import Lecture.ClassDAO;
import Lecture.ClassDTO;
import User.UserDAO;

public class LectureRoomNoticeService implements CommandHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
    	String userID = null;
        int classID = 0;
        
        if(request.getParameter("userID") != null || request.getParameter("userID") != "") {
        	userID = request.getParameter("userID");
        }
        
        if(Integer.parseInt(request.getParameter("classID")) > 0) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
        
        // userType가져오기
        UserDAO DAO = new UserDAO();
		String userType = DAO.getUserType(userID);
        
		// lecture 정보 가져오기
        ClassDAO class_dao = new ClassDAO();
		List<ClassDTO> classInfos = class_dao.getClassInfos(classID);
		String lec_title = null;
		String lec_pro = null;
		for (ClassDTO classInfo : classInfos) {
			lec_title = classInfo.getClassTitle();
			lec_pro = classInfo.getClassProfessor();
		}
		
		// 공지게시판 페이징
		int currentPage = 1;
		int recordsPerPage = 5; // 페이지당 표시할 공지사항 수
		
		if (request.getParameter("currentPage") != null) {
		    currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int startRow = (currentPage - 1) * recordsPerPage;		
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfoList = notice_dao.getLists(1, classID, startRow, recordsPerPage);
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            String userName = DAO.getUserName(noticeInfo.getUserID());
            noticeInfo.setUserName(userName);
        }
		
		// 페이징 처리를 위한 전체 공지사항 수 조회
		int totalRecords = notice_dao.getTotalRecords(1, classID);
		int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

		request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
        request.setAttribute("lectuerType", "lecture"+Integer.toString(classID));
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("noticeInfoList", noticeInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "lectureRoomNotice";
    }
}
