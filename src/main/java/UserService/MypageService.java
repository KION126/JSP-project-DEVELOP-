package UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import Enrol.EnrolDTO;
import Lecture.ClassDAO;
import Lecture.ClassDTO;
import User.UserDAO;

public class MypageService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// myPage.do 요청에 의한 처리 부분
		String userID = null;
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		UserDAO DAO = new UserDAO();
		String userEmail = DAO.getUserEmail(userID);
		String userName = DAO.getUserName(userID);
		String userType = DAO.getUserType(userID);
		
		// 회원이 강의신청한 강좌ID들 가져오기
		EnrolDAO enrol_dao = new EnrolDAO();
		List<EnrolDTO> myClassIDs = enrol_dao.getMyClassID(userID);
		
		ClassDAO class_dao = new ClassDAO();
		
		// 강좌ID로 강좌 정보들 가져오기
		List<List<ClassDTO>> myClassInfosList = new ArrayList<>();
		for (EnrolDTO myClassID : myClassIDs) {
		    List<ClassDTO> myClassInfos = class_dao.getClassInfos(myClassID.getClassID());
		    myClassInfosList.add(myClassInfos);
		}

		request.setAttribute("myClassInfosList", myClassInfosList);
		request.setAttribute("userEmail", userEmail);
		request.setAttribute("userName", userName);
		request.setAttribute("userType", userType);
		request.setAttribute("userID", userID);
		
		return "myPage";
	}
}