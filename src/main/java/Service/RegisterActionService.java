package Service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;

public class RegisterActionService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// login.do 요청에 의한 처리 부분
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String userID = request.getParameter("userID");
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		String userPasswordRe = request.getParameter("userPasswordRe");
		String userName = request.getParameter("userName");
		
		request.setAttribute("userID", userID);
		request.setAttribute("userEmail", userEmail);
		request.setAttribute("userPassword", userPassword);
		request.setAttribute("userPasswordRe", userPasswordRe);
		request.setAttribute("userName", userName);
		
		return "registerAction";
	}
	
}
