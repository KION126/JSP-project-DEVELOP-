package User;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CommandHandler.CommandHandler;
import util.Gmail;
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
				viewPage = register();
				break;
			} case "registerAction.do": {
				viewPage = registerAction(request, response);
				break;
			}
		}
		
		return viewPage;
	}
	
	private String register() {
		return "register";
	}
	
	private String registerAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			
			emailCheckAction(response, userID);
			
			return "emailSend";
		}
		return null;
	}
	
	private void emailCheckAction(HttpServletResponse response, String userID) throws IOException {
		PrintWriter script = response.getWriter();
		if(userID == null){
			script.print("<script>");
			script.print("alert('로그인을 해주세요.azz');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		}
		UserDAO user_dao = new UserDAO();
		
		boolean emailChecked = user_dao.getUserEmailChecked(userID);
		if(emailChecked){
			script.print("<script>");
			script.print("alert('이미 인증 된 회원입니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		}
		String host = "http://localhost:8181/JSP_DEVELOP/";
		String from = "develop1216@gmail.com";
		String to = user_dao.getUserEmail(userID);
		SHA256 sha = new SHA256();
		String code = sha.getSHA256(to);
		String subject = "DEVELOP에서 보내는 이메일 인증 메일입니다.";
		String content = "다음 링크에 접속하여 이메일 인증을 진행해주세요." + 
			"<a href='" + host + "emailCheck.jsp?code=" + code + "'>이메일 인증하기</a>";
		System.out.println("--------------------"+code);
		
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com"); // google SMTP 주소
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 추가된 코드
		p.put("mail.smtp.ssl.enable", "true");  // 추가된 코드
		
		try {
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
		    ses.setDebug(true);
		    MimeMessage msg = new MimeMessage(ses); 
		    msg.setSubject(subject);		// 메일 제목
		    Address fromAddr = new InternetAddress(from); 	// 보내는 사람 정보
		    msg.setFrom(fromAddr);
		    Address toAddr = new InternetAddress(to);		// 받는 사람 정보
		    msg.addRecipient(Message.RecipientType.TO, toAddr);
		    msg.setContent(content, "text/html;charset=UTF-8");
		    Transport.send(msg); // 메세지 전송
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
