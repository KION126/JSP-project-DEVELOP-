	package Comment;
	
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
	
	public class CommentWriteService implements CommandHandler {
	
	    @Override
	    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
	    	response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
	    	
	    	String userID = null;
	        int classID = 0;
	        int boardID = 0;
	        
	        String commentContent = null;
	        
	        if(request.getParameter("userID") != null || request.getParameter("userID") != "") {
	        	userID = request.getParameter("userID");
	        }
	        if(Integer.parseInt(request.getParameter("classID")) > 0) {
	        	classID = Integer.parseInt(request.getParameter("classID"));
	        }
	        if(Integer.parseInt(request.getParameter("boardID")) > 0) {
	        	boardID = Integer.parseInt(request.getParameter("boardID"));
	        }
	        if(request.getParameter("commentContent") != null || request.getParameter("commentContent") != "") {
	        	commentContent = request.getParameter("commentContent");
	        }
	        
	        // userType가져오기
	        UserDAO DAO = new UserDAO();
			String userType = DAO.getUserType(userID);
			String userName = DAO.getUserName(userID);
	        
	        // lecture 정보 가져오기
	        ClassDAO class_dao = new ClassDAO();
			List<ClassDTO> classInfos = class_dao.getClassInfos(classID);
			String lec_title = null;
			String lec_pro = null;
			for (ClassDTO classInfo : classInfos) {
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
			if (!comment_dao.isCommentExists(boardID, userID, commentContent)) {
			    comment_dao.insertDate(new CommentDTO(boardID, userID, userName, commentContent));
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
	        request.setAttribute("not_Hit", not_Hit);
	        
	        
	        request.setAttribute("commentInfoList", commentInfoList);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("totalPages", totalPages);
	        
	        return "lectureRoomNoticeInfo";
	    }
	}
