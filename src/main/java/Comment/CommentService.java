package Comment;
	
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
	
public class CommentService implements CommandHandler {
	
	String viewPage = null;
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 댓글 최신화 수행
		// 댓글 작성 수행
		// 댓글 수정 페이지 이동
		// 댓글 수정 수행
		// 댓글 삭제 수행
		
		String userID = null;
        int classID = 0;
        int boardID = 0;
        int commentID = 0;
        String commentContent = null;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		} if(request.getParameter("classID") != null) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        } if(request.getParameter("boardID") != null) {
        	boardID = Integer.parseInt(request.getParameter("boardID"));
        } if(request.getParameter("commentID") != null) {
        	commentID = Integer.parseInt(request.getParameter("commentID"));
        } if(request.getParameter("commentContent") != null || request.getParameter("commentContent") != "") {
        	commentContent = request.getParameter("commentContent");
        }
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "commentReflash.do": {
				viewPage = commentReplash(request, userID, classID, boardID);	// 댓글 최신화 수행
				break;
			} case "commentWrite.do" : {
				viewPage = commentWriteAction(request, userID, classID, boardID, commentContent);	// 댓글 작성 수행
				break;
			} case "commentModifyConfirm.do" : {
				viewPage = commentModifyConfirm(request, userID, classID, boardID, commentID, commentContent);	// 댓글 수정 페이지 이동
				break;
			}case "commentModifyAction.do": {
				viewPage = commentModifyAction(request, userID, classID, boardID, commentID, commentContent);	// 댓글 수정 수행
				break;
			} case "commentDelete.do": {
				viewPage = commentDeleteAction(request, userID, classID, boardID, commentID);	// 댓글 삭제 수행
				break;
			}
		}
		return viewPage;
    }
    
    private String commentReplash(HttpServletRequest request, String userID, int classID, int boardID) {
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
		
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
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
        
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "commentReflash";
    }
    
    private String commentWriteAction(HttpServletRequest request, String userID, int classID, int boardID, String commentContent) {
    	// userType가져오기
        UserDAO DAO = new UserDAO();
		String userType = DAO.getUserType(userID);
		String userName = DAO.getUserName(userID);
        
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
		
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
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
		if (!comment_dao.isCommentExists(boardID, userID, commentContent)) {
		    comment_dao.insertData(new CommentDTO(boardID, userID, userName, commentContent));
		}
		
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
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "lectureRoomNoticeInfo";
    }
    
    private String commentModifyConfirm(HttpServletRequest request, String userID, int classID, int boardID, int commentID, String commentContent) {
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
		
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
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
        
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("commentID", commentID);
        request.setAttribute("commentContent", commentContent);
        
        return "commentModify";
    }
    
    private String commentModifyAction(HttpServletRequest request, String userID, int classID, int boardID, int commentID, String commentContent) {
    	
    	commentContent = commentContent.replaceAll("\\n", "<br>");
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
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
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
		comment_dao.updateData(new CommentDTO(commentID, commentContent));
		
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
        
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "commentReflash";
    }
    
    private String commentDeleteAction(HttpServletRequest request, String userID, int classID, int boardID, int commentID) {
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
		
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
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
		comment_dao.deleteComment(commentID);
		System.out.println(commentID);
		
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
        
        
        request.setAttribute("commentInfoList", commentInfoList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        
        return "commentReflash";
    }
}
