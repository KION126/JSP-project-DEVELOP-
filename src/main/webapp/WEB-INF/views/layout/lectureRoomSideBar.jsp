<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>lectureRoomSideBar</title>
</head>
<body>
	<div class="col-2">
		<div class="classRoomMenu">
			<input type="button" id="classRoomMenu-item01" onClick="lectureRoom(${classID})"> <label for="classRoomMenu-item01">강의실 홈<em></em></label> <input type="checkbox" id="classRoomMenu-item02"> <label for="classRoomMenu-item02">강의 목록<em></em></label>
			<div style="border-bottom: 0;">
				<p>1강.나는 누구냐</p>
				<br>
				<p>1강.나는 누구냐</p>
				<br>
				<p>1강.나는 누구냐</p>
			</div>
			<input type="checkbox" id="classRoomMenu-item03"> <label for="classRoomMenu-item03">학습활동<em></em></label>
			<div>
				<p>게시판</p>
				<br>
				<p>과제</p>
			</div>
		</div>
	</div>
	<!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
</body>
</html>