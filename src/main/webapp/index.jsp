<%@page import="Lecture.LectureDTO"%>
<%@page import="Lecture.LectureDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DEVELOP 메인화면</title>
<!-- bootstrap CSS 추가 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- custom CSS 추가 -->
<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
	<%@ include file="WEB-INF/views/layout/header.jsp" %>
	
	<div id="carouselExampleIndicators" class="carousel slide">
		<div class="carousel-indicators">
			<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
			<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
			<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
		</div>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img src="./image/mainBanner/banner1.png" class="d-block w-100" alt="...">
			</div>
			<div class="carousel-item">
				<img src="./image/mainBanner/banner2.png" class="d-block w-100" alt="...">
			</div>
			<div class="carousel-item">
				<img src="./image/mainBanner/banner3.png" class="d-block w-100" alt="...">
			</div>
		</div>
		<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next" id="btn-slide-next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span class="visually-hidden">Next</span>
		</button>
	</div>
	
	<div class="search-container">
		<div class="row d-flex">
			<div class="col-2" style="text-align: center;">
				<h2 style="color: #fff; margin-top: 5px; font-weight: bold;">검색</h2>
			</div>

			<div class="col-9" style="text-align: center; margin-left: 30px;">
				<form class="search-form" action="lectureSearch.do" method="post">
					<div class="input-group">
						<input type="text" class="form-control" name="keyword" placeholder="검색어 입력" aria-label="검색어 입력" aria-describedby="button-addon" id="searchbar">
						<div class="input-group-append">
							<button type="submit" id="btn-search">
								<img src="./image/icon/search.png" alt="search" style="max-width: 100%;">
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-2" style="text-align: center;"></div>
			<div class="col-9">
				<div class="indexSearchKeyword-container">
					<a class="searchKeyword" href="lectureSearch.do?keyword=프로그래밍">프로그래밍</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=재테크">재테크</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=마케팅">마케팅</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=IT">IT</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=교육">교육</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=경제">경제</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=간호">간호</a>
					<a class="searchKeyword" href="lectureSearch.do?keyword=디자인">디자인</a>
				</div>
			</div>
		</div>
	</div>
	
	<div class="recommendation-container">
		<h3 style="text-align: left;">인기강좌 TOP6</h3>
		<div class="row">
			<%		LectureDAO dao = new LectureDAO();
						    // 상위 6개의 클래스 정보 가져오기
						    List<LectureDTO> top6Classes = dao.getTop6Classes();
					int i = 0;
				            for (LectureDTO classInfo : top6Classes) {
				            	i++;
				                String cardClass;
				                String topImg;
				                if(i==1){
				                	cardClass = "classCard_1st";
				                	topImg = "1st";
				                } else if(i==2){
				                	cardClass = "classCard_2nd";
				                	topImg = "2nd";
				                } else{
				                	cardClass = "classCard_another";
				                	topImg = "null";
				                }
			%>
			<div class="card <%=cardClass %>" onclick="lectureInfo(<%=classInfo.getClassID()%>)">
				<img src="./image/<%=topImg %>.png" style="height: 100px; width: 100px; position: absolute; float: left;">
				<img src="<%= classInfo.getClassImg() %>" class="card-img-top" alt="...">
				<div class="card-body" style="font-weight: bold; padding: 5px;">
					<span><%= classInfo.getClassTitle() %></span>
					<br>
					<p class="card-text" style="font-size: 13px; font-weight: normal; margin-top: 5px;">
						<%= classInfo.getClassProfessor() %><br> 수강인원:
						<%= classInfo.getClassPersonnel() %>명<br>
					</p>
				</div>
			</div>
			<%
            }
        %>
		</div>
	</div>
	
	<%@ include file="WEB-INF/views/layout/footer.jsp" %>
	<!-- bootstrap.js 추가 -->
	<script src="./js/bootstrap.bundle.min.js" /></script>
	<!-- jquery.js 추가 -->
	<script src="./js/jquery.min.js" /></script>
	<!-- popper.js 추가 -->
	<script src="./js/popper.min.js" /></script>
	<!-- siderbar.js 추가 -->
	<script src="./js/sidebar.js" /></script>
	<!-- slider.js 추가 -->
	<script src="./js/slider.js" /></script>
	<!-- lectureInfo.js 추가 -->
	<script src="./js/lectureInfo.js"></script>
</body>
</html>