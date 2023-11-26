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
            <c:choose>
				<c:when test="${boardType == 1 }">
					<a href="javascript:lectureRoomNotice(${classID }, 1, 1)">강의 공지</a>&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;
					<c:set var="boadTitle" value="강의 공지 글쓰기" />
				</c:when>
				<c:otherwise>
					<a href="javascript:lectureRoomNotice(${classID }, 1, 2)">Q & A</a>&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;
					<c:set var="boadTitle" value="Q & A 글쓰기" />
				</c:otherwise>
			</c:choose>
			<a style="font-weight: bold;">${boadTitle }</a>
            <div class="classRoom-notice" style="height: 700px;">
                <h3 style="font-weight: bold; text-align: center;">${boadTitle }</h3>
					<form action="
					<c:choose>
						<c:when test='${boardID > 0}'>lectureRoomNoticeModifyAction.do</c:when>
						<c:otherwise>lectureRoomNoticeWriteAction.do</c:otherwise>
					</c:choose>
					" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
					<div class="classRoom-notice-container">
                        <div class="row notice-title-container">
                            <div class="col-2">
                                <h5 id="notice-h">제목</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-title" <c:if test="${boardID > 0}">id="boardTitle"</c:if> type="text" name="boardTitle" maxlength="45" placeholder="제목을 입력해 주세요(45자 이내)">
                            </div>
                        </div>
                        <div class="row notice-file-container">
                            <div class="col-2">
                                <h5 id="notice-h">첨부파일</h5>
                            </div>
                            <div class="col-8">
                                <input class="notice-file" type="file" name="boardFile" maxlength="45"><c:if test="${boardID > 0}">수정 전 파일: ${boardFile}</c:if>
                            </div>
                        </div>
                        <div class="row notice-content-container">
                            <div class="col-2">
                                <h5 id="notice-h">내용</h5>
                            </div>
                            <div class="col-8">
                                <textarea class="notice-content" <c:if test="${boardID > 0}">id="boardContent"</c:if> name="boardContent" maxlength="2048" placeholder="내용을 입력해 주세요(2000자 이내)"></textarea>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="boardID" value="${boardID}">
                    <input type="hidden" name="userID" value="${userID}">
                    <input type="hidden" name="classID" value="${classID}">
                    <input type="hidden" name="boardType" value="${boardType}">
                    <input class="btn-notice-write" type="submit" <c:choose><c:when test='${boardID > 0}'>value="수정"</c:when><c:otherwise>value="작성"</c:otherwise></c:choose>>
                </form>
                    <button class="btn-notice-modefy-cancle" onclick="javascript:lectureRoomNotice(${classID }, 1)">취소</button>
            </div>
        </div>
    </div>

    <%@ include file="layout/footer.jsp" %>

    <!-- bootstrap.js 추가 -->
    <script src="./js/bootstrap.bundle.min.js"></script>
    <!-- jquery.js 추가 -->
    <script src="./js/jquery.min.js"></script>
    <!-- popper.js 추가 -->
    <script src="./js/popper.min.js"></script>
    <!-- sidebar.js 추가 -->
    <script src="./js/sidebar.js"></script>
    <!-- lectureRoomNotice.js 추가 -->
    <script src="./js/lectureRoomNotice.js"></script>
    <!-- lectureRoom.js 추가 -->
    <script src="./js/lectureRoom.js"></script>
    <!-- lectureRoomNotice.js 추가 -->
    <script src="./js/lectureRoomNotice.js"></script>
    <!-- lectureRoomNoticeInfo.js 추가 -->
    <script src="./js/lectureRoomNoticeInfo.js"></script>
    <!-- lectureRoomNoticeWrite.js 추가 -->
    <script src="./js/lectureRoomNoticeWrite.js"></script>
    <script>
    function validateForm() {
        var boardTitle = document.getElementsByName("boardTitle")[0].value;
        var boardContent = document.getElementsByName("boardContent")[0].value;
    
        if (boardTitle.trim() === "") {
            alert("제목을 입력해주세요.");
            return false;
        } else if (boardContent.trim() === "") {
            alert("내용을 입력해주세요.");
            return false;
        } else {
            alert("정상적으로 게시물 등록이 완료되었습니다.");
            return true;
        }
    }
    </script>
    <script>
        var boardTitleValue = "${boardTitle}";
        var boardContentValue = "${boardContent}";
    
        if (boardTitleValue != null && boardContentValue != null) {
            // <br>을 \n으로 변경
            boardTitleValue = boardTitleValue.replaceAll("<br>", "\n");
            boardContentValue = boardContentValue.replaceAll("<br>", "\n");
    
            // 값을 폼 엘리먼트에 설정
            document.getElementById("boardTitle").value = boardTitleValue;
            document.getElementById("boardContent").value = boardContentValue;
        }
    </script>
</body>
</html>
