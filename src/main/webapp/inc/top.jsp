<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header>
	<div id="login">
		<c:if test="${id == null }">
			<a href="./MemberLogin.me">login</a> | <a href="./MemberJoin.me">join</a>
		</c:if>
		<c:if test="${id != null }">
			<a href="./MemberInfo.me">${id }'s info</a> |
		<a href="./BasketList.ba">	장바구니</a>	|
		<a href="./MemberLogout.me">logut</a>
		</c:if>
	</div>
	<div class="clear"></div>
	<!-- 로고들어가는 곳 -->
	<div id="logo">
		<img src="./images/logo.gif" width="265" height="62" alt="Fun Web">
	</div>
	<!-- 로고들어가는 곳 -->
	<nav id="top_menu">
		<ul>
			<li><a href="./Main.me">HOME</a></li>
			<li><a href="./GoodsList.go">쇼핑몰</a></li>
			<li><a href="#">SOLUTIONS</a></li>
			<li><a href="./AdminGoodsList.ag">관리자 페이지</a></li>
			<li><a href="#">CONTACT US</a></li>
		</ul>
	</nav>
</header>
