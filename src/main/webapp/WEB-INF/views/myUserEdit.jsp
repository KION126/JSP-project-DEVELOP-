<%@page import="Enrol.EnrolDTO"%>
<%@page import="Enrol.EnrolDAO"%>
<%@page import="Lecture.LectureDTO"%>
<%@page import="Lecture.LectureDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>마이페이지</title>
<!-- bootstrap CSS 추가 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- custom CSS 추가 -->
<link rel="stylesheet" href="./css/custom.css">
</head>
<body>	
    <%@ include file="layout/header.jsp" %>
    
    <div class="page-container">
        <div class="row" style="margin-top: 100px; margin-left: 200px;">
            <div class="col-3">
                <h2 style="font-weight: bold">마이페이지</h2>
                <br>
                <ul class="menus">
                    <li class="menu-item menu-item-01"><a href="myPage.do" style="font-size: 15px">
                    <img src="./image/icon/myClassRoom.png" style="height: 25px; margin-right: 10px;">내강의실
                    </a></li>
                    <li class="menu-item menu-item-02"><a href="myInterest.do" style="font-size: 15px">
                    <img src="./image/icon/myInterest.png" style="height: 25px; margin-right: 10px;">관심분야설정
                    </a></li>
                    <li class="menu-item menu-item-03 active"><a href="myUserEdit.do" style="font-size: 15px">
                    <img src="./image/icon/myUserEdit.png" style="height: 25px; margin-right: 10px;">개인정보관리
                    </a></li>
                </ul>
            </div>
            
            <div class="col-8" style="width: 700px; height:2000px;">
                <div><h2 class="mypageTitle">개인정보관리</h2></div>
                <div>
                	
                </div>
            </div>
        </div>
    </div>

    <%@ include file="layout/footer.jsp" %>
    
    <!-- bootstrap.js 추가 -->
    <script src="./js/bootstrap.bundle.min.js" /></script>
    <!-- jquery.js 추가 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" /></script>
    <!-- popper.js 추가 -->
    <script src="./js/popper.min.js" /></script>
    <script src="./js/sidebar.js" /></script>
    <!-- lectureRoom.js 추가 -->
	<script src="./js/lectureRoom.js" /></script>
</body>
</html>
