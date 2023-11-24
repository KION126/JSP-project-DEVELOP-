package User;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CommandHandler.CommandHandler;

public class IndexService implements CommandHandler{
	String viewPage = null;
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {		
		// 메인, 로그인 페이지 이동
		// 로그아웃 수행
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "index.do": {
				viewPage = indexDO(request);
				break;
			} case "login.do": {
				viewPage = loginDO();
				break;
			} case "logout.do": {
				viewPage = logoutDO(request);
				break;
			}
		}
		
		return viewPage;
	}
	
	// 메인페이지 이동
	private String indexDO(HttpServletRequest request) {
		// userID 세션값 전달
		String userID = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		request.setAttribute("userID", userID);
		
		return "index";
	}
	
	// 로그인페이지 이동
	private String loginDO() {
		return "login";
	}
	
	// 로그아웃 수행 후 메인페이지 이동
	private String logoutDO(HttpServletRequest request) {
		// 세션값 초기화(로그아웃)
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "index";
	}
}
