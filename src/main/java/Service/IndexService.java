package Service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;

public class IndexService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// login.do 요청에 의한 처리 부분
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// myPage.do 요청에 의한 처리 부분
		String userID = null;
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		request.setAttribute("userID", userID);
		
		return "index";
	}
	
}
