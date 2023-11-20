package UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;

public class RegisterService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// regiter.do 요청에 의한 처리 부분
		return "register";
	}
	
}
