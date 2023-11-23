<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
</head>
<body>
	<!-- navbar -->
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid" id="navbarContainerFluid" style="margin: 0 200px;">
			<a href="index.do"> <img id="logo" src="./image/logo.png" alt="DEVELOP" width="150" height="60"></a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page" href="index.do">메인</a></li>
					<li class="nav-item"><a class="nav-link" id="toggleSidebar" href="#">강좌</a></li>
				</ul>
				<ul class="navbar-nav me-2">
					<c:choose>
						<c:when test="${userID != null}">
							<li class="nav-item"><a href="myPage.do"><img src="./image/icon/myPage.png" alt="myPage" style="height: 45px; margin-top: 10px;'"></a></li>
							<li class="nav-item"><a class="nav-link" aria-current="page" href="logout.do">로그아웃</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" aria-current="page" href="login.do">로그인</a></li>
							<li class="nav-item"><a class="nav-link" aria-current="page" href="register.do">회원가입</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
	
	<!-- 사이드바 -->
	<div class="sidebar" id="sidebar">
		<div class="row">
			<div class="col-2" id="allClass">
				<a  href="lectureSearch.do?keyword=" style="font-size: 20px;">전체강좌보기</a>
			</div>
		</div>
		<div class="row" style="height: 300px;">
			<div class="col-2" style="height: 90px;">
				인문<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=인문(언어문학)">언어·문학</a><br>
					<a href="lectureSearch.do?keyword=인문(인문과학)">인문과학</a>
				</div>
			</div>
			<div class="col-2" style="height: 120px;">
				사회<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=사회(경영경제)'">경영경제</a><br>
					<a href="lectureSearch.do?keyword=사회(사회과학)'">사회과학</a><br>
					<a href="lectureSearch.do?keyword=사회(법률)'">법률</a>
				</div>
			</div>
			<div class="col-2" style="height: 180px;">
				교육<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=교육(교육일반)">교육일반</a><br>
					<a href="lectureSearch.do?keyword=교육(유아교육)">유아교육</a><br>
					<a href="lectureSearch.do?keyword=교육(특수교육)">특수교육</a><br>
					<a href="lectureSearch.do?keyword=교육(초등교육)">초등교육</a><br>
					<a href="lectureSearch.do?keyword=교육(중등교육)">중등교육</a>
				</div>
			</div>
			<div class="col-2" style="height: 240px;">
				공학<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=공학(건축)">건축</a><br>
					<a href="lectureSearch.do?keyword=공학(기계금속)">기계·금속</a><br>
					<a href="lectureSearch.do?keyword=공학(전기전자)">전기·전자</a><br>
					<a href="lectureSearch.do?keyword=공학(소재재료)">소재·재료</a><br>
					<a href="lectureSearch.do?keyword=공학(컴퓨터통신)">컴퓨터·통신</a><br>
					<a href="lectureSearch.do?keyword=공학(산업)">산업</a><br>
					<a href="lectureSearch.do?keyword=공학(화공)">화공</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				자연<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=자연(농림수산)">농림·수산</a><br>
					<a href="lectureSearch.do?keyword=자연(생물화학환경)">생물·화학·환경</a><br>
					<a href="lectureSearch.do?keyword=자연(생활과학)">생활과학</a><br> 
					<a href="lectureSearch.do?keyword=자연(수학물리천문지리)">수학·물리·천문·지리</a>
				</div>
			</div>
			<div class="col-2" style="height: 150px;">
				의약<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=의약(의료)">의료</a><br>
					<a href="lectureSearch.do?keyword=의약(간호)">간호</a><br>
					<a href="lectureSearch.do?keyword=의약(약학)">약학</a><br>
					<a href="lectureSearch.do?keyword=의약(치료보건)">치료·보건</a>
				</div>
			</div>
			<div class="col-2" style="height: 210px;">
				예체능<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=예체능(디자인)">디자인</a><br>
					<a href="lectureSearch.do?keyword=예체능(응용예술)">응용예술</a><br>
					<a href="lectureSearch.do?keyword=예체능(무용체육)">무용·체육</a><br>
					<a href="lectureSearch.do?keyword=예체능(미술조형)">미술·조형</a><br>
					<a href="lectureSearch.do?keyword=예체능(연극영화)">연극·영화</a><br>
					<a href="lectureSearch.do?keyword=예체능(음악)">음악</a>
				</div>
			</div>
			<div class="col-2" style="height: 60px;">
				융·복합<br>
				<div class="classthema">
					<a href="lectureSearch.do?keyword=융복합(융복합)">융·복합</a>
				</div>
			</div>
		</div>
	</div>
	<!-- lectureSearch.js 추가 -->
	<script src="./js/lectureSearch.js"></script>
	<!-- myPage.js 추가 -->
	<script src="./js/myPage.js"></script>
</body>
</html>