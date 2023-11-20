package LectureService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import Lecture.ClassDAO;
import Lecture.ClassDTO;
import User.UserDAO;

public class LectureInfoService implements CommandHandler {

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
        
        // 해당 강좌의 정보 가져오기
        ClassDAO class_dao = new ClassDAO();
        ClassDTO classInfos = class_dao.getClassInfo(classID);

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
}
