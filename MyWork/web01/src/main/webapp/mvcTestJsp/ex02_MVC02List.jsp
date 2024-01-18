<%@page import="mvcTest.StudentDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC02_List_JSTL</title>
</head>
<body>
	<table><thead><tr>
			<th>학생번호</th><th>이름</th><th>나이</th><th>조번호</th><th>정보</th><th>포인트</th>
			</tr></thead><tbody></tbody>
	<c:if test="${!empty requestScope.stList}">
			<tr>
				<td>${requestScope.stList.sno}<td><td>${requestScope.stList.name}<td><td>${requestScope.stList.age}<td>
				<td>${requestScope.stList.jno}<td><td>${requestScope.stList.info}<td><td>${requestScope.stList.point}<td>
			</tr>
	</c:if>
	<c:if test="${empty requestScope.stList}">
		<tr>
			<td colspan="6">	<h3>출력할 자료가 없습니다.</h3></td>
		</tr>
	</c:if>
</body>
</html>