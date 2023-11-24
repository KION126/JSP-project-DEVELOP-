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

public class EmailCheckService implements CommandHandler{
	String viewPage = null;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 이메일 인증 시 인증 절차 수행
		// 메인페이지로 이동
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String code = request.getParameter("code");
		System.out.println(code);
		UserDAO DAO = new UserDAO();
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");

		if (userID == null) {
		    PrintWriter script = response.getWriter();
		    script.print("<script>");
		    script.print("alert('로그인을 해주세요.');");
		    script.print("location.href='login.do'");
		    script.print("</script>");
		    script.close();
		}

		String userEmail = DAO.getUserEmail(userID);
		boolean isRight = new SHA256().getSHA256(userEmail).equals(code);

		if (isRight) {
		    DAO.setUserEmailChecked(userID);
		    PrintWriter script = response.getWriter();
		    script.print("<script>");
		    script.print("alert('인증에 성공했습니다.');");
		    script.print("location.href='index.do'");
		    script.print("</script>");
		    script.close();
		} else {
		    PrintWriter script = response.getWriter();
		    script.print("<script>");
		    script.print("alert('유효하지 않은 코드입니다.');");
		    script.print("location.href='index.do'");
		    script.print("</script>");
		    script.close();
		}
		return "index";
	}
}
