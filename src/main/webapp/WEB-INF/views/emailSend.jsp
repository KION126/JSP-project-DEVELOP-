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
<title>DEVELOP 로그인</title>
<!-- bootstrap CSS 추가 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- custom CSS 추가 -->
<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
	<%@ include file="layout/header.jsp" %>

	<div class="alert alert-success mt-4" role="alert">
		이메일 주소 인증 메일이 전송되었습니다. 회원가입 시 입력했던 이메일에서 인증해주세요.
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