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
	
	<div class="row" style="height: 1500px;">
		<%@ include file="layout/lectureRoomSideBar.jsp" %>
		<div class="col-8 classRoom-main-container">
			<a href="javascript:lectureRoom(${classID})">강의실 홈</a>&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;
			<a href="javascript:lectureRoomNotice(${classID }, 1)">강의 공지</a>&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;
			<a style="font-weight: bold;" href="javascript:lectureRoomNoticeInfo(${classID }, ${boardID })">${not_Title }</a>
			<div class="classRoom-notice" style="height: 600px;">
				<h3 style="font-weight: bold; text-align: center;">강의 공지</h3>
				<table class="noticeInfo-table">
					<thead>
						<tr>
							<th class="noticeInfo-head-th" scope="col">${not_Title }</th>
						</tr>
						<tr class="noticeInfo-tr">
							<th class="noticeInfo-th">작성자: ${not_userName }<br> 작성일: ${not_Date }<br> 조회수: ${not_Hit }
							</th>
						</tr>
						<tr class="noticeInfo-tr">
							<th class="noticeInfo-th">첨부파일:
							<a href="javascript:lectureRoomBoardDownload(${classID }, ${boardID})">${not_File }</a></th>
						</tr>
					</thead>
					<tbody>
						<tr class="noticeInfo-tr">
							<th class="noticeInfo-con-th">${not_Con }</th>
						</tr>
					</tbody>
				</table>
				<c:if test="${userIDeqboardID}">
					<button class="btn-notice-delete" onclick="lectureRoomNoticeDeleteAction(${classID }, ${boardID })">삭제</button>
					<button class="btn-notice-modify" onclick="lectureRoomNoticeModifyConfirm(${classID }, ${boardID })">수정</button>
				</c:if>
			</div>
			<c:if test="${commentInfoList.size() > 0 }">
				<div class="notice-comment-conteinet" id="commentSection">
				<c:forEach var="comment" items="${commentInfoList}">
					<div class="row comment">
						<div class="col-1">
							<img src="./image/icon/myPage.png" alt="myPage" style="height: 45px; margin:0 10px;">
						</div>
						<div class="col-9" style="padding:0">							
							<h5 class="commentUserName-h5">${comment.userName }</h5>
							<h5 class="commentDate-h5">${comment.commentDate }</h5>
							<h5 class="commentContent-h5">${comment.commentContent }</h5>
						</div>
						<button class="comment-modify" value="수정"></button>
						<button class="comment-delete" value="삭제"></button>
					</div>
				</c:forEach>
				<div class="pagination">
				    <nav class="pagination-nav" aria-label="Page navigation example">
				         <ul class="pagination">
                            <%-- 이전 링크 --%>
                            <li class="page-item ${currentPage < 2 ? 'disabled' : ''}">
                                <a class="page-link" href="javascript:comment(${classID },${boardID },${currentPage - 1 })">이전</a></li>

                            <%-- 페이지 번호 링크 --%>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="javascript:comment(${classID },${boardID },${i })">${i }</a>
                                </li>
                            </c:forEach>

                            <%-- 다음 링크 --%>	
                            <li class="page-item ${currentPage > totalPages-1 ? 'disabled' : ''}">
                                <a class="page-link" href="javascript:comment(${classID },${boardID },${currentPage + 1 })">다음</a></li>
                        	</ul>
					    </nav>
					</div>
				</div>
			</c:if>
			
			<div class="notice-comment-write-conteiner">
				<form action="commentWrite.do" method="post">
					<textarea class="notice-comment-textarea" name="commentContent" maxlength="200" placeholder="댓글 작성(200자 이내)"></textarea>	
					<input type="hidden" name="classID" value="${classID }">
					<input type="hidden" name="boardID" value="${boardID }">
					<input class="btn-comment-write" type="submit" value="작성">
					<input class="btn-comment-clear" type="button" onclick="clearTextarea()" value="초기화">
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
	<!-- lectureRoomNotice.js 추가 -->
	<script src="./js/lectureRoomNotice.js" /></script>
	<!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
	<!-- lectureRoomNotice.js 추가 -->
	<script src="./js/lectureRoomNotice.js" /></script>
	<!-- lectureRoomNoticeInfo.js 추가 -->
	<script src="./js/lectureRoomNoticeInfo.js" /></script>
	<!-- lectureRoomNoticeModefy.js 추가 -->
	<script src="./js/lectureRoomNoticeModify.js" /></script>
	<!-- lectureRoomNoticeDelete.js 추가 -->
	<script src="./js/lectureRoomNoticeDelete.js" /></script>
	<!-- lectureRoomBoardDownload.js 추가 -->
	<script src="./js/lectureRoomNoticeBoardDownload.js" /></script>
	<!-- comment.js 추가 -->
	<script src="./js/comment.js" /></script>
	<script type="text/javascript">
		function clearTextarea() {
	        // textarea의 내용을 초기화
	        document.querySelector('.notice-comment-textarea').value = '';
	    }
	</script>
	<script type="text/javascript">
		// 페이지가 로드될 때 초기 댓글을 가져옴
		$(document).ready(function () {
		    var classID = ${classID};
		    var boardID = ${boardID};
		    var currentPage = ${currentPage};
		    loadComments(classID, boardID, currentPage);
		});
	</script>
</body>
</html>