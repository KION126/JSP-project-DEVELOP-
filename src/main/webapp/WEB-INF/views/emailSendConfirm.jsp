<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String)session.getAttribute("userID");
	}
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.print("<script>");
		script.print("alert('로그인을 해주세요.');");
		script.print("location.href = 'login.do'");
		script.print("</script>");
		script.close();
		return;
	}
%>
		<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid" id="navbarContainerFluid" style="margin: 0 200px;">
			<a href="./index.jsp"> <img id="logo" src="./image/logo.png" alt="DEVELOP" width="150" height="60">
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="./index.jsp">메인</a></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"> 강좌 </a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="#">전체강좌보기</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="#">인문</a></li>
							<li><a class="dropdown-item" href="#">사회</a></li>
							<li><a class="dropdown-item" href="#">교육</a></li>
							<li><a class="dropdown-item" href="#">공학</a></li>
							<li><a class="dropdown-item" href="#">자연</a></li>
							<li><a class="dropdown-item" href="#">의약</a></li>
							<li><a class="dropdown-item" href="#">예체능</a></li>
						</ul></li>
				</ul>
				<ul class="navbar-nav me-2">
				<%
					if(userID == null){
				%>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="./userLogin.jsp">로그인</a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="./userJoin.jsp">회원가입</a></li>
				<%
					} else{
				%>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="./userLogout.jsp">로그아웃</a></li>
				<%
					}
				%>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="sidebar" id="sidebar">
		<div class="row">
			<div class="col-2" id="allClass">
				<a href="./classSearch.jsp?keyword=" style="font-size: 20px;">전체강좌보기</a>
			</div>
		</div>
		<div class="row" style="height: 300px;">
			<div class="col-2" style="height: 90px;">
				인문<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=인문(언어문학)">언어·문학</a><br> <a href="./classSearch.jsp?keyword=인문(언어과학)">인문과학</a>
				</div>
			</div>
			<div class="col-2" style="height: 120px;">
				사회<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=사회(경영경제)">경영경제</a><br> <a href="./classSearch.jsp?keyword=사회(사회과학)">사회과학</a><br> <a href="./classSearch.jsp?keyword=사회(법률)">법률</a>
				</div>
			</div>
			<div class="col-2" style="height: 180px;">
				교육<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=교육(교육일반)">교육일반</a><br> <a href="./classSearch.jsp?keyword=교육(유아교육)">유아교육</a><br> <a href="./classSearch.jsp?keyword=교육(특수교육)">특수교육</a><br> <a href="./classSearch.jsp?keyword=교육(초등교육)">초등교육</a><br> <a href="./classSearch.jsp?keyword=교육(중등교육)">중등교육</a>
				</div>
			</div>
			<div class="col-2" style="height: 240px;">
				공학<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=공학(건축)">건축</a><br> <a href="./classSearch.jsp?keyword=공학(기계금속)">기계·금속</a><br> <a href="./classSearch.jsp?keyword=공학(전기전자)">전기·전자</a><br>
					<a href="./classSearch.jsp?keyword=공학(소재재료)">소재·재료</a><br> <a href="./classSearch.jsp?keyword=공학(컴퓨터통신)">컴퓨터·통신</a><br> <a href="./classSearch.jsp?keyword=공학(산업)">산업</a><br> <a href="./classSearch.jsp?keyword=공학(화공)">화공</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				자연<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=자연(농림수산)">농림·수산</a><br> <a href="./classSearch.jsp?keyword=자연(생물화학환경)">생물·화학·환경</a><br> <a href="./classSearch.jsp?keyword=자연(생활과학)">생활과학</a><br> <a href="./classSearch.jsp?keyword=자연(수학물리천문지리)">수학·물리·천문·지리</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				의약<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=의약(의료)">의료</a><br> <a href="./classSearch.jsp?keyword=의약(간호)">간호</a><br> <a href="./classSearch.jsp?keyword=의약(약학)">약학</a><br> <a href="./classSearch.jsp?keyword=의약(치료보건)">치료·보건</a>
				</div>
			</div>
			<div class="col-2" style="height: 210px;">
				예체능<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=예체능(디자인)">디자인</a><br> <a href="./classSearch.jsp?keyword=예체능(응용예술)">응용예술</a><br> <a href="./classSearch.jsp?keyword=예체능(무용체육)">무용·체육</a><br> <a href="./classSearch.jsp?keyword=예체능(미술조형)">미술·조형</a><br> <a href="./classSearch.jsp?keyword=예체능(연극영화)">연극·영화</a><br> <a href="./classSearch.jsp?keyword=예체능(음악)">음악</a>
				</div>
			</div>
			<div class="col-2" style="height: 60px;">
				융·복합<br>
				<div class="classthema">
					<a href="./classSearch.jsp?keyword=융복합(융복합)">융·복합</a>
				</div>
			</div>
		</div>
	</div>

	<div class="alert alert-warning mt-4" role="alert">
		이메일 주소 인증을 하셔야 이용 가능합니다. 인증 메일을 받지 못하셨나요?
	</div>
	<a href="emailSendAction.jsp" class="brn brn-primary">인증 메일 다시 받기</a>

	<footer class="bg-dark mt-5 p-5 text-center" style="color: #FFF;"> Copyright &copy; 202044034 왕건 ALL RIGHTS RESEVED. </footer>

	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<script src="./js/sidebar.js" /></script>
</body>
</html>