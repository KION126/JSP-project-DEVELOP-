<%@page import="User.UserDAO"%>
<%@page import="Enrol.EnrolDAO"%>
<%@page import="Lecture.ClassDTO"%>
<%@page import="java.util.List"%>
<%@page import="Lecture.ClassDAO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<c:if test="${NewEnrol}">
		<script>alert(`수강신청이 완료되었습니다. \n마이페이지에서 확인하세요.`);</script>
	</c:if>
	
	<%@ include file="layout/header.jsp" %>

	<div class="classInfo-container" style="background-image: url('./image/classInfo.png');">
		<div class="row">
			<div class="col-6" style="margin-top: 100px; padding-left: 200px;">
				<img src="${img }" style="height: 300px;">
				<div style="margin-top: 20px;">
					<button id="btn_interest">
						<img src="./image/icon/myInterest.png" style="height: 35px;">
					</button>
					
					<c:choose>
						<c:when test="${classEnrolChecked}">
	    					<button id="btn_classRoom" onclick="lectureRoom('${userID}',${classID})">강의실 입장</button>
						</c:when>
						<c:otherwise>
							<button id="btn_classApp" onclick="lectureEnrol('${userID}',${classID}, ${userEmailChecked})">수강신청하기</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-6" style="margin-top: 100px; color: #FFF;">
				<h3 style="font-weight: bold;">${title }</h3>
				<div class="row">
					<div class="col-9">
						분야:${thema }<br> 교수:${pro }<br> <br>
						<p style="text-align: justify">${con }</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="layout/footer.jsp" %>

	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<!-- sidebar.js 추가 -->
	<script src="./js/sidebar.js" /></script>
	<!-- lectureEnrol.js 추가 -->
	<script src="./js/lectureEnrol.js" /></script>
	<!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
</body>
</html>