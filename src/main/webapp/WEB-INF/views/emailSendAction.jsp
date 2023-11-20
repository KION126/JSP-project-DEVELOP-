<%@page import="User.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.Session"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Authenticator"%>
<%@ page import="java.util.Properties" %>
<%@ page import="util.SHA256"%>
<%@ page import="util.Gmail" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="java.math.BigInteger"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DEVELOP</title>
<!-- bootstrap CSS 추가 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- custom CSS 추가 -->
<link rel="stylesheet" href="./css/custom.css">

<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
</head>
<body>
<%
	UserDAO userDao = new UserDAO();
	String userID = null;
	if(request.getParameter("userID") != null){
		userID = request.getParameter("userID");
	}
	
	PrintWriter script = response.getWriter();
	if(userID == null){
		script.print("<script>");
		script.print("alert('로그인을 해주세요.');");
		script.print("history.back();");
		script.print("</script>");
		script.close();
	}
	boolean emailChecked = userDao.getUserEmailChecked(userID);
	if(emailChecked){
		script.print("<script>");
		script.print("alert('이미 인증 된 회원입니다.');");
		script.print("history.back();");
		script.print("</script>");
		script.close();
	}
	
	String host = "http://localhost:8181/JSP_DEVELOP/";
	String from = "develop1216@gmail.com";
	String to = userDao.getUserEmail(userID);
	String subject = "DEVELOP에서 보내는 이메일 인증 메일입니다.";
	String content = "다음 링크에 접속하여 이메일 인증을 진행해주세요." + 
		"<a href='" + host + "emailCheckAction.jsp?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
	
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
%>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid" id="navbarContainerFluid" style="margin: 0 200px;">
			<a href="javascript:index('<%=userID%>')"> <img id="logo" src="./image/logo.png" alt="DEVELOP" width="150" height="60">
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page" href="index.do">메인</a></li>
					<li class="nav-item"><a class="nav-link" id="toggleSidebar" href="#">강좌</a></li>
				</ul>
				<ul class="navbar-nav me-2">
					<%
					if (userID == null || userID == "" || userID.equals("null")) {
					%>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="login.do">로그인</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="register.do">회원가입</a></li>
					<%
					} else {
					%>
					<li class="nav-item"><a href="javascript:myPage('<%=userID%>')"><img src="./image/icon/myPage.png" alt="myPage" style="height: 45px; margin-top: 10px;'"></a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="logout.do">로그아웃</a></li>
					<%
					}
					%>
				</ul>
			</div>
		</div>
	</nav>
	
	<!-- 사이드바 -->
	<div class="sidebar" id="sidebar">
		<div class="row">
			<div class="col-2" id="allClass">
				<a  href="javascript:lectureSearch('<%=userID %>','')" style="font-size: 20px;">전체강좌보기</a>
			</div>
		</div>
		<div class="row" style="height: 300px;">
			<div class="col-2" style="height: 90px;">
				인문<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','인문(언어문학)')">언어·문학</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','인문(인문과학)')">인문과학</a>
				</div>
			</div>
			<div class="col-2" style="height: 120px;">
				사회<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','사회(경영경제)')">경영경제</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','사회(사회과학)')">사회과학</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','사회(법률)')">법률</a>
				</div>
			</div>
			<div class="col-2" style="height: 180px;">
				교육<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','교육(교육일반)')">교육일반</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','교육(유아교육)')">유아교육</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','교육(특수교육)')">특수교육</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','교육(초등교육)')">초등교육</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','교육(중등교육)')">중등교육</a>
				</div>
			</div>
			<div class="col-2" style="height: 240px;">
				공학<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','공학(건축)')">건축</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(기계금속)')">기계·금속</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(전기전자)')">전기·전자</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(소재재료)')">소재·재료</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(컴퓨터통신)')">컴퓨터·통신</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(산업)')">산업</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','공학(화공)')">화공</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				자연<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','자연(농림수산)')">농림·수산</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','자연(생물화학환경)')">생물·화학·환경</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','자연(생활과학)')">생활과학</a><br> 
					<a href="javascript:lectureSearch('<%=userID %>','자연(수학물리천문지리)')">수학·물리·천문·지리</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				의약<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','의약(의료)')">의료</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','의약(간호)')">간호</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','의약(약학)')">약학</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','의약(치료보건)')">치료·보건</a>
				</div>
			</div>
			<div class="col-2" style="height: 210px;">
				예체능<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','예체능(디자인)')">디자인</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','예체능(응용예술)')">응용예술</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','예체능(무용체육)')">무용·체육</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','예체능(미술조형)')">미술·조형</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','예체능(연극영화)')">연극·영화</a><br>
					<a href="javascript:lectureSearch('<%=userID %>','예체능(음악)')">음악</a>
				</div>
			</div>
			<div class="col-2" style="height: 60px;">
				융·복합<br>
				<div class="classthema">
					<a href="javascript:lectureSearch('<%=userID %>','융복합(융복합))')">융·복합</a>
				</div>
			</div>
		</div>
	</div>

	<div class="alert alert-success mt-4" role="alert">
		이메일 주소 인증 메일이 전송되었습니다. 회원가입 시 입력했던 이메일에서 인증해주세요.
	</div>

	<footer class="bg-dark mt-5 p-5 text-center" style="color: #FFF;"> Copyright &copy; 202044034 왕건 ALL RIGHTS RESEVED. </footer>

	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
</body>
</html>