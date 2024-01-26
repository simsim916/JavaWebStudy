<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Spring02_JoDetail </title>
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/myStyle.css">
</head>
<body>
<table border="1" style="width:100%">
	<tr bgcolor="purple">
		<th>JNO</th><th>JNAME</th><th>CAPTAIN</th>
		<th>PROJECT</th><th>SLOGAN</th>
	</tr>
<c:set var="a" value="${requestScope.apple}"/>
	<tr>
		<td>${a.jno}</td><td>${a.jname}</td><td>${a.captain}</td>
		<td>${a.project}</td><td>${a.slogan}</td>
	</tr>
</table>

<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}
</c:if>
<br>
&nbsp;<a href="joList">목록</a>&nbsp;
&nbsp;<a href="joDetail?jCode=U&jno=${a.jno}">수정</a>&nbsp;
&nbsp;<a href="joDelete?jno=${a.jno}">삭제</a>&nbsp;
<br>
&nbsp;<a href="/spring02/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>