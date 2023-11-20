<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DEVELOP 회원가입</title>
<!-- bootstrap CSS 추가 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- custom CSS 추가 -->
<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
	<%@ include file="layout/header.jsp" %>

	<h2 style="text-align: center; margin-top: 100px; font-weight: bold">회원가입</h2>

	<div class="login-container">
		<form action="registerAction.do" method="post">
			<div class="form-group">
				<input type="text" name="userID" placeholder="아이디" required>
			</div>
			<div class="form-group">
				<input type="password" name="userPassword" placeholder="비밀번호"
					required>
			</div>
			<div class="form-group">
				<input type="password" name="userPasswordRe" placeholder="비밀번호 확인"
					required>
			</div>
			<div class="form-group">
				<input type="text" name="userName" placeholder="이름"
					required>
			</div>
			<div class="form-group">
				<input type="text" name="userEmail" placeholder="이메일"
					required>
			</div>
			<div class="a-container">
				<div class="a-button-container"style="text-align: right;">
					<a href="#" class="password"
						style="margin-left: 9px; font-size: 13px;">간편로그인</a>
				</div>
			</div>
			<button type="submit" class="btn_join"> 회원가입 </button>
		</form>
	</div>


	<%@ include file="layout/footer.jsp" %>

	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<script src="./js/sidebar.js" /></script>
</body>
</html>