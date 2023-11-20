package LectureRoomNoticeService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Lecture.ClassDAO;
import Lecture.ClassDTO;
import User.UserDAO;

public class LectureRoomNoticeModifyAction implements CommandHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	System.out.println("asdsadasdasdasdsa");
		int boardID = 0;
    	String userID = null;
        int classID = 0;
        String boardTitle = null;
        String boardContent = null;
        String boardFile = null;
        String boardRealFile = null;

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
 		BoardDTO notice = new BoardDTO(1, boardID, boardTitle, boardContent, boardFile, boardRealFile);
 		notice_dao.updateData(notice);
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