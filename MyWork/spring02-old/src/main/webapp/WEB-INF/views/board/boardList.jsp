<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/myStyle.css">
	<title>** Spring MVC2 BoardList **</title>
</head>
<body>
<h2>** Spring MVC2 BoardList **</h2>
<c:if test="${!empty requestScope.message}">
	<hr><h3>${ requestScope.message}</h3><hr>
</c:if>
<a href="boardInsert">글쓰기</a>
<table>
	<thead><tr>
			<th>seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>Count</th>
			</tr>
	</thead>
	<tbody>
	<c:if test="${!empty requestScope.list}">
	<c:set value="${requestScope.list}" var="n" />
		<c:forEach var="m" items="${n}">
			<tr>
				<td>${m.seq}</td><td><a href="detail?seq=${m.seq}">${m.title}</a></td><td>${m.id}</td><td>${m.regdate}</td><td>${m.cnt}</td>
			</tr>
		</c:forEach>
	</c:if>
	<c:if test="${empty requestScope.mList}">
		<tr>
			<td colspan="5">	<h3>출력할 자료가 없습니다.</h3></td>
		</tr>
	</c:if>
	</tbody>
</table>
<a href="/spring02/home">Home</a>
<a href="javascript:history.go(-1)">이전으로</a>
</body>
</html>