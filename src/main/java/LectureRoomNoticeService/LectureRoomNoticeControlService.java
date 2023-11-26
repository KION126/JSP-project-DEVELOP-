package LectureRoomNoticeService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Comment.CommentDAO;
import Enrol.EnrolDAO;
import Lecture.LectureDAO;
import Lecture.LectureDTO;
import User.UserDAO;

public class LectureRoomNoticeControlService implements CommandHandler {
	String viewPage = null;
	
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 강의실공지 작성 페이지 이동
		// 강의실공지 작성 수행
		// 강의실공지 수정 수행
		// 강의실공지 삭제 수행
		
		String userID = null;
        int classID = 0;
        int boardID = 0;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
        if(request.getParameter("classID") != null) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
        if(request.getParameter("boardID") != null) {
        	boardID = Integer.parseInt(request.getParameter("boardID"));
        }
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "lectureRoomNoticeWrite.do": {
				viewPage = lectureRoomNoticeWrite(request, userID, classID, boardID);	// 강의실공지 작성 페이지
				break;
			} case "lectureRoomNoticeWriteAction.do" : {
				viewPage = lectureRoomNoticeWriteAction(request, userID, classID);	//강의실공지 작성 수행
				break;
			} case "lectureRoomNoticeModifyConfirm.do" : {
				viewPage = lectureRoomNoticeWrite(request, userID, classID, boardID);	// 강의실공지 작성 페이지(수정페이지)
				break;
			}case "lectureRoomNoticeModifyAction.do": {
				viewPage = lectureRoomNoticeModifyAction(request, userID, classID, boardID);	//강의실공지 수정 수행
				break;
			} case "lectureRoomNoticeDeleteAction.do": {
				viewPage = lectureRoomNoticeDeleteAction(request, userID, classID, boardID);	//가으이실공지 삭제 수행
				break;
			}
		}
		
		return viewPage;
    }

	private String lectureRoomNoticeWrite(HttpServletRequest request, String userID, int classID, int boardID) {
		String boardTitle = null;
        String boardContent = null;
        String boardFile = null;
        int boardType = 0;
    	
    	if(request.getParameter("boardType") != null) {
    		boardType = Integer.parseInt(request.getParameter("boardType"));
    	}
        
    	BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfoList = notice_dao.getDate(boardID);
		for (BoardDTO noticeInfo : noticeInfoList) {
			boardTitle = noticeInfo.getboardTitle();
			boardContent = noticeInfo.getboardContent();
			boardFile = noticeInfo.getboardFile();
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
        
		request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("boardID", boardID);
        request.setAttribute("boardTitle", boardTitle);
        request.setAttribute("boardContent", boardContent);
        request.setAttribute("boardFile", boardFile);
        System.out.println(boardType);
        request.setAttribute("boardType", boardType);
        
        return "lectureRoomNoticeWrite";
    }
	
	private String lectureRoomNoticeWriteAction(HttpServletRequest request, String userID, int classID) {
		String boardTitle = null;
        String boardContent = null;
        String boardFile = null;
        String boardRealFile = null;
        int boardType = 0;

        String savepath = request.getRealPath("/upload").replace("\\\\", "/");
        System.out.println(savepath);
        File uploadDir = new File(savepath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리가 없으면 만듭니다
        }

        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;

        try {
            multi = new MultipartRequest(request, savepath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

        File file = multi.getFile("boardFile");

        if (file != null) {
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile = file.getName();
        }
        
        if(multi.getParameter("userID") != null || multi.getParameter("userID") != "") {
        	userID = multi.getParameter("userID");
        }
        String classIDParameter = multi.getParameter("classID");
        if(classIDParameter != null && !classIDParameter.isEmpty()) {
            classID = Integer.parseInt(classIDParameter);
        }
        if(multi.getParameter("boardTitle") != null || multi.getParameter("boardTitle") != "") {
        	boardTitle = multi.getParameter("boardTitle");
        }
        if(multi.getParameter("boardContent") != null || multi.getParameter("boardContent") != "") {
        	boardContent = multi.getParameter("boardContent");
        }
        if(multi.getParameter("boardType") != null) {
        	boardType = Integer.parseInt(multi.getParameter("boardType"));
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
 		BoardDTO notice = new BoardDTO(boardType, userID, classID, boardTitle, boardContent, 0, boardFile, boardRealFile);
 		notice_dao.insertData(notice);
 		List<BoardDTO> noticeInfoList = notice_dao.getLists(boardType, classID, startRow, recordsPerPage);
 		
 		for (BoardDTO noticeInfo : noticeInfoList) {
             String userName = DAO.getUserName(noticeInfo.getUserID());
             noticeInfo.setUserName(userName);
         }
 		
 		// 페이징 처리를 위한 전체 공지사항 수 조회
 		int totalRecords = notice_dao.getTotalRecords(boardType, classID);
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
        request.setAttribute("boardType", boardType);
        
        return "lectureRoomNotice";
	}
	
	private String lectureRoomNoticeModifyAction(HttpServletRequest request, String userID, int classID, int boardID) {
		String boardTitle = null;
        String boardContent = null;
        String boardFile = null;
        String boardRealFile = null;
        int boardType = 0;

        BoardDAO notice_dao = new BoardDAO();
        
        String savepath = request.getRealPath("/upload").replace("\\\\", "/");
        File uploadDir = new File(savepath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리가 없으면 만듭니다
        }

        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;

        try {
            multi = new MultipartRequest(request, savepath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }

        if(multi.getParameter("userID") != null || multi.getParameter("userID") != "") {
        	userID = multi.getParameter("userID");
        }
        String classIDParameter = multi.getParameter("classID");
        if(classIDParameter != null && !classIDParameter.isEmpty()) {
            classID = Integer.parseInt(classIDParameter);
        }
        if(multi.getParameter("boardTitle") != null || multi.getParameter("boardTitle") != "") {
        	boardTitle = multi.getParameter("boardTitle");
        }
        if(multi.getParameter("boardContent") != null || multi.getParameter("boardContent") != "") {
        	boardContent = multi.getParameter("boardContent");
        }
        String boardIDParameter = multi.getParameter("boardID");
        if(boardIDParameter != null && !boardIDParameter.isEmpty()) {
            boardID = Integer.parseInt(boardIDParameter);
        }
        String boardTypeParameter = multi.getParameter("boardType");
        if(boardTypeParameter != null && !boardTypeParameter.isEmpty()) {
        	boardType = Integer.parseInt(boardTypeParameter);
        }

        File file = multi.getFile("boardFile");

        if (file != null) {
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile = file.getName();
            String prev = notice_dao.getRealFile(boardID);
            File prevFile = new File(savepath + "/" + prev);
            if(prevFile.exists()) {
            	prevFile.delete();
            }
        } else {	
        	boardFile = notice_dao.getFile(boardID);
        	boardRealFile = notice_dao.getRealFile(boardID);
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
 		System.out.println(boardType);
 		int startRow = (currentPage - 1) * recordsPerPage;		
 		BoardDTO notice = new BoardDTO(boardType, boardID, boardTitle, boardContent, boardFile, boardRealFile);
 		notice_dao.updateData(notice);
 		List<BoardDTO> noticeInfoList = notice_dao.getLists(boardType, classID, startRow, recordsPerPage);
 		
 		for (BoardDTO noticeInfo : noticeInfoList) {
             String userName = DAO.getUserName(noticeInfo.getUserID());
             noticeInfo.setUserName(userName);
         }
 		
 		// 페이징 처리를 위한 전체 공지사항 수 조회
 		int totalRecords = notice_dao.getTotalRecords(boardType, classID);
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
        request.setAttribute("boardType", boardType);
        
        return "lectureRoomNotice";	
	}
	
	private String lectureRoomNoticeDeleteAction(HttpServletRequest request, String userID, int classID, int boardID) {
		int boardType = 0;
    	
    	if(request.getParameter("boardType") != null) {
    		boardType = Integer.parseInt(request.getParameter("boardType"));
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
 		notice_dao.deleteNotice(boardID);	
 		
 		CommentDAO comment_dao = new CommentDAO();
 		comment_dao.deleteBoardComment(boardID);
		
		String savepath = request.getRealPath("/upload").replace("\\\\", "/");
        String prev = notice_dao.getRealFile(boardID);
        File prevFile = new File(savepath + "/" + prev);
        if(prevFile.exists()) {
        	prevFile.delete();
        }
 		
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
        request.setAttribute("boardType", boardType);
        
        return "lectureRoomNotice";
	}	
}
