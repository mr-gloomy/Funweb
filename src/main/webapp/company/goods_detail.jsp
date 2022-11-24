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
 	<script type="text/javascript">
 		function isBasket(){
 			// 옵션 선택 체크 (크기,색상)
//  			alert(document.fr.size.value);
//  			console.log(document.fr.size);

			if(document.fr.size.value == ""){//크기옵션 선택X
				alert("크기를 선택하시오.")
			document.fr.size.focus();
			return;
			}
			
			if(document.fr.color.value == ""){//크기옵션 선택X
				alert("색상을 선택하시오.")
			document.fr.color.focus();
			return;
			}
			
 			// 장바구니 페이지 이동
 			var isMove = confirm("장바구니 페이지로 이동하시겠습니까?");
 			
 			if(isMove){
 				// 장바구니 페이지로 이동
 				alert(" 장바구니 페이지로 이동 ");
 				document.fr.action="./BasketAddAction.ba";
 				document.fr.isMove.value = isMove;
 				document.fr.submit();
 			}else{
 				// 쇼핑계속하기
 				alert(" 쇼핑계속하기 ");
 				// 장바구니 정보 전달&저장 ./BasketAddAction.ba
 				// + 쇼핑정보
 				location.href="GoodsList.go"
 			}
 		}// isBasket() 끝
 		
 		function isOrder(){
 			if(document.fr.size.value == ""){//크기옵션 선택X
				alert("크기를 선택하시오.")
				document.fr.size.focus();
				return;
			}
			
			if(document.fr.color.value == ""){//크기옵션 선택X
				alert("색상을 선택하시오.")
				document.fr.color.focus();
				return;
			}
 			alert(" 구매 페이지 이동(미구현) "); 
 			
 		}
 	
 	</script>

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
		<%-- 		${dto } --%>
		<article>
			<h1>ITWILL's Shop 상세페이지</h1>
			<form action="" method="post" name="fr">
			 <input type="hidden" name="gno" value="${dto.gno }">
			 <input type="text" name="isMove" value="">
			<table border="1" id="notice">
				<tr>
					<td width="310">
						<img src="./upload/${dto.image.split(',')[0] }" width="300" height="300">
					</td>
					
					<td>
					상품정보(구매옵션) <br>
					<h2>상품명 : ${dto.name } <br></h2>
					<h2>판매가격 : <fmt:formatNumber value="${dto.price }"/>원 <br></h2>
					<h2>구매수량 : <input type="number" name="amount" value="1"> <br></h2>
					<h2>(남은수량 : ${dto.amount }개) <br></h2>
					<hr>
					<h2>구매 옵션 <br></h2>
					<h2>크기 :
					<select name="size">
						<option value="">크기를 선택하세요</option>
						<c:forEach var="size" items="${dto.size.split(',') }">
						<option value="${size }">${size }</option>
						</c:forEach>
					</select>
					</h2>
					<h2>색상 :
							<select name="color">
						<option value="">색상을 선택하세요</option>
						<c:forTokens var="color" items="${dto.color }" delims=",">
						<option value="${color }">${color }</option>
						</c:forTokens>
					</select><br>
					</h2>
					<hr>
					<h2>총 가격 : <fmt:formatNumber value="${dto.price * 1 }"/> 원</h2>
					<h2>
						<a href="javascript: isBasket();">[장바구니 담기]</a>		
						<a href="javascript: isOrder();">[바로 구매하기]</a>
					</h2>
					
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<c:forTokens items="${dto.image }" delims="," var="img">
							<c:if test="${img != 'null' }">
								<img src="./upload/${img }" width="400" height="500"><br>
							</c:if>
							<c:if test="${img == 'null' }">
								<img src="./upload/default.jpg" width="400" height="500"><br>
							</c:if>
						</c:forTokens>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">리뷰/QnA</td>
				</tr>
				
			</table>
			</form>	
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



