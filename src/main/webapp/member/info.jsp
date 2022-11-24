<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="./css/default.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
 <script type="text/javascript">

 </script>
</head>
<body>
	<div id="wrap">
		<!-- 헤더들어가는 곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 추가할 페이지를 컴파일후 페이지를 추가 -->
		<!-- 헤더들어가는 곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문메인이미지 -->
		<div id="sub_img_member"></div>
		<!-- 본문메인이미지 -->
		<!-- 왼쪽메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="./MemberUpdate.me">회원정보 수정</a></li>
				<li><a href="./MemberDelete.me">회원정보 삭제</a></li>
				<li><a href="./OrderList.or">주문내역 조회</a></li>
				<c:if test="${id == 'admin' }">
				<li><a href="./MemberAdmin.me">회원정보 관리</a></li>
				<li><a href="./AdminGoodsAdd.ag">쇼핑몰 상품 등록</a></li>
				<li><a href="./AdminGoodsList.ag">상품리스트(관리자)</a></li>
				<li><a href="./AdminOrderList.ao">주문리스트(관리자)</a></li>
	
				
				</c:if>
			</ul>
		</nav>
		<!-- 왼쪽메뉴 -->
		<!-- 본문내용 -->
		<article>
			<h1>회원 정보</h1>
			<form id="join" >
				<fieldset>
					<legend>기본정보</legend>
					<label>아이디</label> <input type="text" name="id" class="id" value="${dto.id }"><br>
					<label>비밀번호</label> <input type="password" name="pw" value="${dto.pw }"><br>
					<label>이 름</label> <input type="text" name="name" value="${dto.name }"><br>
					<label>E-Mail</label> <input type="email" name="email" value="${dto.email }"><br>
					<label>주소</label> <input type="text" name="addr" value="${dto.addr }"><br>
					<label>연락처</label> <input type="text" name="tel" value="${dto.tel }"><br>
					<label>생년월일</label>
					<select name="birth" >
						<option>${dto.birth.split("\\.")[0] }년</option>
					</select>
					<select name="birth">
						<option>${dto.birth.split("\\.")[1] }월</option>
					</select>
					<select name="birth">
						<option>${dto.birth.split("\\.")[2] }일</option>
					</select>
					<hr>
					<label>성별</label> 
					<input type="radio" name="gender" value="남" 
					<c:if test="${dto.gender == '남' }">
					checked="checked"
					</c:if>
					>남
					<input type="radio" name="gender" value="여"
					<c:if test="${dto.gender == '여' }">
					checked="checked"
					</c:if>>여<br>
				</fieldset>
				<div class="clear"></div>
				<div id="buttons">
					<input type="submit" value="회원가입" class="submit"> 
					<input type="reset" value="초기화" class="cancel">
				</div>
			</form>
		</article>
		<!-- 본문내용 -->
		<!-- 본문들어가는 곳 -->

		<div class="clear"></div>
		<!-- 푸터들어가는 곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터들어가는 곳 -->
	</div>
</body>
</html>