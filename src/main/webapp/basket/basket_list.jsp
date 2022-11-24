<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
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
</head>
<body>
	<div id="wrap">
		<!-- 헤더들어가는 곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더들어가는 곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 메인이미지 -->
		<div id="sub_img_center"></div>
		<!-- 메인이미지 -->

		<!-- 왼쪽메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="#">Notice</a></li>
				<li><a href="#">Public News</a></li>
				<li><a href="#">Driver Download</a></li>
				<li><a href="#">Service Policy</a></li>
			</ul>
		</nav>
		<!-- 왼쪽메뉴 -->

		<!-- 게시판 -->
		<article>
				
			<h1>${id }'s 장바구니</h1>
			<table id="notice">
				<tr>
					<th class="tno">번호</th>
					<th class="ttitle">이미지</th>
					<th class="twrite">상품명</th>
					<th class="tdate">가격</th>
					<th class="tdate">수량</th>
					<th class="tdate">사이즈</th>
					<th class="tdate">색상</th>
					<th class="tdate">관리</th>
				</tr>
			<c:if test="${basketList.size() != 0 }" >
			<c:forEach var="i" begin="0" end="${basketList.size()-1 }" step="1">
				<c:set var="bk" value="${basketList[i] }"/>
				<c:set var="goods" value="${goodsList[i] }"/>
				
				<tr>
					<td class="tno">${bk.b_num }</td>
					<td class="ttitle">
						<img src="./upload/${goods.image.split(',')[0] }"
						 width="60" height="60">
					</td>
					<td class="twrite">${goods.name }</td>
					<td class="tdate">${goods.price }</td>
					<td class="tdate">
			<form action="./AmountUpdateAction.ba?b_num=${bk.b_num }" method="post">
					<input type="text" name="b_g_amount" value="${bk.b_g_amount }"> 
					<input type="submit" value="수정">
			</form>
					</td>
					<td class="tdate">${bk.b_g_size }</td>
					<td class="tdate">${bk.b_g_color }</td>
					<td class="tdate">
					<a href="./BasketDeleteAction.ba?b_num=${bk.b_num }">삭제</a>
					</td>
				</tr>
			</c:forEach>
			</c:if>
			</table>
			<div id="table_search"></div>
			<input type="button" value="구매하기" class="btn"
			onclick="location.href='./OrderStart.or';">
			
			<input type="button" value="쇼핑계속하기" class="btn"
			onclick="location.href='./GoodsList.go';">
			<div class="clear"></div>
			<div id="page_control"></div>
		</article>
		<!-- 게시판 -->
		<!-- 본문들어가는 곳 -->
		<div class="clear"></div>
		<!-- 푸터들어가는 곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터들어가는 곳 -->
	</div>
</body>
</html>