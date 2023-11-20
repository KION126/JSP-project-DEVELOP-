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

public class LectureRoomNoticeWriteService implements CommandHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
    	String userID = null;
        int classID = 0;
        int boardID = 0;
        String boardTitle = null;
        String boardContent = null;
        String boardFile = null;
        
        if(request.getParameter("userID") != null || request.getParameter("userID") != "") {
        	userID = request.getParameter("userID");
        }
        
        if(Integer.parseInt(request.getParameter("classID")) > 0) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
        
        if(request.getParameter("boardID") != null) {
        	boardID = Integer.parseInt(request.getParameter("boardID"));
        	BoardDAO notice_dao = new BoardDAO();
    		List<BoardDTO> noticeInfoList = notice_dao.getDate(boardID);
    		for (BoardDTO noticeInfo : noticeInfoList) {
    			boardTitle = noticeInfo.getboardTitle();
    			boardContent = noticeInfo.getboardContent();
    			boardFile = noticeInfo.getboardFile();
            }
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
        
		request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("boardID", boardID);
        request.setAttribute("boardTitle", boardTitle);
        request.setAttribute("boardContent", boardContent);
        request.setAttribute("boardFile", boardFile);
        
        return "lectureRoomNoticeWrite";
    }
}
