package Register;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CommandHandler.CommandHandler;
import User.UserDAO;
import User.UserDTO;
import util.SHA256;

public class RegisterService implements CommandHandler{
	String viewPage = null;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 회원가입 페이지 이동
		// 회원가입 수행 페이지 이동
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
		case "register.do": {
			viewPage = registerDO();
			break;
		} case "registerAction.do": {
			viewPage = registerActionDO(request, response);
			break;
		}
		default:
		}
		
		return viewPage;
	}
	
	private String registerDO() {
		return "register";
	}
	
	private String registerActionDO(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userID = null;
		String userEmail = null;
		String userPassword = null;
		String userPasswordRe = null;
		String userName = null;

		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		if (request.getParameter("userEmail") != null) {
			userEmail = request.getParameter("userEmail");
		}
		if (request.getParameter("userPassword") != null) {
			userPassword = request.getParameter("userPassword");
		}
		if (request.getParameter("userPasswordRe") != null) {
			userPasswordRe = request.getParameter("userPasswordRe");
		}
		if (request.getParameter("userName") != null) {
			userName = request.getParameter("userName");
		}
		
		if (userID == null || userEmail == null || userPassword == null || userName == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('입력이 안 된 사항이 있습니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		}
		if (!userPasswordRe.equals(userPassword)) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('비밀번호 확인이 틀립니다.\\n비밀번호 확인을 다시 입력해주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		}
		int result = 0;

		UserDTO userDto = new UserDTO();
		userDto.setUserID(userID);
		userDto.setUserPassword(userPassword);
		userDto.setUserName(userName);
		userDto.setUserEmail(userEmail);
		userDto.setUserEmailHash(SHA256.getSHA256(userEmail));
		userDto.setUserEmailChecked(false);
		userDto.setUserType("basic");

		UserDAO DAO = new UserDAO();
		result = DAO.join(userDto);
		
		if (result == -1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('이미 존재하는 아이디입니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);
			
			return "emailSendAction";
		}
		return null;
	}
	
}
