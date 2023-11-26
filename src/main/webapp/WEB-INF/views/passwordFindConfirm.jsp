<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.io.PrintWriter"%>
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
</head>
<body>
	<%@ include file="layout/header.jsp" %>
	
	<h2 style="text-align: center; margin-top: 100px; font-weight: bold">비밀번호 찾기</h2>

	<div class="login-container">
		<form action="passwordFindEmailCheck.do" method="post">
			<div class="form-group">
				<input type="text" name="userName" placeholder="이름" required>
			</div>
			<div class="form-group">
				<input type="password" name="userID" placeholder="아이디" required>
			</div>
			<div class="a-container">
				<div class="a-button-container" style="text-align: left; position: absolute;">
					<a href="idFind.do" class="id" style="font-size: 13px;">아이디 찾기</a>
				</div>
				<div class="a-button-container" style="text-align: right;">
					<a href="login.do" class="id" style="margin-right: 5px; font-size: 13px;">로그인</a>
					<div class="verticality-line"></div>
					<a href="register.do" class="password" style="margin-left: 9px; font-size: 13px;">회원가입</a>
				</div>
			</div>
			<button type="submit" class="btn_login">비밀번호 찾기</button>
		</form>
		<div id="naver_id_login"></div>
	</div>

	<%@ include file="layout/footer.jsp" %>

	<!-- 네이버 로그인API -->
	<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<script src="./js/sidebar.js" /></script>
</body>
</html>