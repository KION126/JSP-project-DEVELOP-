<%@page import="User.UserDAO"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
String code = request.getParameter("code");

UserDAO DAO = new UserDAO();
String userID = (String) session.getAttribute("userID");

if (userID == null) {
    PrintWriter script = response.getWriter();
    script.print("<script>");
    script.print("alert('로그인을 해주세요.');");
    script.print("location.href='login.do'");
    script.print("</script>");
    script.close();
    return;
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
%>
