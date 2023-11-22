<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.catalina.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body style="background-color: #F3F2F4">

	<%@ include file="layout/header.jsp" %>
	
	<div class="classRoom-header-container" style="background-image: url('./image/classRoom/header.png');">
		<h3 class="classRoom-title">${lec_title }</h3>
		<h5 class="classRoom-professor">${lec_pro }</h5>
	</div>
	<div class="row" style="height: 3000px;">
		<%@ include file="layout/lectureRoomSideBar.jsp" %>
		
		<div class="col-8 classRoom-main-container" style="float: left;">
			<div class="classRoom-preview">
					<h5 style="font-weight: bold; display: inline;">강의공지</h5>
					<button class="btn-preview-notice" onclick="lectureRoomNotice('${userID}', ${classID }, 1)">+</button>
					<table class="notice-preview">
					<c:forEach var="notice" items="${noticeInfos}">
						<tr>
							<th class="notice-preview-th"><a href="#" onclick="lectureRoomNoticeInfo('${userID }',${classID },${notice.boardID })">${notice.boardTitle }</a></th>
							<th class="notice-preview-th" style="text-align: right;">${notice.boardDate }</th>	
						</tr>
					</c:forEach>
					</table>
			</div>
			<div class="classRoom-preview" style="margin-left: 22px;">
					<h5 style="font-weight: bold; display: inline;">Q & A</h5>
					<button class="btn-preview-notice" onclick="classRoomNotice()">+</button>
					<table class="notice-preview">
						<tr>
							<th class="notice-preview-th"><a href=""></a></th>
							<th class="notice-preview-th" style="text-align: right;"></th>
						</tr>
					</table>
			</div>
			<div class="classRoom-contents">
				<h5 style="font-weight: bold; display: inline;">주제 별 학습 활동</h5>
				<c:if test="${userType eq lectuerType }">
					<button class="btn-preview-lectureContent" onclick="lectureContentSetting('${userID }',${classID })">강의관리</button>
				</c:if>
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
	<!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
	<!-- lectureRoomNotice.js 추가 -->
	<script src="./js/lectureRoomNotice.js" /></script>
	<!-- lectureRoomNotice.js 추가 -->
	<script src="./js/lectureRoomNoticeInfo.js" /></script>
	<!-- lectureContentSetting.js 추가 -->
	<script src="./js/lectureContentSetting.js" /></script>
</body>
</html>