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
<c:if test="${empty sessionScope.loginID}">
	로그인 후 이용하세요<br>
</c:if>
<c:if test="${!empty requestScope.message}">
	<hr><h4>${requestScope.message}</h4>
</c:if>
<hr>
<!-- 절대경로로 이미지넣어주기 -->
<img alt="" src="resources/images/KarinaSapporo1.jpg" width="70" height="100">
<img alt="" src="resources/images/KarinaSapporo2.jpg" width="45" height="100">
<img alt="" src="resources/images/KarinaSapporo3.jpg" width="80" height="100">
<img alt="" src="resources/images/KarinaSapporo4.jpg" width="75" height="100">
<img alt="" src="/spring02/resources/images/karina1.gif" width="50" height="100">
<img alt="" src="resources/images/karina2.gif" width="65" height="100">
<img alt="" src="resources/images/KarinaBlack.jpg" width="60" height="100">
<img alt="" src="resources/images/KarinaBlack2.jpg" width="65" height="100">
<img alt="" src="resources/images/KarinaBlack4.jpg" width="70" height="100">
<img alt="" src="resources/images/KarinaBlack5.jpg" width="70" height="100">
<img alt="" src="resources/images/KarinaBlack6.jpg" width="100" height="100">
<img alt="" src="resources/images/KarinaFlower1.jpg" width="80" height="100">
<img alt="" src="resources/images/KarinaFlower2.jpg" width="100" height="100">
<img alt="" src="resources/images/KarinaSpring.jpg" width="50" height="100">
<img alt="" src="resources/images/KarinaWhite1.jpg" width="45" height="100">
<img alt="" src="resources/images/Karina3.jpg" width="100" height="55">
<img alt="" src="resources/images/KarinaPink.jpg" width="100" height="70">
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
</body>
</html>
