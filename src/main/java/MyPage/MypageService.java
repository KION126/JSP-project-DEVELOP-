package MyPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		// 마이페이지 이동
		// userID, userEmail, userName, userType 전달
		// 수강중인 강의 리스트 전달
		
		String userID = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		System.out.println(session.getAttribute("userID"));
		System.out.println(userID);
		// userID로 사용자 정보 가져오기
		UserDAO DAO = new UserDAO();
		String userEmail = DAO.getUserEmail(userID);
		String userName = DAO.getUserName(userID);
		String userType = DAO.getUserType(userID);
		
		// 회원이 수강중인 강의들의 classID 가져오기
		EnrolDAO enrol_dao = new EnrolDAO();
		List<EnrolDTO> myClassIDs = enrol_dao.getMyClassID(userID);
		
		// classID로 강의 정보 가져오기
		ClassDAO class_dao = new ClassDAO();
		List<List<ClassDTO>> myClassInfosList = new ArrayList<>();
		for (EnrolDTO myClassID : myClassIDs) {
		    List<ClassDTO> myClassInfos = class_dao.getClassInfos(myClassID.getClassID());
		    myClassInfosList.add(myClassInfos);
		}
		
		request.setAttribute("userID", userID);
		request.setAttribute("userEmail", userEmail);
		request.setAttribute("userName", userName);
		request.setAttribute("userType", userType);
		request.setAttribute("myClassInfosList", myClassInfosList);

		return "myPage";
	}
}