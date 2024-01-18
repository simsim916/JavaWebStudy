<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table><thead><tr>
			<th>학생번호</th><th>이름</th><th>나이</th><th>조번호</th><th>정보</th><th>포인트</th>
			</tr></thead><tbody></tbody>
	<c:if test="${!empty requestScope.mList}">
	<c:set value="${requestScope.mList}" var="n" />
		<c:forEach var="m" items="${n}">
			<tr>
				<td>${m.id}<td><td>${m.password}<td><td>${m.name}<td><td>${m.age}<td>
				<td>${m.jno}<td><td>${m.info}<td><td>${m.point}<td><td>${m.birthday}<td><td>${m.rid}<td>
			</tr>
		</c:forEach>
	</c:if>
	<c:if test="${empty requestScope.mList}">
		<tr>
			<td colspan="6">	<h3>출력할 자료가 없습니다.</h3></td>
		</tr>
	</c:if>
</body>
</html>