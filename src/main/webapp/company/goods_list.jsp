<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<!-- 헤더가 들어가는 곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더가 들어가는 곳 -->

		<!-- 본문 들어가는 곳 -->
		<!-- 서브페이지 메인이미지 -->
		<div id="sub_img"></div>
		<!-- 서브페이지 메인이미지 -->
		<!-- 왼쪽메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="#">Welcome</a></li>
				<li><a href="#">History</a></li>
				<li><a href="#">Newsroom</a></li>
				<li><a href="#">Public Policy</a></li>
			</ul>
		</nav>
		<!-- 왼쪽메뉴 -->
		<!-- 내용 -->
		<article>
			<h1>ITWILL's Shop</h1>
			<c:set var="size" value="${goodsList.size() }" />
			<c:set var="col" value="8" />
			<c:set var="row" value="${Math.ceil(size/col) }" />
			<c:set var="num" value="0" />
			<%-- 		${size } --%>
			<%-- 		${col } --%>
			<%-- 		${row } --%>
			<%-- 		${num } --%>
			<div>
				<table id="notice">
					<tr>
						<th class="tno"><a href="./GoodsList.go">전체</a></th>
						<th class="ttitle"><a href="./GoodsList.go?item=best">베스트상품</a>
						</th>
						<th class="twrite"><a href="./GoodsList.go?item=outwear">외투</a>
						</th>
						<th class="tdate"><a href="./GoodsList.go?item=fulldress">정장</a>
						</th>
						<th class="tdate"><a href="./GoodsList.go?item=Tshirts">티셔츠</a>
						</th>
						<th class="tdate"><a href="./GoodsList.go?item=shirts">셔츠</a>
						</th>
						<th class="tdate"><a href="./GoodsList.go?item=pants">바지</a>
						</th>
						<th class="tdate"><a href="./GoodsList.go?item=shoes">신발</a>
						</th>
					</tr>

					<c:forEach begin="1" end="${row }" step="1">
						<tr>
							<c:forEach begin="1" end="${col }" step="1">
								<c:if test="${num < size }">
									<c:set var="goods" value="${goodsList[num] }" />

									<td><a href="./GoodsDetail.go?gno=${goods.gno }"><img
											src="./upload/${goods.image.split(',')[0] }" width="80"
											height="80"> <br> ${goods.name } <br>
											${goods.price }원 <br></a></td>

									<c:set var="num" value="${num+1 }" />
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>
		</article>
		<!-- 내용 -->
		<!-- 본문 들어가는 곳 -->
		<div class="clear"></div>
		<!-- 푸터 들어가는 곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는 곳 -->
	</div>
</body>
</html>



