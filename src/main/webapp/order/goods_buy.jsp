<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<jsp:include page="../inc/top.jsp"></jsp:include>
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
<h1>상품 구매하기</h1>
<form action="./OrderAddAction.or" method="post">
	<table id="notice">
	<tr>
		<th class="ttitle" colspan="8">
		<h3>주문 상세내역</h3>
		</th>
	</tr>
	<tr>
		<th class="tno">번호</th>
		<th class="ttitle">이미지</th>
		<th class="twrite">상품명</th>
		<th class="tdate">가격</th>
		<th class="tdate">수량</th>
		<th class="tdate">색상</th>
		<th class="tdate">크기</th>
		<th class="tread">관리</th>
	</tr>

	<!-- forEach 써서 기존의 데이터 출력하기 -->
	<c:forEach var="i" begin="0" end="${basketList.size() -1 }" step="1">
		<c:set var="bk" value="${basketList[i] }" />
		<c:set var="goods" value="${goodsList[i] }" />
		<tr>
			<td class="tno">${bk.b_num }</td>
			<td class="ttitle"><img
				src="./upload/${goods.image.split(',')[0] }" width="60"
				height="60"></td>
			<td class="twrite">${goods.name }</td>
			<td class="tdate">${goods.price }</td>
			<td class="tdate">${bk.b_g_amount }</td>
			<td class="tdate">${bk.b_g_color }</td>
			<td class="tdate">${bk.b_g_size }</td>
			<td class="tread"><a
				href="./BasketDeleteAction.ba?b_num=${bk.b_num }">삭제</a> <!-- 보통 삭제는 redirect를 사용 -->
			</td>
		</tr>
	</c:forEach>

	<tr>
		<th class="ttitle" colspan="8">
		  <h3>주문자 정보</h3>
		</th>
	<tr>
	  <td colspan="8">
	     <h2>이름 : ${mDto.name }</h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>전화번호 : ${mDto.tel }</h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>이메일주소 : ${mDto.email }</h2>
	  </td>
	</tr>
	<tr>
		<th class="ttitle" colspan="8">
		  <h3>배송지 정보</h3>
		</th>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>받는사람 : <input type="text" name="o_r_name" value="${mDto.name }"></h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>연락처 : <input type="text" name="o_r_phone" value="${mDto.tel }"></h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>배송지 주소 : <input type="text" name="o_r_addr1" value="${mDto.addr }"></h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>배송지 상세주소 : <input type="text" name="o_r_addr2"></h2>
	  </td>
	</tr>
	<tr>
	  <td colspan="8">
	     <h2>메세지 : <input type="text" name="o_r_msg"></h2>
	  </td>
	</tr>
	
	
	<tr>
		<th class="ttitle" colspan="8">
		  <h3>결제 정보</h3>
		</th>
	</tr>
	  <td colspan="2">
	    <input type="radio" name="o_t_type" value="온라인입금"> 온라인 입금 <br>
	    입금자명 : <input type="text" name="o_t_payer" value="${mDto.name }">
	  </td>
	  <td colspan="2">
	     <input type="radio" name="o_t_type" value="신용카드"> 신용카드
	  </td>
	  <td colspan="2">
	     <input type="radio" name="o_t_type" value="카카오페이"> 카카오 페이
	  </td>
	  <td colspan="2">
	   <input type="radio" name="o_t_type" value="네이버페이"> 네이버 페이
	  </td>
	
	
	</table>
	<div id="table_search">
		<input type="submit" value="주문하기" class="btn">
		
		<input type="reset" value="취소" class="btn">
	</div>
</form>
<div class="clear"></div>
<div id="page_control">

</div>
</article>
<!-- 게시판 -->
<!-- 본문들어가는 곳 -->
<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
	<jsp:include page="../inc/bottom.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>