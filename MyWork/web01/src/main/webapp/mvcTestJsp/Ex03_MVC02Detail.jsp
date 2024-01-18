<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<c:if test="${!empty requestScope.userinfo}">
			<tr>
				<td>${requestScope.userinfo.sno}<td><td>${requestScope.userinfo.name}<td><td>${requestScope.userinfo.age}<td>
				<td>${requestScope.userinfo.jno}<td><td>${requestScope.userinfo.info}<td><td>${requestScope.userinfo.point}<td>
			</tr>
	</c:if>
	<c:if test="${empty requestScope.userinfo}">
		<tr>
			<td colspan="6">	<h3>출력할 자료가 없습니다.</h3></td>
		</tr>
	</c:if>
</body>
</html>