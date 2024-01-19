<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Login ||</title>
</head>
<body>
<h2>** Login Test **</h2>
<form action="/web02/login" method="post">
<table>
	<tr height="30"><td><label for="id">I D</label></td>
		<td><input type="text" id="id" name="id"></td>
	</tr>
	<tr height="30"><td><label for="password">Password</label></td>
		<td><input type="text" id="password" name="password"></td>
	</tr>
	<tr height="30"><td></td>
		<td><input type="submit" value="로그인">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>

<c:if test="${!empty requestScope.loginMessage}">
	<h3>로그인에 실패 하였습니다!! 다시 시도해주세요!</h3>
</c:if>
</body>
</html>