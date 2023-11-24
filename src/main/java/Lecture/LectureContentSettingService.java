package Lecture;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import User.UserDAO;

public class LectureContentSettingService implements CommandHandler {

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
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        return "lectureContentSetting";
    }
}
