package Email;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;
import User.UserDAO;

public class EmailSendAction implements CommandHandler{

	private ServletRequest session;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException {
		
		String userID = (String) session.getAttribute("userID");
		
		System.out.println(userID);
		request.setAttribute("userID", userID);
		
		return "emailSendAction";
	}

}
