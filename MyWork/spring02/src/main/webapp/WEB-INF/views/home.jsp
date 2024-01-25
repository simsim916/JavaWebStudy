<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<link rel="stylesheet" type="" href="/spring02/resources/myLib/myStyle.css">
	<title>Home</title>
</head>
<body>
	<h2>Hello Spring_MVC02!</h2>
	<P>Home_time : ${serverTime}</P>
	<hr>
	
	<c:if test="${!empty sessionScope.loginName}">
		<h3>${sessionScope.loginName}님 안녕하세요</h3><br>
	</c:if>
	<c:if test="${!empty requestScope.message}">
		<h3>${requestScope.message}</h3><br>
	</c:if>
	<c:if test="${empty sessionScope.loginName}"><br>
		<h3>로그인 후 이용하세요</h3>
	</c:if>
	<hr>
<img alt="mainImage" src="/spring02/resources/images/letsgo.png" width="300" height="200">
	<hr>

	<c:if test="${empty sessionScope.loginName}">
		<a href="member/loginForm">Login</a>&nbsp;
		<a href="member/joinForm">Join</a>&nbsp;
		<hr>
	</c:if>
	<c:if test="${!empty sessionScope.loginName}">
		<a href="member/detail?jCode=D">Myinfo</a>&nbsp;
		<a href="member/detail?jCode=U">MyinfoEdit</a>&nbsp;
		<a href="member/logout">Logout</a>&nbsp;
		<a href="member/delete">Delete</a>&nbsp;
		<hr>
	</c:if>

	<a href="member/memberList">MList</a>
</body>
</html>
