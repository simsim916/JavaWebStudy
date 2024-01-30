<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set value="${requestScope.board}" var="b"/>
<html>
<head>
<meta charset="UTF-8">
<title>${b.seq}번 게시글</title>
</head>
<body>
<c:if test="${!empty requestScope.message}">
	<hr><h3>${ requestScope.message}</h3><hr>
</c:if>
<h2>${b.seq}번 게시글</h2>
<p>글쓴이 : ${b.id}</p>
<p>작성일 : ${b.regdate}</p>
<h3>제목 : ${b.title}</h3>
<p>내용 : ${b.content}</p>

<c:if test="${b.id == sessionScope.loginId}">
	<a href="boardUpdate?seq=${b.seq}">Update</a>
	<a href="delete?seq=${b.seq}">Delete</a>
</c:if>
<a href="/spring02/home">Home</a>
<a href="javascript:history.go(-1)">이전으로</a>
</body>
</html>