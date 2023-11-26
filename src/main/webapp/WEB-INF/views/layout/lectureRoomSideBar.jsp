<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>lectureRoomSideBar</title>
</head>
<body>
	<div class="col-2">
		<div class="classRoomMenu">
			<input type="button" id="classRoomMenu-item01" onClick="lectureRoom(${classID})">
			<label for="classRoomMenu-item01">강의실 홈<em></em></label>
			<input type="checkbox" id="classRoomMenu-item02">
			<label for="classRoomMenu-item02">학습활동<em></em></label>
			<div>
				<a onclick="lectureRoomNotice(${classID }, 1,2)" href="#">Q&A 게시판</a>
				<br>
				<p>과제</p>
			</div>
		</div>
	</div>
	<!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
</body>
</html>