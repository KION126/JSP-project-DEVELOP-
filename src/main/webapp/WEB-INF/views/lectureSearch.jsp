<%@page import="Lecture.ClassDTO"%>
<%@page import="java.util.List"%>
<%@page import="Lecture.ClassDAO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
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

<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
</head>
<body>
	<%@ include file="layout/header.jsp" %>

	<div class="classSearch-container">
        <div class="row d-flex">
            <div class="col-2" style="text-align: center;">
                <h2 style="color: #fff; margin-bottom: 20px;">강좌 검색</h2>
            </div>
        </div>
        <div class="row d-flex">
            <div class="col-9" style="text-align: center; margin-left: 30px;">
                <form class="search-form" action="lectureSearch.do" method="post">
                    <div class="input-group">
                        <input type="text" class="form-control" name="keyword" placeholder="검색어 입력"
                               aria-label="검색어 입력" aria-describedby="button-addon" id="classSearchbar">
                        <input type="hidden" name="userID" value="<%=userID%>">
                        <div class="input-group-append">
                            <button type="submit" id="btn-search">
                                <img src="./image/icon/search.png" alt="search" style="max-width: 100%;">
                            </button>
                            <button style="height: 50px; width:80px; border-radius: 5px; border: none; ">
                                전체보기
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="recommendation-container" style="margin-top: 50px;">
        <div class="row">
            <div style="border-bottom: 1px solid #999; margin-bottom: 20px;">
                <p>총${classInfoList.size()}강좌</p>
            </div>
            <c:forEach var="classInfo" items="${classInfoList}">
                <div class="card classCard_search"
                     onclick="lectureInfo('<%=userID%>', ${classInfo.classID})">
                    <img src="${classInfo.classImg}" class="card-img-top" alt="...">
                    <div class="card-body" style="font-weight: bold; padding: 5px;">
                        <span>${classInfo.classTitle}</span><br>
                        <p class="card-text" style="font-size: 13px; font-weight: normal;">
                            ${classInfo.classThema}<br>
                            ${classInfo.classProfessor}<br>
                        </p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

	<%@ include file="layout/footer.jsp" %>

	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<script src="./js/sidebar.js" /></script>
	<!-- lectureInfo.js 추가 -->
	<script src="./js/lectureInfo.js"></script>
</body>
</html>