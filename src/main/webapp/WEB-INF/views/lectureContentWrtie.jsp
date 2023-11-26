<%@page import="java.util.List"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.catalina.User"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>${lec_title }</title>
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
	
	<div class="row" style="height: 800px;">
		<%@ include file="layout/lectureRoomSideBar.jsp" %>
		<div class="col-8 classRoom-main-container">
			<a href="javascript:lectureRoom(${classID})">강의실 홈</a>&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;
			<a style="font-weight: bold;">강의 추가</a>
			<div class="classRoom-notice" style="height: 700px;">
				<h3 style="font-weight: bold; text-align: center;">강의 추가</h3>
				
				<form action="
					<c:choose>
						<c:when test='${contentID > 0}'>lectureContentModifyAction.do</c:when>
						<c:otherwise>lectureContentWriteAction.do</c:otherwise>
					</c:choose>
					" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
					<div class="classRoom-notice-container">
                        <div class="row notice-title-container">
                            <div class="col-2">
                                <h5 id="notice-h">강의 제목</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-title" <c:if test="${contentID > 0}">id="boardTitle"</c:if> type="text" name="contentTitle" maxlength="45" placeholder="제목을 입력해 주세요(45자 이내)">
                            </div>
                        </div>
                        <div class="row notice-file-container">
                            <div class="col-2">
                                <h5 id="notice-h">강의 주차</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-week" <c:if test="${contentID > 0}">id="boardTitle"</c:if> type="number" name="contentWeek" value="0" min="0" max="99">
                            </div>
                        </div>
                        <div class="row notice-file-container">
                            <div class="col-2">
                                <h5 id="notice-h">수강 기간</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-date" <c:if test="${contentID > 0}">id="boardTitle"</c:if> type="date" name="startDate" > ~ 
                                <input class="notice-date" <c:if test="${contentID > 0}">id="boardTitle"</c:if> type="date" name="endDate" >
                            </div>
                        </div>
                        <div class="row notice-file-container">
                            <div class="col-2">
                                <h5 id="notice-h">강의 자료</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-file" type="file" name="contentFile" maxlength="45"><c:if test="${contentID > 0}">수정 전 파일: ${boardFile}</c:if>
                            	<h5 class="lectureContent-video-tip">*최대 1개의 10MB이하 파일을 업로드할 수 있습니다. (여러 파일 업로드시 압축하여 압축파일 업로드)</h5>
                            </div>
                        </div>
                        <div class="row notice-content-container">
                            <div class="col-2">
                                <h5 id="notice-h">강의 영상</h5>
                            </div>
                             <div class="col-8">
                                <input class="lectureContent-video" type="file" name="video01" accept="video/mp4,video/mkv, video/x-m4v,video/*"><c:if test="${contentID > 0}">수정 전 파일: ${boardFile}</c:if>
                                <input class="lectureContent-video" type="file" name="video02" accept="video/mp4,video/mkv, video/x-m4v,video/*"><c:if test="${contentID > 0}">수정 전 파일: ${boardFile}</c:if>
                                <input class="lectureContent-video" type="file" name="video03" accept="video/mp4,video/mkv, video/x-m4v,video/*"><c:if test="${contentID > 0}">수정 전 파일: ${boardFile}</c:if>	
                            	<h5 class="lectureContent-video-tip">*최대 3개의 1GB이하 동영상을 업로드할 수 있습니다. (mp4, mkv 확장자만 업로드가 가능합니다.)</h5>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="classID" value="${classID}">
                    <input type="hidden" name="contentID" value="${contentID}">
                    <input type="hidden" name="userID" value="${userID}">
                    <input class="btn-notice-write" type="submit" <c:choose><c:when test='${boardID > 0}'>value="수정"</c:when><c:otherwise>value="등록"</c:otherwise></c:choose>>
                </form>
				
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
	<!-- lectureContentWrite.js 추가 -->
	<script src="./js/lectureContentSetting.js" /></script>
	<!-- lectureContentWrite.js 추가 -->
	<script src="./js/lectureContentWrite.js" /></script>
	<script src="./js/validateForm.js" /></script>
</body>
</html>