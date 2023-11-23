package Lecture;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CommandHandler.CommandHandler;

public class LectureSearch implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// lectureSearch.do 요청에 의한 처리 부분
		String userID = null;
		String keyword = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		// 전체 검색을 위해 null예외처리 하지 않음
		keyword = request.getParameter("keyword");
		
		// keyword로 강의 리스트 가져오기
		ClassDAO dao = new ClassDAO();
	    List<ClassDTO> classInfoList = dao.searchClassesByKeyword(keyword);
		
	    request.setAttribute("userID", userID);
		request.setAttribute("classInfoList", classInfoList);
		
		return "lectureSearch";
	}
}
