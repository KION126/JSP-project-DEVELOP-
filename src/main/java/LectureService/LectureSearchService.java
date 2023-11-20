package LectureService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;
import Lecture.ClassDAO;
import Lecture.ClassDTO;

public class LectureSearchService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getParameter("userID"));
		
		// lectureSearch.do 요청에 의한 처리 부분
		String userID = request.getParameter("userID");
		String keyword = request.getParameter("keyword");
		
		// 검색된 강좌 정보 가져오기.
		ClassDAO dao = new ClassDAO();
	    List<ClassDTO> classInfoList = dao.searchClassesByKeyword(keyword);
		
	    request.setAttribute("userID", userID);
		request.setAttribute("classInfoList", classInfoList);
		
		return "lectureSearch";
	}
}
