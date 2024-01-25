<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="" href="/spring02/resources/myLib/myStyle.css">
	<title>|| Login ||</title>
</head>
<body>
<h2>** Login Test **</h2>
<form action="login" method="post">
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

<c:if test="${!empty requestScope.message}">
	<h3>${requestScope.message}</h3>
</c:if>
<a href="/spring02/home">home</a>
<a href="javascript:history.go(-1)">back</a>
</body>
</html>