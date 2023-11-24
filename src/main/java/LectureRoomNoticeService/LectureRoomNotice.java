package LectureRoomNoticeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Comment.CommentDAO;
import Comment.CommentDTO;
import Enrol.EnrolDAO;
import Lecture.LectureDAO;
import Lecture.LectureDTO;
import User.UserDAO;

public class LectureRoomNotice implements CommandHandler {
	String viewPage = null;

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 강의실공지, 강의실공지상세 페이지 이동
		// 강의실공지검색 수행
		
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
			case "lectureRoomNotice.do": {
				viewPage = lectureRoomNotice(request, userID, classID);
				break;
			} case "lectureRoomNoticeInfo.do": {
				viewPage = lectureRoomNoticeInfo(request, userID, classID);
				break;
			} case "lectureRoomNoticeSearch.do": {
				viewPage = lectureRoomNoticeSearch(request, userID, classID);
				break;
			}
		}
		
		return viewPage;
    }
    
    private String lectureRoomNotice(HttpServletRequest request, String userID, int classID) {
    	// userType가져오기
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
    
    private String lectureRoomNoticeInfo(HttpServletRequest request, String userID, int classID) {
    	int boardID = 0;
        
        if(Integer.parseInt(request.getParameter("boardID")) > 0) {
        	boardID = Integer.parseInt(request.getParameter("boardID"));
        }

        // userType가져오기
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
    	
    	// 해당 Notice 정보 가져오기
    	BoardDAO notice_dao = new BoardDAO();
    	List<BoardDTO> noticeInfoList = notice_dao.getDate(boardID);
    	notice_dao.hit(boardID);
    	
    	String not_Title = null;
    	String not_userID = null;
    	String not_userName = null;
    	String not_Date = null;
    	String not_Con = null;
    	String not_File = null;
    	int not_Hit = 0;
    	
    	for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
    		not_userID = noticeInfo.getUserID();
    		not_userName = DAO.getUserName(not_userID);
    		not_Date = noticeInfo.getboardDate();
    		not_Con = noticeInfo.getboardContent();
    		not_File = noticeInfo.getboardFile();
    		not_Hit = noticeInfo.getboardHit();
        }
    	
    	boolean userIDeqboardID = false;
    	if(not_userID.equals(userID)) {
    		userIDeqboardID = true;
    	}
    	
    	// 공지게시판 페이징
    	int currentPage = 1;
    	int recordsPerPage = 5; // 페이지당 표시할 공지사항 수
    	
    	if (request.getParameter("currentPage") != null) {
    	    currentPage = Integer.parseInt(request.getParameter("currentPage"));
    	}
    	
    	int startRow = (currentPage - 1) * recordsPerPage;		
    	CommentDAO comment_dao = new CommentDAO();
    	List<CommentDTO> commentInfoList = comment_dao.getLists(boardID, startRow, recordsPerPage);
    	
    	// 페이징 처리를 위한 전체 공지사항 수 조회
    	int totalRecords = comment_dao.getTotalRecords(boardID);
    	int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
    	request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
        request.setAttribute("userIDeqboardID", userIDeqboardID);
    	
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("boardID", boardID);
        request.setAttribute("not_Title", not_Title);
        request.setAttribute("not_userName", not_userName);
        request.setAttribute("not_Date", not_Date);
        request.setAttribute("not_Con", not_Con);
        request.setAttribute("not_File", not_File);
        request.setAttribute("not_Hit", not_Hit);
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "lectureRoomNoticeInfo";
	}
    
    private String lectureRoomNoticeSearch(HttpServletRequest request, String userID, int classID) {
    	String searchOption = null;
        String searchKeyword = null;
        
        if(request.getParameter("searchOption") != null || request.getParameter("searchOption") != "") {
        	searchOption = request.getParameter("searchOption");
        }
        if(request.getParameter("searchKeyword") != null || request.getParameter("searchKeyword") != "") {
        	searchKeyword = request.getParameter("searchKeyword");
        }
        
        // userType가져오기
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
		
		// 공지게시판 페이징
		int currentPage = 1;
		int recordsPerPage = 5; // 페이지당 표시할 공지사항 수
		
		if (request.getParameter("currentPage") != null) {
		    currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int startRow = (currentPage - 1) * recordsPerPage;	

		BoardDAO notice_dao = new BoardDAO();	
		List<BoardDTO> noticeInfoList = notice_dao.searchNotices(1, classID, searchOption, searchKeyword, startRow, recordsPerPage);
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            String userName = DAO.getUserName(noticeInfo.getUserID());
            noticeInfo.setUserName(userName);
        }
		
		// 페이징 처리를 위한 전체 공지사항 수 조회
		int totalRecords = notice_dao.getSearchTotalRecords(1, classID, searchOption, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
		request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("noticeInfoList", noticeInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchOption", searchOption);
        request.setAttribute("searchKeyword", searchKeyword);
        
        return "lectureRoomNoticeSearch";
    }
}
