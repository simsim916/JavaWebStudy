<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/myStyle.css">
</head>
<body>
<h2> Hello Spring MVC !!! </h2>
<P> Home_time : ${serverTime} </P>
<hr>
<c:if test="${!empty sessionScope.loginName}">
	${sessionScope.loginName}님 안녕하세요<br>
</c:if>
<c:if test="${empty sessionScope.loginId}">
	로그인 후 이용하세요<br>
</c:if>
<c:if test="${!empty requestScope.message}">
	<hr><h4>${requestScope.message}</h4>
</c:if>
<hr>
<!-- 절대경로로 이미지넣어주기 -->
<img alt="" src="resources/images/ccc.gif" width="70" height="100">
<hr>
<!-- Login 전 -->
<c:if test="${empty sessionScope.loginID}">
	&nbsp;<a href="member/loginForm">LoginF</a>
	&nbsp;<a href="member/joinForm">JoinF</a>
</c:if>
<!-- Login 후 -->
<c:if test="${!empty sessionScope.loginName}">
	&nbsp;<a href="member/detail?jCode=D">내정보</a>
	&nbsp;<a href="member/detail?jCode=U">내정보수정</a>
	&nbsp;<a href="member/logout">Logout</a>
	&nbsp;<a href="member/delete">회원탈퇴</a>
</c:if>
<br><hr>
	&nbsp;<a href="member/memberList">MList</a>
	&nbsp;<a href="jo/joList">JList</a>
<hr>
	&nbsp;<a href="board/boardList">BList</a>
	
</body>
</html>
