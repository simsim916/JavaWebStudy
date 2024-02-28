<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" 
	href="resources/myLib/myStyle.css">
</head>
<body>
<h2> Hello Spring JPA !!! </h2>
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
	&nbsp;<a href="board/boardList">BList</a>
	&nbsp;<a href="bcrypt">BCrypt</a><br>
	&nbsp;<a href="board/bPageList">BPage</a><br>
	&nbsp;<a href="/axtestform">AjaxTest</a><br>
	&nbsp;<a href="/ginsert">Ginsert</a>
	&nbsp;<a href="/glist">GList</a>
	&nbsp;<a href="/gupdate">GUpdate</a><br>
	&nbsp;<a href="/gdelete?gno=6">Gdelete=6</a><br>
	&nbsp;<a href="/gpage">gpage</a><br>
	
<!-- 절대경로로 이미지넣어주기 -->
<img alt="" src="resources/images/Arcana1.png" width="250" height="250">
<img alt="" src="resources/images/Arcana2.gif" width="250" height="250">
<img alt="" src="resources/images/Arcana3.gif" width="250" height="250"><br>
<hr>
</body>
</html>
