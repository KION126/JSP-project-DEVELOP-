<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.UserDTO"%>
<%@ page import="User.UserDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
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
	return;
}

if (!userPasswordRe.equals(userPassword)) {
	PrintWriter script = response.getWriter();
	script.print("<script>");
	script.print("alert('비밀번호 확인이 틀립니다.\\n비밀번호 확인을 다시 입력해주세요.');");
	script.print("history.back();");
	script.print("</script>");
	script.close();
	return;
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
	session.setAttribute("userID", userID);
	response.sendRedirect("emailSendAction.jsp");
	return;
}	
%>