<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.catalina.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<div class="row" style="min-height: 1000px;">
		<%@ include file="layout/lectureRoomSideBar.jsp" %>
		
		<div class="col-8 classRoom-main-container" style="float: left;">
			<div class="classRoom-preview">
					<h5 style="font-weight: bold; display: inline;">강의공지</h5>
					<button class="btn-preview-notice" onclick="lectureRoomNotice(${classID }, 1, 1)">+</button>
					<table class="notice-preview">
					<c:forEach var="notice" items="${noticeInfos}">
						<tr>
							<th class="notice-preview-th"><a href="#" onclick="lectureRoomNoticeInfo(${classID },${notice.boardID })">${notice.boardTitle }</a></th>
							<th class="notice-preview-th" style="text-align: right;">${notice.boardDate }</th>	
						</tr>
					</c:forEach>
					</table>
			</div>
			<div class="classRoom-preview" style="margin-left: 22px;">
					<h5 style="font-weight: bold; display: inline;">Q & A</h5>
					<button class="btn-preview-notice" onclick="lectureRoomNotice(${classID }, 1,2)">+</button>
					<table class="notice-preview">
					<c:forEach var="QA" items="${QAInfos}">
						<tr>
							<th class="notice-preview-th"><a href="#" onclick="lectureRoomNoticeInfo(${classID },${QA.boardID })">${QA.boardTitle }</a></th>
							<th class="notice-preview-th" style="text-align: right;">${QA.boardDate }</th>	
						</tr>
					</c:forEach>
					</table>
			</div>
			<div class="classRoom-contents">
				<c:if test="${userType eq lectuerType }">
					<button class="btn-preview-lectureContent" onclick="lectureContentWrite(${classID })">강의 추가</button>				
				</c:if>
				<h5 class="classRoomContentsTitle" >주제 별 학습 활동</h5>
				<ul class="lectureContentsUl">
					<c:forEach var="lecConInfo" items="${lecConInfoList}">
						<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
						<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowDate" />
						<fmt:parseDate value="${lecConInfo.startDate}" pattern="yyyy-MM-dd" var="startDateD" />
						<fmt:formatDate value="${startDateD}" pattern="yyyy-MM-dd" var="startDate" />
						<fmt:parseDate value="${lecConInfo.endDate}" pattern="yyyy-MM-dd" var="endDateD" />
						<fmt:formatDate value="${endDateD}" pattern="yyyy-MM-dd" var="endDate" />
						<c:choose>
							<c:when test="${startDate <= nowDate && endDate > nowDate }">
								<li class="lectureContentLi">
									<div class="contentTitle">
										${lecConInfo.week }주차<br>
										${lecConInfo.title }
										<c:if test="${userType eq lectuerType }">
											<button class="btn-delete-lectureContent" onclick="lectureContentDelete(${classID }, ${lecConInfo.contentID })">강의 삭제</button>				
										</c:if>
									</div>
									<div class="contentPeriod">
										${lecConInfo.startDate } ~ ${lecConInfo.endDate }
									</div>
									<div class="contentVideo">
										<c:forEach var="videoInfo" items="${videoInfoList }">				
											<c:if test="${lecConInfo.contentID == videoInfo.contentID}">
												<img class="contentIcon" src="./image/icon/videoPlay.png">
												<a 
												href="javascript:videoPlayer(${classID }, ${lecConInfo.contentID},${videoInfo.videoID})">${videoInfo.video}</a><br>
											</c:if>
										</c:forEach>
									</div>
									<div class="contentFile">
										<c:if test="${lecConInfo.file != null}">
											<img class="contentIcon" src="./image/icon/attachment.png">
											<a href="javascript:lectureContentFileDownload(${classID }, ${lecConInfo.contentID })">${lecConInfo.file}</a>
										</c:if>
									</div>
								</li>
							</c:when>
							<c:otherwise>
								<li class="lectureContentLi">
									<div class="contentTitle">
										강의 종료	
										${lecConInfo.week }주차<br>
										${lecConInfo.title }
										<c:if test="${userType eq lectuerType }">
											<button class="btn-delete-lectureContent" onclick="lectureContentDelete(${classID }, ${lecConInfo.contentID })">강의 삭제</button>				
											<button class="btn-modify-lectureContent" onclick="lectureContentModify(${classID })">강의 수정</button>
										</c:if>
									</div>
									<div class="contentPeriod">
										${lecConInfo.startDate } ~ ${lecConInfo.endDate }
									</div>
									<div class="contentVideo">
										<c:forEach var="videoInfo" items="${videoInfoList }">				
											<c:if test="${lecConInfo.contentID == videoInfo.contentID}">
												<img class="contentIcon" src="./image/icon/videoPlay.png">
												<a onclick="alert('수강기간이 아닙니다.')" href="#" >${videoInfo.video}</a><br>
											</c:if>
										</c:forEach>
									</div>
									<div class="contentFile">
										<c:if test="${lecConInfo.file != null}">
											<img class="contentIcon" src="./image/icon/attachment.png">
											<a href="javascript:lectureContentFileDownload(${classID }, ${lecConInfo.contentID })" >${lecConInfo.file}</a>
										</c:if>
									</div>
								</li>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</ul>
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
	<!-- lectureContentFileDownload.js 추가 -->
	<script src="./js/lectureContentFileDownload.js" /></script>
	<!-- videoPlayer.js 추가 -->
	<script src="./js/videoPlayer.js" /></script>
	<!-- lectureContentWrite.js 추가 -->
	<script src="./js/lectureContentWrite.js" /></script>
	<script src="./js/lectureContentDelete.js" /></script>
</body>
</html>